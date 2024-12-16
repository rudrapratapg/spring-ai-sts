package com.projectai.springai.bootstrap;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.projectai.springai.rag.config.VectorStoreProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoadVectorStore implements CommandLineRunner {
	
	@Autowired
	VectorStore vectorStore;
	
	@Autowired
	VectorStoreProperties vectorStoreProperties;

	@Override
	public void run(String... args) throws Exception {
		log.debug("Loading vector store...");
		
		// Check if vector store is empty
		if(vectorStore.similaritySearch("movie").isEmpty()) {
			log.debug("Loading documents into vector store...");
			
			vectorStoreProperties.getDocumentsToLoad().parallelStream().forEach(
				document -> {
					log.debug("Loading document -> " + document.getFilename());
					
					try {
						// Use TikaDocumentReader to read different types of documents
						TikaDocumentReader documentReader = new TikaDocumentReader(document);
						List<Document> documents = documentReader.get();
						
						// Split documents into chunks
						TextSplitter textSplitter = new TokenTextSplitter();
						List<Document> splittedDocuments = textSplitter.apply(documents);
						
						// Log document details
						splittedDocuments.forEach(doc -> {
							log.debug("Doc metadata: " + doc.getMetadata());
							log.debug("Doc content length: " + doc.getContent().length());
						});
						
						// Add to vector store
						vectorStore.add(splittedDocuments);
					} catch (Exception e) {
						log.error("Error loading document: " + document.getFilename(), e);
					}
				}
			);
			
			log.info("Vector store loaded successfully!");
		} else {
			log.debug("Vector store already contains data.");
		}
	}
}

/*
 * import java.io.BufferedReader; import java.io.InputStreamReader; import
 * java.net.URL; import java.nio.charset.StandardCharsets; import
 * java.nio.file.Files; import java.nio.file.Paths; import java.util.HashMap;
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.CommandLineRunner; import
 * org.springframework.core.io.Resource; import
 * org.springframework.core.io.ResourceLoader; import
 * org.springframework.stereotype.Component;
 * 
 * import com.projectai.springai.rag.config.VectorStoreProperties;
 * 
 * import io.weaviate.client.WeaviateClient; import
 * io.weaviate.client.v1.data.model.WeaviateObject; import
 * lombok.extern.slf4j.Slf4j;
 * 
 * @Component
 * 
 * @Slf4j public class LoadVectorStore implements CommandLineRunner {
 * 
 * @Autowired private VectorStoreProperties vectorStoreProperties;
 * 
 * @Autowired private ResourceLoader resourceLoader;
 * 
 * @Autowired private WeaviateClient weaviateClient;
 * 
 * @Override public void run(String... args) throws Exception {
 * log.info("Loading vector store..."); List<Resource> documentsToLoad =
 * vectorStoreProperties.getDocumentsToLoad(); for (Resource document :
 * documentsToLoad) { String content = readResource(document); WeaviateObject
 * weaviateObject = WeaviateObject.builder() .className("Document")
 * .properties(new HashMap<String, Object>() {{ put("content", content); }})
 * .build(); weaviateClient.data().creator() .withClassName("Document")
 * .withProperties(weaviateObject.getProperties()) .run(); }
 * log.info("Vector store loaded successfully."); }
 * 
 * private String readResource(Resource resource) throws Exception { if
 * (resource.getURI().getScheme().equals("classpath")) { return new
 * String(Files.readAllBytes(Paths.get(resource.getURI())),
 * StandardCharsets.UTF_8); } else if
 * (resource.getURI().getScheme().equals("https")) { URL url =
 * resource.getURI().toURL(); try (BufferedReader reader = new
 * BufferedReader(new InputStreamReader(url.openStream()))) { StringBuilder
 * content = new StringBuilder(); String line; while ((line = reader.readLine())
 * != null) { content.append(line).append("\n"); } return content.toString(); }
 * } else { throw new
 * UnsupportedOperationException("Unsupported resource scheme: " +
 * resource.getURI().getScheme()); } } }
 */



//package com.projectai.springai.bootstrap;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import com.projectai.springai.rag.config.VectorStoreProperties;
//
//import io.weaviate.client.WeaviateClient;
//import io.weaviate.client.v1.batch.api.ObjectsBatcher;
//import io.weaviate.client.v1.data.model.WeaviateObject;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class LoadVectorStore implements CommandLineRunner {
//
//    @Autowired
//    private WeaviateClient weaviateClient;
//
//    @Autowired
//    private VectorStoreProperties vectorStoreProperties;
//
//    @Override
//    public void run(String... args) throws Exception {
//        log.debug("Loading vector store...");
//
//        // List to hold objects for batch creation
//        List<WeaviateObject> objects = new ArrayList<>();
//
//        // Iterate over documents
//        for (Resource resource : vectorStoreProperties.getDocumentsToLoad()) {
//            String documentPath = resource.getFile().getAbsolutePath();
//            try (FileInputStream is = new FileInputStream(Paths.get(documentPath).toFile())) {
//                byte[] data = is.readAllBytes();
//                String content = new String(data, "UTF-8");
//
//                // Prepare object properties
//                Map<String, Object> properties = new HashMap<>();
//                properties.put("content", content);
//                properties.put("media", ""); // Add metadata if needed
//
//                // Create a WeaviateObject
//                WeaviateObject object = WeaviateObject.builder()
//                        .className("YourClassName") // Replace with your schema's class name
//                        .properties(properties)
//                        .build();
//
//                objects.add(object);
//            } catch (IOException e) {
//                log.error("Failed to read document: " + documentPath, e);
//            }
//        }
//
//        // Perform batch operation
//        if (!objects.isEmpty()) {
//            ObjectsBatcher batcher = weaviateClient.batch().objectsBatcher();
//            objects.forEach(batcher::withObject);
//
//            batcher.run(); // Execute batch operation
//            log.info("Successfully added {} documents to Weaviate.", objects.size());
//        } else {
//            log.info("No documents were added to the vector store.");
//        }
//    }
//}




//package com.projectai.springai.bootstrap;
//
//import java.util.List;
//
//import org.springframework.ai.document.Document;
//import org.springframework.ai.reader.tika.TikaDocumentReader;
//import org.springframework.ai.transformer.splitter.TextSplitter;
//import org.springframework.ai.transformer.splitter.TokenTextSplitter;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.projectai.springai.rag.config.VectorStoreProperties;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class LoadVectorStore implements CommandLineRunner {
//	
//	@Autowired
//	VectorStore vectorStore;
//	
//	@Autowired
//	VectorStoreProperties vectorStoreProperties;
//
//	@Override
//	public void run(String... args) throws Exception {
//		log.debug("Loading vector store...");
//		if(vectorStore.similaritySearch("movie").isEmpty()) {
//			log.debug("Loading documents into vector store...");
//			vectorStoreProperties.getDocumentsToLoad().parallelStream().forEach(
//					document -> {
//						log.debug("loading document -> "+document.getFilename());
//						
//						TikaDocumentReader documentReader = new TikaDocumentReader(document);
//						List<Document> documents = documentReader.get();
//						
//						TextSplitter textSplitter = new TokenTextSplitter();
//						
//						List<Document> splittedDocuments = textSplitter.apply(documents);
//						
//						splittedDocuments.forEach(doc -> {
//							log.debug("Doc metadata: " + doc.getMedia());
//							log.debug("Doc content:" + doc.getFormattedContent());
//						});
//						
//						vectorStore.add(splittedDocuments);
//					}
//			);
//		}
//		log.debug("Vector store loaded!!");
//		
//	}
//
//}

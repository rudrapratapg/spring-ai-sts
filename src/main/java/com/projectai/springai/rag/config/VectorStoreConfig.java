package com.projectai.springai.rag.config;

import java.io.File;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

	@Bean
	public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, VectorStoreProperties vectorStoreProperties) {
		SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);
		File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());
//		Resource folder = vectorStoreProperties.getDocumentsToLoad().get(0);
		if(vectorStoreFile.exists()) {
			simpleVectorStore.load(vectorStoreFile);
		} else {
			System.out.println("Loading document into vector store...");
			vectorStoreProperties.getDocumentsToLoad().parallelStream()
			.forEach(
					document -> {
						System.out.println("Loading document: "+document.getFilename());
						TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(document);
						List<Document> docList = tikaDocumentReader.get();
								docList.forEach(doc -> {
									System.out.println("Doc metadata: " + doc.getMedia());
									System.out.println("Doc content:" + doc.getFormattedContent());
								});
								System.out.println("");
						TextSplitter textSplitter = new TokenTextSplitter();
						List<Document> splittedDocList = textSplitter.apply(docList);
						simpleVectorStore.add(splittedDocList);
					}
			);
			simpleVectorStore.save(vectorStoreFile);
		}
		return simpleVectorStore;
	}
}

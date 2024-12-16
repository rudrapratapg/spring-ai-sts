package com.projectai.springai.rag.config;

import java.io.File;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.RedisVectorStore.RedisVectorStoreConfig;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPooled;

@Configuration
public class VectorStoreConfig {

//	@Bean
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
	
	@Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisVectorStoreConfig redisVectorStoreConfig() {
        return RedisVectorStoreConfig.builder()
                .withIndexName("my-index")
                .withPrefix("embedding:")
                .withContentFieldName("content")
                .withEmbeddingFieldName("embedding")
                .withVectorAlgorithm(RedisVectorStore.Algorithm.HSNW)
                .build();
    }
    
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10); // max connections
        jedisPoolConfig.setMaxIdle(5); // max idle connections
        jedisPoolConfig.setMinIdle(1); // min idle connections
        jedisPoolConfig.setTestOnBorrow(true); // test connection before borrowing
        return new JedisPool(jedisPoolConfig, redisHost, redisPort, 0); // 0 is the default timeout
    }
    
    @Bean
    public JedisPooled jedisPooled() {
        return new JedisPooled(redisHost, redisPort);
    }
    
    @Bean
    public RedisVectorStore redisVectorStore(RedisVectorStoreConfig config, EmbeddingModel embeddingModel, JedisPooled jedisPooled) {
        return new RedisVectorStore(config, embeddingModel, jedisPooled, true);
    }
    
}

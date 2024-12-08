package com.projectai.springai.rag.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@ConfigurationProperties(prefix = "sfg.aiapp")
public class VectorStoreProperties {

	String vectorStorePath;
	List<Resource> documentsToLoad;

	public String getVectorStorePath() {
		return vectorStorePath;
	}

	public void setVectorStorePath(String vectorStorePath) {
		this.vectorStorePath = vectorStorePath;
	}

	public List<Resource> getDocumentsToLoad() {
		return documentsToLoad;
	}

	public void setDocumentsToLoad(List<Resource> documentsToLoad) {
		this.documentsToLoad = documentsToLoad;
	}
	
}

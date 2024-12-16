package com.projectai.springai.service;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Question;

public interface RAGService {
	
	public Answer retrieveAndGenerateAnswer(Question question);

}

package com.projectai.springai.service;

import com.projectai.springai.model.Answer;

public interface OpenAIService {
	
	Answer getAnswer(String question);

}

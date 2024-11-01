package com.projectai.springai.service;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.GetCapitalRequest;
import com.projectai.springai.model.Question;

public interface OpenAIService {
	
	Answer getAnswer(Question question);
	
	Answer getCapital(GetCapitalRequest getCapitalRequest);
	
	Answer getCapitalWithInfo(GetCapitalRequest getcapitalRequest);

	Answer getCapitalWithInfoJson(GetCapitalRequest getCapitalRequest);
	
}

package com.projectai.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.GetCapitalInfoResponse;
import com.projectai.springai.model.GetCapitalRequest;
import com.projectai.springai.model.GetCapitalResponse;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.OpenAIService;

@RestController
public class QuestionController {
	
	@Autowired
	OpenAIService openAIService;

	@PostMapping("/ask")
	public Answer question(@RequestBody Question question) {
		return openAIService.getAnswer(question);
	}
	
	@PostMapping("/capital")
	public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
		return openAIService.getCapital(getCapitalRequest);
	}
	
	@PostMapping("/capitalWithInfo")
	public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
		return openAIService.getCapitalWithInfo(getCapitalRequest);
	}
	
	@PostMapping("/capitalWithInfoJson")
	public Answer getCapitalWithInfoJson(@RequestBody GetCapitalRequest getCapitalRequest) {
		return openAIService.getCapitalWithInfoJson(getCapitalRequest);
	}
	
	@PostMapping("/capitalSchema")
	public GetCapitalResponse getCapitalSchema(@RequestBody GetCapitalRequest getCapitalRequest) {
		return openAIService.getCapitalSchema(getCapitalRequest);
	}
	
	@PostMapping("/capitalWithInfoSchema")
	public GetCapitalInfoResponse getCapitalWithInfoSchema(@RequestBody GetCapitalRequest getCapitalRequest) {
		return openAIService.getCapitalWithInfoSchema(getCapitalRequest);
	}
}

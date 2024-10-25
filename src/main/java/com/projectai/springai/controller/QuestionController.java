package com.projectai.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.OpenAIService;

@RestController
public class QuestionController {
	
	@Autowired
	OpenAIService openAIService;

	@PostMapping("/ask")
	public Answer question(@RequestBody Question question) {
		return openAIService.getAnswer(question.question());
	}
}

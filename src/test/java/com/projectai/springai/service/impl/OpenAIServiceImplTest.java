package com.projectai.springai.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.OpenAIService;

@SpringBootTest
class OpenAIServiceImplTest {
	
	@Autowired
	OpenAIService openAIService;

	@Test
	void testGetAnswer() {
		Question question = new Question("What is tha capital of India?");
		Answer answer = openAIService.getAnswer(question.question());
		System.out.println("Answer:"+answer.answer());
	}

}

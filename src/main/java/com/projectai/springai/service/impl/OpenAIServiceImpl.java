package com.projectai.springai.service.impl;


import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectai.springai.model.Answer;
import com.projectai.springai.service.OpenAIService;

@Service
public class OpenAIServiceImpl implements OpenAIService {
	
	private final ChatModel chatModel;
	
	public OpenAIServiceImpl(ChatModel chatModel) {
		this.chatModel = chatModel; 
	}
	
	@Override
	public Answer getAnswer(String question) {
		PromptTemplate template = new PromptTemplate(question);
		Prompt prompt = template.create();
		ChatResponse response = chatModel.call(prompt);
		return new Answer(response.getResult().getOutput().getContent());
	}

}

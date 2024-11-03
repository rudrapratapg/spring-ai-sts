package com.projectai.springai.service.impl;

import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.projectai.springai.model.EmotionAnalysis;
import com.projectai.springai.model.Message;
import com.projectai.springai.service.EmotionAnalysisService;

@Service
public class EmotionAnaysisServiceImpl implements EmotionAnalysisService {
	
	private final ChatModel chatModel;
	
	public EmotionAnaysisServiceImpl(ChatModel chatModel) {
		this.chatModel = chatModel; 
	}
	
	@Value("classpath:templates/emotion-meter-prompt.st")
	private Resource emotionMeterPrompt;

	@Override
	public EmotionAnalysis getEmotionAnalysis(Message message) {
		BeanOutputConverter<EmotionAnalysis> converter = new BeanOutputConverter<>(EmotionAnalysis.class);
		String format = converter.getFormat();
		PromptTemplate promptTemplate = new PromptTemplate(emotionMeterPrompt);
		Prompt prompt = promptTemplate.create(Map.of(
					"format", format,
					"message", message.message()
				));
		ChatResponse response = chatModel.call(prompt);
		return converter.convert(response.getResult().getOutput().getContent());
	}

}

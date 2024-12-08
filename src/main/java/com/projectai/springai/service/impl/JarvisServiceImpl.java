package com.projectai.springai.service.impl;

import java.util.List;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.JarvisService;


@Service
public class JarvisServiceImpl implements JarvisService {
	
	@Autowired
	private final ChatModel chatModel;
	
	public JarvisServiceImpl(ChatModel chatModel) {
		this.chatModel = chatModel;
	}
	
	@Value("classpath:templates/jarvis-prompt.st")
	Resource jarvisPrompt;

	@Override
	public Answer askJarvis(Question question) {
		SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(jarvisPrompt);
		Message systemMessage = systemPromptTemplate.createMessage();
		
		PromptTemplate promptTemplate = new PromptTemplate(question.question());
		Message userMessage = promptTemplate.createMessage();
		
		List<Message> messageList = List.of(systemMessage, userMessage);
		Prompt prompt = new Prompt(messageList);
		
		ChatResponse response = chatModel.call(prompt);
		
		return new Answer(response.getResult().getOutput().getContent());
	}

}

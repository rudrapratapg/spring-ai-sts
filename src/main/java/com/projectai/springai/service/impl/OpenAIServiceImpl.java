package com.projectai.springai.service.impl;


import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectai.springai.model.Answer;
import com.projectai.springai.model.GetCapitalRequest;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.OpenAIService;

@Service
public class OpenAIServiceImpl implements OpenAIService {
	
	@Autowired
	ObjectMapper objectMapper;
	
	private final ChatModel chatModel;
	
	public OpenAIServiceImpl(ChatModel chatModel) {
		this.chatModel = chatModel; 
	}
	
	@Override
	public Answer getAnswer(Question question) {
		PromptTemplate template = new PromptTemplate(question.question());
		Prompt prompt = template.create();
		ChatResponse response = chatModel.call(prompt);
		return new Answer(response.getResult().getOutput().getContent());
	}
	
	//importing the prompt from the .st file
	@Value("classpath:templates/get-capital-prompt.st")
	private Resource getCapitalPrompt;
	
	@Override
	public Answer getCapital(GetCapitalRequest getCapitalRequest) {
		//using the template from .st file here
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
		
		//mapping the dynamic value to the prompt on runtime
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
		ChatResponse response = chatModel.call(prompt);
		return new Answer(response.getResult().getOutput().getContent());
	}

	//importing the prompt from the .st file
	@Value("classpath:templates/get-capital-with-info.st")
	private Resource getCapitalWithInfoPrompt;
	@Override
	public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
		//using the template from .st file here
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
		
		//mapping the dynamic value to the prompt on runtime
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
		ChatResponse response = chatModel.call(prompt);
		return new Answer(response.getResult().getOutput().getContent());
	}
	
	@Value("classpath:templates/get-capital-with-info-json.st")
	private Resource getCapitalJsonPrompt;
	@Override
	public Answer getCapitalWithInfoJson(GetCapitalRequest getCapitalRequest) {
		//using the template from the .st file
		PromptTemplate promptTemplate = new PromptTemplate(getCapitalJsonPrompt);
		
		//mapping the dynamic value to the prompt on runtime
		Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
		ChatResponse response = chatModel.call(prompt);
		
		String responseString;
		String responseContent = response.getResult().getOutput().getContent();
		System.out.println("responseContent: "+responseContent);
		 try {
            JsonNode jsonNode = objectMapper.readTree(responseContent);
            responseString = jsonNode.get("answer").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
		
		return new Answer(responseString);
	}

}

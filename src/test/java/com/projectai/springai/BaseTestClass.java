package com.projectai.springai;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseTestClass {

	@Autowired
	OpenAiChatModel openAiChatModel;
	
	String chat(String prompt) {
		PromptTemplate template = new PromptTemplate(prompt);
		Prompt promptToSend = template.create();
		
		return openAiChatModel.call(promptToSend).getResult().getOutput().getContent();
	}
}

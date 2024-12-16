package com.projectai.springai.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.RAGService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RAGServiceImpl implements RAGService {

	@Autowired
    private VectorStore vectorStore;

    @Autowired
    private OpenAiChatModel chatModel;

    @Override
    public Answer retrieveAndGenerateAnswer(Question question) {
        // Perform similarity search in vector store
        List<Document> relevantDocuments = vectorStore.similaritySearch(question.question());
        
        // Extract content from relevant documents
        String context = relevantDocuments.stream()
            .map(Document::getContent)
            .collect(Collectors.joining("\n\n"));
        
        // Prepare prompt with retrieved context
        String prompt = String.format(
            "Context: %s\n\nQuestion: %s\n\nBased on the provided context, answer the question:",
            context, question.question()
        );
        
        // Generate response using ChatClient
        ChatResponse chatResponse = chatModel.call(new Prompt(new UserMessage(prompt)));
        
        // Extract the first generation's text
        String answer = chatResponse.getResults().get(0).getOutput().getContent();
        
        // Create and return Answer object
//        return Answer.builder()
//            .answer(answer)
//            .documents(relevantDocuments.stream()
//                .map(doc -> doc.getMetadata().toString())
//                .collect(Collectors.toList()))
//            .build();
        return new Answer(answer);
        
    }
    
    @Value("sfg.aiapp.documentsToLoad")
    List<Resource> resourceList;
    
    // Method to add documents to vector store
//    public void addDocumentsToVectorStore(List<Document> documents) {
//        List<Resource> resources = documents.stream()
//                .map(document -> {
//                    try {
//                        return new ClassPathResource(document.getFilename());
//                    } catch (Exception e) {
//                        log.error("Error converting document to resource", e);
//                        return null;
//                    }
//                })
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//
//        vectorStore.add(resources);
//    }
}

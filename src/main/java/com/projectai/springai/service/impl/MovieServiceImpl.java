package com.projectai.springai.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Movie;
import com.projectai.springai.model.MovieList;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
	
	private final ChatModel chatModel;
	private final SimpleVectorStore simpleVectorStore;
	
	public MovieServiceImpl(ChatModel chatModel, SimpleVectorStore simpleVectorStore) {
		this.chatModel = chatModel; 
		this.simpleVectorStore = simpleVectorStore;
	}
	
	
	@Value("classpath:templates/movie-prompt.st")
	private Resource moviePrompt;

	@Override
	public List<Movie> getMovies(Question question) {
		BeanOutputConverter<MovieList> movieConverter = new BeanOutputConverter<>(MovieList.class);
		
//		List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(4));
//		List<String> contentList = documents.stream().map(Document::getContent).collect(Collectors.toList());
		List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(4));
		List<CompletableFuture<String>> contentFutures = documents.stream().map(
																	document -> CompletableFuture.supplyAsync(
																			()-> document.getContent()
																			)
																	).collect(Collectors.toList());
		
		List<String> contentList = contentFutures.stream().map(CompletableFuture::join)
									.collect(Collectors.toList());
		
		PromptTemplate template = new PromptTemplate(moviePrompt);
		
		Prompt prompt = template.create(Map.of(
				"question", question.question(),
				"format", movieConverter.getFormat(),
				"documents", String.join("\n", contentList)
				));
		
		ChatResponse response = chatModel.call(prompt);
		String responseString = response.getResult().getOutput().getContent();
		System.out.println("responseString:: "+responseString);
		MovieList movieList = movieConverter.convert(responseString);
		return movieList.movieList();
	}

	@Override
	public Answer askMovieGenie(Question question) {
		List<Document> documentList = simpleVectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(10));
		List<CompletableFuture<String>> completableFuture = documentList.parallelStream().map(
				document -> CompletableFuture.supplyAsync(
						() -> document.getContent()
						)
				).collect(Collectors.toList());
		
		List<String> contentList = completableFuture.parallelStream().map(
				CompletableFuture::join
				).collect(Collectors.toList());
		
		
		
		BeanOutputConverter<Answer> answerConverter = new BeanOutputConverter<>(Answer.class);
		PromptTemplate template = new PromptTemplate(moviePrompt);
		Prompt prompt = template.create(
				Map.of(
						"question",question.question(),
						"documents",String.join(",", contentList),
						"format", answerConverter.getFormat()
						)
				);
		ChatResponse chatResponse = chatModel.call(prompt);
		String response = chatResponse.getResult().getOutput().getContent();
		System.out.println("response :: "+response);
		return answerConverter.convert(response);
	}

}

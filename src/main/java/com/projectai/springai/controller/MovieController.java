package com.projectai.springai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Movie;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.MovieService;

@RestController
public class MovieController {
	
	@Autowired
	MovieService movieService;

	@PostMapping("askMovieGenie")
	public ResponseEntity<Answer> movieGenie(@RequestBody Question question) {
		Answer answer = movieService.askMovieGenie(question);
		return new ResponseEntity<>(answer, HttpStatus.OK);
	}
	
	@PostMapping("recommendMovie")
	public ResponseEntity<List<Movie>> movieGenie2(@RequestBody Question question) {
		List<Movie> movieList = movieService.getMovies(question);
		if(movieList.isEmpty()) {
			System.out.println("No movies found");
		} else {
			movieList.forEach(System.out::println);
		}
		return new ResponseEntity<>(movieList, HttpStatus.OK);
	}
}

package com.projectai.springai.service;

import java.util.List;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Movie;
import com.projectai.springai.model.Question;

public interface MovieService {

	List<Movie> getMovies(Question question);
	
	Answer askMovieGenie(Question question);
}

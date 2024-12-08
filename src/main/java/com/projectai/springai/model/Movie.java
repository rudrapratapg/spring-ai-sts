package com.projectai.springai.model;

public record Movie(
	    int id,
	    String title,
	    String genres,
	    String originalLanguage,
	    String overview,
	    double popularity,
	    String productionCompanies,
	    String releaseDate,
	    int budget,
	    long revenue,
	    int runtime,
	    String status,
	    String tagline,
	    double voteAverage,
	    int voteCount,
	    String credits,
	    String keywords,
	    String posterPath,
	    String backdropPath,
	    String recommendations
	) {
}

package com.projectai.springai.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalInfoResponse(
	@JsonPropertyDescription("This is the city name")	String capital, 
	@JsonPropertyDescription("This is the population in the city")	Integer population, 
	@JsonPropertyDescription("This is religion of the city")	String region, 
	@JsonPropertyDescription("This is the language spoken in the city")	String language, 
	@JsonPropertyDescription("This is the currency used in the city")	String currency) {

}

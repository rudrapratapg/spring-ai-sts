package com.projectai.springai.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record EmotionAnalysis(
	@JsonPropertyDescription("This is the list of emotions")	Map<String, Integer> emotions,
	@JsonPropertyDescription("This is the list of indicators")    List<String> indicators,
	@JsonPropertyDescription("This is the suggestion")    String suggestion,
	@JsonPropertyDescription("This tells if the message is AI Generated, followed by the chances in percentage")    String aiDetection
) {

}

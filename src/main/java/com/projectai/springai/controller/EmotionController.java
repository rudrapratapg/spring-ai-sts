package com.projectai.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectai.springai.model.EmotionAnalysis;
import com.projectai.springai.model.Message;
import com.projectai.springai.service.EmotionAnalysisService;

@RestController
public class EmotionController {
	
	@Autowired
	EmotionAnalysisService emotionAnalysisService;

	@PostMapping("emotionAnalysis")
	public EmotionAnalysis analyzeEmotion(@RequestBody Message message) {
		return emotionAnalysisService.getEmotionAnalysis(message);
	}
}

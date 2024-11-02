package com.projectai.springai.service;

import com.projectai.springai.model.EmotionAnalysis;
import com.projectai.springai.model.Message;

public interface EmotionAnalysisService {

	EmotionAnalysis getEmotionAnalysis(Message message);
}

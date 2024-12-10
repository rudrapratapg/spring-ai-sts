package com.projectai.springai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projectai.springai.model.Answer;
import com.projectai.springai.model.Question;
import com.projectai.springai.service.JarvisService;

@RestController
public class JarvisController {
	
	@Autowired
	JarvisService jarvisService;

	@PostMapping("callJarvis")
	public Answer callJarvis(@RequestBody Question question) {
		return jarvisService.askJarvis(question);
	}
}

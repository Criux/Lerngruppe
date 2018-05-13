package com.demo.main.model;

import java.util.List;

public class Test {

	int totalQuestions;
	List<Question> questions;
	
	public Test(List<Question> questions, int totalQuestions){
		this.questions=questions;
		this.totalQuestions=totalQuestions;
	}
	public Test(List<Question> questions){
		this(questions,questions.size());
	}
}

package com.demo.main.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionMC {

	String question;
	List<Answer> answers;
	
	//Setters and Getters
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	@Override
	public String toString() {
		String result = "Question=" + question + " - ";
		for(Answer answer:findCorrectAnswers()){
			result+=answer.toString();
		}
		return result;
	}
	private List<Answer> findCorrectAnswers(){
		List<Answer> correct= new ArrayList<Answer>();
		for(Answer answer:answers){
			if(answer.isCorrect){
				correct.add(answer);
			}
		}
		return correct;
	}
}

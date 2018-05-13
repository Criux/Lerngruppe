package com.demo.main.model;

import java.util.ArrayList;
import java.util.List;

public class Question {

	String question;
	String secondaryText;
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
	public String getSecondaryText() {
		return secondaryText;
	}
	public void setSecondaryText(String secondaryText) {
		this.secondaryText = secondaryText;
	}
	@Override
	public String toString() {
		String result = "Question=" + question + " - ";
		for(Answer answer:findCorrectAnswers()){
			result+=answer.toString();
		}
		return result;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
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

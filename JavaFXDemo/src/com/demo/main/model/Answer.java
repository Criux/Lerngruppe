package com.demo.main.model;

public class Answer {

	String text;
	boolean isCorrect;
	
	//Setters and Getters
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	@Override
	public String toString() {
		return "Answer=" + text + " ";
	}
	
	
}

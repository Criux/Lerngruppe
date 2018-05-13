package com.demo.main.model;

import java.util.List;

import com.demo.main.Utils.NoCorrectAnswerException;

public class MultipleChoice extends Question{

	String secondaryText;
	
	@Override
	public void setAnswers(List<Answer> answers) throws NoCorrectAnswerException{
		super.setAnswers(answers);
		if(this.answers.size()!=0){
			int totalCorrectAnswers=0;
			for(Answer a:answers){
				totalCorrectAnswers+=a.isCorrect?1:0;
			}
			if(totalCorrectAnswers==0){
				this.answers=null;
				throw new NoCorrectAnswerException(this);
			}
			if(totalCorrectAnswers>1){
				secondaryText="(Choose "+totalCorrectAnswers+")";
			}else{
				secondaryText="";
			}
		}
	}
	public String getSecondaryText() {
		return secondaryText;
	}
	@Override
	public String toString() {
		String result = "Question=" + questionText +secondaryText+ " - ";
		for(Answer answer:this.findCorrectAnswers()){
			result+=answer.toString();
		}
		return result;
	}
}

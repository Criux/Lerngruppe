package com.demo.main.model;

import java.util.ArrayList;
import java.util.List;

import com.demo.main.Utils.NoAnswersException;
import com.demo.main.Utils.NoCorrectAnswerException;

public class Question {

	String questionText;
	List<Answer> answers;
	String hint;
	
	//Setters and Getters
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		if(questionText!=null&&!questionText.isEmpty())
		this.questionText = questionText;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) throws NoCorrectAnswerException, NoAnswersException {
		if(answers!=null&&answers.size()>0){
			this.answers = answers;
		}else{
			throw new NoAnswersException(this);
		}
		
	}
	@Override
	public String toString() {
		String result = "Question=" + questionText + " - ";
		for(Answer answer:findCorrectAnswers()){
			result+=answer.toString();
		}
		return result;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
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
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		return true;
	}
	protected List<Answer> findCorrectAnswers(){
		List<Answer> correct= new ArrayList<Answer>();
		for(Answer answer:answers){
			if(answer.isCorrect){
				correct.add(answer);
			}
		}
		return correct;
	}
}

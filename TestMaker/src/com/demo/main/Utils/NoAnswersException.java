package com.demo.main.Utils;

import com.demo.main.model.Question;
/**
 * 
 * 
 * 
 */
public class NoAnswersException extends InstantiationException{

	/**
	 * Automatically generated serial version universal ID.
	 */
	private static final long serialVersionUID = -2832301911918281291L;
	/**
	 * Creates the error message containing the question text.
	 */
	public<T extends Question> NoAnswersException(T q){
		super("The question needs at least one answer: \""+q.getQuestionText()+"\"");
	}
}

package com.demo.main.Utils;

import com.demo.main.model.Question;
/**
 * Indicates that the Question does not have a correct answer indicated.<br>
 * <br>
 * If the question is read from a Excel file, the correct answer is noted in bold.
 */
public class NoCorrectAnswerException extends InstantiationException{

	/**
	 * Automatically generated serial version universal ID.
	 */
	private static final long serialVersionUID = -2832301911918281292L;
	/**
	 * Creates the error message containing the question text.
	 */
	public NoCorrectAnswerException(Question q){
		super("The question needs at least one correct answer: \""+q.getQuestion()+"\"");
	}
}

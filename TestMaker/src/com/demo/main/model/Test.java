package com.demo.main.model;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.demo.main.Utils.ExcelReader;

public class Test{

	int totalQuestions;
	int answeredQuestions;
	Iterator<MultipleChoice>iterator;
	List<MultipleChoice> questions;
	
	public Test(List<MultipleChoice> questions, int totalQuestions){
		this.questions=questions;
		if(questions!=null&&questions.size()<totalQuestions){
			this.totalQuestions=questions.size();
		}else{
			this.totalQuestions=totalQuestions;
		}
		this.iterator=questions.iterator();
		this.answeredQuestions=0;
	}
	public Test(List<MultipleChoice> questions){
		this(questions,questions.size());
	}
	public MultipleChoice nextQuestion(){
		if(iterator.hasNext()){
			answeredQuestions++;
			return iterator.next();
		}
		return null;
	}
	public static void main(String[] args) throws IOException{
		ExcelReader reader= new ExcelReader();
		
		Test test= new Test(reader.getQuestions());
		Scanner in= new Scanner(System.in);
		while(test.answeredQuestions<test.totalQuestions){
			MultipleChoice q= test.nextQuestion();
			System.out.println(q.questionText+q.getSecondaryText());
			System.out.println("");
			for(int i=0;i<q.answers.size();i++){
				System.out.println((i+1)+":"+q.answers.get(i).getText());
			}
			
			List<Answer> correct = q.findCorrectAnswers();
			while(correct.size()!=0){
				Answer selectedAnswer=q.answers.get(in.nextInt()-1);
				if(correct.contains(selectedAnswer)){
					correct.remove(selectedAnswer);
					System.out.println("Correct!!");
					if(correct.size()!=0){
						System.out.println(correct.size()+" correct answers remaining.");
					}
				}else{
					System.out.println("Wrong!");
					break;
				}
			}

		}
		
	}
}

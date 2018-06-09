package com.demo.main.controller;

import com.demo.main.model.Answer;
import com.demo.main.model.MultipleChoice;
import com.demo.main.model.Question;
import com.demo.main.model.Test;
import com.microsoft.schemas.office.visio.x2012.main.ShapeSheetType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TestController{

//	@FXML
//	private HBox headerContainer;
	
	@FXML
	private HBox infoContainer;
	
	@FXML
	private VBox questionContainer;
	
	@FXML
	private HBox actionContainer;
	
	@FXML
	private HBox questionTextContainer;
	
	@FXML
	private Label questionText;
	
	@FXML
	private VBox answersContainer;
	
	private MultipleChoice currentQ;
	private Test currentTest;
	int totalWrongAnswers=0;
	int currentQuestionNum=0;
	
	public void startTest(){
		currentTest = TestManager.getInstance().getCurrentTest();
		createProgressTracker();
		showQuestion();
	}
	public void showQuestion(){
		clearAnswers();
    	//questionContainer.getChildren().forEach(c->System.out.println(c));
    	currentQ=currentTest.nextQuestion();
    	currentQuestionNum++;
    	if(currentQ!=null){
    		questionText.setText(currentQ.getQuestionText()+" "+currentQ.getSecondaryText());
    		
    		for(Answer answer:currentQ.getAnswers()){
    			HBox answerContainer=new HBox();
    			answerContainer.setUserData(answer);
    			//answerContainer.setMinHeight(20);
    			VBox.setMargin(answerContainer, new Insets(0,0,10,0));
    			if(answer.isCorrect()){
    				System.out.println("Correct is: "+answer.getText());
    			}
    			answerContainer.setMinHeight(25);
    			CheckBox cb= new CheckBox();
    			cb.setFont(Font.font("Calibri",FontWeight.NORMAL,cb.getFont().getSize()+8));
    			cb.setTextFill(Color.GHOSTWHITE);
    			cb.setText(answer.getText());
    			HBox.setMargin(cb, new Insets(0,0,5,0));
    			answerContainer.getChildren().add(cb);
    			answersContainer.getChildren().add(answerContainer);
    		}
    	}else{
    		int totalQuestions=TestManager.getInstance().getCurrentTest().getTotalQuestions();
    		questionText.setText("Test abgeschlossen. Ergebnis: "+(totalQuestions-totalWrongAnswers)+"/"+totalQuestions);
    		questionText.setAlignment(Pos.CENTER);
    		for(Node node:questionContainer.getChildren()){
        		if(node.getClass().equals(Separator.class)){
        			node.setVisible(false);
        			node.setManaged(false);
        		}
    		totalWrongAnswers=0;
    		hideProgressTracker();
    		if(actionContainer.getChildren().size()==1){
    			actionContainer.getChildren().remove(0);
    			
    		}
    		}
    	}
    	updateProgressTracker();
    }
	private void createConfirmButton(){
		Button button =new Button();
		button.setText("Bestätigen");
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setContentDisplay(ContentDisplay.TOP);
		button.setMnemonicParsing(false);
		button.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-border-color: #00dcb3; -fx-border-radius: 2; -fx-border-width: 2;");
		button.setTextFill(Color.valueOf("#00dcb3"));
		button.setFont(Font.font("Bell MT Bold",FontWeight.NORMAL,18));
		button.setCursor(Cursor.HAND);
		HBox.setMargin(button, new Insets(0,80,0,0));
		
		button.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	checkAnswers();
    	    	showCorrectAnswers();
    	    	
    	    	//showQuestion();    	    	
    	    }
    	});
		button.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    @Override public void handle(MouseEvent e) {
    	    	button.setCursor(Cursor.CLOSED_HAND);   	    	
    	    }
    	});
		
		this.actionContainer.getChildren().add(button);
	}
	private void createNextButton(){
		Button button =new Button();
		button.setText("Weiter");
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setContentDisplay(ContentDisplay.TOP);
		button.setMnemonicParsing(false);
		button.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-border-color: #52b1cf; -fx-border-radius: 2; -fx-border-width: 2;");
		button.setTextFill(Color.valueOf("#52b1cf"));
		button.setFont(Font.font("Bell MT Bold",FontWeight.NORMAL,18));
		button.setCursor(Cursor.HAND);
		HBox.setMargin(button, new Insets(0,80,0,0));
		
		button.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	//checkAnswers();
    	    	//showCorrectAnswers();
    	    	
    	    	showQuestion();    	    	
    	    }
    	});
		button.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    @Override public void handle(MouseEvent e) {
    	    	button.setCursor(Cursor.CLOSED_HAND);   	    	
    	    }
    	});
		this.actionContainer.getChildren().add(button);
	}
	private void checkAnswers(){
    	for(Node node:answersContainer.getChildren()){
    		if(node.getClass().equals(HBox.class)){
    			HBox container=(HBox)node;
        		if(container.getUserData()!=null){
        			if(((Answer)container.getUserData()).isCorrect()!=((CheckBox)container.getChildren().get(0)).isSelected()){
        				totalWrongAnswers++;
        				break;
        			}
        		}
    		}
    		
    	}
    }
	private void showCorrectAnswers(){
    	for(Node node:answersContainer.getChildren()){
    		if(node.getClass().equals(HBox.class)){
    			HBox container=(HBox)node;
        		if(container.getUserData()!=null){
        			CheckBox cBox=(CheckBox)container.getChildren().get(0);
        			
        			if(cBox.isSelected()){
        				cBox.setTextFill(Color.valueOf("#c06664"));
        				cBox.setFont(Font.font(cBox.getFont().getFamily(),FontWeight.BOLD,cBox.getFont().getSize()));
        			}
        			if(((Answer)container.getUserData()).isCorrect()){
        				cBox.setTextFill(Color.valueOf("#00dcb3"));
        				cBox.setFont(Font.font(cBox.getFont().getFamily(),FontWeight.BOLD,cBox.getFont().getSize()));
        			}
        		}
    		}
    		
    			
    	}
    	if(actionContainer.getChildren().size()==1){
			actionContainer.getChildren().remove(0);
			
		}
		createNextButton();
    }
	private void clearAnswers(){
		int total=answersContainer.getChildrenUnmodifiable().size();
		for(int i=0;i<total;i++){
			answersContainer.getChildren().remove(0);
		}
		if(actionContainer.getChildren().size()==1){
			actionContainer.getChildren().remove(0);
			
		}
		createConfirmButton();
		
	}
	private void createProgressTracker(){
		Label progressLabel= new Label();
		progressLabel.setText("Progress Tracker");
		
		progressLabel.setTextFill(Color.GHOSTWHITE);
		progressLabel.setFont(Font.font("Calibri",FontWeight.NORMAL,22));
		HBox.setMargin(progressLabel, new Insets(20,0,0,0));
		infoContainer.getChildren().add(progressLabel);
	}
	private void updateProgressTracker(){
		Label pLabel=(Label)infoContainer.getChildren().get(0);
		pLabel.setText("Aufgabe: "+currentQuestionNum+"/"+currentTest.getTotalQuestions());
		
	}
	private void hideProgressTracker(){
		Label pLabel=(Label)infoContainer.getChildren().get(0);
		pLabel.setVisible(false);
		pLabel.setManaged(false);
	}
}

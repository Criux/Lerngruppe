package com.demo.main.controller;

import java.util.ArrayList;
import java.util.List;

import com.demo.main.model.Answer;
import com.demo.main.model.MultipleChoice;
import com.demo.main.model.Question;
import com.demo.main.model.Test;
import com.microsoft.schemas.office.visio.x2012.main.ShapeSheetType;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TestController{

	@FXML
	private HBox headerContainer;

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

	@FXML
	private HBox barContainer;

	@FXML
	private ProgressBar progressBar;

	private MenuBar menubar;
	private Label progressLbl;
	private String progress;

	private MultipleChoice currentQ;
	private Test currentTest;
	int totalWrongAnswers=0;

	private final ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

	public void startTest(){;
		currentTest = TestManager.getInstance().getCurrentTest();
		progressBar = new ProgressBar();
		progress = currentTest.getAnsweredQuestions() + " / " + currentTest.getTotalQuestions();
		progressLbl = new Label(progress);

		showQuestion();
		progressBar.setProgress(currentTest.getAnsweredQuestions()*1.0/currentTest.getTotalQuestions()*1.0);
		barContainer.getChildren().addAll(progressBar, progressLbl);
		barContainer.setSpacing(10);


	}
	public void showQuestion(){
		clearAnswers();

    	//questionContainer.getChildren().forEach(c->System.out.println(c));
    	currentQ=currentTest.nextQuestion();
    	if(currentQ!=null){
    		questionText.setText(currentQ.getQuestionText()+currentQ.getSecondaryText());
    		progressLbl.setText(currentTest.getAnsweredQuestions() + " / " + currentTest.getTotalQuestions());
    		progressBar.setProgress(currentTest.getAnsweredQuestions()*1.0/currentTest.getTotalQuestions()*1.0);
    		for(Answer answer:currentQ.getAnswers()){
    			HBox answerContainer=new HBox();
    			answerContainer.setUserData(answer);
    			//answerContainer.setMinHeight(20);
    			VBox.setMargin(answerContainer, new Insets(0,0,10,0));
    			if(answer.isCorrect()){
    				System.out.println("Correct is: "+answer.getText());
    			}
    			answerContainer.setMinHeight(15);
    			CheckBox cb= new CheckBox();
    			cb.setFont(Font.font("Calibri",FontWeight.NORMAL,cb.getFont().getSize()+8));
    			cb.setTextFill(Color.GHOSTWHITE);
    			cb.setText(answer.getText());
    			HBox.setMargin(cb, new Insets(0,0,10,0));
    			answerContainer.getChildren().add(cb);
    			answersContainer.getChildren().add(answerContainer);
    		}
    	} else{

    		showResult();

    	}
    	//createConfirmButton();
    }

	private void createConfirmButton(){
		Button button =new Button();
		button.setText("Bestätigen");
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setContentDisplay(ContentDisplay.TOP);
		button.setMnemonicParsing(false);
	//	button.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-border-color: #52b1cf; -fx-border-radius: 2; -fx-border-width: 2;");
	//	button.setTextFill(Color.valueOf("#52b1cf"));
	//	button.setFont(Font.font("Bell MT Bold",FontWeight.NORMAL,18));
		HBox.setMargin(button, new Insets(0,80,0,0));

		button.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	checkAnswers();
    	    	showCorrectAnswers();

    	    	//showQuestion();
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
		button.setId("test-btn-weiter");
	//	button.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-border-color: #00dcb3; -fx-border-radius: 2; -fx-border-width: 2; -fx-text-fill: #00dcb3");
	//	button.setTextFill(Color.valueOf("#00dcb3"));
	//	button.setFont(Font.font("Bell MT Bold",FontWeight.NORMAL,18));
		HBox.setMargin(button, new Insets(0,80,0,0));

		button.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	//checkAnswers();
    	    	//showCorrectAnswers();

    	    	showQuestion();
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

	private void showResult() {
		//Menubar wieder einblenden - bin ich irgendwie zu blöd für

		int totalQuestions=TestManager.getInstance().getCurrentTest().getTotalQuestions();
		int rightAnswers = totalQuestions-totalWrongAnswers;
		chartData.addAll(
				new PieChart.Data("richtig", rightAnswers),
				new PieChart.Data("falsch", totalWrongAnswers)
			);
		PieChart chart = new PieChart();
		chart.setLabelLineLength(20);
		chart.setData(chartData);
		// Chart größer darstellen
		answersContainer.getChildren().add(chart);

		createTable();

		answersContainer.setCenterShape(true);

		questionText.setText("Test beendet! Ergebnis:  "+(totalQuestions-totalWrongAnswers)+"/"+totalQuestions);
//		questionText.setText("Test completed. Result: "+(totalQuestions-totalWrongAnswers)+"/"+totalQuestions);
		for(Node node:questionContainer.getChildren()){
    		if(node.getClass().equals(Separator.class)){
    			node.setVisible(false);
    			node.setManaged(false);
    		}
		totalWrongAnswers=0;
		}
		progressBar.setVisible(false);
		progressLbl.setVisible(false);
		actionContainer.setVisible(false);
	}
	private void createTable() {

		/*
		 * 1) Liste mit korrekt beantworteten Fragen erstellen
		 * 2) Liste mit allen Fragen -> currentTest
		 * 3) Liste mit korrekten Fragen durchgehen (for) -> Kapitel der Frage auslesen -> entsprechenden Index in resultChap hochzählen
		 * 4) Liste mit allen Fragen durchgehen (for) -> Kapitel der Frage auslesen -> entsprechenden Index in totalQChap hochzählen
		 * 5) enthaltene Kapitel mit Ergebnis in Tabelle anzeigen
		 *
		 * Tabelle:
		 * | Kapitel	| richtige Antworten	| Gesamtanzahl	|
		 * -----------------------------------------------
		 * |			|						|				|
		 * |			|						|				|
		 *
		 */


		ArrayList<Integer> resultChap = new ArrayList<Integer>();
		ArrayList<Integer> totalQChap = new ArrayList<Integer>();

		TableView<String> tableResult = new TableView<String>();


	}
}

package com.demo.main.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.demo.main.model.Answer;
import com.demo.main.model.MultipleChoice;
import com.demo.main.model.Test;
import com.demo.main.model.fx.Screen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController{

	@FXML
	private VBox container;
	@FXML
    private MenuBar menubar;

    @FXML
    private MenuItem menuTestCreate;

    @FXML
    private Menu menuFile;
    
    @FXML
    private MenuItem menuFileClose;
    
    @FXML
    private HBox welcomeMessage;
    
    @FXML
    private Label labelErfolg;

    @FXML
    private Label labelLernen;

    @FXML
    private ImageView imgWelcome;

    @FXML
    private Menu menueTest;

    @FXML
    private MenuItem menuTestStatistic;

    //public static Stage stageTestCreate;
    
   // public static Stage stageTestView;

    //public static Parent root1;

    //FXMLLoader loader;
    //Scene scene;    
    
    MultipleChoice currentQ;
    VBox qContainer;
    int totalWrongAnswers=0;
    public void openWindowCreateTest() {

    	try {    		
    		ScreenManager.getInstance().getScreen("TestCreateView").show();    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("FXMLLoader: " + e.getMessage());
    		Alert a = new Alert(AlertType.ERROR);
    		a.setTitle("Error");
    		a.setHeaderText("Es ist ein Fehler aufgetreten:");
    		a.setContentText("Das Fenster konnte nicht geladen werden.");
    		a.showAndWait();
    	}

    }
    public void showTestScreen(){
    	//welcomeMessage.setVisible(false);
    	//welcomeMessage.setManaged(false);
    	
    	container.getChildren().remove(welcomeMessage); //Viel Erfolg beim Lernen entfernen
    	container.getChildren().remove(container.getChildrenUnmodifiable().size()-1); //margin-bottom entfernen
    	
    	//Test-Objekt erstellen
    	Test currentTest = TestManager.getInstance().getCurrentTest();
    	
    	//Neue Elemente kreieren und sie ins Fenster hinzufügen
    	VBox textContainer=new VBox();
    	textContainer.setPrefHeight(400);
    	textContainer.setAlignment(Pos.TOP_CENTER);
    	Label label=new Label();
    	label.setText("Der Test wurde erstellt. Gesamtanzahl: "+currentTest.getTotalQuestions());
    	//textContainer.getChildren().add(label);

    	container.getChildren().add(textContainer);
    	//ScreenManager.getInstance().getScreen("MainView").show();
    	showQuestion();
    	Button button= new Button();
    	button.setText("Weiter");
    	button.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	checkAnswers(currentQ);
    	    	showQuestion();
    	    	ScreenManager.getInstance().getScreen("MainView").getStage().show();
    	    	
    	    }
    	});
    	
    	container.getChildren().add(button);
    }
    public void showQuestion(){
    	qContainer=clearScreen();
    	qContainer.getChildren().forEach(c->System.out.println(c));
    	Test currentTest = TestManager.getInstance().getCurrentTest();
    	currentQ=currentTest.nextQuestion();
    	if(currentQ!=null){
    		HBox qTextContainer= new HBox();
    		qTextContainer.setMinHeight(30);
    		Label qText= new Label();
    		qText.setText(currentQ.getQuestionText()+currentQ.getSecondaryText());
    		qTextContainer.getChildren().add(qText);
    		qContainer.getChildren().add(qTextContainer);
    		
    		for(Answer answer:currentQ.getAnswers()){
    			HBox answerContainer=new HBox();
    			answerContainer.setUserData(answer.isCorrect());
    			if(answer.isCorrect()){
    				System.out.println("Correct is: "+answer.getText());
    			}
    			answerContainer.setMinHeight(15);
    			CheckBox cb= new CheckBox();
    			cb.setText(answer.getText());
    			answerContainer.getChildren().add(cb);
    			qContainer.getChildren().add(answerContainer);
    		}
    	}else{
    		int totalQuestions=TestManager.getInstance().getCurrentTest().getTotalQuestions();
    		Label qText= new Label();
    		qText.setText("Test completed. Result:"+(totalQuestions-totalWrongAnswers)+"/"+totalQuestions);
    		container.getChildren().remove(container.getChildrenUnmodifiable().size()-1);
    		container.getChildren().add(qText);
    		totalWrongAnswers=0;
    	}
    }
    public VBox clearScreen(){
    	VBox newContainer= new VBox();
    	container.getChildren().set(1, newContainer);
    	//System.out.println(((VBox)container.getChildren().get(1)).getChildren().size());
    	return newContainer;
    }
    public void checkAnswers(MultipleChoice q){
    	int counter=0;
    	for(Node node:qContainer.getChildren()){
    		HBox container=(HBox)node;
    		if(container.getUserData()!=null){
    			//System.out.println("Checking answer:"+((CheckBox)container.getChildren().get(0)).getText());
    			if(container.getUserData().equals(((CheckBox)container.getChildren().get(0)).isSelected())){
    				
    			}else{
    				totalWrongAnswers++;
    				//System.out.println("total wrong:"+totalWrongAnswers);
    				break;
    			}
    			counter++;
    		}
    	}
    }
    public void exitProgram() {
    	Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle("Achtung");
		a.setHeaderText("Soll das Programm wirklich geschlossen werden?");
		a.setContentText("Es gehen ggf. ungespeicherte Daten verloren.");
		Optional<ButtonType> result = a.showAndWait();

		if(result.get() == ButtonType.OK) {
			System.exit(0);
		}
    }
    
}
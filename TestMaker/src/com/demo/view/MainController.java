package com.demo.view;

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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {


	@FXML
    private MenuBar menubar;

    @FXML
    private MenuItem menuTestCreate;

    @FXML
    private Menu menuFile;

    @FXML
    private MenuItem menuFileClose;

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

    public static Stage stageTestCreate;

    public static Parent root1;

    FXMLLoader loader;
    Scene scene;
    public void openWindowCreateTest() {

    	try {
    		loader = new FXMLLoader(TestCreateController.class.getResource("TestCreateView.fxml"));
    		Parent root1 = (Parent) loader.load();
    		//getTests(System.getProperty("user.dir")+"\\Tests");
    		stageTestCreate = new Stage();
    		scene=new Scene(root1);
    		stageTestCreate.setScene(scene);
    		stageTestCreate.setResizable(false);
    		stageTestCreate.setTitle("Test erstellen");
    		stageTestCreate.initModality(Modality.APPLICATION_MODAL);
    		forceNumeric();
    		stageTestCreate.showAndWait();
    		
    		
    		
    	} catch(Exception e) {
    		System.out.println("FXMLLoader: " + e.getMessage());
    		Alert a = new Alert(AlertType.ERROR);
    		a.setTitle("Error");
    		a.setHeaderText("Es ist ein Fehler aufgetreten:");
    		a.setContentText("Das Fenster konnte nicht geladen werden.");
    		a.showAndWait();
    	}

    }

    public static Stage getStageTestCreate() {
    	return stageTestCreate;
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

    public static Parent getRoot1() {
    	return root1;
    }
    public void forceNumeric(){
    	TextField textField=((TestCreateController)loader.getController()).getTxtfNumber();
    	textField.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) {
    	        if (!newValue.matches("\\d*")) {
    	            textField.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	    }
    	});
    }
}
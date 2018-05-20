package com.demo.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
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

    public void openWindowCreateTest() {

    	try {
    		FXMLLoader loader = new FXMLLoader(TestCreateController.class.getResource("TestCreateView.fxml"));
    		Parent root1 = (Parent) loader.load();
    		stageTestCreate = new Stage();
    		stageTestCreate.setScene(new Scene(root1));
    		stageTestCreate.setResizable(false);
    		stageTestCreate.setTitle("Test erstellen");
    		stageTestCreate.initModality(Modality.APPLICATION_MODAL);
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



}
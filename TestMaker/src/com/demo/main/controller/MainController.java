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
import java.util.concurrent.TimeUnit;

import com.demo.main.Utils.AlertMessage;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController{

	@FXML
	private VBox container;
	@FXML
    public static MenuBar menubar;

	public static MenuBar getMenuBar() {
		return menubar;
	}

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
    public static Menu menueTest;

	@FXML
    private MenuItem menuTestStatistic;

	@FXML
	private MenuItem chooseDir;

    MultipleChoice currentQ;
    VBox qContainer;
    int totalWrongAnswers=0;
    public void openWindowCreateTest() {

    	try {
    		menubar = new MenuBar();
    		Screen testCreate = ScreenManager.getInstance().getScreen("TestCreateView");
    		testCreate.getStage().setTitle("Test erstellen");
    		testCreate.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("FXMLLoader: " + e.getMessage());
    		new AlertMessage(AlertType.ERROR, "Error", "Es ist ein Fehler aufgetreten:", "Das Fenster konnte nicht geladen werden.");
    	}

    }
    public void exitProgram() {
    	AlertMessage a = new AlertMessage(AlertType.CONFIRMATION, "Achtung", "Soll das Programm wirklich geschlossen werden?", "Es gehen ggf. ungespeicherte Daten verloren.");
		Optional<ButtonType> result = a.showAndWait();

		if(result.get() == ButtonType.OK) {
			System.exit(0);
		}
    }


}
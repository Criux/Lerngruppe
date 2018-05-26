package com.demo.view;

import com.demo.main.controller.ScreenManager;
import com.demo.main.controller.TestController;
import com.demo.main.model.fx.Screen;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		//Hauptfenster finden
		Screen main=ScreenManager.getInstance().getScreen("MainView");
		//Eigene Einstellungen
		main.getScene().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.getIcons().add(new Image(com.demo.view.Main.class.getResourceAsStream("icon.png")));
		//primaryStage.setMaximized(true);
		
		//Den Stage setzen und das Fenster zeigen	
		main.setStage(primaryStage);
		main.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

package com.demo.main.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ScreenManager {

	private static ScreenManager instance;
	
	private ScreenManager(){
		
	}
	public static ScreenManager getInstance(){
        if(instance == null){
        	instance = new ScreenManager();
        }
        return instance;
    }
	
	public Stage mainStage;
	public FXMLLoader mainLoader;
	
	public void initMain(Stage primaryStage,Class c){
		this.mainStage=primaryStage;
		try {
			mainLoader=new FXMLLoader(c.getResource("MainView.fxml"));
			Parent root = (Parent)mainLoader.load();
			System.out.println(getClass().getResource("test.css"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(c.getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(c.getResourceAsStream("icon.png")));
			primaryStage.setScene(scene);
			//primaryStage.setMaximized(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

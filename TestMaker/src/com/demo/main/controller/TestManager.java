package com.demo.main.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.demo.view.TestCreateController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class TestManager {

	public void getTests(String testPath) throws IOException {
		FXMLLoader loader=new FXMLLoader(com.demo.view.Main.class.getResource("TestCreateView.fxml"));
    	Parent root=loader.load();
    	Scene scene=root.getScene();
    	VBox container=((TestCreateController)loader.getController()).getSelectionContainer();
    	Path testFolder=Paths.get(testPath);
    	for(File file:testFolder.toFile().listFiles()){
    		CheckBox box= new CheckBox();
    		box.setText("my text");
    		container.getChildren().add(box);
    	}
    	System.out.println(container.getChildren());
		
    }
}

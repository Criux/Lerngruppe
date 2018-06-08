package com.demo.main.controller;

import java.util.ArrayList;
import java.util.List;

import com.demo.main.model.fx.Screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class ScreenManager {

	private static ScreenManager instance;
	
	private ScreenManager(){
		Screen testCreate = new Screen("TestCreateView.fxml");
		testCreate.getStage().setResizable(false);
		testCreate.getStage().initModality(Modality.APPLICATION_MODAL);

		managedScreens.add(new Screen("TestView.fxml",1200,720));
		managedScreens.add(new Screen("MainView.fxml",1200,720));
		managedScreens.add(testCreate);
	}
	public static ScreenManager getInstance(){
        if(instance == null){
        	instance = new ScreenManager();
        }
        return instance;
    }
	List<Screen> managedScreens = new ArrayList<>();
	public Screen getScreen(String name){
		for(Screen s:managedScreens){
			if(s.getName().equals(name)){
				return s;				
			}
		}
		return null;
	}
	//broken - Do not use 08.06.18 - KMA
	public static <T> T findController(String viewName) {
	    FXMLLoader loader = new FXMLLoader(com.demo.view.Main.class.getResource(viewName));
	    return (T) loader.getController();
	}
	public List<Screen> getManagedScreens() {
		return managedScreens;
	}
}

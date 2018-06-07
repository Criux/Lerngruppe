package com.demo.main.model.fx;

import java.io.IOException;

import com.demo.main.controller.ScreenManager;
import com.demo.main.controller.TestController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Screen {

	FXMLLoader loader;
	Parent root;
	Scene scene;
	Stage stage;
	Object controller;
	String name;
	
	public Screen(String fxmlFile,Stage stage,int width,int height){
		name=fxmlFile.split(".fxml")[0];
		loader= new FXMLLoader(com.demo.view.Main.class.getResource(fxmlFile));
		
		try {
			root= (Parent)loader.load();
			controller=loader.getController();
			if(width==0||height==0){
				scene=new Scene(root);
			}else{
				scene = new Scene(root,width,height);
			}
			if(stage==null){
				this.stage=new Stage();
			}else{
				this.stage=stage;
			}
			this.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	public Screen(String fxmlFile){
		this(fxmlFile,null,0,0);
	}
	public Screen(String fxmlFile,Stage stage){
		this(fxmlFile,stage,0,0);
	}
	public Screen(String fxmlFile,int width,int height){
		this(fxmlFile,null,width,height);
	}
	public void hideAndSwitch(){
		for(Screen s:ScreenManager.getInstance().getManagedScreens()){
			if(s.getStage().isFocused())
				s.getStage().hide();
		}
		this.show();
	}
	private void showAndWait() {
		stage.showAndWait();
		
	}
	public void show(){	
		stage.show();
	}
	public void hide(){
		stage.hide();
	}
	public Object getController() {
		return controller;
	}
	public FXMLLoader getLoader() {
		return loader;
	}
	public void setLoader(FXMLLoader loader) {
		this.loader = loader;
	}
	public Parent getRoot() {
		return root;
	}
	public void setRoot(Parent root) {
		this.root = root;
	}
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setScene(scene);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static void main(String[] args) {
		Screen s= new Screen("TestView.fxml",800,600);
		
		
		
		
	}
}

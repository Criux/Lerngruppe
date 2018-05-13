package com.demo.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class HelloWorld extends Application implements Initializable{
	Stage ps=new Stage();
	Scene scene;
	@FXML private Label questionText=new Label("Which of the following answers is correct?");
	@FXML private VBox answerList;
	@FXML private Label actionLabel;
	//StageHolder holder= new StageHolder();
    public static void main(String[] args) {
        launch(args);
        
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException{
    	ps=primaryStage;
    	Map<String, String> map = getParameters().getNamed();
    	System.out.println(map.size());
    	for (Map.Entry<String, String> entry : map.entrySet())
    	{
    	    System.out.println(entry.getKey() + "/" + entry.getValue());
    	}
    	System.out.println(ps);
        primaryStage.setTitle("JavaFX Welcome");
        FXMLLoader loader= new FXMLLoader(getClass().getResource("fxml.fxml"));
        
        Parent root=loader.load();

        Scene scene2 = new Scene(root);
        
        System.out.println(getClass().getResource("fxml.fxml"));
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("ComicSans", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 4,2,1);
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
                ((Label)scene2.lookup("#questionText")).setText("Which of the following answers is correct?");
                ps.setScene(scene2);
                
            }
        });
        
        
        
        scene = new Scene(grid, 300, 275);
        
        ps.setScene(scene);
        
        ps.show();
    }
    @FXML protected void checkAnswer(ActionEvent event) {
    	
    	RadioButton correctAnswer=(RadioButton)answerList.getChildren().get(1);
    	if(correctAnswer.isSelected()){
    		actionLabel.setTextFill(Color.GREEN);
    		actionLabel.setText("Correct :)");
    	}else{
    		actionLabel.setTextFill(Color.FIREBRICK);
    		actionLabel.setText("Wrong :(");
    	}
    	
    }
    @FXML protected void forceSingleSelection(ActionEvent event){
    	RadioButton selected=(RadioButton)event.getSource();
    	
    	VBox parent=(VBox)selected.parentProperty().getValue();
    	
    	for(Node n:parent.getChildren()){
    		if(n.getClass().equals(selected.getClass())){
    			((RadioButton)n).setSelected(false);
    		}
    		
    	}
    	selected.setSelected(true);
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}

package com.demo.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.main.model.Question;
import com.demo.main.model.QuestionSelectionMode;
import com.demo.main.model.fx.Screen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class TestCreateController {
	String testPath=System.getProperty("user.dir")+"\\Tests";
	FXMLLoader loader;
	Parent root;
	Scene scene;
//	@FXML
//	    private CheckBox checkbox11;
//
//	    @FXML
//	    private CheckBox checkbox10;

	    @FXML
	    private TextField txtfNumber;

	    public TextField getTxtfNumber() {
			return txtfNumber;
		}

		@FXML
	    private Label labelNumber;

//	    @FXML
//	    private CheckBox checkbox15;
//
//	    @FXML
//	    private CheckBox checkbox14;

	    @FXML
	    private Button buttonTestCreate;

//	    @FXML
//	    private CheckBox checkbox1;
//
//	    @FXML
//	    private CheckBox checkbox13;

//	    @FXML
//	    private CheckBox checkbox2;

//	    @FXML
//	    private CheckBox checkbox12;

	    @FXML
	    private TextArea textareaIntro;

//	    @FXML
//	    private CheckBox checkbox16;

	    @FXML
	    private Button buttonClear;

	    @FXML
	    private Button buttonClose;

	    @FXML
	    private Button buttonAll;
	    
	    @FXML
	    private VBox selectionContainer;

//	    @FXML
//	    private CheckBox checkbox3;
//
//	    @FXML
//	    private CheckBox checkbox4;
//
//	    @FXML
//	    private CheckBox checkbox5;
//
//	    @FXML
//	    private CheckBox checkbox6;
//
//	    @FXML
//	    private CheckBox checkbox7;
//
//	    @FXML
//	    private CheckBox checkbox8;
//
//	    @FXML
//	    private CheckBox checkbox9;

	    public VBox getSelectionContainer() {
			return selectionContainer;
		}

		public void setSelectionContainer(VBox selectionContainer) {
			this.selectionContainer = selectionContainer;
		}

		public TestCreateController(){
//	    	loader=new FXMLLoader(getClass().getResource("TestCreateView.fxml"));
//	    	root=loader.load();
//	    	scene=root.getScene();
//	    	System.out.println(scene.lookup("#selectionContainer"));
	    	//getCheckboxesArray();
	    }
   // private HashSet<Question> questionCatalogData;		// enthält alle Fragen aus den ausgewählten Kapiteln

   // public static ArrayList<Question> questionCatalog;				// enthält eine begrenzte Anzahl an zufällig ausgewählten Fragen
    														// der ausgewählten Kapitel

    public int qNumber;
    @FXML
    public void initialize() {
    	//screen=screenManager.getScreen("TestCreateView");
    	forceNumeric();
    }
	private ArrayList<CheckBox> createCheckboxesArray() throws IOException {
    	ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
    	VBox container = getSelectionContainer();
    	Path testFolder=Paths.get(testPath);
    	for(File file:testFolder.toFile().listFiles()){
    		CheckBox box= new CheckBox();
    		box.setText(file.getName().split("\\.")[0]);
    		container.getChildren().add(box);
    	}
    	System.out.println(container.getChildren());
		return checkboxes;
    }
	

    public void clear(ActionEvent event) throws IOException {
    	selectionContainer.getChildrenUnmodifiable().forEach(c->{((CheckBox)c).setSelected(false);});
    	txtfNumber.clear();
    }

    public void handleTestButtonAction(ActionEvent event) {
    	List<CheckBox> checkBoxList=new ArrayList<CheckBox>();
    	selectionContainer.getChildrenUnmodifiable().forEach(c->{if(((CheckBox)c).isSelected()){checkBoxList.add(((CheckBox)c));}});
    	
    	List<File> fileList=new ArrayList<File>();
    	File folder=new File(testPath);
    	for(File test:folder.listFiles()){
    		for(CheckBox cb:checkBoxList){
        		if(test.getName().contains(cb.getText()+".xls")){
        			fileList.add(test);
        		}
        	}
    	}
    	String total=txtfNumber.getText().trim();
    	if(total.isEmpty()){
    		total="0";
    	}
    	TestManager.getInstance().createCurrentTest(fileList, Integer.parseInt(total), QuestionSelectionMode.BALANCED);
		Screen main=ScreenManager.getInstance().getScreen("MainView");
		((MainController)main.getController()).showTestScreen();
		
    	main.hideAndSwitch();

    }

    @FXML public void handleAllButtonAction (ActionEvent event) {
    	TestManager.getInstance().getTestsFromFiles(testPath, selectionContainer);
    }

    public void selectAllCheckboxes() {
    	selectionContainer.getChildrenUnmodifiable().forEach(c->{((CheckBox)c).setSelected(true);});
    }
    private void forceNumeric(){
    	txtfNumber.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) {
    	        if (!newValue.matches("\\d*")) {
    	        	txtfNumber.setText(newValue.replaceAll("[^\\d]", ""));
    	        }
    	    }
    	});
    }

//	/**
//	 * Füllt die <i>ArrayList</i> <u>questionAnswer</u> mit den ausgelesenen Werten aus der Exceltabelle.
//	 * @param questionAnswer
//	 * @param cell
//	 * @param rowNum
//	 * @param column
//	 * @param columnEnd
//	 * @return
//	 */
//	private ArrayList<String> fillQuestionAnswer(ArrayList<String> questionAnswer, Cell cell, int column) {
//
//		String valueS = cell.getStringCellValue().toString();
//		questionAnswer.add(column-2, valueS);
//
//		return questionAnswer;
//	}
//
//
//	/**
//	 * Füllt die <i>ArrayList</i> <u>chapterQid</u> mit den ausgelesenen Werten aus der Exceltabelle.
//	 * @param chapterQid
//	 * @param cell
//	 * @param rowNum
//	 * @param column
//	 * @param columnEnd
//	 * @return
//	 */
//	private ArrayList<Integer> fillchapterQid(ArrayList<Integer> chapterQid, Cell cell, int column) {
//		double valueD = cell.getNumericCellValue();
//		Integer valueI = (int) valueD;
//		chapterQid.add(valueI);
//		System.out.println("Es wurde hinzugefügt: " + valueI);
//		
//		return chapterQid;
//	}
//
//	private HashSet<Question> fillQuestionCatalogData(HashSet<Question> questionCatalogData,
//													ArrayList<Integer> chapterQid,
//													ArrayList<String> questionAnswer) {
//		int chapter = chapterQid.get(0);
//		int id = chapterQid.get(1);
//		String question = questionAnswer.get(0);
//		String answer1 = questionAnswer.get(1);
//		String answer2 = questionAnswer.get(2);
//
//		/*
//		 * null-Werte müssen eingefügt werden, weil es sonst zu Problemen beim Auslesen der Exceldatei kommt,
//		 * wenn eine Frage weniger als die maximale Antwortanzahl (6) hat. Hat sie >2 Antworten, werden die null-Werte
//		 * in der if-else Bedingung überschrieben
//		 */
//		String answer3 = null;
//		String answer4 = null;
//		String answer5 = null;
//		String answer6 = null;
//		int length = questionAnswer.size();
//
//												// Bekommt man das noch schöner hin?
//		if (length == 4) {
//			answer3 = questionAnswer.get(3);
//		} else if (length == 5) {
//			answer3 = questionAnswer.get(3);
//			answer4 = questionAnswer.get(4);
//		} else if (length == 6) {
//			answer3 = questionAnswer.get(3);
//			answer4 = questionAnswer.get(4);
//			answer5 = questionAnswer.get(5);
//		} else if (length == 7) {
//			answer3 = questionAnswer.get(3);
//			answer4 = questionAnswer.get(4);
//			answer5 = questionAnswer.get(5);
//			answer6 = questionAnswer.get(6);
//		}
//
////		Question questionAnswers = new Question(chapter, id, question, answer1, answer2, answer3, answer4, answer5, answer6);
////		questionCatalogData.add(questionAnswers);
//
//		return questionCatalogData;
//	}
//
//	/**
//	 *Es wird ein Nachrichtenfenster erzeugt und geöffnet.
//	 * @param type
//	 * @param title
//	 * @param headerText
//	 * @param contentText
//	 */
//	private void openAlertMessage(AlertType type, String title, String headerText, String contentText) {
//		Alert alert = new Alert(type);
//		alert.setTitle(title);
//		alert.setHeaderText(headerText);
//		alert.setContentText(contentText);
//		alert.showAndWait();
//	}
//
//	/**
//	 *Kontrollmethode (Konsolenausgabe)
//	 */
//	private void showQuestions(HashSet<Question> catalog) {
////		ArrayList<Question> list = new ArrayList<Question>(catalog);
////		int length = list.size();
////		System.out.println("Fragenpool:");
////		System.out.println("Kapitel \tID \tFrage \tAntwort1  \tAntwort2 \tAntwort3 \tAntwort4 \tAntwort5 \tAntwort6");
////		for(int i = 0; i < length; i++) {
////			int chapter = list.get(i).getChapter();
////			int id = list.get(i).getQuestionId();
////			String q = list.get(i).getQuestion();
////			String a1 = list.get(i).getAnswer1();
////			String a2 = list.get(i).getAnswer2();
////			String a3 = list.get(i).getAnswer3();
////			String a4 = list.get(i).getAnswer4();
////			String a5 = list.get(i).getAnswer5();
////			String a6 = list.get(i).getAnswer6();
////			System.out.println(chapter + "\t " + id + "\t " + q + "\t " + a1 + "\t" + a2 + "\t" + a3 + "\t " + a4 + "\t " + a5 + "\t " + a6);
////		}
//	}
//
//	/**
//	 * Wählt zufällig die zuvor festgesetzte Anzahl an Fragen aus dem Fragenpool aus und speichert sie in der <i>ArrayList</i> <u>questionCatalog</u>.
//	 */
//	public ArrayList<Question> chooseRandomQuestion(HashSet<Question> questionCatalogData) {
//
////		int questionCsize = questionCatalogData.size();
////
////		if (txtfNumber.getText().isEmpty() ) {
////			qNumber = questionCsize;
////		} else {
////			qNumber = Integer.parseInt(txtfNumber.getText());
////		}
////
////
////		if (qNumber < questionCsize) {
////			ArrayList <Question> arrayCatalog = new ArrayList<Question>(questionCatalogData);  // muss in ArrayList umgewandelt werden, um auf ein Elemt zugreifen zu können
////			HashSet<Question> hashCatalog = new HashSet<Question>();    //HashSet lässt keine Duplikate zu
////			// solange, Fragen hinzufügen bis die angegebene Anzahl erreicht ist
////
////			while (hashCatalog.size() < qNumber) {
////				int max = questionCsize;
////				Random random = new Random();
////				int index = random.nextInt(max);
////				Question q = arrayCatalog.get(index);
////				hashCatalog.add(q);
////			}
////			questionCatalog = new ArrayList<Question>(hashCatalog);
////		} else {
////			questionCatalog = new ArrayList<Question>(questionCatalogData);
////		}
////
////
////	// nur zur Kontrolle Sysout
////		ArrayList<Question> list = new ArrayList<Question>(questionCatalog);
////		int length = list.size();
////		System.out.println("zufällige Liste:");
////		System.out.println("Kapitel \tID \tFrage \tAntwort1  \tAntwort2 \tAntwort3 \tAntwort4 \tAntwort5 \tAntwort6");
////		for(int i = 0; i < length; i++) {
////			int chapter = list.get(i).getChapter();
////			int id = list.get(i).getQuestionId();
////			String q = list.get(i).getQuestion();
////			String a1 = list.get(i).getAnswer1();
////			String a2 = list.get(i).getAnswer2();
////			String a3 = list.get(i).getAnswer3();
////			String a4 = list.get(i).getAnswer4();
////			String a5 = list.get(i).getAnswer5();
////			String a6 = list.get(i).getAnswer6();
////			System.out.println(chapter + "\t " + id + "\t " + q + "\t " + a1 + "\t" + a2 + "\t" + a3 + "\t " + a4 + "\t " + a5 + "\t " + a6);
////		}
////	//
////		return questionCatalog;
//		return new ArrayList<Question>();
//	}
//
//
//
//	public static ArrayList<Question> getQuestionCatalog() {
//		//return questionCatalog;
//		return new ArrayList<Question>();
//	}

/*
	private void handleKeyEvents() {
		Parent root1 = MainController.getRoot1();

		root1.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				readExcelFillCatalog();
		    	chooseRandomQuestion(questionCatalogData);
			}
		});
	}
*/	 
}

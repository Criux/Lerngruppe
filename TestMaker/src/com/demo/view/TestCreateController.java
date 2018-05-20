package com.demo.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class TestCreateController {

	@FXML
	    private CheckBox checkbox11;

	    @FXML
	    private CheckBox checkbox10;

	    @FXML
	    private TextField txtfNumber;

	    @FXML
	    private Label labelNumber;

	    @FXML
	    private CheckBox checkbox15;

	    @FXML
	    private CheckBox checkbox14;

	    @FXML
	    private Button buttonTestCreate;

	    @FXML
	    private CheckBox checkbox1;

	    @FXML
	    private CheckBox checkbox13;

	    @FXML
	    private CheckBox checkbox2;

	    @FXML
	    private CheckBox checkbox12;

	    @FXML
	    private TextArea textareaIntro;

	    @FXML
	    private CheckBox checkbox16;

	    @FXML
	    private Button buttonClear;

	    @FXML
	    private Button buttonClose;

	    @FXML
	    private Button buttonAll;

	    @FXML
	    private CheckBox checkbox3;

	    @FXML
	    private CheckBox checkbox4;

	    @FXML
	    private CheckBox checkbox5;

	    @FXML
	    private CheckBox checkbox6;

	    @FXML
	    private CheckBox checkbox7;

	    @FXML
	    private CheckBox checkbox8;

	    @FXML
	    private CheckBox checkbox9;


    private HashSet<Question> questionCatalogData;		// enthält alle Fragen aus den ausgewählten Kapiteln

    public static ArrayList<Question> questionCatalog;				// enthält eine begrenzte Anzahl an zufällig ausgewählten Fragen
    														// der ausgewählten Kapitel

    public int qNumber;



	private ArrayList<CheckBox> getCheckboxesArray() {
    	ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
    	checkboxes.add(checkbox1);
    	checkboxes.add(checkbox2);
    	checkboxes.add(checkbox3);
    	checkboxes.add(checkbox4);
    	checkboxes.add(checkbox5);
    	checkboxes.add(checkbox6);
    	checkboxes.add(checkbox7);
    	checkboxes.add(checkbox8);
    	checkboxes.add(checkbox9);
    	checkboxes.add(checkbox10);
    	checkboxes.add(checkbox11);
    	checkboxes.add(checkbox12);
    	checkboxes.add(checkbox13);
    	checkboxes.add(checkbox14);
    	checkboxes.add(checkbox15);
    	checkboxes.add(checkbox16);

		return checkboxes;
    }

    private ArrayList<File> getFileList() {
    	ArrayList<File> file = new ArrayList<File>();
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter1.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter2.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter3.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter4.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter5.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter6.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter7.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter8.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter9.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter10.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter11.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter12.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter13.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter14.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter15.xlsx"));
    	file.add(new File("D://workspace//TestJavaFX//src//application//chapter//chapter16.xlsx"));

		return file;
    }

    public void clear(ActionEvent event) {
    	ArrayList<CheckBox> checkboxes = getCheckboxesArray();
    	for(int i = 0; i < checkboxes.size(); i++) {
    		checkboxes.get(i).setSelected(false);
    	}
    	txtfNumber.clear();
    }

    public void handleTestButtonAction(ActionEvent event) {
    	readExcelFillCatalog();
    	chooseRandomQuestion(questionCatalogData);
    }

    public void handleAllButtonAction (ActionEvent event) {
    	selectAllCheckboxes();
    }

    private void selectAllCheckboxes() {
    	ArrayList<CheckBox> checkboxes = getCheckboxesArray();
    	for(int i = 0; i < checkboxes.size(); i++) {
    		checkboxes.get(i).setSelected(true);
    	}
    }

    /**
     * In dieser Methode werden die Exceldateien ausgelesen und die Daten in einer <i>ArrayList</i> gespeichert.
     * @param questionList
     * @return
     */
	private HashSet<Question> readExcelFillCatalog() {

		// überprüft die Textfeldeingabe bei der Fragenanzahl
		if (txtfNumber.getText().isEmpty() || txtfNumber.getText().matches("[0-9]+")) {

			ArrayList<CheckBox> checkboxes = getCheckboxesArray();
			// wenn keine Checkbox ausgewählt wurde -> Fehlermeldung
			if ((!checkboxes.get(0).isSelected())
				&& (!checkboxes.get(1).isSelected())
				&& (!checkboxes.get(2).isSelected())
				&& (!checkboxes.get(3).isSelected())
				&& (!checkboxes.get(4).isSelected())
				&& (!checkboxes.get(5).isSelected())
				&& (!checkboxes.get(6).isSelected())
				&& (!checkboxes.get(7).isSelected())
				&& (!checkboxes.get(8).isSelected())
				&& (!checkboxes.get(9).isSelected())
				&& (!checkboxes.get(11).isSelected())
				&& (!checkboxes.get(12).isSelected())
				&& (!checkboxes.get(13).isSelected())
				&& (!checkboxes.get(14).isSelected())
				&& (!checkboxes.get(15).isSelected()) )
			{
				openAlertMessage(AlertType.ERROR, "Fehler", "Keine Auswahl:", "Bitte wähle mindestens ein Kapitel aus, um einen Test erstellen zu können");
			 } else {
			// es wurde mind. eine Checkbox ausgewählt
			    ArrayList<File> file = getFileList();
			    System.out.println("Kapitel \tDatei");
			   	questionCatalogData = new HashSet<Question>();

			   	for(int i = 0; i < checkboxes.size(); i++) {
			   		// prüft, ob eine Checkbox ausgewählt ist und die dazugehörige Exceldatei existiert
			   		if(checkboxes.get(i).isSelected()) {
			   			// Fehlermeldung, wenn file nicht existiert
			   			if(file.get(i).exists()) {

			   				System.out.println((i+1) + "\t\t" + file.get(i).getPath());
			    			ArrayList<Integer> chapterQid = new ArrayList<Integer>();
							ArrayList<String> questionAnswer = new ArrayList<String>();
			    			try {
			    				FileInputStream stream = new FileInputStream(file.get(i));
								XSSFWorkbook wb = new XSSFWorkbook(stream);
								XSSFSheet sheet = wb.getSheetAt(0);
								int rowStart = sheet.getFirstRowNum();
								int rowEnd = sheet.getLastRowNum();
								System.out.println("Total rows: "+rowEnd);
								// Zeile für Zeile pro Kapitel durchgehen
								for(int rowNum = (rowStart+1); rowNum < (rowEnd+1); rowNum++) {    // rowStart+1 -> in der 1. gefüllten Zeile der Datei Überschriften verwendet werden

									XSSFRow row = sheet.getRow(rowNum);
									int columnEnd = row.getLastCellNum();

									// Zelle für Zelle pro Spalte durchgehen
									for (int column = 0; column < columnEnd; column++) {
										Cell cell = row.getCell(column);
										int type = cell.getCellType();

										if (type == XSSFCell.CELL_TYPE_STRING) {
											fillQuestionAnswer(questionAnswer,cell, column);
										} else if (type == XSSFCell.CELL_TYPE_NUMERIC) {
											chapterQid=fillchapterQid(chapterQid, cell, column);
										}
									}
									//System.out.println(chapterQid.get(0));
									System.out.println(chapterQid.size());
									fillQuestionCatalogData(questionCatalogData, chapterQid, questionAnswer);
									questionAnswer.clear();
									chapterQid.clear();

								}
								wb.close();
								stream.close();

							} catch (IOException e) {
								System.out.println(e.getMessage());
								e.printStackTrace();
							}
			    		} else {
			    			System.out.println((i+1) + "\t\tDie Datei existiert nicht.");
							openAlertMessage(AlertType.INFORMATION, "Information", "Fehlende Datei:",
									"Der Test wurde erstellt.\nEs fehlen jedoch die Fragen des Kapitels " + (i+1) + ", weil die Datei nicht vorhanden ist.");
			    		}
			    	}
				 }
			   		// nur zur Kontrolle (Konsolenausgabe)
			   		//showQuestions(questionCatalogData);
			    closeWindow();
			 }
		} else {
			// Fehlermeldung, wenn ein Buchstabe, Sonderzeichen oder eine Dezimalzahl in dem TextFeld txtfNumber steht
			openAlertMessage(AlertType.ERROR, "Fehler", "Falsche Eingabe:", "Als Eingabe bei der Gesamtzahl sind nur Ganzzahlen oder kein Eingabe erlaubt!");
		}
		return questionCatalogData;

	}


	public void closeWindow() {
		Stage stage = MainController.getStageTestCreate();
		stage.close();
	}

	/**
	 * Füllt die <i>ArrayList</i> <u>questionAnswer</u> mit den ausgelesenen Werten aus der Exceltabelle.
	 * @param questionAnswer
	 * @param cell
	 * @param rowNum
	 * @param column
	 * @param columnEnd
	 * @return
	 */
	private ArrayList<String> fillQuestionAnswer(ArrayList<String> questionAnswer, Cell cell, int column) {

		String valueS = cell.getStringCellValue().toString();
		questionAnswer.add(column-2, valueS);

		return questionAnswer;
	}


	/**
	 * Füllt die <i>ArrayList</i> <u>chapterQid</u> mit den ausgelesenen Werten aus der Exceltabelle.
	 * @param chapterQid
	 * @param cell
	 * @param rowNum
	 * @param column
	 * @param columnEnd
	 * @return
	 */
	private ArrayList<Integer> fillchapterQid(ArrayList<Integer> chapterQid, Cell cell, int column) {
		double valueD = cell.getNumericCellValue();
		Integer valueI = (int) valueD;
		chapterQid.add(valueI);
		System.out.println("Es wurde hinzugefügt: " + valueI);
		
		return chapterQid;
	}

	private HashSet<Question> fillQuestionCatalogData(HashSet<Question> questionCatalogData,
													ArrayList<Integer> chapterQid,
													ArrayList<String> questionAnswer) {
		int chapter = chapterQid.get(0);
		int id = chapterQid.get(1);
		String question = questionAnswer.get(0);
		String answer1 = questionAnswer.get(1);
		String answer2 = questionAnswer.get(2);

		/*
		 * null-Werte müssen eingefügt werden, weil es sonst zu Problemen beim Auslesen der Exceldatei kommt,
		 * wenn eine Frage weniger als die maximale Antwortanzahl (6) hat. Hat sie >2 Antworten, werden die null-Werte
		 * in der if-else Bedingung überschrieben
		 */
		String answer3 = null;
		String answer4 = null;
		String answer5 = null;
		String answer6 = null;
		int length = questionAnswer.size();

												// Bekommt man das noch schöner hin?
		if (length == 4) {
			answer3 = questionAnswer.get(3);
		} else if (length == 5) {
			answer3 = questionAnswer.get(3);
			answer4 = questionAnswer.get(4);
		} else if (length == 6) {
			answer3 = questionAnswer.get(3);
			answer4 = questionAnswer.get(4);
			answer5 = questionAnswer.get(5);
		} else if (length == 7) {
			answer3 = questionAnswer.get(3);
			answer4 = questionAnswer.get(4);
			answer5 = questionAnswer.get(5);
			answer6 = questionAnswer.get(6);
		}

		Question questionAnswers = new Question(chapter, id, question, answer1, answer2, answer3, answer4, answer5, answer6);
		questionCatalogData.add(questionAnswers);

		return questionCatalogData;
	}

	/**
	 *Es wird ein Nachrichtenfenster erzeugt und geöffnet.
	 * @param type
	 * @param title
	 * @param headerText
	 * @param contentText
	 */
	private void openAlertMessage(AlertType type, String title, String headerText, String contentText) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}

	/**
	 *Kontrollmethode (Konsolenausgabe)
	 */
	private void showQuestions(HashSet<Question> catalog) {
		ArrayList<Question> list = new ArrayList<Question>(catalog);
		int length = list.size();
		System.out.println("Fragenpool:");
		System.out.println("Kapitel \tID \tFrage \tAntwort1  \tAntwort2 \tAntwort3 \tAntwort4 \tAntwort5 \tAntwort6");
		for(int i = 0; i < length; i++) {
			int chapter = list.get(i).getChapter();
			int id = list.get(i).getQuestionId();
			String q = list.get(i).getQuestion();
			String a1 = list.get(i).getAnswer1();
			String a2 = list.get(i).getAnswer2();
			String a3 = list.get(i).getAnswer3();
			String a4 = list.get(i).getAnswer4();
			String a5 = list.get(i).getAnswer5();
			String a6 = list.get(i).getAnswer6();
			System.out.println(chapter + "\t " + id + "\t " + q + "\t " + a1 + "\t" + a2 + "\t" + a3 + "\t " + a4 + "\t " + a5 + "\t " + a6);
		}
	}

	/**
	 * Wählt zufällig die zuvor festgesetzte Anzahl an Fragen aus dem Fragenpool aus und speichert sie in der <i>ArrayList</i> <u>questionCatalog</u>.
	 */
	public ArrayList<Question> chooseRandomQuestion(HashSet<Question> questionCatalogData) {

		int questionCsize = questionCatalogData.size();

		if (txtfNumber.getText().isEmpty() ) {
			qNumber = questionCsize;
		} else {
			qNumber = Integer.parseInt(txtfNumber.getText());
		}


		if (qNumber < questionCsize) {
			ArrayList <Question> arrayCatalog = new ArrayList<Question>(questionCatalogData);  // muss in ArrayList umgewandelt werden, um auf ein Elemt zugreifen zu können
			HashSet<Question> hashCatalog = new HashSet<Question>();    //HashSet lässt keine Duplikate zu
			// solange, Fragen hinzufügen bis die angegebene Anzahl erreicht ist

			while (hashCatalog.size() < qNumber) {
				int max = questionCsize;
				Random random = new Random();
				int index = random.nextInt(max);
				Question q = arrayCatalog.get(index);
				hashCatalog.add(q);
			}
			questionCatalog = new ArrayList<Question>(hashCatalog);
		} else {
			questionCatalog = new ArrayList<Question>(questionCatalogData);
		}


	// nur zur Kontrolle Sysout
		ArrayList<Question> list = new ArrayList<Question>(questionCatalog);
		int length = list.size();
		System.out.println("zufällige Liste:");
		System.out.println("Kapitel \tID \tFrage \tAntwort1  \tAntwort2 \tAntwort3 \tAntwort4 \tAntwort5 \tAntwort6");
		for(int i = 0; i < length; i++) {
			int chapter = list.get(i).getChapter();
			int id = list.get(i).getQuestionId();
			String q = list.get(i).getQuestion();
			String a1 = list.get(i).getAnswer1();
			String a2 = list.get(i).getAnswer2();
			String a3 = list.get(i).getAnswer3();
			String a4 = list.get(i).getAnswer4();
			String a5 = list.get(i).getAnswer5();
			String a6 = list.get(i).getAnswer6();
			System.out.println(chapter + "\t " + id + "\t " + q + "\t " + a1 + "\t" + a2 + "\t" + a3 + "\t " + a4 + "\t " + a5 + "\t " + a6);
		}
	//
		return questionCatalog;
	}



	public static ArrayList<Question> getQuestionCatalog() {
		return questionCatalog;
	}

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

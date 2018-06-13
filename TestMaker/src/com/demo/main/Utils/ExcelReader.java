package com.demo.main.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.main.model.Answer;
import com.demo.main.model.MultipleChoice;
import com.demo.main.model.Question;

public class ExcelReader {
	XSSFWorkbook wb;
	XSSFSheet sheet;
	int questionCol=0;
	List<Integer> answerCols=new ArrayList<Integer>();

	String pathFile;
	int chapter;
	static int max=0;

	public ExcelReader(){
		this(System.getProperty("user.dir")+"\\Tests\\chapter1.xlsx");
		//this("D:\\testQ.xlsx");

	}
	public ExcelReader(String path){
		pathFile = path;
		InputStream inp=null;
		try {
			inp = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			wb = new XSSFWorkbook(inp);
			sheet = wb.getSheetAt(0);
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean isCellBold(XSSFCell cell){

		return cell.getCellStyle().getFont().getBold();

	}
	public List<MultipleChoice> getQuestions(){
		List<MultipleChoice> questions = new ArrayList<MultipleChoice>();
		int totalRows=sheet.getLastRowNum()+1;
		int startAt=hasFileTitleLine()?1:0;
		this.getColNums();
		for(int i=startAt;i<totalRows;i++){
			XSSFRow row=sheet.getRow(i);

			String fileName = new File(pathFile).getName();
			fileName = fileName.substring(0, fileName.lastIndexOf(".xls"));
			chapter = Integer.parseInt(fileName.substring(fileName.indexOf(" ")+1).trim());

			//System.out.println("Question is at:"+questionCol+" and last answer at:"+answerCols.get(answerCols.size()-1));
			MultipleChoice q= createQuestion(row,this.questionCol,this.answerCols);
			if(q!=null){
				questions.add(q);
			}
		}
		System.out.println("found total questions:"+questions.size());
		max = max + questions.size();
		System.out.println("Maximale Anzahl an Fragen: " + max);
		return questions;
	}
	//can create infinite loops when the file has empty rows **Use with Caution**
	public List<MultipleChoice> getRandomQuestions(int total){
		int totalRows=sheet.getLastRowNum()+1;
		List<MultipleChoice>result=null;
		if(total>=totalRows){
			return this.getQuestions();
		}else{
			result=new ArrayList<MultipleChoice>();
			Random random= new Random();
			while(total>0){
				MultipleChoice q=this.createQuestion(sheet.getRow(random.nextInt(totalRows-1)+1), this.questionCol, this.answerCols);
				if(q!=null){
					if(!result.contains(q)){
						result.add(q);
						total--;
					}
				}

			}
			return result;
		}
	}

	private MultipleChoice createQuestion(XSSFRow row, int qCol, List<Integer>aCols) {
		MultipleChoice question= new MultipleChoice();
		List<Answer> answers = new ArrayList<Answer>();
		int lastAnswerPos=row.getLastCellNum();
		if(aCols.size()>1){
			lastAnswerPos=aCols.get(aCols.size()-1);
		}
		for(int i=qCol;i<lastAnswerPos;i++){
			XSSFCell cell=row.getCell(i);
			String data=null;
			//System.out.println("Cell in "+row.getRowNum()+","+i);
			if(cell!=null){
				if(cell.getCellTypeEnum().equals(CellType.STRING)){
					data=cell.getStringCellValue().trim();
				}else if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
					data=String.valueOf(cell.getNumericCellValue()).trim();
				}
				if(i==qCol){
					question.setQuestionText(data);
				}
				else{
					Answer answer=new Answer();
					answer.setText(data);
					answer.setCorrect(isCellBold(cell));
					if(answer.getText()!=null){
						answers.add(answer);
					}
				}
			}

		}
		try {
			question.setChapter(chapter);
			//System.out.println("Answers: "+answers);
			question.setAnswers(answers);
		} catch (NoCorrectAnswerException e) {
			//e.printStackTrace();
			System.err.println("The question in row "+row.getRowNum()+" does not have any correct answers. It will not be added to the questions' list.");
			return null;
		} catch (NoAnswersException e) {
			//e.printStackTrace();
			System.err.println("The question in row "+row.getRowNum()+" does not have any answers. It will not be added to the questions' list.");
			return null;
		}
		if(question.getQuestionText()==null){
			return null;
		}
		return question;

	}
	private boolean hasFileTitleLine(){
		//to be implemented
		return true;
	}
	private void getColNums(){

		XSSFRow row=sheet.getRow(sheet.getFirstRowNum());
		for(Cell cell:row){
			if(cell.getCellTypeEnum().equals(CellType.STRING)){
				if(cell.getStringCellValue().toLowerCase().contains("quest")||cell.getStringCellValue().toLowerCase().contains("frage")||cell.getStringCellValue().toLowerCase().contains("aufga")){
					this.questionCol=cell.getColumnIndex();
				}else if(cell.getStringCellValue().toLowerCase().contains("answer")||cell.getStringCellValue().toLowerCase().contains("option")||cell.getStringCellValue().toLowerCase().contains("antwo")){
					this.answerCols.add(cell.getColumnIndex());
				}

			}
		}
	}
	public static void main(String[] args) {
//		try {
//			ExcelReader r=new ExcelReader();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		int total=20;
		int parts=20;

	}

	public static int getMax() {
		return max;
	}
}

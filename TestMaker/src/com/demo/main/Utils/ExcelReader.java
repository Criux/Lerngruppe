package com.demo.main.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

	public ExcelReader() throws IOException{
		this("D:\\testQ.xlsx");
        
	}
	public ExcelReader(String path) throws IOException{
		InputStream inp = new FileInputStream(path);
		try {
			wb = new XSSFWorkbook(inp);
			sheet = wb.getSheetAt(0);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void test(){
		int totalRows=sheet.getLastRowNum()+1;
		int startAt=hasFileTitleLine()?1:0;
		for(int i=startAt;i<totalRows;i++){
			XSSFRow row=sheet.getRow(i);
			System.out.println(createQuestion(row));		
		}
	}
	private boolean isCellBold(XSSFCell cell){
		
		return cell.getCellStyle().getFont().getBold();
		
	}
	public List<MultipleChoice> getQuestions(){
		List<MultipleChoice> questions = new ArrayList<MultipleChoice>();
		
		int totalRows=sheet.getLastRowNum()+1;
		int startAt=hasFileTitleLine()?1:0;
		for(int i=startAt;i<totalRows;i++){
			XSSFRow row=sheet.getRow(i);
			MultipleChoice q= createQuestion(row);
			if(q!=null){
				questions.add(q);
			}
					
		}
		System.out.println("found total questions:"+questions.size());
		return questions;
	}
	private MultipleChoice createQuestion(XSSFRow row) {
		MultipleChoice question= new MultipleChoice();
		List<Answer> answers = new ArrayList<Answer>();	
		for(int i=0;i<row.getLastCellNum();i++){
			XSSFCell cell=row.getCell(i);
			String data=null;
			//System.out.println("Cell in "+row.getRowNum()+","+i);
			if(cell!=null){
				if(cell.getCellTypeEnum().equals(CellType.STRING)){
					data=cell.getStringCellValue().trim();
				}else if(cell.getCellTypeEnum().equals(CellType.NUMERIC)){
					data=String.valueOf(cell.getNumericCellValue()).trim();
				}
			}
			if(i==0){
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
		try {
			System.out.println("Answers: "+answers);
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
	public static void main(String[] args) {
		try {
			ExcelReader r=new ExcelReader();
			r.test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

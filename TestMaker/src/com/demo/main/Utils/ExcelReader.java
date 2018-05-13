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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.demo.main.model.Answer;
import com.demo.main.model.Question;

public class ExcelReader {
	XSSFWorkbook wb;
	XSSFSheet sheet;

	public ExcelReader() throws IOException{
		InputStream inp = new FileInputStream("D:\\testQ.xlsx");
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
			try {
				System.out.println(createQuestion(row));
			} catch (NoCorrectAnswerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	private boolean isCellBold(XSSFCell cell){
		
		return cell.getCellStyle().getFont().getBold();
		
	}
	public Question createQuestion(XSSFRow row) throws NoCorrectAnswerException{
		Question question= new Question();
		List<Answer> answers = new ArrayList<Answer>();
		for(int i=0;i<row.getLastCellNum();i++){
			XSSFCell cell=row.getCell(i);
			String data=cell.getStringCellValue();
			if(i==0){
				question.setQuestion(data);
			}
			else{
				Answer answer=new Answer();
				answer.setText(data);
				answer.setCorrect(isCellBold(cell));
				
				answers.add(answer);
			}
		}
		int totalCorrectAnswers=0;
		for(Answer a:answers){
			if(a.isCorrect()){
				totalCorrectAnswers++;
			}
		}
		if(totalCorrectAnswers==0){
			throw new NoCorrectAnswerException(question);
		}
		question.setAnswers(answers);
		
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

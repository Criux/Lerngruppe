package com.demo.main.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.demo.main.Utils.ExcelReader;
import com.demo.main.model.MultipleChoice;
import com.demo.main.model.QuestionSelectionMode;
import com.demo.main.model.Test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TestManager {
	private static TestManager instance;
	private Test currentTest;
	private TestManager(){
		
	}
	public static TestManager getInstance(){
        if(instance == null){
        	instance = new TestManager();
        }
        return instance;
    }
	public<T extends Pane> void getTestsFromFiles(String testPath, T container){
    	container.getChildren().removeAll(container.getChildren());
    	File[] list=Paths.get(testPath).toFile().listFiles();
    	Arrays.sort(
 			   list,
 			   new Comparator<File>() {
 			     public int compare(File a, File b) {
 			    	 if(a.getName().length()<b.getName().length()){
 			    		 return -1; 
 			    	 }
 			       return a.getName().compareTo(b.getName());
 			     }
 			   });
        for (File file : list) {
            String fileName=file.getName();
            if(fileName.contains(".xlsx")){
    			CheckBox box= new CheckBox();
        		box.setText(fileName.split("\\.")[0]);
        			container.getChildren().add(box);
    		}
        }
    }
	public void createCurrentTest(List<File> files,int total,QuestionSelectionMode selectionType){
		List<Integer> totalFromFile=null;
		List<MultipleChoice> result=new ArrayList<MultipleChoice>();
		if(selectionType.equals(QuestionSelectionMode.BALANCED)){
			totalFromFile=selectBalanced(files.size(),total);
		}else if(selectionType.equals(QuestionSelectionMode.RANDOM)){
			totalFromFile=selectRandom(files.size(),total);
		}else{
			System.err.println("TestManager: Unknown mode for question selection.");
		}
		Random random = new Random();
		for(int i=0;i<files.size();i++){
			System.out.println("Getting questions from file: "+files.get(i));
			ExcelReader reader=new ExcelReader(files.get(i).getAbsolutePath());
			List<MultipleChoice> fileQ=reader.getQuestions();
			int toAdd=totalFromFile.get(i);
			while(toAdd>0&&fileQ.size()>0){
				int pos=random.nextInt(fileQ.size());
				result.add(fileQ.get(pos));
				fileQ.remove(pos);
				toAdd--;
			}
			
		}
		System.out.println("Creating test with a total of "+result.size()+" questions.");
		Collections.shuffle(result);
		this.currentTest=new Test(result);
	}
	private List<Integer> selectBalanced(int parts,int total){
		List<Integer> result=new ArrayList<Integer>();
		int qPerTest=total/parts;
		for(int i=0;i<parts;i++){
			result.add(qPerTest);
		}
		int remainingQ=total%parts;
		Random random=new Random();
		for(int i=0;i<remainingQ;i++){
			result.get(random.nextInt(result.size()));
		}
		while(remainingQ>0){
			int checkInPos=random.nextInt(result.size());
			if(result.get(checkInPos)==(qPerTest)){
				result.set(checkInPos, qPerTest+1);
				remainingQ--;
			}
		}
		return result;
	}
	private List<Integer> selectRandom(int parts,int total){
		List<Integer> list=new ArrayList<Integer>();
		List<Integer> result=new ArrayList<Integer>();
		for(int i=0;i<parts+1;i++){
			if(i==0){
				list.add(0);
			}else if(i==parts){
				list.add(total);
			}else{
				list.add((int)new Random().nextInt(total));
			}
		}
		Collections.sort(list);
		for(int i=1;i<parts+1;i++){
			//System.out.println(list.get(i));
			result.add(list.get(i)-list.get(i-1));
		}
		return result;
	}
	public Test getCurrentTest() {
		return currentTest;
	}
	
}

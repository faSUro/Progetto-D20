package it.unipv.ingsw.d20.persistence.LocalIOHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import it.unipv.ingsw.d20.vendingmachine.model.Constants;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.BeverageCatalog;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.BeverageDescription;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.Ingredients;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.Tank;

public class VendingLocalIO {
	
	private String idVending;
	
	public VendingLocalIO(String idVending) {
		this.idVending = idVending;
		createFile(Constants.FILEPATH+Constants.BVCATPATH+"_"+idVending);
		createFile(Constants.FILEPATH+Constants.TANKSPATH+"_"+idVending);
		createFile(Constants.FILEPATH+Constants.VENDINGPATH+"_"+idVending);
	}
	
	public void createFile(String name) {
		try {
		      File myObj = new File(name);
		      if (myObj.createNewFile()) {
		    	  System.out.println("File created: " + myObj.getName());
		      } else {
		    	  System.out.println("File already exists.");
		      }
		 } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		 }
	}
	
	public BeverageCatalog getCatalogFromLocal() {
		BeverageCatalog bvCatalog = new BeverageCatalog();
		String nomeFile = Constants.FILEPATH + Constants.BVCATPATH+"_"+this.idVending;
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new BufferedReader(new FileReader(nomeFile)));
			String riga;
			String[] result = null;

			while(inputStream.hasNext()) {
				riga = inputStream.nextLine();
				result = riga.split(",");
				BeverageDescription bvdesc = new BeverageDescription(result[0],result[1],Double.valueOf(result[2]));
				for (int i = 3;i<result.length;i= i+2) {
					bvdesc.addIngredient(Ingredients.valueOf(result[i]),Double.valueOf(result[i+1]));
				}
				bvCatalog.addBeverageDescription(bvdesc);
				result = null;
			}
			
		} catch(FileNotFoundException e) {
			System.out.println(e);
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		
		return bvCatalog;
	}

	public void saveCatalogIntoLocal (BeverageCatalog bvCatalog) {
		String nomeFile = Constants.FILEPATH + Constants.BVCATPATH+"_"+this.idVending;
		try {
			FileWriter myWriter = new FileWriter(nomeFile);
			PrintWriter myPrintWriter   = new PrintWriter(myWriter);
			for (String code : bvCatalog.getCatalog().keySet()) {
				BeverageDescription bd = bvCatalog.getBeverageDesc(code);
				String line = bd.getCode()+","+bd.getName()+","+bd.getPrice();
				Map<Ingredients,Double> ingr = bd.getIngredients();
				for (Ingredients key : ingr.keySet()) {
					line +=","+key+","+ingr.get(key);
				}
				myPrintWriter.println(line);
			}
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} 
	}

	public HashMap<Ingredients,Tank> getTanksFromLocal() {
		HashMap<Ingredients,Tank> tankList = new HashMap<>();
		String nomeFile = Constants.FILEPATH + Constants.TANKSPATH+"_"+this.idVending;
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new BufferedReader(new FileReader(nomeFile)));
			String riga;
			String[] result = null;

			while(inputStream.hasNext()) {
				riga = inputStream.nextLine();
				result = riga.split(",");
				Tank t = new Tank(Ingredients.valueOf(result[0]),Double.valueOf(result[1]),Double.valueOf(result[2]));
				if (!(tankList.containsKey(t.getId()))) 
					tankList.put(t.getId(), t);
				result = null;
			}

		} catch(FileNotFoundException e) {
			System.out.println(e);
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return tankList;
	}
	
	public void saveTankIntoLocal(HashMap<Ingredients,Tank> tankList) {
		String nomeFile = Constants.FILEPATH + Constants.TANKSPATH+"_"+this.idVending;
		try {
			FileWriter myWriter = new FileWriter(nomeFile);
			PrintWriter myPrintWriter   = new PrintWriter(myWriter);
			for (Ingredients i : tankList.keySet()) {
				Tank t = tankList.get(i);
				String line = t.getId()+","+t.getLevel()+","+t.getTemperature();
				myPrintWriter.println(line);
			}
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} 
	}
	
}


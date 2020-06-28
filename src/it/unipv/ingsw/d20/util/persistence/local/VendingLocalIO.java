package it.unipv.ingsw.d20.util.persistence.local;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.unipv.ingsw.d20.util.Paths;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.BeverageCatalog;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.BeverageDescription;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.Ingredients;
import it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.CashContainer;
import it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.Sale;
import it.unipv.ingsw.d20.vendingmachine.model.tanks.Tank;

public class VendingLocalIO {

	public VendingLocalIO() {}
	
	public BeverageCatalog getCatalogFromLocal() {
		BeverageCatalog bvCatalog = new BeverageCatalog();
		String nomeFile = Paths.ASSETS_FOLDER + Paths.BEVERAGE_CATALOG;
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
		String nomeFile = Paths.ASSETS_FOLDER + Paths.BEVERAGE_CATALOG;
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
		String nomeFile = Paths.ASSETS_FOLDER + Paths.TANKS;
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new BufferedReader(new FileReader(nomeFile)));
			String riga;
			String[] result = null;

			while(inputStream.hasNext()) {
				riga = inputStream.nextLine();
				result = riga.split(",");
				Tank t = new Tank(Ingredients.valueOf(result[0]),Double.valueOf(result[1]),Double.valueOf(result[2]), 10); //10 volume casuale per fare test
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
		String nomeFile = Paths.ASSETS_FOLDER + Paths.TANKS;
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
	
	public String getVendingIDFromLocal() {
		String fileName = Paths.ASSETS_FOLDER + Paths.VENDING_ID;
		
		String IDNumber = null;
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(fileName));
		
			IDNumber = in.readLine();
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		if (IDNumber == null) {
			IDNumber = "";
		}
		
		return IDNumber;
	}
	
	public void saveVendingIDIntoLocal(String IDNumber) {
		String fileName = Paths.ASSETS_FOLDER + Paths.VENDING_ID;
		
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(fileName));
			
			out.println(IDNumber);
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public String getVendingTypeFromLocal() {
		String fileName = Paths.ASSETS_FOLDER + Paths.VENDING_TYPE;
		
		String type = null;
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(fileName));
		
			type = in.readLine();
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return type;
	}
	
	public CashContainer getCashContainerFromLocal() {
		int[] cashQuantity = new int[6];
		
		String nomeFile = Paths.ASSETS_FOLDER + Paths.CASH_CONTAINER_STATUS;
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new BufferedReader(new FileReader(nomeFile)));

			for (int i = 0; i < 6; i++) {
				cashQuantity[i] = inputStream.nextInt();
			}

		} catch(FileNotFoundException e) {
			System.out.println(e);
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		
		return new CashContainer(cashQuantity);
	}
	
	public void saveCashContainerIntoLocal(CashContainer cashContainer) {
		String nomeFile = Paths.ASSETS_FOLDER + Paths.CASH_CONTAINER_STATUS;
		
		try {
			FileWriter myWriter = new FileWriter(nomeFile);
			PrintWriter myPrintWriter   = new PrintWriter(myWriter);
			
			for (int i : cashContainer.getCoinNumber()) {
				myPrintWriter.println(i);
			}
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} 
	}

	public void saveSaleIntoLocal(Sale sale) {
		String nomeFile = Paths.ASSETS_FOLDER + Paths.SALE_LIST;
		
		try {
			FileWriter myWriter = new FileWriter(nomeFile, true);
			PrintWriter myPrintWriter   = new PrintWriter(myWriter);
			
			myPrintWriter.println(sale.toString());
			
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} 
		
	}

	public List<String> getSaleListFromLocal() {
		ArrayList<String> saleList = new ArrayList<>();
		
		String nomeFile = Paths.ASSETS_FOLDER + Paths.SALE_LIST;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(nomeFile));
			String buff;
			
			while ((buff = reader.readLine()) != null) {
				saleList.add(buff);
			}
			
			reader.close();
		} catch(FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return saleList;
	}

	public void emptyLocalSale() { //svuota il file con la lista delle sale
		try {
			PrintWriter writer = new PrintWriter(Paths.ASSETS_FOLDER + Paths.SALE_LIST);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
}


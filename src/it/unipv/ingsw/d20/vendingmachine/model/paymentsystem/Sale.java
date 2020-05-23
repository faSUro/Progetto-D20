package it.unipv.ingsw.d20.vendingmachine.model.paymentsystem;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.unipv.ingsw.d20.vendingmachine.model.beverage.Beverage;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.BeverageDescription;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.exceptions.DeliveryFailedException;
import it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.payment.exceptions.*;

@SuppressWarnings("unused")
public class Sale {
	
	private Date date;
	private BeverageDescription beverageDescription;
	private double price;
	
	public Sale(BeverageDescription beverageDescription, double credit) throws InsufficientCreditException, DeliveryFailedException {
		this.beverageDescription = beverageDescription;
		date = new Date();
		price = beverageDescription.getPrice();
		
		checkCredit(credit, price);
			
		Beverage beverage = new Beverage(beverageDescription); //checks whether the beverage was correctly delivered or not (InsufficientIngredientsException)	
	}
	
	public double checkCredit(double amount, double price) throws InsufficientCreditException {
		if (amount >= price) {
			return amount-price;
		} else { 
			throw new InsufficientCreditException();
		}		
	}

	@Override
	public String toString() { 
		StringBuilder saleInfo = new StringBuilder();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		saleInfo.append("Date: " + sdf.format(date) + "\n");
		
		saleInfo.append("Product: " + beverageDescription.getCode() + "\n");
		
		DecimalFormat df = new DecimalFormat("0.00");
		saleInfo.append("Total: �" + df.format(price) + "\n");
		
		return saleInfo.toString();
	}
	
}

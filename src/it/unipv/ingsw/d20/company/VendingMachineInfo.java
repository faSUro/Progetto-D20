package it.unipv.ingsw.d20.company;

import java.util.Date;
import java.util.List;

import it.unipv.ingsw.d20.vendingmachine.model.beverage.Ingredients;
import it.unipv.ingsw.d20.vendingmachine.model.tanks.Tank;

public class VendingMachineInfo {
	
	private Date lastUpdate;
	private double currentAmount;
	private List<Tank> tankList;
	
	public VendingMachineInfo() {
		currentAmount = 0;
		refreshLastUpdate();
	}
	
	public VendingMachineInfo(String cashInfo, String tankInfo) {
		currentAmount = Double.parseDouble(cashInfo);
		setTankList(tankInfo);
		refreshLastUpdate();
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	private void refreshLastUpdate() {
		lastUpdate = new Date();
	}
	
	public double getCurrentAmount() {
		return currentAmount;
	}
	
	public List<Tank> getTankList() {
		return tankList;
	}
	
	private void setTankList(String tankInfo) {
		String[] infoSplit = tankInfo.split(" ");
		
		for (int i = 0; i < infoSplit.length; i += 3) {
			Ingredients ingredient = Ingredients.valueOf(infoSplit[i]);
			double level = Double.parseDouble(infoSplit[i + 1]);
			double temperature = Double.parseDouble(infoSplit[i + 2]);
			Tank t = new Tank(ingredient, level, temperature);
			
			tankList.add(t);
		}
		
	}

}












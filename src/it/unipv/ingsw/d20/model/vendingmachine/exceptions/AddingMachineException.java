package it.unipv.ingsw.d20.model.vendingmachine.exceptions;

public class AddingMachineException extends Exception{
	
	
	public AddingMachineException(String message) {
		super(message);
	}
	
	public void printMessage() {
		System.out.println(super.getMessage());

	}
	
	

}
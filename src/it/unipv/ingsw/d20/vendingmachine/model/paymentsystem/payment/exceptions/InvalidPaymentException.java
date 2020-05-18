package it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.payment.exceptions;

/**
 * @author Luigi Zaccaria Del Pio
 *
 */
@SuppressWarnings("serial")
public class InvalidPaymentException extends Exception {
	
	public InvalidPaymentException() {
		super("Payment rejected");
	}

}
package it.unipv.ingsw.d20.vendingmachine.model.paymentsystem;

import it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.payment.exceptions.InsufficientCashForRestException;
import it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.payment.exceptions.InvalidPaymentException;

public class Test {

	public static void main(String[] args) {
		CashContainer cc = new CashContainer(10, 10, 10, 10, 10);
		
		System.out.println(cc.getTotalAmount());
		
		try {
			cc.addCoin(0.50);
		} catch (InvalidPaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cc.getTotalAmount());
		
		try {
			cc.dispenseRest(5);
			cc.dispenseRest(5);
			cc.dispenseRest(5);
		} catch (InsufficientCashForRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cc.dispenseRest(50);
		} catch (InsufficientCashForRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(cc.getTotalAmount());

	}

}

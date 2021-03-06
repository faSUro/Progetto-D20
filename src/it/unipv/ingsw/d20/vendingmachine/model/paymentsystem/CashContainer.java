package it.unipv.ingsw.d20.vendingmachine.model.paymentsystem;

import it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.exceptions.InsufficientCashForRestException;
import it.unipv.ingsw.d20.vendingmachine.model.paymentsystem.exceptions.InvalidCoinException;

/**
 * La classe gestisce le monete presenti nel distributore automatico.
 *
 */
public class CashContainer {
	
	private final double[] coinValue = {0.05, 0.10, 0.20, 0.50, 1.0, 2.0};
	private int[] coinNumber = new int[6]; //contiene il numero di monete di ciascun tipo (nell'ordine di coinValue)
	
	private double totalAmount;
	
	private static final int MIN_COIN_NUMBER = 10;
	
	/**
	 * Costruttore della classe CashContainer.
	 * @param cashQuantity numero di monete, presenti nel distributore, per ogni valore
	 */
	public CashContainer(int[] cashQuantity) {
		for (int i = 0; i < coinNumber.length; i++) { //inizializzazione del vettore coinNumber
			coinNumber[i] = cashQuantity[i];
		}

		refreshTotalAmount();
	}
	
	/**
	 * Questo metodo aggiunge una moneta alla cassa del distributore.
	 * @param coin valore della moneta
	 * @throws InvalidCoinException eccezione che viene lanciata se la moneta non è valida
	 */
	public void addCoin(double coin) throws InvalidCoinException {
		if (Math.random() < 0.05) //5% di probabilità che la moneta non sia valida
			throw new InvalidCoinException("La moneta inserita non è valida.");
		
		int index;
		for (index = 0; index < coinValue.length; index++) {
			if (coinValue[index] == coin) { //trova l'indice corrispondente al valore della moneta inserita
				break;
			}
		}
		coinNumber[index]++;
		refreshTotalAmount();
	}
	
	/**
	 * Questo metodo aggiorna il totale del denaro presenta nel distributore.
	 */
	public void refreshTotalAmount() {
		totalAmount = 0;
		for (int i = 0; i < coinValue.length; i++) {
			totalAmount += coinValue[i] * coinNumber[i]; //moltiplica il valore della moneta per il numero di monete di quel tipo
		}
	}
	
	/**
	 * Questo metodo eroga in monete la quantita' passata come parametro.
	 * @param credit credito attualmente inserito 
	 * @throws InsufficientCashForRestException eccezione che viene lanciata se non ci sono abbastanza monete per restituire il resto
	 */
	public void dispenseRest(double credit) throws InsufficientCashForRestException {	
		int creditX100 = (int) (credit * 100); //opero sul credito moltiplicato per 100 a causa dell'approssimazione della virgola mobile

		if(creditX100 > (int) (totalAmount * 100)) {
			throw new InsufficientCashForRestException();
		}
		
		for (int i = coinValue.length - 1; i >= 0; i--) { //dispensa le monete partendo da quelle piï¿½ grandi
			while (creditX100 >= (int) (coinValue[i] * 100)) {
				if (coinNumber[i] == 0) {
					break;
				}
				coinNumber[i]--;
				creditX100 -= (int) (coinValue[i] * 100);
			}
		}
		
		System.out.println("Dispensed €" + credit);
		
		refreshTotalAmount();
	}
	
	/**
	 * Questo metodo gestisce il ritiro delle monete da parte dell'operatore.
	 * @return total ammontare del denaro ritirato
	 */
	public double withdrawAmount() {
		double total = 0;
		
		for (int i = 0; i < coinValue.length; i++) {
			int difference = coinNumber[i] - MIN_COIN_NUMBER; 
			if (difference > 0) { //fa in modo di lasciare sempre almeno 10 monete di ogni tipo
				total += difference * coinValue[i];
				coinNumber[i] = MIN_COIN_NUMBER;
			}
		}
		
		refreshTotalAmount();
		
		return total;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	public int[] getCoinNumber() {
		return coinNumber;
	}

}

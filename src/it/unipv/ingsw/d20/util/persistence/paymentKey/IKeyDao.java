package it.unipv.ingsw.d20.util.persistence.paymentKey;

import java.util.ArrayList;


public interface IKeyDao {
	
	/**
	 * Ottiene tutte le chiavette salvate.
	 * @return
	 */
	public ArrayList <KeyPOJO> getAllKeys();
	
	/**
	 * Ottiene una specifica chiave, con il codice seriale specificato come parametro.
	 * @param serialCode
	 * @return
	 */
	public KeyPOJO getKey(String serialCode);
	
	/**
	 * Salva una nuova chiavetta.
	 * @param serialCode Codice della chiavetta
	 * @param credit credito inziale
	 */
	public void addKey(int serialCode, double credit);
	
	/**
	 * Aggiorna il credito di una chiavetta.
	 * @param serialCode Codice della chiavetta
	 * @param credit nuovo credito
	 * @return
	 */
	public boolean updateCredit(String serialCode, double credit);

	/**
	 * Ottiene il credito di una specifica chiavetta.
	 * @param serialCode Codice della chiavetta
	 * @return
	 */
	public double getCredit(String serialCode);
	
	/**
	 * Disattiva una chiavetta.
	 * @param serialCode Codice della chiavetta
	 */
	public void deactivateKey(String serialCode);

}

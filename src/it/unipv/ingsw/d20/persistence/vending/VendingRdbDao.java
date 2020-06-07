package it.unipv.ingsw.d20.persistence.vending;

import java.util.ArrayList;

import it.unipv.ingsw.d20.persistence.RdbOperations;
import it.unipv.ingsw.d20.vendingmachine.model.VendingMachineStatus;

/**
 * Implementazione dell'interfaccia IVendingDao. Implementa il DAO relativo al database relazionale.
 *
 */
public class VendingRdbDao implements IVendingDao{

	private RdbOperations op;
	
	public VendingRdbDao() {
		op = new RdbOperations();
	}
	
	public VendingMachineStatus getVendingStatusById(String id) {
		return op.getVendingStatusById(id);
	}

	@Override
	public void addVending(VendingPOJO vending) {
		op.addVending(vending);
	}

	@Override
	public ArrayList<VendingPOJO> getAllVending() {
		return op.getAllVending();
	}

}
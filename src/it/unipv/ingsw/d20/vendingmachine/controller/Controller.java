package it.unipv.ingsw.d20.vendingmachine.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import it.unipv.ingsw.d20.util.persistence.PersistenceFacade;
import it.unipv.ingsw.d20.util.persistence.vending.IVendingDao;
import it.unipv.ingsw.d20.vendingmachine.gui.customer.CustomerGui;
import it.unipv.ingsw.d20.vendingmachine.gui.customer.Pulsante;
import it.unipv.ingsw.d20.vendingmachine.gui.operator.OperatorGui;
import it.unipv.ingsw.d20.vendingmachine.model.VendingMachine;
import it.unipv.ingsw.d20.vendingmachine.model.VendingMachineStatus;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.Ingredients;
//controller per l'architettura MVC

public class Controller {
	private VendingMachine m;
	private CustomerGui gui;
	private OperatorGui opgui;
		
	public Controller(VendingMachine m, CustomerGui gui) {
		this.gui=gui;
		this.m=m;
		setText();
		addListenerA();
		addListenerB();
		addListenerKey();
		addListenerOp();
		addClosingListener();
	}

	public Controller(VendingMachine m, OperatorGui gui) {
		this.m=m;
		this.opgui=gui;
		setTankText();
		addListenerTank();
		addListenerCust();
		addClosingListener();
	}
	
	private void setText() { //imposta la TextArea per visualizzare il catalogo della vending machine
		gui.setText(m.getCatalog().toStringGui());
	}
	
	private void addListenerA() {
		for(Pulsante puls:gui.getLA()) { //aggiunge i listener ai pulsanti per selezionare la bevanda nell'interfaccia cliente
			puls.addActionListener(new ListenerA(puls.getValue(), m, gui)); 
		}
	}
	
	private void addListenerB() {
		for(Pulsante puls:gui.getLB()) { //aggiunge i listener ai pulsanti dei soldi nell'interfaccia cliente
			puls.addActionListener(new ListenerB(puls.getValue(), m, gui));
		}
	}
	
	private void addListenerKey() { //aggiunge i listener ai pulsanti per la chiavetta nell'interfaccia cliente
		gui.getIn().addActionListener(new ListenerKey(true,m, gui));
		gui.getOut().addActionListener(new ListenerKey(false,m, gui));
		gui.getOut().setEnabled(false); //disabilito preventivamente il pulsante per estrarre una chiavetta
	}
	
	private void addListenerOp() { //aggiunge il listener al pulsante per entrare nella modalità operatore
		gui.getOperator().addActionListener(new ToOperator(m, gui));
	}
	
	private void addListenerCust() { //aggiunge il listener al pulsante per entrare nella modalità cliente
		opgui.getCustomer().addActionListener(new ToCustomer(m, opgui));
	}
	
	private void setTankText() { //imposta il nome dei tank e i livelli attuali
		HashMap<Ingredients,Double> tankLevels=new HashMap<Ingredients, Double>();
		tankLevels=m.getTanksLevels();
		int k=0;
		for(Map.Entry<Ingredients, Double> i : tankLevels.entrySet()) {
			opgui.setElements(String.valueOf(i.getKey()),i.getValue()+"",k);
			k++;
		}
	}
	
	private void addListenerTank() { //aggiunge i listener ai pulsanti dell'interfaccia Operatore
		Pulsante[] puls=new Pulsante[m.getTankNumber()];
		puls=opgui.getPulsanti();
		for(int i=0; i<m.getTankNumber(); i++) {
			puls[i].addActionListener(new ListenerTank(puls[i].getPos(), m, opgui));
		}
	}
	
	private void addClosingListener() {
		gui.addWindowListener(new WindowClosingOperations(m));
	}
	
}



package it.unipv.ingsw.d20.company;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import it.unipv.ingsw.d20.company.net.CompanyServer;
import it.unipv.ingsw.d20.util.persistence.PersistenceFacade;
import it.unipv.ingsw.d20.util.persistence.vending.IVendingDao;
import it.unipv.ingsw.d20.util.persistence.vending.VendingPOJO;
import it.unipv.ingsw.d20.vendingmachine.model.VendingMachine;
import it.unipv.ingsw.d20.vendingmachine.model.exceptions.AddingOperatorException;

/**
 * Questa classe si occupa della gestione delle macchinette nel loro insieme: ci riesce
 * tramite un server che accetta connessioni TCP dalle macchinette e tramite una webapp
 * che utilizzano gli operatori da remoto.
 *
 */
public class Company {
	
	@SuppressWarnings("unused")
	private String name;
	
	/**
	 * Questa mappa contiene la lista di tutte le macchinette attualmente registrate
	 * insieme con il loro status.
	 */
	public static Map<String, Date> vendingMachineStatusList = new HashMap<>();
	
	
	/**
	 * Il costruttore si occupa di assegnare il nome alla compagnia, inizializzare la lista 
	 * delle macchinette rivolgendosi al database e "accendere" il server. Inoltre fa partire
	 * un timer che si occupa di controllare lo status delle macchinette nella lista.
	 * @param name nome della compagnia
	 */
	public Company(String name) {
		this.name = name;

		vendingMachineStatusList = new HashMap<>();
		
		int port = 8888;
        CompanyServer server = new CompanyServer();
        try {
        	server.serverLoop(port);
        } catch (IOException e) {
        	e.printStackTrace();
        }    
        
        PersistenceFacade pf = PersistenceFacade.getInstance();
        IVendingDao v = pf.getVendingDao();
        ArrayList<VendingPOJO> vendingList = v.getAllVendings();
        
        for (VendingPOJO vp : vendingList) { //riempie la mappa con tutte le vending machine già registrate nel database
        	vendingMachineStatusList.put(vp.getIdVending(), new Date());
        }
        
        Timer timer = new Timer();																   //ogni 10 minuti aggiorna lo status nel db di tutte le vending 
		timer.schedule(new RefreshVendingListStatus(), new Date(), TimeUnit.MINUTES.toMillis(10)); //machine, se necessario (se non ha ricevuto update) setta OFF)
	}
	
	/*public VendingMachine selectVendingMachine(String id) {
		return null;
		
	}
	
	public void addOperator(String id) throws AddingOperatorException {
		//da usare per il database
	}
	
	public void addRemoteOperator (String id) throws AddingOperatorException {
		//da usare per il database
	}*/
	
	/**
	 * Associa un nuovo ID ad una macchinetta che è stata accesa per la prima
	 * volta e la registra nel data base.
	 * @return ID della nuova macchinetta
	 */
	public static String registerNewVendingMachine() {
		String IDNumber = generateNewID();
		
		/*PersistenceFacade pf = PersistenceFacade.getInstance();
		IVendingDao ivd = pf.getVendingDao();
		ivd.addVending(new VendingPOJO(IDNumber, null));*/
		
		vendingMachineStatusList.put(IDNumber, new Date());
		
		return IDNumber;
	}
	
	/**
	 * Genera un ID univoco per una macchinetta che è stata accesa per la prima volta.
	 * @return ID generato
	 */
	private static String generateNewID() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String vendingIDNumber = sdf.format(new Date());
		
		String fileName = "IDN" + vendingIDNumber;
		return fileName; 
	}
	
}

package it.unipv.ingsw.d20.vendingmachine.view.customer;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * GUI dedicata al cliente. Nella parte sinistra c'e' un pannello contenente il
 * catalogo (e un tasto per l'accesso alla modalita' operatore), nella parte destra
 * tutti i possibili pulsanti che interessano il cliente (tastierino numerico e
 * controllo del credito).
 *
 */
@SuppressWarnings("serial")
public class CustomerGui extends JFrame {
	
	private static final int WIDTH = 1100, HEIGHT = 675;
	
	private CatalogPanel catalogPanel;
	private KeyboardPanel keyboardPanel;
	
	public CustomerGui() {
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		Container container = getContentPane();
		JPanel mainPanel = new JPanel();
		container.add(mainPanel);
		mainPanel.setLayout(new GridLayout(1, 2));
		
		catalogPanel = new CatalogPanel();
		keyboardPanel = new KeyboardPanel();
		
		mainPanel.add(catalogPanel); mainPanel.add(keyboardPanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setCatalog(String catalog) {
		catalogPanel.setCatalog(catalog);
	}
	
	public String getDisplay() {
		return keyboardPanel.getDisplay();
	}
	
	public void setDisplay(String creditToString) {
		keyboardPanel.setDisplay(creditToString);
	}
	
	public CustomerButton[] getCodeButtons() {
		return keyboardPanel.getCodeButtons();
	}

	public CustomerButton[] getCashButtons() {
		return keyboardPanel.getCashButtons();
	}

	public JButton getInsertKeyButton() {
		return keyboardPanel.getInsertKeyButton();
	}

	public JButton getEjectKeyButton() {
		return keyboardPanel.getEjectKeyButton();
	}

	public JButton getOperatorButton() {
		return catalogPanel.getOperatorButton();
	}
	
}

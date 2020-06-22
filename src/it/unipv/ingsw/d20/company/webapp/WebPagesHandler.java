package it.unipv.ingsw.d20.company.webapp;

import java.util.HashMap;
import java.util.Map;

public class WebPagesHandler {
	
	private static String folder = "res/webapp/pages/";
	
	private final Map<String, String> urlToPage;

    public WebPagesHandler(){	
    	urlToPage = new HashMap<>();
    	//navigation pages
    	urlToPage.put("/login", "login.html");
    	urlToPage.put("/wrong_user", "loginWrongUser.html");
    	urlToPage.put("/wrong_password", "loginWrongPassword.html");
    	urlToPage.put("/select", "selectOp.html");
    	urlToPage.put("/goodbye", "goodbye.html");       	
    	//vendings pages
    	urlToPage.put("/vendings", "vendingsTable.html");
    	urlToPage.put("/add_vending", "vendingForm.html");
    	urlToPage.put("/settings", "vendingSettings.html");
    	urlToPage.put("/report", "vendingReport.html");
    	urlToPage.put("/report_confirmed", "reportConfirmed.html");
    	//operators pages
    	urlToPage.put("/operators", "operatorsTable.html");
    	urlToPage.put("/add_operator", "operatorForm.html");
    	//keys pages
    	urlToPage.put("/keys", "keysTable.html");
    	urlToPage.put("/add_key", "keyForm.html");
    	//beverages pages
    	urlToPage.put("/beverages", "beveragesTable.html");
    	urlToPage.put("/beverage_edit", "beverageSettings.html");
    }
   
    public String getPage(String url) {
		return folder + urlToPage.get(url);
	}
    
}
package it.unipv.ingsw.d20.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.unipv.ingsw.d20.persistence.BvCatalog.BvCatalogPOJO;
import it.unipv.ingsw.d20.persistence.sale.SalePOJO;
import it.unipv.ingsw.d20.persistence.vending.VendingPOJO;

/**
 * Classe che implementa la connessione con il database. Implementa tutte le possibili operazioni con esso.
 *
 */
public class RdbOperations {
	
	private Connection con;
	
	public RdbOperations () {
		this.con = null;
	}

	// CODICE FUNZIONANTE, ma modificato introducendo i metodi startConnection, isOpen e CloseConnection.	
	/*
	public Connection startConnection() { // DA RIVEDERE; SISTEMARE COME IMPLEMENTAZIONE DI NOCERA (Relativamente alla connessione)?

		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://34.65.222.216:3306/prova","root",""); 
		
			//nasce per un problema relativo all'ora	///ingsw20?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
		}catch(Exception e){ System.out.println(e);}  			// TODO Auto-generated catch block
	
		return con;
	}  
	*/ 
	
	public Connection startConnection(Connection conn) { // codice rivisto, corretto come nocera.
		
		String DbDriver = null;
		String DbUrl=null;
		String username=null;
		String password = null;
		
		DbDriver = "com.mysql.cj.jdbc.Driver";
		DbUrl = "jdbc:mysql://34.65.222.216:3306/prova"; // � possibile implementare una connessione a uno schema particolare, in questo caso usiamo di default lo schema "prova"
		username = "root";
		password = "";
		if (isOpen (conn)) // se la connessione al database � gi� aperta viene chiusa, per poi riaprirne una nuova
			closeConnection(conn); // conn viene chiusa.
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DbUrl,username,password); // apertura della connessione
			//nasce per un problema relativo all'ora	///ingsw20?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
			}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}  				
		return conn; // se tutto va a buon fine la connessione viene aperta e restituita.
	}
	
	public boolean isOpen(Connection conn) {
		if (conn == null) 
			return false;
		else 
			return true;
	}
	
	public Connection closeConnection (Connection conn) {
		if ( !isOpen(conn) ) // se la connessione � gi� chiusa.
			return null;
		try {
			conn.close(); // se la chiusura della connessione va a buon fine,
			conn = null;  // conn viene posto a null. Viene poi restituito alla fine del blocco try catch.
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return conn; // se tutto va a buon fine, la connessione viene chiusa e viene restituito il valore null.
	}
	
	// DI SEGUITO CI SARANNO LE QUERY RELATIVA ALLA TABLE VENDING
	
	public String getVendingAddressById(String Id) {
		
		String result = null;
		String query = "SELECT Address FROM Vending WHERE idVending = '" + Id + "'";
		
		con = this.startConnection(con);
		Statement st;
		ResultSet rs;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeConnection(con);
		return result;

	}
	
	public ArrayList<VendingPOJO> getAllVending () {
		ArrayList<VendingPOJO> result = new ArrayList<>();
		String query = "SELECT * FROM Vending";
		
		con = this.startConnection(con);
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				VendingPOJO res = new VendingPOJO(rs.getString(1),rs.getString(2));
				result.add(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.closeConnection(con);
		return result;
	}
	
	public void addVending(String Id, String Address) {
		
		String query = "INSERT INTO Vending values ('" + Id +"','"+ Address + "')";
		
		con = this.startConnection(con);
		Statement st;	
		try {
			st = con.createStatement();
			st.executeUpdate(query); // usato per eseguire una query di inserimento.
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnection(con);
		
	}

	// DI SEGUITO CI SARANNO LE QUERY RELATIVE ALLA SALE
	
	public void addSale(SalePOJO sale) {
		
		ArrayList<String> values = new ArrayList<>();
		values.add(sale.getIdSale()); // primo attributo nella table
		values.add(sale.getIdVending()); // secondo attributo nella table
		values.add(sale.getIdBeverage()); // terzo attributo nella table
		values.add(sale.getDate()); // quarto attributo nella table
		
		String query = QueryGenerator.getInsertIntoValuesQuery("Sale", values);	
		con = this.startConnection(con);
		Statement st;	
		try {
			st = con.createStatement();
			st.executeUpdate(query); // usato per eseguire una query di inserimento.
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.closeConnection(con);


	}

	public SalePOJO getSaleById (String id) {
		SalePOJO result = null;
		String whereStatement = "idSale = '"+id+"'";
		String query = QueryGenerator.getSelectFromWhereQuery("*", "Sale", whereStatement);
		
		con = this.startConnection(con);
		Statement st;
		ResultSet rs;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				result = new SalePOJO (rs.getString(1),rs.getString(2),rs.getString(3),rs.getDate(4).toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeConnection(con);
		return result;
		
	}

	public ArrayList<SalePOJO> getAllSaleByIdVending (String idVending) {
		ArrayList<SalePOJO> result = new ArrayList<>();
		String whereStatement = "idVending = '" +idVending+"'";
		String query = QueryGenerator.getSelectFromWhereQuery("*", "Sale", whereStatement);
		System.out.println(query); // per debugging
		
		con = this.startConnection(con);
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				SalePOJO res = new SalePOJO (rs.getString(1),rs.getString(2),rs.getString(3),rs.getDate(4).toString());
				result.add(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.closeConnection(con);
		
		return result;
	}

	// DI SEGUITO CI SARANNO LE QUERY RELATIVE ALLA TABLE BvCatalog
	
	public BvCatalogPOJO getBeverage(String id) {
		BvCatalogPOJO result = null;
		String whereStatement = "idBev = '"+id+"'";
		String query = QueryGenerator.getSelectFromWhereQuery("*","BvCatalog", whereStatement);
		
		con = this.startConnection(con);
		Statement st;
		ResultSet rs;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				result = new BvCatalogPOJO(rs.getString(1),rs.getDouble(2),rs.getString(3),rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.closeConnection(con);
		return result;
	}
 
}

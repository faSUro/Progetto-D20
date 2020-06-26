package it.unipv.ingsw.d20.company.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

@SuppressWarnings("serial")
public class WelcomeServlet extends HttpServlet {
	
	private static String folder = "res/webapp/pages/";
	private Operator loggedOperator;
	WebAppController controller;
	
	public WelcomeServlet(){
		controller=new WebAppController();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (loggedOperator!=null) {
			if (req.getPathInfo().equals("/select")) { //OK
				resp.getWriter().write(Rythm.render(folder + "selectOp.html", loggedOperator));
			} 
			else if (req.getPathInfo().equals("/vendings")) { //OK
				resp.getWriter().write(Rythm.render(folder + "vendingsTable.html", Vendings.all()));
			}
			else if (req.getPathInfo().equals("/add_vending")) { //OK
				resp.getWriter().write(Rythm.render(folder + "vendingForm.html"));
			}
			else if (req.getPathInfo().equals("/settings")) { //OK
				Vending vending = Vendings.get(Integer.parseInt((req.getParameter("id"))));
				resp.getWriter().write(Rythm.render(folder + "vendingSettings.html", vending, Vendings.all()));
			}
			else if (req.getPathInfo().equals("/report")) { //OK
				resp.getWriter().write(Rythm.render(folder + "vendingReport.html", Operators.all()));
			}
			else if (req.getPathInfo().equals("/report_confirmed")) { //OK
				resp.getWriter().write(Rythm.render(folder + "reportConfirmed.html"));
			}
			else if (req.getPathInfo().equals("/add_operator")) { //OK
				resp.getWriter().write(Rythm.render(folder + "operatorForm.html"));
			}
			else if (req.getPathInfo().equals("/operators")) { //OK
				resp.getWriter().write(Rythm.render(folder + "operatorsTable.html", Operators.all()));
			}
			else if (req.getPathInfo().equals("/keys")) { //OK
				resp.getWriter().write(Rythm.render(folder + "keysTable.html", Vendings.all()));
			}
			else if (req.getPathInfo().equals("/add_key")) { //OK
				resp.getWriter().write(Rythm.render(folder + "keyForm.html"));
			}
			else if (req.getPathInfo().equals("/beverages")) { //OK
				resp.getWriter().write(Rythm.render(folder + "beveragesTable.html", Vendings.all()));
			}
			else if (req.getPathInfo().equals("/goodbye")) { //OK
				resp.getWriter().write(Rythm.render(folder + "goodbye.html"));
			}
			else {
				resp.getWriter().write(Rythm.render(folder + "selectOp.html", loggedOperator));
			}
		}
		else {	
			if  (req.getPathInfo().equals("/login")) { //OK
				resp.getWriter().write(Rythm.render(folder + "login.html"));
			}			
			else if (req.getPathInfo().equals("/wrong_user")) { //OK
				resp.getWriter().write(Rythm.render(folder + "loginWrongUser.html"));
			}
			else if (req.getPathInfo().equals("/wrong_password")) { //OK
				resp.getWriter().write(Rythm.render(folder + "loginWrongPassword.html"));
			}else {
				resp.getWriter().write(Rythm.render(folder + "login.html")); //OK
			}
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (req.getPathInfo().equals("/save_vending")) {
			Vendings.add(Integer.parseInt(req.getParameter("id")), req.getParameter("location"), req.getParameter("status"), req.getParameter("type"));
			resp.sendRedirect("/vendings");
		} 
		else if (req.getPathInfo().equals("/save_operator")) { //OK
			Operators.add(req.getParameter("first_name")+" "+req.getParameter("last_name"), req.getParameter("username"), req.getParameter("password"),  req.getParameter("type"));
			resp.sendRedirect("/operators");
		}
		else if (req.getPathInfo().equals("/send_report")) {
			System.out.println("AIUTOOO");
			resp.sendRedirect("/report_confirmed");
		} 
		else if (req.getPathInfo().equals("/try_login")) { //OK
		    try {   	
			Operator operator=Operators.getMy(req.getParameter("username"));
			loggedOperator=operator.checkLogIn(req.getParameter("username"), req.getParameter("inputPassword"));	
		    }
		    catch (NullPointerException e) {
		    	System.out.println("Invalid Operator Username");
		    	resp.sendRedirect("/wrong_user");
		    }
		    catch (InvalidPasswordException ep) {
		    	System.out.println("Invalid Password");
		    	resp.sendRedirect("/wrong_password");
		    }
		    resp.sendRedirect("/select");
		}
		else if (req.getPathInfo().equals("/logout")) { //OK
			loggedOperator=null;
			resp.sendRedirect("/goodbye");
		} 
		
	}
}
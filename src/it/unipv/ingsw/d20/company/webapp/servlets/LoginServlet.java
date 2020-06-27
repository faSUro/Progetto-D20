package it.unipv.ingsw.d20.company.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import it.unipv.ingsw.d20.company.webapp.WebAppController;
import it.unipv.ingsw.d20.company.webapp.WebPagesHandler;
import it.unipv.ingsw.d20.company.webapp.exceptions.InvalidPasswordException;
import it.unipv.ingsw.d20.company.webapp.exceptions.InvalidUserException;

/**
 * Servlet che gestisce le richieste sul path /d20/* (operazioni di login e logout).
 *
 */
@SuppressWarnings("serial")
public class LoginServlet extends WebAppServlet {
	
	public LoginServlet(WebAppController controller, WebPagesHandler handler){
		super(controller, handler);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url=handler.trimUrl(req.getRequestURI());
			
		if (controller.getLoggedOperator()==null) {
			resp.getWriter().write(Rythm.render(handler.getPage(url)));
		}
		else { //Se l'utente è già loggato, viene rimandato alla pagina di selezione
			resp.sendRedirect("/d20/selection/");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (req.getPathInfo().equals("/logout")) {
			controller.setNotLogged();
			resp.sendRedirect("/d20/goodbye");
		}
		else {
			try {   	
			  controller.checkOperatorLogIn(req.getParameter("username"), req.getParameter("inputPassword"));	
			} catch (InvalidUserException eu) {
			   System.out.println("Invalid Operator Username");
			   resp.sendRedirect("/d20/wrong_user");
			} catch (InvalidPasswordException ep) {
			   System.out.println("Invalid Password");
			   resp.sendRedirect("/d20/wrong_password");
			}
			resp.sendRedirect("/d20/selection/");
		}
	}
	
}
	

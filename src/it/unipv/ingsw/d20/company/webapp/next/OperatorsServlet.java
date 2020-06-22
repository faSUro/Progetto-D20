package it.unipv.ingsw.d20.company.webapp.next;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import it.unipv.ingsw.d20.company.webapp.WebAppController;
import it.unipv.ingsw.d20.company.webapp.WebPagesHandler;

@SuppressWarnings("serial")
public class OperatorsServlet extends WebAppServlet {
	
	public OperatorsServlet(WebAppController controller, WebPagesHandler handler){
		super(controller, handler);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url= req.getPathInfo();
				
		if (controller.getLoggedOperator()!=null) {
			if (url==null) { 
				resp.getWriter().write(Rythm.render(handler.getPage("/select"), controller.getLoggedOperator()));
			}
			else if (url.equals("/")) {
				resp.getWriter().write(Rythm.render(handler.getPage("/operators"), controller.getAllOperators()));
			}
			else {
				System.out.println(url);
				String urlTrimmed=url.substring(0, url.length()-1); //da controllare
				resp.getWriter().write(Rythm.render(handler.getPage(urlTrimmed)));
			}
		}
		else {
			resp.sendRedirect("/d20/");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (req.getPathInfo().equals("/save_operator")) {
		controller.addOperator(req.getParameter("username"), req.getParameter("first_name")+" "+req.getParameter("last_name"), req.getParameter("password"), req.getParameter("type"));
		resp.sendRedirect("/d20/selection/operators/");
		}
	
	}
	
}
package lpo;

import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.util.*;

import java.util.logging.Logger;

@SuppressWarnings("serial")
public class ViewEventsServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(ViewEventsServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.info("########## START VIEW EVENTS ###########");
		
		// check user authentication :
		if(UserManager.GetUser() == null) {
			resp.sendRedirect("WelcomePage.jsp");
		}
		
		// get all current events
		List<lpo.Event> events = DataAccessManager.GetEventList();
		
		req.setAttribute("events", events);
		
		// build display page
		req.getRequestDispatcher("/WEB-INF/DisplayEvents.jsp").forward(req, resp);
	}
}

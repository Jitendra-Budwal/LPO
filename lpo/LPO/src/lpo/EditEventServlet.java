package lpo;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.ServletException;

import java.util.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class EditEventServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(EditEventServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("########## START EDIT EVENT GET ###########");

		// Check for valid user session
		lpo.User user = UserManager.GetUser();
		
		if (user == null)
			resp.sendRedirect("WelcomePage.jsp");

		// get event from datastore 
		String eventKey = req.getParameter("k");
		
		// pull the event object out
		lpo.Event event = EventManager.GetEvent(eventKey);
		
		req.setAttribute("event", event);
		
		// build display page
		req.getRequestDispatcher("/WEB-INF/EditEvent.jsp").forward(req, resp);

	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.info("########## START EDIT EVENT POST ###########");
		
		// Check for valid user session
		lpo.User user = UserManager.GetUser();
		
		if (user == null)
			resp.sendRedirect("WelcomePage.jsp");
		
		// get event from datastore 
		String eventKey = req.getParameter("k");
		
		// pull the event object out
		lpo.Event event = EventManager.GetEvent(eventKey);

		String eventName = req.getParameter("eventName").trim();
		String description = req.getParameter("description").trim();
		
		boolean formIsComplete = true;
		
		int minParticipants = 0;
		try {
			minParticipants = Integer.parseInt(req.getParameter("minParticipants"));
		}
		catch(Exception e) {
			log.info("ERROR PARSING MIN PARTICIPANTS: " + e.toString());
			formIsComplete = false;
		}
		
		log.info("FORM VARS : " + eventName + " " + description + " " + minParticipants);
		
		if (eventName == null 
				|| eventName.isEmpty() 
				|| description == null 
				|| description.isEmpty() 
				|| minParticipants < 1) {
			
			formIsComplete = false;
		}

		if (formIsComplete) {
			
			// create event and populate available attributes
			event.setName(eventName);
			event.setDescription(description);
			event.setMinParticipants(minParticipants);
						
			// persist to database
			DataAccessManager.UpdateEvent(event);
			
			resp.sendRedirect("/Menu");
		}
		else
		{
			// reshow the same jsp page with error message :
			req.getRequestDispatcher("/WEB-INF/EditEvent.jsp").forward(req, resp);
		}

		return;
	}
}

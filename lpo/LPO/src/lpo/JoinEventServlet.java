package lpo;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.ServletException;

import com.google.appengine.api.datastore.EntityNotFoundException;

import java.util.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class JoinEventServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(JoinEventServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.info("########## JOIN EVENT GET ###########");
		
		// Check for valid user session
		lpo.User user = UserManager.GetUser();
		
		if (user == null)
			resp.sendRedirect("WelcomePage.jsp");

		// get event from datastore 
		String eventKey = req.getParameter("k");
		
		// pull the event object out
		lpo.Event event = EventManager.GetEvent(eventKey);
		
		// pull any existing subscriptions
		lpo.EventSubscription eventSubscription = EventSubscriptionManager.GetEventSubscription(user.getEmailAddress(), event.getKey());

		req.setAttribute("event", event);
		req.setAttribute("eventSubscription", eventSubscription);
		
		// build display page
		req.getRequestDispatcher("/WEB-INF/JoinEvent.jsp").forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.info("########## JOIN EVENT POST ###########");
		
		// Check for valid user session
		lpo.User user = UserManager.GetUser();
		
		if (user == null)
			resp.sendRedirect("WelcomePage.jsp");

		// get event from datastore 
		String eventKey = req.getParameter("k");
		
		// pull the event object out
		lpo.Event event = EventManager.GetEvent(eventKey);
				
		ArrayList<String> listDayHour = new ArrayList<String>();
		
		String dayofweek = req.getParameter("dayofweek");
		String hour = req.getParameter("hour");
		
		listDayHour.add(dayofweek + hour);
		
		EventSubscriptionManager.InsertEventSubscription(user.getEmailAddress(), eventKey, listDayHour);
		
		return;
		
	}
}

package lpo;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.ServletException;

import com.google.appengine.api.datastore.EntityNotFoundException;

import java.util.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class ViewEventServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(ViewEventServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.info("########## VIEW EVENT GET###########");
		
		
		// Check for valid user session
		lpo.User user = UserManager.GetUser();
		
		if (user == null)
			resp.sendRedirect("WelcomePage.jsp");

		// get event from datastore 
		String eventKey = req.getParameter("k");
		
		// pull the event object out
		lpo.Event event = EventManager.GetEvent(eventKey);

		Map<String, List<String>> map = EventManager.CheckEventFulfillment(event);
		
		for (Map.Entry<String, List<String>> entry : map.entrySet()) { 
			log.info("DATE : " + entry.getKey());
			for (String s : entry.getValue())
				log.info("SUBSCRIBER : " + s);
		}
		
		// pull any existing subscriptions
		lpo.EventSubscription eventSubscription = EventSubscriptionManager.GetEventSubscription(user.getEmailAddress(), event.getKey());

		req.setAttribute("event", event);
		req.setAttribute("eventSubscription", eventSubscription);
		
		// build display page
		req.getRequestDispatcher("/WEB-INF/ViewEvent.jsp").forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		log.info("########## VIEW EVENT POST ###########");
		log.info("THIS IS THE PARAMETER 'SUNDAY'");
		log.info(req.getParameter("1,0"));
		log.info(req.getParameter("15,3"));
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
		
		
		int[][] subSlots = new int[24][7];
		for(int i=0;i<24;i++){
			for(int j=0;j<7;j++){
				System.out.println("Checking ("+i+","+j+")..."+"the parameter is: "+req.getParameter(Integer.toString(i)+","+Integer.toString(j)));
				if(req.getParameter(Integer.toString(i)+","+Integer.toString(j))!=null){
					subSlots[i][j]=1;
					System.out.println("Time slot registered for ("+i+","+j+")");
				}else{
					subSlots[i][j]=0;
				}
			}
		}
		EventSubscriptionManager.InsertEventSubscription(user.getEmailAddress(), eventKey, subSlots);
		
		
		//EventSubscriptionManager.InsertEventSubscription(user.getEmailAddress(), eventKey, listDayHour);
		
		// build display page
		System.out.println("about to redirect");
				resp.sendRedirect("ViewEvent?k="+eventKey);
		
		
	}
}

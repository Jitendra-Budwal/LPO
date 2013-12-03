package lpo;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.ServletException;

import java.util.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class CreateEventServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(CreateEventServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("########## START CREATE EVENT ###########");
	
		// Check for valid user session
		lpo.User user = UserManager.GetUser();
		
		if (user == null)
			resp.sendRedirect("WelcomePage.jsp");

		// check form variables to ensure that minimum required info was added.
		boolean formIsComplete = true;
		
		String eventName = req.getParameter("eventName");
		String description = req.getParameter("description");
		String invitationList = req.getParameter("invitationList");
		
		String[] emailList;
		List<String> listInvitees = new ArrayList<String>();
		
		if (invitationList != null && !invitationList.isEmpty()) {
			emailList = invitationList.split(",");
			listInvitees = new ArrayList<String>(Arrays.asList(emailList));
			
		}

		if (eventName == null || eventName.isEmpty() || description == null || description.isEmpty()) {
			formIsComplete = false;
		}
		
		int minParticipants = 0;
		try {
			minParticipants = Integer.parseInt(req.getParameter("minParticipants"));
		}
		catch(Exception e) {
			log.info("ERROR PARSING MIN PARTICIPANTS: " + e.toString());
			formIsComplete = false;
		}
		
		log.info("FORM VARS : " + eventName + " " + description + " " + minParticipants + " " + invitationList);
		
		if (formIsComplete) {
			// create event and populate available attributes
			Event newEvent = new Event();
			
			newEvent.setName(eventName);
			newEvent.setDescription(description);
			newEvent.setMinParticipants(minParticipants);
			
			
			if (listInvitees.size() > 0) {
				newEvent.setListInvitees(listInvitees);
				// Check for dupes
				ArrayList<String> recipients = new ArrayList<String>();
				for (String s : listInvitees){
					if (!recipients.contains(s)) {
						recipients.add(s);
					}
				}
				
				// Send Email to invitees
				EmailManager.SendEmail(user.getEmailAddress(), recipients, "Invitation to " + eventName,
						user.getNickName() + " is using LPO to organize the " + eventName 
						+ ". Please visit LPO website at http://lpo-app.appspot.com to get more details and participate.");
				
			}
			
			// persist to database
			String eventKey = DataAccessManager.InsertEvent(newEvent);
//			DataAccessManager.InsertEvent(newEvent);

			
			log.info("SUBSCRIBE CREATOR : " + user.getEmailAddress() + " to new event key" + newEvent.getKey());
			
			//subscribe  creator to this new event with no presumed availibility
			int[][] subSlots = new int[24][7];
			EventSubscriptionManager.InsertEventSubscription(user.getEmailAddress(), eventKey, subSlots);
			resp.sendRedirect("/Menu");
		}
		else
		{
			// reshow the same jsp page with error message :
			req.getRequestDispatcher("/CreateEvent.jsp").forward(req, resp);
		}

		return;
		
		
	}
}

package lpo;

import java.io.IOException;

import javax.servlet.http.*;

import java.util.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class CronEventListEmailServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(CronEventListEmailServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		log.info("########## CRON EVENT LIST SERVLET ###########");
		
		String adminEmailAccount = "psaini14@gmail.com";
		
		// pull hours from query parameter
		int hour = Integer.parseInt(req.getParameter("hrs"));
		
		// figure out day of week and time slot we need to pull
		
		GregorianCalendar g = new GregorianCalendar();
		g.add(Calendar.HOUR_OF_DAY,  hour);
		
		int dayOfWeek = g.get(Calendar.DAY_OF_WEEK) - 1;
		int hourOfDay = g.get(Calendar.HOUR_OF_DAY);
		
		log.info("DAY : " + dayOfWeek + " HOUR : " + hourOfDay);
		

		// get list of all events
		List<lpo.Event> listEvents =  DataAccessManager.GetEventList();
		
		String timeLabel = hour == 1 ? " hour." : " hours.";		
		ArrayList<String> recipients;

		// iterate over event list to check fulfillment

		for (lpo.Event e : listEvents) {
			
			log.info("CHECK EVENT : " + e.getName());
			recipients  = EventManager.CheckEventFulfillment(e, dayOfWeek, hourOfDay);
			
			// if fulfilled, we should get back a recipients list
			if (recipients.size() > 0) {
				
				log.info("EVENT FULFILLED AND TIME TO SEND MESSAGE!");
				
				String subject = "Notification email for : " + e.getName();
				String body = "The event " + e.getName() + " will start in " + hour + timeLabel;
				EmailManager.SendEmail(adminEmailAccount, recipients, subject, body);
			}
		}
		
		// iterate over list and send an email to each subscriber letting them
		// know that the event is staring in X hours

		return;
				
		
//		// CRON JOB to email the entire event list to a user
//		List<lpo.Event> listEvents = DataAccessManager.GetEventList();
//		
//		String emailSubject = "All Events Email";
//		String emailBody = "The following are all events in the system : \n\n";
//		
//		for (lpo.Event event : listEvents) {
//			emailBody += emailBody + event.getName() + "\n" + event.getDescription() + "\n\n";
//		}
//		
//		EmailManager.SendEmail("psaini14@gmail.com", "psaini14@gmail.com", emailSubject, emailBody);
		
		
	}
}

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
		
		// CRON JOB to email the entire event list to a user
		List<lpo.Event> listEvents = DataAccessManager.GetEventList();
		
		String emailSubject = "All Events Email";
		String emailBody = "The following are all events in the system : \n\n";
		
		for (lpo.Event event : listEvents) {
			emailBody += event.getName() + "\n" + event.getDescription() + "\n\n";
		}
		
		EmailManager.SendEmail("budwal.j@gmail.com", "budwal.j@gmail.com", emailSubject, emailBody);
		
		
	}
}

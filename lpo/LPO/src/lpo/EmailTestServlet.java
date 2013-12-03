package lpo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class EmailTestServlet extends HttpServlet {
	
	private static final ArrayList<String> Arrays = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		lpo.User user = UserManager.GetUser();
		if (user != null) {
			// build a test list of recipients (this would be the subscriber list)
			String origList = "jbudwal.swe@gmail.com, budwal.j@gmail.com, budwaljb-whs@yahoo.com";
			
			// remove spaces and split the list using the comma as a deliminator
			origList.replace(" ","");
			String[] splitRecipients = origList.split(",");
			
			// add unique values to the final list
			ArrayList<String> recipients = new ArrayList<String>();
			
			for (String s : splitRecipients){
				if (!recipients.contains(s)) {
					recipients.add(s);
				}
			}
			
			EmailManager.SendEmail(user.getEmailAddress(), recipients, "Test email!", "Hah.  I spammed you!  LPO Rulz!");

			PrintWriter out = resp.getWriter();
		    out.println("Boo!  Email sent");
		    
		}
		else {
			// Can't do much!  No one to send the email to!
			UserService userService = UserServiceFactory.getUserService();
			resp.sendRedirect(userService.createLoginURL("/EmailTestServlet"));
		}
	}
}

package lpo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class AuthorizationServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AuthorizationServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		log.info("Auth Round Trip executed");
		log.info("PARAMETER K = " + req.getParameter("k"));
		log.info("QUERY STRING : " + req.getQueryString());
		// check authorization just in case they're already authorized!
		lpo.User user = UserManager.GetUser();
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			
			// check if we have an event key.  If so, let's redirect
			// back to event view AFTER the successful login
			if (req.getParameter("k") != null && !req.getParameter("k").isEmpty()) {
				resp.sendRedirect(userService.createLoginURL("/ViewEvent?k="+req.getParameter("k")));
				return;
			}
			else {
				resp.sendRedirect(userService.createLoginURL("/Menu"));
				return;
			}
		}
		// Actually Logged in, so let's redirect to Menu
		else {
			resp.sendRedirect("/Menu");
		}
	}
}

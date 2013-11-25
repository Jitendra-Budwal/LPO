package lpo;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class AuthorizationServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AuthorizationServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		log.info("Auth Round Trip executed");
		
		// check authorization just in case they're already authorized!
		lpo.User user = UserManager.GetUser();
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			resp.sendRedirect(userService.createLoginURL("http://localhost:8888/Menu"));
		}
		// Actually Logged in, so let's redirect to Menu
		else {
			resp.sendRedirect("/Menu");
		}
	}
}

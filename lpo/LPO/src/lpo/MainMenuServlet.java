package lpo;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class MainMenuServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(MainMenuServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				
		// Get currently authenticated google user
//		UserService userService = UserServiceFactory.getUserService();
//        User user = userService.getCurrentUser();

		lpo.User user = UserManager.GetUser();
		
        // Validate that the user session is correct
        if (user != null) {
        	// Show Main Menu page
        	log.info("User is available.");
        	
        	req.getRequestDispatcher("/WEB-INF/MainMenu.jsp").forward(req, resp);
        } 
        else {
        	// Redirect to login page
        	log.info("No user - send to welcome page");
        	resp.sendRedirect("WelcomePage.jsp");        
    	}
	}
}

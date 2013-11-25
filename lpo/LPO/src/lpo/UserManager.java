package lpo;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.logging.Logger;

public class UserManager {
		
	private static final Logger log = Logger.getLogger(UserManager.class.getName());
	
	public static lpo.User GetUser() {
		
		log.info("Get User");
		
		lpo.User user = null;
		
		UserService userService = UserServiceFactory.getUserService();
        User gUser = userService.getCurrentUser();
        
        // Cannot proceed unless we have an authenticated google user 
        if (gUser == null)
        	return null;
        else {
        	user = DataAccessManager.GetUser(gUser);
        }
		
        return user;
	}
	
	public static void SetUser() {
		
		
		
	}
	
}

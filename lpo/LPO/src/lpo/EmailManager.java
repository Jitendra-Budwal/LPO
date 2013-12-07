package lpo;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.users.User;

import java.util.logging.Logger;

public class EmailManager {
	
	private static final Logger log = Logger.getLogger(EmailManager.class.getName());
	
	
	// single email recipient
	public static String SendEmail(String fromEmail, String toEmail, String subject, String body) {
		ArrayList<String> listEmail = new ArrayList<String>();
		listEmail.add(toEmail);
		
		return SendEmail(fromEmail, listEmail, subject, body);
	}

	// multiple email recipients
	public static String SendEmail(String fromEmail, ArrayList<String> toEmail, String subject, String body) {
		
		String errorMessage = null;
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		// need try / catch for invalid email field trapping
		try {
			
		    Message msg = new MimeMessage(session);
		    
		    // set from email address :
		    // this must be an admin of the system OR the currently logged
		    // in user
			msg.setFrom(new InternetAddress(fromEmail));
			
			// build a list of one or more recipients
			for(String addr : toEmail) {
				msg.addRecipient(Message.RecipientType.TO,
				 new InternetAddress(addr));
			}			
		    
			// add subject and body
			msg.setSubject(subject);
		    msg.setText(body);
		    
		    // attempt the send
		    Transport.send(msg);
		    
		} 
		catch (AddressException e) {
		    log.info(e.toString());
		    errorMessage = "An Incorrect Recipient Email address was supplied.";
		} 
		catch (MessagingException e) {
			log.info(e.toString());
			errorMessage = "There was a problem with the email server.  Please contact the recipients directly.";
		}
		
		return errorMessage;
		
	}
	
	
	
	
}

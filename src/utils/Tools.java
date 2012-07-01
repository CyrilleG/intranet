package utils;



import intranet.AppSession;
import intranet.AppUser;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class Tools {

	private static AppUser user = null;
	private static AppSession session = null;
	
	public static void refreshUser()
	{
		//if (user != null)
		//	user.refreshRights();
		user = null;
		session = null;
	}
	
	public static HttpSession getHttpSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == allow create
	}
	
	public static AppSession getSession(){
		if(session != null)
			return session;
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	    	AppSession principal = (AppSession) authentication.getPrincipal();
	        return principal instanceof AppSession ? principal : null;
	    }
	    return null;
	}
	public static AppUser getUser(){
		
		if(user != null)
			return user;
		
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	    	AppUser principal = (AppUser) authentication.getDetails();
	        return principal instanceof AppUser ? principal : null;
	    }
	    return null;
	}
	
	public static void redirectUnless(boolean cond)
	{
		
	}
	
	public static boolean hasRight(String ident)
	{
		AppUser user = getUser();
		if(user != null)
			return user.hasRight(ident) || user.isAdmin();
		else 
			return false;
	}
	
	public static void log(String msg)
	{
		
	}
}

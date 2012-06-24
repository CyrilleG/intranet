package modules;

import intranet.AppSession;
import intranet.AppUser;
import intranet.UserInfo;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.pattern.LogEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

public class Module{ 
	
	private static Object last;
	private Module()
	{
		
	}
	public static boolean addUser(String login, String password, boolean enabled, Set<UserInfo> infos)
	{
		return false;
	}
	public static boolean addFilter(String name, String description, String type_name)
	{
		return false;
	}
	public static boolean addGroup(String name, String description)
	{
		return false;
	}
	public static boolean addRight(String ident, String name, String description)
	{
		return false;
	}
	public static boolean addData(String name)
	{
		return false;
	}
	public static Object getLastObject()
	{
		return last;
	}
	
	// example usage
	public static HttpSession getHttpSession() {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    return attr.getRequest().getSession(true); // true == allow create
	}
	
	public static AppSession getSession(){
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	    	AppSession principal = (AppSession) authentication.getPrincipal();
	        return principal instanceof AppSession ? principal : null;
	    }
	    return null;
	}
	public static AppUser getUser(){
		
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
	
	public boolean hasRight(String ident)
	{
		AppUser user = getUser();
		if(user != null)
			return user.hasRight(ident);
		else 
			return false;
	}
	
	public void log(String msg)
	{
		
	}

}

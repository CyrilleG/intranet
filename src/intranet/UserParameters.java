package intranet;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserParameters implements Authentication{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AppSession session;
	private AppUser user;
	private boolean isAuth = false;
	public AppSession getSession() throws Exception{
		
		if(session != null)
			return session;
		throw new Exception("Session not defined");
	}
	public void setSession(AppSession session) {
		if (this.session == null)
		{
			this.session = session;
			session.persist();
		}
	}
	public AppUser getUser() throws Exception{
		if(user != null)
			return user;
		throw new Exception("User not defined");
	}
	public void setUser(AppUser user) {
		if (this.user == null)
			this.user = user;
	}
	@Override
	public String getName() {
		return user.getLogin();
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (isAuthenticated())
			return user.getAllAuthorities();
		else
			return null;
	}
	@Override
	public Object getCredentials() {
		if (isAuthenticated())
			return user;
		else
			return null;
	}
	@Override
	public Object getDetails() {
		return user;
	}
	@Override
	public Object getPrincipal() {
		return session;
	}
	@Override
	public boolean isAuthenticated() {
		return isAuth;
	}
	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
		isAuth = arg0;
	}
}

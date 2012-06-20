package intranet;

public class UserParameters {
	private AppSession session;
	private AppUser user;
	private static UserParameters instance;
	
	
	private UserParameters()
	{
	}
	public static UserParameters getInstance()
	{
		if (instance == null)
			instance = new UserParameters();
		return instance;
	}
	
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
}

package intranet;

public class UserParameters {
	private Session session;
	private User user;
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
	
	public Session getSession() throws Exception{
		if(session != null)
			return session;
		throw new Exception("Session not defined");
	}
	public void setSession(Session session) {
		if (this.session == null)
		{
			this.session = session;
			session.persist();
		}
	}
	public User getUser() throws Exception{
		if(user != null)
			return user;
		throw new Exception("User not defined");
	}
	public void setUser(User user) {
		if (this.user == null)
			this.user = user;
	}
}

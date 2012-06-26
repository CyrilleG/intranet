package modules;

import intranet.UserInfo;

import java.util.Set;

import utils.Tools;

public class Module extends Tools{ 
	
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
	


}

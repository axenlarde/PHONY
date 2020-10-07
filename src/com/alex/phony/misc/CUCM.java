package com.alex.phony.misc;

import java.util.ArrayList;

/**
 * used to store CUCM data
 * @author Alexandre RATEL
 */
public class CUCM
	{
	/**
	 * Variables
	 */
	private String ip, login, password;
	private ArrayList<String> fullConversation;

	public CUCM(String ip, String login, String password)
		{
		super();
		this.ip = ip;
		this.login = login;
		this.password = password;
		fullConversation = new ArrayList<String>();
		}
	
	public String getInfo()
		{
		return ip;
		}

	public String getIp()
		{
		return ip;
		}

	public String getLogin()
		{
		return login;
		}

	public String getPassword()
		{
		return password;
		}

	public ArrayList<String> getFullConversation()
		{
		return fullConversation;
		}
	
	
	
	/*2020*//*Alexandre RATEL 8)*/
	}

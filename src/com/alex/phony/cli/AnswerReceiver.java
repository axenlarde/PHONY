
package com.alex.phony.cli;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.alex.phony.misc.CUCM;
import com.alex.phony.utils.UsefulMethod;
import com.alex.phony.utils.Variables;

/**
 * Class aims to manage Gateway input
 */
public class AnswerReceiver extends Thread
	{
	/**
	 * Variables
	 */
	private boolean stop;
	private CUCM cucm;
	private BufferedReader in;
	private ArrayList<String> exchange;//Used to store the entire conversation
	private int maxBuffer;
	
	
	public AnswerReceiver(CUCM cucm, BufferedReader in)
		{
		this.cucm = cucm;
		this.in = in;
		stop = false;
		exchange = new ArrayList<String>();
		maxBuffer = 15000;
		}
	
	
	public void run()
		{
		String row;
		
		try
			{
			boolean logExchange = Boolean.parseBoolean(UsefulMethod.getTargetOption("logcliexchange"));
			
			while (((row = in.readLine()) != null)&&(!stop))
		    	{
		    	//Store one element of conversation
		    	if(exchange.size()>maxBuffer)exchange.clear();
		    	exchange.add(row);
		    	cucm.getFullConversation().add(row);
		    	if(logExchange)Variables.getLogger().debug(cucm.getInfo()+" : #CLI# "+row);
		    	}
			/******
			 * Deletion of the conversation to
			 * avoid full memory error.
			 */
			exchange.clear();
			Variables.getLogger().debug(cucm.getInfo()+" : #CLI : End of the receiver thread");
			}
		catch(Exception exc)
			{
			Variables.getLogger().error(cucm.getInfo()+" : CLI : ERROR while listening");
			}
		}
	
	public void setStop(boolean stop)
		{
		this.stop = stop;
		}

	public ArrayList<String> getExchange()
		{
		return exchange;
		}
	
	
	
	/*2012*//*RATEL Alexandre 8)*/
	}

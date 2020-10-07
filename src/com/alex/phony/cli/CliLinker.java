package com.alex.phony.cli;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.alex.phony.action.Injector;
import com.alex.phony.misc.CUCM;
import com.alex.phony.utils.UsefulMethod;
import com.alex.phony.utils.Variables;
import com.alex.phony.utils.Variables.CliProtocol;


/**
 * This class will connect to the given device and send the given command
 *
 * @author Alexandre RATEL
 */
public class CliLinker
	{
	/**
	 * Variables
	 */
	private CUCM cucm;
	private CliProtocol protocol;
	private CliConnection connection;
	private Injector clii;
	private BufferedWriter out;
	private AnswerReceiver receiver;
	private int timeout;
	private String carrierReturn;
	
	public CliLinker(Injector clii)
		{
		super();
		this.clii = clii;
		this.cucm = clii.getCucm();
		this.protocol = CliProtocol.ssh;
		
		if(protocol.equals(CliProtocol.ssh))
			{
			this.carrierReturn = "\n";
			}
		else
			{
			this.carrierReturn = "\r\n";
			}
		
		try
			{
			this.timeout = Integer.parseInt(UsefulMethod.getTargetOption("cliconnectiontimeout"));
			}
		catch (Exception e)
			{
			Variables.getLogger().error(cucm.getInfo()+" : CLI : Unable to find timeout so we apply the default value");
			this.timeout = 10000;
			}
		}
	
	/**
	 * here we connect using the given ip
	 * @throws ConnectionException 
	 */
	public void connect(String ip) throws ConnectionException, Exception
		{
		try
			{
			if((connection == null) || (!connection.isConnected()))
				{
				connection = new CliConnection(cucm.getLogin(),
						cucm.getPassword(),
						ip,
						cucm,
						CliProtocol.ssh,
						timeout);
				
				connection.connect();
				out = connection.getOut();
				receiver = connection.getReceiver();
				}
			
			waitForAReturn();//We just wait for the prompt, This way we are sure we are ready to send command
			}
		catch (Exception e)
			{
			throw new ConnectionException("Failed to connect : "+e.getMessage());
			}
		}
	
	/**
	 * Here we connect using the default device ip
	 * @throws ConnectionException 
	 */
	public void connect() throws ConnectionException, Exception
		{
		connect(cucm.getIp());
		}
	
	public void disconnect()
		{
		if((connection != null) && (connection.isConnected()))connection.close();
		}
	
	public String waitFor(String s, int timeout) throws ConnectionException, Exception
		{
		int timer = 0;
		
		if(timeout == 0)Variables.getLogger().debug(cucm.getInfo()+" : CLI : Waiting for the word '"+s+"' forever");
		else Variables.getLogger().debug(cucm.getInfo()+" : CLI : Waiting for the word '"+s+"' during '"+timeout+"s'");
		
		boolean onlyOnce = true;
		
		while(true)
			{
			for(int i=0; i<receiver.getExchange().size(); i++)
				{
				//(?i) : Case insensitive
				if(Pattern.matches("(?i).*"+s+".*",receiver.getExchange().get(i)))
					{
					String SToReturn = receiver.getExchange().get(i);
					receiver.getExchange().clear();
					return SToReturn;
					}
				else if(onlyOnce)
					{
					/**
					 * We send a carriage return just to activate the connection
					 * and only once
					 */
					out.write(carrierReturn);
					out.flush();
					onlyOnce = false;
					}
				}
			
			clii.sleep(100);
			if(timeout == 0)
				{
				//0 is for infinite wait
				}
			else if(timer>(timeout*10))
				{
				Variables.getLogger().debug(cucm.getInfo()+" : CLI : We have been waiting too long for '"+s+"' so we keep going");
				break;
				}
			else
				{
				timer++;
				}
			}
		return null;
		}
	
	/**
	 * Simply wait for a return from the gateway
	 * @throws ConnectionException, Exception 
	 */
	public ArrayList<String> waitForAReturn(int howManyReturnedValue) throws ConnectionException, Exception
		{
		int timer = 0;
		
		//Variables.getLogger().debug(device.getInfo()+" : CLI : Waiting for a reply");
		
		while(true)
			{
			if(receiver.getExchange().size() > howManyReturnedValue)
				{
				ArrayList<String> list = new ArrayList<String>();
				for(int i=0; i<howManyReturnedValue; i++)
					{
					list.add(receiver.getExchange().get(i+1));//We start from 1 because 0 is what we have just sent
					}
				return list;
				}
			
			clii.sleep(100);
			if(timer>100)
				{
				Variables.getLogger().debug(cucm.getInfo()+" : CLI : We have been waiting too long so we keep going");
				break;
				}
			timer++;
			}
		return null;
		}
	
	public void waitForAReturn() throws ConnectionException, Exception
		{
		waitForAReturn(0);
		}
	
	
	public void write(String s) throws ConnectionException, Exception
		{
		receiver.getExchange().clear();
		out.write(s+carrierReturn);
		out.flush();
		waitForAReturn();
		}
	
	/**
	 * Write a command and then analyze the result
	 * if the result contains the given string we will write the given command
	 * otherwise will write the other command instead
	 * 
	 * The command pattern is the following :
	 * command to send:::string to compare:::write if the result contains the string to compare:::write if not
	 * The last parameters is optional. You can write only a:::b:::c
	 * @throws Exception 
	 * @throws ConnectionException 
	 */
	public void writeIf(String s) throws ConnectionException, Exception 
		{
		String[] cmdTab = s.split(":::");
		receiver.getExchange().clear();
		out.write(cmdTab[0]+carrierReturn);
		out.flush();
		
		waitForAReturn(1);
		
		boolean found = false;
		for(String str : receiver.getExchange())
			{
			if(str.toLowerCase().contains(cmdTab[1].toLowerCase()))found = true;
			}
		
		if(found)
			{
			write(cmdTab[2]);
			}
		else
			{
			if(cmdTab.length == 4)write(cmdTab[3]);
			}
		}
	
	public void execute(OneLine l) throws ConnectionException, Exception
		{
		switch(l.getType())
			{
			case connect:
				{
				connect(l.getCommand());
				break;
				}
			case disconnect:
				{
				disconnect();
				break;
				}
			case wait:
				{
				Variables.getLogger().debug(cucm.getInfo()+" : CLI : Waiting for "+l.getCommand()+" ms");
				clii.sleep(Long.parseLong(l.getCommand()));
				break;
				}
			case waitfor:
				{
				waitFor(l.getCommand(),10);
				break;
				}
			case waitforever:
				{
				waitFor(l.getCommand(),0);
				break;
				}
			case write:
				{
				write(l.getCommand());
				break;
				}
			case writeif:
				{
				writeIf(l.getCommand());
				break;
				}
			default:
				{
				write(l.getCommand());
				break;
				}
			}
		}

	public AnswerReceiver getReceiver()
		{
		return receiver;
		}
	
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}

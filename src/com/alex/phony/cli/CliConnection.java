package com.alex.phony.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.alex.phony.misc.CUCM;
import com.alex.phony.utils.Variables;
import com.alex.phony.utils.Variables.CliProtocol;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * Used to establish the connection to a device
 *
 * @author Alexandre RATEL
 */
public class CliConnection
	{
	/**
	 * Variables
	 */
	private String user,password,ip;
	private CUCM cucm;
	private BufferedWriter out;
	private BufferedReader in;
	private AnswerReceiver receiver;
	private CliProtocol protocol;
	private Channel SSHConnection;
	private int timeout;
	private Session SSHSession;
	
	public CliConnection(String user, String password, String ip, CUCM cucm, CliProtocol protocol, int timeout)
		{
		super();
		this.user = user;
		this.password = password;
		this.ip = ip;
		this.cucm = cucm;
		this.protocol = protocol;
		this.timeout = timeout;
		}

	/**
	 * To initialize the connection
	 */
	public void connect() throws Exception, ConnectionException
		{
		try
			{
			trySSH();
			}
		catch(Exception e)
			{
			Variables.getLogger().error("CLI : Failed to connect"+e.getMessage());
			throw new ConnectionException(e.getMessage());
			}
		
		receiver = new AnswerReceiver(cucm, in);
		receiver.start();
		}
	
	/**
	 * To initialize a SSH connection using both method
	 */
	private void trySSH() throws Exception, ConnectionException
		{
		try
			{
			Variables.getLogger().debug("CLI : Trying with jsch");
			initSSH();
			}
		catch (Exception ex)
			{
			Variables.getLogger().error("CLI : Failed to connect using jsch : "+ex.getMessage());
			throw new ConnectionException("Failed to connect : "+ex.getMessage());
			}
		}
	
	
	/**
	 * Initialize SSH connection using jsch
	 * @throws Exception 
	 */
	public void initSSH() throws Exception
		{
		Variables.getLogger().debug(cucm.getInfo()+" CLI : init SSH connection using JSCH");
		
		try
			{
			JSch jsch = new JSch();
			SSHSession = jsch.getSession(user, ip, 22);
			SSHSession.setPassword(password);
			SSHSession.setConfig("StrictHostKeyChecking", "no");
			
			//Connection to the ssh server with timeout
			SSHSession.connect(timeout);
			
			//Start the shell session
			SSHConnection = SSHSession.openChannel("shell");
			
			//Assign input and output Stream
			out = new BufferedWriter(new OutputStreamWriter(SSHConnection.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(SSHConnection.getInputStream()));
			
			((ChannelShell)SSHConnection).setPtyType("vt100");
			//((ChannelShell)SSHConnection).setPtyType("vt102");
			SSHConnection.connect();
			
			Variables.getLogger().debug(cucm.getInfo()+" : CLI : SSH connection initiated successfully");
			}
		catch (Exception e)
			{
			throw new ConnectionException(cucm.getInfo()+" CLI : Unable to connect using SSH : "+e.getMessage());
			}
		}
	
	public boolean isConnected()
		{
		return SSHConnection.isConnected();
		}
	
	/**
	 * To close the connection with the device
	 */
	public void close()
		{
		try
			{
			receiver.setStop(true);
			
			SSHConnection.disconnect();
			SSHSession.disconnect();
			
			Variables.getLogger().debug(cucm.getInfo()+" : CLI : Device disconnected successfully");
			}
		catch (Exception e)
			{
			Variables.getLogger().error(cucm.getInfo()+" : CLI : ERROR while closing connection : "+e.getMessage());
			}
		}

	public BufferedWriter getOut()
		{
		return out;
		}

	public BufferedReader getIn()
		{
		return in;
		}

	public AnswerReceiver getReceiver()
		{
		return receiver;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}

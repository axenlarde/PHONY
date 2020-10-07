package com.alex.phony.cli;

/**
 * Represent one cli command
 *
 * @author Alexandre RATEL
 */
public class OneLine
	{
	/**
	 * variables
	 */
	public enum cliType
		{
		connect,
		disconnect,
		write,
		wait,
		waitfor,
		waitforever,
		writeif,
		};
	
	private String command;
	private cliType type;
	
	public OneLine(String command, cliType type)
		{
		super();
		this.command = command;
		this.type = type;
		}
	
	public String getInfo()
		{
		return type.name()+" "+command;
		}

	public String getCommand()
		{
		return command;
		}

	public void setCommand(String command)
		{
		this.command = command;
		}

	public cliType getType()
		{
		return type;
		}

	public void setType(cliType type)
		{
		this.type = type;
		}
	
	
	
	
	/*2019*//*RATEL Alexandre 8)*/
	}

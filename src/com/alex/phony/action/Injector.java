package com.alex.phony.action;

import com.alex.phony.cli.CliLinker;
import com.alex.phony.misc.CUCM;
import com.alex.phony.utils.Variables;

/**
 * To inject the cli for one device
 *
 * @author Alexandre RATEL
 */
public abstract class Injector extends Thread implements InjectorImpl
	{
	/**
	 * Variables
	 */
	protected CUCM cucm;
	protected CliLinker clil;
	
	public Injector(CUCM cucm)
		{
		super();
		this.cucm = cucm;
		}
	
	public void run()
		{
		try
			{
			/**
			 * Here we send the cli command
			 */
			clil = new CliLinker(this);
			clil.connect();//First we initialize the connection
			Variables.getLogger().debug(cucm.getInfo()+" : Connected successfully, ready for cli injection");
			
			exec();
			
			clil.disconnect();//Last we disconnect
			Variables.getLogger().debug(cucm.getInfo()+" : Disconnected successfully");
			}
		catch (Exception e)
			{
			Variables.getLogger().error(cucm.getInfo()+" : CLI : Critical ERROR : "+e.getMessage());
			}
		}

	public CUCM getCucm()
		{
		return cucm;
		}

	/*2020*//*RATEL Alexandre 8)*/
	}

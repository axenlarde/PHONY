package com.alex.phony.cli;

import java.util.ArrayList;

import com.alex.phony.action.Injector;
import com.alex.phony.misc.CUCM;
import com.alex.phony.utils.Variables;



/**
 * To inject the cli for one device
 *
 * @author Alexandre RATEL
 */
public class CliInjector extends Injector
	{
	/**
	 * Variables
	 */
	private ArrayList<OneLine> todo;
	
	public CliInjector(CUCM cucm, ArrayList<OneLine> todo)
		{
		super(cucm);
		this.todo = todo;
		}
	
	public void exec() throws Exception
		{
		for(OneLine l : todo)
			{
			try
				{
				clil.execute(l);
				this.sleep(50);
				}
			catch (ConnectionException ce)
				{
				throw new ConnectionException(ce);
				}
			catch (Exception e)
				{
				Variables.getLogger().error(cucm.getInfo()+" : CLI : ERROR whith command : "+l.getInfo(), e);
				}
			}
		}

	public ArrayList<OneLine> getTodo()
		{
		return todo;
		}

	/*2020*//*RATEL Alexandre 8)*/
	}

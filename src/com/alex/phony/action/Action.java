package com.alex.phony.action;

import java.util.ArrayList;

import com.alex.phony.cli.CliInjector;
import com.alex.phony.cli.OneLine;
import com.alex.phony.cli.OneLine.cliType;
import com.alex.phony.misc.CUCM;
import com.alex.phony.misc.MiscTools;
import com.alex.phony.misc.Phone;
import com.alex.phony.utils.Variables;

/**
 * Main processs class
 * @author Alexandre RATEL
 *
 */
public class Action extends Thread
	{
	/**
	 * Variables
	 */
	InjectorManager IM;
	
	public Action()
		{
		IM = new InjectorManager();
		start();
		}
	
	public void run()
		{
		try
			{
			/**
			 * 1 : Contact CUCMs and get the phone list
			 */
			for(CUCM cucm : Variables.getCucmList())
				{
				ArrayList<OneLine> todo = new ArrayList<OneLine>();
				todo.add(new OneLine("admin", cliType.waitforever));
				todo.add(new OneLine("show risdb query phone", cliType.write));
				todo.add(new OneLine("Total count", cliType.waitforever));
				IM.getInjectorList().add(new CliInjector(cucm, todo));
				}
			
			IM.start();
			//We wait for the injector to end
			while(true)
				{
				if(IM.isAlive())this.sleep(100);
				else break;
				}
			
			for(CUCM cucm : Variables.getCucmList())
				{
				Variables.getLogger().debug(cucm.getInfo()+" line fetched : "+cucm.getFullConversation().size());
				}
			
			/**
			 * 2 : Parse the phone list
			 */
			//Purging unwanted data
			for(CUCM cucm : Variables.getCucmList())
				{
				Variables.getLogger().debug(cucm.getInfo()+" : Purging unwanted data");
				Variables.getLogger().debug(cucm.getInfo()+" : Line before : "+cucm.getFullConversation().size());
				MiscTools.purgeUnwantedData(cucm.getFullConversation());
				Variables.getLogger().debug(cucm.getInfo()+" : Line after : "+cucm.getFullConversation().size());
				}
			
			//Parsing phone data
			for(CUCM cucm : Variables.getCucmList())
				{
				Variables.getLogger().debug(cucm.getInfo()+" : Parsing phone list");
				Variables.getPhoneList().addAll(MiscTools.parsePhoneList(cucm, cucm.getFullConversation()));
				Variables.getLogger().debug(cucm.getInfo()+" : Phone list parsed, new Phone list size : "+Variables.getPhoneList().size());
				}
			
			//Temp
			for(Phone p : Variables.getPhoneList())
				{
				Variables.getLogger().debug("-"+p.getInfo());
				}
			
			//temp
			
			/**
			 * 3 : Write down the file
			 */
			MiscTools.writePhoneListToCSV(Variables.getPhoneList());
			
			Variables.getLogger().debug(Variables.getSoftwareName()+" : Ends");
			}
		catch (Exception e)
			{
			Variables.getLogger().error(e);
			}
		}
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}

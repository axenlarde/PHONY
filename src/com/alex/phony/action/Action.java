package com.alex.phony.action;

import java.io.File;
import java.util.ArrayList;

import com.alex.phony.cli.CliInjector;
import com.alex.phony.cli.OneLine;
import com.alex.phony.cli.OneLine.cliType;
import com.alex.phony.misc.CUCM;
import com.alex.phony.misc.MiscTools;
import com.alex.phony.misc.Phone;
import com.alex.phony.misc.Phone.PhoneStatus;
import com.alex.phony.utils.UsefulMethod;
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
			String splitter = UsefulMethod.getTargetOption("inputcsvsplitter");
			
			for(CUCM cucm : Variables.getCucmList())
				{
				Variables.getLogger().debug(cucm.getInfo()+" : Parsing phone list");
				Variables.getPhoneList().addAll(MiscTools.parsePhoneList(cucm.getIp(), cucm.getFullConversation(), splitter));
				Variables.getLogger().debug(cucm.getInfo()+" : Phone list parsed, new Phone list size : "+Variables.getPhoneList().size());
				}
			
			//We keep only registered phones
			MiscTools.filterRegisteredPhone(Variables.getPhoneList());
			Variables.getLogger().debug("Phone list filtered with registered phone, new Phone list size : "+Variables.getPhoneList().size());
			
			/**
			 * 3 : Write down the file
			 * 
			 * Here we will also check if a previous version of the file exists
			 * If yes, we will read it and compare the new value to it
			 * If not, we will just write a new file
			 */
			try
				{
				//Checking for existing file
				File f = new File(Variables.getMainDirectory()+"/"+Variables.getOutputFileName());
				if(f.exists())
					{
					Variables.getLogger().debug("Output file found so we update the existing one");
					ArrayList<String> phoneList = MiscTools.readPhoneFile(f);
					splitter = UsefulMethod.getTargetOption("outputcsvsplitter");
					ArrayList<Phone> existingPhoneList = MiscTools.parsePhoneList(null, phoneList, splitter);
					
					//Comparison
					for(Phone ep : existingPhoneList)
						{
						for(Phone cp : Variables.getPhoneList())
							{
							if(ep.getDeviceName().equals(cp.getDeviceName()))
								{
								ep.setNewPhoneStatus(PhoneStatus.Registered);
								break;
								}
							}
						}
					
					//Finally we write down the new file with the updated status
					MiscTools.writePhoneListToCSV(existingPhoneList, true);
					}
				else
					{
					Variables.getLogger().debug("Output file not found so we write a new one");
					MiscTools.writePhoneListToCSV(Variables.getPhoneList(), false);
					}
				}
			catch (Exception e)
				{
				Variables.getLogger().error("ERROR while comparing files : "+e.getMessage(),e);
				/**
				 * In case of error during the comparison process we write down a new file
				 * So at least we get the current scan result
				 */
				MiscTools.writePhoneListToCSV(Variables.getPhoneList(), false);
				}
			
			Variables.getLogger().debug(Variables.getSoftwareName()+" : Ends");
			}
		catch (Exception e)
			{
			Variables.getLogger().error(e);
			}
		}
	
	
	/*2020*//*RATEL Alexandre 8)*/
	}

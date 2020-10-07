package com.alex.phony.misc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.alex.phony.misc.Phone.PhoneStatus;
import com.alex.phony.utils.UsefulMethod;
import com.alex.phony.utils.Variables;


/**
 * @author Alexandre RATEL
 *
 * Miscellaneous static methods
 */
public class MiscTools
	{
	
	/**
	 * Write the overall result to a csv file
	 */
	
	public static void writePhoneListToCSV(ArrayList<Phone> phoneList)
		{
		try
			{
			Variables.getLogger().debug("Writing the phone list to a file");
			String splitter = UsefulMethod.getTargetOption("outputcsvsplitter");
			String cr = "\r\n";
			BufferedWriter csvBuffer = new BufferedWriter(new FileWriter(new File(Variables.getMainDirectory()+"/"+Variables.getOutputFileName()), false));
			
			//FirstLine
			csvBuffer.write("CUCM"+splitter+"DeviceName"+splitter+"Descr"+splitter+"Ipaddr"+splitter+"MACaddr"+splitter+"RegStatus"+splitter+"PhoneProtocol"+splitter+"DeviceModel"+splitter+"username"+splitter+"LoadId"+splitter+"ActiveLoadId"+splitter+"InactiveLoadId"+cr);
			
			for(Phone p : phoneList)
				{
				if(p.getPhoneStatus().equals(PhoneStatus.Registered))
					{
					csvBuffer.write(p.getCucm().getIp()+splitter+
							p.getDeviceName()+splitter+
							p.getDescr()+splitter+
							p.getIpaddr()+splitter+
							p.getMACaddr()+splitter+
							p.getPhoneStatus().name()+splitter+
							p.getPhoneProtocol()+splitter+
							p.getDeviceModel()+splitter+
							p.getUsername()+splitter+
							p.getLoadId()+splitter+
							p.getActiveLoadId()+splitter+
							p.getInactiveLoadId()+splitter+cr);
					}
				}
			
			csvBuffer.flush();
			csvBuffer.close();
			Variables.getLogger().debug("Writing phone list : Done !");
			}
		catch (Exception e)
			{
			Variables.getLogger().error("ERROR while writing overall result to CSV : "+e.getMessage(),e);
			}
		}
	
	/**
	 * Used to remove the unwanted data
	 * @return
	 */
	public static void purgeUnwantedData(ArrayList<String> rawList) throws Exception
		{
		//We remove the prefix
		int end = 0;
		for(int i=0; i<rawList.size(); i++)
			{
			if(rawList.get(i).startsWith("DeviceName"))
				{
				end = i;
				break;
				}
			}
		
		for(int i=0 ; i<end; i++)
			{
			rawList.remove(0);
			}
		
		//We remove the suffix
		int start = 0;
		for(int i=0; i<rawList.size(); i++)
			{
			if(rawList.get(i).equals(""))
				{
				start = i;
				break;
				}
			}
		
		end = rawList.size();
		for(int i=start; i<end; i++)
			{
			rawList.remove(start);
			}
		}
	
	/**
	 * Used to parse the raw data and get the phone list
	 * @param stringList
	 * @return
	 * @throws Exception 
	 */
	public static ArrayList<Phone> parsePhoneList(CUCM cucm, ArrayList<String> stringList) throws Exception
		{
		ArrayList<Phone> phoneList = new ArrayList<Phone>();
		String header = stringList.get(0);
		String splitter = UsefulMethod.getTargetOption("inputcsvsplitter");
		String[] hTab = header.split(splitter);
		
		for(int i=1;i<stringList.size(); i++)
			{
			try
				{
				String[] pTab = stringList.get(i).split(splitter);
				
				phoneList.add(new Phone(cucm,
						pTab[getDataIndexFromHeader(hTab, "deviceName")].trim(),
						pTab[getDataIndexFromHeader(hTab, "descr")].trim(),
						pTab[getDataIndexFromHeader(hTab, "ipaddr")].trim(),
						pTab[getDataIndexFromHeader(hTab, "mACaddr")].trim(),
						pTab[getDataIndexFromHeader(hTab, "regStatus")].trim(),
						pTab[getDataIndexFromHeader(hTab, "phoneProtocol")].trim(),
						pTab[getDataIndexFromHeader(hTab, "deviceModel")].trim(),
						pTab[getDataIndexFromHeader(hTab, "username")].trim(),
						pTab[getDataIndexFromHeader(hTab, "loadId")].trim(),
						pTab[getDataIndexFromHeader(hTab, "activeLoadId")].trim(),
						pTab[getDataIndexFromHeader(hTab, "inactiveLoadId")].trim()));
				}
			catch (Exception e)
				{
				Variables.getLogger().error("ERROR parsing phone "+i+" : "+stringList.get(i)+" : "+e.getMessage(),e);
				}
			}
		
		return phoneList;
		}
	
	/**
	 * To get the column index from the header
	 * @param header
	 * @param dataName
	 * @return
	 * @throws Exception 
	 */
	private static int getDataIndexFromHeader(String[] header, String columnName) throws Exception
		{
		for(int i=0; i<header.length; i++)
			{
			if(header[i].toLowerCase().trim().equals(columnName.toLowerCase()))return i;
			}
		
		throw new Exception("Column name not found in the header : "+columnName);
		}
	
	public static PhoneStatus getPhoneStatus(String status)
		{
		for(PhoneStatus ps : PhoneStatus.values())
			{
			if(ps.name().toLowerCase().startsWith(status.trim().replaceAll("-", "").toLowerCase()))return ps;
			}
		
		return PhoneStatus.Unknown;
		}
	
	/**
	 * return the phone model name based on the phone model ID
	 * @param PMID
	 * @return
	 */
	public static String getPhoneModel(String PMID)
		{
		for(String[] t : Variables.getDevicemodel())
			{
			if(t[0].equals(PMID))return t[1];
			}
		return "Model not found";
		}
	
	
	/*2020*//*Alexandre RATEL 8)*/
	}

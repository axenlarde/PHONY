package com.alex.phony.misc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
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
	
	public static void writePhoneListToCSV(ArrayList<Phone> phoneList, boolean update)
		{
		try
			{
			Variables.getLogger().debug("Writing the phone list to a file");
			String splitter = UsefulMethod.getTargetOption("outputcsvsplitter");
			String cr = "\r\n";
			BufferedWriter csvBuffer = new BufferedWriter(new FileWriter(new File(Variables.getMainDirectory()+"/"+Variables.getOutputFileName()), false));
			
			
			//FirstLine
			String firstLine = "CUCM"+splitter+"DeviceName"+splitter+"Descr"+splitter+"Ipaddr"+splitter+"MACaddr"+splitter+"RegStatus"+splitter+"PhoneProtocol"+splitter+"DeviceModel"+splitter+"username"+splitter+"LoadId"+splitter+"ActiveLoadId"+splitter+"InactiveLoadId";
			firstLine += (update)?splitter+"NewStatus"+cr:cr;
			csvBuffer.write(firstLine);
			
			for(Phone p : phoneList)
				{
				String content = p.getCucmIP()+splitter+
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
						p.getInactiveLoadId();
				
				content += (update)?splitter+p.getNewPhoneStatus()+cr:cr;
				csvBuffer.write(content);
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
	public static ArrayList<Phone> parsePhoneList(String cucmIP, ArrayList<String> stringList, String splitter) throws Exception
		{
		ArrayList<Phone> phoneList = new ArrayList<Phone>();
		String header = stringList.get(0);
		String[] hTab = header.split(splitter, -1);
		
		for(int i=1;i<stringList.size(); i++)
			{
			try
				{
				String[] pTab = stringList.get(i).split(splitter, -1);
				
				phoneList.add(new Phone((cucmIP == null)?pTab[getDataIndexFromHeader(hTab, "CUCM")].trim():cucmIP,
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
		return PMID;
		}
	
	/**
	 * read the given file and convert it to an arraylist of string
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> readPhoneFile(File f) throws Exception
		{
		ArrayList<String> phoneList = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(f);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		
		String line;
		
		while((line = buffer.readLine()) != null)
			{
			phoneList.add(line);
			}
		
		buffer.close();
		fis.close();
		
		return phoneList;
		}
	
	/**
	 * Conventional method to filter registered phones
	 * @param list
	 * @return
	 */
	public static ArrayList<Phone> keepOnlyRegisteredPhones(ArrayList<Phone> list)
		{
		ArrayList<Phone> filteredList = new ArrayList<Phone>();
		
		for(Phone p : list)
			{
			if((p.getPhoneStatus().equals(PhoneStatus.Registered)) || (p.getPhoneStatus().equals(PhoneStatus.ParRegistered)))
				{
				filteredList.add(p);
				}
			}
		return filteredList;
		}
	
	/**
	 * Recursive method to filter only registered phones
	 * @param list
	 */
	public static void filterRegisteredPhone(ArrayList<Phone> list)
		{
		for(Phone p : list)
			{
			if((p.getPhoneStatus().equals(PhoneStatus.Registered)) || (p.getPhoneStatus().equals(PhoneStatus.ParRegistered)))
				{
				//Nothing
				}
			else
				{
				list.remove(p);
				filterRegisteredPhone(list);
				break;
				}
			}
		}
	
	
	/*2020*//*Alexandre RATEL 8)*/
	}

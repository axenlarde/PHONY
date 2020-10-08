package com.alex.phony.misc;

import com.alex.phony.misc.Phone.PhoneStatus;

/**
 * 
 * @author Alexandre RATEL
 *
 */
public class Phone
	{
	/**
	 * Variables
	 */
	public enum PhoneStatus
		{
		Registered,
		ParRegistered,
		Unregistered,
		Rejected,
		Unknown
		};

	private PhoneStatus phoneStatus, newPhoneStatus;
	private String DeviceName, Descr, Ipaddr, MACaddr, RegStatus, PhoneProtocol, DeviceModel, username, LoadId, ActiveLoadId, InactiveLoadId, cucmIP;

	public Phone(String cucmIP, String deviceName, String descr, String ipaddr, String mACaddr, String regStatus, String phoneProtocol,
			String deviceModel, String username, String loadId, String activeLoadId, String inactiveLoadId)
		{
		super();
		this.cucmIP = cucmIP;
		DeviceName = deviceName;
		Descr = descr;
		Ipaddr = ipaddr;
		MACaddr = mACaddr;
		RegStatus = regStatus;
		phoneStatus = MiscTools.getPhoneStatus(RegStatus);
		newPhoneStatus = PhoneStatus.Unregistered;
		PhoneProtocol = phoneProtocol;
		DeviceModel = MiscTools.getPhoneModel(deviceModel);
		this.username = username;
		LoadId = loadId;
		ActiveLoadId = activeLoadId;
		InactiveLoadId = inactiveLoadId;
		}
	
	public String getInfo()
		{
		return DeviceName+" "+Ipaddr+" "+phoneStatus;
		}

	public String getDeviceName()
		{
		return DeviceName;
		}

	public String getDescr()
		{
		return Descr;
		}

	public String getIpaddr()
		{
		return Ipaddr;
		}

	public String getMACaddr()
		{
		return MACaddr;
		}

	public String getRegStatus()
		{
		return RegStatus;
		}

	public String getPhoneProtocol()
		{
		return PhoneProtocol;
		}

	public String getDeviceModel()
		{
		return DeviceModel;
		}

	public String getUsername()
		{
		return username;
		}

	public String getLoadId()
		{
		return LoadId;
		}

	public String getActiveLoadId()
		{
		return ActiveLoadId;
		}

	public String getInactiveLoadId()
		{
		return InactiveLoadId;
		}

	public PhoneStatus getPhoneStatus()
		{
		return phoneStatus;
		}

	public String getCucmIP()
		{
		return cucmIP;
		}

	public PhoneStatus getNewPhoneStatus()
		{
		return newPhoneStatus;
		}

	public void setNewPhoneStatus(PhoneStatus newPhoneStatus)
		{
		this.newPhoneStatus = newPhoneStatus;
		}
	
	
	
	
	/*2020*//*Alexandre RATEL 8)*/
	}

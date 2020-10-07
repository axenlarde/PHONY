package com.alex.phony.utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.alex.phony.misc.CUCM;
import com.alex.phony.misc.Phone;


/**********************************
 * Used to store static variables
 * 
 * @author RATEL Alexandre
 **********************************/
public class Variables
	{
	/**
	 * Variables
	 */
	public enum CliProtocol
		{
		ssh,
		telnet,
		auto
		};
	
	//Misc
	private static String softwareName;
	private static String softwareVersion;
	private static Logger logger;
	private static ArrayList<String[][]> tabConfig;
	private static String mainDirectory;
	private static String configFileName;
	private static String cucmListFileName;
	private static String outputFileName;
	private static ArrayList<CUCM> cucmList;
	private static ArrayList<Phone> phoneList;
	private static final String[][] deviceModel = new String[][]
		{
			{"15","EMCC Base Phone"},
			{"20","SCCP Phone"},
			{"30","Analog Access"},
			{"40","Digital Access"},
			{"42","Digital Access+"},
			{"43","Digital Access WS-X6608"},
			{"47","Analog Access WS-X6624"},
			{"50","Conference Bridge"},
			{"51","Conference Bridge WS-X6608"},
			{"62","H.323 Gateway"},
			{"70","Music On Hold"},
			{"71","Device Pilot"},
			{"73","CTI Route Point"},
			{"80","Voice Mail Port"},
			{"90","Route List"},
			{"100","Load Simulator"},
			{"110","Media Termination Point"},
			{"111","Media Termination Point Hardware"},
			{"120","MGCP Station"},
			{"121","MGCP Trunk"},
			{"122","GateKeeper"},
			{"125","Trunk"},
			{"126","Tone Announcement Player"},
			{"254","Unknown MGCP Gateway"},
			{"255","Unknown"},
			{"52","Cisco IOS Conference Bridge (HDV2)"},
			{"53","Cisco Conference Bridge (WS-SVC-CMM)"},
			{"83","Cisco IOS Software Media Termination Point (HDV2)"},
			{"84","Cisco Media Server (WS-SVC-CMM-MS)"},
			{"112","Cisco IOS Media Termination Point (HDV2)"},
			{"113","Cisco Media Termination Point (WS-SVC-CMM)"},
			{"131","SIP Trunk"},
			{"132","SIP Gateway"},
			{"133","WSM Trunk"},
			{"85","Cisco Video Conference Bridge (IPVC-35xx)"},
			{"522","BlackBerry MVS VoWifi"},
			{"640","Usage Profile"},
			{"598","Ascom IP-DECT Device"},
			{"599","Cisco TelePresence Exchange System"},
			{"36041","Cisco TelePresence Conductor"},
			{"36219","Interactive Voice Response"},
			{"61","H.323 Phone"},
			{"72","CTI Port"},
			{"134","Remote Destination Profile"},
			{"30027","Analog Phone"},
			{"30028","ISDN BRI Phone"},
			{"2","Cisco 12 SP+"},
			{"3","Cisco 12 SP"},
			{"4","Cisco 12 S"},
			{"1","Cisco 30 SP+"},
			{"5","Cisco 30 VIP"},
			{"9","Cisco 7935"},
			{"6","Cisco 7910"},
			{"7","Cisco 7960"},
			{"8","Cisco 7940"},
			{"10","Cisco VGC Phone"},
			{"11","Cisco VGC Virtual Phone"},
			{"48","VGC Gateway"},
			{"12","Cisco ATA 186"},
			{"124","7914 14-Button Line Expansion Module"},
			{"336","Third-party SIP Device (Basic)"},
			{"374","Third-party SIP Device (Advanced)"},
			{"115","Cisco 7941"},
			{"119","Cisco 7971"},
			{"20000","Cisco 7905"},
			{"302","Cisco 7985"},
			{"307","Cisco 7911"},
			{"308","Cisco 7961G-GE"},
			{"309","Cisco 7941G-GE"},
			{"335","Motorola CN622"},
			{"348","Cisco 7931"},
			{"358","Cisco Unified Personal Communicator"},
			{"365","Cisco 7921"},
			{"369","Cisco 7906"},
			{"375","Cisco TelePresence"},
			{"376","Nokia S60"},
			{"30002","Cisco 7920"},
			{"30006","Cisco 7970"},
			{"30007","Cisco 7912"},
			{"30008","Cisco 7902"},
			{"30016","Cisco IP Communicator"},
			{"30018","Cisco 7961"},
			{"30019","Cisco 7936"},
			{"30032","SCCP gateway virtual phone"},
			{"30035","IP-STE"},
			{"404","Cisco 7962"},
			{"412","Cisco 3951"},
			{"431","Cisco 7937"},
			{"434","Cisco 7942"},
			{"435","Cisco 7945"},
			{"436","Cisco 7965"},
			{"437","Cisco 7975"},
			{"446","Cisco 3911"},
			{"550","Cisco ATA 187"},
			{"631","Third-party AS-SIP Endpoint"},
			{"36049","BEKEM 36-Button Line Expansion Module"},
			{"580","Cisco E20"},
			{"684","Cisco 8851"},
			{"627","Cisco TelePresence MX300"},
			{"616","Cisco TelePresence Profile 65 Dual (C90)"},
			{"87","Cisco IOS Guaranteed Audio Video Conference Bridge"},
			{"596","Cisco TelePresence TX1310-65"},
			{"36225","Cisco 8865"},
			{"611","Cisco TelePresence Profile 42 (C60)"},
			{"610","Cisco TelePresence Profile 42 (C20)"},
			{"612","Cisco TelePresence Profile 52 (C40)"},
			{"497","Cisco 6961"},
			{"36217","Cisco 8811"},
			{"88","Cisco IOS Homogeneous Video Conference Bridge"},
			{"584","Cisco TelePresence EX90"},
			{"468","Cisco Unified Mobile Communicator"},
			{"36232","Cisco 8851NR"},
			{"36207","Cisco TelePresence MX700"},
			{"648","Cisco Unified Communications for RTX"},
			{"647","Cisco DX650"},
			{"36224","Cisco 8845"},
			{"642","Carrier-integrated Mobile"},
			{"590","Cisco TelePresence 500-32"},
			{"607","Cisco TelePresence Codec C60"},
			{"548","Cisco 6911"},
			{"36239","Cisco TelePresence DX80"},
			{"228","7915 24-Button Line Expansion Module"},
			{"619","Cisco TelePresence TX9000"},
			{"540","Cisco 8961"},
			{"659","Cisco 8831"},
			{"505","Cisco TelePresence 1300-65"},
			{"623","Cisco 7861"},
			{"622","Cisco 7841"},
			{"496","Cisco 6941"},
			{"503","Cisco Unified Client Services Framework"},
			{"36241","Cisco TelePresence DX70"},
			{"36210","Cisco TelePresence IX5000"},
			{"557","Cisco TelePresence 200"},
			{"36227","Cisco TelePresence MX800 Dual"},
			{"633","Cisco TelePresence Profile 42 (C40)"},
			{"478","Cisco TelePresence 1000"},
			{"230","7916 24-Button Line Expansion Module"},
			{"229","7916 12-Button Line Expansion Module"},
			{"586","Cisco 8941"},
			{"628","IMS-integrated Mobile (Basic)"},
			{"635","CTI Remote Device"},
			{"588","Generic Desktop Video Endpoint"},
			{"632","Cisco Cius SP"},
			{"591","Cisco TelePresence 1300-47"},
			{"683","Cisco 8841"},
			{"36043","Cisco DX70"},
			{"36235","Cisco Spark Remote Device"},
			{"232","CKEM 36-Button Line Expansion Module"},
			{"690","Cisco TelePresence MX300 G2"},
			{"36216","Cisco 8821"},
			{"620","Cisco TelePresence TX9200"},
			{"621","Cisco 7821"},
			{"634","Cisco VXC 6215"},
			{"597","Cisco TelePresence MCU"},
			{"626","Cisco TelePresence SX20"},
			{"562","Cisco Dual Mode for iPhone"},
			{"558","Cisco TelePresence 400"},
			{"479","Cisco TelePresence 3000"},
			{"564","Cisco 6945"},
			{"36208","Cisco TelePresence MX800"},
			{"609","Cisco TelePresence Quick Set C20"},
			{"36213","Cisco 7811"},
			{"606","Cisco TelePresence Codec C90"},
			{"593","Cisco Cius"},
			{"577","Cisco 7926"},
			{"585","Cisco 8945"},
			{"608","Cisco TelePresence Codec C40"},
			{"537","Cisco 9951"},
			{"594","VKEM 36-Button Line Expansion Module"},
			{"592","Cisco 3905"},
			{"582","Generic Single Screen Room System"},
			{"36042","Cisco DX80"},
			{"493","Cisco 9971"},
			{"688","Cisco TelePresence SX80"},
			{"604","Cisco TelePresence EX60"},
			{"227","7915 12-Button Line Expansion Module"},
			{"681","Cisco ATA 190"},
			{"253","SPA8800"},
			{"495","Cisco 6921"},
			{"685","Cisco 8861"},
			{"547","Cisco 6901"},
			{"480","Cisco TelePresence 3200"},
			{"613","Cisco TelePresence Profile 52 (C60)"},
			{"484","Cisco 7925"},
			{"583","Generic Multiple Screen Room System"},
			{"617","Cisco TelePresence MX200"},
			{"481","Cisco TelePresence 500-37"},
			{"652","Cisco Jabber for Tablet"},
			{"575","Cisco Dual Mode for Android"},
			{"614","Cisco TelePresence Profile 52 Dual (C60)"},
			{"682","Cisco TelePresence SX10"},
			{"521","Transnova S3"},
			{"615","Cisco TelePresence Profile 65 (C60)"},
			{"689","Cisco TelePresence MX200 G2"},
			{"645","Universal Device Template"},
			{"86","Cisco IOS Heterogeneous Video Conference Bridge"},
			{"520","Cisco TelePresence 1100"},
			{"36257","CP-8800-Audio 28-Button Key Expansion Module"},
			{"36256","CP-8800-Video 28-Button Key Expansion Module"},
			{"36297","Cisco Webex Room 70 Dual G2"},
			{"36265","Cisco Spark Room 70 Dual"},
			{"36292","Cisco Webex Room Kit Pro"},
			{"36259","Cisco Spark Room 70 Single"},
			{"36296","Cisco Webex Room 70 Single G2"},
			{"36260","Cisco 8832NR"},
			{"36262","Cisco ATA 191"},
			{"36255","Cisco Spark Room Kit Plus"},
			{"36247","Cisco 7832"},
			{"36248","Cisco 8865NR"},
			{"36254","Cisco Spark Room 55"},
			{"36251","Cisco Spark Room Kit"},
			{"36295","Cisco Webex Room 55 Dual"},
			{"36258","Cisco 8832"},
		};
	
    /**************
     * Constructor
     **************/
	public Variables()
		{
		mainDirectory = ".";
		configFileName = "configFile.xml";
		cucmListFileName = "cucmList.txt";
		outputFileName = "phoneList.csv";
		phoneList = new ArrayList<Phone>();
		}

	public static String getSoftwareName()
		{
		return softwareName;
		}

	public static void setSoftwareName(String softwareName)
		{
		Variables.softwareName = softwareName;
		}

	public static String getSoftwareVersion()
		{
		return softwareVersion;
		}

	public static void setSoftwareVersion(String softwareVersion)
		{
		Variables.softwareVersion = softwareVersion;
		}

	public static Logger getLogger()
		{
		return logger;
		}

	public static void setLogger(Logger logger)
		{
		Variables.logger = logger;
		}

	public static ArrayList<String[][]> getTabConfig()
		{
		return tabConfig;
		}

	public static void setTabConfig(ArrayList<String[][]> tabConfig)
		{
		Variables.tabConfig = tabConfig;
		}

	public static String getMainDirectory()
		{
		return mainDirectory;
		}

	public static void setMainDirectory(String mainDirectory)
		{
		Variables.mainDirectory = mainDirectory;
		}

	public static String getConfigFileName()
		{
		return configFileName;
		}

	public static void setConfigFileName(String configFileName)
		{
		Variables.configFileName = configFileName;
		}

	public static String getCucmListFileName()
		{
		return cucmListFileName;
		}

	public static void setCucmListFileName(String cucmListFileName)
		{
		Variables.cucmListFileName = cucmListFileName;
		}

	public static String getOutputFileName()
		{
		return outputFileName;
		}

	public static void setOutputFileName(String outputFileName)
		{
		Variables.outputFileName = outputFileName;
		}

	public static ArrayList<CUCM> getCucmList()
		{
		return cucmList;
		}

	public static void setCucmList(ArrayList<CUCM> cucmList)
		{
		Variables.cucmList = cucmList;
		}

	public static ArrayList<Phone> getPhoneList()
		{
		return phoneList;
		}

	public static void setPhoneList(ArrayList<Phone> phoneList)
		{
		Variables.phoneList = phoneList;
		}

	public static String[][] getDevicemodel()
		{
		return deviceModel;
		}
	
	/*2020*//*RATEL Alexandre 8)*/
	}


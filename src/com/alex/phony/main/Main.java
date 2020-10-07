package com.alex.phony.main;


import org.apache.log4j.Level;

import com.alex.phony.action.Action;
import com.alex.phony.utils.InitLogging;
import com.alex.phony.utils.UsefulMethod;
import com.alex.phony.utils.Variables;


/**
 * Perceler main class
 *
 * @author Alexandre RATEL
 */
public class Main
	{
	
	public Main()
		{
		//Set the software name
		Variables.setSoftwareName("PHONY");
		//Set the software version
		Variables.setSoftwareVersion("1.0");
		
		/****************
		 * Initialization of the logging
		 */
		Variables.setLogger(InitLogging.init(Variables.getSoftwareName()+"_LogFile.txt"));
		Variables.getLogger().info("\r\n");
		Variables.getLogger().info("#### Entering application");
		Variables.getLogger().info("## Welcome to : "+Variables.getSoftwareName()+" version "+Variables.getSoftwareVersion());
		Variables.getLogger().info("## Author : RATEL Alexandre : 2020");
		/*******/
		
		/******
		 * Initialization of the variables
		 */
		new Variables();
		/************/
		
		/**********
		 * We check if the java version is compatible
		 */
		UsefulMethod.checkJavaVersion();
		/***************/
		
		/**********************
		 * Reading of the configuration files
		 */
		try
			{
			/**
			 * Config file reading
			 */
			Variables.setTabConfig(UsefulMethod.readMainConfigFile(Variables.getConfigFileName()));
			
			/**
			 * Database file reading
			 */
			UsefulMethod.initDatabase();
			}
		catch(Exception exc)
			{
			UsefulMethod.failedToInit(exc);
			}
		/********************/
		
		/*****************
		 * Setting of the inside variables from what we read in the configuration file
		 */
		try
			{
			UsefulMethod.initInternalVariables();
			}
		catch(Exception exc)
			{
			Variables.getLogger().error(exc.getMessage());
			Variables.getLogger().setLevel(Level.INFO);
			}
		/*********************/
		
		
		/*******************
		 * Start main user interface
		 */
		try
			{
			Variables.getLogger().info("Init done, starting main process");
			new Action();
			}
		catch (Exception exc)
			{
			UsefulMethod.failedToInit(exc);
			}
		/******************/
		
		//End of the main class
		}
	
	
	

	public static void main(String[] args)
		{
		new Main();
		}
	/*2020*//*RATEL Alexandre 8)*/
	}

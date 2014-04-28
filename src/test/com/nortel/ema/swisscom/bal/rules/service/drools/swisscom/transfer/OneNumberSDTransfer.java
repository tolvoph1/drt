/*
 * 2012 Swisscom
 *
 * $Id: OneNumberSDTransfer.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
 * 
 * JUnit Test Class to test the transfer rules
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.swisscom.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.RULES_FOLDER_PATH;
import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.USER_DIR;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.nortel.ema.swisscom.bal.rules.model.TransferRulesState;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

import junit.framework.TestCase;

public class OneNumberSDTransfer extends TestCase
{
	private static final String LOG4J_PATH = USER_DIR + "/src/test/conf/log4j.xml";

	private static RulesServiceDroolsImpl droolsImpl;
	private static final boolean LOGGING_ON = true;
	private static final boolean LOGOUTPUT_ON = true;
	private static ServiceConfigurationMap serviceConfiguration = new ServiceConfigurationMap();
	@SuppressWarnings("unused")
	private static long countTestCases = 0;
	public static final String WRONG_VALUE = "-1";
	private static Calendar startDateTestCase = null;
	private static Calendar endDateTestCase = null;
	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
	private static final SimpleDateFormat ELAPSEDFORMAT = new SimpleDateFormat("mm:ss.SSS");

	// Constants used in all test cases
	private static final String TRANSFER_RULES_FILE = "swisscom/onenumbersd_transfer";


	static 
	{
		if (LOGGING_ON)
			DOMConfigurator.configure(LOG4J_PATH);
		else
			Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}
	
	public static void main(final String[] args) 
	{
		junit.textui.TestRunner.run(OneNumberSDTransfer.class);
	}

	protected void setUp() 
	{
		if (LOGOUTPUT_ON) 
		{
			startDateTestCase = GregorianCalendar.getInstance();
			System.out.println("Testcase ("+this.getName()+") started at " + DATEFORMAT.format(startDateTestCase.getTime()) );
		}
	}
	protected void tearDown() 
	{
		if (LOGOUTPUT_ON) 
		{
			endDateTestCase = GregorianCalendar.getInstance();
			System.out.println("Testcase ("+this.getName()+") ended at " + DATEFORMAT.format(endDateTestCase.getTime()) );
			long elapsedTestCase = endDateTestCase.getTimeInMillis() - startDateTestCase.getTimeInMillis();
			System.out.println("Testcase ("+this.getName()+") elapsed time is " + elapsedTestCase + "ms , "+ ELAPSEDFORMAT.format(elapsedTestCase) );
		}

	}
	public final void testID0000_Passwords() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","Passwords");
		callProfile.put("SysOrigANI", "");
		callProfile.put("SysOrigin", "");

		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_Passwords", transferRulesState.getIdentificationResult());
		assertEquals("0000_Passwords", transferRulesState.getQualificationResult());	
	}
	public final void testID0000_Infrastructure() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","Infrastructure");
		callProfile.put("SysOrigANI", "");
		callProfile.put("SysOrigin", "");

		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_Infrastructure", transferRulesState.getIdentificationResult());
		assertEquals("0000_Infrastructure", transferRulesState.getQualificationResult());
	}
	public final void testID0000_Projects() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","Projects");
		callProfile.put("SysOrigANI", "");
		callProfile.put("SysOrigin", "");

		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_Projects", transferRulesState.getIdentificationResult());
		assertEquals("0000_Projects", transferRulesState.getQualificationResult());	
	}
	public final void testID0009() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		
		ArrayList<CallProfile> callProfiles = new ArrayList<CallProfile>();
		
		CallProfile callProfile1 = new CallProfile();
		callProfile1.put("Menuwahl", "AppSysSupport");
		callProfile1.put("SysOrigANI", "");
		callProfile1.put("SysOrigin","multilang");
		callProfiles.add(callProfile1);
		
		CallProfile callProfile2 = new CallProfile();
		callProfile2.put("Menuwahl", "AppSysSupport");
		callProfile2.put("SysOrigANI", "");
		callProfile2.put("SysOrigin", "queue1");
		callProfiles.add(callProfile2);
		
		CallProfile callProfile3 = new CallProfile();
		callProfile3.put("Menuwahl", "AppSysSupport");
		callProfile3.put("SysOrigANI", "0581234567");
		callProfile3.put("SysOrigin","");
		callProfiles.add(callProfile3);
		
		CallProfile callProfile4 = new CallProfile();
		callProfile4.put("Menuwahl", "AppSysSupport");
		callProfile4.put("SysOrigANI", "581234567");
		callProfile4.put("SysOrigin","");
		callProfiles.add(callProfile4);
		
		for (Iterator<CallProfile> callProfileIterator = callProfiles.iterator(); callProfileIterator.hasNext();) 
		{
			CallProfile aktCallProfile = callProfileIterator.next(); 
			
			countTestCases++;
			
			TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, aktCallProfile, serviceConfiguration);
			
			assertEquals("SD_0009", transferRulesState.getIdentificationResult());
			assertEquals("0009", transferRulesState.getQualificationResult());	
		}
	}
	public final void testID0002() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","zuerich");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0002", transferRulesState.getIdentificationResult());
		assertEquals("0002", transferRulesState.getQualificationResult());
	}
	public final void testID0001() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","stgallen");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0001", transferRulesState.getIdentificationResult());
		assertEquals("0001", transferRulesState.getQualificationResult());
	}
	public final void testID0003() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","chur");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0003", transferRulesState.getIdentificationResult());
		assertEquals("0003", transferRulesState.getQualificationResult());
	}
	public final void testID0000() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","olten");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0000", transferRulesState.getIdentificationResult());
		assertEquals("0000", transferRulesState.getQualificationResult());
	}
	public final void testID0005() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","luzern");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0005", transferRulesState.getIdentificationResult());
		assertEquals("0005", transferRulesState.getQualificationResult());	
	}
	public final void testID0004() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","bern");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0004", transferRulesState.getIdentificationResult());
		assertEquals("0004", transferRulesState.getQualificationResult());
	}
	public final void testID0006() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","lausanne");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0006", transferRulesState.getIdentificationResult());
		assertEquals("0006", transferRulesState.getQualificationResult());
	}
	public final void testID0007() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","sion");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0007", transferRulesState.getIdentificationResult());
		assertEquals("0007", transferRulesState.getQualificationResult());
	}
	public final void testID0008() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","AppSysSupport");
		callProfile.put("SysOrigin","bellinzona");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_0008", transferRulesState.getIdentificationResult());
		assertEquals("0008", transferRulesState.getQualificationResult());
	}
	public final void testID0009Default() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		callProfile.put("Menuwahl","");
		callProfile.put("SysOrigin","");
		callProfile.put("SysOrigANI", "");
		
		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("SD_DefaultTrf", transferRulesState.getIdentificationResult());
		assertEquals("0009", transferRulesState.getQualificationResult());
	}

	
	
}

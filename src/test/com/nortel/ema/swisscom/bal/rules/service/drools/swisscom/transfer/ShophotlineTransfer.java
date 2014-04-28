/*
 * 2012 Swisscom
 *
 * $Id: ShophotlineTransfer.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
 * 
 * JUnit Test Class to test the transfer rules
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.swisscom.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

public class ShophotlineTransfer extends TestCase
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
	private static final String TRANSFER_RULES_FILE = "swisscom/shophotline_transfer";


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
		junit.textui.TestRunner.run(ShophotlineTransfer.class);
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
	
	public final void testID6503() throws Exception 
	{
		CustomerProfile customerProfile = new CustomerProfile();
		CustomerProducts customerProducts = new CustomerProducts();
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();
		CallProfile callProfile = new CallProfile();
		
		

		countTestCases++;
		
		TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile, customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		
		assertEquals("", transferRulesState.getIdentificationResult());
		assertEquals("6503", transferRulesState.getQualificationResult());
		
		
	}
}

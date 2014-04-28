/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: OneNumberSDTransferTest.java 235 2014-04-15 10:10:59Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-15 12:10:59 +0200 (Tue, 15 Apr 2014) $
 * $Revision: 235 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.onenumbersd.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.rules.model.TransferRulesState;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneNumberSDTransferTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String TRANSFERRULES = "onenumbersd/onenumbersd_transfer";

	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static TransferRulesState transferRulesState;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(OneNumberSDTransferTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}

	public final void testID001() throws Exception {
		callProfile.setMenuwahl("Passwords");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_Passwords", "0000_Passwords", transferRulesState);
	}

	public final void testID002() throws Exception {
		callProfile.setMenuwahl("Infrastructure");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_Infrastructure", "0000_Infrastructure", transferRulesState);
	}

	public final void testID005() throws Exception {

		Map<String, String> facts = new HashMap<String, String>();
		facts.put("SysOrigin", "multilang");
		facts.put("SysOrigin", "queue1");
		facts.put("SysOrigANI", "058");
		facts.put("SysOrigANI", "58");

		for (String fact: facts.keySet()) {

			callProfile = new CallProfile();
			callProfile.setMenuwahl("AppSysSupport");
			callProfile.put(fact, facts.get(fact));

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("SD_0009", "0009", transferRulesState);
		}
	}
	
	public final void testID006() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("zuerich");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0002", "0002", transferRulesState);
	}
	
	public final void testID007() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("stgallen");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0001", "0001", transferRulesState);
	}
	
	public final void testID008() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("chur");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0003", "0003", transferRulesState);
	}
	
	public final void testID009() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("olten");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0000", "0000", transferRulesState);
	}
	
	public final void testID010() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("luzern");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0005", "0005", transferRulesState);
	}
	
	public final void testID011() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("bern");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0004", "0004", transferRulesState);
	}
	
	public final void testID012() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("lausanne");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0006", "0006", transferRulesState);
	}
	
	public final void testID013() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("sion");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0007", "0007", transferRulesState);
	}
	
	public final void testID014() throws Exception {
		callProfile.setMenuwahl("AppSysSupport");
		callProfile.setSysOrigin("bellinzona");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0008", "0008", transferRulesState);
	}

	public final void testID015() throws Exception {
		callProfile.setMenuwahl("Windows7");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_0016", "0016", transferRulesState);
	}
	
	public final void testDefaultTransfer() throws Exception {

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("SD_DefaultTrf", "0009", transferRulesState);
	}
}

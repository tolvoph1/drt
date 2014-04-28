/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGetNachtMenuTest.java 169 2014-01-24 14:44:16Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-01-24 15:44:16 +0100 (Fri, 24 Jan 2014) $
 * $Revision: 169 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.menu;


import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.menu.model.ExtendedMenuOption;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMERulesGetNachtMenuTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULESFILE = RULES_SME_NACHTMENU;

	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static List<ExtendedMenuOption> expectedMenuOptions;
	private static List<ExtendedMenuOption> menuOptions; 

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);       
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(SMERulesGetNachtMenuTest.class);
	}

	private static final ExtendedMenuOption administrationTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ADMINISTRATION");
		option.setPrompt("alm-admin");
		option.setEmergencyText("SF");
		option.setAction("CONFIRMTRANSFER");
		return option;
	} 

	private static final ExtendedMenuOption stoerung() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("STOERUNG");
		option.setPrompt("alm-techsupport");
		option.setEmergencyText("SA");
		option.setAction("MENU");
		return option;
	} 

	private static final ExtendedMenuOption beratungTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("BERATUNG");
		option.setPrompt("alm-beratung");
		option.setEmergencyText("BERATUNG");
		option.setAction("TRANSFER");
		return option;
	} 

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		expectedMenuOptions = createExtendedMenuOptions();
		callProfile.setBusinessnumber(BUSINESSNUMBER_0800055055);
	}	

	public final void testNachtmenu_Static() throws Exception {

		customerProfile.setSegment(SEGMENT_SME);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 07:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administrationTransfer());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


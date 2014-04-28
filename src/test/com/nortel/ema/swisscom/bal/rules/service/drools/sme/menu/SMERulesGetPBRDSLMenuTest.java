/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGetPBRDSLMenuTest.java 113 2013-09-11 09:52:51Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-11 11:52:51 +0200 (Wed, 11 Sep 2013) $
 * $Revision: 113 $
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
public class SMERulesGetPBRDSLMenuTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULESFILE = RULES_SME_PBRDSLMENU;

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

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		expectedMenuOptions = createExtendedMenuOptions();
		callProfile.setBusinessnumber(BUSINESSNUMBER_0800055055);
	}	

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(SMERulesGetPBRDSLMenuTest.class);
	}

	private static ExtendedMenuOption pbrDSL1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("PBRDSL1");
		option.setPrompt("pbrdsl-option1");
		option.setEmergencyText("");
		option.setAction("TRANSFER");
		option.setSkill("");
		return option;
	}  

	private static ExtendedMenuOption pbrDSL2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("PBRDSL2");
		option.setPrompt("pbrdsl-option2");
		option.setEmergencyText("");
		option.setAction("CONTINUE");
		option.setSkill("");
		return option;
	}  

	public final void testNachtmenu_Static() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(pbrDSL1());
		expectedMenuOptions.add(pbrDSL2());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


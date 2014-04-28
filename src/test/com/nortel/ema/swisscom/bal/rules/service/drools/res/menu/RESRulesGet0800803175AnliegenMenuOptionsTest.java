/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGet0800803175AnliegenMenuOptionsTest.java 79 2013-09-09 15:24:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:24:23 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 79 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.menu;


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
public class RESRulesGet0800803175AnliegenMenuOptionsTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
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

	private static ExtendedMenuOption tvTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("TV");
		option.setPrompt("alm-amico");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption otherWireline() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("OTHER");
		option.setPrompt("alm-weitere-sc-produkte");
		option.setAction("WIRELINE");
		return option;
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		expectedMenuOptions = createExtendedMenuOptions();
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGet0800803175AnliegenMenuOptionsTest.class);
	}

	public final void test_Static_SelectionMenu_0800803175() throws Exception {


		menuOptions = droolsImpl.getExtendedMenuOptions("oneIVR/vp5resAnliegenMenu0800803175", customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(tvTransfer());
		expectedMenuOptions.add(otherWireline());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}

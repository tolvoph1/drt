/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGetNewProductMenuPRMMobileTest.java 113 2013-09-11 09:52:51Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-11 11:52:51 +0200 (Wed, 11 Sep 2013) $
 * $Revision: 113 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.menu;


import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.menu.model.ExtendedMenuOption;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMERulesGetNewProductMenuPRMMobileTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static String RULES_FILE_NAME = RULES_SME_NPPRMMENU_MOBILE;

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
		junit.textui.TestRunner.run(SMERulesGetNewProductMenuPRMMobileTest.class);
	}

	public final void test_ProductMenu_ALM1_Mobile() throws Exception {

		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULES_FILE_NAME, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(smeNPAlm1Prm1());
		expectedMenuOptions.add(smeNPAlm1Prm2());
		expectedMenuOptions.add(smeNPAlm1Prm3());
		expectedMenuOptions.add(smeNPAlm1Prm4());
		expectedMenuOptions.add(smeNPAlm1Prm5());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM2_Mobile() throws Exception {

		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULES_FILE_NAME, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(smeNPAlm2Prm1());
		expectedMenuOptions.add(smeNPAlm2Prm2());
		expectedMenuOptions.add(smeNPAlm2Prm3());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM3_Mobile() throws Exception {

		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULES_FILE_NAME, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(smeNPAlm3Prm1());
		expectedMenuOptions.add(smeNPAlm3Prm2());
		expectedMenuOptions.add(smeNPAlm3Prm3());
		expectedMenuOptions.add(smeNPAlm3Prm4());
		expectedMenuOptions.add(smeNPAlm3Prm5());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM4_Mobile() throws Exception {

		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULES_FILE_NAME, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(smeNPAlm4Prm1());
		expectedMenuOptions.add(smeNPAlm4Prm2());
		expectedMenuOptions.add(smeNPAlm4Prm3());
		expectedMenuOptions.add(smeNPAlm4Prm4());
		expectedMenuOptions.add(smeNPAlm4Prm5());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}
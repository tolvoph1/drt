/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGetNewProductMenuALMMobileTest.java 117 2013-09-16 14:13:44Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-16 16:13:44 +0200 (Mon, 16 Sep 2013) $
 * $Revision: 117 $
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

@SuppressWarnings("unused")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMERulesGetNewProductMenuALMMobileTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULESFILE = RULES_SME_NPALMMENU_MOBILE;

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
		junit.textui.TestRunner.run(SMERulesGetNewProductMenuALMMobileTest.class);
	}

	private static final ExtendedMenuOption npAlmOther() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPOTHERALM");
		option.setPrompt("npalm-other");
		option.setAction("CONTINUE");
		return option;
	} 

	private static final ExtendedMenuOption npAlm1Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1");
		option.setPrompt("npalm-example1");
		option.setAction("MENU");
		return option;
	} 

	private static final ExtendedMenuOption npAlm1Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1TRANSFER");
		option.setPrompt("npalm-Iphone5");
		option.setEmergencyText("NPIPHONE");
		option.setAction("TRANSFER");
		return option;
	} 

	private static final ExtendedMenuOption npAlm2Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2");
		option.setPrompt("npalm-iPad");
		option.setAction("MENU");
		return option;
	} 

	private static final ExtendedMenuOption npAlm2Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2TRANSFER");
		option.setPrompt("npalm-example2");
		option.setAction("TRANSFER");
		return option;
	} 

	private static final ExtendedMenuOption npAlm3Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3");
		option.setPrompt("npalm-example3");
		option.setAction("MENU");
		return option;
	} 

	private static final ExtendedMenuOption npAlm3Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3TRANSFER");
		option.setPrompt("npalm-Combox");
		option.setAction("TRANSFER");
		return option;
	} 

	private static final ExtendedMenuOption npAlm4Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4");
		option.setPrompt("npalm-example4");
		option.setAction("MENU");
		return option;
	} 

	private static final ExtendedMenuOption npAlm4Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4TRANSFER");
		option.setPrompt("npalm-example4");
		option.setAction("TRANSFER");
		return option;
	} 

	public final void test_Static_NewProductMenu_ALM_Mobile() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(npAlm1Transfer());
		expectedMenuOptions.add(npAlmOther());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}
/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGetNewProductMenuALMFixnetTest.java 113 2013-09-11 09:52:51Z tolvoph1 $
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
public class SMERulesGetNewProductMenuALMFixnetTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULESFILE = RULES_SME_NPALMMENU_FIXNET;

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
		junit.textui.TestRunner.run(SMERulesGetNewProductMenuALMFixnetTest.class);
	}

	private static final ExtendedMenuOption npAlmOther() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPOTHERALM");
		option.setPrompt("leerprompt");
		option.setAction("CONTINUE");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm1Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1");
		option.setPrompt("npalm-example1");
		option.setAction("MENU");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm1Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1TRANSFER");
		option.setPrompt("npalm-Iphone5");
		option.setAction("TRANSFER");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm2Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2");
		option.setPrompt("npalm-iPad");
		option.setAction("MENU");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm2Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2TRANSFER");
		option.setPrompt("npalm-example2");
		option.setAction("TRANSFER");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm3Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3");
		option.setPrompt("npalm-example3");
		option.setAction("MENU");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm3Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3TRANSFER");
		option.setPrompt("npalm-Combox");
		option.setAction("TRANSFER");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm4Menu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4");
		option.setPrompt("npalm-example4");
		option.setAction("MENU");
		return option;
	} 

	@SuppressWarnings("unused")
	private static final ExtendedMenuOption npAlm4Transfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4TRANSFER");
		option.setPrompt("npalm-example4");
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
	
	public final void test_Static_NewProductMenu_Fixnet() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(npAlmOther());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}
/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGet0800888500MenuTest.java 113 2013-09-11 09:52:51Z tolvoph1 $
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
public class SMERulesGet0800888500MenuTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULESFILE = "sme/vp5sme0800888500menu";

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
		junit.textui.TestRunner.run(SMERulesGet0800888500MenuTest.class);
	}

	private static final ExtendedMenuOption kundendienst() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("KUNDENDIENST");
		option.setPrompt("alm-kundendienst");
		option.setEmergencyText("SF");
		option.setAction("TRANSFER");
		return option;
	} 

	private static final ExtendedMenuOption stoerung() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("STOERUNG");
		option.setPrompt("alm-stoerung");
		option.setEmergencyText("SA");
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
	}	

	public final void testAnliegenMenu_0800888555_24x7() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(kundendienst());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


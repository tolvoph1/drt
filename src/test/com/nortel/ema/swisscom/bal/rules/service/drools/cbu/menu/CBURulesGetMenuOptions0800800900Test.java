/*
 * (c)2008 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: CBURulesGetMenuOptions0800800900Test.java 236 2014-04-15 12:36:40Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-15 14:36:40 +0200 (Tue, 15 Apr 2014) $
 * $Revision: 236 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.menu;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.menu.model.ExtendedMenuOption;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;
import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

/**
 *
 * @author $Author: tolvoph1 $
 * @version $Revision: 236 $ ($Date: 2014-04-15 14:36:40 +0200 (Tue, 15 Apr 2014) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesGetMenuOptions0800800900Test extends TestCase {

	private static final String LOG4J_PATH = USER_DIR + "/src/test/conf/log4j.xml";

	private static RulesServiceDroolsImpl droolsImpl;
	private static final boolean LOGGING_ON = false;
	private static final String RULESFILENAME = "cbu/vp5cbu-0800800900-menu-level1";

	private static ServiceConfigurationMap serviceConfiguration = new ServiceConfigurationMap();

	static {
		if (LOGGING_ON) DOMConfigurator.configure(LOG4J_PATH);
		else Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);       
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesGetMenuOptions0800800900Test.class);
	}


	// Menu options
	private static ExtendedMenuOption billing() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("BILLING");
		option.setPrompt("kd-cbu-menu1-billing");
		option.setAction("BILLING");
		return option;
	} 

	private static ExtendedMenuOption extranet() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("EXTRANET");
		option.setPrompt("kd-cbu-menu1-extranet");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption other() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("OTHER");
		option.setPrompt("kd-cbu-menu1-other");
		option.setAction("OTHER");
		return option;
	} 

	private List<ExtendedMenuOption> expectedMenuOptions;

	public final void test_Menu() throws Exception {
		// Customer Profile
		CustomerProfile customerProfile = new CustomerProfile();
		// Customer Products
		CustomerProducts customerProducts = new CustomerProducts();
		// Call Profile
		CallProfile callProfile = new CallProfile();

		// Call the Rules
		List<ExtendedMenuOption> menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILENAME, customerProfile, customerProducts, null, callProfile,
				"g", serviceConfiguration);

		// Check results 
				expectedMenuOptions = new ArrayList<ExtendedMenuOption>();
				expectedMenuOptions.add(billing());
				expectedMenuOptions.add(extranet());
				expectedMenuOptions.add(other());
				compareExpectedToActual(expectedMenuOptions, menuOptions);
	}    
}


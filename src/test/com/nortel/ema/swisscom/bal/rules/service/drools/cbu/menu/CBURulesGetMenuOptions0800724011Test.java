/*
 * (c)2014 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesGetMenuOptions0800724011Test.java 240 2014-04-23 10:11:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-23 12:11:23 +0200 (Mi, 23 Apr 2014) $
 * $Revision: 240 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.menu;


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

/**
 *
 * @author $Author: tolvoph1 $
 * @version $Revision: 240 $ ($Date: 2014-04-23 12:11:23 +0200 (Mi, 23 Apr 2014) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesGetMenuOptions0800724011Test extends TestCase {

	private static final String LEVEL1_RULESFILENAME = CBU_MENU_0800724011_LEVEL1;

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

	private static  ExtendedMenuOption menuSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("SA");
		option.setPrompt("alm-serviceassurance");
		option.setSkill("SA");
		option.setAction("SA");
		option.setEmergencyText("");
		return option;
	}

	private static  ExtendedMenuOption menuSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("SF");
		option.setPrompt("alm-servicefulfilment");
		option.setSkill("SF");
		option.setAction("SF");
		option.setEmergencyText("");
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

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesGetMenuOptions0800724011Test.class);
	}

	public final void test_MenuLevel1() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+LEVEL1_RULESFILENAME, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(menuSA());
		expectedMenuOptions.add(menuSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


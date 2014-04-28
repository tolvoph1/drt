/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetMigrosProduktMenuOptionsTest.java 79 2013-09-09 15:24:23Z tolvoph1 $
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

/**
 *
 * @author $Author: tolvoph1 $
 * @version $Revision: 79 $ ($Date: 2013-09-09 17:24:23 +0200 (Mon, 09 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesGetMigrosProduktMenuOptionsTest extends TestCase {

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

	private static ExtendedMenuOption prepaid() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("PRE");
		option.setPrompt("menue-prod-mm-mob-prepaid");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption mobileabo() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("PP");
		option.setPrompt("menue-prod-mm-mob-abo");
		option.setAction("TRANSFER");
		return option;
	} 
	
	private static ExtendedMenuOption internet() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("IN");
	  	  option.setPrompt("menue-prod-mm-mob-int");
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

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGetMigrosProduktMenuOptionsTest.class);
	}


	public final void testAnliegenMenu() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_MIGROS, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(prepaid());
		expectedMenuOptions.add(mobileabo());
		expectedMenuOptions.add(internet());
		compareExpectedToActual(expectedMenuOptions, menuOptions); 
	}
}

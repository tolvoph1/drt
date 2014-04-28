/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetCFSCustomerAnliegenMenuOptionsTest.java 129 2013-10-10 07:47:26Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-10-10 09:47:26 +0200 (Thu, 10 Oct 2013) $
 * $Revision: 129 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.menu;


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
public class RESRulesGetCFSCustomerAnliegenMenuOptionsTest extends TestCase {

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

	private static ExtendedMenuOption allesAndere() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("AA");
	  	  option.setEmergencyText("AA");
	  	  option.setAction("TRANSFER");
	  	  return option;
	}

	private static ExtendedMenuOption eventMilitaer() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("EM");
	  	  option.setEmergencyText("EM");
	  	  option.setAction("TRANSFER");
	  	  return option;
	}

	private static ExtendedMenuOption pps() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("PPS");
	  	  option.setEmergencyText("PPS");
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

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGetCFSCustomerAnliegenMenuOptionsTest.class);
	}

	public final void testCFSCustomerAnliegenMenu() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_ANLIEGEN_MENU_CFS_CUSTOMER, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(allesAndere());
		expectedMenuOptions.add(eventMilitaer());
		expectedMenuOptions.add(pps());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


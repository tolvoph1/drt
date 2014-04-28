/*
 * (c)2014 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetMenuOptions0800406080Test.java 223 2014-03-20 13:34:09Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-20 14:34:09 +0100 (Thu, 20 Mar 2014) $
 * $Revision: 223 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.menu;


import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.LogFactory;
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
public class RESRulesGetMenuOptions0800406080Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static List<ExtendedMenuOption> expectedMenuOptions;
	private static List<ExtendedMenuOption> menuOptions; 

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);       
		LogFactory.getLog(RulesServiceDroolsImpl.class);
	}

	private static ExtendedMenuOption tas1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ACCESSDSL");
		option.setPrompt("tas-alm-access-dsl");
		option.setEmergencyText("ACCESSDSL");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption tas2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("PHONE");
		option.setPrompt("tas-alm-telefonie");
		option.setEmergencyText("PHONE");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption tas3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("TV");
		option.setPrompt("tas-alm-freeze");
		option.setEmergencyText("FREEZE");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption tas4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ALLIPFTTH");
		option.setPrompt("tas-alm-allip-ftth");
		option.setEmergencyText("ALLIPFTTH");
		option.setAction("MENU");
		return option;
	}
	
	private static ExtendedMenuOption res() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("RES");
	  	  option.setPrompt("pm-privatkundenprodukte");
	  	  option.setEmergencyText("RES");
	  	  option.setAction("TRANSFER");
	  	  return option;
	}

	private static ExtendedMenuOption sme() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("SME");
	  	  option.setPrompt("pm-geschaeftskundenprodukte");
	  	  option.setEmergencyText("SME");
	  	  option.setAction("MENU");
	  	  return option;
	}
	
	private static ExtendedMenuOption access() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("SMEACCESS");
	  	  option.setPrompt("pm-accessprobleme");
	  	  option.setEmergencyText("SMEACCESS");
	  	  option.setAction("TRANSFER");
	  	  return option;
	}

	private static ExtendedMenuOption service() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("SERVICE");
	  	  option.setPrompt("pm-serviceprobleme");
	  	  option.setEmergencyText("SERVICE");
	  	  option.setAction("TRANSFER");
	  	  return option;
	}

	protected void setUp() {
		// Create objects
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfiguration = new ServiceConfigurationMap();
		expectedMenuOptions = createExtendedMenuOptions();
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGetMenuOptions0800406080Test.class);
	}

	public final void testTASMenuLevel1() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("oneIVR/vp5res-0800406080-menu-level1", customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(tas1());
		expectedMenuOptions.add(tas2());
		expectedMenuOptions.add(tas3());
		expectedMenuOptions.add(tas4());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void testTASMenuLevel2() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("oneIVR/vp5res-0800406080-menu-level2", customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(res());
		expectedMenuOptions.add(sme());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void testTASMenuLevel3() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("oneIVR/vp5res-0800406080-menu-level3", customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(access());
		expectedMenuOptions.add(service());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}

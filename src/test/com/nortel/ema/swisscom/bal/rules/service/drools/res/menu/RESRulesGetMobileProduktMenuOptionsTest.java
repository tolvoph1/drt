/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetMobileProduktMenuOptionsTest.java 118 2013-09-26 13:45:02Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-26 15:45:02 +0200 (Thu, 26 Sep 2013) $
 * $Revision: 118 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.menu;


import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.menu.model.ExtendedMenuOption;
import com.nortel.ema.swisscom.bal.rules.model.facts.R5RulesBean;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesGetMobileProduktMenuOptionsTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static List<ExtendedMenuOption> expectedMenuOptions;
	private static List<ExtendedMenuOption> menuOptions; 
	private static List<String> customerProfiles;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);       
		LogFactory.getLog(RulesServiceDroolsImpl.class);
	}

	private static  ExtendedMenuOption phoneSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName(R5RulesBean.MENUOPTION_PHONE);
		option.setPrompt("pm-telefonie");
		option.setEmergencyText("SA-TELEFONIE");
		option.setAction("TRANSFER");
		return option;
	}
	
	private static  ExtendedMenuOption mobileInternetSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName(R5RulesBean.MENUOPTION_MOBILE_INTERNET);
		option.setPrompt("menu-mobile-internet");
		option.setEmergencyText("SA-INTERNET");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption tvSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("TV");
		option.setPrompt("pm-tv");
		option.setEmergencyText("SA-TV");
		option.setAction("TRANSFER");
		return option;
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGetMobileProduktMenuOptionsTest.class);
	}

	protected void setUp() {
		// Create objects
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfiguration = new ServiceConfigurationMap();
		expectedMenuOptions = createExtendedMenuOptions();
		customerProfiles = new ArrayList<String>();
	}

	public final void testRESProduktMenu_ALM_Stoerung() throws Exception {

		customerProfiles.add(SEGMENT_RES);
		customerProfiles.add(SEGMENT_SME);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_IDENTIFIEDBYANI, "false");

		expectedMenuOptions.add(mobileInternetSA());
		expectedMenuOptions.add(phoneSA());
		expectedMenuOptions.add(tvSA());

		for (String customerProfilesNext: customerProfiles) {

			customerProfile.setSegment(customerProfilesNext);

			menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_MOBILE, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

			compareExpectedToActual(expectedMenuOptions, menuOptions);
		}
	}

	public final void testRESProduktMenu_ALM_Stoerung_confirmedWirelessANI() throws Exception {

		customerProfiles.add(SEGMENT_RES);
		customerProfiles.add(SEGMENT_SME);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");

		expectedMenuOptions.add(mobileInternetSA());
		expectedMenuOptions.add(phoneSA());
		expectedMenuOptions.add(tvSA());

		for (String customerProfilesNext: customerProfiles) {

			customerProfile.setSegment(customerProfilesNext);

			menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_MOBILE, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

			compareExpectedToActual(expectedMenuOptions, menuOptions);
		}
	}

	public final void testRESProduktMenu_ALM_Stoerung2200() throws Exception {

		customerProfiles.add(SEGMENT_RES);
		customerProfiles.add(SEGMENT_SME);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		expectedMenuOptions.add(mobileInternetSA());
		expectedMenuOptions.add(phoneSA());
		expectedMenuOptions.add(tvSA());

		for (String customerProfilesNext: customerProfiles) {

			customerProfile.setSegment(customerProfilesNext);

			menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_MOBILE_2200, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

			compareExpectedToActual(expectedMenuOptions, menuOptions);
		}
	}
}



/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetNewProductMenuPRMTest.java 233 2014-04-08 06:19:14Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-08 08:19:14 +0200 (Tue, 08 Apr 2014) $
 * $Revision: 233 $
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
@SuppressWarnings("unused")
public class RESRulesGetNewProductMenuPRMTest extends TestCase {

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

	private static ExtendedMenuOption npAlm1Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM1");
		option.setPrompt("npprm-iPhone");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm1Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM2");
		option.setPrompt("npprm-iPad-Support");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm1Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM3");
		option.setPrompt("npprm-iPad-Einrichten");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm1Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM4");
		option.setPrompt("npprm-iPad-Versand");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm1Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM5");
		option.setPrompt("npprm-example015");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm2Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM1");
		option.setPrompt("npprm-iPad-Support");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm2Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM2");
		option.setPrompt("npprm-iPad-Einrichten");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm2Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM3");
		option.setPrompt("npprm-iPad-Versand");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm2Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM4");
		option.setPrompt("npprm-example024");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm2Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM5");
		option.setPrompt("npprm-example025");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm3Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM1");
		option.setPrompt("npprm-example031");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm3Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM2");
		option.setPrompt("npprm-example032");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm3Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM3");
		option.setPrompt("npprm-example033");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm3Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM4");
		option.setPrompt("npprm-example034");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm3Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM5");
		option.setPrompt("npprm-example035");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm4Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM1");
		option.setPrompt("npprm-example041");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm4Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM2");
		option.setPrompt("npprm-example042");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm4Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM3");
		option.setPrompt("npprm-example043");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm4Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM4");
		option.setPrompt("npprm-example044");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption npAlm4Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM5");
		option.setPrompt("npprm-example045");
		option.setAction("TRANSFER");
		return option;
	}  

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		customerProfile.setSegment(SEGMENT_RES);
		callProfile = new CallProfile();
		callProfile.put(CPK_BUSINESSTYP, RES);
		customerProducts = new CustomerProducts();
		expectedMenuOptions = createExtendedMenuOptions();
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGetNewProductMenuPRMTest.class);
	}

	public final void test_ProductMenu_ALM1_Mobile() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,	
				"g", serviceConfiguration);

		expectedMenuOptions.add(npAlm1Prm1());
		expectedMenuOptions.add(npAlm1Prm2());
		expectedMenuOptions.add(npAlm1Prm3());
		expectedMenuOptions.add(npAlm1Prm4());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM1_Fixnet() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM2_Mobile() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM2_Fixnet() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM3_Mobile() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM3_Fixnet() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM4_Mobile() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_ProductMenu_ALM4_Fixnet() throws Exception {

		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_NEWPRODUCT_MENU_PM, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}
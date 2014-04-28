/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetFixnetProduktMenuOptionsTest.java 153 2013-11-25 09:43:09Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-11-25 10:43:09 +0100 (Mon, 25 Nov 2013) $
 * $Revision: 153 $
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

/**
 *
 * @author $Author: tolvoph1 $
 * @version $Revision: 153 $ ($Date: 2013-11-25 10:43:09 +0100 (Mon, 25 Nov 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesGetFixnetProduktMenuOptionsTest extends TestCase {

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

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGetFixnetProduktMenuOptionsTest.class);
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
	/**
	 * Adds complete Internet product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterInternetOnlyIN(final CustomerProducts customerProducts) {
		customerProducts.add(PR_BLUEWIN_PHONE);
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_MOBILE);
		customerProducts.add(PR_WIRELESS_HOME_CONNECTION);
	}
	/**
	 * Adds Internet product cluster that is not part of the menu relevant cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterInternetNotIN(final CustomerProducts customerProducts) {
		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);
	}
	/**
	 * Adds complete Fixnet product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterFixnet(final CustomerProducts customerProducts) {
		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);		
	}
	/**
	 * Adds complete TV product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterTV(final CustomerProducts customerProducts) {
		customerProducts.add(PR_BLUEWIN_TV);
	}
	
	private static void addProductClusterAllIP(final CustomerProducts customerProducts) {
		customerProducts.add(PR_ALL_TRIO_BUNDLE);
		customerProducts.add(PR_ALL_DUO_BUNDLE);
		customerProducts.add(PR_ALL_FIBERLINE_MINI);
		customerProducts.add(PR_ALL_FIBERLINE_TV_BASIC);
		customerProducts.add(PR_ALL_CASATRIO_BASIC);
		customerProducts.add(PR_ALL_CASATRIO_MINI);
		customerProducts.add(PR_ALL_HOMEENTERTAINMENT_3STAR);
		customerProducts.add(PR_ALL_HOMEENTERTAINMENT_4STAR);
		customerProducts.add(PR_ALL_HOMEENTERTAINMENT_5STAR);
	}

	// MenuOptions to test against

	private static ExtendedMenuOption phoneSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("PHONE");
		option.setPrompt("pm-telefonie");
		option.setEmergencyText("SA-TELEFONIE");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption internetSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("INTERNET");
		option.setPrompt("pm-internet");
		option.setEmergencyText("SA-INTERNET");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption tvSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("TV");
		option.setPrompt("pm-tv");
		option.setEmergencyText("SA-TV");
		option.setAction("TRANSFER");
		return option;
	}

	public final void testProduktMenu_ALM_Stoerung_FX_only() throws Exception {

		addProductClusterFixnet(customerProducts);
		
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_IN_only_Tagmenu() throws Exception {

		addProductClusterInternetNotIN(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_IN_only_Nachtmenu2200() throws Exception {

		addProductClusterInternetNotIN(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET_2200, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_TV_only() throws Exception {

		addProductClusterTV(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(tvSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_FX_IN() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternetNotIN(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_FX_IN_Tagmenu() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternetNotIN(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_FX_IN_PC_MAC_Nachtmenu2200() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternetNotIN(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET_2200, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_FX_TV() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterTV(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(tvSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_FX_IN_TV_Tagmenu() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternetNotIN(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterTV(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_FX_IN_TV_Nachtmenu2200() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternetNotIN(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterTV(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET_2200, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_AllIP() throws Exception {

		addProductClusterAllIP(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_AllIP_Nachtmenu2200() throws Exception {

		addProductClusterAllIP(customerProducts);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET_2200, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenuNoData_ALM_Stoerung() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		expectedMenuOptions.add(phoneSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testProduktMenu_ALM_Stoerung_Customer_Without_Number_Nachtmenu2200() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET_2200, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void testProduktMenu_ALM_Stoerung_Customer_Without_Number() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_PRODUKT_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}



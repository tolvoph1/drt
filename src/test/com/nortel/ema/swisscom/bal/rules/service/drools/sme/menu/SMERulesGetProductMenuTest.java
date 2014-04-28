/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGetProductMenuTest.java 153 2013-11-25 09:43:09Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-11-25 10:43:09 +0100 (Mon, 25 Nov 2013) $
 * $Revision: 153 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.menu;


import java.util.ArrayList;
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
public class SMERulesGetProductMenuTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULESFILE = RULES_SME_PRODUKTMENU;

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

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		expectedMenuOptions = createExtendedMenuOptions();
		callProfile.setBusinessnumber(BUSINESSNUMBER_0800055055);
	}	
	
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(SMERulesGetProductMenuTest.class);
	}
	
	private static void addProductClusterFixnet(final CustomerProducts customerProducts) {
		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		customerProducts.add(PR_BUSINESS_CONNECT_IMS);
	}

	private static void addProductClusterMobile(final CustomerProducts customerProducts) {
		customerProducts.add(PR_DSL_MOBILE);
		customerProducts.add(PR_MOBILE76);
		customerProducts.add(PR_MOBILE77);
		customerProducts.add(PR_MOBILE78);
		customerProducts.add(PR_MOBILE79);
	}

	private static void addProductClusterInternet(final CustomerProducts customerProducts) {
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_PROFESSIONELL);
		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);
		customerProducts.add(PR_IP_PLUS_BUI);
		customerProducts.add(PR_BUSINESS_INTERNET_LIGHT);
		customerProducts.add(PR_BUSINESS_INTERNET_STANDARD);
	}

	private static void addProductClusterTV(final CustomerProducts customerProducts) {
		customerProducts.add(PR_BLUEWIN_TV);
	}
	private static void addProductClusterITHosting(final CustomerProducts customerProducts) {
		customerProducts.add(PR_VPN_PROFESSIONELL);
		customerProducts.add(PR_HOSTED_EXCHANGE_PROFESSIONELL);
		customerProducts.add(PR_HOSTED_EXCHANGE_PROFESSIONAL_2);
		customerProducts.add(PR_ONLINE_BACKUP_PROFESSIONAL);
		customerProducts.add(PR_SHARED_OFFICE_PROFESSIONAL);
		customerProducts.add(PR_KMUOFFICE);
	}

	private static void addProductClusterPBX(final CustomerProducts customerProducts) {
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);
		customerProducts.add(PR_BUSINESSCONNECT_IPC);
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
	// Menu options
	private static ExtendedMenuOption rechnung() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("RECHNUNG");
		option.setPrompt("pm-einerechnung");
		option.setEmergencyText("SF-RECHNUNG");
		option.setAction("RECHNUNG");
		return option;
	} 

	private static ExtendedMenuOption festnetzSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("FESTNETZ");
		option.setPrompt("pm-festnetztelefonie");
		option.setEmergencyText("SA-FESTNETZ");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption festnetzSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("FESTNETZ");
		option.setPrompt("pm-festnetztelefonie");
		option.setEmergencyText("SF-FESTNETZ");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption mobileSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("MOBILE");
		option.setPrompt("pm-mobileinternet");
		option.setEmergencyText("SA-MOBILE");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption mobileSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("MOBILE");
		option.setPrompt("pm-mobileinternet");
		option.setEmergencyText("SF-MOBILE");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption internetSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("INTERNET");
		option.setPrompt("pm-internetoffice");
		option.setEmergencyText("SA-INTERNET");
		option.setAction("TRANSFER");
		return option;
	} 

	private static ExtendedMenuOption internetSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("INTERNET");
		option.setPrompt("pm-internettv");
		option.setEmergencyText("SF-INTERNET");
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

	private static ExtendedMenuOption ithostingSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ITHOSTING");
		option.setPrompt("pm-ithosting");
		option.setEmergencyText("SA-ITHOSTING");
		option.setAction("TRANSFER");
		return option;
	}  

	private static ExtendedMenuOption ithostingSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ITHOSTING");
		option.setPrompt("pm-ithosting");
		option.setEmergencyText("SF-ITHOSTING");
		option.setAction("TRANSFER");
		return option;
	}     

	private static ExtendedMenuOption pbxSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("VERMITTLUNGSANLAGE");
		option.setPrompt("pm-tva");
		option.setEmergencyText("SA-PBX");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption pbxSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("VERMITTLUNGSANLAGE");
		option.setPrompt("pm-tva2");
		option.setEmergencyText("SF-PBX");
		option.setAction("TRANSFER");
		return option;
	}  

	public final void test01_ProduktMenu_Administration_onlyOneProduct_Festnetz() throws Exception {

		addProductClusterFixnet(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test02_ProduktMenu_Administration_onlyOneProduct_Mobile() throws Exception {

		addProductClusterMobile(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(mobileSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test03_ProduktMenu_Administration_onlyOneProduct_Internet() throws Exception {

		addProductClusterInternet(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(internetSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test04_ProduktMenu_Administration_onlyOneProduct_ITHosting() throws Exception {

		addProductClusterITHosting(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(ithostingSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test04b_ProduktMenu_Administration_onlyOneProduct_ITHosting() throws Exception {

		// Test with OptionOffer
		callProfile.setOptionOfferName("KMU Office W+C 4Star");

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(ithostingSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test05_ProduktMenu_Administration_onlyOneProduct_PBX() throws Exception {

		addProductClusterPBX(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test06_ProduktMenu_Administration_moreTHanOneProduct_InternetOnly() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternet(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		
		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test06a_ProduktMenu_Administration_moreTHanOneProduct_InternetOnly_ALLIP() throws Exception {

		addProductClusterInternet(customerProducts);
		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test07_ProduktMenu_Administration_moreThanOneProduct_ITHostingOnly() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterITHosting(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(ithostingSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test07a_ProduktMenu_Administration_moreThanOneProduct_ITHostingOnly_ALLIP() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(ithostingSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test08_ProduktMenu_Administration_moreThanOneProduct_PBXOnly() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterPBX(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = new ArrayList<ExtendedMenuOption>();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test08a_ProduktMenu_Administration_moreThanOneProduct_PBXOnly_ALLIP() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = new ArrayList<ExtendedMenuOption>();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test09_ProduktMenu_Administration_moreThanOneProduct_Internet_ITHosting() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternet(customerProducts);
		addProductClusterITHosting(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(ithostingSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test09a_ProduktMenu_Administration_moreThanOneProduct_Internet_ITHosting_ALLIP() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(ithostingSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test10_ProduktMenu_Administration_moreThanOneProduct_Internet_PBX() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternet(customerProducts);
		addProductClusterPBX(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test10a_ProduktMenu_Administration_moreThanOneProduct_Internet_PBX_ALLIP() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternet(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test11_ProduktMenu_Administration_moreThanOneProduct_ITHosting_PBX() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterPBX(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(ithostingSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test11a_ProduktMenu_Administration_moreThanOneProduct_ITHosting_PBX_ALLIP() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(ithostingSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test12_ProduktMenu_Administration_moreThanOneProduct_Internet_ITHosting_PBX() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterPBX(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(ithostingSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test12a_ProduktMenu_Administration_moreThanOneProduct_Internet_ITHosting_PBX_ALLIP() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterInternet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(ithostingSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test13_ProduktMenu_Administration_FALLBACK() throws Exception {

		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(rechnung());
		expectedMenuOptions.add(festnetzSF());
		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(internetSF());
		expectedMenuOptions.add(ithostingSF());
		expectedMenuOptions.add(pbxSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test20_ProduktMenu_STOERUNG_UnknownCustomer() throws Exception {

		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(festnetzSA());
		expectedMenuOptions.add(mobileSA());
		expectedMenuOptions.add(internetSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test20A_ProduktMenu_STOERUNG_UnknownCustomer_ALLIP() throws Exception {

		addProductClusterAllIP(customerProducts);

		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);
 
		expectedMenuOptions = createExtendedMenuOptions();
		expectedMenuOptions.add(festnetzSA());
		expectedMenuOptions.add(mobileSA());
		expectedMenuOptions.add(internetSA());
		expectedMenuOptions.add(tvSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	/*
	 * The 63 test cases for the "Stoerung" Produktmenu will be simplified using a binary
	 * representation of the individual clusters in a 6Bit Binary number.
	 * The individual testcases will not have the same order as the rules but will cover all
	 * combinations from 000001 to 111111.
	 * 
	 */
	public final void test21to83_ProduktMenu_Stoerung_All63Variants() throws Exception {

		customerProfile.setCustomerID("123");

		// ArrayList for Menus since we have 2
		ArrayList<String> menus = new ArrayList<String>();
		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			// Create new callProfile for each iteration
			callProfile = new CallProfile();
			callProfile.put(menu, CPV_STOERUNG);

			// Inner loop for the 63 variants
			for (int variant = 1; variant <64; variant++) {
				// Create new object for CustomerProducts
				customerProducts = new CustomerProducts();
				// Add clusters per bitwise filter
				// 1=Festnetz, 2=Mobile, 4=Internet, 8=TV, 16=ITHosting, 32=PBX
				int FESTNETZ = 1;
				int MOBILE = 2;
				int INTERNET = 4;
				int TV = 8;
				int ITHOSTING = 16;
				int PBX = 32;
				// create new object for expected MenuOptions
				expectedMenuOptions = createExtendedMenuOptions();
				if ((variant&FESTNETZ)==FESTNETZ) {
					addProductClusterFixnet(customerProducts);
					expectedMenuOptions.add(festnetzSA());
				}
				if ((variant&MOBILE)==MOBILE) {
					addProductClusterMobile(customerProducts);
					expectedMenuOptions.add(mobileSA());
				}
				if ((variant&INTERNET)==INTERNET) {
					addProductClusterInternet(customerProducts);
					expectedMenuOptions.add(internetSA());
				}
				if ((variant&TV)==TV) {
					addProductClusterTV(customerProducts);
					expectedMenuOptions.add(tvSA());
				}
				if ((variant&ITHOSTING)==ITHOSTING) {
					addProductClusterITHosting(customerProducts);
					expectedMenuOptions.add(ithostingSA());
				}
				if ((variant&PBX)==PBX) {
					addProductClusterPBX(customerProducts);
					expectedMenuOptions.add(pbxSA());
				}

				menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
						"g", serviceConfiguration);

				// Check results 
				compareExpectedToActual(expectedMenuOptions, menuOptions);
			} // end loop over variants
		}
	}


	public final void test84_ProduktMenu_STOERUNG_FALLBACK() throws Exception {

		customerProfile.setCustomerID("123");

		// ArrayList for Menus since we have 2
		ArrayList<String> menus = new ArrayList<String>();
		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {

			callProfile = new CallProfile();
			callProfile.put(menu, CPV_STOERUNG);

			menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
					"g", serviceConfiguration);

			// Check results 
			expectedMenuOptions = createExtendedMenuOptions();
			expectedMenuOptions.add(festnetzSA());
			expectedMenuOptions.add(mobileSA());
			expectedMenuOptions.add(internetSA());
			expectedMenuOptions.add(tvSA());
			expectedMenuOptions.add(ithostingSA());
			expectedMenuOptions.add(pbxSA());
			compareExpectedToActual(expectedMenuOptions, menuOptions);
		}
	}
	public final void test85to92_ProduktMenu_Stoerung_AllIPVariants() throws Exception {

		customerProfile.setCustomerID("123");

		// ArrayList for Menus since we have 2
		ArrayList<String> menus = new ArrayList<String>();
		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			callProfile = new CallProfile();
			callProfile.put(menu, CPV_STOERUNG);

			// Inner loop for the 8 variants
			for (int variant = 0; variant <8; variant++) {
				// Create new object for CustomerProducts
				customerProducts = new CustomerProducts();
				// Add ALL IP Product
				addProductClusterAllIP(customerProducts);
				// Add clusters per bitwise filter
				// 1=Mobile, 2=ITHosting, 4=PBX
				int MOBILE = 1;
				int ITHOSTING = 2;
				int PBX = 4;
				// create new object for expected MenuOptions
				expectedMenuOptions = createExtendedMenuOptions();
				// options that are always present
				addProductClusterFixnet(customerProducts);
				expectedMenuOptions.add(festnetzSA());
				if ((variant&MOBILE)==MOBILE) {
					addProductClusterMobile(customerProducts);
					expectedMenuOptions.add(mobileSA());
				}
				addProductClusterInternet(customerProducts);
				expectedMenuOptions.add(internetSA());
				addProductClusterTV(customerProducts);
				expectedMenuOptions.add(tvSA());

				if ((variant&ITHOSTING)==ITHOSTING) {
					addProductClusterITHosting(customerProducts);
					expectedMenuOptions.add(ithostingSA());
				}
				if ((variant&PBX)==PBX) {
					addProductClusterPBX(customerProducts);
					expectedMenuOptions.add(pbxSA());
				}

				menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
						"g", serviceConfiguration);

				compareExpectedToActual(expectedMenuOptions, menuOptions);
			} // end loop over variants
		}
	}
}


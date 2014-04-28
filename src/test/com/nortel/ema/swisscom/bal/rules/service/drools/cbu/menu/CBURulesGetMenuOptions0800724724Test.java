/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesGetMenuOptions0800724724Test.java 81 2013-09-10 08:23:04Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-10 10:23:04 +0200 (Tue, 10 Sep 2013) $
 * $Revision: 81 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.menu;


import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

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
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts.Product;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

/**
 *
 * @author $Author: tolvoph1 $
 * @version $Revision: 81 $ ($Date: 2013-09-10 10:23:04 +0200 (Tue, 10 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesGetMenuOptions0800724724Test extends TestCase {

	private static final String LEVEL1_RULESFILENAME = CBU_MENU_0800724724_LEVEL1;
	private static final String LEVEL1_CP_KEY = CPK_CBU_0800724724_MENU_LEVEL_1;
	private static final String LEVEL2_RULESFILENAME = CBU_MENU_0800724724_LEVEL2;

	private static RulesServiceDroolsImpl droolsImpl;
	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static List<ExtendedMenuOption> expectedMenuOptions;
	private static List<ExtendedMenuOption> menuOptions; 
	private static ArrayList<Product> productList;

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
		option.setAction("MENU");
		option.setEmergencyText("SA");
		return option;
	}

	private static  ExtendedMenuOption menuSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("SF");
		option.setPrompt("alm-servicefulfilment");
		option.setSkill("SF");
		option.setAction("MENU");
		option.setEmergencyText("SF");
		return option;
	}

	private static  ExtendedMenuOption hiddenTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("CUC_International");
		option.setPosition(9);
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption voiceSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("VOICE");
		option.setPrompt("pm-voice");
		option.setSkill("VOICE");
		option.setEmergencyText("SA-VOICE"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption dataSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("DATA");
		option.setPrompt("pm-data");
		option.setSkill("DATA");
		option.setEmergencyText("SA-DATA"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption dataSAconfirm() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("DATA");
		option.setPrompt("pm-data");
		option.setSkill("DATA");
		option.setEmergencyText("SA-DATA"); 
		option.setAction("CONFIRM");
		return option;
	}

	private static  ExtendedMenuOption mobileSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("MO");
		option.setPrompt("pm-mobile");
		option.setSkill("MO");
		option.setEmergencyText("SA-MOBILE"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption mss() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("MSS");
		option.setPrompt("pm-mss");
		option.setSkill("MSS");
		option.setEmergencyText("MSS"); 
		option.setAction("MSS");
		return option;
	}

	private static  ExtendedMenuOption mobileSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("MO");
		option.setPrompt("pm-mobile");
		option.setSkill("MO");
		option.setEmergencyText("SF-MOBILE"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption otherSF() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("OTHER");
		option.setPrompt("pm-weiteres");
		option.setSkill("OTHER");
		option.setEmergencyText("OTHER"); 
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
		productList = new ArrayList<Product>();
	}

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesGetMenuOptions0800724724Test.class);
	}

	public final void test_MenuLevel1() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+LEVEL1_RULESFILENAME, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(menuSA());
		expectedMenuOptions.add(menuSF());
		expectedMenuOptions.add(hiddenTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_MenuLevel2_SA_Level1_noSMEProducts() throws Exception {

		callProfile.put(LEVEL1_CP_KEY, CPV_CBU_MENU_SA);

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+LEVEL2_RULESFILENAME, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(voiceSA());
		expectedMenuOptions.add(dataSA());
		expectedMenuOptions.add(mobileSA());
		expectedMenuOptions.add(mss());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_MenuLevel2_SA_Level1_SMEProducts() throws Exception {

		productList.add(PR_DSL_MOBILE);
		productList.add(PR_BUSINESS_INTERNET_LIGHT);
		productList.add(PR_BUSINESS_INTERNET_STANDARD);
		productList.add(PR_VPN_PROFESSIONELL);
		productList.add(PR_FX_BLUEWIN_DSL);

		callProfile.put(LEVEL1_CP_KEY, CPV_CBU_MENU_SA);

		expectedMenuOptions.add(voiceSA());
		expectedMenuOptions.add(dataSAconfirm());
		expectedMenuOptions.add(mobileSA());
		expectedMenuOptions.add(mss());

		for (Product product: productList) {

			customerProducts = new CustomerProducts();
			customerProducts.add(product);

			menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+LEVEL2_RULESFILENAME, customerProfile, customerProducts, customerProductClusters,
					callProfile, "g", serviceConfiguration);

			compareExpectedToActual(expectedMenuOptions, menuOptions);
		}
	}

	public final void test_MenuLevel2_SF_Level1() throws Exception {

		callProfile.put(LEVEL1_CP_KEY, CPV_CBU_MENU_SF);

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+LEVEL2_RULESFILENAME, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(mobileSF());
		expectedMenuOptions.add(otherSF());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


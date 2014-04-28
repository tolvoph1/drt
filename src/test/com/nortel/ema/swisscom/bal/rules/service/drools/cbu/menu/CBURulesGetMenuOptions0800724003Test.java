/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesGetMenuOptions0800724003Test.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
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
 * @version $Revision: 78 $ ($Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesGetMenuOptions0800724003Test extends TestCase {

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

	private static  ExtendedMenuOption saOUS() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("SA_OUS");
		option.setPrompt("alm-serviceassurance");
		option.setSkill("SA_OUS");
		option.setEmergencyText("SA");
		option.setAction("MENU");
		return option;
	}

	private static  ExtendedMenuOption order() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ORDER");
		option.setPrompt("alm-order-nv");
		option.setSkill("ORDER");
		option.setEmergencyText("ORDER");
		option.setAction("MENU");
		return option;
	}

	private static  ExtendedMenuOption fixnetSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("FX");
		option.setPrompt("pm-voice-nv-pw");
		option.setSkill("FX");
		option.setEmergencyText("SA-FIXNET");
		option.setAction("TRANSFER");
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

	private static  ExtendedMenuOption pagingSA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("PAGING");
		option.setPrompt("pm-paging-nv");
		option.setSkill("PAGING");
		option.setEmergencyText("PAGING");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption fixnetOrder() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("FX");
		option.setPrompt("pm-voice-nv");
		option.setSkill("FX");
		option.setEmergencyText("ORDER-FIXNET");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption mobileOrder() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("MO");
		option.setPrompt("pm-mobile");
		option.setSkill("MO");
		option.setEmergencyText("ORDER-MOBILE");
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
		junit.textui.TestRunner.run(CBURulesGetMenuOptions0800724003Test.class);
	}

	public final void test_MenuLevel1() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800724003_LEVEL1, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(saOUS());
		expectedMenuOptions.add(order());
		compareExpectedToActual(expectedMenuOptions, menuOptions);		
	}

	public final void test_MenuLevel2_Level1_SA_OUS() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800724003_LEVEL2, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(fixnetSA());
		expectedMenuOptions.add(mobileSA());
		expectedMenuOptions.add(pagingSA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);		
	}

	public final void test_MenuLevel2_Level1_ORDER() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800724003_LEVEL2, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(fixnetOrder());
		expectedMenuOptions.add(mobileOrder());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

}


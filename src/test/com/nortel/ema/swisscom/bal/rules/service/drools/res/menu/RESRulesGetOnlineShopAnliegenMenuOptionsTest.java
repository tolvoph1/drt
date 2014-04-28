/*
 * (c)2014 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetOnlineShopAnliegenMenuOptionsTest.java 231 2014-04-07 07:25:35Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-07 09:25:35 +0200 (Mon, 07 Apr 2014) $
 * $Revision: 231 $
 * $URL: http://sol47658/svn/BAL_Rules/trunk/BAL_Rules/src/test/com/nortel/ema/swisscom/bal/rules/service/drools/res/menu/RESRulesGetOnlineShopAnliegenMenuOptionsTest.java $
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesGetOnlineShopAnliegenMenuOptionsTest extends TestCase {

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

	private static ExtendedMenuOption tvInternetAdmin() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("TVA");
	  	  option.setEmergencyText("TVA");
	  	  option.setPrompt("alm-tv-internet-admin");
	  	  option.setAction("TRANSFER");
	  	  return option;
	} 

	private static ExtendedMenuOption allesAndere() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("AA");
	  	  option.setEmergencyText("AA");
	  	  option.setPrompt("alm-alles-andere");
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
		junit.textui.TestRunner.run(RESRulesGetOnlineShopAnliegenMenuOptionsTest.class);
	}

	public final void testAnliegenMenu() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_ANLIEGEN_MENU_ONLINESHOP, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(tvInternetAdmin());
		expectedMenuOptions.add(allesAndere());
		compareExpectedToActual(expectedMenuOptions, menuOptions); 
	}
}

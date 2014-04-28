/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesGetMenuOptions0800724008Test.java 78 2013-09-09 15:18:53Z tolvoph1 $
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
public class CBURulesGetMenuOptions0800724008Test extends TestCase {

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
		option.setPrompt("alm-serviceassuranceous");
		option.setSkill("SA_OUS");
		option.setEmergencyText("SA"); //??
		option.setAction("MENU");
		return option;
	}

	private static  ExtendedMenuOption order() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ORDER");
		option.setPrompt("alm-order");
		option.setSkill("ORDER");
		option.setEmergencyText("ORDER"); //??
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption voice() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("VOICE");
		option.setPrompt("pm-voice");
		option.setSkill("VOICE");
		option.setEmergencyText("SA-VOICE");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption data() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("DATA");
		option.setPrompt("pm-data");
		option.setSkill("DATA");
		option.setEmergencyText("SA-DATA");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption blackberry() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("BB");
		option.setPrompt("pm-bb");
		option.setSkill("BB");
		option.setEmergencyText("SA-BLACKBERRY"); //??
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
		junit.textui.TestRunner.run(CBURulesGetMenuOptions0800724008Test.class);
	}

	public final void test_MenuLevel1() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800724008_LEVEL1, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(saOUS());
		expectedMenuOptions.add(order());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_MenuLevel2() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800724008_LEVEL2, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(voice());
		expectedMenuOptions.add(data());
		expectedMenuOptions.add(blackberry());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

}


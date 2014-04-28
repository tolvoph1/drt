/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesGetMenuOptions0800724002Test.java 78 2013-09-09 15:18:53Z tolvoph1 $
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
public class CBURulesGetMenuOptions0800724002Test extends TestCase {

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

	private static  ExtendedMenuOption blackberry() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("BB");
		option.setPrompt("alm-bb");
		option.setSkill("BB");
		option.setEmergencyText("BLACKBERRY");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption almvoiceAXA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("VOICE_AXA");
		option.setPrompt("alm-voiceAXA");
		option.setSkill("VOICE_AXA");
		option.setEmergencyText("INCIDENT-VOICE"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption almdataAXA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("DATA_AXA");
		option.setPrompt("alm-dataAXA");
		option.setSkill("DATA_AXA");
		option.setEmergencyText("INCIDENT-DATA");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption order() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ORDER");
		option.setPrompt("alm-order");
		option.setSkill("ORDER");
		option.setEmergencyText("ORDER"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption pmvoiceAXA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("VOICE_AXA");
		option.setPrompt("pm-voiceAXA");
		option.setSkill("VOICE_AXA");
		option.setEmergencyText("SA-VOICE");
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption pmdataAXA() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("DATA_AXA");
		option.setPrompt("pm-dataAXA");
		option.setSkill("DATA_AXA");
		option.setEmergencyText("SA-DATA");
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
		junit.textui.TestRunner.run(CBURulesGetMenuOptions0800724002Test.class);
	}

	public final void test_MenuLevel1() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800724002_LEVEL1, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(blackberry());
		expectedMenuOptions.add(almvoiceAXA());
		expectedMenuOptions.add(almdataAXA());
		expectedMenuOptions.add(order());
		compareExpectedToActual(expectedMenuOptions, menuOptions);

	}

	public final void test_MenuLevel2() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800724002_LEVEL2, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(pmvoiceAXA());
		expectedMenuOptions.add(pmdataAXA());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


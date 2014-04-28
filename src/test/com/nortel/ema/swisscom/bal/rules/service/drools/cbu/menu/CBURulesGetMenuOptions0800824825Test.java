/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesGetMenuOptions0800824825Test.java 78 2013-09-09 15:18:53Z tolvoph1 $
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
public class CBURulesGetMenuOptions0800824825Test extends TestCase {

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

	private static  ExtendedMenuOption sa() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("SA");
		option.setPrompt("alm-serviceassurance");
		option.setSkill("SA");
		option.setEmergencyText("SA"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static  ExtendedMenuOption sf() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("SF");
		option.setPrompt("alm-servicefulfilment");
		option.setSkill("SF");
		option.setEmergencyText("SF"); 
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption transferALM() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("TRANSFERALM");
		option.setPrompt("");
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
		junit.textui.TestRunner.run(CBURulesGetMenuOptions0800824825Test.class);
	}


	public final void test_0800824825_Level1() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800824825_LEVEL1, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(sf());
		expectedMenuOptions.add(sa());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void test_0800824825_Level1_MDSV4_withinHours() throws Exception {

		callProfile.setOptionOfferName("MDS End User Support");
		callProfile.put(CPK_SYSCVTDATE, "03/19/2012 08:29:59");

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800824825_LEVEL1, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(transferALM());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
	
	public final void test_0800824825_Level1_MDSV4_outsideHours() throws Exception {

		callProfile.setOptionOfferName("MDS End User Support");
		callProfile.put(CPK_SYSCVTDATE, "09/04/2012 06:29:59");

		menuOptions = droolsImpl.getExtendedMenuOptions("cbu/"+CBU_MENU_0800824825_LEVEL1, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(sf());
		expectedMenuOptions.add(sa());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


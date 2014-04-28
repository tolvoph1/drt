/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetFixnetAnliegenMenuOptionsTest.java 147 2013-11-13 12:35:18Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-11-13 13:35:18 +0100 (Wed, 13 Nov 2013) $
 * $Revision: 147 $
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

/**
 *
 * @author $Author: tolvoph1 $
 * @version $Revision: 147 $ ($Date: 2013-11-13 13:35:18 +0100 (Wed, 13 Nov 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesGetFixnetAnliegenMenuOptionsTest extends TestCase {

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

	private static ExtendedMenuOption kundendienstTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("KUNDENDIENST");
		option.setPrompt("alm-kundendienst");
		option.setEmergencyText("SF");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption rechnungTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("RECHNUNG");
		option.setPrompt("alm-rechnung");
		option.setEmergencyText("RECHNUNG");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption stoerungMenu() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("STOERUNG");
		option.setPrompt("alm-stoerung");
		option.setEmergencyText("SA");
		option.setAction("MENU");
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
		junit.textui.TestRunner.run(RESRulesGetFixnetAnliegenMenuOptionsTest.class);
	}

	public final void testRESFullAnliegenMenu() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_ANLIEGEN_MENU_FIXNET, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(kundendienstTransfer());
		expectedMenuOptions.add(stoerungMenu());		
		expectedMenuOptions.add(rechnungTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testRESNachtAnliegenMenu() throws Exception {

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_ANLIEGEN_MENU_FIXNET_NACHT, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		expectedMenuOptions.add(stoerungMenu());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


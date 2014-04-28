/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesGetMobileCBUAnliegenMenuOptionsTest.java 147 2013-11-13 12:35:18Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-11-13 13:35:18 +0100 (Wed, 13 Nov 2013) $
 * $Revision: 147 $
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
public class RESRulesGetMobileCBUAnliegenMenuOptionsTest extends TestCase {

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
		option.setEmergencyText("UNDEFINED-SF");
		option.setAction("TRANSFER");
		return option;
	}

	private static ExtendedMenuOption stoerungTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("STOERUNG");
		option.setPrompt("alm-stoerung");
		option.setAction("TRANSFER");
		return option;
	}

	// CRQ2012-014 CBU Customers with special product get no anliegenmenu but direct transfer
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
		customerProfile.setSegment(SEGMENT_CBU);
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		expectedMenuOptions = createExtendedMenuOptions();
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesGetMobileCBUAnliegenMenuOptionsTest.class);
	}

	public final void testMobileCBUFullAnliegenMenu() throws Exception {

		expectedMenuOptions.add(kundendienstTransfer());
		expectedMenuOptions.add(stoerungTransfer());

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_ANLIEGEN_MENU_MOBILE_CBU, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);


		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testMobileCBUMDSV4Menu_cvtDate_Within() throws Exception {


		callProfile.setOptionOfferName("MDS End User Support");

		callProfile.put(CPK_SYSCVTDATE, "03/19/2012 08:29:59");

		expectedMenuOptions.add(transferALM());

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_ANLIEGEN_MENU_MOBILE_CBU, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);


		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testMobileCBUMDSV4Menu_cvtDate_Outside() throws Exception {

		callProfile.setOptionOfferName("MDS End User Support");

		callProfile.put(CPK_SYSCVTDATE, "03/19/2012 07:29:59");

		expectedMenuOptions.add(kundendienstTransfer());
		expectedMenuOptions.add(stoerungTransfer());

		menuOptions = droolsImpl.getExtendedMenuOptions(ONEIVRRES_ANLIEGEN_MENU_MOBILE_CBU, customerProfile, customerProducts, customerProductClusters,
				callProfile, "g", serviceConfiguration);

		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}

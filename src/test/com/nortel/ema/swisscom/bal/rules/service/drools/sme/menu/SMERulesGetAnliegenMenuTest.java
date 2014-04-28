/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesGetAnliegenMenuTest.java 113 2013-09-11 09:52:51Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-11 11:52:51 +0200 (Wed, 11 Sep 2013) $
 * $Revision: 113 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.menu;


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
public class SMERulesGetAnliegenMenuTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULESFILE = RULES_SME_ANLIEGENMENU;

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

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(SMERulesGetAnliegenMenuTest.class);
	}

	private static final ExtendedMenuOption administration() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ADMINISTRATION");
		option.setPrompt("alm-admin");
		option.setEmergencyText("SF");
		option.setAction("MENU");
		return option;
	} 

	private static final ExtendedMenuOption administrationTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("ADMINISTRATION");
		option.setPrompt("alm-admin");
		option.setEmergencyText("SF");
		option.setAction("TRANSFER");
		return option;
	} 

	private static final ExtendedMenuOption stoerung() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("STOERUNG");
		option.setPrompt("alm-techsupport");
		option.setEmergencyText("SA");
		option.setAction("MENU");
		return option;
	} 

	private static final ExtendedMenuOption stoerungTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("STOERUNG");
		option.setPrompt("alm-techsupport");
		option.setEmergencyText("SA");
		option.setAction("TRANSFER");
		return option;
	} 

	private static final ExtendedMenuOption beratungSAL() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("BERATUNG");
		option.setPrompt("alm-beratung");
		option.setEmergencyText("BERATUNG");
		option.setAction("SAL");
		return option;
	} 

	private static final ExtendedMenuOption beratungTransfer() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("BERATUNG");
		option.setPrompt("alm-beratung");
		option.setEmergencyText("BERATUNG");
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
		callProfile.setBusinessnumber(BUSINESSNUMBER_0800055055);
	}	

	public final void testAnliegenMenu_0800055055_SME_NO_DSLMOBILE_BORDER() throws Exception {

		customerProfile.setSegment(SEGMENT_SME);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 07:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_0800055055_SME_NO_DSLMOBILE_NO_BORDER() throws Exception {

		customerProfile.setSegment(SEGMENT_SME);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 08:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungSAL());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_0800055055_SME_DSLMOBILE_BORDER() throws Exception {

		customerProfile.setSegment(SEGMENT_SME);

		customerProducts.add(PR_DSL_MOBILE);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 07:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_0800055055_SME_DSLMOBILE_NO_BORDER() throws Exception {

		customerProfile.setSegment(SEGMENT_SME);

		customerProducts.add(PR_DSL_MOBILE);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 08:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungSAL());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_CareManager_BORDER() throws Exception {

		customerProfile.setSa1_ntAccount("CARMAN_SG1");
		customerProfile.setSegment(SEGMENT_SME);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 07:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administrationTransfer());
		expectedMenuOptions.add(stoerungTransfer());
		expectedMenuOptions.add(beratungTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_CareManager_NO_BORDER() throws Exception {

		customerProfile.setSa1_ntAccount("CARMAN_SG1");
		customerProfile.setSegment(SEGMENT_SME);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 08:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administrationTransfer());
		expectedMenuOptions.add(stoerungTransfer());
		expectedMenuOptions.add(beratungSAL());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_FALLBACK_NO_DSLMOBILE_BORDER() throws Exception {

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 07:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_FALLBACK_NO_DSLMOBILE_NO_BORDER() throws Exception {

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 08:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungSAL());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_FALLBACK_DSLMOBILE_BORDER() throws Exception {

		customerProducts.add(PR_DSL_MOBILE);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 07:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungTransfer());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}

	public final void testAnliegenMenu_FALLBACK_DSLMOBILE_NO_BORDER() throws Exception {

		customerProducts.add(PR_DSL_MOBILE);

		callProfile.put(CPK_SYSCVTDATE, "06/20/2012 08:30:00");

		menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILE, customerProfile, customerProducts, customerProductClusters, callProfile,
				"g", serviceConfiguration);

		expectedMenuOptions.add(administration());
		expectedMenuOptions.add(stoerung());
		expectedMenuOptions.add(beratungSAL());
		compareExpectedToActual(expectedMenuOptions, menuOptions);
	}
}


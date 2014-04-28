/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesState0800566566Test.java 79 2013-09-09 15:24:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:24:23 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 79 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.rules.model.StateEngineRulesResult;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesState0800566566Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static String RULES_FILE_NAME = "oneIVR/vp5res-0800566566-state";
	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
	private static ArrayList<String> genericList;

	private static ServiceConfigurationMap serviceConfigurationMap;


	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesState0800566566Test.class);
	}

	protected void setUp() {
		// Create objects
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
		genericList = new ArrayList<String>();
	}

	public final void testID010_Init_CheckOH() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_M-PARTNER");
		expectedResult.setNextState("AfterInit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020a_AfterInit_NoAniProceed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AfterInit");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020b_AfterInit_GotAniSetPNFromSession() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AfterInit");
		callProfile.put(CPK_ANI, "some-value");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("AutoLangSelect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030a_AutoLangSelect_SetCustomerLang() throws Exception {

		genericList.add("g");
		genericList.add("f");
		genericList.add("i");
		genericList.add("e");

		for (String language: genericList) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_LANGUAGE, language);

			customerProfile.setSegment(SEGMENT_RES);
			customerProfile.setLanguageSpoken(language);

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1(language);
			expectedResult.setNextState("Welcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030b_AutoLangSelect_SetMultilingual() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
		callProfile.put(CPK_SINGLELANGUAGE, "false");

		expectedResult.setAction(SE_ACTION_SETLANG);
		expectedResult.setOutputVar1("ml");
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040a_Welcome_SingleLanguage_PlayWelcomeTrue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Migros-welcome");
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040b_Welcome_SingleLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c_Welcome_MultiLanguage_PlayWelcomeTrue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Migros-Welcome-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040d_Welcome_MultiLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050a_LangSelect_open() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LanguageSelection");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050b_LangSelect_closed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LanguageSelection");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("SpeakClosedBye");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060a_EmergencyAnnouncements_Set_open() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "EmergencyAnnouncements");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("RES");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060b_EmergencyAnnouncements_Set_closed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "EmergencyAnnouncements");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("SpeakClosedBye");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070_EmergencyAnnouncements_Play() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "PlayEmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("MPartnerTransfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080_MPartnerTransfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "MPartnerTransfer");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_SpeakClosedBye() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SpeakClosedBye");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("closed-bye");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Disconnect");

		expectedResult.setAction(SE_ACTION_DISCONNECT);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID201_GoodbyeDisconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GoodbyeDisconnect");

		expectedResult.setAction(SE_ACTION_GOODBYE);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("oneIVR/vp5resTransfer");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID900_InputError() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "InputError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID901_ConnectorError() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "ConnectorError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID902_TechError() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "TechError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID998_FALLBACK_TRANSFER() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "UnknownState");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}

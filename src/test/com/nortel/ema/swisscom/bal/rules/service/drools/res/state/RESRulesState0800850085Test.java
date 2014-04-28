/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesState0800850085Test.java 237 2014-04-16 11:34:27Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-16 13:34:27 +0200 (Wed, 16 Apr 2014) $
 * $Revision: 237 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.rules.model.StateEngineRulesResult;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesState0800850085Test extends TestCase {

	private static final String RULES_FILE_NAME = "oneIVR/vp5res-0800850085-state";

	private static RulesServiceDroolsImpl droolsImpl;
	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
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
		junit.textui.TestRunner.run(RESRulesState0800850085Test.class);
	}

	protected void setUp() {
		// Create objects
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		callProfile.put(CPK_BUSINESSTYP, RES);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800850085);
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
	}

	public final void testID010a_Init_NoAniJingle() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("jingle");
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010b_Init_GotAniSetPNFromSession() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");
		callProfile.put(CPK_ANI, "some-value");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("Welcome");		

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

public final void testID020a_AutoLangSelect_SetRegionLang() throws Exception {

		ArrayList<String> languages = new ArrayList<String>();
		languages.add("g");
		languages.add("f");
		languages.add("i");
		languages.add("e");

		for (String lang: languages) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_LANGUAGE, lang);

			customerProfile.setSegment(SEGMENT_RES);
			customerProfile.setLanguageSpoken(lang);

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1(lang);
			expectedResult.setNextState("Welcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID020b_AutoLangSelect_SetMultilingual() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
		callProfile.put(CPK_SINGLELANGUAGE, "false");

		expectedResult.setAction(SE_ACTION_SETLANG);
		expectedResult.setOutputVar1("ml");
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID030_Welcome_OpeningHours() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Welcome");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_WELCOME");
		expectedResult.setNextState("WelcomeActual");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID040a1_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Morning() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("morning");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-morning");
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040a2_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Day() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("day");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-day");
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040a3_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Evening() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("evening");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-evening");
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040a4_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Default() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-general");
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040b_WelcomeActual_SingleLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c1_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Morning() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("morning");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-morning-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c2_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Day() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("day");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-day-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c3_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Evening() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("evening");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-evening-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c4_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Default() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-general-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040d_WelcomeActual_MultiLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050_LangSelect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID060_EmergencyAnnouncements() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "EmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("RES");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070_PlayEmergencyAnnouncements() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "PlayEmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("MenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080_THL_MenuLevel1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "MenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresTHLMenu");
		expectedResult.setOutputVar3("oneIVR/vp5resAnliegenMenuTHL");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckMenuLevel1Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090a_CheckMenuLevel1Response_Option1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put("oneIVRresTHLMenu", "THL1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("PhoneNumberInput");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID090b_CheckMenuLevel1Response_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID100_PhoneNumberInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "PhoneNumberInput");

		expectedResult.setAction(SE_ACTION_ENTERPN);
		expectedResult.setOutputVar1("enterPhoneNumber-THL");
		expectedResult.setNextState("Transfer");

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

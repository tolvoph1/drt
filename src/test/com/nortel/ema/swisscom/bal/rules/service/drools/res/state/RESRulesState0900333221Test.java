/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesState0900333221Test.java 132 2013-10-18 07:01:24Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-10-18 09:01:24 +0200 (Fri, 18 Oct 2013) $
 * $Revision: 132 $
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
public class RESRulesState0900333221Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULES_FILE_NAME = "oneIVR/vp5res-0900333221-state";

	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
	private static ServiceConfigurationMap serviceConfigurationMap;
	ArrayList<String> aniList;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesState0900333221Test.class);
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
		callProfile.setBusinessnumber(BUSINESSNUMBER_0900333221);
		callProfile.setBusinesstyp(CBU);
		aniList = new ArrayList<String>();
	}

	public final void testID010a_Init_NoAniPlayJingle() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Init");

		aniList.add("0");
		aniList.add("");

		for (String ani: aniList) {

			callProfile.put(CPK_ANI, ani);

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("jingle");
			expectedResult.setNextState("Welcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID010b_Init_GotAniSetPNFromSession() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Init");
		callProfile.put(CPK_ANI, "0621324567");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020_Welcome_OpeningHours() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_WELCOME");
		expectedResult.setNextState("WelcomeActual");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0030a1_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Morning() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("morning");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-morning");
		expectedResult.setNextState("CheckANI");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0030a2_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Day() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("day");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-day");
		expectedResult.setNextState("CheckANI");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0030a3_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Evening() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");
		callProfile.setResultOH("evening");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-evening");
		expectedResult.setNextState("CheckANI");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0030a4_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Default() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-general");
		expectedResult.setNextState("CheckANI");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0030b_WelcomeActual_SingleLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckANI");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0030c1_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Morning() throws Exception {

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

	public final void testID0030c2_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Day() throws Exception {

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

	public final void testID0030c3_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Evening() throws Exception {

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

	public final void testID0030c4_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Default() throws Exception {

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

	public final void testID0030d_WelcomeActual_MultiLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020_LanguageSelection() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"LanguageSelection");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setNextState("CheckANI");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050a_CheckANI_ANI_Unknown_AskForPhonenumber() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckANI");
		callProfile.put(CPK_ANI,"");

		expectedResult.setAction(SE_ACTION_ENTERPN);
		expectedResult.setNextState("GetResultCustomerInformation");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050b_CheckANI_ANI_Known_Proceed() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckANI");
		callProfile.put(CPK_ANI,"0621111111");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetResultCustomerInformation");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060_GetResultCustomerInformation() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"GetResultCustomerInformation");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("EmergencyAnnouncements");	

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070_EmergencyAnnouncements_Set() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"EmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("RES-DAS");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080_EmergencyAnnouncements_Play() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"PlayEmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("oneIVR/vp5resTransfer");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Disconnect");

		expectedResult.setAction(SE_ACTION_DISCONNECT);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID998_FALLBACK_TRANSFER() throws Exception {

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}
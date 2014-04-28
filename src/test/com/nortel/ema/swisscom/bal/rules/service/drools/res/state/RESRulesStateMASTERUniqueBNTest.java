/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesStateMASTERUniqueBNTest.java 164 2014-01-16 10:29:30Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-01-16 11:29:30 +0100 (Thu, 16 Jan 2014) $
 * $Revision: 164 $
 * 
 * JUnit Testklasse fuer Unique Businessnummern, Testet alle unten aufgefuehrten BNs
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import java.util.ArrayList;
import java.util.Arrays;

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
public class RESRulesStateMASTERUniqueBNTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"oneIVR/vp5res-MASTER-uniqueBN-state",
					"oneIVR/vp5res-0800800832-state",
					"oneIVR/vp5res-0800800975-state",
					"oneIVR/vp5res-0848800175-state",
					"oneIVR/vp5res-0848800811-state"
					));

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

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesStateMASTERUniqueBNTest.class);
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
	}

	public final void testID010a_Init_NoAniPlayJingle() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");

			ArrayList<String> aniList = new ArrayList<String>();
			aniList.add("0");
			aniList.add("");

			ArrayList<String> segmentList = new ArrayList<String>();
			segmentList.add(SEGMENT_CBU);
			segmentList.add(SEGMENT_SME);
			segmentList.add(SEGMENT_RES);
			segmentList.add("0");
			segmentList.add("");

			for (String segment: segmentList) {
				for (String ani: aniList) {

					callProfile.put(CPK_ANI, ani);
					customerProfile.setSegment(segment);

					expectedResult.setAction(SE_ACTION_SPEAK);
					expectedResult.setOutputVar1("jingle");
					expectedResult.setNextState("Welcome");

					actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

					checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
				}
			}
		}
	}

	public final void testID010b_Init_GotAniSetPNFromSession() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");
			callProfile.put(CPK_ANI, "0621324567");

			ArrayList<String> segmentList = new ArrayList<String>();
			segmentList.add(SEGMENT_CBU);
			segmentList.add(SEGMENT_SME);
			segmentList.add(SEGMENT_RES);
			segmentList.add("0");
			segmentList.add("");

			for (String segment: segmentList) {

				customerProfile.setSegment(segment);

				expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
				expectedResult.setOutputVar1("true");
				expectedResult.setNextState("AutoLangSelect");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID020a_AutoLangSelect_SetCustomerLangRES() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			ArrayList<String> languages = new ArrayList<String>();
			languages.add("g");
			languages.add("f");
			languages.add("i");
			languages.add("e");

			for (String lang: languages) {

				callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
				callProfile.put(CPK_SINGLELANGUAGE,"true");
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
	}

	public final void testID020b_AutoLangSelect_SetMultilingual() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1("ml");
			expectedResult.setNextState("Welcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030_Welcome_OpeningHours() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome");

			expectedResult.setAction(SE_ACTION_CHECKOH);
			expectedResult.setOutputVar1("OH_RES_WELCOME");
			expectedResult.setNextState("WelcomeActual");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID040a1_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Morning() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.put(CPK_SINGLELANGUAGE, "true");
			callProfile.put(CPK_PLAYWELC, "true");
			callProfile.setResultOH("morning");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-morning");
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID040a2_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Day() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.put(CPK_SINGLELANGUAGE, "true");
			callProfile.put(CPK_PLAYWELC, "true");
			callProfile.setResultOH("day");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-day");
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID040a3_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Evening() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.put(CPK_SINGLELANGUAGE, "true");
			callProfile.put(CPK_PLAYWELC, "true");
			callProfile.setResultOH("evening");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-evening");
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID040a4_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Default() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.put(CPK_SINGLELANGUAGE, "true");
			callProfile.put(CPK_PLAYWELC, "true");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-general");
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID040b_WelcomeActual_SingleLanguage_PlayWelcomeFalse() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.put(CPK_SINGLELANGUAGE, "true");
			callProfile.put(CPK_PLAYWELC, "false");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID040c1_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Morning() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}
	
	public final void testID040c2_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Day() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}
	
	public final void testID040c3_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Evening() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}
	
	public final void testID040c4_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Default() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}

	public final void testID040d_WelcomeActual_MultiLanguage_PlayWelcomeFalse() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_PLAYWELC, "false");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("LanguageSelection");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID050_LangSelect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

			expectedResult.setAction(SE_ACTION_LANGSELECT);
			expectedResult.setNextState("CheckLangSelectResponse");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID060a_CheckLangSelectResponse_Continue() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckLangSelectResponse");
			callProfile.put(CPK_ACTIONRESPONSE, "DONE");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID060b_CheckLangSelectResponse_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckLangSelectResponse");
			callProfile.put(CPK_ACTIONRESPONSE, "InputError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID070_OpeningHoursGlobal() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "OpeningHoursGlobal");

			expectedResult.setAction(SE_ACTION_CHECKOH);
			expectedResult.setOutputVar1("OH_RES_Global");
			expectedResult.setNextState("CheckOHGlobalResult");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID080a_CheckOHGlobalResult_Closed() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOHGlobalResult");
			callProfile.put(CPK_RESULTOH, "1");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("closed-bye");
			expectedResult.setNextState("Disconnect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID080b_CheckOHGlobalResult_Open() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOHGlobalResult");
			callProfile.put(CPK_RESULTOH, "100");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("CustomerIdentification");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID090a_CustID_Ausland() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_INTERNATIONAL, "true");

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID090b_CustID_NoAniNotAusland() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_INTERNATIONAL, "false");

			expectedResult.setAction(SE_ACTION_ENTERPN);
			expectedResult.setNextState("CheckEnteredPhoneNumber");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID090c_CustID_UnknownAniNotAusland() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_INTERNATIONAL, "false");
			callProfile.put(CPK_ANI, "some-value");

			expectedResult.setAction(SE_ACTION_ENTERPN);
			expectedResult.setNextState("CheckEnteredPhoneNumber");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID090d_CustID_GotAniNotAusland_SME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_ANI, "some-value");
			callProfile.put(CPK_INTERNATIONAL, "false");

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setCustomerID("1234");

			expectedResult.setAction(SE_ACTION_CUSTIDENT);
			expectedResult.setOutputVar1("SME");
			expectedResult.setNextState("CheckResultCustIdent");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID090e_CustID_GotAniNotAusland_CBU() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_ANI, "some-value");
			callProfile.put(CPK_INTERNATIONAL, "false");

			customerProfile.setSegment(SEGMENT_CBU);
			customerProfile.setCustomerID("1234");

			expectedResult.setAction(SE_ACTION_CUSTIDENT);
			expectedResult.setOutputVar1("CBU");
			expectedResult.setNextState("CheckResultCustIdent");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID090f_CustID_GotAniNotAusland_Others() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_ANI, "some-value");
			callProfile.put(CPK_INTERNATIONAL, "false");

			customerProfile.setSegment(SEGMENT_RES);
			customerProfile.setCustomerID("1234");

			expectedResult.setAction(SE_ACTION_CUSTIDENT);
			expectedResult.setNextState("CheckResultCustIdent");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID100a_CheckResultCustIdent_ConfirmedMobileCBU() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
			callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireless");

			customerProfile.setSegment(SEGMENT_CBU);

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("CheckSegment");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID100b_CheckResultCustIdent_Confirmed() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
			callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("CheckSegment");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID100c_CheckResultCustIdent_Rejected() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
			callProfile.put(CPK_ACTIONRESPONSE, "ANIrejected");

			expectedResult.setAction(SE_ACTION_ENTERPN);
			expectedResult.setNextState("CheckEnteredPhoneNumber");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID110a_CheckEnteredPhoneNumber_FailedOrCancelled() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckEnteredPhoneNumber");

			ArrayList<String> actionResponses = new ArrayList<String>();
			actionResponses.add("FAILED");
			actionResponses.add("CANCELLED");

			for (String actionResponse: actionResponses) {

				callProfile.put(CPK_ACTIONRESPONSE, actionResponse);

				expectedResult.setAction(SE_ACTION_PROCEED);
				expectedResult.setNextState("Transfer");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID110b_CheckEnteredPhoneNumber_Ok() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckEnteredPhoneNumber");
			callProfile.put(CPK_ACTIONRESPONSE, "DONE");

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("CheckSegment");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID120aCheckSegment_MobileCBU() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckSegment");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireless");

			customerProfile.setSegment(SEGMENT_CBU);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}	
	}

	public final void testID120b_CheckSegment_SME_TransferRID() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckSegment");
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800832);
			callProfile.put(CPK_CONNECTIONTYPE, "Wireline");

			customerProfile.setSegment(SEGMENT_SME);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID120c1_CheckSegment_SME_SpeakPrompt_Then_TransferCallflow() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckSegment");

			customerProfile.setSegment(SEGMENT_SME);

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("AuskreuzungRESzuSME");
			expectedResult.setNextState("TransferCallflowSME");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID120c2_TransferCallflowSME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "TransferCallflowSME");

			expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
			expectedResult.setOutputVar1("SME");
			expectedResult.setNextState("Disconnect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID120d_CheckSegment_CBU_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckSegment");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireline");

			customerProfile.setSegment(SEGMENT_CBU);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID120e_CheckSegment_Others_Continue() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckSegment");

			customerProfile.setSegment(SEGMENT_RES);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID130a_Info_TarifInfo() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Info");
			callProfile.put(CPK_INTERNATIONAL, "true");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("ausland-tarif");
			expectedResult.setNextState("BusinessNumberAction");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID130b_Info_Free() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Info");
			callProfile.put(CPK_INTERNATIONAL, "false");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("0800800800-info");
			expectedResult.setNextState("BusinessNumberAction");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID140a_BusinessNumberAction_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "BusinessNumberAction");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID140b_BusinessNumberAction_0800803175() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "BusinessNumberAction");
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800803175);

			expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
			expectedResult.setOutputVar1("oneIVRresBNMenu");
			expectedResult.setOutputVar3("oneIVR/vp5resAnliegenMenu0800803175");
			expectedResult.addToOutputColl1("select", "alm-menue-please-select");
			expectedResult.setNextState("CheckBusinessNumberMenuResponse");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID150a_BusinessNumberMenuResponse_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckBusinessNumberMenuResponse");
			callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID150b_BusinessNumberMenuResponse_Disconnect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckBusinessNumberMenuResponse");
			callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("GoodbyeDisconnect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID150c_BusinessNumberMenuResponse_Wireline() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckBusinessNumberMenuResponse");
			callProfile.put(CPK_ACTIONRESPONSE, "WIRELINE");

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-wireline-state");
			expectedResult.setOutputVar2("Wireline");
			expectedResult.setNextState("InitWirelinePers");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID200_Disconnect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Disconnect");

			expectedResult.setAction(SE_ACTION_DISCONNECT);

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID201_GoodbyeDisconnect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GoodbyeDisconnect");

			expectedResult.setAction(SE_ACTION_GOODBYE);

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID210_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Transfer");

			expectedResult.setAction(SE_ACTION_TRANSFER);
			expectedResult.setOutputVar1("oneIVR/vp5resTransfer");
			expectedResult.setNextState("Disconnect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID900_InputError() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "InputError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID901_ConnectorError() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "ConnectorError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID902_TechError() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "TechError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID998_FALLBACK_TRANSFER() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "UnknownState");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
}

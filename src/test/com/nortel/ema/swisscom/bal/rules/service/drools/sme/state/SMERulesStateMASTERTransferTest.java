/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesStateMASTERTransferTest.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.state;

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
/**
 * 
 * Test Class needs to run for all rules files that this master file is copied to:
 * 0443064646
 * 0443069405
 * 0443069500
 * 0800001684
 * 0900111848
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMERulesStateMASTERTransferTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"sme/vp5sme-MASTER-transfer-state",
					"sme/vp5sme-0443064646-state",
					"sme/vp5sme-0443069405-state",
					"sme/vp5sme-0443069500-state",
					"sme/vp5sme-0800001684-state",
					"sme/vp5sme-0900111848-state"
					));

	private static ServiceConfigurationMap serviceConfigurationMap;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static ArrayList<String> miscList;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
	private static ArrayList<String> segments;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(SMERulesStateMASTERTransferTest.class);
	}

	protected void setUp() {
		serviceConfigurationMap = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		miscList = new ArrayList<String>();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
		segments = new ArrayList<String>();
	}

	public final void testID010a_Init_NoAniPlayJingle() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");

			miscList.add("0");
			miscList.add("");
			miscList.add(null);

			for (String ani: miscList) {				

				callProfile.put(CPK_ANI, ani);

				expectedResult.setAction(SE_ACTION_SPEAK);
				expectedResult.setOutputVar1("jingle");
				expectedResult.setNextState("Welcome");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID010b_Init_GotAniSetPNFromSession() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");
			callProfile.put(CPK_ANI, "0621234567");

			expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
			expectedResult.setOutputVar1("true");
			expectedResult.setNextState("AutoLangSelect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID020a_AutoLangSelect_Wireline_multiLanguageRegion() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1("g");
			expectedResult.setNextState("Welcome-MultiLanguage");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID020b_AutoLangSelect_Wireline_singleLanguageRegion() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "true");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

			miscList.add("g");
			miscList.add("f");
			miscList.add("i");
			miscList.add("e");

			for (String lang: miscList) {
				callProfile.put(CPK_LANGUAGE, lang);

				expectedResult.setAction(SE_ACTION_SETLANG);
				expectedResult.setOutputVar1(lang);
				expectedResult.setNextState("Welcome-SingleLanguage");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID020c_AutoLangSelect_unknown_singleLanguageRegion() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "true");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_UNKNOWN);

			miscList.add("g");
			miscList.add("f");
			miscList.add("i");
			miscList.add("e");

			for (String lang: miscList) {
				callProfile.put(CPK_LANGUAGE, lang);

				expectedResult.setAction(SE_ACTION_SETLANG);
				expectedResult.setOutputVar1(lang);
				expectedResult.setNextState("Welcome-SingleLanguage");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID020d_AutoLangSelect_unknown_multiLanguageRegion() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_UNKNOWN);

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1("g");
			expectedResult.setNextState("Welcome-MultiLanguage");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID020e_AutoLangSelect_Wireless_multiLanguageRegion_haveCliDataLanguage() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

			miscList.add("g");
			miscList.add("f");
			miscList.add("i");
			miscList.add("e");

			for (String lang: miscList) {
				callProfile.put(CPK_CLIDATA_LANGUAGE, lang);

				expectedResult.setAction(SE_ACTION_SETLANG);
				expectedResult.setOutputVar1(lang);
				expectedResult.setNextState("Welcome-SingleLanguage");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID020f_AutoLangSelect_Wireless_multiLanguageRegion_RES_haveSpokenLanguage() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

			customerProfile.setSegment(SEGMENT_RES);

			miscList.add("g");
			miscList.add("f");
			miscList.add("i");
			miscList.add("e");

			for (String lang: miscList) {
				customerProfile.setLanguageSpoken(lang);

				expectedResult.setAction(SE_ACTION_SETLANG);
				expectedResult.setOutputVar1(lang);
				expectedResult.setNextState("Welcome-SingleLanguage");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID020g_AutoLangSelect_Wireless_multiLanguageRegion_SME_CBU_haveSpokenLanguage() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

			miscList.add("g");
			miscList.add("f");
			miscList.add("i");
			miscList.add("e");

			segments.add(SEGMENT_SME);
			segments.add(SEGMENT_CBU);

			for (String lang: miscList) {
				for (String segment: segments) {
					customerProfile.setLanguageSpoken(lang);
					customerProfile.setSegment(segment);

					expectedResult.setAction(SE_ACTION_SETLANG);
					expectedResult.setOutputVar1(lang);
					expectedResult.setNextState("Welcome-MultiLanguage");

					actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

					checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
				}
			}
		}
	}

	public final void testID020h_AutoLangSelect_Wireless_multiLanguageRegion_noSpokenLanguage() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

			miscList.add("");
			miscList.add(null);

			segments.add(SEGMENT_SME);
			segments.add(SEGMENT_CBU);
			segments.add(SEGMENT_RES);
			segments.add("");
			segments.add(null);

			for (String lang: miscList) {
				for (String segment: segments) {
					customerProfile.setLanguageSpoken(lang);
					customerProfile.setSegment(segment);

					expectedResult.setAction(SE_ACTION_SETLANG);
					expectedResult.setOutputVar1("g");
					expectedResult.setNextState("Welcome-MultiLanguage");

					actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

					checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
				}
			}
		}
	}

	public final void testID030a_Welcome_SingleLanguage_PlayWelcomeTrue() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome-SingleLanguage");
			callProfile.put(CPK_PLAYWELC, "true");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome");
			expectedResult.setNextState("CustomerIdentification");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030b_Welcome_SingleLanguage_PlayWelcomeFalse() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome-SingleLanguage");
			callProfile.put(CPK_PLAYWELC, "false");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("CustomerIdentification");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030a_Welcome_MultiLanguage_PlayWelcomeTrue() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome-MultiLanguage");
			callProfile.put(CPK_PLAYWELC, "true");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("Welcome-Multilingual");
			expectedResult.setNextState("LanguageSelection");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030b_Welcome_MultiLanguage_PlayWelcomeFalse() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome-MultiLanguage");
			callProfile.put(CPK_PLAYWELC, "false");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("LanguageSelection");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID040_LanguageSelection() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

			expectedResult.setAction(SE_ACTION_LANGSELECT);
			expectedResult.setOutputVar1("false");
			expectedResult.setNextState("CustomerIdentification");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID050a_CustID_NoAni() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");

			miscList.add(null);
			miscList.add("0");
			miscList.add("");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PhoneNumberInput");

			for (String thisAni: miscList) {

				callProfile.put(CPK_ANI,thisAni);

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID050b_CustID_GotAni_SME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_ANI,"0791234567");

			customerProfile.setSegment(SEGMENT_SME);

			expectedResult.setAction(SE_ACTION_CUSTIDENT);
			expectedResult.setOutputVar2("SME");
			expectedResult.setNextState("CheckResultCustIdent");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID050c_CustID_GotAni_CBU() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_ANI,"0791234567");

			customerProfile.setSegment(SEGMENT_CBU);

			expectedResult.setAction(SE_ACTION_CUSTIDENT);
			expectedResult.setOutputVar2("SME");
			expectedResult.setNextState("CheckResultCustIdent");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID050d_CustID_GotAni_Others() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_ANI,"0791234567");

			segments.add(SEGMENT_RES);
			segments.add("");
			segments.add(null);

			expectedResult.setAction(SE_ACTION_CUSTIDENT);
			expectedResult.setOutputVar2("SME");
			expectedResult.setNextState("CheckResultCustIdent");

			for (String segment: segments) {

				customerProfile.setSegment(segment);

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID060a_CheckResultCustIdent_Confirmed() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
			callProfile.put(CPK_ACTIONRESPONSE, ACTIONRESPONSE_ANICONFIRMED);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("GetResultCustInfo");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID060b_CheckResultCustIdent_Rejected() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
			callProfile.put(CPK_ACTIONRESPONSE, ACTIONRESPONSE_ANIREJECTED);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PhoneNumberInput");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID070_PhoneNumberInput() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PhoneNumberInput");

			expectedResult.setAction(SE_ACTION_ENTERPN);
			expectedResult.setOutputVar1("enterPhoneNumber");
			expectedResult.setNextState("GetResultCustInfo");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID080_GetResultCustInfo() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GetResultCustInfo");

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("GetINAS");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID090_GetINAS() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GetINAS");

			expectedResult.setAction(SE_ACTION_SETINASACTIONS);
			expectedResult.setOutputVar1("SME");
			expectedResult.setNextState("PlayINAS");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID100_PlayINAS_MAIN() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PlayINAS");

			expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
			expectedResult.setOutputVar1("MAIN");
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID110_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Transfer");

			expectedResult.setAction(SE_ACTION_TRANSFER);
			expectedResult.setOutputVar1("sme/vp5smeTransfer");
			expectedResult.setNextState("Disconnect");

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

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
}

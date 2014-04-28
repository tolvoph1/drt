/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesState0800055055Test.java 183 2014-02-19 14:53:49Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-02-19 15:53:49 +0100 (Wed, 19 Feb 2014) $
 * $Revision: 183 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.state;

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
public class SMERulesState0800055055Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static String RULES_FILE_NAME = "sme/vp5sme-0800055055-state";

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

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(SMERulesState0800055055Test.class);
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

		callProfile.put(CPK_NEXTSTATE, "Init");

		miscList.add("0");
		miscList.add("");
		miscList.add(null);

		for (String ani: miscList) {

			callProfile.put(CPK_ANI, ani);

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("jingle");
			expectedResult.setNextState("Welcome-MultiLanguage");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID010b_Init_GotAniSetPNFromSession() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");
		callProfile.put(CPK_ANI, "0621234567");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("SetINASPilotSprachportal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010c_Seiteneinstieg() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Seiteneinstieg");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetResultCustInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID012_SetINASPilotSprachportal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SetINASPilotSprachportal");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("SME-PILOT");
		expectedResult.setNextState("ExecINASPilotSprachportal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID014_ExecINASPilotSprachportal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "ExecINASPilotSprachportal");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("CheckPilotSpracherkennung");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID016a_CheckPilotSpracherkennung_PilotCaller() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckPilotSpracherkennung");
		callProfile.put(CPK_GENERICINASACTION, "PilotSpracherkennung");

		expectedResult.setAction(SE_ACTION_SWITCHRULES);
		expectedResult.setOutputVar1("oneIVR/vp5res-sme-Pilot-Spracherkennung-state");
		expectedResult.setNextState("Init");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID016b_CheckPilotSpracherkennung_AllOthers() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckPilotSpracherkennung");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AutoLangSelect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020a_AutoLangSelect_Wireline_multiLanguageRegion() throws Exception {

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

	public final void testID020b_AutoLangSelect_Wireline_singleLanguageRegion() throws Exception {

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

	public final void testID020c_AutoLangSelect_unknown_singleLanguageRegion() throws Exception {

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

	public final void testID020d_AutoLangSelect_unknown_multiLanguageRegion() throws Exception {

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

	public final void testID020e_AutoLangSelect_Wireless_multiLanguageRegion_haveCliDataLanguage() throws Exception {

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

	public final void testID020f_AutoLangSelect_Wireless_multiLanguageRegion_RES_haveSpokenLanguage() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
		callProfile.put(CPK_SINGLELANGUAGE, "false");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		miscList.add("g");
		miscList.add("f");
		miscList.add("i");
		miscList.add("e");

		customerProfile.setSegment(SEGMENT_RES);

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

	public final void testID020g_AutoLangSelect_Wireless_multiLanguageRegion_SME_CBU_haveSpokenLanguage() throws Exception {

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

	public final void testID020h_AutoLangSelect_Wireless_multiLanguageRegion_noSpokenLanguage() throws Exception {

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

	public final void testID020i_AutoLangSelect_FALLBACK() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		customerProfile.setLanguageSpoken("");

		expectedResult.setAction(SE_ACTION_SETLANG);
		expectedResult.setOutputVar1("g");
		expectedResult.setNextState("Welcome-MultiLanguage");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030a_Welcome_SingleLanguage_PlayWelcomeTrue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome-SingleLanguage");
		callProfile.put(CPK_PLAYWELC, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome");
		expectedResult.setNextState("CustomerIdentification");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030b_Welcome_SingleLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome-SingleLanguage");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CustomerIdentification");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030c_Welcome_MultiLanguage_PlayWelcomeTrue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome-MultiLanguage");
		callProfile.put(CPK_PLAYWELC, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Welcome-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030d_Welcome_MultiLanguage_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Welcome-MultiLanguage");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040_LanguageSelection() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("CustomerIdentification");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050a_CustID_NoAni() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");

		miscList.add(null);
		miscList.add("0");
		miscList.add("");

		for (String thisAni: miscList) {

			callProfile.put(CPK_ANI,thisAni);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PhoneNumberInput");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID050b_CustID_GotAni_SME() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_ANI,"0791234567");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetResultCustInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050c_CustID_GotAni_CBU() throws Exception {

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

	public final void testID050d_CustID_GotAni_Others() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_ANI,"0791234567");

		segments.add(SEGMENT_RES);
		segments.add("");
		segments.add(null);

		for (String segment: segments) {

			customerProfile.setSegment(segment);

			expectedResult.setAction(SE_ACTION_CUSTIDENT);
			expectedResult.setOutputVar2("SME");
			expectedResult.setNextState("CheckResultCustIdent");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID060a_CheckResultCustIdent_Confirmed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, ACTIONRESPONSE_ANICONFIRMED);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetResultCustInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060b_CheckResultCustIdent_Rejected() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, ACTIONRESPONSE_ANIREJECTED);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("PhoneNumberInput");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070_PhoneNumberInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "PhoneNumberInput");

		expectedResult.setAction(SE_ACTION_ENTERPN);
		expectedResult.setOutputVar1("enterPhoneNumber");
		expectedResult.setOutputVar2("true");
		expectedResult.setNextState("GetResultCustInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080a_GetResultCustInfo() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GetResultCustInfo");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("CheckCustomerType");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090a_CheckCustomerType_RES_or_EASY_RES() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");

		customerProfile.setSegment(SEGMENT_RES);

		expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
		expectedResult.setOutputVar1("RES");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090a_CheckCustomerType_RES_or_EASY_EASY() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");

		customerProfile.setSegment("");
		customerProfile.setSourceCode("PPB");
		customerProfile.setTypeCode("Prepaid Subscription");

		expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
		expectedResult.setOutputVar1("RES");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090b_CheckCustomerType_CBU_SwisscomCaller() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");

		customerProfile.setSegment(SEGMENT_CBU);
		customerProfile.setLastName("Swisscom Hotline");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetINAS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090c_CheckCustomerType_CBU_non_SwisscomCaller() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");

		customerProfile.setSegment(SEGMENT_CBU);
		customerProfile.setLastName("Stiftung Wohnen im Alter");

		expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
		expectedResult.setOutputVar1("CBU");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090d_CheckCustomerType_others() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetINAS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_GetINAS() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GetINAS");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("SME");
		expectedResult.setNextState("CheckForOptionOffer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110a_CheckForOptionOffer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckForOptionOffer");

		customerProfile.setCustomerID("0815");
		callProfile.setPhonenumber("+41622865075");

		expectedResult.setAction(SE_ACTION_CHECKOPTIONOFFER);
		expectedResult.addToOutputColl1("scn", customerProfile.getCustomerID());
		expectedResult.addToOutputColl1("optionOffer", "KMU Office W+C 4Star");
		expectedResult.addToOutputColl1("phonenumber", "+41622865075");
		expectedResult.setNextState("SwitchRules");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110b_CheckForOptionOffer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckForOptionOffer");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("SwitchRules");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120a_SwitchRules_WirelinePersonalisation() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SwitchRules");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		expectedResult.setAction(SE_ACTION_SWITCHRULES);
		expectedResult.setOutputVar1("sme/vp5sme-wireline-state");
		expectedResult.setOutputVar2("Wireline");
		expectedResult.setNextState("InitWirelinePers");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120b_SwitchRules_WirelessPersonalisation() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SwitchRules");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		CustomerProductClusters customerProductClusters = new CustomerProductClusters();

		expectedResult.setAction(SE_ACTION_SWITCHRULES);
		expectedResult.setOutputVar1("sme/vp5sme-wireless-state");
		expectedResult.setOutputVar2("Wireless");
		expectedResult.setNextState("InitWirelessPers");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120c_SwitchRules_OthersPersonalisation() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SwitchRules");

		expectedResult.setAction(SE_ACTION_SWITCHRULES);
		expectedResult.setOutputVar1("sme/vp5sme-wireline-state");
		expectedResult.setOutputVar2("Wireline");
		expectedResult.setNextState("InitWirelinePers");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID500_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("sme/vp5smeTransfer");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID600_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Disconnect");

		expectedResult.setAction(SE_ACTION_DISCONNECT);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID601_GoodbyeDisconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GoodbyeDisconnect");

		expectedResult.setAction(SE_ACTION_GOODBYE);

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

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}

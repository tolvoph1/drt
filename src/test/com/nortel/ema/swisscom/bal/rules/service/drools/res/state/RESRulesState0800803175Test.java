/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesState0800803175Test.java 128 2013-10-08 08:32:18Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-10-08 10:32:18 +0200 (Tue, 08 Oct 2013) $
 * $Revision: 128 $
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
public class RESRulesState0800803175Test extends TestCase {


	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "oneIVR/vp5res-0800803175-state";
	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
	private static ServiceConfigurationMap serviceConfigurationMap;
	private static ArrayList<String> aniList;
	private static ArrayList<String> segmentList;

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
		junit.textui.TestRunner.run(RESRulesState0800803175Test.class);
	}

	protected void setUp() {
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		expectedResult = new StateEngineRulesResult();
		callProfile.put(CPK_BUSINESSTYP, RES);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800803175);
		expectedResult.setState(StateEngineRulesResult.DONE);
		aniList = new ArrayList<String>();
		segmentList = new ArrayList<String>();
	}

	public final void testID010a_Init_NoAniPlayJingle() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		aniList.add("0");
		aniList.add("");

		segmentList.add(SEGMENT_CBU);
		segmentList.add(SEGMENT_SME);
		segmentList.add(SEGMENT_RES);
		segmentList.add("0");
		segmentList.add("");

		for (String segment : segmentList) {
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

	public final void testID010b_Init_GotAniSetPNFromSession() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");
		callProfile.put(CPK_ANI, "0621324567");

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

	public final void testID020a_AutoLangSelect_SetCustomerLangRES() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_LANGUAGE, "g");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setLanguageSpoken("g");

		expectedResult.setAction(SE_ACTION_SETLANG);
		expectedResult.setOutputVar1("g");
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
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
		expectedResult.setNextState("OpeningHoursGlobal");

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
		expectedResult.setNextState("OpeningHoursGlobal");

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
		expectedResult.setNextState("OpeningHoursGlobal");

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
		expectedResult.setNextState("OpeningHoursGlobal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040b_WelcomeActual_SingleLanguage_PlayWelcomeFalse() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SINGLELANGUAGE, "true");
		callProfile.put(CPK_PLAYWELC, "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("OpeningHoursGlobal");

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
	
	public final void testID050_LangSelect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setNextState("CheckLangSelectResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060a_CheckLangSelectResponse_Continue() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckLangSelectResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "DONE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("OpeningHoursGlobal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060b_CheckLangSelectResponse_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckLangSelectResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "InputError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070_OpeningHoursGlobal() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "OpeningHoursGlobal");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_Global");
		expectedResult.setNextState("CheckOHGlobalResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070a_CheckOHGlobalResult_Closed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHGlobalResult");
		callProfile.put(CPK_RESULTOH, "1");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("closed-bye");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070b_CheckOHGlobalResult_Open() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHGlobalResult");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CustomerIdentification");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080a_CustID_Ausland() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_INTERNATIONAL, "true");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("Info");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080b_CustID_NoAniNotAusland() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_INTERNATIONAL, "false");

		expectedResult.setAction(SE_ACTION_ENTERPN);
		expectedResult.setNextState("CheckEnteredPhoneNumber");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080c_CustID_UnknownAniNotAusland() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_INTERNATIONAL, "false");
		callProfile.put(CPK_ANI, "some-value");

		expectedResult.setAction(SE_ACTION_ENTERPN);
		expectedResult.setNextState("CheckEnteredPhoneNumber");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080d_CustID_GotAniNotAusland_SME() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_ANI, "some-value");
		callProfile.put(CPK_INTERNATIONAL, "false");

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setCustomerID("33333333");

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setOutputVar1("SME");
		expectedResult.setNextState("CheckResultCustIdent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080e_CustID_GotAniNotAusland_CBU() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_ANI, "some-value");
		callProfile.put(CPK_INTERNATIONAL, "false");

		customerProfile.setSegment(SEGMENT_CBU);
		customerProfile.setCustomerID("33333333");

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setOutputVar1("CBU");
		expectedResult.setNextState("CheckResultCustIdent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080f_CustID_GotAniNotAusland_Others() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
		callProfile.put(CPK_ANI, "some-value");
		callProfile.put(CPK_INTERNATIONAL, "false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCustomerID("33333333");

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setNextState("CheckResultCustIdent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090a_CheckResultCustIdent_ConfirmedMobileCBU() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");
		callProfile.put(CPK_CONNECTIONTYPE, "Wireless");

		customerProfile.setSegment(SEGMENT_CBU);

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("GetINAS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090b_CheckResultCustIdent_Confirmed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("GetINAS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090c_CheckResultCustIdent_Rejected() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIrejected");

		expectedResult.setAction(SE_ACTION_ENTERPN);
		expectedResult.setNextState("CheckEnteredPhoneNumber");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100a_CheckEnteredPhoneNumber_FailedOrCancelled() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckEnteredPhoneNumber");
		callProfile.put(CPK_ACTIONRESPONSE, "FAILED");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100b_CheckEnteredPhoneNumber_Ok() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckEnteredPhoneNumber");
		callProfile.put(CPK_ACTIONRESPONSE, "DONE");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("GetINAS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110_GetINAS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GetINAS");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("RES-HST");
		expectedResult.setNextState("PlayINAS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120_PlayINAS_MAIN() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "PlayINAS");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("BranchSegments");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130a_BranchSegments_RES() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "BranchSegments");

		customerProfile.setSegment(SEGMENT_RES);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Info");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130b_BranchSegments_nonRES() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "BranchSegments");


		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		segmentList.add(SEGMENT_SME);
		segmentList.add(SEGMENT_CBU);

		for (String segment: segmentList) {

			customerProfile.setSegment(segment);

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID140a_Info_TarifInfo() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Info");
		callProfile.put(CPK_INTERNATIONAL, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("ausland-tarif");
		expectedResult.setNextState("BusinessNumberAction");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140b_Info_Free() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Info");
		callProfile.put(CPK_INTERNATIONAL, "false");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("0800800800-info");
		expectedResult.setNextState("BusinessNumberAction");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_BusinessNumberAction() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "BusinessNumberAction");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresBNMenu");
		expectedResult.setOutputVar3("oneIVR/vp5resAnliegenMenu0800803175");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckBusinessNumberMenuResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160a_BusinessNumberMenuResponse_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckBusinessNumberMenuResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");		

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160b_BusinessNumberMenuResponse_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckBusinessNumberMenuResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160c_BusinessNumberMenuResponse_Wireline() throws Exception {
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

	public final void testID500_Transfer() throws Exception {
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

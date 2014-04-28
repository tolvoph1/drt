/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesState0800444404Test.java 88 2013-09-10 10:03:20Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-10 12:03:20 +0200 (Tue, 10 Sep 2013) $
 * $Revision: 88 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.state;

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

/**
 * @author $Author: tolvoph1 $
 * @version $Revision: 88 $ ($Date: 2013-09-10 12:03:20 +0200 (Tue, 10 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesState0800444404Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULES_FILE_NAME = "cbu/vp5cbu-0800444404-state";

	private static ServiceConfigurationMap serviceConfigurationMap;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
	private static ArrayList<String> aniList;


	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesState0800444404Test.class);
	}

	protected void setUp() {
		serviceConfigurationMap = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		aniList = new ArrayList<String>();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800444404);
		callProfile.put(CPK_BUSINESSTYP, CBU);
	}


	public final void testID001a_Init_NoAniPlayJingle() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Init");

		aniList.add("0");
		aniList.add("");
		aniList.add(null);

		for (String ani: aniList) {

			callProfile.put(CPK_ANI, ani);

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("jingle");
			expectedResult.setNextState("PreWelcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID001b_Init_GotAniSetPNFromSession() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Init");
		callProfile.put(CPK_ANI, "0621324567");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("PreWelcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID005a_PreWelcome_Check_SalesAssistantLangauge_HaveLanguage() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"PreWelcome");
		callProfile.put("singleLanguage", "false");

		ArrayList<String> languages = new ArrayList<String>();
		languages.add("DE");
		languages.add("FR");
		languages.add("IT");
		languages.add("EN");

		for (String lang: languages) {
			customerProfile.setSa1_correspLang(lang);

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1(lang);
			expectedResult.setNextState("Welcome");


			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			assertEquals("true", callProfile.getSingleLanguage());
		}

	}

	public final void testID005b_PreWelcome_Check_SysOrigLanguage_Have_Language() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"PreWelcome");

		ArrayList<String> correspLanguages = new ArrayList<String>();
		correspLanguages.add("");
		correspLanguages.add(null);

		ArrayList<String> origLanguages = new ArrayList<String>();
		origLanguages.add("de");
		origLanguages.add("fr");
		origLanguages.add("it");
		origLanguages.add("en");


		for (String correspLang: correspLanguages) {
			for (String origLang: origLanguages) {

				customerProfile.setSa1_correspLang(correspLang);
				callProfile.setSysOrigLanguage(origLang);

				expectedResult.setAction(SE_ACTION_SETLANG);
				expectedResult.setOutputVar1(origLang);
				expectedResult.setNextState("Welcome");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
				assertEquals("true", callProfile.getSingleLanguage());
			}
		}
	}

	public final void testID005c_PreWelcome_Neither_SA_nor_OrigLanguage() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"PreWelcome");

		ArrayList<String> correspLanguages = new ArrayList<String>();
		correspLanguages.add("");
		correspLanguages.add(null);

		ArrayList<String> origLanguages = new ArrayList<String>();
		origLanguages.add("");
		origLanguages.add(null);

		for (String correspLang: correspLanguages) {
			for (String origLang: origLanguages) {

				customerProfile.setSa1_correspLang(correspLang);
				callProfile.setSysOrigLanguage(origLang);

				expectedResult.setAction(SE_ACTION_PROCEED);
				expectedResult.setNextState("WelcomeMultilingual");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
				assertEquals("false", callProfile.getSingleLanguage());
			}
		}
	}

	public final void testID010a_Welcome_PlayWelcomeTrue() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Welcome");
		callProfile.put(CPK_PLAYWELC,"true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome");
		expectedResult.setNextState("CheckSegment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010b_Welcome_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Welcome");
		callProfile.put(CPK_PLAYWELC,"false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckSegment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID012a_WelcomeMultilingual_PlayWelcomeTrue() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"WelcomeMultilingual");
		callProfile.put(CPK_PLAYWELC,"true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Welcome-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID012b_WelcomeMultilingual_PlayWelcomeFalse() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"WelcomeMultilingual");
		callProfile.put(CPK_PLAYWELC,"false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020_LanguageSelection() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"LanguageSelection");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("CheckSegment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_CheckSegment_SME() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckSegment");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setNextState("CheckResultCustIdent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_CheckSegment_RES() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckSegment");

		customerProfile.setSegment(SEGMENT_RES);

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setNextState("CheckResultCustIdent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_CheckSegment_Easy_MBudget() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckSegment");

		customerProfile.setSegment("");
		customerProfile.setSourceCode("PPB");
		customerProfile.setTypeCode("Prepaid Subscription");

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setNextState("CheckResultCustIdent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_CheckResultCustIdent_RES_Confirmed() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");

		customerProfile.setSegment(SEGMENT_RES);

		expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
		expectedResult.setOutputVar1("RES");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_CheckResultCustIdent_RES_Confirmed_Easy_MBudget() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");

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

	public final void testID100_CheckResultCustIdent_SME_Confirmed() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
		expectedResult.setOutputVar1("SME");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_CheckResultCustIdent_Rejected() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckResultCustIdent");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIrejected");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetResultCustomerInformation");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110_GetResultCustomerInformation() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"GetResultCustomerInformation");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120_EmergencyAnnouncements() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"EmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("CBU-WIRELESS");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID125_PlayEmergencyAnnouncements() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"PlayEmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("GetSalesAssistant");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_GetSalesAssistant() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"GetSalesAssistant");

		customerProfile.setSegment(SEGMENT_CBU);

		expectedResult.setAction(SE_ACTION_ACCOUNTMANAGER);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_GetSalesAssistant_SME() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"GetSalesAssistant");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("cbu/vp5cbu-0800444404-transfer");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200_Disconnect() throws Exception {

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
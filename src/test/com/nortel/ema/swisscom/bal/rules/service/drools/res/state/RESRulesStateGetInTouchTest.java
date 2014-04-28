/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesStateGetInTouchTest.java 119 2013-09-26 13:55:18Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-26 15:55:18 +0200 (Thu, 26 Sep 2013) $
 * $Revision: 119 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
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
public class RESRulesStateGetInTouchTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULES_FILE_NAME = "oneIVR/vp5res-GetInTouch-state";

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
		junit.textui.TestRunner.run(RESRulesStateGetInTouchTest.class);
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
		serviceConfigurationMap.put("vp.res.getInTouch.DefaultRID", "9000");
		serviceConfigurationMap.put("vp.res.getInTouch.DefaultRuleID", "GIT_default");
		serviceConfigurationMap.put("vp.res.getInTouch.PlatinRID", "5432");
	}

	public final void testID010_Init_Empty_SysRID_SysRuleID_SysConcern_or_SysOrigLanguage_or_SysOrigLanguage_ML() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");
		callProfile.put(CPK_SYSHTTPCLIENT, "client");

		// 4 ArrayLists for 4 parameters to test
		ArrayList<String> sysRIDs = new ArrayList<String>();
		sysRIDs.add(null);
		sysRIDs.add("1234");

		ArrayList<String> sysRuleIDs = new ArrayList<String>();
		sysRuleIDs.add(null);
		sysRuleIDs.add("99999");

		ArrayList<String> sysConcerns = new ArrayList<String>();
		sysConcerns.add(null);
		sysConcerns.add("sa-fx");

		ArrayList<String> sysLangs = new ArrayList<String>();
		sysLangs.add(null);
		sysLangs.add("ML");
		sysLangs.add("MN");  // Completely invalid
		sysLangs.add("DE");
		sysLangs.add("FR");
		sysLangs.add("IT");
		sysLangs.add("EN");

		// Loops for all combinations of the above lists

		for (String sysRID: sysRIDs) {
			for (String sysRuleID: sysRuleIDs) {
				for (String sysConcern: sysConcerns) {
					for (String sysLang: sysLangs) {

						// Only skip if the one valid case is met
						if (
								!(
										sysRID == null || 
										sysRuleID == null || 
										sysConcern == null || 
										( 
												!StringUtils.equalsIgnoreCase("de",sysLang) && 
												!StringUtils.equalsIgnoreCase("fr",sysLang) && 
												!StringUtils.equalsIgnoreCase("it",sysLang) && 
												!StringUtils.equalsIgnoreCase("en",sysLang) 
												)
										)
								) {
							continue;
						}
						callProfile.put(CPK_SYSRID, sysRID);
						callProfile.put(CPK_SYSRULEID, sysRuleID);
						callProfile.put(CPK_SYSCONCERN, sysConcern);
						callProfile.put(CPK_SYSORIGLANGUAGE, sysLang);

						expectedResult.setAction(SE_ACTION_SPEAK);
						expectedResult.setOutputVar1("jingle welcome");
						expectedResult.setNextState("WriteLogThenDefaultRIDTransfer");

						actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

						checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
					}
				}
			}
		}
	}

	public final void testID020_Init_AllOthers() throws Exception {
		// 
		callProfile.put(CPK_NEXTSTATE, "Init");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");

		// Languages
		ArrayList<String> langs = new ArrayList<String>();
		langs.add("DE");
		langs.add("de");
		langs.add("FR");
		langs.add("fr");
		langs.add("IT");
		langs.add("it");
		langs.add("EN");
		langs.add("en");

		for (String lang: langs) {

			callProfile.put(CPK_SYSORIGLANGUAGE, lang);

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1(lang);
			expectedResult.setNextState("CheckRelevantNumber");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030a_CheckRelevantNumber_Empty() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckRelevantNumber");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("jingle welcome");
		expectedResult.setNextState("WriteLogThenTransfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030b_CheckRelevantNumber_NotEmpty() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckRelevantNumber");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetInTouchMainFlow");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200_GetInTouch_MainFlow_Start() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GetInTouchMainFlow");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "+41621234567");

		expectedResult.setAction(SE_ACTION_SETSESSIONANI);
		expectedResult.setOutputVar1(callProfile.getSysRelevantNo());
		expectedResult.setNextState("SetPhonenumberFromSession");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210_SetPhonenumberFromSession() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SetPhonenumberFromSession");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID220_Welcome_OpeningHours() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Welcome");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_WELCOME");
		expectedResult.setNextState("WelcomeActual");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID230a1_WelcomeActual_Morning() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");
		callProfile.setResultOH("morning");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-morning");
		expectedResult.setNextState("VIPRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID230a2_WelcomeActual_Day() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");
		callProfile.setResultOH("day");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-day");
		expectedResult.setNextState("VIPRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID230a3_WelcomeActual_Evening() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");
		callProfile.setResultOH("evening");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-evening");
		expectedResult.setNextState("VIPRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID230a4_WelcomeActual_Default() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome-general");
		expectedResult.setNextState("VIPRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID240a_VIPRouting_IsVIP_CheckOH() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VIPRouting");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setFineSegment(FINESEGMENT_PP);
		customerProfile.setSubSegment(SUBSEGMENT_VIP);

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_PERS_RES_PREMIUM_VIP");
		expectedResult.setNextState("CheckOHVIPResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID240b_VIPRouting_NoVIP_PROCEED() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VIPRouting");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckPlatinRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID250a_CheckOHVIPResult_Active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHVIPResult");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		// Values for OH Result
		ArrayList<String> results = new ArrayList<String>();
		results.add("tag");
		results.add("nacht");

		for (String result: results) {

			callProfile.setResultOH(result);

			expectedResult.setAction(SE_ACTION_WRITELOG);
			expectedResult.setOutputVar1("Get-In-Touch");
			expectedResult.addToOutputColl1("Concern", "STOERUNG-INTERNET");
			expectedResult.addToOutputColl1("Client", "httpclient");
			expectedResult.setNextState("VIPTransfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID250b_CheckOHVIPResult_Inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHVIPResult");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		callProfile.setResultOH("closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckPlatinRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID260a_CheckPlatinRouting_IsPlatinCustomer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckPlatinRouting");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setFineSegment(FINESEGMENT_PP);
		customerProfile.setSubSegment(SUBSEGMENT_PLATIN);

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Get-In-Touch");
		expectedResult.addToOutputColl1("Concern", "STOERUNG-INTERNET");
		expectedResult.addToOutputColl1("Client", "httpclient");
		expectedResult.setNextState("PlatinTransfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID260b_CheckPlatinRouting_NoPlatinCustomer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckPlatinRouting");
		callProfile.put(CPK_SYSRID, "1234");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("WriteLogThenTransfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID400_WriteLog_Then_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WriteLogThenTransfer");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Get-In-Touch");
		expectedResult.addToOutputColl1("Concern", "STOERUNG-INTERNET");
		expectedResult.addToOutputColl1("Client", "httpclient");
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID410_WriteLog_Then_DefaultRIDTransfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "WriteLogThenDefaultRIDTransfer");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Get-In-Touch");
		expectedResult.addToOutputColl1("Concern", "STOERUNG-INTERNET");
		expectedResult.addToOutputColl1("Client", "httpclient");
		expectedResult.setNextState("DefaultRIDTransfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID500_TransferToSysRID_With_RuleID() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Transfer");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSRID, "9876");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_TRANSFERRID);
		expectedResult.setOutputVar1("9876");
		expectedResult.setOutputVar2("12345");
		expectedResult.setOutputVar3("true");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID510_PlatinTransfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "PlatinTransfer");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSRID, "9876");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_TRANSFERRID);
		expectedResult.setOutputVar1("5432");
		expectedResult.setOutputVar2("12345");
		expectedResult.setOutputVar3("true");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID520_VIPTransfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VIPTransfer");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSRID, "9876");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		serviceConfigurationMap.put("vp.res.getInTouch.PlatinRID", "5432");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("oneIVR/vp5resTransfer");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID530_DefaultRIDTransfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "DefaultRIDTransfer");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_TRANSFERRID);
		expectedResult.setOutputVar1((String)serviceConfigurationMap.get("vp.res.getInTouch.DefaultRID"));
		expectedResult.setOutputVar2((String)serviceConfigurationMap.get("vp.res.getInTouch.DefaultRuleID"));
		expectedResult.setOutputVar3("true");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID900_Fallback_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "unknownState");
		callProfile.put(CPK_SYSRULEID, "12345");
		callProfile.put(CPK_SYSRID, "9876");
		callProfile.put(CPK_SYSCONCERN, "STOERUNG-INTERNET");
		callProfile.put(CPK_SYSHTTPCLIENT, "httpclient");
		callProfile.put(CPK_SYSORIGLANGUAGE, "DE");
		callProfile.put(CPK_SYSRELEVANTNO, "0621234567");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("WriteLogThenTransfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}

/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesStateMASTERMainTest.java 233 2014-04-08 06:19:14Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-08 08:19:14 +0200 (Tue, 08 Apr 2014) $
 * $Revision: 233 $
 * 
 * JUnit Test Class to test the MAIN RES State rules
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

import com.nortel.ema.swisscom.bal.rules.model.StateEngineRulesResult;
import com.nortel.ema.swisscom.bal.rules.model.facts.R5RulesBean;
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
 *	00800 55 64 64 64
 *  00800 88 11 12 13
 *	062 207 60 59  
 *	062 207 60 90
 *	062 286 12 12  
 *	0800 55 64 64
 *	0800 800 800
 *	0800 814 814
 * 	0800 88 11 12
 *	081 287 90 46
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesStateMASTERMainTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	// Rewrite as of October 16th, 2012: Instead of copying&pasting this example TestClass and
	// comment/uncomment the constants for all involved businessnumbers an ArrayList with all
	// required rules filenames is created and each testcase iterates through this list.
	// So only this one example test class needs to be run to cover all involved rules files.

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"oneIVR/vp5res-MASTER-main-state",
					"oneIVR/vp5res-0080055646464-state",
					"oneIVR/vp5res-0080088111213-state",
					"oneIVR/vp5res-0622076059-state",
					"oneIVR/vp5res-0622076090-state",
					"oneIVR/vp5res-0622861212-state",
					"oneIVR/vp5res-0800556464-state",
					"oneIVR/vp5res-0800800800-state",
					"oneIVR/vp5res-0800814814-state",
					"oneIVR/vp5res-0800881112-state",
					"oneIVR/vp5res-0812879046-state"
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

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesStateMASTERMainTest.class);
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

	public final void testID0010a_Init_NoAniPlayJingle() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");

			ArrayList<String> aniList = new ArrayList<String>();
			aniList.add("0");
			aniList.add("");
			aniList.add(null);

			ArrayList<String> segmentList = new ArrayList<String>();
			segmentList.add(SEGMENT_CBU);
			segmentList.add(SEGMENT_SME);
			segmentList.add(SEGMENT_RES);
			segmentList.add("0");
			segmentList.add("");
			segmentList.add(null);

			for (String segment: segmentList) {
				for (String ani: aniList) {

					callProfile.put(CPK_ANI, ani);
					customerProfile.setSegment(segment);

					expectedResult.setAction(SE_ACTION_SPEAK);
					expectedResult.setOutputVar1("jingle");
					expectedResult.setNextState("AutoLangSelect");

					actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

					checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
				}
			}
		}
	}

	public final void testID0010b_Init_GotAniSetPNFromSession() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");
			callProfile.put(CPK_ANI, "0621234567");

			ArrayList<String> segmentList = new ArrayList<String>();
			segmentList.add(SEGMENT_CBU);
			segmentList.add(SEGMENT_SME);
			segmentList.add(SEGMENT_RES);
			segmentList.add("0");
			segmentList.add("");
			segmentList.add(null);

			for (String segment: segmentList) {

				customerProfile.setSegment(segment);

				expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
				expectedResult.setOutputVar1("true");
				expectedResult.setNextState("SetINASPilotSprachportal");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID0010c_GetInTouch_Caller_GIT_active() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.setNextState("Init");
			callProfile.setSysOrigANI("0621234567");
			callProfile.setSysOrsSessionID("0815");
			callProfile.setSysChannel("App");
			callProfile.setAni(callProfile.getSysOrigANI());

			serviceConfigurationMap.put("vp.res.getInTouch.active", "true");

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-getintouch-state");
			expectedResult.setNextState("Init");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0010c2_GetInTouch_Caller_GIT_active_anonymous() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.setNextState("Init");
			callProfile.setSysOrigANI("anonymous");
			callProfile.setSysOrsSessionID("0815");
			callProfile.setSysChannel("App");
			callProfile.setAni("");

			serviceConfigurationMap.put("vp.res.getInTouch.active", "true");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("jingle");
			expectedResult.setNextState("AutoLangSelect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0020_Seiteneinstieg() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Seiteneinstieg");
			callProfile.put(CPK_ANI, "0621234567");

			ArrayList<String> segmentList = new ArrayList<String>();
			segmentList.add(SEGMENT_CBU);
			segmentList.add(SEGMENT_SME);
			segmentList.add(SEGMENT_RES);
			segmentList.add("0");
			segmentList.add("");
			segmentList.add(null);

			for (String segment: segmentList) {

				customerProfile.setSegment(segment);

				expectedResult.setAction(SE_ACTION_PROCEED);
				expectedResult.setNextState("Info");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID0022_SetINASPilotSprachportal() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "SetINASPilotSprachportal");

			expectedResult.setAction(SE_ACTION_SETINASACTIONS);
			expectedResult.setOutputVar1("RES-PILOT");
			expectedResult.setNextState("ExecINASPilotSprachportal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0024_ExecINASPilotSprachportal() throws Exception {
		for (String RULES_FILE_NAME: rules) {
			callProfile.put(CPK_NEXTSTATE, "ExecINASPilotSprachportal");

			expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
			expectedResult.setOutputVar1("MAIN");
			expectedResult.setNextState("CheckPilotSpracherkennung");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0026a_CheckPilotSpracherkennung_PilotCaller() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckPilotSpracherkennung");
			callProfile.put(CPK_GENERICINASACTION, "PilotSpracherkennung");

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-sme-Pilot-Spracherkennung-state");
			expectedResult.setNextState("Init");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0026b_CheckPilotSpracherkennung_AllOthers() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckPilotSpracherkennung");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("AutoLangSelect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0030a_AutoLangSelect_SetCustomerLangRES() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}

	public final void testID0030b_AutoLangSelect_SetMultilingual_nonInternational() throws Exception {
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

	public final void testID0030c_AutoLangSelect_SetMultilingual_International() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "AutoLangSelect");
			callProfile.put(CPK_SINGLELANGUAGE, "false");
			callProfile.put(CPK_INTERNATIONAL, "true");

			expectedResult.setAction(SE_ACTION_SETLANG);
			expectedResult.setOutputVar1("ml");
			expectedResult.setNextState("SwitchRulesInternationalWireless");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0040a_SwitchRules_Ausland_Wireless() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			ArrayList<String> segments = new ArrayList<String>();
			segments.add(SEGMENT_CBU);
			segments.add(SEGMENT_RES);

			callProfile.put(CPK_NEXTSTATE, "SwitchRulesInternationalWireless");

			for (String segment: segments) {

				customerProfile.setSegment(segment);

				expectedResult.setAction(SE_ACTION_SWITCHRULES);
				expectedResult.setOutputVar1("oneIVR/vp5res-ausland-wireless-state");
				expectedResult.setOutputVar2("Ausland");
				expectedResult.setNextState("Init");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID0040b_SwitchRules_Ausland_Wireless_SME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "SwitchRulesInternationalWireless");

			customerProfile.setSegment(SEGMENT_SME);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Welcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0050_Welcome_OpeningHours() throws Exception {
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

	public final void testID0060a1_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Morning() throws Exception {
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

	public final void testID0060a2_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Day() throws Exception {
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

	public final void testID0060a3_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Evening() throws Exception {
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

	public final void testID0060a4_WelcomeActual_SingleLanguage_PlayWelcomeTrue_Default() throws Exception {
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

	public final void testID0060b_WelcomeActual_SingleLanguage_PlayWelcomeFalse() throws Exception {
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

	public final void testID0060c1_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Morning() throws Exception {
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

	public final void testID0060c2_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Day() throws Exception {
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

	public final void testID0060c3_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Evening() throws Exception {
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

	public final void testID0060c4_WelcomeActual_MultiLanguage_PlayWelcomeTrue_Default() throws Exception {
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

	public final void testID0060d_WelcomeActual_MultiLanguage_PlayWelcomeFalse() throws Exception {
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

	public final void testID0070_LangSelect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

			expectedResult.setAction(SE_ACTION_LANGSELECT);
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0080a_OpeningHoursGlobal() throws Exception {
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


	public final void testID0080b_OpeningHoursGlobal_Ausland_Wireless_SME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "OpeningHoursGlobal");
			callProfile.setInternational("true");

			customerProfile.setSegment(SEGMENT_SME);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0090a_CheckOHGlobalResult_Closed() throws Exception {
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

	public final void testID0090b_CheckOHGlobalResult_Open() throws Exception {
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

	public final void testID0100a_CustID_Ausland() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_INTERNATIONAL, "true");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0100b_CustID_NoAniNotAusland() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_INTERNATIONAL, "false");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PhoneNumberInput");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0100c_CustID_UnknownAniNotAusland() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_INTERNATIONAL, "false");
			callProfile.put(CPK_ANI, "some-value");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PhoneNumberInput");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0100d_CustID_GotAniNotAusland_VIP() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CustomerIdentification");
			callProfile.put(CPK_INTERNATIONAL, "false");
			callProfile.put(CPK_ANI, "0621234567");

			customerProfile.setCustomerID("dummyBskID");
			customerProfile.setSegment(SEGMENT_RES);
			customerProfile.setFineSegment(FINESEGMENT_PP);
			customerProfile.setSubSegment(SUBSEGMENT_VIP);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0100e_CustID_GotAniNotAusland_SME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}

	public final void testID0100f_CustID_GotAniNotAusland_CBU() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}

	public final void testID0100g_CustID_GotAniNotAusland_Others() throws Exception {
		for (String RULES_FILE_NAME: rules) {

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
	}

	public final void testID0110a_CheckResultCustIdent_Confirmed() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
			callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0110b_CheckResultCustIdent_Rejected() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdent");
			callProfile.put(CPK_ACTIONRESPONSE, "ANIrejected");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PhoneNumberInput");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0120a_PhoneNumberInput_unknown() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PhoneNumberInput");

			expectedResult.setAction(SE_ACTION_ENTERPN);
			expectedResult.setOutputVar1("enterPhoneNumber-VivoLibero");
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0120b_PhoneNumberInput_SME_CBU() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PhoneNumberInput");

			ArrayList<String> segments = new ArrayList<String>();
			segments.add(SEGMENT_SME);
			segments.add(SEGMENT_CBU);

			for (String segment: segments) {
				customerProfile.setSegment(segment);

				expectedResult.setAction(SE_ACTION_ENTERPN);
				expectedResult.setOutputVar1("enterPhoneNumber-VivoLibero");
				expectedResult.setNextState("Info");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID0130c_PhoneNumberInput_Wireline() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PhoneNumberInput");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

			expectedResult.setAction(SE_ACTION_ENTERPN);
			expectedResult.setOutputVar1("enterPhoneNumber-VivoLibero");
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0130d_PhoneNumberInput_Wireless() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PhoneNumberInput");
			callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

			expectedResult.setAction(SE_ACTION_ENTERPN);
			expectedResult.setOutputVar1("enterPhoneNumber-VivoLibero");
			expectedResult.setNextState("Info");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0140a_Info_TarifInfo() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Info");
			callProfile.put(CPK_INTERNATIONAL, "true");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("ausland-tarif");
			expectedResult.setNextState("GetResultCustInfo");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0140b_Info_Free() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Info");
			callProfile.put(CPK_INTERNATIONAL, "false");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("info-free-stay-on-the-line");
			expectedResult.setNextState("GetResultCustInfo");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0150a_GetResultCustInfo_CheckCustomerType() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GetResultCustInfo");

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("CheckCustomerType");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0150b_GetResultCustInfo_MobileCBU() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GetResultCustInfo");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireless");
			callProfile.put(CPK_INTERNATIONAL, "false");

			customerProfile.setSegment(SEGMENT_CBU);

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("EmergencyAnnouncements");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0150c_GetResultCustInfo_NewCustomer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GetResultCustInfo");
			callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NEWCUSTOMER);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("NewCustomerTreatment");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0160a1_CheckCustomerType_SME_SpeakPrompt_Then_TransferToSME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");

			customerProfile.setSegment(SEGMENT_SME);

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("AuskreuzungRESzuSME");
			expectedResult.setNextState("TransferCallflowSME");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0160a2_TransferCallflowSME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "TransferCallflowSME");

			customerProfile.setSegment(SEGMENT_SME);

			expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
			expectedResult.setOutputVar1("SME");
			expectedResult.setNextState("Disconnect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0160b_CheckCustomerType_CBU_Wireline_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireline");

			customerProfile.setSegment(SEGMENT_CBU);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID160c_CheckCustomerType_SAL_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireless");

			customerProfile.setSegment(SEGMENT_RES);
			customerProfile.setSa1_ntAccount("dummyID");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0160d_CheckCustomerType_Others() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckCustomerType");

			customerProfile.setSegment(SEGMENT_RES);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("EmergencyAnnouncements");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0170_EmergencyAnnouncements() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "EmergencyAnnouncements");
			callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

			customerProfile.setSegment(SEGMENT_RES);

			expectedResult.setAction(SE_ACTION_SETINASACTIONS);
			expectedResult.setOutputVar1("RES");
			expectedResult.setNextState("SwitchRules");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0180a_SwitchRules_MobileCBUPersonalisation() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "SwitchRules");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireless");

			customerProfile.setSegment(R5RulesBean.SEGMENT_CBU);

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-mobileCBU-state");
			expectedResult.setOutputVar2("Wireless");
			expectedResult.setNextState("InitMobileCBU");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0180c_SwitchRules_MigrosPersonalisation() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "SwitchRules");
			callProfile.put(CPK_CUSTOMERTYPE, "MMO");

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-0800151728-state");
			expectedResult.setNextState("EmergencyAnnouncements");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID180d_SwitchRules_WirelessPersonalisation() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "SwitchRules");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireless");
			callProfile.put(CPK_INTERNATIONAL, "false");

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-wireless-state");
			expectedResult.setOutputVar2("Wireless");
			expectedResult.setNextState("InitWirelessPers");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID180e_SwitchRules_WirelinePersonalisation() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "SwitchRules");
			callProfile.put(CPK_CONNECTIONTYPE, "Wireline");
			callProfile.put(CPK_INTERNATIONAL, "false");

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-wireline-state");
			expectedResult.setOutputVar2("Wireline");
			expectedResult.setNextState("InitWirelinePers");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0200_Disconnect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Disconnect");

			expectedResult.setAction(SE_ACTION_DISCONNECT);

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0201_GoodbyeDisconnect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GoodbyeDisconnect");

			expectedResult.setAction(SE_ACTION_GOODBYE);

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0210_Transfer() throws Exception {
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

	public final void testID0300_NewCustomerTreatment() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "NewCustomerTreatment");

			expectedResult.setAction(SE_ACTION_CONFIRM);
			expectedResult.setOutputVar1("neukunde-fragen-mobile1-anderes2");
			expectedResult.setOutputVar2("newcustomerconfirm");
			expectedResult.addToOutputColl1("nomatch1", "no");
			expectedResult.addToOutputColl1("noinput1", "no");
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0900_InputError() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "InputError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0901_ConnectorError() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "ConnectorError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0902_TechError() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "TechError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID0998_FALLBACK_TRANSFER() throws Exception {
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

/*
 * (c)2014 Swisscom (Schweiz) AG.
 *
 * $Id: RESSMERulesStatePilotSpracherkennungTest.java 228 2014-03-31 08:38:37Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-31 10:38:37 +0200 (Mon, 31 Mar 2014) $
 * $Revision: 228 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

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
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESSMERulesStatePilotSpracherkennungTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"oneIVR/vp5res-sme-Pilot-Spracherkennung-state"				
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
		junit.textui.TestRunner.run(RESSMERulesStatePilotSpracherkennungTest.class);
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

	public final void testID010_Init() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Welcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID020a_Welcome_OpeningHours_nonSME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome");
			callProfile.setBusinesstyp("RES");

			expectedResult.setAction(SE_ACTION_CHECKOH);
			expectedResult.setOutputVar1("OH_RES_WELCOME");
			expectedResult.setNextState("WelcomeActual");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID020b_Welcome_OpeningHours_SME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome");
			callProfile.setBusinesstyp("SME");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome");
			expectedResult.setNextState("Record");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030a1_WelcomeActual_Morning() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.setResultOH("morning");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-morning");
			expectedResult.setNextState("Record");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030a2_WelcomeActual_Day() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.setResultOH("day");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-day");
			expectedResult.setNextState("Record");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030a3_WelcomeActual_Evening() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");
			callProfile.setResultOH("evening");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-evening");
			expectedResult.setNextState("Record");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030a4_WelcomeActual_Default() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "WelcomeActual");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("welcome-general");
			expectedResult.setNextState("Record");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID040_Record() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Record");

			expectedResult.setAction(SE_ACTION_RECORD);
			expectedResult.setOutputVar1("30");
			expectedResult.setOutputVar2("2");
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID050a_Transfer_RES() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Transfer");
			callProfile.setBusinesstyp(RES);

			expectedResult.setAction(SE_ACTION_SWITCHRULES);
			expectedResult.setOutputVar1("oneIVR/vp5res-0800800800-state");
			expectedResult.setNextState("OpeningHoursGlobal");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID050b_Transfer_SME() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Transfer");
			callProfile.setBusinesstyp(SME);

			expectedResult.setAction(SE_ACTION_TRANSFERRID);
			expectedResult.setOutputVar1("8560");
			expectedResult.setOutputVar2("42500");
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

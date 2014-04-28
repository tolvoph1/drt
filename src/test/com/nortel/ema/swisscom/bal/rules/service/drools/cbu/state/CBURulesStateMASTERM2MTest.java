/*
 * (c)2014 Swisscom (Schweiz) AG
 *
 * $Id: CBURulesStateMASTERM2MTest.java 239 2014-04-22 13:06:28Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-22 15:06:28 +0200 (Tue, 22 Apr 2014) $
 * $Revision: 239 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.state;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

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
 *	0800 724 011
 *	0800 724 777
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesStateMASTERM2MTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"cbu/vp5cbu-MASTER-M2M-state",
					"cbu/vp5cbu-0800724011-state",
					"cbu/vp5cbu-0800724777-state"
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
		junit.textui.TestRunner.run(CBURulesStateMASTERM2MTest.class);
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

	public final void testID010a_Init_NoAni_Not_0800724011() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800724777);

			ArrayList<String> aniList = new ArrayList<String>();
			aniList.add("0");
			aniList.add("");
			aniList.add(null);

			for (String ani: aniList) {

				callProfile.put(CPK_ANI, ani);

				expectedResult.setAction(SE_ACTION_PROCEED);
				expectedResult.setNextState("GetINAS");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID010b_Init_GotAniSetPNFromSession_Not_0800724011() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800724777);
			callProfile.put(CPK_ANI, "0621234567");

			expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
			expectedResult.setOutputVar1("false");
			expectedResult.setNextState("GetResultCustomerInformation");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID010c_Init_NoAniPlayJingle_0800724011() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800724011);

			ArrayList<String> aniList = new ArrayList<String>();
			aniList.add("0");
			aniList.add("");
			aniList.add(null);

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
	}

	public final void testID010d_Init_GotAniSetPNFromSession_0800724011() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Init");
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800724011);
			callProfile.put(CPK_ANI, "0621234567");

			expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
			expectedResult.setOutputVar1("true");
			expectedResult.setNextState("Welcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID020a_MultiLanguage_PlayWelcomeTrue() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome");
			callProfile.put(CPK_PLAYWELC, TRUE);

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("Welcome-Multilingual");
			expectedResult.setNextState("LanguageSelection");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID020b_MultiLanguage_PlayWelcomeFalse() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Welcome");
			callProfile.put(CPK_PLAYWELC, FALSE);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("LanguageSelection");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID030_LanguageSelection() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

			expectedResult.setAction(SE_ACTION_LANGSELECT);
			expectedResult.setOutputVar1("false");
			expectedResult.setNextState("GetResultCustomerInformation");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID040_GetResultCustomerInformation() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GetResultCustomerInformation");

			expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
			expectedResult.setNextState("GetINAS");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID050_GetINAS() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "GetINAS");

			expectedResult.setAction(SE_ACTION_SETINASACTIONS);
			expectedResult.setOutputVar1("CBU-M2M");
			expectedResult.setNextState("PlayINAS");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID060_PlayINAS() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PlayINAS");

			expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
			expectedResult.setOutputVar1("MAIN");
			expectedResult.setNextState("MenuLevel1");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID070_MenuLevel1() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "MenuLevel1");

			expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
			expectedResult.setOutputVar1("cbu/vp5cbu-0800724011-menu-level1");
			expectedResult.setOutputVar3("cbu/vp5cbu-0800724011-menu-level1");
			expectedResult.addToOutputColl1("select", "alm-menue-please-select");
			expectedResult.setNextState("CheckMenuSelectionLevel1");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID080a_CheckMenuSelectionLevel1_SF() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckMenuSelectionLevel1");
			callProfile.put(CPK_ACTIONRESPONSE, "SF");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("CheckOpeningHoursKundendienst");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID080b_CheckMenuSelectionLevel1_SA() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckMenuSelectionLevel1");
			callProfile.put(CPK_ACTIONRESPONSE, "SA");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("CheckOpeningHoursStoerung");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID080c_CheckMenuSelectionLevel1_InputError() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckMenuSelectionLevel1");
			callProfile.put(CPK_ACTIONRESPONSE, "InputError");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID100_CheckOpeningHoursKundendienst() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOpeningHoursKundendienst");

			expectedResult.setAction(SE_ACTION_CHECKOH);
			expectedResult.setOutputVar1("OH_CBU_M2M_KUNDENDIENST");
			expectedResult.setNextState("CheckOHResultKundendienst");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID110a_CheckOHResultKundendienst_Closed() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOHResultKundendienst");
			callProfile.put(CPK_RESULTOH, "closed");

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("OeffnungszeitenM2M");
			expectedResult.setNextState("Disconnect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID110b_CheckOHResultKundendienst_Open() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOHResultKundendienst");
			callProfile.put(CPK_RESULTOH, "open");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PlayINASAfterKundendienst");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID120_PlayINASAfterKundendienst() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PlayINASAfterKundendienst");

			expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
			expectedResult.setOutputVar1("SF");
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID130_CheckOpeningHoursStoerung() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOpeningHoursStoerung");

			expectedResult.setAction(SE_ACTION_CHECKOH);
			expectedResult.setOutputVar1("OH_CBU_M2M_KUNDENDIENST");
			expectedResult.setNextState("CheckOHResultStoerung");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID140a_CheckOHResultStoerung_Closed() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOHResultStoerung");
			callProfile.put(CPK_RESULTOH, "closed");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("EnterPINAfterStoerungWhenClosed");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID140b_CheckOHResultStoerung_Open() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "CheckOHResultStoerung");
			callProfile.put(CPK_RESULTOH, "open");

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("PlayINASAfterStoerung");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID150_PlayINASAfterStoerung() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "PlayINASAfterStoerung");

			expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
			expectedResult.setOutputVar1("SA");
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID160_EnterPINAfterStoerungWhenClosed() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE,"EnterPINAfterStoerungWhenClosed");

			expectedResult.setAction(SE_ACTION_PIN);
			expectedResult.setOutputVar2("OeffnungszeitenM2M");
			expectedResult.addToOutputColl1("1001", "true");
			expectedResult.setNextState("PlayINASAfterStoerung");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID200_Transfer() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Transfer");

			expectedResult.setAction(SE_ACTION_TRANSFER);
			expectedResult.setOutputVar1("cbu/vp5cbu-0800724011-transfer");
			expectedResult.setNextState("Disconnect");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID210_Disconnect() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_NEXTSTATE, "Disconnect");

			expectedResult.setAction(SE_ACTION_DISCONNECT);

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

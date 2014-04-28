/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesState0442946666Test.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
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
 * @version $Revision: 78 $ ($Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesState0442946666Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULES_FILE_NAME = "cbu/vp5cbu-0442946666-state";

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

	protected void setUp() {
		// Create objects
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
		callProfile.setBusinessnumber(BUSINESSNUMBER_0442946666);
		callProfile.setBusinesstyp(CBU);
		aniList = new ArrayList<String>();
	}

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesState0442946666Test.class);
	}

	public final void testID001_Init_NoAniPlayJingle() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Init");

		aniList.add("0");
		aniList.add("");

		for (String ani: aniList) {

			callProfile.put(CPK_ANI, ani);

			expectedResult.setAction(SE_ACTION_SPEAK);
			expectedResult.setOutputVar1("jingle");
			expectedResult.setNextState("NoWelcome");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
			
			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID001_Init_GotAniSetPNFromSession() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Init");
		callProfile.put(CPK_ANI, "0621324567");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("NoWelcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010_SingleLanguage() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"NoWelcome");
		callProfile.put(CPK_SINGLELANGUAGE,"true");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CaseBasedRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010_MultiLanguage() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"NoWelcome");
		callProfile.put(CPK_SINGLELANGUAGE,"false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020_LanguageSelection() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"LanguageSelection");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setNextState("CaseBasedRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}


	public final void testID110_CaseBasedRouting() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CaseBasedRouting");

		expectedResult.setAction(SE_ACTION_CBR);
		expectedResult.setNextState("CheckCBRActionResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID115a_CaseBasedRouting_actionResponse_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckCBRActionResponse");
		callProfile.put(CPK_ACTIONRESPONSE,"TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID115b_CaseBasedRouting_actionResponse_NoTransfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckCBRActionResponse");
		callProfile.put(CPK_ACTIONRESPONSE,"DONE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetResultCustomerInformation");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}


	public final void testID120_GetResultCustomerInformation() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"GetResultCustomerInformation");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_EmergencyAnnouncements() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"EmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("CBU-CS");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID135_PlayEmergencyAnnouncements() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"PlayEmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("MenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_MenuLevel1() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"MenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("cbu/vp5cbu-0442946666-menu-level1");
		expectedResult.setOutputVar2("CBU-CS");
		expectedResult.setOutputVar3("cbu/vp5cbu-0442946666-menu-level1");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckMenuSelectionLevel1");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_CheckMenuSelectionLevel1AnotherMenuLevel() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"MENU");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("cbu/vp5cbu-0442946666-menu-level2");
		expectedResult.setOutputVar2("CBU-CS");
		expectedResult.setOutputVar3("cbu/vp5cbu-0442946666-menu-level2");
		expectedResult.addToOutputColl1("select", "pm-menue-please-select");
		expectedResult.setNextState("CheckMenuSelectionLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_CheckMenuSelectionLevel1Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_CheckMenuSelectionLevel1InputError() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"InputError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	// Menu Level 2 always returns TRANSFER so no need to check for actionResponse=MENU
	public final void testID160_CheckMenuSelectionLevel2() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel2");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160_CheckMenuSelectionLevel2InputError() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel2");
		callProfile.put(CPK_ACTIONRESPONSE,"InputError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("cbu/vp5cbu-0442946666-transfer");
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
/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesState080080800900Test.java 236 2014-04-15 12:36:40Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-15 14:36:40 +0200 (Tue, 15 Apr 2014) $
 * $Revision: 236 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.state;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.ArrayList;

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

/**
 * @author $Author: tolvoph1 $
 * @version $Revision: 236 $ ($Date: 2014-04-15 14:36:40 +0200 (Tue, 15 Apr 2014) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesState080080800900Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULES_FILE_NAME = "cbu/vp5cbu-080080800900-state";
	// variables used in all/most test cases
	private static ServiceConfigurationMap serviceConfigurationMap;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
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
		junit.textui.TestRunner.run(CBURulesState080080800900Test.class);
	}

	protected void setUp() {
		serviceConfigurationMap = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		segments = new ArrayList<String>();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_080080800900);
		callProfile.put(CPK_BUSINESSTYP, CBU);
	}

	public final void testID001_Init_NoAniPlayJingle() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Init");

		ArrayList<String> aniList = new ArrayList<String>();
		aniList.add("0");
		aniList.add("");

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

	public final void testID001_Init_GotAniSetPNFromSession() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Init");
		callProfile.put(CPK_ANI, "0621324567");

		expectedResult.setAction(SE_ACTION_SETPNFROMSESSION);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("Welcome");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}


	public final void testID010_SingleLanguage_PlayWelcomeTrue() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Welcome");
		callProfile.put(CPK_SINGLELANGUAGE,"true");
		callProfile.put(CPK_PLAYWELC,"true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("welcome");
		expectedResult.setNextState("CheckSegment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010_SingleLanguage_PlayWelcomeFalse() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Welcome");
		callProfile.put(CPK_SINGLELANGUAGE,"true");
		callProfile.put(CPK_PLAYWELC,"false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckSegment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010_MultiLanguage_PlayWelcomeTrue() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Welcome");
		callProfile.put(CPK_SINGLELANGUAGE,"false");
		callProfile.put(CPK_PLAYWELC,"true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Welcome-Multilingual");
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010_MultiLanguage_PlayWelcomeFalse() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Welcome");
		callProfile.put(CPK_SINGLELANGUAGE,"false");
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
		expectedResult.setNextState("CheckSegment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090a_CheckSegment_RES() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckSegment");

		customerProfile.setSegment(SEGMENT_RES);

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setNextState("CheckResultCustIdent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090b_CheckSegment_CBU_OTHERS() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckSegment");

		segments.add(SEGMENT_CBU);
		segments.add(SEGMENT_SME);
		segments.add(null);

		for (String segment: segments) { 

			customerProfile.setSegment(segment);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("GetResultCustomerInformation");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
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
		expectedResult.setOutputVar1("CBU-KUNDENDIENST");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_PlayEmergencyAnnouncements() throws Exception {
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
		expectedResult.setOutputVar1("cbukundendienstmenulevel1");
		expectedResult.setOutputVar3("cbu/vp5cbu-080080800900-menu-level1");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckMenuSelectionLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID150a_CheckMenuSelectionLevel1_BILLING() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"BILLING");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckSegmentAfterMenu");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID150b_CheckMenuSelectionLevel1_OTHER() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"OTHER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckSegmentAfterMenu");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID150c_CheckMenuSelectionLevel1_DefaultTransfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID160a_CheckSegmentAfterMenu_SME() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckSegmentAfterMenu");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
		expectedResult.setOutputVar1("SME");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID160b_CheckSegmentAfterMenu_AllOther() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"CheckSegmentAfterMenu");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
		
	public final void testID190_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("cbu/vp5cbu-080080800900-transfer");
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
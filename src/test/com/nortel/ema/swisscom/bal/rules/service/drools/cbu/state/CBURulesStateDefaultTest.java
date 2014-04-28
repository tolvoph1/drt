/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesStateDefaultTest.java 88 2013-09-10 10:03:20Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-10 12:03:20 +0200 (Tue, 10 Sep 2013) $
 * $Revision: 88 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.state;

import java.util.ArrayList;

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
 * @author $Author: tolvoph1 $
 * @version $Revision: 88 $ ($Date: 2013-09-10 12:03:20 +0200 (Tue, 10 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesStateDefaultTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULES_FILE_NAME = "cbu/default";
	// variables used in all/most test cases
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
		junit.textui.TestRunner.run(CBURulesStateDefaultTest.class);
	}

	protected void setUp() {
		serviceConfigurationMap = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		miscList = new ArrayList<String>();
		segments = new ArrayList<String>();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800724724);
		callProfile.put(CPK_BUSINESSTYP, CBU);
	}


	public final void testID001_Init_NoAniPlayJingle() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Init");

		miscList.add("0");
		miscList.add("");

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

		customerProfile.setLanguageSpoken("");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setNextState("CheckSegment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_CheckSegment_CBU_OTHERS() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckSegment");

		segments.add(SEGMENT_CBU);
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

	public final void testID090_CheckSegment_SME() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckSegment");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_XFERCALLFLOW);
		expectedResult.setOutputVar1("SME");
		expectedResult.setNextState("Disconnect");

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
		expectedResult.setOutputVar1("CBU-WIRELINE");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID125_PlayEmergencyAnnouncements() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"PlayEmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("MenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_MenuLevel1() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"MenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("cbu/vp5cbu-0800724724-menu-level1");
		expectedResult.setOutputVar2("CBU-WIRELINE");
		expectedResult.setOutputVar3("cbu/vp5cbu-0800724724-menu-level1");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckMenuSelectionLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_CheckMenuSelectionLevel1AnotherMenuLevel() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"MENU");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("cbu/vp5cbu-0800724724-menu-level2");
		expectedResult.setOutputVar2("CBU-WIRELINE");
		expectedResult.setOutputVar3("cbu/vp5cbu-0800724724-menu-level2");
		expectedResult.addToOutputColl1("select", "pm-menue-please-select");
		expectedResult.setNextState("CheckMenuSelectionLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_CheckMenuSelectionLevel1Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_CheckMenuSelectionLevel1InputError() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel1");
		callProfile.put(CPK_ACTIONRESPONSE,"InputError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_CheckMenuSelectionLevel2_Confirm() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel2");
		callProfile.put(CPK_ACTIONRESPONSE,"CONFIRM");

		expectedResult.setAction(SE_ACTION_CONFIRM);
		expectedResult.setOutputVar1("portal:pbrdsl-option1and2");
		expectedResult.setOutputVar2("pbrdsl_confirm");
		expectedResult.addToOutputColl1("yes", "1");
		expectedResult.addToOutputColl1("no", "2");
		expectedResult.addToOutputColl1("nomatch1", "retry");
		expectedResult.addToOutputColl1("nomatch2", "no");
		expectedResult.addToOutputColl1("noinput1", "retry");
		expectedResult.addToOutputColl1("noinput2", "no");
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_CheckMenuSelectionLevel2_MSS() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel2");
		callProfile.put(CPK_ACTIONRESPONSE,"MSS");

		expectedResult.setAction(SE_ACTION_MSS);
		expectedResult.setOutputVar1("3");
		expectedResult.setOutputVar2("enterPINCode-NOK-transfer");
		expectedResult.addToOutputColl1("8645", "true");
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_CheckMenuSelectionLevel2_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"CheckMenuSelectionLevel2");
		callProfile.put(CPK_ACTIONRESPONSE,"Transfer");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_CheckMenuSelectionLevel2InputError() throws Exception {

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
		expectedResult.setOutputVar1("cbu/vp5cbu-0800724724-transfer");
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

	public final void testID210_Seiteneinstieg() throws Exception {

		callProfile.put(CPK_NEXTSTATE,"Seiteneinstieg");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("EmergencyAnnouncements");

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
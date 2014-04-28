/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesState0812879925Test.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
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
 * @version $Revision: 78 $ ($Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesState0812879925Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String RULES_FILE_NAME = "cbu/vp5cbu-0812879925-state";

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
		callProfile.setBusinessnumber(BUSINESSNUMBER_0812879925);
		callProfile.setBusinesstyp(CBU);
		aniList = new ArrayList<String>();
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesState0812879925Test.class);
	}

	public final void testID001_Init_NoAniPlayJingle() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Init");

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
		expectedResult.setNextState("GetResultCustomerInformation");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_GetResultCustomerInformation() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"GetResultCustomerInformation");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("EmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_EmergencyAnnouncements() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"EmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_SETINASACTIONS);
		expectedResult.setOutputVar1("CBU-DAS");
		expectedResult.setNextState("PlayEmergencyAnnouncements");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID105_PlayEmergencyAnnouncements() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"PlayEmergencyAnnouncements");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE,"Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("cbu/vp5cbu-0812879925-transfer");
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

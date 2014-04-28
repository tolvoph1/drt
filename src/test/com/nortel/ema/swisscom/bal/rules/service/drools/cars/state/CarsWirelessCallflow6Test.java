/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CarsWirelessCallflow6Test.java 156 2013-12-10 08:00:59Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-12-10 09:00:59 +0100 (Tue, 10 Dec 2013) $
 * $Revision: 156 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cars.state;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;
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
public class CarsWirelessCallflow6Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "cars/wireless-callflow6";
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
		junit.textui.TestRunner.run(CarsWirelessCallflow6Test.class);
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

	public final void testID010_161201_Init_Workflow() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161201");
		expectedResult.setNextState("CheckCreditLimitInfoAvailable");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020a_CheckCreditLimitInfoAvailable_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckCreditLimitInfoAvailable");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161202");
		expectedResult.setNextState("RoutingToCreditlimitBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020b_CheckCreditLimitInfoAvailable_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckCreditLimitInfoAvailable");

		customerProfile.setCarsStatus("creditLimitInfoAvailable");
		customerProfile.setLimitExcess("50.00");
		customerProfile.setMinimumTopUpAmount("90.00");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MobileDunnedIntro");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030_MobileDunnedIntro() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "MobileDunnedIntro");

		customerProfile.setCarsStatus("creditLimitInfoAvailable");
		customerProfile.setLimitExcess("50.00");
		customerProfile.setMinimumTopUpAmount("90.00");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:NatelGesperrtKredilimitenUeberschreitungUmA"+
				" ttaCurrency:"+ customerProfile.getLimitExcess());
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskForTopUpFirstTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050_AskForTopUpFirstTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForTopUpFirstTime");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("NatelGesperrtKredilimitenUeberschreitungUmB");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskForTopUpFirstTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060a_161205_CheckInputAskForTopUpFirstTime_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE, "1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161205");
		expectedResult.setNextState("IncreaseCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060b_161206_CheckInputAskForTopUpFirstTime_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE, "2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161206");
		expectedResult.setNextState("MobileDunnedIntro");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060c_161243_CheckInputAskForTopUpFirstTime_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161243");
		expectedResult.setNextState("SpeakPleaseSelectAfterFirstTopUp");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060d_161243_CheckInputAskForTopUpFirstTime_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161243");
		expectedResult.setNextState("SpeakPleaseSelectAfterFirstTopUp");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070_SpeakPleaseSelectAfterFirstTopUp() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPleaseSelectAfterFirstTopUp");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("MobileDunnedSecondTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID075_MobileDunnedSecondTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "MobileDunnedSecondTime");

		customerProfile.setCarsStatus("creditLimitInfoAvailable");
		customerProfile.setLimitExcess("50.00");
		customerProfile.setMinimumTopUpAmount("90.00");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:NatelGesperrtKredilimitenUeberschreitungUmA"+
				" ttaCurrency:"+ customerProfile.getLimitExcess());
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskForTopUpSecondTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080_AskForTopUpSecondTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForTopUpSecondTime");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("NatelGesperrtKredilimitenUeberschreitungUmB");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskForTopUpSecondTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090a_161244_CheckInputAskForTopUpSecondTime_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE, "1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161244");
		expectedResult.setNextState("IncreaseCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090b_161209_CheckInputAskForTopUpSecondTime_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE, "2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161209");
		expectedResult.setNextState("MobileDunnedSecondTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090d_161245_CheckInputAskForTopUpSecondTime_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161245");
		expectedResult.setNextState("SpeakPleaseSelectAfterTopUpSecondTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090e_161245_CheckInputAskForTopUpSecondTime_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161245");
		expectedResult.setNextState("SpeakPleaseSelectAfterTopUpSecondTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_SpeakNoInputReceivedAfterTopUpSecondTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPleaseSelectAfterTopUpSecondTime");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("MobileDunnedThirdTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID102_MobileDunnedThirdTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "MobileDunnedThirdTime");

		customerProfile.setCarsStatus("creditLimitInfoAvailable");
		customerProfile.setLimitExcess("50.00");
		customerProfile.setMinimumTopUpAmount("90.00");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:NatelGesperrtKredilimitenUeberschreitungUmA"+
				" ttaCurrency:"+ customerProfile.getLimitExcess());
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskForTopUpSecondTimeAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID105_AskForTopUpSecondTimeAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForTopUpSecondTimeAfterNINM");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("NatelGesperrtKredilimitenUeberschreitungUmB");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskForTopUpSecondTimeAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID106a_161244_CheckInputAskForTopUpSecondTimeAfterNINM_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTimeAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE, "1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161244");
		expectedResult.setNextState("IncreaseCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID106b_161209_CheckInputAskForTopUpSecondTimeAfterNINM_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTimeAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE, "2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161209");
		expectedResult.setNextState("MobileDunnedThirdTime");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID106d_161246_CheckInputAskForTopUpSecondTimeAfterNINM_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTimeAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161246");
		expectedResult.setNextState("SpeakGoodbyeThenDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID106e_161246_CheckInputAskForTopUpSecondTimeAfterNINM_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForTopUpSecondTimeAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161246");
		expectedResult.setNextState("SpeakGoodbyeThenDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110_SpeakGoodbyeThenDisconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakGoodbyeThenDisconnect");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:cars-goodbye");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120_IncreaseCreditLimit() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "IncreaseCreditLimit");

		expectedResult.setAction(SE_ACTION_INCREASEKL);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("CheckResultIncreaseCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130a_161212_CheckResultGetInvoices_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultIncreaseCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161212");
		expectedResult.setNextState("SpeakErrorIncreaseCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130b_161213_CheckResultGetInvoices_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultIncreaseCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161213");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_SpeakErrorIncreaseCreditLimit() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakErrorIncreaseCreditLimit");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:AuftragNichtAusgefuehrtMitarbeiterVerbinden");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SetPlayTransferPromptFalseThenRouting2CreditlimitBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_SpeakPaymentPromiseInfo() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPaymentPromiseInfo");

		customerProfile.setCarsStatus("creditLimitInfoAvailable");
		customerProfile.setLimitExcess("50.00");
		customerProfile.setMinimumTopUpAmount("90.00");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:DankeAuftragSelbstentsperrungReduktionInFuenfTagenA"+
				" ttaCurrency:"+ customerProfile.getMinimumTopUpAmount() +
				" portal:DankeAuftragSelbstentsperrungReduktionInFuenfTagenB");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskForRepeatPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160_AskForRepeatPaymentPromiseInfo() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForRepeatPaymentPromiseInfo");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("InfoWiederholenTaste1");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("1");
		expectedResult.setNextState("CheckInputAskForRepeatPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170a_161214_CheckInputAskForTopUpSecondTime_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForRepeatPaymentPromiseInfo");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE, "1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161214");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170b_CheckInputAskForTopUpSecondTime_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForRepeatPaymentPromiseInfo");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AskForFurtherInformationCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170c_CheckInputAskForTopUpSecondTime_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForRepeatPaymentPromiseInfo");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AskForFurtherInformationCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID180_AskForFurtherInformationCreditLimit() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AskForFurtherInformationCreditLimit");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FrageKLInfoAndereFrage");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFromAskForFurtherInformationCreditLimit");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID190a_CheckInputFromAskForFurtherInformationCreditLimit_Input_1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AddStatistic161203");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID190b_161221_CheckInputFromAskForFurtherInformationCreditLimit_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161221");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID190c_161223_CheckInputFromAskForFurtherInformationCreditLimit_NoInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161223");
		expectedResult.setNextState("AskForFurtherInformationCreditLimitNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID190d_161223_CheckInputFromAskForFurtherInformationCreditLimit_NoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161223");
		expectedResult.setNextState("AskForFurtherInformationCreditLimitNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID200_AskForFurtherInformationCreditLimitNoInputNoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AskForFurtherInformationCreditLimitNoInputNoMatch");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("RepeatAskForFurtherInformationCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID210_RepeatAskForFurtherInformationCreditLimit() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RepeatAskForFurtherInformationCreditLimit");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FrageKLInfoAndereFrage");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFromRepeatAskForFurtherInformationCreditLimit");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID220a_CheckInputFromRepeatAskForFurtherInformationCreditLimit_Input_1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AddStatistic161203");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID220b_161222_CheckInputFromRepeatAskForFurtherInformationCreditLimit_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161222");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID220c_161224_CheckInputFromRepeatAskForFurtherInformationCreditLimit_NoInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161224");
		expectedResult.setNextState("AskForFurtherInformationCreditLimitSecondNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID220d_161224_CheckInputFromRepeatAskForFurtherInformationCreditLimit_NoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161224");
		expectedResult.setNextState("AskForFurtherInformationCreditLimitSecondNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID230_AskForFurtherInformationCreditLimitSecondNoInputNoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AskForFurtherInformationCreditLimitSecondNoInputNoMatch");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SecondRepeatAskForFurtherInformationCreditLimit");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID240_SecondRepeatAskForFurtherInformationCreditLimit() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SecondRepeatAskForFurtherInformationCreditLimit");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FrageKLInfoAndereFrage");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID250a_CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit_Input_1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AddStatistic161203");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID250b_161222_CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161222");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID250c_161229_CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit_NoInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161229");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID250d_161229_CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit_NoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskForFurtherInformationCreditLimit");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161229");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
		
	public final void testID260_161203_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic161203");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("161203");
		expectedResult.setNextState("CommonWorkflow");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

//	 START COMMON WORKFLOW-------------------------------------------------------------------------------------
	public final void testID300_101101_CommonWorkflow() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CommonWorkflow");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101101");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID310_AskForCostControlFirstTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForCostControlFirstTime");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("KostenkonrtolleSMSTaste1AuflademoeglichkeitenTaste2OffeneRechnungenTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputAskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID320a_101111_CheckInputAskForCostControlFirstTime_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101111");
		expectedResult.setNextState("AskForQuickcheckSMSFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID320b_101112_CheckInputAskForCostControlFirstTime_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101112");
		expectedResult.setNextState("InfoSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID320c_101113_CheckInputAskForCostControlFirstTime_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101113");
		expectedResult.setNextState("GetInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID320d_101102_CheckInputAskForCostControlFirstTime_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101102");
		expectedResult.setNextState("SpeakPleaseSelectAfterFirstCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID320e_101102_CheckInputAskForCostControlFirstTime_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101102");
		expectedResult.setNextState("SpeakPleaseSelectAfterFirstCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID330_SpeakPleaseSelectAfterFirstCostControl() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPleaseSelectAfterFirstCostControl");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskForCostControlSecondTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID340_AskForCostControlSecondTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForCostControlSecondTime");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("KostenkonrtolleSMSTaste1AuflademoeglichkeitenTaste2OffeneRechnungenTaste3 UebrigeFragenTaste4");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("4");
		expectedResult.setNextState("CheckInputAskForCostControlSecondTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID350a_101121_CheckInputAskForCostControlSecondTime_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101121");
		expectedResult.setNextState("AskForQuickcheckSMSFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID350b_101122_CheckInputAskForCostControlSecondTime_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101122");
		expectedResult.setNextState("InfoSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID350c_101123_CheckInputAskForCostControlSecondTime_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101123");
		expectedResult.setNextState("GetInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID350d_101114_CheckInputAskForCostControlSecondTime_Input_4() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"4");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101114");
		expectedResult.setNextState("SpeakTransferThenSetFlagThen1stCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID350e_101103_CheckInputAskForCostControlSecondTime_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101103");
		expectedResult.setNextState("FirstNoInputAfterSecondCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID350f_101103_CheckInputAskForCostControlSecondTime_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForCostControlSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101103");
		expectedResult.setNextState("FirstNoInputAfterSecondCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID360_FirstNoInputAfterSecondCostControl() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "FirstNoInputAfterSecondCostControl");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("KeineEingabeMitarbeiterVerbindenTaste1");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("1");
		expectedResult.setNextState("CheckInputFirstNoInputAfterSecondCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID370a_101106_CheckInputFirstNoInputAfterSecondCostControl_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFirstNoInputAfterSecondCostControl");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101106");
		expectedResult.setNextState("SpeakTransferThenSetFlagThen1stCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID370b_101104_CheckInputFirstNoInputAfterSecondCostControl_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFirstNoInputAfterSecondCostControl");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101104");
		expectedResult.setNextState("SecondNoInputAfterSecondCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID370c_101104_CheckInputFirstNoInputAfterSecondCostControl_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFirstNoInputAfterSecondCostControl");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101104");
		expectedResult.setNextState("SecondNoInputAfterSecondCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID380_SecondNoInputAfterSecondCostControl() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SecondNoInputAfterSecondCostControl");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("KeineEingabeMitarbeiterVerbindenTaste1");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("1");
		expectedResult.setNextState("CheckInputSecondNoInputAfterSecondCostControl");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID390a_101105_CheckInputSecondNoInputAfterSecondCostControl_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputSecondNoInputAfterSecondCostControl");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101105");
		expectedResult.setNextState("SpeakTransferThenSetFlagThen1stCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID390b_101105_CheckInputSecondNoInputAfterSecondCostControl_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputSecondNoInputAfterSecondCostControl");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101105");
		expectedResult.setNextState("SpeakTransferThenSetFlagThen1stCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID390c_101105_CheckInputSecondNoInputAfterSecondCostControl_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputSecondNoInputAfterSecondCostControl");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101105");
		expectedResult.setNextState("SpeakTransferThenSetFlagThen1stCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID400_AskForQuickcheckSMSFirstTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForQuickcheckSMSFirstTime");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("RestguthabenMonatskostenSMSTaste1NeinTaste2");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskForQuickcheckSMSFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID410a_101131_CheckInputAskForQuickcheckSMSFirstTime_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101131");
		expectedResult.setNextState("SpeakConfirmQuickCheckSMSOrder");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID410b_101132_CheckInputAskForQuickcheckSMSFirstTime_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101132");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID410c_101133_CheckInputAskForQuickcheckSMSFirstTime_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101133");
		expectedResult.setNextState("AskForQuickcheckSMSSecondTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID410d_101133_CheckInputAskForQuickcheckSMSFirstTime_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSFirstTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101133");
		expectedResult.setNextState("AskForQuickcheckSMSSecondTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID420_AskForQuickcheckSMSSecondTime() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForQuickcheckSMSSecondTime");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("RestguthabenMonatskostenSMSTaste1NeinTaste2");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskForQuickcheckSMSSecondTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID430a_101131_CheckInputAskForQuickcheckSMSSecondTime_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101131");
		expectedResult.setNextState("SpeakConfirmQuickCheckSMSOrder");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID430b_101132_CheckInputAskForQuickcheckSMSSecondTime_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101132");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID430c_101132_CheckInputAskForQuickcheckSMSSecondTime_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101132");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID430d_101132_CheckInputAskForQuickcheckSMSSecondTime_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForQuickcheckSMSSecondTime");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101132");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID440_SpeakConfirmQuickCheckSMSOrder() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakConfirmQuickCheckSMSOrder");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:DankeWoechentlicheInfoSMS");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("OrderQuickCheckSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID450_OrderQuickCheckSMS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "OrderQuickCheckSMS");

		expectedResult.setAction(SE_ACTION_SUBSCRIBEQCSMS);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("CheckResultOrderQuickCheckSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID460_101135_CheckResultOrderQuickCheckSMS_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultOrderQuickCheckSMS");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101135");
		expectedResult.setNextState("2ndCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID470_101134_CheckResultOrderQuickCheckSMS_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultOrderQuickCheckSMS");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101134");
		expectedResult.setNextState("SpeakReturnToMainMenu");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID480_SpeakReturnToMainMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakReturnToMainMenu");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:Hauptmenu");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID490_SpeakInfoSMS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "InfoSMS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:DankeAuftragInfoAuflademoeglichkeitenSMS");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("OrderInfoSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID500_OrderInfoSMS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "OrderInfoSMS");

		expectedResult.setAction(SE_ACTION_SENDINFOSMS);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("SpeakReturnToMainMenuAfterInfoSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID510_SpeakReturnToMainMenuAfterInfoSMS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakReturnToMainMenuAfterInfoSMS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:Hauptmenu");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic101141");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID520_101141_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic101141");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101141");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID530_GetInvoices() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GetInvoices");

		expectedResult.setAction(SE_ACTION_GETINVOICES);
		expectedResult.setOutputVar1("dunning");
		expectedResult.setNextState("CheckResultGetInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID540a_101152_CheckResultGetInvoices_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101152");
		expectedResult.setNextState("3rdCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID540a_101152_CheckResultGetInvoices_SUCCESS_noUnbilledCost() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101152");
		expectedResult.setNextState("3rdCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID540b_CheckResultGetInvoices_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		customerProfile.setUnbilledCost("123.12");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BetragOffeneRechnungen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("CheckInvoiceAmount");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID550a_CheckInvoiceAmount_HaveOpenInvoice_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("invoices:");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic101151");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID550b_CheckInvoiceAmount_HaveOpenInvoice_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT, "false");

		customerProfile.setUnbilledCost("345.12");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:AlleRechnungenBezahltLaufendeMonatskosten"+
						   " ttaCurrency:"+customerProfile.getUnbilledCost());
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic101162");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID560_101151_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic101151");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101151");
		expectedResult.setNextState("SpeakReturnMainMenu");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID560_101162_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic101162");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101162");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID620_SpeakReturnMainMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakReturnMainMenu");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:Hauptmenu");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic101155");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID630_101155_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic101155");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101155");
		expectedResult.setNextState("AskForCostControlFirstTime");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID770a_101212_1stCheckForActiveCreditLimitBlock_HasCreditLimitBlock_ThenCreditlimitBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "1stCheckForActiveCreditLimitBlock");
		
		customerProfile.setCarsStatus("creditLimitBlock");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101212");
		expectedResult.setNextState("RoutingToCreditlimitBlocked");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID770b_101211_1stCheckForActiveCreditLimitBlock_NoCreditLimitBlock_ThenCCC() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "1stCheckForActiveCreditLimitBlock");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101211");
		expectedResult.setNextState("RoutingToCCC");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID780a_101222_2ndCheckForActiveCreditLimitBlock_HasCreditLimitBlock_ThenCreditlimitBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "2ndCheckForActiveCreditLimitBlock");
		
		customerProfile.setCarsStatus("creditLimitBlock");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101222");
		expectedResult.setNextState("RoutingToCreditlimitBlocked");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID780b_101221_2ndCheckForActiveCreditLimitBlock_NoCreditLimitBlock_ThenCCC() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "2ndCheckForActiveCreditLimitBlock");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101221");
		expectedResult.setNextState("RoutingToCCC");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID790a_101232_3rdCheckForActiveCreditLimitBlock_HasCreditLimitBlock_ThenCreditlimitBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "3rdCheckForActiveCreditLimitBlock");
		
		customerProfile.setCarsStatus("creditLimitBlock");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101232");
		expectedResult.setNextState("RoutingToCreditlimitBlocked");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID790b_101231_3rdCheckForActiveCreditLimitBlock_NoCreditLimitBlock_ThenCCC() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "3rdCheckForActiveCreditLimitBlock");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("101231");
		expectedResult.setNextState("RoutingToCCC");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID800_RoutingToCCC() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RoutingToCCC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("CCC");
		expectedResult.setOutputVar2("");
		expectedResult.setOutputVar3("");
		expectedResult.setNextState("Disconnect");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}	
	
	public final void testID817_SetPlayTransferPromptFalseThen3rdCheckForActiveCreditLimitBlock() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SetPlayTransferPromptFalseThen3rdCheckForActiveCreditLimitBlock");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("3rdCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID818_SpeakTransferThenSetFlagThen1stCheckForActiveCreditLimitBlock() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakTransferThenSetFlagThen1stCheckForActiveCreditLimitBlock");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:VerbindenMitarbeiter");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SetPlayTransferPromptFalseThen1stCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID819_SetPlayTransferPromptFalseThen1stCheckForActiveCreditLimitBlock() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SetPlayTransferPromptFalseThen1stCheckForActiveCreditLimitBlock");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("1stCheckForActiveCreditLimitBlock");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
			
//END COMMON WORKFLOW---------------------------------------------------------------------------------------

	public final void testID820_RoutingToCreditlimitBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RoutingToCreditlimitBlocked");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CREDITLIMIT_BLOCKED);
		expectedResult.setOutputVar2("");
		expectedResult.setOutputVar3("");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}	
	
	public final void testID821_SetPlayTransferPromptFalseThenRouting2CreditlimitBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SetPlayTransferPromptFalseThenRouting2CreditlimitBlocked");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("RoutingToCreditlimitBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID830_Back2Portal() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Back2Portal");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}	
}
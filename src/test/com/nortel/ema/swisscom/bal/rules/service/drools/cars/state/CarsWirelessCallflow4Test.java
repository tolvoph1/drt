/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: CarsWirelessCallflow4Test.java 161 2014-01-09 13:20:21Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-01-09 14:20:21 +0100 (Thu, 09 Jan 2014) $
 * $Revision: 161 $
 * 
 * JUnit Test Class to test the transfer rules
 * There is one test case per cell in the qualification tab of the Excel sheet
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
public class CarsWirelessCallflow4Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "cars/wireless-callflow4";
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
		junit.textui.TestRunner.run(CarsWirelessCallflow4Test.class);
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

	public final void testID010_141201_Init_Workflow() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141201");
		expectedResult.setNextState("AskOverdueInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030_AskOverdueInvoices() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskOverdueInvoices");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FragenUeberfaelligeRechnungenTaste1FragenOffeneRechnungenTaste2AndereAnliegenTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputAskOverdueInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040a_CheckInputAskOverdueInvoices_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AddStatistic141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID040b_141242_CheckInputAskOverdueInvoices_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141242");
		expectedResult.setNextState("GetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c_141241_CheckInputAskOverdueInvoices_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141241");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040d_141243_CheckInputAskOverdueInvoices_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141243");
		expectedResult.setNextState("SpeakPleaseSelectBeforeAskOverdueInvoicesAgain");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040e_141243_CheckInputAskOverdueInvoices_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141243");
		expectedResult.setNextState("SpeakPleaseSelectBeforeAskOverdueInvoicesAgain");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050_SpeakPleaseSelectBeforeAskOverdueInvoicesAgain() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPleaseSelectBeforeAskOverdueInvoicesAgain");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskOverdueInvoicesAgain");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060_AskOverdueInvoicesAgain() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskOverdueInvoicesAgain");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FragenUeberfaelligeRechnungenTaste1FragenOffeneRechnungenTaste2AndereAnliegenTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputAskOverdueInvoicesAgain");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070a_CheckInputAskOverdueInvoicesAgain_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AddStatistic141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070b_141244_CheckInputAskOverdueInvoicesAgain_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141244");
		expectedResult.setNextState("GetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070c_141246_CheckInputAskOverdueInvoicesAgain_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141246");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070d_141245_CheckInputAskOverdueInvoicesAgain_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141245");
		expectedResult.setNextState("SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070e_141245_CheckInputAskOverdueInvoicesAgain_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141245");
		expectedResult.setNextState("SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID072_SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AskOverdueInvoicesAgainAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID073_AskOverdueInvoicesAgainAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskOverdueInvoicesAgainAfterNINM");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FragenUeberfaelligeRechnungenTaste1FragenOffeneRechnungenTaste2AndereAnliegenTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskOverdueInvoicesAgainAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID074a_CheckInputAskOverdueInvoicesAgainAfterNINM_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AddStatistic141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID074b_141244_CheckInputAskOverdueInvoicesAgainAfterNINM_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141244");
		expectedResult.setNextState("GetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID074c_141246_CheckInputAskOverdueInvoicesAgainAfterNINM_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141246");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID074d_141247_CheckInputAskOverdueInvoicesAgainAfterNINM_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141247");
		expectedResult.setNextState("SpeakGoodbyeAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID074e_141247_CheckInputAskOverdueInvoicesAgainAfterNINM_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141247");
		expectedResult.setNextState("SpeakGoodbyeAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID075_SpeakGoodbyeAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakGoodbyeAfterNINM");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:cars-goodbye");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID078_GetInvoices() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GetInvoices");

		expectedResult.setAction(SE_ACTION_GETINVOICES);
		expectedResult.setOutputVar1("dunning");
		expectedResult.setNextState("CheckResultGetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID079a_141202_CheckResultGetInvoices_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141202");
		expectedResult.setNextState("RoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID079b_CheckResultGetInvoices_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("SpeakOpenInvoiceIntro");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080_SpeakOpenInvoiceIntro() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakOpenInvoiceIntro");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:ZuZahlendeRechnungen");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141203");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_141203_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141203");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141203");
		expectedResult.setNextState("CheckInvoiceAmount");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100a_141205_CheckInvoiceAmount_HaveOpenInvoice_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT,"true");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141205");
		expectedResult.setNextState("SpeakInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100b_141204_CheckInvoiceAmount_HaveOpenInvoice_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT,"false");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141204");
		expectedResult.setNextState("SpeakAllInvoicesPaid");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110a_SpeakInvoices() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakInvoices");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("invoices:");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SetSendSMSFlagTrueAfterInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110b_SpeakAllInvoicesPaid() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakAllInvoicesPaid");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:UeberfaelligeRechnungenBeglichen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("FurtherQuestions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID115_SetSendSMSFlag_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SetSendSMSFlagTrueAfterInvoices");

		expectedResult.setAction(SE_ACTION_SMSAFTERHANGUP);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("CheckRecentPayment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120a_CheckRecentPayment_HaveRecentPayment_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckRecentPayment");
		callProfile.put(CPK_HAVE_RECENT_PAYMENT, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("recentPayment:LetzteZahlungA,LetzteZahlungB");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141206");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120b_CheckRecentPayment_HaveRecentPayment_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckRecentPayment");
		callProfile.put(CPK_HAVE_RECENT_PAYMENT, "false");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:NochKeineZahlung");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141206");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_141206_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141206");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141206");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_SpeakPaymentPromiseInfo() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPaymentPromiseInfo");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BestaetigungZahlungInnertDreiTagen");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141301");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_141301_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141301");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141301");
		expectedResult.setNextState("AskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160_AskForPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForPaymentPromise");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("ZusicherungZahlungTaste1InfoWiederholenTaste2");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170a_141302_CheckInputAskForPaymentPromise_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141302");
		expectedResult.setNextState("SendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170b_141303_CheckInputAskForPaymentPromise_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141303");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170c_141304_CheckInputAskForPaymentPromise_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141304");
		expectedResult.setNextState("RepeatAskForPaymentPromiseIntro");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170d_141304_CheckInputAskForPaymentPromise_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141304");
		expectedResult.setNextState("RepeatAskForPaymentPromiseIntro");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID180_RepeatAskForPaymentPromiseIntro() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatAskForPaymentPromiseIntro");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141305");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190_141305_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141305");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141305");
		expectedResult.setNextState("RepeatAskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200_RepeatAskForPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatAskForPaymentPromise");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("ZusicherungZahlungTaste1InfoWiederholenTaste2 VerbindenMitarbeiterTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputRepeatAskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210a_141306_CheckInputRepeatAskForPaymentPromise_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141306");
		expectedResult.setNextState("SendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210b_141307_CheckInputRepeatAskForPaymentPromise_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141307");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210c_141309_CheckInputRepeatAskForPaymentPromise_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210d_141308_CheckInputRepeatAskForPaymentPromise_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141308");
		expectedResult.setNextState("NoInputDetectedAfter141308");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210e_141308_CheckInputRepeatAskForPaymentPromise_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141308");
		expectedResult.setNextState("NoInputDetectedAfter141308");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID220_NoInputDetectedAfter112308() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "NoInputDetectedAfter141308");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:keineEingabe");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141305AfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID225_141305_AddStatisticAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141305AfterNINM");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141305");
		expectedResult.setNextState("RepeatAskForPaymentPromiseAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID226_RepeatAskForPaymentPromiseAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatAskForPaymentPromiseAfterNINM");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("ZusicherungZahlungTaste1InfoWiederholenTaste2 VerbindenMitarbeiterTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputRepeatAskForPaymentPromiseAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID227a_141306_CheckInputRepeatAskForPaymentPromiseAfterNINM_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141306");
		expectedResult.setNextState("SendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID227b_141307_CheckInputRepeatAskForPaymentPromiseAfterNINM_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141307");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID227c_141309_CheckInputRepeatAskForPaymentPromiseAfterNINM_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID227d_141309_CheckInputRepeatAskForPaymentPromiseAfterNINM_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID227e_141309_CheckInputRepeatAskForPaymentPromiseAfterNINM_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID230_SendPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SendPaymentPromise");

		expectedResult.setAction(SE_ACTION_PAYMENTPROMISE);
		expectedResult.setOutputVar1("dunning");
		expectedResult.setNextState("CheckResultSendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID240a_CheckResultSendPaymentPromise_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultSendPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:DankeZusicherungZahlungBarringPrevention");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141311");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID240b_141310_CheckResultSendPaymentPromise_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultSendPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141310");
		expectedResult.setNextState("SpeakErrorPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID250_SpeakErrorPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakErrorPaymentPromise");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:AuftragNichtAusgefuehrtMitarbeiterVerbinden");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SetPlayTransferPromptFalseThenRouting2BarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID255_SetPlayTransferPromptFalseThenRouting2BarringPrevention() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SetPlayTransferPromptFalseThenRouting2BarringPrevention");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID260_141311_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141311");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141311");
		expectedResult.setNextState("FurtherQuestions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID270_141248_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141248");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141248");
		expectedResult.setNextState("GetInvoicesAfter141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID280_GetInvoicesAfter141248() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GetInvoicesAfter141248");

		expectedResult.setAction(SE_ACTION_GETINVOICES);
		expectedResult.setOutputVar1("dunning");
		expectedResult.setNextState("CheckResultGetInvoicesAfter141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID290a_141315_CheckResultGetInvoicesAfter141248_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoicesAfter141248");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141315");
		expectedResult.setNextState("RoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID290b_CheckResultGetInvoicesAfter141248_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoicesAfter141248");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("SendPaymentPromiseAfter141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID310_SendPaymentPromiseAfter141248() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SendPaymentPromiseAfter141248");

		expectedResult.setAction(SE_ACTION_PAYMENTPROMISE);
		expectedResult.setOutputVar1("dunning");
		expectedResult.setNextState("CheckResultSendPaymentPromiseAfter141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID320a_CheckResultSendPaymentPromiseAfter141248_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultSendPaymentPromiseAfter141248");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:DankeInformationZahlungAufWiederhoeren");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic141249");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID320b_141310_CheckResultSendPaymentPromise_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultSendPaymentPromiseAfter141248");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141310");
		expectedResult.setNextState("SpeakErrorPaymentPromiseAfter141248");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID330_SpeakErrorPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakErrorPaymentPromiseAfter141248");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:AuftragNichtAusgefuehrtMitarbeiterVerbinden");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SetPlayTransferPromptFalseThenRouting2BarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID340_141249_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic141249");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141249");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	

	public final void testID700_FurtherQuestions() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "FurtherQuestions");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("WeitereFragenrechnungTaste1AndereFragenTaste2");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFurtherQuestions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710a_141312_CheckResultFurtherQuestions_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141312");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710b_141313_CheckResultFurtherQuestions_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141313");
		expectedResult.setNextState("RepeatFurtherQuestionsExtraPrompt");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710c_141313_CheckResultFurtherQuestions_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141313");
		expectedResult.setNextState("RepeatFurtherQuestionsExtraPrompt");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID720_RepeatFurtherQuestionsExtraPrompt() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatFurtherQuestionsExtraPrompt");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("WeitereFragenrechnungTaste1AndereFragenTaste2 KeineWeiterenInformationenAuflegen");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFurtherQuestionsAfterNoInput");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID730a_141416_CheckResultFurtherQuestionsAfterNoInput_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141416");
		expectedResult.setNextState("SendInvoiceSMSThenBack2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID730b_141232_CheckResultFurtherQuestionsAfterNoInput_AnyInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("141314");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID820_SendInvoiceSMSThenRoutingToBarringPrevention() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SendInvoiceSMSThenRoutingToBarringPrevention");

		expectedResult.setAction(SE_ACTION_SENDINVOICESMS);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("RoutingToBarringPrevention");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID821_RoutingToBarringPrevention() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RoutingToBarringPrevention");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("");
		expectedResult.setOutputVar3("");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID829_SendInvoiceSMSThenBack2Portal() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SendInvoiceSMSThenBack2Portal");

		expectedResult.setAction(SE_ACTION_SENDINVOICESMS);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("Back2Portal");

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

	public final void testID900_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Disconnect");

		expectedResult.setAction(SE_ACTION_DISCONNECT);
		expectedResult.setNextState("ThisShouldNeverHappen");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}
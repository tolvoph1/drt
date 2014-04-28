/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: CarsWirelineCallflow2Test.java 162 2014-01-09 13:41:46Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-01-09 14:41:46 +0100 (Thu, 09 Jan 2014) $
 * $Revision: 162 $
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
public class CarsWirelineCallflow2Test extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "cars/wireline-callflow2";
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
		junit.textui.TestRunner.run(CarsWirelineCallflow2Test.class);
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

	public final void testID010_221201_Init_Workflow() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221201");
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
		expectedResult.setNextState("AddStatistic221248");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID040b_221242_CheckInputAskOverdueInvoices_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221242");
		expectedResult.setNextState("GetInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID040c_221241_CheckInputAskOverdueInvoices_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221241");
		expectedResult.setNextState("Back2Portal");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID040d_221243_CheckInputAskOverdueInvoices_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221243");
		expectedResult.setNextState("SpeakPleaseSelectBeforeAskOverdueInvoicesAgain");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID040e_221243_CheckInputAskOverdueInvoices_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221243");
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
		expectedResult.setNextState("AddStatistic221248");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070b_221246_CheckInputAskOverdueInvoicesAgain_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221246");
		expectedResult.setNextState("GetInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070c_221244_CheckInputAskOverdueInvoicesAgain_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221244");
		expectedResult.setNextState("Back2Portal");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070d_221245_CheckInputAskOverdueInvoicesAgain_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221245");
		expectedResult.setNextState("SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070e_221245_CheckInputAskOverdueInvoicesAgain_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgain");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221245");
		expectedResult.setNextState("SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID072_SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPleaseSelectBeforeAskOverdueInvoicesAgainAfterNINM");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
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
		expectedResult.setOutputVar3("3");
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
		expectedResult.setNextState("AddStatistic221248");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID074b_221246_CheckInputAskOverdueInvoicesAgainAfterNINM_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221246");
		expectedResult.setNextState("GetInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID074c_221244_CheckInputAskOverdueInvoicesAgainAfterNINM_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221244");
		expectedResult.setNextState("Back2Portal");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID074d_221247_CheckInputAskOverdueInvoicesAgainAfterNINM_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221247");
		expectedResult.setNextState("SpeakGoodbyeAfterNINM");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID074e_221247_CheckInputAskOverdueInvoicesAgainAfterNINM_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskOverdueInvoicesAgainAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221247");
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
	
	public final void testID079a_221202_CheckResultGetInvoices_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221202");
		expectedResult.setNextState("RoutingToDunningLetterSMS");
		
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
		expectedResult.setOutputVar1("portal:InfoUeberfaelligeRechnungen");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic221203");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID090_221203_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic221203");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221203");
		expectedResult.setNextState("CheckInvoiceAmount");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID100a_221205_CheckInvoiceAmount_HaveOpenInvoice_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT,"true");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221205");
		expectedResult.setNextState("SpeakInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID100b_221204_CheckInvoiceAmount_HaveOpenInvoice_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT,"false");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221204");
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
		expectedResult.setNextState("CheckRecentPayment");
		
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
		
	public final void testID120a_CheckRecentPayment_HaveRecentPayment_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckRecentPayment");
		callProfile.put(CPK_HAVE_RECENT_PAYMENT, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("recentPayment:LetzteZahlungA,LetzteZahlungB");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic221206");
		
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
		expectedResult.setNextState("AddStatistic221206");	

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID130_221206_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic221206");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221206");
		expectedResult.setNextState("HearAgainAfterInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID140_HearAgainAfterInvoices() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "HearAgainAfterInvoices");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("AngabenWiederholenTaste1");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("1");
		expectedResult.setNextState("CheckInputFromHearAgainAfterInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID150a_221207_CheckInputFromHearAgainAfterInvoices_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromHearAgainAfterInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221207");
		expectedResult.setNextState("SpeakInvoices");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID150b_221208_CheckInputFromHearAgainAfterInvoices_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromHearAgainAfterInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221208");
		expectedResult.setNextState("FurtherQuestions");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID150c_221208_CheckInputFromHearAgainAfterInvoices_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromHearAgainAfterInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221208");
		expectedResult.setNextState("FurtherQuestions");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID200_221248_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic221248");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221248");
		expectedResult.setNextState("SpeakPromptThanksPaymentInfo");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID210_SpeakPromptThanksPaymentInfo() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPromptThanksPaymentInfo");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:DankeInformationZahlungAufWiederhoeren");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic221249");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID220_221249_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic221249");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221249");
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
	
	public final void testID710a_221230_CheckResultFurtherQuestions_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221230");
		expectedResult.setNextState("RoutingToDunningLetterSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID710b_221415_CheckResultFurtherQuestions_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221415");
		expectedResult.setNextState("Back2Portal");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID710c_221231_CheckResultFurtherQuestions_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221231");
		expectedResult.setNextState("RepeatFurtherQuestionsExtraPrompt");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID710d_221231_CheckResultFurtherQuestions_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221231");
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
	
	public final void testID730a_221416_CheckResultFurtherQuestionsAfterNoInput_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221416");
		expectedResult.setNextState("Back2Portal");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID730b_221232_CheckResultFurtherQuestionsAfterNoInput_AnyInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("221232");
		expectedResult.setNextState("RoutingToDunningLetterSMS");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
		
	public final void testID821_RoutingToDunningLetterSMS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RoutingToDunningLetterSMS");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setNextState("Disconnect");
		
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
/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: CarsWirelessCallflow1SCTest.java 156 2013-12-10 08:00:59Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-12-10 09:00:59 +0100 (Tue, 10 Dec 2013) $
 * $Revision: 156 $
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
public class CarsWirelessCallflow1SCTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "cars/wireless-callflow1-sc";
	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
	private static ServiceConfigurationMap serviceConfigurationMap;

	private static final String STRUCTURED_CUSTOMER_YES = "2.1423";
	
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
		junit.textui.TestRunner.run(CarsWirelessCallflow1SCTest.class);
	}

	private static void addCarsStatus(String status) {
		if (customerProfile.getCarsStatus().equals("")) {
			customerProfile.setCarsStatus(status);
		} else {
			customerProfile.setCarsStatus(customerProfile.getCarsStatus()+","+status);
		}
	}
	
	private static void setTestData(String flags) {

		// Split testdata
		String splitFlags[] = flags.split(",");
		// Analyze split flags
		for (int i=0; i<splitFlags.length;i++) {
			//System.out.println("Checking value at position ["+i+"]:"+splitFlags[i]);
			String check = splitFlags[i].toUpperCase();
			if (check.equals("E")) {
				customerProfile.setBillingAccount(STRUCTURED_CUSTOMER_YES);
				customerProfile.setBillAccntID("");
				customerProfile.setOuTypeCode("Something Else");
			} else if (check.equals("F")) {
				addCarsStatus("dunningProcedure");
			} else if (check.equals("G")) {
				customerProfile.setSegment(SEGMENT_RES);
			} else if (check.equals("H")) {
				addCarsStatus("interactionHistoryAvailable");
			} else if (check.equals("I")) {
				addCarsStatus("block");
			} else if (check.equals("J")) {
				customerProfile.setBillingProfileCreditLimit("100");
			} else if (check.equals("K")) {
				addCarsStatus("creditLimitBlock");
			} else if (check.equals("L")) {
				callProfile.setIdentifiedByANI(TRUE);
			} else if (check.equals("M")) {
				addCarsStatus("debarringPending");
			} else if (check.equals("N")) {
				customerProfile.setAge("30");
			} else if (check.equals("O")) {
				addCarsStatus("creditLimitOverValue");
			} else if (check.equals("P")) {
				addCarsStatus("creditLimitRecentPP");
			} else if (check.equals("Q")) {
				addCarsStatus("recentBlock");
			} else if (check.equals("R")) {
				addCarsStatus("quickProcedure");
			} else if (check.equals("S")) {
				addCarsStatus("dunningLevelMinor");
			} else if (check.equals("T")) {
				addCarsStatus("dunningLevelMajor");
			} else if (check.equals("U")) {
				addCarsStatus("recentPaymentCheck");
			} else if (check.equals("V")) {
				addCarsStatus("dunningLetter");
			} else if (check.equals("W")) {
				addCarsStatus("recentDunningLetter");
			} else if (check.equals("X")) {
				addCarsStatus("dunningSMS");
			} else if (check.equals("Y")) {
				addCarsStatus("recentDunningSMS");
			} else if (check.equals("Z")) {
				addCarsStatus("partialBlock");
			} else if (check.equals("AA")) {
				addCarsStatus("recentPartialBlock");
			} else if (check.equals("AB")) {
				addCarsStatus("partialBlockRecentPP");
			} else if (check.equals("AC")) {
				customerProfile.setBillingProfileCreditLimit("100");
			}

		}
	}
	
	protected void setUp() {
		// Create objects
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProfile.setCarsStatus("");
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		serviceConfigurationMap.put("cars.wless.ov.limit.age","18");
		serviceConfigurationMap.put("cars.wless.active.callflow.1ns",TRUE);
		serviceConfigurationMap.put("cars.wless.active.callflow.1sc",TRUE);
		serviceConfigurationMap.put("cars.wless.active.callflow.2",TRUE);
		serviceConfigurationMap.put("cars.wless.active.callflow.4",TRUE);
		serviceConfigurationMap.put("cars.wless.active.callflow.5",TRUE);
		serviceConfigurationMap.put("cars.wless.active.callflow.6",TRUE);
		serviceConfigurationMap.put("cars.wless.active.callflow.7",TRUE);
		serviceConfigurationMap.put("cars.wless.active.callflow.8",TRUE);
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
	}

	public final void testID010_112201_Init_Workflow() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112201");
		expectedResult.setNextState("GetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020_GetInvoices() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GetInvoices");

		expectedResult.setAction(SE_ACTION_GETINVOICES);
		expectedResult.setOutputVar1("obo");
		expectedResult.setNextState("CheckResultGetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030a_112202_CheckResultGetInvoices_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112202");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030b_CheckResultGetInvoices_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:InfoNatelGesperrtSelfcareWireless");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic112203");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040_112203_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic112203");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112203");
		expectedResult.setNextState("CheckInvoiceAmount");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050a_112205_CheckInvoiceAmount_HaveOpenInvoice_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT, "true");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112205");
		expectedResult.setNextState("SpeakInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050b_112204_CheckInvoiceAmount_HaveOpenInvoice_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT, "false");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112204");
		expectedResult.setNextState("SpeakAllInvoicesPaid");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060a_SpeakInvoices() throws Exception {
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

	public final void testID060b_SpeakAllInvoicesPaid() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakAllInvoicesPaid");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:FaelligeRechnungenBeglichen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("CreditLimitOverviewB");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID065_SetSendSMSFlag_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SetSendSMSFlagTrueAfterInvoices");

		expectedResult.setAction(SE_ACTION_SMSAFTERHANGUP);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("CheckRecentPayment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070a_CheckRecentPayment_HaveRecentPayment_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckRecentPayment");
		callProfile.put(CPK_HAVE_RECENT_PAYMENT, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("recentPayment:LetzteZahlungA,LetzteZahlungB");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic112206");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID070b_CheckRecentPayment_HaveRecentPayment_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckRecentPayment");
		callProfile.put(CPK_HAVE_RECENT_PAYMENT, "false");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:NochKeineZahlung");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic112206");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080_112206_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic112206");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112206");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_SpeakPaymentPromiseInfo() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPaymentPromiseInfo");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:ZusicherungZahlungSelfcareWireless");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic112301");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_112301_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic112301");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112301");
		expectedResult.setNextState("AskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110_AskForPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AskForPaymentPromise");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("AnschlussEntsperrenTaste1InfoWiederholenTaste2");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputAskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120a_112302_CheckInputAskForPaymentPromise_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112302");
		expectedResult.setNextState("SendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120b_112303_CheckInputAskForPaymentPromise_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112303");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120c_112304_CheckInputAskForPaymentPromise_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112304");
		expectedResult.setNextState("RepeatAskForPaymentPromiseIntro");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120d_112304_CheckInputAskForPaymentPromise_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112304");
		expectedResult.setNextState("RepeatAskForPaymentPromiseIntro");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_RepeatAskForPaymentPromiseIntro() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatAskForPaymentPromiseIntro");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic112305");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_112305_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic112305");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112305");
		expectedResult.setNextState("RepeatAskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_RepeatAskForPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatAskForPaymentPromise");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("AnschlussEntsperrenTaste1InfoWiederholenTaste2 VerbindenMitarbeiterTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputRepeatAskForPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160a_112306_CheckInputRepeatAskForPaymentPromise_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112306");
		expectedResult.setNextState("SendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160b_112307_CheckInputRepeatAskForPaymentPromise_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112307");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160c_112309_CheckInputRepeatAskForPaymentPromise_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160d_112308_CheckInputRepeatAskForPaymentPromise_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112308");
		expectedResult.setNextState("NoInputDetectedAfter112308");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160e_112308_CheckInputRepeatAskForPaymentPromise_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112308");
		expectedResult.setNextState("NoInputDetectedAfter112308");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170_NoInputDetectedAfter112308() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "NoInputDetectedAfter112308");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:keineEingabe");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic112305AfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID175_112305_AddStatisticAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic112305AfterNINM");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112305");
		expectedResult.setNextState("RepeatAskForPaymentPromiseAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID180_RepeatAskForPaymentPromiseAfterNINM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatAskForPaymentPromiseAfterNINM");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("AnschlussEntsperrenTaste1InfoWiederholenTaste2 VerbindenMitarbeiterTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputRepeatAskForPaymentPromiseAfterNINM");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190a_112306_CheckInputRepeatAskForPaymentPromiseAfterNINM_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112306");
		expectedResult.setNextState("SendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190b_112307_CheckInputRepeatAskForPaymentPromiseAfterNINM_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112307");
		expectedResult.setNextState("SpeakPaymentPromiseInfo");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190c_112309_CheckInputRepeatAskForPaymentPromiseAfterNINM_Input_3() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190d_112309_CheckInputRepeatAskForPaymentPromiseAfterNINM_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190e_112309_CheckInputRepeatAskForPaymentPromiseAfterNINM_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputRepeatAskForPaymentPromiseAfterNINM");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112309");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200_SendPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SendPaymentPromise");

		expectedResult.setAction(SE_ACTION_PAYMENTPROMISE);
		expectedResult.setOutputVar1("obo");
		expectedResult.setNextState("CheckResultSendPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210a_CheckResultSendPaymentPromise_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultSendPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:DankeZusicherungZahlungSelfcareWireless");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic112311");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210b_112310_CheckResultSendPaymentPromise_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultSendPaymentPromise");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112310");
		expectedResult.setNextState("SpeakErrorPaymentPromise");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID220_SpeakErrorPaymentPromise() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakErrorPaymentPromise");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:AuftragNichtAusgefuehrtMitarbeiterVerbinden");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SetPlayTransferPromptFalseThenRouting2DunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID225_SetPlayTransferPromptFalseThenRouting2DunningBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SetPlayTransferPromptFalseThenRouting2DunningBlocked");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID230_112311_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic112311");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112311");
		expectedResult.setNextState("CreditLimitOverviewA");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID300_A_ID01a_WF7_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,M");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow7");
		expectedResult.setOutputVar2("112114|112118");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID01i_WF7_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,M");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.7",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112114|112118");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID02a_WF5_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,N,O");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow5");
		expectedResult.setOutputVar2("112114|112119|112120|112122");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID02i_WF5_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,N,O");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112114|112119|112120|112122");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID03a_WF5_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,N,P");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow5");
		expectedResult.setOutputVar2("112114|112119|112120|112123|112124");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID03i_WF5_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,N,P");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112114|112119|112120|112123|112124");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID04a_WF6_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,N");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow6");
		expectedResult.setOutputVar2("112114|112119|112120|112123|112125");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID04i_WF6_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K,N");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.6",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112114|112119|112120|112123|112125");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID05a_WF5_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow5");
		expectedResult.setOutputVar2("112114|112119|112121");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID05i_WF5_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		setTestData("K");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112114|112119|112121");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID06_112115_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		
		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112115");
		expectedResult.setNextState("ExitPointA");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID07a_WF7_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,M");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow7");
		expectedResult.setOutputVar2("112116|112118");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID07i_WF7_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,M");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.7",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112116|112118");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID08a_WF5_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,N,O");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow5");
		expectedResult.setOutputVar2("112116|112119|112120|112122");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID08i_WF5_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,N,O");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112116|112119|112120|112122");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID09a_WF5_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,N,P");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow5");
		expectedResult.setOutputVar2("112116|112119|112120|112123|112124");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID09i_WF5_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,N,P");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112116|112119|112120|112123|112124");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID10a_WF6_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,N");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow6");
		expectedResult.setOutputVar2("112116|112119|112120|112123|112125");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID10i_WF6_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K,N");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.6",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112116|112119|112120|112123|112125");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID11a_WF5_active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K");
		
		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1("cars/wireless-callflow5");
		expectedResult.setOutputVar2("112116|112119|112121");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID11i_WF5_inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		setTestData("K");
		
		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);
		
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("112116|112119|112121");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID12_112117_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		
		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112117");
		expectedResult.setNextState("ExitPointB");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID310a_ExitPointA() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "ExitPointA");
		
		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("FurtherQuestions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID310b_ExitPointB() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "ExitPointB");
		
		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("FurtherQuestions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID700_FurtherQuestions() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "FurtherQuestions");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("HinweisNatelEntsperrungFrageRechnungAndereFragen");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFurtherQuestions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710a_112312_CheckResultFurtherQuestions_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112312");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID710b_112415_CheckResultFurtherQuestions_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112415");
		expectedResult.setNextState("SendInvoiceSMSThenBack2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710c_112313_CheckResultFurtherQuestions_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112313");
		expectedResult.setNextState("RepeatFurtherQuestionsExtraPrompt");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710d_112313_CheckResultFurtherQuestions_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112313");
		expectedResult.setNextState("RepeatFurtherQuestionsExtraPrompt");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID720_RepeatFurtherQuestionsExtraPrompt() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatFurtherQuestionsExtraPrompt");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("HinweisNatelEntsperrungFrageRechnungAndereFragen KeineWeiterenInformationenAuflegen");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFurtherQuestionsAfterNoInput");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID730a_112416_CheckResultFurtherQuestionsAfterNoInput_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112416");
		expectedResult.setNextState("SendInvoiceSMSThenBack2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID730b_112314_CheckResultFurtherQuestionsAfterNoInput_AnyInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("112314");
		expectedResult.setNextState("SendInvoiceSMSThenRoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID820_SendInvoiceSMSThenRoutingToDunningBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SendInvoiceSMSThenRoutingToDunningBlocked");

		expectedResult.setAction(SE_ACTION_SENDINVOICESMS);
		expectedResult.setOutputVar1("true");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID821_RoutingToDunningBlocked() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RoutingToDunningBlocked");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
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
}

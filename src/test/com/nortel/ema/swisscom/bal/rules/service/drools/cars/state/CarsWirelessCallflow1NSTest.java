/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: CarsWirelessCallflow1NSTest.java 159 2013-12-18 10:36:38Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-12-18 11:36:38 +0100 (Wed, 18 Dec 2013) $
 * $Revision: 159 $
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
public class CarsWirelessCallflow1NSTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "cars/wireless-callflow1-ns";
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
		junit.textui.TestRunner.run(CarsWirelessCallflow1NSTest.class);
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

	public final void testID010_111201_Init_Workflow() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111201");
		expectedResult.setNextState("AskQuestionAboutCurrentBlockOrOther");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID01101_AskQuestionAboutCurrentBlockOrOther() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AskQuestionAboutCurrentBlockOrOther");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FrageSperreOderRechnungAndereFragen");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFromAskQuestionAboutCurrentBlockOrOther");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01102a_111401_CheckInputFromAskQuestionAboutCurrentBlockOrOther_Input_1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111401");
		expectedResult.setNextState("GetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01102b_111402_CheckInputFromAskQuestionAboutCurrentBlockOrOther_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111402");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01102c_111403_CheckInputFromAskQuestionAboutCurrentBlockOrOther_NoInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111403");
		expectedResult.setNextState("AskQuestionAboutCurrentBlockOrOtherNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01102d_111403_CheckInputFromAskQuestionAboutCurrentBlockOrOther_NoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111403");
		expectedResult.setNextState("AskQuestionAboutCurrentBlockOrOtherNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01103_AskQuestionAboutCurrentBlockOrOtherNoInputNoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AskQuestionAboutCurrentBlockOrOtherNoInputNoMatch");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("RepeatAskQuestionAboutCurrentBlockOrOther");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01104_RepeatAskQuestionAboutCurrentBlockOrOther() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RepeatAskQuestionAboutCurrentBlockOrOther");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FrageSperreOderRechnungAndereFragen");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01105a_111405_CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther_Input_1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111405");
		expectedResult.setNextState("GetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01105b_111406_CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111406");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01105c_111407_CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther_NoInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111407");
		expectedResult.setNextState("AskQuestionAboutCurrentBlockOrOtherSecondNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01105d_111407_CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther_NoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111407");
		expectedResult.setNextState("AskQuestionAboutCurrentBlockOrOtherSecondNoInputNoMatch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01106_AskQuestionAboutCurrentBlockOrOtherSecondNoInputNoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AskQuestionAboutCurrentBlockOrOtherSecondNoInputNoMatch");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("SecondRepeatAskQuestionAboutCurrentBlockOrOther");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01107_SecondRepeatAskQuestionAboutCurrentBlockOrOther() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "SecondRepeatAskQuestionAboutCurrentBlockOrOther");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FrageSperreOderRechnungAndereFragen");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("2");
		expectedResult.setNextState("CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01108a_111405_CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther_Input_1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111405");
		expectedResult.setNextState("GetInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01108b_111406_CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111406");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01108c_111409_CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther_NoInput() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111409");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID01108d_111409_CheckInputFromRepeatAskQuestionAboutCurrentBlockOrOther_NoMatch() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromSecondRepeatAskQuestionAboutCurrentBlockOrOther");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111409");
		expectedResult.setNextState("Back2Portal");

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

	public final void testID030a_111202_CheckResultGetInvoices_ERROR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "ERROR");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111202");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030b_CheckResultGetInvoices_SUCCESS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultGetInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE, "SUCCESS");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:InfoNatelGesperrtNoSelfcareWirelessNoSMS");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic111203");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040_111203_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic111203");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111203");
		expectedResult.setNextState("CheckInvoiceAmount");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050a_111205_CheckInvoiceAmount_HaveOpenInvoice_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT, "true");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111205");
		expectedResult.setNextState("SpeakInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050b_111204_CheckInvoiceAmount_HaveOpenInvoice_false() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInvoiceAmount");
		callProfile.put(CPK_CARS_OPENINVOICEAMOUNT, "false");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111204");
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
		expectedResult.setNextState("CheckRecentPayment");

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

	public final void testID070a_CheckRecentPayment_HaveRecentPayment_true() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckRecentPayment");
		callProfile.put(CPK_HAVE_RECENT_PAYMENT, "true");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("recentPayment:LetzteZahlungA,LetzteZahlungB");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic111206");

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
		expectedResult.setNextState("AddStatistic111206");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID080_111206_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic111206");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111206");
		expectedResult.setNextState("HearAgainAfterInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID090_HearAgainAfterInvoices() throws Exception {
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

	public final void testID100a_111207_CheckInputFromHearAgainAfterInvoices_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromHearAgainAfterInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111207");
		expectedResult.setNextState("SpeakInvoices");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100b_111208CheckInputFromHearAgainAfterInvoices_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromHearAgainAfterInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111208");
		expectedResult.setNextState("CreditLimitOverviewA");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100c_CheckInputFromHearAgainAfterInvoices_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromHearAgainAfterInvoices");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111208");
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
		expectedResult.setOutputVar2("111114|111118");
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
		expectedResult.setOutputVar2("111114|111118");
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
		expectedResult.setOutputVar2("111114|111119|111120|111122");
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
		expectedResult.setOutputVar2("111114|111119|111120|111122");
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
		expectedResult.setOutputVar2("111114|111119|111120|111123|111124");
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
		expectedResult.setOutputVar2("111114|111119|111120|111123|111124");
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
		expectedResult.setOutputVar2("111114|111119|111120|111123|111125");
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
		expectedResult.setOutputVar2("111114|111119|111120|111123|111125");
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
		expectedResult.setOutputVar2("111114|111119|111121");
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
		expectedResult.setOutputVar2("111114|111119|111121");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID06_111115_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewA");
		
		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111115");
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
		expectedResult.setOutputVar2("111116|111118");
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
		expectedResult.setOutputVar2("111116|111118");
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
		expectedResult.setOutputVar2("111116|111119|111120|111122");
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
		expectedResult.setOutputVar2("111116|111119|111120|111122");
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
		expectedResult.setOutputVar2("111116|111119|111120|111123|111124");
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
		expectedResult.setOutputVar2("111116|111119|111120|111123|111124");
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
		expectedResult.setOutputVar2("111116|111119|111120|111123|111125");
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
		expectedResult.setOutputVar2("111116|111119|111120|111123|111125");
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
		expectedResult.setOutputVar2("111116|111119|111121");
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
		expectedResult.setOutputVar2("111116|111119|111121");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID300_A_ID12_111117_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CreditLimitOverviewB");
		
		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111117");
		expectedResult.setNextState("ExitPointB");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID310a_ExitPointA() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "ExitPointA");
		
		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("SpeakFaxInfoPrompt");

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
		
	public final void testID500_SpeakFaxInfoPrompt() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakFaxInfoPrompt");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:EntsperrungBeschleunigenFaxNrWireless");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic111220");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID510_111220_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic111220");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111220");
		expectedResult.setNextState("RepeatFaxNumberOrFurtherQuestion");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID520_RepeatFaxNumberOrFurtherQuestion() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatFaxNumberOrFurtherQuestion");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FaxNrWiederholenTaste1RechnungTaste2AndereFragenTaste3");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputFromRepeatFaxNumberOrFurtherQuestion");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID530a_111221_CheckInputFromRepeatFaxNumberOrFurtherQuestion_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111221");
		expectedResult.setNextState("SpeakFaxNumberOnly");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID530b_111222_CheckInputFromRepeatFaxNumberOrFurtherQuestion_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111222");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID530c_111411_CheckInputFromRepeatFaxNumberOrFurtherQuestion_Input_3() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");
		
		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111411");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID530d_111223_CheckInputFromRepeatFaxNumberOrFurtherQuestion_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111223");
		expectedResult.setNextState("SpeakPleaseSelectFromTheFollowingOptions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID530e_111223_CheckInputFromRepeatFaxNumberOrFurtherQuestion_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111223");
		expectedResult.setNextState("SpeakPleaseSelectFromTheFollowingOptions");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID535_SpeakPleaseSelectFromTheFollowingOptions() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakPleaseSelectFromTheFollowingOptions");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:BitteOptionWaehlen");
		expectedResult.setOutputVar2("true");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("RepeatFaxNumberAgainOrFurtherQuestion");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID540_SpeakFaxNumberOnly() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakFaxNumberOnly");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("portal:FaxNrLautetWireless");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("false");
		expectedResult.setNextState("AddStatistic111225");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID550_111225_AddStatistic() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "AddStatistic111225");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111225");
		expectedResult.setNextState("RepeatFaxNumberAgainOrFurtherQuestion");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID560_RepeatFaxNumberAgainOrFurtherQuestion() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "RepeatFaxNumberAgainOrFurtherQuestion");

		expectedResult.setAction(SE_ACTION_INPUT);
		expectedResult.setOutputVar1("FaxNrWiederholenTaste1RechnungTaste2AndereFragenTaste3 KeineWeiterenInformationenAuflegen");
		expectedResult.setOutputVar2("5s");
		expectedResult.setOutputVar3("3");
		expectedResult.setNextState("CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID570a_111224_CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111224");
		expectedResult.setNextState("SpeakFaxNumberOnly");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID570b_111226_CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion_Input_2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111226");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID570c_111412_CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"3");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111412");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID570d_111226_CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111226");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID570e_111226_CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFromRepeatFaxNumberAgainOrFurtherQuestion");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111226");
		expectedResult.setNextState("RoutingToDunningBlocked");

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

	public final void testID710a_111227_CheckResultFurtherQuestions_Input_1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"1");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111227");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710b_111415_CheckResultFurtherQuestions_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111415");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID710c_111228_CheckResultFurtherQuestions_NoInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOINPUT");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111228");
		expectedResult.setNextState("RepeatFurtherQuestionsExtraPrompt");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID710d_111228_CheckResultFurtherQuestions_NoMatch() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestions");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"NOMATCH");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111228");
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

	public final void testID730a_111416_CheckResultFurtherQuestionsAfterNoInput_Input_2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");
		callProfile.put(CPK_CARS_ACTIONRESPONSE,"SUCCESS");
		callProfile.put(CPK_CARS_ACTIONRESPONSEVALUE,"2");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111416");
		expectedResult.setNextState("Back2Portal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID730b_111229_CheckResultFurtherQuestionsAfterNoInput_AnyInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckInputFurtherQuestionsAfterNoInput");

		expectedResult.setAction(SE_ACTION_ADDSTATISTIC);
		expectedResult.setOutputVar1("111229");
		expectedResult.setNextState("RoutingToDunningBlocked");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID820_RoutingToDunningBlocked() throws Exception {
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
	
	public final void testID830_Back2Portal() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Back2Portal");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}	
}

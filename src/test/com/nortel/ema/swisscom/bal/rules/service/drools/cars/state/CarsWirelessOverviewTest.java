/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: CarsWirelessOverviewTest.java 156 2013-12-10 08:00:59Z tolvoph1 $
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
public class CarsWirelessOverviewTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	// Test Constants
	private static final String FALSE = "false";
	private static final String TRUE = "true";

	// Variables used by all testcases
	private static final String RULES_FILE_NAME = "cars/wireless-overview";
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
		junit.textui.TestRunner.run(CarsWirelessOverviewTest.class);
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
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		// All cases that are not defined otherwise are for nextState = "RunOverview"
		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile = new CustomerProfile();
		customerProfile.setBillAccntID("something");
		customerProfile.setOuTypeCode("Person");
		customerProfile.setSegment("");
		customerProfile.setBillingProfileCreditLimit("0");
		customerProfile.setAge("16");
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
		expectedResult.setNextState("DONE");
		expectedResult.setState(StateEngineRulesResult.DONE);
	}

	public final void testID0MASTER_Inactive() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		setTestData("E");

		serviceConfigurationMap.put("cars.wless.active", FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100100");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0MASTER_Active() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		setTestData("E");

		serviceConfigurationMap.put("cars.wless.active", TRUE);

		expectedResult.setAction(SE_ACTION_GETSTATUS);
		expectedResult.setNextState("RunOverview");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100_Back2Portal() throws Exception {
		setTestData("E");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100102");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID101_Back2Portal() throws Exception {
		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100107|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID102_Workflow8_active() throws Exception {
		setTestData("L,J,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100107|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID102_Workflow8_inactive() throws Exception {
		setTestData("L,J,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100107|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID103_Transfer2CCC() throws Exception {
		setTestData("AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100107|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID104_Back2Portal() throws Exception {
		setTestData("G,H");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100145|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID107_Back2Portal() throws Exception {
		setTestData("G,H,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100145|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID111_Workflow4_active() throws Exception {
		setTestData("G,H,L,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID111_Workflow4_inactive() throws Exception {
		setTestData("G,H,L,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID112_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID113_Back2Portal() throws Exception {
		setTestData("G,H,X");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100147|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID116_Back2Portal() throws Exception {
		setTestData("G,H,X,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100147|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID119_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120_Workflow4_active() throws Exception {
		setTestData("G,H,L,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120_Workflow4_inactive() throws Exception {
		setTestData("G,H,L,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID121_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID122_Workflow2_active() throws Exception {
		setTestData("G,H,L,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID122_Workflow2_inactive() throws Exception {
		setTestData("G,H,L,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID123_Transfer2DunningLetterSMS() throws Exception {
		setTestData("G,H,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100141|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID124_Back2Portal() throws Exception {
		setTestData("G,H,V");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100145|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID127_Back2Portal() throws Exception {
		setTestData("G,H,V,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100145|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,V,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID131_Workflow4_active() throws Exception {
		setTestData("G,H,L,V,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID131_Workflow4_inactive() throws Exception {
		setTestData("G,H,L,V,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID132_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,V,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID133_Back2Portal() throws Exception {
		setTestData("G,H,V,X");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100147|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID136_Back2Portal() throws Exception {
		setTestData("G,H,V,X,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100147|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID139_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,V,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_Workflow4_active() throws Exception {
		setTestData("G,H,L,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140_Workflow4_inactive() throws Exception {
		setTestData("G,H,L,V,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID141_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID142_Workflow2_active() throws Exception {
		setTestData("G,H,L,V,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID142_Workflow2_inactive() throws Exception {
		setTestData("G,H,L,V,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID143_Transfer2DunningLetterSMS() throws Exception {
		setTestData("G,H,V,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100143|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID144_Workflow2_active() throws Exception {
		setTestData("G,H,L,V,W");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID144_Workflow2_inactive() throws Exception {
		setTestData("G,H,L,V,W");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID145_Transfer2DunningLetterSMS() throws Exception {
		setTestData("G,H,V,W");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100113|100140|100142|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID147_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID147_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID148_Transfer2CCC() throws Exception {
		setTestData("G,H,J,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID151_Transfer2CCC() throws Exception {
		setTestData("G,H,J,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID152_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID153_Workflow4_active() throws Exception {
		setTestData("G,H,J,L,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID153_Workflow4_inactive() throws Exception {
		setTestData("G,H,J,L,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID154_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID156_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,X,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID156_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,X,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID157_Transfer2CCC() throws Exception {
		setTestData("G,H,J,X,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID159_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,X,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID159_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,X,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID160_Transfer2CCC() throws Exception {
		setTestData("G,H,J,X,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID161_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID162_Workflow4_active() throws Exception {
		setTestData("G,H,J,L,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID162_Workflow4_inactive() throws Exception {
		setTestData("G,H,J,L,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID163_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID164_Workflow2_active() throws Exception {
		setTestData("G,H,J,L,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID164_Workflow2_inactive() throws Exception {
		setTestData("G,H,J,L,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID165_Transfer2DunningLetterSMS() throws Exception {
		setTestData("G,H,J,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100141|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID167_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,V,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID167_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,V,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID168_Transfer2CCC() throws Exception {
		setTestData("G,H,J,V,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,V,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID170_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,V,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID171_Transfer2CCC() throws Exception {
		setTestData("G,H,J,V,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID172_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,V,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID173_Workflow4_active() throws Exception {
		setTestData("G,H,J,L,V,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID173_Workflow4_inactive() throws Exception {
		setTestData("G,H,J,L,V,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID174_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,V,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID176_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,V,X,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID176_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,V,X,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID177_Transfer2CCC() throws Exception {
		setTestData("G,H,J,V,X,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID179_Workflow8_active() throws Exception {
		setTestData("G,H,J,L,V,X,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID179_Workflow8_inactive() throws Exception {
		setTestData("G,H,J,L,V,X,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID180_Transfer2CCC() throws Exception {
		setTestData("G,H,J,V,X,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID181_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,V,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID182_Workflow4_active() throws Exception {
		setTestData("G,H,J,L,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID182_Workflow4_inactive() throws Exception {
		setTestData("G,H,J,L,V,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID183_Transfer2BarringPrevention() throws Exception {
		setTestData("G,H,J,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID184_Workflow2_active() throws Exception {
		setTestData("G,H,J,L,V,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID184_Workflow2_inactive() throws Exception {
		setTestData("G,H,J,L,V,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID185_Transfer2DunningLetterSMS() throws Exception {
		setTestData("G,H,J,V,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100143|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID186_Workflow2_active() throws Exception {
		setTestData("G,H,J,L,V,W");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID186_Workflow2_inactive() throws Exception {
		setTestData("G,H,J,L,V,W");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID187_Transfer2DunningLetterSMS() throws Exception {
		setTestData("G,H,J,V,W");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100115|100140|100142|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID188_Transfer2DunningBlocked_Step1_DisableTransferPrompt() throws Exception {
		setTestData("G,H,J,K");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("Transfer2DunningBlockedAfterTransferPromptSwitch188");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID188_Transfer2DunningBlocked_Step2_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Transfer2DunningBlockedAfterTransferPromptSwitch188");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CREDITLIMIT_BLOCKED);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100117");
		expectedResult.addToOutputColl2("prompt", "GesperrtesNatelInfo0800Wireless");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID189_Workflow5_active() throws Exception {
		setTestData("G,H,J,K,L");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF5);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100121");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID189_Workflow5_inactive() throws Exception {
		setTestData("G,H,J,K,L");

		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100121");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190_Workflow6_active() throws Exception {
		setTestData("G,H,J,K,L,N");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF6);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100120|100123|100125");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190_Workflow6_inactive() throws Exception {
		setTestData("G,H,J,K,L,N");

		serviceConfigurationMap.put("cars.wless.active.callflow.6",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100120|100123|100125");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID191_Workflow5_active() throws Exception {
		setTestData("G,H,J,K,L,N,P");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF5);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100120|100123|100124");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID191_Workflow5_inactive() throws Exception {
		setTestData("G,H,J,K,L,N,P");

		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100120|100123|100124");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID192_Workflow5_active() throws Exception {
		setTestData("G,H,J,K,L,N,O");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF5);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100120|100122");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID192_Workflow5_inactive() throws Exception {
		setTestData("G,H,J,K,L,N,O");

		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100119|100120|100122");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID193_Workflow7_active() throws Exception {
		setTestData("G,H,J,K,L,M");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF7);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100118");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID193_Workflow7_inactive() throws Exception {
		setTestData("G,H,J,K,L,M");

		serviceConfigurationMap.put("cars.wless.active.callflow.7",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100105|100112|100114|100116|100118");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID194_Back2Portal() throws Exception {
		setTestData("G");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100109|100111");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID195_Transfer2DunningBlocked() throws Exception {
		setTestData("G,I");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("100101|100103|100106|100109|100110");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID196_Back2Portal() throws Exception {
		setTestData("F,G,H");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100145|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID199_Back2Portal() throws Exception {
		setTestData("F,G,H,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100145|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID202_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID203_Workflow4_active() throws Exception {
		setTestData("F,G,H,L,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID203_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,L,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID204_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID205_Back2Portal() throws Exception {
		setTestData("F,G,H,X");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100147|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID208_Back2Portal() throws Exception {
		setTestData("F,G,H,X,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100147|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID211_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID212_Workflow4_active() throws Exception {
		setTestData("F,G,H,L,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID212_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,L,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID213_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID214_Workflow2_active() throws Exception {
		setTestData("F,G,H,L,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID214_Workflow2_inactive() throws Exception {
		setTestData("F,G,H,L,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID215_Transfer2DunningLetterSMS() throws Exception {
		setTestData("F,G,H,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100141|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID216_Back2Portal() throws Exception {
		setTestData("F,G,H,V");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100145|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID219_Back2Portal() throws Exception {
		setTestData("F,G,H,V,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100145|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID222_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,V,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID223_Workflow4_active() throws Exception {
		setTestData("F,G,H,L,V,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID223_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,L,V,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID224_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,V,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID225_Back2Portal() throws Exception {
		setTestData("F,G,H,V,X");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100147|100151|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID228_Back2Portal() throws Exception {
		setTestData("F,G,H,V,X,Z");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100147|100150|100153|100159");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID231_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,V,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID232_Workflow4_active() throws Exception {
		setTestData("F,G,H,L,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID232_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,L,V,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID233_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID234_Workflow2_active() throws Exception {
		setTestData("F,G,H,L,V,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID234_Workflow2_inactive() throws Exception {
		setTestData("F,G,H,L,V,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID235_Transfer2DunningLetterSMS() throws Exception {
		setTestData("F,G,H,V,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100143|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID236_Workflow2_active() throws Exception {
		setTestData("F,G,H,L,V,W");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID236_Workflow2_inactive() throws Exception {
		setTestData("F,G,H,L,V,W");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID237_Transfer2DunningLetterSMS() throws Exception {
		setTestData("F,G,H,V,W");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100113|100140|100142|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID239_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID239_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID240_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID242_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID242_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID243_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID244_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID245_Workflow4_active() throws Exception {
		setTestData("F,G,H,J,L,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID245_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,J,L,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID246_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID248_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,X,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID248_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,X,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID249_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,X,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID251_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,X,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID251_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,X,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID252_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,X,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID253_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID254_Workflow4_active() throws Exception {
		setTestData("F,G,H,J,L,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID254_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,J,L,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID255_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID256_Workflow2_active() throws Exception {
		setTestData("F,G,H,J,L,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID256_Workflow2_inactive() throws Exception {
		setTestData("F,G,H,J,L,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID257_Transfer2TransferDunningLetterSMS() throws Exception {
		setTestData("F,G,H,J,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100141|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID259_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,V,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID259_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID260_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,V,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID262_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,V,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID262_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID263_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,V,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID264_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,V,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID265_Workflow4_active() throws Exception {
		setTestData("F,G,H,J,L,V,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID265_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID266_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,V,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100145|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID268_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,V,X,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID268_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,X,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100151|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID269_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,V,X,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100151|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID271_Workflow8_active() throws Exception {
		setTestData("F,G,H,J,L,V,X,Z,AC");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF8);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID271_Workflow8_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,X,Z,AC");

		serviceConfigurationMap.put("cars.wless.active.callflow.8",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100150|100153|100158|100160");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID272_Transfer2CCC() throws Exception {
		setTestData("F,G,H,J,V,X,Z,AC");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CCC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100150|100153|100158|100161");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID273_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,V,X,Z,AA,AB");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100150|100152|100154");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID274_Workflow4_active() throws Exception {
		setTestData("F,G,H,J,L,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF4);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID274_Workflow4_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,X,Z,AA");

		serviceConfigurationMap.put("cars.wless.active.callflow.4",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100150|100152|100155|100156");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID275_Transfer2BarringPrevention() throws Exception {
		setTestData("F,G,H,J,V,X,Z,AA");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_BARRING_PREVENTION);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100147|100150|100152|100155|100157");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID276_Workflow2_active() throws Exception {
		setTestData("F,G,H,J,L,V,X,Y");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID276_Workflow2_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,X,Y");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100146|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID277_Transfer2DunningLetterSMS() throws Exception {
		setTestData("F,G,H,J,V,X,Y");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100143|100144|100146|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID278_Workflow2_active() throws Exception {
		setTestData("F,G,H,J,L,V,W");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF2);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID278_Workflow2_inactive() throws Exception {
		setTestData("F,G,H,J,L,V,W");

		serviceConfigurationMap.put("cars.wless.active.callflow.2",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100142|100148");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID279_Transfer2DunningLetterSMS() throws Exception {
		setTestData("F,G,H,J,V,W");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100115|100140|100142|100149");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID280_Transfer2DunningBlocked_Step1_DisableTransferPrompt() throws Exception {
		setTestData("F,G,H,J,K");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("Transfer2DunningBlockedAfterTransferPromptSwitch280");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID280_Transfer2DunningBlocked_Step2_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Transfer2DunningBlockedAfterTransferPromptSwitch280");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_CREDITLIMIT_BLOCKED);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100117");
		expectedResult.addToOutputColl2("prompt", "GesperrtesNatelInfo0800Wireless");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID281_Workflow5_active() throws Exception {
		setTestData("F,G,H,J,K,L");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF5);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100121");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID281_Workflow5_inactive() throws Exception {
		setTestData("F,G,H,J,K,L");

		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100121");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID282_Workflow6_active() throws Exception {
		setTestData("F,G,H,J,K,L,N");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF6);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100120|100123|100125");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID282_Workflow6_inactive() throws Exception {
		setTestData("F,G,H,J,K,L,N");

		serviceConfigurationMap.put("cars.wless.active.callflow.6",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100120|100123|100125");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID283_Workflow5_active() throws Exception {
		setTestData("F,G,H,J,K,L,N,P");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF5);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100120|100123|100124");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID283_Workflow5_inactive() throws Exception {
		setTestData("F,G,H,J,K,L,N,P");

		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100120|100123|100124");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID284_Workflow5_active() throws Exception {
		setTestData("F,G,H,J,K,L,N,O");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF5);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100120|100122");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID284_Workflow5_inactive() throws Exception {
		setTestData("F,G,H,J,K,L,N,O");

		serviceConfigurationMap.put("cars.wless.active.callflow.5",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100119|100120|100122");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID285_Workflow7_active() throws Exception {
		setTestData("F,G,H,J,K,L,M");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF7);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100118");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID285_Workflow7_inactive() throws Exception {
		setTestData("F,G,H,J,K,L,M");

		serviceConfigurationMap.put("cars.wless.active.callflow.7",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100127|100112|100114|100116|100118");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID286_Transfer2DunningBlocked() throws Exception {
		setTestData("F,G,H,I");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100129");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID287_Transfer2DunningBlocked_Step1_DisableTransferPrompt() throws Exception {
		setTestData("F,G,H,I,Q");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("Transfer2DunningBlockedAfterTransferPromptSwitch287");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID287_Transfer2DunningBlocked_Step2_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Transfer2DunningBlockedAfterTransferPromptSwitch287");
		setTestData("F,G,H,I,Q");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100131");
		expectedResult.addToOutputColl2("prompt", "GesperrtesNatelInfo0800Wireless");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID288_Transfer2DunningBlocked() throws Exception {
		setTestData("F,G,H,I,L,Q");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100133|100135|100137");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID289_Workflow1NS_active() throws Exception {
		setTestData("F,G,H,I,L,Q,T");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF1NS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100133|100135|100136");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID289_Workflow1NS_inactive() throws Exception {
		setTestData("F,G,H,I,L,Q,T");

		serviceConfigurationMap.put("cars.wless.active.callflow.1ns",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100133|100135|100136");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID290_Workflow1SC_active() throws Exception {
		setTestData("F,G,H,I,L,Q,S");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF1SC);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100133|100134|100139");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID290_Workflow1SC_inactive() throws Exception {
		setTestData("F,G,H,I,L,Q,S");

		serviceConfigurationMap.put("cars.wless.active.callflow.1sc",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100133|100134|100139");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID291_Workflow1NS_active() throws Exception {
		setTestData("F,G,H,I,L,Q,S,U");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF1NS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100133|100134|100138");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID291_Workflow1NS_inactive() throws Exception {
		setTestData("F,G,H,I,L,Q,S,U");

		serviceConfigurationMap.put("cars.wless.active.callflow.1ns",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100133|100134|100138");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID292_Workflow1NS_active() throws Exception {
		setTestData("F,G,H,I,L,Q,R");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELESS_WF1NS);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100132");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID292_Workflow1NS_inactive() throws Exception {
		setTestData("F,G,H,I,L,Q,R");

		serviceConfigurationMap.put("cars.wless.active.callflow.1ns",FALSE);

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("100101|100103|100106|100108|100104|100126|100128|100130|100132");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}
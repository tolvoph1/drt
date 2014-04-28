/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CarsWirelineOverviewTest.java 213 2014-03-17 08:08:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-17 09:08:23 +0100 (Mon, 17 Mar 2014) $
 * $Revision: 213 $
 * 
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
public class CarsWirelineOverviewTest extends TestCase {


	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "cars/wireline-overview";
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
		junit.textui.TestRunner.run(CarsWirelineOverviewTest.class);
	}

	protected void setUp() {
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		callProfile.put(CPK_BUSINESSTYP, RES);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		serviceConfigurationMap.put("cars.wline.active","true");
		serviceConfigurationMap.put("cars.wline.active.callflow.1ns","true");
		serviceConfigurationMap.put("cars.wline.active.callflow.1sc","true");
		serviceConfigurationMap.put("cars.wline.active.callflow.2","true");
		serviceConfigurationMap.put("cars.wline.active.callflow.4","true");
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);

	}

	public final void testID000a_MASTER_Wireline_Inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");

		customerProfile.setCarsStatus("200100");

		serviceConfigurationMap.put("cars.wline.active", "false");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200100");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID000b_MASTER_Wireline_Active_EmptyCarsStatus() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_GETSTATUS);
		expectedResult.setNextState("RunOverview");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID000c_MASTER_Wireline_Active_HaveCarsStatus() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Init");

		customerProfile.setCarsStatus("block");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("RunOverview");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID001_Back2Portal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile.setCarsStatus("interactionHistoryAvailable");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200105");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID002_Back2Portal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile.setCarsStatus("interactionHistoryAvailable,dunningProcedure");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200107");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID003_Back2Portal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200109|200111");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID004_Transfer2DunningBlocked() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("block");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("200101|200109|200110");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID005_Back2Portal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200127|200141");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID006_Back2Portal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,dunningLetter");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200127|200140|200143");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID007a_WF2_active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,dunningLetter,recentDunningLetter");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELINE_WF2);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200127|200140|200142|200148");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID007b_WF2_inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		serviceConfigurationMap.put("cars.wline.active.callflow.2","false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,dunningLetter,recentDunningLetter");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200127|200140|200142|200148");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID008_Transfer2DunningLetterSMS() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,dunningLetter,recentDunningLetter");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_LETTER_SMS);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200127|200140|200142|200149");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID009_Transfer2DunningBlocked() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200129");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010_Transfer2DunningBlocked_Step1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock");

		expectedResult.setAction(SE_ACTION_TRANSFERPROMPTSWITCH);
		expectedResult.setOutputVar1("false");
		expectedResult.setNextState("Transfer2DunningBlockedAfterTransferPromptSwitch");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID010_Transfer2DunningBlocked_Step2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Transfer2DunningBlockedAfterTransferPromptSwitch");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200131");
		expectedResult.addToOutputColl2("prompt", "GesperrtesNatelInfo0800Wireline");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID011a_WF1NS_active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,quickProcedure");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELINE_WF1NS);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200132");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID011b_WF1NS_inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		serviceConfigurationMap.put("cars.wline.active.callflow.1ns","false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,quickProcedure");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200132");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID012a_WF1NS_active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,dunningLevelMajor");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELINE_WF1NS);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200133|200135|200136");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID012b_WF1NS_inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		serviceConfigurationMap.put("cars.wline.active.callflow.1ns","false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,dunningLevelMajor");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200133|200135|200136");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID013_Transfer2DunningBlocked() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1(CARS_TRANSFER_DUNNING_BLOCKED);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200133|200135|200137");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID014a_WF1NS_active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,dunningLevelMinor,recentPaymentCheck");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELINE_WF1NS);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200133|200134|200138");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID014b_WF1NS_inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		serviceConfigurationMap.put("cars.wline.active.callflow.1ns","false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,dunningLevelMinor,recentPaymentCheck");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200133|200134|200138");
		expectedResult.setNextState("DONE");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID01ba_WF1SC_active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,dunningLevelMinor");

		expectedResult.setAction(SE_ACTION_RUNCALLFLOW);
		expectedResult.setOutputVar1(CARS_WIRELINE_WF1SC);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200133|200134|200139");
		expectedResult.setNextState("DONE");		

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID015b_WF1SC_inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RunOverview");
		callProfile.put(CallProfile.IDENTIFIED_BY_ANI,"true");

		serviceConfigurationMap.put("cars.wline.active.callflow.1sc","false");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setCarsStatus("dunningProcedure,interactionHistoryAvailable,block,recentBlock,dunningLevelMinor");

		expectedResult.setAction(SE_ACTION_BACK2PORTAL);
		expectedResult.setOutputVar2("200101|200108|200104|200106|200126|200128|200130|200133|200134|200139");
		expectedResult.setNextState("DONE");	

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}

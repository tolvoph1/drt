/*
 * (c)2013 Swissom (Schweiz) AG.
 *
 * $Id: RESRulesStateWirelineTest.java 213 2014-03-17 08:08:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-17 09:08:23 +0100 (Mon, 17 Mar 2014) $
 * $Revision: 213 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.rules.model.StateEngineRulesResult;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesStateWirelineTest extends TestCase {

	private static final String RULES_FILE_NAME = "oneIVR/vp5res-wireline-state";
	private static RulesServiceDroolsImpl droolsImpl;
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
		junit.textui.TestRunner.run(RESRulesStateWirelineTest.class);
	}

	protected void setUp() {
		// Create objects
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		callProfile.put(CPK_BUSINESSTYP, RES);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		serviceConfigurationMap.put("vp.res.outgrower.age", "26");
		serviceConfigurationMap.put("vp.res.outgrower.months","2");
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
	}

	public final void testID0010_InitWirelinePers() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "InitWirelinePers");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckVIPPlatin");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0020a_CheckVIPPlatin_VIP() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckVIPPlatin");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setFineSegment(FINESEGMENT_PP);
		customerProfile.setSubSegment(SUBSEGMENT_VIP);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VIPRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0020b_CheckVIPPlatin_Platin() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckVIPPlatin");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setFineSegment(FINESEGMENT_PP);
		customerProfile.setSubSegment(SUBSEGMENT_PLATIN);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHRESGlobal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0020c_CheckVIPPlatin_NonVIP() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckVIPPlatin");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("INASMAIN");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0030_VIPRouting_CheckOH() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VIPRouting");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setFineSegment(FINESEGMENT_PP);
		customerProfile.setSubSegment(SUBSEGMENT_VIP);

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_PERS_RES_PREMIUM_VIP");
		expectedResult.setNextState("CheckOHVIPResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);
		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0040a_CheckOHVIPResult_Active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHVIPResult");
		callProfile.put(CPK_RESULTOH, "tag");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0040b_CheckOHVIPResult_Inactive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHVIPResult");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("INASMAIN");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0050_INAS_MAIN() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "INASMAIN");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("CheckOHNewProductMenu");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0060_CheckOHNewProductMenu() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHNewProductMenu");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_NewProductMenu");
		expectedResult.setNextState("NewProductMenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0070a_NewProductMenuLevel1_Closed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "NewProductMenuLevel1");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0070b_NewProductMenuLevel1_Open_Platin() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "NewProductMenuLevel1");
		callProfile.put(CPK_RESULTOH, "open");

		customerProfile.setSegment(SEGMENT_RES);
		customerProfile.setFineSegment(FINESEGMENT_PP);
		customerProfile.setSubSegment(SUBSEGMENT_PLATIN);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0070c_NewProductMenuLevel1_Open_NotPlatin() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "NewProductMenuLevel1");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresNPAlmMenuFixnet");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("oneIVR/vp5resNPAlmMenuFixnet");
		expectedResult.addToOutputColl1("select", "npalm-menue-please-select");
		expectedResult.setNextState("CheckNewProdMenuLevel1Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0080a_NewProductMenuLevel1Response_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0080b_NewProductMenuLevel1Response_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0080c_NewProductMenuLevel1Response_Continue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0080d_NewProductMenuLevel1Response_SubMenu() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "MENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("NewProductMenuLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}	

	public final void testID0090_NewProductMenu_Level2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "NewProductMenuLevel2");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresNPPrmMenu");
		expectedResult.setOutputVar2("false");
		expectedResult.setOutputVar3("oneIVR/vp5resNPPrmMenu");
		expectedResult.addToOutputColl1("select", "npprm-menue-please-select");
		expectedResult.setNextState("CheckNewProdMenuLevel2Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100a_NewProductMenuLevel2Response_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100b_NewProductMenuLevel2Response_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100c_NewProductMenuLevel2Response_Continue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0110a_VectoringEntry_State01() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_61_INPROGRESS);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_01");
		expectedResult.setNextState("LogVectoring01");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0110b_VectoringEntry_State02() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_SCHEDULED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_02");
		expectedResult.setNextState("LogVectoring02");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0110c_VectoringEntry_State03() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_INPROGRESS);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_03");
		expectedResult.setNextState("LogVectoring03");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0110d_VectoringEntry_State04() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_CLOSED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_04");
		expectedResult.setNextState("LogVectoring04");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0110e_VectoringEntry_State05() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_CLOSED_ALARM);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_05");
		expectedResult.setNextState("LogVectoring05");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0110f_VectoringEntry_State06() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_RESCHEDULED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_06");
		expectedResult.setNextState("LogVectoring06");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0110g_VectoringEntry_State07() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_DELAYED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_07");
		expectedResult.setNextState("LogVectoring07");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0110h_VectoringEntry_State08() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_ABORTED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_08");
		expectedResult.setNextState("LogVectoring08");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0110i_VectoringEntry_Default() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0120a_LogVectoring01() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring01");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '61-InProgress', 'Vectoring_01', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0120b_LogVectoring02() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring02");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Scheduled', 'Vectoring_02', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0120c_LogVectoring03() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring03");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-InProgress', 'Vectoring_03', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0120d_LogVectoring04() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring04");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Closed', 'Vectoring_04', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0120e_LogVectoring05() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring05");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Closed-Alarm', 'Vectoring_05', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0120f_LogVectoring06() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring06");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Rescheduled', 'Vectoring_06', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0120g_LogVectoring07() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring07");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Delayed', 'Vectoring_07', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0120h_LogVectoring08() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring08");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Aborted', 'Vectoring_08', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0130_ReturnFromVectoring() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "ReturnFromVectoring");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GetCARSStatus");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID0135_GetCARSStatus() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GetCARSStatus");

		expectedResult.setAction(SE_ACTION_CARSSTATUS);
		expectedResult.setNextState("RecallerCampaign");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0140a_RecallerCampaign_Active() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RecallerCampaign");

		serviceConfigurationMap.put("vp.res.recaller.active", "true");
		callProfile.put(CPK_SYSORSSESSIONID, "1234");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("RECALLER");
		expectedResult.setNextState("CheckForRecaller");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0140b_RecallerCampaign_InActive() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RecallerCampaign");
		
		serviceConfigurationMap.put("vp.res.recaller.active", "false");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0140c_RecallerCampaign_Kassensperre() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RecallerCampaign");
		
		serviceConfigurationMap.put("vp.res.recaller.active", "true");
		callProfile.put(CPK_SYSORSSESSIONID, "1234");
		customerProfile.setCarsStatus("block");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0150a_CheckForRecaller_skipRecaller() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckForRecaller");
		callProfile.put(CPK_GENERICINASACTION, "skipRecaller");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0150b_CheckForRecaller() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckForRecaller");

		expectedResult.setAction(SE_ACTION_CHECKFORRECALLER);
		expectedResult.setNextState("CheckResultCheckForRecaller");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0160a_CheckResultCheckForRecaller_NoRecaller() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckResultCheckForRecaller");
		callProfile.put(CPK_RECALLERSTATUS, FALSE);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0160b_CheckResultCheckForRecaller_Recaller_HaveRoutingIDandRuleID() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckResultCheckForRecaller");
		callProfile.put(CPK_RECALLERSTATUS, TRUE);
		callProfile.put(CPK_RECALLERROUTINGID, "0815");
		callProfile.put(CPK_RECALLERRULEID,"49999");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("RecallerConfirm");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0160c_CheckResultCheckForRecaller_Recaller_RoutingIDandorRuleID_missing() throws Exception {

		// First test RoutingID missing
		callProfile.put(CPK_NEXTSTATE, "CheckResultCheckForRecaller");
		callProfile.put(CPK_RECALLERSTATUS, TRUE);
		callProfile.put(CPK_RECALLERRULEID,"49999");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);

		// Second test RuleID missing
		// Create new callProfile to avoid already filled keys from first test
		callProfile = new CallProfile();
		callProfile.put(CPK_NEXTSTATE, "CheckResultCheckForRecaller");
		callProfile.put(CPK_RECALLERSTATUS, TRUE);
		callProfile.put(CPK_RECALLERROUTINGID,"0815");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0170_RecallerConfirm() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "RecallerConfirm");

		expectedResult.setAction(SE_ACTION_CONFIRM);
		expectedResult.setOutputVar1("SR");
		expectedResult.setOutputVar2("recallerconfirm");
		expectedResult.addToOutputColl1("nomatch1", "no");
		expectedResult.addToOutputColl1("noinput1", "no");
		expectedResult.setNextState("CheckResultRecallerConfirm");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0180a_CheckResultRecallerConfirm_yes() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckResultRecallerConfirm");
		callProfile.put("recallerconfirm","yes");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("TransferRecaller");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0180b_CheckResultRecallerConfirm_no() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckResultRecallerConfirm");
		callProfile.put("recallerconfirm","no");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0200_TransferRecaller() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "TransferRecaller");
		callProfile.put(CPK_RECALLERROUTINGID, "0815");
		callProfile.put(CPK_RECALLERRULEID,"49999");

		expectedResult.setAction(SE_ACTION_TRANSFERRID);
		expectedResult.setOutputVar1(callProfile.getRecallerRoutingID());
		expectedResult.setOutputVar2(callProfile.getRecallerRuleID());
		expectedResult.setOutputVar3("true");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0210_CheckOHCBR() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHCBR");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_CBR");
		expectedResult.setNextState("CheckOHCBRResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0220a_CheckOHCBRResult_Open() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHCBRResult");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_CBR);
		expectedResult.setNextState("CheckCBRActionResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0220b_CheckOHCBRResult_Closed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHCBRResult");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CARS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0230a_CheckCBRActionResponse_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCBRActionResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0230b_CheckCBRActionResponse_Continue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCBRActionResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CARS");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0240a_CARS_ValidCustomer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CARS");

		customerProfile.setCustomerID("12345678");
		customerProfile.setBillingProfileId("BSC:BAC:1234");

		expectedResult.setAction(SE_ACTION_CARS);
		expectedResult.setOutputVar1("cars/wireline-overview");
		expectedResult.setNextState("CheckCARSResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0240b_CARS_InvalidCustomer_noCustomerID() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CARS");

		customerProfile.setCustomerID("");
		customerProfile.setBillingProfileId("BSC:BAC:1234");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_CAMPAIGN");
		expectedResult.setNextState("CheckOHCampaignResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);

		customerProfile.setCustomerID("12345678");
		customerProfile.setBillingProfileId("02");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}


	public final void testID0250a_CheckCARSResponse_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCARSResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0250b_CheckCARSResponse_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCARSResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0250c_CheckCARSResponse_Continue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckCARSResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_CAMPAIGN");
		expectedResult.setNextState("CheckOHCampaignResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0260a_CheckOHCampaignResult_Open() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHCampaignResult");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("PERSONALISATION");
		expectedResult.setNextState("AgeBasedRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0260b_CheckOHCampaignResult_Closed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHCampaignResult");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("AgeBasedRouting");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0270a_AgeBasedRouting_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AgeBasedRouting");

		customerProfile.setAge("100");
		serviceConfigurationMap.put("vp.res.caller.MaxAge", "75");
		serviceConfigurationMap.put("vp.res.caller.BestAgeStart", "60");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0270b_AgeBasedRouting_Continue() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "AgeBasedRouting");

		customerProfile.setAge("40");
		serviceConfigurationMap.put("vp.res.caller.MaxAge", "75");
		serviceConfigurationMap.put("vp.res.caller.BestAge", "60");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHRESGlobal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);

		customerProfile.setAge("100");
		customerProfile.setSubSegment(SUBSEGMENT_PLATIN);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);

		serviceConfigurationMap.put("vp.res.caller.MaxAge", "75");
		serviceConfigurationMap.put("vp.res.caller.BestAgeStart", "65");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHRESGlobal");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0300_CheckOHRESGlobal() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHRESGlobal");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_Global");
		expectedResult.setNextState("CheckOHRESGlobalResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	
	public final void testID0310a_CheckOHRESGlobalResult_Open() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHRESGlobalResult");
		callProfile.put(CPK_RESULTOH, "100");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0310b_CheckOHRESGlobalResult_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHRESGlobalResult");
		callProfile.put(CPK_RESULTOH, "102");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0310c_CheckOHRESGlobalResult_Night200() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHRESGlobalResult");
		callProfile.put(CPK_RESULTOH, "200");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("nm-2200");
		expectedResult.setNextState("NightMenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0310d_CheckOHRESGlobalResult_Closed() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckOHRESGlobalResult");
		callProfile.put(CPK_RESULTOH, "1");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("closed-bye");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0320_MenuLevel1_AnliegenMenu() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "MenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresAnliegenMenu");
		expectedResult.setOutputVar3("oneIVR/vp5resAnliegenMenuFixnet");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckMenuLevel1Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0330a_MenuLevel1Response_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0330b_MenuLevel1Response_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0330c_MenuLevel1Response_SubMenu() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "MENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MenuLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0340_MenuLevel2_ProduktMenu() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "MenuLevel2");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresProduktMenu");
		expectedResult.setOutputVar3("oneIVR/vp5resProduktMenuFixnet");
		expectedResult.addToOutputColl1("select", "pm-menue-please-select");
		expectedResult.setNextState("CheckMenuLevel2Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0350a_MenuLevel2Response_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0370_NightMenu_Level1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "NightMenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresAnliegenMenu");
		expectedResult.setOutputVar3("oneIVR/vp5resAnliegenMenuFixnetNacht");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckNightMenuLevel1Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0380a_NightMenuLevel1Response_SubMenu() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "MENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("NightMenuLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0380b_NightMenuLevel1Response_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0390_NightMenu_Level2() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "NightMenuLevel2");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("oneIVRresProduktMenu");
		expectedResult.setOutputVar3("oneIVR/vp5resProduktMenuFixnet2200");
		expectedResult.addToOutputColl1("select", "pm-menue-please-select");
		expectedResult.setNextState("CheckNightMenuLevel2Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0400a_NightMenuLevel2Response_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0400b_NightMenuLevel2Response_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0410a_CheckSelfServiceResponse_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckSelfServiceResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID0410b_CheckSelfServiceResponse_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "CheckSelfServiceResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID5000_Disconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Disconnect");

		expectedResult.setAction(SE_ACTION_DISCONNECT);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID5010_GoodbyeDisconnect() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GoodbyeDisconnect");

		expectedResult.setAction(SE_ACTION_GOODBYE);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID5030_Transfer() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("oneIVR/vp5resTransfer");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID9000_InputError() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "InputError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID9010_ConnectorError() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "ConnectorError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID9020_TechError() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "TechError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID9980_FALLBACK_TRANSFER() throws Exception {

		callProfile.put(CPK_NEXTSTATE,  "UnknownState");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}

/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMERulesStateWirelineTest.java 213 2014-03-17 08:08:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-17 09:08:23 +0100 (Mon, 17 Mar 2014) $
 * $Revision: 213 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.state;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts.Product;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMERulesStateWirelineTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	private static String RULES_FILE_NAME = "sme/vp5sme-wireline-state";

	private static CustomerProducts customerProducts;
	private static CallProfile callProfile;
	private static CustomerProfile customerProfile;
	private static CustomerProductClusters customerProductClusters;
	private static StateEngineRulesResult actualResult;
	private static StateEngineRulesResult expectedResult;
	private static ArrayList<Product> products;
	private static ArrayList<String> actionResponses;
	private static ArrayList<String> menus;
	private static ArrayList<String> grobSegments;

	private static ServiceConfigurationMap serviceConfigurationMap = new ServiceConfigurationMap();

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(SMERulesStateWirelineTest.class);
	}

	protected void setUp() {
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		expectedResult = new StateEngineRulesResult();
		expectedResult.setState(StateEngineRulesResult.DONE);
		products = new ArrayList<Product>();
		actionResponses = new ArrayList<String>();
		menus = new ArrayList<String>();
		grobSegments = new ArrayList<String>();
	}

	public final void testID010_InitWirelinePers() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "InitWirelinePers");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("MAIN");
		expectedResult.setNextState("CheckOHNewProductMenu");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020a_CheckOH_NewProductMenu_Wireline() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHNewProductMenu");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_SME_NEWPRODUCT_WIRELINE");
		expectedResult.setNextState("NewProductMenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID020b_CheckOH_NewProductMenu_Others() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHNewProductMenu");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_UNKNOWN);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030a_NewProductMenuLevel1_Closed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "NewProductMenuLevel1");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030b_NewProductMenuLevel1_Open() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "NewProductMenuLevel1");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("smeNPAlmMenuFixnet");
		expectedResult.setOutputVar3("sme/vp5smeNPAlmMenuFixnet");
		expectedResult.addToOutputColl1("select", "npalm-menue-please-select");
		expectedResult.setNextState("CheckNewProdMenuLevel1Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040a_NewProductMenuLevel1Response_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");

		actionResponses.add("TRANSFER");
		actionResponses.add("InputError");
		actionResponses.add("ConnectorError");

		for (String actionResponse: actionResponses) {

			callProfile.put(CPK_ACTIONRESPONSE, actionResponse);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");
			
			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID040b_NewProductMenuLevel1Response_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c_NewProductMenuLevel1Response_Continue() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040d_NewProductMenuLevel1Response_SubMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "MENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("NewProductMenuLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050_NewProductMenuLevel2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "NewProductMenuLevel2");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("smeNPPrmMenuFixnet");
		expectedResult.setOutputVar3("sme/vp5smeNPPrmMenuFixnet");
		expectedResult.addToOutputColl1("select", "npprm-menue-please-select");
		expectedResult.setNextState("CheckNewProdMenuLevel2Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060a_NewProductMenuLevel2Response_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel2Response");

		actionResponses.add("TRANSFER");
		actionResponses.add("InputError");
		actionResponses.add("ConnectorError");

		for (String actionResponse: actionResponses) {

			callProfile.put(CPK_ACTIONRESPONSE, actionResponse);
			
			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID060b_NewProductMenuLevel2Response_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060c_NewProductMenuLevel2Response_Continue() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNewProdMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("VectoringEntry");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070a_VectoringEntry_State01() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");
		
		customerProfile.setChange4Vectoring(CustomerProfile.C4V_61_INPROGRESS);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_01");
		expectedResult.setNextState("LogVectoring01");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070b_VectoringEntry_State02() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");
		
		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_SCHEDULED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_02");
		expectedResult.setNextState("LogVectoring02");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070c_VectoringEntry_State03() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");
		
		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_INPROGRESS);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_03");
		expectedResult.setNextState("LogVectoring03");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070d_VectoringEntry_State04() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");
		
		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_CLOSED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_04");
		expectedResult.setNextState("LogVectoring04");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070e_VectoringEntry_State05() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");
		
		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_CLOSED_ALARM);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_05");
		expectedResult.setNextState("LogVectoring05");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070f_VectoringEntry_State06() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_RESCHEDULED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_06");
		expectedResult.setNextState("LogVectoring06");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070g_VectoringEntry_State07() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_DELAYED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_07");
		expectedResult.setNextState("LogVectoring07");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070h_VectoringEntry_State08() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");

		customerProfile.setChange4Vectoring(CustomerProfile.C4V_60_ABORTED);

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("Vectoring_08");
		expectedResult.setNextState("LogVectoring08");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID070i_VectoringEntry_Default() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "VectoringEntry");
		
		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("ReturnFromVectoring");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080a_LogVectoring01() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring01");
		
		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '61-InProgress', 'Vectoring_01', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080b_LogVectoring02() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring02");
		
		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Scheduled', 'Vectoring_02', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080c_LogVectoring03() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring03");
		
		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-InProgress', 'Vectoring_03', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080d_LogVectoring04() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring04");
		
		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Closed', 'Vectoring_04', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080e_LogVectoring05() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring05");
		
		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Closed-Alarm', 'Vectoring_05', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080f_LogVectoring06() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring06");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Rescheduled', 'Vectoring_06', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080g_LogVectoring07() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring07");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Delayed', 'Vectoring_07', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID080h_LogVectoring08() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "LogVectoring08");

		expectedResult.setAction(SE_ACTION_WRITELOG);
		expectedResult.setOutputVar1("Generic");
		expectedResult.setOutputVar2("INSERT INTO VP2_CAMPAIGN_LOGGING (CALL_ID, CAMPAIGN_ID, ACTION, PROMPT, ROUTINGSKILL,  REASONCODE, DAY_ID, MINUTE_ID) VALUES ('@DLG_ID@', 'Vectoring', '60-Aborted', 'Vectoring_08', '', '', @DAY_ID@, @MINUTE_ID@)");
		expectedResult.setNextState("ReturnFromVectoring");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID090_ReturnFromVectoring() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "ReturnFromVectoring");
		
		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHSMEGlobal");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID100a_CheckOHSMEGlobal() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHSMEGlobal");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_SME_Global");
		expectedResult.setNextState("CheckOHSMEGlobalResult");		
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID100b_CheckOHSMEGlobal_QuadriCA() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHSMEGlobal");

		products.add(PR_MOBILE_N_STACK);

		for (Product p: products) {

			CustomerProducts customerProducts = new CustomerProducts();
			customerProducts.add(p);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");	
			
			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}
	
	public final void testID110a_CheckOHSMEGlobalResult_Day() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHSMEGlobalResult");
		callProfile.put(CPK_RESULTOH, "100");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("INASPersonalisation");		

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID110b_CheckOHSMEGlobalResult_Night() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHSMEGlobalResult");
		callProfile.put(CPK_RESULTOH, "1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("NightMenuLevel1");	

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID120_INASPersonalisation() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "INASPersonalisation");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("PERSONALISATION");
		expectedResult.setNextState("GetCARSStatus");	
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID125_GetCARSStatus() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "GetCARSStatus");

		expectedResult.setAction(SE_ACTION_CARSSTATUS);
		expectedResult.setNextState("PersonalisationCheck1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID130a_PersonalisationCheck1_gesperrterKunde_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "PersonalisationCheck1");
		
		customerProfile.setCarsStatus("block");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID130b_PersonalisationCheck1_nichtGesperrterKunde_Proceed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "PersonalisationCheck1");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("PersonalisationCheck2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140a_PersonalisationCheck2_mobileGesperrt_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "PersonalisationCheck2");
		callProfile.put(CPK_CALLDETAIL, "bla,mobilegesperrt,bla");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID140b_PersonalisationCheck2_not_mobileGesperrt_Proceed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "PersonalisationCheck2");
		callProfile.put(CPK_CALLDETAIL, "bla,iamnotmobilegesperrt,bla");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckForOptionOfferResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID150a_CheckForOptionOfferResult_ActivationDateWithinLast10Days() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckForOptionOfferResult");
		callProfile.setOptionOfferName("KMU Office W+C 4Star");
		// Create date within the last 10 days
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -9);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		
		callProfile.setOptionOfferActivation(sdf.format(cal.getTime()));
		
		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("KMUTeamworkTreatment");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID150b_CheckForOptionOfferResult_EverythingElse() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckForOptionOfferResult");
		callProfile.setOptionOfferName("Teamwork");

		// First test with correct optionOffer but outside the 10 days
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -11);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		
		callProfile.setOptionOfferActivation(sdf.format(cal.getTime()));
		
		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		
		// Second test without correct optionOffer
		callProfile.setOptionOfferName("SomethingElse");
		callProfile.setOptionOfferActivation("");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID160_KMUTeamworkTreatment() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "KMUTeamworkTreatment");

		expectedResult.setAction(SE_ACTION_CONFIRM);
		expectedResult.setOutputVar1("portal:kmu_question");
		expectedResult.setOutputVar2("kmuquestion");
		expectedResult.addToOutputColl1("yes", "1");
		expectedResult.addToOutputColl1("no", "2");
		expectedResult.addToOutputColl1("nomatch1", "retry");
		expectedResult.addToOutputColl1("noinput1", "retry");
		expectedResult.addToOutputColl1("nomatch2", "no");
		expectedResult.addToOutputColl1("noinput2", "no");
		expectedResult.setNextState("CheckKMUResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID170a_CheckKMUResponse_Yes() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckKMUResponse");
		callProfile.put("kmuquestion", "yes");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID170b_CheckKMUResponse_No() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckKMUResponse");
		callProfile.put("kmuquestion", "no");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("CheckOHCBR");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID180_CheckOHCBR() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHCBR");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_SME_CBR");
		expectedResult.setNextState("CheckOHCBRResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190a_CheckOHCBRResult_Open() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHCBRResult");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_CBR);
		expectedResult.setNextState("CheckCBRActionResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID190b_CheckOHCBRResult_Closed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHCBRResult");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200a_CheckCBRActionResponse_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckCBRActionResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "TRANSFER");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID200b_CheckCBRActionResponse_Continue() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckCBRActionResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID210_MenuLevel1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "MenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("smeAnliegenMenu");
		expectedResult.setOutputVar3("sme/vp5smeAnliegenMenu");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckMenuLevel1Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID220a1_MenuLevel1Response_Transfer_nonDiffKube() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");

		actionResponses.add("TRANSFER");
		actionResponses.add("InputError");
		actionResponses.add("ConnectorError");

		for (String actionResponse: actionResponses) {

			callProfile.put(CPK_ACTIONRESPONSE, actionResponse);
			
			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}


	public final void testID220a2_MenuLevel1Response_Transfer_Diffkube() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");

		customerProfile.setSa1_ntAccount("CARMAN_OL1");

		actionResponses.add("TRANSFER");
		actionResponses.add("InputError");
		actionResponses.add("ConnectorError");

		menus.add(CPV_STOERUNG);
		menus.add(CPV_ADMINISTRATION);

		for (String actionResponse: actionResponses) {
			for (String anliegenOption: menus) {

				callProfile.put(CPK_ACTIONRESPONSE, actionResponse);
				callProfile.put(SME_ANLIEGENMENU,anliegenOption);
				
				expectedResult.setAction(SE_ACTION_PROCEED);
				expectedResult.setNextState("SpeakDiffKubeThenTransfer");

				actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

				checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
			}
		}
	}

	public final void testID220b_MenuLevel1Response_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID220c_MenuLevel1Response_SubMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "MENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MenuLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}


	public final void testID220d_MenuLevel1Response_SAL() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "SAL");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("TransferSAL");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID220e_MenuLevel1Response_PBRDSLMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "PBRDSLMENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("PBRDSLMenu");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID230_PBRDSLMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "PBRDSLMenu");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("smePBRDSLMenu");
		expectedResult.setOutputVar3("sme/vp5smePBRDSLMenu");
		expectedResult.addToOutputColl1("select", "pbrdsl-menue-please-select");
		expectedResult.setNextState("CheckPBRDSLMenuResponse");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID240a_PBRDSLMenuResponse_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckPBRDSLMenuResponse");

		actionResponses.add("TRANSFER");
		actionResponses.add("InputError");
		actionResponses.add("ConnectorError");

		for (String actionResponse: actionResponses) {

			callProfile.put(CPK_ACTIONRESPONSE, actionResponse);

			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");
			
			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID240b_PBRDSLMenuResponse_Continue() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckPBRDSLMenuResponse");
		callProfile.put(CPK_ACTIONRESPONSE, "CONTINUE");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MenuLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}


	public final void testID250_MenuLevel2() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "MenuLevel2");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("smeProduktMenu");
		expectedResult.setOutputVar3("sme/vp5smeProduktMenu");
		expectedResult.addToOutputColl1("select", "pm-menue-please-select");
		expectedResult.setNextState("CheckMenuLevel2Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID260a_MenuLevel2Response_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel2Response");

		actionResponses.add("TRANSFER");
		actionResponses.add("InputError");
		actionResponses.add("ConnectorError");

		for (String actionResponse: actionResponses) {

			callProfile.put(CPK_ACTIONRESPONSE, actionResponse);
			
			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID260c1_MenuLevel2Response_Rechnung_SME_Seiteneinstieg() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "RECHNUNG");
		callProfile.put(CPK_CALLDETAIL,"Seiteneinstieg");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("INASBICOAfterRechnung");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID260c2_MenuLevel2Response_Rechnung_SME_no_Seiteneinstieg_numberChanged() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "RECHNUNG");
		callProfile.put("ani","0791234567");
		callProfile.put("phonenumber","+41419213930");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("INASBICOAfterRechnung");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID260c3_MenuLevel2Response_Rechnung_SME_no_Seiteneinstieg_numberUnchanged() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "RECHNUNG");
		callProfile.put("ani","0419213930");
		callProfile.put("phonenumber","+41419213930");

		customerProfile.setSegment(SEGMENT_SME);

		expectedResult.setAction(SE_ACTION_CUSTIDENT);
		expectedResult.setOutputVar2("SME");
		expectedResult.setNextState("CheckResultCustIdentAfterRechnung");
		
		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID260d_MenuLevel2Response_Rechnung_nonSME() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckMenuLevel2Response");
		callProfile.put(CPK_ACTIONRESPONSE, "RECHNUNG");

		grobSegments.add(SEGMENT_CBU);
		grobSegments.add(SEGMENT_RES);
		grobSegments.add("");
		grobSegments.add(null);

		for (String segment: grobSegments) {
			customerProfile.setSegment(segment);
			
			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("INASBICOAfterRechnung");
			
			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID290a_CheckResultCustIdentAfterRechnung_Confirmed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdentAfterRechnung");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIconfirmed");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("INASBICOAfterRechnung");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID290b_CheckResultCustIdentAfterRechnung_Rejected() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckResultCustIdentAfterRechnung");
		callProfile.put(CPK_ACTIONRESPONSE, "ANIrejected");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("PhoneNumberInputAfterRechnungAniRejected");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID300_PhoneNumberInputAfterRechnungAniRejected() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "PhoneNumberInputAfterRechnungAniRejected");

		expectedResult.setAction(SE_ACTION_ENTERPN);
		expectedResult.setOutputVar1("enterPhoneNumber");
		expectedResult.setNextState("GetResultCustInfoAfterRechnungAniRejected");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID310_GetResultCustInfoAfterRechnungAniRejected() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GetResultCustInfoAfterRechnungAniRejected");

		expectedResult.setAction(SE_ACTION_GETRESULTCUSTINFO);
		expectedResult.setNextState("INASBICOAfterRechnung");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID320_INAS_BICO_AfterRechnung() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "INASBICOAfterRechnung");

		expectedResult.setAction(SE_ACTION_EXECINASACTIONS);
		expectedResult.setOutputVar1("BICO");
		expectedResult.setNextState("CheckOHSMEBICOEMERGENCY");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID330_CheckOHSMEBICOEMERGENCY() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHSMEBICOEMERGENCY");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_SME_BICO_EMERGENCY");
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID340_NightMenuLevel1() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "NightMenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("smeNachtMenu");
		expectedResult.setOutputVar3("sme/vp5smeNachtMenu");
		expectedResult.addToOutputColl1("select", "alm-menue-please-select");
		expectedResult.setNextState("CheckNightMenuLevel1Response");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID350a_NightMenuLevel1Response_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");

		actionResponses.add("TRANSFER");
		actionResponses.add("InputError");
		actionResponses.add("ConnectorError");

		for (String actionResponse: actionResponses) {

			callProfile.put(CPK_ACTIONRESPONSE, actionResponse);
			
			expectedResult.setAction(SE_ACTION_PROCEED);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID350b_NightMenuLevel1Response_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "DISCONNECT");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("GoodbyeDisconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID350c_NightMenuLevel1Response_SubMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "MENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("MenuLevel2");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}


	public final void testID350d_NightMenuLevel1Response_SAL() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "SAL");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("TransferSAL");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID350e_NightMenuLevel1Response_PBRDSLMenu() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");
		callProfile.put(CPK_ACTIONRESPONSE, "PBRDSLMENU");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("PBRDSLMenu");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
		
	public final void testID350f_NightMenuLevel1Response_CONFIRMTRANSFER() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckNightMenuLevel1Response");

		actionResponses.add("CONFIRMTRANSFER");

		for (String actionResponse: actionResponses) {

			callProfile.put(CPK_ACTIONRESPONSE, actionResponse);

			expectedResult.setAction(SE_ACTION_CONFIRM);
			expectedResult.setOutputVar1("incident:INAS-dtmf-closed-kmu");
			expectedResult.setOutputVar2("closedkmu_confirm");
			expectedResult.addToOutputColl1("yes", "1");
			expectedResult.addToOutputColl1("no", "2");
			expectedResult.addToOutputColl1("nomatch1", "no");
			expectedResult.addToOutputColl1("noinput1", "no");
			expectedResult.setNextState("CheckClosedKMUConfirm");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID360a_CheckClosedKMUConfirm_Yes() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckClosedKMUConfirm");
		callProfile.put("closedkmu_confirm", "yes");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
	
	public final void testID360b_CheckClosedKMUConfirm_No() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckClosedKMUConfirm");
		callProfile.put("closedkmu_confirm", "no");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
		
	public final void testID500_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("sme/vp5smeTransfer");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID510_SpeakDiffKube_Prompt_ThenTransferSilent() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "SpeakDiffKubeThenTransfer");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("alm-dikube-end");
		expectedResult.setNextState("TransferSilent");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID520_TransferSilent() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "TransferSilent");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("sme/vp5smeTransfer");
		expectedResult.setOutputVar2("false");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID530_TransferSAL() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "TransferSAL");

		expectedResult.setAction(SE_ACTION_GENERICRETURN);
		expectedResult.setOutputVar1("PostProcess_SAL");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID890_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Disconnect");

		expectedResult.setAction(SE_ACTION_DISCONNECT);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID891_GoodbyeDisconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GoodbyeDisconnect");

		expectedResult.setAction(SE_ACTION_GOODBYE);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID900_InputError() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "InputError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID901_ConnectorError() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "ConnectorError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID902_TechError() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "TechError");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

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

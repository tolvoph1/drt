/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesState0800817175Test.java 155 2013-11-27 14:32:18Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-11-27 15:32:18 +0100 (Wed, 27 Nov 2013) $
 * $Revision: 155 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import java.util.ArrayList;
import java.util.List;

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
public class RESRulesState0800817175Test extends TestCase {


	private static RulesServiceDroolsImpl droolsImpl;
	private static final String RULES_FILE_NAME = "oneIVR/vp5res-0800817175-state";
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
		junit.textui.TestRunner.run(RESRulesState0800817175Test.class);
	}

	protected void setUp() {
		customerProducts = new CustomerProducts();
		callProfile = new CallProfile();
		customerProfile = new CustomerProfile();
		customerProductClusters = new CustomerProductClusters();
		serviceConfigurationMap = new ServiceConfigurationMap();
		expectedResult = new StateEngineRulesResult();
		callProfile.put(CPK_BUSINESSTYP, RES);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800817175);
		expectedResult.setState(StateEngineRulesResult.DONE);
	}

	public final void testID010_Init() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Init");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("LanguageSelection");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID020_LanguageSelection() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "LanguageSelection");

		expectedResult.setAction(SE_ACTION_LANGSELECT);
		expectedResult.setOutputVar1("false");
		expectedResult.setOutputVar2("dfi");
		expectedResult.setNextState("MenuLevel1");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID030_MenuLevel1() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "MenuLevel1");

		expectedResult.setAction(SE_ACTION_EXTENDEDMENU);
		expectedResult.setOutputVar1("CFSCustomerAnliegenMenu");
		expectedResult.setOutputVar3("oneIVR/vp5resAnliegenMenuCFSCustomer");
		expectedResult.addToOutputColl1("select", "cfs-customer-menu-AA-EM-PPS");
		expectedResult.addToOutputColl1("select-NI-1", "cfs-customer-menu-AA-EM-PPS");
		expectedResult.addToOutputColl1("select-NI-2", "cfs-customer-menu-AA-EM-PPS");
		expectedResult.addToOutputColl1("select-NM-1", "cfs-customer-menu-AA-EM-PPS");
		expectedResult.addToOutputColl1("select-NM-2", "cfs-customer-menu-AA-EM-PPS");
		expectedResult.addToOutputColl1("select-HELP", "cfs-customer-menu-AA-EM-PPS");
		expectedResult.setNextState("OpeningHours");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040a_OpeningHours_Menu_AA() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "OpeningHours");
		callProfile.put("CFSCustomerAnliegenMenu","AA");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_CFS_CUSTOMER_AA");
		expectedResult.setNextState("CheckOHResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040b_OpeningHours_Menu_EM() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "OpeningHours");
		callProfile.put("CFSCustomerAnliegenMenu","EM");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_CFS_CUSTOMER_EM");
		expectedResult.setNextState("CheckOHResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040c_OpeningHours_Menu_PPS() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "OpeningHours");
		callProfile.put("CFSCustomerAnliegenMenu","PPS");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_CFS_CUSTOMER_PPS");
		expectedResult.setNextState("CheckOHResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID040d_OpeningHours_Menu_ErrorInput() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "OpeningHours");

		expectedResult.setAction(SE_ACTION_CHECKOH);
		expectedResult.setOutputVar1("OH_RES_CFS_CUSTOMER_AA");
		expectedResult.setNextState("CheckOHResult");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050a_CheckOHResult_Closed() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHResult");
		callProfile.put(CPK_RESULTOH, "closed");

		expectedResult.setAction(SE_ACTION_SPEAK);
		expectedResult.setOutputVar1("closed-bye");
		expectedResult.setNextState("Disconnect");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID050b_CheckOHResult_Open() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "CheckOHResult");
		callProfile.put(CPK_RESULTOH, "open");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("EnterPLZ");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID060_EnterPLZ() throws Exception {

		callProfile.put(CPK_NEXTSTATE, "EnterPLZ");
		
		List<String> menuSelected = new ArrayList<String>();
		menuSelected.add("AA");
		menuSelected.add("EM");
		menuSelected.add("PPS");

		for (String menu: menuSelected) {

			callProfile.put("CFSCustomerAnliegenMenu",menu);

			expectedResult.setAction(SE_ACTION_ENTERPLZ);
			expectedResult.setOutputVar1(menu);
			expectedResult.setNextState("Transfer");

			actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

			checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
		}
	}

	public final void testID200_Disconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Disconnect");

		expectedResult.setAction(SE_ACTION_DISCONNECT);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID201_GoodbyeDisconnect() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "GoodbyeDisconnect");

		expectedResult.setAction(SE_ACTION_GOODBYE);

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}

	public final void testID500_Transfer() throws Exception {
		callProfile.put(CPK_NEXTSTATE, "Transfer");

		expectedResult.setAction(SE_ACTION_TRANSFER);
		expectedResult.setOutputVar1("oneIVR/vp5resTransfer");
		expectedResult.setNextState("Disconnect");

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
		callProfile.put(CPK_NEXTSTATE, "UnknownState");

		expectedResult.setAction(SE_ACTION_PROCEED);
		expectedResult.setNextState("Transfer");

		actualResult = droolsImpl.executeStateEngine(RULES_FILE_NAME, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfigurationMap);

		checkStateEngineRulesResult(RULES_FILE_NAME, expectedResult, actualResult);
	}
}

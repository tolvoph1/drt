/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferAXAWinterthurTest.java 97 2013-09-10 13:54:52Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-10 15:54:52 +0200 (Tue, 10 Sep 2013) $
 * $Revision: 97 $
 *
 * Testklasse für AXA Winterthur Businessnummer:
 * - 0800 724 001
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.rules.model.TransferRulesState;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesTransferAXAWinterthurTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String TRANSFER_RULES_FILE = "cbu/vp5cbu-0800724001-transfer";

	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static TransferRulesState transferRulesState;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesTransferAXAWinterthurTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}

	public final void testID0807() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_2, CPV_CBU_MENU_VOICE_AXA);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40807", "7300", transferRulesState);

	}

	public final void testID0808() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_2, CPV_CBU_MENU_DATA_AXA);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40808", "7301", transferRulesState);
	}

	public final void testID0809() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_2, CPV_CBU_MENU_MSS);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40809", "7201", transferRulesState);
	}

	public final void testID0810() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, CPV_LANGUAGE_G);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40810", "7031", transferRulesState);
	}

	public final void testID0811() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, CPV_LANGUAGE_F);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40811", "7032", transferRulesState);
	}

	public final void testID0935() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, CPV_LANGUAGE_I);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40935", "7033", transferRulesState);
	}

	public final void testID0936() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, CPV_LANGUAGE_E);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40936", "7031", transferRulesState);
	}

	public final void testID1005() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_ERROR);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41005", "7300", transferRulesState);
	}

	public final void testID1006() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_2, CPV_CBU_MENU_ERROR);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41006", "7300", transferRulesState);
	}

	public final void testID1025_FALLBACK() throws Exception {

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41025", "7175", transferRulesState);
	}	

	public final void testID1049() throws Exception {

		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724001_MENU_LEVEL_2, CPV_CBU_MENU_MO);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41049", "9591", transferRulesState);
	}
}





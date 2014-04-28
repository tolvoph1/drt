/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferSchweizerischePostTest.java 227 2014-03-31 07:31:26Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-31 09:31:26 +0200 (Mon, 31 Mar 2014) $
 * $Revision: 227 $
 *
 * Testklasse für Schweizerische Post Businessnummer:
 * - 0800 724 008
 * 
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
public class CBURulesTransferSchweizerischePostTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String TRANSFER_RULES_FILE = "cbu/vp5cbu-0800724008-transfer";

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
		junit.textui.TestRunner.run(CBURulesTransferSchweizerischePostTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}

	public final void testID0817() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, "g");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40817", "7039", transferRulesState);
	}

	public final void testID0940() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, "f");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40940", "7040", transferRulesState);
	}

	public final void testID0941() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, "i");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40941", "7041", transferRulesState);
	}

	public final void testID0942() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_LANGUAGE, "e");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40942", "7039", transferRulesState);
	}

	public final void testID0972() throws Exception {

		callProfile.put(CPK_CALLDETAIL, "cbr_cbu");
		callProfile.put(CPK_SROWNERUNIT, "ENT-CS-IOE-COC-1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40972", "7525", transferRulesState);
	}

	public final void testID1017() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_2, CPV_CBU_MENU_BB);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41017", "7525", transferRulesState);
	}

	public final void testID1018() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_ERROR);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41018", "7311", transferRulesState);
	}

	public final void testID1019() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_2, CPV_CBU_MENU_ERROR);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41019", "7312", transferRulesState);
	}

	public final void testID1025_FALLBACK() throws Exception {

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41025", "7175", transferRulesState);
	}	

	public final void testID1062() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_2, CPV_CBU_MENU_VOICE);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41062", "7311", transferRulesState);
	}

	public final void testID1063() throws Exception {

		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724008_MENU_LEVEL_2, CPV_CBU_MENU_DATA);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41063", "7312", transferRulesState);
	}
}





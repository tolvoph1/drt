/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferMobileDASITAdminTest.java 105 2013-09-10 14:51:20Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-10 16:51:20 +0200 (Tue, 10 Sep 2013) $
 * $Revision: 105 $
 *
 * Testklasse für Mobile DAS IT Admin1 Businessnummer:
 * - 0800 870 875
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
public class CBURulesTransferMobileDASITAdminTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String TRANSFER_RULES_FILE = "cbu/vp5cbu-0800870875-transfer";

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
		junit.textui.TestRunner.run(CBURulesTransferMobileDASITAdminTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}

	private final void runTest(String testCase, String PIN, String ID, String QU) throws Exception{

		callProfile.put(CPK_PIN, PIN);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult(testCase, ID, QU, transferRulesState);
	}

	public final void testID0906() throws Exception {
		runTest("906","7247","40906","7527");
	}

	public final void testID0907() throws Exception {
		runTest("907","349","40907","7529");
	}

	public final void testID0908() throws Exception {
		runTest("908","262","40908","7527");
	}

	public final void testID0909() throws Exception {
		runTest("909","error","40909","7529");
	}

	public final void testID0910() throws Exception {
		runTest("910","843","40910","7527");
	}

	public final void testID0911() throws Exception {
		runTest("911","937","40911","7529");
	}

	public final void testID0912() throws Exception {
		runTest("912","4357","40912","7529");
	}

	public final void testID0913() throws Exception {
		runTest("913","23646","40913","7529");
	}

	public final void testID0914() throws Exception {
		runTest("914","87425","40914","7529");
	}

	public final void testID0915() throws Exception {
		runTest("915","273348","40915","7529");
	}

	public final void testID0916() throws Exception {
		runTest("916","746","40916","7529");
	}

	public final void testID0917() throws Exception {
		runTest("917","7278","40917","7529");
	}

	public final void testID0918() throws Exception {
		runTest("918","672","40918","7527");
	}

	public final void testID1022() throws Exception {
		runTest("1022","252","41022","7527");
	}

	public final void testID1025_FALLBACK() throws Exception {
		runTest("1025","unknown","41025","7175");
	}

	public final void testID1054() throws Exception {
		runTest("1054","637","41054","7544");
	}

	public final void testID1055() throws Exception {
		runTest("1055","63722","41055","7545");
	}

	public final void testID1056() throws Exception {
		runTest("1056","63764","41056","7546");
	}
}





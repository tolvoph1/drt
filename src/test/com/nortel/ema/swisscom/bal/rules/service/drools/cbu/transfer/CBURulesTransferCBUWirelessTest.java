/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferCBUWirelessTest.java 227 2014-03-31 07:31:26Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-31 09:31:26 +0200 (Mon, 31 Mar 2014) $
 * $Revision: 227 $
 * 
 * Testklasse für die  CBU Wireless Businessnummer
 * - 0800 44 44 04
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer;

import java.util.ArrayList;
import java.util.Arrays;

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
public class CBURulesTransferCBUWirelessTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(					
					"cbu/vp5cbu-0800444404-transfer"		
					));

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
		junit.textui.TestRunner.run(CBURulesTransferCBUWirelessTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}

	public final void testID0891() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProfile.setSegment(SEGMENT_SME);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40891", "7573", transferRulesState);
		}
	}

	public final void testID0997() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProfile.setSegment(SEGMENT_CBU);

			callProfile.put(CPK_SALESTYPE, "Sales Assistant 1");
			callProfile.put(CPK_ORGUNIT, "ENT-CS-CSD-CSK-5");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40997", "7000", transferRulesState);
		}
	}

	public final void testID0999() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProfile.setSegment(SEGMENT_CBU);

			callProfile.put(CPK_SALESTYPE, "Sales Assistant 1");
			callProfile.put(CPK_ORGUNIT, "ENT-CS-CSD-CSK-3");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40999", "7001", transferRulesState);
		}
	}

	public final void testID1000() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProfile.setSegment(SEGMENT_CBU);

			callProfile.put(CPK_SALESTYPE, "Sales Assistant 1");
			callProfile.put(CPK_ORGUNIT, "ENT-CS-CSD-CSK-6");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41000", "7002", transferRulesState);
		}
	}

	public final void testID1001() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProfile.setSegment(SEGMENT_CBU);

			callProfile.put(CPK_SALESTYPE, "Sales Assistant 1");
			callProfile.put(CPK_ORGUNIT, "ENT-CS-CSD-CSK-1");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41001", "7003", transferRulesState);
		}
	}

	public final void testID1053() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41053", "7557", transferRulesState);
		}
	}
}

/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferOuttaskingKundenIncidentTest.java 105 2013-09-10 14:51:20Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-10 16:51:20 +0200 (Tue, 10 Sep 2013) $
 * $Revision: 105 $
 *
 * Testklasse für Schweizerische Post Businessnummer:
 * - 0800 819 175
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
public class CBURulesTransferOuttaskingKundenIncidentTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String TRANSFER_RULES_FILE = "cbu/vp5cbu-0800819175-transfer";

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
		junit.textui.TestRunner.run(CBURulesTransferOuttaskingKundenIncidentTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}

	public final void testID0943() throws Exception {

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("40943", "7177", transferRulesState);
	}
}
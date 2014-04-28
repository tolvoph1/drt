/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferCBUKundendienstTest.java 236 2014-04-15 12:36:40Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-15 14:36:40 +0200 (Tue, 15 Apr 2014) $
 * $Revision: 236 $
 *
 * Testklasse für die Hauptnummer CBU Kundendienst:
 * - 0800 800 900
 * - 0800 80 800 900
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class CBURulesTransferCBUKundendienstTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"cbu/vp5cbu-0800800900-transfer",
					"cbu/vp5cbu-080080800900-transfer"
					));

	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static TransferRulesState transferRulesState;
	private static List<String> menuSelections;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}	

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesTransferCBUKundendienstTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		menuSelections = new ArrayList<String>();
	}

	public final void testID0801() throws Exception {

		menuSelections.add("OTHER");
		menuSelections.add("error");

		for (String RULES_FILE_NAME: rules) {
			for (String menuSelected: menuSelections) {

				callProfile.put("cbukundendienstmenulevel1", menuSelected);

				transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);

				checkTransferRulesResult(RULES_FILE_NAME, "40801", "7025", transferRulesState);
			}
		}
	}
	
	public final void testID0802() throws Exception {
		for (String RULES_FILE_NAME: rules) {
		
				callProfile.put("cbukundendienstmenulevel1", "BILLING");

				transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);

				checkTransferRulesResult(RULES_FILE_NAME, "40802", "7273", transferRulesState);
				}
	}
	
	public final void testID0803() throws Exception {
		for (String RULES_FILE_NAME: rules) {
		
				callProfile.put("cbukundendienstmenulevel1", "EXTRANET");

				transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);

				checkTransferRulesResult(RULES_FILE_NAME, "40803", "7274", transferRulesState);
				}
	}
}
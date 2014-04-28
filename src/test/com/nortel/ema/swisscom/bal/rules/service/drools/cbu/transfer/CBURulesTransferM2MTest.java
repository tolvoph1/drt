/*
 * (c)2014 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferM2MTest.java 165 2014-01-17 10:59:37Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-01-17 11:59:37 +0100 (Fri, 17 Jan 2014) $
 * $Revision: 165 $
 *
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

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
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProductClusters;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesTransferM2MTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"cbu/vp5cbu-0800724011-transfer"
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
		junit.textui.TestRunner.run(CBURulesTransferM2MTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}
	
	public final void testID1065() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_CBU_0800724011_MENU_LEVEL_1, CPV_CBU_MENU_SF);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41065", "7006", transferRulesState);
		}
	}

	public final void testID1066() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_CBU_0800724011_MENU_LEVEL_1, CPV_CBU_MENU_SA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41066", "7005", transferRulesState);
		}
	}
	
	public final void testID1067() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_CBU_0800724011_MENU_LEVEL_1, CPV_CBU_MENU_ERROR);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41067", "7005", transferRulesState);
		}
	}
}





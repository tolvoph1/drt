/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferVBSTest.java 131 2013-10-18 05:47:56Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-10-18 07:47:56 +0200 (Fri, 18 Oct 2013) $
 * $Revision: 131 $
 *
 * Testklasse für VBS Businessnummer:
 * - 0800 824 825
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

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
public class CBURulesTransferVBSTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String TRANSFER_RULES_FILE = "cbu/vp5cbu-0800824825-transfer";
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

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesTransferVBSTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}


	public final void testID1040() throws Exception {

		callProfile.put(CPK_CBU_0800824825_MENU_LEVEL_1, "TRANSFERALM");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41040", "7530", transferRulesState);
	}
	
	public final void testID1041() throws Exception {

		callProfile.put(CPK_CBU_0800824825_MENU_LEVEL_1, "SF");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41041", "7558", transferRulesState);
	}
	
	public final void testID1042() throws Exception {

		callProfile.put(CPK_CBU_0800824825_MENU_LEVEL_1,"SA");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41042", "7559", transferRulesState);
	}
	
	public final void testID1043() throws Exception {

		callProfile.put(CPK_CBU_0800824825_MENU_LEVEL_1, CPV_MENUOPTION_ERROR);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41043", "7558", transferRulesState);
	}
}





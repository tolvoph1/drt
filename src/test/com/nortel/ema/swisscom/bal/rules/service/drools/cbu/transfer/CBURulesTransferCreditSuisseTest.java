/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferCreditSuisseTest.java 227 2014-03-31 07:31:26Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-31 09:31:26 +0200 (Mon, 31 Mar 2014) $
 * $Revision: 227 $
 * 
 * Testklasse für die Credit Suisse Businessnummern:
 * - 0800 724 002
 * - 0800 724 002 02
 * - 044 294 66 66
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
public class CBURulesTransferCreditSuisseTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"cbu/vp5cbu-0442946666-transfer",
					"cbu/vp5cbu-0800724002-transfer",
					"cbu/vp5cbu-080072400202-transfer"
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
		junit.textui.TestRunner.run(CBURulesTransferCreditSuisseTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
	}

	public String KEY(String rulesFileName) {
		if (rulesFileName.equals("cbu/vp5cbu-0442946666-transfer")) {
			return CPK_CBU_0442946666_MENU_LEVEL_1;
		} else if (rulesFileName.equals("cbu/vp5cbu-0800724002-transfer")) {
			return CPK_CBU_0800724002_MENU_LEVEL_1;
		} else if (rulesFileName.equals("cbu/vp5cbu-080072400202-transfer")) {
			return CPK_CBU_080072400202_MENU_LEVEL_1;
		} else {
			return null;
		}
	}

	public final void testID0812() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_VOICE_AXA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40812", "7066", transferRulesState);
		}
	}

	public final void testID0813() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_DATA_AXA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40813", "7087", transferRulesState);
		}
	}

	public final void testID0814() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_ORDER);
			callProfile.put(CPK_LANGUAGE, "g");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40814", "7035", transferRulesState);
		}
	}

	public final void testID0815() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_BB);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40815", "7523", transferRulesState);
		}
	}

	public final void testID0937() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_ORDER);
			callProfile.put(CPK_LANGUAGE, "f");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40937", "7036", transferRulesState);
		}
	}

	public final void testID0938() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_ORDER);
			callProfile.put(CPK_LANGUAGE, "i");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40938", "7038", transferRulesState);
		}
	}

	public final void testID0939() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_ORDER);
			callProfile.put(CPK_LANGUAGE, CPV_LANGUAGE_E);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40939", "7035", transferRulesState);
		}
	}

	public final void testID0971() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(CPK_CALLDETAIL, "cbr_cbu");
			callProfile.put(CPK_SROWNERUNIT, "ENT-CS-IOE-COC-1");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40971", "7523", transferRulesState);
		}
	}

	public final void testID1007() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY(RULES_FILE_NAME), CPV_CBU_MENU_ERROR);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41007", "7066", transferRulesState);
		}
	}

	public final void testID1025_FALLBACK() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41025", "7175", transferRulesState);
		}
	}
}





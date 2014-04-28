/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferNovartisTest.java 99 2013-09-10 14:14:07Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-10 16:14:07 +0200 (Tue, 10 Sep 2013) $
 * $Revision: 99 $
 * 
 * Testklasse für Novartis Businessnummer:
 * - 0800 724 003
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.ArrayList;

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
public class CBURulesTransferNovartisTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final String TRANSFER_RULES_FILE = "cbu/vp5cbu-0800724003-transfer";

	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static TransferRulesState transferRulesState;
	private static ArrayList<String> languages;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesTransferNovartisTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		languages = new ArrayList<String>();
	}

	public final void testID1029() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_FX_NV);

		languages.add("g");
		languages.add("f");
		languages.add("i");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41029", "7314", transferRulesState);
		}
	}

	public final void testID1030() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_FX_NV);

		callProfile.put(CPK_LANGUAGE,"e");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41030", "7315", transferRulesState);
	}

	public final void testID1031() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_MO_NV);

		languages.add("g");
		languages.add("f");
		languages.add("i");
		languages.add("e");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41031", "7561", transferRulesState);
		}
	}

	public final void testID1032() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_PAGING_NV);

		languages.add("g");
		languages.add("f");
		languages.add("i");
		languages.add("e");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41032", "7198", transferRulesState);
		}
	}

	public final void testID1033() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_FX_NV);

		languages.add("g");
		languages.add("e");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41033", "7192", transferRulesState);
		}
	}

	public final void testID1034() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_FX_NV);

		callProfile.put(CPK_LANGUAGE,"f");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41034", "7193", transferRulesState);
	}

	public final void testID1035() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_FX_NV);

		callProfile.put(CPK_LANGUAGE,"i");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41035", "7194", transferRulesState);
	}

	public final void testID1036() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_MO_NV);

		languages.add("g");
		languages.add("e");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41036", "7512", transferRulesState);
		}
	}

	public final void testID1037() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_MO_NV);

		callProfile.put(CPK_LANGUAGE,"f");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41037", "7514", transferRulesState);				
	}

	public final void testID1038() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_MO_NV);

		callProfile.put(CPK_LANGUAGE,"i");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);

		checkTransferRulesResult("41038", "7560", transferRulesState);
	}

	public final void testID1045() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ERROR);

		languages.add("g");
		languages.add("f");
		languages.add("i");
		languages.add("e");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41045", "7192", transferRulesState);
		}
	}

	public final void testID1046() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_SA_OUS);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_ERROR);

		languages.add("g");
		languages.add("f");
		languages.add("i");
		languages.add("e");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41046", "7561", transferRulesState);				
		}
	}

	public final void testID1047a() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_1, CPV_CBU_MENU_ORDER);
		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_ERROR);

		languages.add("g");
		languages.add("f");
		languages.add("i");
		languages.add("e");

		for (String language: languages) {

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41047", "7192", transferRulesState);
		}
	}

	public final void testID1047b() throws Exception {

		callProfile.put(CPK_CBU_0800724003_MENU_LEVEL_2, CPV_CBU_MENU_ERROR);

		languages.add("g");
		languages.add("f");
		languages.add("i");
		languages.add("e");

		for (String language: languages) {	

			callProfile.put(CPK_LANGUAGE,language);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFER_RULES_FILE, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult("41047", "7192", transferRulesState);				
		}
	}
}
/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBURulesTransferIncidentWirelineTest.java 108 2013-09-11 08:56:01Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-11 10:56:01 +0200 (Wed, 11 Sep 2013) $
 * $Revision: 108 $
 *
 * Testklasse für die Hauptnummer Incident Wireline:
 * - 0800 724 724
 * - 00800 724 724 24
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
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts.Product;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CBURulesTransferIncidentWirelineTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;

	private static final ArrayList<String> rules = new ArrayList<String>(
			Arrays.asList(
					"cbu/vp5cbu-0800724724-transfer",
					"cbu/vp5cbu-0080072472424-transfer"
					));

	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static TransferRulesState transferRulesState;
	private static ArrayList<Product> productList;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}	

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CBURulesTransferIncidentWirelineTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		productList = new ArrayList<Product>();
	}

	public String KEY1(String rulesFileName) {
		if (rulesFileName.equals("cbu/vp5cbu-0800724724-transfer")) {
			return CPK_CBU_0800724724_MENU_LEVEL_1;
		} else if (rulesFileName.equals("cbu/vp5cbu-0080072472424-transfer")) {
			return CPK_CBU_0080072472424_MENU_LEVEL_1;
		} else {
			return null;
		}
	}

	public String KEY2(String rulesFileName) {
		if (rulesFileName.equals("cbu/vp5cbu-0800724724-transfer")) {
			return CPK_CBU_0800724724_MENU_LEVEL_2;
		} else if (rulesFileName.equals("cbu/vp5cbu-0080072472424-transfer")) {
			return CPK_CBU_0080072472424_MENU_LEVEL_2;
		} else {
			return null;
		}
	}

	public final void testID0951() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_FSS);
			customerProductClusters.add(PR_CLUSTER_ONB);
			customerProductClusters.add(PR_CLUSTER_OWP);
			customerProductClusters.add(PR_CLUSTER_OPB);
			// Products with lower priority
			customerProductClusters.add(PR_CLUSTER_ACD);
			customerProductClusters.add(PR_CLUSTER_MCCS);
			customerProductClusters.add(PR_CLUSTER_IPP);
			customerProductClusters.add(PR_CLUSTER_INTN);
			customerProductClusters.add(PR_CLUSTER_EALARM);
			customerProductClusters.add(PR_CLUSTER_LL);
			customerProductClusters.add(PR_CLUSTER_LL_PRE);
			customerProductClusters.add(PR_CLUSTER_SINET);
			customerProductClusters.add(PR_CLUSTER_CYK);
			customerProductClusters.add(PR_CLUSTER_BVOIP);
			customerProductClusters.add(PR_CLUSTER_NWS);
			customerProductClusters.add(PR_CLUSTER_IPSS);
			customerProductClusters.add(PR_CLUSTER_LAN_I);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40951", "7075", transferRulesState);
		}
	}

	public final void testID0954() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_IPP);
			// Products with lower priority
			customerProductClusters.add(PR_CLUSTER_INTN);
			customerProductClusters.add(PR_CLUSTER_EALARM);
			customerProductClusters.add(PR_CLUSTER_LL);
			customerProductClusters.add(PR_CLUSTER_LL_PRE);
			customerProductClusters.add(PR_CLUSTER_SINET);
			customerProductClusters.add(PR_CLUSTER_CYK);
			customerProductClusters.add(PR_CLUSTER_BVOIP);
			customerProductClusters.add(PR_CLUSTER_NWS);
			customerProductClusters.add(PR_CLUSTER_IPSS);
			customerProductClusters.add(PR_CLUSTER_LAN_I);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40954", "7306", transferRulesState);
		}
	}

	public final void testID0956() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_EALARM);
			customerProductClusters.add(PR_CLUSTER_LL);
			// Products with lower priority
			customerProductClusters.add(PR_CLUSTER_LL_PRE);
			customerProductClusters.add(PR_CLUSTER_SINET);
			customerProductClusters.add(PR_CLUSTER_CYK);
			customerProductClusters.add(PR_CLUSTER_BVOIP);
			customerProductClusters.add(PR_CLUSTER_NWS);
			customerProductClusters.add(PR_CLUSTER_IPSS);
			customerProductClusters.add(PR_CLUSTER_LAN_I);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40956", "7307", transferRulesState);
		}
	}

	public final void testID0959() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_IPSS);
			customerProductClusters.add(PR_CLUSTER_LAN_I);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40959", "7308", transferRulesState);
		}
	}

	public final void testID0960() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_VO_AVANOR);
			// Other products that have lower priority
			customerProductClusters.add(PR_CLUSTER_VO_ALC);
			customerProductClusters.add(PR_CLUSTER_VO_AAS);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40960", "7170", transferRulesState);
		}
	}

	public final void testID0961() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_VO_CISBVOIP);
			// Other products that have lower priority
			customerProductClusters.add(PR_CLUSTER_VO_SIE);
			customerProductClusters.add(PR_CLUSTER_VO_AVANOR);
			customerProductClusters.add(PR_CLUSTER_VO_ALC);
			customerProductClusters.add(PR_CLUSTER_VO_AAS);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40961", "7173", transferRulesState);
		}
	}

	public final void testID0962() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_VO_SIE);
			// Other products that have lower priority
			customerProductClusters.add(PR_CLUSTER_VO_AVANOR);
			customerProductClusters.add(PR_CLUSTER_VO_ALC);
			customerProductClusters.add(PR_CLUSTER_VO_AAS);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40962", "7169", transferRulesState);
		}
	}

	public final void testID0964() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_VO_ALC);
			// Other products that have lower priority
			customerProductClusters.add(PR_CLUSTER_VO_AAS);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40964", "7171", transferRulesState);
		}
	}

	public final void testID0966() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_VO_AAS);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40966", "7172", transferRulesState);
		}
	}

	public final void testID0967() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40967", "7177", transferRulesState);
		}
	}

	public final void testID0968() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40968", "7309", transferRulesState);
		}
	}

	public final void testID0969() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SF);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_OTHER);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40969", "7024", transferRulesState);
		}
	}

	public final void testID0977() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_MO);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "40977", "7556", transferRulesState);
		}
	}

	public final void testID1008() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_MCC);
			// Other products that have lower priority
			customerProductClusters.add(PR_CLUSTER_VO_CISBVOIP);
			customerProductClusters.add(PR_CLUSTER_VO_SIE);
			customerProductClusters.add(PR_CLUSTER_VO_AVANOR);
			customerProductClusters.add(PR_CLUSTER_VO_ALC);
			customerProductClusters.add(PR_CLUSTER_VO_AAS);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41008", "7176", transferRulesState);
		}
	}

	public final void testID1009() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_CSOL);
			// Other products that have lower priority
			customerProductClusters.add(PR_CLUSTER_MCC);
			customerProductClusters.add(PR_CLUSTER_VO_CISBVOIP);
			customerProductClusters.add(PR_CLUSTER_VO_SIE);
			customerProductClusters.add(PR_CLUSTER_VO_AVANOR);
			customerProductClusters.add(PR_CLUSTER_VO_ALC);
			customerProductClusters.add(PR_CLUSTER_VO_AAS);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_VOICE);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41009", "7174", transferRulesState);
		}
	}

	public final void testID1010() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_ERROR);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41010", "7175", transferRulesState);
		}
	}

	public final void testID1011() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_ERROR);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41011", "7175", transferRulesState);
		}
	}

	public final void testID1012() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			productList.add(PR_DSL_MOBILE);
			productList.add(PR_FX_BLUEWIN_DSL);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);
			callProfile.put("pbrdsl_confirm","yes");

			for (Product product: productList) {

				customerProducts = new CustomerProducts();
				customerProducts.add(product);

				transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);

				checkTransferRulesResult(RULES_FILE_NAME, "41012", "9014", transferRulesState);
			}
		}
	}

	public final void testID1013() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			productList.add(PR_BUSINESSCONNECT_IPC);
			productList.add(PR_BUSINESS_INTERNET_STANDARD);
			productList.add(PR_VPN_PROFESSIONELL);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);
			callProfile.put("pbrdsl_confirm","yes");

			for (Product product: productList) {

				customerProducts = new CustomerProducts();
				customerProducts.add(product);
				if (product == PR_BUSINESS_INTERNET_STANDARD) { // If Customer has BIS also add BIL since this rules has higher salience
					customerProducts.add(PR_BUSINESS_INTERNET_LIGHT);
				}

				transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);

				checkTransferRulesResult(RULES_FILE_NAME, "41013", "8025", transferRulesState);
			}
		}
	}

	public final void testID1014() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProducts.add(PR_BUSINESS_INTERNET_LIGHT);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);
			callProfile.put("pbrdsl_confirm","yes");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41014", "8020", transferRulesState);
		}
	}

	public final void testID1025_FALLBACK() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			transferRulesState = droolsImpl.getTransferQueue(
					RULES_FILE_NAME, customerProfile, customerProducts,
					customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41025", "7175", transferRulesState);
		}
	}

	public final void testID1027() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SF);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_MO);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41027", "7557", transferRulesState);
		}
	}

	public final void testID1048() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SF);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_ERROR);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41048", "7024", transferRulesState);
		}
	}

	public final void testID1050() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_CUC_INT);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41050", "7316", transferRulesState);
		}
	}

	public final void testID1051() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_MSS);
			callProfile.put("pin", "8645");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41051", "7201", transferRulesState);
		}
	}

	public final void testID1052() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_MSS);
			callProfile.put("pin", "error");

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41052", "7309", transferRulesState);
		}
	}

	public final void testID1064() throws Exception {
		for (String RULES_FILE_NAME: rules) {

			customerProductClusters.add(PR_CLUSTER_SECURITY);

			callProfile.put(KEY1(RULES_FILE_NAME), CPV_CBU_MENU_SA);
			callProfile.put(KEY2(RULES_FILE_NAME), CPV_CBU_MENU_DATA);

			transferRulesState = droolsImpl.getTransferQueue(RULES_FILE_NAME, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);

			checkTransferRulesResult(RULES_FILE_NAME, "41064", "7317", transferRulesState);
		}
	}
}





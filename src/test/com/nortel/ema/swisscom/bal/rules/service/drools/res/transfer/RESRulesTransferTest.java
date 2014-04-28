/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESRulesTransferTest.java 233 2014-04-08 06:19:14Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-08 08:19:14 +0200 (Tue, 08 Apr 2014) $
 * $Revision: 233 $
 * 
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts.Product;

/**
 * @author $Author: tolvoph1 $
 * @version $Revision: 233 $ ($Date: 2014-04-08 08:19:14 +0200 (Tue, 08 Apr 2014) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RESRulesTransferTest extends TestCase {

	private static RulesServiceDroolsImpl droolsImpl;
	// Define the transfer rules filename used for all testcases
	private static final String TRANSFERRULES = ONEIVRRES_TRANSFER;
	// Check counter for testcases, used in setUp and tearDown methods to check if at least 1 testcase was run
	private static long numberOfTestCasesInThisCase = 0;
	private static long countTestCases = 0;
	// variables used in all/most test cases
	private static ServiceConfigurationMap serviceConfiguration;
	private static CustomerProfile customerProfile;
	private static CallProfile callProfile;
	private static CustomerProducts customerProducts;
	private static CustomerProductClusters customerProductClusters;
	private static TransferRulesState transferRulesState;
	private static ArrayList<String> grobSegments;
	private static ArrayList<String> feinSegments;
	private static ArrayList<String> subSegments;
	private static HashMap<String, String> menuSelectionsMap; 
	private static ArrayList<String> businessNumbers;
	private static ArrayList<String> custTypeList;
	private static ArrayList<String> connTypeList;
	private static ArrayList<String> miscList;
	@SuppressWarnings("unused")
	private static ArrayList<Product> products;
	private static CustomerProducts customerProductsList;

	static {
		Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);
	}

	private static void setupServiceConfiguration() {
		serviceConfiguration.put("vp.res.standardRoutingID","9000");
		serviceConfiguration.put("vp.res.totalCallTimeout","30");
		serviceConfiguration.put("vp.res.topRouting.Active","true");
		serviceConfiguration.put("vp.res.saverdeskRouting.Active","false");
		serviceConfiguration.put("vp.res.dnislanguage","g");
		serviceConfiguration.put("vp.res.nottext.Active","true");
		serviceConfiguration.put("vp.res.caller.MaxAge","95");
		serviceConfiguration.put("vp.res.caller.BestAgeStart","95");
		serviceConfiguration.put("vp.res.openCases.maxNumberOpenCases","6");
		serviceConfiguration.put("vp.res.longTimePeriod","0");
		serviceConfiguration.put("vp.res.shortTimePeriod","24");
		serviceConfiguration.put("vp.res.getResultRetryTime","5000");
		serviceConfiguration.put("vp.res.getResultRetryMax","3");
		serviceConfiguration.put("vp.res.dtmfNumberConfirmation.Active","false");
		serviceConfiguration.put("vp.res.mysAccessState.StatusOpenWait","5000");
		serviceConfiguration.put("vp.res.premium.bn.internat","0080088111213");
		serviceConfiguration.put("vp.res.portfolioBasedRouting.Active","1");
		serviceConfiguration.put("vp.res.ccm.maxBillMonths","12");
		serviceConfiguration.put("vp.res.ccm.minPaymentDueAge","3");
		serviceConfiguration.put("vp.res.openCases.originFilter","	");
		serviceConfiguration.put("vp.res.premium.bn.nat","0800881112");
		serviceConfiguration.put("vp.res.openCases.numDays","30");
		serviceConfiguration.put("vp.res.mysAccessState.Active","true");
		serviceConfiguration.put("vp.res.mysAccessState.dtmfNumberConfirmation.Active","true");
		serviceConfiguration.put("vp.res.mysAccessState.SMSNotification.Active","true");
		serviceConfiguration.put("vp.res.openCases.status","	");
		serviceConfiguration.put("vp.res.YoungsterBestAge.maxAge","27");
	}

	/**
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		junit.textui.TestRunner.run(RESRulesTransferTest.class);
	}

	protected void setUp() {
		serviceConfiguration = new ServiceConfigurationMap();
		customerProfile = new CustomerProfile();
		callProfile = new CallProfile();
		customerProducts = new CustomerProducts();
		customerProductClusters = new  CustomerProductClusters();
		grobSegments = new ArrayList<String>();
		feinSegments = new ArrayList<String>();
		subSegments = new ArrayList<String>();
		callProfile.put(CPK_BUSINESSTYP, RES);
		menuSelectionsMap = new HashMap<String, String>();
		businessNumbers = new ArrayList<String>();
		custTypeList = new ArrayList<String>();
		connTypeList = new ArrayList<String>();
		miscList = new ArrayList<String>();
		setupServiceConfiguration();
		numberOfTestCasesInThisCase = countTestCases;
		products = new ArrayList<Product>();
		customerProductsList = new CustomerProducts();
	}
	protected void tearDown() {

		if (numberOfTestCasesInThisCase==countTestCases) {
			System.out.println("WARNING: This test case had 0 runs!!!");
			System.exit(1);
		}

	}
	/**
	 * Adds complete Internet product cluster to the passed customerProducts object
	 * PR_BLUEWIN_PHONE
	 * PR_FX_BLUEWIN_DSL
	 * PR_FX_BLUEWIN_DIALUP
	 * PR_FX_BLUEWIN_NAKEDACCOUNT
	 * PR_DSL_MOBILE
	 * @param customerProducts
	 */
	private static void addProductClusterInternetOnlyIN(final CustomerProducts customerProducts) {
		customerProducts.add(PR_BLUEWIN_PHONE);
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_MOBILE);
	}
	/**
	 * Adds Internet product cluster that is not part of the menu relevant cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterABB(final CustomerProducts customerProducts) {
		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);
	}
	/**
	 * Adds complete Mobile product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterMobile(final CustomerProducts customerProducts) {
		customerProducts.add(PR_HASMOBILE);
		customerProducts.add(PR_MOBILE76);
		customerProducts.add(PR_MOBILE77);
		customerProducts.add(PR_MOBILE78);
		customerProducts.add(PR_MOBILE79);
	}

	/**
	 * Adds Duplo only product cluster to the passed customerProducts object
	 * PR_DSL_MOBILE
	 * @param customerProducts
	 */
	@SuppressWarnings("unused")
	private static void addProductClusterDuploOnly(final CustomerProducts customerProducts) {
		customerProducts.add(PR_DSL_MOBILE);
	}

	/**
	 * Adds non-Duplo Internet product cluster to the passed customerProducts object
	 * PR_FX_BLUEWIN_DSL
	 * PR_FX_BLUEWIN_DIALUP
	 * @param customerProducts
	 */
	private static void addProductClusterInternet_NoDuplo(final CustomerProducts customerProducts) {
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
	}


	/**
	 * Adds complete Fixnet product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterFixnet(final CustomerProducts customerProducts) {
		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_MULTILINEISDN_MSNNUMMER);
	}
	/**
	 * Adds complete TV product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterTV(final CustomerProducts customerProducts) {
		customerProducts.add(PR_BLUEWIN_TV);
		//		customerProducts.add(PR_ALL_TRIO_BUNDLE);
	}
	/**
	 * Adds complete VOIP product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterVoIP(final CustomerProducts customerProducts) {
		customerProducts.add(PR_VOIP1L);
		customerProducts.add(PR_VOIPFX);
		customerProducts.add(PR_VOIP1LVN);
		customerProducts.add(PR_VOIPFXFN);
	}
	/**
	 * Adds complete AllIP product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterAllIP(final CustomerProducts customerProducts) {
		customerProducts.add(PR_ALL_TRIO_BUNDLE);
		customerProducts.add(PR_ALL_DUO_BUNDLE);
		customerProducts.add(PR_ALL_FIBERLINE_MINI);
		customerProducts.add(PR_ALL_FIBERLINE_TV_BASIC);
		customerProducts.add(PR_ALL_CASATRIO_BASIC);
		customerProducts.add(PR_ALL_CASATRIO_MINI);
		customerProducts.add(PR_ALL_HOMEENTERTAINMENT_3STAR);
		customerProducts.add(PR_ALL_HOMEENTERTAINMENT_4STAR);
		customerProducts.add(PR_ALL_HOMEENTERTAINMENT_5STAR);
	}

	/**
	 * Adds all available products to the passed CustomerProducts object
	 * @param customerProducts
	 */
	private static void addProductsAll(final CustomerProducts customerProducts) {
		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
	}
	private static void addProductsAllExceptTV(final CustomerProducts customerProducts) {
		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterMobile(customerProducts);
	}

	/**
	 * Adds all existing feinSegments to the passed ArrayList
	 * @param feinSegments
	 */
	private static void addFeinSegmentsAll(final ArrayList<String> feinSegments) {
		feinSegments.add(FINESEGMENT_PP);
		feinSegments.add(FINESEGMENT_TY);
		feinSegments.add(FINESEGMENT_MFA);
		feinSegments.add(FINESEGMENT_S55);
		feinSegments.add(FINESEGMENT_PS);
		feinSegments.add(FINESEGMENT_1);
		feinSegments.add(FINESEGMENT_2);
		feinSegments.add(FINESEGMENT_3);
		feinSegments.add(FINESEGMENT_4);
		feinSegments.add(FINESEGMENT_5);
		feinSegments.add(FINESEGMENT_PLATIN);
		feinSegments.add(FINESEGMENT_GOLD);
		feinSegments.add(FINESEGMENT_FIRST);
		feinSegments.add(FINESEGMENT_BUS);
		feinSegments.add(FINESEGMENT_NEW);
	}
	/**
	 * Adds all feinsegments for RES grobsegment
	 * @param feinSegments
	 */
	private static void addFeinSegmentsRES(final ArrayList<String> feinSegments) {
		feinSegments.add(FINESEGMENT_PP);
		feinSegments.add(FINESEGMENT_TY);
		feinSegments.add(FINESEGMENT_MFA);
		feinSegments.add(FINESEGMENT_S55);
		feinSegments.add(FINESEGMENT_PS);
	}

	/**
	 * Adds all feinsegments for CBU grobsegment
	 * @param feinSegments
	 */
	private static void addFeinSegmentsCBU(final ArrayList<String> feinSegments) {
		feinSegments.add(FINESEGMENT_PLATIN);
		feinSegments.add(FINESEGMENT_GOLD);
		feinSegments.add(FINESEGMENT_FIRST);
		feinSegments.add(FINESEGMENT_BUS);
		feinSegments.add(FINESEGMENT_NEW);
	}

	/**
	 * Add feinsegment PP to passed ArrayList
	 * @param feinSegments
	 */
	private static void addFeinSegmentPP(final ArrayList<String> feinSegments) {
		feinSegments.add(FINESEGMENT_PP);
	}

	/**
	 * Adds all existing grobSegments to the passed ArrayList
	 * @param grobSegments
	 */
	private static void addGrobSegmentsAll(final ArrayList<String> grobSegments) {
		grobSegments.add(SEGMENT_SME);
		grobSegments.add(SEGMENT_RES);
		grobSegments.add(SEGMENT_CBU);
	}

	/**
	 * Adds all existing subSegments to the passed Array
	 * @param subSegments
	 */
	private static void addSubSegmentsAll(final ArrayList<String> subSegments) {
		subSegments.add(SUBSEGMENT_GOLD);
		subSegments.add(SUBSEGMENT_PLATIN);
		subSegments.add(SUBSEGMENT_VIP);
		subSegments.add(SUBSEGMENT_CHILD);
		subSegments.add(SUBSEGMENT_TEENS);
		subSegments.add(SUBSEGMENT_TWENS);
		subSegments.add(SUBSEGMENT_YADULTS);
		subSegments.add(SUBSEGMENT_ICT);
		subSegments.add(SUBSEGMENT_COMDOT);
		subSegments.add(SUBSEGMENT_COM);
		subSegments.add(SUBSEGMENT_NAM);
		subSegments.add(SUBSEGMENT_KAT);
	}
	/**
	 * Adds all subSegments for RES grobsegment
	 * @param subSegments
	 */
	private static void addSubSegmentsRES(final ArrayList<String> subSegments) {
		subSegments.add(SUBSEGMENT_GOLD);
		subSegments.add(SUBSEGMENT_PLATIN);
		subSegments.add(SUBSEGMENT_VIP);
		subSegments.add(SUBSEGMENT_CHILD);
		subSegments.add(SUBSEGMENT_TEENS);
		subSegments.add(SUBSEGMENT_TWENS);
		subSegments.add(SUBSEGMENT_YADULTS);
	}

	/**
	 * Adds all subSegments for CBU grobsegment
	 * @param subSegments
	 */
	private static void addSubSegmentsCBU(final ArrayList<String> subSegments) {
		subSegments.add(SUBSEGMENT_NAM);
		subSegments.add(SUBSEGMENT_KAT);
	}

	/**
	 * Add subSegment VIP to the passed ArrayList
	 * @param subSegments
	 */
	private static void addSubSegmentVIP(final ArrayList<String> subSegments) {
		subSegments.add(SUBSEGMENT_VIP);
	}
	/**
	 * Add subSegment Platin to the passed ArrayList
	 * @param subSegments
	 */
	private static void addSubSegmentPlatin(final ArrayList<String> subSegments) {
		subSegments.add(SUBSEGMENT_PLATIN);
	}
	/**
	 * Adds RES grobSegment to the passed ArrayList
	 * @param grobSegments
	 */
	private static void addGrobSegmentRES(final ArrayList<String> grobSegments) {
		grobSegments.add(SEGMENT_RES);
	}
	/**
	 * Adds CBU grobSegment to the passed ArrayList
	 * @param grobSegments
	 */
	private static void addGrobSegmentCBU(final ArrayList<String> grobSegments) {
		grobSegments.add(SEGMENT_CBU);
	}

	/**
	 * Adds all businessnumbers to the ArrayList that belong to Variant A
	 * @param businessNumbers
	 */
	private static void addBusinessNumbersVariantA(final ArrayList<String> businessNumbers) {
		businessNumbers.add(BUSINESSNUMBER_0080088111213);
		businessNumbers.add(BUSINESSNUMBER_0800881112);
		businessNumbers.add(BUSINESSNUMBER_0800800800);
		businessNumbers.add(BUSINESSNUMBER_0800814814);
		businessNumbers.add(BUSINESSNUMBER_0800556464);
		businessNumbers.add(BUSINESSNUMBER_0622076090);
		businessNumbers.add(BUSINESSNUMBER_0622861212);
		businessNumbers.add(BUSINESSNUMBER_0622076059);
		businessNumbers.add(BUSINESSNUMBER_0812879046);
		businessNumbers.add(BUSINESSNUMBER_0800854854);
		businessNumbers.add(BUSINESSNUMBER_0800803175);
	}
	/**
	 * Adds all businessnumbers to the ArrayList that belong to Variant B
	 * @param businessNumbers
	 */
	private static void addBusinessNumbersVariantB(final ArrayList<String> businessNumbers) {
		businessNumbers.add(BUSINESSNUMBER_0800151728);
		businessNumbers.add(BUSINESSNUMBER_0812879952);
	}

	private static void addBusinessNumbersVariantC(final ArrayList<String> businessNumbers) {
		businessNumbers.add(BUSINESSNUMBER_0800656000);
		businessNumbers.add(BUSINESSNUMBER_0622076560);
	}

	private static void addBusinessNumbersVariantD(final ArrayList<String> businessNumbers) {
		businessNumbers.add(BUSINESSNUMBER_0800566566);
	}

	private static void addBusinessNumbersVariantF(final ArrayList<String> businessNumbers) {
		businessNumbers.add(BUSINESSNUMBER_0800850085);
	}

	private static void addBusinessNumbersVariantG(final ArrayList<String> businessNumbers) {
		businessNumbers.add(BUSINESSNUMBER_0800406080);
	}

	private static void addBusinessNumbersVariantH(final ArrayList<String> businessNumbers) {
		businessNumbers.add(BUSINESSNUMBER_0800888817);
	}

	public final void testID0006() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentVIP(subSegments);

		callProfile.put(CPK_CALLDETAIL, "oh_pers_res_premium_vip_tag");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40006", "9116", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0007() throws Exception {

		addProductsAllExceptTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40007", "9115", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0009() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40009", "9115", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0012() throws Exception {


		customerProducts.add(PR_FX_BLUEWIN_DSL);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		feinSegments.remove(FINESEGMENT_PP);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40012", "9014", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0013() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_CALLDETAIL, "oh_pers_res_premium_platin_tag");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_CBU_MENU_ERROR);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40013", "9115", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0014() throws Exception {

		addProductClusterABB(customerProductsList);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		feinSegments.remove(FINESEGMENT_PP);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);
		callProfile.setCallDetail("oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (Product product: customerProductsList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							customerProducts = new CustomerProducts();
							customerProducts.add(product);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("40014", "9057", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID0020() throws Exception {

		addProductClusterInternetOnlyIN(customerProductsList);

		addGrobSegmentRES(grobSegments);
		feinSegments.add(FINESEGMENT_PP);
		subSegments.add(SUBSEGMENT_GOLD);
		subSegments.add(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (Product product: customerProductsList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							customerProducts = new CustomerProducts();
							customerProducts.add(product);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("40020", "9015", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID0021() throws Exception {

		addProductsAllExceptTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		feinSegments.add(FINESEGMENT_PP);
		subSegments.add(SUBSEGMENT_GOLD);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40021", "9110", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0024() throws Exception {


		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentVIP(subSegments);

		callProfile.put(CPK_CALLDETAIL, "oh_pers_res_premium_vip_nacht");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40024", "9116", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0026() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_CALLDETAIL, "oh_pers_res_premium_platin_nacht");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40026", "9116", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0029() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		// Test 24x7 so both OH Check results
		custTypeList.add("oh_res_global_200");
		custTypeList.add("oh_res_global_100");

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800803175);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String callDetail: custTypeList) {

						callProfile.setCallDetail(callDetail);

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						countTestCases++;
						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, new CustomerProductClusters(), callProfile, serviceConfiguration);
						checkTransferRulesResult("40029", "9084", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0046() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterMobile(customerProducts);
		customerProducts.add(PR_BLUEWIN_TV);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");


		//		 Business Numbers List
		ArrayList<String> businessNumbers = new ArrayList<String>();
		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40046", "9115", transferRulesState);
					}
				}
			}
		}
	}	

	public final void testID0072() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40072", "9502", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0073() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentVIP(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40073", "9502", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0088() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		serviceConfiguration.put("vp.res.caller.MaxAge", "65");
		serviceConfiguration.put("vp.res.caller.BestAgeStart", "40");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						CustomerProfile customerProfile = new CustomerProfile();
						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("50");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40088", "9164", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0089() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterMobile(customerProducts);
		customerProducts.add(PR_BLUEWIN_TV);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		serviceConfiguration.put("vp.res.caller.MaxAge", "65");
		serviceConfiguration.put("vp.res.caller.BestAgeStart", "40");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("50");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40089", "9160", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0094() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_BUSINESSNUMBER,BUSINESSNUMBER_0800800832);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					countTestCases++;
					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40094", "9032", transferRulesState);
				}
			}
		}
	}

	public final void testID0095() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_BUSINESSNUMBER,BUSINESSNUMBER_0800800975);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40095", "9002", transferRulesState);
				}
			}
		}
	}

	public final void testID0105() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_BUSINESSNUMBER,BUSINESSNUMBER_0800820212);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40105", "9027", transferRulesState);
				}
			}
		}
	}

	public final void testID0125() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0848800175);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					countTestCases++;
					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40125", "9002", transferRulesState);
				}
			}
		}
	}

	public final void testID0126() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0848800811);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					countTestCases++;
					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40126", "9000", transferRulesState);
				}
			}
		}
	}

	public final void testID0136() throws Exception {

		countTestCases++;
		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("40136", "9000", transferRulesState);
	}

	public final void testID0141() throws Exception {

		customerProducts.add(PR_FX_BLUEWIN_DSL);

		addGrobSegmentRES(grobSegments);
		feinSegments.add(FINESEGMENT_PP);
		subSegments.add(SUBSEGMENT_GOLD);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU,CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40141", "9001", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0155() throws Exception {

		addProductClusterAllIP(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40155", "9168", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0162() throws Exception {

		addProductsAll(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		serviceConfiguration.put("vp.res.caller.MaxAge", "120");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("125");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40162", "9160", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0175() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,srbr_before_move");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40175", "9205", transferRulesState);
					}
				}
			}
		}
	}


	public final void testID0250() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"cbr_abu");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40250", "9153", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0320() throws Exception {

		addProductsAllExceptTV(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		feinSegments.add(FINESEGMENT_PP);
		subSegments.add(SUBSEGMENT_GOLD);
		subSegments.add(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40320", "9139", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0324() throws Exception {

		addProductsAll(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);


		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40324", "9138", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0327() throws Exception {

		customerProducts.add(PR_FIBRE_GENERIC);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU,CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40327", "9092", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0330() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,srbr_ftth_sf");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40330", "9167", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0388() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);


		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40388", "9002", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0446() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_200");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setComputerType(COMPUTERTYPE_PC);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40446", "9002", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0451() throws Exception {

		addProductClusterAllIP(customerProducts);	

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		subSegments.add(SUBSEGMENT_VIP);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40451", "9146", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0460() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_allip");
		callProfile.put(CPK_INTERNATIONAL, FALSE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40460", "9167", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0603() throws Exception {

		addProductsAllExceptTV(customerProducts);
		addProductClusterVoIP(customerProducts);
		customerProducts.add(PR_FIBRE_GENERIC);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU,CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40603", "9169", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3001() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43001", "9168", transferRulesState);
		}
	}

	public final void testID3002() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43002", "9000", transferRulesState);
		}
	}

	public final void testID3003() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43003", "9139", transferRulesState);
		}
	}

	public final void testID3004() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PASSWORD);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43004", "9139", transferRulesState);
		}
	}

	public final void testID3005() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_TV);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43005", "9141", transferRulesState);
		}
	}

	public final void testID3006() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		serviceConfiguration.put("vp.res.caller.MaxAge", "75");
		serviceConfiguration.put("vp.res.caller.BestAgeStart", "65");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("70");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43006", "9161", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3007() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU,CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU,CPV_MENUOPTION_PHONE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentsNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentsNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("26");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43007", "9183", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3011() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_MOBILE_INTERNET);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43011", "9561", transferRulesState);
		}
	}

	public final void testID3012() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_FESTNETZ_INTERNET);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43012", "9014", transferRulesState);
		}
	}

	public final void testID3013() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"srbr_order_onp");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43013", "0513", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3014() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,srbr_after_move");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43014", "9200", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3015() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"srbr_fio_bern");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43015", "9599", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3016() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"srbr_fio_zuerich");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43016", "9598", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3017() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"srbr_fio_lausanne");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43017", "9597", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3019() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		addBusinessNumbersVariantA(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_TV);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43019", "9010", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3020() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentPlatin(subSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800010742);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					countTestCases++;
					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, new CustomerProductClusters(), callProfile, serviceConfiguration);
					checkTransferRulesResult("43020", "9130", transferRulesState);
				}
			}
		}
	}

	public final void testID3021() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantB(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_TV);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43021", "9010", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3022_3042() throws Exception {

		Map<String, String> testConfig = new HashMap<String, String>();
		testConfig.put("43022", "0553");
		testConfig.put("43023", "0554");
		testConfig.put("43024", "0555");
		testConfig.put("43025", "0556");
		testConfig.put("43026", "0557");
		testConfig.put("43027", "0558");
		testConfig.put("43028", "0559");
		testConfig.put("43029", "0560");
		testConfig.put("43030", "0561");
		testConfig.put("43031", "0562");
		testConfig.put("43032", "0563");
		testConfig.put("43033", "0564");
		testConfig.put("43034", "0565");
		testConfig.put("43035", "0566");
		testConfig.put("43036", "0572");
		testConfig.put("43037", "0573");
		testConfig.put("43038", "0574");
		testConfig.put("43039", "0575");
		testConfig.put("43040", "0576");
		testConfig.put("43041", "0577");
		testConfig.put("43042", "0578");

		callProfile.setBusinessnumber(BUSINESSNUMBER_0800817112);
		callProfile.setCallDetail("cfstransfer");

		for (String rule: testConfig.keySet()) {

			String routingID = testConfig.get(rule);
			callProfile.setRoutingID(routingID);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult(rule, routingID, transferRulesState);
		}
	}

	public final void testID3031_Error_Default() throws Exception {

		callProfile.setBusinessnumber(BUSINESSNUMBER_0800817112);

		countTestCases++;

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("43031", "0562", transferRulesState);
	}

	public final void testID3043_3054() throws Exception {

		Map<String, String> testConfig = new HashMap<String, String>();
		testConfig.put("43043", "0549");
		testConfig.put("43044", "0550");
		testConfig.put("43045", "0551");
		testConfig.put("43046", "0552");
		testConfig.put("43047", "0545");
		testConfig.put("43048", "0546");
		testConfig.put("43049", "0547");
		testConfig.put("43050", "0548");
		testConfig.put("43051", "0541");
		testConfig.put("43052", "0542");
		testConfig.put("43053", "0543");
		testConfig.put("43054", "0544");

		callProfile.setBusinessnumber(BUSINESSNUMBER_0800817175);
		callProfile.setCallDetail("cfstransfer");

		for (String rule: testConfig.keySet()) {

			String routingID = testConfig.get(rule);
			callProfile.setRoutingID(routingID);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult(rule, routingID, transferRulesState);
		}
	}

	public final void testID3044_Error_Default() throws Exception {

		callProfile.setBusinessnumber(BUSINESSNUMBER_0800817175);

		countTestCases++;

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("43044", "0550", transferRulesState);
	}

	public final void testID3055() throws Exception {

		customerProducts.add(PR_MOBILE_N_STACK);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);
		addSubSegmentsAll(subSegments);

		addBusinessNumbersVariantA(businessNumbers);

		callProfile.put(CPK_CALLDETAIL, CPV_SSFC);

		miscList.add(CARS_TRANSFER_CCC);
		miscList.add(CARS_TRANSFER_DUNNING_BLOCKED);
		miscList.add(CARS_TRANSFER_DUNNING_LETTER_SMS);
		miscList.add(CARS_TRANSFER_CREDITLIMIT_BLOCKED);
		miscList.add(CARS_TRANSFER_BARRING_PREVENTION);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String miscNext: miscList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CARS_TRANSFER_TARGET, miscNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43055", "9047", transferRulesState);
						}
					}
				}
			}
		}
	}


	public final void testID3056() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER,BUSINESSNUMBER_0900333221);

		countTestCases++;
		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("43056", "9535", transferRulesState);
	}

	public final void testID3057() throws Exception {

		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NEWCUSTOMER);
		callProfile.put("newcustomerconfirm","yes");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43057", "9500", transferRulesState);
		}
	}

	public final void testID3058() throws Exception {

		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NEWCUSTOMER);
		callProfile.put("newcustomerconfirm","no");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43058", "9000", transferRulesState);
		}
	}

	public final void testID3060() throws Exception {

		customerProducts.add(PR_MOBILE_N_STACK);
		customerProfile.setSegment(SEGMENT_RES);
		callProfile.setConnectionType(CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43060", "9009", transferRulesState);
		}
	}

	public final void testID3061() throws Exception {

		customerProducts.add(PR_MOBILE_N_STACK);
		customerProfile.setSegment(SEGMENT_RES);
		callProfile.setConnectionType(CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43061", "9009", transferRulesState);
		}
	}

	public final void testID3062() throws Exception {

		customerProducts.add(PR_MOBILE_N_STACK);
		customerProfile.setSegment(SEGMENT_RES);
		callProfile.setConnectionType(CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43062", "9043", transferRulesState);
		}
	}

	public final void testID3063() throws Exception {

		customerProducts.add(PR_MOBILE_N_STACK);
		customerProfile.setSegment(SEGMENT_RES);
		callProfile.setConnectionType(CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_ERROR);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43063", "9009", transferRulesState);
		}
	}

	public final void testID3064() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800851);
		callProfile.put("oneIVRresAnliegenMenuOnlineShop","TVA");

		countTestCases++;

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("43064", "9000", transferRulesState);
	}

	public final void testID3065() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800851);
		callProfile.put("oneIVRresAnliegenMenuOnlineShop","AA");

		countTestCases++;

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("43065", "9007", transferRulesState);
	}
	
	public final void testID3066() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800851);
		callProfile.put("oneIVRresAnliegenMenuOnlineShop", CPV_MENUOPTION_ERROR);

		countTestCases++;

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("43066", "9007", transferRulesState);
	}
	
	public final void testID3067() throws Exception {

		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_EASY);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43067", "9503", transferRulesState);
		}
	}

	public final void testID3491() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);
		callProfile.put(CPK_CUSTOMERTYPE, CPV_CUSTOMERTYPE_NONUMBER);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43491", "9138", transferRulesState);
		}
	}

	public final void testID3499() throws Exception {

		addProductsAll(customerProducts);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800803175);

		customerProfile.setSegment(SEGMENT_SME);

		countTestCases++;
		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, new CustomerProductClusters(), callProfile, serviceConfiguration);
		checkTransferRulesResult("43499", "8092", transferRulesState);
	}

	public final void testID3500() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43500", "OH_RULEID_RES_NPWLineError", transferRulesState);
			}
		}
	}

	public final void testID3501() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1_TRANSFER);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43501", "OH_RULEID_RES_NPWLineAlm1", transferRulesState);
			}
		}
	}

	public final void testID3502() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM1);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43502", "OH_RULEID_RES_NPWLineAlm1Prm1", transferRulesState);
			}
		}
	}

	public final void testID3503() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM2);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43503", "OH_RULEID_RES_NPWLineAlm1Prm2", transferRulesState);
			}
		}
	}
	public final void testID3504() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM3);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43504", "OH_RULEID_RES_NPWLineAlm1Prm3", transferRulesState);
			}
		}
	}
	public final void testID3505() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM4);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43505", "OH_RULEID_RES_NPWLineAlm1Prm4", transferRulesState);
			}
		}
	}

	public final void testID3506() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM5);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43506", "OH_RULEID_RES_NPWLineAlm1Prm5", transferRulesState);
			}
		}
	}
	public final void testID3507() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43507", "OH_RULEID_RES_NPWLineAlm1Error", transferRulesState);
			}
		}
	}
	public final void testID3508() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2_TRANSFER);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43508", "OH_RULEID_RES_NPWLineAlm2", transferRulesState);
			}
		}
	}

	public final void testID3509() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM1);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43509", "OH_RULEID_RES_NPWLineAlm2Prm1", transferRulesState);
			}
		}
	}
	public final void testID3510() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM2);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43510", "OH_RULEID_RES_NPWLineAlm2Prm2", transferRulesState);
			}
		}
	}
	public final void testID3511() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM3);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43511", "OH_RULEID_RES_NPWLineAlm2Prm3", transferRulesState);
			}
		}
	}

	public final void testID3512() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM4);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43512", "OH_RULEID_RES_NPWLineAlm2Prm4", transferRulesState);
			}
		}
	}
	public final void testID3513() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM5);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43513", "OH_RULEID_RES_NPWLineAlm2Prm5", transferRulesState);
			}
		}
	}
	public final void testID3514() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43514", "OH_RULEID_RES_NPWLineAlm2Error", transferRulesState);
			}
		}
	}
	public final void testID3515() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3_TRANSFER);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43515", "OH_RULEID_RES_NPWLineAlm3", transferRulesState);
			}
		}
	}

	public final void testID3516() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM1);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43516", "OH_RULEID_RES_NPWLineAlm3Prm1", transferRulesState);
			}
		}
	}
	public final void testID3517() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM2);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;
				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43517", "OH_RULEID_RES_NPWLineAlm3Prm2", transferRulesState);
			}
		}
	}
	public final void testID3518() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM3);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43518", "OH_RULEID_RES_NPWLineAlm3Prm3", transferRulesState);
			}
		}
	}
	public final void testID3519() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM4);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43519", "OH_RULEID_RES_NPWLineAlm3Prm4", transferRulesState);
			}
		}
	}

	public final void testID3520() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM5);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43520", "OH_RULEID_RES_NPWLineAlm3Prm5", transferRulesState);
			}
		}
	}
	public final void testID3521() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43521", "OH_RULEID_RES_NPWLineAlm3Error", transferRulesState);
			}
		}
	}
	public final void testID3522() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4_TRANSFER);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43522", "OH_RULEID_RES_NPWLineAlm4", transferRulesState);
			}
		}
	}

	public final void testID3523() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM1);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43523", "OH_RULEID_RES_NPWLineAlm4Prm1", transferRulesState);
			}
		}
	}
	public final void testID3524() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM2);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43524", "OH_RULEID_RES_NPWLineAlm4Prm2", transferRulesState);
			}
		}
	}
	public final void testID3525() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM3);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43525", "OH_RULEID_RES_NPWLineAlm4Prm3", transferRulesState);
			}
		}
	}
	public final void testID3526() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM4);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43526", "OH_RULEID_RES_NPWLineAlm4Prm4", transferRulesState);
			}
		}
	}
	public final void testID3527() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM5);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43527", "OH_RULEID_RES_NPWLineAlm4Prm5", transferRulesState);
			}
		}
	}
	public final void testID3528() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43528", "OH_RULEID_RES_NPWLineAlm4Error", transferRulesState);
			}
		}
	}
	public final void testID3529() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_ERROR);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43529", "OH_RULEID_RES_NPWLessError", transferRulesState);
				}
			}
		}
	}

	public final void testID3530() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1_TRANSFER);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43530", "OH_RULEID_RES_NPWLessAlm1", transferRulesState);
				}
			}
		}
	}

	public final void testID3531() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM1);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43531", "OH_RULEID_RES_NPWLessAlm1Prm1", transferRulesState);
				}
			}
		}
	}
	public final void testID3532() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM2);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43532", "OH_RULEID_RES_NPWLessAlm1Prm2", transferRulesState);
				}
			}
		}
	}
	public final void testID3533() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM3);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43533", "OH_RULEID_RES_NPWLessAlm1Prm3", transferRulesState);
				}
			}
		}
	}
	public final void testID3534() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM4);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43534", "OH_RULEID_RES_NPWLessAlm1Prm4", transferRulesState);
				}
			}
		}
	}
	public final void testID3535() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM1_PRM5);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43535", "OH_RULEID_RES_NPWLessAlm1Prm5", transferRulesState);
				}
			}
		}
	}
	public final void testID3536() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43536", "OH_RULEID_RES_NPWLessAlm1Error", transferRulesState);
				}
			}
		}
	}
	public final void testID3537() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2_TRANSFER);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43537", "OH_RULEID_RES_NPWLessAlm2", transferRulesState);
				}
			}
		}
	}

	public final void testID3538() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM1);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43538", "OH_RULEID_RES_NPWLessAlm2Prm1", transferRulesState);
				}
			}
		}
	}
	public final void testID3539() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM2);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43539", "OH_RULEID_RES_NPWLessAlm2Prm2", transferRulesState);
				}
			}
		}
	}
	public final void testID3540() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM3);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43540", "OH_RULEID_RES_NPWLessAlm2Prm3", transferRulesState);
				}
			}
		}
	}
	public final void testID3541() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM4);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43541", "OH_RULEID_RES_NPWLessAlm2Prm4", transferRulesState);
				}
			}
		}
	}
	public final void testID3542() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM2_PRM5);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43542", "OH_RULEID_RES_NPWLessAlm2Prm5", transferRulesState);
				}
			}
		}
	}
	public final void testID3543() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsAll(feinSegments);
		addGrobSegmentCBU(grobSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43543", "OH_RULEID_RES_NPWLessAlm2Error", transferRulesState);
				}
			}
		}
	}
	public final void testID3544() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3_TRANSFER);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43544", "OH_RULEID_RES_NPWLessAlm3", transferRulesState);
				}
			}
		}
	}

	public final void testID3545() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM1);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43545", "OH_RULEID_RES_NPWLessAlm3Prm1", transferRulesState);
				}
			}
		}
	}
	public final void testID3546() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM2);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43546", "OH_RULEID_RES_NPWLessAlm3Prm2", transferRulesState);
				}
			}
		}
	}
	public final void testID3547() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM3);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43547", "OH_RULEID_RES_NPWLessAlm3Prm3", transferRulesState);
				}
			}
		}
	}
	public final void testID3548() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM4);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43548", "OH_RULEID_RES_NPWLessAlm3Prm4", transferRulesState);
				}
			}
		}
	}
	public final void testID3549() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM3_PRM5);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43549", "OH_RULEID_RES_NPWLessAlm3Prm5", transferRulesState);
				}
			}
		}
	}
	public final void testID3550() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43550", "OH_RULEID_RES_NPWLessAlm3Error", transferRulesState);
				}
			}
		}
	}
	public final void testID3551() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4_TRANSFER);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43551", "OH_RULEID_RES_NPWLessAlm4", transferRulesState);
				}
			}
		}
	}

	public final void testID3552() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM1);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43552", "OH_RULEID_RES_NPWLessAlm4Prm1", transferRulesState);
				}
			}
		}
	}
	public final void testID3553() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM2);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43553", "OH_RULEID_RES_NPWLessAlm4Prm2", transferRulesState);
				}
			}
		}
	}
	public final void testID3554() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM3);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43554", "OH_RULEID_RES_NPWLessAlm4Prm3", transferRulesState);
				}
			}
		}
	}
	public final void testID3555() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM4);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43555", "OH_RULEID_RES_NPWLessAlm4Prm4", transferRulesState);
				}
			}
		}
	}
	public final void testID3556() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_NP_ALM4_PRM5);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43556", "OH_RULEID_RES_NPWLessAlm4Prm5", transferRulesState);
				}
			}
		}
	}
	public final void testID3557() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800800800);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(CPK_ONEIVRRES_NEWPRODUCTMENU_PRM, CPV_MENUOPTION_ERROR);

		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_WIRELESS);
		menuSelectionsMap.put(CPK_NEW_PRODUCT_SELECTION, CPV_AUSLAND);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

					countTestCases++;

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("43557", "OH_RULEID_RES_NPWLessAlm4Error", transferRulesState);
				}
			}
		}
	}

	public final void testID3701() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsCBU(feinSegments);
		addSubSegmentsCBU(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_INTERNATIONAL, FALSE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43701", "7557", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3702() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		serviceConfiguration.put("vp.res.caller.MaxAge", "75");
		serviceConfiguration.put("vp.res.caller.BestAgeStart", "65");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("70");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43702", "9165", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3703() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);
		subSegments.remove(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		serviceConfiguration.put("vp.res.caller.MaxAge", "75");
		serviceConfiguration.put("vp.res.caller.BestAgeStart", "65");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("70");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43703", "9166", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3704() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsCBU(feinSegments);
		addSubSegmentsCBU(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_INTERNATIONAL, FALSE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43704", "9561", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3706() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"cbr_disp");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43706", "9089", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3708() throws Exception {

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_200");

		addBusinessNumbersVariantA(businessNumbers);
		businessNumbers.remove(BUSINESSNUMBER_0800803175);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43708", "9002", transferRulesState);
		}
	}

	public final void testID3710() throws Exception {

		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsCBU(feinSegments);
		addSubSegmentsCBU(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU,"TRANSFERALM");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43710", "7549", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3718() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"srbr_infinity");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43718", "9013", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3721() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		feinSegments.remove(FINESEGMENT_PP);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43721", "9561", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3723() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL, CPV_SSFC);
		callProfile.put(CPK_CARS_TRANSFER_TARGET, CARS_TRANSFER_DUNNING_BLOCKED);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43723", "9013", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3724() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL, CPV_SSFC);
		callProfile.put(CPK_CARS_TRANSFER_TARGET, CARS_TRANSFER_DUNNING_LETTER_SMS);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43724", "9033", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3725() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL, CPV_SSFC);
		callProfile.put(CPK_CARS_TRANSFER_TARGET, CARS_TRANSFER_CREDITLIMIT_BLOCKED);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43725", "9034", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3726() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL, CPV_SSFC);
		callProfile.put(CPK_CARS_TRANSFER_TARGET, CARS_TRANSFER_BARRING_PREVENTION);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43726", "9035", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3728() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		customerProducts.add(PR_BLUEWIN_PHONE);
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43728", "9500", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3733() throws Exception {

		addProductClusterInternetOnlyIN(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43733", "9561", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3735() throws Exception {

		addProductClusterInternetOnlyIN(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_VIP);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_200");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43735", "9561", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3737() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentVIP(subSegments);

		callProfile.put(CPK_CALLDETAIL, ",oh_res_global_100,oh_pers_res_premium_vip_tag");
		callProfile.put(CPK_INTERNATIONAL, TRUE);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43737", "9116", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3738() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentPP(feinSegments);
		addSubSegmentVIP(subSegments);

		callProfile.put(CPK_CALLDETAIL, "oh_res_global_201,oh_pers_res_premium_vip_nacht");
		callProfile.put(CPK_INTERNATIONAL, TRUE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43738", "9116", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3743() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");
		callProfile.put(CPK_INTERNATIONAL, TRUE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_sa");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43743", "9003", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3748() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_INTERNATIONAL, TRUE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_dsla");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43748", "9015", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3753() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_INTERNATIONAL, TRUE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_abu");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43753", "9153", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3755() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_INTERNATIONAL, TRUE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_allip");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43755", "9167", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3758() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsCBU(feinSegments);
		addSubSegmentsCBU(subSegments);

		callProfile.put(CPK_INTERNATIONAL, TRUE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43758", "9556", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3762() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);
		addSubSegmentsAll(subSegments);

		callProfile.put(CPK_INTERNATIONAL, TRUE);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_200");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43762", "9500", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3778() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		addBusinessNumbersVariantA(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_MIGROS_PRE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43778", "9505", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3779() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		addBusinessNumbersVariantA(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_MIGROS_PP);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43779", "9507", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3780() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		addBusinessNumbersVariantA(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_INPH);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43780", "9508", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3781() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		addBusinessNumbersVariantA(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_MIGROS_IN);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43781", "9505", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3784() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		addBusinessNumbersVariantA(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43784", "9505", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3787() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantB(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_MIGROS_PRE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43787", "9505", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3788() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantB(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_MIGROS_PP);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43788", "9507", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3789() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantB(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_INPH);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43789", "9508", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3790() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantB(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_MIGROS_IN);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43790", "9505", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3793() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantB(businessNumbers);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_MIGROS_MO);
		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43793", "9505", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3796() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_ERROR);

		addBusinessNumbersVariantB(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43796", "9505", transferRulesState);
						}
					}
				}
			}
		}
	}	

	public final void testID3821() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL, CPV_SSFC);
		callProfile.put(CPK_CARS_TRANSFER_TARGET, CARS_TRANSFER_CCC);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43821", "9500", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3823() throws Exception {

		addProductsAll(customerProducts);
		addProductClusterAllIP(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL, CPV_SSFC);

		addBusinessNumbersVariantA(businessNumbers);

		miscList.add(CARS_TRANSFER_CCC);
		miscList.add(CARS_TRANSFER_DUNNING_BLOCKED);
		miscList.add(CARS_TRANSFER_DUNNING_LETTER_SMS);
		miscList.add(CARS_TRANSFER_CREDITLIMIT_BLOCKED);
		miscList.add(CARS_TRANSFER_BARRING_PREVENTION);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String miscNext: miscList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CARS_TRANSFER_TARGET, miscNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43823", "9092", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3825() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_TASMENU, CPV_MENUOPTION_ACCESSDSL);

		addBusinessNumbersVariantG(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43825", "9005", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3826() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_TASMENU, CPV_MENUOPTION_PHONE);

		addBusinessNumbersVariantG(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43826", "9037", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3827() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_TASMENU, CPV_MENUOPTION_TV);

		addBusinessNumbersVariantG(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43827", "9036", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3828() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		menuSelectionsMap.put(CPK_ONEIVRRES_TASMENU_LEVEL2, "RES");
		menuSelectionsMap.put(CPK_ONEIVRRES_TASMENU_LEVEL2, CPV_MENUOPTION_ERROR);

		callProfile.put(CPK_ONEIVRRES_TASMENU, CPV_MENUOPTION_ALLIPFTTH);

		addBusinessNumbersVariantG(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43828", "9170", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3829() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantH(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43829", "0513", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3830() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_TASMENU, CPV_MENUOPTION_ALLIPFTTH);
		callProfile.put(CPK_ONEIVRRES_TASMENU_LEVEL2, "SME");
		callProfile.put(CPK_ONEIVRRES_TASMENU_LEVEL3, "SMEACCESS");

		addBusinessNumbersVariantG(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43830", "9170", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3831() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		menuSelectionsMap.put(CPK_ONEIVRRES_TASMENU_LEVEL3, "SERVICE");
		menuSelectionsMap.put(CPK_ONEIVRRES_TASMENU_LEVEL3, CPV_MENUOPTION_ERROR);

		callProfile.put(CPK_ONEIVRRES_TASMENU, CPV_MENUOPTION_ALLIPFTTH);
		callProfile.put(CPK_ONEIVRRES_TASMENU_LEVEL2, "SME");

		addBusinessNumbersVariantG(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (Map.Entry menuSelectionEntry: menuSelectionsMap.entrySet()) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put((String)menuSelectionEntry.getKey(), (String)menuSelectionEntry.getValue());

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43831", "8028", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3848() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL,"cbr_res_wless");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43848", "9530", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3865() throws Exception {

		addGrobSegmentRES(grobSegments);
		feinSegments.add(FINESEGMENT_PP);
		subSegments.add(SUBSEGMENT_GOLD);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43865", "9003", transferRulesState);
					}
				}
			}
		}
	}	

	public final void testID3866() throws Exception {

		addProductClusterFixnet(customerProducts);
		addProductClusterABB(customerProducts);
		addProductClusterInternetOnlyIN(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterVoIP(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43866", "9138", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3867() throws Exception {

		addProductClusterAllIP(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43867", "9145", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3868() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		feinSegments.add(FINESEGMENT_PP);
		subSegments.add(SUBSEGMENT_GOLD);
		subSegments.add(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_TV);

		addBusinessNumbersVariantA(businessNumbers);

		connTypeList.add(CPV_WIRELINE);
		connTypeList.add(CPV_WIRELESS);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String connTypeNext: connTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CONNECTIONTYPE, connTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43868", "9114", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3869() throws Exception {

		addProductsAllExceptTV(customerProducts);
		customerProducts.add(PR_BLUEWIN_TV);
		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_TV);

		addBusinessNumbersVariantA(businessNumbers);

		connTypeList.add(CPV_WIRELINE);
		connTypeList.add(CPV_WIRELESS);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String connTypeNext: connTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CONNECTIONTYPE, connTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43869", "9141", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3870() throws Exception {

		addProductClusterAllIP(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_TV);

		addBusinessNumbersVariantA(businessNumbers);

		connTypeList.add(CPV_WIRELINE);
		connTypeList.add(CPV_WIRELESS);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String connTypeNext: connTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CONNECTIONTYPE, connTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43870", "9147", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3871() throws Exception {

		addProductClusterAllIP(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PASSWORD);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43871", "9146", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3877() throws Exception {

		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PASSWORD);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43877", "9139", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3915() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		custTypeList.add(CPV_CUSTOMERTYPE_MBU);
		custTypeList.add(CPV_CUSTOMERTYPE_MMO);

		callProfile.put(CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU, CPV_MENUOPTION_ERROR);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						for (String custTypeNext: custTypeList) {

							customerProfile.setSegment(grobSegmentsNext);
							customerProfile.setFineSegment(feinSegmentNext);
							customerProfile.setSubSegment(subSegmentsNext);

							callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
							callProfile.put(CPK_CUSTOMERTYPE, custTypeNext);

							countTestCases++;

							transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
									customerProducts, customerProductClusters, callProfile, serviceConfiguration);
							checkTransferRulesResult("43915", "9505", transferRulesState);
						}
					}
				}
			}
		}
	}

	public final void testID3935() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantC(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43935", "9528", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3936() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		addBusinessNumbersVariantD(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43936", "6502", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3938() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_THLMENU, CPV_MENUOPTION_THL1);

		addBusinessNumbersVariantF(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43938", "6502", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3939() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_THLMENU, CPV_MENUOPTION_THL2);
		addBusinessNumbersVariantF(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43939", "6501", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3940() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_THLMENU, CPV_MENUOPTION_ERROR);

		addBusinessNumbersVariantF(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43940", "6502", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3942() throws Exception {

		addProductClusterInternet_NoDuplo(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		feinSegments.remove(FINESEGMENT_PP);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_200");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setComputerType(COMPUTERTYPE_PC);
						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43942", "9561", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3943() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsCBU(feinSegments);
		addSubSegmentsCBU(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43943", "9120", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3944() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43944", "9000", transferRulesState);
		}
	}

	public final void testID3945() throws Exception {

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43945", "9002", transferRulesState);
		}
	}

	public final void testID3946() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_INTERNET);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43946", "9014", transferRulesState);
		}
	}

	public final void testID3948() throws Exception {

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PASSWORD);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43948", "9014", transferRulesState);
		}
	}

	public final void testID3949() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43949", "9006", transferRulesState);
		}
	}

	public final void testID3950() throws Exception {

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43950", "9000", transferRulesState);
		}
	}

	public final void testID3955() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_ERROR);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43955", "9500", transferRulesState);
		}
	}

	public final void testID3956() throws Exception {

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_PHONE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43956", "9561", transferRulesState);
		}
	}

	public final void testID3958() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43958", "9531", transferRulesState);
		}
	}

	public final void testID3959() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43959", "9500", transferRulesState);
		}
	}

	public final void testID3963() throws Exception {

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_TV);

		addBusinessNumbersVariantA(businessNumbers);

		connTypeList.add(CPV_WIRELINE);
		connTypeList.add(CPV_WIRELESS);

		for (String businessNumbersNext: businessNumbers) {
			for (String connTypeNext: connTypeList) {

				callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);
				callProfile.put(CPK_CONNECTIONTYPE, connTypeNext);

				countTestCases++;

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("43963", "9041", transferRulesState);
			}
		}
	}

	public final void testID3966() throws Exception {

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_MOBILE_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						// Skip RES PP Gold and RES PP Platin
						if (grobSegmentsNext==SEGMENT_RES) {
							if (feinSegmentNext==FINESEGMENT_PP) {
								if (subSegmentsNext==SUBSEGMENT_GOLD || subSegmentsNext==SUBSEGMENT_PLATIN) {
									continue;
								}
							}
						}
						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43966", "9561", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3968() throws Exception {

		customerProducts.add(PR_FX_BLUEWIN_DSL);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsAll(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_FESTNETZ_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						// Skip RES PP Gold and RES PP Platin
						if (grobSegmentsNext==SEGMENT_RES) {
							if (feinSegmentNext==FINESEGMENT_PP) {
								if (subSegmentsNext==SUBSEGMENT_GOLD || subSegmentsNext==SUBSEGMENT_PLATIN) {
									continue;
								}
							}
						}
						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43968", "9014", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3969() throws Exception {

		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_FESTNETZ_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {
						// Skip RES PP Gold and RES PP Platin
						if (grobSegmentsNext==SEGMENT_RES) {
							if (feinSegmentNext==FINESEGMENT_PP) {
								if (subSegmentsNext==SUBSEGMENT_GOLD || subSegmentsNext==SUBSEGMENT_PLATIN) {
									continue;
								}
							}
						}
						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43969", "9057", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3971() throws Exception {

		customerProducts.add(PR_FX_BLUEWIN_DSL);

		addGrobSegmentRES(grobSegments);
		feinSegments.add(FINESEGMENT_PP);
		subSegments.add(SUBSEGMENT_GOLD);
		subSegments.add(SUBSEGMENT_PLATIN);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_FESTNETZ_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43971", "9015", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3973() throws Exception {

		customerProducts.add(PR_FX_BLUEWIN_DSL_FTTH);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_FESTNETZ_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		ArrayList<String> businessNumbers = new ArrayList<String>();
		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43973", "9139", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3975() throws Exception {

		addProductClusterAllIP(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_FESTNETZ_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43975", "9146", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3977() throws Exception {

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_IDENTIFIEDBYANI, "true");
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_STOERUNG);
		callProfile.put(CPK_ONEIVRRES_PRODUKTMENU, CPV_MENUOPTION_FESTNETZ_INTERNET);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String businessNumbersNext: businessNumbers) {

			callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

			countTestCases++;

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("43977", "9014", transferRulesState);
		}
	}

	public final void testID3978() throws Exception {


		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_dsl_tech");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43978", "9014", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3981() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_dslsa");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43981", "9014", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3983() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100,cbr_achsa");
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43983", "9002", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3984() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		feinSegments.remove(FINESEGMENT_PP);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_GOLD);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU,CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentsNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentsNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("26");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43984", "9180", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3988() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		feinSegments.remove(FINESEGMENT_PP);
		addSubSegmentsRES(subSegments);
		subSegments.remove(SUBSEGMENT_GOLD);

		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);
		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU,CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CALLDETAIL,"oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentsNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentsNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("26");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43988", "9184", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3989() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_KUNDENDIENST);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments ) {
			for (String feinSegmentsNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentsNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("26");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43989", "9185", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3990() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);

		callProfile.put(CPK_ONEIVRRES_ANLIEGENMENU, CPV_MENUOPTION_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);
		callProfile.put(CPK_CALLDETAIL, "oh_res_global_100");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments ) {
			for (String feinSegmentsNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentsNext);
						customerProfile.setSubSegment(subSegmentsNext);
						customerProfile.setAge("26");

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43990", "9186", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3998() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		callProfile.put(CPK_CALLDETAIL,"srbr_quality_support_admin");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43998", "9050", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID3999() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentRES(grobSegments);
		addFeinSegmentsRES(feinSegments);
		addSubSegmentsRES(subSegments);
		callProfile.put(CPK_CALLDETAIL,"srbr_quality_supporter_tech");

		addBusinessNumbersVariantA(businessNumbers);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					for (String businessNumbersNext: businessNumbers) {

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						callProfile.put(CPK_BUSINESSNUMBER, businessNumbersNext);

						countTestCases++;

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("43999", "9053", transferRulesState);
					}
				}
			}
		}
	}

}
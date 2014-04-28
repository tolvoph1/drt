/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: SMERulesTransferTest.java 221 2014-03-19 09:00:54Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-19 10:00:54 +0100 (Wed, 19 Mar 2014) $
 * $Revision: 221 $
 * 
 * JUnit Test Class to test the transfer rules
 * There is one test case per cell in the qualification tab of the Excel sheet
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.transfer;

import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
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
 *  @version $Revision: 221 $ ($Date: 2014-03-19 10:00:54 +0100 (Wed, 19 Mar 2014) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SMERulesTransferTest extends TestCase {

	private static final String LOG4J_PATH = USER_DIR + "/src/test/conf/log4j.xml";

	private static RulesServiceDroolsImpl droolsImpl;
	private static final boolean LOGGING_ON = false;

	private static final String TRANSFERRULES = "sme/vp5smeTransfer";
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
	private static ArrayList<String> menus;
	private static ArrayList<Product> products;
	private static ArrayList<String> miscList;
	private static ArrayList<String> businessNumbers;

	static {
		if (LOGGING_ON)
			DOMConfigurator.configure(LOG4J_PATH);
		else
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
		junit.textui.TestRunner.run(SMERulesTransferTest.class);
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
		callProfile.put(CPK_BUSINESSTYP, SME);
		menus = new ArrayList<String>();
		products = new ArrayList<Product>();
		miscList = new ArrayList<String>();
		businessNumbers = new ArrayList<String>();
	}
	protected void tearDown() {

	}

	private static void addProductClusterFixnet(final CustomerProducts customerProducts) {
		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		customerProducts.add(PR_BUSINESS_CONNECT_IMS);
	}

	private static void addProductClusterMobile(final CustomerProducts customerProducts) {
		customerProducts.add(PR_DSL_MOBILE);
		customerProducts.add(PR_MOBILE76);
		customerProducts.add(PR_MOBILE77);
		customerProducts.add(PR_MOBILE78);
		customerProducts.add(PR_MOBILE79);
	}

	private static void addProductClusterInternet(final CustomerProducts customerProducts) {
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_PROFESSIONELL);
		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);
		customerProducts.add(PR_IP_PLUS_BUI);
	}

	private static void addProductClusterITHosting(final CustomerProducts customerProducts) {
		customerProducts.add(PR_VPN_PROFESSIONELL);
		customerProducts.add(PR_HOSTED_EXCHANGE_PROFESSIONELL);
		customerProducts.add(PR_HOSTED_EXCHANGE_PROFESSIONAL_2);
		customerProducts.add(PR_ONLINE_BACKUP_PROFESSIONAL);
		customerProducts.add(PR_SHARED_OFFICE_PROFESSIONAL);
	}


	/**
	 * Adds complete PBX product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterPBX(final CustomerProducts customerProducts) {
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);
		customerProducts.add(PR_BUSINESSCONNECT_IPC);
	}


	/**
	 * Adds complete TV product cluster to the passed customerProducts object
	 * @param customerProducts
	 */
	private static void addProductClusterTV(final CustomerProducts customerProducts) {
		customerProducts.add(PR_BLUEWIN_TV);
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
		addProductClusterInternet(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);
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
	 * Add possible empty feinsegments to passed ArrayList
	 * @param feinSegments
	 */
	private static void addFeinSegmentEmpty(ArrayList<String> feinSegments) {
		feinSegments.add("");
		feinSegments.add(null);
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
	 * Adds SME grobSegment to the passed ArrayList
	 * @param grobSegments
	 */
	private static void addGrobSegmentSME(final ArrayList<String> grobSegments) {
		grobSegments.add(SEGMENT_SME);
	}
	/**
	 * Adds CBU grobSegment to the passed ArrayList
	 * @param grobSegments
	 */
	private static void addGrobSegmentCBU(final ArrayList<String> grobSegments) {
		grobSegments.add(SEGMENT_CBU);
	}
	/**
	 * Adds possible 'empty' grobSegment values to the passed ArrayList
	 * @param grobSegments
	 */
	private static void addGrobSegmentEmpty(final ArrayList<String> grobSegments) {
		grobSegments.add("");
		grobSegments.add(null);
	}	

	public final void testID0071() throws Exception {

		addProductClusterInternet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);


		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40071", "8801", transferRulesState);
			}
		}
	}

	public final void testID0075() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40075", "8804", transferRulesState);
			}
		}
	}

	public final void testID0079() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_RECHNUNG);
		callProfile.put(CPK_CALLDETAIL, "nothing");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40079", "8806", transferRulesState);
			}
		}
	}

	public final void testID0225() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0443069500);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40225", "6005", transferRulesState);
			}
		}
	}

	public final void testID0236() throws Exception {

		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		addProductClusterInternet(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_FESTNETZ);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);


		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40236", "8000", transferRulesState);
			}
		}
	}

	public final void testID0238() throws Exception {

		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		addProductClusterInternet(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);
		callProfile.put(SME_PRODUKTMENU, CPV_FESTNETZ);


		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {

				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40238", "8032", transferRulesState);
			}
		}
	}	

	public final void testID0240() throws Exception {

		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_PROFESSIONELL);
		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40240", "8004", transferRulesState);
			}
		}
	}

	public final void testID0242() throws Exception {

		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_PROFESSIONELL);
		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);
		customerProducts.add(PR_IP_PLUS_BUI);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		grobSegments.add("");
		grobSegments.add(null);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_VERMITTLUNGSANLAGE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40242", "8007", transferRulesState);
			}
		}
	}

	public final void testID0247() throws Exception {

		addProductClusterFixnet(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_PROFESSIONELL);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				TransferRulesState transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40247", "8016", transferRulesState);
			}
		}
	}

	public final void testID0252() throws Exception {

		customerProducts.add(PR_BUSINESS_INTERNET_LIGHT);

		addGrobSegmentCBU(grobSegments);
		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext); 
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40252", "8020", transferRulesState);
				}
			}
		}
	}

	public final void testID0253() throws Exception {

		customerProducts.add(PR_BUSINESS_INTERNET_LIGHT);

		addGrobSegmentCBU(grobSegments);
		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40253", "8018", transferRulesState);
			}
		}
	}

	public final void testID0256_Fallback() throws Exception{

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("40256", "8800", transferRulesState);
	}

	public final void testID0257() throws Exception {

		customerProfile.setSegment(SEGMENT_RES);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("40257", "9000", transferRulesState);
	}

	public final void testID0263() throws Exception {

		addProductsAll(customerProducts);
		customerProducts.add(PR_BUSINESSCONNECT_IPC);
		customerProducts.add(PR_BUSINESS_CONNECT_IMS);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40263", "8073", transferRulesState);
			}
		}
	}

	public final void testID0270() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_MOBILE);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40270", "8500", transferRulesState);
			}
		}
	}

	public final void testID0275() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_BUSINESSTYP, SME);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);
		callProfile.put(SME_PRODUKTMENU, CPV_MOBILE);


		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40275", "8509", transferRulesState);
			}
		}
	}

	public final void testID0281() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40281", "8000", transferRulesState);
			}
		}
	}

	public final void testID0289() throws Exception {

		customerProducts.add(PR_VPN_PROFESSIONELL);
		customerProducts.add(PR_BUSINESS_INTERNET_STANDARD);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40289", "8025", transferRulesState);
				}
			}
		}
	}

	public final void testID0303() throws Exception {

		addProductsAll(customerProducts);
		customerProducts.add(PR_BUSINESSCONNECT_IPC);
		customerProducts.add(PR_BUSINESS_CONNECT_IMS);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_RECHNUNG);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40303", "8073", transferRulesState);
			}
		}
	}

	public final void testID0304() throws Exception {

		products.add(PR_BUSINESSCONNECT_IPC);
		products.add(PR_BUSINESS_CONNECT_IMS);
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		grobSegments.add("");
		grobSegments.add(null);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);
		callProfile.put(SME_PRODUKTMENU, CPV_FESTNETZ);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (Product productsNext: products) {

					customerProducts.add(productsNext);
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40304", "8065", transferRulesState);
				}
			}
		}
	}

	public final void testID0306() throws Exception {

		addProductsAll(customerProducts);
		customerProducts.add(PR_BUSINESSCONNECT_IPC);
		customerProducts.add(PR_BUSINESS_CONNECT_IMS);

		addGrobSegmentSME(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_VERMITTLUNGSANLAGE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40306", "8073", transferRulesState);
			}
		}
	}

	public final void testID0313() throws Exception {
		addProductsAll(customerProducts);
		customerProducts.add(PR_BUSINESS_INTERNET_STANDARD);
		customerProducts.add(PR_VPN_PROFESSIONELL);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40313", "8026", transferRulesState);
			}
		}
	}

	public final void testID0316() throws Exception {

		products.add(PR_BUSINESSCONNECT_IPC);
		products.add(PR_BUSINESS_CONNECT_IMS);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_VERMITTLUNGSANLAGE);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					for (Product productsNext: products) {

						CustomerProducts customerProducts = new CustomerProducts();
						addProductsAll(customerProducts);
						customerProducts.add(productsNext);

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						callProfile.put(menu, CPV_STOERUNG);

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("40316", "8065", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID0360() throws Exception {

		customerProducts.add(PR_BUSINESSCONNECT_IPC);
		customerProducts.add(PR_BUSINESS_CONNECT_IMS);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_FESTNETZ);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40360", "8073", transferRulesState);
			}
		}
	}

	public final void testID0367() throws Exception {

		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		addProductClusterInternet(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_VERMITTLUNGSANLAGE);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40367", "4007", transferRulesState);
				}
			}
		}
	}

	public final void testID0368() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_BUSINESSTYP, SME);
		callProfile.put(SME_PRODUKTMENU, CPV_ERROR);
		callProfile.put(CPK_CALLDETAIL, "nothing");

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {

					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40368", "8802", transferRulesState);
				}
			}
		}
	}

	public final void testID0369() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		grobSegments.add("");
		grobSegments.add(null);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_ERROR);
		callProfile.put(CPK_CALLDETAIL, "nothing");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("40369", "8800", transferRulesState);
			}
		}
	}

	public final void testID0609_gesperrterKunde() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addFeinSegmentsAll(feinSegments);
		addSubSegmentsAll(subSegments);

		CallProfile callProfile = new CallProfile();
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_BUSINESSTYP, SME);

		customerProfile.setCarsStatus("block");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40609", "8010", transferRulesState);
				}
			}
		}
	}

	public final void testID0609_mobilegesperrt() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addFeinSegmentsAll(feinSegments);
		addSubSegmentsAll(subSegments);

		CallProfile callProfile = new CallProfile();
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_BUSINESSTYP, SME);

		callProfile.setCallDetail("mobilegesperrt,test,nonsense");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("40609", "8010", transferRulesState);
				}
			}
		}
	}

	public final void testID1999() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSegment(SEGMENT_CBU);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("41999", "7557", transferRulesState);
	}
	
	public final void testID2000() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSegment(SEGMENT_RES);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42000", "6502", transferRulesState);
	}
	
	public final void testID2001() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_SG4");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42001", "8128", transferRulesState);
	}

	public final void testID2002() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_SG5");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42002", "8129", transferRulesState);
	}

	public final void testID2003() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_SG6");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42003", "8130", transferRulesState);
	}

	public final void testID2004() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_LA3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42004", "8131", transferRulesState);
	}

	public final void testID2005() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_OL1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42005", "8113", transferRulesState);
	}

	public final void testID2006() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_OL2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42006", "8114", transferRulesState);
	}

	public final void testID2007() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_OL3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42007", "8115", transferRulesState);
	}

	public final void testID2008() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_SG1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42008", "8116", transferRulesState);
	}

	public final void testID2009() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_SG2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42009", "8117", transferRulesState);
	}

	public final void testID2010() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_SG3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42010", "8118", transferRulesState);
	}

	public final void testID2011() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_OL4");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42011", "8119", transferRulesState);
	}

	public final void testID2012() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_BZ1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42012", "8120", transferRulesState);
	}

	public final void testID2013() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_BZ2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42013", "8121", transferRulesState);
	}

	public final void testID2014() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_LA1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42014", "8122", transferRulesState);
	}

	public final void testID2015() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);

		customerProfile.setSa1_ntAccount("CARMAN_LA2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42015", "8123", transferRulesState);
	}

	public final void testID2016() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		miscList.add("g");
		miscList.add("f");
		miscList.add("e");

		for (String lang: miscList) {

			callProfile.put(CPK_LANGUAGE, lang);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42016", "8572", transferRulesState);
		}
	}

	public final void testID2017() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELESS);

		callProfile.put(CPK_LANGUAGE, "i");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42017", "8573", transferRulesState);
	}

	public final void testID2018() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		miscList.add("g");
		miscList.add("f");
		miscList.add("e");

		for (String lang: miscList) {

			callProfile.put(CPK_LANGUAGE, lang);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42018", "8201", transferRulesState);
		}
	}

	public final void testID2019() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800851851);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);

		callProfile.put(CPK_LANGUAGE, "i");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42019", "8202", transferRulesState);
	}

	public final void testID2020() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);
		addSubSegmentsAll(subSegments);

		businessNumbers.add(BUSINESSNUMBER_0800055055);
		businessNumbers.add(BUSINESSNUMBER_0800851851);

		callProfile.put(CPK_CALLDETAIL,"srbr_port");

		for (String businessNumber: businessNumbers) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					for (String subSegmentsNext: subSegments) {

						callProfile.put(CPK_BUSINESSNUMBER, businessNumber);

						customerProfile.setSegment(grobSegmentsNext);
						customerProfile.setFineSegment(feinSegmentNext);
						customerProfile.setSubSegment(subSegmentsNext);

						transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
								customerProducts, customerProductClusters, callProfile, serviceConfiguration);
						checkTransferRulesResult("42020", "8571", transferRulesState);
					}
				}
			}
		}
	}

	public final void testID2021() throws Exception {

		addProductClusterAllIP(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("42021", "8204", transferRulesState);
				}
			}
		}
	}

	public final void testID2022() throws Exception {

		addProductClusterAllIP(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_FESTNETZ);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("42022", "8203", transferRulesState);
				}
			}
		}
	}

	public final void testID2023() throws Exception {
		addProductClusterAllIP(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_TV);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("42023", "8205", transferRulesState);
				}
			}
		}
	}

	public final void testID2024() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "srbr_allip_ftth");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42024", "8206", transferRulesState);
			}
		}
	}

	public final void testID2025() throws Exception {

		addProductClusterAllIP(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentSME(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_FESTNETZ);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42025", "8206", transferRulesState);
			}
		}
	}

	public final void testID2030() throws Exception {

		products.add(PR_MOBILE_N_STACK);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);

		for (Product product: products) {
			customerProducts = new CustomerProducts();
			customerProducts.add(product);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42030", "9009", transferRulesState);
		}
	}

	public final void testID2031() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put("kmuquestion","yes");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42031", "8200", transferRulesState);
	}

	public final void testID2032() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_ITHOSTING);
		callProfile.setOptionOfferName("KMU Office W+C 4Star");

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			customerProfile.setSegment(SEGMENT_SME); 
			callProfile.put(menu, CPV_STOERUNG);

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42032", "8200", transferRulesState);
		}
	}

	public final void testID2040() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "srbr_allip_order");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42040", "8150", transferRulesState);
			}
		}
	}
	
	public final void testID2041() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "srbr_allip_order_done_sa");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42041", "8151", transferRulesState);
			}
		}
	}
	
	public final void testID2026() throws Exception {

		addProductClusterAllIP(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42026", "8206", transferRulesState);
			}
		}
	}

	public final void testID2702() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_olb");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42702", "9565", transferRulesState);
			}
		}
	}

	public final void testID2706() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NACHTMENU, CPV_ADMINISTRATION);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42706", "8800", transferRulesState);

	}

	public final void testID2707() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PBRDSLMENU, CPV_MENUOPTION_PBRDSL1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42707", "8016", transferRulesState);
			}
		}
	}

	public final void testID2708() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PBRDSLMENU, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42708", "8802", transferRulesState);
			}
		}
	}

	public final void testID2710() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_sa_em");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42710", "8016", transferRulesState);
			}
		}
	}

	public final void testID2711() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_sa_al1");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42711", "8032", transferRulesState);
			}
		}
	}

	public final void testID2713() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_at_conv1");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42713", "8800", transferRulesState);
			}
		}
	}

	public final void testID2714() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_at_ws2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42714", "8508", transferRulesState);
			}
		}
	}

	public final void testID2715() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_at_wl2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42715", "8009", transferRulesState);
			}
		}
	}

	public final void testID2716() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_at_da");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42716", "8018", transferRulesState);
			}
		}
	}

	public final void testID2717() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_cont_conv1");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42717", "8800", transferRulesState);
			}
		}
	}

	public final void testID2718() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_cont_ws2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42718", "8508", transferRulesState);
			}
		}
	}

	public final void testID2719() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_cont_wl2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42719", "8009", transferRulesState);
			}
		}
	}

	public final void testID2720() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_cont_da");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42720", "8018", transferRulesState);
			}
		}
	}

	public final void testID2721() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_contmut_conv1");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42721", "8800", transferRulesState);
			}
		}
	}

	public final void testID2722() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_contmut_ws2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42722", "8508", transferRulesState);
			}
		}
	}

	public final void testID2723() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_contmut_wl2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42723", "8009", transferRulesState);
			}
		}
	}

	public final void testID2724() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_contmut_da");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42724", "8018", transferRulesState);
			}
		}
	}

	public final void testID2725() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_tov_conv1");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42725", "8800", transferRulesState);
			}
		}
	}

	public final void testID2726() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_tov_wl2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42726", "8009", transferRulesState);
			}
		}
	}

	public final void testID2727() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_mod_tov_da");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42727", "8018", transferRulesState);
			}
		}
	}

	public final void testID2728() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_cb_conv1");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42728", "8800", transferRulesState);
			}
		}
	}

	public final void testID2729() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_cb_ws2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42729", "8508", transferRulesState);
			}
		}
	}

	public final void testID2730() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_cb_wl2");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42730", "8009", transferRulesState);
			}
		}
	}

	public final void testID2731() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_cb_da");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42731", "8018", transferRulesState);
			}
		}
	}

	public final void testID2732() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_sa_bil");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42732", "8020", transferRulesState);
			}
		}
	}

	public final void testID2733() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_sa_bis");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42733", "8025", transferRulesState);
			}
		}
	}

	public final void testID2734() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_sa_bcon");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42734", "8065", transferRulesState);
			}
		}
	}

	public final void testID2735() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(CPK_CALLDETAIL, "cbr_sa_pbx");

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42735", "4007", transferRulesState);
			}
		}
	}

	public final void testID2737() throws Exception {

		addGrobSegmentCBU(grobSegments);
		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_ITHOSTING);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext); 
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("42737", "8067", transferRulesState);
				}
			}
		}
	}

	public final void testID2738() throws Exception {

		customerProducts.add(PR_ONLINE_BACKUP_PROFESSIONAL);
		customerProducts.add(PR_HOSTED_EXCHANGE_PROFESSIONELL);
		customerProducts.add(PR_HOSTED_EXCHANGE_PROFESSIONAL_2);
		customerProducts.add(PR_SHARED_OFFICE_PROFESSIONAL);

		addGrobSegmentCBU(grobSegments);
		addGrobSegmentSME(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42738", "8072", transferRulesState);
			}
		}
	}

	public final void testID2740() throws Exception {

		addProductClusterFixnet(customerProducts);
		customerProducts.add(PR_FX_BLUEWIN_DSL);
		customerProducts.add(PR_FX_BLUEWIN_DIALUP);
		customerProducts.add(PR_FX_BLUEWIN_NAKEDACCOUNT);
		customerProducts.add(PR_DSL_PROFESSIONELL);
		customerProducts.add(PR_BUI_ADVANCED);
		customerProducts.add(PR_IP_PLUS_BUI);
		addProductClusterMobile(customerProducts);
		addProductClusterPBX(customerProducts);
		addProductClusterTV(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NACHTMENU, CPV_STOERUNG);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42740", "8036", transferRulesState);
			}
		}
	}

	public final void testID2741() throws Exception {

		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		addProductClusterInternet(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NACHTMENU, CPV_STOERUNG);
		callProfile.put(SME_PRODUKTMENU, CPV_FESTNETZ);
		callProfile.put(CPK_CONNECTIONTYPE, CPV_WIRELINE);


		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42741", "8031", transferRulesState);
			}
		}
	}		

	public final void testID2742() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentEmpty(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800888500);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42742", "4018", transferRulesState);
			}
		}
	}

	public final void testID2743() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentEmpty(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800888500);
		callProfile.put(SME_ANLIEGENMENU, CPV_KUNDENDIENST);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42743", "OH_RULEID_2743", transferRulesState);
			}
		}
	}

	public final void testID2744() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentEmpty(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800888500);
		callProfile.put(SME_ANLIEGENMENU, CPV_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42744", "4018", transferRulesState);
			}
		}
	}

	public final void testID2801() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42801", "8096", transferRulesState);
	}

	public final void testID2802() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42802", "8097", transferRulesState);
	}

	public final void testID2803() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42803", "8098", transferRulesState);
	}

	public final void testID2804() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42804", "8099", transferRulesState);
	}

	public final void testID2805() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42805", "8102", transferRulesState);
	}

	public final void testID2806() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42806", "8103", transferRulesState);
	}

	public final void testID2807() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL4");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42807", "8104", transferRulesState);
	}

	public final void testID2808() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_BZ1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42808", "8132", transferRulesState);
	}

	public final void testID2809() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_BZ2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42809", "8133", transferRulesState);
	}

	public final void testID2810() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_LA1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42810", "8111", transferRulesState);
	}

	public final void testID2811() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_LA2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42811", "8112", transferRulesState);
	}

	public final void testID2812() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG4");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42812", "8124", transferRulesState);
	}

	public final void testID2815() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_LA3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42815", "8127", transferRulesState);
	}

	public final void testID2816() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_SG4");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42816", "8128", transferRulesState);
		}
	}

	public final void testID2819() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_LA3");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42819", "8131", transferRulesState);
		}
	}

	public final void testID2820() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_OL1");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42820", "8113", transferRulesState);
		}
	}

	public final void testID2821() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_OL2");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42821", "8114", transferRulesState);
		}
	}

	public final void testID2822() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_OL3");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42822", "8115", transferRulesState);
		}
	}

	public final void testID2823() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_SG1");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42823", "8116", transferRulesState);
		}
	}

	public final void testID2824() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_SG2");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42824", "8117", transferRulesState);
		}
	}

	public final void testID2825() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_SG3");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42825", "8118", transferRulesState);
		}
	}

	public final void testID2826() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_OL4");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42826", "8119", transferRulesState);
		}
	}

	public final void testID2827() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_BZ1");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42827", "8120", transferRulesState);
		}
	}

	public final void testID2828() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_BZ2");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42828", "8121", transferRulesState);
		}
	}

	public final void testID2829() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_LA1");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42829", "8122", transferRulesState);
		}
	}

	public final void testID2830() throws Exception {

		miscList.add(CPV_ADMINISTRATION);
		miscList.add(CPV_ERROR);

		for (String option: miscList) {
			callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
			callProfile.put(SME_ANLIEGENMENU, option);

			customerProfile.setSegment(SEGMENT_SME);
			customerProfile.setSa1_ntAccount("CARMAN_LA2");

			transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
					customerProducts, customerProductClusters, callProfile, serviceConfiguration);
			checkTransferRulesResult("42830", "8123", transferRulesState);
		}
	}

	public final void testID2831() throws Exception {

		customerProducts.add(PR_FX_BLUEWIN_DSL_SAT);
		customerProducts.add(PR_WIRELESS_HOME_CONNECTION);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addGrobSegmentEmpty(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("42831", "9057", transferRulesState);
				}
			}
		}
	}

	public final void testID2835() throws Exception {

		customerProducts.add(PR_IP_PLUS_BUI);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		grobSegments.add("");
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_STOERUNG);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42835", "8192", transferRulesState);
			}
		}
	}

	public final void testID2836() throws Exception {

		customerProducts.add(PR_BLUEWIN_TV);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		grobSegments.add("");
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_PRODUKTMENU, CPV_TV);

		menus.add(SME_ANLIEGENMENU);
		menus.add(SME_NACHTMENU);

		for (String menu: menus) {
			for (String grobSegmentsNext: grobSegments) {
				for (String feinSegmentNext: feinSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					callProfile.put(menu, CPV_STOERUNG);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("42836", "8196", transferRulesState);
				}
			}
		}
	}

	public final void testID2838() throws Exception {

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		grobSegments.add("");
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_ITHOSTING);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42838", "8072", transferRulesState);
			}
		}
	}

	public final void testID2839() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800782788);

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42839", "8074", transferRulesState);
	}

	public final void testID2840() throws Exception {
		customerProducts.add(PR_IP_PLUS_BUI);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		grobSegments.add("");
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_INTERNET);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42840", "8191", transferRulesState);
			}
		}
	}

	public final void testID2841() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		feinSegments.add(FINESEGMENT_1);
		feinSegments.add(FINESEGMENT_2);
		feinSegments.add(FINESEGMENT_3);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NACHTMENU, CPV_BERATUNG);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42841", "8197", transferRulesState);
			}
		}
	}

	public final void testID2842() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		feinSegments.add(FINESEGMENT_4);
		feinSegments.add(FINESEGMENT_5);
		feinSegments.add("");
		feinSegments.add(null);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NACHTMENU, CPV_BERATUNG);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42842", "8198", transferRulesState);
			}
		}
	}

	public final void testID2843() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG4");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42843", "8128", transferRulesState);
	}

	public final void testID2846() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_LA3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42846", "8131", transferRulesState);
	}

	public final void testID2847() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42847", "8113", transferRulesState);
	}

	public final void testID2848() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42848", "8114", transferRulesState);
	}

	public final void testID2849() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42849", "8115", transferRulesState);
	}

	public final void testID2850() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42850", "8116", transferRulesState);
	}

	public final void testID2851() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42851", "8117", transferRulesState);
	}

	public final void testID2852() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_SG3");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42852", "8118", transferRulesState);
	}

	public final void testID2853() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_OL4");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42853", "8119", transferRulesState);
	}

	public final void testID2854() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_BZ1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42854", "8120", transferRulesState);
	}

	public final void testID2855() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_BZ2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42855", "8121", transferRulesState);
	}

	public final void testID2856() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_LA1");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42856", "8122", transferRulesState);
	}

	public final void testID2857() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_LA2");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42857", "8123", transferRulesState);
	}

	public final void testID2858() throws Exception {

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_BERATUNG);

		customerProfile.setSegment(SEGMENT_SME);
		customerProfile.setSa1_ntAccount("CARMAN_BLABLA");

		transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
				customerProducts, customerProductClusters, callProfile, serviceConfiguration);
		checkTransferRulesResult("42858", "8803", transferRulesState);
	}

	public final void testID2860() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);
		addSubSegmentsAll(subSegments);

		callProfile.put(CPK_CALLDETAIL,"srbr_infinity");
		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				for (String subSegmentsNext: subSegments) {
					customerProfile.setSegment(grobSegmentsNext);
					customerProfile.setFineSegment(feinSegmentNext);
					customerProfile.setSubSegment(subSegmentsNext);

					transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
							customerProducts, customerProductClusters, callProfile, serviceConfiguration);
					checkTransferRulesResult("42860", "8010", transferRulesState);
				}
			}
		}
	}

	public final void testID2862() throws Exception {

		addProductClusterInternet(customerProducts);
		addProductClusterITHosting(customerProducts);
		addProductClusterMobile(customerProducts);
		addProductClusterTV(customerProducts);
		customerProducts.add(PR_ECONOMYLINE);
		customerProducts.add(PR_MULTILINEISDN);
		customerProducts.add(PR_BUSINESSLINEISDN);
		customerProducts.add(PR_BUSINESSLINE_BASISANSCHLUSS);
		customerProducts.add(PR_BUSINESSLINE_PRIMAERANSCHLUSS);
		customerProducts.add(PR_BUSINESSNUMBERS);
		customerProducts.add(PR_PBX_MIETE);
		customerProducts.add(PR_PBX_KAUF);
		customerProducts.add(PR_PBX_WARTUNGSVERTRAG);
		customerProducts.add(PR_PBX_MIGRATIONSVERTRAG);

		addGrobSegmentsAll(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_ANLIEGENMENU, CPV_ADMINISTRATION);
		callProfile.put(SME_PRODUKTMENU, CPV_RECHNUNG);
		callProfile.put(CPK_CUSTOMERTYPE, "NewCustomer");


		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42862", "8806", transferRulesState);
			}
		}
	}

	public final void testID2900() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42900", "OH_RULEID_SME_NPWLineError", transferRulesState);
			}
		}
	}

	public final void testID2901() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42901", "OH_RULEID_SME_NPWLineAlm1", transferRulesState);
			}
		}
	}

	public final void testID2902() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42902", "OH_RULEID_SME_NPWLineAlm1Prm1", transferRulesState);
			}
		}
	}

	public final void testID2903() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42903", "OH_RULEID_SME_NPWLineAlm1Prm2", transferRulesState);
			}
		}
	}

	public final void testID2904() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42904", "OH_RULEID_SME_NPWLineAlm1Prm3", transferRulesState);
			}
		}
	}

	public final void testID2905() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42905", "OH_RULEID_SME_NPWLineAlm1Prm4", transferRulesState);
			}
		}
	}

	public final void testID2906() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42906", "OH_RULEID_SME_NPWLineAlm1Prm5", transferRulesState);
			}
		}
	}

	public final void testID2907() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42907", "OH_RULEID_SME_NPWLineAlm1Error", transferRulesState);
			}
		}
	}

	public final void testID2908() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42908", "OH_RULEID_SME_NPWLineAlm2", transferRulesState);
			}
		}
	}

	public final void testID2909() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42909", "OH_RULEID_SME_NPWLineAlm2Prm1", transferRulesState);
			}
		}
	}

	public final void testID2910() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42910", "OH_RULEID_SME_NPWLineAlm2Prm2", transferRulesState);
			}
		}
	}

	public final void testID2911() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42911", "OH_RULEID_SME_NPWLineAlm2Prm3", transferRulesState);
			}
		}
	}

	public final void testID2912() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42912", "OH_RULEID_SME_NPWLineAlm2Prm4", transferRulesState);
			}
		}
	}

	public final void testID2913() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42913", "OH_RULEID_SME_NPWLineAlm2Prm5", transferRulesState);
			}
		}
	}

	public final void testID2914() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42914", "OH_RULEID_SME_NPWLineAlm2Error", transferRulesState);
			}
		}
	}

	public final void testID2915() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42915", "OH_RULEID_SME_NPWLineAlm3", transferRulesState);
			}
		}
	}

	public final void testID2916() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42916", "OH_RULEID_SME_NPWLineAlm3Prm1", transferRulesState);
			}
		}
	}

	public final void testID2917() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42917", "OH_RULEID_SME_NPWLineAlm3Prm2", transferRulesState);
			}
		}
	}

	public final void testID2918() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42918", "OH_RULEID_SME_NPWLineAlm3Prm3", transferRulesState);
			}
		}
	}

	public final void testID2919() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42919", "OH_RULEID_SME_NPWLineAlm3Prm4", transferRulesState);
			}
		}
	}

	public final void testID2920() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42920", "OH_RULEID_SME_NPWLineAlm3Prm5", transferRulesState);
			}
		}
	}

	public final void testID2921() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42921", "OH_RULEID_SME_NPWLineAlm3Error", transferRulesState);
			}
		}
	}

	public final void testID2922() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42922", "OH_RULEID_SME_NPWLineAlm4", transferRulesState);
			}
		}
	}

	public final void testID2923() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42923", "OH_RULEID_SME_NPWLineAlm4Prm1", transferRulesState);
			}
		}
	}

	public final void testID2924() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42924", "OH_RULEID_SME_NPWLineAlm4Prm2", transferRulesState);
			}
		}
	}

	public final void testID2925() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42925", "OH_RULEID_SME_NPWLineAlm4Prm3", transferRulesState);
			}
		}
	}

	public final void testID2926() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42926", "OH_RULEID_SME_NPWLineAlm4Prm4", transferRulesState);
			}
		}
	}

	public final void testID2927() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42927", "OH_RULEID_SME_NPWLineAlm4Prm5", transferRulesState);
			}
		}
	}

	public final void testID2928() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_FIXNET, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_FIXNET, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42928", "OH_RULEID_SME_NPWLineAlm4Error", transferRulesState);
			}
		}
	}

	public final void testID2929() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42929", "OH_RULEID_SME_NPWLessError", transferRulesState);
			}
		}
	}

	public final void testID2930() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42930", "OH_RULEID_SME_NPWLessAlm1", transferRulesState);
			}
		}
	}

	public final void testID2931() throws Exception {
		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42931", "OH_RULEID_SME_NPWLessAlm1Prm1", transferRulesState);
			}
		}
	}

	public final void testID2932() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42932", "OH_RULEID_SME_NPWLessAlm1Prm2", transferRulesState);
			}
		}
	}

	public final void testID2933() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42933", "OH_RULEID_SME_NPWLessAlm1Prm3", transferRulesState);
			}
		}
	}

	public final void testID2934() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42934", "OH_RULEID_SME_NPWLessAlm1Prm4", transferRulesState);
			}
		}
	}

	public final void testID2935() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42935", "OH_RULEID_SME_NPWLessAlm1Prm5", transferRulesState);
			}
		}
	}

	public final void testID2936() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM1);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42936", "OH_RULEID_SME_NPWLessAlm1Error", transferRulesState);
			}
		}
	}

	public final void testID2937() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42937", "OH_RULEID_SME_NPWLessAlm2", transferRulesState);
			}
		}
	}

	public final void testID2938() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42938", "OH_RULEID_SME_NPWLessAlm2Prm1", transferRulesState);
			}
		}
	}

	public final void testID2939() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42939", "OH_RULEID_SME_NPWLessAlm2Prm2", transferRulesState);
			}
		}
	}

	public final void testID2940() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42940", "OH_RULEID_SME_NPWLessAlm2Prm3", transferRulesState);
			}
		}
	}

	public final void testID2941() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42941", "OH_RULEID_SME_NPWLessAlm2Prm4", transferRulesState);
			}
		}
	}

	public final void testID2942() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42942", "OH_RULEID_SME_NPWLessAlm2Prm5", transferRulesState);
			}
		}
	}

	public final void testID2943() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM2);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42943", "OH_RULEID_SME_NPWLessAlm2Error", transferRulesState);
			}
		}
	}

	public final void testID2944() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42944", "OH_RULEID_SME_NPWLessAlm3", transferRulesState);
			}
		}
	}

	public final void testID2945() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42945", "OH_RULEID_SME_NPWLessAlm3Prm1", transferRulesState);
			}
		}
	}

	public final void testID2946() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42946", "OH_RULEID_SME_NPWLessAlm3Prm2", transferRulesState);
			}
		}
	}

	public final void testID2947() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42947", "OH_RULEID_SME_NPWLessAlm3Prm3", transferRulesState);
			}
		}
	}

	public final void testID2948() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42948", "OH_RULEID_SME_NPWLessAlm3Prm4", transferRulesState);
			}
		}
	}

	public final void testID2949() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42949", "OH_RULEID_SME_NPWLessAlm3Prm5", transferRulesState);
			}
		}
	}

	public final void testID2950() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM3);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42950", "OH_RULEID_SME_NPWLessAlm3Error", transferRulesState);
			}
		}
	}

	public final void testID2951() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4_TRANSFER);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42951", "OH_RULEID_SME_NPWLessAlm4", transferRulesState);
			}
		}
	}

	public final void testID2952() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4_PRM1);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42952", "OH_RULEID_SME_NPWLessAlm4Prm1", transferRulesState);
			}
		}
	}

	public final void testID2953() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4_PRM2);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42953", "OH_RULEID_SME_NPWLessAlm4Prm2", transferRulesState);
			}
		}
	}

	public final void testID2954() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4_PRM3);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42954", "OH_RULEID_SME_NPWLessAlm4Prm3", transferRulesState);
			}
		}
	}

	public final void testID2955() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4_PRM4);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42955", "OH_RULEID_SME_NPWLessAlm4Prm4", transferRulesState);
			}
		}
	}

	public final void testID2956() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);
		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4_PRM5);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42956", "OH_RULEID_SME_NPWLessAlm4Prm5", transferRulesState);
			}
		}
	}

	public final void testID2957() throws Exception {

		addProductsAll(customerProducts);

		addGrobSegmentSME(grobSegments);
		addGrobSegmentCBU(grobSegments);
		addFeinSegmentsAll(feinSegments);

		callProfile.put(CPK_BUSINESSNUMBER, BUSINESSNUMBER_0800055055);

		callProfile.put(SME_NPALMMENU_MOBILE, CPV_MENUOPTION_NP_ALM4);
		callProfile.put(SME_NPPRMMENU_MOBILE, CPV_MENUOPTION_ERROR);

		for (String grobSegmentsNext: grobSegments) {
			for (String feinSegmentNext: feinSegments) {
				customerProfile.setSegment(grobSegmentsNext);
				customerProfile.setFineSegment(feinSegmentNext);

				transferRulesState = droolsImpl.getTransferQueue(TRANSFERRULES, customerProfile,
						customerProducts, customerProductClusters, callProfile, serviceConfiguration);
				checkTransferRulesResult("42957", "OH_RULEID_SME_NPWLessAlm4Error", transferRulesState);
			}
		}
	}

}

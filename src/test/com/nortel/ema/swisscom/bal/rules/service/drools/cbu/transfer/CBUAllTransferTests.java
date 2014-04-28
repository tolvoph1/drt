/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBUAllTransferTests.java 165 2014-01-17 10:59:37Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-01-17 11:59:37 +0100 (Fri, 17 Jan 2014) $
 * $Revision: 165 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CBUAllTransferTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer");
		//$JUnit-BEGIN$
		suite.addTestSuite(CBURulesTransferAXAWinterthurTest.class);
		suite.addTestSuite(CBURulesTransferCBUKundendienstTest.class);
		suite.addTestSuite(CBURulesTransferCBUWirelessTest.class);
		suite.addTestSuite(CBURulesTransferCreditSuisseTest.class);
		suite.addTestSuite(CBURulesTransferDASCSTest.class);
		suite.addTestSuite(CBURulesTransferDASOneWorkplaceTest.class);
		suite.addTestSuite(CBURulesTransferDASPartnerTest.class);
		suite.addTestSuite(CBURulesTransferIncidentWirelineTest.class);
		suite.addTestSuite(CBURulesTransferM2MTest.class);
		suite.addTestSuite(CBURulesTransferMobileDASITAdminTest.class);
		suite.addTestSuite(CBURulesTransferNovartisTest.class);
		suite.addTestSuite(CBURulesTransferOuttaskingKundenIncidentTest.class);
		suite.addTestSuite(CBURulesTransferSchweizerischePostTest.class);
		suite.addTestSuite(CBURulesTransferVBSTest.class);
		//$JUnit-END$
		return suite;
	}

}

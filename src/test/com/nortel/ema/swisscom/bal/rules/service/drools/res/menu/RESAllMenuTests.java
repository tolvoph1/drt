/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESAllMenuTests.java 233 2014-04-08 06:19:14Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-08 08:19:14 +0200 (Tue, 08 Apr 2014) $
 * $Revision: 233 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.menu;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RESAllMenuTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.oneivr.menu");
		//$JUnit-BEGIN$
		suite.addTestSuite(RESRulesGet0800803175AnliegenMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetCFSCustomerAnliegenMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetFixnetAnliegenMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetFixnetProduktMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetMenuOptions0800406080Test.class);
		suite.addTestSuite(RESRulesGetMigrosAnliegenMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetMigrosProduktMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetMobileAnliegenMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetMobileCBUAnliegenMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetMobileProduktMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetNewProductMenuALMFixnetTest.class);
		suite.addTestSuite(RESRulesGetNewProductMenuALMMobileTest.class);
		suite.addTestSuite(RESRulesGetNewProductMenuPRMTest.class);
		suite.addTestSuite(RESRulesGetOnlineShopAnliegenMenuOptionsTest.class);
		suite.addTestSuite(RESRulesGetTHLAnliegenMenuOptionsTest.class);
		//$JUnit-END$
		return suite;
	}

}

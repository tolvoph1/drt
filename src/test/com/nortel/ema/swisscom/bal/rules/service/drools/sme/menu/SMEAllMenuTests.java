/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMEAllMenuTests.java 79 2013-09-09 15:24:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:24:23 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 79 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.menu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SMEAllMenuTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(SMEAllMenuTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(SMERulesGet0800888500MenuTest.class);
		suite.addTestSuite(SMERulesGetAnliegenMenuTest.class);
		suite.addTestSuite(SMERulesGetNachtMenuTest.class);
		suite.addTestSuite(SMERulesGetNewProductMenuALMFixnetTest.class);
		suite.addTestSuite(SMERulesGetNewProductMenuALMMobileTest.class);
		suite.addTestSuite(SMERulesGetNewProductMenuPRMFixnetTest.class);
		suite.addTestSuite(SMERulesGetNewProductMenuPRMMobileTest.class);
		suite.addTestSuite(SMERulesGetPBRDSLMenuTest.class);
		suite.addTestSuite(SMERulesGetProductMenuTest.class);
		//$JUnit-END$
		return suite;
	}
}

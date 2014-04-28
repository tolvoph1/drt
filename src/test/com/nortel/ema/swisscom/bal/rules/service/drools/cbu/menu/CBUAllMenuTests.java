/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBUAllMenuTests.java 236 2014-04-15 12:36:40Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-15 14:36:40 +0200 (Tue, 15 Apr 2014) $
 * $Revision: 236 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.menu;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CBUAllMenuTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.cbu.menu");
		//$JUnit-BEGIN$
		suite.addTestSuite(CBURulesGetMenuOptions0080072472424Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0442946666Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800724001Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions080072400202Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800724002Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800724003Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800724008Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800724011Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800724724Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800800900Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions080080800900Test.class);
		suite.addTestSuite(CBURulesGetMenuOptions0800824825Test.class);
		//$JUnit-END$
		return suite;
	}

}

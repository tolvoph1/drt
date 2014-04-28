/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBUAllStateTests.java 202 2014-03-05 12:04:28Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-03-05 13:04:28 +0100 (Wed, 05 Mar 2014) $
 * $Revision: 202 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu.state;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CBUAllStateTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.cbu.state");
		//$JUnit-BEGIN$
		suite.addTestSuite(CBURulesState0080072472424Test.class);
		suite.addTestSuite(CBURulesState0442946666Test.class);
		suite.addTestSuite(CBURulesState0582249143Test.class);
		suite.addTestSuite(CBURulesState0582249144Test.class);
		suite.addTestSuite(CBURulesState0582249151Test.class);
		suite.addTestSuite(CBURulesState0582249183Test.class);
		suite.addTestSuite(CBURulesState0800444404Test.class);
		suite.addTestSuite(CBURulesState0800724001Test.class);
		suite.addTestSuite(CBURulesState080072400202Test.class);
		suite.addTestSuite(CBURulesState0800724002Test.class);
		suite.addTestSuite(CBURulesState0800724003Test.class);
		suite.addTestSuite(CBURulesState0800724008Test.class);
		suite.addTestSuite(CBURulesState0800724724Test.class);
		suite.addTestSuite(CBURulesState0800800900Test.class);
		suite.addTestSuite(CBURulesState080080800900Test.class);
		suite.addTestSuite(CBURulesState0800819175Test.class);
		suite.addTestSuite(CBURulesState0800824825Test.class);
		suite.addTestSuite(CBURulesState0800870875Test.class);
		suite.addTestSuite(CBURulesState0812879925Test.class);
		suite.addTestSuite(CBURulesState0812879948Test.class);
		suite.addTestSuite(CBURulesState0812879963Test.class);
		suite.addTestSuite(CBURulesStateDefaultTest.class);
		suite.addTestSuite(CBURulesStateMASTERM2MTest.class);
		//$JUnit-END$
		return suite;
	}

}

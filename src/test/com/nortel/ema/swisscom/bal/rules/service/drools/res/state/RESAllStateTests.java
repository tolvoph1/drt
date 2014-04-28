/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESAllStateTests.java 233 2014-04-08 06:19:14Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-08 08:19:14 +0200 (Tue, 08 Apr 2014) $
 * $Revision: 233 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res.state;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RESAllStateTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.vp5.state");
		//$JUnit-BEGIN$
		suite.addTestSuite(RESRulesState0800010742Test.class);
		suite.addTestSuite(RESRulesState0800406080Test.class);
		suite.addTestSuite(RESRulesState0800566566Test.class);
		suite.addTestSuite(RESRulesState0800800851Test.class);
		suite.addTestSuite(RESRulesState0800803175Test.class);
		suite.addTestSuite(RESRulesState0800817112Test.class);
		suite.addTestSuite(RESRulesState0800817175Test.class);
		suite.addTestSuite(RESRulesState0800820212Test.class);
		suite.addTestSuite(RESRulesState0800850085Test.class);
		suite.addTestSuite(RESRulesState0800888817Test.class);
		suite.addTestSuite(RESRulesState0900333221Test.class);
		suite.addTestSuite(RESRulesStateAuslandWirelessTest.class);
		suite.addTestSuite(RESRulesStateGetInTouchTest.class);
		suite.addTestSuite(RESRulesStateMASTERMainTest.class);
		suite.addTestSuite(RESRulesStateMASTERMigrosTest.class);
		suite.addTestSuite(RESRulesStateMASTERRepairTest.class);
		suite.addTestSuite(RESRulesStateMASTERUniqueBNTest.class);
		suite.addTestSuite(RESRulesStateMobileCBUTest.class);
		suite.addTestSuite(RESRulesStateWirelessTest.class);
		suite.addTestSuite(RESRulesStateWirelineTest.class);
		suite.addTestSuite(RESSMERulesStatePilotSpracherkennungTest.class);
		//$JUnit-END$
		return suite;
	}

}

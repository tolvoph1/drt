/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMEAllStateTests.java 79 2013-09-09 15:24:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:24:23 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 79 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme.state;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SMEAllStateTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(SMEAllStateTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(SMERulesState0800055055Test.class);
		suite.addTestSuite(SMERulesState0800782788Test.class);
		suite.addTestSuite(SMERulesState0800851851Test.class);
		suite.addTestSuite(SMERulesState0800888500Test.class);
		suite.addTestSuite(SMERulesStateMASTERTransferTest.class);
		suite.addTestSuite(SMERulesStateWirelessTest.class);
		suite.addTestSuite(SMERulesStateWirelineTest.class);
		//$JUnit-END$
		return suite;
	}

}

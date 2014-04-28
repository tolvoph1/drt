/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: RESAllTests.java 79 2013-09-09 15:24:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:24:23 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 79 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.res;

import com.nortel.ema.swisscom.bal.rules.service.drools.res.menu.RESAllMenuTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.res.state.RESAllStateTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.res.transfer.RESAllTransferTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RESAllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.cbu");
		//$JUnit-BEGIN$
		suite.addTest(RESAllMenuTests.suite());
		suite.addTest(RESAllStateTests.suite());
		suite.addTest(RESAllTransferTests.suite());
		//$JUnit-END$
		return suite;
	}
}

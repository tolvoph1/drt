/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CBUAllTests.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cbu;

import com.nortel.ema.swisscom.bal.rules.service.drools.cbu.menu.CBUAllMenuTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.cbu.state.CBUAllStateTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.cbu.transfer.CBUAllTransferTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CBUAllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.cbu");
		//$JUnit-BEGIN$
		suite.addTest(CBUAllMenuTests.suite());
		suite.addTest(CBUAllStateTests.suite());
		suite.addTest(CBUAllTransferTests.suite());
		//$JUnit-END$
		return suite;
	}

}

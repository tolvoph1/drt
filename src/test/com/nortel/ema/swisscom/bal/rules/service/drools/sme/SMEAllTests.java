/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: SMEAllTests.java 79 2013-09-09 15:24:23Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:24:23 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 79 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.sme;

import com.nortel.ema.swisscom.bal.rules.service.drools.sme.menu.SMEAllMenuTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.sme.state.SMEAllStateTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.sme.transfer.SMEAllTransferTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SMEAllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.sme");
		//$JUnit-BEGIN$
		suite.addTest(SMEAllMenuTests.suite());
		suite.addTest(SMEAllStateTests.suite());
		suite.addTest(SMEAllTransferTests.suite());
		//$JUnit-END$
		return suite;
	}

}

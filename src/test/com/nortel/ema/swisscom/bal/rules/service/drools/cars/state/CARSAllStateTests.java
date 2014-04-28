/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: CARSAllStateTests.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.cars.state;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CARSAllStateTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nortel.ema.swisscom.bal.rules.service.drools.cars.state");
		//$JUnit-BEGIN$
		suite.addTestSuite(CarsWirelessCallflow1NSTest.class);
		suite.addTestSuite(CarsWirelessCallflow6Test.class);
		suite.addTestSuite(CarsWirelessCallflow2Test.class);
		suite.addTestSuite(CarsWirelessCallflow4Test.class);
		suite.addTestSuite(CarsWirelessCallflow5Test.class);
		suite.addTestSuite(CarsWirelessCallflow7Test.class);
		suite.addTestSuite(CarsWirelessOverviewTest.class);
		suite.addTestSuite(CarsWirelineCallflow1NSTest.class);
		suite.addTestSuite(CarsWirelessCallflow1SCTest.class);
		suite.addTestSuite(CarsWirelineCallflow2Test.class);
		suite.addTestSuite(CarsWirelineCallflow1SCTest.class);
		suite.addTestSuite(CarsWirelineOverviewTest.class);
		suite.addTestSuite(CarsWirelessCallflow8Test.class);
		//$JUnit-END$
		return suite;
	}

}

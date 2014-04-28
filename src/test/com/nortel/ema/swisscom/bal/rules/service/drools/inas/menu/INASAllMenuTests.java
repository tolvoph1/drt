/*
 * (c)2013 Swisscom (Schweiz) AG.
 *
 * $Id: INASAllMenuTests.java 238 2014-04-22 08:52:33Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-22 10:52:33 +0200 (Tue, 22 Apr 2014) $
 * $Revision: 238 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.inas.menu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class INASAllMenuTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(INASAllMenuTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(DummyPrioriteTest.class);
		suite.addTestSuite(INASTestMenuOptionsTest.class);
		suite.addTestSuite(IPhone5RESTest.class);
		suite.addTestSuite(IPhone5SMETest.class);
		//$JUnit-END$
		return suite;
	}

}

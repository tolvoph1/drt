package com.nortel.ema.swisscom.bal.rules.service.drools;

import com.nortel.ema.swisscom.bal.rules.service.drools.cbu.CBUAllTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.res.RESAllTests;
import com.nortel.ema.swisscom.bal.rules.service.drools.sme.SMEAllTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTest(CBUAllTests.suite());
		suite.addTest(SMEAllTests.suite());
		suite.addTest(RESAllTests.suite());
		//$JUnit-END$
		return suite; 
	}
}


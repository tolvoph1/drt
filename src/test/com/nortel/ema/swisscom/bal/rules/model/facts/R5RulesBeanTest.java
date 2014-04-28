package com.nortel.ema.swisscom.bal.rules.model.facts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class R5RulesBeanTest extends TestCase {

	@Test
	public void testBirthdayWithinLastXMonths() {

		// All these testSets are defined to run on 1st June 2012, modify to fit
		// Define array with some testdate and expected result
		LinkedList<testSet> testData = new LinkedList<testSet>();
		testData.add(new testSet("12051974", "2", "38", true));
		testData.add(new testSet("31031974", "2", "38", false));
		testData.add(new testSet("18041974", "2", "38", true));
		testData.add(new testSet("10071985", "2", "27", false));
		testData.add(new testSet(null, "2", "37",false));
		testData.add(new testSet("12051974", null, "37",false));
		testData.add(new testSet("12051974", "2", null, false));
		
		// Iterate through List of testdata
		for (testSet ts: testData) {
			assertEquals(ts.expectedResult, R5RulesBean.birthdayWithinLastXMonths(ts.birthDate, ts.monthsSince, ts.maxAge));
		}
	}

	public void testBirthdayWithinNextXMonths() {

		// All these testSets are defined to run on 18th June 2012, modify to fit
		// Define array with some testdate and expected result
		LinkedList<testSet> testData = new LinkedList<testSet>();
		testData.add(new testSet("12071974", "2", "38", true));
		testData.add(new testSet("17061974", "2", "38", false));
		testData.add(new testSet("18081974", "2", "38", true));
		testData.add(new testSet("19081985", "2", "27", false));
		testData.add(new testSet(null, "2", "37",false));
		testData.add(new testSet("12051974", null, "37",false));
		testData.add(new testSet("12051974", "2", null, false));
		
		// Iterate through List of testdata
		for (testSet ts: testData) {
			assertEquals(ts.expectedResult, R5RulesBean.birthdayWithinNextXMonths(ts.birthDate, ts.monthsSince, ts.maxAge));
		}
	}
		
	public void testDeriveAgeInYears() {
		assertEquals(10, R5RulesBean.deriveAgeInYears("12052002"));
		// Invalid input returns -1
		assertEquals(-1, R5RulesBean.deriveAgeInYears("1234"));
		assertEquals(-1, R5RulesBean.deriveAgeInYears(""));
		assertEquals(-1, R5RulesBean.deriveAgeInYears(null));
	}
	
	public void testIsSpecialDateAndTime() {
		// cvtDate is in format "MM/dd/yyyy HH:mm:ss"
		assertEquals(true, R5RulesBean.isSpecialDateAndTime(0, 1, 2012, 8, 0, 0, 0, 1, 2012, 9, 0, 0, "01/01/2012 08:30:00"));
		assertEquals(false, R5RulesBean.isSpecialDateAndTime(0, 1, 2012, 8, 0, 0, 0, 1, 2012, 9, 0, 0, "01/01/2012 09:30:00"));
		// Invalid cvtDates
		assertEquals(R5RulesBean.isSpecialDateAndTime(6, 4, 2012, 0, 0, 0, 6, 4, 2012, 0, 30, 0, null), R5RulesBean.isSpecialDateAndTime(6, 4, 2012, 0, 0, 0, 6, 4, 2012, 0, 30, 0, "01/01/2012 09:30:0"));
	}
	
	public void testWithinTheLastXDays() {
		// Create DDMMYYYY String within 10 days from NOW
		Calendar within = new GregorianCalendar();
		within.add(Calendar.DAY_OF_MONTH, -9);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String withinString = sdf.format(within.getTime());
		assertEquals(true, R5RulesBean.withinTheLastXDays(withinString, 10,""));
		assertEquals(false, R5RulesBean.withinTheLastXDays(withinString, 8,""));
		// Test date in the future
		Calendar future = new GregorianCalendar();
		future.add(Calendar.DAY_OF_MONTH, 1);
		String futureString = sdf.format(future.getTime());
		assertEquals(false, R5RulesBean.withinTheLastXDays(futureString, 10,""));
		// Check exception
		assertEquals(false,  R5RulesBean.withinTheLastXDays("asdf", 10,""));
		
		// Test with cvtDate
		String cvtDate = new String("01/20/2013 10:00:10");
		String activationDate = new String("18012013");
		assertEquals(true, R5RulesBean.withinTheLastXDays(activationDate, 10, cvtDate));
		activationDate = new String("10012013");
		assertEquals(true, R5RulesBean.withinTheLastXDays(activationDate, 10, cvtDate));
	}
	
	
	
	private class testSet {
		String birthDate;
		String monthsSince;
		String maxAge;
		boolean expectedResult;
		
		
		testSet(String birthDate, String monthsSince, String maxAge, boolean expectedResult) {
			this.birthDate = birthDate;
			this.monthsSince = monthsSince;
			this.maxAge = maxAge;
			this.expectedResult = expectedResult;
		}
	}
}

/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: R5RulesBean.java 133 2013-10-18 12:28:57Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-10-18 14:28:57 +0200 (Fri, 18 Oct 2013) $
 * $Revision: 133 $
 */
package com.nortel.ema.swisscom.bal.rules.model.facts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * This class holds all the facts that are created during rules
 * execution. The main reason for this class is to be able to
 * map incoming parameters to logical facts so that changes in 
 * the incoming parameters only have to be made at one place.
 */
public class R5RulesBean {

	// Add Property Change Support to automatically re-evaluate
	// the rule base when a property changes
	private final PropertyChangeSupport changes = new PropertyChangeSupport( this );

	public static final String EMPTY = "EMPTY";
	private String state = EMPTY;
	private String businessNumber = EMPTY;
	private String businessTyp = EMPTY;
	private String installedBase = EMPTY;

	private String segment = EMPTY;
	private String fineSegment = "0";
	private String subSegment = "0";
	private String special1 = EMPTY;
	private String anliegen = EMPTY;

	private boolean hasProductClusterFixnet = false;
	private boolean hasProductClusterMobile = false;
	private boolean hasProductClusterInternet = false;
	private boolean hasProductClusterTV = false;
	private boolean hasProductClusterDataservice = false;
	private boolean hasProductClusterITHosting = false;
	private boolean hasProductClusterPBX = false;
	private boolean hasProductClusterAllIP = false;
	private boolean hasProductClusterFTTH = false;
	private boolean hasProductClusterABB = false;
	private boolean hasProductClusterDialup = false;
	private boolean hasProductClusterDuplo = false;
	private boolean hasProductClusterVoIP = false;

	// Attributes for menu levels and constants, which are independent of menus now
	private String menuLevel1Selected = EMPTY;
	private String menuLevel2Selected = EMPTY;
	private String menuLevel3Selected = EMPTY;

	// New attributes for oneIVR RES for easier rules checks
	// Call Info, refers to column Z in the 6044
	private String callInfo = EMPTY;
	// Info, column AH
	private String info = EMPTY;
	// special1 is already defined above, column CJ
	// Selfservice, taken from callDetail
	private String selfService = EMPTY;

	// New Attributes for age based routing to avoid eval expressions in rules
	private String customerAge = null;
	private String bestAge = null;
	private String maxAge = null;
	// And the same as Integer for comparison
	private int customerAgeInt = -1;
	private int bestAgeInt = -1;
	private int maxAgeInt = -1;

	// Boolean flag for Youngster BestAge (CRQ2012-037)
	private boolean youngsterBestAge = false;

	// Attributes to store CliData information until new BAL.war has been deployed that provides
	// getter methods in the CallProfile
	private String cliDataLanguage = EMPTY;
	private String cliDataPhonenumber = EMPTY;

	// Constants 
	public static final String WIRELINE = "Wireline";
	public static final String WIRELESS = "Wireless";
	public static final String UNKNOWN = "unknown";
	public static final String EASY = "Easy";
	public static final String AUSLAND = "Ausland";
	public static final String NOTEASY = "NotEasy";
	public static final String FOREIGNEASY = "ForeignEasy";
	public static final String FOREIGNSWISSCOM = "ForeignSwisscom";
	public static final String FOREIGNUNKNOWN = "ForeignUnknown";

	// OneIVR Self-Services
	public static final String SSCHECK = "SSCHECK";
	public static final String SSQCHECK = "SSQCHECK";
	public static final String SSCOPW = "SSCOPW";
	public static final String SSFC = "SSFC";
	public static final String SSLOAD = "SSLOAD";
	public static final String SSPG = "SSPG";
	public static final String SSPP = "SSPP";
	public static final String SSREP = "SSREP";
	public static final String SSRK = "SSRK";

	// OneIVR Customer types
	public static final String CUSTOMERTYPE_EASY = "Easy";
	public static final String CUSTOMERTYPE_MMO = "MMO";
	public static final String CUSTOMERTYPE_MBU = "MBU";
	// New Customer Type used by all portals
	public static final String CUSTOMERTYPE_NEWCUSTOMER = "NewCustomer";
	// NoNumber type introduced with Vivo Libero
	public static final String CUSTOMERTYPE_NONUMBER = "NoNumber";

	// OneIVR CARS Transfer Targets
	public static final String CARSTARGET_CCC = "CCC";
	public static final String CARSTARGET_DUNNING_BLOCKED = "Dunning Blocked";
	public static final String CARSTARGET_DUNNING_LETTER_SMS = "Dunning Letter SMS";
	public static final String CARSTARGET_BARRING_PREVENTION = "Barring Prevention";
	public static final String CARSTARGET_CREDITLIMIT_BLOCKED = "Creditlimit Blocked";

	// OneIVR Menu option names
	public static final String MENUOPTION_BC = "BC";
	public static final String MENUOPTION_RECHNUNG = "RECHNUNG";
	public static final String MENUOPTION_SF = "SF";
	public static final String MENUOPTION_KUNDENDIENST = "KUNDENDIENST";
	public static final String MENUOPTION_SA = "SA";
	public static final String MENUOPTION_STOERUNG = "STOERUNG";
	public static final String MENUOPTION_IN = "IN";
	public static final String MENUOPTION_INTERNET = "INTERNET";
	public static final String MENUOPTION_PH = "PH";
	public static final String MENUOPTION_PHONE = "PHONE";
	public static final String MENUOPTION_ERROR = "error";
	public static final String MENUOPTION_COPW = "COPW";
	public static final String MENUOPTION_COMBOX = "COMBOX";
	public static final String MENUOPTION_STHY = "STHY";
	public static final String MENUOPTION_DIEBSTAHL = "DIEBSTAHL";
	public static final String MENUOPTION_ROAM = "ROAM";
	public static final String MENUOPTION_ROAMING = "ROAMING";
	public static final String MENUOPTION_OTHER = "OTHER";
	public static final String MENUOPTION_WEITEREDIENSTE = "WEITEREDIENSTE";
	public static final String MENUOPTION_TV = "TV";
	public static final String MENUOPTION_CHECKLOAD = "CHECKLOAD";
	public static final String MENUOPTION_GUTHABEN = "GUTHABEN";
	public static final String MENUOPTION_EASY = "EASY";
	public static final String MENUOPTION_PW = "PW";
	public static final String MENUOPTION_PASSWORD = "PASSWORD";
	public static final String MENUOPTION_GUTHABENAUFLADEN = "GUTHABENAUFLADEN";
	public static final String MENUOPTION_GUTHABENABFRAGEN = "GUTHABENABFRAGEN";
	public static final String MENUOPTION_MBU = "MBU";
	public static final String MENUOPTION_THL1 = "THL1";
	public static final String MENUOPTION_THL2 = "THL2";
	public static final String MENUOPTION_SFCARD = "SFCARD";
	public static final String MENUOPTION_ACCESSDSL = "ACCESSDSL";
	public static final String MENUOPTION_ALLIPFTTH = "ALLIPFTTH";
	public static final String MENUOPTION_MOBILE_INTERNET = "MO-INTERNET";
	public static final String MENUOPTION_FESTNETZ_INTERNET = "FX-INTERNET";

	// OneIVR New Product Menu option names
	public static final String MENUOPTION_NP_ALM1 = "NPALM1";
	public static final String MENUOPTION_NP_ALM2 = "NPALM2";
	public static final String MENUOPTION_NP_ALM3 = "NPALM3";
	public static final String MENUOPTION_NP_ALM4 = "NPALM4";
	public static final String MENUOPTION_NP_ALM1_TRANSFER = "NPALM1TRANSFER";
	public static final String MENUOPTION_NP_ALM2_TRANSFER = "NPALM2TRANSFER";
	public static final String MENUOPTION_NP_ALM3_TRANSFER = "NPALM3TRANSFER";
	public static final String MENUOPTION_NP_ALM4_TRANSFER = "NPALM4TRANSFER";
	public static final String MENUOPTION_NP_PRM1 = "NPPRM1";
	public static final String MENUOPTION_NP_PRM2 = "NPPRM2";
	public static final String MENUOPTION_NP_PRM3 = "NPPRM3";
	public static final String MENUOPTION_NP_PRM4 = "NPPRM4";
	public static final String MENUOPTION_NP_PRM5 = "NPPRM5";
	//SME NewProductMenu options
	public static final String MENUOPTION_NP_ALM1_PRM1 = "NPALM1PRM1";
	public static final String MENUOPTION_NP_ALM1_PRM2 = "NPALM1PRM2";
	public static final String MENUOPTION_NP_ALM1_PRM3 = "NPALM1PRM3";
	public static final String MENUOPTION_NP_ALM1_PRM4 = "NPALM1PRM4";
	public static final String MENUOPTION_NP_ALM1_PRM5 = "NPALM1PRM5";
	public static final String MENUOPTION_NP_ALM2_PRM1 = "NPALM2PRM1";
	public static final String MENUOPTION_NP_ALM2_PRM2 = "NPALM2PRM2";
	public static final String MENUOPTION_NP_ALM2_PRM3 = "NPALM2PRM3";
	public static final String MENUOPTION_NP_ALM2_PRM4 = "NPALM2PRM4";
	public static final String MENUOPTION_NP_ALM2_PRM5 = "NPALM2PRM5";
	public static final String MENUOPTION_NP_ALM3_PRM1 = "NPALM3PRM1";
	public static final String MENUOPTION_NP_ALM3_PRM2 = "NPALM3PRM2";
	public static final String MENUOPTION_NP_ALM3_PRM3 = "NPALM3PRM3";
	public static final String MENUOPTION_NP_ALM3_PRM4 = "NPALM3PRM4";
	public static final String MENUOPTION_NP_ALM3_PRM5 = "NPALM3PRM5";
	public static final String MENUOPTION_NP_ALM4_PRM1 = "NPALM4PRM1";
	public static final String MENUOPTION_NP_ALM4_PRM2 = "NPALM4PRM2";
	public static final String MENUOPTION_NP_ALM4_PRM3 = "NPALM4PRM3";
	public static final String MENUOPTION_NP_ALM4_PRM4 = "NPALM4PRM4";
	public static final String MENUOPTION_NP_ALM4_PRM5 = "NPALM4PRM5";

	public static final String ERROR = "error";

	// Constants for Customer Segments
	public static final String SEGMENT_RES = "1";
	public static final String SEGMENT_SME = "2";
	public static final String SEGMENT_CBU = "3";

	// Feinsegmente
	// RES
	public static final String FINESEGMENT_PP = "10";	// Premium & Prestige
	public static final String FINESEGMENT_TY = "11";	// Teens & Youth
	public static final String FINESEGMENT_MFA = "12";	// Mainstream & Families 
	public static final String FINESEGMENT_S55 = "13";	// Silver 55+
	public static final String FINESEGMENT_PS = "14";	// Price Sensitive
	// SME
	public static final String FINESEGMENT_1 = "1";
	public static final String FINESEGMENT_2 = "2";
	public static final String FINESEGMENT_3 = "3";
	public static final String FINESEGMENT_4 = "4";
	public static final String FINESEGMENT_5 = "5";
	public static final String FINESEGMENT_0 = "0";
	// CBU
	public static final String FINESEGMENT_PLATIN = "31";
	public static final String FINESEGMENT_GOLD = "32";
	public static final String FINESEGMENT_FIRST = "33";
	public static final String FINESEGMENT_BUS = "34";
	public static final String FINESEGMENT_NEW = "30";

	// Subsegmente
	// RES
	public static final String SUBSEGMENT_GOLD = "11";		// Premium & Prestige
	public static final String SUBSEGMENT_PLATIN = "10";	// Premium & Prestige
	public static final String SUBSEGMENT_VIP = "12";		// Premium & Prestige
	public static final String SUBSEGMENT_CHILD = "13";		// Teens & Youth Child (4-12)
	public static final String SUBSEGMENT_TEENS = "14";		// Teens & Youth Teens (13-19)
	public static final String SUBSEGMENT_TWENS = "15";		// Teens & Youth Twens (20-26)
	public static final String SUBSEGMENT_YADULTS = "16";	// Teens & Youth Young Adults (27-30)
	// SME
	public static final String SUBSEGMENT_ICT = "1";
	public static final String SUBSEGMENT_COMDOT = "3";
	public static final String SUBSEGMENT_COM = "2";
	// CBU
	public static final String SUBSEGMENT_NAM = "30";
	public static final String SUBSEGMENT_KAT = "31";

	// CompType
	public static final String COMPUTERTYPE_MAC = "MAC";
	public static final String COMPUTERTYPE_PC = "PC";

	// State Constants
	public static final String NOT_INITIALIZED = "NOT_INIATIALIZED";
	public static final String INITIALIZED = "INITIALIZED";
	public static final String ANLIEGEN_DETERMINED = "ANLIEGEN_DETERMINED";
	public static final String PRODUKTMENU_DETERMINED = "PRODUKTMENU_DETERMINED";
	public static final String PRODUKTCLUSTER_DETERMINED = "PRODUKTCLUSTER_DETERMINED";
	public static final String ANLIEGENMENU_DONE = "ANLIEGENMENU_DONE";
	public static final String PRODUKTMENU_DONE = "PRODUKTMENU_DONE";
	public static final String DONE = "DONE";
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		String SEPARATOR = System.getProperty("line.separator");
		result.append("R5RulesBean:"+SEPARATOR);
		result.append(" anliegen: " +anliegen+SEPARATOR);
		result.append(" bestAge: " +bestAge+SEPARATOR);
		result.append(" businessNumber: " +businessNumber+SEPARATOR);
		result.append(" businessTyp: "+businessTyp+SEPARATOR);
		result.append(" callInfo: "+callInfo+SEPARATOR);
		result.append(" cliDataLanguage: "+cliDataLanguage+SEPARATOR);
		result.append(" cliDataPhonenumber: "+cliDataPhonenumber+SEPARATOR);
		result.append(" customerAge: "+customerAge+SEPARATOR);
		result.append(" fineSegment: "+fineSegment+SEPARATOR);
		result.append(" hasProductClusterABB: "+hasProductClusterABB+SEPARATOR);
		result.append(" hasProductClusterAllIP: "+hasProductClusterAllIP+SEPARATOR);
		result.append(" hasProductClusterDataservice: "+hasProductClusterDataservice+SEPARATOR);
		result.append(" hasProductClusterDialup: "+hasProductClusterDialup+SEPARATOR);
		result.append(" hasProductClusterDuplo: "+hasProductClusterDuplo+SEPARATOR);
		result.append(" hasProductClusterFixnet: "+hasProductClusterFixnet+SEPARATOR);
		result.append(" hasProductClusterFTTH: "+hasProductClusterFTTH+SEPARATOR);
		result.append(" hasProductClusterInternet: "+hasProductClusterInternet+SEPARATOR);
		result.append(" hasProductClusterITHosting: "+hasProductClusterITHosting+SEPARATOR);
		result.append(" hasProductClusterMobile: "+hasProductClusterMobile+SEPARATOR);
		result.append(" hasProductClusterPBX: "+hasProductClusterPBX+SEPARATOR);
		result.append(" hasProductClusterTV: "+hasProductClusterTV+SEPARATOR);
		result.append(" hasProductClusterVoIP: "+hasProductClusterVoIP+SEPARATOR);
		result.append(" info: "+info+SEPARATOR);
		result.append(" installedBase: "+installedBase+SEPARATOR);
		result.append(" maxAge: "+maxAge+SEPARATOR);
		result.append(" menuLevel1Selected: "+menuLevel1Selected+SEPARATOR);
		result.append(" menuLevel2Selected: "+menuLevel2Selected+SEPARATOR);
		result.append(" menuLevel3Selected: "+menuLevel3Selected+SEPARATOR);
		result.append(" segment: "+segment+SEPARATOR);
		result.append(" selfService: "+selfService+SEPARATOR);
		result.append(" special1: "+special1+SEPARATOR);
		result.append(" state: " +state+SEPARATOR);					
		result.append(" subSegment: "+subSegment+SEPARATOR);
		result.append(" hasMoreThanOneProductCluster: "+getHasMoreThanOneProductCluster()+SEPARATOR);
		result.append(" isCustomerInBestAge: "+isCustomerInBestAge()+SEPARATOR);
		result.append(" isCustomerOverMaxAge: "+isCustomerOverMaxAge()+SEPARATOR);
		result.append(" isyoungsterBestAge: "+isYoungsterBestAge()+SEPARATOR);
		return result.toString();
	}

	public void addPropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.addPropertyChangeListener( listener );
	}
	public void removePropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.removePropertyChangeListener(listener);
	}

	// Constructor
	public R5RulesBean () {
		super();
		this.state = INITIALIZED;
	}

	// Method to check if caller has more than one active clusters, using the cluster flags
	// Using this name so it can be access via "R5RulesBean ( hasMoreThanOneProductCluster == true)" in rules
	public boolean getHasMoreThanOneProductCluster() {
		int count = 0;
		if (hasProductClusterABB)
			count++;
		if (hasProductClusterAllIP)
			count++;
		if (hasProductClusterDataservice)
			count++;
		if (hasProductClusterDialup)
			count++;
		if (hasProductClusterDuplo)
			count++;
		if (hasProductClusterFixnet)
			count++;
		if (hasProductClusterFTTH)
			count++;
		if (hasProductClusterInternet)
			count++;
		if (hasProductClusterITHosting)
			count++;
		if (hasProductClusterMobile)
			count++;
		if (hasProductClusterPBX)
			count++;
		if (hasProductClusterTV)
			count++;
		if (hasProductClusterVoIP)
			count++;
		return (count>1);
	}

	public static int deriveAgeInYears(final String birthDateString)  {
		// Catch null input
		if (birthDateString == null)
			return -1;
		Calendar birthDate = Calendar.getInstance();
		try {
			birthDate.setTime(new SimpleDateFormat("ddMMyyyy").parse(birthDateString));
		} catch ( ParseException e) {
			return -1;
		}
		Calendar today = Calendar.getInstance();

		// remove time sections of today
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		today.set(Calendar.AM_PM, Calendar.AM);

		if (today.before(birthDate) || today.compareTo(birthDate) == 0) {
			return -1;
		}

		int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

		// reduce age by one if we haven't got to their birthday yet.
		if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR))
			age--;

		return age;
	}

	/**
	 * This method takes numeric days of week and hours and minutes and returns true if the current
	 * time is within those limits.
	 * DayOfWeek 1=Sun, 2=Mon, ..., 6=Fri, 7=Sat
	 * @param dayStart	Day of Week that the range starts, this day is included in the range
	 * @param dayEnd	Day of Week that the range ends, this day is included in the range
	 * @param HHMMStart HHMM that the time starts, this time is included in the range
	 * @param HHMMEnd   HHMM that the time ends, this time is included in the range
	 * @return true if the current time is within the range passed
	 * @return false if the current time is outside the range passed
	 * Note: This function can only handle pretty simple ranges like Mo-Fr 0730 to 1730
	 */
	public static boolean isWithinOfficeHours (int dayStart, int dayEnd, int HHMMStart, int HHMMEnd) {
		// DayOfWeek 1=Sun, 2=Mon,... 6=Fri, 7=Sat
		Calendar cal = new GregorianCalendar();
		int hour24 = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int HHMM = min + 100*hour24;
		if ( dayOfWeek >= dayStart && dayOfWeek <= dayEnd && HHMM >= HHMMStart && HHMM <= HHMMEnd) {
			return true;
		} else {
			return false;
		}
	}

	// Wrapper method for isWithingOfficeHours to support cvtDate
	public static boolean isWithinOfficeHours (int dayStart, int dayEnd, int HHMMStart, int HHMMEnd, String cvtDate) {
		// Parse and convert cvtDate
		GregorianCalendar cal;
		// Check if SysCVTdate exists, must be 19 Characters long if correct
		if ((cvtDate == null) || cvtDate.length()!=19 || cvtDate == "" ) {
			cal = new GregorianCalendar();
		} else {
			// Parse this String (it should be in the right format MM/dd/yyyy HH:mm:ss)
			final int dayOfMonth = new Integer(cvtDate.substring(3,5));
			final int month = new Integer(cvtDate.substring(0,2));
			final int year = new Integer(cvtDate.substring(6,10));
			final int hourOfDay = new Integer(cvtDate.substring(11,13));
			final int minute = new Integer(cvtDate.substring(14,16));
			final int second = new Integer(cvtDate.substring(17,19));
			cal = new GregorianCalendar(year, month-1, dayOfMonth, hourOfDay, minute, second);
		}
		// Calculate current day / Time
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // SUNDAY=1, MONDAY=2,...,SATURDAY=7
		int HHMM = cal.get(Calendar.MINUTE) + 100*cal.get(Calendar.HOUR_OF_DAY);
		// Now we have a Calendar object with either the current date or the date from the sysCVTDate
		// Check if current weekday is within the range dayStart<=current<=dayEnd
		if (dayOfWeek >= dayStart && dayOfWeek <= dayEnd) {
			// within weekday range
			// check if time within range
			if (HHMM >= HHMMStart && HHMM <= HHMMEnd) {
				return true;
			} else {
				// not within time range
				return false;
			}
		} else {
			// not within weekday range
			return false;
		}

	}

	/**
	 * Checks if the date/time in calTest is between start and end where the limits are not included
	 * @param calTest
	 * @param start
	 * @param end
	 * @return true if calTest is inside start and end non-inclusive, false otherwise
	 */
	private static boolean isSpecialDateAndTime(GregorianCalendar calTest, GregorianCalendar start, GregorianCalendar end) {
		if (calTest.after(start) && calTest.before(end)) {
			return true;
		} else {
			return false;
		}
	}  

	/**
	 * Checks if the current date/time or the SysCVTDate from the CallProfile is within a certain time frame defined by
	 * the integer variables. The borders are not included so start<cal<end
	 * Checks if the SysCVTDate key is set in the CallProfile and uses that value if it exists,
	 * otherwise, uses the current date
	 * @param callProfile The CallProfile of the current call
	 * @return true if the date/time is inside the required time range
	 */
	public static boolean isSpecialDateAndTime(int startMonth,
			int startDay,
			int startYear,
			int startHour,
			int startMinute,
			int startSecond,
			int endMonth,
			int endDay,
			int endYear,
			int endHour,
			int endMinute,
			int endSecond,
			String cvtDate) {
		GregorianCalendar start = new GregorianCalendar(startYear, startMonth, startDay, startHour, startMinute, startSecond);
		GregorianCalendar end   = new GregorianCalendar(endYear, endMonth, endDay, endHour, endMinute, endSecond);
		GregorianCalendar cal;
		// Check if SysCVTdate exists
		if ((cvtDate == null) || cvtDate.length()!=19 ) {
			cal = new GregorianCalendar();
		} else {
			// Parse this String (it should be in the right format MM/dd/yyyy HH:mm:ss)
			final int dayOfMonth = new Integer(cvtDate.substring(3,5));
			final int month = new Integer(cvtDate.substring(0,2));
			final int year = new Integer(cvtDate.substring(6,10));
			final int hourOfDay = new Integer(cvtDate.substring(11,13));
			final int minute = new Integer(cvtDate.substring(14,16));
			final int second = new Integer(cvtDate.substring(17,19));
			cal = new GregorianCalendar(year, month-1, dayOfMonth, hourOfDay, minute, second);
		}
		return isSpecialDateAndTime(cal,start,end);
	}

	/**
	 * @param customerProfileBirthdate String Format DDMMYYYY
	 * @param monthsSince int Months back from today
	 * @param maxAge The birthday to use a starting point for the calculation.
	 * @return true if the birthdate (String DDMMYYYY) contains a birthday within the last monthsSince months
	 */
	public static boolean birthdayWithinLastXMonths(String customerProfileBirthdate, String monthsSince, String maxAge) {
		// Check if birthDate and the rest aren't null
		if (customerProfileBirthdate == null || monthsSince == null || maxAge == null )
			return false;
		// Check if dateString is valid
		if (customerProfileBirthdate.length()!=8) {
			return false;
		} else {
			try {
				// Parse birthdate String
				Calendar birthDate = Calendar.getInstance();
				birthDate.setTime(new SimpleDateFormat("ddMMyyyy").parse(customerProfileBirthdate));
				// Create Calendar object with current date minux monthsSince
				Calendar earliestDate = Calendar.getInstance();
				earliestDate.add(Calendar.MONTH, -1*new Integer(monthsSince));
				// subtract one day from earliestDate if it's exactly monthsSince away
				earliestDate.add(Calendar.DAY_OF_MONTH, -1);
				// Set time fields on earliestDate to 0
				earliestDate.set(Calendar.HOUR, 0);
				earliestDate.set(Calendar.MINUTE, 0);
				earliestDate.set(Calendar.SECOND, 0);
				earliestDate.set(Calendar.MILLISECOND, 0);
				earliestDate.set(Calendar.AM_PM, Calendar.AM);
				// Create Calendar with birthDate plus maxAge
				Calendar birthDatePlusMaxAge = Calendar.getInstance();
				birthDatePlusMaxAge.setTime(birthDate.getTime());
				birthDatePlusMaxAge.add(Calendar.YEAR, new Integer(maxAge));

				// Now check if earliestDate is before birthDatePlusMaxAge
				// create now date to avoid birthdate in the future
				Calendar today = Calendar.getInstance();
				if (earliestDate.before(birthDatePlusMaxAge) && birthDatePlusMaxAge.before(today) ) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				System.err.println("Exception in birthdayWithinLastXMonths: "+e.toString());
				return false;
			}

		}


	}

	/**
	 * @param customerProfileBirthdate String Format DDMMYYYY
	 * @param months int Months from today
	 * @param maxAge The birthday to use a starting point for the calculation.
	 * @return true if the birthdate (String DDMMYYYY) contains a birthday within the next "months" months
	 */
	public static boolean birthdayWithinNextXMonths(String customerProfileBirthdate, String months, String maxAge) {
		// Check if birthDate and the rest aren't null
		if (customerProfileBirthdate == null || months == null || maxAge == null )
			return false;
		// Check if dateString is valid
		if (customerProfileBirthdate.length()!=8) {
			return false;
		} else {
			try {
				// Parse birthdate String
				Calendar birthDate = Calendar.getInstance();
				birthDate.setTime(new SimpleDateFormat("ddMMyyyy").parse(customerProfileBirthdate));
				// Create Calendar object with current date minux monthsSince
				Calendar latestDate = Calendar.getInstance();
				latestDate.add(Calendar.MONTH, new Integer(months));
				// add one day from earliestDate if it's exactly months away
				latestDate.add(Calendar.DAY_OF_MONTH, +1);
				// Set time fields on latestDate to 0
				latestDate.set(Calendar.HOUR, 0);
				latestDate.set(Calendar.MINUTE, 0);
				latestDate.set(Calendar.SECOND, 0);
				latestDate.set(Calendar.MILLISECOND, 0);
				latestDate.set(Calendar.AM_PM, Calendar.AM);
				// Create Calendar with birthDate plus Age
				Calendar birthDatePlusMaxAge = Calendar.getInstance();
				birthDatePlusMaxAge.setTime(birthDate.getTime());
				birthDatePlusMaxAge.add(Calendar.YEAR, new Integer(maxAge));

				// Now check if latestDate is after birthDatePlusMaxAge and birthDatePlusMaxAge is after today
				// create now date to avoid birthdate in the future
				Calendar today = Calendar.getInstance();
				if (latestDate.after(birthDatePlusMaxAge) && birthDatePlusMaxAge.after(today) ) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				System.err.println("Exception in birthdayWithinLastXMonths: "+e.toString());
				return false;
			}
		}
	}

	/**
	 * @param dateToCheck Date in format DDMMYYYY that needs to be checked
	 * @param days int number of days used for check
	 * @return true if dateToCheck is within the last "days" days, calculated from 0am
	 */
	public static boolean withinTheLastXDays(String dateToCheck, int days, String cvtDate) {
		try {
			Calendar checkDate = new GregorianCalendar();
			checkDate.setTime(new SimpleDateFormat("ddMMyyyy").parse(dateToCheck));
			
			Calendar now;
			Calendar limit;
			if ((cvtDate == null) || cvtDate.length()!=19 ) {
				now = new GregorianCalendar();
				// Set time to all 0
				now.set(Calendar.HOUR_OF_DAY, 0);
				now.set(Calendar.MINUTE,0);
				now.set(Calendar.SECOND,0);
				now.set(Calendar.MILLISECOND, 0);
				limit = new GregorianCalendar();
				limit.set(Calendar.HOUR_OF_DAY, 0);
				limit.set(Calendar.MINUTE,0);
				limit.set(Calendar.SECOND,0);
				limit.set(Calendar.MILLISECOND, 0);
				limit.add(Calendar.DAY_OF_MONTH, -days);
			} else {
				// Parse this String (it should be in the right format MM/dd/yyyy HH:mm:ss)
				final int dayOfMonth = new Integer(cvtDate.substring(3,5));
				final int month = new Integer(cvtDate.substring(0,2));
				final int year = new Integer(cvtDate.substring(6,10));
				Calendar cvtCal = Calendar.getInstance();
				cvtCal.set(year, month-1, dayOfMonth,0,0,0);
				cvtCal.set(Calendar.MILLISECOND, 0);
				Date cvtD = new Date(cvtCal.getTimeInMillis());
				now = new GregorianCalendar();
				now.setTime(cvtD);
				limit = new GregorianCalendar();
				limit.setTime(cvtD);
				limit.add(Calendar.DAY_OF_MONTH, -days);
			}
			if ((limit.compareTo(checkDate)<=0) && (now.compareTo(checkDate)>=0)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Exception in withinTheLastXDays: "+e.toString());
			return false;
		}
			
	}

	/**
	 * @return the businessNumber
	 */
	public String getBusinessNumber() {
		return businessNumber;
	}
	/**
	 * @param businessNumber the businessNumber to set
	 */
	public void setBusinessNumber(String businessNumber) {
		if ( businessNumber != null) {
			final String oldBusinessNumber = this.businessNumber;
			this.businessNumber = businessNumber;
			this.changes.firePropertyChange( "state", oldBusinessNumber, businessNumber);
		}
	}
	/**
	 * @return the installedBase
	 */
	public String getInstalledBase() {
		return installedBase;
	}
	/**
	 * @param installedBase the installedBase to set
	 */
	public void setInstalledBase(String installedBase) {
		if ( installedBase != null ) {
			final String oldInstalledBase = this.installedBase;
			this.installedBase = installedBase;
			this.changes.firePropertyChange("state",oldInstalledBase,installedBase);
		}
	}
	/**
	 * @return the special1
	 */
	public String getSpecial1() {
		return special1;
	}
	/**
	 * @param special1 the special1 to set
	 */
	public void setSpecial1(String special1) {
		if ( special1 != null ) {
			final String oldSpecial1 = this.special1;
			this.special1 = special1;
			this.changes.firePropertyChange("state",oldSpecial1,special1);
		}
	}
	/**
	 * @return the anliegen
	 */
	public String getAnliegen() {
		return anliegen;
	}
	/**
	 * @param anliegen the anliegen to set
	 */
	public void setAnliegen(String anliegen) {
		if ( anliegen != null ) {
			final String oldAnliegen = this.anliegen;
			this.anliegen = anliegen;
			this.changes.firePropertyChange("state", oldAnliegen, anliegen);
		}
	}
	/**
	 * @return the hasProductClusterDataservice
	 */
	public boolean getHasProductClusterDataservice() {
		return hasProductClusterDataservice;
	}
	/**
	 * @param hasProductClusterDataservice the hasProductClusterDataservice to set
	 */
	public void setHasProductClusterDataservice(boolean hasProductClusterDataservice) {
		final boolean oldHasProductClusterDataservice = this.hasProductClusterDataservice;
		this.hasProductClusterDataservice = hasProductClusterDataservice;
		this.changes.firePropertyChange("state",oldHasProductClusterDataservice,hasProductClusterDataservice);
	}

	public boolean getHasProductClusterITHosting() {
		return hasProductClusterITHosting;
	}

	public void setHasProductClusterITHosting(boolean hasProductClusterITHosting) {
		final boolean oldHasProductClusterITHosting = this.hasProductClusterITHosting;
		this.hasProductClusterITHosting = hasProductClusterITHosting;
		this.changes.firePropertyChange("state", oldHasProductClusterITHosting, hasProductClusterITHosting);
	}

	/**
	 * @return the hasProductClusterFixnet
	 */
	public boolean getHasProductClusterFixnet() {
		return hasProductClusterFixnet;
	}
	/**
	 * @param hasProductClusterFixnet the hasProductClusterFixnet to set
	 */
	public void setHasProductClusterFixnet(boolean hasProductClusterFixnet) {
		final boolean oldHasProductClusterFixnet = this.hasProductClusterFixnet;
		this.hasProductClusterFixnet = hasProductClusterFixnet;
		this.changes.firePropertyChange("state",oldHasProductClusterFixnet,hasProductClusterFixnet);
	}
	/**
	 * @return the hasProductClusterInternet
	 */
	public boolean getHasProductClusterInternet() {
		return hasProductClusterInternet;
	}
	/**
	 * @param hasProductClusterInternet the hasProductClusterInternet to set
	 */
	public void setHasProductClusterInternet(boolean hasProductClusterInternet) {
		final boolean oldHasProductClusterInternet = this.hasProductClusterInternet;
		this.hasProductClusterInternet = hasProductClusterInternet;
		this.changes.firePropertyChange("state",oldHasProductClusterInternet,hasProductClusterInternet);
	}
	/**
	 * @return the hasProductClusterMobile
	 */
	public boolean getHasProductClusterMobile() {
		return hasProductClusterMobile;
	}
	/**
	 * @param hasProductClusterMobile the hasProductClusterMobile to set
	 */
	public void setHasProductClusterMobile(boolean hasProductClusterMobile) {
		final boolean oldHasProductClusterMobile = this.hasProductClusterMobile;
		this.hasProductClusterMobile = hasProductClusterMobile;
		this.changes.firePropertyChange("state",oldHasProductClusterMobile,hasProductClusterMobile);
	}
	/**
	 * @return the hasProductClusterPBX
	 */
	public boolean getHasProductClusterPBX() {
		return hasProductClusterPBX;
	}
	/**
	 * @param hasProductClusterPBX the hasProductClusterPBX to set
	 */
	public void setHasProductClusterPBX(boolean hasProductClusterPBX) {
		final boolean oldHasProductClusterPBX = this.hasProductClusterPBX;
		this.hasProductClusterPBX = hasProductClusterPBX;
		this.changes.firePropertyChange("state",oldHasProductClusterPBX,hasProductClusterPBX);
	}
	/**
	 * @return the hasProductClusterTV
	 */
	public boolean getHasProductClusterTV() {
		return hasProductClusterTV;
	}
	/**
	 * @param hasProductClusterTV the hasProductClusterTV to set
	 */
	public void setHasProductClusterTV(boolean hasProductClusterTV) {
		final boolean oldHasProductClusterTV = this.hasProductClusterTV;
		this.hasProductClusterTV = hasProductClusterTV;
		this.changes.firePropertyChange("state",oldHasProductClusterTV,hasProductClusterTV);
	}

	/**
	 * @return the hasProductClusterAllIP
	 */
	public boolean getHasProductClusterAllIP() {
		return hasProductClusterAllIP;
	}
	/**
	 * @param hasProductClusterAllIP the hasProductClusterAllIP to set
	 */
	public void setHasProductClusterAllIP(boolean hasProductClusterAllIP) {
		final boolean oldHasProductClusterAllIP = this.hasProductClusterAllIP;
		this.hasProductClusterAllIP = hasProductClusterAllIP;
		this.changes.firePropertyChange("state",oldHasProductClusterAllIP,hasProductClusterAllIP);
	}

	/**
	 * @return the hasProductClusterFTTH
	 */
	public boolean getHasProductClusterFTTH() {
		return hasProductClusterFTTH;
	}
	/**
	 * @param hasProductClusterFTTH the hasProductClusterFTTH to set
	 */
	public void setHasProductClusterFTTH(boolean hasProductClusterFTTH) {
		final boolean oldHasProductClusterFTTH = this.hasProductClusterFTTH;
		this.hasProductClusterFTTH = hasProductClusterFTTH;
		this.changes.firePropertyChange("state",oldHasProductClusterFTTH,hasProductClusterFTTH);
	}

	/**
	 * @return the hasProductClusterABB
	 */
	public boolean getHasProductClusterABB() {
		return hasProductClusterABB;
	}
	/**
	 * @param hasProductClusterABB the hasProductClusterABB to set
	 */
	public void setHasProductClusterABB(boolean hasProductClusterABB) {
		final boolean oldHasProductClusterABB = this.hasProductClusterABB;
		this.hasProductClusterABB = hasProductClusterABB;
		this.changes.firePropertyChange("state",oldHasProductClusterABB,hasProductClusterABB);
	}

	/**
	 * @return the hasProductClusterDialup
	 */
	public boolean getHasProductClusterDialup() {
		return hasProductClusterDialup;
	}
	/**
	 * @param hasProductClusterDialup the hasProductClusterDialup to set
	 */
	public void setHasProductClusterDialup(boolean hasProductClusterDialup) {
		final boolean oldHasProductClusterDialup = this.hasProductClusterDialup;
		this.hasProductClusterDialup = hasProductClusterDialup;
		this.changes.firePropertyChange("state",oldHasProductClusterDialup,hasProductClusterDialup);
	}

	/**
	 * @return the hasProductClusterDuplo
	 */
	public boolean getHasProductClusterDuplo() {
		return hasProductClusterDuplo;
	}
	/**
	 * @param hasProductClusterDuplo the hasProductClusterDuplo to set
	 */
	public void setHasProductClusterDuplo(boolean hasProductClusterDuplo) {
		final boolean oldHasProductClusterDuplo = this.hasProductClusterDuplo;
		this.hasProductClusterDuplo = hasProductClusterDuplo;
		this.changes.firePropertyChange("state",oldHasProductClusterDuplo,hasProductClusterDuplo);
	}

	/**
	 * @return the hasProductClusterVoIP
	 */
	public boolean getHasProductClusterVoIP() {
		return hasProductClusterVoIP;
	}
	/**
	 * @param hasProductClusterVoIP the hasProductClusterVoIP to set
	 */
	public void setHasProductClusterVoIP(boolean hasProductClusterVoIP) {
		final boolean oldHasProductClusterVoIP = this.hasProductClusterVoIP;
		this.hasProductClusterVoIP = hasProductClusterVoIP;
		this.changes.firePropertyChange("state",oldHasProductClusterVoIP,hasProductClusterVoIP);
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		if ( state != null ) {
			final String oldState = this.state;
			this.state = state;
			this.changes.firePropertyChange("state",oldState,state);
		}
	}

	/**
	 * @return the businessTyp
	 */
	public String getBusinessTyp() {
		return businessTyp;
	}
	/**
	 * @param businessTyp the businessTyp to set
	 */
	public void setBusinessTyp(String businesstyp) {
		if ( businesstyp != null) {
			final String oldBusinesstyp = this.businessTyp;
			this.businessTyp = businesstyp;
			this.changes.firePropertyChange("state",oldBusinesstyp,businesstyp);
		}
	}


	public String getMenuLevel1Selected() {
		return menuLevel1Selected;
	}

	public void setMenuLevel1Selected(String menuLevel1Selected) {
		if ( menuLevel1Selected != null ) {
			final String oldMenuLevel1Selected = this.menuLevel1Selected;
			this.menuLevel1Selected = menuLevel1Selected;
			this.changes.firePropertyChange("state",oldMenuLevel1Selected,menuLevel1Selected);
		}
	}

	public String getMenuLevel2Selected() {
		return menuLevel2Selected;
	}

	public void setMenuLevel2Selected(String menuLevel2Selected) {
		if ( menuLevel2Selected != null ) {
			final String oldMenuLevel2Selected = this.menuLevel2Selected;
			this.menuLevel2Selected = menuLevel2Selected;
			this.changes.firePropertyChange("state",oldMenuLevel2Selected,menuLevel2Selected);
		}
	}

	public String getMenuLevel3Selected() {
		return menuLevel3Selected;
	}

	public void setMenuLevel3Selected(String menuLevel3Selected) {
		if ( menuLevel3Selected != null ) {
			final String oldMenuLevel3Selected = this.menuLevel3Selected;
			this.menuLevel3Selected = menuLevel3Selected;
			this.changes.firePropertyChange("state",oldMenuLevel3Selected,menuLevel3Selected);
		}
	}

	public String getFineSegment() {
		return fineSegment;
	}

	public void setFineSegment(String finesegment) {
		if (finesegment != null) {
			final String oldFinesegment = this.fineSegment;
			this.fineSegment = finesegment;
			this.changes.firePropertyChange("state",oldFinesegment,finesegment);
		}
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		if (segment != null) {
			final String oldSegment = this.segment;
			this.segment = segment;
			this.changes.firePropertyChange("state",oldSegment,segment);
		}
	}

	public String getSubSegment() {
		return subSegment;
	}

	public void setSubSegment(String subsegment) {
		if (subsegment != null) {
			final String oldSubsegment = this.subSegment;
			this.subSegment = subsegment;
			this.changes.firePropertyChange("state",oldSubsegment,subsegment);
		}
	}

	public String getCallInfo() {
		return callInfo;
	}

	public void setCallInfo(String callInfo) {
		if (callInfo != null) {
			final String oldCallInfo = this.callInfo;
			this.callInfo = callInfo;
			this.changes.firePropertyChange("state",oldCallInfo,callInfo);
		}
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		if (info != null) {
			final String oldInfo = this.info;		
			this.info = info;
			this.changes.firePropertyChange("state",oldInfo,info);
		}
	}

	public String getSelfService() {
		return selfService;
	}

	public void setSelfService(String selfService) {
		if (selfService != null) {
			final String oldSelfService = this.selfService;
			this.selfService = selfService;
			this.changes.firePropertyChange("state",oldSelfService,selfService);
		}
	}

	public String getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(String customerAge) {
		if (customerAge != null) {
			final String oldCustomerAge= this.customerAge;		
			this.customerAge = customerAge;
			try {
				this.customerAgeInt = new Integer(customerAge);
			} catch (NumberFormatException e) {
				this.customerAgeInt = -1;
			}
			this.changes.firePropertyChange("state",oldCustomerAge,customerAge);
		}
	}

	public String getBestAge() {
		return bestAge;
	}

	public void setBestAge(String bestAge) {
		if (bestAge != null) {
			final String oldBestAge= this.bestAge;		
			this.bestAge = bestAge;
			try {
				this.bestAgeInt = new Integer(bestAge);
			} catch (NumberFormatException e) {
				this.bestAgeInt = -1;
			}
			this.changes.firePropertyChange("state",oldBestAge,bestAge);
		}
	}

	public String getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(String maxAge) {
		if (maxAge != null) {
			final String oldMaxAge= this.maxAge;		
			this.maxAge = maxAge;
			try {
				this.maxAgeInt = new Integer(maxAge);
			} catch (NumberFormatException e) {
				this.maxAgeInt = -1;
			}
			this.changes.firePropertyChange("state",oldMaxAge,maxAge);
		}
	}
	public boolean isCustomerInBestAge() {
		if (maxAgeInt != -1 && bestAgeInt != -1 && customerAgeInt != -1) {
			return (bestAgeInt<customerAgeInt && customerAgeInt<maxAgeInt);
		} else {
			return false;
		}
	}
	public boolean isCustomerOverMaxAge() {
		if (maxAgeInt != -1 && customerAgeInt != -1) {
			return (maxAgeInt<customerAgeInt);
		} else {
			return false;
		}
	}

	public void setYoungsterBestAge(boolean youngsterBestAge) {
		final boolean oldYoungsterBestAge = this.youngsterBestAge;
		this.youngsterBestAge = youngsterBestAge;
		this.changes.firePropertyChange("state",oldYoungsterBestAge,youngsterBestAge);
	}
	public boolean isYoungsterBestAge() {
		return youngsterBestAge;
	}

	public String getCliDataLanguage() {
		return cliDataLanguage;
	}

	public void setCliDataLanguage(String cliDataLanguage) {
		if (cliDataLanguage != null) {
			final String oldCliDataLanguage = this.cliDataLanguage;
			this.cliDataLanguage = cliDataLanguage;
			this.changes.firePropertyChange("status", oldCliDataLanguage, cliDataLanguage);
		}

	}

	public String getCliDataPhonenumber() {
		return cliDataPhonenumber;
	}

	public void setCliDataPhonenumber(String cliDataPhonenumber) {
		if (cliDataPhonenumber != null) {
			final String oldCliDataPhonenumber = this.cliDataPhonenumber;
			this.cliDataPhonenumber = cliDataPhonenumber;
			this.changes.firePropertyChange("state", oldCliDataPhonenumber, cliDataPhonenumber);
		}
	}


}

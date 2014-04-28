/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: R2RulesBean.java 5 2013-08-05 10:00:45Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-08-05 12:00:45 +0200 (Mon, 05 Aug 2013) $
 * $Revision: 5 $
 */
package com.nortel.ema.swisscom.bal.rules.model.facts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.GregorianCalendar;


/*
 * This class holds all the facts that are created during rules
 * execution. The main reason for this class is to be able to
 * map incoming parameters to logical facts so that changes in 
 * the incoming parameters only have to be made at one place.
 */
public class R2RulesBean {

	// Add Property Change Support to automatically re-evaluate
	// the rule base when a property changes
	private final PropertyChangeSupport changes = new PropertyChangeSupport( this );

	public static final String EMPTY = "EMPTY";
	private String state = EMPTY;
	private String portal = EMPTY;
	private String businessNumber = EMPTY;
	private String businessTyp = EMPTY;
	private String openingHours = EMPTY;
	private String language = EMPTY;
	private String installedBase = EMPTY;
	private String segment = EMPTY;
	private String fineSegment = "0";
	private String subSegment = "0";
	private String special1 = EMPTY;
	private String anliegen = EMPTY;
	private String bereich = EMPTY;
	private String zusatz = EMPTY;
	private String routingId = EMPTY;
	private boolean hasProductClusterFixnet = false;
	private boolean hasProductClusterMobile = false;
	private boolean hasProductClusterInternet = false;
	private boolean hasProductClusterTV = false;
	private boolean hasProductClusterDataservice = false;
	private boolean hasProductClusterPBX = false;
	
	// Anliegen Menu Selection, selection variable and constants
	private String anliegenMenuSelected = EMPTY;
	public static final Long SMEBERATUNG = new Long(1010);
	public static final Long SMEKUNDENDIENST = new Long(1020);
	public static final Long SMESTOERUNGPBRDSL = new Long(1025);
	public static final Long SMESTOERUNG = new Long(1030);
	public static final Long SMEBERATUNGTRANSFER = new Long(1040);
	public static final Long SMEBERATUNGDSA = new Long(1045);
	public static final Long SMETRANSFERALM = new Long(1050);
	
	public static final Long RESBERATUNG = new Long(2010);
	public static final Long RESKUNDENDIENST = new Long(2020);
	public static final Long RESKUNDENDIENSTTRANSFER = new Long(2022);
	public static final Long RESRECHNUNG = new Long(2025);
	public static final Long RESRECHNUNGTRANSFER = new Long(2026);
	public static final Long RESSTOERUNG = new Long(2030);
	public static final Long RESTRANSFERALM = new Long(2040);
	
	public static final String ERROR = "error";
	
	// SME Nachtmenu constants
	private String nachtMenuSelected = EMPTY;
	public static final Long SME_NM_MOBILE = new Long(1710);
	public static final Long SME_NM_NACHRICHT = new Long(1720);
	public static final Long SME_NM_STOERUNG = new Long(1730);
	public static final Long SME_NM_TRANSFER = new Long(1740);
	
	// Product Menu Selection, selection variable and constants
	private String produktMenuSelected = EMPTY;
	public static final Long SMETELEFONIE = new Long(1510);
	public static final Long SMEINTERNET = new Long(1530);
	public static final Long SMEINTERNETMYSUPPORT = new Long(1535);
	public static final Long SMEPBX = new Long(1540);
	public static final Long SMEDATASERVICES = new Long(1550);
	public static final Long SMEOTHERS = new Long(1560);
	public static final Long SMERECHNUNG = new Long(1570);
	
	public static final Long RESFIXNET = new Long(2510);
	public static final Long RESBILLING = new Long(2520);
	public static final Long RESINTERNETBERATUNG = new Long(2530);
	public static final Long RESINTERNETSTOERUNG = new Long(2535);
	public static final Long RESINTERNETSTOERUNGDETAILQ = new Long(2536);
	public static final Long RESTV = new Long(2540);
	public static final Long RESPASSGEN = new Long(2550);
	public static final Long RESPASSWORDAGENT = new Long(2555);
	public static final Long RESMUTATION = new Long(2560);
	public static final Long RESOTHERS = new Long(2570);
	
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
		return ("R2RulesBean:\n"+
				" State: " +state+"\n"+
				" Portal: " +portal+"\n"+
				" businessNumber: " +businessNumber+"\n"+
				" businessTyp: "+businessTyp+"\n"+
				" openingHours: "+openingHours+"\n"+
				" language: "+language+"\n"+
				" installedBase: "+installedBase+"\n"+
				" segment: "+segment+"\n"+
				" fineSegment: "+fineSegment+"\n"+
				" subSegment: "+subSegment+"\n"+
				" special1: "+special1+"\n"+
				" anliegen: "+anliegen+"\n"+
				" bereich: "+bereich+"\n"+
				" zusatz: "+zusatz+"\n"+
				" routingId: "+routingId+"\n"+
				" PC-Fixnet: "+hasProductClusterFixnet+"\n"+
				" PC-Mobile: "+hasProductClusterMobile+"\n"+
				" PC-Internet: "+hasProductClusterInternet+"\n"+
				" PC-TV: "+hasProductClusterTV+"\n"+
				" PC-Dataservice: "+hasProductClusterDataservice+"\n"+
				" PC-PBX: "+hasProductClusterPBX+"\n"+
				" AnliegenMenuSelected: "+anliegenMenuSelected+"\n"+
				" ProductMenuSelected: "+produktMenuSelected+"\n");
	}
	
	public void addPropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.addPropertyChangeListener( listener );
	}
	public void removePropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.removePropertyChangeListener(listener);
	}
	
	// Constructor
	public R2RulesBean () {
		super();
		this.state = INITIALIZED;
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
	 * @return the bereich
	 */
	public String getBereich() {
		return bereich;
	}
	/**
	 * @param bereich the bereich to set
	 */
	public void setBereich(String bereich) {
		if ( bereich != null ) {
			final String oldBereich = this.bereich;
			this.bereich = bereich;
			this.changes.firePropertyChange("state", oldBereich, bereich);
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
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		if ( language != null ) {
			final String oldLanguage = this.language;
			this.language = language;
			this.changes.firePropertyChange("state",oldLanguage,language);
		}
	}
	/**
	 * @return the openingHours
	 */
	public String getOpeningHours() {
		return openingHours;
	}
	/**
	 * @param openingHours the openingHours to set
	 */
	public void setOpeningHours(String openingHours) {
		if ( openingHours != null ) {
			final String oldOpeningHours = this.openingHours;
			this.openingHours = openingHours;
			this.changes.firePropertyChange("state",oldOpeningHours,openingHours);
		}
	}
	/**
	 * @return the portal
	 */
	public String getPortal() {
		return portal;
	}
	/**
	 * @param portal the portal to set
	 */
	public void setPortal(String portal) {
		if ( portal != null ) {
			final String oldPortal = this.portal;
			this.portal = portal;
			this.changes.firePropertyChange("state",oldPortal,portal);
		}
	}
	/**
	 * @return the routingId
	 */
	public String getRoutingId() {
		return routingId;
	}
	/**
	 * @param routingId the routingId to set
	 */
	public void setRoutingId(String routingID) {
		if ( routingID != null ) {
			final String oldRoutingID = this.routingId;
			this.routingId = routingID;
			this.changes.firePropertyChange("state",oldRoutingID,routingID);
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
	 * @return the zusatz
	 */
	public String getZusatz() {
		return zusatz;
	}
	/**
	 * @param zusatz the zusatz to set
	 */
	public void setZusatz(String zusatz) {
		if ( zusatz != null ) {
			final String oldZusatz = this.zusatz;
			this.zusatz = zusatz;
			this.changes.firePropertyChange("state",oldZusatz,zusatz);
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
	/**
	 * @return the anliegenMenuSelected
	 */
	public String getAnliegenMenuSelected() {
		return anliegenMenuSelected;
	}
	/**
	 * @param anliegenMenuSelected the anliegenMenuSelected to set
	 */
	public void setAnliegenMenuSelected(String anliegenMenuSelected) {
		if ( anliegenMenuSelected != null ) {
			final String oldAnliegenMenuSelected = this.anliegenMenuSelected;
			this.anliegenMenuSelected = anliegenMenuSelected;
			this.changes.firePropertyChange("state",oldAnliegenMenuSelected,anliegenMenuSelected);
		}
	}
	/**
	 * @return the produktMenuSelected
	 */
	public String getProduktMenuSelected() {
		return produktMenuSelected;
	}
	/**
	 * @param produktMenuSelected the produktMenuSelected to set
	 */
	public void setProduktMenuSelected(String produktMenuSelected) {
		if ( produktMenuSelected != null ) {
			final String oldProduktMenuSelected = this.produktMenuSelected;
			this.produktMenuSelected = produktMenuSelected;
			this.changes.firePropertyChange("state",oldProduktMenuSelected,produktMenuSelected);
		}
	}
	
	/**
	 * @return the nachtMenuSelected
	 */
	public String getNachtMenuSelected() {
		return nachtMenuSelected;
	}

	/**
	 * @param nachtMenuSelected the nachtMenuSelected to set
	 */
	public void setNachtMenuSelected(String nachtMenuSelected) {
		if ( nachtMenuSelected != null ) {
			final String oldNachtMenuSelected = this.nachtMenuSelected;
			this.nachtMenuSelected = nachtMenuSelected;
			this.changes.firePropertyChange("state",oldNachtMenuSelected,nachtMenuSelected);
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
	
	
	
}

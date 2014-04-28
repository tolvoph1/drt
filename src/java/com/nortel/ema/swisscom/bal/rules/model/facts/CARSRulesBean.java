/*
 * (c)2011 Avaya. All Rights Reserved.
 *
 * $Id: CARSRulesBean.java 5 2013-08-05 10:00:45Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-08-05 12:00:45 +0200 (Mon, 05 Aug 2013) $
 * $Revision: 5 $
 */

package com.nortel.ema.swisscom.bal.rules.model.facts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/*
 * This class holds new properties for CARS, especially all the facts
 * in the overview workflows, but also potential facts from dialog workflows
 * like "balance > 0", etc.
 */
public class CARSRulesBean {

	// Add Property Change Support to automatically re-evaluate
	// the rule base when a property changes
	private final PropertyChangeSupport changes = new PropertyChangeSupport( this );

	public static final String EMPTY = "EMPTY";
	
	// State of Bean
	public static final String STATUS_UNINITIALIZED = "UNINIT";
	public static final String STATUS_INITIALIZED = "INIT";
	public static final String STATUS_VALUES = "VALUES";
	private String state = STATUS_UNINITIALIZED;
	
	// Member Variable to hold information
	private HashMap<String, Boolean> information;
	
	// Keys for HashMap containing boolean values
	public static final String STRUCTUREDCUSTOMER = "structuredCustomer";	// Ist Kunde strukturiert?
	public static final String DUNNINGPROCEDURE = "dunningProcedure";		// Hat der Kunde ein aktives Mahnverfahren?
	public static final String RESCUSTOMER = "resCustomer"; 				// Ist der Kunde RES?
	public static final String INTERACTIONHISTORYAVAILABLE = "interactionHistoryAvailable"; // Fehler beim Abfragen der Interaktionen
	public static final String CREDITLIMITINFOAVAILABLE = "creditLimitInfoAvailable"; // Info ueber Kreditlimite verfuegbar?
	public static final String BLOCK = "block";								// Ist der Kunde gesperrt?
	public static final String RECENTBLOCK = "recentBlock";			// Sperrung innerhalb der letzten 25 Tage?
	public static final String CREDITLIMITBLOCK = "creditLimitBlock";	// Hat der Kunde eine aktive KL Sperre?
	public static final String ANICONFIRMED = "aniConfirmed";		// ANI bestaetigt?
	public static final String DEBARRINGPENDING = "debarringPending";	// Ist ein pendenter Entsperrauftrag am Laufen?
	public static final String CREDITLIMITOVERVALUE = "creditLimitOverValue";	// Ist die Limite um X CHF oder mehr ueberschritten?
	public static final String CREDITLIMITRECENTPP = "creditLimitRecentPP";	// Zahlversprechen in den letzten 5 Tagen schon gemacht?
	public static final String QUICKPROCEDURE = "quickProcedure";		// Kunde in einem Schnellverfahren?
	public static final String DUNNINGLEVELMINOR = "dunningLevelMinor";	// Mahnstufe zwischen >=040x und <=046a?
	public static final String DUNNINGLEVELMAJOR = "dunningLevelMajor";	// Mahnstufe >046a?
	public static final String RECENTPAYMENTCHECK = "recentPaymentCheck";	// Payment Check in den letzten 15 Tagen benutzt?
	public static final String DUNNINGLETTER = "dunningLetter";		// Mahnbrief MS020a, MS022a, MS022b erhalten?
	public static final String RECENTDUNNINGLETTER = "recentDunningLetter";	// Mahnbrief in den letzten X Tagen erhalten?
	public static final String DUNNINGSMS = "dunningSMS";			// Gemahnte Rechnung mit SMS MS010a?
	public static final String RECENTDUNNINGSMS = "recentDunningSMS";	// MS010a innerhalb der letzten X Tage?
	public static final String PARTIALBLOCK = "partialBlock";		// Steht Kunde kurz vor Teilsperre (MS038a)?
	public static final String RECENTPARTIALBLOCK = "recentPartialBlock";	// MS038a innerhalb der letzten X Tage?
	public static final String PARTIALBLOCKRECENTPP = "partialBlockRecentPP";	// Zahlversprechen in den letzten X Tagen?
	public static final String CUSTOMEROVER18 = "customerOver18";		// Ist der Kunde ueber 18 Jahre alt?
	public static final String CREDITLIMIT = "creditLimit";  	// Hat der Kunde Kreditlimite?
	// HashSet that contains all possible attributes
	private Vector<String> carsAttributes = new Vector<String>();
	private void initCarsAttributes() {
		carsAttributes.add(STRUCTUREDCUSTOMER);
		carsAttributes.add(DUNNINGPROCEDURE);
		carsAttributes.add(RESCUSTOMER);
		carsAttributes.add(INTERACTIONHISTORYAVAILABLE);
		carsAttributes.add(CREDITLIMITINFOAVAILABLE);
		carsAttributes.add(BLOCK);
		carsAttributes.add(RECENTBLOCK);
		carsAttributes.add(CREDITLIMITBLOCK);
		carsAttributes.add(ANICONFIRMED);
		carsAttributes.add(DEBARRINGPENDING);
		carsAttributes.add(CREDITLIMITOVERVALUE);
		carsAttributes.add(CREDITLIMITRECENTPP);
		carsAttributes.add(QUICKPROCEDURE);
		carsAttributes.add(DUNNINGLEVELMINOR);
		carsAttributes.add(DUNNINGLEVELMAJOR);
		carsAttributes.add(RECENTPAYMENTCHECK);
		carsAttributes.add(DUNNINGLETTER);
		carsAttributes.add(RECENTDUNNINGLETTER);
		carsAttributes.add(DUNNINGSMS);
		carsAttributes.add(RECENTDUNNINGSMS);
		carsAttributes.add(PARTIALBLOCK);
		carsAttributes.add(RECENTPARTIALBLOCK);
		carsAttributes.add(PARTIALBLOCKRECENTPP);
		carsAttributes.add(CUSTOMEROVER18);
		carsAttributes.add(CREDITLIMIT);
		// Init all attributes in HashMap
		for (int i=0; i<carsAttributes.size();++i) {
			information.put(carsAttributes.elementAt(i), false);
		}
	}
	
	// Flag if customer has credit limit, evaluation is done on Setter method
	
	
	// Additional attributes that are not part of the HashMap (Strings)
	
	private String currentBalance = EMPTY; // Summe aller offenen Rechnungen
	private String remainingCredit = EMPTY; // Restguthaben
	private String minimumTopUpAmount = EMPTY; // Mindestaufladebetrag
	private String unbilledCost = EMPTY; // Laufende Monatskosten
	private String limitExcess = EMPTY; // Betrag Kreditlimitenueberschreitung
	
		
	public void addPropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.addPropertyChangeListener( listener );
	}
	public void removePropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.removePropertyChangeListener(listener);
	}
	
	// Constructor
	public CARSRulesBean () {
		super();
		this.state = STATUS_INITIALIZED;
		this.information = new HashMap<String, Boolean>();
		initCarsAttributes();
	}
	
	/**
	 * Takes a comma-separated string with facts that are true and adds
	 * them to the HashMap with a value of (Boolean)true
	 * @param carsStatus Comma-separated string with true facts
	 */
	public void initialize(String carsStatus) {
		if (carsStatus != null) {
			final HashMap<String, Boolean> oldInformation = information;
			for ( String s: carsStatus.split(",")) {
				information.put(s.toString(),true);
			}
			this.changes.firePropertyChange("state", oldInformation, information);
		}
	}
	
	public String toString() {
		// return only elements that are true
		Iterator it = information.keySet().iterator();
		StringBuffer output = new StringBuffer();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (information.get(key)) {
				output.append(key+" ");
			}
		}
		return output.toString();
	}
	
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		if (state != null) {
			final String oldState = this.state;
			this.state = state;
			this.changes.firePropertyChange("state", oldState, state);
		}
	}
	
	// Getter Methods for attributes that are set by initialize method, no explicit setter
	// methods for these
	public Boolean getBlock() {
		return information.get(BLOCK);
	}
	public Boolean getCreditLimitBlock() {
		return information.get(CREDITLIMITBLOCK);
	}
	public Boolean getCreditLimitOverValue() {
		return information.get(CREDITLIMITOVERVALUE);
	}
	public Boolean getCreditLimitRecentPP() {
		return information.get(CREDITLIMITRECENTPP);
	}
	public Boolean getDebarringPending() {
		return information.get(DEBARRINGPENDING);
	}
	public Boolean getDunningLetter() {
		return information.get(DUNNINGLETTER);
	}
	public Boolean getDunningLevelMajor() {
		return information.get(DUNNINGLEVELMAJOR);
	}
	public Boolean getDunningLevelMinor() {
		return information.get(DUNNINGLEVELMINOR);
	}
	public Boolean getDunningProcedure() {
		return information.get(DUNNINGPROCEDURE);
	}
	public Boolean getDunningSMS() {
		return information.get(DUNNINGSMS);
	}
	public Boolean getInteractionHistoryAvailable() {
		return information.get(INTERACTIONHISTORYAVAILABLE);
	}
	public Boolean getCreditLimitInfoAvailable() {
		return information.get(CREDITLIMITINFOAVAILABLE);
	}
	public Boolean getPartialBlock() {
		return information.get(PARTIALBLOCK);
	}
	public Boolean getPartialBlockRecentPP() {
		return information.get(PARTIALBLOCKRECENTPP);
	}
	public Boolean getQuickProcedure() {
		return information.get(QUICKPROCEDURE);
	}
	public Boolean getRecentBlock() {
		return information.get(RECENTBLOCK);
	}
	public Boolean getRecentDunningLetter() {
		return information.get(RECENTDUNNINGLETTER);
	}
	public Boolean getRecentDunningSMS() {
		return information.get(RECENTDUNNINGSMS);
	}
	public Boolean getRecentPartialBlock() {
		return information.get(RECENTPARTIALBLOCK);
	}
	public Boolean getRecentPaymentCheck() {
		return information.get(RECENTPAYMENTCHECK);
	}
	
	
	// Getter and Setter for additional attributes that are not set by initialize method
	public Boolean getAniConfirmed() {
		return information.get(ANICONFIRMED);
	}
	public void setAniConfirmed(String sAniConfirmed) {
		if (sAniConfirmed != null) {
			Boolean bAniConfirmed = Boolean.valueOf(sAniConfirmed);
			final Boolean oldAniConfirmed = information.get(ANICONFIRMED);
			information.put(ANICONFIRMED, bAniConfirmed);
			this.changes.firePropertyChange("state", oldAniConfirmed, bAniConfirmed);
		}
	}
	public Boolean getCreditLimit() {
		return information.get(CREDITLIMIT);
	}
	public void setCreditLimit(String sCreditLimit) {
		if (sCreditLimit != null) {
			Boolean bCreditLimit;
			if (sCreditLimit != null && !sCreditLimit.equals("0") && !sCreditLimit.equals("")) {
				bCreditLimit = true;
			} else {
				bCreditLimit = false;
			}
			final Boolean oldKreditlimite = information.get(CREDITLIMIT);
			information.put(CREDITLIMIT,bCreditLimit);
			this.changes.firePropertyChange("state", oldKreditlimite, bCreditLimit);
		} 
	}
	public String getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		if (currentBalance != null) {
			final String oldCurrentBalance = this.currentBalance;
			this.currentBalance = currentBalance;
			this.changes.firePropertyChange("state",oldCurrentBalance,currentBalance);
		}
	}
	
	public String getRemainingCredit() {
		return remainingCredit;
	}
	public void setRemainingCredit(String remainingCredit) {
		if (remainingCredit != null) {
			final String oldRemainingCredit = this.remainingCredit;
			this.remainingCredit = remainingCredit;
			this.changes.firePropertyChange("state",oldRemainingCredit,remainingCredit);
		}
	}
	
	
	public String getMinimumTopUpAmount() {
		return minimumTopUpAmount;
	}
	public void setMinimumTopUpAmount(String minimumTopUpAmount) {
		if (minimumTopUpAmount != null) {
			final String oldMinimumTopUpAmount = this.minimumTopUpAmount;
			this.minimumTopUpAmount = minimumTopUpAmount;
			this.changes.firePropertyChange("state",oldMinimumTopUpAmount,minimumTopUpAmount);
		}
	}
	public String getUnbilledCost() {
		return unbilledCost;
	}
	public void setUnbilledCost(String unbilledCost) {
		if (unbilledCost != null) {
			final String oldUnbilledCost = this.unbilledCost;
			this.unbilledCost = unbilledCost;
			this.changes.firePropertyChange("state",oldUnbilledCost,unbilledCost);
		}
	}
	public String getLimitExcess() {
		return limitExcess;
	}
	public void setLimitExcess(String limitExcess) {
		if (limitExcess != null) {
			final String oldLimitExcess = this.limitExcess;
			this.limitExcess = limitExcess;
			this.changes.firePropertyChange("state",oldLimitExcess,limitExcess);
		}
	}
	
	public Boolean getCustomerOver18() {
		return information.get(CUSTOMEROVER18);
	}
	public void setCustomerOver18(String sCustomerOver18) {
		if (sCustomerOver18 != null) {
			Boolean bCustomerOver18 = Boolean.valueOf(sCustomerOver18);
			final Boolean oldCustomerOver18 = information.get(CUSTOMEROVER18);
			information.put(CUSTOMEROVER18,bCustomerOver18);
			this.changes.firePropertyChange("state",oldCustomerOver18,bCustomerOver18);
		}
	}
	public Boolean getResCustomer() {
		return information.get(RESCUSTOMER);
	}
	public void setResCustomer(String sResCustomer) {
		if (sResCustomer != null) {
			Boolean bResCustomer = Boolean.valueOf(sResCustomer);
			final Boolean oldResCustomer = information.get(RESCUSTOMER);
			information.put(RESCUSTOMER,bResCustomer);
			this.changes.firePropertyChange("state", oldResCustomer, bResCustomer);
		}
	}
	public Boolean getStructuredCustomer() {
		return information.get(STRUCTUREDCUSTOMER);
	}
	public void setStructuredCustomer(String sStructuredCustomer) {
		if (sStructuredCustomer != null) {
			Boolean bStructuredCustomer = Boolean.valueOf(sStructuredCustomer);
			final Boolean oldStructuredCustomer = information.get(STRUCTUREDCUSTOMER);
			information.put(STRUCTUREDCUSTOMER,bStructuredCustomer);
			this.changes.firePropertyChange("state",oldStructuredCustomer,bStructuredCustomer);
		}
	}
}

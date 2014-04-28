/*
 * (c)2010 Avaya. All Rights Reserved.
 *
 * $Id: BusinessNumberBean.java 5 2013-08-05 10:00:45Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-08-05 12:00:45 +0200 (Mon, 05 Aug 2013) $
 * $Revision: 5 $
 */
package com.nortel.ema.swisscom.bal.rules.model.facts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/*
 * This class is meant to carry only one attribute, a "business number variant".
 * It should be used in the rules and set in a preliminary rule that checks for
 * a number of business numbers.
 * E.g. if your portal has 5 business numbers A,B,C,D and E the following might
 * be set by a rule initialized with a higher salience then every other rule:
 * when
 *   CallProfile.getBusinessNumber() = A OR
 *   CallProfile.getBusinessNumber() = B OR
 *   CallProfile.getBusinessNumber() = C
 *   bnBean : BusinessNumberBean()
 * then
 *   bnBean.setVariant("ABC);
 * end
 * 
 * another rule might contain the following
 * when
 *   CallProfile.getBusinessNumber() = D OR
 *   CallProfile.getBusinessNumber() = E
 *   bnBean : BusinessNumberBean()
 * then
 *   bnBean.setVariant("DE);
 * end
 * 
 * In the actual transfer rule you can then easily check for one of the variants
 * when
 *   BusinessNumberBean( variant == "ABC" )
 * then
 *   // do something
 * end
 * 
 * or
 * 
 * when
 *   BusinessNumberBean ( variant == "DE" )
 * then
 *   // do somethine else
 * end
 * 
 * If a new business number is added to one of the variants it just has to be done in one place
 *  *
 */
public class BusinessNumberBean {

	// Add Property Change Support to automatically re-evaluate
	// the rule base when a property changes
	private final PropertyChangeSupport changes = new PropertyChangeSupport( this );

	public static final String EMPTY = "EMPTY";
	public static final String VARIANT_A = "A";
	public static final String VARIANT_B = "B";
	public static final String VARIANT_C = "C";
	public static final String VARIANT_D = "D";
	public static final String VARIANT_E = "E";
	public static final String VARIANT_F = "F";
	public static final String VARIANT_G = "G";
	public static final String VARIANT_H = "H";
	
	private String variant = EMPTY;
	private String businessNumber = EMPTY;
	

	public String toString() {
		return ("BusinessNumberBean:\n"+
				" Variant: " +variant+
				" BusinessNumber: "+businessNumber+"\n");
	}

	public void addPropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.addPropertyChangeListener( listener );
	}
	public void removePropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.removePropertyChangeListener(listener);
	}

	// Constructor
	public BusinessNumberBean () {
		super();
	}

	/**
	 * @return the variant
	 */
	public String getVariant() {
		return variant;
	}
	/**
	 * @param variant the variant to set.
	 */
	public void setVariant(String variant) {
		if ( variant != null ) {
			final String oldVariant = this.variant;
			this.variant = variant;
			this.changes.firePropertyChange("state",oldVariant,variant);
		}
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		if ( businessNumber != null ) {
			final String oldBusinessNumber = this.businessNumber;
			this.businessNumber = businessNumber;
			this.changes.firePropertyChange("state",oldBusinessNumber,businessNumber);
		}
		
	}

}

/*
 * (c)2010 Avaya. All Rights Reserved.
 *
 * $Id: MenuSelectionBean.java 5 2013-08-05 10:00:45Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-08-05 12:00:45 +0200 (Mon, 05 Aug 2013) $
 * $Revision: 5 $
 */
package com.nortel.ema.swisscom.bal.rules.model.facts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/*
 * This class is meant to carry the selected menu options for all menus in the callflow
 * The idea is to have attributes for each menu level that need to be populated by
 * a set of initialization rules inside each individual rules file
 * 
 * This is required since the actual menu selection is stored in the call Profile using
 * the name of rules file as key and the menu option name from the database as value, e.g.
 * Key="oneIVR/vp5resNPAlmMenu",Value="RESNPALM1"
 * 
 * There will be 5 menu level attributes defined here in order to allow storing more than 2
 * menus for evaluation by the rules, e.g. if a 2 level menu leads to self service that
 * in turn offers a menu. Whoever modifies the transfer rules needs to cater for this
 * setup and put in the right level for later evaluation
 * 
 */
public class MenuSelectionBean {

	// Add Property Change Support to automatically re-evaluate
	// the rule base when a property changes
	private final PropertyChangeSupport changes = new PropertyChangeSupport( this );

	public static final String EMPTY = "EMPTY";
	private String menuA = EMPTY;
	private String menuB = EMPTY;
	private String menuC = EMPTY;
	private String menuD = EMPTY;
	private String menuE = EMPTY;
	private String menuF = EMPTY;
	private String menuG = EMPTY;
	private String menuH = EMPTY;


	public String toString() {
		return ("MenuSelectionBean:\n"+
				" MenuA: " +menuA+"\n"+
				" MenuB: " +menuB+"\n"+
				" MenuC: " +menuC+"\n"+
				" MenuD: " +menuD+"\n"+
				" MenuE: " +menuE+"\n"+
				" MenuE: " +menuF+"\n"+
				" MenuE: " +menuG+"\n"+
				" MenuE: " +menuH+"\n");
	}

	public void addPropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.addPropertyChangeListener( listener );
	}
	public void removePropertyChangeListener (final PropertyChangeListener listener ) {
		this.changes.removePropertyChangeListener(listener);
	}

	// Constructor
	public MenuSelectionBean () {
		super();
	}

	/**
	 * @return the menuA
	 */
	public String getMenuA() {
		return menuA;
	}
	/**
	 * @return the menuB
	 */
	public String getMenuB() {
		return menuB;
	}
	/**
	 * @return the menuC
	 */
	public String getMenuC() {
		return menuC;
	}
	/**
	 * @return the menuD
	 */
	public String getMenuD() {
		return menuD;
	}
	/**
	 * @return the menuE
	 */
	public String getMenuE() {
		return menuE;
	}
	/**
	 * @return the menuF
	 */
	public String getMenuF() {
		return menuF;
	}
	/**
	 * @return the menuG
	 */
	public String getMenuG() {
		return menuG;
	}
	/**
	 * @return the menuH
	 */
	public String getMenuH() {
		return menuH;
	}
	/**
	 * @param menuA the menuA to set.
	 */
	public void setMenuA(String menuA) {
		if ( menuA != null ) {
			final String oldMenuA = this.menuA;
			this.menuA = menuA;
			this.changes.firePropertyChange("state",oldMenuA,menuA);
		}
	}
	/**
	 * @param menuB the menuB to set.
	 */
	public void setMenuB(String menuB) {
		if ( menuB != null ) {
			final String oldMenuB = this.menuB;
			this.menuB = menuB;
			this.changes.firePropertyChange("state",oldMenuB,menuB);
		}
	}
	/**
	 * @param menuC the menuC to set.
	 */
	public void setMenuC(String menuC) {
		if ( menuC != null ) {
			final String oldMenuC = this.menuC;
			this.menuC = menuC;
			this.changes.firePropertyChange("state",oldMenuC,menuC);
		}
	}
	
	/**
	 * @param menuD the menuD to set.
	 */
	public void setMenuD(String menuD) {
		if ( menuD != null ) {
			final String oldMenuD = this.menuD;
			this.menuD = menuD;
			this.changes.firePropertyChange("state",oldMenuD,menuD);
		}
	}
	
	/**
	 * @param menuE the menuE to set.
	 */
	public void setMenuE(String menuE) {
		if ( menuE != null ) {
			final String oldMenuE = this.menuE;
			this.menuE = menuE;
			this.changes.firePropertyChange("state",oldMenuE,menuE);
		}
	}
	
	/**
	 * @param menuF the menuF to set.
	 */
	public void setMenuF(String menuF) {
		if ( menuF != null ) {
			final String oldMenuF = this.menuF;
			this.menuF = menuF;
			this.changes.firePropertyChange("state",oldMenuF,menuF);
		}
	}
	/**
	 * @param menuG the menuG to set.
	 */
	public void setMenuG(String menuG) {
		if ( menuG != null ) {
			final String oldMenuG = this.menuG;
			this.menuG = menuG;
			this.changes.firePropertyChange("state",oldMenuG,menuG);
		}
	}
	/**
	 * @param menuH the menuH to set.
	 */
	public void setMenuH(String menuH) {
		if ( menuH != null ) {
			final String oldMenuH = this.menuH;
			this.menuH = menuH;
			this.changes.firePropertyChange("state",oldMenuH,menuH);
		}
	}
}

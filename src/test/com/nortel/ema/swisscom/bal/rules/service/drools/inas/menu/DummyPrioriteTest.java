/*
 * (c)2008 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: DummyPrioriteTest.java 78 2013-09-09 15:18:53Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $
 * $Revision: 78 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools.inas.menu;


import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.nortel.ema.swisscom.bal.menu.model.ExtendedMenuOption;
import com.nortel.ema.swisscom.bal.rules.persistence.file.FileRulesDAOImpl;
import com.nortel.ema.swisscom.bal.rules.service.drools.RulesServiceDroolsImpl;
import static com.nortel.ema.swisscom.bal.rules.service.drools.RulesTestConstants.*;
import com.nortel.ema.swisscom.bal.vo.model.CallProfile;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProfile;
import com.nortel.ema.swisscom.bal.vo.model.ServiceConfigurationMap;

/**
 *
 * @author $Author: tolvoph1 $
 * @version $Revision: 78 $ ($Date: 2013-09-09 17:18:53 +0200 (Mon, 09 Sep 2013) $)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DummyPrioriteTest extends TestCase {

	private static final String LOG4J_PATH = USER_DIR + "/src/test/conf/log4j.xml";

	private static RulesServiceDroolsImpl droolsImpl;
	private static final boolean LOGGING_ON = false;
	private static final boolean LOGOUTPUT_ON = true;
	private static final String RULESFILENAME = "inas/dummy-priorite";

	private static ServiceConfigurationMap serviceConfiguration = new ServiceConfigurationMap();

	static {
		if (LOGGING_ON) DOMConfigurator.configure(LOG4J_PATH);
		else Logger.getRootLogger().setLevel(Level.OFF);
		droolsImpl = new RulesServiceDroolsImpl();
		final FileRulesDAOImpl fileRulesDAOImpl = new FileRulesDAOImpl();
		fileRulesDAOImpl.setRulesFolderPath(RULES_FOLDER_PATH);
		droolsImpl.setRulesDAO(fileRulesDAOImpl);       
	}

	/**
	 * @param args the arguments
	 */
	 public static void main(final String[] args) {
		 junit.textui.TestRunner.run(DummyPrioriteTest.class);
	 }

	 public final void test_Menu() throws Exception {
		 // Customer Profile
		 CustomerProfile customerProfile = new CustomerProfile();
		 // Customer Products
		 CustomerProducts customerProducts = new CustomerProducts();
		 // Call Profile
		 CallProfile callProfile = new CallProfile();

		 // Call the Rules
		 List<ExtendedMenuOption> menuOptions = droolsImpl.getExtendedMenuOptions(RULESFILENAME, customerProfile, customerProducts, null, callProfile,
				 "g", serviceConfiguration);

		 if (LOGOUTPUT_ON) {
			 for (int i=0; i<menuOptions.size();i++) {
				 ExtendedMenuOption current = menuOptions.get(i);
				 System.out.println("Menuoption("+i+")");
				 System.out.println("Name:"+current.getName());
				 System.out.println("Action:"+current.getAction());
				 System.out.println("Prompt:"+current.getPrompt());
				 System.out.println("Position:"+current.getPosition());
				 System.out.println("Skill:"+current.getSkill());
				 System.out.println("EmergencyText:"+current.getEmergencyText());
				 System.out.println("RoutingID:"+current.getRoutingID());
				 System.out.println("Reasoncode:"+current.getReasonCode());
				 System.out.println("==========================");
			 }
		 }
		 // Check results
		 assertTrue(menuOptions.size()==1);
		 
		 assertEquals("DUMMYCONTINUE", menuOptions.get(0).getName());
		 assertEquals("CONTINUE", menuOptions.get(0).getAction());
		 assertEquals("leerprompt", menuOptions.get(0).getPrompt());
		 assertEquals("", menuOptions.get(0).getSkill());
		 assertEquals("", menuOptions.get(0).getEmergencyText());
		 assertEquals("", menuOptions.get(0).getRoutingID());
		 assertEquals("", menuOptions.get(0).getReasonCode());
		 
		 
	 }    
}


/*
 * (c)2007 Nortel Networks Limited. All Rights Reserved.
 *
 * $Id: RulesTestConstants.java 231 2014-04-07 07:25:35Z tolvoph1 $
 * $Author: tolvoph1 $
 * $Date: 2014-04-07 09:25:35 +0200 (Mon, 07 Apr 2014) $
 * $Revision: 231 $
 */
package com.nortel.ema.swisscom.bal.rules.service.drools;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.nortel.ema.swisscom.bal.menu.model.ExtendedMenuOption;
import com.nortel.ema.swisscom.bal.rules.model.StateEngineRulesResult;
import com.nortel.ema.swisscom.bal.rules.model.TransferRulesState;
import com.nortel.ema.swisscom.bal.vo.model.CustomerProducts.Product;

public class RulesTestConstants extends TestCase {

	// Base path to the rules, change if testing rules in another location
	public static final String USER_DIR = System.getProperty("user.dir");
	public static final String RULES_FOLDER_PATH = USER_DIR + "/src/rules";

	// SME NewIVR menu constants that replace the old vp2sme and oneIVR/vp5sme ones
	public static final String RULES_SME_ANLIEGENMENU = "sme/vp5smeAnliegenMenu";
	public static final String SME_ANLIEGENMENU = "smeAnliegenMenu";

	public static final String RULES_SME_NACHTMENU = "sme/vp5smeNachtMenu";
	public static final String SME_NACHTMENU = "smeNachtMenu";

	public static final String RULES_SME_NPALMMENU_MOBILE = "sme/vp5smeNPAlmMenuMobile";
	public static final String SME_NPALMMENU_MOBILE = "smeNPAlmMenuMobile";

	public static final String RULES_SME_NPALMMENU_FIXNET = "sme/vp5smeNPAlmMenuFixnet";
	public static final String SME_NPALMMENU_FIXNET = "smeNPAlmMenuFixnet";

	public static final String RULES_SME_NPPRMMENU_MOBILE = "sme/vp5smeNPPrmMenuMobile";
	public static final String SME_NPPRMMENU_MOBILE = "smeNPPrmMenuMobile";

	public static final String RULES_SME_NPPRMMENU_FIXNET = "sme/vp5smeNPPrmMenuFixnet";
	public static final String SME_NPPRMMENU_FIXNET = "smeNPPrmMenuFixnet";

	public static final String RULES_SME_PRODUKTMENU = "sme/vp5smeProduktMenu";
	public static final String SME_PRODUKTMENU = "smeProduktMenu";

	public static final String RULES_SME_PBRDSLMENU = "sme/vp5smePBRDSLMenu";
	public static final String SME_PBRDSLMENU = "smePBRDSLMenu";

	public static final String ONEIVRRES_ANLIEGEN_MENU_FIXNET = "oneIVR/vp5resAnliegenMenuFixnet";
	public static final String ONEIVRRES_ANLIEGEN_MENU_FIXNET_NACHT = "oneIVR/vp5resAnliegenMenuFixnetNacht";
	public static final String ONEIVRRES_PRODUKT_MENU_FIXNET = "oneIVR/vp5resProduktMenuFixnet";
	public static final String ONEIVRRES_PRODUKT_MENU_FIXNET_2200 = "oneIVR/vp5resProduktMenuFixnet2200";
	public static final String ONEIVRRES_ANLIEGEN_MENU_MOBILE = "oneIVR/vp5resAnliegenMenuMobile";
	public static final String ONEIVRRES_ANLIEGEN_MENU_MOBILE_NACHT = "oneIVR/vp5resAnliegenMenuMobileNacht";
	public static final String ONEIVRRES_ANLIEGEN_MENU_MOBILE_CBU = "oneIVR/vp5resAnliegenMenuMobileCBU";
	public static final String ONEIVRRES_PRODUKT_MENU_MOBILE = "oneIVR/vp5resProduktMenuMobile";
	public static final String ONEIVRRES_PRODUKT_MENU_MOBILE_2200 = "oneIVR/vp5resProduktMenuMobile2200";
	public static final String ONEIVRRES_ANLIEGEN_MENU_EASY = "oneIVR/vp5resAnliegenMenuEasy";
	public static final String ONEIVRRES_PRODUKT_MENU_EASY = "oneIVR/vp5resProduktMenuEasy";
	public static final String ONEIVRRES_TRANSFER = "oneIVR/vp5resTransfer";
	public static final String ONEIVRRES_MIGROSMOBILEMENU = "oneIVR/vp5resMigrosMobileMenu";
	public static final String ONEIVRRES_NEWPRODUCT_MENU_ALM_MOBILE = "oneIVR/vp5resNPAlmMenuMobile";
	public static final String ONEIVRRES_NEWPRODUCT_MENU_ALM_FIXNET = "oneIVR/vp5resNPAlmMenuFixnet";
	public static final String ONEIVRRES_NEWPRODUCT_MENU_ALM_EASY = "oneIVR/vp5resNPAlmMenuEasy";
	public static final String ONEIVRRES_NEWPRODUCT_MENU_PM = "oneIVR/vp5resNPPrmMenu";
	// Migros
	public static final String ONEIVRRES_ANLIEGEN_MENU_MIGROS = "oneIVR/vp5resAnliegenMenuMigros";
	public static final String ONEIVRRES_PRODUKT_MENU_MIGROS = "oneIVR/vp5resProduktMenuMigros";
	// CFS
	public static final String ONEIVRRES_ANLIEGEN_MENU_CFS_CUSTOMER = "oneIVR/vp5resAnliegenMenuCFSCustomer";
	// OnlineShop
	public static final String ONEIVRRES_ANLIEGEN_MENU_ONLINESHOP = "oneIVR/vp5resAnliegenMenuOnlineShop";

	public static final String CPK_ONEIVRRES_ANLIEGENMENU = "oneIVRresAnliegenMenu";
	public static final String CPK_ONEIVRRES_PRODUKTMENU = "oneIVRresProduktMenu";
	public static final String CPK_ONEIVRRES_MIGROSMOBILEANLIEGENMENU = "oneIVRresMigrosMenuLevel1";
	public static final String CPK_ONEIVRRES_MIGROSMOBILEPRODUKTMENU = "oneIVRresMigrosMenuLevel2";
	public static final String CPK_ONEIVRRES_THLMENU = "oneIVRresTHLMenu";
	public static final String CPK_ONEIVRRES_TASMENU = "oneIVRresTASMenu";
	public static final String CPK_ONEIVRRES_TASMENU_LEVEL2 = "oneIVRresTASMenuLevel2";
	public static final String CPK_ONEIVRRES_TASMENU_LEVEL3 = "oneIVRresTASMenuLevel3";
	public static final String CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_FIXNET = "oneIVRresNPAlmMenuFixnet";
	public static final String CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_MOBILE = "oneIVRresNPAlmMenuMobile";
	public static final String CPK_ONEIVRRES_NEWPRODUCTMENU_ALM_EASY = "oneIVRresNPAlmMenuEasy";
	public static final String CPK_ONEIVRRES_NEWPRODUCTMENU_PRM = "oneIVRresNPPrmMenu";
	// CBU Rules Files
	// Menu
	public static final String CBU_MENU_0800724724_LEVEL1 = "vp5cbu-0800724724-menu-level1";
	public static final String CBU_MENU_0800724724_LEVEL2 = "vp5cbu-0800724724-menu-level2";
	public static final String CBU_MENU_0080072472424_LEVEL1 = "vp5cbu-0080072472424-menu-level1";
	public static final String CBU_MENU_0080072472424_LEVEL2 = "vp5cbu-0080072472424-menu-level2";
	public static final String CBU_MENU_0800724001_LEVEL1 = "vp5cbu-0800724001-menu-level1";
	public static final String CBU_MENU_0800724001_LEVEL2 = "vp5cbu-0800724001-menu-level2";
	public static final String CBU_MENU_0800724002_LEVEL1 = "vp5cbu-0800724002-menu-level1";
	public static final String CBU_MENU_0800724002_LEVEL2 = "vp5cbu-0800724002-menu-level2";
	public static final String CBU_MENU_080072400202_LEVEL1 = "vp5cbu-080072400202-menu-level1";
	public static final String CBU_MENU_080072400202_LEVEL2 = "vp5cbu-080072400202-menu-level2";
	public static final String CBU_MENU_0442946666_LEVEL1 = "vp5cbu-0442946666-menu-level1";
	public static final String CBU_MENU_0442946666_LEVEL2 = "vp5cbu-0442946666-menu-level2";
	public static final String CBU_MENU_0800724008_LEVEL1 = "vp5cbu-0800724008-menu-level1";
	public static final String CBU_MENU_0800724008_LEVEL2 = "vp5cbu-0800724008-menu-level2";
	public static final String CBU_MENU_0800765400_LEVEL1 = "vp5cbu-0800765400-menu-level1";
	public static final String CBU_MENU_0900333221_LEVEL1 = "vp5cbu-0900333221-menu-level1";
	public static final String CBU_MENU_0800724003_LEVEL1 = "vp5cbu-0800724003-menu-level1";
	public static final String CBU_MENU_0800724003_LEVEL2 = "vp5cbu-0800724003-menu-level2";
	public static final String CBU_MENU_0800824825_LEVEL1 = "vp5cbu-0800824825-menu-level1";
	public static final String CBU_MENU_0800724011_LEVEL1 = "vp5cbu-0800724011-menu-level1";
	
	// Grobsegmente
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
	public static final String SUBSEGMENT_VIP = "12";			// Premium & Prestige
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

	// Product Constants for Fixnet Cluster
	public static final Product PR_ECONOMYLINE = new Product("EconomyLine","","","","","","","","","","","","","");
	public static final Product PR_MULTILINEISDN = new Product("MultiLine ISDN","","","","","","","","","","","","","");
	public static final Product PR_MULTILINEISDN_MSNNUMMER = new Product ("MultiLine ISDN|MSN-Nummer","","","","","","","","","","","","","");
	public static final Product PR_BUSINESSLINEISDN = new Product ("BusinessLine ISDN","","","","","","","","","","","","","");
	public static final Product PR_BUSINESSLINEISDN_DURCHWAHLRANGE = new Product ("BusinessLine ISDN|Durchwahl-Range","","","","","","","","","","","","","");
	public static final Product PR_BUSINESSLINE_BASISANSCHLUSS = new Product ("BusinessLine Basisanschluss","","","","","","","","","","","","","");
	public static final Product PR_BUSINESSLINE_PRIMAERANSCHLUSS = new Product ("BusinessLine Primäranschluss","","","","","","","","","","","","","");
	public static final Product PR_BUSINESSNUMBERS = new Product ("Business Numbers","","","","","","","","","","","","","");
	public static final Product PR_BUSINESSNUMBERS_BNUGRUNDANGEBOT_18XY = new Product ("Business Numbers|BNU-Grundangebot 18xy","","","","","","","","","","","","","");
	// Product Constants for Internet Cluster	
	public static final Product PR_CYBERNETDSL = new Product ("Cybernet DSL (Cybernet-Flag)","","","","","","","","","","","","","");
	public static final Product PR_BLUEWIN_PHONE = new Product ("Bluewin Phone","","","","","","","","","","","","","");
	public static final Product PR_FX_BLUEWIN_DSL = new Product ("FX Bluewin DSL","","","","","","","","","","","","","");
	public static final Product PR_FX_BLUEWIN_DIALUP = new Product ("FX Bluewin Dial-Up","","","","","","","","","","","","","");
	public static final Product PR_FX_BLUEWIN_NAKEDACCOUNT = new Product ("FX Bluewin nakedAccount","","","","","","","","","","","","","");
	public static final Product PR_DSL_MOBILE = new Product ("DSL_Mobile","","","","","","","","","","","","","");
	public static final Product PR_VPN_PROFESSIONELL = new Product ("VPN-Professionell","","","","","","","","","","","","","");
	public static final Product PR_DSL_PROFESSIONELL = new Product ("DSL-Professionell","","","","","","","","","","","","","");
	public static final Product PR_FX_BLUEWIN_DSL_SAT = new Product ("FX Bluewin DSL","","","","","","","","SAT","","","","","");
	public static final Product PR_FX_BLUEWIN_DSL_FTTH = new Product ("FX Bluewin DSL","","","","","","","","FTTH","","","","","");
	public static final Product PR_WIRELESS_HOME_CONNECTION = new Product("Wireless Home Connection", "", "", "", "", "", "", "", "", "", "","","","");
	public static final Product PR_BUI_ADVANCED = new Product("BUI Advanced", "", "", "", "", "", "", "", "", "", "","","","");
	public static final Product PR_IP_PLUS_BUI = new Product("IP-Plus Business Internet", "", "", "", "", "", "", "", "", "", "","","","");

	// Constants for Informatikdienste
	public static final Product PR_HOSTED_EXCHANGE_PROFESSIONAL_2 = new Product ("Hosted Exchange Professional 2.0","","","","","","","","","","","","","");
	public static final Product PR_HOSTED_EXCHANGE_PROFESSIONELL = new Product ("Hosted Exchange Professionell","","","","","","","","","","","","","");
	public static final Product PR_BUSINESS_INTERNET_LIGHT = new Product ("Business Internet light","","","","","","","","","","","","","");
	public static final Product PR_BUSINESS_INTERNET_STANDARD = new Product ("Business Internet standard","","","","","","","","","","","","","");
	public static final Product PR_ONLINE_BACKUP_PROFESSIONAL = new Product ("Online Backup Professional","","","","","","","","","","","","","");
	public static final Product PR_SHARED_OFFICE_PROFESSIONAL = new Product ("Shared Office Professional","","","","","","","","","","","","","");
	public static final Product PR_WEBHOSTING = new Product ("Webhosting","","","","","","","","","","","","","");
	public static final Product PR_KMUOFFICE = new Product("KMU Office", "", "", "", "", "", "", "", "", "", "", "", "","");

	// Product Constants for Bluewin TV Cluster	
	public static final Product PR_BLUEWIN_TV = new Product ("BluewinTV","","","","","","","","","","","","","");

	// Product Constats for AllIP Cluster
	public static final Product PR_ALL_TRIO_BUNDLE = new Product ("ALL:Trio Bundle","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_DUO_BUNDLE = new Product ("ALL:Duo Bundle","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_FIBERLINE_MINI = new Product ("ALL:Fiberline Mini","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_FIBERLINE_TV_BASIC = new Product ("ALL:Fiberline TV Basic","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_CASATRIO_BASIC = new Product ("ALL:Casa Trio Basic","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_CASATRIO_MINI = new Product ("ALL:Casa Trio Mini","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_HOMEENTERTAINMENT_3STAR = new Product ("ALL:Home & Entertainment 3 Star","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_HOMEENTERTAINMENT_4STAR = new Product ("ALL:Home & Entertainment 4 Star","","","","ALL IP","","","","","","","","","");
	public static final Product PR_ALL_HOMEENTERTAINMENT_5STAR = new Product ("ALL:Home & Entertainment 5 Star","","","","ALL IP","","","","","","","","","");

	// Product Constants for PBX Cluster	
	public static final Product PR_PBX_MIETE = new Product ("PBX Miete","","","","","","","","","","","","","");
	public static final Product PR_PBX_KAUF = new Product ("PBX Kauf","","","","","","","","","","","","","");
	public static final Product PR_PBX_WARTUNGSVERTRAG = new Product ("PBX Wartungsvertrag","","","","","","","","","","","","","");
	public static final Product PR_PBX_MIGRATIONSVERTRAG = new Product ("PBX Migrationsvertrag","","","","","","","","","","","","","");
	public static final Product PR_BUSINESSCONNECT_IPC = new Product ("BusinessConnect (IP-Centrex)","","","","","","","","","","","","","");
	public static final Product PR_BUSINESS_CONNECT_IMS = new Product ("Business Connect IMS","","","","","","","","","","","","","");
	// ManyBSK "product"
	public static final Product PR_MANYBSK = new Product ("manyBsk","","","","","","","","","","","","","");
	// hasMobile "product"
	public static final Product PR_HASMOBILE = new Product ("hasMobile","","","","","","","","","","","","","");
	public static final Product PR_MOBILE76 = new Product ("","","+41761234567","","","","","","","","","","","");
	public static final Product PR_MOBILE77 = new Product ("","","+41771234567","","","","","","","","","","","");
	public static final Product PR_MOBILE78 = new Product ("","","+41781234567","","","","","","","","","","","");
	public static final Product PR_MOBILE79 = new Product ("","","+41791234567","","","","","","","","","","","");

	// Constants for VoIP Cluster
	public static final Product PR_VOIP1L = new Product("VoIP 1st Line","","","","","","","","","","","","","");
	public static final Product PR_VOIP1LVN = new Product("VoIP 1st Line|VoIP Number","","","","","","","","","","","","","");
	public static final Product PR_VOIPFX = new Product("VoIP FX","","","","","","","","","","","","","");
	public static final Product PR_VOIPFXFN = new Product("VoIP FX|VoIP FX Number","","","","","","","","","","","","","");

	// CBU Cluster Names
	public static final String PR_CLUSTER_FSS = "Full Service Solution";
	public static final String PR_CLUSTER_ONB = "OneNetBase";
	public static final String PR_CLUSTER_OWP = "OneWorkPlace";
	public static final String PR_CLUSTER_OPB = "OnePhoneBusiness";
	public static final String PR_CLUSTER_ACD = "ACD";
	public static final String PR_CLUSTER_MCCS = "MCCS";
	public static final String PR_CLUSTER_IPP = "IP Plus";
	public static final String PR_CLUSTER_INTN = "International";
	public static final String PR_CLUSTER_EALARM = "eAlarm";
	public static final String PR_CLUSTER_LL = "LL";
	public static final String PR_CLUSTER_LL_PRE = "LL_Premium";
	public static final String PR_CLUSTER_SINET = "Sinet";
	public static final String PR_CLUSTER_CYK = "Cyberkey";
	public static final String PR_CLUSTER_BVOIP = "Business Voip";
	public static final String PR_CLUSTER_NWS = "NWS";
	public static final String PR_CLUSTER_IPSS = "IPSS";
	public static final String PR_CLUSTER_LAN_I = "Lan-I";
	public static final String PR_CLUSTER_VO_AVANOR = "Avaya Nortel";
	public static final String PR_CLUSTER_VO_CISBVOIP = "Cisco Business Voip";
	public static final String PR_CLUSTER_VO_SIE = "Siemens Voice";
	public static final String PR_CLUSTER_VO_ALC = "Alcatel Voice";
	public static final String PR_CLUSTER_VO_AAS = "Astra";
	public static final String PR_CLUSTER_IT_UTILITY = "IT_Utility";
	public static final String PR_CLUSTER_CSOL = "Complex Solutions";
	public static final String PR_CLUSTER_MCC = "MCC";
	public static final String PR_CLUSTER_SECURITY = "Security";

	// MDSV4 Products 
	public static final Product PR_MDS = new Product("Mobile Device Services","","","","","","","","","","","","","");
	public static final Product PR_MDS_EUS = new Product("MDS End User Support","","","","","","","","","","","","","");

	// RES Outgrower Products
	public static final Product PR_XTRA_LIBERTY_FLAT = new Product("NATEL xtra liberty flat","","","","","","","","","","","","","");
	public static final Product PR_XTRA_LIBERTY_MEZZO = new Product("NATEL xtra-liberty mezzo","","","","","","","","","","","","","");
	public static final Product PR_XTRA_MESSENGER_PLUS = new Product("NATEL xtra-messenger plus","","","","","","","","","","","","","");
	public static final Product PR_XTRA_DATA = new Product("NATEL xtra data","","","","","","","","","","","","","");
	public static final Product PR_XTRA_DATA_BASIC = new Product("NATEL xtra-data basic","","","","","","","","","","","","","");
	public static final Product PR_XTRA_PICCOLO = new Product("NATEL xtra piccolo","","","","","","","","","","","","","");
	public static final Product PR_XTRA_BASIC = new Product("NATEL xtra basic","","","","","","","","","","","","","");
	public static final Product PR_XTRA_MEZZO = new Product("NATEL xtra mezzo","","","","","","","","","","","","","");
	public static final Product PR_XTRA_M = new Product("NATEL xtra M","","","","","","","","","","","","","");
	
	// RES Fibre Product (accessProtocol = BX)
	public static final Product PR_FIBRE_GENERIC = new Product("", "", "", "", "", "", "", "", "", "", "", "", "BX","");
	
	// Mobile N-Stack product
	public static final Product PR_MOBILE_N_STACK = new Product("", "", "", "", "", "", "", "", "", "", "", "", "", "Mobile-N");

	// Constants for BSK Number Configuration
	public static final String NOBSK = "noBsk";
	public static final String ONEBSK = "oneBsk";
	public static final String MANYBSK = "manyBsk";

	// Businesstyp
	public static final String SME = "SME";
	public static final String RES = "RES";
	public static final String CBU = "CBU";

	// Business Numbers
	public static final String BUSINESSNUMBER_175 = "175";
	public static final String BUSINESSNUMBER_0800800832 = "0800800832";
	public static final String BUSINESSNUMBER_0800800975 = "0800800975";
	public static final String BUSINESSNUMBER_0800808040 = "0800808040";
	public static final String BUSINESSNUMBER_0800811801 = "0800811801";
	public static final String BUSINESSNUMBER_0800820211 = "0800820211";
	public static final String BUSINESSNUMBER_0800820212 = "0800820212";
	public static final String BUSINESSNUMBER_0800851851 = "0800851851";
	public static final String BUSINESSNUMBER_0800874874 = "0800874874";
	public static final String BUSINESSNUMBER_0800889911 = "0800889911";
	public static final String BUSINESSNUMBER_0848800175 = "0848800175";
	public static final String BUSINESSNUMBER_0848800811 = "0848800811";
	public static final String BUSINESSNUMBER_0848802286 = "0848802286";
	public static final String BUSINESSNUMBER_0900111848 = "0900111848";
	public static final String BUSINESSNUMBER_0443069405 = "0443069405";
	public static final String BUSINESSNUMBER_0443069500 = "0443069500";
	public static final String BUSINESSNUMBER_0800055055 = "0800055055";
	public static final String BUSINESSNUMBER_0844864161 = "0844864161";
	public static final String BUSINESSNUMBER_0443064646 = "0443064646";
	public static final String BUSINESSNUMBER_0800001684 = "0800001684";
	public static final String BUSINESSNUMBER_0800803175 = "0800803175";
	public static final String BUSINESSNUMBER_0800888500 = "0800888500";
	public static final String BUSINESSNUMBER_0800010742 = "0800010742";
	public static final String BUSINESSNUMBER_0800817112 = "0800817112";
	public static final String BUSINESSNUMBER_0800817175 = "0800817175";
	public static final String BUSINESSNUMBER_0800800851 = "0800800851";
	// CBU Businessnumbers
	public static final String BUSINESSNUMBER_0442946666 = "0442946666";
	public static final String BUSINESSNUMBER_0800724001 = "0800724001";
	public static final String BUSINESSNUMBER_0800724002 = "0800724002";
	public static final String BUSINESSNUMBER_080072400202 = "080072400202";
	public static final String BUSINESSNUMBER_0800724008 = "0800724008";
	public static final String BUSINESSNUMBER_0800724724 = "0800724724";
	public static final String BUSINESSNUMBER_0080072472424 = "080072472424";
	public static final String BUSINESSNUMBER_0800765400 = "0800765400";
	public static final String BUSINESSNUMBER_0900333221 = "0900333221";
	public static final String BUSINESSNUMBER_0800800900 = "0800800900";
	public static final String BUSINESSNUMBER_080080800900 = "080080800900";
	public static final String BUSINESSNUMBER_0800819175 = "0800819175";
	public static final String BUSINESSNUMBER_0800444404 = "0800444404";
	public static final String BUSINESSNUMBER_0622076910 = "0622076910";
	public static final String BUSINESSNUMBER_0812879925 = "0812879925";
	public static final String BUSINESSNUMBER_0812879948 = "0812879948";
	public static final String BUSINESSNUMBER_0812879963 = "0812879963";
	public static final String BUSINESSNUMBER_0812879964 = "0812879964";
	public static final String BUSINESSNUMBER_0800870875 = "0800870875";
	public static final String BUSINESSNUMBER_0582249137 = "0582249137";
	public static final String BUSINESSNUMBER_0582249143 = "0582249143";
	public static final String BUSINESSNUMBER_0582249144 = "0582249144";
	public static final String BUSINESSNUMBER_0582249151 = "0582249151";
	public static final String BUSINESSNUMBER_0582249183 = "0582249183";
	// Novartis
	public static final String BUSINESSNUMBER_0800724003 = "0800724003";
	// VBS
	public static final String BUSINESSNUMBER_0800824825 = "0800824825";
	// M2M Old and New
	public static final String BUSINESSNUMBER_0800724777 = "0800724777"; //OLD
	public static final String BUSINESSNUMBER_0800724011 = "0800724011"; //NEW

	// OneIVR Businessnumbers
	public static final String BUSINESSNUMBER_0080088111213 = "0080088111213";
	public static final String BUSINESSNUMBER_0800881112 = "0800881112";
	public static final String BUSINESSNUMBER_0800800800 = "0800800800";
	public static final String BUSINESSNUMBER_0800814814 = "0800814814";
	public static final String BUSINESSNUMBER_0800556464 = "0800556464";
	public static final String BUSINESSNUMBER_0080055646464 = "0080055646464";
	public static final String BUSINESSNUMBER_0622076090 = "0622076090";
	public static final String BUSINESSNUMBER_0622861212 = "0622861212";
	public static final String BUSINESSNUMBER_0622076059 = "0622076059";
	public static final String BUSINESSNUMBER_0812879046 = "0812879046";
	public static final String BUSINESSNUMBER_0800854854 = "0800854854";
	public static final String BUSINESSNUMBER_0800151728 = "0800151728";
	public static final String BUSINESSNUMBER_0812879952 = "0812879952";
	// Repair Hotline
	public static final String BUSINESSNUMBER_0800656000 = "0800656000";
	public static final String BUSINESSNUMBER_0622076560 = "0622076560";
	//M-Partner
	public static final String BUSINESSNUMBER_0800566566 = "0800566566";
	// RTM
	public static final String BUSINESSNUMBER_0800615615 = "0800615615";
	// Trader Hotline
	public static final String BUSINESSNUMBER_0800850085 = "0800850085";
	// TAS Hotline
	public static final String BUSINESSNUMBER_0800406080 = "0800406080";
	// ULL Rebilling 
	public static final String BUSINESSNUMBER_0800888817 = "0800888817";
	// SME Startup
	public static final String BUSINESSNUMBER_0800782788 = "0800782788";

	// Call Profile keys
	public static final String CPK_BUSINESSNUMBER = "businessnumber";
	public static final String CPK_BUSINESSTYP = "businesstyp";
	public static final String CPK_CALLDETAIL = "callDetail";
	public static final String CPK_LANGUAGE = "language";
	public static final String CPK_ORGUNIT = "orgunit";
	public static final String CPK_PIN = "pin";
	public static final String CPK_SALESTYPE = "salestype";

	public static final String CPK_NEXTSTATE = "nextState";
	public static final String CPK_ANI = "ani";
	public static final String CPK_DNIS = "dnis";
	public static final String CPK_SINGLELANGUAGE = "singleLanguage";
	public static final String CPK_CUSTOMERTYPE = "CustomerType";
	public static final String CPK_PLAYWELC = "playwelc";
	public static final String CPK_ACTIONRESPONSE = "actionResponse";
	public static final String CPK_INTERNATIONAL = "International";
	public static final String CPK_PHONENUMBER = "phonenumber";
	public static final String CPK_RESULTOH = "resultOH";
	public static final String CPK_CONNECTIONTYPE = "ConnectionType";
	public static final String CPK_STATEENGINECOUNTER = "stateEngineCounter";
	public static final String CPK_NEW_PRODUCT_SELECTION = "NewProductSelection";
	public static final String CPK_IDENTIFIEDBYANI = "IdentifiedByANI";
	public static final String CPK_HAVE_RECENT_PAYMENT = "haveRecentPayment";
	// CLI Data
	public static final String CPK_CLIDATA_LANGUAGE = "cliDataLanguage";
	public static final String CPK_CLIDATA_PHONENUMBER = "cliDataPhonenumber";
	
	// Call Profile keys introduced with GetInTouch
	public static final String CPK_SYSORIGANI = "SysOrigANI";
	public static final String CPK_SYSORSSESSIONID = "SysOrsSessionID";
	public static final String CPK_SYSCHANNEL = "SysChannel";
	public static final String CPK_SYSORIGLANGUAGE = "SysOrigLanguage";
	public static final String CPK_SYSRID = "SysRID";
	public static final String CPK_SYSRULEID = "SysRuleID";
	public static final String CPK_SYSRELEVANTNO = "SysRelevantNo";
	public static final String CPK_SYSCONCERN = "SysConcern";
	public static final String CPK_SYSHTTPCLIENT = "SysHttpClient";
	
	// INAS Generic actions
	public static final String CPK_GENERICINASACTION = "genericINASAction";
	
	// Recaller keys
	public static final String CPK_RECALLERSTATUS = "recallerStatus";
	public static final String CPK_RECALLERROUTINGID = "recallerRoutingID";
	public static final String CPK_RECALLERRULEID = "recallerRuleID";
	public static final String CPK_SKIPUPDATESESSION = "skipUpdateSession";

	// CBU
	public static final String CPK_CBU_0800724724_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800724724_LEVEL1;
	public static final String CPK_CBU_0800724724_MENU_LEVEL_2 = "cbu/" + CBU_MENU_0800724724_LEVEL2;
	public static final String CPK_CBU_0080072472424_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0080072472424_LEVEL1;
	public static final String CPK_CBU_0080072472424_MENU_LEVEL_2 = "cbu/" + CBU_MENU_0080072472424_LEVEL2;
	public static final String CPK_CBU_0800724001_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800724001_LEVEL1;
	public static final String CPK_CBU_0800724001_MENU_LEVEL_2 = "cbu/" + CBU_MENU_0800724001_LEVEL2;
	public static final String CPK_CBU_0800724002_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800724002_LEVEL1;
	public static final String CPK_CBU_0800724002_MENU_LEVEL_2 = "cbu/" + CBU_MENU_0800724002_LEVEL2;
	public static final String CPK_CBU_080072400202_MENU_LEVEL_1 = "cbu/" + CBU_MENU_080072400202_LEVEL1;
	public static final String CPK_CBU_080072400202_MENU_LEVEL_2 = "cbu/" + CBU_MENU_080072400202_LEVEL2;
	public static final String CPK_CBU_0442946666_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0442946666_LEVEL1;
	public static final String CPK_CBU_0442946666_MENU_LEVEL_2 = "cbu/" + CBU_MENU_0442946666_LEVEL2;
	public static final String CPK_CBU_0800724008_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800724008_LEVEL1;
	public static final String CPK_CBU_0800724008_MENU_LEVEL_2 = "cbu/" + CBU_MENU_0800724008_LEVEL2;
	public static final String CPK_CBU_0900333221_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0900333221_LEVEL1;
	public static final String CPK_CBU_0800765400_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800765400_LEVEL1;
	public static final String CPK_CBU_0800724003_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800724003_LEVEL1;
	public static final String CPK_CBU_0800724003_MENU_LEVEL_2 = "cbu/" + CBU_MENU_0800724003_LEVEL2;
	public static final String CPK_CBU_0800824825_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800824825_LEVEL1;
	public static final String CPK_CBU_0800724011_MENU_LEVEL_1 = "cbu/" + CBU_MENU_0800724011_LEVEL1;

	// Call Profile Key for SysCVTdate. Note: There is currently no getter method for this key
	public static final String CPK_SYSCVTDATE = "SysCVTdate";
	// actionResponse values
	public static final String ACTIONRESPONSE_ANICONFIRMED = "ANIconfirmed";
	public static final String ACTIONRESPONSE_ANIREJECTED = "ANIrejected";
	public static final String ACTIONRESPONSE_CANCELLED = "CANCELLED";
	public static final String ACTIONRESPONSE_CLOSED = "CLOSED";
	public static final String ACTIONRESPONSE_CONTINUE = "CONTINUE";
	public static final String ACTIONRESPONSE_DISCONNECT = "DISCONNECT";
	public static final String ACTIONRESPONSE_DONE = "DONE";
	public static final String ACTIONRESPONSE_FAILED = "FAILED";
	public static final String ACTIONRESPONSE_INPUTERROR = "InputError";
	public static final String ACTIONRESPONSE_OPEN = "OPEN";
	public static final String ACTIONRESPONSE_TRANSFER = "TRANSFER";
	public static final String ACTIONRESPONSE_TRANSFER_ROUTING_ID = "TRANSFER_ROUTING_ID";

	public static final String CPK_SROWNER = "srOwner";
	public static final String CPK_SROWNERUNIT = "srOwnerUnit";
	public static final String CPK_BU = "bu";


	//  Call Profile values for key = callflow1
	public static final String CPV_NACHTMENU = "nachtmenu";

	public static final String CPV_BERATUNG = "BERATUNG";
	public static final String CPV_ADMINISTRATION = "ADMINISTRATION";
	// KUNDENDIENST is still used in the 0800 888 500 BN
	public static final String CPV_KUNDENDIENST = "KUNDENDIENST";
	public static final String CPV_STOERUNG = "STOERUNG";

	// Call Profile values for key = produktmenu
	public static final String CPV_INTERNET = "INTERNET";
	public static final String CPV_ITHOSTING = "ITHOSTING";
	public static final String CPV_VERMITTLUNGSANLAGE = "VERMITTLUNGSANLAGE";
	public static final String CPV_INFORMATIKDIENSTE = "INFORMATIKDIENSTE";
	public static final String CPV_RECHNUNG = "RECHNUNG";
	public static final String CPV_FESTNETZ = "FESTNETZ";
	public static final String CPV_MOBILE = "MOBILE";
	public static final String CPV_INTERNETBERATUNG = "INTERNETBERATUNG";
	public static final String CPV_INTERNETSTOERUNG = "INTERNETSTOERUNG";
	public static final String CPV_INTERNETSTOERUNGDETAILQ = "INTERNETSTOERUNGDETAILQ";
	public static final String CPV_TV = "TV";
	public static final String CPV_PASSWORDAGENT = "PASSWORDAGENT";
	public static final String CPV_PASSGEN= "PASSGEN";
	public static final String CPV_MUTATIONEN = "MUTATIONEN";
	public static final String CPV_WEITEREDIENSTE = "WEITEREDIENSTE";
	public static final String CPV_CASATRIO = "CASATRIO";
	public static final String CPV_ERROR = "error";

	// Call Profile values for CBU menu selections
	public static final String CPV_CBU_MENU_SA = "SA";
	public static final String CPV_CBU_MENU_SF = "SF";
	public static final String CPV_CBU_MENU_SA_OUS = "SA_OUS";
	public static final String CPV_CBU_MENU_ORDER = "ORDER";
	public static final String CPV_CBU_MENU_BB = "BB";
	public static final String CPV_CBU_MENU_OTHER = "OTHER";
	public static final String CPV_CBU_MENU_ULTD = "ULTD";
	public static final String CPV_CBU_MENU_MO = "MO";
	public static final String CPV_CBU_MENU_DUMMY1 = "DUMMY1";
	public static final String CPV_CBU_MENU_DUMMY2 = "DUMMY2";
	public static final String CPV_CBU_MENU_DUMMY3 = "DUMMY3";
	public static final String CPV_CBU_MENU_DUMMY4 = "DUMMY4";
	public static final String CPV_CBU_MENU_PWLAN = "PWLAN";
	public static final String CPV_CBU_MENU_DATA = "DATA";
	public static final String CPV_CBU_MENU_DATA_AXA = "DATA_AXA";
	public static final String CPV_CBU_MENU_VOICE = "VOICE";
	public static final String CPV_CBU_MENU_VOICE_AXA = "VOICE_AXA";
	public static final String CPV_CBU_MENU_MSS = "MSS";    
	public static final String CPV_CBU_MENU_ERROR = "error";
	public static final String CPV_CBU_MENU_CUC_INT = "CUC_International";
	// Novartis
	public static final String CPV_CBU_MENU_FX_NV = "FX";
	public static final String CPV_CBU_MENU_MO_NV = "MO";
	public static final String CPV_CBU_MENU_PAGING_NV = "PAGING";

	// Call Profile values for CBU language
	public static final String CPV_LANGUAGE_G = "g";
	public static final String CPV_LANGUAGE_F = "f";
	public static final String CPV_LANGUAGE_I = "i";
	public static final String CPV_LANGUAGE_E = "e";

	public static final String CPV_WIRELESS = "Wireless";
	public static final String CPV_WIRELINE = "Wireline";
	public static final String CPV_UNKNOWN = "unknown";
	public static final String CPV_AUSLAND = "Ausland";
	public static final String CPV_CUSTOMERTYPE_EASY = "Easy";
	public static final String CPV_CUSTOMERTYPE_MMO = "MMO";
	public static final String CPV_CUSTOMERTYPE_MBU = "MBU";
	public static final String CPV_CUSTOMERTYPE_NEWCUSTOMER = "NewCustomer";
	public static final String CPV_CUSTOMERTYPE_NONUMBER = "NoNumber";
	public static final String FALSE = "false";
	public static final String TRUE = "true";

	public static final String CPV_MENUOPTION_NP_ALM1 = "NPALM1";
	public static final String CPV_MENUOPTION_NP_ALM2 = "NPALM2";
	public static final String CPV_MENUOPTION_NP_ALM3 = "NPALM3";
	public static final String CPV_MENUOPTION_NP_ALM4 = "NPALM4";
	public static final String CPV_MENUOPTION_NP_PRM1 = "NPPRM1";
	public static final String CPV_MENUOPTION_NP_PRM2 = "NPPRM2";
	public static final String CPV_MENUOPTION_NP_PRM3 = "NPPRM3";
	public static final String CPV_MENUOPTION_NP_PRM4 = "NPPRM4";
	public static final String CPV_MENUOPTION_NP_PRM5 = "NPPRM5";
	// SME New Product Menu Options
	public static final String CPV_MENUOPTION_NP_ALM1_TRANSFER = "NPALM1TRANSFER";
	public static final String CPV_MENUOPTION_NP_ALM2_TRANSFER = "NPALM2TRANSFER";
	public static final String CPV_MENUOPTION_NP_ALM3_TRANSFER = "NPALM3TRANSFER";
	public static final String CPV_MENUOPTION_NP_ALM4_TRANSFER = "NPALM4TRANSFER";
	public static final String CPV_MENUOPTION_NP_ALM1_PRM1 = "NPALM1PRM1";
	public static final String CPV_MENUOPTION_NP_ALM1_PRM2 = "NPALM1PRM2";
	public static final String CPV_MENUOPTION_NP_ALM1_PRM3 = "NPALM1PRM3";
	public static final String CPV_MENUOPTION_NP_ALM1_PRM4 = "NPALM1PRM4";
	public static final String CPV_MENUOPTION_NP_ALM1_PRM5 = "NPALM1PRM5";
	public static final String CPV_MENUOPTION_NP_ALM2_PRM1 = "NPALM2PRM1";
	public static final String CPV_MENUOPTION_NP_ALM2_PRM2 = "NPALM2PRM2";
	public static final String CPV_MENUOPTION_NP_ALM2_PRM3 = "NPALM2PRM3";
	public static final String CPV_MENUOPTION_NP_ALM2_PRM4 = "NPALM2PRM4";
	public static final String CPV_MENUOPTION_NP_ALM2_PRM5 = "NPALM2PRM5";
	public static final String CPV_MENUOPTION_NP_ALM3_PRM1 = "NPALM3PRM1";
	public static final String CPV_MENUOPTION_NP_ALM3_PRM2 = "NPALM3PRM2";
	public static final String CPV_MENUOPTION_NP_ALM3_PRM3 = "NPALM3PRM3";
	public static final String CPV_MENUOPTION_NP_ALM3_PRM4 = "NPALM3PRM4";
	public static final String CPV_MENUOPTION_NP_ALM3_PRM5 = "NPALM3PRM5";
	public static final String CPV_MENUOPTION_NP_ALM4_PRM1 = "NPALM4PRM1";
	public static final String CPV_MENUOPTION_NP_ALM4_PRM2 = "NPALM4PRM2";
	public static final String CPV_MENUOPTION_NP_ALM4_PRM3 = "NPALM4PRM3";
	public static final String CPV_MENUOPTION_NP_ALM4_PRM4 = "NPALM4PRM4";
	public static final String CPV_MENUOPTION_NP_ALM4_PRM5 = "NPALM4PRM5";

	public static final String CPV_MENUOPTION_RECHNUNG = "RECHNUNG";
	public static final String CPV_MENUOPTION_KUNDENDIENST = "KUNDENDIENST";
	public static final String CPV_MENUOPTION_STOERUNG = "STOERUNG";
	public static final String CPV_MENUOPTION_INTERNET = "INTERNET";
	public static final String CPV_MENUOPTION_MOBILE_INTERNET = "MO-INTERNET";
	public static final String CPV_MENUOPTION_FESTNETZ_INTERNET = "FX-INTERNET";
	public static final String CPV_MENUOPTION_PHONE = "PHONE";
	public static final String CPV_MENUOPTION_ERROR = "error";
	public static final String CPV_MENUOPTION_COMBOX = "COMBOX";
	public static final String CPV_MENUOPTION_DIEBSTAHL = "DIEBSTAHL";
	public static final String CPV_MENUOPTION_ROAMING = "ROAMING";
	public static final String CPV_MENUOPTION_OTHER = "OTHER";
	public static final String CPV_MENUOPTION_TV = "TV";
	public static final String CPV_MENUOPTION_EASY = "EASY";
	public static final String CPV_MENUOPTION_PASSWORD = "PASSWORD";
	public static final String CPV_MENUOPTION_GUTHABEN = "GUTHABEN";
	public static final String CPV_MENUOPTION_MBU = "MBU";
	// New Migros Mobile Menuoption CONSTANTS
	public static final String CPV_MENUOPTION_MIGROS_IN = "IN";
	public static final String CPV_MENUOPTION_MIGROS_MO = "MO";
	public static final String CPV_MENUOPTION_MIGROS_PRE = "PRE";
	public static final String CPV_MENUOPTION_MIGROS_PP = "PP";
	public static final String CPV_MENUOPTION_MIGROS_INPH = "INPH";
	public static final String CPV_MENUOPTION_MIGROS_TV = "TV";

	public static final String CPV_MENUOPTION_THL1 = "THL1";
	public static final String CPV_MENUOPTION_THL2 = "THL2";
	public static final String CPV_MENUOPTION_ACCESSDSL = "ACCESSDSL";
	public static final String CPV_MENUOPTION_ALLIPFTTH = "ALLIPFTTH";
	// SME
	public static final String CPV_MENUOPTION_PBRDSL1 = "PBRDSL1";

	// OneIVR SelfServices CallDetail String
	public static final String CPV_SSCHECK = "SSCHECK";
	public static final String CPV_SSQCHECK = "SSQCHECK";
	public static final String CPV_SSCOPW = "SSCOPW";
	public static final String CPV_SSFC = "SSFC";
	public static final String CPV_SSLOAD = "SSLOAD";
	public static final String CPV_SSPG = "SSPG";
	public static final String CPV_SSPP = "SSPP";
	public static final String CPV_SSREP = "SSREP";
	public static final String CPV_SSRK = "SSRK";

	public static final String CARS_TRANSFER_CCC = "CCC";
	public static final String CARS_TRANSFER_DUNNING_BLOCKED = "Dunning Blocked";
	public static final String CARS_TRANSFER_DUNNING_LETTER_SMS = "Dunning Letter SMS";
	public static final String CARS_TRANSFER_BARRING_PREVENTION = "Barring Prevention";
	public static final String CARS_TRANSFER_CREDITLIMIT_BLOCKED = "Creditlimit Blocked";

	public static final String CARS_WIRELINE_WF1NS = "cars/wireline-callflow1-ns";
	public static final String CARS_WIRELINE_WF1SC = "cars/wireline-callflow1-sc";
	public static final String CARS_WIRELINE_WF2 = "cars/wireline-callflow2";
	public static final String CARS_WIRELESS_WF1NS = "cars/wireless-callflow1-ns";
	public static final String CARS_WIRELESS_WF1SC = "cars/wireless-callflow1-sc";
	public static final String CARS_WIRELESS_WF2 = "cars/wireless-callflow2";
	public static final String CARS_WIRELESS_WF4 = "cars/wireless-callflow4";
	public static final String CARS_WIRELESS_WF5 = "cars/wireless-callflow5";
	public static final String CARS_WIRELESS_WF6 = "cars/wireless-callflow6";
	public static final String CARS_WIRELESS_WF7 = "cars/wireless-callflow7";
	public static final String CARS_WIRELESS_WF8 = "cars/wireless-callflow8";
	// Call Profile Keys for CARS
	public static final String CPK_CARS_ACTIONRESPONSE = "carsActionResponse";
	public static final String CPK_CARS_ACTIONRESPONSEVALUE = "carsActionResponseValue";
	public static final String CPK_CARS_OPENINVOICEAMOUNT = "openInvoiceAmount";
	public static final String CPK_CARS_TRANSFER_TARGET = "carsTransferTarget";

	// VO State Engine Actions
	// Generic
	public static final String SE_ACTION_CBR = "CBR";
	public static final String SE_ACTION_CONFIRM = "CONFIRM";
	public static final String SE_ACTION_CUSTIDENT = "CUSTIDENT";
	public static final String SE_ACTION_DISCONNECT = "DISCONNECT";
	public static final String SE_ACTION_ENTERPN = "ENTERPN";
	public static final String SE_ACTION_EXECINASACTIONS = "EXECINASACTIONS";
	public static final String SE_ACTION_EXTENDEDMENU = "EXTENDEDMENU";
	public static final String SE_ACTION_GENERICRETURN = "GENERICRETURN";
	public static final String SE_ACTION_GOODBYE = "GOODBYE";
	public static final String SE_ACTION_LANGSELECT = "LANGSELECT";
	public static final String SE_ACTION_PNLOOKUP = "PNLOOKUP";
	public static final String SE_ACTION_SENDSMS = "SENDSMS";
	public static final String SE_ACTION_SETINASACTIONS = "SETINASACTIONS";
	public static final String SE_ACTION_SETLANG = "SETLANG";
	public static final String SE_ACTION_SETPHONENUMBER = "SETPHONENUMBER";
	public static final String SE_ACTION_SETPNFROMSESSION = "SETPNFROMSESSION";
	public static final String SE_ACTION_SETPNFROMSESSION_NOLOOKUP= "SETPNFROMSESSION_NOLOOKUP";
	public static final String SE_ACTION_SETSESSIONANI = "SETSESSIONANI";
	public static final String SE_ACTION_SPEAK = "SPEAK";
	public static final String SE_ACTION_STOREKVP = "STOREKVP";
	public static final String SE_ACTION_TRANSFER = "TRANSFER";
	public static final String SE_ACTION_XFERCALLFLOW = "XFERCALLFLOW";
	public static final String SE_ACTION_TRANSFERRID = "TRANSFERRID";
	public static final String SE_ACTION_WRITELOG = "WRITELOG";
	public static final String SE_ACTION_CHECKFORRECALLER = "CHECKFORRECALLER";
	public static final String SE_ACTION_UPDATERECALLERSESSION = "UPDATERECALLERSESSION";
	public static final String SE_ACTION_CHECKOPTIONOFFER = "CHECKOPTIONOFFER";
	// Actions that are hidden in Compound State Engine 
	public static final String SE_ACTION_PROCEED = "PROCEED";
	public static final String SE_ACTION_CHECKOH = "CHECKOH";
	public static final String SE_ACTION_SWITCHRULES = "SWITCHRULES";
	// Pilot Spracherkennung
	public static final String SE_ACTION_RECORD = "RECORD";
	// Portal Specific 
	public static final String SE_ACTION_GETRESULTCUSTINFO = "GETRESULTCUSTINFO";
	public static final String SE_ACTION_PASSGEN = "PASSGEN";
	public static final String SE_ACTION_CARS = "CARS";
	public static final String SE_ACTION_PRIMO = "PRIMO";
	public static final String SE_ACTION_EASYCHECK = "EASYCHECK";
	public static final String SE_ACTION_EASYLOAD = "EASYLOAD";
	public static final String SE_ACTION_QUICKCHECK = "QUICKCHECK";
	public static final String SE_ACTION_COMBOX = "COMBOX";
	public static final String SE_ACTION_ACCOUNTMANAGER = "ACCOUNTMANAGER";
	public static final String SE_ACTION_PIN = "PIN";
	public static final String SE_ACTION_MSS = "MSS";
	public static final String SE_ACTION_ENTERPLZ = "ENTERPLZ";
	// CARS State Engine Actions
	public static final String SE_ACTION_ADDSTATISTIC = "ADDSTATISTIC";
	public static final String SE_ACTION_READSERVICECONFIG = "READSERVICECONFIG";
	public static final String SE_ACTION_GETSTATUS = "GETSTATUS";
	public static final String SE_ACTION_GETINVOICES = "GETINVOICES";
	public static final String SE_ACTION_INPUT = "INPUT";
	public static final String SE_ACTION_PAYMENTPROMISE = "PAYMENTPROMISE";
	public static final String SE_ACTION_SENDINVOICECOPY = "SENDINVOICECOPY";
	public static final String SE_ACTION_SENDINFOSMS = "SENDINFOSMS";
	public static final String SE_ACTION_SENDINVOICESMS = "SENDINVOICESMS";
	public static final String SE_ACTION_INCREASEKL = "INCREASEKL";
	public static final String SE_ACTION_SUBSCRIBEQCSMS = "SUBSCRIBEQCSMS";
	public static final String SE_ACTION_TRANSFERPROMPTSWITCH = "TRANSFERPROMPTSWITCH";
	public static final String SE_ACTION_BACK2PORTAL = "BACK2PORTAL";
	public static final String SE_ACTION_RUNCALLFLOW = "RUNCALLFLOW";
	public static final String SE_ACTION_SMSAFTERHANGUP = "SMSAFTERHANGUP";
	// Call CARS Status from RES/SME/CBU State Engine
	public static final String SE_ACTION_CARSSTATUS = "CARSSTATUS";
	
	
	// MenuOptions that should never change
	// SME NewProductMenus
	public static final ExtendedMenuOption smeNPAlm1Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM1");
		option.setPrompt("npprm-alm1-example1");
		option.setSkill("NPALM1PRM1");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm1Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM2");
		option.setPrompt("npprm-alm1-example2");
		option.setSkill("NPALM1PRM2");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm1Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM3");
		option.setPrompt("npprm-alm1-example3");
		option.setSkill("NPALM1PRM3");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm1Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM4");
		option.setPrompt("npprm-alm1-example4");
		option.setSkill("NPALM1PRM4");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm1Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM1PRM5");
		option.setPrompt("npprm-alm1-example5");
		option.setSkill("NPALM1PRM5");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm2Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM1");
		option.setPrompt("npprm-alm2-example1");
		option.setSkill("NPALM2PRM1");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm2Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM2");
		option.setPrompt("npprm-alm2-example2");
		option.setSkill("NPALM2PRM2");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm2Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM3");
		option.setPrompt("npprm-alm2-example3");
		option.setSkill("NPALM2PRM3");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm2Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM4");
		option.setPrompt("npprm-alm2-example4");
		option.setSkill("NPALM2PRM4");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm2Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM2PRM5");
		option.setPrompt("npprm-alm2-example5");
		option.setSkill("NPALM2PRM5");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm3Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM1");
		option.setPrompt("npprm-alm3-example1");
		option.setSkill("NPALM3PRM1");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm3Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM2");
		option.setPrompt("npprm-alm3-example2");
		option.setSkill("NPALM3PRM2");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm3Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM3");
		option.setPrompt("npprm-alm3-example3");
		option.setSkill("NPALM3PRM3");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm3Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM4");
		option.setPrompt("npprm-alm3-example4");
		option.setSkill("NPALM3PRM4");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm3Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM3PRM5");
		option.setPrompt("npprm-alm3-example5");
		option.setSkill("NPALM3PRM5");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm4Prm1() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM1");
		option.setPrompt("npprm-alm4-example1");
		option.setSkill("NPALM4PRM1");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm4Prm2() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM2");
		option.setPrompt("npprm-alm4-example2");
		option.setSkill("NPALM4PRM2");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm4Prm3() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM3");
		option.setPrompt("npprm-alm4-example3");
		option.setSkill("NPALM4PRM3");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm4Prm4() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM4");
		option.setPrompt("npprm-alm4-example4");
		option.setSkill("NPALM4PRM4");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption smeNPAlm4Prm5() {
		ExtendedMenuOption option = new ExtendedMenuOption();
		option.setName("NPALM4PRM5");
		option.setPrompt("npprm-alm4-example5");
		option.setSkill("NPALM4PRM5");
		option.setAction("TRANSFER");
		return option;
	} 

	public static final ExtendedMenuOption npAlmOther() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPOTHERALM");
	  	  option.setPrompt("iPhone-01-2");
	  	  option.setAction("CONTINUE");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm1Menu() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM1");
	  	  option.setPrompt("npalm-example1");
	  	  option.setAction("MENU");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm1Transfer() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM1TRANSFER");
	  	  option.setPrompt("iPhone-01-1");
	  	  option.setEmergencyText("NPIPHONE");
	  	  option.setAction("TRANSFER");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm2Menu() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM2");
	  	  option.setPrompt("npalm-iPad");
	  	  option.setAction("MENU");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm2Transfer() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM2TRANSFER");
	  	  option.setPrompt("npalm-example2");
	  	  option.setAction("TRANSFER");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm3Menu() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM3");
	  	  option.setPrompt("npalm-example3");
	  	  option.setAction("MENU");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm3Transfer() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM3TRANSFER");
	  	  option.setPrompt("npalm-Combox");
	  	  option.setAction("TRANSFER");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm4Menu() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM4");
	  	  option.setPrompt("npalm-example4");
	  	  option.setAction("MENU");
	  	  return option;
	} 

	public static final ExtendedMenuOption npAlm4Transfer() {
	  	  ExtendedMenuOption option = new ExtendedMenuOption();
	  	  option.setName("NPALM4TRANSFER");
	  	  option.setPrompt("npalm-example4");
	  	  option.setAction("TRANSFER");
	  	  return option;
	} 
	// Methods to output results from the Rules
	// MenuOptions
	public static void printMenuOptions(String name, List<ExtendedMenuOption> menuOptions) {
		System.out.println("=============================================================");
		System.out.println(name);
		System.out.println("_____________________________________________________________");
		for (int i=0; i<menuOptions.size();i++) {
			System.out.println("Option: "+i);
			System.out.println("Name:\t" + menuOptions.get(i).getName());
			System.out.println("Action:\t"+ menuOptions.get(i).getAction());
			System.out.println("Prompt:\t" + menuOptions.get(i).getPrompt());
			System.out.println("Skill:\t" + menuOptions.get(i).getSkill());
			System.out.println("EmergencyText:\t" + menuOptions.get(i).getEmergencyText());
			System.out.println("RoutingID:\t" + menuOptions.get(i).getRoutingID());
			System.out.println("Reasoncode:\t" + menuOptions.get(i).getReasonCode());
			System.out.println("----------------------------------------");
		}
	}

	/**
	 * Compare a list of MenuOptions with expected list
	 * @param expected The List of expected menuOptions in proper order
	 * @param actual The actual list of menuOptions as returned by the rules
	 */
	public static void compareExpectedToActual(List<ExtendedMenuOption> expected, List<ExtendedMenuOption> actual) {

		// size
		assertEquals("Size",expected.size(),actual.size());

		// Compare each element
		for (int i=0; i<actual.size();i++) {
			assertEquals(i+":Name",expected.get(i).getName(),actual.get(i).getName());
			assertEquals(i+":Position", expected.get(i).getPosition(), actual.get(i).getPosition());
			assertEquals(i+":Prompt",expected.get(i).getPrompt(),actual.get(i).getPrompt());
			assertEquals(i+":Skill",expected.get(i).getSkill(),actual.get(i).getSkill());
			assertEquals(i+":Action",expected.get(i).getAction(),actual.get(i).getAction());
			assertEquals(i+":EmergencyText", expected.get(i).getEmergencyText(),actual.get(i).getEmergencyText());
			assertEquals(i+":routingID", expected.get(i).getRoutingID(),actual.get(i).getRoutingID());
			assertEquals(i+":reasonCode", expected.get(i).getReasonCode(),actual.get(i).getReasonCode());
		}
	}

	/**
	 * Creates a new ArrayList<ExtendedMenuOption>
	 * @return a new empty ArrayList<ExtendenMenuOption>
	 */
	public static List<ExtendedMenuOption> createExtendedMenuOptions () {
		return new ArrayList<ExtendedMenuOption>();
	}
	
	/**
	 * Check if the transferRulesState returned by the rules matches the expected values for
	 * identificationResult and qualificationResult.
	 * Will output an error message when mismatching.
	 * @param id The expected identificationResult
	 * @param qu The expected qualificationResult
	 * @param trs The TransferRulesState object as returned by the transfer Rules engine
	 */
	public static void checkTransferRulesResult(String id, String qu, TransferRulesState trs) {
		assertEquals("Wrong identificationResult", id, trs.getIdentificationResult());
		assertEquals("Wrong qualificationResult", qu, trs.getQualificationResult());
	}
	
	/**
	 * Prüfmethode für Tests mit mehrere Rules-Dateien in einer Testmethode.
	 * Prüft, ob Identifikations- und Qualifikationsresultat übereinstimme und gibt bei
	 * Nichtübereinstimmung den Namen der Rulesdatei und das zu beanstandende Feld aus
	 * @param rulesFileName Name der Rules-Datei, die aktuell geprüft wird
	 * @param id Erwartetes Identifikationsresultat
	 * @param qu Erwartetes Qualifikationsresultat
	 * @param trs TranserRulesState Objekt, welches das tatsächliche Ergebnis enthält
	 */
	public static void checkTransferRulesResult(
			String rulesFileName, 
			String id, 
			String qu, 
			TransferRulesState trs) {
		assertEquals(rulesFileName+"|Wrong identificationResult", id, trs.getIdentificationResult());
		assertEquals(rulesFileName+"|Wrong qualificationResult", qu, trs.getQualificationResult());
	}
	
	/**
	 * Compares the expected stateEngineRulesResult against an actual stateEngineRulesResult
	 * Outputs the Rules Filename on nomatch
	 * @param rulesFileName The name of the used rules file
	 * @param expectedResult The expected Result
	 * @param actualResult The actual Result
	 */
	public static void checkStateEngineRulesResult(
			String rulesFileName,
			StateEngineRulesResult expectedResult,
			StateEngineRulesResult actualResult) {
	
		assertEquals(rulesFileName+"|state",expectedResult.getState(),actualResult.getState());
		assertEquals(rulesFileName+"|error",expectedResult.getError(),actualResult.getError());
		assertEquals(rulesFileName+"|action",expectedResult.getAction(),actualResult.getAction());
		assertEquals(rulesFileName+"|outputVar1",expectedResult.getOutputVar1(),actualResult.getOutputVar1());
		assertEquals(rulesFileName+"|outputVar2",expectedResult.getOutputVar2(),actualResult.getOutputVar2());
		assertEquals(rulesFileName+"|outputVar3",expectedResult.getOutputVar3(),actualResult.getOutputVar3());
		assertEquals(rulesFileName+"|nextState",expectedResult.getNextState(),actualResult.getNextState());
		// The following should work since the StateEngineCollection class offers an equals method
		assertEquals(rulesFileName+"|outputColl1",expectedResult.getOutputColl1(),actualResult.getOutputColl1());
		assertEquals(rulesFileName+"|outputColl2",expectedResult.getOutputColl2(),actualResult.getOutputColl2());
	}
}




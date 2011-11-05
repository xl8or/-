package com.android.email.activity.setup;

import android.util.Log;
import com.android.email.activity.setup.AccountSettingsUtils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AccountSetupCustomer {

   private static final String CUSTOMER_PATH = "/system/csc/";
   private static final String CUSTOMER_XML = "customer.xml";
   private static final String DEBUG_TAG = "AccountSetupCustomer";
   private static final String OTHERS_XML = "others.xml";
   static final String TAG_ACCOUNTNAME = "AccountName";
   static final String TAG_COMBINDED = "Combinded";
   static final String TAG_EMAILADDR = "EmailAddr";
   static final String TAG_IC_ATTACH = "Attach";
   static final String TAG_IC_AUTO_SYNC = "AutoSync";
   static final String TAG_IC_DEF_PORT = "110";
   static final String TAG_IC_MAILBOX_TYPE = "MailboxType";
   static final String TAG_IC_OPTION = "Option";
   static final String TAG_IC_PORT = "Port";
   static final String TAG_IC_SECURE = "Secure";
   static final String TAG_IC_SECURE_ONOFF = "SecureLogin";
   static final String TAG_IC_SERVER = "ServAddr";
   static final String TAG_INCOMING = "Incoming";
   static final String TAG_LIST = "Account";
   static final String TAG_LOGINTYPE = "LoginType";
   static final String TAG_NETWORKNAME = "NetworkName";
   static final String TAG_NODE = "Settings.Messages.Email";
   static final String TAG_OG_AUTHENT = "Authent";
   static final String TAG_OG_DEF_PORT = "25";
   static final String TAG_OG_IDPASS = "IDPassword";
   static final String TAG_OG_OPTION = "Option";
   static final String TAG_OG_PORT = "Port";
   static final String TAG_OG_SECURE = "Secure";
   static final String TAG_OG_SERVER = "ServAddr";
   static final String TAG_OG_SMTPAUTH = "SmtpAuth";
   static final String TAG_OUTGOING = "Outgoing";
   static final String TAG_PASSWORD = "Password";
   static final String TAG_SECURE_OFF = "off";
   static final String TAG_USER_ID = "UserId";
   private static int mCustomerCount;
   static boolean mCustomerEmailType = 1;
   static NodeList mCustomerList;
   static Node mCustomerNode;
   private static Document mDoc;
   private static String mFilePath;
   private static Node mRoot;
   private static AccountSetupCustomer sInstance = new AccountSetupCustomer();


   private AccountSetupCustomer() {
      mCustomerEmailType = (boolean)1;
      this.loadXMLFile();
   }

   public static String getCustomerFilePath(String var0) {
      String var1 = "/system/csc/" + var0;
      String var2 = "filePath: " + var1;
      int var3 = Log.d("AccountSetupCustomer", var2);
      return var1;
   }

   public static AccountSetupCustomer getInstance() {
      return sInstance;
   }

   public static AccountSetupCustomer getInstance(String var0) {
      if(sInstance != null) {
         sInstance.loadXMLFile(var0);
      }

      return sInstance;
   }

   public int getCustomerCount() {
      return mCustomerCount;
   }

   public boolean getEmailType() {
      return mCustomerEmailType;
   }

   public AccountSettingsUtils.Provider getProviderCustomer(String var1) {
      AccountSettingsUtils.Provider var2 = null;
      AccountSettingsUtils.Provider var4;
      if(mCustomerNode == null) {
         int var3 = Log.d("AccountSetupCustomer", "Error while trying to load mCustomerNode.");
         var4 = null;
      } else {
         StringBuilder var5 = (new StringBuilder()).append("mCustomerCount: ");
         int var6 = mCustomerCount;
         String var7 = var5.append(var6).toString();
         int var8 = Log.e("AccountSetupCustomer", var7);
         int var9 = 0;

         while(true) {
            int var10 = mCustomerCount;
            if(var9 >= var10) {
               var4 = var2;
               break;
            }

            label132: {
               String var25;
               label129: {
                  NodeList var13 = mCustomerList;
                  var13.item(var9);
                  NodeList var16 = mCustomerList;
                  Node var18 = var16.item(var9);
                  String var21 = "AccountName";
                  Node var22 = this.getTagNode(var18, var21);
                  var25 = this.getTagValue(var22);
                  NodeList var26 = mCustomerList;
                  Node var28 = var26.item(var9);
                  String var31 = "EmailAddr";
                  Node var32 = this.getTagNode(var28, var31);
                  String var35 = this.getTagValue(var32);
                  String var37 = "yahoo.com";
                  if(var1.contains(var37)) {
                     String var38 = "Yahoo! Mail";
                     if(var38.equals(var25)) {
                        StringBuffer var40 = new StringBuffer();
                        String var41 = "domain[yahoo]: ";
                        StringBuffer var42 = var40.append(var41);
                        StringBuffer var44 = var42.append(var1).append(", emailAddr: ");
                        String var46 = var44.append(var35).toString();
                        int var47 = Log.d("AccountSetupCustomer", var46);
                        break label129;
                     }
                  }

                  if(var35 == null || !var35.contains(var1)) {
                     StringBuffer var57 = new StringBuffer();
                     String var58 = "domain: ";
                     StringBuffer var59 = var57.append(var58);
                     StringBuffer var61 = var59.append(var1).append(", emailAddr: ");
                     String var63 = var61.append(var35).toString();
                     int var64 = Log.d("AccountSetupCustomer", var63);
                     break label132;
                  }
               }

               NodeList var48 = mCustomerList;
               Node var50 = var48.item(var9);
               String var53 = "Incoming";
               NodeList var54 = this.getTagList(var50, var53);
               if(var54 == null) {
                  var4 = null;
                  break;
               }

               byte var66 = 0;
               Node var67 = var54.item(var66);
               String var70 = "MailboxType";
               Node var71 = this.getTagNode(var67, var70);
               String var74 = this.getTagValue(var71);
               if(var74 == null) {
                  var4 = null;
                  break;
               }

               String var79;
               label97: {
                  String var76 = "imap3";
                  if(!var74.equalsIgnoreCase(var76)) {
                     String var78 = "imap4";
                     if(!var74.equalsIgnoreCase(var78)) {
                        var79 = "pop3";
                        break label97;
                     }
                  }

                  var79 = "imap";
               }

               String var96;
               label92: {
                  byte var81 = 0;
                  Node var82 = var54.item(var81);
                  String var85 = "Secure";
                  Node var86 = this.getTagNode(var82, var85);
                  String var89 = this.getTagValue(var86);
                  if(var89 != null) {
                     String var91 = "off";
                     if(!var89.equalsIgnoreCase(var91)) {
                        StringBuffer var92 = new StringBuffer();
                        String var93 = "+";
                        StringBuffer var94 = var92.append(var93);
                        var96 = var94.append(var89).append("+").toString();
                        break label92;
                     }
                  }

                  var96 = "";
               }

               byte var98 = 0;
               Node var99 = var54.item(var98);
               String var102 = "ServAddr";
               Node var103 = this.getTagNode(var99, var102);
               String var106 = this.getTagValue(var103);
               StringBuffer var107 = new StringBuffer();
               String var108 = "://";
               StringBuffer var109 = var107.append(var108);
               String var111 = var109.append(var106).toString();
               byte var113 = 0;
               Node var114 = var54.item(var113);
               String var117 = "Port";
               Node var118 = this.getTagNode(var114, var117);
               String var121 = this.getTagValue(var118);
               if(var121 != null) {
                  StringBuffer var122 = new StringBuffer();
                  String var123 = ":";
                  StringBuffer var124 = var122.append(var123);
                  var121 = var124.append(var121).toString();
               }

               NodeList var126 = mCustomerList;
               Node var128 = var126.item(var9);
               String var131 = "LoginType";
               Node var132 = this.getTagNode(var128, var131);
               String var135 = this.getTagValue(var132);
               String var136;
               if(var135 != null && var135.equalsIgnoreCase("domain_type")) {
                  var136 = "$email";
               } else {
                  var136 = "$user";
               }

               StringBuffer var137 = new StringBuffer();
               StringBuffer var140 = var137.append(var79);
               if(var96 != null) {
                  StringBuffer var143 = var137.append(var96);
               }

               String var148;
               NodeList var161;
               String var178;
               label80: {
                  StringBuffer var146 = var137.append(var111);
                  var148 = var146.append(var121).toString();
                  StringBuffer var149 = new StringBuffer();
                  String var150 = "incomingUri: ";
                  StringBuffer var151 = var149.append(var150);
                  String var153 = var151.append(var148).toString();
                  int var154 = Log.d("AccountSetupCustomer", var153);
                  NodeList var155 = mCustomerList;
                  Node var157 = var155.item(var9);
                  String var160 = "Outgoing";
                  var161 = this.getTagList(var157, var160);
                  var74 = "smtp";
                  byte var163 = 0;
                  Node var164 = var161.item(var163);
                  String var167 = "Secure";
                  Node var168 = this.getTagNode(var164, var167);
                  String var171 = this.getTagValue(var168);
                  if(var171 != null) {
                     String var173 = "off";
                     if(!var171.equalsIgnoreCase(var173)) {
                        StringBuffer var174 = new StringBuffer();
                        String var175 = "+";
                        StringBuffer var176 = var174.append(var175);
                        var178 = var176.append(var171).append("+").toString();
                        break label80;
                     }
                  }

                  var178 = "";
               }

               byte var180 = 0;
               Node var181 = var161.item(var180);
               String var184 = "ServAddr";
               Node var185 = this.getTagNode(var181, var184);
               String var188 = this.getTagValue(var185);
               StringBuffer var189 = new StringBuffer();
               String var190 = "://";
               StringBuffer var191 = var189.append(var190);
               String var193 = var191.append(var188).toString();
               byte var195 = 0;
               Node var196 = var161.item(var195);
               String var199 = "Port";
               Node var200 = this.getTagNode(var196, var199);
               String var203 = this.getTagValue(var200);
               if(var203 != null) {
                  StringBuffer var204 = new StringBuffer();
                  String var205 = ":";
                  StringBuffer var206 = var204.append(var205);
                  String var208 = var206.append(var203).toString();
               }

               StringBuffer var209 = new StringBuffer();
               StringBuffer var212 = var209.append(var74);
               if(var178 != null) {
                  StringBuffer var215 = var209.append(var178);
               }

               StringBuffer var218 = var209.append(var193);
               String var220 = var218.append(var203).toString();
               StringBuffer var221 = new StringBuffer();
               String var222 = "outgoingUri: ";
               StringBuffer var223 = var221.append(var222);
               String var225 = var223.append(var220).toString();
               int var226 = Log.d("AccountSetupCustomer", var225);
               byte var228 = 0;
               Node var229 = var161.item(var228);
               String var232 = "SmtpAuth";
               Node var233 = this.getTagNode(var229, var232);
               String var236 = this.getTagValue(var233);

               Exception var277;
               label130: {
                  AccountSettingsUtils.Provider var237;
                  try {
                     var237 = new AccountSettingsUtils.Provider();
                  } catch (Exception var283) {
                     var277 = var283;
                     break label130;
                  }

                  try {
                     label70: {
                        var237.id = var25;
                        var237.label = var25;
                        var237.domain = var1;
                        Object var241 = null;
                        var237.note = (String)var241;
                        URI var242 = new URI(var148);
                        var237.incomingUriTemplate = var242;
                        var237.incomingUsernameTemplate = var136;
                        URI var247 = new URI(var220);
                        var237.outgoingUriTemplate = var247;
                        if(var236 != null) {
                           String var252 = "off";
                           if(!var236.equalsIgnoreCase(var252)) {
                              var237.outgoingUsernameTemplate = var136;
                              break label70;
                           }
                        }

                        String var253 = "";
                        var237.outgoingUsernameTemplate = var253;
                     }

                     StringBuilder var254 = (new StringBuilder()).append("<provider id=\"");
                     String var255 = var237.id;
                     StringBuilder var256 = var254.append(var255).append("\" label=\"");
                     String var257 = var237.label;
                     StringBuilder var258 = var256.append(var257).append("\" domain=\"");
                     String var259 = var237.domain;
                     String var260 = var258.append(var259).append("\">").toString();
                     int var261 = Log.d("AccountSetupCustomer", var260);
                     StringBuilder var262 = (new StringBuilder()).append("\t<incoming uri=\"");
                     String var263 = var237.incomingUriTemplate.toString();
                     StringBuilder var264 = var262.append(var263).append("\" username=\"");
                     String var265 = var237.incomingUsernameTemplate;
                     String var266 = var264.append(var265).append("\">").toString();
                     int var267 = Log.d("AccountSetupCustomer", var266);
                     StringBuilder var268 = (new StringBuilder()).append("\t<outgoing uri=\"");
                     String var269 = var237.outgoingUriTemplate.toString();
                     StringBuilder var270 = var268.append(var269).append("\" username=\"");
                     String var271 = var237.outgoingUsernameTemplate;
                     String var272 = var270.append(var271).append("\">").toString();
                     int var273 = Log.d("AccountSetupCustomer", var272);
                     int var274 = Log.d("AccountSetupCustomer", "</provider>");
                  } catch (Exception var282) {
                     var277 = var282;
                     var2 = var237;
                     break label130;
                  }

                  var4 = var237;
                  break;
               }

               String var278 = "AccountSetupCustomer";
               String var279 = "Error while trying to load customer provider settings.";
               Log.e(var278, var279, var277);
            }

            ++var9;
         }
      }

      return var4;
   }

   public int getTagCount(NodeList var1) {
      int var2 = 0;
      if(var1 != null) {
         var2 = var1.getLength();
      }

      return var2;
   }

   public boolean getTagEmailType(Node var1, String var2) {
      boolean var3;
      if(var1 == null) {
         var3 = true;
      } else {
         NodeList var4 = var1.getChildNodes();
         if(var4 != null) {
            int var5 = var4.getLength();

            for(int var6 = 0; var6 < var5; ++var6) {
               Node var7 = var4.item(var6);
               if(var7.getNodeName().equals(var2)) {
                  if(Integer.parseInt(this.getTagValue(var7)) == 1) {
                     var3 = true;
                  } else {
                     var3 = false;
                  }

                  return var3;
               }
            }
         }

         var3 = true;
      }

      return var3;
   }

   public NodeList getTagList(Node var1, String var2) {
      NodeList var3;
      if(mDoc != null && var1 != null) {
         Document var4 = mDoc;
         String var5 = var1.getNodeName();
         Element var6 = var4.createElement(var5);
         NodeList var7 = var1.getChildNodes();
         if(var7 != null) {
            int var8 = var7.getLength();

            for(int var9 = 0; var9 < var8; ++var9) {
               Node var10 = var7.item(var9);
               if(var10.getNodeName().equals(var2)) {
                  var6.appendChild(var10);
               }
            }
         }

         var3 = var6.getChildNodes();
      } else {
         var3 = null;
      }

      return var3;
   }

   public Node getTagNode(String var1) {
      Node var2;
      if(mRoot == null) {
         var2 = null;
      } else {
         Node var3 = mRoot;
         StringTokenizer var4 = new StringTokenizer(var1, ".");

         while(true) {
            if(!var4.hasMoreTokens()) {
               var2 = var3;
               break;
            }

            String var5 = var4.nextToken();
            if(var3 == null) {
               var2 = null;
               break;
            }

            var3 = this.getTagNode(var3, var5);
         }
      }

      return var2;
   }

   public Node getTagNode(Node var1, String var2) {
      NodeList var3 = var1.getChildNodes();
      Node var8;
      if(var3 != null) {
         int var4 = var3.getLength();

         for(int var5 = 0; var5 < var4; ++var5) {
            Node var6 = var3.item(var5);
            String var7 = var6.getNodeName();
            if(var2.equals(var7)) {
               var8 = var6;
               return var8;
            }
         }
      }

      var8 = null;
      return var8;
   }

   public String getTagValue(Node var1) {
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getFirstChild().getNodeValue();
      }

      return var2;
   }

   public void loadXMLFile() {
      try {
         mFilePath = getCustomerFilePath("customer.xml");
         DocumentBuilder var1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         String var2 = mFilePath;
         File var3 = new File(var2);
         mDoc = var1.parse(var3);
         mRoot = mDoc.getDocumentElement();
         this.setAccountSetupCustomer();
      } catch (ParserConfigurationException var13) {
         String var5 = "ParserConfigurationException:" + var13;
         int var6 = Log.e("AccountSetupCustomer", var5);
      } catch (SAXException var14) {
         String var8 = "SAXException: " + var14;
         int var9 = Log.e("AccountSetupCustomer", var8);
      } catch (IOException var15) {
         String var11 = "IOException: " + var15;
         int var12 = Log.e("AccountSetupCustomer", var11);
      }
   }

   public void loadXMLFile(String var1) {
      try {
         mFilePath = getCustomerFilePath(var1);
         DocumentBuilder var2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         String var3 = mFilePath;
         File var4 = new File(var3);
         mDoc = var2.parse(var4);
         mRoot = mDoc.getDocumentElement();
         this.setAccountSetupCustomer();
      } catch (ParserConfigurationException var14) {
         String var6 = "ParserConfigurationException:" + var14;
         int var7 = Log.e("AccountSetupCustomer", var6);
      } catch (SAXException var15) {
         String var9 = "SAXException: " + var15;
         int var10 = Log.e("AccountSetupCustomer", var9);
      } catch (IOException var16) {
         String var12 = "IOException: " + var16;
         int var13 = Log.e("AccountSetupCustomer", var12);
      }
   }

   public void setAccountSetupCustomer() {
      mCustomerNode = this.getTagNode("Settings.Messages.Email");
      Node var1 = mCustomerNode;
      mCustomerEmailType = this.getTagEmailType(var1, "Combinded");
      Node var2 = mCustomerNode;
      mCustomerList = this.getTagList(var2, "Account");
      NodeList var3 = mCustomerList;
      mCustomerCount = this.getTagCount(var3);
   }
}

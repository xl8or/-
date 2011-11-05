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
      return false;
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

            NodeList var13 = mCustomerList;
            var13.item(var9);
            NodeList var16 = mCustomerList;
            Node var18 = var16.item(var9);
            String var21 = "AccountName";
            Node var22 = this.getTagNode(var18, var21);
            String var25 = this.getTagValue(var22);
            NodeList var26 = mCustomerList;
            Node var28 = var26.item(var9);
            String var31 = "EmailAddr";
            Node var32 = this.getTagNode(var28, var31);
            String var35 = this.getTagValue(var32);
            if(var35 != null) {
               String var37 = "@";
               byte var38 = var35.startsWith(var37);
               byte var39 = 1;
               if(var38 == var39) {
                  byte var41 = 1;
                  var35 = var35.substring(var41);
               }
            }

            if(var35 != null && var35.equalsIgnoreCase(var1)) {
               NodeList var52 = mCustomerList;
               Node var54 = var52.item(var9);
               String var57 = "Incoming";
               NodeList var58 = this.getTagList(var54, var57);
               if(var58 == null) {
                  var4 = null;
                  break;
               }

               byte var60 = 0;
               Node var61 = var58.item(var60);
               String var64 = "MailboxType";
               Node var65 = this.getTagNode(var61, var64);
               String var68 = this.getTagValue(var65);
               if(var68 == null) {
                  var4 = null;
                  break;
               }

               String var73;
               label98: {
                  String var70 = "imap3";
                  if(!var68.equalsIgnoreCase(var70)) {
                     String var72 = "imap4";
                     if(!var68.equalsIgnoreCase(var72)) {
                        var73 = "pop3";
                        break label98;
                     }
                  }

                  var73 = "imap";
               }

               String var90;
               label93: {
                  byte var75 = 0;
                  Node var76 = var58.item(var75);
                  String var79 = "Secure";
                  Node var80 = this.getTagNode(var76, var79);
                  String var83 = this.getTagValue(var80);
                  if(var83 != null) {
                     String var85 = "off";
                     if(!var83.equalsIgnoreCase(var85)) {
                        StringBuffer var86 = new StringBuffer();
                        String var87 = "+";
                        StringBuffer var88 = var86.append(var87);
                        var90 = var88.append(var83).append("+").toString();
                        break label93;
                     }
                  }

                  var90 = "";
               }

               byte var92 = 0;
               Node var93 = var58.item(var92);
               String var96 = "ServAddr";
               Node var97 = this.getTagNode(var93, var96);
               String var100 = this.getTagValue(var97);
               StringBuffer var101 = new StringBuffer();
               String var102 = "://";
               StringBuffer var103 = var101.append(var102);
               String var105 = var103.append(var100).toString();
               byte var107 = 0;
               Node var108 = var58.item(var107);
               String var111 = "Port";
               Node var112 = this.getTagNode(var108, var111);
               String var115 = this.getTagValue(var112);
               if(var115 != null) {
                  StringBuffer var116 = new StringBuffer();
                  String var117 = ":";
                  StringBuffer var118 = var116.append(var117);
                  var115 = var118.append(var115).toString();
               }

               NodeList var120 = mCustomerList;
               Node var122 = var120.item(var9);
               String var125 = "LoginType";
               Node var126 = this.getTagNode(var122, var125);
               String var129 = this.getTagValue(var126);
               String var130;
               if(var129 != null && var129.equalsIgnoreCase("domain_type")) {
                  var130 = "$email";
               } else {
                  var130 = "$user";
               }

               StringBuffer var131 = new StringBuffer();
               StringBuffer var134 = var131.append(var73);
               if(var90 != null) {
                  StringBuffer var137 = var131.append(var90);
               }

               String var142;
               NodeList var155;
               String var172;
               label81: {
                  StringBuffer var140 = var131.append(var105);
                  var142 = var140.append(var115).toString();
                  StringBuffer var143 = new StringBuffer();
                  String var144 = "incomingUri: ";
                  StringBuffer var145 = var143.append(var144);
                  String var147 = var145.append(var142).toString();
                  int var148 = Log.d("AccountSetupCustomer", var147);
                  NodeList var149 = mCustomerList;
                  Node var151 = var149.item(var9);
                  String var154 = "Outgoing";
                  var155 = this.getTagList(var151, var154);
                  var68 = "smtp";
                  byte var157 = 0;
                  Node var158 = var155.item(var157);
                  String var161 = "Secure";
                  Node var162 = this.getTagNode(var158, var161);
                  String var165 = this.getTagValue(var162);
                  if(var165 != null) {
                     String var167 = "off";
                     if(!var165.equalsIgnoreCase(var167)) {
                        StringBuffer var168 = new StringBuffer();
                        String var169 = "+";
                        StringBuffer var170 = var168.append(var169);
                        var172 = var170.append(var165).append("+").toString();
                        break label81;
                     }
                  }

                  var172 = "";
               }

               byte var174 = 0;
               Node var175 = var155.item(var174);
               String var178 = "ServAddr";
               Node var179 = this.getTagNode(var175, var178);
               String var182 = this.getTagValue(var179);
               StringBuffer var183 = new StringBuffer();
               String var184 = "://";
               StringBuffer var185 = var183.append(var184);
               String var187 = var185.append(var182).toString();
               byte var189 = 0;
               Node var190 = var155.item(var189);
               String var193 = "Port";
               Node var194 = this.getTagNode(var190, var193);
               String var197 = this.getTagValue(var194);
               if(var197 != null) {
                  StringBuffer var198 = new StringBuffer();
                  String var199 = ":";
                  StringBuffer var200 = var198.append(var199);
                  String var202 = var200.append(var197).toString();
               }

               StringBuffer var203 = new StringBuffer();
               StringBuffer var206 = var203.append(var68);
               if(var172 != null) {
                  StringBuffer var209 = var203.append(var172);
               }

               StringBuffer var212 = var203.append(var187);
               String var214 = var212.append(var197).toString();
               StringBuffer var215 = new StringBuffer();
               String var216 = "outgoingUri: ";
               StringBuffer var217 = var215.append(var216);
               String var219 = var217.append(var214).toString();
               int var220 = Log.d("AccountSetupCustomer", var219);
               byte var222 = 0;
               Node var223 = var155.item(var222);
               String var226 = "SmtpAuth";
               Node var227 = this.getTagNode(var223, var226);
               String var230 = this.getTagValue(var227);

               Exception var271;
               label126: {
                  AccountSettingsUtils.Provider var231;
                  try {
                     var231 = new AccountSettingsUtils.Provider();
                  } catch (Exception var277) {
                     var271 = var277;
                     break label126;
                  }

                  try {
                     label71: {
                        var231.id = var25;
                        var231.label = var25;
                        var231.domain = var1;
                        Object var235 = null;
                        var231.note = (String)var235;
                        URI var236 = new URI(var142);
                        var231.incomingUriTemplate = var236;
                        var231.incomingUsernameTemplate = var130;
                        URI var241 = new URI(var214);
                        var231.outgoingUriTemplate = var241;
                        if(var230 != null) {
                           String var246 = "off";
                           if(!var230.equalsIgnoreCase(var246)) {
                              var231.outgoingUsernameTemplate = var130;
                              break label71;
                           }
                        }

                        String var247 = "";
                        var231.outgoingUsernameTemplate = var247;
                     }

                     StringBuilder var248 = (new StringBuilder()).append("<provider id=\"");
                     String var249 = var231.id;
                     StringBuilder var250 = var248.append(var249).append("\" label=\"");
                     String var251 = var231.label;
                     StringBuilder var252 = var250.append(var251).append("\" domain=\"");
                     String var253 = var231.domain;
                     String var254 = var252.append(var253).append("\">").toString();
                     int var255 = Log.d("AccountSetupCustomer", var254);
                     StringBuilder var256 = (new StringBuilder()).append("\t<incoming uri=\"");
                     String var257 = var231.incomingUriTemplate.toString();
                     StringBuilder var258 = var256.append(var257).append("\" username=\"");
                     String var259 = var231.incomingUsernameTemplate;
                     String var260 = var258.append(var259).append("\">").toString();
                     int var261 = Log.d("AccountSetupCustomer", var260);
                     StringBuilder var262 = (new StringBuilder()).append("\t<outgoing uri=\"");
                     String var263 = var231.outgoingUriTemplate.toString();
                     StringBuilder var264 = var262.append(var263).append("\" username=\"");
                     String var265 = var231.outgoingUsernameTemplate;
                     String var266 = var264.append(var265).append("\">").toString();
                     int var267 = Log.d("AccountSetupCustomer", var266);
                     int var268 = Log.d("AccountSetupCustomer", "</provider>");
                  } catch (Exception var276) {
                     var271 = var276;
                     var2 = var231;
                     break label126;
                  }

                  var4 = var231;
                  break;
               }

               String var272 = "AccountSetupCustomer";
               String var273 = "Error while trying to load customer provider settings.";
               Log.e(var272, var273, var271);
            } else {
               StringBuffer var44 = new StringBuffer();
               String var45 = "domain: ";
               StringBuffer var46 = var44.append(var45);
               StringBuffer var48 = var46.append(var1).append(", emailAddr: ");
               String var50 = var48.append(var35).toString();
               int var51 = Log.d("AccountSetupCustomer", var50);
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

   public Node getTagNode(Node param1, String param2) {
      // $FF: Couldn't be decompiled
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

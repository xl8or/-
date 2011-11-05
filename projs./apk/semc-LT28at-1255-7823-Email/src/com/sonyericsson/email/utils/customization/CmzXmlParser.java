package com.sonyericsson.email.utils.customization;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;
import com.sonyericsson.email.utils.customization.AccountData;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class CmzXmlParser {

   private static final String ATTR_KEY = "key";
   private static final String ATTR_TYPE = "type";
   private static final String KEY_BRANDED_ICON = "branded-icon";
   private static final String KEY_BRANDED_LABEL = "branded-label";
   private static final String KEY_CHECK_INTERVAL = "check-interval";
   private static final String KEY_CHECK_INTERVAL_LIST = "check-interval-list";
   private static final String KEY_DOMAIN = "domain";
   private static final String KEY_EAS_CHECK_INTERVAL = "eas-check-interval";
   private static final String KEY_EAS_CHECK_INTERVAL_LIST = "eas-check-interval-list";
   private static final String KEY_EMAIL_ADDRESS = "email-address";
   private static final String KEY_EMAIL_NOTIFICATIONS = "email-notifications";
   private static final String KEY_EMAIL_SIGNATURE = "email-signature";
   private static final String KEY_INCOMINC_ENCRYPTION = "incoming-encryption";
   private static final String KEY_INCOMING_FULL_EMAIL_LOGIN = "incoming-full-email-login";
   private static final String KEY_INCOMING_PASSWORD = "incoming-password";
   private static final String KEY_INCOMING_PORT = "incoming-port";
   private static final String KEY_INCOMING_PROTOCOL = "incoming-protocol";
   private static final String KEY_INCOMING_SERVER = "incoming-server";
   private static final String KEY_INCOMING_USERNAME = "incoming-username";
   private static final String KEY_NOTIFICATION_TONE = "notification-tone";
   private static final String KEY_NOTIFICATION_VIBRATE = "notification-vibrate";
   private static final String KEY_OUTGOING_AUTHENTICATION = "outgoing-authentication";
   private static final String KEY_OUTGOING_ENCRYPTION = "outgoing-encryption";
   private static final String KEY_OUTGOING_FULL_EMAIL_LOGIN = "outgoing-full-email-login";
   private static final String KEY_OUTGOING_PASSWORD = "outgoing-password";
   private static final String KEY_OUTGOING_PORT = "outgoing-port";
   private static final String KEY_OUTGOING_SERVER = "outgoing-server";
   private static final String KEY_OUTGOING_USERNAME = "outgoing-username";
   private static final String KEY_RO_SEMC_PUSH_EMAIL = "ro-semc-push-email";
   private static final String TAG_GROUP = "group";
   private static final String TAG_GROUPS = "groups";
   private static final String TAG_SETTING = "setting";
   static final String TYPE_ACCOUNT_PRECONFIG = "account-preconfigured";
   static final String TYPE_DEF_UX_SETTINGS = "default-ux-settings";
   static final String TYPE_PROVIDERS_BRANDED = "providers-branded";
   static final String TYPE_PROVIDERS_OTHER = "providers-other";
   private Context mContext;


   CmzXmlParser(Context var1) {
      this.mContext = var1;
   }

   private Hashtable<String, String> getXmlData(XmlPullParser var1, String var2) {
      Hashtable var3 = new Hashtable();
      String var4 = "";
      boolean var5 = false;

      while(!var5) {
         try {
            int var6 = var1.next();
            if(var6 == 1) {
               break;
            }

            if(var6 == 3) {
               String var7 = var1.getName();
               if("group".equals(var7)) {
                  var5 = true;
                  continue;
               }
            }

            if(var6 == 2) {
               String var8 = var1.getName();
               if("setting".equals(var8)) {
                  String var9 = var1.getAttributeValue((String)null, "key");
                  if(var1.next() == 4) {
                     var4 = var1.getText().trim();
                  }

                  if(var9.trim().equalsIgnoreCase("domain") && var2.trim().length() > 0) {
                     String var10 = var4.trim();
                     String var11 = var2.trim();
                     if(!var10.equalsIgnoreCase(var11)) {
                        var5 = true;
                        var3 = null;
                        continue;
                     }
                  }

                  var3.put(var9, var4);
               } else {
                  StringBuilder var15 = (new StringBuilder()).append("IGNORING - Unexpected xml tag while loading customized settings: <");
                  String var16 = var1.getName();
                  String var17 = var15.append(var16).append("> - Expected: <setting>").toString();
                  int var18 = Log.i("Email", var17);
               }
            }
         } catch (XmlPullParserException var21) {
            int var14 = Log.e("Email", "Error while trying to load custom settings from xml.", var21);
            break;
         } catch (IOException var22) {
            int var20 = Log.e("Email", "Error while trying to load custom settings from xml.", var22);
            break;
         }
      }

      return var3;
   }

   private XmlPullParser getXmlParser(Reader var1) {
      XmlPullParser var2;
      try {
         var2 = XmlPullParserFactory.newInstance().newPullParser();
         var2.setInput(var1);
      } catch (XmlPullParserException var5) {
         int var4 = Log.e("Email", "Error while trying to generate XmlPullParser", var5);
      }

      return var2;
   }

   private boolean isCorrectType(String var1, boolean var2) {
      boolean var3 = false;
      if(var2 && "account-preconfigured".equals(var1) || "providers-branded".equals(var1) || "providers-other".equals(var1)) {
         var3 = true;
      }

      return var3;
   }

   public AccountData getAccountMasterReset(Reader var1, String var2) {
      AccountData var3 = null;
      XmlPullParser var4 = this.getXmlParser(var1);
      Hashtable var5 = this.getHashMasterReset(var4, var2, (boolean)1);
      if(var5 != null) {
         var3 = this.populateAccountData(var5);
      }

      return var3;
   }

   public AccountData[] getBrandedProviders(Reader var1) {
      XmlPullParser var2 = this.getXmlParser(var1);
      Vector var3 = this.getHashCmzData(var2, "providers-branded", (boolean)0);
      AccountData[] var10;
      if(var3.size() > 0) {
         Vector var4 = new Vector();
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            Hashtable var6 = (Hashtable)var5.next();
            AccountData var7 = this.populateAccountData(var6);
            var4.add(var7);
         }

         AccountData[] var9 = new AccountData[0];
         var10 = (AccountData[])var4.toArray(var9);
      } else {
         var10 = null;
      }

      return var10;
   }

   public AccountData getCustomUXSettings(Reader var1) {
      AccountData var2 = null;
      XmlPullParser var3 = this.getXmlParser(var1);
      Hashtable var4 = this.getCustomUxSettingsHash(var3);
      if(var4 != null) {
         var2 = this.populateAccountData(var4);
      }

      return var2;
   }

   Hashtable<String, String> getCustomUxSettingsHash(XmlPullParser var1) {
      return this.getUxSettings(var1);
   }

   public AccountData getDefaultUXSettings() {
      AccountData var1 = null;
      Hashtable var2 = this.getDefaultUxSettingsHash();
      if(var2 != null) {
         var1 = this.populateAccountData(var2);
      }

      return var1;
   }

   Hashtable<String, String> getDefaultUxSettingsHash() {
      XmlResourceParser var1 = this.mContext.getResources().getXml(2131034116);
      return this.getUxSettings(var1);
   }

   Vector<Hashtable<String, String>> getHashCmzData(XmlPullParser var1, String var2, boolean var3) {
      return this.getHashCmzData(var1, var2, var3, "");
   }

   Vector<Hashtable<String, String>> getHashCmzData(XmlPullParser param1, String param2, boolean param3, String param4) {
      // $FF: Couldn't be decompiled
   }

   Hashtable<String, String> getHashMasterReset(XmlPullParser param1, String param2, boolean param3) {
      // $FF: Couldn't be decompiled
   }

   public AccountData getOtherProvider(Reader var1, String var2) {
      AccountData var3 = null;
      XmlPullParser var4 = this.getXmlParser(var1);
      Hashtable var5 = this.getHashMasterReset(var4, var2, (boolean)0);
      if(var5 != null) {
         var3 = this.populateAccountData(var5);
      }

      return var3;
   }

   public AccountData getPreconfigAccount(Reader var1) {
      AccountData var2 = null;
      XmlPullParser var3 = this.getXmlParser(var1);
      Vector var4 = this.getHashCmzData(var3, "account-preconfigured", (boolean)1);
      int var5 = var4.size();
      if(1 == var5) {
         Hashtable var6 = (Hashtable)var4.elementAt(0);
         var2 = this.populateAccountData(var6);
      }

      return var2;
   }

   Hashtable<String, String> getUxSettings(XmlPullParser param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean hasBrandedProvider(Reader var1) {
      XmlPullParser var2 = this.getXmlParser(var1);
      return this.hasGroupsTagWithType(var2, "providers-branded");
   }

   public boolean hasCustomUxSettings(Reader var1) {
      XmlPullParser var2 = this.getXmlParser(var1);
      return this.hasGroupsTagWithType(var2, "default-ux-settings");
   }

   boolean hasGroupsTagWithType(XmlPullParser param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean hasOtherProvider(Reader var1) {
      XmlPullParser var2 = this.getXmlParser(var1);
      return this.hasGroupsTagWithType(var2, "providers-other");
   }

   public boolean hasPreconfiguredAccount(Reader var1) {
      XmlPullParser var2 = this.getXmlParser(var1);
      return this.hasGroupsTagWithType(var2, "account-preconfigured");
   }

   AccountData populateAccountData(Hashtable<String, String> var1) {
      AccountData var2 = new AccountData();
      String var3 = (String)var1.get("domain");
      if(var3 != null) {
         var2.setDomain(var3);
      }

      String var4 = (String)var1.get("email-address");
      if(var4 != null) {
         var2.setEmailAddress(var4);
      }

      String var5 = (String)var1.get("incoming-username");
      if(var5 != null) {
         var2.setIncomingUsername(var5);
      }

      String var6 = (String)var1.get("incoming-password");
      if(var6 != null) {
         var2.setIncomingPassword(var6);
      }

      String var7 = (String)var1.get("outgoing-username");
      if(var7 != null) {
         var2.setOutgoingUsername(var7);
      }

      String var8 = (String)var1.get("outgoing-password");
      if(var8 != null) {
         var2.setOutgoingPassword(var8);
      }

      String var9 = (String)var1.get("branded-icon");
      if(var9 != null) {
         var2.setBrandedIconUri(var9);
      }

      String var10 = (String)var1.get("branded-label");
      if(var10 != null) {
         var2.setBrandedLabel(var10);
      }

      String var11 = (String)var1.get("incoming-protocol");
      if(var11 != null) {
         var2.setIncomingProtocol(var11);
      }

      String var12 = (String)var1.get("incoming-server");
      if(var12 != null) {
         var2.setIncomingServer(var12);
      }

      String var13 = (String)var1.get("incoming-encryption");
      if(var13 != null) {
         var2.setIncomingEncryption(var13);
      }

      String var14 = (String)var1.get("incoming-port");
      if(var14 != null) {
         var2.setIncomingPort(var14);
      }

      String var15 = (String)var1.get("incoming-full-email-login");
      if(var15 != null) {
         var2.setHasIncomingFullEmailLogin(var15);
      }

      String var16 = (String)var1.get("outgoing-server");
      if(var16 != null) {
         var2.setOutgoingServer(var16);
      }

      String var17 = (String)var1.get("outgoing-encryption");
      if(var17 != null) {
         var2.setOutgoingEncryption(var17);
      }

      String var18 = (String)var1.get("outgoing-port");
      if(var18 != null) {
         var2.setOutgoingPort(var18);
      }

      String var19 = (String)var1.get("outgoing-full-email-login");
      if(var19 != null) {
         var2.setHasOutgoingFullEmailLogin(var19);
      }

      String var20 = (String)var1.get("outgoing-authentication");
      if(var20 != null) {
         var2.setHasOutgoingAuthentication(var20);
      }

      String var21 = (String)var1.get("check-interval-list");
      if(var21 != null) {
         int[] var22 = this.splitCheckListToArray(var21);
         if(var22 != null) {
            var2.setCheckIntervalList(var22);
         }
      }

      String var23 = (String)var1.get("eas-check-interval-list");
      if(var23 != null) {
         int[] var24 = this.splitCheckListToArray(var23);
         if(var24 != null) {
            var2.setEasCheckIntervalList(var24);
         }
      }

      String var25 = (String)var1.get("check-interval");
      if(var25 != null) {
         var2.setCheckIntervalSeconds(var25);
      }

      String var26 = (String)var1.get("eas-check-interval");
      if(var26 != null) {
         var2.setEasCheckIntervalSeconds(var26);
      }

      String var27 = (String)var1.get("email-notifications");
      if(var27 != null) {
         var2.setHasEmailNotifications(var27);
      }

      String var28 = (String)var1.get("notification-tone");
      if(var28 != null) {
         var2.setNotificationTone(var28);
      }

      String var29 = (String)var1.get("notification-vibrate");
      if(var29 != null) {
         var2.setHasNotificationVibrate(var29);
      }

      String var30 = (String)var1.get("ro-semc-push-email");
      if(var30 != null) {
         var2.setSysPropMailpush(var30);
      }

      String var31 = (String)var1.get("email-signature");
      if(var31 != null) {
         var2.setSignature(var31);
      }

      return var2;
   }

   int[] splitCheckListToArray(String var1) {
      String[] var2 = var1.split("\\|");
      int[] var3 = new int[var2.length];
      String[] var4 = var2;
      int var5 = var2.length;
      int var6 = 0;
      int var7 = 0;

      int[] var15;
      while(true) {
         if(var6 >= var5) {
            Arrays.sort(var3);
            if(var3.length > 0) {
               var15 = var3;
            } else {
               var15 = null;
            }
            break;
         }

         String var8 = var4[var6];

         int var11;
         label32: {
            NumberFormatException var12;
            label43: {
               int var9;
               try {
                  var9 = Integer.parseInt(var8.trim());
               } catch (NumberFormatException var18) {
                  var12 = var18;
                  break label43;
               }

               int var10 = var9;
               var11 = var7 + 1;

               try {
                  var3[var7] = var10;
                  break label32;
               } catch (NumberFormatException var17) {
                  var12 = var17;
               }
            }

            int var14 = Log.e("Email", "Error while parsing String into Int: ", var12);
            var15 = null;
            break;
         }

         ++var6;
         var7 = var11;
      }

      return var15;
   }
}

package com.sonyericsson.email.utils.r2r;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class R2RParser {

   private static final String OUTGOING_PROTOCOL = "smtp";
   private static final String TAG_AAUTHNAME_FORMAT = "aauthname-format";
   private static final String TAG_ACCOUNTS = "accounts";
   private static final String TAG_ADDRESS = "address";
   private static final String TAG_CONFIGURATION = "configuration";
   private static final String TAG_DOMAIN = "domain";
   private static final String TAG_EMAIL = "email";
   private static final String TAG_ENCRYPTION_TYPE = "encryption-type";
   private static final String TAG_ID = "id";
   private static final String TAG_NAME = "name";
   private static final String TAG_PORT = "port";
   private static final String TAG_PROTOCOL = "protocol";
   private static final String TAG_REQUIRES_AUTH = "requires-auth";
   private String mDomain;
   private R2RParser.EmailR2RSettings mEmailR2RSettings;
   private String mSettingName;


   private R2RParser() {}

   public static R2RParser newParser() {
      return new R2RParser();
   }

   private R2RParser.EmailR2RSettings.Configuration parseConfiguration(XmlPullParser var1) throws XmlPullParserException, IOException {
      String var2 = null;
      int var3 = var1.next();

      while(true) {
         if(var3 == 2) {
            String var4 = var1.getName();
            if("id".equals(var4)) {
               if(var1.next() == 4) {
                  String var5 = var1.getText();
               }
            } else if("protocol".equals(var4)) {
               if(var1.next() == 4 && var1.getText().equals("imap4")) {
                  ;
               }
            } else if("encryption-type".equals(var4)) {
               if(var1.next() == 4) {
                  var2 = var1.getText();
                  if(!var2.equalsIgnoreCase("ssl/tls") && !var2.equalsIgnoreCase("starttls") && var2.equalsIgnoreCase("none")) {
                     ;
                  }
               }
            } else if("address".equals(var4)) {
               if(var1.next() == 4) {
                  String var6 = var1.getText();
               }
            } else if("port".equals(var4)) {
               if(var1.next() == 4) {
                  String var7 = var1.getText();

                  try {
                     int var8 = Integer.parseInt(var7);
                  } catch (NumberFormatException var18) {
                     ;
                  }
               }
            } else if("requires-auth".equals(var4)) {
               if(var1.next() == 4) {
                  Boolean var10 = Boolean.valueOf(Boolean.parseBoolean(var1.getText()));
               }
            } else if("aauthname-format".equals(var4) && var1.next() == 4) {
               String var11 = var1.getText();
            }
         } else if(var3 == 3) {
            String var12 = var1.getName();
            if("configuration".equals(var12)) {
               R2RParser.EmailR2RSettings.Configuration var14;
               if(false && false && var2 != null && false && -1 != -1 && false && false) {
                  boolean var13 = null.booleanValue();
                  var14 = new R2RParser.EmailR2RSettings.Configuration((String)null, (String)null, var2, (String)null, -1, var13, (String)null);
               } else {
                  String var15 = "Invalid configuration (id=" + null + ", protocol=" + null + ", encryptionType=" + var2 + ", address=" + null + ", port=" + -1 + ")";
                  int var16 = Log.e("Email", var15);
                  var14 = null;
               }

               return var14;
            }
         }

         var3 = var1.next();
      }
   }

   public R2RParser.EmailR2RSettings parse(InputStream var1) throws XmlPullParserException, IOException {
      XmlPullParser var2 = XmlPullParserFactory.newInstance().newPullParser();
      InputStreamReader var3 = new InputStreamReader(var1);
      var2.setInput(var3);

      for(int var4 = var2.getEventType(); var4 != 1; var4 = var2.next()) {
         if(var4 == 2) {
            String var5 = var2.getName();
            if("email".equals(var5)) {
               this.mSettingName = null;
               this.mDomain = null;
               R2RParser.EmailR2RSettings var6 = new R2RParser.EmailR2RSettings();
               this.mEmailR2RSettings = var6;
            } else if("name".equals(var5)) {
               if(var2.next() == 4) {
                  String var7 = var2.getText();
                  this.mSettingName = var7;
               }
            } else if("domain".equals(var5)) {
               if(var2.next() == 4) {
                  String var8 = var2.getText();
                  this.mDomain = var8;
               }
            } else if("configuration".equals(var5)) {
               R2RParser.EmailR2RSettings.Configuration var9 = this.parseConfiguration(var2);
               if(var9 != null) {
                  String var10 = var9.protocol;
                  if("smtp".equals(var10)) {
                     this.mEmailR2RSettings.addOutgoingConfig(var9);
                  } else {
                     this.mEmailR2RSettings.addIncomingConfig(var9);
                  }
               }
            }
         } else if(var4 == 3) {
            String var11 = var2.getName();
            if("email".equals(var11)) {
               R2RParser.EmailR2RSettings var12 = this.mEmailR2RSettings;
               String var13 = this.mDomain;
               var12.domain = var13;
               R2RParser.EmailR2RSettings var14 = this.mEmailR2RSettings;
               String var15 = this.mSettingName;
               var14.name = var15;
               this.mDomain = null;
               this.mSettingName = null;
            }
         }
      }

      var1.close();
      if(this.mEmailR2RSettings.domain == null || !this.mEmailR2RSettings.finishParsing()) {
         this.mEmailR2RSettings = null;
      }

      return this.mEmailR2RSettings;
   }

   public static class EmailR2RSettings {

      private static final String IMAP = "imap";
      private static final String NONE_ENCRYPTION = "";
      private static final String POP3 = "pop3";
      private static final Comparator<R2RParser.EmailR2RSettings.Configuration> sConfigIncomingComparator = new R2RParser.EmailR2RSettings.1();
      private static final Comparator<R2RParser.EmailR2RSettings.Configuration> sConfigOutgoingComparator = new R2RParser.EmailR2RSettings.2();
      public String domain;
      private ArrayList<R2RParser.EmailR2RSettings.Configuration> mIncomingConfigs;
      private ArrayList<R2RParser.EmailR2RSettings.Configuration> mOutgoingConfigs;
      public String name;


      EmailR2RSettings() {
         ArrayList var1 = new ArrayList();
         this.mIncomingConfigs = var1;
         ArrayList var2 = new ArrayList();
         this.mOutgoingConfigs = var2;
      }

      void addIncomingConfig(R2RParser.EmailR2RSettings.Configuration var1) {
         this.mIncomingConfigs.add(var1);
      }

      void addOutgoingConfig(R2RParser.EmailR2RSettings.Configuration var1) {
         this.mOutgoingConfigs.add(var1);
      }

      public boolean finishParsing() {
         boolean var1;
         if(this.mIncomingConfigs.size() != 0 && this.mOutgoingConfigs.size() != 0) {
            ArrayList var2 = this.mIncomingConfigs;
            Comparator var3 = sConfigIncomingComparator;
            Collections.sort(var2, var3);
            ArrayList var4 = this.mOutgoingConfigs;
            Comparator var5 = sConfigOutgoingComparator;
            Collections.sort(var4, var5);
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public R2RParser.EmailR2RSettings.Configuration[] getAllIncomingConfig() {
         R2RParser.EmailR2RSettings.Configuration[] var1 = new R2RParser.EmailR2RSettings.Configuration[this.mIncomingConfigs.size()];
         this.mIncomingConfigs.toArray(var1);
         return var1;
      }

      public R2RParser.EmailR2RSettings.Configuration[] getAllOutgoingConfig() {
         R2RParser.EmailR2RSettings.Configuration[] var1 = new R2RParser.EmailR2RSettings.Configuration[this.mOutgoingConfigs.size()];
         this.mOutgoingConfigs.toArray(var1);
         return var1;
      }

      public R2RParser.EmailR2RSettings.Configuration getPreferedIncomingConfig() {
         return (R2RParser.EmailR2RSettings.Configuration)this.mIncomingConfigs.get(0);
      }

      public R2RParser.EmailR2RSettings.Configuration getPreferedOutgoingConfig() {
         return (R2RParser.EmailR2RSettings.Configuration)this.mOutgoingConfigs.get(0);
      }

      public static class Configuration {

         public final String aauthnameFormat;
         public final String address;
         public final String encryptionType;
         public final String id;
         public final boolean isRequiresAuth;
         public final int port;
         public final String protocol;


         Configuration(String var1, String var2, String var3, String var4, int var5, boolean var6, String var7) {
            this.id = var1;
            this.protocol = var2;
            this.encryptionType = var3;
            this.address = var4;
            this.port = var5;
            this.isRequiresAuth = var6;
            this.aauthnameFormat = var7;
         }

         public boolean equals(Object var1) {
            boolean var2;
            if(!(var1 instanceof R2RParser.EmailR2RSettings.Configuration)) {
               var2 = false;
            } else {
               R2RParser.EmailR2RSettings.Configuration var3 = (R2RParser.EmailR2RSettings.Configuration)var1;
               String var4 = this.id;
               String var5 = var3.id;
               if(var4.equals(var5)) {
                  String var6 = this.protocol;
                  String var7 = var3.protocol;
                  if(var6.equals(var7)) {
                     String var8 = this.encryptionType;
                     String var9 = var3.encryptionType;
                     if(var8.equals(var9)) {
                        String var10 = this.address;
                        String var11 = var3.address;
                        if(var10.equals(var11)) {
                           int var12 = this.port;
                           int var13 = var3.port;
                           if(var12 == var13) {
                              var2 = true;
                              return var2;
                           }
                        }
                     }
                  }
               }

               var2 = false;
            }

            return var2;
         }

         public int hashCode() {
            int var1 = this.id.hashCode();
            int var2 = this.protocol.hashCode();
            int var3 = var1 + var2;
            int var4 = this.encryptionType.hashCode();
            int var5 = var3 + var4;
            int var6 = this.address.hashCode();
            int var7 = var5 + var6;
            int var8 = this.port;
            return var7 + var8;
         }
      }

      static class 2 implements Comparator<R2RParser.EmailR2RSettings.Configuration> {

         2() {}

         public int compare(R2RParser.EmailR2RSettings.Configuration var1, R2RParser.EmailR2RSettings.Configuration var2) {
            byte var3;
            if(!var1.encryptionType.equals("") && var2.encryptionType.equals("")) {
               var3 = -1;
            } else if(var1.encryptionType.equals("") && !var2.encryptionType.equals("")) {
               var3 = 1;
            } else {
               var3 = 0;
            }

            return var3;
         }
      }

      static class 1 implements Comparator<R2RParser.EmailR2RSettings.Configuration> {

         1() {}

         public int compare(R2RParser.EmailR2RSettings.Configuration var1, R2RParser.EmailR2RSettings.Configuration var2) {
            byte var3;
            if(var1.protocol.equals("imap") && var2.protocol.equals("pop3")) {
               var3 = -1;
            } else if(var1.protocol.equals("pop3") && var2.protocol.equals("imap")) {
               var3 = 1;
            } else if(!var1.encryptionType.equals("") && var2.encryptionType.equals("")) {
               var3 = -1;
            } else if(var1.encryptionType.equals("") && !var2.encryptionType.equals("")) {
               var3 = 1;
            } else {
               var3 = 0;
            }

            return var3;
         }
      }
   }
}

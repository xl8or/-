package com.android.email.wds;

import com.android.email.Email;
import com.android.email.wds.EmailProviderWds;
import com.android.email.wds.ServicemineEmailSetting;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ServicemineParser {

   private static final String TAG = "SERVICEMINE-PARSER: ";


   public ServicemineParser() {}

   public EmailProviderWds parse(InputSource var1) throws ParserConfigurationException, SAXException, IOException, URISyntaxException, EmailProviderWds.MissingEmailConnectionException {
      new ArrayList();
      SAXParser var3 = SAXParserFactory.newInstance().newSAXParser();
      ServicemineParser.ServiceMineXmlHandler var4 = new ServicemineParser.ServiceMineXmlHandler((ServicemineParser.1)null);
      var3.parse(var1, var4);
      Collection var5 = var4.getSettings();
      Iterator var6 = var5.iterator();

      while(var6.hasNext()) {
         ServicemineEmailSetting var7 = (ServicemineEmailSetting)var6.next();
         StringBuilder var8 = (new StringBuilder()).append("");
         String var9 = var7.id;
         String var10 = var8.append(var9).toString();
         Email.logd("SERVICEMINE-PARSER: ", var10);
         StringBuilder var11 = (new StringBuilder()).append("");
         int var12 = var7.port;
         String var13 = var11.append(var12).toString();
         Email.logd("SERVICEMINE-PARSER: ", var13);
         StringBuilder var14 = (new StringBuilder()).append("");
         String var15 = var7.server;
         String var16 = var14.append(var15).toString();
         Email.logd("SERVICEMINE-PARSER: ", var16);
         StringBuilder var17 = (new StringBuilder()).append("");
         ServicemineEmailSetting.SecurityType var18 = var7.securityType;
         String var19 = var17.append(var18).toString();
         Email.logd("SERVICEMINE-PARSER: ", var19);
         Email.logd("SERVICEMINE-PARSER: ", "===========================");
      }

      EmailProviderWds var20 = new EmailProviderWds(var5);
      String var21 = var4.autoCorrectedDomain;
      var20.autoCorrectedDomain = var21;
      String var22 = var4.authNameFormat;
      var20.authNameFormat = var22;
      return var20;
   }

   private class ServiceMineXmlHandler extends DefaultHandler {

      String authNameFormat;
      String autoCorrectedDomain;
      Stack<String> characteristics;
      StringBuffer characters;
      ServicemineEmailSetting currentSetting;
      Map<String, ServicemineEmailSetting> settings;


      private ServiceMineXmlHandler() {
         HashMap var2 = new HashMap();
         this.settings = var2;
         this.characters = null;
         this.autoCorrectedDomain = null;
         this.authNameFormat = "$user";
         Stack var3 = new Stack();
         this.characteristics = var3;
      }

      // $FF: synthetic method
      ServiceMineXmlHandler(ServicemineParser.1 var2) {
         this();
      }

      public void characters(char[] var1, int var2, int var3) throws SAXException {
         if(this.characters == null) {
            StringBuffer var4 = new StringBuffer();
            this.characters = var4;
         }

         this.characters.append(var1, var2, var3);
      }

      public void endElement(String var1, String var2, String var3) throws SAXException {
         String var4 = var3;
         if(var3 == null || var3 == "") {
            var4 = var2;
         }

         if("configuration".equals(var4)) {
            Map var5 = this.settings;
            String var6 = this.currentSetting.id;
            ServicemineEmailSetting var7 = this.currentSetting;
            var5.put(var6, var7);
            this.currentSetting = null;
         } else if("protocol".equals(var4)) {
            ServicemineEmailSetting var9 = this.currentSetting;
            String var10 = this.characters.toString().trim();
            var9.setProtocol(var10);
         } else if("encryption-type".equals(var4)) {
            ServicemineEmailSetting var11 = this.currentSetting;
            String var12 = this.characters.toString().trim();
            var11.setSecurityType(var12);
         } else if("characteristic".equals(var4)) {
            Object var13 = this.characteristics.pop();
         } else if("lookup-domain".equals(var4)) {
            String var14 = this.characters.toString();
            this.autoCorrectedDomain = var14;
         } else if("aauthname-format".equals(var4)) {
            String var15 = this.characters.toString().trim();
            if("email-address".equals(var15)) {
               this.authNameFormat = "$email";
            }
         }

         this.characters = null;
      }

      public Collection<ServicemineEmailSetting> getSettings() {
         return this.settings.values();
      }

      public void startElement(String var1, String var2, String var3, Attributes var4) throws SAXException {
         String var5 = var3;
         if(var3 == null || var3 == "") {
            var5 = var2;
         }

         if("configuration".equals(var5)) {
            String var6 = var4.getValue("id");
            ServicemineEmailSetting var7 = (ServicemineEmailSetting)this.settings.get(var6);
            this.currentSetting = var7;
            StringBuilder var8 = (new StringBuilder()).append("id : ").append(var6).append(" | ");
            ServicemineEmailSetting var9 = this.currentSetting;
            String var10 = var8.append(var9).toString();
            Email.loge("SERVICEMINE-PARSER: ", var10);
            if(this.currentSetting == null) {
               ServicemineEmailSetting var11 = new ServicemineEmailSetting();
               this.currentSetting = var11;
            }

            StringBuilder var12 = (new StringBuilder()).append("id : ").append(var6).append(" | ");
            ServicemineEmailSetting var13 = this.currentSetting;
            String var14 = var12.append(var13).toString();
            Email.loge("SERVICEMINE-PARSER: ", var14);
            this.currentSetting.id = var6;
         } else if("characteristic".equals(var5)) {
            Stack var15 = this.characteristics;
            String var16 = var4.getValue("type");
            var15.push(var16);
         } else if("parm".equals(var5)) {
            String var18 = var4.getValue("name");
            if("ADDR".equals(var18)) {
               Object var19 = this.characteristics.peek();
               if("APPADDR".equals(var19)) {
                  ServicemineEmailSetting var20 = this.currentSetting;
                  String var21 = var4.getValue("value");
                  var20.server = var21;
                  return;
               }
            }

            if("PORTNBR".equals(var18)) {
               Object var22 = this.characteristics.peek();
               if("PORT".equals(var22)) {
                  ServicemineEmailSetting var23 = this.currentSetting;
                  int var24 = Integer.valueOf(var4.getValue("value")).intValue();
                  var23.port = var24;
               }
            }
         } else if("error".equals(var5)) {
            throw new SAXException("Error tag encountered");
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

package com.android.email.wds;

import com.android.email.wds.SimpleIdentifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

class ServicemineEmailSetting {

   public static ServicemineEmailSetting.Protocol EAS = new ServicemineEmailSetting.Protocol("eas", (ServicemineEmailSetting.1)null);
   public static ServicemineEmailSetting.Protocol IMAP4 = new ServicemineEmailSetting.Protocol("imap4", (ServicemineEmailSetting.1)null);
   public static ServicemineEmailSetting.SecurityType NONE = new ServicemineEmailSetting.SecurityType("none", (ServicemineEmailSetting.1)null);
   public static ServicemineEmailSetting.Protocol POP3 = new ServicemineEmailSetting.Protocol("pop3", (ServicemineEmailSetting.1)null);
   public static ServicemineEmailSetting.Protocol SMTP = new ServicemineEmailSetting.Protocol("smtp", (ServicemineEmailSetting.1)null);
   public static ServicemineEmailSetting.SecurityType SSL = new ServicemineEmailSetting.SecurityType("ssl/tls", (ServicemineEmailSetting.1)null);
   public static ServicemineEmailSetting.SecurityType TLS = new ServicemineEmailSetting.SecurityType("starttls", (ServicemineEmailSetting.1)null);
   public static Map<String, ServicemineEmailSetting.Protocol> protocols = new HashMap();
   public static Map<String, ServicemineEmailSetting.SecurityType> securityTypes = new HashMap();
   public String id;
   public int port;
   public ServicemineEmailSetting.Protocol protocol;
   public ServicemineEmailSetting.SecurityType securityType;
   public String server;


   public ServicemineEmailSetting() {
      Map var1 = securityTypes;
      String var2 = TLS.toString();
      ServicemineEmailSetting.SecurityType var3 = TLS;
      var1.put(var2, var3);
      Map var5 = securityTypes;
      String var6 = SSL.toString();
      ServicemineEmailSetting.SecurityType var7 = SSL;
      var5.put(var6, var7);
      Map var9 = securityTypes;
      String var10 = NONE.toString();
      ServicemineEmailSetting.SecurityType var11 = NONE;
      var9.put(var10, var11);
      Map var13 = protocols;
      String var14 = IMAP4.toString();
      ServicemineEmailSetting.Protocol var15 = IMAP4;
      var13.put(var14, var15);
      Map var17 = protocols;
      String var18 = POP3.toString();
      ServicemineEmailSetting.Protocol var19 = POP3;
      var17.put(var18, var19);
      Map var21 = protocols;
      String var22 = SMTP.toString();
      ServicemineEmailSetting.Protocol var23 = SMTP;
      var21.put(var22, var23);
      Map var25 = protocols;
      String var26 = EAS.toString();
      ServicemineEmailSetting.Protocol var27 = EAS;
      var25.put(var26, var27);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof ServicemineEmailSetting)) {
         var2 = false;
      } else {
         ServicemineEmailSetting var3 = (ServicemineEmailSetting)var1;
         String var4 = this.server;
         String var5 = var3.server;
         if(var4.equals(var5)) {
            int var6 = this.port;
            int var7 = var3.port;
            if(var6 == var7) {
               ServicemineEmailSetting.Protocol var8 = this.protocol;
               ServicemineEmailSetting.Protocol var9 = var3.protocol;
               if(var8.equals(var9)) {
                  ServicemineEmailSetting.SecurityType var10 = this.securityType;
                  ServicemineEmailSetting.SecurityType var11 = var3.securityType;
                  if(var10.equals(var11)) {
                     var2 = true;
                     return var2;
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public URI getUriTemplate() throws URISyntaxException {
      String var1 = null;
      ServicemineEmailSetting.Protocol var2 = IMAP4;
      ServicemineEmailSetting.Protocol var3 = this.protocol;
      String var4;
      if(var2.equals(var3)) {
         var4 = "imap";
      } else {
         var4 = this.protocol.toString();
      }

      ServicemineEmailSetting.SecurityType var5 = NONE;
      ServicemineEmailSetting.SecurityType var6 = this.securityType;
      if(var5.equals(var6)) {
         var1 = "";
      } else {
         ServicemineEmailSetting.SecurityType var12 = SSL;
         ServicemineEmailSetting.SecurityType var13 = this.securityType;
         if(var12.equals(var13)) {
            var1 = "+ssl+";
         } else {
            ServicemineEmailSetting.SecurityType var14 = TLS;
            ServicemineEmailSetting.SecurityType var15 = this.securityType;
            if(var14.equals(var15)) {
               var1 = "+tls+";
            }
         }
      }

      StringBuilder var7 = (new StringBuilder()).append(var4).append(var1).append("://");
      String var8 = this.server;
      StringBuilder var9 = var7.append(var8).append(":");
      int var10 = this.port;
      String var11 = var9.append(var10).toString();
      return new URI(var11);
   }

   public int hashCode() {
      return super.hashCode();
   }

   public void setProtocol(String var1) {
      ServicemineEmailSetting.Protocol var2 = (ServicemineEmailSetting.Protocol)protocols.get(var1);
      this.protocol = var2;
      if(this.protocol == null) {
         String var3 = "Protocol \'" + var1 + "\' not recognised";
         throw new IllegalArgumentException(var3);
      }
   }

   public void setSecurityType(String var1) {
      ServicemineEmailSetting.SecurityType var2 = (ServicemineEmailSetting.SecurityType)securityTypes.get(var1);
      this.securityType = var2;
      if(this.securityType == null) {
         String var3 = "Security type \'" + var1 + "\' not recognised";
         throw new IllegalArgumentException(var3);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   public static class Protocol extends SimpleIdentifier {

      private Protocol(String var1) {
         super(var1);
      }

      // $FF: synthetic method
      Protocol(String var1, ServicemineEmailSetting.1 var2) {
         this(var1);
      }
   }

   public static class SecurityType extends SimpleIdentifier {

      private SecurityType(String var1) {
         super(var1);
      }

      // $FF: synthetic method
      SecurityType(String var1, ServicemineEmailSetting.1 var2) {
         this(var1);
      }
   }
}

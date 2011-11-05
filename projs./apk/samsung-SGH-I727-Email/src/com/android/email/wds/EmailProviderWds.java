package com.android.email.wds;

import com.android.email.wds.ServicemineEmailSetting;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;

public class EmailProviderWds {

   public String authNameFormat = "$user";
   public String autoCorrectedDomain;
   public URI incomingUriTemplate;
   public String label = "Unlabeled";
   public URI outgoingUriTemplate;


   public EmailProviderWds(Collection<ServicemineEmailSetting> var1) throws URISyntaxException, EmailProviderWds.MissingEmailConnectionException {
      ServicemineEmailSetting var2 = null;
      ServicemineEmailSetting var3 = null;
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         ServicemineEmailSetting var5 = (ServicemineEmailSetting)var4.next();
         ServicemineEmailSetting.Protocol var6 = var5.protocol;
         ServicemineEmailSetting.Protocol var7 = ServicemineEmailSetting.POP3;
         if(var6 == var7) {
            if(var2 == null) {
               var2 = var5;
            }
         } else {
            ServicemineEmailSetting.Protocol var8 = var5.protocol;
            ServicemineEmailSetting.Protocol var9 = ServicemineEmailSetting.IMAP4;
            if(var8 == var9) {
               var2 = var5;
            } else {
               ServicemineEmailSetting.Protocol var10 = var5.protocol;
               ServicemineEmailSetting.Protocol var11 = ServicemineEmailSetting.SMTP;
               if(var10 == var11) {
                  if(var3 == null) {
                     var3 = var5;
                  } else {
                     ServicemineEmailSetting.SecurityType var12 = var3.securityType;
                     ServicemineEmailSetting.SecurityType var13 = ServicemineEmailSetting.NONE;
                     if(var12 == var13) {
                        var3 = var5;
                     }
                  }
               }
            }
         }
      }

      if(var2 == null) {
         throw new EmailProviderWds.MissingEmailConnectionException("No incoming connection provided", (EmailProviderWds.1)null);
      } else if(var3 == null) {
         throw new EmailProviderWds.MissingEmailConnectionException("No outgoing connection provided", (EmailProviderWds.1)null);
      } else {
         URI var14 = var2.getUriTemplate();
         this.incomingUriTemplate = var14;
         URI var15 = var3.getUriTemplate();
         this.outgoingUriTemplate = var15;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   public class MissingEmailConnectionException extends Exception {

      private static final long serialVersionUID = 4123512345613461L;


      private MissingEmailConnectionException(String var2) {
         super(var2);
      }

      // $FF: synthetic method
      MissingEmailConnectionException(String var2, EmailProviderWds.1 var3) {
         this(var2);
      }
   }
}

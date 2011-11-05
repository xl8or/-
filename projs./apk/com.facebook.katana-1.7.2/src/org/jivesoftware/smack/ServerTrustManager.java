package org.jivesoftware.smack;

import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.X509TrustManager;
import org.jivesoftware.smack.ConnectionConfiguration;

class ServerTrustManager implements X509TrustManager {

   private static Pattern cnPattern = Pattern.compile("(?i)(cn=)([^,]*)");
   private ConnectionConfiguration configuration;
   private String server;
   private KeyStore trustStore;


   public ServerTrustManager(String param1, ConnectionConfiguration param2) {
      // $FF: Couldn't be decompiled
   }

   public static List<String> getPeerIdentity(X509Certificate var0) {
      Object var1 = getSubjectAlternativeNames(var0);
      if(((List)var1).isEmpty()) {
         String var2 = var0.getSubjectDN().getName();
         Matcher var3 = cnPattern.matcher(var2);
         if(var3.find()) {
            var2 = var3.group(2);
         }

         ArrayList var4 = new ArrayList();
         var4.add(var2);
         var1 = var4;
      }

      return (List)var1;
   }

   private static List<String> getSubjectAlternativeNames(X509Certificate var0) {
      Object var1 = new ArrayList();

      List var2;
      try {
         if(var0.getSubjectAlternativeNames() != null) {
            return (List)var1;
         }

         var2 = Collections.emptyList();
      } catch (CertificateParsingException var3) {
         var3.printStackTrace();
         return (List)var1;
      }

      var1 = var2;
      return (List)var1;
   }

   public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {}

   public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
      int var3 = var1.length;
      List var4 = getPeerIdentity(var1[0]);
      if(this.configuration.isVerifyChainEnabled()) {
         int var5 = var3 - 1;
         Principal var6 = null;

         Principal var10;
         for(int var7 = var5; var7 >= 0; var6 = var10) {
            X509Certificate var8 = var1[var7];
            Principal var9 = var8.getIssuerDN();
            var10 = var8.getSubjectDN();
            if(var6 != null) {
               if(!var9.equals(var6)) {
                  String var15 = "subject/issuer verification failed of " + var4;
                  throw new CertificateException(var15);
               }

               int var11 = var7 + 1;

               try {
                  PublicKey var12 = var1[var11].getPublicKey();
                  var1[var7].verify(var12);
               } catch (GeneralSecurityException var37) {
                  String var14 = "signature verification failed of " + var4;
                  throw new CertificateException(var14);
               }
            }

            var7 += -1;
         }
      }

      if(this.configuration.isVerifyRootCAEnabled()) {
         boolean var20;
         label91: {
            label90: {
               KeyStoreException var25;
               boolean var41;
               label105: {
                  String var19;
                  try {
                     KeyStore var16 = this.trustStore;
                     int var17 = var3 - 1;
                     X509Certificate var18 = var1[var17];
                     var19 = var16.getCertificateAlias(var18);
                  } catch (KeyStoreException var39) {
                     var25 = var39;
                     var41 = false;
                     break label105;
                  }

                  if(var19 != null) {
                     var20 = true;
                  } else {
                     var20 = false;
                  }

                  if(var20 || var3 != 1) {
                     break label91;
                  }

                  try {
                     if(!this.configuration.isSelfSignedCertificateEnabled()) {
                        break label91;
                     }

                     PrintStream var21 = System.out;
                     String var22 = "Accepting self-signed certificate of remote server: " + var4;
                     var21.println(var22);
                     break label90;
                  } catch (KeyStoreException var38) {
                     var41 = var20;
                     var25 = var38;
                  }
               }

               var25.printStackTrace();
               var20 = var41;
               break label91;
            }

            boolean var23 = true;
         }

         if(!var20) {
            String var24 = "root certificate not trusted of " + var4;
            throw new CertificateException(var24);
         }
      }

      if(this.configuration.isNotMatchingDomainCheckEnabled()) {
         if(var4.size() == 1 && ((String)var4.get(0)).startsWith("*.")) {
            String var26 = ((String)var4.get(0)).replace("*.", "");
            if(!this.server.endsWith(var26)) {
               String var27 = "target verification failed of " + var4;
               throw new CertificateException(var27);
            }
         } else {
            String var28 = this.server;
            if(!var4.contains(var28)) {
               String var29 = "target verification failed of " + var4;
               throw new CertificateException(var29);
            }
         }
      }

      if(this.configuration.isExpiredCertificatesCheckEnabled()) {
         Date var30 = new Date();

         for(int var40 = 0; var40 < var3; ++var40) {
            try {
               var1[var40].checkValidity(var30);
            } catch (GeneralSecurityException var36) {
               StringBuilder var32 = (new StringBuilder()).append("invalid date of ");
               String var33 = this.server;
               String var34 = var32.append(var33).toString();
               throw new CertificateException(var34);
            }
         }

      }
   }

   public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
   }
}

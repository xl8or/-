package org.apache.commons.httpclient.cookie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookieSpecBase;
import org.apache.commons.httpclient.cookie.MalformedCookieException;

public class NetscapeDraftSpec extends CookieSpecBase {

   public NetscapeDraftSpec() {}

   private static boolean isSpecialDomain(String var0) {
      String var1 = var0.toUpperCase();
      boolean var2;
      if(!var1.endsWith(".COM") && !var1.endsWith(".EDU") && !var1.endsWith(".NET") && !var1.endsWith(".GOV") && !var1.endsWith(".MIL") && !var1.endsWith(".ORG") && !var1.endsWith(".INT")) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public boolean domainMatch(String var1, String var2) {
      return var1.endsWith(var2);
   }

   public Cookie[] parse(String var1, int var2, String var3, boolean var4, String var5) throws MalformedCookieException {
      LOG.trace("enter NetscapeDraftSpec.parse(String, port, path, boolean, Header)");
      if(var1 == null) {
         throw new IllegalArgumentException("Host of origin may not be null");
      } else if(var1.trim().length() == 0) {
         throw new IllegalArgumentException("Host of origin may not be blank");
      } else if(var2 < 0) {
         String var6 = "Invalid port: " + var2;
         throw new IllegalArgumentException(var6);
      } else if(var3 == null) {
         throw new IllegalArgumentException("Path of origin may not be null.");
      } else if(var5 == null) {
         throw new IllegalArgumentException("Header may not be null.");
      } else {
         if(var3.trim().length() == 0) {
            var3 = "/";
         }

         String var7 = var1.toLowerCase();
         String var8 = var3;
         int var9 = var3.lastIndexOf("/");
         if(var9 >= 0) {
            if(var9 == 0) {
               var9 = 1;
            }

            var8 = var3.substring(0, var9);
         }

         char[] var10 = var5.toCharArray();
         HeaderElement var11 = new HeaderElement(var10);
         String var12 = var11.getName();
         String var13 = var11.getValue();
         Cookie var15 = new Cookie(var7, var12, var13, var8, (Date)null, (boolean)0);
         NameValuePair[] var16 = var11.getParameters();
         if(var16 != null) {
            int var17 = 0;

            while(true) {
               int var18 = var16.length;
               if(var17 >= var18) {
                  break;
               }

               NameValuePair var19 = var16[var17];
               this.parseAttribute(var19, var15);
               ++var17;
            }
         }

         Cookie[] var20 = new Cookie[]{var15};
         return var20;
      }
   }

   public void parseAttribute(NameValuePair var1, Cookie var2) throws MalformedCookieException {
      if(var1 == null) {
         throw new IllegalArgumentException("Attribute may not be null.");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Cookie may not be null.");
      } else {
         String var3 = var1.getName().toLowerCase();
         String var4 = var1.getValue();
         if(var3.equals("expires")) {
            if(var4 == null) {
               throw new MalformedCookieException("Missing value for expires attribute");
            } else {
               try {
                  Locale var5 = Locale.US;
                  Date var6 = (new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", var5)).parse(var4);
                  var2.setExpiryDate(var6);
               } catch (ParseException var11) {
                  StringBuilder var8 = (new StringBuilder()).append("Invalid expires attribute: ");
                  String var9 = var11.getMessage();
                  String var10 = var8.append(var9).toString();
                  throw new MalformedCookieException(var10);
               }
            }
         } else {
            super.parseAttribute(var1, var2);
         }
      }
   }

   public void validate(String var1, int var2, String var3, boolean var4, Cookie var5) throws MalformedCookieException {
      LOG.trace("enterNetscapeDraftCookieProcessor RCF2109CookieProcessor.validate(Cookie)");
      super.validate(var1, var2, var3, var4, var5);
      if(var1.indexOf(".") >= 0) {
         String var6 = var5.getDomain();
         int var7 = (new StringTokenizer(var6, ".")).countTokens();
         if(isSpecialDomain(var5.getDomain())) {
            if(var7 < 2) {
               StringBuilder var8 = (new StringBuilder()).append("Domain attribute \"");
               String var9 = var5.getDomain();
               String var10 = var8.append(var9).append("\" violates the Netscape cookie specification for ").append("special domains").toString();
               throw new MalformedCookieException(var10);
            }
         } else if(var7 < 3) {
            StringBuilder var11 = (new StringBuilder()).append("Domain attribute \"");
            String var12 = var5.getDomain();
            String var13 = var11.append(var12).append("\" violates the Netscape cookie specification").toString();
            throw new MalformedCookieException(var13);
         }
      }
   }
}

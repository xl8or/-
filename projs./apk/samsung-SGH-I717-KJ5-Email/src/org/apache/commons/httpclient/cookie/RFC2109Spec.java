package org.apache.commons.httpclient.cookie;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookieSpecBase;
import org.apache.commons.httpclient.cookie.MalformedCookieException;
import org.apache.commons.httpclient.util.ParameterFormatter;

public class RFC2109Spec extends CookieSpecBase {

   public static final String SET_COOKIE_KEY = "set-cookie";
   private final ParameterFormatter formatter;


   public RFC2109Spec() {
      ParameterFormatter var1 = new ParameterFormatter();
      this.formatter = var1;
      this.formatter.setAlwaysUseQuotes((boolean)1);
   }

   private void formatCookieAsVer(StringBuffer var1, Cookie var2, int var3) {
      String var4 = var2.getValue();
      if(var4 == null) {
         var4 = "";
      }

      String var5 = var2.getName();
      NameValuePair var6 = new NameValuePair(var5, var4);
      this.formatParam(var1, var6, var3);
      if(var2.getPath() != null && var2.isPathAttributeSpecified()) {
         StringBuffer var7 = var1.append("; ");
         String var8 = var2.getPath();
         NameValuePair var9 = new NameValuePair("$Path", var8);
         this.formatParam(var1, var9, var3);
      }

      if(var2.getDomain() != null) {
         if(var2.isDomainAttributeSpecified()) {
            StringBuffer var10 = var1.append("; ");
            String var11 = var2.getDomain();
            NameValuePair var12 = new NameValuePair("$Domain", var11);
            this.formatParam(var1, var12, var3);
         }
      }
   }

   private void formatParam(StringBuffer var1, NameValuePair var2, int var3) {
      if(var3 < 1) {
         String var4 = var2.getName();
         var1.append(var4);
         StringBuffer var6 = var1.append("=");
         if(var2.getValue() != null) {
            String var7 = var2.getValue();
            var1.append(var7);
         }
      } else {
         this.formatter.format(var1, var2);
      }
   }

   public boolean domainMatch(String var1, String var2) {
      boolean var3;
      if(!var1.equals(var2) && (!var2.startsWith(".") || !var1.endsWith(var2))) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   public String formatCookie(Cookie var1) {
      LOG.trace("enter RFC2109Spec.formatCookie(Cookie)");
      if(var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else {
         int var2 = var1.getVersion();
         StringBuffer var3 = new StringBuffer();
         String var4 = Integer.toString(var2);
         NameValuePair var5 = new NameValuePair("$Version", var4);
         this.formatParam(var3, var5, var2);
         StringBuffer var6 = var3.append("; ");
         this.formatCookieAsVer(var3, var1, var2);
         return var3.toString();
      }
   }

   public String formatCookies(Cookie[] var1) {
      LOG.trace("enter RFC2109Spec.formatCookieHeader(Cookie[])");
      int var2 = Integer.MAX_VALUE;
      int var3 = 0;

      while(true) {
         int var4 = var1.length;
         if(var3 >= var4) {
            StringBuffer var6 = new StringBuffer();
            String var7 = Integer.toString(var2);
            NameValuePair var8 = new NameValuePair("$Version", var7);
            this.formatParam(var6, var8, var2);
            int var9 = 0;

            while(true) {
               int var10 = var1.length;
               if(var9 >= var10) {
                  return var6.toString();
               }

               StringBuffer var11 = var6.append("; ");
               Cookie var12 = var1[var9];
               this.formatCookieAsVer(var6, var12, var2);
               ++var9;
            }
         }

         Cookie var5 = var1[var3];
         if(var5.getVersion() < var2) {
            var2 = var5.getVersion();
         }

         ++var3;
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
         if(var3.equals("path")) {
            if(var4 == null) {
               throw new MalformedCookieException("Missing value for path attribute");
            } else if(var4.trim().length() == 0) {
               throw new MalformedCookieException("Blank value for path attribute");
            } else {
               var2.setPath(var4);
               var2.setPathAttributeSpecified((boolean)1);
            }
         } else if(var3.equals("version")) {
            if(var4 == null) {
               throw new MalformedCookieException("Missing value for version attribute");
            } else {
               try {
                  int var5 = Integer.parseInt(var4);
                  var2.setVersion(var5);
               } catch (NumberFormatException var10) {
                  StringBuilder var7 = (new StringBuilder()).append("Invalid version: ");
                  String var8 = var10.getMessage();
                  String var9 = var7.append(var8).toString();
                  throw new MalformedCookieException(var9);
               }
            }
         } else {
            super.parseAttribute(var1, var2);
         }
      }
   }

   public void validate(String var1, int var2, String var3, boolean var4, Cookie var5) throws MalformedCookieException {
      LOG.trace("enter RFC2109Spec.validate(String, int, String, boolean, Cookie)");
      super.validate(var1, var2, var3, var4, var5);
      if(var5.getName().indexOf(32) != -1) {
         throw new MalformedCookieException("Cookie name may not contain blanks");
      } else if(var5.getName().startsWith("$")) {
         throw new MalformedCookieException("Cookie name may not start with $");
      } else if(var5.isDomainAttributeSpecified()) {
         if(!var5.getDomain().equals(var1)) {
            if(!var5.getDomain().startsWith(".")) {
               StringBuilder var6 = (new StringBuilder()).append("Domain attribute \"");
               String var7 = var5.getDomain();
               String var8 = var6.append(var7).append("\" violates RFC 2109: domain must start with a dot").toString();
               throw new MalformedCookieException(var8);
            } else {
               int var9 = var5.getDomain().indexOf(46, 1);
               if(var9 >= 0) {
                  int var10 = var5.getDomain().length() - 1;
                  if(var9 != var10) {
                     var1 = var1.toLowerCase();
                     String var14 = var5.getDomain();
                     if(!var1.endsWith(var14)) {
                        StringBuilder var15 = (new StringBuilder()).append("Illegal domain attribute \"");
                        String var16 = var5.getDomain();
                        String var17 = var15.append(var16).append("\". Domain of origin: \"").append(var1).append("\"").toString();
                        throw new MalformedCookieException(var17);
                     }

                     int var18 = var1.length();
                     int var19 = var5.getDomain().length();
                     int var20 = var18 - var19;
                     if(var1.substring(0, var20).indexOf(46) == -1) {
                        return;
                     }

                     StringBuilder var21 = (new StringBuilder()).append("Domain attribute \"");
                     String var22 = var5.getDomain();
                     String var23 = var21.append(var22).append("\" violates RFC 2109: host minus domain may not contain any dots").toString();
                     throw new MalformedCookieException(var23);
                  }
               }

               StringBuilder var11 = (new StringBuilder()).append("Domain attribute \"");
               String var12 = var5.getDomain();
               String var13 = var11.append(var12).append("\" violates RFC 2109: domain must contain an embedded dot").toString();
               throw new MalformedCookieException(var13);
            }
         }
      }
   }
}

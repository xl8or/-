package org.apache.commons.httpclient.cookie;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.cookie.MalformedCookieException;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CookieSpecBase implements CookieSpec {

   protected static final Log LOG = LogFactory.getLog(CookieSpec.class);
   private Collection datepatterns = null;


   public CookieSpecBase() {}

   private static void addInPathOrder(List var0, Cookie var1) {
      int var2 = 0;

      while(true) {
         int var3 = var0.size();
         if(var2 >= var3) {
            break;
         }

         Cookie var4 = (Cookie)var0.get(var2);
         if(var1.compare(var1, var4) > 0) {
            break;
         }

         ++var2;
      }

      var0.add(var2, var1);
   }

   public boolean domainMatch(String var1, String var2) {
      boolean var3;
      if(var1.equals(var2)) {
         var3 = true;
      } else {
         if(!var2.startsWith(".")) {
            var2 = "." + var2;
         }

         if(!var1.endsWith(var2)) {
            String var4 = var2.substring(1);
            if(!var1.equals(var4)) {
               var3 = false;
               return var3;
            }
         }

         var3 = true;
      }

      return var3;
   }

   public String formatCookie(Cookie var1) {
      LOG.trace("enter CookieSpecBase.formatCookie(Cookie)");
      if(var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else {
         StringBuffer var2 = new StringBuffer();
         String var3 = var1.getName();
         var2.append(var3);
         StringBuffer var5 = var2.append("=");
         String var6 = var1.getValue();
         if(var6 != null) {
            var2.append(var6);
         }

         return var2.toString();
      }
   }

   public Header formatCookieHeader(Cookie var1) {
      LOG.trace("enter CookieSpecBase.formatCookieHeader(Cookie)");
      String var2 = this.formatCookie(var1);
      return new Header("Cookie", var2);
   }

   public Header formatCookieHeader(Cookie[] var1) {
      LOG.trace("enter CookieSpecBase.formatCookieHeader(Cookie[])");
      String var2 = this.formatCookies(var1);
      return new Header("Cookie", var2);
   }

   public String formatCookies(Cookie[] var1) throws IllegalArgumentException {
      LOG.trace("enter CookieSpecBase.formatCookies(Cookie[])");
      if(var1 == null) {
         throw new IllegalArgumentException("Cookie array may not be null");
      } else if(var1.length == 0) {
         throw new IllegalArgumentException("Cookie array may not be empty");
      } else {
         StringBuffer var2 = new StringBuffer();
         int var3 = 0;

         while(true) {
            int var4 = var1.length;
            if(var3 >= var4) {
               return var2.toString();
            }

            if(var3 > 0) {
               StringBuffer var5 = var2.append("; ");
            }

            Cookie var6 = var1[var3];
            String var7 = this.formatCookie(var6);
            var2.append(var7);
            ++var3;
         }
      }
   }

   public Collection getValidDateFormats() {
      return this.datepatterns;
   }

   public boolean match(String var1, int var2, String var3, boolean var4, Cookie var5) {
      LOG.trace("enter CookieSpecBase.match(String, int, String, boolean, Cookie");
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
         throw new IllegalArgumentException("Cookie may not be null");
      } else {
         if(var3.trim().length() == 0) {
            var3 = "/";
         }

         String var7 = var1.toLowerCase();
         boolean var8;
         if(var5.getDomain() == null) {
            LOG.warn("Invalid cookie state: domain not specified");
            var8 = false;
         } else if(var5.getPath() == null) {
            LOG.warn("Invalid cookie state: path not specified");
            var8 = false;
         } else {
            label42: {
               if(var5.getExpiryDate() != null) {
                  Date var9 = var5.getExpiryDate();
                  Date var10 = new Date();
                  if(!var9.after(var10)) {
                     break label42;
                  }
               }

               String var11 = var5.getDomain();
               if(this.domainMatch(var7, var11)) {
                  String var12 = var5.getPath();
                  if(this.pathMatch(var3, var12) && (!var5.getSecure() || var4)) {
                     var8 = true;
                     return var8;
                  }
               }
            }

            var8 = false;
         }

         return var8;
      }
   }

   public Cookie[] match(String var1, int var2, String var3, boolean var4, Cookie[] var5) {
      LOG.trace("enter CookieSpecBase.match(String, int, String, boolean, Cookie[])");
      Cookie[] var6;
      if(var5 == null) {
         var6 = null;
      } else {
         LinkedList var7 = new LinkedList();
         int var8 = 0;

         while(true) {
            int var9 = var5.length;
            if(var8 >= var9) {
               Cookie[] var17 = new Cookie[var7.size()];
               var6 = (Cookie[])((Cookie[])var7.toArray(var17));
               break;
            }

            Cookie var10 = var5[var8];
            if(this.match(var1, var2, var3, var4, var10)) {
               Cookie var16 = var5[var8];
               addInPathOrder(var7, var16);
            }

            ++var8;
         }
      }

      return var6;
   }

   public Cookie[] parse(String var1, int var2, String var3, boolean var4, String var5) throws MalformedCookieException {
      LOG.trace("enter CookieSpecBase.parse(String, port, path, boolean, Header)");
      if(var1 == null) {
         throw new IllegalArgumentException("Host of origin may not be null");
      } else if(var1.trim().length() == 0) {
         throw new IllegalArgumentException("Host of origin may not be blank");
      } else if(var2 < 0) {
         StringBuilder var6 = (new StringBuilder()).append("Invalid port: ");
         String var8 = var6.append(var2).toString();
         throw new IllegalArgumentException(var8);
      } else if(var3 == null) {
         throw new IllegalArgumentException("Path of origin may not be null.");
      } else if(var5 == null) {
         throw new IllegalArgumentException("Header may not be null.");
      } else {
         if(var3.trim().length() == 0) {
            var3 = "/";
         }

         String var9 = var1.toLowerCase();
         String var10 = var3;
         int var11 = var3.lastIndexOf("/");
         if(var11 >= 0) {
            if(var11 == 0) {
               var11 = 1;
            }

            byte var13 = 0;
            var10 = var3.substring(var13, var11);
         }

         HeaderElement[] var15 = null;
         boolean var16 = false;
         int var17 = var5.toLowerCase().indexOf("expires=");
         if(var17 != -1) {
            label85: {
               int var18 = "expires=".length();
               var17 += var18;
               String var20 = ";";
               int var22 = var5.indexOf(var20, var17);
               byte var24 = -1;
               if(var22 == var24) {
                  var22 = var5.length();
               }

               try {
                  String var28 = var5.substring(var17, var22);
                  Collection var29 = this.datepatterns;
                  DateUtil.parseDate(var28, var29);
               } catch (DateParseException var54) {
                  break label85;
               }

               var16 = true;
            }
         }

         if(var16) {
            var15 = new HeaderElement[1];
            char[] var31 = var5.toCharArray();
            HeaderElement var32 = new HeaderElement(var31);
            var15[0] = var32;
         } else {
            HeaderElement[] var50 = HeaderElement.parseElements(var5.toCharArray());
         }

         Cookie[] var33 = new Cookie[var15.length];
         int var34 = 0;

         while(true) {
            int var35 = var15.length;
            if(var34 >= var35) {
               return var33;
            }

            HeaderElement var36 = var15[var34];

            Cookie var40;
            try {
               String var37 = var36.getName();
               String var38 = var36.getValue();
               var40 = new Cookie(var9, var37, var38, var10, (Date)null, (boolean)0);
            } catch (IllegalArgumentException var53) {
               String var51 = var53.getMessage();
               throw new MalformedCookieException(var51);
            }

            NameValuePair[] var41 = var36.getParameters();
            if(var41 != null) {
               int var42 = 0;

               while(true) {
                  int var43 = var41.length;
                  if(var42 >= var43) {
                     break;
                  }

                  NameValuePair var46 = var41[var42];
                  this.parseAttribute(var46, var40);
                  ++var42;
               }
            }

            var33[var34] = var40;
            ++var34;
         }
      }
   }

   public Cookie[] parse(String var1, int var2, String var3, boolean var4, Header var5) throws MalformedCookieException {
      LOG.trace("enter CookieSpecBase.parse(String, port, path, boolean, String)");
      if(var5 == null) {
         throw new IllegalArgumentException("Header may not be null.");
      } else {
         String var6 = var5.getValue();
         return this.parse(var1, var2, var3, var4, var6);
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
         if(!var3.equals("path")) {
            if(var3.equals("domain")) {
               if(var4 == null) {
                  throw new MalformedCookieException("Missing value for domain attribute");
               } else if(var4.trim().length() == 0) {
                  throw new MalformedCookieException("Blank value for domain attribute");
               } else {
                  var2.setDomain(var4);
                  var2.setDomainAttributeSpecified((boolean)1);
               }
            } else if(var3.equals("max-age")) {
               if(var4 == null) {
                  throw new MalformedCookieException("Missing value for max-age attribute");
               } else {
                  int var5;
                  try {
                     var5 = Integer.parseInt(var4);
                  } catch (NumberFormatException var26) {
                     StringBuilder var15 = (new StringBuilder()).append("Invalid max-age attribute: ");
                     String var16 = var26.getMessage();
                     String var17 = var15.append(var16).toString();
                     throw new MalformedCookieException(var17);
                  }

                  long var7 = System.currentTimeMillis();
                  long var9 = (long)var5 * 1000L;
                  long var11 = var7 + var9;
                  Date var13 = new Date(var11);
                  var2.setExpiryDate(var13);
               }
            } else if(var3.equals("secure")) {
               var2.setSecure((boolean)1);
            } else if(var3.equals("comment")) {
               var2.setComment(var4);
            } else if(var3.equals("expires")) {
               if(var4 == null) {
                  throw new MalformedCookieException("Missing value for expires attribute");
               } else {
                  try {
                     Collection var18 = this.datepatterns;
                     Date var19 = DateUtil.parseDate(var4, var18);
                     var2.setExpiryDate(var19);
                  } catch (DateParseException var27) {
                     LOG.debug("Error parsing cookie date", var27);
                     String var21 = "Unable to parse expiration date parameter: " + var4;
                     throw new MalformedCookieException(var21);
                  }
               }
            } else if(LOG.isDebugEnabled()) {
               Log var22 = LOG;
               StringBuilder var23 = (new StringBuilder()).append("Unrecognized cookie attribute: ");
               String var24 = var1.toString();
               String var25 = var23.append(var24).toString();
               var22.debug(var25);
            }
         } else {
            if(var4 == null || var4.trim().length() == 0) {
               var4 = "/";
            }

            var2.setPath(var4);
            var2.setPathAttributeSpecified((boolean)1);
         }
      }
   }

   public boolean pathMatch(String var1, String var2) {
      byte var3 = var1.startsWith(var2);
      if(var3 != 0) {
         int var4 = var1.length();
         int var5 = var2.length();
         if(var4 != var5 && !var2.endsWith("/")) {
            int var6 = var2.length();
            char var7 = var1.charAt(var6);
            char var8 = PATH_DELIM_CHAR;
            if(var7 == var8) {
               var3 = 1;
            } else {
               var3 = 0;
            }
         }
      }

      return (boolean)var3;
   }

   public void setValidDateFormats(Collection var1) {
      this.datepatterns = var1;
   }

   public void validate(String var1, int var2, String var3, boolean var4, Cookie var5) throws MalformedCookieException {
      LOG.trace("enter CookieSpecBase.validate(String, port, path, boolean, Cookie)");
      if(var1 == null) {
         throw new IllegalArgumentException("Host of origin may not be null");
      } else if(var1.trim().length() == 0) {
         throw new IllegalArgumentException("Host of origin may not be blank");
      } else if(var2 < 0) {
         String var6 = "Invalid port: " + var2;
         throw new IllegalArgumentException(var6);
      } else if(var3 == null) {
         throw new IllegalArgumentException("Path of origin may not be null.");
      } else {
         if(var3.trim().length() == 0) {
            var3 = "/";
         }

         String var7 = var1.toLowerCase();
         if(var5.getVersion() < 0) {
            StringBuilder var8 = (new StringBuilder()).append("Illegal version number ");
            String var9 = var5.getValue();
            String var10 = var8.append(var9).toString();
            throw new MalformedCookieException(var10);
         } else {
            if(var7.indexOf(".") >= 0) {
               String var11 = var5.getDomain();
               if(!var7.endsWith(var11)) {
                  String var12 = var5.getDomain();
                  if(var12.startsWith(".")) {
                     int var13 = var12.length();
                     var12 = var12.substring(1, var13);
                  }

                  if(!var7.equals(var12)) {
                     StringBuilder var14 = (new StringBuilder()).append("Illegal domain attribute \"");
                     String var15 = var5.getDomain();
                     String var16 = var14.append(var15).append("\". Domain of origin: \"").append(var7).append("\"").toString();
                     throw new MalformedCookieException(var16);
                  }
               }
            } else {
               String var17 = var5.getDomain();
               if(!var7.equals(var17)) {
                  StringBuilder var18 = (new StringBuilder()).append("Illegal domain attribute \"");
                  String var19 = var5.getDomain();
                  String var20 = var18.append(var19).append("\". Domain of origin: \"").append(var7).append("\"").toString();
                  throw new MalformedCookieException(var20);
               }
            }

            String var21 = var5.getPath();
            if(!var3.startsWith(var21)) {
               StringBuilder var22 = (new StringBuilder()).append("Illegal path attribute \"");
               String var23 = var5.getPath();
               String var24 = var22.append(var23).append("\". Path of origin: \"").append(var3).append("\"").toString();
               throw new MalformedCookieException(var24);
            }
         }
      }
   }
}

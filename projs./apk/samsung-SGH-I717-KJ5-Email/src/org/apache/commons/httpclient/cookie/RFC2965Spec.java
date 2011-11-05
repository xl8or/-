package org.apache.commons.httpclient.cookie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.Cookie2;
import org.apache.commons.httpclient.cookie.CookieAttributeHandler;
import org.apache.commons.httpclient.cookie.CookieOrigin;
import org.apache.commons.httpclient.cookie.CookiePathComparator;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.cookie.CookieSpecBase;
import org.apache.commons.httpclient.cookie.CookieVersionSupport;
import org.apache.commons.httpclient.cookie.MalformedCookieException;
import org.apache.commons.httpclient.cookie.RFC2109Spec;
import org.apache.commons.httpclient.util.ParameterFormatter;
import org.apache.commons.logging.Log;

public class RFC2965Spec extends CookieSpecBase implements CookieVersionSupport {

   private static final Comparator PATH_COMPOARATOR = new CookiePathComparator();
   public static final String SET_COOKIE2_KEY = "set-cookie2";
   private final List attribHandlerList;
   private final Map attribHandlerMap;
   private final ParameterFormatter formatter;
   private final CookieSpec rfc2109;


   public RFC2965Spec() {
      ParameterFormatter var1 = new ParameterFormatter();
      this.formatter = var1;
      this.formatter.setAlwaysUseQuotes((boolean)1);
      HashMap var2 = new HashMap(10);
      this.attribHandlerMap = var2;
      ArrayList var3 = new ArrayList(10);
      this.attribHandlerList = var3;
      RFC2109Spec var4 = new RFC2109Spec();
      this.rfc2109 = var4;
      RFC2965Spec.Cookie2PathAttributeHandler var5 = new RFC2965Spec.Cookie2PathAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("path", var5);
      RFC2965Spec.Cookie2DomainAttributeHandler var6 = new RFC2965Spec.Cookie2DomainAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("domain", var6);
      RFC2965Spec.Cookie2PortAttributeHandler var7 = new RFC2965Spec.Cookie2PortAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("port", var7);
      RFC2965Spec.Cookie2MaxageAttributeHandler var8 = new RFC2965Spec.Cookie2MaxageAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("max-age", var8);
      RFC2965Spec.CookieSecureAttributeHandler var9 = new RFC2965Spec.CookieSecureAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("secure", var9);
      RFC2965Spec.CookieCommentAttributeHandler var10 = new RFC2965Spec.CookieCommentAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("comment", var10);
      RFC2965Spec.CookieCommentUrlAttributeHandler var11 = new RFC2965Spec.CookieCommentUrlAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("commenturl", var11);
      RFC2965Spec.CookieDiscardAttributeHandler var12 = new RFC2965Spec.CookieDiscardAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("discard", var12);
      RFC2965Spec.Cookie2VersionAttributeHandler var13 = new RFC2965Spec.Cookie2VersionAttributeHandler((RFC2965Spec.1)null);
      this.registerAttribHandler("version", var13);
   }

   private String createPortAttribute(int[] var1) {
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;

      for(int var4 = var1.length; var3 < var4; ++var3) {
         if(var3 > 0) {
            StringBuffer var5 = var2.append(",");
         }

         int var6 = var1[var3];
         var2.append(var6);
      }

      return var2.toString();
   }

   private void doFormatCookie2(Cookie2 var1, StringBuffer var2) {
      String var3 = var1.getName();
      String var4 = var1.getValue();
      if(var4 == null) {
         var4 = "";
      }

      ParameterFormatter var5 = this.formatter;
      NameValuePair var6 = new NameValuePair(var3, var4);
      var5.format(var2, var6);
      if(var1.getDomain() != null && var1.isDomainAttributeSpecified()) {
         StringBuffer var7 = var2.append("; ");
         ParameterFormatter var8 = this.formatter;
         String var9 = var1.getDomain();
         NameValuePair var10 = new NameValuePair("$Domain", var9);
         var8.format(var2, var10);
      }

      if(var1.getPath() != null && var1.isPathAttributeSpecified()) {
         StringBuffer var11 = var2.append("; ");
         ParameterFormatter var12 = this.formatter;
         String var13 = var1.getPath();
         NameValuePair var14 = new NameValuePair("$Path", var13);
         var12.format(var2, var14);
      }

      if(var1.isPortAttributeSpecified()) {
         String var15 = "";
         if(!var1.isPortAttributeBlank()) {
            int[] var16 = var1.getPorts();
            var15 = this.createPortAttribute(var16);
         }

         StringBuffer var17 = var2.append("; ");
         ParameterFormatter var18 = this.formatter;
         NameValuePair var19 = new NameValuePair("$Port", var15);
         var18.format(var2, var19);
      }
   }

   private static String getEffectiveHost(String var0) {
      String var1 = var0.toLowerCase();
      if(var0.indexOf(46) < 0) {
         var1 = var1 + ".local";
      }

      return var1;
   }

   private int[] parsePortAttribute(String var1) throws MalformedCookieException {
      StringTokenizer var2 = new StringTokenizer(var1, ",");
      int[] var3 = new int[var2.countTokens()];
      int var4 = 0;

      while(true) {
         try {
            if(!var2.hasMoreTokens()) {
               return var3;
            }

            int var5 = Integer.parseInt(var2.nextToken().trim());
            var3[var4] = var5;
            if(var3[var4] < 0) {
               throw new MalformedCookieException("Invalid Port attribute.");
            }
         } catch (NumberFormatException var10) {
            StringBuilder var7 = (new StringBuilder()).append("Invalid Port attribute: ");
            String var8 = var10.getMessage();
            String var9 = var7.append(var8).toString();
            throw new MalformedCookieException(var9);
         }

         ++var4;
      }
   }

   private boolean portMatch(int var1, int[] var2) {
      boolean var3 = false;
      int var4 = 0;

      for(int var5 = var2.length; var4 < var5; ++var4) {
         int var6 = var2[var4];
         if(var1 == var6) {
            var3 = true;
            break;
         }
      }

      return var3;
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

   protected CookieAttributeHandler findAttribHandler(String var1) {
      return (CookieAttributeHandler)this.attribHandlerMap.get(var1);
   }

   public String formatCookie(Cookie var1) {
      LOG.trace("enter RFC2965Spec.formatCookie(Cookie)");
      if(var1 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else {
         String var9;
         if(var1 instanceof Cookie2) {
            Cookie2 var2 = (Cookie2)var1;
            int var3 = var2.getVersion();
            StringBuffer var4 = new StringBuffer();
            ParameterFormatter var5 = this.formatter;
            String var6 = Integer.toString(var3);
            NameValuePair var7 = new NameValuePair("$Version", var6);
            var5.format(var4, var7);
            StringBuffer var8 = var4.append("; ");
            this.doFormatCookie2(var2, var4);
            var9 = var4.toString();
         } else {
            var9 = this.rfc2109.formatCookie(var1);
         }

         return var9;
      }
   }

   public String formatCookies(Cookie[] var1) {
      LOG.trace("enter RFC2965Spec.formatCookieHeader(Cookie[])");
      if(var1 == null) {
         throw new IllegalArgumentException("Cookies may not be null");
      } else {
         boolean var2 = false;
         int var3 = -1;
         int var4 = 0;

         while(true) {
            int var5 = var1.length;
            if(var4 >= var5) {
               break;
            }

            Cookie var6 = var1[var4];
            if(!(var6 instanceof Cookie2)) {
               var2 = true;
               break;
            }

            if(var6.getVersion() > var3) {
               var3 = var6.getVersion();
            }

            ++var4;
         }

         if(var3 < 0) {
            var3 = 0;
         }

         String var7;
         if(!var2 && var3 >= 1) {
            Comparator var8 = PATH_COMPOARATOR;
            Arrays.sort(var1, var8);
            StringBuffer var9 = new StringBuffer();
            ParameterFormatter var10 = this.formatter;
            String var11 = Integer.toString(var3);
            NameValuePair var12 = new NameValuePair("$Version", var11);
            var10.format(var9, var12);
            byte var17 = 0;

            while(true) {
               int var13 = var1.length;
               if(var17 >= var13) {
                  var7 = var9.toString();
                  break;
               }

               StringBuffer var14 = var9.append("; ");
               Cookie2 var15 = (Cookie2)var1[var17];
               this.doFormatCookie2(var15, var9);
               int var16 = var17 + 1;
            }
         } else {
            var7 = this.rfc2109.formatCookies(var1);
         }

         return var7;
      }
   }

   protected CookieAttributeHandler getAttribHandler(String var1) {
      CookieAttributeHandler var2 = this.findAttribHandler(var1);
      if(var2 == null) {
         String var3 = "Handler not registered for " + var1 + " attribute.";
         throw new IllegalStateException(var3);
      } else {
         return var2;
      }
   }

   protected Iterator getAttribHandlerIterator() {
      return this.attribHandlerList.iterator();
   }

   public int getVersion() {
      return 1;
   }

   public Header getVersionHeader() {
      ParameterFormatter var1 = new ParameterFormatter();
      StringBuffer var2 = new StringBuffer();
      String var3 = Integer.toString(this.getVersion());
      NameValuePair var4 = new NameValuePair("$Version", var3);
      var1.format(var2, var4);
      String var5 = var2.toString();
      return new Header("Cookie2", var5, (boolean)1);
   }

   public boolean match(String var1, int var2, String var3, boolean var4, Cookie var5) {
      LOG.trace("enter RFC2965.match(String, int, String, boolean, Cookie");
      if(var5 == null) {
         throw new IllegalArgumentException("Cookie may not be null");
      } else {
         byte var6;
         if(var5 instanceof Cookie2) {
            if(var5.isPersistent() && var5.isExpired()) {
               var6 = 0;
            } else {
               String var7 = getEffectiveHost(var1);
               CookieOrigin var8 = new CookieOrigin(var7, var2, var3, var4);
               Iterator var9 = this.getAttribHandlerIterator();

               while(true) {
                  if(var9.hasNext()) {
                     if(((CookieAttributeHandler)var9.next()).match(var5, var8)) {
                        continue;
                     }

                     var6 = 0;
                     break;
                  }

                  var6 = 1;
                  break;
               }
            }
         } else {
            CookieSpec var10 = this.rfc2109;
            var6 = var10.match(var1, var2, var3, var4, var5);
         }

         return (boolean)var6;
      }
   }

   public Cookie[] parse(String var1, int var2, String var3, boolean var4, String var5) throws MalformedCookieException {
      LOG.trace("enter RFC2965Spec.parse(String, int, String, boolean, String)");
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

         String var9 = getEffectiveHost(var1);
         HeaderElement[] var10 = HeaderElement.parseElements(var5.toCharArray());
         LinkedList var11 = new LinkedList();
         int var12 = 0;

         while(true) {
            int var13 = var10.length;
            if(var12 >= var13) {
               Cookie[] var40 = new Cookie[var11.size()];
               return (Cookie[])((Cookie[])var11.toArray(var40));
            }

            HeaderElement var16 = var10[var12];

            Cookie2 var22;
            try {
               String var17 = var16.getName();
               String var18 = var16.getValue();
               int[] var19 = new int[]{var2};
               var22 = new Cookie2(var9, var17, var18, var3, (Date)null, (boolean)0, var19);
            } catch (IllegalArgumentException var41) {
               String var33 = var41.getMessage();
               throw new MalformedCookieException(var33);
            }

            NameValuePair[] var23 = var16.getParameters();
            if(var23 != null) {
               int var24 = var23.length;
               HashMap var25 = new HashMap(var24);

               for(int var26 = var23.length - 1; var26 >= 0; var26 += -1) {
                  NameValuePair var27 = var23[var26];
                  String var28 = var27.getName().toLowerCase();
                  Object var32 = var25.put(var28, var27);
               }

               Iterator var34 = var25.entrySet().iterator();

               while(var34.hasNext()) {
                  NameValuePair var35 = (NameValuePair)((Entry)var34.next()).getValue();
                  this.parseAttribute(var35, var22);
               }
            }

            var11.add(var22);
            ++var12;
         }
      }
   }

   public Cookie[] parse(String var1, int var2, String var3, boolean var4, Header var5) throws MalformedCookieException {
      LOG.trace("enter RFC2965.parse(String, int, String, boolean, Header)");
      if(var5 == null) {
         throw new IllegalArgumentException("Header may not be null.");
      } else if(var5.getName() == null) {
         throw new IllegalArgumentException("Header name may not be null.");
      } else {
         Cookie[] var12;
         if(var5.getName().equalsIgnoreCase("set-cookie2")) {
            String var6 = var5.getValue();
            var12 = this.parse(var1, var2, var3, var4, var6);
         } else {
            if(!var5.getName().equalsIgnoreCase("set-cookie")) {
               throw new MalformedCookieException("Header name is not valid. RFC 2965 supports \"set-cookie\" and \"set-cookie2\" headers.");
            }

            CookieSpec var13 = this.rfc2109;
            String var14 = var5.getValue();
            var12 = var13.parse(var1, var2, var3, var4, var14);
         }

         return var12;
      }
   }

   public void parseAttribute(NameValuePair var1, Cookie var2) throws MalformedCookieException {
      if(var1 == null) {
         throw new IllegalArgumentException("Attribute may not be null.");
      } else if(var1.getName() == null) {
         throw new IllegalArgumentException("Attribute Name may not be null.");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Cookie may not be null.");
      } else {
         String var3 = var1.getName().toLowerCase();
         String var4 = var1.getValue();
         CookieAttributeHandler var5 = this.findAttribHandler(var3);
         if(var5 == null) {
            if(LOG.isDebugEnabled()) {
               Log var6 = LOG;
               StringBuilder var7 = (new StringBuilder()).append("Unrecognized cookie attribute: ");
               String var8 = var1.toString();
               String var9 = var7.append(var8).toString();
               var6.debug(var9);
            }
         } else {
            var5.parse(var2, var4);
         }
      }
   }

   protected void registerAttribHandler(String var1, CookieAttributeHandler var2) {
      if(var1 == null) {
         throw new IllegalArgumentException("Attribute name may not be null");
      } else if(var2 == null) {
         throw new IllegalArgumentException("Attribute handler may not be null");
      } else {
         if(!this.attribHandlerList.contains(var2)) {
            this.attribHandlerList.add(var2);
         }

         this.attribHandlerMap.put(var1, var2);
      }
   }

   public void validate(String var1, int var2, String var3, boolean var4, Cookie var5) throws MalformedCookieException {
      LOG.trace("enter RFC2965Spec.validate(String, int, String, boolean, Cookie)");
      if(!(var5 instanceof Cookie2)) {
         CookieSpec var9 = this.rfc2109;
         var9.validate(var1, var2, var3, var4, var5);
      } else if(var5.getName().indexOf(32) != -1) {
         throw new MalformedCookieException("Cookie name may not contain blanks");
      } else if(var5.getName().startsWith("$")) {
         throw new MalformedCookieException("Cookie name may not start with $");
      } else {
         String var6 = getEffectiveHost(var1);
         CookieOrigin var7 = new CookieOrigin(var6, var2, var3, var4);
         Iterator var8 = this.getAttribHandlerIterator();

         while(var8.hasNext()) {
            ((CookieAttributeHandler)var8.next()).validate(var5, var7);
         }

      }
   }

   private class CookieCommentAttributeHandler implements CookieAttributeHandler {

      private CookieCommentAttributeHandler() {}

      // $FF: synthetic method
      CookieCommentAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         return true;
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         var1.setComment(var2);
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {}
   }

   private class CookieSecureAttributeHandler implements CookieAttributeHandler {

      private CookieSecureAttributeHandler() {}

      // $FF: synthetic method
      CookieSecureAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else {
            boolean var3 = var1.getSecure();
            boolean var4 = var2.isSecure();
            boolean var5;
            if(var3 == var4) {
               var5 = true;
            } else {
               var5 = false;
            }

            return var5;
         }
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         var1.setSecure((boolean)1);
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {}
   }

   private class Cookie2PathAttributeHandler implements CookieAttributeHandler {

      private Cookie2PathAttributeHandler() {}

      // $FF: synthetic method
      Cookie2PathAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else {
            String var3 = var2.getPath();
            boolean var4;
            if(var1.getPath() == null) {
               CookieSpecBase.LOG.warn("Invalid cookie state: path attribute is null.");
               var4 = false;
            } else {
               if(var3.trim().length() == 0) {
                  var3 = "/";
               }

               RFC2965Spec var5 = RFC2965Spec.this;
               String var6 = var1.getPath();
               if(!var5.pathMatch(var3, var6)) {
                  var4 = false;
               } else {
                  var4 = true;
               }
            }

            return var4;
         }
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new MalformedCookieException("Missing value for path attribute");
         } else if(var2.trim().length() == 0) {
            throw new MalformedCookieException("Blank value for path attribute");
         } else {
            var1.setPath(var2);
            var1.setPathAttributeSpecified((boolean)1);
         }
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else {
            String var3 = var2.getPath();
            if(var3 == null) {
               throw new IllegalArgumentException("Path of origin host may not be null.");
            } else if(var1.getPath() == null) {
               throw new MalformedCookieException("Invalid cookie state: path attribute is null.");
            } else {
               if(var3.trim().length() == 0) {
                  var3 = "/";
               }

               RFC2965Spec var4 = RFC2965Spec.this;
               String var5 = var1.getPath();
               if(!var4.pathMatch(var3, var5)) {
                  StringBuilder var6 = (new StringBuilder()).append("Illegal path attribute \"");
                  String var7 = var1.getPath();
                  String var8 = var6.append(var7).append("\". Path of origin: \"").append(var3).append("\"").toString();
                  throw new MalformedCookieException(var8);
               }
            }
         }
      }
   }

   private class Cookie2MaxageAttributeHandler implements CookieAttributeHandler {

      private Cookie2MaxageAttributeHandler() {}

      // $FF: synthetic method
      Cookie2MaxageAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         return true;
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new MalformedCookieException("Missing value for max-age attribute");
         } else {
            int var4;
            label21: {
               int var3;
               try {
                  var3 = Integer.parseInt(var2);
               } catch (NumberFormatException var13) {
                  var4 = -1;
                  break label21;
               }

               var4 = var3;
            }

            if(var4 < 0) {
               throw new MalformedCookieException("Invalid max-age attribute.");
            } else {
               long var6 = System.currentTimeMillis();
               long var8 = (long)var4 * 1000L;
               long var10 = var6 + var8;
               Date var12 = new Date(var10);
               var1.setExpiryDate(var12);
            }
         }
      }

      public void validate(Cookie var1, CookieOrigin var2) {}
   }

   private class Cookie2PortAttributeHandler implements CookieAttributeHandler {

      private Cookie2PortAttributeHandler() {}

      // $FF: synthetic method
      Cookie2PortAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else {
            boolean var5;
            if(var1 instanceof Cookie2) {
               Cookie2 var3 = (Cookie2)var1;
               int var4 = var2.getPort();
               if(var3.isPortAttributeSpecified()) {
                  if(var3.getPorts() == null) {
                     CookieSpecBase.LOG.warn("Invalid cookie state: port not specified");
                     var5 = false;
                     return var5;
                  }

                  RFC2965Spec var6 = RFC2965Spec.this;
                  int[] var7 = var3.getPorts();
                  if(!var6.portMatch(var4, var7)) {
                     var5 = false;
                     return var5;
                  }
               }

               var5 = true;
            } else {
               var5 = false;
            }

            return var5;
         }
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var1 instanceof Cookie2) {
            Cookie2 var3 = (Cookie2)var1;
            if(var2 != null && var2.trim().length() != 0) {
               int[] var4 = RFC2965Spec.this.parsePortAttribute(var2);
               var3.setPorts(var4);
            } else {
               var3.setPortAttributeBlank((boolean)1);
            }

            var3.setPortAttributeSpecified((boolean)1);
         }
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else if(var1 instanceof Cookie2) {
            Cookie2 var3 = (Cookie2)var1;
            int var4 = var2.getPort();
            if(var3.isPortAttributeSpecified()) {
               RFC2965Spec var5 = RFC2965Spec.this;
               int[] var6 = var3.getPorts();
               if(!var5.portMatch(var4, var6)) {
                  throw new MalformedCookieException("Port attribute violates RFC 2965: Request port not found in cookie\'s port list.");
               }
            }
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class CookieDiscardAttributeHandler implements CookieAttributeHandler {

      private CookieDiscardAttributeHandler() {}

      // $FF: synthetic method
      CookieDiscardAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         return true;
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         if(var1 instanceof Cookie2) {
            ((Cookie2)var1).setDiscard((boolean)1);
         }
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {}
   }

   private class CookieCommentUrlAttributeHandler implements CookieAttributeHandler {

      private CookieCommentUrlAttributeHandler() {}

      // $FF: synthetic method
      CookieCommentUrlAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         return true;
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         if(var1 instanceof Cookie2) {
            ((Cookie2)var1).setCommentURL(var2);
         }
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {}
   }

   private class Cookie2VersionAttributeHandler implements CookieAttributeHandler {

      private Cookie2VersionAttributeHandler() {}

      // $FF: synthetic method
      Cookie2VersionAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         return true;
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var1 instanceof Cookie2) {
            Cookie2 var3 = (Cookie2)var1;
            if(var2 == null) {
               throw new MalformedCookieException("Missing value for version attribute");
            } else {
               int var5;
               label23: {
                  int var4;
                  try {
                     var4 = Integer.parseInt(var2);
                  } catch (NumberFormatException var7) {
                     var5 = -1;
                     break label23;
                  }

                  var5 = var4;
               }

               if(var5 < 0) {
                  throw new MalformedCookieException("Invalid cookie version.");
               } else {
                  var3.setVersion(var5);
                  var3.setVersionAttributeSpecified((boolean)1);
               }
            }
         }
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var1 instanceof Cookie2) {
            if(!((Cookie2)var1).isVersionAttributeSpecified()) {
               throw new MalformedCookieException("Violates RFC 2965. Version attribute is required.");
            }
         }
      }
   }

   private class Cookie2DomainAttributeHandler implements CookieAttributeHandler {

      private Cookie2DomainAttributeHandler() {}

      // $FF: synthetic method
      Cookie2DomainAttributeHandler(RFC2965Spec.1 var2) {
         this();
      }

      public boolean match(Cookie var1, CookieOrigin var2) {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else {
            String var3 = var2.getHost().toLowerCase();
            String var4 = var1.getDomain();
            boolean var5;
            if(!RFC2965Spec.this.domainMatch(var3, var4)) {
               var5 = false;
            } else {
               int var6 = var3.length();
               int var7 = var4.length();
               int var8 = var6 - var7;
               if(var3.substring(0, var8).indexOf(46) != -1) {
                  var5 = false;
               } else {
                  var5 = true;
               }
            }

            return var5;
         }
      }

      public void parse(Cookie var1, String var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
         } else if(var2.trim().length() == 0) {
            throw new MalformedCookieException("Blank value for domain attribute");
         } else {
            String var3 = var2.toLowerCase();
            if(!var3.startsWith(".")) {
               var3 = "." + var3;
            }

            var1.setDomain(var3);
            var1.setDomainAttributeSpecified((boolean)1);
         }
      }

      public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException {
         if(var1 == null) {
            throw new IllegalArgumentException("Cookie may not be null");
         } else if(var2 == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
         } else {
            String var3 = var2.getHost().toLowerCase();
            if(var1.getDomain() == null) {
               throw new MalformedCookieException("Invalid cookie state: domain not specified");
            } else {
               String var4 = var1.getDomain().toLowerCase();
               if(var1.isDomainAttributeSpecified()) {
                  if(!var4.startsWith(".")) {
                     StringBuilder var5 = (new StringBuilder()).append("Domain attribute \"");
                     String var6 = var1.getDomain();
                     String var7 = var5.append(var6).append("\" violates RFC 2109: domain must start with a dot").toString();
                     throw new MalformedCookieException(var7);
                  } else {
                     label38: {
                        int var8 = var4.indexOf(46, 1);
                        if(var8 >= 0) {
                           int var9 = var4.length() - 1;
                           if(var8 != var9) {
                              break label38;
                           }
                        }

                        if(!var4.equals(".local")) {
                           StringBuilder var10 = (new StringBuilder()).append("Domain attribute \"");
                           String var11 = var1.getDomain();
                           String var12 = var10.append(var11).append("\" violates RFC 2965: the value contains no embedded dots ").append("and the value is not .local").toString();
                           throw new MalformedCookieException(var12);
                        }
                     }

                     if(!RFC2965Spec.this.domainMatch(var3, var4)) {
                        StringBuilder var13 = (new StringBuilder()).append("Domain attribute \"");
                        String var14 = var1.getDomain();
                        String var15 = var13.append(var14).append("\" violates RFC 2965: effective host name does not ").append("domain-match domain attribute.").toString();
                        throw new MalformedCookieException(var15);
                     } else {
                        int var16 = var3.length();
                        int var17 = var4.length();
                        int var18 = var16 - var17;
                        if(var3.substring(0, var18).indexOf(46) != -1) {
                           StringBuilder var19 = (new StringBuilder()).append("Domain attribute \"");
                           String var20 = var1.getDomain();
                           String var21 = var19.append(var20).append("\" violates RFC 2965: ").append("effective host minus domain may not contain any dots").toString();
                           throw new MalformedCookieException(var21);
                        }
                     }
                  }
               } else if(!var1.getDomain().equals(var3)) {
                  StringBuilder var22 = (new StringBuilder()).append("Illegal domain attribute: \"");
                  String var23 = var1.getDomain();
                  String var24 = var22.append(var23).append("\".").append("Domain of origin: \"").append(var3).append("\"").toString();
                  throw new MalformedCookieException(var24);
               }
            }
         }
      }
   }
}

package org.apache.commons.httpclient;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.util.LangUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Cookie extends NameValuePair implements Serializable, Comparator {

   private static final Log LOG = LogFactory.getLog(Cookie.class);
   private String cookieComment;
   private String cookieDomain;
   private Date cookieExpiryDate;
   private String cookiePath;
   private int cookieVersion;
   private boolean hasDomainAttribute;
   private boolean hasPathAttribute;
   private boolean isSecure;


   public Cookie() {
      Object var2 = null;
      Object var3 = null;
      Object var4 = null;
      this((String)null, "noname", (String)var2, (String)var3, (Date)var4, (boolean)0);
   }

   public Cookie(String var1, String var2, String var3) {
      Object var8 = null;
      this(var1, var2, var3, (String)null, (Date)var8, (boolean)0);
   }

   public Cookie(String var1, String var2, String var3, String var4, int var5, boolean var6) {
      this(var1, var2, var3, var4, (Date)null, var6);
      if(var5 < -1) {
         StringBuilder var13 = (new StringBuilder()).append("Invalid max age:  ");
         String var14 = Integer.toString(var5);
         String var15 = var13.append(var14).toString();
         throw new IllegalArgumentException(var15);
      } else if(var5 >= 0) {
         long var16 = System.currentTimeMillis();
         long var18 = (long)var5 * 1000L;
         long var20 = var16 + var18;
         Date var22 = new Date(var20);
         this.setExpiryDate(var22);
      }
   }

   public Cookie(String var1, String var2, String var3, String var4, Date var5, boolean var6) {
      super(var2, var3);
      this.hasPathAttribute = (boolean)0;
      this.hasDomainAttribute = (boolean)0;
      this.cookieVersion = 0;
      LOG.trace("enter Cookie(String, String, String, String, Date, boolean)");
      if(var2 == null) {
         throw new IllegalArgumentException("Cookie name may not be null");
      } else if(var2.trim().length() == 0) {
         throw new IllegalArgumentException("Cookie name may not be blank");
      } else {
         this.setPath(var4);
         this.setDomain(var1);
         this.setExpiryDate(var5);
         this.setSecure(var6);
      }
   }

   public int compare(Object var1, Object var2) {
      LOG.trace("enter Cookie.compare(Object, Object)");
      if(!(var1 instanceof Cookie)) {
         String var3 = var1.getClass().getName();
         throw new ClassCastException(var3);
      } else if(!(var2 instanceof Cookie)) {
         String var4 = var2.getClass().getName();
         throw new ClassCastException(var4);
      } else {
         Cookie var5 = (Cookie)var1;
         Cookie var6 = (Cookie)var2;
         int var7;
         if(var5.getPath() == null && var6.getPath() == null) {
            var7 = 0;
         } else if(var5.getPath() == null) {
            if(var6.getPath().equals("/")) {
               var7 = 0;
            } else {
               var7 = -1;
            }
         } else if(var6.getPath() == null) {
            if(var5.getPath().equals("/")) {
               var7 = 0;
            } else {
               var7 = 1;
            }
         } else {
            String var8 = var5.getPath();
            String var9 = var6.getPath();
            var7 = var8.compareTo(var9);
         }

         return var7;
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(this == var1) {
         var2 = true;
      } else if(var1 instanceof Cookie) {
         Cookie var3 = (Cookie)var1;
         String var4 = this.getName();
         String var5 = var3.getName();
         if(LangUtils.equals(var4, var5)) {
            String var6 = this.cookieDomain;
            String var7 = var3.cookieDomain;
            if(LangUtils.equals(var6, var7)) {
               String var8 = this.cookiePath;
               String var9 = var3.cookiePath;
               if(LangUtils.equals(var8, var9)) {
                  var2 = true;
                  return var2;
               }
            }
         }

         var2 = false;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getComment() {
      return this.cookieComment;
   }

   public String getDomain() {
      return this.cookieDomain;
   }

   public Date getExpiryDate() {
      return this.cookieExpiryDate;
   }

   public String getPath() {
      return this.cookiePath;
   }

   public boolean getSecure() {
      return this.isSecure;
   }

   public int getVersion() {
      return this.cookieVersion;
   }

   public int hashCode() {
      String var1 = this.getName();
      int var2 = LangUtils.hashCode(17, var1);
      String var3 = this.cookieDomain;
      int var4 = LangUtils.hashCode(var2, var3);
      String var5 = this.cookiePath;
      return LangUtils.hashCode(var4, var5);
   }

   public boolean isDomainAttributeSpecified() {
      return this.hasDomainAttribute;
   }

   public boolean isExpired() {
      boolean var5;
      if(this.cookieExpiryDate != null) {
         long var1 = this.cookieExpiryDate.getTime();
         long var3 = System.currentTimeMillis();
         if(var1 <= var3) {
            var5 = true;
            return var5;
         }
      }

      var5 = false;
      return var5;
   }

   public boolean isExpired(Date var1) {
      boolean var6;
      if(this.cookieExpiryDate != null) {
         long var2 = this.cookieExpiryDate.getTime();
         long var4 = var1.getTime();
         if(var2 <= var4) {
            var6 = true;
            return var6;
         }
      }

      var6 = false;
      return var6;
   }

   public boolean isPathAttributeSpecified() {
      return this.hasPathAttribute;
   }

   public boolean isPersistent() {
      boolean var1;
      if(this.cookieExpiryDate != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setComment(String var1) {
      this.cookieComment = var1;
   }

   public void setDomain(String var1) {
      if(var1 != null) {
         int var2 = var1.indexOf(":");
         if(var2 != -1) {
            var1 = var1.substring(0, var2);
         }

         String var3 = var1.toLowerCase();
         this.cookieDomain = var3;
      }
   }

   public void setDomainAttributeSpecified(boolean var1) {
      this.hasDomainAttribute = var1;
   }

   public void setExpiryDate(Date var1) {
      this.cookieExpiryDate = var1;
   }

   public void setPath(String var1) {
      this.cookiePath = var1;
   }

   public void setPathAttributeSpecified(boolean var1) {
      this.hasPathAttribute = var1;
   }

   public void setSecure(boolean var1) {
      this.isSecure = var1;
   }

   public void setVersion(int var1) {
      this.cookieVersion = var1;
   }

   public String toExternalForm() {
      CookieSpec var1;
      if(this.getVersion() > 0) {
         var1 = CookiePolicy.getDefaultSpec();
      } else {
         var1 = CookiePolicy.getCookieSpec("netscape");
      }

      return var1.formatCookie(this);
   }

   public String toString() {
      return this.toExternalForm();
   }
}

package org.apache.commons.httpclient.cookie;

import java.util.Date;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.cookie.CookiePolicy;

public class Cookie2 extends Cookie {

   public static final String COMMENT = "comment";
   public static final String COMMENTURL = "commenturl";
   public static final String DISCARD = "discard";
   public static final String DOMAIN = "domain";
   public static final String MAXAGE = "max-age";
   public static final String PATH = "path";
   public static final String PORT = "port";
   public static final String SECURE = "secure";
   public static final String VERSION = "version";
   private String cookieCommentURL;
   private int[] cookiePorts;
   private boolean discard;
   private boolean hasPortAttribute;
   private boolean hasVersionAttribute;
   private boolean isPortAttributeBlank;


   public Cookie2() {
      Object var2 = null;
      Object var3 = null;
      Object var4 = null;
      super((String)null, "noname", (String)var2, (String)var3, (Date)var4, (boolean)0);
      this.discard = (boolean)0;
      this.hasPortAttribute = (boolean)0;
      this.isPortAttributeBlank = (boolean)0;
      this.hasVersionAttribute = (boolean)0;
   }

   public Cookie2(String var1, String var2, String var3) {
      super(var1, var2, var3);
      this.discard = (boolean)0;
      this.hasPortAttribute = (boolean)0;
      this.isPortAttributeBlank = (boolean)0;
      this.hasVersionAttribute = (boolean)0;
   }

   public Cookie2(String var1, String var2, String var3, String var4, Date var5, boolean var6) {
      super(var1, var2, var3, var4, var5, var6);
      this.discard = (boolean)0;
      this.hasPortAttribute = (boolean)0;
      this.isPortAttributeBlank = (boolean)0;
      this.hasVersionAttribute = (boolean)0;
   }

   public Cookie2(String var1, String var2, String var3, String var4, Date var5, boolean var6, int[] var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.discard = (boolean)0;
      this.hasPortAttribute = (boolean)0;
      this.isPortAttributeBlank = (boolean)0;
      this.hasVersionAttribute = (boolean)0;
      this.setPorts(var7);
   }

   public String getCommentURL() {
      return this.cookieCommentURL;
   }

   public int[] getPorts() {
      return this.cookiePorts;
   }

   public boolean isPersistent() {
      boolean var1;
      if(this.getExpiryDate() != null && !this.discard) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isPortAttributeBlank() {
      return this.isPortAttributeBlank;
   }

   public boolean isPortAttributeSpecified() {
      return this.hasPortAttribute;
   }

   public boolean isVersionAttributeSpecified() {
      return this.hasVersionAttribute;
   }

   public void setCommentURL(String var1) {
      this.cookieCommentURL = var1;
   }

   public void setDiscard(boolean var1) {
      this.discard = var1;
   }

   public void setPortAttributeBlank(boolean var1) {
      this.isPortAttributeBlank = var1;
   }

   public void setPortAttributeSpecified(boolean var1) {
      this.hasPortAttribute = var1;
   }

   public void setPorts(int[] var1) {
      this.cookiePorts = var1;
   }

   public void setVersionAttributeSpecified(boolean var1) {
      this.hasVersionAttribute = var1;
   }

   public String toExternalForm() {
      return CookiePolicy.getCookieSpec("rfc2965").formatCookie(this);
   }
}

package org.apache.commons.httpclient;

import java.util.BitSet;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

public class HttpsURL extends HttpURL {

   public static final int DEFAULT_PORT = 443;
   public static final char[] DEFAULT_SCHEME = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null};
   public static final int _default_port = 443;
   public static final char[] _default_scheme = DEFAULT_SCHEME;
   static final long serialVersionUID = 887844277028676648L;


   protected HttpsURL() {}

   public HttpsURL(String var1) throws URIException {
      this.parseUriReference(var1, (boolean)0);
      this.checkValid();
   }

   public HttpsURL(String var1, int var2, String var3) throws URIException {
      Object var8 = null;
      Object var9 = null;
      this((String)null, var1, var2, var3, (String)var8, (String)var9);
   }

   public HttpsURL(String var1, int var2, String var3, String var4) throws URIException {
      Object var10 = null;
      this((String)null, var1, var2, var3, var4, (String)var10);
   }

   public HttpsURL(String var1, String var2) throws URIException {
      this.protocolCharset = var2;
      this.parseUriReference(var1, (boolean)0);
      this.checkValid();
   }

   public HttpsURL(String var1, String var2, int var3, String var4) throws URIException {
      Object var10 = null;
      this(var1, var2, var3, var4, (String)null, (String)var10);
   }

   public HttpsURL(String var1, String var2, int var3, String var4, String var5) throws URIException {
      this(var1, var2, var3, var4, var5, (String)null);
   }

   public HttpsURL(String var1, String var2, int var3, String var4, String var5, String var6) throws URIException {
      StringBuffer var7 = new StringBuffer();
      if(var1 != null || var2 != null || var3 != -1) {
         char[] var8 = DEFAULT_SCHEME;
         this._scheme = var8;
         char[] var9 = _default_scheme;
         var7.append(var9);
         StringBuffer var11 = var7.append("://");
         if(var1 != null) {
            var7.append(var1);
            StringBuffer var13 = var7.append('@');
         }

         if(var2 != null) {
            BitSet var14 = URI.allowed_host;
            String var15 = URIUtil.encode(var2, var14);
            var7.append(var15);
            if(var3 != -1 || var3 != 443) {
               StringBuffer var17 = var7.append(':');
               var7.append(var3);
            }
         }
      }

      if(var4 != null) {
         if(scheme != null && !var4.startsWith("/")) {
            throw new URIException(1, "abs_path requested");
         }

         BitSet var19 = URI.allowed_abs_path;
         String var20 = URIUtil.encode(var4, var19);
         var7.append(var20);
      }

      if(var5 != null) {
         StringBuffer var22 = var7.append('?');
         BitSet var23 = URI.allowed_query;
         String var24 = URIUtil.encode(var5, var23);
         var7.append(var24);
      }

      if(var6 != null) {
         StringBuffer var26 = var7.append('#');
         BitSet var27 = URI.allowed_fragment;
         String var28 = URIUtil.encode(var6, var27);
         var7.append(var28);
      }

      String var30 = var7.toString();
      this.parseUriReference(var30, (boolean)1);
      this.checkValid();
   }

   public HttpsURL(String var1, String var2, String var3) throws URIException {
      Object var8 = null;
      Object var9 = null;
      this(var1, var2, var3, -1, (String)null, (String)var8, (String)var9);
   }

   public HttpsURL(String var1, String var2, String var3, int var4) throws URIException {
      Object var10 = null;
      Object var11 = null;
      this(var1, var2, var3, var4, (String)null, (String)var10, (String)var11);
   }

   public HttpsURL(String var1, String var2, String var3, int var4, String var5) throws URIException {
      Object var12 = null;
      this(var1, var2, var3, var4, var5, (String)null, (String)var12);
   }

   public HttpsURL(String var1, String var2, String var3, int var4, String var5, String var6) throws URIException {
      this(var1, var2, var3, var4, var5, var6, (String)null);
   }

   public HttpsURL(String var1, String var2, String var3, int var4, String var5, String var6, String var7) throws URIException {
      String var8 = HttpURL.toUserinfo(var1, var2);
      this(var8, var3, var4, var5, var6, var7);
   }

   public HttpsURL(String var1, String var2, String var3, String var4) throws URIException {
      this((String)null, var1, -1, var2, var3, var4);
   }

   public HttpsURL(String var1, String var2, String var3, String var4, String var5) throws URIException {
      this(var1, var2, -1, var3, var4, var5);
   }

   public HttpsURL(HttpsURL var1, String var2) throws URIException {
      HttpsURL var3 = new HttpsURL(var2);
      this(var1, var3);
   }

   public HttpsURL(HttpsURL var1, HttpsURL var2) throws URIException {
      super((HttpURL)var1, (HttpURL)var2);
      this.checkValid();
   }

   public HttpsURL(char[] var1) throws URIException, NullPointerException {
      String var2 = new String(var1);
      this.parseUriReference(var2, (boolean)1);
      this.checkValid();
   }

   public HttpsURL(char[] var1, String var2) throws URIException, NullPointerException {
      this.protocolCharset = var2;
      String var3 = new String(var1);
      this.parseUriReference(var3, (boolean)1);
      this.checkValid();
   }

   protected void checkValid() throws URIException {
      char[] var1 = this._scheme;
      char[] var2 = DEFAULT_SCHEME;
      if(!this.equals(var1, var2)) {
         if(this._scheme != null) {
            throw new URIException(1, "wrong class use");
         }
      }
   }

   public int getPort() {
      int var1;
      if(this._port == -1) {
         var1 = 443;
      } else {
         var1 = this._port;
      }

      return var1;
   }

   public char[] getRawScheme() {
      char[] var1;
      if(this._scheme == null) {
         var1 = null;
      } else {
         var1 = DEFAULT_SCHEME;
      }

      return var1;
   }

   public String getScheme() {
      String var1;
      if(this._scheme == null) {
         var1 = null;
      } else {
         char[] var2 = DEFAULT_SCHEME;
         var1 = new String(var2);
      }

      return var1;
   }
}

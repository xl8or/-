package org.apache.commons.httpclient;

import java.util.BitSet;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

public class HttpURL extends URI {

   public static final int DEFAULT_PORT = 80;
   public static final char[] DEFAULT_SCHEME = new char[]{(char)null, (char)null, (char)null, (char)null};
   public static final int _default_port = 80;
   public static final char[] _default_scheme = DEFAULT_SCHEME;
   static final long serialVersionUID = -7158031098595039459L;


   protected HttpURL() {}

   public HttpURL(String var1) throws URIException {
      this.parseUriReference(var1, (boolean)0);
      this.checkValid();
   }

   public HttpURL(String var1, int var2, String var3) throws URIException {
      Object var5 = null;
      Object var9 = null;
      Object var10 = null;
      this((String)null, (String)var5, var1, var2, var3, (String)var9, (String)var10);
   }

   public HttpURL(String var1, int var2, String var3, String var4) throws URIException {
      Object var6 = null;
      Object var11 = null;
      this((String)null, (String)var6, var1, var2, var3, var4, (String)var11);
   }

   public HttpURL(String var1, String var2) throws URIException {
      this.protocolCharset = var2;
      this.parseUriReference(var1, (boolean)0);
      this.checkValid();
   }

   public HttpURL(String var1, String var2, int var3, String var4) throws URIException {
      Object var10 = null;
      this(var1, var2, var3, var4, (String)null, (String)var10);
   }

   public HttpURL(String var1, String var2, int var3, String var4, String var5) throws URIException {
      this(var1, var2, var3, var4, var5, (String)null);
   }

   public HttpURL(String var1, String var2, int var3, String var4, String var5, String var6) throws URIException {
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
            if(var3 != -1 || var3 != 80) {
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

   public HttpURL(String var1, String var2, String var3) throws URIException {
      Object var8 = null;
      Object var9 = null;
      this(var1, var2, var3, -1, (String)null, (String)var8, (String)var9);
   }

   public HttpURL(String var1, String var2, String var3, int var4) throws URIException {
      Object var10 = null;
      Object var11 = null;
      this(var1, var2, var3, var4, (String)null, (String)var10, (String)var11);
   }

   public HttpURL(String var1, String var2, String var3, int var4, String var5) throws URIException {
      Object var12 = null;
      this(var1, var2, var3, var4, var5, (String)null, (String)var12);
   }

   public HttpURL(String var1, String var2, String var3, int var4, String var5, String var6) throws URIException {
      this(var1, var2, var3, var4, var5, var6, (String)null);
   }

   public HttpURL(String var1, String var2, String var3, int var4, String var5, String var6, String var7) throws URIException {
      String var8 = toUserinfo(var1, var2);
      this(var8, var3, var4, var5, var6, var7);
   }

   public HttpURL(String var1, String var2, String var3, String var4) throws URIException {
      Object var6 = null;
      this((String)null, (String)var6, var1, -1, var2, var3, var4);
   }

   public HttpURL(String var1, String var2, String var3, String var4, String var5) throws URIException {
      this(var1, var2, -1, var3, var4, var5);
   }

   public HttpURL(HttpURL var1, String var2) throws URIException {
      HttpURL var3 = new HttpURL(var2);
      this(var1, var3);
   }

   public HttpURL(HttpURL var1, HttpURL var2) throws URIException {
      super((URI)var1, (URI)var2);
      this.checkValid();
   }

   public HttpURL(char[] var1) throws URIException, NullPointerException {
      String var2 = new String(var1);
      this.parseUriReference(var2, (boolean)1);
      this.checkValid();
   }

   public HttpURL(char[] var1, String var2) throws URIException, NullPointerException {
      this.protocolCharset = var2;
      String var3 = new String(var1);
      this.parseUriReference(var3, (boolean)1);
      this.checkValid();
   }

   protected static String toUserinfo(String var0, String var1) throws URIException {
      String var2;
      if(var0 == null) {
         var2 = null;
      } else {
         StringBuffer var3 = new StringBuffer(20);
         BitSet var4 = URI.allowed_within_userinfo;
         String var5 = URIUtil.encode(var0, var4);
         var3.append(var5);
         if(var1 == null) {
            var2 = var3.toString();
         } else {
            StringBuffer var7 = var3.append(':');
            BitSet var8 = URI.allowed_within_userinfo;
            String var9 = URIUtil.encode(var1, var8);
            var3.append(var9);
            var2 = var3.toString();
         }
      }

      return var2;
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

   public String getEscapedPassword() {
      char[] var1 = this.getRawPassword();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getEscapedUser() {
      char[] var1 = this.getRawUser();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = new String(var1);
      }

      return var2;
   }

   public String getPassword() throws URIException {
      char[] var1 = this.getRawPassword();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = this.getProtocolCharset();
         var2 = decode(var1, var3);
      }

      return var2;
   }

   public int getPort() {
      int var1;
      if(this._port == -1) {
         var1 = 80;
      } else {
         var1 = this._port;
      }

      return var1;
   }

   public char[] getRawAboveHierPath() throws URIException {
      char[] var1 = this.getRawCurrentHierPath();
      char[] var2;
      if(var1 != null && var1.length != 0) {
         var2 = this.getRawCurrentHierPath(var1);
      } else {
         var2 = rootPath;
      }

      return var2;
   }

   public char[] getRawCurrentHierPath() throws URIException {
      char[] var1;
      if(this._path != null && this._path.length != 0) {
         char[] var2 = this._path;
         var1 = super.getRawCurrentHierPath(var2);
      } else {
         var1 = rootPath;
      }

      return var1;
   }

   public char[] getRawPassword() {
      char[] var1 = this._userinfo;
      int var2 = this.indexFirstOf(var1, ':');
      char[] var3;
      if(var2 == -1) {
         var3 = null;
      } else {
         int var4 = this._userinfo.length - var2 - 1;
         char[] var5 = new char[var4];
         char[] var6 = this._userinfo;
         int var7 = var2 + 1;
         System.arraycopy(var6, var7, var5, 0, var4);
         var3 = var5;
      }

      return var3;
   }

   public char[] getRawPath() {
      char[] var1 = super.getRawPath();
      char[] var2;
      if(var1 != null && var1.length != 0) {
         var2 = var1;
      } else {
         var2 = rootPath;
      }

      return var2;
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

   public char[] getRawUser() {
      char[] var1;
      if(this._userinfo != null && this._userinfo.length != 0) {
         char[] var2 = this._userinfo;
         int var3 = this.indexFirstOf(var2, ':');
         if(var3 == -1) {
            var1 = this._userinfo;
         } else {
            char[] var4 = new char[var3];
            System.arraycopy(this._userinfo, 0, var4, 0, var3);
            var1 = var4;
         }
      } else {
         var1 = null;
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

   public String getUser() throws URIException {
      char[] var1 = this.getRawUser();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         String var3 = this.getProtocolCharset();
         var2 = decode(var1, var3);
      }

      return var2;
   }

   public void setEscapedPassword(String var1) throws URIException {
      char[] var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toCharArray();
      }

      this.setRawPassword(var2);
   }

   public void setEscapedUser(String var1) throws URIException, NullPointerException {
      char[] var2 = var1.toCharArray();
      this.setRawUser(var2);
   }

   public void setEscapedUserinfo(String var1, String var2) throws URIException, NullPointerException {
      char[] var3 = var1.toCharArray();
      char[] var4;
      if(var2 == null) {
         var4 = null;
      } else {
         var4 = var2.toCharArray();
      }

      this.setRawUserinfo(var3, var4);
   }

   public void setPassword(String var1) throws URIException {
      char[] var2;
      if(var1 == null) {
         var2 = null;
      } else {
         BitSet var3 = allowed_within_userinfo;
         String var4 = this.getProtocolCharset();
         var2 = encode(var1, var3, var4);
      }

      this.setRawPassword(var2);
   }

   public void setQuery(String var1, String var2) throws URIException, NullPointerException {
      StringBuffer var3 = new StringBuffer();
      String var4 = this.getProtocolCharset();
      BitSet var5 = allowed_within_query;
      char[] var6 = encode(var1, var5, var4);
      var3.append(var6);
      StringBuffer var8 = var3.append('=');
      BitSet var9 = allowed_within_query;
      char[] var10 = encode(var2, var9, var4);
      var3.append(var10);
      char[] var12 = var3.toString().toCharArray();
      this._query = var12;
      this.setURI();
   }

   public void setQuery(String[] var1, String[] var2) throws URIException, NullPointerException {
      int var3 = var1.length;
      int var4 = var2.length;
      if(var3 != var4) {
         throw new URIException("wrong array size of query");
      } else {
         StringBuffer var5 = new StringBuffer();
         String var6 = this.getProtocolCharset();

         for(int var7 = 0; var7 < var3; ++var7) {
            String var8 = var1[var7];
            BitSet var9 = allowed_within_query;
            char[] var10 = encode(var8, var9, var6);
            var5.append(var10);
            StringBuffer var12 = var5.append('=');
            String var13 = var2[var7];
            BitSet var14 = allowed_within_query;
            char[] var15 = encode(var13, var14, var6);
            var5.append(var15);
            if(var7 + 1 < var3) {
               StringBuffer var17 = var5.append('&');
            }
         }

         char[] var18 = var5.toString().toCharArray();
         this._query = var18;
         this.setURI();
      }
   }

   public void setRawPassword(char[] var1) throws URIException {
      if(var1 != null) {
         BitSet var2 = within_userinfo;
         if(!this.validate(var1, var2)) {
            throw new URIException(3, "escaped password not valid");
         }
      }

      if(this.getRawUser() != null && this.getRawUser().length != 0) {
         char[] var3 = this.getRawUser();
         String var4 = new String(var3);
         String var5;
         if(var1 == null) {
            var5 = null;
         } else {
            var5 = new String(var1);
         }

         StringBuilder var6 = (new StringBuilder()).append(var4);
         String var7;
         if(var5 == null) {
            var7 = "";
         } else {
            var7 = ":" + var5;
         }

         String var8 = var6.append(var7).toString();
         char[] var9 = this.getRawHost();
         String var10 = new String(var9);
         String var11;
         if(this._port == -1) {
            var11 = var10;
         } else {
            StringBuilder var15 = (new StringBuilder()).append(var10).append(":");
            int var16 = this._port;
            var11 = var15.append(var16).toString();
         }

         String var12 = var8 + "@" + var11;
         char[] var13 = var8.toCharArray();
         this._userinfo = var13;
         char[] var14 = var12.toCharArray();
         this._authority = var14;
         this.setURI();
      } else {
         throw new URIException(1, "username required");
      }
   }

   public void setRawUser(char[] var1) throws URIException {
      if(var1 != null && var1.length != 0) {
         BitSet var2 = within_userinfo;
         if(!this.validate(var1, var2)) {
            throw new URIException(3, "escaped user not valid");
         } else {
            String var3 = new String(var1);
            char[] var4 = this.getRawPassword();
            String var5;
            if(var4 == null) {
               var5 = null;
            } else {
               var5 = new String(var4);
            }

            StringBuilder var6 = (new StringBuilder()).append(var3);
            String var7;
            if(var5 == null) {
               var7 = "";
            } else {
               var7 = ":" + var5;
            }

            String var8 = var6.append(var7).toString();
            char[] var9 = this.getRawHost();
            String var10 = new String(var9);
            String var11;
            if(this._port == -1) {
               var11 = var10;
            } else {
               StringBuilder var15 = (new StringBuilder()).append(var10).append(":");
               int var16 = this._port;
               var11 = var15.append(var16).toString();
            }

            String var12 = var8 + "@" + var11;
            char[] var13 = var8.toCharArray();
            this._userinfo = var13;
            char[] var14 = var12.toCharArray();
            this._authority = var14;
            this.setURI();
         }
      } else {
         throw new URIException(1, "user required");
      }
   }

   public void setRawUserinfo(char[] var1, char[] var2) throws URIException {
      if(var1 != null && var1.length != 0) {
         BitSet var3 = within_userinfo;
         if(this.validate(var1, var3)) {
            if(var2 != null) {
               BitSet var4 = within_userinfo;
               if(!this.validate(var2, var4)) {
                  throw new URIException(3, "escaped userinfo not valid");
               }
            }

            String var5 = new String(var1);
            String var6;
            if(var2 == null) {
               var6 = null;
            } else {
               var6 = new String(var2);
            }

            StringBuilder var7 = (new StringBuilder()).append(var5);
            String var8;
            if(var6 == null) {
               var8 = "";
            } else {
               var8 = ":" + var6;
            }

            String var9 = var7.append(var8).toString();
            char[] var10 = this.getRawHost();
            String var11 = new String(var10);
            String var12;
            if(this._port == -1) {
               var12 = var11;
            } else {
               StringBuilder var16 = (new StringBuilder()).append(var11).append(":");
               int var17 = this._port;
               var12 = var16.append(var17).toString();
            }

            String var13 = var9 + "@" + var12;
            char[] var14 = var9.toCharArray();
            this._userinfo = var14;
            char[] var15 = var13.toCharArray();
            this._authority = var15;
            this.setURI();
         } else {
            throw new URIException(3, "escaped userinfo not valid");
         }
      } else {
         throw new URIException(1, "user required");
      }
   }

   protected void setURI() {
      StringBuffer var1 = new StringBuffer();
      if(this._scheme != null) {
         char[] var2 = this._scheme;
         var1.append(var2);
         StringBuffer var4 = var1.append(':');
      }

      if(this._is_net_path) {
         StringBuffer var5 = var1.append("//");
         if(this._authority != null) {
            if(this._userinfo != null) {
               if(this._host != null) {
                  char[] var6 = this._host;
                  var1.append(var6);
                  if(this._port != -1) {
                     StringBuffer var8 = var1.append(':');
                     int var9 = this._port;
                     var1.append(var9);
                  }
               }
            } else {
               char[] var17 = this._authority;
               var1.append(var17);
            }
         }
      }

      if(this._opaque != null && this._is_opaque_part) {
         char[] var11 = this._opaque;
         var1.append(var11);
      } else if(this._path != null && this._path.length != 0) {
         char[] var19 = this._path;
         var1.append(var19);
      }

      if(this._query != null) {
         StringBuffer var13 = var1.append('?');
         char[] var14 = this._query;
         var1.append(var14);
      }

      char[] var16 = var1.toString().toCharArray();
      this._uri = var16;
      this.hash = 0;
   }

   public void setUser(String var1) throws URIException, NullPointerException {
      BitSet var2 = allowed_within_userinfo;
      String var3 = this.getProtocolCharset();
      char[] var4 = encode(var1, var2, var3);
      this.setRawUser(var4);
   }

   public void setUserinfo(String var1, String var2) throws URIException, NullPointerException {
      String var3 = this.getProtocolCharset();
      BitSet var4 = within_userinfo;
      char[] var5 = encode(var1, var4, var3);
      char[] var6;
      if(var2 == null) {
         var6 = null;
      } else {
         BitSet var7 = within_userinfo;
         var6 = encode(var2, var7, var3);
      }

      this.setRawUserinfo(var5, var6);
   }
}

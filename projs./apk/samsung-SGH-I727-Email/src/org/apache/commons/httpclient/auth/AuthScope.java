package org.apache.commons.httpclient.auth;

import org.apache.commons.httpclient.util.LangUtils;

public class AuthScope {

   public static final AuthScope ANY;
   public static final String ANY_HOST = null;
   public static final int ANY_PORT = 255;
   public static final String ANY_REALM = null;
   public static final String ANY_SCHEME = null;
   private String host;
   private int port;
   private String realm;
   private String scheme;


   static {
      String var0 = ANY_HOST;
      String var1 = ANY_REALM;
      String var2 = ANY_SCHEME;
      ANY = new AuthScope(var0, -1, var1, var2);
   }

   public AuthScope(String var1, int var2) {
      String var3 = ANY_REALM;
      String var4 = ANY_SCHEME;
      this(var1, var2, var3, var4);
   }

   public AuthScope(String var1, int var2, String var3) {
      String var4 = ANY_SCHEME;
      this(var1, var2, var3, var4);
   }

   public AuthScope(String var1, int var2, String var3, String var4) {
      this.scheme = null;
      this.realm = null;
      this.host = null;
      this.port = -1;
      String var5;
      if(var1 == null) {
         var5 = ANY_HOST;
      } else {
         var5 = var1.toLowerCase();
      }

      this.host = var5;
      int var6;
      if(var2 < 0) {
         var6 = -1;
      } else {
         var6 = var2;
      }

      this.port = var6;
      String var7;
      if(var3 == null) {
         var7 = ANY_REALM;
      } else {
         var7 = var3;
      }

      this.realm = var7;
      String var8;
      if(var4 == null) {
         var8 = ANY_SCHEME;
      } else {
         var8 = var4.toUpperCase();
      }

      this.scheme = var8;
   }

   public AuthScope(AuthScope var1) {
      this.scheme = null;
      this.realm = null;
      this.host = null;
      this.port = -1;
      if(var1 == null) {
         throw new IllegalArgumentException("Scope may not be null");
      } else {
         String var2 = var1.getHost();
         this.host = var2;
         int var3 = var1.getPort();
         this.port = var3;
         String var4 = var1.getRealm();
         this.realm = var4;
         String var5 = var1.getScheme();
         this.scheme = var5;
      }
   }

   private static boolean paramsEqual(int var0, int var1) {
      boolean var2;
      if(var0 == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static boolean paramsEqual(String var0, String var1) {
      byte var2;
      if(var0 == null) {
         if(var0 == var1) {
            var2 = 1;
         } else {
            var2 = 0;
         }
      } else {
         var2 = var0.equals(var1);
      }

      return (boolean)var2;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(var1 == this) {
         var2 = true;
      } else if(!(var1 instanceof AuthScope)) {
         var2 = super.equals(var1);
      } else {
         AuthScope var3 = (AuthScope)var1;
         String var4 = this.host;
         String var5 = var3.host;
         if(paramsEqual(var4, var5)) {
            int var6 = this.port;
            int var7 = var3.port;
            if(paramsEqual(var6, var7)) {
               String var8 = this.realm;
               String var9 = var3.realm;
               if(paramsEqual(var8, var9)) {
                  String var10 = this.scheme;
                  String var11 = var3.scheme;
                  if(paramsEqual(var10, var11)) {
                     var2 = true;
                     return var2;
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getRealm() {
      return this.realm;
   }

   public String getScheme() {
      return this.scheme;
   }

   public int hashCode() {
      String var1 = this.host;
      int var2 = LangUtils.hashCode(17, var1);
      int var3 = this.port;
      int var4 = LangUtils.hashCode(var2, var3);
      String var5 = this.realm;
      int var6 = LangUtils.hashCode(var4, var5);
      String var7 = this.scheme;
      return LangUtils.hashCode(var6, var7);
   }

   public int match(AuthScope var1) {
      int var2 = 0;
      String var3 = this.scheme;
      String var4 = var1.scheme;
      int var11;
      if(paramsEqual(var3, var4)) {
         var2 = 0 + 1;
      } else {
         String var12 = this.scheme;
         String var13 = ANY_SCHEME;
         if(var12 != var13) {
            String var14 = var1.scheme;
            String var15 = ANY_SCHEME;
            if(var14 != var15) {
               var11 = -1;
               return var11;
            }
         }
      }

      String var5 = this.realm;
      String var6 = var1.realm;
      if(paramsEqual(var5, var6)) {
         var2 += 2;
      } else {
         String var16 = this.realm;
         String var17 = ANY_REALM;
         if(var16 != var17) {
            String var18 = var1.realm;
            String var19 = ANY_REALM;
            if(var18 != var19) {
               var11 = -1;
               return var11;
            }
         }
      }

      int var7 = this.port;
      int var8 = var1.port;
      if(paramsEqual(var7, var8)) {
         var2 += 4;
      } else if(this.port != -1 && var1.port != -1) {
         var11 = -1;
         return var11;
      }

      String var9 = this.host;
      String var10 = var1.host;
      if(paramsEqual(var9, var10)) {
         var2 += 8;
      } else {
         String var20 = this.host;
         String var21 = ANY_HOST;
         if(var20 != var21) {
            String var22 = var1.host;
            String var23 = ANY_HOST;
            if(var22 != var23) {
               var11 = -1;
               return var11;
            }
         }
      }

      var11 = var2;
      return var11;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      if(this.scheme != null) {
         String var2 = this.scheme.toUpperCase();
         var1.append(var2);
         StringBuffer var4 = var1.append(' ');
      }

      if(this.realm != null) {
         StringBuffer var5 = var1.append('\'');
         String var6 = this.realm;
         var1.append(var6);
         StringBuffer var8 = var1.append('\'');
      } else {
         StringBuffer var15 = var1.append("<any realm>");
      }

      if(this.host != null) {
         StringBuffer var9 = var1.append('@');
         String var10 = this.host;
         var1.append(var10);
         if(this.port >= 0) {
            StringBuffer var12 = var1.append(':');
            int var13 = this.port;
            var1.append(var13);
         }
      }

      return var1.toString();
   }
}

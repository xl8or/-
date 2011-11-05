package org.apache.commons.httpclient;

import org.apache.commons.httpclient.ProtocolException;

public class HttpVersion implements Comparable {

   public static final HttpVersion HTTP_0_9 = new HttpVersion(0, 9);
   public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);
   public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);
   private int major = 0;
   private int minor = 0;


   public HttpVersion(int var1, int var2) {
      if(var1 < 0) {
         throw new IllegalArgumentException("HTTP major version number may not be negative");
      } else {
         this.major = var1;
         if(var2 < 0) {
            throw new IllegalArgumentException("HTTP minor version number may not be negative");
         } else {
            this.minor = var2;
         }
      }
   }

   public static HttpVersion parse(String var0) throws ProtocolException {
      if(var0 == null) {
         throw new IllegalArgumentException("String may not be null");
      } else if(!var0.startsWith("HTTP/")) {
         String var1 = "Invalid HTTP version string: " + var0;
         throw new ProtocolException(var1);
      } else {
         int var2 = "HTTP/".length();
         int var3 = var0.indexOf(".", var2);
         if(var3 == -1) {
            String var4 = "Invalid HTTP version number: " + var0;
            throw new ProtocolException(var4);
         } else {
            int var5;
            try {
               var5 = Integer.parseInt(var0.substring(var2, var3));
            } catch (NumberFormatException var13) {
               String var9 = "Invalid HTTP major version number: " + var0;
               throw new ProtocolException(var9);
            }

            int var6 = var5;
            var2 = var3 + 1;
            var3 = var0.length();

            try {
               var5 = Integer.parseInt(var0.substring(var2, var3));
            } catch (NumberFormatException var12) {
               String var11 = "Invalid HTTP minor version number: " + var0;
               throw new ProtocolException(var11);
            }

            return new HttpVersion(var6, var5);
         }
      }
   }

   public int compareTo(Object var1) {
      HttpVersion var2 = (HttpVersion)var1;
      return this.compareTo(var2);
   }

   public int compareTo(HttpVersion var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Version parameter may not be null");
      } else {
         int var2 = this.getMajor();
         int var3 = var1.getMajor();
         int var4 = var2 - var3;
         if(var4 == 0) {
            int var5 = this.getMinor();
            int var6 = var1.getMinor();
            var4 = var5 - var6;
         }

         return var4;
      }
   }

   public boolean equals(Object var1) {
      byte var2;
      if(this == var1) {
         var2 = 1;
      } else if(!(var1 instanceof HttpVersion)) {
         var2 = 0;
      } else {
         HttpVersion var3 = (HttpVersion)var1;
         var2 = this.equals(var3);
      }

      return (boolean)var2;
   }

   public boolean equals(HttpVersion var1) {
      boolean var2;
      if(this.compareTo(var1) == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public boolean greaterEquals(HttpVersion var1) {
      boolean var2;
      if(this.compareTo(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      int var1 = this.major * 100000;
      int var2 = this.minor;
      return var1 + var2;
   }

   public boolean lessEquals(HttpVersion var1) {
      boolean var2;
      if(this.compareTo(var1) <= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("HTTP/");
      int var3 = this.major;
      var1.append(var3);
      StringBuffer var5 = var1.append('.');
      int var6 = this.minor;
      var1.append(var6);
      return var1.toString();
   }
}

package org.apache.commons.httpclient.cookie;


public final class CookieOrigin {

   private final String host;
   private final String path;
   private final int port;
   private final boolean secure;


   public CookieOrigin(String var1, int var2, String var3, boolean var4) {
      if(var1 == null) {
         throw new IllegalArgumentException("Host of origin may not be null");
      } else if(var1.trim().length() == 0) {
         throw new IllegalArgumentException("Host of origin may not be blank");
      } else if(var2 < 0) {
         String var5 = "Invalid port: " + var2;
         throw new IllegalArgumentException(var5);
      } else if(var3 == null) {
         throw new IllegalArgumentException("Path of origin may not be null.");
      } else {
         this.host = var1;
         this.port = var2;
         this.path = var3;
         this.secure = var4;
      }
   }

   public String getHost() {
      return this.host;
   }

   public String getPath() {
      return this.path;
   }

   public int getPort() {
      return this.port;
   }

   public boolean isSecure() {
      return this.secure;
   }
}

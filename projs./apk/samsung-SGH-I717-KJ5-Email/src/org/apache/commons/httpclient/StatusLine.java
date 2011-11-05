package org.apache.commons.httpclient;

import org.apache.commons.httpclient.HttpException;

public class StatusLine {

   private final String httpVersion;
   private final String reasonPhrase;
   private final int statusCode;
   private final String statusLine;


   public StatusLine(String param1) throws HttpException {
      // $FF: Couldn't be decompiled
   }

   public static boolean startsWithHTTP(String var0) {
      int var1 = 0;

      boolean var4;
      boolean var5;
      try {
         while(Character.isWhitespace(var0.charAt(var1))) {
            ++var1;
         }

         int var2 = var1 + 4;
         String var3 = var0.substring(var1, var2);
         var4 = "HTTP".equals(var3);
      } catch (StringIndexOutOfBoundsException var7) {
         var5 = false;
         return var5;
      }

      var5 = var4;
      return var5;
   }

   public final String getHttpVersion() {
      return this.httpVersion;
   }

   public final String getReasonPhrase() {
      return this.reasonPhrase;
   }

   public final int getStatusCode() {
      return this.statusCode;
   }

   public final String toString() {
      return this.statusLine;
   }
}

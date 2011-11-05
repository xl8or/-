package com.android.volley;

import com.android.volley.Cache;
import com.android.volley.NetworkError;

public class Response<T extends Object> {

   public final Cache.Entry cacheEntry;
   public final NetworkError error;
   public final Response.ErrorCode errorCode;
   public boolean intermediate = 0;
   public final String message;
   public final T result;


   private Response(Response.ErrorCode var1, String var2, NetworkError var3) {
      this.result = null;
      this.cacheEntry = null;
      this.errorCode = var1;
      this.message = var2;
      this.error = var3;
   }

   private Response(T var1, Cache.Entry var2) {
      this.result = var1;
      this.cacheEntry = var2;
      Response.ErrorCode var3 = Response.ErrorCode.OK;
      this.errorCode = var3;
      this.message = null;
      this.error = null;
   }

   public static <T extends Object> Response<T> error(Response.ErrorCode var0, String var1, NetworkError var2) {
      return new Response(var0, var1, var2);
   }

   public static <T extends Object> Response<T> success(T var0, Cache.Entry var1) {
      return new Response(var0, var1);
   }

   public static enum ErrorCode {

      // $FF: synthetic field
      private static final Response.ErrorCode[] $VALUES;
      AUTH("AUTH", 4),
      NETWORK("NETWORK", 3),
      OK("OK", 0),
      SERVER("SERVER", 1),
      TIMEOUT("TIMEOUT", 2);


      static {
         Response.ErrorCode[] var0 = new Response.ErrorCode[5];
         Response.ErrorCode var1 = OK;
         var0[0] = var1;
         Response.ErrorCode var2 = SERVER;
         var0[1] = var2;
         Response.ErrorCode var3 = TIMEOUT;
         var0[2] = var3;
         Response.ErrorCode var4 = NETWORK;
         var0[3] = var4;
         Response.ErrorCode var5 = AUTH;
         var0[4] = var5;
         $VALUES = var0;
      }

      private ErrorCode(String var1, int var2) {}
   }

   public interface Listener<T extends Object> {

      void onResponse(T var1);
   }

   public interface ErrorListener {

      void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3);
   }
}

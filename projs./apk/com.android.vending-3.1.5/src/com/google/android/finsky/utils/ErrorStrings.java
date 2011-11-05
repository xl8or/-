package com.google.android.finsky.utils;

import android.content.Context;
import com.android.volley.Response;

public class ErrorStrings {

   public ErrorStrings() {}

   public static String get(Context var0, Response.ErrorCode var1, String var2) {
      int[] var3 = ErrorStrings.1.$SwitchMap$com$android$volley$Response$ErrorCode;
      int var4 = var1.ordinal();
      switch(var3[var4]) {
      case 1:
         var2 = var0.getString(2131230929);
         break;
      case 2:
         if(var2 == null) {
            var2 = var0.getString(2131230927);
         }
         break;
      case 3:
         if(var2 == null) {
            var2 = var0.getString(2131230921);
         }
         break;
      case 4:
         var2 = var0.getString(2131230922);
         break;
      default:
         String var5 = "Invalid error state " + var1;
         throw new IllegalArgumentException(var5);
      }

      return var2;
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$volley$Response$ErrorCode = new int[Response.ErrorCode.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$volley$Response$ErrorCode;
            int var1 = Response.ErrorCode.AUTH.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$volley$Response$ErrorCode;
            int var3 = Response.ErrorCode.SERVER.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$volley$Response$ErrorCode;
            int var5 = Response.ErrorCode.NETWORK.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$volley$Response$ErrorCode;
            int var7 = Response.ErrorCode.TIMEOUT.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }
}

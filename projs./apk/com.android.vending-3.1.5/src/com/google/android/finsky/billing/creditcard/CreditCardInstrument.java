package com.google.android.finsky.billing.creditcard;

import android.accounts.Account;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.Spanned;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.DfeAnalytics;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.FopFactory;
import com.google.android.finsky.billing.Instrument;
import com.google.android.finsky.billing.creditcard.CreateCreditCardFlow;
import com.google.android.finsky.config.G;
import com.google.android.finsky.remoting.protos.Buy;
import com.google.android.finsky.utils.FinskyLog;

public class CreditCardInstrument extends Instrument {

   public CreditCardInstrument(Buy.BuyResponse.CheckoutInfo.CheckoutOption var1, Drawable var2) {
      super(var1, var2);
   }

   public static void registerWithFactory(FopFactory var0) {
      CreditCardInstrument.1 var1 = new CreditCardInstrument.1();
      var0.registerFormOfPayment(0, var1);
   }

   public BillingFlow completePurchase(BillingFlowContext var1, BillingFlowListener var2, Bundle var3) {
      return null;
   }

   static class 1 extends FopFactory.FormOfPayment {

      1() {}

      public boolean canAdd() {
         return true;
      }

      public BillingFlow create(BillingFlowContext var1, BillingFlowListener var2, Bundle var3) {
         String var4 = var3.getString("authAccount");
         FinskyApp var5 = FinskyApp.get();
         Account var6 = AccountHandler.findAccount(var4, var5);
         CreateCreditCardFlow var8;
         if(var6 == null) {
            Object[] var7 = new Object[0];
            FinskyLog.e("Invalid account passed in parameters.", var7);
            var8 = null;
         } else {
            DfeApi var9 = FinskyApp.get().getDfeApi(var6);
            FinskyApp var10 = FinskyApp.get();
            String var11 = (String)G.checkoutAuthTokenType.get();
            AndroidAuthenticator var12 = new AndroidAuthenticator(var10, var6, var11);
            Looper var13 = Looper.getMainLooper();
            Handler var14 = new Handler(var13);
            DfeAnalytics var15 = new DfeAnalytics(var14, var9);
            var8 = new CreateCreditCardFlow(var1, var2, var12, var9, var15, var3);
         }

         return var8;
      }

      public Instrument get(Buy.BuyResponse.CheckoutInfo.CheckoutOption var1, Drawable var2) {
         return new CreditCardInstrument(var1, var2);
      }

      public Drawable getAddIcon() {
         return FinskyApp.get().getResources().getDrawable(2130837544);
      }

      public String getAddText() {
         return FinskyApp.get().getString(2131230782);
      }
   }

   public static class CreditCardNumberFilter implements InputFilter {

      public CreditCardNumberFilter() {}

      public static String getNumbers(CharSequence var0) {
         StringBuilder var1 = new StringBuilder(16);
         int var2 = 0;

         for(int var3 = var0.length(); var2 < var3; ++var2) {
            char var4 = var0.charAt(var2);
            if(isNumber(var4)) {
               var1.append(var4);
            }
         }

         return var1.toString();
      }

      private static boolean isAllowed(char var0, char var1) {
         boolean var2;
         if(!isNumber(var0) && (!isSeparator(var0) || !isNumber(var1))) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      private static boolean isNumber(char var0) {
         boolean var1;
         if(var0 >= 48 && var0 <= 57) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      private static boolean isSeparator(char var0) {
         boolean var1;
         if(var0 != 32 && var0 != 45) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
         char var8;
         if(var5 > 0) {
            int var7 = var5 + -1;
            var8 = var4.charAt(var7);
         } else {
            var8 = 0;
         }

         int var9 = var3 - var2;
         String var10;
         if(var9 == 1) {
            if(isAllowed(var1.charAt(var2), var8)) {
               var10 = null;
            } else {
               var10 = "";
            }
         } else if(var9 == 0) {
            var10 = null;
         } else {
            char[] var11 = new char[var9];
            byte var12 = 0;
            boolean var13 = false;

            for(int var14 = var2; var14 < var3; ++var14) {
               char var15 = var1.charAt(var14);
               if(isAllowed(var15, var8)) {
                  var11[var12] = var15;
                  var8 = var15;
               } else {
                  var13 = true;
               }
            }

            if(var13) {
               var10 = new String(var11, 0, var12);
            } else {
               var10 = null;
            }
         }

         return var10;
      }
   }
}

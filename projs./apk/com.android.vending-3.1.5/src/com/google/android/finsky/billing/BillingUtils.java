package com.google.android.finsky.billing;

import android.content.Context;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import com.android.i18n.addressinput.AddressData;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.remoting.protos.Address;
import com.google.android.finsky.utils.Sha1Util;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public class BillingUtils {

   public BillingUtils() {}

   public static String getDefaultCountry(Context var0, String var1) {
      if(TextUtils.isEmpty(var1)) {
         var1 = ((TelephonyManager)var0.getSystemService("phone")).getSimCountryIso().toUpperCase();
      }

      if(TextUtils.isEmpty(var1)) {
         var1 = "US";
      }

      return var1;
   }

   public static String getLine1Number(Context var0) {
      TelephonyManager var1 = (TelephonyManager)var0.getSystemService("phone");
      byte var2 = 1;

      label18: {
         byte var6;
         try {
            Class[] var3 = new Class[0];
            Method var4 = TelephonyManager.class.getMethod("isVoiceCapable", var3);
            Object[] var5 = new Object[0];
            var6 = ((Boolean)var4.invoke(var1, var5)).booleanValue();
         } catch (Throwable var9) {
            break label18;
         }

         var2 = var6;
      }

      String var7;
      if(var2 != 0) {
         var7 = var1.getLine1Number();
      } else {
         var7 = null;
      }

      return var7;
   }

   public static String getRiskHeader(Context var0) {
      String var1 = ((TelephonyManager)var0.getSystemService("phone")).getDeviceId();
      String var2 = FinskyApp.get().getCurrentAccountName();
      String var3 = String.valueOf(G.androidId.get());
      return Sha1Util.secureHash(var1 + ":" + var2 + ":" + var3);
   }

   public static <T extends View> T getTopMostView(ViewGroup var0, Collection<T> var1) {
      Pair var2 = null;
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         View var4 = (View)var3.next();
         int var5 = getViewOffsetToChild(var0, var4);
         if(var2 != null) {
            int var6 = ((Integer)var2.first).intValue();
            if(var5 >= var6) {
               continue;
            }
         }

         var2 = Pair.create(Integer.valueOf(var5), var4);
      }

      View var7;
      if(var2 != null) {
         var7 = (View)var2.second;
      } else {
         var7 = null;
      }

      return var7;
   }

   public static int getViewOffsetToChild(ViewGroup var0, View var1) {
      Rect var2 = new Rect();
      var0.offsetDescendantRectToMyCoords(var1, var2);
      return var2.top;
   }

   public static Address instrumentAddressFromAddressData(AddressData var0, String var1) {
      Address var2 = new Address();
      if(var0.getPostalCountry() != null) {
         String var3 = var0.getPostalCountry();
         var2.setPostalCountry(var3);
      }

      if(var0.getAddressLine1() != null) {
         String var5 = var0.getAddressLine1();
         var2.setAddressLine1(var5);
      }

      if(var0.getAddressLine2() != null) {
         String var7 = var0.getAddressLine2();
         var2.setAddressLine2(var7);
      }

      if(var0.getAdministrativeArea() != null) {
         String var9 = var0.getAdministrativeArea();
         var2.setState(var9);
      }

      if(var0.getLocality() != null) {
         String var11 = var0.getLocality();
         var2.setCity(var11);
      }

      if(var0.getDependentLocality() != null) {
         String var13 = var0.getDependentLocality();
         var2.setDependentLocality(var13);
      }

      if(var0.getPostalCode() != null) {
         String var15 = var0.getPostalCode();
         var2.setPostalCode(var15);
      }

      if(var0.getSortingCode() != null) {
         String var17 = var0.getSortingCode();
         var2.setSortingCode(var17);
      }

      if(var0.getRecipient() != null) {
         String var19 = var0.getRecipient();
         var2.setName(var19);
      }

      if(var0.getLanguageCode() != null) {
         String var21 = var0.getLanguageCode();
         var2.setLanguageCode(var21);
      }

      if(var1 != null) {
         var2.setPhoneNumber(var1);
      }

      return var2;
   }

   public static boolean isEmptyOrSpaces(String var0) {
      boolean var1;
      if(var0 != null && var0.trim().length() != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static String replaceLanguageAndRegion(String var0) {
      if(var0.contains("%lang%") || var0.contains("%region%")) {
         Locale var1 = Locale.getDefault();
         String var2 = var1.getLanguage().toLowerCase();
         String var3 = var0.replace("%lang%", var2);
         String var4 = var1.getCountry().toLowerCase();
         var0 = var3.replace("%region%", var4);
      }

      return var0;
   }

   public static String replaceLocale(String var0) {
      if(var0.contains("%locale%")) {
         Locale var1 = Locale.getDefault();
         StringBuilder var2 = new StringBuilder();
         String var3 = var1.getLanguage();
         StringBuilder var4 = var2.append(var3).append("_");
         String var5 = var1.getCountry().toLowerCase();
         String var6 = var4.append(var5).toString();
         var0 = var0.replace("%locale%", var6);
      }

      return var0;
   }
}

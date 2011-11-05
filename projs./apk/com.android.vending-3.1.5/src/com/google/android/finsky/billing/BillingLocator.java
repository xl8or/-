package com.google.android.finsky.billing;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.NoCache;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.carrierbilling.api.DcbApi;
import com.google.android.finsky.billing.carrierbilling.api.DcbApiContext;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.remoting.RadioHttpClient;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Utils;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.impl.client.DefaultHttpClient;

public class BillingLocator {

   private static boolean isInitialized = 0;
   private static Context sApplicationContext;
   private static CarrierBillingStorage sCarrierBillingStorage;


   public BillingLocator() {}

   public static DcbApi createDcbApi() {
      Utils.ensureOnMainThread();
      DcbApi var0;
      if(sApplicationContext == null) {
         var0 = null;
      } else {
         Context var1 = sApplicationContext;
         DefaultHttpClient var2 = new DefaultHttpClient();
         RadioHttpClient var3 = new RadioHttpClient(var1, var2);
         NoCache var4 = new NoCache();
         Context var5 = sApplicationContext;
         BasicNetwork var6 = new BasicNetwork(var5, var3);
         RequestQueue var7 = new RequestQueue(var4, var6);
         CarrierBillingStorage var8 = getCarrierBillingStorage();
         String var9 = getLine1NumberFromTelephony();
         String var10 = getSubscriberIdFromTelephony();
         DcbApiContext var11 = new DcbApiContext(var8, var9, var10);
         var0 = new DcbApi(var7, var11);
      }

      return var0;
   }

   public static List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> getBillingCountries() {
      Utils.ensureOnMainThread();
      ArrayList var0 = Lists.newArrayList();
      String var1 = (String)BillingPreferences.BILLING_COUNTRIES.get();
      if(var1 == null) {
         var0 = null;
      } else {
         String[] var2 = var1.split("\\}\\{");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if(var5.length() == 0) {
               Object[] var6 = new Object[0];
               FinskyLog.w("Got empty billing country string.", var6);
            } else {
               if(var5.charAt(0) == 123) {
                  var5 = var5.substring(1);
               }

               int var7 = var5.length() + -1;
               if(var5.charAt(var7) == 125) {
                  int var8 = var5.length() + -1;
                  var5 = var5.substring(0, var8);
               }

               String[] var9 = var5.split(",");
               if(var9.length != 2) {
                  Object[] var10 = new Object[]{var5, null};
                  Integer var11 = Integer.valueOf(var9.length);
                  var10[1] = var11;
                  FinskyLog.w("Invalid country string: %s. Expected 2 parts, got %d.", var10);
               } else {
                  VendingProtos.PurchaseMetadataResponseProto.Countries.Country var12 = new VendingProtos.PurchaseMetadataResponseProto.Countries.Country();
                  String var13 = var9[0];
                  var12.setCountryCode(var13);
                  String var15 = var9[1];
                  var12.setCountryName(var15);
                  var0.add(var12);
               }
            }
         }
      }

      return var0;
   }

   public static CarrierBillingStorage getCarrierBillingStorage() {
      Utils.ensureOnMainThread();
      if(sCarrierBillingStorage == null && sApplicationContext != null) {
         throw new IllegalStateException("CarrierBillingStorage has not been initialized.");
      } else {
         return sCarrierBillingStorage;
      }
   }

   public static String getLine1NumberFromTelephony() {
      Utils.ensureOnMainThread();
      if(sApplicationContext == null) {
         throw new IllegalStateException("BillingLocator has not been initialized.");
      } else {
         String var0 = ((TelephonyManager)sApplicationContext.getSystemService("phone")).getLine1Number();
         if(var0 == null) {
            var0 = "";
         }

         return var0;
      }
   }

   private static String getSubscriberIdFromTelephony() {
      return ((TelephonyManager)sApplicationContext.getSystemService("phone")).getSubscriberId();
   }

   public static void initCarrierBillingStorage(Runnable var0) {
      Utils.ensureOnMainThread();
      sCarrierBillingStorage.init(var0);
   }

   public static void initSingleton() {
      if(!isInitialized) {
         isInitialized = (boolean)1;
         sApplicationContext = FinskyApp.get();
         Context var0 = sApplicationContext;
         sCarrierBillingStorage = new CarrierBillingStorage(var0);
      } else {
         throw new IllegalStateException("BillingLocator already initialized.");
      }
   }

   public static void setBillingCountries(List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> var0) {
      Utils.ensureOnMainThread();
      StringBuilder var1 = new StringBuilder();

      String var7;
      StringBuilder var8;
      for(Iterator var2 = var0.iterator(); var2.hasNext(); var8 = var1.append(var7).append('}')) {
         VendingProtos.PurchaseMetadataResponseProto.Countries.Country var3 = (VendingProtos.PurchaseMetadataResponseProto.Countries.Country)var2.next();
         StringBuilder var4 = var1.append('{');
         String var5 = var3.getCountryCode();
         StringBuilder var6 = var4.append(var5).append(',');
         var7 = var3.getCountryName();
      }

      PreferenceFile.SharedPreference var9 = BillingPreferences.BILLING_COUNTRIES;
      String var10 = var1.toString();
      var9.put(var10);
      PreferenceFile.SharedPreference var11 = BillingPreferences.LAST_BILLING_COUNTRIES_REFRESH_MILLIS;
      Long var12 = Long.valueOf(System.currentTimeMillis());
      var11.put(var12);
   }
}

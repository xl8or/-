package com.google.android.vending.remoting.api;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.android.volley.RequestQueue;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.Maps;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.api.VendingApiContext;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VendingApiFactory {

   private Context mContext;
   private RequestQueue mQueue;
   private final Map<String, VendingApi> mVendingApiMap;


   public VendingApiFactory(Context var1, RequestQueue var2) {
      HashMap var3 = Maps.newHashMap();
      this.mVendingApiMap = var3;
      this.mContext = var1;
      this.mQueue = var2;
   }

   private VendingApiContext getApiContext(Account var1) {
      try {
         PackageManager var2 = this.mContext.getPackageManager();
         String var3 = this.mContext.getPackageName();
         int var4 = var2.getPackageInfo(var3, 0).versionCode;
         TelephonyManager var5 = (TelephonyManager)this.mContext.getSystemService("phone");
         Context var6 = this.mContext;
         Locale var7 = Locale.getDefault();
         String var8 = Long.toHexString(((Long)G.androidId.get()).longValue());
         String var9 = var5.getNetworkOperatorName();
         String var10 = var5.getSimOperatorName();
         String var11 = var5.getNetworkOperator();
         String var12 = var5.getSimOperator();
         String var13 = Build.DEVICE;
         String var14 = VERSION.SDK;
         String var15 = (String)G.clientId.get();
         String var16 = (String)G.loggingId.get();
         VendingApiContext var18 = new VendingApiContext(var6, var1, var7, var8, var4, var9, var10, var11, var12, var13, var14, var15, var16);
         return var18;
      } catch (NameNotFoundException var20) {
         throw new RuntimeException("Can\'t find our own package", var20);
      }
   }

   public VendingApi getApi(String var1) {
      Map var2 = this.mVendingApiMap;
      synchronized(var2) {
         VendingApi var3 = (VendingApi)this.mVendingApiMap.get(var1);
         if(var3 == null) {
            RequestQueue var4 = this.mQueue;
            Account var5 = new Account(var1, "com.google");
            VendingApiContext var6 = this.getApiContext(var5);
            var3 = new VendingApi(var4, var6);
            this.mVendingApiMap.put(var1, var3);
         }

         return var3;
      }
   }
}

package com.android.email;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import com.android.email.activity.setup.AccountSettingsUtils;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;

public class VendorPolicyLoader {

   private static final Class<?>[] ARGS;
   private static final String FIND_PROVIDER = "findProvider";
   private static final String FIND_PROVIDER_IN_URI = "findProvider.inUri";
   private static final String FIND_PROVIDER_IN_USER = "findProvider.inUser";
   private static final String FIND_PROVIDER_NOTE = "findProvider.note";
   private static final String FIND_PROVIDER_OUT_URI = "findProvider.outUri";
   private static final String FIND_PROVIDER_OUT_USER = "findProvider.outUser";
   private static final String GET_IMAP_ID = "getImapId";
   private static final String GET_IMAP_ID_CAPA = "getImapId.capabilities";
   private static final String GET_IMAP_ID_HOST = "getImapId.host";
   private static final String GET_IMAP_ID_USER = "getImapId.user";
   private static final String GET_POLICY_METHOD = "getPolicy";
   private static final String POLICY_CLASS = "com.android.email.policy.EmailPolicy";
   private static final String POLICY_PACKAGE = "com.android.email.policy";
   private static final String USE_ALTERNATE_EXCHANGE_STRINGS = "useAlternateExchangeStrings";
   private static VendorPolicyLoader sInstance;
   private final Method mPolicyMethod;


   static {
      Class[] var0 = new Class[]{String.class, Bundle.class};
      ARGS = var0;
   }

   private VendorPolicyLoader(Context var1) {
      this(var1, "com.android.email.policy", "com.android.email.policy.EmailPolicy", (boolean)0);
   }

   VendorPolicyLoader(Context var1, String var2, String var3, boolean var4) {
      if(!var4 && !isSystemPackage(var1, var2)) {
         this.mPolicyMethod = null;
      } else {
         Method var5 = null;
         byte var6 = 3;

         label21: {
            Method var9;
            try {
               Class var7 = var1.createPackageContext(var2, var6).getClassLoader().loadClass(var3);
               Class[] var8 = ARGS;
               var9 = var7.getMethod("getPolicy", var8);
            } catch (NameNotFoundException var17) {
               break label21;
            } catch (ClassNotFoundException var18) {
               String var11 = "VendorPolicyLoader: " + var18;
               int var12 = Log.w("Email", var11);
               break label21;
            } catch (NoSuchMethodException var19) {
               String var14 = "VendorPolicyLoader: " + var19;
               int var15 = Log.w("Email", var14);
               break label21;
            }

            var5 = var9;
         }

         this.mPolicyMethod = var5;
      }
   }

   public static void clearInstanceForTest() {
      sInstance = null;
   }

   public static VendorPolicyLoader getInstance(Context var0) {
      if(sInstance == null) {
         sInstance = new VendorPolicyLoader(var0);
      }

      return sInstance;
   }

   public static void injectPolicyForTest(Context var0, String var1, Class<?> var2) {
      String var3 = var2.getName();
      Object[] var4 = new Object[]{var1, var3};
      String var5 = String.format("Using policy: package=%s name=%s", var4);
      int var6 = Log.d("Email", var5);
      sInstance = new VendorPolicyLoader(var0, var1, var3, (boolean)1);
   }

   static boolean isSystemPackage(Context var0, String var1) {
      int var2;
      boolean var3;
      try {
         var2 = var0.getPackageManager().getApplicationInfo(var1, 0).flags;
      } catch (NameNotFoundException var5) {
         var3 = false;
         return var3;
      }

      if((var2 & 1) != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public AccountSettingsUtils.Provider findProviderForDomain(String var1) {
      Bundle var2 = new Bundle();
      var2.putString("findProvider", var1);
      Bundle var3 = this.getPolicy("findProvider", var2);
      AccountSettingsUtils.Provider var12;
      if(var3 != null && !var3.isEmpty()) {
         label25: {
            AccountSettingsUtils.Provider var4;
            try {
               var4 = new AccountSettingsUtils.Provider();
               var4.id = null;
               var4.label = null;
               var4.domain = var1;
               String var5 = var3.getString("findProvider.inUri");
               URI var6 = new URI(var5);
               var4.incomingUriTemplate = var6;
               String var7 = var3.getString("findProvider.inUser");
               var4.incomingUsernameTemplate = var7;
               String var8 = var3.getString("findProvider.outUri");
               URI var9 = new URI(var8);
               var4.outgoingUriTemplate = var9;
               String var10 = var3.getString("findProvider.outUser");
               var4.outgoingUsernameTemplate = var10;
               String var11 = var3.getString("findProvider.note");
               var4.note = var11;
            } catch (URISyntaxException var16) {
               String var14 = "uri exception while vendor policy loads " + var1;
               int var15 = Log.d("Email", var14);
               break label25;
            }

            var12 = var4;
            return var12;
         }
      }

      var12 = null;
      return var12;
   }

   public String getImapIdValues(String var1, String var2, String var3) {
      Bundle var4 = new Bundle();
      var4.putString("getImapId.user", var1);
      var4.putString("getImapId.host", var2);
      var4.putString("getImapId.capabilities", var3);
      return this.getPolicy("getImapId", var4).getString("getImapId");
   }

   Bundle getPolicy(String var1, Bundle var2) {
      Bundle var3 = null;
      if(this.mPolicyMethod != null) {
         try {
            Method var4 = this.mPolicyMethod;
            Object[] var5 = new Object[]{var1, var2};
            var3 = (Bundle)var4.invoke((Object)null, var5);
         } catch (Exception var9) {
            int var8 = Log.w("Email", "VendorPolicyLoader", var9);
         }
      }

      Bundle var6;
      if(var3 != null) {
         var6 = var3;
      } else {
         var6 = Bundle.EMPTY;
      }

      return var6;
   }

   public boolean useAlternateExchangeStrings() {
      return this.getPolicy("useAlternateExchangeStrings", (Bundle)null).getBoolean("useAlternateExchangeStrings", (boolean)0);
   }
}

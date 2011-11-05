package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.KeyValueManager;
import com.facebook.katana.provider.KeyValueProvider;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Toaster;
import java.util.concurrent.atomic.AtomicBoolean;

public class FacebookAffiliation {

   private static final String TAG = "FacebookAffiliation";
   protected static boolean mFacebookEmployee;
   protected static boolean mFacebookEmployeeAccurate;
   protected static boolean mFacebookEmployeeEver;
   protected static boolean mFacebookEmployeeEverAccurate;
   protected static AtomicBoolean mRequestLock = new AtomicBoolean((boolean)0);
   protected static boolean mStatusSynced;
   protected static boolean mSuppressToast;


   private FacebookAffiliation() {}

   public static boolean hasEmployeeEverLoggedInOnThisPhone() {
      boolean var0;
      if((!mFacebookEmployeeEverAccurate || !mFacebookEmployeeEver) && (!mFacebookEmployeeAccurate || !mFacebookEmployee)) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static boolean hasEmployeeNeverLoggedInOnThisPhone() {
      boolean var0;
      if(mFacebookEmployeeEverAccurate && !mFacebookEmployeeEver && mFacebookEmployeeAccurate && !mFacebookEmployee) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void invalidateEmployeeBit(Context var0) {
      Log.v("FacebookAffiliation", "employee accurate bit cleared");
      mFacebookEmployeeAccurate = (boolean)0;
      mSuppressToast = (boolean)0;
      KeyValueManager.delete(var0, "key=\"is_employee\"", (String[])null);
   }

   public static boolean isCurrentUserEmployee() {
      boolean var0;
      if(mFacebookEmployeeAccurate && mFacebookEmployee) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void maybeToast(Context var0) {
      if(!mSuppressToast) {
         Toaster.toast(var0.getApplicationContext(), "This beta build is only enabled for employees and authorized users.");
         mSuppressToast = (boolean)1;
      }
   }

   public static void requestCompleted() {
      mRequestLock.set((boolean)0);
   }

   public static void setIsEmployee(Context var0, boolean var1) {
      String var2 = "employee bit set to " + var1;
      Log.v("FacebookAffiliation", var2);
      mFacebookEmployee = var1;
      byte var3;
      if((!mFacebookEmployeeEverAccurate || !mFacebookEmployeeEver) && !var1) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      mFacebookEmployeeEver = (boolean)var3;
      mFacebookEmployeeAccurate = (boolean)1;
      mFacebookEmployeeEverAccurate = (boolean)1;
      Boolean var4 = Boolean.valueOf(mFacebookEmployee);
      KeyValueManager.setValue(var0, "is_employee", var4);
      Boolean var5 = Boolean.valueOf(mFacebookEmployeeEver);
      KeyValueManager.setValue(var0, "seen_employee", var5);
      mStatusSynced = (boolean)1;
   }

   public static boolean shouldInitiateRequest() {
      boolean var0;
      if((!mFacebookEmployeeAccurate || !mFacebookEmployeeEverAccurate) && mRequestLock.compareAndSet((boolean)0, (boolean)1)) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void syncStatus(Context var0) {
      ContentResolver var1 = var0.getContentResolver();
      Uri var2 = KeyValueProvider.CONTENT_URI;
      String[] var3 = KeyValueManager.PROJECTION;
      Object var4 = null;
      Cursor var5 = var1.query(var2, var3, "key IN(\"is_employee\",\"seen_employee\")", (String[])null, (String)var4);
      if(var5 != null) {
         boolean var9;
         for(boolean var6 = var5.moveToFirst(); !var5.isAfterLast(); var9 = var5.moveToNext()) {
            String var7 = var5.getString(0);
            String var8 = var5.getString(1);
            if(var7.equals("is_employee")) {
               mFacebookEmployee = Boolean.parseBoolean(var8);
               mFacebookEmployeeAccurate = (boolean)1;
            } else if(var7.equals("seen_employee")) {
               mFacebookEmployeeEver = Boolean.parseBoolean(var8);
               mFacebookEmployeeEverAccurate = (boolean)1;
            }
         }

         var5.close();
      }

      mStatusSynced = (boolean)1;
   }

   public static boolean synced() {
      return mStatusSynced;
   }

   private interface FacebookAffiliationQuery {

      String PREDICATE = "key IN(\"is_employee\",\"seen_employee\")";

   }
}

package com.google.android.finsky.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.utils.Utils;
import com.google.android.finsky.utils.VendingPreferences;
import java.lang.reflect.Method;

public class SharedPreferencesUtils {

   private static final String PREFS_KEY_FILTER_PASSCODE = "pin_code";
   private static Method sAsyncSaveMethod = null;
   private static boolean sUseAsyncSave = 1;


   public SharedPreferencesUtils() {}

   public static boolean commit(Editor var0) {
      byte var1;
      if(!sUseAsyncSave) {
         var1 = var0.commit();
      } else {
         if(sAsyncSaveMethod == null) {
            try {
               Class[] var2 = new Class[0];
               sAsyncSaveMethod = Editor.class.getDeclaredMethod("apply", var2);
            } catch (Exception var8) {
               sUseAsyncSave = (boolean)0;
            }
         }

         if(sAsyncSaveMethod != null) {
            label33: {
               try {
                  Method var3 = sAsyncSaveMethod;
                  Object[] var4 = new Object[0];
                  var3.invoke(var0, var4);
               } catch (Exception var9) {
                  sUseAsyncSave = (boolean)0;
                  break label33;
               }

               var1 = 1;
               return (boolean)var1;
            }
         }

         var1 = var0.commit();
      }

      return (boolean)var1;
   }

   public static String getCurrentPasscode(Context var0, SharedPreferences var1) {
      String var2 = var1.getString("pin_code", (String)null);
      if(TextUtils.isEmpty(var2)) {
         var2 = migrateOldPasscodes(var0, var1);
      }

      return var2;
   }

   private static String migrateOldPasscodes(Context var0, SharedPreferences var1) {
      String var2 = null;
      Account[] var3 = AccountHandler.getAccounts(var0);
      String var4 = null;
      if(var3.length == 1) {
         String var5 = var3[0].name;
         String var6 = Utils.getPreferenceKey("pin_code", var5);
         var4 = var1.getString(var6, (String)null);
         if(TextUtils.isEmpty(var4)) {
            var4 = (String)VendingPreferences.PIN_CODE.get();
         }
      }

      if(!TextUtils.isEmpty(var4)) {
         var1.edit().putString("pin_code", var4).commit();
         var2 = var4;
      }

      VendingPreferences.PIN_CODE.remove();
      Editor var8 = var1.edit();

      for(int var9 = var3.length + -1; var9 >= 0; var9 += -1) {
         String var10 = var3[var9].name;
         String var11 = Utils.getPreferenceKey("pin_code", var10);
         var8.remove(var11);
      }

      boolean var13 = var8.commit();
      return var2;
   }
}

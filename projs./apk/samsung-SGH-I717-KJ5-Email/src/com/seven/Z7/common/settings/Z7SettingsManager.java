package com.seven.Z7.common.settings;

import android.os.RemoteException;
import android.util.Log;
import com.seven.Z7.common.IZ7Service;
import com.seven.Z7.common.Z7Preference;
import java.util.Map;

public class Z7SettingsManager {

   public static final int GLOBAL_ID = 0;
   private static final String TAG = "Z7SettingsManager";
   private final IZ7Service service;


   public Z7SettingsManager(IZ7Service var1) {
      this.service = var1;
   }

   private Object getPreference(int var1, String var2, Object var3) {
      Object var6;
      Object var7;
      try {
         IZ7Service var4 = this.service;
         Z7Preference var5 = new Z7Preference(var2, var3);
         var6 = var4.getPreference(var1, var5).getValue();
      } catch (RemoteException var11) {
         String var9 = "Error in getting value for preference " + var2 + " returning defaults";
         int var10 = Log.e("Z7SettingsManager", var9, var11);
         var7 = var3;
         return var7;
      }

      var7 = var6;
      return var7;
   }

   public void enableExternalLog(boolean var1) {
      Boolean var2 = Boolean.valueOf(var1);
      this.setPreference(0, "external_logs", var2);
   }

   public void enableServiceNotifications(boolean var1) {
      Boolean var2 = Boolean.valueOf(var1);
      this.setPreference(0, "service_notifications", var2);
   }

   public Map<String, Object> getAllSettings(int var1) {
      Map var2;
      Map var3;
      try {
         var2 = this.service.getPreferences(var1);
      } catch (RemoteException var6) {
         int var5 = Log.e("Z7SettingsManager", "Getting preferences failed", var6);
         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public boolean getBooleanPreference(int var1, String var2, boolean var3) {
      Boolean var4 = Boolean.valueOf(var3);
      return ((Boolean)this.getPreference(var1, var2, var4)).booleanValue();
   }

   public int getIntPreference(int var1, String var2, int var3) {
      Integer var4 = Integer.valueOf(var3);
      return ((Integer)this.getPreference(var1, var2, var4)).intValue();
   }

   public long getLongPreference(int var1, String var2, long var3) {
      Long var5 = Long.valueOf(var3);
      return ((Long)this.getPreference(var1, var2, var5)).longValue();
   }

   public String getStringPreference(int var1, String var2, String var3) {
      return (String)this.getPreference(var1, var2, var3);
   }

   public boolean isExternalLogEnabled() {
      return this.getBooleanPreference(0, "external_logs", (boolean)1);
   }

   public boolean isServiceNotificationsEnabled() {
      return this.getBooleanPreference(0, "service_notifications", (boolean)1);
   }

   public void removePreference(int var1, String var2) {
      try {
         this.service.removePreference(var1, var2);
      } catch (Exception var4) {
         ;
      }
   }

   public void setPreference(int var1, String var2, Object var3) {
      Z7Preference var4 = new Z7Preference(var2, var3);
      if(var4.getType() != 0) {
         try {
            this.service.setPreference(var1, var4);
         } catch (RemoteException var8) {
            String var6 = "Setting preference failed: " + var2;
            int var7 = Log.e("Z7SettingsManager", var6, var8);
         }
      }
   }
}

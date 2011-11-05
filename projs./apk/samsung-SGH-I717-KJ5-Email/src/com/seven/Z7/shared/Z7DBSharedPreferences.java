package com.seven.Z7.shared;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.android.email.Email;
import com.seven.Z7.provider.Z7Content;
import com.seven.Z7.shared.Z7DBPrefsEditor;
import com.seven.Z7.shared.Z7DBSharedPreferenceCache;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Z7DBSharedPreferences implements SharedPreferences {

   public static final String CATEGORY_ACCOUNT = "Account-";
   public static final String CATEGORY_GLOBAL = "Global";
   public static final String CATEGORY_UI = "UIPrefs";
   public static final String CATEGORY_WHERE = "category=?";
   public static final String KEY_WHERE = "key=?";
   public static final String OLD_CATEGORY_ACCOUNT = "Account.";
   private static Map<String, Z7DBSharedPreferences> dbSharedPreferencesInstances = new HashMap();
   private String category;
   private Uri contentUri;
   private Context context;
   private ReentrantLock listenerLock;
   private List<OnSharedPreferenceChangeListener> listeners;
   private Z7DBSharedPreferenceCache preferenceCache;
   private Z7DBSharedPreferences.PreferenceObserver preferenceObserver;


   public Z7DBSharedPreferences(String var1) {
      this.category = var1;
      Email var2 = Email.getApplication();
      this.context = var2;
      Uri var3 = Uri.withAppendedPath(Z7Content.Z7DBPrefs.CONTENT_URI, var1);
      this.contentUri = var3;
      ReentrantLock var4 = new ReentrantLock();
      this.listenerLock = var4;
   }

   private boolean containsInDB(String param1) {
      // $FF: Couldn't be decompiled
   }

   public static Z7DBSharedPreferences getAccountSharedPreferences(int var0) {
      if(var0 <= 0) {
         String var1 = "Bad account ID for preferences: " + var0;
         throw new InvalidParameterException(var1);
      } else {
         return getDBPrefencesInstance("Account-" + var0);
      }
   }

   private Map<String, Object> getAllFromDB() {
      // $FF: Couldn't be decompiled
   }

   private static Z7DBSharedPreferences getDBPrefencesInstance(String var0) {
      synchronized(Z7DBSharedPreferences.class){}

      Z7DBSharedPreferences var1;
      try {
         var1 = (Z7DBSharedPreferences)dbSharedPreferencesInstances.get(var0);
         if(var1 == null) {
            var1 = new Z7DBSharedPreferences(var0);
            dbSharedPreferencesInstances.put(var0, var1);
         }
      } finally {
         ;
      }

      return var1;
   }

   private Object getFromDB(String param1, int param2, Object param3) {
      // $FF: Couldn't be decompiled
   }

   public static Z7DBSharedPreferences getGlobalSharedPreferences() {
      return getDBPrefencesInstance("Global");
   }

   public static Z7DBSharedPreferences getUISharedPreferences() {
      return getDBPrefencesInstance("UIPrefs");
   }

   private void notifyListeners(List<String> var1) {
      if(this.listeners != null) {
         this.listenerLock.lock();

         try {
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               String var3 = (String)var2.next();
               Iterator var4 = this.listeners.iterator();

               while(var4.hasNext()) {
                  ((OnSharedPreferenceChangeListener)var4.next()).onSharedPreferenceChanged(this, var3);
               }
            }
         } finally {
            this.listenerLock.unlock();
         }

      }
   }

   private void onFirstListenerAdded() {
      Map var1 = this.getAll();
      Z7DBSharedPreferenceCache var2 = new Z7DBSharedPreferenceCache(var1);
      this.preferenceCache = var2;
      this.registerContentObserver();
   }

   private void onLastListenerRemoved() {
      this.unregisterContentObserver();
      this.preferenceCache = null;
   }

   private static Object parseValue(String var0, int var1) {
      Object var2;
      switch(var1) {
      case 1:
         var2 = new Integer(var0);
         break;
      case 2:
         var2 = new Long(var0);
         break;
      case 3:
         var2 = new Float(var0);
         break;
      case 4:
         var2 = new Boolean(var0);
         break;
      case 5:
         var2 = var0;
         break;
      case 6:
         var2 = var0.getBytes();
         break;
      default:
         var2 = false;
      }

      return var2;
   }

   private void registerContentObserver() {
      Looper var1 = Email.getApplication().getMainLooper();
      Handler var2 = new Handler(var1);
      if(this.preferenceObserver == null) {
         Z7DBSharedPreferences.PreferenceObserver var3 = new Z7DBSharedPreferences.PreferenceObserver(var2);
         this.preferenceObserver = var3;
      }

      ContentResolver var4 = this.context.getContentResolver();
      Uri var5 = this.contentUri;
      Z7DBSharedPreferences.PreferenceObserver var6 = this.preferenceObserver;
      var4.registerContentObserver(var5, (boolean)1, var6);
   }

   private void unregisterContentObserver() {
      ContentResolver var1 = this.context.getContentResolver();
      Z7DBSharedPreferences.PreferenceObserver var2 = this.preferenceObserver;
      var1.unregisterContentObserver(var2);
   }

   public boolean contains(String var1) {
      boolean var2;
      if(this.preferenceCache != null) {
         var2 = this.preferenceCache.contains(var1);
      } else {
         var2 = this.containsInDB(var1);
      }

      return var2;
   }

   public Z7DBPrefsEditor edit() {
      String var1 = this.category;
      Context var2 = this.context;
      return new Z7DBPrefsEditor(var1, this, var2);
   }

   public Object get(String var1, int var2, Object var3) {
      Object var4;
      if(this.preferenceCache != null && this.preferenceCache.contains(var1)) {
         var4 = this.preferenceCache.get(var1);
      } else {
         var4 = this.getFromDB(var1, var2, var3);
      }

      return var4;
   }

   public Map<String, Object> getAll() {
      Map var1;
      if(this.preferenceCache != null) {
         var1 = this.preferenceCache.getAll();
      } else {
         var1 = this.getAllFromDB();
      }

      return var1;
   }

   public boolean getBoolean(String var1, boolean var2) {
      Boolean var3 = Boolean.valueOf(var2);
      return ((Boolean)this.get(var1, 4, var3)).booleanValue();
   }

   public int getFlag(String param1) {
      // $FF: Couldn't be decompiled
   }

   public float getFloat(String var1, float var2) {
      Float var3 = Float.valueOf(var2);
      return (float)((Integer)this.get(var1, 3, var3)).intValue();
   }

   public int getInt(String var1, int var2) {
      Integer var3 = Integer.valueOf(var2);
      return ((Integer)this.get(var1, 1, var3)).intValue();
   }

   public long getLong(String var1, long var2) {
      Long var4 = Long.valueOf(var2);
      return ((Long)this.get(var1, 2, var4)).longValue();
   }

   public String getString(String var1, String var2) {
      return (String)this.get(var1, 5, var2);
   }

   public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener param1) {
      // $FF: Couldn't be decompiled
   }

   public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener param1) {
      // $FF: Couldn't be decompiled
   }

   private class PreferenceObserver extends ContentObserver {

      public PreferenceObserver(Handler var2) {
         super(var2);
      }

      public boolean deliverSelfNotifications() {
         return true;
      }

      public void onChange(boolean var1) {
         Z7DBSharedPreferenceCache var2 = Z7DBSharedPreferences.this.preferenceCache;
         Map var3 = Z7DBSharedPreferences.this.getAllFromDB();
         List var4 = var2.refreshCache(var3);
         Z7DBSharedPreferences.this.notifyListeners(var4);
      }
   }
}

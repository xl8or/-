package com.htc.android.mail.eassvc.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class BasePreferences {

   protected static Editor editor;
   protected static SharedPreferences settings;


   public BasePreferences() {}

   public static void load(Context var0) {
      settings = var0.getSharedPreferences("pimSyncPref", 0);
      editor = settings.edit();
   }

   public static boolean save() {
      return editor.commit();
   }
}

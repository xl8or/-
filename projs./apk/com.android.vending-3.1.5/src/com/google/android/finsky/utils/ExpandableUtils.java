package com.google.android.finsky.utils;

import android.os.Bundle;

public class ExpandableUtils {

   public static final int EXPANSION_STATE_COLLAPSED = 1;
   public static final int EXPANSION_STATE_DEFAULT = 0;
   public static final int EXPANSION_STATE_EXPANDED = 2;
   private static final String KEY_EXPANSION_STATE = "expansion_state";


   public ExpandableUtils() {}

   public static int getSavedExpansionState(Bundle var0, String var1) {
      int var2 = 0;
      if(var0 != null) {
         String var3 = "expansion_state:" + var1;
         if(var0.containsKey(var3)) {
            var2 = var0.getInt(var3);
         }
      }

      return var2;
   }

   public static void saveExpansionState(Bundle var0, String var1, int var2) {
      String var3 = "expansion_state:" + var1;
      var0.putInt(var3, var2);
   }
}

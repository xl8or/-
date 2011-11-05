package org.acra;

import android.content.res.Configuration;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.util.HashMap;

public class ConfigurationInspector {

   private static final String FIELD_MCC = "mcc";
   private static final String FIELD_MNC = "mnc";
   private static final String FIELD_SCREENLAYOUT = "screenLayout";
   private static final String FIELD_UIMODE = "uiMode";
   private static final String PREFIX_HARDKEYBOARDHIDDEN = "HARDKEYBOARDHIDDEN_";
   private static final String PREFIX_KEYBOARD = "KEYBOARD_";
   private static final String PREFIX_KEYBOARDHIDDEN = "KEYBOARDHIDDEN_";
   private static final String PREFIX_NAVIGATION = "NAVIGATION_";
   private static final String PREFIX_NAVIGATIONHIDDEN = "NAVIGATIONHIDDEN_";
   private static final String PREFIX_ORIENTATION = "ORIENTATION_";
   private static final String PREFIX_SCREENLAYOUT = "SCREENLAYOUT_";
   private static final String PREFIX_TOUCHSCREEN = "TOUCHSCREEN_";
   private static final String PREFIX_UI_MODE = "UI_MODE_";
   private static final String SUFFIX_MASK = "_MASK";
   private static SparseArray<String> mHardKeyboardHiddenValues;
   private static SparseArray<String> mKeyboardHiddenValues;
   private static SparseArray<String> mKeyboardValues;
   private static SparseArray<String> mNavigationHiddenValues;
   private static SparseArray<String> mNavigationValues;
   private static SparseArray<String> mOrientationValues;
   private static SparseArray<String> mScreenLayoutValues;
   private static SparseArray<String> mTouchScreenValues;
   private static SparseArray<String> mUiModeValues;
   private static final HashMap<String, SparseArray<String>> mValueArrays;


   static {
      // $FF: Couldn't be decompiled
   }

   public ConfigurationInspector() {}

   private static String activeFlags(SparseArray<String> var0, int var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = 0;

      while(true) {
         int var4 = var0.size();
         if(var3 >= var4) {
            return var2.toString();
         }

         int var5 = var0.keyAt(var3);
         if(((String)var0.get(var5)).endsWith("_MASK")) {
            int var6 = var1 & var5;
            if(var6 > 0) {
               if(var2.length() > 0) {
                  StringBuilder var7 = var2.append('+');
               }

               String var8 = (String)var0.get(var6);
               var2.append(var8);
            }
         }

         ++var3;
      }
   }

   private static String getFieldValueName(Configuration var0, Field var1) throws IllegalArgumentException, IllegalAccessException {
      String var2 = var1.getName();
      String var3;
      if(!var2.equals("mcc") && !var2.equals("mnc")) {
         if(var2.equals("uiMode")) {
            SparseArray var4 = (SparseArray)mValueArrays.get("UI_MODE_");
            int var5 = var1.getInt(var0);
            var3 = activeFlags(var4, var5);
         } else if(var2.equals("screenLayout")) {
            SparseArray var6 = (SparseArray)mValueArrays.get("SCREENLAYOUT_");
            int var7 = var1.getInt(var0);
            var3 = activeFlags(var6, var7);
         } else {
            HashMap var8 = mValueArrays;
            StringBuilder var9 = new StringBuilder();
            String var10 = var2.toUpperCase();
            String var11 = var9.append(var10).append('_').toString();
            SparseArray var12 = (SparseArray)var8.get(var11);
            if(var12 == null) {
               var3 = Integer.toString(var1.getInt(var0));
            } else {
               int var13 = var1.getInt(var0);
               String var14 = (String)var12.get(var13);
               if(var14 == null) {
                  var3 = Integer.toString(var1.getInt(var0));
               } else {
                  var3 = var14;
               }
            }
         }
      } else {
         var3 = Integer.toString(var1.getInt(var0));
      }

      return var3;
   }

   public static String toString(Configuration param0) {
      // $FF: Couldn't be decompiled
   }
}

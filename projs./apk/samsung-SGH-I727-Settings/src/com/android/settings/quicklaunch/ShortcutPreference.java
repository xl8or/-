package com.android.settings.quicklaunch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.preference.Preference;
import android.view.View;

public class ShortcutPreference extends Preference implements Comparable<Preference> {

   private static String STRING_ASSIGN_APPLICATION;
   private static String STRING_NO_SHORTCUT;
   private static int sDimAlpha;
   private static ColorStateList sDimSummaryColor;
   private static ColorStateList sDimTitleColor;
   private static ColorStateList sRegularSummaryColor;
   private static ColorStateList sRegularTitleColor;
   private static Object sStaticVarsLock = new Object();
   private boolean mHasBookmark;
   private char mShortcut;


   public ShortcutPreference(Context param1, char param2) {
      // $FF: Couldn't be decompiled
   }

   public int compareTo(Preference var1) {
      int var2;
      if(!(var1 instanceof ShortcutPreference)) {
         var2 = super.compareTo(var1);
      } else {
         char var3 = ((ShortcutPreference)var1).mShortcut;
         if(Character.isDigit(this.mShortcut) && Character.isLetter(var3)) {
            var2 = 1;
         } else if(Character.isDigit(var3) && Character.isLetter(this.mShortcut)) {
            var2 = -1;
         } else {
            var2 = this.mShortcut - var3;
         }
      }

      return var2;
   }

   public char getShortcut() {
      return this.mShortcut;
   }

   public CharSequence getSummary() {
      Object var1;
      if(this.mHasBookmark) {
         var1 = super.getSummary();
      } else {
         var1 = STRING_NO_SHORTCUT;
      }

      return (CharSequence)var1;
   }

   public CharSequence getTitle() {
      Object var1;
      if(this.mHasBookmark) {
         var1 = super.getTitle();
      } else {
         var1 = STRING_ASSIGN_APPLICATION;
      }

      return (CharSequence)var1;
   }

   public boolean hasBookmark() {
      return this.mHasBookmark;
   }

   protected void onBindView(View param1) {
      // $FF: Couldn't be decompiled
   }

   public void setHasBookmark(boolean var1) {
      boolean var2 = this.mHasBookmark;
      if(var1 != var2) {
         this.mHasBookmark = var1;
         this.notifyChanged();
      }
   }

   public void setShortcut(char var1) {
      char var2 = this.mShortcut;
      if(var1 != var2) {
         this.mShortcut = var1;
         this.notifyChanged();
      }
   }
}

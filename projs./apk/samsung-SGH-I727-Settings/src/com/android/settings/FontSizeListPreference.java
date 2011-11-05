package com.android.settings;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.ListPreference;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.util.Log;
import com.android.settings.FontSizeListAdapter;
import java.util.Vector;

public class FontSizeListPreference extends ListPreference {

   public static final String TAG = "FontSizeListPreference";
   private Context context;
   private FontSizeListAdapter mFontSizeListAdapter;
   private int mSelectedFontSize;


   public FontSizeListPreference(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public FontSizeListPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.context = null;
      this.mFontSizeListAdapter = null;
      this.mSelectedFontSize = -1;
      this.context = var1;
      this.setTitle(2131232468);
      this.setSummary(2131232469);
      this.setDialogTitle(2131232468);
      this.setNegativeButtonText(17039360);
   }

   protected void onPrepareDialogBuilder(Builder var1) {
      if(this.mFontSizeListAdapter == null) {
         Context var2 = this.context;
         FontSizeListAdapter var3 = new FontSizeListAdapter(var2);
         this.mFontSizeListAdapter = var3;
      }

      Vector var4 = this.mFontSizeListAdapter.mFontSizeNames;
      String[] var5 = new String[this.mFontSizeListAdapter.mFontSizeNames.size()];
      String[] var6 = (String[])((String[])var4.toArray(var5));
      this.setEntries(var6);
      Vector var7 = this.mFontSizeListAdapter.mFontSizeIndexes;
      String[] var8 = new String[this.mFontSizeListAdapter.mFontSizeIndexes.size()];
      String[] var9 = (String[])((String[])var7.toArray(var8));
      this.setEntryValues(var9);
      int var10 = System.getInt(this.context.getContentResolver(), "font_size", 2);
      this.mSelectedFontSize = var10;
      StringBuilder var11 = (new StringBuilder()).append("onPrepareDialogBuilder : Selected item : ");
      int var12 = this.mSelectedFontSize;
      String var13 = var11.append(var12).toString();
      int var14 = Log.d("FontSizeListPreference", var13);
      FontSizeListAdapter var15 = this.mFontSizeListAdapter;
      int var16 = this.mSelectedFontSize;
      var1.setSingleChoiceItems(var15, var16, this);
      super.onPrepareDialogBuilder(var1);
   }
}

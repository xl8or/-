package com.android.settings;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Vector;

public class FontSizeListAdapter extends BaseAdapter {

   private static final float HUGE_FONT_SIZE = 24.5F;
   private static final float LARGE_FONT_SIZE = 22.0F;
   private static final float NORMAL_FONT_SIZE = 18.0F;
   private static final float SMALL_FONT_SIZE = 15.0F;
   private static final String TAG = "FontSizeListAdapter";
   private static final float TINY_FONT_SIZE = 12.0F;
   Context context = null;
   public Vector mFontSizeIndexes;
   public Vector mFontSizeNames;
   LayoutInflater mInflater = null;


   FontSizeListAdapter(Context var1) {
      Vector var2 = new Vector();
      this.mFontSizeNames = var2;
      Vector var3 = new Vector();
      this.mFontSizeIndexes = var3;
      this.context = var1;
      LayoutInflater var4 = (LayoutInflater)var1.getSystemService("layout_inflater");
      this.mInflater = var4;
      Vector var5 = this.mFontSizeNames;
      String var6 = var1.getResources().getString(2131232470);
      var5.add(var6);
      boolean var8 = this.mFontSizeIndexes.add("0");
      Vector var9 = this.mFontSizeNames;
      String var10 = var1.getResources().getString(2131232471);
      var9.add(var10);
      boolean var12 = this.mFontSizeIndexes.add("1");
      Vector var13 = this.mFontSizeNames;
      String var14 = var1.getResources().getString(2131232472);
      var13.add(var14);
      boolean var16 = this.mFontSizeIndexes.add("2");
      Vector var17 = this.mFontSizeNames;
      String var18 = var1.getResources().getString(2131232473);
      var17.add(var18);
      boolean var20 = this.mFontSizeIndexes.add("3");
      Vector var21 = this.mFontSizeNames;
      String var22 = var1.getResources().getString(2131232474);
      var21.add(var22);
      boolean var24 = this.mFontSizeIndexes.add("4");
   }

   public int getCount() {
      return this.mFontSizeNames.size();
   }

   public Object getItem(int var1) {
      return this.mFontSizeNames.elementAt(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      TextView var4;
      if(var2 == null) {
         var4 = (TextView)this.mInflater.inflate(17367055, (ViewGroup)null);
      } else {
         var4 = (TextView)var2;
      }

      var4.setTextColor(-16777216);
      WindowManager var5 = (WindowManager)this.context.getSystemService("window");
      DisplayMetrics var6 = new DisplayMetrics();
      var5.getDefaultDisplay().getMetrics(var6);
      int var7 = var6.densityDpi;
      float var9;
      switch(var1) {
      case 0:
         String var12 = this.context.getResources().getString(2131232470);
         var4.setText(var12);
         var9 = 24.5F;
         break;
      case 1:
         String var13 = this.context.getResources().getString(2131232471);
         var4.setText(var13);
         var9 = 22.0F;
         break;
      case 2:
      default:
         String var8 = this.context.getResources().getString(2131232472);
         var4.setText(var8);
         var9 = 18.0F;
         break;
      case 3:
         String var14 = this.context.getResources().getString(2131232473);
         var4.setText(var14);
         var9 = 15.0F;
         break;
      case 4:
         String var15 = this.context.getResources().getString(2131232474);
         var4.setText(var15);
         var9 = 12.0F;
      }

      var4.setTextSize(var9);
      float var10 = (float)var7 / 160.0F;
      int var11 = (int)(65.0F * var10);
      var4.setHeight(var11);
      return var4;
   }
}

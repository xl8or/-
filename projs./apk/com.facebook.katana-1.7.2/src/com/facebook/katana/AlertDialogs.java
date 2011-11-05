package com.facebook.katana;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class AlertDialogs {

   public AlertDialogs() {}

   public static AlertDialog createAlert(Context var0, String var1, int var2, String var3, String var4, OnClickListener var5, String var6, OnClickListener var7, OnCancelListener var8, boolean var9) {
      Builder var10 = new Builder(var0);
      var10.setTitle(var1);
      if(var2 != 0) {
         Drawable var12 = var0.getResources().getDrawable(var2);
         var10.setIcon(var12);
      }

      var10.setMessage(var3);
      var10.setPositiveButton(var4, var5);
      var10.setNegativeButton(var6, var7);
      var10.setOnCancelListener(var8);
      var10.setCancelable(var9);
      return var10.create();
   }

   public static AlertDialog showSubMenu(Context var0, String var1, String[] var2, int[] var3, OnClickListener var4, OnCancelListener var5) {
      Builder var6 = new Builder(var0);
      if(var1 != null) {
         var6.setTitle(var1);
      }

      AlertDialogs.1 var8 = new AlertDialogs.1(var2, var0, var3);
      var6.setAdapter(var8, var4);
      if(var5 != null) {
         var6.setOnCancelListener(var5);
      }

      return var6.create();
   }

   static class 1 implements ListAdapter {

      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final int[] val$icons;
      // $FF: synthetic field
      final String[] val$texts;


      1(String[] var1, Context var2, int[] var3) {
         this.val$texts = var1;
         this.val$context = var2;
         this.val$icons = var3;
      }

      public boolean areAllItemsEnabled() {
         return true;
      }

      public int getCount() {
         return this.val$texts.length;
      }

      public Object getItem(int var1) {
         return Integer.valueOf(var1);
      }

      public long getItemId(int var1) {
         return (long)var1;
      }

      public int getItemViewType(int var1) {
         return 0;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         View var4;
         if(var2 == null) {
            var4 = ((LayoutInflater)this.val$context.getSystemService("layout_inflater")).inflate(2130903099, (ViewGroup)null);
         } else {
            var4 = var2;
         }

         if(this.val$icons != null) {
            ImageView var5 = (ImageView)var4.findViewById(2131624110);
            int var6 = this.val$icons[var1];
            var5.setImageResource(var6);
         } else {
            ((ImageView)var4.findViewById(2131624110)).setImageBitmap((Bitmap)null);
         }

         TextView var7 = (TextView)var4.findViewById(2131624111);
         String var8 = this.val$texts[var1];
         var7.setText(var8);
         return var4;
      }

      public int getViewTypeCount() {
         return 1;
      }

      public boolean hasStableIds() {
         return true;
      }

      public boolean isEmpty() {
         return false;
      }

      public boolean isEnabled(int var1) {
         return true;
      }

      public void registerDataSetObserver(DataSetObserver var1) {}

      public void unregisterDataSetObserver(DataSetObserver var1) {}
   }
}

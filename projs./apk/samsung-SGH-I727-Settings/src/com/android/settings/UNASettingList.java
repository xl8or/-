package com.android.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UNASettingList extends ListActivity {

   private static final String TAG = "UNASetting";
   private static final String UNA_SETTING = "una_setting";
   private static final String[] mUNAItems = new String[3];
   private Dialog mChargingDialog;


   public UNASettingList() {}

   private void showChargingDialog(int var1) {
      if(this.mChargingDialog != null) {
         this.mChargingDialog.dismiss();
      }

      Builder var2 = (new Builder(this)).setTitle(2131232102).setIcon(17301543);
      String var3 = this.getString(2131232101);
      Builder var4 = var2.setMessage(var3);
      UNASettingList.3 var5 = new UNASettingList.3();
      Builder var6 = var4.setNegativeButton(2131231633, var5);
      UNASettingList.2 var7 = new UNASettingList.2(var1);
      Builder var8 = var6.setPositiveButton(2131231632, var7);
      UNASettingList.1 var9 = new UNASettingList.1();
      AlertDialog var10 = var8.setOnKeyListener(var9).show();
      this.mChargingDialog = var10;
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      int var2 = System.getInt(this.getContentResolver(), "una_setting", 1);
      String var3 = "onCreate : Settings.System.UNA_SETTING : " + var2;
      int var4 = Log.d("UNASetting", var3);
      String[] var5 = mUNAItems;
      String var6 = this.getResources().getString(2131232098);
      var5[0] = var6;
      String[] var7 = mUNAItems;
      String var8 = this.getResources().getString(2131232099);
      var7[1] = var8;
      String[] var9 = mUNAItems;
      String var10 = this.getResources().getString(2131232100);
      var9[2] = var10;
      ListView var11 = this.getListView();
      View var12 = this.getLayoutInflater().inflate(2130903133, (ViewGroup)null);
      var11.addHeaderView(var12, (Object)null, (boolean)0);
      String[] var13 = mUNAItems;
      ArrayAdapter var14 = new ArrayAdapter(this, 17367055, var13);
      var11.setAdapter(var14);
      var11.setItemsCanFocus((boolean)0);
      var11.setChoiceMode(1);
      int var15 = var2 + 1;
      var11.setItemChecked(var15, (boolean)1);
   }

   protected void onListItemClick(ListView var1, View var2, int var3, long var4) {
      switch(var3) {
      case 1:
      case 2:
         ContentResolver var6 = this.getContentResolver();
         int var7 = var3 - 1;
         System.putInt(var6, "una_setting", var7);
         String var9 = "position : " + var3;
         int var10 = Log.d("UNASetting", var9);
         this.finish();
         return;
      case 3:
         this.showChargingDialog(var3);
         return;
      default:
      }
   }

   class 1 implements OnKeyListener {

      1() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         switch(var2) {
         case 4:
            int var6 = System.getInt(UNASettingList.this.getContentResolver(), "una_setting", 1);
            ListView var7 = UNASettingList.this.getListView();
            int var8 = var6 + 1;
            var7.setItemChecked(var8, (boolean)1);
            var1.dismiss();
            break;
         default:
            String var4 = var3.toString();
            int var5 = Log.d("UNASetting", var4);
         }

         return true;
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         int var3 = System.getInt(UNASettingList.this.getContentResolver(), "una_setting", 1);
         ListView var4 = UNASettingList.this.getListView();
         int var5 = var3 + 1;
         var4.setItemChecked(var5, (boolean)1);
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final int val$position;


      2(int var2) {
         this.val$position = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         ContentResolver var3 = UNASettingList.this.getContentResolver();
         int var4 = this.val$position - 1;
         System.putInt(var3, "una_setting", var4);
         StringBuilder var6 = (new StringBuilder()).append("position : ");
         int var7 = this.val$position;
         String var8 = var6.append(var7).toString();
         int var9 = Log.d("UNASetting", var8);
         UNASettingList.this.finish();
      }
   }
}

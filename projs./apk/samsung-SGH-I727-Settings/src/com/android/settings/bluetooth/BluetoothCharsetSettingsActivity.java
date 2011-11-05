package com.android.settings.bluetooth;

import android.app.ListActivity;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class BluetoothCharsetSettingsActivity extends ListActivity {

   private static final String TAG = "BluetoothCharsetSettingsActivity";
   private TextView helpTextView;
   private TextView titleView;
   private View viewForHelp;


   public BluetoothCharsetSettingsActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      ArrayList var2 = new ArrayList();
      boolean var3 = var2.add("UTF-8");
      boolean var4 = var2.add("EUC-KR");
      ArrayAdapter var5 = new ArrayAdapter(this, 17367055, var2);
      ListView var6 = this.getListView();
      var6.setChoiceMode(1);
      int var7 = System.getInt(this.getContentResolver(), "characterset", 0);
      LinearLayout var8 = (LinearLayout)((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2130903109, (ViewGroup)null);
      this.viewForHelp = var8;
      TextView var9 = (TextView)this.viewForHelp.findViewById(2131427558);
      this.titleView = var9;
      TextView var10 = (TextView)this.viewForHelp.findViewById(16842755);
      this.helpTextView = var10;
      this.titleView.setText(2131232263);
      View var11 = this.viewForHelp;
      var6.addFooterView(var11, (Object)null, (boolean)0);
      var6.setAdapter(var5);
      String var12 = "onCreate, postion: " + var7;
      int var13 = Log.d("BluetoothCharsetSettingsActivity", var12);
      var6.setItemChecked(var7, (boolean)1);
   }

   protected void onListItemClick(ListView var1, View var2, int var3, long var4) {
      boolean var6 = System.putInt(this.getContentResolver(), "characterset", var3);
      String var7 = "onListItemClick, postion: " + var3;
      int var8 = Log.e("BluetoothCharsetSettingsActivity", var7);
   }
}

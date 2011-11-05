package com.android.settings;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.sec.android.hardware.SecHardwareInterface;

public class ModePreview extends Activity implements OnItemClickListener {

   private static final String[] mModeItem = new String[3];
   private ListView mListView;


   public ModePreview() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903097);
      String[] var2 = mModeItem;
      String var3 = this.getResources().getString(2131232238);
      var2[0] = var3;
      String[] var4 = mModeItem;
      String var5 = this.getResources().getString(2131232239);
      var4[1] = var5;
      String[] var6 = mModeItem;
      String var7 = this.getResources().getString(2131232240);
      var6[2] = var7;
      ListView var8 = (ListView)this.findViewById(2131427502);
      this.mListView = var8;
      ListView var9 = this.mListView;
      String[] var10 = mModeItem;
      ArrayAdapter var11 = new ArrayAdapter(this, 17367055, var10);
      var9.setAdapter(var11);
      this.mListView.setItemsCanFocus((boolean)0);
      ListView var12 = this.mListView;
      int var13 = System.getInt(this.getContentResolver(), "screen_mode_setting", 1);
      var12.setItemChecked(var13, (boolean)1);
      this.mListView.setOnItemClickListener(this);
      Button var14 = (Button)this.findViewById(2131427480);
      ModePreview.1 var15 = new ModePreview.1();
      var14.setOnClickListener(var15);
      Button var16 = (Button)this.findViewById(2131427481);
      ModePreview.2 var17 = new ModePreview.2();
      var16.setOnClickListener(var17);
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      try {
         SecHardwareInterface.setmDNIeUserMode(var3);
      } catch (Exception var8) {
         int var7 = Log.e("PreviewMode", "could not persist mode setting", var8);
      }
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if(var1 == 4) {
         try {
            SecHardwareInterface.setmDNIeUserMode(System.getInt(this.getContentResolver(), "screen_mode_setting", 1));
         } catch (Exception var5) {
            int var4 = Log.e("PreviewMode", "could not change mode setting", var5);
         }
      }

      return super.onKeyDown(var1, var2);
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         ContentResolver var2 = ModePreview.this.getContentResolver();
         int var3 = ModePreview.this.mListView.getCheckedItemPosition();
         System.putInt(var2, "screen_mode_setting", var3);
         ModePreview.this.finish();
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         try {
            SecHardwareInterface.setmDNIeUserMode(System.getInt(ModePreview.this.getContentResolver(), "screen_mode_setting", 1));
         } catch (Exception var4) {
            int var3 = Log.e("PreviewMode", "could not change mode setting", var4);
         }

         ModePreview.this.finish();
      }
   }
}

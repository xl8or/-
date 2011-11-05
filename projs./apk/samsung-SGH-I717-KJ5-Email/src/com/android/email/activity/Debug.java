package com.android.email.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.android.email.Controller;
import com.android.email.Email;
import com.android.email.Preferences;
import com.android.exchange.Eas;
import com.android.exchange.utility.FileLogger;

public class Debug extends Activity implements OnCheckedChangeListener {

   private CheckBox mEnableDebugLoggingView;
   private CheckBox mEnableExchangeFileLoggingView;
   private CheckBox mEnableExchangeLoggingView;
   private CheckBox mEnableSensitiveLoggingView;
   private Preferences mPreferences;
   private TextView mVersionView;


   public Debug() {}

   public static void updateLoggingFlags(Context var0) {
      Preferences var1 = Preferences.getPreferences(var0);
      byte var2;
      if(var1.getEnableDebugLogging()) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      byte var3;
      if(var1.getEnableExchangeLogging()) {
         var3 = 2;
      } else {
         var3 = 0;
      }

      byte var4;
      if(var1.getEnableExchangeFileLogging()) {
         var4 = 4;
      } else {
         var4 = 0;
      }

      int var5 = var2 | var3 | var4;
      Controller.getInstance(var0).serviceLogging(var5);
   }

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      switch(var1.getId()) {
      case 2131362041:
         Email.DEBUG = var2;
         Preferences var7 = this.mPreferences;
         boolean var8 = Email.DEBUG;
         var7.setEnableDebugLogging(var8);
         break;
      case 2131362042:
         Email.DEBUG_SENSITIVE = var2;
         Preferences var9 = this.mPreferences;
         boolean var10 = Email.DEBUG_SENSITIVE;
         var9.setEnableSensitiveLogging(var10);
         break;
      case 2131362043:
         this.mPreferences.setEnableExchangeLogging(var2);
         break;
      case 2131362044:
         this.mPreferences.setEnableExchangeFileLogging(var2);
         if(!var2) {
            FileLogger.close();
         }
      }

      updateLoggingFlags(this);
      byte var3;
      if(this.mPreferences.getEnableDebugLogging()) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      byte var4;
      if(this.mPreferences.getEnableExchangeLogging()) {
         var4 = 2;
      } else {
         var4 = 0;
      }

      byte var5;
      if(this.mPreferences.getEnableExchangeFileLogging()) {
         var5 = 4;
      } else {
         var5 = 0;
      }

      int var6 = var3 | var4 | var5;
      Controller.getInstance(this.getApplication()).serviceLogging(var6);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903082);
      Preferences var2 = Preferences.getPreferences(this);
      this.mPreferences = var2;
      TextView var3 = (TextView)this.findViewById(2131362040);
      this.mVersionView = var3;
      CheckBox var4 = (CheckBox)this.findViewById(2131362041);
      this.mEnableDebugLoggingView = var4;
      CheckBox var5 = (CheckBox)this.findViewById(2131362042);
      this.mEnableSensitiveLoggingView = var5;
      CheckBox var6 = (CheckBox)this.findViewById(2131362043);
      this.mEnableExchangeLoggingView = var6;
      CheckBox var7 = (CheckBox)this.findViewById(2131362044);
      this.mEnableExchangeFileLoggingView = var7;
      this.mEnableDebugLoggingView.setOnCheckedChangeListener(this);
      this.mEnableSensitiveLoggingView.setOnCheckedChangeListener(this);
      this.mEnableExchangeLoggingView.setOnCheckedChangeListener(this);
      this.mEnableExchangeFileLoggingView.setOnCheckedChangeListener(this);
      TextView var8 = this.mVersionView;
      String var9 = this.getString(2131167099).toString();
      Object[] var10 = new Object[1];
      String var11 = this.getString(2131165428);
      var10[0] = var11;
      String var12 = String.format(var9, var10);
      var8.setText(var12);
      CheckBox var13 = this.mEnableDebugLoggingView;
      boolean var14 = Email.DEBUG;
      var13.setChecked(var14);
      CheckBox var15 = this.mEnableSensitiveLoggingView;
      boolean var16 = Email.DEBUG_SENSITIVE;
      var15.setChecked(var16);
      CheckBox var17 = (CheckBox)this.findViewById(2131362043);
      this.mEnableExchangeLoggingView = var17;
      CheckBox var18 = (CheckBox)this.findViewById(2131362044);
      this.mEnableExchangeFileLoggingView = var18;
      this.mEnableExchangeLoggingView.setOnCheckedChangeListener(this);
      this.mEnableExchangeFileLoggingView.setOnCheckedChangeListener(this);
      CheckBox var19 = this.mEnableExchangeLoggingView;
      boolean var20 = Eas.USER_LOG;
      var19.setChecked(var20);
      CheckBox var21 = this.mEnableExchangeFileLoggingView;
      boolean var22 = Eas.FILE_LOG;
      var21.setChecked(var22);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      this.getMenuInflater().inflate(2131689478, var1);
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      byte var2;
      if(var1.getItemId() == 2131362547) {
         Preferences.getPreferences(this).dump();
         var2 = 1;
      } else {
         var2 = super.onOptionsItemSelected(var1);
      }

      return (boolean)var2;
   }
}

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
      case 2131558459:
         Email.DEBUG = var2;
         Preferences var3 = this.mPreferences;
         boolean var4 = Email.DEBUG;
         var3.setEnableDebugLogging(var4);
         break;
      case 2131558460:
         Email.DEBUG_SENSITIVE = var2;
         Preferences var5 = this.mPreferences;
         boolean var6 = Email.DEBUG_SENSITIVE;
         var5.setEnableSensitiveLogging(var6);
         break;
      case 2131558461:
         this.mPreferences.setEnableExchangeLogging(var2);
         break;
      case 2131558462:
         this.mPreferences.setEnableExchangeFileLogging(var2);
         if(!var2) {
            FileLogger.close();
         }
      }

      updateLoggingFlags(this);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903056);
      Preferences var2 = Preferences.getPreferences(this);
      this.mPreferences = var2;
      TextView var3 = (TextView)this.findViewById(2131558458);
      this.mVersionView = var3;
      CheckBox var4 = (CheckBox)this.findViewById(2131558459);
      this.mEnableDebugLoggingView = var4;
      CheckBox var5 = (CheckBox)this.findViewById(2131558460);
      this.mEnableSensitiveLoggingView = var5;
      TextView var6 = this.mVersionView;
      String var7 = this.getString(2131165264).toString();
      Object[] var8 = new Object[1];
      String var9 = this.getString(2131165184);
      var8[0] = var9;
      String var10 = String.format(var7, var8);
      var6.setText(var10);
      CheckBox var11 = this.mEnableDebugLoggingView;
      boolean var12 = Email.DEBUG;
      var11.setChecked(var12);
      CheckBox var13 = this.mEnableSensitiveLoggingView;
      boolean var14 = Email.DEBUG_SENSITIVE;
      var13.setChecked(var14);
      CheckBox var15 = (CheckBox)this.findViewById(2131558461);
      this.mEnableExchangeLoggingView = var15;
      CheckBox var16 = (CheckBox)this.findViewById(2131558462);
      this.mEnableExchangeFileLoggingView = var16;
      CheckBox var17 = this.mEnableExchangeLoggingView;
      boolean var18 = Eas.PARSER_LOG;
      var17.setChecked(var18);
      CheckBox var19 = this.mEnableExchangeFileLoggingView;
      boolean var20 = Eas.FILE_LOG;
      var19.setChecked(var20);
      this.mEnableDebugLoggingView.setOnCheckedChangeListener(this);
      this.mEnableSensitiveLoggingView.setOnCheckedChangeListener(this);
      this.mEnableExchangeLoggingView.setOnCheckedChangeListener(this);
      this.mEnableExchangeFileLoggingView.setOnCheckedChangeListener(this);
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      this.getMenuInflater().inflate(2131492867, var1);
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      byte var2;
      if(var1.getItemId() == 2131558538) {
         Preferences.getPreferences(this).dump();
         var2 = 1;
      } else {
         var2 = super.onOptionsItemSelected(var1);
      }

      return (boolean)var2;
   }
}

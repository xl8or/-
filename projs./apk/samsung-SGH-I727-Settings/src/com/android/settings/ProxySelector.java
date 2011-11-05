package com.android.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Proxy;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Pattern;

public class ProxySelector extends Activity {

   private static final Pattern HOSTNAME_PATTERN = Pattern.compile("^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$");
   private static final String HOSTNAME_REGEXP = "^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$";
   private static final String LOGTAG = "Settings";
   OnClickListener mClearHandler;
   OnClickListener mDefaultHandler;
   EditText mHostnameField;
   Button mOKButton;
   OnClickListener mOKHandler;
   OnFocusChangeListener mOnFocusChangeHandler;
   EditText mPortField;


   public ProxySelector() {
      ProxySelector.1 var1 = new ProxySelector.1();
      this.mOKHandler = var1;
      ProxySelector.2 var2 = new ProxySelector.2();
      this.mClearHandler = var2;
      ProxySelector.3 var3 = new ProxySelector.3();
      this.mDefaultHandler = var3;
      ProxySelector.4 var4 = new ProxySelector.4();
      this.mOnFocusChangeHandler = var4;
   }

   void initView() {
      EditText var1 = (EditText)this.findViewById(2131427579);
      this.mHostnameField = var1;
      EditText var2 = this.mHostnameField;
      OnFocusChangeListener var3 = this.mOnFocusChangeHandler;
      var2.setOnFocusChangeListener(var3);
      EditText var4 = (EditText)this.findViewById(2131427580);
      this.mPortField = var4;
      EditText var5 = this.mPortField;
      OnClickListener var6 = this.mOKHandler;
      var5.setOnClickListener(var6);
      EditText var7 = this.mPortField;
      OnFocusChangeListener var8 = this.mOnFocusChangeHandler;
      var7.setOnFocusChangeListener(var8);
      Button var9 = (Button)this.findViewById(2131427581);
      this.mOKButton = var9;
      Button var10 = this.mOKButton;
      OnClickListener var11 = this.mOKHandler;
      var10.setOnClickListener(var11);
      Button var12 = (Button)this.findViewById(2131427582);
      OnClickListener var13 = this.mClearHandler;
      var12.setOnClickListener(var13);
      Button var14 = (Button)this.findViewById(2131427583);
      OnClickListener var15 = this.mDefaultHandler;
      var14.setOnClickListener(var15);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903120);
      this.initView();
      this.populateFields((boolean)0);
   }

   void populateFields(boolean var1) {
      String var2;
      int var3;
      if(var1) {
         var2 = Proxy.getDefaultHost();
         var3 = Proxy.getDefaultPort();
      } else {
         var2 = Proxy.getHost(this);
         var3 = Proxy.getPort(this);
      }

      if(var2 == null) {
         var2 = "";
      }

      this.mHostnameField.setText(var2);
      String var4;
      if(var3 == -1) {
         var4 = "";
      } else {
         var4 = Integer.toString(var3);
      }

      this.mPortField.setText(var4);
      Intent var5 = this.getIntent();
      String var6 = var5.getStringExtra("button-label");
      if(!TextUtils.isEmpty(var6)) {
         this.mOKButton.setText(var6);
      }

      String var7 = var5.getStringExtra("title");
      if(!TextUtils.isEmpty(var7)) {
         this.setTitle(var7);
      }
   }

   boolean saveToDb() {
      String var1 = this.mHostnameField.getText().toString().trim();
      String var2 = this.mPortField.getText().toString().trim();
      int var3 = -1;
      int var4 = this.validate(var1, var2);
      boolean var5;
      if(var4 > 0) {
         this.showError(var4);
         var5 = false;
      } else {
         if(var2.length() > 0) {
            int var6;
            try {
               var6 = Integer.parseInt(var2);
            } catch (NumberFormatException var13) {
               var5 = false;
               return var5;
            }

            var3 = var6;
         }

         ContentResolver var7 = this.getContentResolver();
         String var8 = Proxy.getDefaultHost();
         if(var1.equals(var8)) {
            int var9 = Proxy.getDefaultPort();
            if(var3 == var9) {
               var1 = null;
            }
         }

         if(!TextUtils.isEmpty(var1)) {
            var1 = var1 + ':' + var2;
         }

         Secure.putString(var7, "http_proxy", var1);
         Intent var11 = new Intent("android.intent.action.PROXY_CHANGE");
         this.sendBroadcast(var11);
         var5 = true;
      }

      return var5;
   }

   protected void showError(int var1) {
      AlertDialog var2 = (new Builder(this)).setTitle(2131230851).setMessage(var1).setPositiveButton(2131230852, (android.content.DialogInterface.OnClickListener)null).show();
   }

   int validate(String var1, String var2) {
      int var3;
      if(!HOSTNAME_PATTERN.matcher(var1).matches()) {
         var3 = 2131230853;
      } else if(var1.length() > 0 && var2.length() == 0) {
         var3 = 2131230854;
      } else {
         if(var2.length() > 0) {
            if(var1.length() == 0) {
               var3 = 2131230855;
               return var3;
            }

            int var4;
            try {
               var4 = Integer.parseInt(var2);
            } catch (NumberFormatException var7) {
               var3 = 2131230856;
               return var3;
            }

            if(var4 <= 0 || var4 > '\uffff') {
               var3 = 2131230856;
               return var3;
            }
         }

         var3 = 0;
      }

      return var3;
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         ProxySelector.this.populateFields((boolean)1);
      }
   }

   class 4 implements OnFocusChangeListener {

      4() {}

      public void onFocusChange(View var1, boolean var2) {
         if(var2) {
            Selection.selectAll((Spannable)((TextView)var1).getText());
         }
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         if(ProxySelector.this.saveToDb()) {
            ProxySelector.this.finish();
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         ProxySelector.this.mHostnameField.setText("");
         ProxySelector.this.mPortField.setText("");
      }
   }
}

package com.android.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DialerKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

public class Password extends Activity implements OnClickListener, OnLongClickListener, OnKeyListener, TextWatcher {

   private static final int MAX_PASSWORD_LENGTH = 8;
   private static final int MIN_PASSWORD_LENGTH = 4;
   private TextView mDigits;
   private TextView mHeadrText;
   private String mPassword;
   private String mSubject;
   private String mTitle;


   public Password() {}

   private void keyPressed(int var1) {
      if(var1 == 67 || this.mDigits.getText().toString().length() < 8) {
         KeyEvent var2 = new KeyEvent(0, var1);
         this.mDigits.onKeyDown(var1, var2);
      }
   }

   private boolean match(String var1) {
      boolean var4;
      if(this.reasonable(var1)) {
         if(var1.contains("*")) {
            String var2 = this.mPassword;
            if("****".equals(var2)) {
               String var3 = this.getResources().getString(2131232182);
               this.showAlert(var3);
            } else {
               String var5 = this.getResources().getString(2131232438);
               this.showAlert(var5);
            }

            var4 = false;
         } else {
            String var6 = this.mPassword;
            if(!"****".equals(var6)) {
               String var7 = this.mPassword;
               if(!var1.equals(var7)) {
                  String var8 = this.getResources().getString(2131232438);
                  this.showAlert(var8);
                  var4 = false;
                  return var4;
               }
            }

            var4 = true;
         }
      } else {
         var4 = false;
      }

      return var4;
   }

   private void onPasswordConfirm() {
      String var1 = this.mDigits.getText().toString();
      if(this.match(var1)) {
         Intent var2 = new Intent();
         var2.putExtra(".password", var1);
         this.setResult(-1, var2);
         this.finish();
      }
   }

   private boolean reasonable(String var1) {
      boolean var4;
      if(TextUtils.isEmpty(var1)) {
         String var2 = this.mPassword;
         if("****".equals(var2)) {
            String var3 = this.getResources().getString(2131232439);
            this.showAlert(var3);
         } else {
            String var5 = this.getResources().getString(2131232438);
            this.showAlert(var5);
         }

         var4 = false;
      } else if(var1.length() >= 4 && var1.length() <= 8) {
         var4 = true;
      } else {
         String var6 = this.mPassword;
         if("****".equals(var6)) {
            String var7 = this.getResources().getString(2131232440);
            this.showAlert(var7);
         } else {
            String var8 = this.getResources().getString(2131232438);
            this.showAlert(var8);
         }

         var4 = false;
      }

      return var4;
   }

   private void setupKeypad() {
      View var1 = this.findViewById(2131427408);
      var1.setOnClickListener(this);
      var1.setOnLongClickListener(this);
      this.findViewById(2131427409).setOnClickListener(this);
      this.findViewById(2131427410).setOnClickListener(this);
      this.findViewById(2131427411).setOnClickListener(this);
      this.findViewById(2131427412).setOnClickListener(this);
      this.findViewById(2131427413).setOnClickListener(this);
      this.findViewById(2131427414).setOnClickListener(this);
      this.findViewById(2131427415).setOnClickListener(this);
      this.findViewById(2131427416).setOnClickListener(this);
      this.findViewById(2131427417).setOnClickListener(this);
      this.findViewById(2131427419).setOnClickListener(this);
      this.findViewById(2131427406).setOnClickListener(this);
      View var2 = this.findViewById(2131427418);
      var2.setOnClickListener(this);
      var2.setOnLongClickListener(this);
   }

   private void showAlert(String var1) {
      this.mDigits.setText("");
      Builder var2 = new Builder(this);
      var2.setMessage(var1);
      String var4 = this.getResources().getString(2131232441);
      Password.1 var5 = new Password.1();
      var2.setPositiveButton(var4, var5);
      AlertDialog var7 = var2.show();
   }

   public void afterTextChanged(Editable var1) {}

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131427406:
         this.keyPressed(67);
         return;
      case 2131427407:
      default:
         return;
      case 2131427408:
         this.keyPressed(8);
         return;
      case 2131427409:
         this.keyPressed(9);
         return;
      case 2131427410:
         this.keyPressed(10);
         return;
      case 2131427411:
         this.keyPressed(11);
         return;
      case 2131427412:
         this.keyPressed(12);
         return;
      case 2131427413:
         this.keyPressed(13);
         return;
      case 2131427414:
         this.keyPressed(14);
         return;
      case 2131427415:
         this.keyPressed(15);
         return;
      case 2131427416:
         this.keyPressed(16);
         return;
      case 2131427417:
         this.onPasswordConfirm();
         return;
      case 2131427418:
         this.keyPressed(7);
         return;
      case 2131427419:
         this.finish();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903074);
      Bundle var2 = this.getIntent().getExtras();
      if(var2 != null) {
         String var3 = var2.getString(".title");
         this.mTitle = var3;
         String var4 = var2.getString(".subject");
         this.mSubject = var4;
         String var5 = var2.getString(".password");
         this.mPassword = var5;
      }

      TextView var6 = (TextView)this.findViewById(2131427371);
      this.mHeadrText = var6;
      TextView var7 = this.mHeadrText;
      String var8 = this.mSubject;
      var7.setText(var8);
      TextView var9 = (TextView)this.findViewById(2131427405);
      this.mDigits = var9;
      TextView var10 = this.mDigits;
      DialerKeyListener var11 = DialerKeyListener.getInstance();
      var10.setKeyListener(var11);
      this.mDigits.setOnClickListener(this);
      this.mDigits.setOnKeyListener(this);
      if(this.findViewById(2131427408) != null) {
         this.setupKeypad();
      }
   }

   public boolean onKey(View var1, int var2, KeyEvent var3) {
      return false;
   }

   public boolean onLongClick(View var1) {
      return false;
   }

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   class 1 implements android.content.DialogInterface.OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {}
   }
}

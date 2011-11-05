package com.google.android.finsky.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PinEntryDialog extends FragmentActivity implements OnClickListener {

   private static final String ARG_ID_ALLOW_REMOVE_PIN = "allow-remove-pin";
   private static final String ARG_ID_PIN_TO_MATCH = "pin-to-match";
   private static final String ARG_ID_PROMPT_STRING_ID = "prompt-string-id";
   private static final String ARG_ID_RESULT_KEY = "result-key";
   private static final int NEW_PIN_MINIMUM_LENGTH = 4;
   private String mMatchPin;
   private Button mOkButton;
   private EditText mPinEntryView;
   private TextWatcher mPinWatcher;


   public PinEntryDialog() {
      PinEntryDialog.1 var1 = new PinEntryDialog.1();
      this.mPinWatcher = var1;
   }

   public static Intent getIntent(Context var0, int var1, String var2, String var3, boolean var4) {
      Intent var5 = new Intent(var0, PinEntryDialog.class);
      var5.putExtra("prompt-string-id", var1);
      var5.putExtra("pin-to-match", var2);
      var5.putExtra("result-key", var3);
      var5.putExtra("allow-remove-pin", var4);
      return var5;
   }

   private String getUserPin() {
      return this.mPinEntryView.getText().toString().trim();
   }

   private void setPinResult(String var1) {
      String var2 = this.getIntent().getStringExtra("result-key");
      if(var2 != null) {
         Intent var3 = new Intent();
         var3.putExtra(var2, var1);
         this.setResult(-1, var3);
      } else {
         this.setResult(-1);
      }
   }

   private void syncOkButtonState() {
      byte var1;
      if(this.getUserPin().length() >= 4) {
         var1 = 1;
      } else {
         var1 = 0;
      }

      this.mOkButton.setEnabled((boolean)var1);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131755054:
         this.setResult(0);
         this.finish();
         return;
      case 2131755110:
         String var2 = this.getUserPin();
         if(this.mMatchPin != null && !this.mMatchPin.equals(var2)) {
            this.mPinEntryView.setText("");
            this.mPinEntryView.setHint(2131231072);
            return;
         }

         this.setPinResult(var2);
         this.finish();
         return;
      case 2131755262:
         this.setPinResult((String)null);
         this.finish();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130968680);
      Intent var2 = this.getIntent();
      int var3 = var2.getIntExtra("prompt-string-id", -1);
      String var4 = var2.getStringExtra("pin-to-match");
      this.mMatchPin = var4;
      boolean var5 = var2.getBooleanExtra("allow-remove-pin", (boolean)0);
      TextView var6 = (TextView)this.findViewById(2131755227);
      EditText var7 = (EditText)this.findViewById(2131755261);
      this.mPinEntryView = var7;
      Button var8 = (Button)this.findViewById(2131755110);
      this.mOkButton = var8;
      Button var9 = (Button)this.findViewById(2131755054);
      var6.setText(var3);
      EditText var10 = this.mPinEntryView;
      TextWatcher var11 = this.mPinWatcher;
      var10.addTextChangedListener(var11);
      this.mOkButton.setOnClickListener(this);
      var9.setOnClickListener(this);
      if(var5) {
         Button var12 = (Button)this.findViewById(2131755262);
         var12.setVisibility(0);
         var12.setOnClickListener(this);
      }
   }

   public void onResume() {
      super.onResume();
      this.syncOkButtonState();
   }

   class 1 implements TextWatcher {

      1() {}

      public void afterTextChanged(Editable var1) {}

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         PinEntryDialog.this.syncOkButtonState();
      }
   }
}

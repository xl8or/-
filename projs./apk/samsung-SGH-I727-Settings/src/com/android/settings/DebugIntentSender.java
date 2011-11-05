package com.android.settings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DebugIntentSender extends Activity {

   private EditText mAccountField;
   private OnClickListener mClicked;
   private EditText mDataField;
   private EditText mIntentField;
   private EditText mResourceField;
   private Button mSendBroadcastButton;
   private Button mStartActivityButton;


   public DebugIntentSender() {
      DebugIntentSender.1 var1 = new DebugIntentSender.1();
      this.mClicked = var1;
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903079);
      EditText var2 = (EditText)this.findViewById(2131427449);
      this.mIntentField = var2;
      this.mIntentField.setText("android.intent.action.SYNC");
      Selection.selectAll(this.mIntentField.getText());
      EditText var3 = (EditText)this.findViewById(2131427450);
      this.mDataField = var3;
      this.mDataField.setBackgroundResource(17301528);
      EditText var4 = (EditText)this.findViewById(2131427451);
      this.mAccountField = var4;
      EditText var5 = (EditText)this.findViewById(2131427452);
      this.mResourceField = var5;
      Button var6 = (Button)this.findViewById(2131427453);
      this.mSendBroadcastButton = var6;
      Button var7 = this.mSendBroadcastButton;
      OnClickListener var8 = this.mClicked;
      var7.setOnClickListener(var8);
      Button var9 = (Button)this.findViewById(2131427454);
      this.mStartActivityButton = var9;
      Button var10 = this.mStartActivityButton;
      OnClickListener var11 = this.mClicked;
      var10.setOnClickListener(var11);
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         Button var2 = DebugIntentSender.this.mSendBroadcastButton;
         if(!var1.equals(var2)) {
            Button var3 = DebugIntentSender.this.mStartActivityButton;
            if(!var1.equals(var3)) {
               return;
            }
         }

         String var4 = DebugIntentSender.this.mIntentField.getText().toString();
         String var5 = DebugIntentSender.this.mDataField.getText().toString();
         String var6 = DebugIntentSender.this.mAccountField.getText().toString();
         String var7 = DebugIntentSender.this.mResourceField.getText().toString();
         Intent var8 = new Intent(var4);
         if(!TextUtils.isEmpty(var5)) {
            Uri var9 = Uri.parse(var5);
            var8.setData(var9);
         }

         var8.putExtra("account", var6);
         var8.putExtra("resource", var7);
         Button var13 = DebugIntentSender.this.mSendBroadcastButton;
         if(var1.equals(var13)) {
            DebugIntentSender.this.sendBroadcast(var8);
         } else {
            DebugIntentSender.this.startActivity(var8);
         }

         DebugIntentSender.this.setResult(-1);
         DebugIntentSender.this.finish();
      }
   }
}

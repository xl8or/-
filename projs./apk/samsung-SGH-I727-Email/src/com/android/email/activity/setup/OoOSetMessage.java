package com.android.email.activity.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class OoOSetMessage extends Activity {

   private Button mCancelButton;
   boolean mMsqReqType;
   private Button mSetButton;


   public OoOSetMessage() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903131);
      Button var2 = (Button)this.findViewById(2131362208);
      this.mSetButton = var2;
      Button var3 = (Button)this.findViewById(2131362209);
      this.mCancelButton = var3;
      RadioButton var4 = (RadioButton)this.findViewById(2131362205);
      OoOSetMessage.1 var5 = new OoOSetMessage.1();
      var4.setOnClickListener(var5);
      RadioButton var6 = (RadioButton)this.findViewById(2131362206);
      OoOSetMessage.2 var7 = new OoOSetMessage.2();
      var6.setOnClickListener(var7);
      Button var8 = this.mSetButton;
      OoOSetMessage.3 var9 = new OoOSetMessage.3(var4, var6);
      var8.setOnClickListener(var9);
      Button var10 = this.mCancelButton;
      OoOSetMessage.4 var11 = new OoOSetMessage.4();
      var10.setOnClickListener(var11);
      boolean var12 = this.getIntent().getBooleanExtra("oooMsgReqType", (boolean)1);
      this.mMsqReqType = var12;
      String var13;
      if(this.mMsqReqType == 1) {
         this.setTitle(2131166709);
         if(var4 != null) {
            var4.setVisibility(8);
         }

         if(var6 != null) {
            var6.setVisibility(8);
         }

         var13 = this.getIntent().getStringExtra("oooMsgReqDataInternal");
         if(var13 != null) {
            ((EditText)this.findViewById(2131362207)).setText(var13);
         }
      } else {
         this.setTitle(2131166710);
         var13 = this.getIntent().getStringExtra("oooMsgReqDataExternalKnown");
         if(var13 != null) {
            ((EditText)this.findViewById(2131362207)).setText(var13);
            if(var4 != null) {
               var4.setChecked((boolean)1);
            }
         } else {
            var13 = this.getIntent().getStringExtra("oooMsgReqDataInternalUnknown");
            if(var13 != null) {
               ((EditText)this.findViewById(2131362207)).setText(var13);
               if(var6 != null) {
                  var6.setChecked((boolean)1);
               }
            }
         }
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(View var1) {
         OoOSetMessage.this.setResult(-1);
         OoOSetMessage.this.finish();
      }
   }

   class 3 implements OnClickListener {

      // $FF: synthetic field
      final RadioButton val$rb1;
      // $FF: synthetic field
      final RadioButton val$rb2;


      3(RadioButton var2, RadioButton var3) {
         this.val$rb1 = var2;
         this.val$rb2 = var3;
      }

      public void onClick(View var1) {
         String var2 = ((EditText)OoOSetMessage.this.findViewById(2131362207)).getText().toString();
         if(var2.length() > 0) {
            Intent var3 = new Intent();
            if(OoOSetMessage.this.mMsqReqType == 1) {
               var3.putExtra("oooMsgReqDataInternal", var2);
            } else {
               if(this.val$rb1 != null && this.val$rb1.isChecked()) {
                  var3.putExtra("oooMsgReqDataExternalKnown", var2);
               }

               if(this.val$rb2 != null && this.val$rb2.isChecked()) {
                  var3.putExtra("oooMsgReqDataInternalUnknown", var2);
               }
            }

            OoOSetMessage.this.setResult(-1, var3);
            OoOSetMessage.this.finish();
         } else {
            OoOSetMessage.this.setResult(-1);
            OoOSetMessage.this.finish();
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {}
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {}
   }
}

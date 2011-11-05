package com.htc.android.mail;

import android.app.Activity;
import android.content.ContentValues;
import android.content.IContentProvider;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HtcSkinUtil;
import android.widget.TextView;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.ll;

public class signatureEditor extends Activity {

   private static final boolean DEBUG = Mail.MAIL_DEBUG;
   private static final String TAG = "signatureEditor";
   String sig = null;


   public signatureEditor() {}

   private void saveAccountSignature(String var1) {
      ContentValues var2 = new ContentValues();
      var2.put("_signature", var1);
      IContentProvider var3 = MailProvider.instance();
      Uri var4 = this.getIntent().getData();
      if(DEBUG) {
         String var5 = "saveAccountNotify>" + var4 + "," + var2;
         ll.d("signatureEditor", var5);
      }

      Object var6 = null;
      Object var7 = null;

      try {
         var3.update(var4, var2, (String)var6, (String[])var7);
      } catch (DeadObjectException var11) {
         ;
      } catch (RemoteException var12) {
         ;
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      int var2 = HtcSkinUtil.getDrawableResIdentifier(this, "common_app_bkg", 34080439);
      if(var2 != 0) {
         Window var3 = this.getWindow();
         Drawable var4 = this.getResources().getDrawable(var2);
         var3.setBackgroundDrawable(var4);
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      String var2 = this.getIntent().getStringExtra("sig");
      this.sig = var2;
      if(DEBUG) {
         StringBuilder var3 = (new StringBuilder()).append("signatureEditor create>");
         String var4 = this.sig;
         String var5 = var3.append(var4).toString();
         ll.d("signatureEditor", var5);
      }

      boolean var6 = this.requestWindowFeature(1);
      this.setContentView(2130903093);
      this.findViewById(2131296578).setRoundedCornerEnabled((boolean)1, (boolean)0);
      this.findViewById(2131296579).setRoundedCornerEnabled((boolean)1, (boolean)0);
      ((TextView)this.findViewById(33685587)).setText(2131362446);
      ((TextView)this.findViewById(33685588)).setText(2131362446);
      ((Button)this.findViewById(33685505)).setText(2131362413);
      ((Button)this.findViewById(33685507)).setText(2131362063);
      EditText var7 = (EditText)this.findViewById(2131296579);
      if(this.sig == null) {
         this.sig = "";
      }

      String var8 = this.sig;
      var7.append(var8);
      boolean var9 = var7.requestFocus();
      Button var10 = (Button)this.findViewById(33685507);
      signatureEditor.1 var11 = new signatureEditor.1();
      var10.setOnClickListener(var11);
      Button var12 = (Button)this.findViewById(33685505);
      signatureEditor.2 var13 = new signatureEditor.2();
      var12.setOnClickListener(var13);
   }

   protected final void onResume() {
      super.onResume();
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         signatureEditor.this.finish();
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         signatureEditor var2 = signatureEditor.this;
         String var3 = ((EditText)signatureEditor.this.findViewById(2131296579)).getText().toString();
         var2.sig = var3;
         Intent var4 = signatureEditor.this.getIntent();
         signatureEditor var5 = signatureEditor.this;
         String var6 = signatureEditor.this.sig;
         var5.saveAccountSignature(var6);
         String var7 = signatureEditor.this.sig;
         var4.putExtra("sig", var7);
         signatureEditor.this.setResult(-1, var4);
         signatureEditor.this.finish();
      }
   }
}

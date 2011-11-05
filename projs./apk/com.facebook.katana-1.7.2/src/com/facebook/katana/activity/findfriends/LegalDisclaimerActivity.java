package com.facebook.katana.activity.findfriends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.facebook.katana.Constants;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.util.GrowthUtils;

public class LegalDisclaimerActivity extends BaseFacebookActivity {

   private static final int SUMMARY_DIALOG = 1;
   private AppSession mAppSession;
   private Button mImportButton;


   public LegalDisclaimerActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903078);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         this.hideSearchButton();
         Button var3 = (Button)this.findViewById(2131624059);
         this.mImportButton = var3;
         LegalDisclaimerActivity.1 var4 = new LegalDisclaimerActivity.1();
         this.mImportButton.setOnClickListener(var4);
         String var5 = this.getString(2131361826);
         this.setPrimaryActionFace(-1, var5);
         if(GrowthUtils.kddiImporterEnabled(this)) {
            ((LinearLayout)this.findViewById(2131624062)).setVisibility(0);
            this.findViewById(2131624060).setVisibility(8);
            Button var6 = (Button)this.findViewById(2131624063);
            Uri var7 = Uri.parse(Constants.URL.getJapanKddiContactImporterUrl(this));
            LegalDisclaimerActivity.2 var8 = new LegalDisclaimerActivity.2(var7);
            var6.setOnClickListener(var8);
         }
      }
   }

   protected Dialog onCreateDialog(int var1) {
      AlertDialog var2;
      switch(var1) {
      case 1:
         Builder var3 = (new Builder(this)).setCancelable((boolean)1).setMessage(2131362456);
         LegalDisclaimerActivity.3 var4 = new LegalDisclaimerActivity.3();
         var2 = var3.setPositiveButton(2131362013, var4).create();
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      this.setResult(0);
      this.finish();
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.cancel();
      }
   }

   class 2 implements android.view.View.OnClickListener {

      // $FF: synthetic field
      final Uri val$kddiUri;


      2(Uri var2) {
         this.val$kddiUri = var2;
      }

      public void onClick(View var1) {
         Uri var2 = this.val$kddiUri;
         Intent var3 = new Intent("android.intent.action.VIEW", var2);
         LegalDisclaimerActivity.this.startActivity(var3);
      }
   }

   class 1 implements android.view.View.OnClickListener {

      1() {}

      public void onClick(View var1) {
         LegalDisclaimerActivity.this.setResult(-1);
         LegalDisclaimerActivity.this.finish();
      }
   }
}

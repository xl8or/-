package com.google.android.finsky.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SharedPreferencesUtils;

public class TosActivity extends Activity implements OnClickListener {

   private static final String EXTRA_ACCOUNT = "finsky.TosActivity.account";
   private static final String EXTRA_TOS_CONTENT = "finsky.TosActivity.tos";
   private String mAccount = null;
   private String mContent = null;


   public TosActivity() {}

   public static Intent getIntent(Context var0, String var1, String var2) {
      Intent var3 = new Intent(var0, TosActivity.class);
      var3.putExtra("finsky.TosActivity.account", var1);
      var3.putExtra("finsky.TosActivity.tos", var2);
      return var3;
   }

   private static String makeTosKey(String var0, String var1) {
      StringBuilder var2 = (new StringBuilder()).append(var0).append(":");
      int var3 = var1.hashCode();
      return var2.append(var3).toString();
   }

   private void onAccepted() {
      Editor var1 = this.getSharedPreferences("finsky", 0).edit();
      String var2 = this.mAccount;
      String var3 = this.mContent;
      String var4 = makeTosKey(var2, var3);
      boolean var5 = SharedPreferencesUtils.commit(var1.putBoolean(var4, (boolean)1));
      this.setResult(-1);
      this.finish();
   }

   private void onDeclined() {
      this.setResult(0);
      this.finish();
   }

   public static boolean requiresAcceptance(Context var0, String var1, String var2) {
      byte var3 = 0;
      SharedPreferences var4 = var0.getSharedPreferences("finsky", var3);
      String var5 = makeTosKey(var1, var2);
      if(!var4.getBoolean(var5, (boolean)var3)) {
         var3 = 1;
      }

      return (boolean)var3;
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131755314:
         this.onAccepted();
         return;
      case 2131755315:
         this.onDeclined();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Bundle var2;
      if(var1 != null) {
         var2 = var1;
      } else {
         var2 = this.getIntent().getExtras();
      }

      if(var2 != null) {
         String var3 = var2.getString("finsky.TosActivity.account");
         this.mAccount = var3;
         String var4 = var2.getString("finsky.TosActivity.tos");
         this.mContent = var4;
      }

      if(this.mAccount != null && this.mContent != null) {
         this.setContentView(2130968713);
         this.findViewById(2131755314).setOnClickListener(this);
         this.findViewById(2131755315).setOnClickListener(this);
         TextView var6 = (TextView)this.findViewById(2131755211);
         MovementMethod var7 = LinkMovementMethod.getInstance();
         var6.setMovementMethod(var7);
         Spanned var8 = Html.fromHtml(this.mContent);
         var6.setText(var8);
      } else {
         Object[] var5 = new Object[0];
         FinskyLog.w("Bad request to Terms of Service activity.", var5);
         this.finish();
      }
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      String var2 = this.mAccount;
      var1.putString("finsky.TosActivity.account", var2);
      String var3 = this.mContent;
      var1.putString("finsky.TosActivity.tos", var3);
   }
}

package com.htc.android.mail;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.htc.android.mail.Mail;

public class DebugActivity extends Activity implements OnCheckedChangeListener {

   private CheckBox mEnableDebug;
   private CheckBox mEnableDetailDebug;
   private CheckBox mEnableExchangeDebug;
   private CheckBox mEnableRDVersion;
   private TextView mVersionView;


   public DebugActivity() {}

   public void onCheckedChanged(CompoundButton var1, boolean var2) {
      switch(var1.getId()) {
      case 2131296368:
         Mail.MAIL_DEBUG = var2;
         return;
      case 2131296369:
         Mail.MAIL_DETAIL_DEBUG = var2;
         return;
      case 2131296370:
         Mail.EAS_DEBUG = var2;
         return;
      case 2131296371:
         Mail.RD_VERSION = var2;
         return;
      default:
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903052);
      TextView var2 = (TextView)this.findViewById(2131296367);
      this.mVersionView = var2;
      CheckBox var3 = (CheckBox)this.findViewById(2131296368);
      this.mEnableDebug = var3;
      CheckBox var4 = (CheckBox)this.findViewById(2131296369);
      this.mEnableDetailDebug = var4;
      CheckBox var5 = (CheckBox)this.findViewById(2131296370);
      this.mEnableExchangeDebug = var5;
      CheckBox var6 = (CheckBox)this.findViewById(2131296371);
      this.mEnableRDVersion = var6;
      this.mEnableDebug.setOnCheckedChangeListener(this);
      this.mEnableDetailDebug.setOnCheckedChangeListener(this);
      this.mEnableExchangeDebug.setOnCheckedChangeListener(this);
      this.mEnableRDVersion.setOnCheckedChangeListener(this);
      TextView var7 = this.mVersionView;
      String var8 = this.getString(2131362565);
      var7.setText(var8);
      CheckBox var9 = this.mEnableDebug;
      boolean var10 = Mail.MAIL_DEBUG;
      var9.setChecked(var10);
      CheckBox var11 = this.mEnableDetailDebug;
      boolean var12 = Mail.MAIL_DETAIL_DEBUG;
      var11.setChecked(var12);
      CheckBox var13 = this.mEnableExchangeDebug;
      boolean var14 = Mail.EAS_DEBUG;
      var13.setChecked(var14);
      CheckBox var15 = this.mEnableRDVersion;
      boolean var16 = Mail.RD_VERSION;
      var15.setChecked(var16);
   }

   protected final void onDestroy() {
      super.onDestroy();
      StringBuilder var1 = (new StringBuilder()).append("debug=");
      boolean var2 = Mail.MAIL_DEBUG;
      StringBuilder var3 = var1.append(var2).append(", detail_debug=");
      boolean var4 = Mail.MAIL_DETAIL_DEBUG;
      StringBuilder var5 = var3.append(var4).append(", Exchange_debug=");
      boolean var6 = Mail.EAS_DEBUG;
      StringBuilder var7 = var5.append(var6).append(", RD_version=");
      boolean var8 = Mail.RD_VERSION;
      String var9 = var7.append(var8).toString();
      int var10 = Log.i("DebugActivity", var9);
   }
}

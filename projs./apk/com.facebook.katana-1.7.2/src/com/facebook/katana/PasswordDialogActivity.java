package com.facebook.katana;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;

public class PasswordDialogActivity extends BaseFacebookActivity {

   private static final int PROGRESS_LOGGING_OUT_DIALOG_ID = 1;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;


   public PasswordDialogActivity() {}

   private void disableButtons() {
      this.findViewById(2131624160).setEnabled((boolean)0);
      this.findViewById(2131624021).setEnabled((boolean)0);
   }

   private void doLogout() {
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      if(var1 != null) {
         this.showDialog(1);
         var1.authLogout(this);
      }
   }

   public boolean facebookOnBackPressed() {
      return true;
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903118);
      String var2 = this.getIntent().getStringExtra("un");
      ((TextView)this.findViewById(2131624157)).setText(var2);
      View var3 = this.findViewById(2131624160);
      PasswordDialogActivity.1 var4 = new PasswordDialogActivity.1();
      var3.setOnClickListener(var4);
      View var5 = this.findViewById(2131624021);
      PasswordDialogActivity.2 var6 = new PasswordDialogActivity.2();
      var5.setOnClickListener(var6);
      AppSession var7 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var7;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         PasswordDialogActivity.3 var8 = new PasswordDialogActivity.3();
         this.mAppSessionListener = var8;
      }
   }

   protected Dialog onCreateDialog(int var1) {
      ProgressDialog var2;
      switch(var1) {
      case 1:
         ProgressDialog var3 = new ProgressDialog(this);
         var3.setProgressStyle(0);
         CharSequence var4 = this.getText(2131362237);
         var3.setMessage(var4);
         var3.setIndeterminate((boolean)1);
         var3.setCancelable((boolean)0);
         var2 = var3;
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   protected void onPause() {
      super.onPause();
      AppSession var1 = this.mAppSession;
      AppSessionListener var2 = this.mAppSessionListener;
      var1.removeListener(var2);
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = this.mAppSession;
      AppSessionListener var2 = this.mAppSessionListener;
      var1.addListener(var2);
   }

   class 3 extends AppSessionListener {

      3() {}

      public void onLogoutComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         PasswordDialogActivity.this.removeDialog(1);
         LoginActivity.toLogin(PasswordDialogActivity.this);
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         PasswordDialogActivity.this.doLogout();
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         String var2 = ((EditText)PasswordDialogActivity.this.findViewById(2131624158)).getText().toString();
         AppSession var3 = AppSession.getActiveSession(PasswordDialogActivity.this, (boolean)0);
         PasswordDialogActivity var4 = PasswordDialogActivity.this;
         var3.handlePasswordEntry(var4, var2);
         PasswordDialogActivity.this.disableButtons();
         PasswordDialogActivity.this.finish();
      }
   }
}

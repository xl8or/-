package com.facebook.katana;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.PasswordDialogActivity;
import com.facebook.katana.binding.AppSession;

public class LoginNotificationActivity extends Activity {

   public LoginNotificationActivity() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      AppSession var3 = AppSession.getActiveSession(this, (boolean)0);
      Intent var4 = new Intent(var2);
      Class var5;
      if(var3 != null) {
         var5 = PasswordDialogActivity.class;
      } else {
         var5 = LoginActivity.class;
      }

      var4.setClass(this, var5);
      this.startActivity(var4);
      this.finish();
   }
}

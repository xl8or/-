package com.htc.android.mail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.htc.android.mail.Account;
import com.htc.android.mail.AolSpamActivity;
import com.htc.android.mail.SendErrorNotification;

public class AolSpamNotification extends SendErrorNotification {

   public AolSpamNotification(Context var1) {
      super(var1);
   }

   public void createTarget(Account var1) {
      StringBuilder var2 = (new StringBuilder()).append("content://mail/accounts/");
      long var3 = var1.id;
      Uri var5 = Uri.parse(var2.append(var3).toString());
      Context var6 = this.mNotifyContext;
      Intent var7 = new Intent("android.intent.action.VIEW", var5, var6, AolSpamActivity.class);
      this.mTarget = var7;
   }
}

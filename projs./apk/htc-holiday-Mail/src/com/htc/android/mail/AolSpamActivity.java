package com.htc.android.mail;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.htc.android.mail.SendErrorNotification;

public class AolSpamActivity extends Activity {

   public AolSpamActivity() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      long var2 = ContentUris.parseId(this.getIntent().getData());
      Context var4 = this.getApplicationContext();
      int var5 = (new SendErrorNotification(var4)).clearNotification(var2);
      Uri var6 = Uri.parse("http://challenge.aol.com/spam.html");
      Intent var7 = new Intent("android.intent.action.VIEW", var6);
      this.startActivity(var7);
      this.finish();
   }
}

package com.google.android.finsky.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.utils.IntentUtils;

public class AppsUrlHandlerActivity extends Activity {

   public AppsUrlHandlerActivity() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      IntentUtils.forwardIntentToMainActivity(this, var2);
      this.finish();
   }
}

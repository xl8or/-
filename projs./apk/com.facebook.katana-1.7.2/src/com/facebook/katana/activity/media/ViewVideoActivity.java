package com.facebook.katana.activity.media;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.util.StringUtils;

public class ViewVideoActivity extends BaseFacebookActivity {

   public static final String EXTRA_HREF = "href";


   public ViewVideoActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      String var2 = this.getIntent().getStringExtra("href");
      if(var2 != null) {
         Intent var3 = new Intent("android.intent.action.VIEW");
         Uri var4 = Uri.parse(var2);
         if(StringUtils.saneStringEquals(var4.getScheme(), "https")) {
            var4 = var4.buildUpon().scheme("http").build();
         }

         var3.setDataAndType(var4, "video/*");
         if(this.getPackageManager().queryIntentActivities(var3, 0).size() > 0) {
            this.startActivity(var3);
         }
      }

      this.finish();
   }
}

package com.facebook.katana.activity.places;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.ProfileTabHostActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.ApplicationUtils;

public class StubPlacesActivity extends BaseFacebookActivity {

   public StubPlacesActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      Intent var2 = this.getIntent();
      FacebookPlace var3 = (FacebookPlace)var2.getParcelableExtra("extra_place");
      if(var3 != null) {
         FacebookPage var4 = var3.getPageInfo();
         Intent var5 = new Intent(this, ProfileTabHostActivity.class);
         long var6 = var4.mPageId;
         var5.putExtra("extra_user_id", var6);
         String var9 = var3.mName;
         var5.putExtra("extra_user_display_name", var9);
         String var11 = var4.mPicSmall;
         var5.putExtra("extra_image_url", var11);
         Intent var13 = var5.putExtra("extra_user_type", 2);
         var5.putExtra("extra_place", var3);
         Intent var15 = var5.addFlags(65536);
         this.startActivity(var5);
         this.finish();
      } else {
         long var16 = var2.getLongExtra("extra_user_id", 65535L);
         ApplicationUtils.OpenPlaceProfile(this, var16);
      }
   }
}

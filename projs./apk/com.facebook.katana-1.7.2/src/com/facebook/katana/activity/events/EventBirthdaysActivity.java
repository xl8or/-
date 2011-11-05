package com.facebook.katana.activity.events;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.events.EventBirthdaysAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.ApplicationUtils;

public class EventBirthdaysActivity extends BaseFacebookListActivity implements OnItemClickListener {

   private EventBirthdaysAdapter mAdapter;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;


   public EventBirthdaysActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903069);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         ListView var3 = this.getListView();
         long[] var4 = this.getIntent().getLongArrayExtra("extra_user_ids");
         StringBuilder var5 = new StringBuilder("user_id");
         StringBuilder var6 = var5.append(" IN (");
         int var7 = 0;

         while(true) {
            int var8 = var4.length;
            if(var7 >= var8) {
               StringBuilder var13 = var5.append(")");
               ProfileImagesCache var14 = this.mAppSession.getUserImagesCache();
               String var15 = var5.toString();
               EventBirthdaysAdapter var16 = new EventBirthdaysAdapter(this, var14, var15);
               this.mAdapter = var16;
               EventBirthdaysAdapter var17 = this.mAdapter;
               var3.setAdapter(var17);
               EventBirthdaysActivity.EventsAppSessionListener var18 = new EventBirthdaysActivity.EventsAppSessionListener((EventBirthdaysActivity.1)null);
               this.mAppSessionListener = var18;
               var3.setOnItemClickListener(this);
               return;
            }

            if(var7 != 0) {
               StringBuilder var9 = var5.append(",");
            }

            long var10 = var4[var7];
            var5.append(var10);
            ++var7;
         }
      }
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      long var6 = ((EventBirthdaysAdapter.Item)this.mAdapter.getItem(var3)).getUserId();
      ApplicationUtils.OpenUserProfile(this, var6, (FacebookProfile)null);
   }

   protected void onPause() {
      super.onPause();
      AppSession var1 = this.mAppSession;
      AppSessionListener var2 = this.mAppSessionListener;
      var1.removeListener(var2);
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         AppSession var2 = this.mAppSession;
         AppSessionListener var3 = this.mAppSessionListener;
         var2.addListener(var3);
      }
   }

   private class EventsAppSessionListener extends AppSessionListener {

      private EventsAppSessionListener() {}

      // $FF: synthetic method
      EventsAppSessionListener(EventBirthdaysActivity.1 var2) {
         this();
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            EventBirthdaysActivity.this.mAdapter.updateUserImage(var6);
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         EventBirthdaysActivity.this.mAdapter.updateUserImage(var2);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

package com.facebook.katana.activity.events;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.events.EventGuestsAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.ApplicationUtils;
import java.util.List;
import java.util.Map;

public class EventGuestsActivity extends BaseFacebookListActivity implements OnItemClickListener {

   private EventGuestsAdapter mAdapter;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;


   public EventGuestsActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903069);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         ListView var3 = this.getListView();
         ProfileImagesCache var4 = this.mAppSession.getUserImagesCache();
         EventGuestsAdapter var5 = new EventGuestsAdapter(this, var4);
         this.mAdapter = var5;
         EventGuestsAdapter var6 = this.mAdapter;
         var3.setAdapter(var6);
         EventGuestsActivity.EventsAppSessionListener var7 = new EventGuestsActivity.EventsAppSessionListener((EventGuestsActivity.1)null);
         this.mAppSessionListener = var7;
         long var8 = this.getIntent().getLongExtra("extra_event_id", 65535L);
         this.mAppSession.eventGetMembers(this, var8);
         var3.setOnItemClickListener(this);
      }
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      long var6 = ((EventGuestsAdapter.Item)this.mAdapter.getItem(var3)).getUser().mUserId;
      ApplicationUtils.OpenUserProfile(this, var6, (FacebookProfile)null);
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.removeListener(var2);
      }
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

   // $FF: synthetic class
   static class 1 {
   }

   private class EventsAppSessionListener extends AppSessionListener {

      private EventsAppSessionListener() {}

      // $FF: synthetic method
      EventsAppSessionListener(EventGuestsActivity.1 var2) {
         this();
      }

      public void onEventGetMembersComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, Map<FacebookEvent.RsvpStatus, List<FacebookUser>> var8) {
         EventGuestsActivity.this.mAdapter.setItemsInfo(var8);
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            EventGuestsActivity.this.mAdapter.updateUserImage(var6);
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         EventGuestsActivity.this.mAdapter.updateUserImage(var2);
      }
   }
}

package com.facebook.katana.activity.events;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.events.EventBirthdaysActivity;
import com.facebook.katana.activity.events.EventDetailsActivity;
import com.facebook.katana.activity.events.EventsAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.provider.EventsProvider;
import java.util.Iterator;
import java.util.List;

public class EventsActivity extends BaseFacebookListActivity implements OnItemClickListener {

   private static final int REFRESH_ID = 2;
   private EventsAdapter mAdapter;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private long mUserId;


   public EventsActivity() {}

   private void showBirthdaysGroup(EventsAdapter.Item var1) {
      Object var2 = var1.getBirthdaysCount();
      int var3 = 0;

      for(Iterator var4 = var1.getBirthdaysList().iterator(); var4.hasNext(); ++var3) {
         long var5 = ((EventsAdapter.Birthday)var4.next()).getUserId().longValue();
         ((Object[])var2)[var3] = var5;
      }

      Intent var7 = new Intent(this, EventBirthdaysActivity.class);
      var7.putExtra("extra_user_ids", (long[])var2);
      this.startActivity(var7);
   }

   private void showEventDetails(long var1) {
      Intent var3 = new Intent(this, EventDetailsActivity.class);
      Uri var4 = EventsProvider.EVENT_EID_CONTENT_URI;
      String var5 = String.valueOf(var1);
      Uri var6 = Uri.withAppendedPath(var4, var5);
      var3.setData(var6);
      this.startActivity(var3);
   }

   private void showProgress(boolean var1) {
      if(var1) {
         this.findViewById(2131624177).setVisibility(0);
         this.findViewById(2131624022).setVisibility(8);
      } else {
         this.findViewById(2131624177).setVisibility(8);
         if(this.mAdapter.getCount() == 0) {
            TextView var2 = (TextView)this.findViewById(2131624022);
            var2.setText(2131361893);
            var2.setVisibility(0);
            this.findViewById(2131624023).setVisibility(8);
         }
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903069);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         long var3 = this.mAppSession.getSessionInfo().userId;
         this.mUserId = var3;
         ListView var5 = this.getListView();
         StreamPhotosCache var6 = this.mAppSession.getPhotosCache();
         EventsAdapter var7 = new EventsAdapter(this, var6);
         this.mAdapter = var7;
         EventsAdapter var8 = this.mAdapter;
         var5.setAdapter(var8);
         EventsActivity.EventsAppSessionListener var9 = new EventsActivity.EventsAppSessionListener((EventsActivity.1)null);
         this.mAppSessionListener = var9;
         var5.setOnItemClickListener(this);
      }
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuItem var3 = var1.add(0, 2, 0, 2131362246).setIcon(2130837692);
      return true;
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      EventsAdapter.Item var6 = (EventsAdapter.Item)this.mAdapter.getItem(var3);
      if(var6.getType() == 1) {
         this.showBirthdaysGroup(var6);
      } else if(var6.getType() == 0) {
         long var7 = var6.getEid();
         this.showEventDetails(var7);
      }
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 2:
         this.refresh();
         var2 = true;
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   protected void onPause() {
      super.onPause();
      AppSession var1 = this.mAppSession;
      AppSessionListener var2 = this.mAppSessionListener;
      var1.removeListener(var2);
      this.mAdapter.stopBucketizeTask();
   }

   protected void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(this.mAppSessionListener == null) {
            EventsActivity.EventsAppSessionListener var2 = new EventsActivity.EventsAppSessionListener((EventsActivity.1)null);
            this.mAppSessionListener = var2;
         }

         AppSession var3 = this.mAppSession;
         AppSessionListener var4 = this.mAppSessionListener;
         var3.addListener(var4);
         this.refresh();
         this.mAdapter.setItemsAsync();
      }
   }

   void refresh() {
      this.showProgress((boolean)1);
      AppSession var1 = this.mAppSession;
      long var2 = this.mUserId;
      var1.getEvents(this, var2);
      this.logStepDataRequested();
   }

   private class EventsAppSessionListener extends AppSessionListener {

      private EventsAppSessionListener() {}

      // $FF: synthetic method
      EventsAppSessionListener(EventsActivity.1 var2) {
         this();
      }

      public void onDownloadStreamPhotoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, Bitmap var7) {
         EventsActivity.this.mAdapter.updatePhoto(var7, var6);
      }

      public void onFriendsSyncComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         if(var3 == 200) {
            EventsActivity.this.mAdapter.setItemsAsync();
         }
      }

      public void onPhotoDecodeComplete(AppSession var1, Bitmap var2, String var3) {
         EventsActivity.this.mAdapter.updatePhoto(var2, var3);
      }

      public void onUserGetEventsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookEvent> var6) {
         EventsActivity.this.logStepDataReceived();
         EventsActivity.this.mAdapter.setItemsAsync();
         EventsActivity.this.showProgress((boolean)0);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

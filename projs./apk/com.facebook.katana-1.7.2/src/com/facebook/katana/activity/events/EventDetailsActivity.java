package com.facebook.katana.activity.events;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.events.EventDetailsAdapter;
import com.facebook.katana.activity.events.EventGuestsActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.Toaster;
import java.net.URLEncoder;
import java.util.List;

public class EventDetailsActivity extends BaseFacebookListActivity implements OnItemClickListener {

   private final int RSVP_DIALOG = 0;
   private EventDetailsAdapter mAdapter;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private Cursor mCursor;


   public EventDetailsActivity() {}

   private void showGuestsList(long var1) {
      Intent var3 = new Intent(this, EventGuestsActivity.class);
      var3.putExtra("extra_event_id", var1);
      this.startActivity(var3);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903066);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         EventDetailsActivity.EventsAppSessionListener var3 = new EventDetailsActivity.EventsAppSessionListener((EventDetailsActivity.1)null);
         this.mAppSessionListener = var3;
         AppSession var4 = this.mAppSession;
         AppSessionListener var5 = this.mAppSessionListener;
         var4.addListener(var5);
         this.prepareEvent((boolean)1);
      }
   }

   protected Dialog onCreateDialog(int var1) {
      CharSequence[] var2 = new CharSequence[3];
      String var3 = this.getString(2131361880);
      var2[0] = var3;
      String var4 = this.getString(2131361891);
      var2[1] = var4;
      String var5 = this.getString(2131361881);
      var2[2] = var5;
      Builder var6 = new Builder(this);
      String var7 = this.getString(2131361879);
      var6.setTitle(var7);
      EventDetailsActivity.1 var9 = new EventDetailsActivity.1();
      int var10 = this.mAdapter.getRsvpStatus();
      int var11 = FacebookEvent.RsvpStatusEnum.NOT_REPLIED.ordinal();
      if(var10 == var11) {
         var10 = -1;
      }

      var6.setSingleChoiceItems(var2, var10, var9);
      return var6.create();
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      int var6 = this.getListView().getHeaderViewsCount();
      int var7 = var3 - var6;
      if(var7 < 0) {
         switch(var2.getId()) {
         case 2131624032:
            this.showDialog(0);
            return;
         default:
         }
      } else {
         int var8 = this.mAdapter.getCount();
         if(var7 < var8) {
            EventDetailsAdapter.Item var9 = (EventDetailsAdapter.Item)this.mAdapter.getItem(var7);
            switch(var9.getType()) {
            case 0:
               long var10 = this.mAdapter.getCreatorId();
               ApplicationUtils.OpenUserProfile(this, var10, (FacebookProfile)null);
               return;
            case 1:
            default:
               return;
            case 2:
               StringBuilder var14 = (new StringBuilder()).append("geo:0,0?q=");
               String var15 = URLEncoder.encode(var9.getString());
               String var16 = var14.append(var15).toString();

               try {
                  Uri var17 = Uri.parse(var16);
                  Intent var18 = new Intent("android.intent.action.VIEW", var17);
                  this.startActivity(var18);
                  return;
               } catch (ActivityNotFoundException var20) {
                  return;
               }
            case 3:
               long var12 = this.mAdapter.getEventId();
               this.showGuestsList(var12);
            }
         }
      }
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
         if(this.mAppSessionListener == null) {
            EventDetailsActivity.EventsAppSessionListener var2 = new EventDetailsActivity.EventsAppSessionListener((EventDetailsActivity.1)null);
            this.mAppSessionListener = var2;
         }

         AppSession var3 = this.mAppSession;
         AppSessionListener var4 = this.mAppSessionListener;
         var3.addListener(var4);
      }
   }

   public void prepareEvent(boolean var1) {
      if(this.getListView().getAdapter() == null) {
         Uri var2 = this.getIntent().getData();
         String[] var3 = EventDetailsAdapter.EventQuery.PROJECTION;
         Object var5 = null;
         Cursor var6 = this.managedQuery(var2, var3, "", (String[])null, (String)var5);
         this.mCursor = var6;
         if(this.mCursor != null && this.mCursor.moveToFirst()) {
            StreamPhotosCache var11 = this.mAppSession.getPhotosCache();
            Cursor var12 = this.mCursor;
            long var13 = this.mCursor.getLong(7);
            long var15 = this.mAppSession.getSessionInfo().userId;
            EventDetailsAdapter var18 = new EventDetailsAdapter(this, var11, var12, var13, var15);
            this.mAdapter = var18;
            ListView var19 = this.getListView();
            View var20 = this.mAdapter.getFooterView();
            var19.addFooterView(var20, (Object)null, (boolean)0);
            ListView var21 = this.getListView();
            View var22 = this.mAdapter.getHeaderView();
            var21.addHeaderView(var22, (Object)null, (boolean)0);
            ListView var23 = this.getListView();
            View var24 = this.mAdapter.getRsvpView();
            var23.addHeaderView(var24, (Object)null, (boolean)1);
            ListView var25 = this.getListView();
            View var26 = this.mAdapter.getHeaderDivider();
            var25.addHeaderView(var26, (Object)null, (boolean)0);
            ListView var27 = this.getListView();
            EventDetailsAdapter var28 = this.mAdapter;
            var27.setAdapter(var28);
            this.getListView().setOnItemClickListener(this);
            this.mAdapter.setEventInfo();
         } else if(var1) {
            AppSession var7 = this.mAppSession;
            long var8 = this.mAppSession.getSessionInfo().userId;
            var7.getEvents(this, var8);
            this.logStepDataRequested();
         } else {
            Toaster.toast(this, 2131361878);
         }
      }
   }

   private class EventsAppSessionListener extends AppSessionListener {

      private EventsAppSessionListener() {}

      // $FF: synthetic method
      EventsAppSessionListener(EventDetailsActivity.1 var2) {
         this();
      }

      public void onDownloadStreamPhotoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, Bitmap var7) {
         if(EventDetailsActivity.this.mAdapter != null) {
            EventDetailsActivity.this.mAdapter.updatePhoto(var7, var6);
         }
      }

      public void onEventRsvpComplete(AppSession var1, String var2, int var3, String var4, Exception var5, long var6, boolean var8) {
         long var9 = EventDetailsActivity.this.mAdapter.getEventId();
         if(var6 == var9) {
            EventDetailsActivity.this.findViewById(2131624035).setVisibility(8);
            if(var3 == 200 && var8) {
               boolean var11 = EventDetailsActivity.this.mCursor.requery();
               if(EventDetailsActivity.this.mCursor.moveToFirst()) {
                  EventDetailsAdapter var12 = EventDetailsActivity.this.mAdapter;
                  View var13 = EventDetailsActivity.this.findViewById(2131624034);
                  int var14 = EventDetailsActivity.this.mCursor.getInt(11);
                  var12.setRsvpStatus(var13, var14);
               }
            } else {
               Toaster.toast(EventDetailsActivity.this, 2131361890);
            }
         }
      }

      public void onPhotoDecodeComplete(AppSession var1, Bitmap var2, String var3) {
         if(EventDetailsActivity.this.mAdapter != null) {
            EventDetailsActivity.this.mAdapter.updatePhoto(var2, var3);
         }
      }

      public void onUserGetEventsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookEvent> var6) {
         EventDetailsActivity.this.logStepDataReceived();
         EventDetailsActivity.this.prepareEvent((boolean)0);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         AppSession var3 = EventDetailsActivity.this.mAppSession;
         EventDetailsActivity var4 = EventDetailsActivity.this;
         long var5 = EventDetailsActivity.this.mAdapter.getEventId();
         FacebookEvent.RsvpStatus var7 = FacebookEvent.getRsvpStatus(var2);
         var3.eventRsvp(var4, var5, var7);
         EventDetailsActivity.this.findViewById(2131624035).setVisibility(0);
         EventDetailsActivity.this.dismissDialog(0);
      }
   }
}

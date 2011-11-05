package com.facebook.katana.activity.places;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.places.FriendCheckinsAdapter;
import com.facebook.katana.activity.places.PlacesNearbyActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.model.FacebookCheckin;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.model.FacebookPost;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.IntentUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import com.facebook.katana.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.JSONException;

public class FriendCheckinsActivity extends BaseFacebookListActivity implements OnItemClickListener {

   protected static final String COMPOSER_CHECKIN_ERROR = "composer_checkin_error";
   private static final int REFRESH_ID = 2;
   protected static final int STRUCTURED_COMPOSER = 10;
   private FriendCheckinsAdapter mAdapter;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private String mCheckinReqId;
   private boolean mEnabled;
   private boolean mProgressVisible;


   public FriendCheckinsActivity() {}

   private void refreshStaticViews(boolean var1) {
      TextView var2 = (TextView)this.findViewById(2131624022);
      if(this.mEnabled) {
         var2.setText(2131362395);
         String var3 = this.getString(2131362366);
         this.setPrimaryActionFace(-1, var3);
      } else {
         var2.setText(2131361896);
      }

      if(this.mProgressVisible) {
         this.findViewById(2131624177).setVisibility(0);
         this.findViewById(2131624023).setVisibility(0);
         var2.setVisibility(8);
      } else {
         this.findViewById(2131624177).setVisibility(8);
         if(!var1) {
            this.findViewById(2131624023).setVisibility(8);
            var2.setVisibility(0);
         }
      }
   }

   private void setupStaticViews() {
      ((TextView)this.findViewById(2131624024)).setText(2131361836);
      this.refreshStaticViews((boolean)0);
   }

   private void setupViews() {
      this.setContentView(2130903081);
      this.setupStaticViews();
      this.hideSearchButton();
      SectionedListView var1 = (SectionedListView)this.getListView();
      var1.setDividerHeight(0);
      FriendCheckinsAdapter var2 = this.mAdapter;
      var1.setSectionedListAdapter(var2);
      View var3 = this.findViewById(16908292);
      var1.setEmptyView(var3);
      var1.setOnItemClickListener(this);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var2 != 0) {
         switch(var1) {
         case 10:
            String var5 = "extra_status_text";
            String var6 = var3.getStringExtra(var5);
            String var8 = "extra_tagged_ids";
            long[] var9 = var3.getLongArrayExtra(var8);
            ArrayList var10 = new ArrayList();
            long[] var11 = var9;
            int var12 = var9.length;

            for(int var13 = 0; var13 < var12; ++var13) {
               Long var16 = Long.valueOf(var11[var13]);
               boolean var19 = var10.add(var16);
            }

            String var21 = "extra_place";
            FacebookPlace var22 = (FacebookPlace)var3.getParcelableExtra(var21);
            String var24 = "extra_tagged_location";
            Location var25 = (Location)var3.getParcelableExtra(var24);
            String var27 = "extra_status_privacy";
            String var28 = var3.getStringExtra(var27);
            String var30 = "extra_status_target_id";
            long var31 = 65535L;
            Long var33 = Long.valueOf(var3.getLongExtra(var30, var31));
            if(var22 != null && var25 != null) {
               try {
                  AppSession var43 = this.mAppSession;
                  Set var44 = IntentUtils.primitiveToSet(var9);
                  String var46 = var43.checkin(this, var22, var25, var6, var44, var33, var28);
                  this.mCheckinReqId = var46;
                  return;
               } catch (JSONException var50) {
                  int var49 = 2131362368;
                  Toaster.toast(this, var49);
                  return;
               }
            } else {
               String var34;
               if(var22 == null) {
                  var34 = "NULL";
               } else {
                  var34 = Long.toString(var22.mPageId);
               }

               String var35;
               if(var25 == null) {
                  var35 = "NULL";
               } else {
                  var35 = var25.toString();
               }

               StringBuilder var36 = (new StringBuilder()).append("Returned from checkin using ComposerActivity with null place or location. place=");
               StringBuilder var38 = var36.append(var34).append(" location=");
               String var40 = var38.append(var35).toString();
               Utils.reportSoftError("composer_checkin_error", var40);
               int var42 = 2131362368;
               Toaster.toast(this, var42);
               return;
            }
         default:
         }
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         StreamPhotosCache var3 = this.mAppSession.getPhotosCache();
         FriendCheckinsAdapter var4 = new FriendCheckinsAdapter(this, var3);
         this.mAdapter = var4;
         FriendCheckinsActivity.FriendCheckinsListener var5 = new FriendCheckinsActivity.FriendCheckinsListener((FriendCheckinsActivity.1)null);
         this.mAppSessionListener = var5;
         this.setupViews();
      }
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      super.onCreateOptionsMenu(var1);
      MenuItem var3 = var1.add(0, 2, 0, 2131362246).setIcon(2130837692);
      return true;
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      FacebookCheckin var6 = (FacebookCheckin)this.mAdapter.getItem(var3);
      if(var6.getDetails().getAppInfo() != null) {
         Uri var7 = Uri.parse(var6.getDetails().getPlaceInfo().getPageInfo().mUrl);
         Intent var8 = new Intent("android.intent.action.VIEW", var7);
         this.startActivity(var8);
      } else {
         FacebookPlace var9 = var6.getDetails().getPlaceInfo();
         ApplicationUtils.OpenPlaceProfile(this, var9);
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
      this.mAdapter.suspendAdapter();
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
         Boolean var4 = Gatekeeper.get(this, "places");
         if(var4 != null && var4.booleanValue() == 1) {
            this.mEnabled = (boolean)1;
            this.refresh();
            this.mAdapter.resumeAdapter();
         } else {
            this.mEnabled = (boolean)0;
            this.mProgressVisible = (boolean)0;
            this.refreshStaticViews((boolean)0);
         }
      }
   }

   void refresh() {
      this.mProgressVisible = (boolean)1;
      this.refreshStaticViews((boolean)0);
      String var1 = this.mAppSession.getFriendCheckins(this);
      this.logStepDataRequested();
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      if(Gatekeeper.get(this, "meta_composer").booleanValue()) {
         Bundle var2 = new Bundle();
         var2.putBoolean("extra_is_checkin", (boolean)1);
         Integer var3 = Integer.valueOf(10);
         this.launchComposer((Uri)null, var2, var3, 65535L);
      } else {
         Intent var4 = new Intent(this, PlacesNearbyActivity.class);
         Intent var5 = var4.putExtra("extra_is_checkin", (boolean)1);
         this.startActivity(var4);
      }
   }

   private class FriendCheckinsListener extends AppSessionListener {

      private FriendCheckinsListener() {}

      // $FF: synthetic method
      FriendCheckinsListener(FriendCheckinsActivity.1 var2) {
         this();
      }

      public void onCheckinComplete(AppSession var1, String var2, int var3, String var4, Exception var5, FacebookPost var6) {
         String var7 = FriendCheckinsActivity.this.mCheckinReqId;
         if(var2.equals(var7)) {
            String var8 = FriendCheckinsActivity.this.mCheckinReqId = null;
            if(var3 == 200) {
               FriendCheckinsActivity.this.refresh();
            }
         }
      }

      public void onDownloadStreamPhotoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, Bitmap var7) {
         if(var3 == 200) {
            FriendCheckinsActivity.this.mAdapter.updatePhoto(var7, var6);
         }
      }

      public void onFriendCheckinsComplete(AppSession var1, String var2, int var3, String var4, Exception var5, List<FacebookCheckin> var6) {
         byte var7 = 0;
         boolean var8 = (boolean)(FriendCheckinsActivity.this.mProgressVisible = (boolean)0);
         if(var3 == 200) {
            FriendCheckinsActivity.this.logStepDataReceived();
            FriendCheckinsActivity.this.mAdapter.update(var6);
            if(var6.size() > 0) {
               var7 = 1;
            }
         } else {
            FriendCheckinsActivity var9 = FriendCheckinsActivity.this;
            String var10 = FriendCheckinsActivity.this.getString(2131362228);
            String var11 = StringUtils.getErrorString(var9, var10, var3, var4, var5);
            Toaster.toast(FriendCheckinsActivity.this, var11);
         }

         FriendCheckinsActivity.this.refreshStaticViews((boolean)var7);
      }

      public void onGkSettingsGetComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, boolean var7) {
         if(var3 == 200) {
            if("places".equals(var6)) {
               if(var7) {
                  boolean var8 = (boolean)(FriendCheckinsActivity.this.mEnabled = (boolean)1);
                  FriendCheckinsActivity.this.refresh();
                  FriendCheckinsActivity.this.mAdapter.resumeAdapter();
               }
            }
         }
      }

      public void onPhotoDecodeComplete(AppSession var1, Bitmap var2, String var3) {
         FriendCheckinsActivity.this.mAdapter.updatePhoto(var2, var3);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

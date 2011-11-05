package com.facebook.katana;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.CheckboxAdapterListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.PickFriendsAdapter;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.ArrayList;

public class PickFriendsActivity extends BaseFacebookListActivity implements OnItemClickListener, CheckboxAdapterListener {

   public static final String INITIAL_FRIENDS = "com.facebook.katana.PickFriendsActivity.initial_friends";
   public static final String RESULT_FRIENDS = "com.facebook.katana.PickFriendsActivity.result_friends";
   private PickFriendsAdapter mAdapter;
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private PickFriendsActivity.QueryHandler mQueryHandler;
   private TextView mRecipientsSummaryTextView;


   public PickFriendsActivity() {}

   private void handleQueryComplete(Cursor var1) {
      this.startManagingCursor(var1);
      this.mAdapter.changeCursor(var1);
      if(!this.mAppSession.isFriendsSyncPending()) {
         if(this.mAdapter.getCount() == 0) {
            String var2 = this.mAppSession.syncFriends(this);
            this.showProgress((boolean)1);
         } else {
            this.showProgress((boolean)0);
         }
      } else {
         this.showProgress((boolean)1);
      }
   }

   private void setupEmptyView() {
      ((TextView)this.findViewById(2131624022)).setText(2131361904);
      ((TextView)this.findViewById(2131624024)).setText(2131361903);
   }

   private void showButtonBar(boolean var1) {
      ViewStub var2 = (ViewStub)this.findViewById(2131624170);
      if(var2 != null) {
         View var3 = var2.inflate();
         View var4 = this.findViewById(2131624160);
         PickFriendsActivity.1 var5 = new PickFriendsActivity.1();
         var4.setOnClickListener(var5);
         View var6 = this.findViewById(2131624021);
         PickFriendsActivity.2 var7 = new PickFriendsActivity.2();
         var6.setOnClickListener(var7);
      }

      View var8 = this.findViewById(2131624171);
      if(var1 && var8.getVisibility() == 8) {
         Animation var9 = AnimationUtils.loadAnimation(this, 2130968581);
         var8.setAnimation(var9);
         var8.setVisibility(0);
      } else if(!var1) {
         if(var8.getVisibility() == 0) {
            Animation var10 = AnimationUtils.loadAnimation(this, 2130968580);
            var8.setAnimation(var10);
            var8.setVisibility(8);
         }
      }
   }

   private void showProgress(boolean var1) {
      if(var1) {
         this.findViewById(2131624177).setVisibility(0);
         this.findViewById(2131624022).setVisibility(8);
         this.findViewById(2131624023).setVisibility(0);
      } else {
         this.findViewById(2131624177).setVisibility(8);
         this.findViewById(2131624022).setVisibility(0);
         this.findViewById(2131624023).setVisibility(8);
      }
   }

   private void updateSummaryText() {
      int var1 = this.mAdapter.getMarkedFriends().size();
      String var2;
      if(1 == var1) {
         var2 = this.getString(2131362014);
      } else {
         Object[] var3 = new Object[1];
         Integer var4 = Integer.valueOf(var1);
         var3[0] = var4;
         var2 = this.getString(2131361996, var3);
      }

      this.mRecipientsSummaryTextView.setText(var2);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903129);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         this.hideSearchButton();
         ArrayList var3 = this.getIntent().getParcelableArrayListExtra("com.facebook.katana.PickFriendsActivity.initial_friends");
         ProfileImagesCache var4 = this.mAppSession.getUserImagesCache();
         PickFriendsAdapter var7 = new PickFriendsAdapter(this, (Cursor)null, var4, this, var3);
         this.mAdapter = var7;
         ListView var8 = this.getListView();
         PickFriendsAdapter var9 = this.mAdapter;
         var8.setAdapter(var9);
         this.setupEmptyView();
         PickFriendsActivity.QueryHandler var10 = new PickFriendsActivity.QueryHandler(this);
         this.mQueryHandler = var10;
         PickFriendsActivity.PickFriendsAppSessionListener var11 = new PickFriendsActivity.PickFriendsAppSessionListener((PickFriendsActivity.1)null);
         this.mAppSessionListener = var11;
         this.getListView().setOnItemClickListener(this);
         TextView var12 = (TextView)this.findViewById(2131624169);
         this.mRecipientsSummaryTextView = var12;
         this.updateSummaryText();
      }
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      this.mAdapter.flipMarked(var3);
   }

   public void onMarkChanged(long var1, boolean var3, int var4) {
      byte var5;
      if(!var3 && var4 <= 0) {
         var5 = 0;
      } else {
         var5 = 1;
      }

      this.showButtonBar((boolean)var5);
      this.updateSummaryText();
   }

   protected void onPause() {
      super.onPause();
      if(this.mAppSession != null) {
         AppSession var1 = this.mAppSession;
         AppSessionListener var2 = this.mAppSessionListener;
         var1.removeListener(var2);
      }
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         AppSession var2 = this.mAppSession;
         AppSessionListener var3 = this.mAppSessionListener;
         var2.addListener(var3);
         this.showProgress((boolean)1);
         PickFriendsActivity.QueryHandler var4 = this.mQueryHandler;
         Uri var5 = ConnectionsProvider.FRIENDS_CONTENT_URI;
         String[] var6 = PickFriendsAdapter.FriendsQuery.PROJECTION;
         Object var7 = null;
         Object var8 = null;
         Object var9 = null;
         var4.startQuery(1, (Object)null, var5, var6, (String)var7, (String[])var8, (String)var9);
      }
   }

   public boolean shouldChangeState(long var1, boolean var3, int var4) {
      return true;
   }

   private final class QueryHandler extends AsyncQueryHandler {

      public static final int QUERY_FRIENDS_TOKEN = 1;


      public QueryHandler(Context var2) {
         ContentResolver var3 = var2.getContentResolver();
         super(var3);
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         if(!PickFriendsActivity.this.isFinishing()) {
            PickFriendsActivity.this.showProgress((boolean)0);
            PickFriendsActivity.this.handleQueryComplete(var3);
         } else {
            var3.close();
         }
      }
   }

   private class PickFriendsAppSessionListener extends AppSessionListener {

      private PickFriendsAppSessionListener() {}

      // $FF: synthetic method
      PickFriendsAppSessionListener(PickFriendsActivity.1 var2) {
         this();
      }

      public void onFriendsSyncComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         PickFriendsActivity.this.findViewById(2131624177).setVisibility(8);
         if(var3 != 200) {
            PickFriendsActivity var6 = PickFriendsActivity.this;
            String var7 = PickFriendsActivity.this.getString(2131361902);
            String var8 = StringUtils.getErrorString(var6, var7, var3, var4, var5);
            Toaster.toast(PickFriendsActivity.this, var8);
         }
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            PickFriendsActivity.this.mAdapter.updateUserImage(var6);
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         PickFriendsActivity.this.mAdapter.updateUserImage(var2);
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         PickFriendsActivity.this.finish();
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         ArrayList var2 = PickFriendsActivity.this.mAdapter.getMarkedFriends();
         if(var2.size() > 0) {
            Intent var3 = new Intent();
            var3.putExtra("com.facebook.katana.PickFriendsActivity.result_friends", var2);
            PickFriendsActivity.this.setResult(-1, var3);
         }

         PickFriendsActivity.this.finish();
      }
   }
}

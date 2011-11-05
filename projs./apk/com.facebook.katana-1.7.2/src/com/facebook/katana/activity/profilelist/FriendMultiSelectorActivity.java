package com.facebook.katana.activity.profilelist;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import com.facebook.katana.activity.profilelist.ProfileListNaiveCursorAdapter;
import com.facebook.katana.activity.profilelist.SelectableProfileListNaiveCursorAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.IntentUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.Set;

public class FriendMultiSelectorActivity extends ProfileListActivity implements OnItemClickListener {

   public static final String EXTRA_SELECTED_PROFILES = "profiles";
   public static final int PROGRESS_FLAG_FRIENDS_QUERY = 1;
   public static final int PROGRESS_FLAG_FRIENDS_SYNC = 2;
   protected String mCurrentQuery;
   protected int mProgress;
   protected FriendMultiSelectorActivity.QueryHandler mQueryHandler;
   protected ImageView mSearchIcon;
   protected Set<Long> mSelectedUids;
   protected TextView mTextBox;


   public FriendMultiSelectorActivity() {}

   private void setupEmptyView() {
      TextView var1 = (TextView)this.findViewById(2131624022);
      TextView var2 = (TextView)this.findViewById(2131624024);
      var1.setText(2131361904);
      var2.setText(2131361903);
   }

   private void setupViews() {
      this.setContentView(2130903154);
      SectionedListView var1 = (SectionedListView)this.getListView();
      ProfileListActivity.ProfileListAdapter var2 = this.mAdapter;
      var1.setSectionedListAdapter(var2);
      this.setupEmptyView();
      String var3 = this.getString(2131361867);
      this.setPrimaryActionFace(-1, var3);
      this.hideSearchButton();
      this.getListView().setOnItemClickListener(this);
      TextView var4 = (TextView)this.findViewById(2131623961);
      this.mTextBox = var4;
      TextView var5 = this.mTextBox;
      String var6 = this.mCurrentQuery;
      var5.setText(var6);
      TextView var7 = this.mTextBox;
      FriendMultiSelectorActivity.1 var8 = new FriendMultiSelectorActivity.1();
      var7.addTextChangedListener(var8);
      ImageView var9 = (ImageView)this.findViewById(2131624230);
      this.mSearchIcon = var9;
      ImageView var10 = this.mSearchIcon;
      FriendMultiSelectorActivity.2 var11 = new FriendMultiSelectorActivity.2();
      var10.setOnClickListener(var11);
   }

   private void showProgress(int var1, boolean var2) {
      if(var2) {
         int var3 = this.mProgress | var1;
         this.mProgress = var3;
      } else {
         int var5 = this.mProgress;
         int var6 = ~var1;
         int var7 = var5 & var6;
         this.mProgress = var7;
      }

      boolean var4;
      if(this.mProgress != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if(var4) {
         this.findViewById(2131624022).setVisibility(8);
         this.findViewById(2131624023).setVisibility(0);
      } else {
         this.findViewById(2131624022).setVisibility(0);
         this.findViewById(2131624023).setVisibility(8);
      }
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.setupViews();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         Set var3 = IntentUtils.primitiveToSet(this.getIntent().getLongArrayExtra("profiles"));
         this.mSelectedUids = var3;
         ProfileImagesCache var4 = this.mAppSession.getUserImagesCache();
         Set var5 = this.mSelectedUids;
         SelectableProfileListNaiveCursorAdapter var6 = new SelectableProfileListNaiveCursorAdapter(this, var4, (Cursor)null, var5);
         this.mAdapter = var6;
         FriendMultiSelectorActivity.QueryHandler var7 = new FriendMultiSelectorActivity.QueryHandler(this);
         this.mQueryHandler = var7;
         FriendMultiSelectorActivity.FriendMultiSelectorAppSessionListener var8 = new FriendMultiSelectorActivity.FriendMultiSelectorAppSessionListener();
         this.mAppSessionListener = var8;
         this.setupViews();
         boolean var9 = this.mTextBox.requestFocus();
      }
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      ((SelectableProfileListNaiveCursorAdapter)this.mAdapter).toggle(var3, var2);
   }

   public void onResume() {
      super.onResume();
      AppSession var1 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var1;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         AppSession var2 = this.mAppSession;
         ProfileListActivity.ProfileListListener var3 = this.mAppSessionListener;
         var2.addListener(var3);
         if(((SelectableProfileListNaiveCursorAdapter)this.mAdapter).getCursor() == null) {
            this.showProgress(1, (boolean)1);
            FriendMultiSelectorActivity.QueryHandler var4 = this.mQueryHandler;
            Uri var5 = ConnectionsProvider.FRIENDS_CONTENT_URI;
            String[] var6 = ProfileListNaiveCursorAdapter.FriendsQuery.PROJECTION;
            Object var7 = null;
            Object var8 = null;
            var4.startQuery(1, (Object)null, var5, var6, "display_name IS NOT NULL AND LENGTH(display_name) > 0", (String[])var7, (String)var8);
         }
      }
   }

   public void titleBarPrimaryActionClickHandler(View var1) {
      Intent var2 = new Intent();
      long[] var3 = IntentUtils.setToPrimitive(this.mSelectedUids);
      var2.putExtra("profiles", var3);
      this.setResult(-1, var2);
      this.finish();
   }

   protected class FriendMultiSelectorAppSessionListener extends ProfileListActivity.ProfileListListener {

      protected FriendMultiSelectorAppSessionListener() {
         super();
      }

      public void onFriendsSyncComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         FriendMultiSelectorActivity.this.showProgress(2, (boolean)0);
         if(var3 == 200) {
            SelectableProfileListNaiveCursorAdapter var6 = (SelectableProfileListNaiveCursorAdapter)FriendMultiSelectorActivity.this.mAdapter;
            Cursor var7 = var6.getCursor();
            if(var7 != null) {
               boolean var8 = var7.requery();
               var6.refreshData(var7);
            }
         } else {
            FriendMultiSelectorActivity var9 = FriendMultiSelectorActivity.this;
            String var10 = FriendMultiSelectorActivity.this.getString(2131361902);
            String var11 = StringUtils.getErrorString(var9, var10, var3, var4, var5);
            Toaster.toast(FriendMultiSelectorActivity.this, var11);
         }
      }
   }

   private final class QueryHandler extends AsyncQueryHandler {

      public static final int QUERY_FRIENDS_TOKEN = 1;


      public QueryHandler(Context var2) {
         ContentResolver var3 = var2.getContentResolver();
         super(var3);
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         if(FriendMultiSelectorActivity.this.isFinishing()) {
            var3.close();
         } else {
            FriendMultiSelectorActivity.this.showProgress(1, (boolean)0);
            FriendMultiSelectorActivity.this.startManagingCursor(var3);
            ((SelectableProfileListNaiveCursorAdapter)FriendMultiSelectorActivity.this.mAdapter).refreshData(var3);
            if(!FriendMultiSelectorActivity.this.mAppSession.isFriendsSyncPending()) {
               if(FriendMultiSelectorActivity.this.mAdapter.getCount() == 0) {
                  AppSession var4 = FriendMultiSelectorActivity.this.mAppSession;
                  FriendMultiSelectorActivity var5 = FriendMultiSelectorActivity.this;
                  var4.syncFriends(var5);
                  FriendMultiSelectorActivity.this.showProgress(2, (boolean)1);
               }
            } else {
               FriendMultiSelectorActivity.this.showProgress(2, (boolean)1);
            }
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         if(FriendMultiSelectorActivity.this.mTextBox.length() > 0) {
            FriendMultiSelectorActivity.this.mTextBox.setText("");
         }
      }
   }

   class 1 implements TextWatcher {

      1() {}

      public void afterTextChanged(Editable var1) {
         int var2;
         if(var1.length() > 0) {
            var2 = 17301594;
         } else {
            var2 = 2130837724;
         }

         FriendMultiSelectorActivity.this.mSearchIcon.setImageResource(var2);
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         String var5 = var1.toString().trim();
         FriendMultiSelectorActivity.this.mCurrentQuery = var5;
         Filter var6 = ((SelectableProfileListNaiveCursorAdapter)FriendMultiSelectorActivity.this.mAdapter).mFilter;
         String var7 = FriendMultiSelectorActivity.this.mCurrentQuery;
         var6.filter(var7);
         ((SectionedListView)FriendMultiSelectorActivity.this.getListView()).setFastScrollEnabled((boolean)0);
      }
   }
}

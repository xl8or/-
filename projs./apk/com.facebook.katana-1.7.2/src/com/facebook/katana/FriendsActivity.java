package com.facebook.katana;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.FriendsAdapter;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.UserSearchResultsAdapter;
import com.facebook.katana.UsersTabProgressSource;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.activity.profilelist.ProfileListCursorAdapter;
import com.facebook.katana.activity.profilelist.ProfileListNaiveCursorAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;

public class FriendsActivity extends BaseFacebookListActivity implements UsersTabProgressSource, OnItemClickListener, OnScrollListener {

   public static final int PROGRESS_FLAG_FRIENDS_QUERY = 1;
   public static final int PROGRESS_FLAG_FRIENDS_SYNC = 2;
   public static final int PROGRESS_FLAG_SEARCH = 4;
   public static final int PROGRESS_FLAG_SEARCH_QUERY = 3;
   protected static final int RESULT_BATCH_SIZE = 20;
   private static final String SAVED_STATE_LAST_QUERY = "query";
   private AppSession mAppSession;
   private AppSessionListener mAppSessionListener;
   private String mCurrentQuery;
   protected String mCurrentUserSearchQueryId;
   private FriendsActivity.FriendsAndUserSearchAdapter mFriendsAndEveryoneAdapter;
   private FriendsActivity.FriendsQueryHandler mFriendsQueryHandler;
   private int mLastQueryLimit;
   private int mLastQueryStart;
   private int mProgress;
   private TabProgressListener mProgressListener;
   private FriendsActivity.UserSearchQueryHandler mUserSearchQueryHandler;


   public FriendsActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903084);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         if(var1 != null) {
            String var3 = var1.getString("query");
            this.mCurrentQuery = var3;
         } else {
            this.mCurrentQuery = "";
         }

         ProfileImagesCache var4 = this.mAppSession.getUserImagesCache();
         FriendsActivity.FriendsAndUserSearchAdapter var5 = new FriendsActivity.FriendsAndUserSearchAdapter(this, var4, (Cursor)null);
         this.mFriendsAndEveryoneAdapter = var5;
         SectionedListView var6 = (SectionedListView)this.getListView();
         FriendsActivity.FriendsAndUserSearchAdapter var7 = this.mFriendsAndEveryoneAdapter;
         var6.setSectionedListAdapter(var7);
         this.setupEmptyView();
         FriendsActivity.FriendsQueryHandler var8 = new FriendsActivity.FriendsQueryHandler(this);
         this.mFriendsQueryHandler = var8;
         FriendsActivity.UserSearchQueryHandler var9 = new FriendsActivity.UserSearchQueryHandler(this);
         this.mUserSearchQueryHandler = var9;
         FriendsActivity.FriendsAppSessionListener var10 = new FriendsActivity.FriendsAppSessionListener((FriendsActivity.1)null);
         this.mAppSessionListener = var10;
         this.getListView().setOnItemClickListener(this);
         this.getListView().setOnScrollListener(this);
         FriendsActivity.UserSearchQueryHandler var11 = this.mUserSearchQueryHandler;
         Uri var12 = ConnectionsProvider.USER_SEARCH_CONTENT_URI;
         Object var13 = null;
         Object var14 = null;
         var11.startDelete(1, (Object)null, var12, (String)var13, (String[])var14);
      }
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      FacebookProfile var6 = (FacebookProfile)this.mFriendsAndEveryoneAdapter.getItem(var3);
      long var7 = var6.mId;
      ApplicationUtils.OpenUserProfile(this, var7, var6);
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
         if(this.mFriendsAndEveryoneAdapter.friendCursor == null) {
            this.showProgress(1, (boolean)1);
            FriendsActivity.FriendsQueryHandler var4 = this.mFriendsQueryHandler;
            Uri var5 = ConnectionsProvider.FRIENDS_CONTENT_URI;
            String[] var6 = ProfileListNaiveCursorAdapter.FriendsQuery.PROJECTION;
            Object var7 = null;
            Object var8 = null;
            var4.startQuery(1, (Object)null, var5, var6, "display_name IS NOT NULL AND LENGTH(display_name) > 0", (String[])var7, (String)var8);
         } else {
            FriendsActivity.FriendsAndUserSearchAdapter var15 = this.mFriendsAndEveryoneAdapter;
            Cursor var16 = this.mFriendsAndEveryoneAdapter.friendCursor;
            var15.refreshData(var16);
         }

         if(this.mFriendsAndEveryoneAdapter.userSearchCursor == null) {
            this.showProgress(3, (boolean)1);
            FriendsActivity.UserSearchQueryHandler var9 = this.mUserSearchQueryHandler;
            Uri var10 = ConnectionsProvider.USER_SEARCH_CONTENT_URI;
            String[] var11 = UserSearchResultsAdapter.SearchResultsQuery.PROJECTION;
            Object var12 = null;
            Object var13 = null;
            Object var14 = null;
            var9.startQuery(1, (Object)null, var10, var11, (String)var12, (String[])var13, (String)var14);
         }
      }
   }

   public void onScroll(AbsListView var1, int var2, int var3, int var4) {
      int var5 = this.getListView().getLastVisiblePosition();
      if(this.mFriendsAndEveryoneAdapter.userSearchCursor != null) {
         int var6 = this.mFriendsAndEveryoneAdapter.getCursor().getCount();
         if(var5 > 0) {
            int var7 = var6 - 1;
            if(var5 == var7) {
               if(this.mCurrentQuery != null) {
                  if(this.mCurrentQuery.length() > 0) {
                     this.showProgress(4, (boolean)1);
                     int var8;
                     if(this.mFriendsAndEveryoneAdapter.userSearchCursor != null) {
                        var8 = this.mFriendsAndEveryoneAdapter.userSearchCursor.getCount();
                     } else {
                        var8 = 0;
                     }

                     String var9 = this.mCurrentQuery;
                     String var10 = this.performSearchRequest(var9, var8, 20);
                     this.mCurrentUserSearchQueryId = var10;
                  }
               }
            }
         }
      }
   }

   public void onScrollStateChanged(AbsListView var1, int var2) {}

   protected String performSearchRequest(String var1, int var2, int var3) {
      String var4 = this.mCurrentQuery;
      String var5;
      if(var1.equals(var4) && this.mLastQueryStart >= var2 && this.mLastQueryLimit == var3) {
         var5 = this.mCurrentUserSearchQueryId;
      } else {
         this.mCurrentQuery = var1;
         this.mLastQueryStart = var2;
         this.mLastQueryLimit = var3;
         var5 = this.mAppSession.usersSearch(this, var1, var2, var3);
      }

      return var5;
   }

   public void search(String var1) {
      String var2 = this.mCurrentQuery;
      if(!var1.equals(var2)) {
         this.mFriendsAndEveryoneAdapter.mFilter.filter(var1);
         this.mFriendsAndEveryoneAdapter.setEveryoneSectionEnabled((boolean)0);
         if(var1.length() == 0) {
            this.mCurrentQuery = "";
            this.mCurrentUserSearchQueryId = null;
         } else {
            ((SectionedListView)this.getListView()).setFastScrollEnabled((boolean)0);
            this.showProgress(4, (boolean)1);
            String var3 = this.performSearchRequest(var1, 0, 20);
            this.mCurrentUserSearchQueryId = var3;
         }
      }
   }

   public void setProgressListener(TabProgressListener var1) {
      this.mProgressListener = var1;
      if(this.mProgressListener != null) {
         TabProgressListener var2 = this.mProgressListener;
         byte var3;
         if(this.mProgress != 0) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         var2.onShowProgress((boolean)var3);
      }
   }

   protected void setupEmptyView() {
      TextView var1 = (TextView)this.findViewById(2131624022);
      TextView var2 = (TextView)this.findViewById(2131624024);
      var1.setText(2131361904);
      var2.setText(2131361903);
   }

   protected void showProgress(int var1, boolean var2) {
      if(var2) {
         int var3 = this.mProgress | var1;
         this.mProgress = var3;
      } else {
         int var5 = this.mProgress;
         int var6 = ~var1;
         int var7 = var5 & var6;
         this.mProgress = var7;
      }

      byte var4;
      if(this.mProgress != 0) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      if(var4 != 0) {
         this.findViewById(2131624022).setVisibility(8);
         this.findViewById(2131624023).setVisibility(0);
      } else {
         this.findViewById(2131624022).setVisibility(0);
         this.findViewById(2131624023).setVisibility(8);
      }

      if(this.mProgressListener != null) {
         this.mProgressListener.onShowProgress((boolean)var4);
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private class FriendsAppSessionListener extends AppSessionListener {

      private FriendsAppSessionListener() {}

      // $FF: synthetic method
      FriendsAppSessionListener(FriendsActivity.1 var2) {
         this();
      }

      public void onFriendsSyncComplete(AppSession var1, String var2, int var3, String var4, Exception var5) {
         FriendsActivity.this.showProgress(2, (boolean)0);
         if(var3 == 200) {
            FriendsActivity.this.logStepDataReceived();
            Cursor var6 = FriendsActivity.this.mFriendsAndEveryoneAdapter.friendCursor;
            if(var6 != null) {
               boolean var7 = var6.requery();
            }

            FriendsActivity.this.mFriendsAndEveryoneAdapter.refreshData(var6);
         } else {
            FriendsActivity var8 = FriendsActivity.this;
            String var9 = FriendsActivity.this.getString(2131361902);
            String var10 = StringUtils.getErrorString(var8, var9, var3, var4, var5);
            Toaster.toast(FriendsActivity.this, var10);
         }
      }

      public void onProfileImageDownloaded(AppSession var1, String var2, int var3, String var4, Exception var5, ProfileImage var6, ProfileImagesCache var7) {
         if(var3 == 200) {
            FriendsActivity.this.mFriendsAndEveryoneAdapter.updateUserImage(var6);
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         FriendsActivity.this.mFriendsAndEveryoneAdapter.updateUserImage(var2);
      }

      public void onUsersSearchComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, int var7) {
         if(FriendsActivity.this.mCurrentUserSearchQueryId != null) {
            String var8 = FriendsActivity.this.mCurrentUserSearchQueryId;
            if(var2.equals(var8)) {
               FriendsActivity.this.showProgress(4, (boolean)0);
               if(var3 == 200) {
                  FriendsActivity.this.logStepDataReceived();
                  Cursor var9 = FriendsActivity.this.mFriendsAndEveryoneAdapter.userSearchCursor;
                  if(var9 != null) {
                     boolean var10 = var9.requery();
                  }

                  FriendsActivity.this.mFriendsAndEveryoneAdapter.setEveryoneSectionEnabled((boolean)1);
                  FriendsActivity.this.mFriendsAndEveryoneAdapter.manageEveryoneSection(var9);
               } else {
                  FriendsActivity var11 = FriendsActivity.this;
                  String var12 = FriendsActivity.this.getString(2131361906);
                  String var13 = StringUtils.getErrorString(var11, var12, var3, var4, var5);
                  Toaster.toast(FriendsActivity.this, var13);
               }
            }
         }
      }
   }

   private final class FriendsQueryHandler extends AsyncQueryHandler {

      public static final int QUERY_FRIENDS_TOKEN = 1;


      public FriendsQueryHandler(Context var2) {
         ContentResolver var3 = var2.getContentResolver();
         super(var3);
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         if(FriendsActivity.this.isFinishing()) {
            var3.close();
         } else {
            FriendsActivity.this.showProgress(1, (boolean)0);
            FriendsActivity.this.startManagingCursor(var3);
            FriendsActivity.this.mFriendsAndEveryoneAdapter.refreshData(var3);
            if(!FriendsActivity.this.mAppSession.isFriendsSyncPending()) {
               if(FriendsActivity.this.mFriendsAndEveryoneAdapter.getCount() == 0) {
                  AppSession var4 = FriendsActivity.this.mAppSession;
                  FriendsActivity var5 = FriendsActivity.this;
                  var4.syncFriends(var5);
                  FriendsActivity.this.logStepDataRequested();
                  FriendsActivity.this.showProgress(2, (boolean)1);
               }
            } else {
               FriendsActivity.this.showProgress(2, (boolean)1);
            }
         }
      }
   }

   protected final class UserSearchQueryHandler extends AsyncQueryHandler {

      public static final int DELETE_SEARCH_TOKEN = 1;
      public static final int QUERY_SEARCH_TOKEN = 1;


      public UserSearchQueryHandler(Context var2) {
         ContentResolver var3 = var2.getContentResolver();
         super(var3);
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         if(FriendsActivity.this.isFinishing()) {
            var3.close();
         } else {
            FriendsActivity.this.showProgress(3, (boolean)0);
            FriendsActivity.this.startManagingCursor(var3);
            FriendsActivity.this.mFriendsAndEveryoneAdapter.setEveryoneSectionEnabled((boolean)1);
            FriendsActivity.this.mFriendsAndEveryoneAdapter.manageEveryoneSection(var3);
         }
      }
   }

   protected class FriendsAndUserSearchAdapter extends FriendsAdapter {

      Cursor friendCursor;
      boolean showEveryoneSection;
      Cursor userSearchCursor;


      public FriendsAndUserSearchAdapter(Context var2, ProfileImagesCache var3, Cursor var4) {
         super(var2, var3, var4);
         this.friendCursor = var4;
         this.showEveryoneSection = (boolean)0;
      }

      public Object getChild(int var1, int var2) {
         ProfileListCursorAdapter.Section var3 = (ProfileListCursorAdapter.Section)this.mSections.get(var1);
         int var4 = var3.getCursorStartPosition() + var2;
         this.mCursor.moveToPosition(var4);
         FacebookProfile var6;
         if(this.mCursor.getCount() < 1) {
            var6 = null;
         } else {
            FacebookProfile var11;
            if(var3 instanceof FriendsActivity.EveryoneSection) {
               long var7 = this.mCursor.getLong(1);
               String var9 = this.mCursor.getString(2);
               String var10 = this.mCursor.getString(3);
               var11 = new FacebookProfile(var7, var9, var10, 0);
            } else {
               long var12 = this.mCursor.getLong(1);
               String var14 = this.mCursor.getString(2);
               String var15 = this.mCursor.getString(3);
               var11 = new FacebookProfile(var12, var14, var15, 0);
            }

            var6 = var11;
         }

         return var6;
      }

      public void manageEveryoneSection(Cursor param1) {
         // $FF: Couldn't be decompiled
      }

      public void refreshData(Cursor var1) {
         synchronized(this){}

         try {
            this.friendCursor = var1;
            super.refreshData(var1);
            Cursor var2 = this.userSearchCursor;
            this.manageEveryoneSection(var2);
         } finally {
            ;
         }

      }

      public void setEveryoneSectionEnabled(boolean var1) {
         this.showEveryoneSection = var1;
         Cursor var2 = this.userSearchCursor;
         this.manageEveryoneSection(var2);
      }
   }

   public static class EveryoneSection extends ProfileListCursorAdapter.Section {

      public EveryoneSection(Context var1, int var2, int var3) {
         String var4 = var1.getString(2131361899);
         super(var4, var2, var3);
      }

      public String toString() {
         return "*";
      }
   }
}

package com.facebook.katana;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ProfileSearchResultsAdapter;
import com.facebook.katana.TabProgressListener;
import com.facebook.katana.UsersTabProgressSource;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.ui.SectionedListView;

public abstract class ProfileSearchResultsActivity extends BaseFacebookListActivity implements UsersTabProgressSource, OnItemClickListener, OnScrollListener {

   public static final int PROGRESS_FLAG_SEARCH = 2;
   public static final int PROGRESS_FLAG_SEARCH_QUERY = 1;
   protected static final int RESULT_BATCH_SIZE = 20;
   protected int lastQueryLimit;
   protected int lastQueryStart;
   protected String lastQueryString;
   protected AppSession mAppSession;
   protected AppSessionListener mAppSessionListener;
   protected String mCurrentQuery;
   protected String mCurrentQueryId;
   private int mProgress;
   private TabProgressListener mProgressListener;
   protected ProfileSearchResultsActivity.QueryHandler mQueryHandler;
   protected ProfileSearchResultsAdapter mSearchAdapter;
   protected int mTotalSearchResults;


   public ProfileSearchResultsActivity() {}

   protected abstract ProfileSearchResultsActivity.ProfileSearchAppSessionListener getAppSessionListener();

   protected abstract ProfileSearchResultsAdapter getSearchAdapter();

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903084);
      AppSession var2 = AppSession.getActiveSession(this, (boolean)1);
      this.mAppSession = var2;
      if(this.mAppSession == null) {
         LoginActivity.toLogin(this);
      } else {
         ProfileSearchResultsAdapter var3 = this.getSearchAdapter();
         this.mSearchAdapter = var3;
         SectionedListView var4 = (SectionedListView)this.getListView();
         ProfileSearchResultsAdapter var5 = this.mSearchAdapter;
         var4.setSectionedListAdapter(var5);
         this.setupEmptyView();
         ProfileSearchResultsActivity.QueryHandler var6 = new ProfileSearchResultsActivity.QueryHandler(this);
         this.mQueryHandler = var6;
         ProfileSearchResultsActivity.ProfileSearchAppSessionListener var7 = this.getAppSessionListener();
         this.mAppSessionListener = var7;
         this.getListView().setOnItemClickListener(this);
         this.getListView().setOnScrollListener(this);
      }
   }

   public abstract void onItemClick(AdapterView<?> var1, View var2, int var3, long var4);

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
      }
   }

   public void onScroll(AbsListView var1, int var2, int var3, int var4) {
      int var5 = this.getListView().getLastVisiblePosition();
      if(this.mSearchAdapter.getCursor() != null) {
         int var6 = this.mSearchAdapter.getCursor().getCount();
         if(var5 > 0) {
            int var7 = var6 - 1;
            if(var5 == var7) {
               if(this.mCurrentQuery != null) {
                  this.showProgress(2, (boolean)1);
                  String var8 = this.mCurrentQuery;
                  String var9 = this.performSearchRequest(var8, var6, 20);
                  this.mCurrentQueryId = var9;
               }
            }
         }
      }
   }

   public void onScrollStateChanged(AbsListView var1, int var2) {}

   public boolean onSearchRequested() {
      return true;
   }

   protected abstract String performSearchRequest(String var1, int var2, int var3);

   public void search(String var1) {
      String var2 = this.mCurrentQuery;
      if(!var1.equals(var2)) {
         this.mCurrentQuery = var1;
         this.showProgress(2, (boolean)1);
         String var3 = this.mCurrentQuery;
         String var4 = this.performSearchRequest(var3, 0, 20);
         this.mCurrentQueryId = var4;
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

   protected abstract void setupEmptyView();

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

   protected class ProfileSearchAppSessionListener extends AppSessionListener {

      protected ProfileSearchAppSessionListener() {}

      public void onDownloadStreamPhotoComplete(AppSession var1, String var2, int var3, String var4, Exception var5, String var6, Bitmap var7) {
         ProfileSearchResultsActivity.this.mSearchAdapter.updatePhoto(var7, var6);
      }

      public void onPhotoDecodeComplete(AppSession var1, Bitmap var2, String var3) {
         ProfileSearchResultsActivity.this.mSearchAdapter.updatePhoto(var2, var3);
      }
   }

   protected final class QueryHandler extends AsyncQueryHandler {

      public static final int DELETE_SEARCH_TOKEN = 1;
      public static final int QUERY_SEARCH_TOKEN = 1;


      public QueryHandler(Context var2) {
         ContentResolver var3 = var2.getContentResolver();
         super(var3);
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         if(ProfileSearchResultsActivity.this.isFinishing()) {
            var3.close();
         } else {
            ProfileSearchResultsActivity.this.showProgress(1, (boolean)0);
            ProfileSearchResultsActivity.this.startManagingCursor(var3);
            ProfileSearchResultsActivity.this.mSearchAdapter.refreshData(var3);
         }
      }
   }
}

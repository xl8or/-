package com.facebook.katana;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.facebook.katana.PageListAdapter;
import com.facebook.katana.PageSearchResultsAdapter;
import com.facebook.katana.ProfileSearchResultsActivity;
import com.facebook.katana.ProfileSearchResultsAdapter;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.provider.PagesProvider;
import com.facebook.katana.service.method.PagesSearch;
import com.facebook.katana.ui.SectionedListMultiAdapter;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;

public class PageSearchResultsActivity extends ProfileSearchResultsActivity {

   private SectionedListMultiAdapter mCombinedAdapter;
   private PageListAdapter mUserPagesAdapter;
   private PageSearchResultsActivity.UserPagesQueryHandler mUserPagesQueryHandler;


   public PageSearchResultsActivity() {}

   protected ProfileSearchResultsActivity.ProfileSearchAppSessionListener getAppSessionListener() {
      return new PageSearchResultsActivity.PagesAppSessionListener((PageSearchResultsActivity.1)null);
   }

   protected ProfileSearchResultsAdapter getSearchAdapter() {
      StreamPhotosCache var1 = this.mAppSession.getPhotosCache();
      return new PageSearchResultsAdapter(this, (Cursor)null, var1);
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      ProfileSearchResultsActivity.QueryHandler var2 = this.mQueryHandler;
      Uri var3 = PagesProvider.SEARCH_RESULTS_CONTENT_URI;
      Object var4 = null;
      Object var5 = null;
      var2.startDelete(1, (Object)null, var3, (String)var4, (String[])var5);
      PageSearchResultsActivity.UserPagesQueryHandler var6 = new PageSearchResultsActivity.UserPagesQueryHandler(this);
      this.mUserPagesQueryHandler = var6;
      ProfileImagesCache var7 = this.mAppSession.getUserImagesCache();
      PageListAdapter var8 = new PageListAdapter(this, var7, (Cursor)null);
      this.mUserPagesAdapter = var8;
      SectionedListMultiAdapter var9 = new SectionedListMultiAdapter();
      this.mCombinedAdapter = var9;
      SectionedListMultiAdapter var10 = this.mCombinedAdapter;
      PageListAdapter var11 = this.mUserPagesAdapter;
      var10.addSectionedAdapter(var11);
      SectionedListMultiAdapter var12 = this.mCombinedAdapter;
      ProfileSearchResultsAdapter var13 = this.mSearchAdapter;
      var12.addSectionedAdapter(var13);
      SectionedListView var14 = (SectionedListView)this.getListView();
      SectionedListMultiAdapter var15 = this.mCombinedAdapter;
      var14.setSectionedListAdapter(var15);
      ((SectionedListView)this.getListView()).setFastScrollEnabled((boolean)0);
   }

   public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
      FacebookProfile var6 = (FacebookProfile)((SectionedListView)this.getListView()).getSectionedListAdapter().getItem(var3);
      long var7 = var6.mId;
      ApplicationUtils.OpenPageProfile(this, var7, var6);
   }

   public void onResume() {
      super.onResume();
      if(this.mSearchAdapter.getCursor() == null) {
         this.showProgress(1, (boolean)1);
         PageSearchResultsActivity.UserPagesQueryHandler var1 = this.mUserPagesQueryHandler;
         Uri var2 = ConnectionsProvider.PAGES_CONTENT_URI;
         String[] var3 = PageListAdapter.UserPagesQuery.PROJECTION;
         Object var4 = null;
         Object var5 = null;
         var1.startQuery(1, (Object)null, var2, var3, (String)var4, (String[])var5, "connection_type, display_name");
         ProfileSearchResultsActivity.QueryHandler var6 = this.mQueryHandler;
         Uri var7 = PagesProvider.SEARCH_RESULTS_CONTENT_URI;
         String[] var8 = PageSearchResultsAdapter.SearchResultsQuery.PROJECTION;
         Object var9 = null;
         Object var10 = null;
         Object var11 = null;
         var6.startQuery(1, (Object)null, var7, var8, (String)var9, (String[])var10, (String)var11);
      }
   }

   protected String performSearchRequest(String var1, int var2, int var3) {
      String var4 = this.lastQueryString;
      String var5;
      if(var1.equals(var4) && this.lastQueryStart == var2 && this.lastQueryLimit == var3) {
         var5 = this.mCurrentQueryId;
      } else {
         this.lastQueryString = var1;
         this.lastQueryStart = var2;
         this.lastQueryLimit = var3;
         var5 = PagesSearch.RequestPagesSearch(this, var1, var2, var3);
      }

      return var5;
   }

   public void search(String var1) {
      this.mUserPagesAdapter.mFilter.filter(var1);
      super.search(var1);
   }

   protected void setupEmptyView() {
      TextView var1 = (TextView)this.findViewById(2131624022);
      TextView var2 = (TextView)this.findViewById(2131624024);
      var1.setText(2131362068);
      var2.setText(2131361908);
   }

   private class PagesAppSessionListener extends ProfileSearchResultsActivity.ProfileSearchAppSessionListener {

      private PagesAppSessionListener() {
         super();
      }

      // $FF: synthetic method
      PagesAppSessionListener(PageSearchResultsActivity.1 var2) {
         this();
      }

      public void onPagesSearchComplete(AppSession var1, String var2, int var3, String var4, Exception var5, int var6, int var7) {
         String var8 = PageSearchResultsActivity.this.mCurrentQueryId;
         if(var2.equals(var8)) {
            PageSearchResultsActivity.this.showProgress(2, (boolean)0);
            if(var3 == 200) {
               PageSearchResultsActivity.this.logStepDataReceived();
               PageSearchResultsActivity.this.mTotalSearchResults = var7;
               Cursor var9 = PageSearchResultsActivity.this.mSearchAdapter.getCursor();
               if(var9 != null) {
                  boolean var10 = var9.requery();
               }

               PageSearchResultsActivity.this.mSearchAdapter.refreshData(var9);
            } else {
               PageSearchResultsActivity var11 = PageSearchResultsActivity.this;
               String var12 = PageSearchResultsActivity.this.getString(2131361906);
               String var13 = StringUtils.getErrorString(var11, var12, var3, var4, var5);
               Toaster.toast(PageSearchResultsActivity.this, var13);
            }
         }
      }

      public void onProfileImageLoaded(AppSession var1, ProfileImage var2) {
         PageSearchResultsActivity.this.mUserPagesAdapter.updateUserImage(var2);
      }
   }

   protected final class UserPagesQueryHandler extends AsyncQueryHandler {

      public static final int QUERY_SEARCH_TOKEN = 1;


      public UserPagesQueryHandler(Context var2) {
         ContentResolver var3 = var2.getContentResolver();
         super(var3);
      }

      protected void onQueryComplete(int var1, Object var2, Cursor var3) {
         if(PageSearchResultsActivity.this.isFinishing()) {
            var3.close();
         } else {
            PageSearchResultsActivity.this.showProgress(1, (boolean)0);
            PageSearchResultsActivity.this.startManagingCursor(var3);
            PageSearchResultsActivity.this.mUserPagesAdapter.refreshData(var3);
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

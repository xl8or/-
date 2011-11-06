// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PageSearchResultsActivity.java

package com.facebook.katana;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.provider.PagesProvider;
import com.facebook.katana.service.method.PagesSearch;
import com.facebook.katana.ui.SectionedListAdapter;
import com.facebook.katana.ui.SectionedListMultiAdapter;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;

// Referenced classes of package com.facebook.katana:
//            ProfileSearchResultsActivity, PageSearchResultsAdapter, PageListAdapter, ProfileSearchResultsAdapter

public class PageSearchResultsActivity extends ProfileSearchResultsActivity
{
    private class PagesAppSessionListener extends ProfileSearchResultsActivity.ProfileSearchAppSessionListener
    {

        public void onPagesSearchComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, int k)
        {
            if(s.equals(mCurrentQueryId))
            {
                showProgress(2, false);
                if(i == 200)
                {
                    logStepDataReceived();
                    mTotalSearchResults = k;
                    Cursor cursor = mSearchAdapter.getCursor();
                    if(cursor != null)
                        cursor.requery();
                    mSearchAdapter.refreshData(cursor);
                } else
                {
                    String s2 = StringUtils.getErrorString(PageSearchResultsActivity.this, getString(0x7f0a0072), i, s1, exception);
                    Toaster.toast(PageSearchResultsActivity.this, s2);
                }
            }
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mUserPagesAdapter.updateUserImage(profileimage);
        }

        final PageSearchResultsActivity this$0;

        private PagesAppSessionListener()
        {
            this$0 = PageSearchResultsActivity.this;
            ProfileSearchAppSessionListener(PageSearchResultsActivity.this);
        }

    }

    protected final class UserPagesQueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            if(isFinishing())
            {
                cursor.close();
            } else
            {
                showProgress(1, false);
                startManagingCursor(cursor);
                mUserPagesAdapter.refreshData(cursor);
            }
        }

        public static final int QUERY_SEARCH_TOKEN = 1;
        final PageSearchResultsActivity this$0;

        public UserPagesQueryHandler(Context context)
        {
            this$0 = PageSearchResultsActivity.this;
            AsyncQueryHandler(context.getContentResolver());
        }
    }


    public PageSearchResultsActivity()
    {
    }

    protected ProfileSearchResultsActivity.ProfileSearchAppSessionListener getAppSessionListener()
    {
        return new PagesAppSessionListener();
    }

    protected ProfileSearchResultsAdapter getSearchAdapter()
    {
        return new PageSearchResultsAdapter(this, null, mAppSession.getPhotosCache());
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mQueryHandler.startDelete(1, null, PagesProvider.SEARCH_RESULTS_CONTENT_URI, null, null);
        mUserPagesQueryHandler = new UserPagesQueryHandler(this);
        mUserPagesAdapter = new PageListAdapter(this, mAppSession.getUserImagesCache(), null);
        mCombinedAdapter = new SectionedListMultiAdapter();
        mCombinedAdapter.addSectionedAdapter(mUserPagesAdapter);
        mCombinedAdapter.addSectionedAdapter(mSearchAdapter);
        ((SectionedListView)getListView()).setSectionedListAdapter(mCombinedAdapter);
        ((SectionedListView)getListView()).setFastScrollEnabled(false);
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        FacebookProfile facebookprofile = (FacebookProfile)((SectionedListView)getListView()).getSectionedListAdapter().getItem(i);
        ApplicationUtils.OpenPageProfile(this, facebookprofile.mId, facebookprofile);
    }

    public void onResume()
    {
        super.onResume();
        if(mSearchAdapter.getCursor() == null)
        {
            showProgress(1, true);
            mUserPagesQueryHandler.startQuery(1, null, ConnectionsProvider.PAGES_CONTENT_URI, PageListAdapter.UserPagesQuery.PROJECTION, null, null, "connection_type, display_name");
            mQueryHandler.startQuery(1, null, PagesProvider.SEARCH_RESULTS_CONTENT_URI, PageSearchResultsAdapter.SearchResultsQuery.PROJECTION, null, null, null);
        }
    }

    protected String performSearchRequest(String s, int i, int j)
    {
        String s1;
        if(s.equals(lastQueryString) && lastQueryStart == i && lastQueryLimit == j)
        {
            s1 = mCurrentQueryId;
        } else
        {
            lastQueryString = s;
            lastQueryStart = i;
            lastQueryLimit = j;
            s1 = PagesSearch.RequestPagesSearch(this, s, i, j);
        }
        return s1;
    }

    public void search(String s)
    {
        mUserPagesAdapter.mFilter.filter(s);
        super.search(s);
    }

    protected void setupEmptyView()
    {
        TextView textview = (TextView)findViewById(0x7f0e0056);
        TextView textview1 = (TextView)findViewById(0x7f0e0058);
        textview.setText(0x7f0a0114);
        textview1.setText(0x7f0a0074);
    }

    private SectionedListMultiAdapter mCombinedAdapter;
    private PageListAdapter mUserPagesAdapter;
    private UserPagesQueryHandler mUserPagesQueryHandler;


}

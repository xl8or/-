// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileSearchResultsActivity.java

package com.facebook.katana;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.ui.SectionedListView;

// Referenced classes of package com.facebook.katana:
//            UsersTabProgressSource, LoginActivity, ProfileSearchResultsAdapter, TabProgressListener

public abstract class ProfileSearchResultsActivity extends BaseFacebookListActivity
    implements UsersTabProgressSource, android.widget.AdapterView.OnItemClickListener, android.widget.AbsListView.OnScrollListener
{
    protected class ProfileSearchAppSessionListener extends AppSessionListener
    {

        public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
        {
            mSearchAdapter.updatePhoto(bitmap, s2);
        }

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            mSearchAdapter.updatePhoto(bitmap, s);
        }

        final ProfileSearchResultsActivity this$0;

        protected ProfileSearchAppSessionListener()
        {
            this$0 = ProfileSearchResultsActivity.this;
            super();
        }
    }

    protected final class QueryHandler extends AsyncQueryHandler
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
                mSearchAdapter.refreshData(cursor);
            }
        }

        public static final int DELETE_SEARCH_TOKEN = 1;
        public static final int QUERY_SEARCH_TOKEN = 1;
        final ProfileSearchResultsActivity this$0;

        public QueryHandler(Context context)
        {
            this$0 = ProfileSearchResultsActivity.this;
            super(context.getContentResolver());
        }
    }


    public ProfileSearchResultsActivity()
    {
    }

    protected abstract ProfileSearchAppSessionListener getAppSessionListener();

    protected abstract ProfileSearchResultsAdapter getSearchAdapter();

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03002c);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mSearchAdapter = getSearchAdapter();
            ((SectionedListView)getListView()).setSectionedListAdapter(mSearchAdapter);
            setupEmptyView();
            mQueryHandler = new QueryHandler(this);
            mAppSessionListener = getAppSessionListener();
            getListView().setOnItemClickListener(this);
            getListView().setOnScrollListener(this);
        }
    }

    public abstract void onItemClick(AdapterView adapterview, View view, int i, long l);

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
            LoginActivity.toLogin(this);
        else
            mAppSession.addListener(mAppSessionListener);
    }

    public void onScroll(AbsListView abslistview, int i, int j, int k)
    {
        int l = getListView().getLastVisiblePosition();
        if(mSearchAdapter.getCursor() != null)
        {
            int i1 = mSearchAdapter.getCursor().getCount();
            if(l > 0 && l == i1 - 1 && mCurrentQuery != null)
            {
                showProgress(2, true);
                mCurrentQueryId = performSearchRequest(mCurrentQuery, i1, 20);
            }
        }
    }

    public void onScrollStateChanged(AbsListView abslistview, int i)
    {
    }

    public boolean onSearchRequested()
    {
        return true;
    }

    protected abstract String performSearchRequest(String s, int i, int j);

    public void search(String s)
    {
        if(!s.equals(mCurrentQuery))
        {
            mCurrentQuery = s;
            showProgress(2, true);
            mCurrentQueryId = performSearchRequest(mCurrentQuery, 0, 20);
        }
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
        {
            TabProgressListener tabprogresslistener1 = mProgressListener;
            boolean flag;
            if(mProgress != 0)
                flag = true;
            else
                flag = false;
            tabprogresslistener1.onShowProgress(flag);
        }
    }

    protected abstract void setupEmptyView();

    protected void showProgress(int i, boolean flag)
    {
        boolean flag1;
        if(flag)
            mProgress = i | mProgress;
        else
            mProgress = mProgress & ~i;
        if(mProgress != 0)
            flag1 = true;
        else
            flag1 = false;
        if(flag1)
        {
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
        if(mProgressListener != null)
            mProgressListener.onShowProgress(flag1);
    }

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
    protected QueryHandler mQueryHandler;
    protected ProfileSearchResultsAdapter mSearchAdapter;
    protected int mTotalSearchResults;
}

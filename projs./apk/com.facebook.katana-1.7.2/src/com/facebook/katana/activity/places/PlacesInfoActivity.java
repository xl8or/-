// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesInfoActivity.java

package com.facebook.katana.activity.places;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.*;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookPlace;

// Referenced classes of package com.facebook.katana.activity.places:
//            PlacesInfoAdapter

public class PlacesInfoActivity extends ProfileFacebookListActivity
    implements TabProgressSource
{
    private class ActivityBlob
    {

        final FacebookPlace mBlobInfo;
        final PlacesInfoActivity this$0;

        public ActivityBlob(FacebookPlace facebookplace)
        {
            this$0 = PlacesInfoActivity.this;
            super();
            mBlobInfo = facebookplace;
        }
    }


    public PlacesInfoActivity()
    {
    }

    private void handleInfo(FacebookPlace facebookplace)
    {
        mAdapter.setPlaceInfo(facebookplace);
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a015a);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a0157);
    }

    public void onCreate(Bundle bundle)
    {
        mHasFatTitleHeader = true;
        mPlaceInfo = (FacebookPlace)getIntent().getParcelableExtra("extra_place");
        mProfileId = mPlaceInfo.mPageId;
        super.onCreate(bundle);
        setContentView(0x7f030062);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(getIntent().getBooleanExtra("within_tab", false))
                findViewById(0x7f0e0016).setVisibility(8);
            setupListHeaders();
            setupFatTitleHeader();
            mAdapter = new PlacesInfoAdapter(this);
            mAdapter.setPlaceInfo(mPlaceInfo);
            getListView().setAdapter(mAdapter);
            ActivityBlob activityblob = (ActivityBlob)getLastNonConfigurationInstance();
            if(activityblob != null)
                handleInfo(activityblob.mBlobInfo);
            setupEmptyView();
        }
    }

    public void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
            LoginActivity.toLogin(this);
    }

    public Object onRetainNonConfigurationInstance()
    {
        ActivityBlob activityblob;
        if(mPlaceInfo != null)
            activityblob = new ActivityBlob(mPlaceInfo);
        else
            activityblob = null;
        return activityblob;
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
            mProgressListener.onShowProgress(mShowingProgress);
    }

    private PlacesInfoAdapter mAdapter;
    private AppSession mAppSession;
    private FacebookPlace mPlaceInfo;
    private TabProgressListener mProgressListener;
    private boolean mShowingProgress;
}

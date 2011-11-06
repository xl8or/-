// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileInfoActivity.java

package com.facebook.katana;

import android.graphics.Bitmap;
import android.view.*;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.binding.AppSession;

// Referenced classes of package com.facebook.katana:
//            TabProgressSource, LoginActivity, TabProgressListener, ProfileInfoAdapter

public class ProfileInfoActivity extends ProfileFacebookListActivity
    implements TabProgressSource
{
    protected class InfoAppSessionListener extends com.facebook.katana.activity.ProfileFacebookListActivity.FBListActivityAppSessionListener
    {

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            mAdapter.updatePhoto();
            updateFatTitleHeader();
        }

        final ProfileInfoActivity this$0;

        protected InfoAppSessionListener()
        {
            this$0 = ProfileInfoActivity.this;
            super(ProfileInfoActivity.this);
        }
    }


    public ProfileInfoActivity()
    {
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 2, 0, 0x7f0a0177).setIcon(0x7f0200c1);
        return true;
    }

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
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        mAppSession.addListener(mAppSessionListener);
        if(mReqId != null && !mAppSession.isRequestPending(mReqId))
        {
            removeDialog(2);
            mReqId = null;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void setProgressListener(TabProgressListener tabprogresslistener)
    {
        mProgressListener = tabprogresslistener;
        if(mProgressListener != null)
            mProgressListener.onShowProgress(mShowingProgress);
    }

    protected void showProgress(boolean flag)
    {
        if(mProgressListener != null)
            mProgressListener.onShowProgress(flag);
        View view = findViewById(0x7f0e00f1);
        mShowingProgress = flag;
        if(flag)
        {
            if(view != null)
                view.setVisibility(0);
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            if(view != null)
                view.setVisibility(8);
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
    }

    protected static final int MESSAGE_DIALOG = 1;
    protected static final int PROGRESS_DIALOG = 2;
    protected static final int VIEW_PROFILE = 2;
    protected ProfileInfoAdapter mAdapter;
    protected TabProgressListener mProgressListener;
    protected String mReqId;
    protected boolean mShowingProgress;
}

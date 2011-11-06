// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PageInfoActivity.java

package com.facebook.katana;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookPageFull;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.method.FqlGetPageFanStatus;
import com.facebook.katana.service.method.FqlGetPages;
import com.facebook.katana.service.method.PagesAddFan;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;

// Referenced classes of package com.facebook.katana:
//            ProfileInfoActivity, PageInfoAdapter, LoginActivity, ProfileInfoAdapter

public class PageInfoActivity extends ProfileInfoActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class PageInfoAppSessionListener extends ProfileInfoActivity.InfoAppSessionListener
    {

        public void onGetPageFanStatusComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                boolean flag)
        {
            if(!mIsTab && l == 
// JavaClassFileOutputException: get_constant: invalid tag

        public void onPagesAddFanComplete(AppSession appsession, String s, int i, String s1, Exception exception, boolean flag)
        {
            if(i == 200 && s.equals(mPendingLikeReqId) && isOnTop())
            {
                ApplicationUtils.OpenPageProfile(PageInfoActivity.this, 
// JavaClassFileOutputException: get_constant: invalid tag

        public void onPagesGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                Object obj)
        {
            if(l == mPageId)
            {
                showProgress(false);
                if(i == 200)
                {
                    logStepDataReceived();
                    if(obj != null)
                    {
                        if(obj instanceof FacebookPageFull)
                            handleInfo((FacebookPageFull)obj);
                    } else
                    {
                        Toaster.toast(PageInfoActivity.this, 0x7f0a00fc);
                        finish();
                    }
                } else
                {
                    String s2 = StringUtils.getErrorString(PageInfoActivity.this, getString(0x7f0a00fd), i, s1, exception);
                    Toaster.toast(PageInfoActivity.this, s2);
                }
            }
        }

        final PageInfoActivity this$0;

        private PageInfoAppSessionListener()
        {
            this$0 = PageInfoActivity.this;
            super(PageInfoActivity.this);
        }

    }


    public PageInfoActivity()
    {
    }

    private void handleInfo(FacebookPageFull facebookpagefull)
    {
        ((PageInfoAdapter)mAdapter).setPageInfo(facebookpagefull);
        mInfo = facebookpagefull;
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a015a);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a00f6);
    }

    public void onCreate(Bundle bundle)
    {
        if(getIntent().getBooleanExtra("within_tab", false))
            mHasFatTitleHeader = true;
        super.onCreate(bundle);
        setContentView(0x7f03004d);
        mPageId = getIntent().getLongExtra("com.facebook.katana.profile.id", 0L);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            ListView listview;
            if(getParent() != null)
                findViewById(0x7f0e0016).setVisibility(8);
            else
                FqlGetPageFanStatus.RequestPageFanStatus(this, mProfileId);
            setupListHeaders();
            setupFatTitleHeader();
            mAppSessionListener = new PageInfoAppSessionListener();
            mAdapter = new PageInfoAdapter(this, mAppSession.getPhotosCache(), getIntent().getBooleanExtra("com.facebook.katana.profile.show_photo", true));
            listview = getListView();
            listview.setAdapter(mAdapter);
            setupEmptyView();
            listview.setOnItemClickListener(this);
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 2 2: default 20
    //                   2 24;
           goto _L1 _L2
_L1:
        Object obj = null;
_L4:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a00f8));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        ProfileInfoAdapter.Item item = mAdapter.getItemByPosition(getCursorPosition(i));
        item.getType();
        JVM INSTR tableswitch 0 2: default 44
    //                   0 45
    //                   1 44
    //                   2 87;
           goto _L1 _L2 _L1 _L3
_L1:
        return;
_L2:
        if(mAppSession != null && mInfo != null)
        {
            String s = mInfo.mUrl;
            if(s != null)
                mAppSession.openURL(this, s);
        }
        continue; /* Loop/switch isn't completed */
_L3:
        Intent intent = new Intent("android.intent.action.DIAL", Uri.fromParts("tel", item.getSubTitle(), null));
        intent.setFlags(0x10000000);
        startActivity(intent);
        if(true) goto _L1; else goto _L4
_L4:
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 2: default 24
    //                   2 30;
           goto _L1 _L2
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        mAppSession.openURL(this, mInfo.mUrl);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onResume()
    {
        super.onResume();
        if(mInfo == null)
        {
            FqlGetPages.RequestPageInfo(this, (new StringBuilder()).append("page_id = ").append(mPageId).toString(), com/facebook/katana/model/FacebookPageFull);
            logStepDataRequested();
            showProgress(true);
        }
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        if(mCanLike)
        {
            PagesAddFan pagesaddfan = new PagesAddFan(this, null, mAppSession.getSessionInfo().sessionKey, mProfileId, null);
            mPendingLikeReqId = mAppSession.postToService(this, pagesaddfan, 1001, 1020, null);
            setPrimaryActionFace(-1, null);
            showProgress(true);
        }
    }

    private boolean mCanLike;
    private FacebookPageFull mInfo;
    private boolean mIsTab;
    private long mPageId;
    private String mPendingLikeReqId;







/*
    static boolean access$502(PageInfoActivity pageinfoactivity, boolean flag)
    {
        pageinfoactivity.mCanLike = flag;
        return flag;
    }

*/




}

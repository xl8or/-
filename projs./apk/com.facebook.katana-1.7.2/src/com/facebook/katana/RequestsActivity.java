// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RequestsActivity.java

package com.facebook.katana;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.Toaster;
import java.util.LinkedHashMap;
import java.util.Map;

// Referenced classes of package com.facebook.katana:
//            RequestsAdapter, LoginActivity

public class RequestsActivity extends BaseFacebookListActivity
{

    public RequestsActivity()
    {
        mSkipFetchOnResume = false;
        mAppSessionListener = new AppSessionListener() {

            public void onUserGetFriendRequestsComplete(AppSession appsession, String s, int i, String s1, Exception exception, Map map)
            {
                if(map != null) goto _L2; else goto _L1
_L1:
                Toaster.toast(RequestsActivity.this, 0x7f0a0181);
_L4:
                appsession.removeListener(this);
                return;
_L2:
                logStepDataReceived();
                createAdapter(map);
                if(map.size() == 0)
                    showNoRequestors();
                if(true) goto _L4; else goto _L3
_L3:
            }

            final RequestsActivity this$0;

            
            {
                this$0 = RequestsActivity.this;
                super();
            }
        }
;
    }

    private void createAdapter(Map map)
    {
        if(mRequestsAdapter == null)
        {
            mRequestsAdapter = new RequestsAdapter(this, mAppSession, map);
            setListAdapter(mRequestsAdapter);
        } else
        {
            mRequestsAdapter.setupRequestors(map);
            mRequestsAdapter.notifyDataSetChanged();
        }
    }

    private void showNoRequestors()
    {
        View view = (View)getListView().getParent();
        TextView textview = (TextView)view.findViewById(0x7f0e0056);
        textview.setVisibility(0);
        textview.setText(0x7f0a0185);
        view.findViewById(0x7f0e0057).setVisibility(4);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03006e);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this, getIntent());
_L4:
        return;
_L2:
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("extra_frend_requests"))
        {
            android.os.Parcelable aparcelable[] = intent.getExtras().getParcelableArray("extra_frend_requests");
            LinkedHashMap linkedhashmap = new LinkedHashMap();
            int i = aparcelable.length;
            for(int j = 0; j < i; j++)
            {
                FacebookUser facebookuser = (FacebookUser)aparcelable[j];
                linkedhashmap.put(Long.valueOf(facebookuser.mUserId), facebookuser);
            }

            createAdapter(linkedhashmap);
            if(linkedhashmap.size() == 0)
                showNoRequestors();
            mSkipFetchOnResume = true;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(mRequestsAdapter != null)
            mRequestsAdapter.onDestroy();
    }

    protected void onPause()
    {
        super.onPause();
        mSkipFetchOnResume = false;
        if(mRequestsAdapter != null && mRequestsAdapter.mSyncRequired)
        {
            mAppSession.syncFriends(this);
            mRequestsAdapter.mSyncRequired = false;
        }
        mAppSession.removeListener(mAppSessionListener);
    }

    protected void onResume()
    {
        super.onResume();
        if(!mSkipFetchOnResume)
        {
            mAppSession.addListener(mAppSessionListener);
            long l = mAppSession.getSessionInfo().userId;
            mAppSession.getFriendRequests(this, l);
            logStepDataRequested();
        }
    }

    public static final String EXTRA_FRIEND_REQUESTS = "extra_frend_requests";
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private RequestsAdapter mRequestsAdapter;
    private boolean mSkipFetchOnResume;



}

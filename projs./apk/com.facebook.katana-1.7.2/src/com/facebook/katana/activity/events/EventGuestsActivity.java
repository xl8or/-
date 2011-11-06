// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventGuestsActivity.java

package com.facebook.katana.activity.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.ApplicationUtils;
import java.util.Map;

// Referenced classes of package com.facebook.katana.activity.events:
//            EventGuestsAdapter

public class EventGuestsActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class EventsAppSessionListener extends AppSessionListener
    {

        public void onEventGetMembersComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                Map map)
        {
            mAdapter.setItemsInfo(map);
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mAdapter.updateUserImage(profileimage);
        }

        final EventGuestsActivity this$0;

        private EventsAppSessionListener()
        {
            this$0 = EventGuestsActivity.this;
            super();
        }

    }


    public EventGuestsActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03001d);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            ListView listview = getListView();
            mAdapter = new EventGuestsAdapter(this, mAppSession.getUserImagesCache());
            listview.setAdapter(mAdapter);
            mAppSessionListener = new EventsAppSessionListener();
            long l = getIntent().getLongExtra("extra_event_id", -1L);
            mAppSession.eventGetMembers(this, l);
            listview.setOnItemClickListener(this);
        }
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        ApplicationUtils.OpenUserProfile(this, ((EventGuestsAdapter.Item)mAdapter.getItem(i)).getUser().mUserId, null);
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
            LoginActivity.toLogin(this);
        else
            mAppSession.addListener(mAppSessionListener);
    }

    private EventGuestsAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;

}

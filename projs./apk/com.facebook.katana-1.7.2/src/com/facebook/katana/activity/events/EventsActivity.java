// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventsActivity.java

package com.facebook.katana.activity.events;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.provider.EventsProvider;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.events:
//            EventBirthdaysActivity, EventDetailsActivity, EventsAdapter

public class EventsActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class EventsAppSessionListener extends AppSessionListener
    {

        public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
        {
            mAdapter.updatePhoto(bitmap, s2);
        }

        public void onFriendsSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            if(i == 200)
                mAdapter.setItemsAsync();
        }

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            mAdapter.updatePhoto(bitmap, s);
        }

        public void onUserGetEventsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            logStepDataReceived();
            mAdapter.setItemsAsync();
            showProgress(false);
        }

        final EventsActivity this$0;

        private EventsAppSessionListener()
        {
            this$0 = EventsActivity.this;
            super();
        }

    }


    public EventsActivity()
    {
    }

    private void showBirthdaysGroup(EventsAdapter.Item item)
    {
        long al[] = new long[item.getBirthdaysCount()];
        int i = 0;
        for(Iterator iterator = item.getBirthdaysList().iterator(); iterator.hasNext();)
        {
            al[i] = ((EventsAdapter.Birthday)iterator.next()).getUserId().longValue();
            i++;
        }

        Intent intent = new Intent(this, com/facebook/katana/activity/events/EventBirthdaysActivity);
        intent.putExtra("extra_user_ids", al);
        startActivity(intent);
    }

    private void showEventDetails(long l)
    {
        Intent intent = new Intent(this, com/facebook/katana/activity/events/EventDetailsActivity);
        intent.setData(Uri.withAppendedPath(EventsProvider.EVENT_EID_CONTENT_URI, String.valueOf(l)));
        startActivity(intent);
    }

    private void showProgress(boolean flag)
    {
        if(!flag) goto _L2; else goto _L1
_L1:
        findViewById(0x7f0e00f1).setVisibility(0);
        findViewById(0x7f0e0056).setVisibility(8);
_L4:
        return;
_L2:
        findViewById(0x7f0e00f1).setVisibility(8);
        if(mAdapter.getCount() == 0)
        {
            TextView textview = (TextView)findViewById(0x7f0e0056);
            textview.setText(0x7f0a0065);
            textview.setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
        if(true) goto _L4; else goto _L3
_L3:
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
            mUserId = mAppSession.getSessionInfo().userId;
            ListView listview = getListView();
            mAdapter = new EventsAdapter(this, mAppSession.getPhotosCache());
            listview.setAdapter(mAdapter);
            mAppSessionListener = new EventsAppSessionListener();
            listview.setOnItemClickListener(this);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 2, 0, 0x7f0a01c6).setIcon(0x7f0200bc);
        return true;
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        EventsAdapter.Item item = (EventsAdapter.Item)mAdapter.getItem(i);
        if(item.getType() != 1) goto _L2; else goto _L1
_L1:
        showBirthdaysGroup(item);
_L4:
        return;
_L2:
        if(item.getType() == 0)
            showEventDetails(item.getEid());
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 2: default 24
    //                   2 32;
           goto _L1 _L2
_L1:
        boolean flag = super.onOptionsItemSelected(menuitem);
_L4:
        return flag;
_L2:
        refresh();
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onPause()
    {
        super.onPause();
        mAppSession.removeListener(mAppSessionListener);
        mAdapter.stopBucketizeTask();
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(mAppSessionListener == null)
                mAppSessionListener = new EventsAppSessionListener();
            mAppSession.addListener(mAppSessionListener);
            refresh();
            mAdapter.setItemsAsync();
        }
    }

    void refresh()
    {
        showProgress(true);
        mAppSession.getEvents(this, mUserId);
        logStepDataRequested();
    }

    private static final int REFRESH_ID = 2;
    private EventsAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private long mUserId;



}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventBirthdaysActivity.java

package com.facebook.katana.activity.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.util.ApplicationUtils;

// Referenced classes of package com.facebook.katana.activity.events:
//            EventBirthdaysAdapter

public class EventBirthdaysActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class EventsAppSessionListener extends AppSessionListener
    {

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mAdapter.updateUserImage(profileimage);
        }

        final EventBirthdaysActivity this$0;

        private EventsAppSessionListener()
        {
            this$0 = EventBirthdaysActivity.this;
            super();
        }

    }


    public EventBirthdaysActivity()
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
            long al[] = getIntent().getLongArrayExtra("extra_user_ids");
            StringBuilder stringbuilder = new StringBuilder("user_id");
            stringbuilder.append(" IN (");
            for(int i = 0; i < al.length; i++)
            {
                if(i != 0)
                    stringbuilder.append(",");
                stringbuilder.append(al[i]);
            }

            stringbuilder.append(")");
            mAdapter = new EventBirthdaysAdapter(this, mAppSession.getUserImagesCache(), stringbuilder.toString());
            listview.setAdapter(mAdapter);
            mAppSessionListener = new EventsAppSessionListener();
            listview.setOnItemClickListener(this);
        }
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        ApplicationUtils.OpenUserProfile(this, ((EventBirthdaysAdapter.Item)mAdapter.getItem(i)).getUserId(), null);
    }

    protected void onPause()
    {
        super.onPause();
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

    private EventBirthdaysAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;

}

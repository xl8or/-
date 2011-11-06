// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventDetailsActivity.java

package com.facebook.katana.activity.events;

import android.app.Dialog;
import android.content.*;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.Toaster;
import java.net.URLEncoder;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.events:
//            EventGuestsActivity, EventDetailsAdapter

public class EventDetailsActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class EventsAppSessionListener extends AppSessionListener
    {

        public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
        {
            if(mAdapter != null)
                mAdapter.updatePhoto(bitmap, s2);
        }

        public void onEventRsvpComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                boolean flag)
        {
            if(l == mAdapter.getEventId())
            {
                findViewById(0x7f0e0063).setVisibility(8);
                if(i == 200 && flag)
                {
                    mCursor.requery();
                    if(mCursor.moveToFirst())
                        mAdapter.setRsvpStatus(findViewById(0x7f0e0062), mCursor.getInt(11));
                } else
                {
                    Toaster.toast(EventDetailsActivity.this, 0x7f0a0062);
                }
            }
        }

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            if(mAdapter != null)
                mAdapter.updatePhoto(bitmap, s);
        }

        public void onUserGetEventsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            logStepDataReceived();
            prepareEvent(false);
        }

        final EventDetailsActivity this$0;

        private EventsAppSessionListener()
        {
            this$0 = EventDetailsActivity.this;
            super();
        }

    }


    public EventDetailsActivity()
    {
    }

    private void showGuestsList(long l)
    {
        Intent intent = new Intent(this, com/facebook/katana/activity/events/EventGuestsActivity);
        intent.putExtra("extra_event_id", l);
        startActivity(intent);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03001a);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mAppSessionListener = new EventsAppSessionListener();
            mAppSession.addListener(mAppSessionListener);
            prepareEvent(true);
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        CharSequence acharsequence[] = new CharSequence[3];
        acharsequence[0] = getString(0x7f0a0058);
        acharsequence[1] = getString(0x7f0a0063);
        acharsequence[2] = getString(0x7f0a0059);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getString(0x7f0a0057));
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int k)
            {
                mAppSession.eventRsvp(EventDetailsActivity.this, mAdapter.getEventId(), FacebookEvent.getRsvpStatus(k));
                findViewById(0x7f0e0063).setVisibility(0);
                dismissDialog(0);
            }

            final EventDetailsActivity this$0;

            
            {
                this$0 = EventDetailsActivity.this;
                super();
            }
        }
;
        int j = mAdapter.getRsvpStatus();
        if(j == com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.NOT_REPLIED.ordinal())
            j = -1;
        builder.setSingleChoiceItems(acharsequence, j, onclicklistener);
        return builder.create();
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        int j = i - getListView().getHeaderViewsCount();
        if(j >= 0) goto _L2; else goto _L1
_L1:
        view.getId();
        JVM INSTR tableswitch 2131624032 2131624032: default 40
    //                   2131624032 41;
           goto _L3 _L4
_L3:
        return;
_L4:
        showDialog(0);
        continue; /* Loop/switch isn't completed */
_L2:
        EventDetailsAdapter.Item item;
        if(j >= mAdapter.getCount())
            continue; /* Loop/switch isn't completed */
        item = (EventDetailsAdapter.Item)mAdapter.getItem(j);
        item.getType();
        JVM INSTR tableswitch 0 3: default 112
    //                   0 115
    //                   1 112
    //                   2 144
    //                   3 130;
           goto _L5 _L6 _L5 _L7 _L8
_L5:
        break; /* Loop/switch isn't completed */
_L6:
        ApplicationUtils.OpenUserProfile(this, mAdapter.getCreatorId(), null);
        break; /* Loop/switch isn't completed */
_L8:
        showGuestsList(mAdapter.getEventId());
        break; /* Loop/switch isn't completed */
        String s;
        if(false)
            ;
        continue; /* Loop/switch isn't completed */
_L7:
        s = (new StringBuilder()).append("geo:0,0?q=").append(URLEncoder.encode(item.getString())).toString();
        try
        {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(s)));
        }
        catch(ActivityNotFoundException activitynotfoundexception) { }
        if(true) goto _L3; else goto _L9
_L9:
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
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(mAppSessionListener == null)
                mAppSessionListener = new EventsAppSessionListener();
            mAppSession.addListener(mAppSessionListener);
        }
    }

    public void prepareEvent(boolean flag)
    {
        if(getListView().getAdapter() == null)
        {
            mCursor = managedQuery(getIntent().getData(), EventDetailsAdapter.EventQuery.PROJECTION, "", null, null);
            if(mCursor == null || !mCursor.moveToFirst())
            {
                if(flag)
                {
                    mAppSession.getEvents(this, mAppSession.getSessionInfo().userId);
                    logStepDataRequested();
                } else
                {
                    Toaster.toast(this, 0x7f0a0056);
                }
            } else
            {
                mAdapter = new EventDetailsAdapter(this, mAppSession.getPhotosCache(), mCursor, mCursor.getLong(7), mAppSession.getSessionInfo().userId);
                getListView().addFooterView(mAdapter.getFooterView(), null, false);
                getListView().addHeaderView(mAdapter.getHeaderView(), null, false);
                getListView().addHeaderView(mAdapter.getRsvpView(), null, true);
                getListView().addHeaderView(mAdapter.getHeaderDivider(), null, false);
                getListView().setAdapter(mAdapter);
                getListView().setOnItemClickListener(this);
                mAdapter.setEventInfo();
            }
        }
    }

    private final int RSVP_DIALOG = 0;
    private EventDetailsAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private Cursor mCursor;




}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendCheckinsActivity.java

package com.facebook.katana.activity.places;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.TextView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.features.Gatekeeper;
import com.facebook.katana.model.*;
import com.facebook.katana.ui.SectionedListView;
import com.facebook.katana.util.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

// Referenced classes of package com.facebook.katana.activity.places:
//            FriendCheckinsAdapter, PlacesNearbyActivity

public class FriendCheckinsActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class FriendCheckinsListener extends AppSessionListener
    {

        public void onCheckinComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookPost facebookpost)
        {
            if(s.equals(mCheckinReqId))
            {
                mCheckinReqId = null;
                if(i == 200)
                    refresh();
            }
        }

        public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
        {
            if(i == 200)
                mAdapter.updatePhoto(bitmap, s2);
        }

        public void onFriendCheckinsComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            boolean flag = false;
            mProgressVisible = false;
            if(i == 200)
            {
                logStepDataReceived();
                mAdapter.update(list);
                if(list.size() > 0)
                    flag = true;
            } else
            {
                String s2 = StringUtils.getErrorString(FriendCheckinsActivity.this, getString(0x7f0a01b4), i, s1, exception);
                Toaster.toast(FriendCheckinsActivity.this, s2);
            }
            refreshStaticViews(flag);
        }

        public void onGkSettingsGetComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, boolean flag)
        {
            if(i == 200 && "places".equals(s2) && flag)
            {
                mEnabled = true;
                refresh();
                mAdapter.resumeAdapter();
            }
        }

        public void onPhotoDecodeComplete(AppSession appsession, Bitmap bitmap, String s)
        {
            mAdapter.updatePhoto(bitmap, s);
        }

        final FriendCheckinsActivity this$0;

        private FriendCheckinsListener()
        {
            this$0 = FriendCheckinsActivity.this;
            super();
        }

    }


    public FriendCheckinsActivity()
    {
    }

    private void refreshStaticViews(boolean flag)
    {
        TextView textview;
        textview = (TextView)findViewById(0x7f0e0056);
        if(mEnabled)
        {
            textview.setText(0x7f0a025b);
            setPrimaryActionFace(-1, getString(0x7f0a023e));
        } else
        {
            textview.setText(0x7f0a0068);
        }
        if(!mProgressVisible) goto _L2; else goto _L1
_L1:
        findViewById(0x7f0e00f1).setVisibility(0);
        findViewById(0x7f0e0057).setVisibility(0);
        textview.setVisibility(8);
_L4:
        return;
_L2:
        findViewById(0x7f0e00f1).setVisibility(8);
        if(!flag)
        {
            findViewById(0x7f0e0057).setVisibility(8);
            textview.setVisibility(0);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void setupStaticViews()
    {
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a002c);
        refreshStaticViews(false);
    }

    private void setupViews()
    {
        setContentView(0x7f030029);
        setupStaticViews();
        hideSearchButton();
        SectionedListView sectionedlistview = (SectionedListView)getListView();
        sectionedlistview.setDividerHeight(0);
        sectionedlistview.setSectionedListAdapter(mAdapter);
        sectionedlistview.setEmptyView(findViewById(0x1020004));
        sectionedlistview.setOnItemClickListener(this);
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if(j != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        switch(i)
        {
        case 10: // '\n'
            String s = intent.getStringExtra("extra_status_text");
            long al[] = intent.getLongArrayExtra("extra_tagged_ids");
            ArrayList arraylist = new ArrayList();
            int k = al.length;
            for(int l = 0; l < k; l++)
                arraylist.add(Long.valueOf(al[l]));

            FacebookPlace facebookplace = (FacebookPlace)intent.getParcelableExtra("extra_place");
            Location location = (Location)intent.getParcelableExtra("extra_tagged_location");
            String s1 = intent.getStringExtra("extra_status_privacy");
            Long long1 = Long.valueOf(intent.getLongExtra("extra_status_target_id", -1L));
            if(facebookplace == null || location == null)
            {
                String s2;
                String s3;
                if(facebookplace == null)
                    s2 = "NULL";
                else
                    s2 = Long.toString(facebookplace.mPageId);
                if(location == null)
                    s3 = "NULL";
                else
                    s3 = location.toString();
                Utils.reportSoftError("composer_checkin_error", (new StringBuilder()).append("Returned from checkin using ComposerActivity with null place or location. place=").append(s2).append(" location=").append(s3).toString());
                Toaster.toast(this, 0x7f0a0240);
            } else
            {
                try
                {
                    mCheckinReqId = mAppSession.checkin(this, facebookplace, location, s, IntentUtils.primitiveToSet(al), long1, s1);
                }
                catch(JSONException jsonexception)
                {
                    Toaster.toast(this, 0x7f0a0240);
                }
            }
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mAdapter = new FriendCheckinsAdapter(this, mAppSession.getPhotosCache());
            mAppSessionListener = new FriendCheckinsListener();
            setupViews();
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
        FacebookCheckin facebookcheckin = (FacebookCheckin)mAdapter.getItem(i);
        if(facebookcheckin.getDetails().getAppInfo() != null)
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(facebookcheckin.getDetails().getPlaceInfo().getPageInfo().mUrl)));
        else
            ApplicationUtils.OpenPlaceProfile(this, facebookcheckin.getDetails().getPlaceInfo());
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
        mAdapter.suspendAdapter();
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
            mAppSession.addListener(mAppSessionListener);
            Boolean boolean1 = Gatekeeper.get(this, "places");
            if(boolean1 != null && boolean1.booleanValue())
            {
                mEnabled = true;
                refresh();
                mAdapter.resumeAdapter();
            } else
            {
                mEnabled = false;
                mProgressVisible = false;
                refreshStaticViews(false);
            }
        }
    }

    void refresh()
    {
        mProgressVisible = true;
        refreshStaticViews(false);
        mAppSession.getFriendCheckins(this);
        logStepDataRequested();
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        if(Gatekeeper.get(this, "meta_composer").booleanValue())
        {
            Bundle bundle = new Bundle();
            bundle.putBoolean("extra_is_checkin", true);
            launchComposer(null, bundle, Integer.valueOf(10), -1L);
        } else
        {
            Intent intent = new Intent(this, com/facebook/katana/activity/places/PlacesNearbyActivity);
            intent.putExtra("extra_is_checkin", true);
            startActivity(intent);
        }
    }

    protected static final String COMPOSER_CHECKIN_ERROR = "composer_checkin_error";
    private static final int REFRESH_ID = 2;
    protected static final int STRUCTURED_COMPOSER = 10;
    private FriendCheckinsAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mCheckinReqId;
    private boolean mEnabled;
    private boolean mProgressVisible;



/*
    static boolean access$102(FriendCheckinsActivity friendcheckinsactivity, boolean flag)
    {
        friendcheckinsactivity.mProgressVisible = flag;
        return flag;
    }

*/




/*
    static boolean access$402(FriendCheckinsActivity friendcheckinsactivity, boolean flag)
    {
        friendcheckinsactivity.mEnabled = flag;
        return flag;
    }

*/



/*
    static String access$502(FriendCheckinsActivity friendcheckinsactivity, String s)
    {
        friendcheckinsactivity.mCheckinReqId = s;
        return s;
    }

*/
}

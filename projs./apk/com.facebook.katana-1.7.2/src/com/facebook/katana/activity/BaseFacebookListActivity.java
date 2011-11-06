// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseFacebookListActivity.java

package com.facebook.katana.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.view.FacebookListView;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity:
//            FacebookActivity, FacebookActivityDelegate

public class BaseFacebookListActivity extends ListActivity
    implements FacebookActivity
{

    public BaseFacebookListActivity()
    {
        mDelegate = new FacebookActivityDelegate(this);
        mListHeaders = new ArrayList();
    }

    public boolean facebookOnBackPressed()
    {
        finish();
        return true;
    }

    public void finish()
    {
        mDelegate.finish();
        super.finish();
    }

    public long getActivityId()
    {
        return mDelegate.getActivityId();
    }

    protected int getCursorPosition(int i)
    {
        return i - getListView().getHeaderViewsCount();
    }

    protected String getTag()
    {
        return mDelegate.getTag();
    }

    protected void hideSearchButton()
    {
        mDelegate.hideSearchButton();
    }

    public boolean isOnTop()
    {
        return mDelegate.isOnTop();
    }

    protected void launchComposer(Uri uri, Bundle bundle, Integer integer, long l)
    {
        mDelegate.launchComposer(uri, bundle, integer, l);
    }

    protected void logStepDataReceived()
    {
        mDelegate.logStepDataReceived();
        setListViewNextDrawStep(com.facebook.katana.service.method.PerfLogging.Step.UI_DRAWN_FRESH);
    }

    protected void logStepDataRequested()
    {
        mDelegate.logStepDataRequested();
    }

    public void onContentChanged()
    {
        super.onContentChanged();
        mDelegate.onContentChanged();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mDelegate.onCreate(bundle);
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        Boolean boolean1 = mDelegate.onKeyDown(i, keyevent);
        boolean flag;
        if(boolean1 != null)
            flag = boolean1.booleanValue();
        else
            flag = super.onKeyDown(i, keyevent);
        return flag;
    }

    public boolean onKeyUp(int i, KeyEvent keyevent)
    {
        Boolean boolean1 = mDelegate.onKeyUp(i, keyevent);
        boolean flag;
        if(boolean1 != null)
            flag = boolean1.booleanValue();
        else
            flag = super.onKeyUp(i, keyevent);
        return flag;
    }

    protected void onPause()
    {
        mDelegate.onPause();
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        mDelegate.onResume();
    }

    public boolean onSearchRequested()
    {
        return mDelegate.onSearchRequested();
    }

    protected void onStart()
    {
        super.onStart();
        setListViewNextDrawStep(com.facebook.katana.service.method.PerfLogging.Step.UI_DRAWN_STALE);
        setupListView(mDelegate.mActivityId, getTag());
    }

    public void setListEmptyText(int i)
    {
        ((TextView)findViewById(0x7f0e0056)).setText(i);
    }

    protected void setListLoading(boolean flag)
    {
        int i;
        byte byte0;
        if(flag)
            i = 0;
        else
            i = 8;
        if(flag)
            byte0 = 8;
        else
            byte0 = 0;
        findViewById(0x7f0e0057).setVisibility(i);
        findViewById(0x7f0e0056).setVisibility(byte0);
    }

    public void setListLoadingText(int i)
    {
        ((TextView)findViewById(0x7f0e0058)).setText(i);
    }

    public void setListViewNextDrawStep(com.facebook.katana.service.method.PerfLogging.Step step)
    {
        ListView listview = getListView();
        if(listview instanceof FacebookListView)
            ((FacebookListView)listview).setNextDrawStep(step);
    }

    protected void setPrimaryActionFace(int i, String s)
    {
        mDelegate.setPrimaryActionFace(i, s);
    }

    protected void setPrimaryActionIcon(int i)
    {
        mDelegate.setPrimaryActionIcon(i);
    }

    public void setTransitioningToBackground(boolean flag)
    {
        mDelegate.setTransitioningToBackground(flag);
    }

    protected void setupListHeaders()
    {
        if(!mListHeaders.isEmpty())
        {
            LayoutInflater layoutinflater = (LayoutInflater)getSystemService("layout_inflater");
            Integer integer;
            for(Iterator iterator = mListHeaders.iterator(); iterator.hasNext(); getListView().addHeaderView(layoutinflater.inflate(integer.intValue(), null), null, false))
                integer = (Integer)iterator.next();

        }
    }

    public void setupListView(long l, String s)
    {
        ListView listview = getListView();
        if(listview instanceof FacebookListView)
        {
            ((FacebookListView)listview).activityId = l;
            ((FacebookListView)listview).TAG = s;
        }
    }

    public void startActivity(Intent intent)
    {
        mDelegate.startActivity(intent);
        super.startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int i)
    {
        mDelegate.startActivityForResult(intent, i);
        super.startActivityForResult(intent, i);
    }

    public void titleBarClickHandler(View view)
    {
        Intent intent = IntentUriHandler.getIntentForUri(this, "fb://root");
        intent.setFlags(0x4000000);
        startActivity(intent);
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        mDelegate.titleBarPrimaryActionClickHandler(view);
    }

    public void titleBarSearchClickHandler(View view)
    {
        mDelegate.titleBarSearchClickHandler(view);
    }

    private FacebookActivityDelegate mDelegate;
    protected List mListHeaders;
}

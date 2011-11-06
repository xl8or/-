// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyTabHost.java

package com.facebook.katana;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;

public class MyTabHost extends TabHost
{
    private class IntentContentStrategy
        implements ContentStrategy
    {

        public View getContentView()
        {
            if(mLocalActivityManager == null)
                throw new IllegalStateException("Did you forget to call 'public void setup(LocalActivityManager activityGroup)'?");
            Window window = mLocalActivityManager.startActivity(mTag, mIntent);
            View view;
            if(window != null)
                view = window.getDecorView();
            else
                view = null;
            if(mLaunchedView != view && mLaunchedView != null && mLaunchedView.getParent() != null)
                mTabContent.removeView(mLaunchedView);
            mLaunchedView = view;
            if(mLaunchedView != null)
            {
                mLaunchedView.setVisibility(0);
                mLaunchedView.setFocusableInTouchMode(true);
                ((ViewGroup)mLaunchedView).setDescendantFocusability(0x40000);
            }
            return mLaunchedView;
        }

        public void tabClosed()
        {
            if(mLaunchedView != null)
                mLaunchedView.setVisibility(8);
        }

        private final Intent mIntent;
        private View mLaunchedView;
        private final String mTag;
        final MyTabHost this$0;

        private IntentContentStrategy(String s, Intent intent)
        {
            this$0 = MyTabHost.this;
            super();
            mTag = s;
            mIntent = intent;
        }

    }

    private static interface ContentStrategy
    {

        public abstract View getContentView();

        public abstract void tabClosed();
    }

    public class TabSpec
    {

        public TabSpec setContent(Intent intent)
        {
            mContentStrategy = new IntentContentStrategy(tag, intent);
            return this;
        }

        private ContentStrategy mContentStrategy;
        public final String tag;
        final MyTabHost this$0;
        public final View view;


        private TabSpec(String s, View view1)
        {
            this$0 = MyTabHost.this;
            super();
            tag = s;
            view = view1;
            view1.setTag(s);
        }

    }

    public static interface OnTabChangeListener
    {

        public abstract void onTabChanged(String s);
    }


    public MyTabHost(Context context)
    {
        super(context);
        mTabs = null;
        mTabSpecs = new ArrayList(2);
        mCurrentTab = -1;
        mCurrentView = null;
        mLocalActivityManager = null;
        mHandleTouchMode = true;
    }

    public MyTabHost(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mTabs = null;
        mTabSpecs = new ArrayList(2);
        mCurrentTab = -1;
        mCurrentView = null;
        mLocalActivityManager = null;
        mHandleTouchMode = true;
        mCurrentTab = -1;
        mCurrentView = null;
    }

    private void invokeOnTabChangeListener()
    {
        if(mOnTabChangeListener != null)
            mOnTabChangeListener.onTabChanged(getCurrentTabTag());
    }

    public void addTab(TabSpec tabspec)
    {
        if(tabspec.view == null)
            throw new IllegalArgumentException("you must set the tab indicator view.");
        if(tabspec.mContentStrategy == null)
            throw new IllegalArgumentException("you must specify a way to create the tab content");
        View view = tabspec.view;
        view.setOnKeyListener(mTabKeyListener);
        mTabSpecs.add(tabspec);
        if(mCurrentTab == -1)
            setCurrentTab(0);
        mTabs.addView(view);
        view.setLayoutParams(new android.widget.LinearLayout.LayoutParams(0, -1, 1F));
    }

    public void clearAllTabs()
    {
        mCurrentTab = -1;
        mCurrentView = null;
        mTabContent.removeAllViews();
        mTabSpecs.clear();
        requestLayout();
        invalidate();
    }

    public boolean dispatchKeyEvent(KeyEvent keyevent)
    {
        boolean flag = mTabContent.dispatchKeyEvent(keyevent);
        boolean flag1;
        if(!flag && keyevent.getAction() == 0 && keyevent.getKeyCode() == 19 && mCurrentView.hasFocus() && mCurrentView.findFocus().focusSearch(33) == null)
        {
            ((TabSpec)mTabSpecs.get(mCurrentTab)).view.requestFocus();
            playSoundEffect(2);
            flag1 = true;
        } else
        {
            flag1 = flag;
        }
        return flag1;
    }

    public void dispatchWindowFocusChanged(boolean flag)
    {
        mCurrentView.dispatchWindowFocusChanged(flag);
    }

    public int getCurrentTab()
    {
        return mCurrentTab;
    }

    public String getCurrentTabTag()
    {
        String s;
        if(mCurrentTab >= 0 && mCurrentTab < mTabSpecs.size())
            s = ((TabSpec)mTabSpecs.get(mCurrentTab)).tag;
        else
            s = null;
        return s;
    }

    public View getCurrentTabView()
    {
        View view;
        if(mCurrentTab >= 0 && mCurrentTab < mTabSpecs.size())
            view = ((TabSpec)mTabSpecs.get(mCurrentTab)).view;
        else
            view = null;
        return view;
    }

    public View getCurrentView()
    {
        return mCurrentView;
    }

    public FrameLayout getTabContentView()
    {
        return mTabContent;
    }

    public List getTabSpecs()
    {
        return mTabSpecs;
    }

    public void handleTouchMode(boolean flag)
    {
        mHandleTouchMode = flag;
    }

    public TabSpec myNewTabSpec(String s, View view)
    {
        return new TabSpec(s, view);
    }

    public void onTouchModeChanged(boolean flag)
    {
        if(mHandleTouchMode && !flag && (!mCurrentView.hasFocus() || mCurrentView.isFocused()))
            ((TabSpec)mTabSpecs.get(mCurrentTab)).view.requestFocus();
    }

    public void setCurrentTab(int i)
    {
        if(i >= 0 && i < mTabSpecs.size() && i != mCurrentTab)
        {
            ((RadioButton)(RadioButton)((TabSpec)mTabSpecs.get(i)).view).setChecked(true);
            if(mCurrentTab != -1)
                ((TabSpec)mTabSpecs.get(mCurrentTab)).mContentStrategy.tabClosed();
            mCurrentTab = i;
            TabSpec tabspec = (TabSpec)mTabSpecs.get(i);
            ((TabSpec)mTabSpecs.get(mCurrentTab)).view.requestFocus();
            mCurrentView = tabspec.mContentStrategy.getContentView();
            if(mCurrentView.getParent() == null)
                mTabContent.addView(mCurrentView, new android.view.ViewGroup.LayoutParams(-1, -1));
            if(!mTabs.hasFocus())
                mCurrentView.requestFocus();
            invokeOnTabChangeListener();
        }
    }

    public void setCurrentTabByTag(String s)
    {
        int i = 0;
        do
        {
label0:
            {
                if(i < mTabSpecs.size())
                {
                    if(!((TabSpec)mTabSpecs.get(i)).tag.equals(s))
                        break label0;
                    setCurrentTab(i);
                }
                return;
            }
            i++;
        } while(true);
    }

    public void setOnTabChangedListener(OnTabChangeListener ontabchangelistener)
    {
        mOnTabChangeListener = ontabchangelistener;
    }

    public void setup()
    {
        mTabKeyListener = new android.view.View.OnKeyListener() {

            public boolean onKey(View view, int i, KeyEvent keyevent)
            {
                i;
                JVM INSTR lookupswitch 6: default 60
            //                           19: 88
            //                           20: 88
            //                           21: 88
            //                           22: 88
            //                           23: 88
            //                           66: 88;
                   goto _L1 _L2 _L2 _L2 _L2 _L2 _L2
_L1:
                boolean flag;
                mTabContent.requestFocus(2);
                flag = mTabContent.dispatchKeyEvent(keyevent);
_L4:
                return flag;
_L2:
                flag = false;
                if(true) goto _L4; else goto _L3
_L3:
            }

            final MyTabHost this$0;

            
            {
                this$0 = MyTabHost.this;
                super();
            }
        }
;
        mTabContent = (FrameLayout)findViewById(0x1020011);
        if(mTabContent == null)
        {
            throw new RuntimeException("Your TabHost must have a FrameLayout whose id attribute is 'android.R.id.tabcontent'");
        } else
        {
            mTabs = (ViewGroup)findViewById(0x7f0e010b);
            return;
        }
    }

    public void setup(LocalActivityManager localactivitymanager)
    {
        setup();
        mLocalActivityManager = localactivitymanager;
    }

    protected int mCurrentTab;
    private View mCurrentView;
    private boolean mHandleTouchMode;
    protected LocalActivityManager mLocalActivityManager;
    private OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mTabContent;
    private android.view.View.OnKeyListener mTabKeyListener;
    private List mTabSpecs;
    private ViewGroup mTabs;

}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefreshableFacewebWebViewContainer.java

package com.facebook.katana.webview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import android.widget.RelativeLayout;
import com.facebook.katana.util.Utils;
import com.facebook.orca.common.ui.widgets.refreshablelistview.RefreshableListViewItem;
import com.facebook.orca.common.ui.widgets.refreshablelistview.RefreshableListViewState;

// Referenced classes of package com.facebook.katana.webview:
//            FacewebWebView, FacebookWebView, FacebookRpcCall

public class RefreshableFacewebWebViewContainer extends RelativeLayout
{

    public RefreshableFacewebWebViewContainer(Context context)
    {
        super(context);
        mThreshhold = 100;
        mLastOffset = 0;
        mFriction = 0.75D;
        mEnabled = false;
        mLoading = false;
        mFirstLoad = true;
        state = RefreshableListViewState.NORMAL;
        init();
    }

    public RefreshableFacewebWebViewContainer(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mThreshhold = 100;
        mLastOffset = 0;
        mFriction = 0.75D;
        mEnabled = false;
        mLoading = false;
        mFirstLoad = true;
        state = RefreshableListViewState.NORMAL;
        init();
    }

    public RefreshableFacewebWebViewContainer(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mThreshhold = 100;
        mLastOffset = 0;
        mFriction = 0.75D;
        mEnabled = false;
        mLoading = false;
        mFirstLoad = true;
        state = RefreshableListViewState.NORMAL;
        init();
    }

    /**
     * @deprecated Method animate is deprecated
     */

    private void animate(int i, boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        if(mAnimation != null && android.os.Build.VERSION.SDK_INT >= 8)
            mAnimation.cancel();
        mAnimation = new TranslateAnimation(0F, 0F, mLastOffset, i);
        if(flag)
        {
            mAnimation.setDuration(500L);
            mAnimation.setInterpolator(new AccelerateInterpolator());
        }
        if(i == 0)
            mAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {

                public void onAnimationEnd(Animation animation)
                {
                    changeState(RefreshableListViewState.NORMAL);
                }

                public void onAnimationRepeat(Animation animation)
                {
                }

                public void onAnimationStart(Animation animation)
                {
                }

                final RefreshableFacewebWebViewContainer this$0;

            
            {
                this$0 = RefreshableFacewebWebViewContainer.this;
                super();
            }
            }
);
        mAnimation.setFillAfter(true);
        mWv.startAnimation(mAnimation);
        mHeaderView.startAnimation(mAnimation);
        mLastOffset = i;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private void changeState(RefreshableListViewState refreshablelistviewstate)
    {
        state = refreshablelistviewstate;
        mHeaderView.setState(refreshablelistviewstate);
    }

    private void enable()
    {
        mEnabled = true;
        setOverScrollOff();
    }

    private void init()
    {
        mWv = FacewebWebView.newFaceWebView(getContext());
        mWv.setOnTouchListener(new android.view.View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent motionevent)
            {
                return RefreshableFacewebWebViewContainer.this.onTouch(motionevent);
            }

            final RefreshableFacewebWebViewContainer this$0;

            
            {
                this$0 = RefreshableFacewebWebViewContainer.this;
                super();
            }
        }
);
        addView(mWv);
        mWv.registerNativeCallHandler("enablePullToRefresh", new FacebookWebView.NativeCallHandler() {

            public void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                enable();
            }

            final RefreshableFacewebWebViewContainer this$0;

            
            {
                this$0 = RefreshableFacewebWebViewContainer.this;
                super();
            }
        }
);
        mWv.registerNativeCallHandler("pageLoading", new FacebookWebView.NativeCallHandler() {

            public void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                loadStart();
            }

            final RefreshableFacewebWebViewContainer this$0;

            
            {
                this$0 = RefreshableFacewebWebViewContainer.this;
                super();
            }
        }
);
        mWv.registerNativeCallHandler("pageLoaded", new FacebookWebView.NativeCallHandler() {

            public void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
            {
                loadEnd();
            }

            final RefreshableFacewebWebViewContainer this$0;

            
            {
                this$0 = RefreshableFacewebWebViewContainer.this;
                super();
            }
        }
);
        mHeaderView = new RefreshableListViewItem(mWv.getContext());
        mHeaderView.setDirection(0);
        mHeaderView.setState(RefreshableListViewState.NORMAL);
        android.widget.RelativeLayout.LayoutParams layoutparams = new android.widget.RelativeLayout.LayoutParams(-1, mThreshhold);
        layoutparams.leftMargin = 0;
        layoutparams.topMargin = -mThreshhold;
        addView(mHeaderView, layoutparams);
        if(!mFirstLoad)
        {
            loadStart();
            animate(mThreshhold, false);
        }
    }

    private void loadEnd()
    {
        mLoading = false;
        mFirstLoad = true;
        mHeaderView.setLastLoadedTime(System.currentTimeMillis());
        if(state != RefreshableListViewState.LOADING) goto _L2; else goto _L1
_L1:
        changeState(RefreshableListViewState.NORMAL);
        if(mLastOffset != 0) goto _L4; else goto _L3
_L3:
        changeState(RefreshableListViewState.NORMAL);
_L2:
        View view = ((Activity)getContext()).findViewById(0x7f0e006b);
        if(view != null)
            view.setVisibility(8);
        return;
_L4:
        if(mLastOffset <= mThreshhold)
        {
            changeState(RefreshableListViewState.PULL_TO_REFRESH);
            if(mLastOffset == mThreshhold)
                animate(0, true);
        } else
        {
            changeState(RefreshableListViewState.RELEASE_TO_REFRESH);
        }
        if(true) goto _L2; else goto _L5
_L5:
    }

    private void loadStart()
    {
        mLoading = true;
        changeState(RefreshableListViewState.LOADING);
    }

    private void refresh()
    {
        mWv.refresh();
    }

    private void setOverScrollOff()
    {
        if(android.os.Build.VERSION.SDK_INT >= 9)
            mWv.setOverScrollMode(2);
    }

    public FacewebWebView getWebView()
    {
        return mWv;
    }

    public boolean onTouch(MotionEvent motionevent)
    {
        if(mEnabled) goto _L2; else goto _L1
_L1:
        boolean flag1 = mWv.onTouchEvent(motionevent);
_L7:
        return flag1;
_L2:
        int i;
        boolean flag;
        i = motionevent.getAction();
        flag = true;
        i;
        JVM INSTR tableswitch 0 2: default 56
    //                   0 95
    //                   1 150
    //                   2 285;
           goto _L3 _L4 _L5 _L6
_L3:
        class _cls6
        {

            static final int $SwitchMap$com$facebook$orca$common$ui$widgets$refreshablelistview$RefreshableListViewState[];

            static 
            {
                $SwitchMap$com$facebook$orca$common$ui$widgets$refreshablelistview$RefreshableListViewState = new int[RefreshableListViewState.values().length];
                NoSuchFieldError nosuchfielderror3;
                try
                {
                    $SwitchMap$com$facebook$orca$common$ui$widgets$refreshablelistview$RefreshableListViewState[RefreshableListViewState.LOADING.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$orca$common$ui$widgets$refreshablelistview$RefreshableListViewState[RefreshableListViewState.NORMAL.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$facebook$orca$common$ui$widgets$refreshablelistview$RefreshableListViewState[RefreshableListViewState.PULL_TO_REFRESH.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$facebook$orca$common$ui$widgets$refreshablelistview$RefreshableListViewState[RefreshableListViewState.RELEASE_TO_REFRESH.ordinal()] = 4;
_L2:
                return;
                nosuchfielderror3;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        if(flag)
        {
            if(state != RefreshableListViewState.NORMAL)
                motionevent.offsetLocation(0F, -mLastOffset);
            flag1 = mWv.onTouchEvent(motionevent);
        } else
        {
            flag1 = true;
        }
        if(true) goto _L7; else goto _L4
_L4:
        if(state == RefreshableListViewState.NORMAL)
            mTouchDownY = motionevent.getY();
        else
        if(state == RefreshableListViewState.LOADING)
            mTouchDownY = (float)((double)motionevent.getY() - (double)mLastOffset / mFriction);
          goto _L3
_L5:
        if(state == RefreshableListViewState.PULL_TO_REFRESH)
        {
            animate(0, true);
            mWv.setVerticalScrollBarEnabled(true);
            changeState(RefreshableListViewState.NORMAL);
            flag = false;
        } else
        if(state == RefreshableListViewState.RELEASE_TO_REFRESH)
        {
            refresh();
            animate(mThreshhold, true);
            mWv.setVerticalScrollBarEnabled(true);
            flag = false;
        } else
        if(state == RefreshableListViewState.LOADING)
        {
            int j = (int)(mFriction * (double)(motionevent.getY() - mTouchDownY));
            if(mTouchDownY != motionevent.getY() && j > mThreshhold)
            {
                animate(mThreshhold, true);
                flag = false;
            }
        }
          goto _L3
_L6:
        switch(_cls6..SwitchMap.com.facebook.orca.common.ui.widgets.refreshablelistview.RefreshableListViewState[state.ordinal()])
        {
        case 1: // '\001'
            if(mLastOffset < 0)
            {
                animate(0, false);
                mWv.setVerticalScrollBarEnabled(true);
                changeState(RefreshableListViewState.NORMAL);
            } else
            {
                animate((int)(mFriction * (double)(motionevent.getY() - mTouchDownY)), false);
                flag = false;
            }
            break;

        case 2: // '\002'
            if(mWv.getScrollY() == 0 && motionevent.getY() > mTouchDownY && !mLoading)
            {
                mTouchDownY = motionevent.getY();
                animate(1, false);
                changeState(RefreshableListViewState.PULL_TO_REFRESH);
                mWv.setVerticalScrollBarEnabled(false);
                flag = false;
            }
            break;

        case 3: // '\003'
            if(mLastOffset < 0)
            {
                animate(0, false);
                mWv.setVerticalScrollBarEnabled(true);
                changeState(RefreshableListViewState.NORMAL);
            } else
            {
                if(mLastOffset > mThreshhold)
                    changeState(RefreshableListViewState.RELEASE_TO_REFRESH);
                animate((int)(mFriction * (double)(motionevent.getY() - mTouchDownY)), false);
                flag = false;
            }
            break;

        case 4: // '\004'
            if(mLastOffset < 0)
            {
                animate(0, false);
                mWv.setVerticalScrollBarEnabled(true);
                changeState(RefreshableListViewState.NORMAL);
            } else
            {
                if(mLastOffset < mThreshhold)
                    changeState(RefreshableListViewState.PULL_TO_REFRESH);
                animate((int)(mFriction * (double)(motionevent.getY() - mTouchDownY)), false);
                flag = false;
            }
            break;
        }
        if(true) goto _L3; else goto _L8
_L8:
    }

    private static final int ANIMATION_MS = 500;
    private static final int MIN_SDK_FOR_ANIMATION_CANCEL = 8;
    private static final int MIN_SDK_FOR_OVERSCROLL = 9;
    private static final String TAG = Utils.getClassName(com/facebook/katana/webview/RefreshableFacewebWebViewContainer);
    TranslateAnimation mAnimation;
    private boolean mEnabled;
    private boolean mFirstLoad;
    private double mFriction;
    private RefreshableListViewItem mHeaderView;
    private int mLastOffset;
    private boolean mLoading;
    private int mThreshhold;
    private float mTouchDownY;
    private FacewebWebView mWv;
    private RefreshableListViewState state;





}

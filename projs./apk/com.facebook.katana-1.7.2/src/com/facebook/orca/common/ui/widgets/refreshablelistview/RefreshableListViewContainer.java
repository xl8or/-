// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefreshableListViewContainer.java

package com.facebook.orca.common.ui.widgets.refreshablelistview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ListView;
import android.widget.Scroller;

// Referenced classes of package com.facebook.orca.common.ui.widgets.refreshablelistview:
//            RefreshableListViewState, RefreshableListViewItem, RefreshableListView

public class RefreshableListViewContainer extends ViewGroup
{
    public static abstract class OnRefreshListener
    {

        public abstract void onRefresh();

        public OnRefreshListener()
        {
        }
    }


    public RefreshableListViewContainer(Context context)
    {
        super(context);
        state = RefreshableListViewState.NORMAL;
        direction = 0;
        bufferedPixels = 0F;
        desiredHeaderHeightExposed = 0F;
        currentHeaderHeightExposed = 0F;
        init(context, null);
    }

    public RefreshableListViewContainer(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        state = RefreshableListViewState.NORMAL;
        direction = 0;
        bufferedPixels = 0F;
        desiredHeaderHeightExposed = 0F;
        currentHeaderHeightExposed = 0F;
        init(context, attributeset);
    }

    public RefreshableListViewContainer(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        state = RefreshableListViewState.NORMAL;
        direction = 0;
        bufferedPixels = 0F;
        desiredHeaderHeightExposed = 0F;
        currentHeaderHeightExposed = 0F;
        init(context, attributeset);
    }

    private void animateScroll(boolean flag)
    {
        if(state != RefreshableListViewState.NORMAL) goto _L2; else goto _L1
_L1:
        float f = headerHeight + overScrollHeaderHeight;
        int k = Math.max((int)(4000F * (desiredHeaderHeightExposed / f)), 1000);
        int l = (int)desiredHeaderHeightExposed;
        scroller.startScroll(0, l, 0, -l, k);
        invalidate();
_L4:
        return;
_L2:
        if(state == RefreshableListViewState.LOADING)
        {
            if(!flag)
            {
                int i = (int)desiredHeaderHeightExposed;
                int j;
                if(direction == 0)
                    j = headerHeight - i;
                else
                    j = -headerHeight - i;
                scroller.startScroll(0, i, 0, j, 500);
            }
            invalidate();
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void changeState(RefreshableListViewState refreshablelistviewstate)
    {
        if(refreshablelistviewstate != state)
        {
            state = refreshablelistviewstate;
            RefreshableListViewItem refreshablelistviewitem = getHeaderView();
            refreshablelistviewitem.setDirection(direction);
            if(refreshablelistviewstate == RefreshableListViewState.NORMAL)
                refreshablelistviewitem.setState(getAssociatedStateForDirection(direction));
            else
                refreshablelistviewitem.setState(refreshablelistviewstate);
            if(refreshablelistviewstate == RefreshableListViewState.LOADING && onRefreshListener != null)
                onRefreshListener.onRefresh();
        }
    }

    private boolean dispatchMotionEventToListView(MotionEvent motionevent)
    {
        RefreshableListView refreshablelistview = getListView();
        if(refreshablelistview.getVisibility() != 0 || refreshablelistview.getAnimation() != null) goto _L2; else goto _L1
_L1:
        float f2;
        float f3;
        int i;
        int j;
        Rect rect;
        float f = motionevent.getX();
        float f1 = motionevent.getY();
        f2 = f + (float)getScrollX();
        f3 = f1 + (float)getScrollY();
        i = (int)f2;
        j = (int)f3;
        rect = new Rect();
        refreshablelistview.getHitRect(rect);
        if(!rect.contains(i, j)) goto _L2; else goto _L3
_L3:
        boolean flag;
        motionevent.setLocation(f2 - (float)refreshablelistview.getLeft(), f3 - (float)refreshablelistview.getTop());
        flag = refreshablelistview.dispatchTouchEvent(motionevent);
_L5:
        return flag;
_L2:
        flag = true;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private RefreshableListViewState getAssociatedStateForDirection(int i)
    {
        RefreshableListViewState refreshablelistviewstate;
        if(i == 0)
            refreshablelistviewstate = RefreshableListViewState.PULL_TO_REFRESH;
        else
        if(i == 1)
            refreshablelistviewstate = RefreshableListViewState.PUSH_TO_REFRESH;
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown direction: ").append(i).toString());
        return refreshablelistviewstate;
    }

    private RefreshableListViewItem getHeaderView()
    {
        return (RefreshableListViewItem)getChildAt(1);
    }

    private RefreshableListView getListView()
    {
        return (RefreshableListView)getChildAt(2);
    }

    private View getOverScrollHeaderView()
    {
        return getChildAt(0);
    }

    private void handleActionMove(MotionEvent motionevent)
    {
        desiredHeaderHeightExposed = 0.8F * (motionevent.getY() - lastMotionY) + desiredHeaderHeightExposed;
        if(direction == 0)
            desiredHeaderHeightExposed = Math.max(0F, desiredHeaderHeightExposed);
        else
            desiredHeaderHeightExposed = Math.min(0F, desiredHeaderHeightExposed);
        if(state == RefreshableListViewState.LOADING)
            if(direction == 0)
                desiredHeaderHeightExposed = Math.min(desiredHeaderHeightExposed, headerHeight);
            else
                desiredHeaderHeightExposed = Math.max(desiredHeaderHeightExposed, -headerHeight);
    }

    private void handleActionMoveForBuffering(MotionEvent motionevent)
    {
        float f = motionevent.getY() - lastMotionY;
        if(direction == 0)
        {
            if(f > 0F)
                bufferedPixels = f + bufferedPixels;
            else
                bufferedPixels = 0F;
        } else
        if(f < 0F)
            bufferedPixels = bufferedPixels - f;
        else
            bufferedPixels = 0F;
    }

    private void init(Context context, AttributeSet attributeset)
    {
        scroller = new Scroller(context);
        direction = 0;
        bufferedPixelThreadsholdPx = (int)(35F * context.getResources().getDisplayMetrics().density);
    }

    private boolean isFirstItemFullyVisible()
    {
        RefreshableListView refreshablelistview = getListView();
        boolean flag;
        if(desiredHeaderHeightExposed > 0F)
            flag = true;
        else
        if(refreshablelistview.getFirstVisiblePosition() > 0)
        {
            flag = false;
        } else
        {
            View view = refreshablelistview.getChildAt(0);
            if(view != null && view.getTop() < 0)
                flag = false;
            else
                flag = true;
        }
        return flag;
    }

    private boolean isLastItemFullyVisible()
    {
        RefreshableListView refreshablelistview = getListView();
        boolean flag;
        if(desiredHeaderHeightExposed < 0F)
            flag = true;
        else
        if(refreshablelistview.getLastVisiblePosition() != refreshablelistview.getCount() - 1)
        {
            flag = false;
        } else
        {
            View view = refreshablelistview.getChildAt(refreshablelistview.getChildCount() - 1);
            if(view == null)
            {
                flag = false;
            } else
            {
                Rect rect = new Rect();
                Rect rect1 = new Rect();
                refreshablelistview.getDrawingRect(rect1);
                view.getHitRect(rect);
                if(rect.bottom > rect1.bottom)
                    flag = false;
                else
                    flag = true;
            }
        }
        return flag;
    }

    private void maybeAdjustPositions()
    {
        if(scroller.computeScrollOffset())
        {
            desiredHeaderHeightExposed = scroller.getCurrY();
            if(!scroller.isFinished())
                invalidate();
        }
        if(currentHeaderHeightExposed != desiredHeaderHeightExposed) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i = (int)(desiredHeaderHeightExposed - currentHeaderHeightExposed);
        if(i != 0)
        {
            currentHeaderHeightExposed = currentHeaderHeightExposed + (float)i;
            getListView().offsetTopAndBottom(i);
            getHeaderView().offsetTopAndBottom(i);
            getOverScrollHeaderView().offsetTopAndBottom(i);
            invalidate();
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private boolean shouldHandleScrollOfRefreshItem(MotionEvent motionevent)
    {
        boolean flag;
        if(desiredHeaderHeightExposed != 0F)
            flag = true;
        else
        if(direction == 0)
        {
            if(isFirstItemFullyVisible())
            {
                if(motionevent.getY() - lastMotionY > 0F)
                    flag = true;
                else
                    flag = false;
            } else
            {
                flag = false;
            }
        } else
        if(direction == 1)
        {
            if(isLastItemFullyVisible())
            {
                if(motionevent.getY() - lastMotionY < 0F)
                    flag = true;
                else
                    flag = false;
            } else
            {
                flag = false;
            }
        } else
        {
            throw new IllegalStateException((new StringBuilder()).append("Unknown direction: ").append(direction).toString());
        }
        return flag;
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
    {
        return true;
    }

    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        maybeAdjustPositions();
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new android.view.ViewGroup.LayoutParams(-1, -1);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeset)
    {
        return new android.view.ViewGroup.LayoutParams(getContext(), attributeset);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
    {
        return new android.view.ViewGroup.LayoutParams(layoutparams);
    }

    public int getDirection()
    {
        return direction;
    }

    public OnRefreshListener getOnRefreshListener()
    {
        return onRefreshListener;
    }

    public void notifyLoading()
    {
        if(state != RefreshableListViewState.LOADING)
        {
            changeState(RefreshableListViewState.LOADING);
            animateScroll(true);
        }
    }

    public void notifyLoadingFinished()
    {
        if(state == RefreshableListViewState.LOADING)
        {
            changeState(RefreshableListViewState.NORMAL);
            animateScroll(true);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        return true;
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        if(getChildCount() == 3) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(direction != 0)
            break; /* Loop/switch isn't completed */
        int l1 = l - j;
        getListView().layout(0, 0, k - i, l1);
        int i2 = 0 - headerHeight;
        getHeaderView().layout(0, i2, k - i, 0);
        int j2 = i2 - overScrollHeaderHeight;
        getOverScrollHeaderView().layout(0, j2, k - i, i2);
_L4:
        currentHeaderHeightExposed = 0F;
        maybeAdjustPositions();
        if(true) goto _L1; else goto _L3
_L3:
        if(direction == 1)
        {
            int i1 = l - j;
            getListView().layout(0, 0, k - i, i1);
            int j1 = i1 + headerHeight;
            getHeaderView().layout(0, i1, k - i, j1);
            int k1 = j1 + overScrollHeaderHeight;
            getOverScrollHeaderView().layout(0, j1, k - i, k1);
        } else
        {
            throw new IllegalStateException((new StringBuilder()).append("Unknown direction: ").append(direction).toString());
        }
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    protected void onMeasure(int i, int j)
    {
        int k = getChildCount();
        int l = 0;
        int i1 = 0;
        for(int j1 = 0; j1 < k; j1++)
        {
            View view = getChildAt(j1);
            measureChild(view, i, j);
            i1 = Math.max(i1, view.getMeasuredWidth());
            l = Math.max(l, view.getMeasuredHeight());
        }

        int k1 = Math.max(l, getSuggestedMinimumHeight());
        setMeasuredDimension(resolveSize(Math.max(i1, getSuggestedMinimumWidth()), i), resolveSize(k1, j));
        headerHeight = getHeaderView().getMeasuredHeight();
        overScrollHeaderHeight = getOverScrollHeaderView().getMeasuredHeight();
    }

    void onScrollStateChanged(int i)
    {
        currentScrollState = i;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        int i;
        boolean flag;
        i = motionevent.getAction();
        if(state == RefreshableListViewState.NORMAL && currentScrollState == 1 && i == 2 && shouldHandleScrollOfRefreshItem(motionevent))
            changeState(RefreshableListViewState.BUFFERING);
        flag = true;
        i;
        JVM INSTR tableswitch 0 2: default 72
    //                   0 72
    //                   1 101
    //                   2 177;
           goto _L1 _L1 _L2 _L3
_L1:
        lastMotionY = motionevent.getY();
        boolean flag1 = true;
        if(flag)
            flag1 = dispatchMotionEventToListView(motionevent);
        maybeAdjustPositions();
        return flag1;
_L2:
        if(state == RefreshableListViewState.PULL_TO_REFRESH || state == RefreshableListViewState.PUSH_TO_REFRESH || state == RefreshableListViewState.BUFFERING)
        {
            changeState(RefreshableListViewState.NORMAL);
            animateScroll(false);
        } else
        if(state == RefreshableListViewState.RELEASE_TO_REFRESH)
        {
            changeState(RefreshableListViewState.LOADING);
            animateScroll(false);
        }
        bufferedPixels = 0F;
        continue; /* Loop/switch isn't completed */
_L3:
        if(state != RefreshableListViewState.BUFFERING) goto _L5; else goto _L4
_L4:
        handleActionMoveForBuffering(motionevent);
        if(bufferedPixels <= (float)bufferedPixelThreadsholdPx) goto _L7; else goto _L6
_L6:
        changeState(getAssociatedStateForDirection(direction));
_L8:
        flag = false;
        continue; /* Loop/switch isn't completed */
_L7:
        if(bufferedPixels == 0F)
            changeState(RefreshableListViewState.NORMAL);
        if(true) goto _L8; else goto _L5
_L5:
        if(state != RefreshableListViewState.PULL_TO_REFRESH && state != RefreshableListViewState.RELEASE_TO_REFRESH && state != RefreshableListViewState.PUSH_TO_REFRESH) goto _L10; else goto _L9
_L9:
        handleActionMove(motionevent);
        if(desiredHeaderHeightExposed == 0F)
        {
            changeState(RefreshableListViewState.NORMAL);
            continue; /* Loop/switch isn't completed */
        }
        if(Math.abs(desiredHeaderHeightExposed) < (float)headerHeight) goto _L12; else goto _L11
_L11:
        changeState(RefreshableListViewState.RELEASE_TO_REFRESH);
_L13:
        flag = false;
        continue; /* Loop/switch isn't completed */
_L12:
        if(Math.abs(desiredHeaderHeightExposed) < (float)headerHeight)
            changeState(getAssociatedStateForDirection(direction));
        if(true) goto _L13; else goto _L10
_L10:
        if(state == RefreshableListViewState.LOADING && shouldHandleScrollOfRefreshItem(motionevent))
        {
            handleActionMove(motionevent);
            flag = false;
        }
        if(true) goto _L1; else goto _L14
_L14:
    }

    public void setDirection(int i)
    {
        direction = i;
    }

    public void setLastLoadedTime(long l)
    {
        getHeaderView().setLastLoadedTime(l);
    }

    public void setOnRefreshListener(OnRefreshListener onrefreshlistener)
    {
        onRefreshListener = onrefreshlistener;
    }

    private static final int BUFFERED_PIXELS_DP = 35;
    public static final int DIRECTION_PULL = 0;
    public static final int DIRECTION_PUSH = 1;
    private static final String TAG = "RefreshableListViewContainer";
    private int bufferedPixelThreadsholdPx;
    private float bufferedPixels;
    private float currentHeaderHeightExposed;
    private int currentScrollState;
    private float desiredHeaderHeightExposed;
    private int direction;
    private int headerHeight;
    private float lastMotionY;
    private OnRefreshListener onRefreshListener;
    private int overScrollHeaderHeight;
    private Scroller scroller;
    private RefreshableListViewState state;
}

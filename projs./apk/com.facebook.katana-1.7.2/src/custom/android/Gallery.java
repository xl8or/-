// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Gallery.java

package custom.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.view.animation.Transformation;
import android.widget.Scroller;
import android.widget.SpinnerAdapter;
import java.lang.reflect.Field;

// Referenced classes of package custom.android:
//            AbsSpinner

public class Gallery extends AbsSpinner
    implements android.view.GestureDetector.OnGestureListener
{
    public static class LayoutParams extends android.view.ViewGroup.LayoutParams
    {

        public LayoutParams(int i, int j)
        {
            super(i, j);
        }

        public LayoutParams(Context context, AttributeSet attributeset)
        {
            super(context, attributeset);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
        {
            super(layoutparams);
        }
    }

    private class FlingRunnable
        implements Runnable
    {

        private void endFling(boolean flag)
        {
            mScroller.forceFinished(true);
            if(flag)
                scrollIntoSlots();
        }

        private void startCommon()
        {
            removeCallbacks(this);
        }

        public void run()
        {
            if(mItemCount == 0)
            {
                endFling(true);
            } else
            {
                mShouldStopFling = false;
                Scroller scroller = mScroller;
                boolean flag = scroller.computeScrollOffset();
                int i = scroller.getCurrX();
                int j = mLastFlingX - i;
                int k = getPaddingLeft();
                int l = getPaddingRight();
                int j1;
                if(j > 0)
                {
                    mDownTouchPosition = mFirstPosition;
                    j1 = Math.min(getWidth() - k - l - 1, j);
                } else
                {
                    int i1 = getChildCount() - 1;
                    mDownTouchPosition = i1 + mFirstPosition;
                    j1 = Math.max(-(getWidth() - l - k - 1), j);
                }
                trackMotionScroll(j1);
                if(flag && !mShouldStopFling)
                {
                    mLastFlingX = i;
                    post(this);
                } else
                {
                    endFling(true);
                }
            }
        }

        public void startUsingDistance(int i)
        {
            if(i != 0)
            {
                startCommon();
                mLastFlingX = 0;
                mScroller.startScroll(0, 0, -i, 0, mAnimationDuration);
                post(this);
            }
        }

        public void startUsingVelocity(int i)
        {
            if(i != 0)
            {
                startCommon();
                int j;
                if(i < 0)
                    j = 0x7fffffff;
                else
                    j = 0;
                mLastFlingX = j;
                mScroller.fling(j, 0, i, 0, 0, 0x7fffffff, 0, 0x7fffffff);
                post(this);
            }
        }

        public void stop(boolean flag)
        {
            removeCallbacks(this);
            endFling(flag);
        }

        private int mLastFlingX;
        private Scroller mScroller;
        final Gallery this$0;



        public FlingRunnable()
        {
            this$0 = Gallery.this;
            super();
            mScroller = new Scroller(getContext());
        }
    }


    public Gallery(Context context)
    {
        this(context, null);
    }

    public Gallery(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, 0x7f010004);
    }

    public Gallery(Context context, AttributeSet attributeset, int i)
    {
        int l;
        int i1;
        super(context, attributeset, i);
        mSpacing = 0;
        mAnimationDuration = 400;
        FlingRunnable flingrunnable = new FlingRunnable();
        mFlingRunnable = flingrunnable;
        Runnable runnable = new Runnable() {

            public void run()
            {
                mSuppressSelectionChanged = false;
                selectionChanged();
            }

            final Gallery this$0;

            
            {
                this$0 = Gallery.this;
                super();
            }
        }
;
        mDisableSuppressSelectionChangedRunnable = runnable;
        mShouldCallbackDuringFling = true;
        mShouldCallbackOnUnselectedItemClick = true;
        GestureDetector gesturedetector = new GestureDetector(context, this);
        mGestureDetector = gesturedetector;
        mGestureDetector.setIsLongpressEnabled(true);
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, R.styleable.Gallery, i, 0);
        int j = typedarray.getInt(0, -1);
        if(j >= 0)
            setGravity(j);
        int k = typedarray.getInt(1, -1);
        if(k > 0)
            setAnimationDuration(k);
        setSpacing(typedarray.getDimensionPixelOffset(3, 0));
        setUnselectedAlpha(typedarray.getFloat(2, 0.5F));
        typedarray.recycle();
        l = 1024;
        i1 = 2048;
        int j1;
        Field field1 = android/view/ViewGroup.getDeclaredField("FLAG_USE_CHILD_DRAWING_ORDER");
        Field field2 = android/view/ViewGroup.getDeclaredField("FLAG_SUPPORT_STATIC_TRANSFORMATIONS");
        field1.setAccessible(true);
        field2.setAccessible(true);
        l = field1.getInt(this);
        j1 = field2.getInt(this);
        i1 = j1;
_L1:
        Field field = android/view/ViewGroup.getDeclaredField("mGroupFlags");
        field.setAccessible(true);
        field.set(this, Integer.valueOf(i1 | (l | field.getInt(this))));
_L2:
        return;
        NoSuchFieldException nosuchfieldexception1;
        nosuchfieldexception1;
        Log.e("Gallery", nosuchfieldexception1.getMessage(), nosuchfieldexception1);
          goto _L1
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        Log.e("Gallery", illegalaccessexception.getMessage(), illegalaccessexception);
          goto _L1
        NoSuchFieldException nosuchfieldexception;
        nosuchfieldexception;
        Log.e("Gallery", nosuchfieldexception.getMessage(), nosuchfieldexception);
          goto _L2
        IllegalAccessException illegalaccessexception1;
        illegalaccessexception1;
        Log.e("Gallery", illegalaccessexception1.getMessage(), illegalaccessexception1);
          goto _L2
    }

    private int calculateTop(View view, boolean flag)
    {
        int i;
        int j;
        int k;
        if(flag)
            i = getMeasuredHeight();
        else
            i = getHeight();
        if(flag)
            j = view.getMeasuredHeight();
        else
            j = view.getHeight();
        k = 0;
        mGravity;
        JVM INSTR lookupswitch 3: default 60
    //                   16: 92
    //                   48: 80
    //                   80: 131;
           goto _L1 _L2 _L3 _L4
_L1:
        return k;
_L3:
        k = mSpinnerPadding.top;
        continue; /* Loop/switch isn't completed */
_L2:
        int l = i - mSpinnerPadding.bottom - mSpinnerPadding.top - j;
        k = mSpinnerPadding.top + l / 2;
        continue; /* Loop/switch isn't completed */
_L4:
        k = i - mSpinnerPadding.bottom - j;
        if(true) goto _L1; else goto _L5
_L5:
    }

    private void detachOffScreenChildren(boolean flag)
    {
        int i;
        int j;
        int k;
        i = getChildCount();
        j = 0;
        k = 0;
        if(!flag) goto _L2; else goto _L1
_L1:
        int j1;
        int k1;
        j1 = getPaddingLeft();
        k1 = 0;
_L6:
        if(k1 >= i) goto _L4; else goto _L3
_L3:
        View view1 = getChildAt(k1);
        if(view1.getRight() < j1) goto _L5; else goto _L4
_L4:
        detachViewsFromParent(j, k);
        if(flag)
            mFirstPosition = k + mFirstPosition;
        return;
_L5:
        k++;
        mRecycler.add(view1);
        k1++;
          goto _L6
_L2:
        int l;
        int i1;
        l = getWidth() - getPaddingRight();
        i1 = i - 1;
_L9:
        if(i1 < 0) goto _L4; else goto _L7
_L7:
        View view = getChildAt(i1);
        if(view.getLeft() <= l) goto _L4; else goto _L8
_L8:
        j = i1;
        k++;
        mRecycler.add(view);
        i1--;
          goto _L9
    }

    private boolean dispatchLongPress(View view, int i, long l)
    {
        boolean flag = false;
        if(mOnItemLongClickListener != null)
            flag = mOnItemLongClickListener.onItemLongClick(this, mDownTouchView, mDownTouchPosition, l);
        if(!flag)
        {
            mContextMenuInfo = new AdapterView.AdapterContextMenuInfo(view, i, l);
            flag = super.showContextMenuForChild(this);
        }
        if(flag)
            performHapticFeedback(0);
        return flag;
    }

    private void dispatchPress(View view)
    {
        if(view != null)
            view.setPressed(true);
        setPressed(true);
    }

    private void dispatchUnpress()
    {
        for(int i = getChildCount() - 1; i >= 0; i--)
            getChildAt(i).setPressed(false);

        setPressed(false);
    }

    private void fillToGalleryLeft()
    {
        int i = mSpacing;
        int j = getPaddingLeft();
        View view = getChildAt(0);
        int k;
        int l;
        if(view != null)
        {
            k = mFirstPosition - 1;
            l = view.getLeft() - i;
        } else
        {
            k = 0;
            l = getRight() - getLeft() - getPaddingRight();
            mShouldStopFling = true;
        }
        for(; l > j && k >= 0; k--)
        {
            View view1 = makeAndAddView(k, k - mSelectedPosition, l, false);
            mFirstPosition = k;
            l = view1.getLeft() - i;
        }

    }

    private void fillToGalleryRight()
    {
        int i = mSpacing;
        int j = getRight() - getLeft() - getPaddingRight();
        int k = getChildCount();
        int l = mItemCount;
        View view = getChildAt(k - 1);
        int i1;
        int j1;
        if(view != null)
        {
            i1 = k + mFirstPosition;
            j1 = i + view.getRight();
        } else
        {
            i1 = mItemCount - 1;
            mFirstPosition = i1;
            j1 = getPaddingLeft();
            mShouldStopFling = true;
        }
        for(; j1 < j && i1 < l; i1++)
            j1 = i + makeAndAddView(i1, i1 - mSelectedPosition, j1, true).getRight();

    }

    private int getCenterOfGallery()
    {
        int i = getPaddingLeft();
        int j = getPaddingRight();
        return i + (getWidth() - i - j) / 2;
    }

    private static int getCenterOfView(View view)
    {
        return view.getLeft() + view.getWidth() / 2;
    }

    private View makeAndAddView(int i, int j, int k, boolean flag)
    {
        View view = mRecycler.get();
        View view1 = mAdapter.getView(i, view, this);
        setUpChild(view1, j, k, flag);
        return view1;
    }

    private void offsetChildrenLeftAndRight(int i)
    {
        for(int j = getChildCount() - 1; j >= 0; j--)
            getChildAt(j).offsetLeftAndRight(i);

    }

    private void onFinishedMovement()
    {
        if(mSuppressSelectionChanged)
        {
            mSuppressSelectionChanged = false;
            super.selectionChanged();
        }
        invalidate();
    }

    private void scrollIntoSlots()
    {
        if(getChildCount() != 0 && mSelectedChild != null)
        {
            int i = getCenterOfView(mSelectedChild);
            int j = getCenterOfGallery() - i;
            if(j != 0)
                mFlingRunnable.startUsingDistance(j);
            else
                onFinishedMovement();
        }
    }

    private boolean scrollToChild(int i)
    {
        View view = getChildAt(i);
        boolean flag;
        if(view != null)
        {
            int j = getCenterOfGallery() - getCenterOfView(view);
            mFlingRunnable.startUsingDistance(j);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    private void setSelectionToCenterChild()
    {
        View view = mSelectedChild;
        if(mSelectedChild != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i;
        int j;
        int k;
        int l;
        i = getCenterOfGallery();
        if(view.getLeft() <= i && view.getRight() >= i)
            continue; /* Loop/switch isn't completed */
        j = 0x7fffffff;
        k = 0;
        l = getChildCount() - 1;
_L4:
        View view1;
label0:
        {
            if(l >= 0)
            {
                view1 = getChildAt(l);
                if(view1.getLeft() > i || view1.getRight() < i)
                    break label0;
                k = l;
            }
            int i1 = k + mFirstPosition;
            if(i1 != mSelectedPosition)
            {
                setSelectedPositionInt(i1);
                setNextSelectedPositionInt(i1);
                checkSelectionChanged();
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
        int j1 = Math.min(Math.abs(view1.getLeft() - i), Math.abs(view1.getRight() - i));
        if(j1 < j)
        {
            j = j1;
            k = l;
        }
        l--;
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    private void setUpChild(View view, int i, int j, boolean flag)
    {
        LayoutParams layoutparams = (LayoutParams)view.getLayoutParams();
        if(layoutparams == null)
            layoutparams = (LayoutParams)generateDefaultLayoutParams();
        byte byte0;
        boolean flag1;
        int k;
        int l;
        int i1;
        int j1;
        int k1;
        int l1;
        if(flag)
            byte0 = -1;
        else
            byte0 = 0;
        addViewInLayout(view, byte0, layoutparams);
        if(i == 0)
            flag1 = true;
        else
            flag1 = false;
        view.setSelected(flag1);
        k = ViewGroup.getChildMeasureSpec(mHeightMeasureSpec, mSpinnerPadding.top + mSpinnerPadding.bottom, layoutparams.height);
        view.measure(ViewGroup.getChildMeasureSpec(mWidthMeasureSpec, mSpinnerPadding.left + mSpinnerPadding.right, layoutparams.width), k);
        l = calculateTop(view, true);
        i1 = l + view.getMeasuredHeight();
        j1 = view.getMeasuredWidth();
        if(flag)
        {
            k1 = j;
            l1 = k1 + j1;
        } else
        {
            k1 = j - j1;
            l1 = j;
        }
        view.layout(k1, l, l1, i1);
    }

    private void updateSelectedItemMetadata()
    {
        View view;
        View view1;
        view = mSelectedChild;
        view1 = getChildAt(mSelectedPosition - mFirstPosition);
        mSelectedChild = view1;
        if(view1 != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        view1.setSelected(true);
        view1.setFocusable(true);
        if(hasFocus())
            view1.requestFocus();
        if(view != null)
        {
            view.setSelected(false);
            view.setFocusable(false);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
    {
        return layoutparams instanceof LayoutParams;
    }

    protected int computeHorizontalScrollExtent()
    {
        return 1;
    }

    protected int computeHorizontalScrollOffset()
    {
        return mSelectedPosition;
    }

    protected int computeHorizontalScrollRange()
    {
        return mItemCount;
    }

    public boolean dispatchKeyEvent(KeyEvent keyevent)
    {
        return keyevent.dispatch(this, null, null);
    }

    protected void dispatchSetPressed(boolean flag)
    {
        if(mSelectedChild != null)
            mSelectedChild.setPressed(flag);
    }

    public void dispatchSetSelected(boolean flag)
    {
    }

    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams()
    {
        return new LayoutParams(-2, -2);
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeset)
    {
        return new LayoutParams(getContext(), attributeset);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutparams)
    {
        return new LayoutParams(layoutparams);
    }

    protected int getChildDrawingOrder(int i, int j)
    {
        int k = mSelectedPosition - mFirstPosition;
        int l;
        if(k < 0)
            l = j;
        else
        if(j == i - 1)
            l = k;
        else
        if(j >= k)
            l = j + 1;
        else
            l = j;
        return l;
    }

    int getChildHeight(View view)
    {
        return view.getMeasuredHeight();
    }

    protected boolean getChildStaticTransformation(View view, Transformation transformation)
    {
        transformation.clear();
        float f;
        if(view == mSelectedChild)
            f = 1F;
        else
            f = mUnselectedAlpha;
        transformation.setAlpha(f);
        return true;
    }

    protected android.view.ContextMenu.ContextMenuInfo getContextMenuInfo()
    {
        return mContextMenuInfo;
    }

    int getLimitedMotionScrollAmount(boolean flag, int i)
    {
        View view;
        int j1;
        int j;
        if(flag)
            j = mItemCount - 1;
        else
            j = 0;
        view = getChildAt(j - mFirstPosition);
        if(view != null) goto _L2; else goto _L1
_L1:
        j1 = i;
_L4:
        return j1;
_L2:
        int k = getCenterOfView(view);
        int l = getCenterOfGallery();
        if(flag)
        {
            if(k <= l)
            {
                j1 = 0;
                continue; /* Loop/switch isn't completed */
            }
        } else
        if(k >= l)
        {
            j1 = 0;
            continue; /* Loop/switch isn't completed */
        }
        int i1 = l - k;
        if(flag)
            j1 = Math.max(i1, i);
        else
            j1 = Math.min(i1, i);
        if(true) goto _L4; else goto _L3
_L3:
    }

    void layout(int i, boolean flag)
    {
        int j = mSpinnerPadding.left;
        int k = getRight() - getLeft() - mSpinnerPadding.left - mSpinnerPadding.right;
        if(mDataChanged)
            handleDataChanged();
        if(mItemCount == 0)
        {
            resetList();
        } else
        {
            if(mNextSelectedPosition >= 0)
                setSelectedPositionInt(mNextSelectedPosition);
            recycleAllViews();
            detachAllViewsFromParent();
            mFirstPosition = mSelectedPosition;
            View view = makeAndAddView(mSelectedPosition, 0, 0, true);
            view.offsetLeftAndRight((j + k / 2) - view.getWidth() / 2);
            fillToGalleryRight();
            fillToGalleryLeft();
            invalidate();
            checkSelectionChanged();
            mDataChanged = false;
            mNeedSync = false;
            setNextSelectedPositionInt(mSelectedPosition);
            updateSelectedItemMetadata();
        }
    }

    boolean moveNext()
    {
        boolean flag;
        if(mItemCount > 0 && mSelectedPosition < mItemCount - 1)
        {
            scrollToChild(1 + (mSelectedPosition - mFirstPosition));
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    boolean movePrevious()
    {
        boolean flag;
        if(mItemCount > 0 && mSelectedPosition > 0)
        {
            scrollToChild(mSelectedPosition - mFirstPosition - 1);
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    void onCancel()
    {
        onUp();
    }

    public boolean onDown(MotionEvent motionevent)
    {
        mFlingRunnable.stop(false);
        mDownTouchPosition = pointToPosition((int)motionevent.getX(), (int)motionevent.getY());
        if(mDownTouchPosition >= 0)
        {
            mDownTouchView = getChildAt(mDownTouchPosition - mFirstPosition);
            mDownTouchView.setPressed(true);
        }
        mIsFirstScroll = true;
        return true;
    }

    public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        if(!mShouldCallbackDuringFling)
        {
            removeCallbacks(mDisableSuppressSelectionChangedRunnable);
            if(!mSuppressSelectionChanged)
                mSuppressSelectionChanged = true;
        }
        mFlingRunnable.startUsingVelocity((int)(-f));
        return true;
    }

    protected void onFocusChanged(boolean flag, int i, Rect rect)
    {
        super.onFocusChanged(flag, i, rect);
        if(flag && mSelectedChild != null)
            mSelectedChild.requestFocus(i);
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        i;
        JVM INSTR lookupswitch 4: default 44
    //                   21: 53
    //                   22: 70
    //                   23: 87
    //                   66: 87;
           goto _L1 _L2 _L3 _L4 _L4
_L1:
        boolean flag = super.onKeyDown(i, keyevent);
_L5:
        return flag;
_L2:
        if(movePrevious())
            playSoundEffect(1);
        flag = true;
        continue; /* Loop/switch isn't completed */
_L3:
        if(moveNext())
            playSoundEffect(3);
        flag = true;
        if(true) goto _L5; else goto _L4
_L4:
        mReceivedInvokeKeyDown = true;
        if(true) goto _L1; else goto _L6
_L6:
    }

    public boolean onKeyUp(int i, KeyEvent keyevent)
    {
        i;
        JVM INSTR lookupswitch 2: default 28
    //                   23: 37
    //                   66: 37;
           goto _L1 _L2 _L2
_L1:
        boolean flag = super.onKeyUp(i, keyevent);
_L4:
        return flag;
_L2:
        if(mReceivedInvokeKeyDown && mItemCount > 0)
        {
            dispatchPress(mSelectedChild);
            postDelayed(new Runnable() {

                public void run()
                {
                    dispatchUnpress();
                }

                final Gallery this$0;

            
            {
                this$0 = Gallery.this;
                super();
            }
            }
, ViewConfiguration.getPressedStateDuration());
            performItemClick(getChildAt(mSelectedPosition - mFirstPosition), mSelectedPosition, mAdapter.getItemId(mSelectedPosition));
        }
        mReceivedInvokeKeyDown = false;
        flag = true;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        super.onLayout(flag, i, j, k, l);
        mInLayout = true;
        layout(0, false);
        mInLayout = false;
    }

    public void onLongPress(MotionEvent motionevent)
    {
        if(mDownTouchPosition >= 0)
        {
            performHapticFeedback(0);
            long l = getItemIdAtPosition(mDownTouchPosition);
            dispatchLongPress(mDownTouchView, mDownTouchPosition, l);
        }
    }

    public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        getParent().requestDisallowInterceptTouchEvent(true);
        if(mShouldCallbackDuringFling) goto _L2; else goto _L1
_L1:
        if(mIsFirstScroll)
        {
            if(!mSuppressSelectionChanged)
                mSuppressSelectionChanged = true;
            postDelayed(mDisableSuppressSelectionChangedRunnable, 250L);
        }
_L4:
        trackMotionScroll(-1 * (int)f);
        mIsFirstScroll = false;
        return true;
_L2:
        if(mSuppressSelectionChanged)
            mSuppressSelectionChanged = false;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void onShowPress(MotionEvent motionevent)
    {
    }

    public boolean onSingleTapUp(MotionEvent motionevent)
    {
        boolean flag;
        if(mDownTouchPosition >= 0)
        {
            scrollToChild(mDownTouchPosition - mFirstPosition);
            if(mShouldCallbackOnUnselectedItemClick || mDownTouchPosition == mSelectedPosition)
                performItemClick(mDownTouchView, mDownTouchPosition, mAdapter.getItemId(mDownTouchPosition));
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        boolean flag;
        int i;
        flag = mGestureDetector.onTouchEvent(motionevent);
        i = motionevent.getAction();
        if(i != 1) goto _L2; else goto _L1
_L1:
        onUp();
_L4:
        return flag;
_L2:
        if(i == 3)
            onCancel();
        if(true) goto _L4; else goto _L3
_L3:
    }

    void onUp()
    {
        if(mFlingRunnable.mScroller.isFinished())
            scrollIntoSlots();
        dispatchUnpress();
    }

    void selectionChanged()
    {
        if(!mSuppressSelectionChanged)
            super.selectionChanged();
    }

    public void setAnimationDuration(int i)
    {
        mAnimationDuration = i;
    }

    public void setCallbackDuringFling(boolean flag)
    {
        mShouldCallbackDuringFling = flag;
    }

    public void setCallbackOnUnselectedItemClick(boolean flag)
    {
        mShouldCallbackOnUnselectedItemClick = flag;
    }

    public void setGravity(int i)
    {
        if(mGravity != i)
        {
            mGravity = i;
            requestLayout();
        }
    }

    void setSelectedPositionInt(int i)
    {
        super.setSelectedPositionInt(i);
        updateSelectedItemMetadata();
    }

    public void setSpacing(int i)
    {
        mSpacing = i;
    }

    public void setUnselectedAlpha(float f)
    {
        mUnselectedAlpha = f;
    }

    public boolean showContextMenu()
    {
        boolean flag;
        if(isPressed() && mSelectedPosition >= 0)
            flag = dispatchLongPress(getChildAt(mSelectedPosition - mFirstPosition), mSelectedPosition, mSelectedRowId);
        else
            flag = false;
        return flag;
    }

    public boolean showContextMenuForChild(View view)
    {
        int i = getPositionForView(view);
        boolean flag;
        if(i < 0)
            flag = false;
        else
            flag = dispatchLongPress(view, i, mAdapter.getItemId(i));
        return flag;
    }

    void trackMotionScroll(int i)
    {
        if(getChildCount() != 0)
        {
            boolean flag;
            int j;
            if(i < 0)
                flag = true;
            else
                flag = false;
            j = getLimitedMotionScrollAmount(flag, i);
            if(j != i)
            {
                mFlingRunnable.endFling(false);
                onFinishedMovement();
            }
            offsetChildrenLeftAndRight(j);
            detachOffScreenChildren(flag);
            if(flag)
                fillToGalleryRight();
            else
                fillToGalleryLeft();
            setSelectionToCenterChild();
            invalidate();
        }
    }

    private static final int SCROLL_TO_FLING_UNCERTAINTY_TIMEOUT = 250;
    private static final String TAG = "Gallery";
    private static final boolean localLOGV;
    private int mAnimationDuration;
    private AdapterView.AdapterContextMenuInfo mContextMenuInfo;
    private Runnable mDisableSuppressSelectionChangedRunnable;
    private int mDownTouchPosition;
    private View mDownTouchView;
    private FlingRunnable mFlingRunnable;
    private GestureDetector mGestureDetector;
    private int mGravity;
    private boolean mIsFirstScroll;
    private boolean mReceivedInvokeKeyDown;
    private View mSelectedChild;
    private boolean mShouldCallbackDuringFling;
    private boolean mShouldCallbackOnUnselectedItemClick;
    private boolean mShouldStopFling;
    private int mSpacing;
    private boolean mSuppressSelectionChanged;
    private float mUnselectedAlpha;


/*
    static boolean access$002(Gallery gallery, boolean flag)
    {
        gallery.mSuppressSelectionChanged = flag;
        return flag;
    }

*/






/*
    static boolean access$602(Gallery gallery, boolean flag)
    {
        gallery.mShouldStopFling = flag;
        return flag;
    }

*/


/*
    static int access$702(Gallery gallery, int i)
    {
        gallery.mDownTouchPosition = i;
        return i;
    }

*/
}

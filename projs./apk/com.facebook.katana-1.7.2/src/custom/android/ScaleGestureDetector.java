// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScaleGestureDetector.java

package custom.android;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class ScaleGestureDetector
{
    public class SimpleOnScaleGestureListener
        implements OnScaleGestureListener
    {

        public boolean onScale(ScaleGestureDetector scalegesturedetector)
        {
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scalegesturedetector)
        {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scalegesturedetector)
        {
        }

        final ScaleGestureDetector this$0;

        public SimpleOnScaleGestureListener()
        {
            this$0 = ScaleGestureDetector.this;
            super();
        }
    }

    public static interface OnScaleGestureListener
    {

        public abstract boolean onScale(ScaleGestureDetector scalegesturedetector);

        public abstract boolean onScaleBegin(ScaleGestureDetector scalegesturedetector);

        public abstract void onScaleEnd(ScaleGestureDetector scalegesturedetector);
    }


    public ScaleGestureDetector(Context context, OnScaleGestureListener onscalegesturelistener)
    {
        ViewConfiguration viewconfiguration = ViewConfiguration.get(context);
        mContext = context;
        mListener = onscalegesturelistener;
        mEdgeSlop = viewconfiguration.getScaledEdgeSlop();
    }

    private static float getRawX(MotionEvent motionevent, int i)
    {
        return (motionevent.getX() - motionevent.getRawX()) + motionevent.getX(i);
    }

    private static float getRawY(MotionEvent motionevent, int i)
    {
        return (motionevent.getY() - motionevent.getRawY()) + motionevent.getY(i);
    }

    private void reset()
    {
        if(mPrevEvent != null)
        {
            mPrevEvent.recycle();
            mPrevEvent = null;
        }
        if(mCurrEvent != null)
        {
            mCurrEvent.recycle();
            mCurrEvent = null;
        }
        mSloppyGesture = false;
        mGestureInProgress = false;
    }

    private void setContext(MotionEvent motionevent)
    {
        if(mCurrEvent != null)
            mCurrEvent.recycle();
        mCurrEvent = MotionEvent.obtain(motionevent);
        mCurrLen = -1F;
        mPrevLen = -1F;
        mScaleFactor = -1F;
        MotionEvent motionevent1 = mPrevEvent;
        float f = motionevent1.getX(0);
        float f1 = motionevent1.getY(0);
        float f2 = motionevent1.getX(1);
        float f3 = motionevent1.getY(1);
        float f4 = motionevent.getX(0);
        float f5 = motionevent.getY(0);
        float f6 = motionevent.getX(1);
        float f7 = motionevent.getY(1);
        float f8 = f2 - f;
        float f9 = f3 - f1;
        float f10 = f6 - f4;
        float f11 = f7 - f5;
        mPrevFingerDiffX = f8;
        mPrevFingerDiffY = f9;
        mCurrFingerDiffX = f10;
        mCurrFingerDiffY = f11;
        mFocusX = f4 + 0.5F * f10;
        mFocusY = f5 + 0.5F * f11;
        mTimeDelta = motionevent.getEventTime() - motionevent1.getEventTime();
        mCurrPressure = motionevent.getPressure(0) + motionevent.getPressure(1);
        mPrevPressure = motionevent1.getPressure(0) + motionevent1.getPressure(1);
    }

    public float getCurrentSpan()
    {
        if(mCurrLen == -1F)
        {
            float f = mCurrFingerDiffX;
            float f1 = mCurrFingerDiffY;
            mCurrLen = (float)Math.sqrt(f * f + f1 * f1);
        }
        return mCurrLen;
    }

    public long getEventTime()
    {
        return mCurrEvent.getEventTime();
    }

    public float getFocusX()
    {
        return mFocusX;
    }

    public float getFocusY()
    {
        return mFocusY;
    }

    public float getPreviousSpan()
    {
        if(mPrevLen == -1F)
        {
            float f = mPrevFingerDiffX;
            float f1 = mPrevFingerDiffY;
            mPrevLen = (float)Math.sqrt(f * f + f1 * f1);
        }
        return mPrevLen;
    }

    public float getScaleFactor()
    {
        if(mScaleFactor == -1F)
            mScaleFactor = getCurrentSpan() / getPreviousSpan();
        return mScaleFactor;
    }

    public long getTimeDelta()
    {
        return mTimeDelta;
    }

    public boolean isInProgress()
    {
        return mGestureInProgress;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        int i = motionevent.getAction();
        if(mGestureInProgress) goto _L2; else goto _L1
_L1:
        if(i != 5 && i != 261 || motionevent.getPointerCount() < 2) goto _L4; else goto _L3
_L3:
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        mRightSlopEdge = (float)displaymetrics.widthPixels - mEdgeSlop;
        mBottomSlopEdge = (float)displaymetrics.heightPixels - mEdgeSlop;
        reset();
        mPrevEvent = MotionEvent.obtain(motionevent);
        mTimeDelta = 0L;
        setContext(motionevent);
        float f7 = mEdgeSlop;
        float f8 = mRightSlopEdge;
        float f9 = mBottomSlopEdge;
        float f10 = motionevent.getRawX();
        float f11 = motionevent.getRawY();
        float f12 = getRawX(motionevent, 1);
        float f13 = getRawY(motionevent, 1);
        boolean flag2;
        boolean flag3;
        if(f10 < f7 || f11 < f7 || f10 > f8 || f11 > f9)
            flag2 = true;
        else
            flag2 = false;
        if(f12 < f7 || f13 < f7 || f12 > f8 || f13 > f9)
            flag3 = true;
        else
            flag3 = false;
        if(flag2 && flag3)
        {
            mFocusX = -1F;
            mFocusY = -1F;
            mSloppyGesture = true;
        } else
        if(flag2)
        {
            mFocusX = motionevent.getX(1);
            mFocusY = motionevent.getY(1);
            mSloppyGesture = true;
        } else
        if(flag3)
        {
            mFocusX = motionevent.getX(0);
            mFocusY = motionevent.getY(0);
            mSloppyGesture = true;
        } else
        {
            mGestureInProgress = mListener.onScaleBegin(this);
        }
_L6:
        return true;
_L4:
        if(i == 2 && mSloppyGesture)
        {
            float f = mEdgeSlop;
            float f1 = mRightSlopEdge;
            float f2 = mBottomSlopEdge;
            float f3 = motionevent.getRawX();
            float f4 = motionevent.getRawY();
            float f5 = getRawX(motionevent, 1);
            float f6 = getRawY(motionevent, 1);
            boolean flag;
            boolean flag1;
            if(f3 < f || f4 < f || f3 > f1 || f4 > f2)
                flag = true;
            else
                flag = false;
            if(f5 < f || f6 < f || f5 > f1 || f6 > f2)
                flag1 = true;
            else
                flag1 = false;
            if(flag && flag1)
            {
                mFocusX = -1F;
                mFocusY = -1F;
            } else
            if(flag)
            {
                mFocusX = motionevent.getX(1);
                mFocusY = motionevent.getY(1);
            } else
            if(flag1)
            {
                mFocusX = motionevent.getX(0);
                mFocusY = motionevent.getY(0);
            } else
            {
                mSloppyGesture = false;
                mGestureInProgress = mListener.onScaleBegin(this);
            }
        } else
        if((i == 6 || i == 262) && mSloppyGesture)
        {
            int k;
            if((0xff00 & i) >> 8 == 0)
                k = 1;
            else
                k = 0;
            mFocusX = motionevent.getX(k);
            mFocusY = motionevent.getY(k);
        }
        continue; /* Loop/switch isn't completed */
_L2:
        switch(i)
        {
        default:
            break;

        case 2: // '\002'
            setContext(motionevent);
            if(mCurrPressure / mPrevPressure > 0.67F && mListener.onScale(this))
            {
                mPrevEvent.recycle();
                mPrevEvent = MotionEvent.obtain(motionevent);
            }
            break;

        case 6: // '\006'
        case 262: 
            setContext(motionevent);
            int j;
            if((0xff00 & i) >> 8 == 0)
                j = 1;
            else
                j = 0;
            mFocusX = motionevent.getX(j);
            mFocusY = motionevent.getY(j);
            if(!mSloppyGesture)
                mListener.onScaleEnd(this);
            reset();
            break;

        case 3: // '\003'
            if(!mSloppyGesture)
                mListener.onScaleEnd(this);
            reset();
            break;
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    private static final float PRESSURE_THRESHOLD = 0.67F;
    private float mBottomSlopEdge;
    private Context mContext;
    private MotionEvent mCurrEvent;
    private float mCurrFingerDiffX;
    private float mCurrFingerDiffY;
    private float mCurrLen;
    private float mCurrPressure;
    private float mEdgeSlop;
    private float mFocusX;
    private float mFocusY;
    private boolean mGestureInProgress;
    private OnScaleGestureListener mListener;
    private MotionEvent mPrevEvent;
    private float mPrevFingerDiffX;
    private float mPrevFingerDiffY;
    private float mPrevLen;
    private float mPrevPressure;
    private float mRightSlopEdge;
    private float mScaleFactor;
    private boolean mSloppyGesture;
    private long mTimeDelta;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScaleGestureDetector.java

package com.facebook.katana.util;

import android.content.Context;
import android.view.MotionEvent;

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
        mContext = context;
        mListener = onscalegesturelistener;
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
        if((i == 5 || i == 261) && motionevent.getPointerCount() >= 2)
        {
            reset();
            mPrevEvent = MotionEvent.obtain(motionevent);
            mTimeDelta = 0L;
            setContext(motionevent);
            mGestureInProgress = mListener.onScaleBegin(this);
        }
_L4:
        return true;
_L2:
        switch(i)
        {
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
            mListener.onScaleEnd(this);
            mGestureInProgress = false;
            reset();
            break;

        case 3: // '\003'
            mListener.onScaleEnd(this);
            mGestureInProgress = false;
            reset();
            break;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static final float PRESSURE_THRESHOLD = 0.67F;
    private final Context mContext;
    private MotionEvent mCurrEvent;
    private float mCurrFingerDiffX;
    private float mCurrFingerDiffY;
    private float mCurrLen;
    private float mCurrPressure;
    private float mFocusX;
    private float mFocusY;
    private boolean mGestureInProgress;
    private final OnScaleGestureListener mListener;
    private MotionEvent mPrevEvent;
    private float mPrevFingerDiffX;
    private float mPrevFingerDiffY;
    private float mPrevLen;
    private float mPrevPressure;
    private float mScaleFactor;
    private long mTimeDelta;
}

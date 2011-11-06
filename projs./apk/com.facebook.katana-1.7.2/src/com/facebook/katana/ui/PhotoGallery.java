// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotoGallery.java

package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.*;
import custom.android.Gallery;
import custom.android.ScaleGestureDetector;

// Referenced classes of package com.facebook.katana.ui:
//            ImageViewTouchBase

public class PhotoGallery extends Gallery
    implements custom.android.ScaleGestureDetector.OnScaleGestureListener, android.view.GestureDetector.OnDoubleTapListener
{

    public PhotoGallery(Context context, AttributeSet attributeset)
    {
        super(context, attributeset, 0);
        mScaleDetector = new ScaleGestureDetector(context, this);
        (new GestureDetector(this)).setOnDoubleTapListener(this);
        setSpacing(100);
    }

    private boolean isScrollingLeft(MotionEvent motionevent, MotionEvent motionevent1)
    {
        boolean flag;
        if(motionevent1.getX() > motionevent.getX())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void destroy()
    {
        mScaleDetector = null;
    }

    public boolean onDoubleTap(MotionEvent motionevent)
    {
        if(mView.getScale() > 2F)
            mView.zoomTo(1F);
        else
            mView.zoomToPoint(3F, motionevent.getX(), motionevent.getY());
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionevent)
    {
        return false;
    }

    public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        boolean flag;
        if(mView.getImageRight() > (float)mView.getWidth() && !isScrollingLeft(motionevent, motionevent1))
            flag = false;
        else
        if(mView.getImageLeft() < 0F && isScrollingLeft(motionevent, motionevent1))
        {
            flag = false;
        } else
        {
            byte byte0;
            if(isScrollingLeft(motionevent, motionevent1))
                byte0 = 21;
            else
                byte0 = 22;
            onKeyDown(byte0, null);
            flag = true;
        }
        return flag;
    }

    public boolean onScale(ScaleGestureDetector scalegesturedetector)
    {
        float f = scalegesturedetector.getScaleFactor() * mView.getScale();
        mView.zoomTo(f, scalegesturedetector.getFocusX(), scalegesturedetector.getFocusY());
        return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector scalegesturedetector)
    {
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector scalegesturedetector)
    {
    }

    public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        boolean flag;
        if(mView.getScale() > 1.5F || mView.getImageRight() > (float)mView.getWidth() && !isScrollingLeft(motionevent, motionevent1) || mView.getImageLeft() < 0F && isScrollingLeft(motionevent, motionevent1))
        {
            mView.postTranslateCenter(-f, -f1);
            flag = true;
        } else
        {
            flag = super.onScroll(motionevent, motionevent1, f, f1);
        }
        return flag;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionevent)
    {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        boolean flag1;
        if(getChildCount() == 0)
        {
            flag1 = false;
        } else
        {
            View view = getSelectedView();
            if(view == null)
            {
                flag1 = super.onTouchEvent(motionevent);
            } else
            {
                mView = (ImageViewTouchBase)(ImageViewTouchBase)view.findViewById(0x7f0e002d);
                mScaleDetector.onTouchEvent(motionevent);
                boolean flag = mScaleDetector.isInProgress();
                if(!flag)
                    flag = super.onTouchEvent(motionevent);
                flag1 = flag;
            }
        }
        return flag1;
    }

    private static final int DEFAULT_SPACING = 100;
    private static final float MAX_ZOOM_FOR_SWIPE = 1.5F;
    ScaleGestureDetector mScaleDetector;
    ImageViewTouchBase mView;
}

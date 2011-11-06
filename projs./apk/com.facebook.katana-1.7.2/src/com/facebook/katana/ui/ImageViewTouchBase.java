// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageViewTouchBase.java

package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.facebook.katana.RotateBitmap;

public class ImageViewTouchBase extends ImageView
{
    public static interface Recycler
    {

        public abstract void recycle(Bitmap bitmap);
    }


    public ImageViewTouchBase(Context context)
    {
        super(context);
        mBaseMatrix = new Matrix();
        mSuppMatrix = new Matrix();
        mDisplayMatrix = new Matrix();
        mMatrixValues = new float[9];
        mBitmapDisplayed = new RotateBitmap(null);
        mThisWidth = -1;
        mThisHeight = -1;
        mStretch = true;
        mMaxZoom = 4F;
        mHandler = new Handler();
        mOnLayoutRunnable = null;
        setScaleType(android.widget.ImageView.ScaleType.MATRIX);
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mBaseMatrix = new Matrix();
        mSuppMatrix = new Matrix();
        mDisplayMatrix = new Matrix();
        mMatrixValues = new float[9];
        mBitmapDisplayed = new RotateBitmap(null);
        mThisWidth = -1;
        mThisHeight = -1;
        mStretch = true;
        mMaxZoom = 4F;
        mHandler = new Handler();
        mOnLayoutRunnable = null;
        setScaleType(android.widget.ImageView.ScaleType.MATRIX);
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mBaseMatrix = new Matrix();
        mSuppMatrix = new Matrix();
        mDisplayMatrix = new Matrix();
        mMatrixValues = new float[9];
        mBitmapDisplayed = new RotateBitmap(null);
        mThisWidth = -1;
        mThisHeight = -1;
        mStretch = true;
        mMaxZoom = 4F;
        mHandler = new Handler();
        mOnLayoutRunnable = null;
        setScaleType(android.widget.ImageView.ScaleType.MATRIX);
    }

    private float correctedZoomScale(float f)
    {
        float f1 = f;
        if(f1 <= mMaxZoom) goto _L2; else goto _L1
_L1:
        f1 = mMaxZoom;
_L4:
        return f1;
_L2:
        if(f1 < MIN_ZOOM_SCALE)
            f1 = MIN_ZOOM_SCALE;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void getProperBaseMatrix(RotateBitmap rotatebitmap, Matrix matrix)
    {
        float f = getWidth();
        float f1 = getHeight();
        float f2 = rotatebitmap.getWidth();
        float f3 = rotatebitmap.getHeight();
        matrix.reset();
        matrix.postConcat(rotatebitmap.getRotateMatrix());
        if(mStretch)
        {
            float f4 = Math.min(Math.min(f / f2, 10F), Math.min(f1 / f3, 10F));
            matrix.postScale(f4, f4);
            matrix.postTranslate((f - f2 * f4) / 2F, (f1 - f3 * f4) / 2F);
        } else
        {
            matrix.postTranslate((f - f2) / 2F, (f1 - f3) / 2F);
        }
    }

    private void setImageBitmap(Bitmap bitmap, int i)
    {
        super.setImageBitmap(bitmap);
        Drawable drawable = getDrawable();
        if(drawable != null)
            drawable.setDither(true);
        Bitmap bitmap1 = mBitmapDisplayed.getBitmap();
        mBitmapDisplayed.setBitmap(bitmap);
        mBitmapDisplayed.setRotation(i);
        if(bitmap1 != null && bitmap1 != bitmap && mRecycler != null)
            mRecycler.recycle(bitmap1);
    }

    void D(String s)
    {
    }

    protected void center(boolean flag, boolean flag1)
    {
        if(mBitmapDisplayed.getBitmap() != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        RectF rectf;
        float f2;
        int i;
        Matrix matrix = getImageViewMatrix();
        rectf = new RectF(0F, 0F, mBitmapDisplayed.getBitmap().getWidth(), mBitmapDisplayed.getBitmap().getHeight());
        matrix.mapRect(rectf);
        float f = rectf.height();
        float f1 = rectf.width();
        f2 = 0F;
        float f3 = 0F;
        if(flag1)
        {
            int j = getHeight();
            if(f < (float)j)
                f3 = ((float)j - f) / 2F - rectf.top;
            else
            if(rectf.top > 0F)
                f3 = -rectf.top;
            else
            if(rectf.bottom < (float)j)
                f3 = (float)getHeight() - rectf.bottom;
        }
        if(flag)
        {
            i = getWidth();
            if(f1 >= (float)i)
                break; /* Loop/switch isn't completed */
            f2 = ((float)i - f1) / 2F - rectf.left;
        }
_L4:
        D((new StringBuilder()).append("center() delta: ").append(f2).append(", ").append(f3).toString());
        postTranslate(f2, f3);
        setImageMatrix(getImageViewMatrix());
        if(true) goto _L1; else goto _L3
_L3:
        if(rectf.left > 0F)
            f2 = -rectf.left;
        else
        if(rectf.right < (float)i)
            f2 = (float)i - rectf.right;
          goto _L4
        if(true) goto _L1; else goto _L5
_L5:
    }

    public void clear()
    {
        setImageBitmapResetBase(null, true);
    }

    public float getImageLeft()
    {
        float f;
        if(mBitmapDisplayed.getBitmap() == null)
        {
            f = 0F;
        } else
        {
            Matrix matrix = getImageViewMatrix();
            RectF rectf = new RectF(0F, 0F, mBitmapDisplayed.getBitmap().getWidth(), mBitmapDisplayed.getBitmap().getHeight());
            matrix.mapRect(rectf);
            f = rectf.left;
        }
        return f;
    }

    public float getImageRight()
    {
        float f;
        if(mBitmapDisplayed.getBitmap() == null)
        {
            f = 0F;
        } else
        {
            Matrix matrix = getImageViewMatrix();
            RectF rectf = new RectF(0F, 0F, mBitmapDisplayed.getBitmap().getWidth(), mBitmapDisplayed.getBitmap().getHeight());
            matrix.mapRect(rectf);
            f = rectf.right;
        }
        return f;
    }

    protected Matrix getImageViewMatrix()
    {
        mDisplayMatrix.set(mBaseMatrix);
        mDisplayMatrix.postConcat(mSuppMatrix);
        return mDisplayMatrix;
    }

    public float getMaxZoom()
    {
        return mMaxZoom;
    }

    public float getScale()
    {
        return getScale(mSuppMatrix);
    }

    protected float getScale(Matrix matrix)
    {
        return getValue(matrix, 0);
    }

    protected float getValue(Matrix matrix, int i)
    {
        matrix.getValues(mMatrixValues);
        return mMatrixValues[i];
    }

    protected float maxZoom()
    {
        float f;
        if(mBitmapDisplayed.getBitmap() == null)
            f = 1F;
        else
            f = 4F * Math.max((float)mBitmapDisplayed.getWidth() / (float)mThisWidth, (float)mBitmapDisplayed.getHeight() / (float)mThisHeight);
        return f;
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        Runnable runnable;
        super.onLayout(flag, i, j, k, l);
        mThisWidth = k - i;
        mThisHeight = l - j;
        runnable = mOnLayoutRunnable;
        if(runnable == null) goto _L2; else goto _L1
_L1:
        mOnLayoutRunnable = null;
        runnable.run();
        runnable.run();
_L4:
        return;
_L2:
        if(mBitmapDisplayed.getBitmap() != null)
        {
            getProperBaseMatrix(mBitmapDisplayed, mBaseMatrix);
            setImageMatrix(getImageViewMatrix());
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void panBy(float f, float f1)
    {
        postTranslate(f, f1);
        setImageMatrix(getImageViewMatrix());
    }

    public void postTranslate(float f, float f1)
    {
        mSuppMatrix.postTranslate(f, f1);
    }

    public void postTranslateCenter(float f, float f1)
    {
        postTranslate(f, f1);
        center(true, true);
    }

    public void setImageBitmap(Bitmap bitmap)
    {
        setImageBitmap(bitmap, 0);
    }

    public void setImageBitmapResetBase(Bitmap bitmap, boolean flag)
    {
        setImageRotateBitmapResetBase(new RotateBitmap(bitmap), flag);
    }

    public void setImageBitmapResetBaseNoScale(final RotateBitmap bitmap, final boolean resetSupp)
    {
        mStretch = false;
        if(getWidth() <= 0)
        {
            mOnLayoutRunnable = new Runnable() {

                public void run()
                {
                    setImageBitmapResetBaseNoScale(bitmap, resetSupp);
                }

                final ImageViewTouchBase this$0;
                final RotateBitmap val$bitmap;
                final boolean val$resetSupp;

            
            {
                this$0 = ImageViewTouchBase.this;
                bitmap = rotatebitmap;
                resetSupp = flag;
                super();
            }
            }
;
        } else
        {
            if(bitmap.getBitmap() != null)
            {
                getProperBaseMatrix(bitmap, mBaseMatrix);
                setImageBitmap(bitmap.getBitmap(), bitmap.getRotation());
            } else
            {
                mBaseMatrix.reset();
                setImageBitmap(null);
            }
            if(resetSupp)
                mSuppMatrix.reset();
            setImageMatrix(getImageViewMatrix());
            mMaxZoom = maxZoom();
        }
    }

    public void setImageRotateBitmapResetBase(final RotateBitmap bitmap, final boolean resetSupp)
    {
        mStretch = true;
        if(getWidth() <= 0)
        {
            mOnLayoutRunnable = new Runnable() {

                public void run()
                {
                    setImageRotateBitmapResetBase(bitmap, resetSupp);
                }

                final ImageViewTouchBase this$0;
                final RotateBitmap val$bitmap;
                final boolean val$resetSupp;

            
            {
                this$0 = ImageViewTouchBase.this;
                bitmap = rotatebitmap;
                resetSupp = flag;
                super();
            }
            }
;
        } else
        {
            if(bitmap.getBitmap() != null)
            {
                getProperBaseMatrix(bitmap, mBaseMatrix);
                setImageBitmap(bitmap.getBitmap(), bitmap.getRotation());
            } else
            {
                mBaseMatrix.reset();
                setImageBitmap(null);
            }
            if(resetSupp)
                mSuppMatrix.reset();
            setImageMatrix(getImageViewMatrix());
            mMaxZoom = maxZoom();
        }
    }

    public void setRecycler(Recycler recycler)
    {
        mRecycler = recycler;
    }

    public void zoomIn()
    {
        zoomIn(1.25F);
    }

    public void zoomIn(float f)
    {
        if(getScale() != mMaxZoom && mBitmapDisplayed.getBitmap() != null)
        {
            float f1 = (float)getWidth() / 2F;
            float f2 = (float)getHeight() / 2F;
            mSuppMatrix.postScale(f, f, f1, f2);
            setImageMatrix(getImageViewMatrix());
        }
    }

    public void zoomOut()
    {
        zoomOut(1.25F);
    }

    public void zoomOut(float f)
    {
        if(getScale() != MIN_ZOOM_SCALE && mBitmapDisplayed.getBitmap() != null)
        {
            float f1 = (float)getWidth() / 2F;
            float f2 = (float)getHeight() / 2F;
            Matrix matrix = new Matrix(mSuppMatrix);
            matrix.postScale(1F / f, 1F / f, f1, f2);
            if(getScale(matrix) < 1F)
                mSuppMatrix.setScale(1F, 1F, f1, f2);
            else
                mSuppMatrix.postScale(1F / f, 1F / f, f1, f2);
            setImageMatrix(getImageViewMatrix());
            center(true, true);
        }
    }

    public void zoomTo(float f)
    {
        zoomTo(f, (float)getWidth() / 2F, (float)getHeight() / 2F);
    }

    public void zoomTo(float f, float f1, float f2)
    {
        float f3 = correctedZoomScale(f);
        float f4 = getScale();
        float f5 = f3 / f4;
        D((new StringBuilder()).append("Old scale ").append(f4).append(", delta ").append(f5).toString());
        mSuppMatrix.postScale(f5, f5, f1, f2);
        setImageMatrix(getImageViewMatrix());
        center(true, true);
    }

    protected void zoomTo(float f, final float centerX, final float centerY, final float durationMs)
    {
        final float incrementPerMs = (f - getScale()) / durationMs;
        final float oldScale = getScale();
        final long startTime = System.currentTimeMillis();
        mHandler.post(new Runnable() {

            public void run()
            {
                long l = System.currentTimeMillis();
                float f1 = Math.min(durationMs, l - startTime);
                float f2 = oldScale + f1 * incrementPerMs;
                zoomTo(f2, centerX, centerY);
                if(f1 < durationMs)
                    mHandler.post(this);
            }

            final ImageViewTouchBase this$0;
            final float val$centerX;
            final float val$centerY;
            final float val$durationMs;
            final float val$incrementPerMs;
            final float val$oldScale;
            final long val$startTime;

            
            {
                this$0 = ImageViewTouchBase.this;
                durationMs = f;
                startTime = l;
                oldScale = f1;
                incrementPerMs = f2;
                centerX = f3;
                centerY = f4;
                super();
            }
        }
);
    }

    public void zoomToPoint(float f, float f1, float f2)
    {
        float f3 = (float)getWidth() / 2F;
        float f4 = (float)getHeight() / 2F;
        panBy(f3 - f1, f4 - f2);
        zoomTo(f, f3, f4);
    }

    private static final boolean DEBUG = false;
    private static float MIN_ZOOM_SCALE = 0F;
    static final float SCALE_RATE = 1.25F;
    protected Matrix mBaseMatrix;
    protected final RotateBitmap mBitmapDisplayed;
    private final Matrix mDisplayMatrix;
    protected Handler mHandler;
    protected int mLastXTouchPos;
    protected int mLastYTouchPos;
    private final float mMatrixValues[];
    protected float mMaxZoom;
    private Runnable mOnLayoutRunnable;
    private Recycler mRecycler;
    boolean mStretch;
    protected Matrix mSuppMatrix;
    int mThisHeight;
    int mThisWidth;

    static 
    {
        MIN_ZOOM_SCALE = 1F;
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ResizableFontButton.java

package com.facebook.katana.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;

public class ResizableFontButton extends Button
{

    public ResizableFontButton(Context context)
    {
        super(context);
        SCALE = getContext().getResources().getDisplayMetrics().density;
        mForceResize = false;
        mMinTextSize = 8F;
        mSpacingMult = 1F;
        mSpacingAdd = 0F;
        mAddEllipsis = true;
        mTextSize = getTextSizeInDip();
    }

    public ResizableFontButton(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        SCALE = getContext().getResources().getDisplayMetrics().density;
        mForceResize = false;
        mMinTextSize = 8F;
        mSpacingMult = 1F;
        mSpacingAdd = 0F;
        mAddEllipsis = true;
        mTextSize = getTextSizeInDip();
    }

    public ResizableFontButton(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        SCALE = getContext().getResources().getDisplayMetrics().density;
        mForceResize = false;
        mMinTextSize = 8F;
        mSpacingMult = 1F;
        mSpacingAdd = 0F;
        mAddEllipsis = true;
        mTextSize = getTextSizeInDip();
    }

    private int diptoPixel(float f)
    {
        return (int)(0.5F + f * SCALE);
    }

    private int getTextHeight(CharSequence charsequence, TextPaint textpaint, float f)
    {
        textpaint.setTextSize(diptoPixel(f));
        Rect rect = new Rect();
        textpaint.getTextBounds(charsequence.toString(), 0, charsequence.length(), rect);
        return rect.bottom;
    }

    private float getTextSizeInDip()
    {
        return pixelToDip(getTextSize());
    }

    private float getTextWidth(CharSequence charsequence, TextPaint textpaint, float f)
    {
        textpaint.setTextSize(diptoPixel(f));
        return textpaint.measureText(charsequence, 0, charsequence.length());
    }

    private float pixelToDip(float f)
    {
        return f / SCALE;
    }

    private void resetTextSize()
    {
        super.setTextSize(1, mTextSize);
    }

    private void resizeText()
    {
        int i = getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight();
        int j = getHeight() - getCompoundPaddingTop() - getCompoundPaddingBottom();
        CharSequence charsequence = getText();
        if(charsequence != null && charsequence.length() != 0 && j > 0 && i > 0)
        {
            TextPaint textpaint = new TextPaint(getPaint());
            float f = Math.max(mTextSize, mMinTextSize);
            float f1;
            for(f1 = getTextHeight(charsequence, textpaint, f); f1 > (float)j && f > mMinTextSize; f1 = getTextHeight(charsequence, textpaint, f))
                f = Math.max(f - 1F, mMinTextSize);

            float f2;
            for(f2 = getTextWidth(charsequence, textpaint, f); f2 > (float)i && f > mMinTextSize; f2 = getTextWidth(charsequence, textpaint, f))
                f = Math.max(f - 1F, mMinTextSize);

            if(mAddEllipsis && f <= mMinTextSize && (f2 > (float)i || f1 > (float)j))
            {
                StaticLayout staticlayout = new StaticLayout(charsequence, textpaint, i, android.text.Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, false);
                staticlayout.draw(HELPER_CANVAS);
                int k = staticlayout.getLineForVertical(j) - 1;
                int l = staticlayout.getLineStart(k);
                int i1 = staticlayout.getLineEnd(k);
                float f3 = staticlayout.getLineWidth(k);
                for(float f4 = textpaint.measureText("\u2026"); (float)i < f3 + f4;)
                {
                    f3 = textpaint.measureText(charsequence.subSequence(l, i1).toString());
                    i1--;
                }

                setText((new StringBuilder()).append(charsequence.subSequence(0, i1)).append("\u2026").toString());
            }
            super.setTextSize(1, f);
            setLineSpacing(mSpacingAdd, mSpacingMult);
            mForceResize = false;
        }
    }

    public float getMinTextSize()
    {
        return mMinTextSize;
    }

    public boolean isAddEllipsis()
    {
        return mAddEllipsis;
    }

    protected void onDraw(Canvas canvas)
    {
        if(mForceResize)
            resizeText();
        super.onDraw(canvas);
    }

    protected void onSizeChanged(int i, int j, int k, int l)
    {
        if(i != k || j != l)
            mForceResize = true;
    }

    protected void onTextChanged(CharSequence charsequence, int i, int j, int k)
    {
        mForceResize = true;
        resetTextSize();
    }

    public void setAddEllipsis(boolean flag)
    {
        mAddEllipsis = flag;
    }

    public void setLineSpacing(float f, float f1)
    {
        super.setLineSpacing(f, f1);
        mSpacingMult = f1;
        mSpacingAdd = f;
    }

    public void setMinTextSize(float f)
    {
        mMinTextSize = f;
        mForceResize = true;
        requestLayout();
        invalidate();
    }

    public void setTextSize(float f)
    {
        super.setTextSize(f);
        mTextSize = getTextSizeInDip();
    }

    public void setTextSize(int i, float f)
    {
        super.setTextSize(i, f);
        mTextSize = getTextSizeInDip();
    }

    private static final String ELLIPSIS = "\u2026";
    private static final Canvas HELPER_CANVAS = new Canvas();
    private static final float MIN_TEXT_SIZE = 8F;
    private static final float TEXT_RESIZE_STEP = 1F;
    private final float SCALE;
    private boolean mAddEllipsis;
    private boolean mForceResize;
    private float mMinTextSize;
    private float mSpacingAdd;
    private float mSpacingMult;
    private float mTextSize;

}

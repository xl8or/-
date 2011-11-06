// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TagSuggestionView.java

package com.facebook.katana.ui;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;

public class TagSuggestionView extends LinearLayout
    implements android.view.View.OnTouchListener
{

    public TagSuggestionView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(0x7f030083, this);
        setPadding(0, 0, 0, 0);
        setBackgroundColor(0);
        mTextBtn = (Button)findViewById(0x7f0e0143);
        mSuggestionBtn = (Button)findViewById(0x7f0e0142);
        mTextBtn.setOnTouchListener(this);
        mSuggestionBtn.setOnTouchListener(this);
    }

    public int getFaceBoxHeight()
    {
        return (int)(2.5F * ((float)mImageSize * mDistance));
    }

    public int getFaceBoxWidth()
    {
        return (int)(1.8F * ((float)mImageSize * mDistance));
    }

    public int getFullHeight()
    {
        float f = -mTextBtn.getPaint().ascent();
        float f1 = mTextBtn.getPaint().descent();
        float f2 = mTextBtn.getCompoundPaddingTop();
        return (int)((float)mTextBtn.getCompoundPaddingBottom() + (f2 + (f + f1)) + (float)getFaceBoxHeight());
    }

    public int getFullWidth()
    {
        float f = mTextBtn.getPaint().measureText(mTextBtn.getText().toString());
        int i = mTextBtn.getCompoundPaddingLeft();
        return Math.max(mTextBtn.getCompoundPaddingRight() + (i + (int)f), getFaceBoxWidth());
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public boolean onTouch(View view, MotionEvent motionevent)
    {
        motionevent.getAction();
        JVM INSTR tableswitch 0 3: default 36
    //                   0 42
    //                   1 61
    //                   2 36
    //                   3 61;
           goto _L1 _L2 _L3 _L1 _L3
_L1:
        return onTouchEvent(motionevent);
_L2:
        mTextBtn.setPressed(true);
        mSuggestionBtn.setPressed(true);
        continue; /* Loop/switch isn't completed */
_L3:
        mTextBtn.setPressed(false);
        mSuggestionBtn.setPressed(false);
        if(true) goto _L1; else goto _L4
_L4:
    }

    public void setEyeDistance(float f)
    {
        mDistance = f;
        updateSize();
    }

    public void setImageSize(int i)
    {
        mImageSize = i;
        updateSize();
    }

    public void setX(float f)
    {
        x = f;
    }

    public void setY(float f)
    {
        y = f;
    }

    public void updateSize()
    {
        mSuggestionBtn.setWidth(getFaceBoxWidth());
        mSuggestionBtn.setHeight(getFaceBoxHeight());
    }

    private static final float FACE_HEIGHT_FACTOR = 2.5F;
    private static final float FACE_WIDTH_FACTOR = 1.8F;
    private float mDistance;
    private int mImageSize;
    private Button mSuggestionBtn;
    private Button mTextBtn;
    private float x;
    private float y;
}

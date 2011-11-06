// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TagView.java

package com.facebook.katana.ui;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.Button;

public class TagView extends Button
{

    public TagView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public int getFullHeight()
    {
        float f = -getPaint().ascent();
        float f1 = getPaint().descent();
        float f2 = getCompoundPaddingTop();
        return (int)((float)getCompoundPaddingBottom() + (f2 + (f + f1)));
    }

    public int getFullWidth()
    {
        float f = getPaint().measureText(getText().toString());
        int i = getCompoundPaddingLeft();
        int j = getCompoundPaddingRight();
        return (int)(f + (float)i + (float)j);
    }

    public long userId;
    public float x;
    public float y;
}

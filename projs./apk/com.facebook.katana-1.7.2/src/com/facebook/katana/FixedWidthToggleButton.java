// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FixedWidthToggleButton.java

package com.facebook.katana;

import android.content.Context;
import android.graphics.Paint;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.widget.ToggleButton;

class FixedWidthToggleButton extends ToggleButton
{

    public FixedWidthToggleButton(Context context)
    {
        super(context);
    }

    public FixedWidthToggleButton(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public FixedWidthToggleButton(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    protected void onMeasure(int i, int j)
    {
        CharSequence acharsequence[] = new CharSequence[2];
        acharsequence[0] = getTextOn();
        acharsequence[1] = getTextOff();
        (new Paint()).setTypeface(getTypeface());
        int k = 0;
        int l = acharsequence.length;
        for(int i1 = 0; i1 < l; i1++)
        {
            int j1 = (int)(new StaticLayout(acharsequence[i1], getPaint(), 1000, android.text.Layout.Alignment.ALIGN_NORMAL, 1F, 0F, true)).getLineWidth(0);
            if(j1 > k)
                k = j1;
        }

        setMeasuredDimension(resolveSize(k + getPaddingLeft() + getPaddingRight(), i), resolveSize(getSuggestedMinimumHeight(), j));
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ActionMenuButton.java

package com.facebook.katana;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class ActionMenuButton extends TextView
{

    public ActionMenuButton(Context context)
    {
        super(context);
        mRect = new RectF();
        init();
    }

    public ActionMenuButton(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mRect = new RectF();
        init();
    }

    public ActionMenuButton(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mRect = new RectF();
        init();
    }

    private void init()
    {
        setFocusable(true);
        setPadding(5, 0, 5, 1);
        mPaint = new Paint(1);
        mPaint.setColor(getContext().getResources().getColor(0x7f070006));
    }

    public void draw(Canvas canvas)
    {
        Layout layout = getLayout();
        RectF rectf = mRect;
        int i = getCompoundPaddingLeft();
        int j = getExtendedPaddingTop();
        rectf.set(((float)i + layout.getLineLeft(0)) - 5F, (j + layout.getLineTop(0)) - 1, Math.min(5F + ((float)i + layout.getLineRight(0)), (getScrollX() + getRight()) - getLeft()), 1 + (j + layout.getLineBottom(0)));
        canvas.drawRoundRect(rectf, 8F, 8F, mPaint);
        super.draw(canvas);
    }

    protected void drawableStateChanged()
    {
        invalidate();
        super.drawableStateChanged();
    }

    private static final int CORNER_RADIUS = 8;
    private static final int PADDING_H = 5;
    private static final int PADDING_V = 1;
    private Paint mPaint;
    private final RectF mRect;
}

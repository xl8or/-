// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaneLinkMovementMethod.java

package com.facebook.katana.ui;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class SaneLinkMovementMethod extends LinkMovementMethod
{

    public SaneLinkMovementMethod()
    {
    }

    public static MovementMethod getInstance()
    {
        if(sInstance == null)
            sInstance = new SaneLinkMovementMethod();
        return sInstance;
    }

    public boolean onTouchEvent(TextView textview, Spannable spannable, MotionEvent motionevent)
    {
        int i = motionevent.getAction();
        if(i != 1 && i != 0) goto _L2; else goto _L1
_L1:
        ClickableSpan aclickablespan[];
        int j = (int)motionevent.getX();
        int k = (int)motionevent.getY();
        int l = j - textview.getTotalPaddingLeft();
        int i1 = k - textview.getTotalPaddingTop();
        int j1 = l + textview.getScrollX();
        int k1 = i1 + textview.getScrollY();
        Layout layout = textview.getLayout();
        int l1 = layout.getOffsetForHorizontal(layout.getLineForVertical(k1), j1);
        aclickablespan = (ClickableSpan[])spannable.getSpans(l1, l1, android/text/style/ClickableSpan);
        if(aclickablespan.length == 0) goto _L2; else goto _L3
_L3:
        boolean flag;
        if(i == 1)
            aclickablespan[0].onClick(textview);
        flag = true;
_L5:
        return flag;
_L2:
        flag = false;
        if(true) goto _L5; else goto _L4
_L4:
    }

    private static SaneLinkMovementMethod sInstance;
}

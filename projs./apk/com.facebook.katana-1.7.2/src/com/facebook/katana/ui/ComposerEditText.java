// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComposerEditText.java

package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.facebook.katana.ComposerActivity;
import com.facebook.katana.util.Utils;

public class ComposerEditText extends EditText
{

    public ComposerEditText(Context context)
    {
        super(context);
    }

    public ComposerEditText(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public void extendSelection(int i)
    {
        super.extendSelection(Math.min(i, ComposerActivity.getEndIndex(getText())));
    }

    protected void onSelectionChanged(int i, int j)
    {
        int k = ComposerActivity.getEndIndex(getText());
        if(j <= k && i <= k) goto _L2; else goto _L1
_L1:
        setSelection(i, j);
_L4:
        return;
_L2:
        if(i == -1 || j == -1)
            setSelection(0, 0);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void setSelection(int i)
    {
        super.setSelection(Math.min(i, ComposerActivity.getEndIndex(getText())));
    }

    public void setSelection(int i, int j)
    {
        int k = ComposerActivity.getEndIndex(getText());
        super.setSelection(Math.min(i, k), Math.min(j, k));
    }

    protected static final String TAG = Utils.getClassName(com/facebook/katana/ui/ComposerEditText);

}

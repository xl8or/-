// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TagUsersAutoCompleteTextView.java

package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import com.facebook.katana.activity.media.UserHolder;

public class TagUsersAutoCompleteTextView extends AutoCompleteTextView
{

    public TagUsersAutoCompleteTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    protected CharSequence convertSelectionToString(Object obj)
    {
        return ((UserHolder)obj).getDisplayName();
    }

    public boolean enoughToFilter()
    {
        return true;
    }

    protected void onFocusChanged(boolean flag, int i, Rect rect)
    {
        super.onFocusChanged(flag, i, rect);
        if(flag)
            performFiltering(getText(), 0);
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        boolean flag;
        if(keyevent.getKeyCode() == 66)
        {
            if(getAdapter().getCount() == 1)
            {
                getOnItemClickListener().onItemClick(null, null, 0, -1L);
                setText(null);
            }
            flag = true;
        } else
        {
            flag = super.onKeyDown(i, keyevent);
        }
        return flag;
    }
}

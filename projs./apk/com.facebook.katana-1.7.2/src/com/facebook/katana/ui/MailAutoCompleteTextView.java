// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailAutoCompleteTextView.java

package com.facebook.katana.ui;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;

public class MailAutoCompleteTextView extends AutoCompleteTextView
{

    public MailAutoCompleteTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    protected CharSequence convertSelectionToString(Object obj)
    {
        Cursor cursor = (Cursor)obj;
        return cursor.getString(cursor.getColumnIndexOrThrow("display_name"));
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        boolean flag;
        if(keyevent.getKeyCode() == 66)
        {
            Cursor cursor = ((CursorAdapter)getAdapter()).getCursor();
            if(cursor != null && cursor.getCount() == 1)
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

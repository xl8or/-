// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SectionedListView.java

package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.*;
import com.facebook.katana.view.FacebookListView;

// Referenced classes of package com.facebook.katana.ui:
//            SectionedListAdapter, SectionedListInternalAdapter

public class SectionedListView extends FacebookListView
{

    public SectionedListView(Context context)
    {
        super(context);
    }

    public SectionedListView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public SectionedListView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    public volatile Adapter getAdapter()
    {
        return getAdapter();
    }

    public ListAdapter getAdapter()
    {
        return mInternalAdapter;
    }

    public SectionedListAdapter getSectionedListAdapter()
    {
        return mRealAdapter;
    }

    public volatile void setAdapter(Adapter adapter)
    {
        setAdapter((ListAdapter)adapter);
    }

    public void setAdapter(ListAdapter listadapter)
    {
    }

    public void setSectionedListAdapter(SectionedListAdapter sectionedlistadapter)
    {
        if(mRealAdapter != null)
            mRealAdapter.setInternalListAdapter(null);
        mRealAdapter = sectionedlistadapter;
        mInternalAdapter = new SectionedListInternalAdapter(sectionedlistadapter);
        mRealAdapter.setInternalListAdapter(mInternalAdapter);
        super.setAdapter(mInternalAdapter);
    }

    protected BaseAdapter mInternalAdapter;
    protected SectionedListAdapter mRealAdapter;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SectionedListView.java

package com.facebook.katana.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

// Referenced classes of package com.facebook.katana.ui:
//            SectionedListAdapter

class SectionedListInternalAdapter extends BaseAdapter
    implements SectionIndexer
{

    public SectionedListInternalAdapter(SectionedListAdapter sectionedlistadapter)
    {
        mRealAdapter = sectionedlistadapter;
    }

    public int getCount()
    {
        int i;
        if(mRealAdapter.isEmpty())
            i = 0;
        else
            i = mRealAdapter.getCount();
        return i;
    }

    public Object getItem(int i)
    {
        return mRealAdapter.getItem(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getItemViewType(int i)
    {
        return mRealAdapter.getItemViewType(i);
    }

    public int getPositionForSection(int i)
    {
        return mRealAdapter.getPositionForSection(i);
    }

    public int getSectionForPosition(int i)
    {
        int ai[] = mRealAdapter.decodeFlatPosition(i);
        if(!$assertionsDisabled && ai.length != 2)
            throw new AssertionError();
        else
            return ai[1];
    }

    public Object[] getSections()
    {
        int i = mRealAdapter.getSectionCount();
        Object aobj[] = new Object[i];
        for(int j = 0; j < i; j++)
            aobj[j] = mRealAdapter.getSection(j);

        return aobj;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        return mRealAdapter.getView(i, view, viewgroup);
    }

    public int getViewTypeCount()
    {
        return mRealAdapter.getViewTypeCount();
    }

    public boolean isEnabled(int i)
    {
        boolean flag1 = mRealAdapter.isEnabled(i);
        boolean flag = flag1;
_L2:
        return flag;
        ArrayIndexOutOfBoundsException arrayindexoutofboundsexception;
        arrayindexoutofboundsexception;
        flag = true;
        if(true) goto _L2; else goto _L1
_L1:
    }

    static final boolean $assertionsDisabled;
    protected final SectionedListAdapter mRealAdapter;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/ui/SectionedListInternalAdapter.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

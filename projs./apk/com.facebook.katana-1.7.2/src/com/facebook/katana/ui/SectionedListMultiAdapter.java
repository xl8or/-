// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SectionedListMultiAdapter.java

package com.facebook.katana.ui;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.*;

// Referenced classes of package com.facebook.katana.ui:
//            SectionedListAdapter

public class SectionedListMultiAdapter extends SectionedListAdapter
{
    private class AdapterDataObserver extends DataSetObserver
    {

        public void onChanged()
        {
            rebuildAdapterCache();
            notifyDataSetChanged();
        }

        final SectionedListMultiAdapter this$0;

        private AdapterDataObserver()
        {
            this$0 = SectionedListMultiAdapter.this;
            super();
        }

    }


    public SectionedListMultiAdapter()
    {
        mAdapters = new ArrayList();
        mObserver = new AdapterDataObserver();
    }

    private int getAdapterIndexForSection(int i)
    {
        for(int j = mAdapters.size() - 1; j >= 0; j--)
            if(mAdaptersPositionStart[j] <= i)
                return j;

        throw new IndexOutOfBoundsException();
    }

    private int getAdapterSectionPosition(int i, int j)
    {
        return j - mAdaptersPositionStart[i];
    }

    private void rebuildAdapterCache()
    {
        mAdaptersPositionStart = new int[mAdapters.size()];
        mAdaptersViewCountStart = new int[mAdapters.size()];
        mViewTypeCount = 0;
        mSectionsCount = 0;
        if(mAdapters.size() != 0)
        {
            int i = 0;
            while(i < mAdapters.size()) 
            {
                SectionedListAdapter sectionedlistadapter = (SectionedListAdapter)mAdapters.get(i);
                int j = sectionedlistadapter.getSectionCount();
                mAdaptersPositionStart[i] = mSectionsCount;
                mSectionsCount = j + mSectionsCount;
                mAdaptersViewCountStart[i] = mViewTypeCount;
                mViewTypeCount = mViewTypeCount + sectionedlistadapter.getViewTypeCount();
                i++;
            }
        }
    }

    public void addSectionedAdapter(SectionedListAdapter sectionedlistadapter)
    {
        mAdapters.add(sectionedlistadapter);
        sectionedlistadapter.registerDataSetObserver(mObserver);
        rebuildAdapterCache();
        notifyDataSetChanged();
    }

    public Object getChild(int i, int j)
    {
        int k = getAdapterIndexForSection(i);
        int l = getAdapterSectionPosition(k, i);
        return ((SectionedListAdapter)mAdapters.get(k)).getChild(l, j);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        int k = getAdapterIndexForSection(i);
        int l = getAdapterSectionPosition(k, i);
        return ((SectionedListAdapter)mAdapters.get(k)).getChildView(l, j, flag, view, viewgroup);
    }

    public int getChildViewType(int i, int j)
    {
        int k = getAdapterIndexForSection(i);
        int l = getAdapterSectionPosition(k, i);
        return mAdaptersViewCountStart[k] + ((SectionedListAdapter)mAdapters.get(k)).getChildViewType(l, j);
    }

    public int getChildrenCount(int i)
    {
        int j = getAdapterIndexForSection(i);
        int k = getAdapterSectionPosition(j, i);
        return ((SectionedListAdapter)mAdapters.get(j)).getChildrenCount(k);
    }

    public Object getSection(int i)
    {
        int j = getAdapterIndexForSection(i);
        int k = getAdapterSectionPosition(j, i);
        return ((SectionedListAdapter)mAdapters.get(j)).getSection(k);
    }

    public int getSectionCount()
    {
        return mSectionsCount;
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        int j = getAdapterIndexForSection(i);
        int k = getAdapterSectionPosition(j, i);
        return ((SectionedListAdapter)mAdapters.get(j)).getSectionHeaderView(k, view, viewgroup);
    }

    public int getSectionHeaderViewType(int i)
    {
        int j = getAdapterIndexForSection(i);
        int k = getAdapterSectionPosition(j, i);
        return mAdaptersViewCountStart[j] + ((SectionedListAdapter)mAdapters.get(j)).getSectionHeaderViewType(k);
    }

    public int getViewTypeCount()
    {
        return mViewTypeCount;
    }

    public boolean isEmpty()
    {
        boolean flag = true;
        for(Iterator iterator = mAdapters.iterator(); iterator.hasNext();)
        {
            SectionedListAdapter sectionedlistadapter = (SectionedListAdapter)iterator.next();
            if(flag && sectionedlistadapter.isEmpty())
                flag = true;
            else
                flag = false;
        }

        return flag;
    }

    public boolean isEnabled(int i, int j)
    {
        return true;
    }

    public void removeSectionedAdapter(SectionedListAdapter sectionedlistadapter)
    {
        sectionedlistadapter.unregisterDataSetObserver(mObserver);
        mAdapters.remove(sectionedlistadapter);
        rebuildAdapterCache();
        notifyDataSetChanged();
    }

    private List mAdapters;
    private int mAdaptersPositionStart[];
    private int mAdaptersViewCountStart[];
    private AdapterDataObserver mObserver;
    private int mSectionsCount;
    private int mViewTypeCount;

}

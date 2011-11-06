// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SectionedListAdapter.java

package com.facebook.katana.ui;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.*;

public abstract class SectionedListAdapter
{
    static class SectionCache
    {

        static int findSectionByFlatIndex(List list, int i)
        {
            SectionCache sectioncache = new SectionCache(0);
            sectioncache.mStartOffset = i;
            return Collections.binarySearch(list, sectioncache, cmp);
        }

        static Comparator cmp = new Comparator() {

            public int compare(SectionCache sectioncache, SectionCache sectioncache1)
            {
                byte byte0;
                if(sectioncache.mStartOffset + sectioncache.mNumItems < sectioncache1.mStartOffset)
                    byte0 = -1;
                else
                if(sectioncache.mStartOffset > sectioncache1.mStartOffset + sectioncache1.mNumItems)
                    byte0 = 1;
                else
                    byte0 = 0;
                return byte0;
            }

            public volatile int compare(Object obj, Object obj1)
            {
                return compare((SectionCache)obj, (SectionCache)obj1);
            }

        }
;
        int mNumItems;
        final int mSectionPosition;
        int mStartOffset;


        SectionCache(int i)
        {
            mSectionPosition = i;
        }
    }


    public SectionedListAdapter()
    {
    }

    public int[] decodeFlatPosition(int i)
    {
        ensureSectionCacheValid();
        int ai[] = new int[2];
        ai[0] = SectionCache.findSectionByFlatIndex(mSectionCache, i);
        ai[1] = i - ((SectionCache)mSectionCache.get(ai[0])).mStartOffset - 1;
        return ai;
    }

    protected void ensureSectionCacheValid()
    {
        if(!mSectionCacheValid)
            rebuildSectionCache();
    }

    public abstract Object getChild(int i, int j);

    public abstract View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup);

    public abstract int getChildViewType(int i, int j);

    public abstract int getChildrenCount(int i);

    public int getCount()
    {
        ensureSectionCacheValid();
        int i;
        if(mSectionCache.size() == 0)
        {
            i = 0;
        } else
        {
            SectionCache sectioncache = (SectionCache)mSectionCache.get(mSectionCache.size() - 1);
            i = 1 + (sectioncache.mStartOffset + sectioncache.mNumItems);
        }
        return i;
    }

    public Object getItem(int i)
    {
        int ai[] = decodeFlatPosition(i);
        if(!$assertionsDisabled && ai.length != 2)
            throw new AssertionError();
        Object obj;
        if(ai[1] == -1)
            obj = null;
        else
            obj = getChild(ai[0], ai[1]);
        return obj;
    }

    int getItemViewType(int i)
    {
        int ai[] = decodeFlatPosition(i);
        if(!$assertionsDisabled && ai.length != 2)
            throw new AssertionError();
        int j;
        if(ai[1] == -1)
            j = getSectionHeaderViewType(ai[0]);
        else
            j = getChildViewType(ai[0], ai[1]);
        return j;
    }

    public int getPositionForSection(int i)
    {
        ensureSectionCacheValid();
        return ((SectionCache)mSectionCache.get(i)).mStartOffset;
    }

    public abstract Object getSection(int i);

    public abstract int getSectionCount();

    public abstract View getSectionHeaderView(int i, View view, ViewGroup viewgroup);

    public abstract int getSectionHeaderViewType(int i);

    View getView(int i, View view, ViewGroup viewgroup)
    {
        int ai[] = decodeFlatPosition(i);
        if(!$assertionsDisabled && ai.length != 2)
            throw new AssertionError();
        View view1;
        if(ai[1] == -1)
        {
            view1 = getSectionHeaderView(ai[0], view, viewgroup);
        } else
        {
            boolean flag;
            if(((SectionCache)mSectionCache.get(ai[0])).mNumItems - 1 == ai[1])
                flag = true;
            else
                flag = false;
            view1 = getChildView(ai[0], ai[1], flag, view, viewgroup);
        }
        return view1;
    }

    public abstract int getViewTypeCount();

    public abstract boolean isEmpty();

    public boolean isEnabled(int i)
    {
        int ai[] = decodeFlatPosition(i);
        if(!$assertionsDisabled && ai.length != 2)
            throw new AssertionError();
        boolean flag;
        if(ai[1] == -1)
            flag = false;
        else
            flag = isEnabled(ai[0], ai[1]);
        return flag;
    }

    public abstract boolean isEnabled(int i, int j);

    public boolean isPositionSectionHeader(int i)
    {
        int ai[] = decodeFlatPosition(i);
        if(!$assertionsDisabled && ai.length != 2)
            throw new AssertionError();
        boolean flag;
        if(ai[1] == -1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void notifyDataSetChanged()
    {
        mSectionCacheValid = false;
        if(mInternalAdapter != null)
            mInternalAdapter.notifyDataSetChanged();
        if(mDataSetObservers != null)
        {
            for(int i = 0; i < mDataSetObservers.size(); i++)
                ((DataSetObserver)mDataSetObservers.get(i)).onChanged();

        }
    }

    protected void rebuildSectionCache()
    {
        if(!$assertionsDisabled && mSectionCacheValid)
            throw new AssertionError();
        if(mSectionCache == null)
            mSectionCache = new ArrayList();
        int i = mSectionCache.size();
        for(int j = getSectionCount(); i < j; i++)
            mSectionCache.add(new SectionCache(i));

        if(!$assertionsDisabled && mSectionCache.size() < getSectionCount())
            throw new AssertionError();
        int k = mSectionCache.size();
        for(int l = getSectionCount(); k > l; k--)
            mSectionCache.remove(k - 1);

        if(!$assertionsDisabled && mSectionCache.size() != getSectionCount())
            throw new AssertionError();
        int i1 = 0;
        int j1 = 0;
        for(; i1 < getSectionCount(); i1++)
        {
            int k1 = getChildrenCount(i1);
            SectionCache sectioncache = (SectionCache)mSectionCache.get(i1);
            sectioncache.mStartOffset = j1;
            sectioncache.mNumItems = k1;
            j1 += k1 + 1;
        }

        mSectionCacheValid = true;
    }

    public void registerDataSetObserver(DataSetObserver datasetobserver)
    {
        if(mDataSetObservers == null)
            mDataSetObservers = new ArrayList();
        mDataSetObservers.add(datasetobserver);
    }

    void setInternalListAdapter(BaseAdapter baseadapter)
    {
        mInternalAdapter = baseadapter;
    }

    public void unregisterDataSetObserver(DataSetObserver datasetobserver)
    {
        if(mDataSetObservers != null)
            mDataSetObservers.remove(datasetobserver);
    }

    static final boolean $assertionsDisabled;
    private List mDataSetObservers;
    protected BaseAdapter mInternalAdapter;
    protected List mSectionCache;
    protected boolean mSectionCacheValid;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/ui/SectionedListAdapter.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

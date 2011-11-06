// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExtendableListAdapter.java

package com.facebook.katana.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.*;
import android.widget.*;

public class ExtendableListAdapter
    implements WrapperListAdapter
{
    public static interface LoadMoreCallback
    {

        public abstract boolean hasMore();

        public abstract void loadMore();
    }


    public ExtendableListAdapter(Context context, ListAdapter listadapter, LoadMoreCallback loadmorecallback)
    {
        mLoadMoreTextResId = -1;
        mContext = context;
        mWrappedAdapter = listadapter;
        mLoadMoreCallback = loadmorecallback;
    }

    private boolean isMoreLoaderShown()
    {
        return mLoadMoreCallback.hasMore();
    }

    private int translatePosition(int i)
    {
        int j = getCount();
        if(i < 0 || i >= j)
        {
            Object aobj[] = new Object[2];
            aobj[0] = Integer.valueOf(i);
            aobj[1] = Integer.valueOf(j);
            throw new ArrayIndexOutOfBoundsException(String.format("Invalid index: %d of %d", aobj));
        }
        int k;
        if(i == mWrappedAdapter.getCount())
            k = -2;
        else
            k = i;
        return k;
    }

    public boolean areAllItemsEnabled()
    {
        return false;
    }

    public int getCount()
    {
        int k;
        if(mWrappedAdapter.getCount() == 0)
        {
            k = 0;
        } else
        {
            int i = mWrappedAdapter.getCount();
            int j;
            if(isMoreLoaderShown())
                j = 1;
            else
                j = 0;
            k = i + j;
        }
        return k;
    }

    public Object getItem(int i)
    {
        Object obj;
        if(translatePosition(i) >= 0)
            obj = mWrappedAdapter.getItem(i);
        else
            obj = null;
        return obj;
    }

    public long getItemId(int i)
    {
        int j = translatePosition(i);
        long l;
        if(j >= 0)
            l = mWrappedAdapter.getItemId(j);
        else
            l = -2L;
        return l;
    }

    public int getItemViewType(int i)
    {
        int j = translatePosition(i);
        int k;
        if(j >= 0)
            k = mWrappedAdapter.getItemViewType(j);
        else
            k = mWrappedAdapter.getViewTypeCount();
        return k;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        if(translatePosition(i) == -2)
        {
            View view2;
            if(view != null)
                view2 = view;
            else
                view2 = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f03003c, null);
            if(isMoreLoaderShown())
            {
                view2.setVisibility(0);
                if(mLoadMoreTextResId != -1)
                    ((TextView)view2.findViewById(0x7f0e00b0)).setText(mLoadMoreTextResId);
                mLoadMoreCallback.loadMore();
            } else
            {
                view2.setVisibility(8);
            }
            view1 = view2;
        } else
        {
            view1 = mWrappedAdapter.getView(i, view, viewgroup);
        }
        return view1;
    }

    public int getViewTypeCount()
    {
        return 1 + mWrappedAdapter.getViewTypeCount();
    }

    public ListAdapter getWrappedAdapter()
    {
        return mWrappedAdapter;
    }

    public boolean hasStableIds()
    {
        return mWrappedAdapter.hasStableIds();
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(getCount() == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEnabled(int i)
    {
        int j = translatePosition(i);
        boolean flag;
        if(j == -2)
            flag = true;
        else
            flag = mWrappedAdapter.isEnabled(j);
        return flag;
    }

    public void registerDataSetObserver(DataSetObserver datasetobserver)
    {
        mWrappedAdapter.registerDataSetObserver(datasetobserver);
    }

    public void setLoadMoreTextResId(int i)
    {
        mLoadMoreTextResId = i;
    }

    public void unregisterDataSetObserver(DataSetObserver datasetobserver)
    {
        mWrappedAdapter.unregisterDataSetObserver(datasetobserver);
    }

    public static final int MORE_LOADER_POSITION = -2;
    private Context mContext;
    private LoadMoreCallback mLoadMoreCallback;
    private int mLoadMoreTextResId;
    private ListAdapter mWrappedAdapter;
}

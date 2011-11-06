// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefreshableListView.java

package com.facebook.orca.common.ui.widgets.refreshablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

// Referenced classes of package com.facebook.orca.common.ui.widgets.refreshablelistview:
//            RefreshableListViewContainer

public class RefreshableListView extends ListView
{

    public RefreshableListView(Context context)
    {
        super(context);
        init(context);
    }

    public RefreshableListView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init(context);
    }

    public RefreshableListView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        init(context);
    }

    private void init(Context context)
    {
        super.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {

            public void onScroll(AbsListView abslistview, int i, int j, int k)
            {
                RefreshableListView.this.onScroll(i, j, k);
            }

            public void onScrollStateChanged(AbsListView abslistview, int i)
            {
                RefreshableListView.this.onScrollStateChanged(i);
            }

            final RefreshableListView this$0;

            
            {
                this$0 = RefreshableListView.this;
                super();
            }
        }
);
    }

    private void onScroll(int i, int j, int k)
    {
        if(onScrollListener != null)
            onScrollListener.onScroll(this, i, j, k);
    }

    private void onScrollStateChanged(int i)
    {
        ((RefreshableListViewContainer)getParent()).onScrollStateChanged(i);
        if(onScrollListener != null)
            onScrollListener.onScrollStateChanged(this, i);
    }

    public void setOnScrollListener(android.widget.AbsListView.OnScrollListener onscrolllistener)
    {
        onScrollListener = onscrolllistener;
    }

    private static final String TAG = "PullToRefreshListView";
    private android.widget.AbsListView.OnScrollListener onScrollListener;


}

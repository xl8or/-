// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RefreshableListViewState.java

package com.facebook.orca.common.ui.widgets.refreshablelistview;


public final class RefreshableListViewState extends Enum
{

    private RefreshableListViewState(String s, int i)
    {
        super(s, i);
    }

    public static RefreshableListViewState valueOf(String s)
    {
        return (RefreshableListViewState)Enum.valueOf(com/facebook/orca/common/ui/widgets/refreshablelistview/RefreshableListViewState, s);
    }

    public static RefreshableListViewState[] values()
    {
        return (RefreshableListViewState[])$VALUES.clone();
    }

    private static final RefreshableListViewState $VALUES[];
    public static final RefreshableListViewState BUFFERING;
    public static final RefreshableListViewState LOADING;
    public static final RefreshableListViewState NORMAL;
    public static final RefreshableListViewState PULL_TO_REFRESH;
    public static final RefreshableListViewState PUSH_TO_REFRESH;
    public static final RefreshableListViewState RELEASE_TO_REFRESH;

    static 
    {
        NORMAL = new RefreshableListViewState("NORMAL", 0);
        PULL_TO_REFRESH = new RefreshableListViewState("PULL_TO_REFRESH", 1);
        PUSH_TO_REFRESH = new RefreshableListViewState("PUSH_TO_REFRESH", 2);
        RELEASE_TO_REFRESH = new RefreshableListViewState("RELEASE_TO_REFRESH", 3);
        LOADING = new RefreshableListViewState("LOADING", 4);
        BUFFERING = new RefreshableListViewState("BUFFERING", 5);
        RefreshableListViewState arefreshablelistviewstate[] = new RefreshableListViewState[6];
        arefreshablelistviewstate[0] = NORMAL;
        arefreshablelistviewstate[1] = PULL_TO_REFRESH;
        arefreshablelistviewstate[2] = PUSH_TO_REFRESH;
        arefreshablelistviewstate[3] = RELEASE_TO_REFRESH;
        arefreshablelistviewstate[4] = LOADING;
        arefreshablelistviewstate[5] = BUFFERING;
        $VALUES = arefreshablelistviewstate;
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrivacyList.java

package org.jivesoftware.smack;

import java.util.List;

public class PrivacyList
{

    protected PrivacyList(boolean flag, boolean flag1, String s, List list)
    {
        isActiveList = flag;
        isDefaultList = flag1;
        listName = s;
        items = list;
    }

    public List getItems()
    {
        return items;
    }

    public boolean isActiveList()
    {
        return isActiveList;
    }

    public boolean isDefaultList()
    {
        return isDefaultList;
    }

    public String toString()
    {
        return listName;
    }

    private boolean isActiveList;
    private boolean isDefaultList;
    private List items;
    private String listName;
}

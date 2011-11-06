// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendList.java

package com.facebook.katana.model;

import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

// Referenced classes of package com.facebook.katana.model:
//            PrivacySetting

public class FriendList extends JMCachingDictDestination
{

    private FriendList()
    {
        flid = INVALID_FBID;
        name = null;
        owner = INVALID_FBID;
    }

    public FriendList(long l, String s, long l1)
    {
        flid = l;
        name = s;
        owner = l1;
    }

    public PrivacySetting toPrivacySetting()
    {
        return new PrivacySetting(null, "CUSTOM", name, Long.toString(flid), null, null, "SOME_FRIENDS");
    }

    public static long INVALID_FBID = -1L;
    public final long flid;
    public final String name;
    public final long owner;

}

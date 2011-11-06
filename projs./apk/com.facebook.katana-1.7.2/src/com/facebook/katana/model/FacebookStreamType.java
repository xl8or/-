// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookStreamType.java

package com.facebook.katana.model;


public final class FacebookStreamType extends Enum
{

    private FacebookStreamType(String s, int i)
    {
        super(s, i);
    }

    public static FacebookStreamType valueOf(String s)
    {
        return (FacebookStreamType)Enum.valueOf(com/facebook/katana/model/FacebookStreamType, s);
    }

    public static FacebookStreamType[] values()
    {
        return (FacebookStreamType[])$VALUES.clone();
    }

    private static final FacebookStreamType $VALUES[];
    public static final FacebookStreamType EVENT_WALL_STREAM;
    public static final FacebookStreamType GROUP_WALL_STREAM;
    public static final FacebookStreamType NEWSFEED_STREAM;
    public static final FacebookStreamType PAGE_WALL_STREAM;
    public static final FacebookStreamType PLACE_ACTIVITY_STREAM;
    public static final FacebookStreamType PROFILE_WALL_STREAM;

    static 
    {
        NEWSFEED_STREAM = new FacebookStreamType("NEWSFEED_STREAM", 0);
        PROFILE_WALL_STREAM = new FacebookStreamType("PROFILE_WALL_STREAM", 1);
        GROUP_WALL_STREAM = new FacebookStreamType("GROUP_WALL_STREAM", 2);
        PLACE_ACTIVITY_STREAM = new FacebookStreamType("PLACE_ACTIVITY_STREAM", 3);
        PAGE_WALL_STREAM = new FacebookStreamType("PAGE_WALL_STREAM", 4);
        EVENT_WALL_STREAM = new FacebookStreamType("EVENT_WALL_STREAM", 5);
        FacebookStreamType afacebookstreamtype[] = new FacebookStreamType[6];
        afacebookstreamtype[0] = NEWSFEED_STREAM;
        afacebookstreamtype[1] = PROFILE_WALL_STREAM;
        afacebookstreamtype[2] = GROUP_WALL_STREAM;
        afacebookstreamtype[3] = PLACE_ACTIVITY_STREAM;
        afacebookstreamtype[4] = PAGE_WALL_STREAM;
        afacebookstreamtype[5] = EVENT_WALL_STREAM;
        $VALUES = afacebookstreamtype;
    }
}

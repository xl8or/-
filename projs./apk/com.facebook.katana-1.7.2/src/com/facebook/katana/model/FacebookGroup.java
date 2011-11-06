// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookGroup.java

package com.facebook.katana.model;

import java.util.Map;

// Referenced classes of package com.facebook.katana.model:
//            FacebookProfile

public class FacebookGroup extends FacebookProfile
{

    public FacebookGroup()
    {
        super(-1L, null, null, 3);
    }

    protected static void postprocessJMAutogenFields(Map map)
    {
        Object obj = map.remove("id");
        if(!$assertionsDisabled && obj == null)
            throw new AssertionError();
        map.put("gid", obj);
        Object obj1 = map.remove("pic_square");
        if(!$assertionsDisabled && obj1 == null)
        {
            throw new AssertionError();
        } else
        {
            map.put("pic_small", obj1);
            map.remove("type");
            return;
        }
    }

    public int getUnreadCount()
    {
        return mUnreadCount;
    }

    public void setUnreadCount(int i)
    {
        mUnreadCount = i;
    }

    static final boolean $assertionsDisabled;
    protected int mUnreadCount;
    public final long mUpdateTime = 0L;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/model/FacebookGroup.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

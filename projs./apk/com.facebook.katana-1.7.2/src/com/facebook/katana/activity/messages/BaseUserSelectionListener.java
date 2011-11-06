// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseUserSelectionListener.java

package com.facebook.katana.activity.messages;

import android.content.Context;
import com.facebook.katana.util.ApplicationUtils;

// Referenced classes of package com.facebook.katana.activity.messages:
//            UserSelectionListener

public class BaseUserSelectionListener
    implements UserSelectionListener
{

    BaseUserSelectionListener(Context context)
    {
        mContext = context;
    }

    public void onUserSelected(long l)
    {
        ApplicationUtils.OpenUserProfile(mContext, l, null);
    }

    private final Context mContext;
}

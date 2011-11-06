// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlUsersGetBriefInfo.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.FacebookUser;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGetUsersProfile, ApiMethodCallback, ApiMethodListener

public class FqlUsersGetBriefInfo extends FqlGetUsersProfile
    implements ApiMethodCallback
{

    public FqlUsersGetBriefInfo(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, Map map)
    {
        super(context, intent, s, apimethodlistener, map, com/facebook/katana/model/FacebookUser);
    }

    public static String getUsersBriefInfo(AppSession appsession, Context context, Long along[])
    {
        HashMap hashmap = new HashMap();
        int i = along.length;
        for(int j = 0; j < i; j++)
            hashmap.put(along[j], null);

        return appsession.postToService(context, new FqlUsersGetBriefInfo(context, null, appsession.getSessionInfo().sessionKey, null, hashmap), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onUsersGetInfoComplete(appsession, s, i, s1, exception, new ArrayList(getUsers().values())));
    }
}

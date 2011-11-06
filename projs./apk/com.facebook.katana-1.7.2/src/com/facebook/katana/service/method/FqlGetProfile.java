// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetProfile.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.StringUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGetProfileGeneric, ApiMethodCallback, ApiMethodListener

public class FqlGetProfile extends FqlGetProfileGeneric
    implements ApiMethodCallback
{

    public FqlGetProfile(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, s, apimethodlistener, s1, com/facebook/katana/model/FacebookProfile);
    }

    public FqlGetProfile(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, Map map)
    {
        this(context, intent, s, apimethodlistener, buildWhereClause(map));
    }

    public static String RequestGroupMembers(Context context, long l)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        String s = (new StringBuilder()).append("id IN (SELECT uid FROM group_member WHERE gid=").append(l).append(")").toString();
        return appsession.postToService(context, new FqlGetProfile(context, null, appsession.getSessionInfo().sessionKey, null, s), 1001, 601, null);
    }

    public static String RequestSingleProfile(Context context, long l)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        HashMap hashmap = new HashMap();
        hashmap.put(Long.valueOf(l), null);
        return appsession.postToService(context, new FqlGetProfile(context, null, appsession.getSessionInfo().sessionKey, null, hashmap), 1001, 84, null);
    }

    private static String buildWhereClause(Map map)
    {
        StringBuilder stringbuilder = new StringBuilder(" id IN(");
        Object aobj[] = new Object[1];
        aobj[0] = map.keySet();
        StringUtils.join(stringbuilder, ",", null, aobj);
        stringbuilder.append(")");
        return stringbuilder.toString();
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        intent.getIntExtra("extended_type", -1);
        JVM INSTR lookupswitch 2: default 36
    //                   84: 37
    //                   601: 126;
           goto _L1 _L2 _L3
_L1:
        return;
_L2:
        FacebookProfile facebookprofile = null;
        Iterator iterator1 = mProfiles.values().iterator();
        if(iterator1.hasNext())
            facebookprofile = (FacebookProfile)iterator1.next();
        Iterator iterator2 = appsession.getListeners().iterator();
        while(iterator2.hasNext()) 
            ((AppSessionListener)iterator2.next()).onGetProfileComplete(appsession, s, i, s1, exception, facebookprofile);
        continue; /* Loop/switch isn't completed */
_L3:
        Iterator iterator = appsession.getListeners().iterator();
        while(iterator.hasNext()) 
            ((AppSessionListener)iterator.next()).onGetGroupsMembersComplete(appsession, s, i, s1, exception, mProfiles);
        if(true) goto _L1; else goto _L4
_L4:
    }

    public Map getProfiles()
    {
        return Collections.unmodifiableMap(mProfiles);
    }

    private static final String TAG = "FqlGetProfile";
}

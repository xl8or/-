// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatHibernateKeepalive.java

package com.facebook.katana.binding;

import android.content.*;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.service.BackgroundRequestService;
import com.facebook.katana.util.URLQueryBuilder;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.facebook.katana.binding:
//            AppSession

public class ChatHibernateKeepalive extends BroadcastReceiver
{

    public ChatHibernateKeepalive()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        if(appsession != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
        if(facebooksessioninfo != null)
        {
            HashMap hashmap = new HashMap();
            hashmap.put("api_key", "882a8490361da98702bf97a021ddc14d");
            hashmap.put("uid", String.valueOf(facebooksessioninfo.userId));
            hashmap.put("session_key", facebooksessioninfo.sessionKey);
            StringBuilder stringbuilder = URLQueryBuilder.buildSignedQueryString(hashmap, facebooksessioninfo.sessionSecret);
            String s = (new StringBuilder()).append(com.facebook.katana.Constants.URL.getChatHibernateUrl(context)).append("?").append(stringbuilder.toString()).toString();
            Intent intent1 = new Intent(context, com/facebook/katana/service/BackgroundRequestService);
            intent1.putExtra("com.facebook.katana.service.BackgroundRequestService.operation", com.facebook.katana.service.BackgroundRequestService.Operation.HTTP_REQUEST);
            intent1.putExtra("com.facebook.katana.service.BackgroundRequestService.uri", s);
            context.startService(intent1);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UsersInvite.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.util.StringUtils;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodCallback, ApiMethodListener

public class UsersInvite extends ApiMethod
    implements ApiMethodCallback
{

    public UsersInvite(Context context, Intent intent, String s, List list, String s1, String s2, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "POST", "users.invite", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mContacts = list;
        mParams.put("call_id", String.valueOf(System.currentTimeMillis()));
        mParams.put("session_key", s);
        StringBuilder stringbuilder = new StringBuilder();
        com.facebook.katana.util.StringUtils.StringProcessor stringprocessor = new com.facebook.katana.util.StringUtils.StringProcessor() {

            public String formatString(Object obj)
            {
                return obj.toString().replace(",", "");
            }

            final UsersInvite this$0;

            
            {
                this$0 = UsersInvite.this;
                super();
            }
        }
;
        Object aobj[] = new Object[1];
        aobj[0] = list;
        StringUtils.join(stringbuilder, ",", stringprocessor, aobj);
        mParams.put("emails", stringbuilder.toString());
        if(s1 != null)
            mParams.put("message", s1);
        mParams.put("country_code", s2);
    }

    public static String invite(AppSession appsession, Context context, List list, String s, String s1)
    {
        return appsession.postToService(context, new UsersInvite(context, null, appsession.getSessionInfo().sessionKey, list, s, s1, null), 1001, 1020, null);
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onUsersInviteComplete(appsession, s, i, s1, exception, mContacts));
    }

    protected static final String EMAILS_PARAM = "emails";
    protected List mContacts;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetUsersProfile.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodListener

public class FqlGetUsersProfile extends FqlGeneratedQuery
{

    public FqlGetUsersProfile(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, Class class1)
    {
        super(context, intent, s, apimethodlistener, "user", s1, class1);
        mProfiles = new LinkedHashMap();
        mOpaque = null;
        mCls = class1;
        mFillEmptyProfiles = false;
    }

    public FqlGetUsersProfile(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, Map map, Class class1)
    {
        super(context, intent, s, apimethodlistener, "user", buildWhereClause(map), class1);
        mProfiles = map;
        mOpaque = null;
        mCls = class1;
        mFillEmptyProfiles = true;
    }

    public FqlGetUsersProfile(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, Map map, Object obj)
    {
        super(context, intent, s, apimethodlistener, "user", buildWhereClause(map), com/facebook/katana/model/FacebookUser);
        mProfiles = map;
        mOpaque = obj;
        mCls = com/facebook/katana/model/FacebookUser;
        mFillEmptyProfiles = true;
    }

    private static String buildWhereClause(Map map)
    {
        StringBuilder stringbuilder = new StringBuilder("uid IN(");
        Object aobj[] = new Object[1];
        aobj[0] = map.keySet();
        StringUtils.join(stringbuilder, ",", null, aobj);
        stringbuilder.append(")");
        return stringbuilder.toString();
    }

    public Object getOpaque()
    {
        return mOpaque;
    }

    public Map getUsers()
    {
        return mProfiles;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        List list = JMParser.parseObjectListJson(jsonparser, mCls);
        if(list != null)
        {
            FacebookUser facebookuser1;
            for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); mProfiles.put(Long.valueOf(facebookuser1.mUserId), facebookuser1))
                facebookuser1 = (FacebookUser)iterator1.next();

        }
        if(mFillEmptyProfiles)
        {
            Iterator iterator = mProfiles.entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                if(entry.getValue() == null)
                {
                    FacebookUser facebookuser = FacebookUser.newInstance(mCls, mContext.getString(0x7f0a0067));
                    mProfiles.put(entry.getKey(), facebookuser);
                }
            } while(true);
        }
    }

    private static final String TAG = "FqlGetUsersProfile";
    private final Class mCls;
    private final boolean mFillEmptyProfiles;
    private final Object mOpaque;
    private final Map mProfiles;
}

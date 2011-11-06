// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetProfile.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodListener

class FqlGetProfileGeneric extends FqlGeneratedQuery
{

    FqlGetProfileGeneric(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, Class class1)
    {
        super(context, intent, s, apimethodlistener, "profile", s1, class1);
        mCls = class1;
    }

    protected Map getProfiles()
    {
        return Collections.unmodifiableMap(mProfiles);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        FacebookProfile facebookprofile;
        for(Iterator iterator = JMParser.parseObjectListJson(jsonparser, mCls).iterator(); iterator.hasNext(); mProfiles.put(Long.valueOf(facebookprofile.mId), facebookprofile))
            facebookprofile = (FacebookProfile)iterator.next();

    }

    protected final Class mCls;
    protected final Map mProfiles = new HashMap();
}

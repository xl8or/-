// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetFriendLists.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FriendList;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodListener

public class FqlGetFriendLists extends FqlGeneratedQuery
{

    protected FqlGetFriendLists(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
    {
        this(context, intent, s, apimethodlistener, buildWhereClause(l));
    }

    protected FqlGetFriendLists(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
    {
        super(context, intent, s, apimethodlistener, "friendlist", s1, com/facebook/katana/model/FriendList);
    }

    protected static String buildWhereClause(long l)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("owner=").append(l).append(" ORDER BY name");
        return stringbuilder.toString();
    }

    public List getFriendLists()
    {
        return Collections.unmodifiableList(mFriendLists);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        mFriendLists = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/FriendList);
    }

    private List mFriendLists;
}

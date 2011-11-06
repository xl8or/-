// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetUsersFriendStatus.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import java.io.IOException;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class FqlGetUsersFriendStatus extends FqlQuery
{

    public FqlGetUsersFriendStatus(Context context, Intent intent, String s, long l, long l1, 
            ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, (new StringBuilder()).append("SELECT uid1, uid2 FROM friend WHERE uid1=").append(l).append(" AND uid2=").append(l1).toString(), apimethodlistener);
        uid1 = l;
        uid2 = l1;
    }

    public boolean areFriends()
    {
        return mFriends;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        Long long1;
        Long long2;
        JsonToken jsontoken1;
        JsonToken jsontoken = jsonparser.getCurrentToken();
        long1 = null;
        long2 = null;
        if(jsontoken != JsonToken.START_ARRAY)
            throw new IOException("Unexpected JSON response");
        jsontoken1 = jsonparser.nextToken();
        if(jsontoken1 != JsonToken.END_ARRAY) goto _L2; else goto _L1
_L1:
        jsonparser.nextToken();
        JsonToken jsontoken2;
        String s;
        if(long1 != null && long2 != null && long1.longValue() == uid1 && long2.longValue() == uid2)
            mFriends = true;
        else
            mFriends = false;
        return;
_L2:
        if(!$assertionsDisabled && jsontoken1 != JsonToken.START_OBJECT)
            throw new AssertionError();
        jsontoken2 = jsonparser.nextToken();
_L4:
        if(jsontoken2 == JsonToken.END_OBJECT)
            continue;
        if(jsontoken2 == JsonToken.VALUE_STRING)
        {
            s = jsonparser.getCurrentName();
            if(!s.equals("uid1"))
                break; /* Loop/switch isn't completed */
            long1 = Long.valueOf(jsonparser.getText());
        }
_L6:
        jsontoken2 = jsonparser.nextToken();
        if(true) goto _L4; else goto _L3
_L3:
        if(!s.equals("uid2")) goto _L6; else goto _L5
_L5:
        long2 = Long.valueOf(jsonparser.getText());
          goto _L6
        while(jsontoken2 != JsonToken.END_ARRAY) 
        {
            if(jsontoken2 == JsonToken.START_ARRAY || jsontoken2 == JsonToken.START_OBJECT)
            {
                jsonparser.skipChildren();
                if(!$assertionsDisabled && jsonparser.getCurrentToken() != JsonToken.END_ARRAY && jsonparser.getCurrentToken() != JsonToken.END_OBJECT)
                    throw new AssertionError();
            }
            jsontoken2 = jsonparser.nextToken();
        }
          goto _L1
    }

    static final boolean $assertionsDisabled;
    private boolean mFriends;
    private long uid1;
    private long uid2;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/FqlGetUsersFriendStatus.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

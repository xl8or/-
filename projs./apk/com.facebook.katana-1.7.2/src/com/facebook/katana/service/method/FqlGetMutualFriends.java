// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetMutualFriends.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.util.Log;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class FqlGetMutualFriends extends FqlQuery
{

    public FqlGetMutualFriends(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, String s1)
    {
        super(context, intent, s, buildQuery(l, s1), apimethodlistener);
    }

    protected static String buildQuery(long l, String s)
    {
        StringBuilder stringbuilder = new StringBuilder("SELECT uid1, uid2 FROM friend WHERE uid1 IN (SELECT uid1  FROM friend  WHERE uid2=");
        stringbuilder.append(String.valueOf(l));
        stringbuilder.append(") AND ");
        stringbuilder.append(s);
        return stringbuilder.toString();
    }

    public Map getMutualFriends()
    {
        return Collections.unmodifiableMap(mMutualFriends);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(!$assertionsDisabled && jsontoken != JsonToken.START_ARRAY)
            throw new AssertionError();
        JsonToken jsontoken1 = jsonparser.nextToken();
        while(jsontoken1 != JsonToken.END_ARRAY) 
        {
            if(jsontoken1 == JsonToken.START_OBJECT)
            {
                Long long1 = null;
                Long long2 = null;
                JsonToken jsontoken2 = jsonparser.nextToken();
                while(jsontoken2 != JsonToken.END_OBJECT) 
                {
                    if(jsontoken2 == JsonToken.VALUE_NUMBER_INT || jsontoken2 == JsonToken.VALUE_STRING)
                    {
                        if(jsonparser.getCurrentName() == "uid1")
                            long1 = Long.valueOf(jsonparser.getText());
                        else
                        if(jsonparser.getCurrentName() == "uid2")
                            long2 = Long.valueOf(jsonparser.getText());
                    } else
                    if(jsontoken2 == JsonToken.START_OBJECT || jsontoken2 == JsonToken.START_ARRAY)
                        jsonparser.skipChildren();
                    jsontoken2 = jsonparser.nextToken();
                }
                if(long1 == null || long2 == null)
                {
                    Log.e("FqlGetMutualFriends", "Missing uid1 or uid2 from response");
                } else
                {
                    Object obj = (List)mMutualFriends.get(long2);
                    if(obj == null)
                    {
                        obj = new ArrayList();
                        mMutualFriends.put(long2, obj);
                    }
                    ((List) (obj)).add(long1);
                }
            } else
            if(jsontoken1 == JsonToken.START_ARRAY)
                jsonparser.skipChildren();
            jsontoken1 = jsonparser.nextToken();
        }
    }

    static final boolean $assertionsDisabled = false;
    private static final String TAG = "FqlGetMutualFriends";
    private final Map mMutualFriends = new HashMap();

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/FqlGetMutualFriends.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

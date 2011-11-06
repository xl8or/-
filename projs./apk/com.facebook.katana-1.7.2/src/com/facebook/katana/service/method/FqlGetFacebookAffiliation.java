// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetFacebookAffiliation.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookAffiliation;
import com.facebook.katana.model.FacebookApiException;
import java.io.IOException;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class FqlGetFacebookAffiliation extends FqlQuery
{

    public FqlGetFacebookAffiliation(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
    {
        super(context, intent, s, buildQuery(l), apimethodlistener);
    }

    private static String buildQuery(long l)
    {
        StringBuilder stringbuilder = new StringBuilder("SELECT affiliations FROM user WHERE uid=");
        stringbuilder.append(l);
        return stringbuilder.toString();
    }

    private static boolean parseAffiliationsJSON(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        JsonToken jsontoken1;
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(!$assertionsDisabled && jsontoken != JsonToken.START_ARRAY)
            throw new AssertionError();
        jsontoken1 = jsonparser.nextToken();
_L8:
        if(jsontoken1 == JsonToken.END_ARRAY) goto _L2; else goto _L1
_L1:
        if(jsontoken1 != JsonToken.START_OBJECT) goto _L4; else goto _L3
_L3:
        JsonToken jsontoken2 = jsonparser.nextToken();
_L7:
        if(jsontoken2 == JsonToken.END_OBJECT)
            continue; /* Loop/switch isn't completed */
        if(jsontoken2 != JsonToken.VALUE_STRING && jsontoken2 != JsonToken.VALUE_NUMBER_INT || !jsonparser.getCurrentName().equals("nid") || !jsonparser.getText().equals("50431648")) goto _L6; else goto _L5
_L5:
        boolean flag = true;
_L9:
        return flag;
_L6:
        if(jsontoken2 == JsonToken.START_OBJECT || jsontoken2 == JsonToken.START_ARRAY)
            jsonparser.skipChildren();
        jsontoken2 = jsonparser.nextToken();
          goto _L7
_L4:
        if(jsontoken1 == JsonToken.START_ARRAY)
            jsonparser.skipChildren();
        jsontoken1 = jsonparser.nextToken();
          goto _L8
_L2:
        flag = false;
          goto _L9
    }

    private static boolean parseUserArrayJSON(JsonParser jsonparser)
        throws JsonParseException, IOException
    {
        JsonToken jsontoken;
        jsontoken = jsonparser.getCurrentToken();
        if(!$assertionsDisabled && jsontoken != JsonToken.START_ARRAY)
            throw new AssertionError();
          goto _L1
_L5:
        jsontoken = jsonparser.nextToken();
_L1:
        if(jsontoken == JsonToken.END_ARRAY) goto _L3; else goto _L2
_L2:
        if(jsontoken != JsonToken.START_OBJECT) goto _L5; else goto _L4
_L4:
        String s;
        JsonToken jsontoken1;
        s = null;
        jsontoken1 = jsonparser.nextToken();
_L9:
        if(jsontoken1 == JsonToken.END_OBJECT) goto _L5; else goto _L6
_L6:
        if(jsontoken1 != JsonToken.FIELD_NAME) goto _L8; else goto _L7
_L7:
        s = jsonparser.getText();
_L11:
        jsontoken1 = jsonparser.nextToken();
          goto _L9
_L8:
        if(jsontoken1 != JsonToken.START_ARRAY || s == null || !s.equals("affiliations")) goto _L11; else goto _L10
_L10:
        boolean flag = parseAffiliationsJSON(jsonparser);
_L13:
        return flag;
_L3:
        flag = false;
        if(true) goto _L13; else goto _L12
_L12:
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        boolean flag = false;
        if(jsontoken == JsonToken.START_ARRAY)
            flag = parseUserArrayJSON(jsonparser);
        FacebookAffiliation.setIsEmployee(mContext, flag);
    }

    static final boolean $assertionsDisabled = false;
    private static final String FACEBOOK_NETWORK_ID = "50431648";
    private static final String TAG = "FqlGetFacebookAffiliation";

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/FqlGetFacebookAffiliation.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

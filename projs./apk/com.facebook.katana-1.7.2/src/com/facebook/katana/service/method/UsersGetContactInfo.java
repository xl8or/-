// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UsersGetContactInfo.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookContactInfo;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener

public class UsersGetContactInfo extends ApiMethod
{

    public UsersGetContactInfo(Context context, Intent intent, String s, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", "users.getContactInfo", com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mParams.put("call_id", (new StringBuilder()).append("").append(System.currentTimeMillis()).toString());
        mParams.put("session_key", s);
        mParams.put("uids", s1);
    }

    public List getContactInfoList()
    {
        return mContactInfoList;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
        {
            int i = -1;
            String s = null;
            JsonToken jsontoken2 = jsonparser.nextToken();
            while(jsontoken2 != JsonToken.END_OBJECT) 
            {
                if(jsontoken2 == JsonToken.VALUE_NUMBER_INT)
                {
                    if(jsonparser.getCurrentName().equals("error_code"))
                        i = jsonparser.getIntValue();
                } else
                if(jsontoken2 == JsonToken.VALUE_STRING && jsonparser.getCurrentName().equals("error_msg"))
                    s = jsonparser.getText();
                jsontoken2 = jsonparser.nextToken();
            }
            if(i != -1)
                throw new FacebookApiException(i, s);
        } else
        if(jsontoken == JsonToken.START_ARRAY)
        {
            for(JsonToken jsontoken1 = jsonparser.nextToken(); jsontoken1 != JsonToken.END_ARRAY; jsontoken1 = jsonparser.nextToken())
                if(jsontoken1 == JsonToken.START_OBJECT)
                    mContactInfoList.add(new FacebookContactInfo(jsonparser));

        } else
        {
            throw new IOException("Malformed JSON");
        }
    }

    private final List mContactInfoList = new ArrayList();
}

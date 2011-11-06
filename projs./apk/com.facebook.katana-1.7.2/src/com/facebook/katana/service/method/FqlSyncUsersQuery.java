// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlSyncUsersQuery.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.*;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class FqlSyncUsersQuery extends FqlQuery
{

    public FqlSyncUsersQuery(Context context, Intent intent, String s, long l, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, "SELECT uid,first_name,last_name,name,pic_square,cell,other_phone,contact_email,birthday_date FROM user WHERE (uid IN (SELECT uid2 FROM friend WHERE uid1=%1));".replaceFirst("%1", (new StringBuilder()).append("").append(l).toString()), apimethodlistener);
    }

    public List getFriends()
    {
        return mFriends;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
        {
            int i = -1;
            String s = null;
            JsonToken jsontoken1 = jsonparser.nextToken();
            while(jsontoken1 != JsonToken.END_OBJECT) 
            {
                if(jsontoken1 == JsonToken.VALUE_NUMBER_INT)
                {
                    if(jsonparser.getCurrentName().equals("error_code"))
                        i = jsonparser.getIntValue();
                } else
                if(jsontoken1 == JsonToken.VALUE_STRING && jsonparser.getCurrentName().equals("error_msg"))
                    s = jsonparser.getText();
                jsontoken1 = jsonparser.nextToken();
            }
            if(i > 0)
                throw new FacebookApiException(i, s);
        } else
        if(jsontoken == JsonToken.START_ARRAY)
            for(; jsontoken != JsonToken.END_ARRAY; jsontoken = jsonparser.nextToken())
                if(jsontoken == JsonToken.START_OBJECT)
                    mFriends.add((FacebookFriendInfo)FacebookUser.parseFromJSON(com/facebook/katana/model/FacebookFriendInfo, jsonparser));

    }

    private static final String FRIENDS_QUERY = "SELECT uid,first_name,last_name,name,pic_square,cell,other_phone,contact_email,birthday_date FROM user WHERE (uid IN (SELECT uid2 FROM friend WHERE uid1=%1));";
    private final List mFriends = new ArrayList();
}

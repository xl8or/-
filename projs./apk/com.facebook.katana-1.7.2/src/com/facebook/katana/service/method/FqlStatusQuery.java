// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlStatusQuery.java

package com.facebook.katana.service.method;

import android.content.*;
import com.facebook.katana.model.*;
import com.facebook.katana.platform.PlatformStorage;
import com.facebook.katana.provider.UserStatusesProvider;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.*;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlQuery, ApiMethodListener

public class FqlStatusQuery extends FqlQuery
{

    public FqlStatusQuery(Context context, Intent intent, String s, String s1, String s2, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, s1, apimethodlistener);
        mMyUsername = s2;
    }

    private static void saveUserStatuses(Context context, String s, List list)
    {
        ContentResolver contentresolver = context.getContentResolver();
        contentresolver.delete(UserStatusesProvider.CONTENT_URI, null, null);
        ContentValues contentvalues = new ContentValues();
        for(Iterator iterator = list.iterator(); iterator.hasNext(); contentresolver.insert(UserStatusesProvider.CONTENT_URI, contentvalues))
        {
            FacebookStatus facebookstatus = (FacebookStatus)iterator.next();
            contentvalues.clear();
            contentvalues.put("user_id", Long.valueOf(facebookstatus.getUser().mUserId));
            contentvalues.put("first_name", facebookstatus.getUser().mFirstName);
            contentvalues.put("last_name", facebookstatus.getUser().mLastName);
            contentvalues.put("display_name", facebookstatus.getUser().getDisplayName());
            contentvalues.put("user_pic", facebookstatus.getUser().mImageUrl);
            contentvalues.put("message", facebookstatus.getMessage());
            contentvalues.put("timestamp", Long.valueOf(facebookstatus.getTime()));
        }

        if(PlatformUtils.platformStorageSupported(context))
            PlatformStorage.insertStatuses(context, s, list);
    }

    public List getStatusList()
    {
        return mStatusList;
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        JsonToken jsontoken = jsonparser.getCurrentToken();
        if(jsontoken == JsonToken.START_OBJECT)
        {
            FacebookApiException facebookapiexception = new FacebookApiException(jsonparser);
            if(facebookapiexception.getErrorCode() != -1)
                throw facebookapiexception;
        } else
        if(jsontoken == JsonToken.START_ARRAY)
        {
            for(JsonToken jsontoken1 = jsonparser.nextToken(); jsontoken1 != JsonToken.END_ARRAY; jsontoken1 = jsonparser.nextToken())
                if(jsontoken1 == JsonToken.START_OBJECT)
                    mStatusList.add(new FacebookStatus(jsonparser));

        } else
        {
            throw new IOException("Malformed JSON");
        }
        if(mStatusList.size() > 0)
            saveUserStatuses(mContext, mMyUsername, mStatusList);
    }

    public static final String FRIENDS_STATUS_QUERY = "SELECT uid,first_name,last_name,name,status,pic_square FROM user WHERE ((uid IN (SELECT target_id FROM connection WHERE source_id=%1$d AND target_type='user' AND is_following=1)) AND (status.message != '')) ORDER BY status.time DESC LIMIT 25";
    private final String mMyUsername;
    private final List mStatusList = new ArrayList();
}

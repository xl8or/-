// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookUserPersistent.java

package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.Utils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.model:
//            FacebookUser

public class FacebookUserPersistent extends FacebookUser
{
    private static interface FriendsQuery
    {

        public static final int INDEX_USER_DISPLAY_NAME = 3;
        public static final int INDEX_USER_FIRST_NAME = 1;
        public static final int INDEX_USER_ID = 0;
        public static final int INDEX_USER_IMAGE_URL = 4;
        public static final int INDEX_USER_LAST_NAME = 2;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[5];
            as[0] = "user_id";
            as[1] = "first_name";
            as[2] = "last_name";
            as[3] = "display_name";
            as[4] = "user_image_url";
        }
    }


    public FacebookUserPersistent()
    {
    }

    public FacebookUserPersistent(long l, String s, String s1, String s2, String s3)
    {
        super(l, s, s1, s2, s3);
    }

    public FacebookUserPersistent(JsonParser jsonparser)
        throws JsonParseException, IllegalAccessException, InstantiationException, IOException, JMException
    {
        parseFromJSON(com/facebook/katana/model/FacebookUser, jsonparser);
    }

    public static FacebookUserPersistent readFromContentProvider(Context context, long l)
    {
        Uri uri = Uri.withAppendedPath(ConnectionsProvider.FRIEND_UID_CONTENT_URI, String.valueOf(l));
        Cursor cursor = context.getContentResolver().query(uri, FriendsQuery.PROJECTION, null, null, null);
        FacebookUserPersistent facebookuserpersistent;
        if(cursor.moveToFirst())
            facebookuserpersistent = new FacebookUserPersistent(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        else
            facebookuserpersistent = null;
        cursor.close();
        return facebookuserpersistent;
    }

    public String computeHash()
    {
        if(mHashCode == null)
        {
            Object aobj[] = new Object[5];
            aobj[0] = Long.valueOf(mUserId);
            aobj[1] = mFirstName;
            aobj[2] = mLastName;
            aobj[3] = mDisplayName;
            aobj[4] = mImageUrl;
            mHashCode = String.valueOf(Utils.hashItemsLong(aobj));
        }
        return mHashCode;
    }

    private String mHashCode;
}

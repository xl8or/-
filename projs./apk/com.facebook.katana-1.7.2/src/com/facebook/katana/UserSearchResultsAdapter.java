// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserSearchResultsAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.database.Cursor;
import com.facebook.katana.binding.StreamPhotosCache;

// Referenced classes of package com.facebook.katana:
//            ProfileSearchResultsAdapter

public abstract class UserSearchResultsAdapter extends ProfileSearchResultsAdapter
{
    public static interface SearchResultsQuery
    {

        public static final int INDEX_USER_DISPLAY_NAME = 2;
        public static final int INDEX_USER_ID = 1;
        public static final int INDEX_USER_IMAGE_URL = 3;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "_id";
            as[1] = "user_id";
            as[2] = "display_name";
            as[3] = "user_image_url";
        }
    }


    public UserSearchResultsAdapter(Context context, Cursor cursor, StreamPhotosCache streamphotoscache)
    {
        super(context, cursor, streamphotoscache);
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PageSearchResultsAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.database.Cursor;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.facebook.katana:
//            ProfileSearchResultsAdapter

public class PageSearchResultsAdapter extends ProfileSearchResultsAdapter
{
    public static interface SearchResultsQuery
    {

        public static final int INDEX_PAGE_DISPLAY_NAME = 2;
        public static final int INDEX_PAGE_ID = 1;
        public static final int INDEX_PAGE_IMAGE_URL = 3;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "_id";
            as[1] = "page_id";
            as[2] = "display_name";
            as[3] = "pic";
        }
    }


    public PageSearchResultsAdapter(Context context, Cursor cursor, StreamPhotosCache streamphotoscache)
    {
        super(context, cursor, streamphotoscache);
    }

    public Object getChild(int i, int j)
    {
        mCursor.moveToPosition(getChildActualPosition(i, j));
        return new FacebookProfile(mCursor.getLong(1), mCursor.getString(2), mCursor.getString(3), 1);
    }

    public int getSectionCount()
    {
        int i;
        if(mCursor == null || mCursor.getCount() == 0)
            i = 0;
        else
            i = mSections.size();
        return i;
    }

    public void refreshData(Cursor cursor)
    {
        mCursor = cursor;
        mSections = new ArrayList();
        if(cursor != null)
        {
            mSections.add(new com.facebook.katana.activity.profilelist.ProfileListCursorAdapter.Section(mContext.getString(0x7f0a0115), 0, cursor.getCount()));
            notifyDataSetChanged();
        }
    }
}

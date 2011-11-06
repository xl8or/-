// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileListNaiveCursorAdapter.java

package com.facebook.katana.activity.profilelist;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Filter;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListImageCacheAdapter

public class ProfileListNaiveCursorAdapter extends ProfileListImageCacheAdapter
{
    public static interface FriendsQuery
    {

        public static final String FILTER = "display_name IS NOT NULL AND LENGTH(display_name) > 0";
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


    public ProfileListNaiveCursorAdapter(Context context, ProfileImagesCache profileimagescache, Cursor cursor)
    {
        super(context, profileimagescache, cursor);
    }

    public Object getChild(int i, int j)
    {
        mCursor.moveToPosition(getChildActualPosition(i, j));
        return new FacebookProfile(mCursor.getLong(1), mCursor.getString(2), mCursor.getString(3), 0);
    }

    protected Object getItemType(Cursor cursor)
    {
        return cursor.getString(2).substring(0, 1);
    }

    protected String getTitleForType(Object obj)
    {
        return (String)obj;
    }

    public void refreshData(Cursor cursor)
    {
        mCursor = cursor;
        mSections = new ArrayList();
        if(cursor != null)
        {
            String s = "";
            int i = -1;
            int j = 0;
            int k = cursor.getCount();
            int l = 0;
            cursor.moveToFirst();
            while(l < k) 
            {
                String s1 = cursor.getString(2).substring(0, 1);
                if(!s.equals(s1))
                {
                    if(j > 0)
                        mSections.add(new ProfileListCursorAdapter.Section(s, i, j));
                    s = s1;
                    i = l;
                    j = 0;
                }
                l++;
                j++;
                cursor.moveToNext();
            }
            if(j > 0)
                mSections.add(new ProfileListCursorAdapter.Section(s, i, j));
            notifyDataSetChanged();
        }
    }

    public final Filter mFilter = new Filter() {

        protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
        {
            Cursor cursor1;
            android.widget.Filter.FilterResults filterresults;
            if(charsequence != null && charsequence.length() > 0)
            {
                Uri uri = Uri.withAppendedPath(ConnectionsProvider.FRIENDS_SEARCH_CONTENT_URI, charsequence.toString());
                cursor1 = ((Activity)mContext).managedQuery(uri, FriendsQuery.PROJECTION, "display_name IS NOT NULL AND LENGTH(display_name) > 0", null, null);
            } else
            {
                cursor1 = ((Activity)mContext).managedQuery(ConnectionsProvider.FRIENDS_CONTENT_URI, FriendsQuery.PROJECTION, "display_name IS NOT NULL AND LENGTH(display_name) > 0", null, null);
            }
            filterresults = new android.widget.Filter.FilterResults();
            filterresults.count = cursor1.getCount();
            filterresults.values = cursor1;
            return filterresults;
        }

        protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
        {
            if(filterresults.values != null)
            {
                Cursor cursor1 = (Cursor)filterresults.values;
                refreshData(cursor1);
            }
            notifyDataSetChanged();
        }

        final ProfileListNaiveCursorAdapter this$0;

            
            {
                this$0 = ProfileListNaiveCursorAdapter.this;
                super();
            }
    }
;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PageListAdapter.java

package com.facebook.katana;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Filter;
import com.facebook.katana.activity.profilelist.ProfileListImageCacheAdapter;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;

public class PageListAdapter extends ProfileListImageCacheAdapter
{
    public static interface UserPagesQuery
    {

        public static final int INDEX_PAGE_DISPLAY_NAME = 2;
        public static final int INDEX_PAGE_ID = 1;
        public static final int INDEX_PAGE_IMAGE_URL = 3;
        public static final int INDEX_PAGE_TYPE = 4;
        public static final String ORDER_BY = "connection_type, display_name";
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[5];
            as[0] = "_id";
            as[1] = "user_id";
            as[2] = "display_name";
            as[3] = "user_image_url";
            as[4] = "connection_type";
        }
    }


    public PageListAdapter(Context context, ProfileImagesCache profileimagescache, Cursor cursor)
    {
        super(context, profileimagescache, cursor);
        mFilter = new Filter() {

            protected android.widget.Filter.FilterResults performFiltering(CharSequence charsequence)
            {
                Cursor cursor1;
                android.widget.Filter.FilterResults filterresults;
                if(charsequence != null && charsequence.length() > 0)
                {
                    Uri uri = Uri.withAppendedPath(ConnectionsProvider.PAGES_SEARCH_CONTENT_URI, charsequence.toString());
                    cursor1 = ((Activity)
// JavaClassFileOutputException: get_constant: invalid tag

            protected void publishResults(CharSequence charsequence, android.widget.Filter.FilterResults filterresults)
            {
                if(filterresults.values != null)
                {
                    Cursor cursor1 = (Cursor)filterresults.values;
                    refreshData(cursor1);
                }
                notifyDataSetChanged();
            }

            final PageListAdapter this$0;

            
            {
                this$0 = PageListAdapter.this;
                super();
            }
        }
;
    }

    public Object getChild(int i, int j)
    {
        mCursor.moveToPosition(getChildActualPosition(i, j));
        return new FacebookProfile(mCursor.getLong(1), mCursor.getString(2), mCursor.getString(3), 1);
    }

    protected Object getItemType(Cursor cursor)
    {
        return Integer.valueOf(cursor.getInt(4));
    }

    protected String getTitleForType(Object obj)
    {
        String s;
        if(obj.equals(Integer.valueOf(com.facebook.katana.provider.ConnectionsProvider.ConnectionType.PAGE_ADMIN.ordinal())))
            s = mContext.getString(0x7f0a0116);
        else
        if(obj.equals(Integer.valueOf(com.facebook.katana.provider.ConnectionsProvider.ConnectionType.PAGE_FAN.ordinal())))
            s = mContext.getString(0x7f0a0117);
        else
            s = null;
        return s;
    }

    Filter mFilter;


}

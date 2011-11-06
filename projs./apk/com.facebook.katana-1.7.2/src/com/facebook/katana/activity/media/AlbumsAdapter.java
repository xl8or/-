// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbumsAdapter.java

package com.facebook.katana.activity.media;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.util.ImageUtils;
import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends CursorAdapter
{
    private static interface AlbumsQuery
    {

        public static final int INDEX_ALBUM_ID = 1;
        public static final int INDEX_COVER_PHOTO_ID = 2;
        public static final int INDEX_COVER_PHOTO_URL = 3;
        public static final int INDEX_COVER_THUMBNAIL = 4;
        public static final int INDEX_NAME = 6;
        public static final int INDEX_SIZE = 5;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[7];
            as[0] = "_id";
            as[1] = "aid";
            as[2] = "cover_pid";
            as[3] = "cover_url";
            as[4] = "thumbnail";
            as[5] = "size";
            as[6] = "name";
        }
    }


    public AlbumsAdapter(Context context, Uri uri, String s, AppSession appsession)
    {
        super(context, ((Activity)context).managedQuery(uri, AlbumsQuery.PROJECTION, s, null, null), true);
        mContext = context;
        mAppSession = appsession;
        mOwner = Long.valueOf((String)uri.getPathSegments().get(2)).longValue();
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        String s = cursor.getString(1);
        ViewHolder viewholder = (ViewHolder)view.getTag();
        viewholder.setItemId(s);
        byte abyte0[] = cursor.getBlob(4);
        TextView textview;
        int i;
        String s2;
        if(abyte0 != null)
        {
            android.graphics.Bitmap bitmap = ImageUtils.decodeByteArray(abyte0, 0, abyte0.length);
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
        } else
        {
            String s1 = cursor.getString(3);
            if(s1 != null)
            {
                viewholder.mImageView.setImageResource(0x7f020100);
                String s3 = cursor.getString(2);
                mAppSession.downloadAlbumThumbail(mContext, mOwner, s, s3, s1);
            } else
            {
                viewholder.mImageView.setImageResource(0x7f020100);
            }
        }
        ((TextView)view.findViewById(0x7f0e0005)).setText(cursor.getString(6));
        textview = (TextView)view.findViewById(0x7f0e000c);
        i = cursor.getInt(5);
        if(i == 0)
            s2 = mContext.getString(0x7f0a0014);
        else
        if(i == 1)
        {
            s2 = mContext.getString(0x7f0a0015);
        } else
        {
            Context context1 = mContext;
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(i);
            s2 = context1.getString(0x7f0a0016, aobj);
        }
        textview.setText(s2);
    }

    public String getAlbumId(Cursor cursor)
    {
        return cursor.getString(1);
    }

    public String getAlbumName(Cursor cursor)
    {
        return cursor.getString(6);
    }

    public int getAlbumSize(Cursor cursor)
    {
        return cursor.getInt(5);
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewgroup)
    {
        View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030002, null);
        ViewHolder viewholder = new ViewHolder(view, 0x7f0e0004);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        return view;
    }

    private final AppSession mAppSession;
    private final Context mContext;
    private final long mOwner;
    private final List mViewHolders = new ArrayList();
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotosAdapter.java

package com.facebook.katana.activity.media;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.view.*;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.provider.PhotosProvider;
import com.facebook.katana.util.ImageUtils;
import java.util.*;

public class PhotosAdapter extends CursorAdapter
{
    public static interface PhotosQuery
    {

        public static final int INDEX_PHOTO_ID = 1;
        public static final int INDEX_SRC = 2;
        public static final int INDEX_THUMBNAIL = 3;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "_id";
            as[1] = "pid";
            as[2] = "src";
            as[3] = "thumbnail";
        }
    }


    public PhotosAdapter(Context context, String s, long l, AppSession appsession)
    {
        super(context, ((Activity)context).managedQuery(PhotosProvider.PHOTOS_CONTENT_URI, PhotosQuery.PROJECTION, (new StringBuilder()).append("aid=").append(DatabaseUtils.sqlEscapeString(s)).append(" AND ").append("owner").append("=").append(l).toString(), null, null), true);
        mContext = context;
        mAlbumId = s;
        mAppSession = appsession;
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        String s;
        ViewHolder viewholder;
        byte abyte0[];
        s = cursor.getString(1);
        viewholder = (ViewHolder)view.getTag();
        viewholder.setItemId(s);
        abyte0 = cursor.getBlob(3);
        if(abyte0 == null) goto _L2; else goto _L1
_L1:
        android.graphics.Bitmap bitmap = ImageUtils.decodeByteArray(abyte0, 0, abyte0.length);
        if(bitmap != null)
            viewholder.mImageView.setImageBitmap(bitmap);
_L4:
        return;
_L2:
        String s1 = cursor.getString(2);
        if(s1 != null)
        {
            viewholder.mImageView.setImageResource(0x7f020100);
            if(!mDownloadPending.containsKey(s))
            {
                mAppSession.downloadPhotoThumbail(mContext, mAlbumId, s, s1);
                mDownloadPending.put(s, s1);
            }
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200ff);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewgroup)
    {
        View view = ((LayoutInflater)mContext.getSystemService("layout_inflater")).inflate(0x7f030053, null);
        ViewHolder viewholder = new ViewHolder(view, 0x7f0e00e5);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        return view;
    }

    protected void onContentChanged()
    {
        super.onContentChanged();
    }

    public void onDownloadPhotoError(String s)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            if(!s.equals(viewholder.getItemId()))
                continue;
            viewholder.mImageView.setImageResource(0x7f0200ff);
            break;
        } while(true);
    }

    private final String mAlbumId;
    private final AppSession mAppSession;
    private final Context mContext;
    private final Map mDownloadPending = new HashMap();
    private final List mViewHolders = new ArrayList();
}

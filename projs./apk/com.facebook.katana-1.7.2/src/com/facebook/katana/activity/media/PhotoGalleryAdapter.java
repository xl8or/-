// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhotoGalleryAdapter.java

package com.facebook.katana.activity.media;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.widget.*;
import com.facebook.katana.RotateBitmap;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.ui.ImageViewTouchBase;
import com.facebook.katana.util.*;
import java.util.ArrayList;
import java.util.Map;

public class PhotoGalleryAdapter extends CursorAdapter
{
    private static class ViewHolder
    {

        TextView mCaption;
        ImageViewTouchBase mImageView;
        ProgressBar mProgressBar;

        private ViewHolder()
        {
        }

    }


    public PhotoGalleryAdapter(Context context, Cursor cursor, AppSession appsession, Map map, String s)
    {
        super(context, cursor);
        mContext = context;
        mPendingDownloadMap = map;
        mAlbumId = s;
        mAppSession = appsession;
        mInflater = LayoutInflater.from(context);
        mViewHolders = new ArrayList();
        mPhotoDownloadingBitmap = BitmapFactory.decodeResource(mContext.getResources(), 0x7f020100);
    }

    private void setPhotoCaption(TextView textview, String s)
    {
        if(s == null || s.length() == 0)
        {
            textview.setVisibility(4);
        } else
        {
            textview.setVisibility(0);
            textview.setText(s);
        }
    }

    public void bindView(View view, Context context, Cursor cursor)
    {
        ViewHolder viewholder;
        String s1;
        String s = cursor.getString(1);
        viewholder = (ViewHolder)view.getTag();
        s1 = cursor.getString(5);
        setPhotoCaption(viewholder.mCaption, cursor.getString(2));
        if(s1 == null)
        {
            try
            {
                byte abyte0[] = cursor.getBlob(4);
                if(abyte0 != null)
                {
                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(abyte0, 0, abyte0.length);
                    viewholder.mImageView.setImageRotateBitmapResetBase(new RotateBitmap(bitmap1, 0), true);
                } else
                {
                    viewholder.mImageView.setImageBitmapResetBaseNoScale(new RotateBitmap(mPhotoDownloadingBitmap), true);
                }
                viewholder.mProgressBar.setVisibility(0);
                if(!mPendingDownloadMap.containsKey(s))
                {
                    mPendingDownloadMap.put(s, null);
                    mAppSession.downloadFullPhoto(mContext, mAlbumId, s, cursor.getString(3));
                }
            }
            catch(OutOfMemoryError outofmemoryerror)
            {
                viewholder.mProgressBar.setVisibility(8);
                viewholder.mImageView.setImageBitmapResetBaseNoScale(new RotateBitmap(mPhotoDownloadingBitmap), true);
                Toaster.toast(mContext, 0x7f0a0130);
                Utils.reportSoftError("photo_gallery_oom_error", (new StringBuilder()).append("filename: ").append(s1).append(", src: ").append(cursor.getString(3)).toString());
            }
            break MISSING_BLOCK_LABEL_305;
        }
        viewholder.mProgressBar.setVisibility(8);
        Bitmap bitmap = ImageUtils.decodeFile(s1, null);
        if(bitmap != null)
            viewholder.mImageView.setImageRotateBitmapResetBase(new RotateBitmap(bitmap, 0), true);
    }

    public Object getItem(int i)
    {
        Cursor cursor = getCursor();
        cursor.moveToPosition(i);
        return cursor.getString(1);
    }

    public View newView(Context context, Cursor cursor, ViewGroup viewgroup)
    {
        View view = mInflater.inflate(0x7f030052, null);
        ViewHolder viewholder = new ViewHolder();
        viewholder.mImageView = (ImageViewTouchBase)view.findViewById(0x7f0e002d);
        viewholder.mProgressBar = (ProgressBar)view.findViewById(0x7f0e00e3);
        viewholder.mCaption = (TextView)view.findViewById(0x7f0e00e4);
        view.setTag(viewholder);
        mViewHolders.add(viewholder);
        return view;
    }

    public void requery()
    {
        Cursor cursor = getCursor();
        if(cursor != null)
            cursor.requery();
    }

    protected static final String PHOTO_GALLERY_OOM_ERROR = "photo_gallery_oom_error";
    private String mAlbumId;
    private AppSession mAppSession;
    private Context mContext;
    private LayoutInflater mInflater;
    private Map mPendingDownloadMap;
    private Bitmap mPhotoDownloadingBitmap;
    private ArrayList mViewHolders;
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileInfoAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.method.LinkMovementMethod;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.util.ImageUtils;
import java.util.ArrayList;
import java.util.List;

public class ProfileInfoAdapter extends BaseAdapter
{
    protected static class Item
    {

        public String getSubTitle()
        {
            return mSubTitle;
        }

        public long getTargetId()
        {
            return mTargetId;
        }

        public String getTitle()
        {
            return mTitle;
        }

        public int getType()
        {
            return mType;
        }

        public String getUrl()
        {
            return mUrl;
        }

        public static final int TYPE_EMAIL = 3;
        public static final int TYPE_INFO = 1;
        public static final int TYPE_PHONE = 2;
        public static final int TYPE_PROFILE_PHOTO = 0;
        public static final int TYPE_SIGNIFICANT_OTHER = 4;
        public static final int TYPE_URL = 5;
        final String mSubTitle;
        final long mTargetId;
        final String mTitle;
        final int mType;
        final String mUrl;

        public Item(int i)
        {
            mType = i;
            mTitle = null;
            mSubTitle = null;
            mUrl = null;
            mTargetId = -1L;
        }

        public Item(int i, String s, String s1)
        {
            mType = i;
            mTitle = s;
            mSubTitle = s1;
            mUrl = null;
            mTargetId = -1L;
        }

        public Item(int i, String s, String s1, String s2)
        {
            mType = i;
            mTitle = s;
            mSubTitle = s1;
            mUrl = s2;
            mTargetId = -1L;
        }

        public Item(int i, String s, String s1, String s2, long l)
        {
            mType = i;
            mTitle = s;
            mSubTitle = s1;
            mUrl = s2;
            mTargetId = l;
        }
    }


    public ProfileInfoAdapter(Context context, StreamPhotosCache streamphotoscache, boolean flag)
    {
        mContext = context;
        mPhotosContainer = streamphotoscache;
        mShowProfilePhoto = flag;
    }

    private Bitmap getNoAvatarImage()
    {
        if(mNoAvatarBitmap == null)
            mNoAvatarBitmap = BitmapFactory.decodeResource(mContext.getResources(), 0x7f0200f3);
        return mNoAvatarBitmap;
    }

    public int getCount()
    {
        return mItems.size();
    }

    public Object getItem(int i)
    {
        return Integer.valueOf(i);
    }

    public Item getItemByPosition(int i)
    {
        return (Item)mItems.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        Item item = (Item)mItems.get(i);
        if(view != null) goto _L2; else goto _L1
_L1:
        LayoutInflater layoutinflater = (LayoutInflater)mContext.getSystemService("layout_inflater");
        item.mType;
        JVM INSTR tableswitch 0 4: default 72
    //                   0 127
    //                   1 72
    //                   2 140
    //                   3 140
    //                   4 140;
           goto _L3 _L4 _L3 _L5 _L5 _L5
_L3:
        View view1 = layoutinflater.inflate(0x7f030038, null);
_L11:
        item.mType;
        JVM INSTR tableswitch 0 5: default 124
    //                   0 159
    //                   1 512
    //                   2 312
    //                   3 312
    //                   4 399
    //                   5 512;
           goto _L6 _L7 _L8 _L9 _L9 _L10 _L8
_L6:
        return view1;
_L4:
        view1 = layoutinflater.inflate(0x7f030064, null);
          goto _L11
_L5:
        view1 = layoutinflater.inflate(0x7f030037, null);
          goto _L11
_L2:
        view1 = view;
          goto _L11
_L7:
        LinearLayout linearlayout1 = (LinearLayout)view1.findViewById(0x7f0e0105);
        TextView textview3;
        if(item.getUrl() != null)
        {
            Bitmap bitmap1 = mPhotosContainer.get(mContext, item.getUrl());
            if(bitmap1 != null)
                ImageUtils.formatPhotoStreamImageView(mContext, linearlayout1, bitmap1);
            else
                ImageUtils.formatPhotoStreamImageView(mContext, linearlayout1, getNoAvatarImage());
        } else
        {
            ImageUtils.formatPhotoStreamImageView(mContext, linearlayout1, getNoAvatarImage());
        }
        ((TextView)view1.findViewById(0x7f0e0100)).setText(item.getTitle());
        textview3 = (TextView)view1.findViewById(0x7f0e0106);
        if(item.getSubTitle() != null)
        {
            textview3.setVisibility(0);
            textview3.setText(item.getSubTitle());
        } else
        {
            textview3.setVisibility(8);
        }
          goto _L6
_L9:
        TextView textview2 = (TextView)view1.findViewById(0x7f0e00a6);
        ImageView imageview = (ImageView)view1.findViewById(0x7f0e00a8);
        imageview.setVisibility(0);
        ((TextView)view1.findViewById(0x7f0e00a5)).setText(item.getTitle());
        textview2.setText(item.getSubTitle());
        if(item.mType == 3)
            imageview.setImageResource(0x7f020126);
        else
            imageview.setImageResource(0x7f020125);
          goto _L6
_L10:
        TextView textview1 = (TextView)view1.findViewById(0x7f0e00a6);
        ((TextView)view1.findViewById(0x7f0e00a5)).setText(item.getTitle());
        textview1.setText(item.getSubTitle());
        if(item.getUrl() != null)
        {
            Bitmap bitmap = mPhotosContainer.get(mContext, item.getUrl());
            LinearLayout linearlayout = (LinearLayout)view1.findViewById(0x7f0e00a7);
            if(bitmap != null)
                ImageUtils.formatPhotoStreamImageView(mContext, linearlayout, bitmap);
            else
                ImageUtils.formatPhotoStreamImageView(mContext, linearlayout, getNoAvatarImage());
        }
          goto _L6
_L8:
        ((TextView)view1.findViewById(0x7f0e00a5)).setText(item.getTitle());
        TextView textview = (TextView)view1.findViewById(0x7f0e00a6);
        textview.setText(item.getSubTitle());
        textview.setAutoLinkMask(1);
        textview.setMovementMethod(LinkMovementMethod.getInstance());
          goto _L6
    }

    public void updatePhoto()
    {
        notifyDataSetChanged();
    }

    protected final Context mContext;
    protected final List mItems = new ArrayList();
    private Bitmap mNoAvatarBitmap;
    protected final StreamPhotosCache mPhotosContainer;
    protected final boolean mShowProfilePhoto;
}

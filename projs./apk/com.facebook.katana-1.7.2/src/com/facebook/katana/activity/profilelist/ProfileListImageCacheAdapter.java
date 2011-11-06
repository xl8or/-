// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileListImageCacheAdapter.java

package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.database.Cursor;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListCursorAdapter

public abstract class ProfileListImageCacheAdapter extends ProfileListCursorAdapter
{

    public ProfileListImageCacheAdapter(Context context, ProfileImagesCache profileimagescache, Cursor cursor)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mUserImagesCache = profileimagescache;
        mViewHolders = new ArrayList();
        refreshData(cursor);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        FacebookProfile facebookprofile = (FacebookProfile)getChild(i, j);
        View view1 = view;
        ViewHolder viewholder;
        String s;
        if(view1 == null)
        {
            view1 = inflateChildView(facebookprofile);
            viewholder = new ViewHolder(view1, 0x7f0e0033);
            mViewHolders.add(viewholder);
            view1.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view1.getTag();
        }
        viewholder.setItemId(Long.valueOf(facebookprofile.mId));
        s = facebookprofile.mImageUrl;
        if(s != null && s.length() != 0)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, facebookprofile.mId, s);
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        ((TextView)view1.findViewById(0x7f0e0100)).setText(facebookprofile.mDisplayName);
        return view1;
    }

    protected abstract Object getItemType(Cursor cursor);

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        if(view1 == null)
            view1 = mInflater.inflate(0x7f03002e, null);
        ((TextView)view1).setText(((ProfileListCursorAdapter.Section)mSections.get(i)).getTitle());
        return view1;
    }

    protected abstract String getTitleForType(Object obj);

    protected View inflateChildView(FacebookProfile facebookprofile)
    {
        View view = mInflater.inflate(0x7f030068, null);
        ((ViewStub)view.findViewById(0x7f0e0108)).inflate();
        return view;
    }

    public void refreshData(Cursor cursor)
    {
        mCursor = cursor;
        mSections = new ArrayList();
        if(cursor != null)
        {
            int i = -1;
            int j = 0;
            int k = cursor.getCount();
            int l = 0;
            cursor.moveToFirst();
            Object obj = null;
            while(l < k) 
            {
                Object obj1 = getItemType(cursor);
                if(!obj1.equals(obj))
                {
                    if(j > 0)
                        mSections.add(new ProfileListCursorAdapter.Section(getTitleForType(obj), i, j));
                    i = l;
                    j = 0;
                    obj = obj1;
                }
                l++;
                j++;
                cursor.moveToNext();
            }
            if(j > 0)
                mSections.add(new ProfileListCursorAdapter.Section(getTitleForType(obj), i, j));
            notifyDataSetChanged();
        }
    }

    protected final Context mContext;
    protected final LayoutInflater mInflater;
    protected final ProfileImagesCache mUserImagesCache;
}

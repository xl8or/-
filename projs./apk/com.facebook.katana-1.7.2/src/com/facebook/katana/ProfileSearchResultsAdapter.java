// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileSearchResultsAdapter.java

package com.facebook.katana;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.activity.profilelist.ProfileListCursorAdapter;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.*;

// Referenced classes of package com.facebook.katana:
//            ViewHolder

public abstract class ProfileSearchResultsAdapter extends ProfileListCursorAdapter
{

    public ProfileSearchResultsAdapter(Context context, Cursor cursor, StreamPhotosCache streamphotoscache)
    {
        mContext = context;
        mPhotosCache = streamphotoscache;
        mInflater = LayoutInflater.from(context);
        mSections = new ArrayList();
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
        s = facebookprofile.mImageUrl;
        viewholder.setItemId(s);
        if(s != null && s.length() != 0)
        {
            Bitmap bitmap = mPhotosCache.get(mContext, s, 2);
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

    public int getCount()
    {
        int i;
        if(getCursor() == null)
            i = 0;
        else
            i = getCursor().getCount();
        return i;
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        if(((com.facebook.katana.activity.profilelist.ProfileListCursorAdapter.Section)mSections.get(i)).getTitle() == null)
        {
            view1 = new View(mContext);
        } else
        {
            View view2 = view;
            if(view2 == null)
                view2 = mInflater.inflate(0x7f03002e, null);
            ((TextView)view2).setText(((com.facebook.katana.activity.profilelist.ProfileListCursorAdapter.Section)mSections.get(i)).getTitle());
            view1 = view2;
        }
        return view1;
    }

    protected View inflateChildView(FacebookProfile facebookprofile)
    {
        View view = mInflater.inflate(0x7f030068, null);
        ((ViewStub)view.findViewById(0x7f0e0108)).inflate();
        return view;
    }

    public abstract void refreshData(Cursor cursor);

    public void updatePhoto(Bitmap bitmap, String s)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            String s1 = (String)viewholder.getItemId();
            if(s1 != null && s1.equals(s))
                viewholder.mImageView.setImageBitmap(bitmap);
        } while(true);
    }

    protected final Context mContext;
    protected final LayoutInflater mInflater;
    protected final StreamPhotosCache mPhotosCache;
    private final List mViewHolders = new ArrayList();
}

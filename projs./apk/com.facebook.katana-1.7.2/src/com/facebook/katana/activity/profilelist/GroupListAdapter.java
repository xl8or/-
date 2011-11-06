// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroupListActivity.java

package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.util.StringUtils;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity.profilelist:
//            ProfileListDynamicAdapter

class GroupListAdapter extends ProfileListDynamicAdapter
{

    public GroupListAdapter(Context context, ProfileImagesCache profileimagescache)
    {
        super(context, profileimagescache);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        FacebookGroup facebookgroup = (FacebookGroup)getChild(i, j);
        View view1 = view;
        ViewHolder viewholder;
        String s;
        TextView textview1;
        int k;
        if(view1 == null)
        {
            view1 = mInflater.inflate(0x7f03002f, null);
            viewholder = new ViewHolder(view1, 0x7f0e0033);
            mViewHolders.add(viewholder);
            view1.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view1.getTag();
        }
        viewholder.setItemId(Long.valueOf(facebookgroup.mId));
        s = facebookgroup.mImageUrl;
        if(s != null && s.length() != 0)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, facebookgroup.mId, s);
            TextView textview;
            Context context;
            Object aobj[];
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f02007f);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f02007f);
        }
        ((TextView)view1.findViewById(0x7f0e008e)).setText(facebookgroup.mDisplayName);
        textview = (TextView)view1.findViewById(0x7f0e008f);
        context = mContext;
        aobj = new Object[1];
        aobj[0] = StringUtils.getTimeAsString(mContext, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * facebookgroup.mUpdateTime);
        textview.setText(context.getString(0x7f0a008b, aobj));
        textview1 = (TextView)view1.findViewById(0x7f0e0090);
        k = facebookgroup.getUnreadCount();
        if(k == 0)
        {
            textview1.setVisibility(8);
        } else
        {
            textview1.setText(Integer.toString(k));
            textview1.setVisibility(0);
        }
        return view1;
    }

    public void updateProfileList(List list)
    {
        mProfiles = list;
        notifyDataSetChanged();
    }
}

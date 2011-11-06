// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesInfoAdapter.java

package com.facebook.katana.activity.places;

import android.content.Context;
import android.content.res.Resources;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.model.FacebookPlace;
import com.facebook.katana.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class PlacesInfoAdapter extends BaseAdapter
{
    protected static class Item
    {

        public String getSubTitle()
        {
            return mSubTitle;
        }

        public String getTitle()
        {
            return mTitle;
        }

        public int getType()
        {
            return mType;
        }

        public static final int TYPE_INFO = 0;
        public static final int TYPE_LOCATION = 1;
        private final String mSubTitle;
        private final String mTitle;
        private final int mType;


        public Item(int i, String s, String s1)
        {
            mType = i;
            mTitle = s;
            mSubTitle = s1;
        }
    }


    public PlacesInfoAdapter(Context context)
    {
        mContext = context;
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

    public int getItemViewType(int i)
    {
        ((Item)mItems.get(i)).mType;
        JVM INSTR tableswitch 0 1: default 40
    //                   0 45
    //                   1 50;
           goto _L1 _L2 _L3
_L1:
        byte byte0 = -1;
_L5:
        return byte0;
_L2:
        byte0 = 0;
        continue; /* Loop/switch isn't completed */
_L3:
        byte0 = 1;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        View view1;
        Item item;
        view1 = null;
        item = (Item)mItems.get(i);
        if(view != null) goto _L2; else goto _L1
_L1:
        LayoutInflater layoutinflater = (LayoutInflater)mContext.getSystemService("layout_inflater");
        item.mType;
        JVM INSTR tableswitch 0 1: default 64
    //                   0 95
    //                   1 108;
           goto _L3 _L4 _L5
_L3:
        item.mType;
        JVM INSTR tableswitch 0 1: default 92
    //                   0 127
    //                   1 224;
           goto _L6 _L7 _L8
_L6:
        return view1;
_L4:
        view1 = layoutinflater.inflate(0x7f030038, null);
          goto _L3
_L5:
        view1 = layoutinflater.inflate(0x7f030038, null);
          goto _L3
_L2:
        view1 = view;
          goto _L3
_L7:
        if(!StringUtils.isBlank(item.getTitle()))
            ((TextView)view1.findViewById(0x7f0e00a5)).setText(item.getTitle());
        else
            ((TextView)view1.findViewById(0x7f0e00a5)).setVisibility(8);
        if(!StringUtils.isBlank(item.getSubTitle()))
            ((TextView)view1.findViewById(0x7f0e00a6)).setText(item.getSubTitle());
        else
            ((TextView)view1.findViewById(0x7f0e00a6)).setVisibility(8);
          goto _L6
_L8:
        if(!StringUtils.isBlank(item.getTitle()))
            ((TextView)view1.findViewById(0x7f0e00a5)).setText(item.getTitle());
        else
            ((TextView)view1.findViewById(0x7f0e00a5)).setVisibility(8);
        if(!StringUtils.isBlank(item.getSubTitle()))
            ((TextView)view1.findViewById(0x7f0e00a6)).setText(item.getSubTitle());
        else
            ((TextView)view1.findViewById(0x7f0e00a6)).setVisibility(8);
          goto _L6
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public void setPlaceInfo(FacebookPlace facebookplace)
    {
        mItems.clear();
        if(!StringUtils.isBlank(facebookplace.mDescription))
            mItems.add(new Item(0, mContext.getString(0x7f0a0153), facebookplace.mDescription));
        FacebookPage facebookpage = facebookplace.getPageInfo();
        if(facebookpage != null && facebookpage.mFanCount != -1 && facebookpage.mFanCount != 0)
        {
            List list1 = mItems;
            Resources resources1 = mContext.getResources();
            int j = facebookpage.mFanCount;
            Object aobj1[] = new Object[1];
            aobj1[0] = Integer.valueOf(facebookpage.mFanCount);
            list1.add(new Item(0, resources1.getQuantityString(0x7f0b0001, j, aobj1), null));
        }
        if(facebookplace.mCheckinCount != 0)
        {
            List list = mItems;
            Resources resources = mContext.getResources();
            int i = facebookplace.mCheckinCount;
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(facebookplace.mCheckinCount);
            list.add(new Item(0, resources.getQuantityString(0x7f0b0002, i, aobj), null));
        }
        if(facebookpage != null && facebookpage.mLocation != null)
        {
            String s = StringUtils.formatLocation(facebookpage.mLocation);
            if(!StringUtils.isBlank(s))
                mItems.add(new Item(1, mContext.getString(0x7f0a0154), s));
        }
        notifyDataSetChanged();
    }

    private final Context mContext;
    private final List mItems = new ArrayList();
}

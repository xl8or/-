// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventGuestsAdapter.java

package com.facebook.katana.activity.events;

import android.content.Context;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.StringUtils;
import java.util.*;

public class EventGuestsAdapter extends BaseAdapter
{
    public class byStatusAndName
        implements Comparator
    {

        public int compare(Item item, Item item1)
        {
            int i = item.getWeight() - item1.getWeight();
            int j;
            if(i != 0)
                j = i;
            else
                j = item.getUser().getDisplayName().compareTo(item1.getUser().getDisplayName());
            return j;
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((Item)obj, (Item)obj1);
        }

        final EventGuestsAdapter this$0;

        public byStatusAndName()
        {
            this$0 = EventGuestsAdapter.this;
            super();
        }
    }

    protected static class Item
    {

        public String getStatus()
        {
            return mStatus;
        }

        public FacebookUser getUser()
        {
            return mUser;
        }

        public int getWeight()
        {
            return mWeight;
        }

        private final String mStatus;
        private final FacebookUser mUser;
        private final int mWeight;

        public Item(String s, FacebookUser facebookuser, int i)
        {
            mStatus = s;
            mUser = facebookuser;
            mWeight = i;
        }
    }


    public EventGuestsAdapter(Context context, ProfileImagesCache profileimagescache)
    {
        mContext = context;
        mUserImagesCache = profileimagescache;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return mItems.size();
    }

    public Object getItem(int i)
    {
        return mItems.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        Item item = (Item)mItems.get(i);
        View view1;
        TextView textview;
        ViewHolder viewholder;
        String s;
        if(view == null)
        {
            view1 = mInflater.inflate(0x7f03001c, null);
            ViewHolder viewholder1 = new ViewHolder(view1, 0x7f0e005c);
            mViewHolders.add(viewholder1);
            view1.setTag(viewholder1);
        } else
        {
            view1 = view;
        }
        textview = (TextView)view1.findViewById(0x7f0e0065);
        textview.setText(item.getStatus());
        if(i == 0 || !item.getStatus().equals(((Item)mItems.get(i - 1)).getStatus()))
            textview.setVisibility(0);
        else
            textview.setVisibility(8);
        viewholder = (ViewHolder)view1.getTag();
        viewholder.setItemId(Long.valueOf(item.getUser().mUserId));
        s = item.getUser().mImageUrl;
        if(s != null)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, item.getUser().mUserId, s);
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        ((TextView)view1.findViewById(0x7f0e005e)).setText(item.getUser().getDisplayName());
        return view1;
    }

    public void setItemsInfo(Map map)
    {
        mItems.clear();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            int i = ((com.facebook.katana.model.FacebookEvent.RsvpStatus)entry.getKey()).status.ordinal();
            String s = StringUtils.rsvpStatusToString(mContext, i);
            Iterator iterator1 = ((List)entry.getValue()).iterator();
            while(iterator1.hasNext()) 
            {
                FacebookUser facebookuser = (FacebookUser)iterator1.next();
                if(facebookuser != null)
                    mItems.add(new Item(s, facebookuser, i));
            }
        }

        Collections.sort(mItems, new byStatusAndName());
        notifyDataSetChanged();
    }

    public void updateUserImage(ProfileImage profileimage)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            Long long1 = (Long)viewholder.getItemId();
            if(long1 != null && long1.equals(Long.valueOf(profileimage.id)))
                viewholder.mImageView.setImageBitmap(profileimage.getBitmap());
        } while(true);
    }

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final List mItems = new ArrayList();
    private final ProfileImagesCache mUserImagesCache;
    private final List mViewHolders = new ArrayList();
}

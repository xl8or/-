// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventBirthdaysAdapter.java

package com.facebook.katana.activity.events;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImage;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.TimeUtils;
import java.util.*;

public class EventBirthdaysAdapter extends BaseAdapter
{
    public class byTime
        implements Comparator
    {

        public int compare(Item item, Item item1)
        {
            return (int)(item.getStartingTime() - item1.getStartingTime());
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((Item)obj, (Item)obj1);
        }

        final EventBirthdaysAdapter this$0;

        public byTime()
        {
            this$0 = EventBirthdaysAdapter.this;
            super();
        }
    }

    protected static class Item
    {

        public String getBar()
        {
            return mBar;
        }

        public int getDay()
        {
            return mDay;
        }

        public String getDisplayName()
        {
            return mDisplayName;
        }

        public String getImageURL()
        {
            return mImageUrl;
        }

        public int getMonth()
        {
            return mMonth;
        }

        public long getStartingTime()
        {
            return mStartingTime;
        }

        public long getUserId()
        {
            return mUserId;
        }

        public int getYear()
        {
            return mYear;
        }

        private final String mBar;
        private final int mDay;
        private final String mDisplayName;
        private final String mImageUrl;
        private final int mMonth;
        private final long mStartingTime;
        private final long mUserId;
        private final int mYear;

        public Item(long l, String s, int i, int j, int k, String s1, 
                long l1, String s2)
        {
            mUserId = l;
            mDisplayName = s;
            mMonth = i;
            mDay = j;
            mYear = k;
            mImageUrl = s1;
            mStartingTime = l1;
            mBar = s2;
        }
    }

    private static interface BirthdaysQuery
    {

        public static final int INDEX_BIRTHDAY_MONTH = 3;
        public static final int INDEX_BIRTHDAY_YEAR = 5;
        public static final int INDEX_NORMALIZED_BIRTHDAY_DAY = 4;
        public static final int INDEX_USER_DISPLAY_NAME = 2;
        public static final int INDEX_USER_ID = 1;
        public static final int INDEX_USER_IMAGE_URL = 6;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[7];
            as[0] = "_id";
            as[1] = "user_id";
            as[2] = "display_name";
            as[3] = "birthday_month";
            as[4] = "normalized_birthday_day";
            as[5] = "birthday_year";
            as[6] = "user_image_url";
        }
    }


    public EventBirthdaysAdapter(Context context, ProfileImagesCache profileimagescache, String s)
    {
        mContext = context;
        mUserImagesCache = profileimagescache;
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList();
        mWhere = s;
        setItemsInfo();
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
        String s;
        String s1;
        ViewHolder viewholder;
        String s2;
        TextView textview1;
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
        s = item.getBar();
        s1 = null;
        if(i > 0)
            s1 = ((Item)mItems.get(i - 1)).getBar();
        if(s1 == null || !s1.equals(s))
        {
            textview.setText(s);
            textview.setVisibility(0);
        } else
        {
            textview.setVisibility(8);
        }
        viewholder = (ViewHolder)view1.getTag();
        viewholder.setItemId(Long.valueOf(item.getUserId()));
        s2 = item.getImageURL();
        if(s2 != null)
        {
            android.graphics.Bitmap bitmap = mUserImagesCache.get(mContext, item.getUserId(), s2);
            int j;
            Context context;
            Object aobj[];
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        ((TextView)view1.findViewById(0x7f0e005e)).setText(item.getDisplayName());
        textview1 = (TextView)view1.findViewById(0x7f0e0066);
        if(item.getYear() != -1)
        {
            j = TimeUtils.getAge(item.getYear(), 1000L * item.getStartingTime());
            context = mContext;
            aobj = new Object[1];
            aobj[0] = Integer.valueOf(j);
            textview1.setText(context.getString(0x7f0a0020, aobj));
        } else
        {
            textview1.setText("");
        }
        return view1;
    }

    public void setItemsInfo()
    {
        mItems.clear();
        Cursor cursor = ((Activity)mContext).managedQuery(ConnectionsProvider.FRIENDS_BIRTHDAY_CONTENT_URI, BirthdaysQuery.PROJECTION, mWhere, null, null);
        cursor.moveToFirst();
        for(int i = cursor.getCount(); i > 0;)
        {
            int j = cursor.getInt(3);
            int k = cursor.getInt(4);
            long l = TimeUtils.timeInSeconds(j - 1, k, false);
            if(TimeUtils.getTimePeriod(1000L * l) == -1)
                l = TimeUtils.timeInSeconds(j - 1, k, true);
            Item item = new Item(cursor.getLong(1), cursor.getString(2), j, k, cursor.getInt(5), cursor.getString(6), l, TimeUtils.getTimeAsStringForTimePeriod(mContext, 100, 1000L * l));
            mItems.add(item);
            i--;
            cursor.moveToNext();
        }

        List list = mItems;
        byTime bytime = new byTime();
        Collections.sort(list, bytime);
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
    private List mItems;
    private final ProfileImagesCache mUserImagesCache;
    private final List mViewHolders = new ArrayList();
    private final String mWhere;
}

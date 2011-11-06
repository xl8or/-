// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventDetailsAdapter.java

package com.facebook.katana.activity.events;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.model.FacebookEvent;
import com.facebook.katana.util.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class EventDetailsAdapter extends BaseAdapter
{
    public static interface EventQuery
    {

        public static final int INDEX_CREATOR_ID = 10;
        public static final int INDEX_END_TIME = 9;
        public static final int INDEX_EVENT_DESCRIPTION = 3;
        public static final int INDEX_EVENT_ID = 7;
        public static final int INDEX_EVENT_NAME = 1;
        public static final int INDEX_HIDE_GUEST_LIST = 12;
        public static final int INDEX_HOST = 4;
        public static final int INDEX_LOCATION = 5;
        public static final int INDEX_MEDIUM_IMAGE_URL = 2;
        public static final int INDEX_RSVP_STATUS = 11;
        public static final int INDEX_START_TIME = 8;
        public static final int INDEX_VENUE = 6;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[13];
            as[0] = "_id";
            as[1] = "event_name";
            as[2] = "medium_image_url";
            as[3] = "description";
            as[4] = "host";
            as[5] = "location";
            as[6] = "venue";
            as[7] = "event_id";
            as[8] = "start_time";
            as[9] = "end_time";
            as[10] = "creator_id";
            as[11] = "rsvp_status";
            as[12] = "hide_guest_list";
        }
    }

    protected static class Item
    {

        public boolean getEnabled()
        {
            return mEnabled;
        }

        public String getLabel()
        {
            return mLabel;
        }

        public String getString()
        {
            return mString;
        }

        public int getType()
        {
            return mType;
        }

        public static final int TYPE_ADDRESS = 2;
        public static final int TYPE_GUESTS = 3;
        public static final int TYPE_HOST = 0;
        public static final int TYPE_LOCATION = 1;
        private final boolean mEnabled;
        private final String mLabel;
        private final String mString;
        private final int mType;

        public Item(int i, String s, String s1, boolean flag)
        {
            mType = i;
            mLabel = s;
            mString = s1;
            mEnabled = flag;
        }
    }


    public EventDetailsAdapter(Context context, StreamPhotosCache streamphotoscache, Cursor cursor, long l, long l1)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPhotosCache = streamphotoscache;
        mCursor = cursor;
        mEventId = l;
        mUserId = l1;
    }

    public int getCount()
    {
        return mItems.size();
    }

    public long getCreatorId()
    {
        return mCreatorId;
    }

    public long getEventId()
    {
        return mEventId;
    }

    public View getFooterView()
    {
        View view = mInflater.inflate(0x7f030016, null);
        TextView textview = (TextView)view.findViewById(0x7f0e005a);
        String s = mCursor.getString(3);
        if(s == null || s.length() == 0)
            view.findViewById(0x7f0e0059).setVisibility(8);
        else
            textview.setText(mCursor.getString(3));
        return view;
    }

    public View getHeaderDivider()
    {
        return mInflater.inflate(0x7f030018, null);
    }

    public View getHeaderView()
    {
        View view = mInflater.inflate(0x7f030017, null);
        ((TextView)view.findViewById(0x7f0e005e)).setText(mCursor.getString(1));
        int i = TimeUtils.getPstOffset();
        long l = mCursor.getLong(8);
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(TimeUtils.getTimeAsStringForTimePeriod(mContext, -1, 1000L * l + (long)i));
        long l1 = mCursor.getLong(9);
        stringbuilder.append("\n");
        stringbuilder.append(TimeUtils.getTimeAsStringForTimePeriod(mContext, -1, 1000L * l1 + (long)i));
        ((TextView)view.findViewById(0x7f0e005f)).setText(stringbuilder.toString());
        mEventImage = (ImageView)view.findViewById(0x7f0e005c);
        String s = mCursor.getString(2);
        mUrl = s;
        if(s != null)
        {
            Bitmap bitmap = mPhotosCache.get(mContext, s, 1);
            if(bitmap != null)
                mEventImage.setImageBitmap(bitmap);
            else
                mEventImage.setImageResource(0x7f0200e2);
        } else
        {
            mEventImage.setImageResource(0x7f0200e2);
        }
        return view;
    }

    public Object getItem(int i)
    {
        return mItems.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public int getRsvpStatus()
    {
        return mRsvpStatus;
    }

    public View getRsvpView()
    {
        View view = mInflater.inflate(0x7f030019, null);
        setRsvpStatus(view.findViewById(0x7f0e0062), mCursor.getInt(11));
        return view;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        Item item;
        View view1;
        int j;
        item = (Item)mItems.get(i);
        view1 = mInflater.inflate(0x7f03001b, null);
        j = 0x7f02007c;
        if(i != 0 || getCount() != 1) goto _L2; else goto _L1
_L1:
        j = 0x7f02007a;
_L4:
        view1.setBackgroundResource(j);
        ((TextView)view1.findViewById(0x7f0e0061)).setText(item.getLabel());
        ((TextView)view1.findViewById(0x7f0e0064)).setText(item.getString());
        return view1;
_L2:
        if(i == 0)
            j = 0x7f02007d;
        else
        if(i == getCount() - 1)
            j = 0x7f02007b;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public boolean isEnabled(int i)
    {
        return ((Item)mItems.get(i)).getEnabled();
    }

    public void setEventInfo()
    {
        mItems.clear();
        mCreatorId = mCursor.getLong(10);
        mItems.add(new Item(0, mContext.getString(0x7f0a005c), mCursor.getString(4), true));
        String s = mCursor.getString(5);
        if(s != null && s.length() != 0)
            mItems.add(new Item(1, mContext.getString(0x7f0a005d), s, false));
        StringBuilder stringbuilder = new StringBuilder();
        try
        {
            Map map = FacebookEvent.deserializeVenue(mCursor.getBlob(6));
            String s1 = ((Serializable)map.get("street")).toString();
            if(s1.length() != 0)
                stringbuilder.append(s1);
            StringBuilder stringbuilder1 = new StringBuilder();
            String s2 = ((Serializable)map.get("city")).toString();
            if(s2.length() != 0)
                stringbuilder1.append(s2);
            String s3 = ((Serializable)map.get("state")).toString();
            if(s3.length() != 0)
            {
                if(s2.length() != 0)
                    stringbuilder1.append(", ");
                stringbuilder1.append(s3);
            }
            if(stringbuilder1.length() != 0)
            {
                stringbuilder.append("\n");
                stringbuilder.append(stringbuilder1);
            }
            String s4 = ((Serializable)map.get("country")).toString();
            if(s4.length() != 0)
            {
                stringbuilder.append("\n");
                stringbuilder.append(s4);
            }
            if(stringbuilder.length() != 0)
                mItems.add(new Item(2, mContext.getString(0x7f0a005a), stringbuilder.toString(), true));
        }
        catch(IOException ioexception)
        {
            Log.e("EventDetails Activity", "Deserialization failed for event.");
        }
        if(mCursor.getInt(12) == 0 || mCursor.getLong(10) == mUserId)
            mItems.add(new Item(3, mContext.getString(0x7f0a005b), mContext.getString(0x7f0a005f), true));
        notifyDataSetChanged();
    }

    public void setRsvpStatus(View view, int i)
    {
        mRsvpStatus = i;
        ((TextView)view).setText(StringUtils.rsvpStatusToString(mContext, i));
    }

    public void updatePhoto(Bitmap bitmap, String s)
    {
        if(mUrl != null && mUrl.equals(s))
            mEventImage.setImageBitmap(bitmap);
    }

    private final Context mContext;
    private long mCreatorId;
    private Cursor mCursor;
    private long mEventId;
    private ImageView mEventImage;
    private final LayoutInflater mInflater;
    private final List mItems = new ArrayList();
    private final StreamPhotosCache mPhotosCache;
    private int mRsvpStatus;
    private String mUrl;
    private long mUserId;
}

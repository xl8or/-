// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventsAdapter.java

package com.facebook.katana.activity.events;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.*;
import android.widget.*;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.provider.EventsProvider;
import com.facebook.katana.util.TimeUtils;
import java.util.*;

public class EventsAdapter extends BaseAdapter
{
    private class BucketizeItemsTask extends AsyncTask
    {

        protected transient Object doInBackground(Object aobj[])
        {
            return setItemsInfo();
        }

        protected void onPostExecute(Object obj)
        {
            mItems = (List)obj;
            notifyDataSetChanged();
            try
            {
                finalize();
            }
            catch(Throwable throwable) { }
            int i = ((access._cls200) (this)).access$200;
            if(mNumberOfTasks > 0)
                setItemsAsync();
        }

        protected void onPreExecute()
        {
        }

        final EventsAdapter this$0;

        private BucketizeItemsTask()
        {
            this$0 = EventsAdapter.this;
            super();
        }

    }

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

        final EventsAdapter this$0;

        public byTime()
        {
            this$0 = EventsAdapter.this;
            super();
        }
    }

    private static interface BirthdaysQuery
    {

        public static final int INDEX_BIRTHDAY_MONTH = 3;
        public static final int INDEX_BIRTHDAY_YEAR = 5;
        public static final int INDEX_NORMALIZED_BIRTHDAY_DAY = 4;
        public static final int INDEX_USER_DISPLAY_NAME = 2;
        public static final int INDEX_USER_ID = 1;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[6];
            as[0] = "_id";
            as[1] = "user_id";
            as[2] = "display_name";
            as[3] = "birthday_month";
            as[4] = "normalized_birthday_day";
            as[5] = "birthday_year";
        }
    }

    private static interface EventsQuery
    {

        public static final int INDEX_EVENT_END_TIME = 3;
        public static final int INDEX_EVENT_ID = 6;
        public static final int INDEX_EVENT_IMAGE_URL = 4;
        public static final int INDEX_EVENT_NAME = 1;
        public static final int INDEX_EVENT_START_TIME = 2;
        public static final int INDEX_RSVP_STATUS = 5;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[7];
            as[0] = "_id";
            as[1] = "event_name";
            as[2] = "start_time";
            as[3] = "end_time";
            as[4] = "image_url";
            as[5] = "rsvp_status";
            as[6] = "event_id";
        }
    }

    protected static class Item
    {

        public void addBirthday(Birthday birthday)
        {
            mBirthdays.add(birthday);
        }

        public String getBar()
        {
            return mBar;
        }

        public int getBirthdaysCount()
        {
            return mBirthdays.size();
        }

        public List getBirthdaysList()
        {
            return mBirthdays;
        }

        public long getEid()
        {
            return mEid;
        }

        public long getEndingTime()
        {
            return mEndingTime;
        }

        public String getEventName()
        {
            return mEventName;
        }

        public String getImageURL()
        {
            return mImageURL;
        }

        public String getRSVP()
        {
            return mRSVP;
        }

        public long getStartingTime()
        {
            return mStartingTime;
        }

        public int getTimePeriod()
        {
            return mTimePeriod;
        }

        public int getType()
        {
            return mType;
        }

        public static final int TYPE_BIRTHDAY = 1;
        public static final int TYPE_EVENT;
        private String mBar;
        private List mBirthdays;
        private final long mEid;
        private final long mEndingTime;
        private final String mEventName;
        private final String mImageURL;
        private String mRSVP;
        private long mStartingTime;
        private int mTimePeriod;
        private final int mType;



        public Item(int i)
        {
            mType = i;
            mEid = 0L;
            mImageURL = null;
            mEventName = null;
            mStartingTime = 0L;
            mEndingTime = 0L;
            mRSVP = null;
            mTimePeriod = -1;
            mBirthdays = new ArrayList();
            mBar = null;
        }

        public Item(int i, int j, long l, Context context)
        {
            this(i);
            mTimePeriod = j;
            mStartingTime = l;
            mBar = TimeUtils.getStringOfTimePeriod(mTimePeriod, context);
        }

        public Item(int i, long l, String s, String s1, long l1, 
                long l2, int j, Context context)
        {
            mType = i;
            mEid = l;
            mImageURL = s;
            mEventName = s1;
            mStartingTime = l1;
            mEndingTime = l2;
            mRSVP = "";
            if(j != com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.ATTENDING.ordinal()) goto _L2; else goto _L1
_L1:
            mRSVP = context.getString(0x7f0a0058);
_L4:
            mTimePeriod = TimeUtils.getTimePeriod(l1 * 1000L, 1000L * l2);
            mBar = TimeUtils.getStringOfTimePeriod(mTimePeriod, context);
            return;
_L2:
            if(j == com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.UNSURE.ordinal())
                mRSVP = context.getString(0x7f0a0063);
            else
            if(j == com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.DECLINED.ordinal())
                mRSVP = context.getString(0x7f0a0059);
            else
            if(j == com.facebook.katana.model.FacebookEvent.RsvpStatusEnum.NOT_REPLIED.ordinal())
                mRSVP = context.getString(0x7f0a0060);
            if(true) goto _L4; else goto _L3
_L3:
        }
    }

    protected static class Birthday
    {

        public int getDay()
        {
            return mDay;
        }

        public String getDisplayName()
        {
            return mDisplayName;
        }

        public int getMonth()
        {
            return mMonth;
        }

        public Long getUserId()
        {
            return mUserId;
        }

        public int getYear()
        {
            return mYear;
        }

        private final int mDay;
        private final String mDisplayName;
        private final int mMonth;
        private final long mTime;
        private final int mTimePeriod;
        private final Long mUserId;
        private final int mYear;



        Birthday(String s, Long long1, int i, int j, int k, long l, 
                int i1)
        {
            mDisplayName = s;
            mUserId = long1;
            mMonth = i;
            mDay = j;
            mYear = k;
            mTime = l;
            mTimePeriod = i1;
        }
    }


    public EventsAdapter(Context context, StreamPhotosCache streamphotoscache)
    {
        mBucketizeTask = null;
        mNumberOfTasks = 0;
        mContext = context;
        mPhotosCache = streamphotoscache;
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList();
    }

    private List setItemsInfo()
    {
        ArrayList arraylist;
        Birthday abirthday[];
        ArrayList arraylist1;
        int l;
        arraylist = new ArrayList();
        Cursor cursor = ((Activity)mContext).managedQuery(EventsProvider.EVENTS_CONTENT_URI, EventsQuery.PROJECTION, "", null, null);
        cursor.moveToFirst();
        for(int i = cursor.getCount(); i > 0;)
        {
            Item item = new Item(0, cursor.getLong(6), cursor.getString(4), cursor.getString(1), cursor.getLong(2) + (long)mTzOffsetInSeconds, cursor.getLong(3) + (long)mTzOffsetInSeconds, cursor.getInt(5), mContext);
            if(item.getTimePeriod() != -1)
                arraylist.add(item);
            i--;
            cursor.moveToNext();
        }

        Cursor cursor1 = ((Activity)mContext).managedQuery(ConnectionsProvider.FRIENDS_BIRTHDAY_CONTENT_URI, BirthdaysQuery.PROJECTION, "", null, null);
        int j = cursor1.getCount();
        abirthday = new Birthday[j];
        cursor1.moveToFirst();
        for(int k = 0; k < j;)
        {
            int i3 = cursor1.getInt(3);
            int j3 = cursor1.getInt(4);
            int k3 = cursor1.getInt(5);
            long l3 = TimeUtils.timeInSeconds(i3 - 1, j3, false);
            int i4 = TimeUtils.getTimePeriod(1000L * l3);
            if(i4 == -1)
            {
                l3 = TimeUtils.timeInSeconds(i3 - 1, j3, true);
                i4 = TimeUtils.getTimePeriod(1000L * l3);
            }
            abirthday[k] = new Birthday(cursor1.getString(2), Long.valueOf(cursor1.getLong(1)), i3, j3, k3, l3, i4);
            k++;
            cursor1.moveToNext();
        }

        arraylist1 = new ArrayList();
        l = 0;
_L6:
        ArrayList arraylist2;
        int j1;
        int k1;
        int i1 = abirthday.length;
        if(l < i1)
        {
            Item item2 = null;
            int k2 = 0;
            do
            {
label0:
                {
                    int l2 = arraylist1.size();
                    if(k2 < l2)
                    {
                        if(((Item)arraylist1.get(k2)).mTimePeriod != abirthday[l].mTimePeriod)
                            break label0;
                        item2 = (Item)arraylist1.get(k2);
                    }
                    if(item2 == null)
                    {
                        item2 = new Item(1, abirthday[l].mTimePeriod, abirthday[l].mTime, mContext);
                        arraylist1.add(item2);
                    }
                    item2.addBirthday(abirthday[l]);
                    l++;
                    continue; /* Loop/switch isn't completed */
                }
                k2++;
            } while(true);
        }
        byTime bytime = new byTime();
        Collections.sort(arraylist1, bytime);
        arraylist2 = new ArrayList();
        j1 = 0;
        k1 = 0;
_L2:
        Item item1;
        int l1 = arraylist.size();
        if(k1 >= l1)
            break MISSING_BLOCK_LABEL_680;
        item1 = (Item)arraylist.get(k1);
        if(item1.getTimePeriod() == -1 || item1.getTimePeriod() == 0)
            break; /* Loop/switch isn't completed */
        int j2 = arraylist1.size();
        if(j1 >= j2 || ((Item)arraylist1.get(j1)).getTimePeriod() == item1.getTimePeriod() || ((Item)arraylist1.get(j1)).getStartingTime() >= item1.getStartingTime())
            break; /* Loop/switch isn't completed */
        arraylist2.add(arraylist1.get(j1));
        j1++;
        k1--;
_L3:
        k1++;
        if(true) goto _L2; else goto _L1
_L1:
        arraylist2.add(item1);
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
        do
        {
            int i2 = arraylist1.size();
            if(j1 >= i2)
                break;
            arraylist2.add(arraylist1.get(j1));
            j1++;
        } while(true);
        return arraylist2;
        if(true) goto _L6; else goto _L5
_L5:
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
        View view1 = view;
        Item item = (Item)mItems.get(i);
        if(view == null)
        {
            view1 = mInflater.inflate(0x7f03001c, null);
            ViewHolder viewholder1 = new ViewHolder(view1, 0x7f0e005c);
            mViewHolders.add(viewholder1);
            view1.setTag(viewholder1);
        }
        TextView textview = (TextView)view1.findViewById(0x7f0e0065);
        if(i == 0 || ((Item)mItems.get(i - 1)).getTimePeriod() != item.getTimePeriod())
        {
            textview.setText(item.getBar());
            textview.setVisibility(0);
        } else
        {
            textview.setVisibility(8);
        }
        if(item.mType == 0)
        {
            String s = item.getImageURL();
            ViewHolder viewholder = (ViewHolder)view1.getTag();
            viewholder.setItemId(s);
            if(s != null && s.length() != 0)
            {
                Bitmap bitmap = mPhotosCache.get(mContext, s, 1);
                String s1;
                if(bitmap != null)
                    viewholder.mImageView.setImageBitmap(bitmap);
                else
                    viewholder.mImageView.setImageResource(0x7f020061);
            } else
            {
                viewholder.mImageView.setImageResource(0x7f020061);
            }
            ((TextView)view1.findViewById(0x7f0e005e)).setText(item.getEventName());
            s1 = TimeUtils.getTimeAsStringForTimePeriod(mContext, item.getTimePeriod(), 1000L * item.getStartingTime());
            ((TextView)view1.findViewById(0x7f0e0066)).setText((new StringBuilder()).append(s1).append(" - ").append(item.getRSVP()).toString());
        } else
        {
            TextView textview1 = (TextView)view1.findViewById(0x7f0e005e);
            Resources resources = mContext.getResources();
            int j = item.getBirthdaysCount();
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(item.getBirthdaysCount());
            textview1.setText(resources.getQuantityString(0x7f0b0000, j, aobj));
            TextView textview2 = (TextView)view1.findViewById(0x7f0e0066);
            StringBuilder stringbuilder = new StringBuilder();
            List list = item.getBirthdaysList();
            int k = 0;
            do
            {
                int l = list.size();
                if(k >= l || k >= 3)
                    break;
                stringbuilder.append(((Birthday)list.get(k)).getDisplayName());
                if(k != 2 && k + 1 != list.size())
                    stringbuilder.append(", ");
                k++;
            } while(true);
            if(list.size() > 3)
            {
                stringbuilder.append(" ");
                stringbuilder.append(mContext.getResources().getString(0x7f0a004c));
            }
            textview2.setText(stringbuilder.toString());
            ((ImageView)view1.findViewById(0x7f0e005c)).setImageResource(0x7f0200e1);
        }
        return view1;
    }

    public void setItemsAsync()
    {
        if(mBucketizeTask == null || mBucketizeTask.getStatus() == android.os.AsyncTask.Status.FINISHED)
        {
            mNumberOfTasks = 1 + mNumberOfTasks;
            mBucketizeTask = (new BucketizeItemsTask()).execute(new Object[0]);
        } else
        {
            mNumberOfTasks = 1 + mNumberOfTasks;
        }
    }

    public void stopBucketizeTask()
    {
        if(mBucketizeTask != null)
            mBucketizeTask.cancel(true);
    }

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

    public AsyncTask mBucketizeTask;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private List mItems;
    private int mNumberOfTasks;
    private final StreamPhotosCache mPhotosCache;
    protected final int mTzOffsetInSeconds = TimeUtils.getPstOffset() / 1000;
    private final List mViewHolders = new ArrayList();



/*
    static List access$102(EventsAdapter eventsadapter, List list)
    {
        eventsadapter.mItems = list;
        return list;
    }

*/



/*
    static int access$210(EventsAdapter eventsadapter)
    {
        int i = eventsadapter.mNumberOfTasks;
        eventsadapter.mNumberOfTasks = i - 1;
        return i;
    }

*/
}

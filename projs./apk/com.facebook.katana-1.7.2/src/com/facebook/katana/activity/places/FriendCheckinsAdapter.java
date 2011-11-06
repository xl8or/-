// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FriendCheckinsAdapter.java

package com.facebook.katana.activity.places;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.StreamPhotosCache;
import com.facebook.katana.features.places.PlacesUtils;
import com.facebook.katana.model.*;
import com.facebook.katana.ui.SectionedListAdapter;
import com.facebook.katana.util.FBLocationManager;
import com.facebook.katana.util.StringUtils;
import java.util.*;

public class FriendCheckinsAdapter extends SectionedListAdapter
    implements com.facebook.katana.util.FBLocationManager.FBLocationListener
{
    private static class Group
    {

        final List mCheckins;
        final String mLabel;

        Group(String s, List list)
        {
            mLabel = s;
            mCheckins = list;
        }
    }

    private class BucketizeItemsTask extends AsyncTask
    {

        protected volatile Object doInBackground(Object aobj[])
        {
            return doInBackground((List[])aobj);
        }

        protected transient List doInBackground(List alist[])
        {
            if(!$assertionsDisabled && alist.length != 1)
                throw new AssertionError();
            List list;
            if(alist[0] == null)
                list = null;
            else
                list = classifyCheckins(alist[0]);
            return list;
        }

        protected volatile void onPostExecute(Object obj)
        {
            onPostExecute((List)obj);
        }

        protected void onPostExecute(List list)
        {
            mGroups = list;
            notifyDataSetChanged();
        }

        static final boolean $assertionsDisabled;
        final FriendCheckinsAdapter this$0;

        static 
        {
            boolean flag;
            if(!com/facebook/katana/activity/places/FriendCheckinsAdapter.desiredAssertionStatus())
                flag = true;
            else
                flag = false;
            $assertionsDisabled = flag;
        }

        private BucketizeItemsTask()
        {
            this$0 = FriendCheckinsAdapter.this;
            super();
        }

    }


    public FriendCheckinsAdapter(Context context, StreamPhotosCache streamphotoscache)
    {
        mBucketizeTask = null;
        mContext = context;
        mPhotosCache = streamphotoscache;
        mInflater = LayoutInflater.from(context);
        resumeAdapter();
    }

    private List classifyCheckins(List list)
    {
        long l = (new GregorianCalendar()).getTimeInMillis() / 1000L;
        ArrayList arraylist = new ArrayList(3);
        AppSession appsession = AppSession.getActiveSession(mContext, false);
        ArrayList arraylist1 = new ArrayList(1);
        ArrayList arraylist2 = new ArrayList();
        ArrayList arraylist3 = new ArrayList();
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            FacebookCheckin facebookcheckin = (FacebookCheckin)iterator.next();
            FacebookCheckinDetails facebookcheckindetails = facebookcheckin.getDetails();
            FacebookPlace facebookplace = facebookcheckindetails.getPlaceInfo();
            float af[] = new float[1];
            if(appsession != null && facebookcheckin.mActorId == appsession.getSessionInfo().userId)
            {
                arraylist1.add(facebookcheckin);
                PlacesUtils.setLastCheckin(mContext, facebookcheckin, 1000L * facebookcheckindetails.mTimestamp);
            } else
            if(mLastKnownLocation != null)
            {
                Location.distanceBetween(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), facebookplace.mLatitude, facebookplace.mLongitude, af);
                if(af[0] <= 3000F && l - facebookcheckindetails.mTimestamp <= 10800L)
                    arraylist2.add(facebookcheckin);
                else
                    arraylist3.add(facebookcheckin);
            } else
            {
                arraylist3.add(facebookcheckin);
            }
        }

        if(arraylist1.size() > 0)
        {
            Group group = new Group(mContext.getString(0x7f0a013b), arraylist1);
            arraylist.add(group);
        }
        if(arraylist2.size() == 0)
        {
            if(arraylist3.size() > 0)
                arraylist.add(new Group(mContext.getString(0x7f0a0139), arraylist3));
        } else
        {
            Group group1 = new Group(mContext.getString(0x7f0a013a), arraylist2);
            arraylist.add(group1);
            arraylist.add(new Group(mContext.getString(0x7f0a0138), arraylist3));
        }
        return arraylist;
    }

    public Object getChild(int i, int j)
    {
        return ((Group)mGroups.get(i)).mCheckins.get(j);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        FacebookCheckin facebookcheckin = (FacebookCheckin)getChild(i, j);
        if(view == null || !mChildViews.contains(view))
        {
            view1 = mInflater.inflate(0x7f030010, null);
            ViewHolder viewholder = new ViewHolder(view1, 0x7f0e0033);
            mViewHolders.add(viewholder);
            view1.setTag(viewholder);
        }
        FacebookUser facebookuser = facebookcheckin.getActor();
        FacebookCheckinDetails facebookcheckindetails = facebookcheckin.getDetails();
        ViewHolder viewholder1 = (ViewHolder)view1.getTag();
        String s = facebookuser.mImageUrl;
        viewholder1.setItemId(s);
        String s1;
        if(s != null && s.length() != 0)
        {
            Bitmap bitmap = mPhotosCache.get(mContext, s, 1);
            Context context1;
            Object aobj1[];
            if(bitmap != null)
                viewholder1.mImageView.setImageBitmap(bitmap);
            else
                viewholder1.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder1.mImageView.setImageResource(0x7f0200f3);
        }
        ((TextView)view1.findViewById(0x7f0e001c)).setText(facebookuser.getDisplayName());
        ((TextView)view1.findViewById(0x7f0e0034)).setText(facebookcheckindetails.getPlaceInfo().mName);
        if(facebookcheckindetails.getAppInfo() != null)
        {
            context1 = mContext;
            aobj1 = new Object[2];
            aobj1[0] = StringUtils.getTimeAsString(mContext, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * facebookcheckindetails.mTimestamp);
            aobj1[1] = facebookcheckindetails.getAppInfo().mName;
            s1 = context1.getString(0x7f0a0136, aobj1);
        } else
        {
            Context context = mContext;
            Object aobj[] = new Object[1];
            aobj[0] = StringUtils.getTimeAsString(mContext, com.facebook.katana.util.StringUtils.TimeFormatStyle.STREAM_RELATIVE_STYLE, 1000L * facebookcheckindetails.mTimestamp);
            s1 = context.getString(0x7f0a0137, aobj);
        }
        ((TextView)view1.findViewById(0x7f0e0035)).setText(s1);
        mChildViews.add(view1);
        return view1;
    }

    public int getChildViewType(int i, int j)
    {
        return 1;
    }

    public int getChildrenCount(int i)
    {
        List list = ((Group)mGroups.get(i)).mCheckins;
        int j;
        if(list == null)
            j = 0;
        else
            j = list.size();
        return j;
    }

    public Object getSection(int i)
    {
        return mGroups.get(i);
    }

    public int getSectionCount()
    {
        int i;
        if(mGroups == null)
            i = 0;
        else
            i = mGroups.size();
        return i;
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        Group group = (Group)getSection(i);
        if(view == null || !mGroupViews.contains(view))
            view1 = mInflater.inflate(0x7f03002e, null);
        ((TextView)view1).setText(group.mLabel);
        mGroupViews.add(view1);
        return view1;
    }

    public int getSectionHeaderViewType(int i)
    {
        return 0;
    }

    public int getViewTypeCount()
    {
        return 2;
    }

    public boolean isEmpty()
    {
        boolean flag;
        if(mGroups == null)
            flag = true;
        else
        if(mGroups.size() == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEnabled(int i, int j)
    {
        return true;
    }

    public void onLocationChanged(Location location)
    {
        mLastKnownLocation = location;
        update(mCheckins);
    }

    public void onTimeOut()
    {
    }

    void resumeAdapter()
    {
        FBLocationManager.addLocationListener(mContext, this);
    }

    void suspendAdapter()
    {
        if(mBucketizeTask != null)
            mBucketizeTask.cancel(true);
        FBLocationManager.removeLocationListener(this);
    }

    void update(List list)
    {
        if(mBucketizeTask != null && mBucketizeTask.getStatus() == android.os.AsyncTask.Status.RUNNING)
        {
            mBucketizeTask.cancel(true);
            mBucketizeTask = null;
        }
        mBucketizeTask = new BucketizeItemsTask();
        AsyncTask asynctask = mBucketizeTask;
        List alist[] = new List[1];
        alist[0] = list;
        asynctask.execute(alist);
        mCheckins = list;
    }

    void updatePhoto(Bitmap bitmap, String s)
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
    private List mCheckins;
    private final Set mChildViews = new HashSet();
    private final Context mContext;
    private final Set mGroupViews = new HashSet();
    private List mGroups;
    private final LayoutInflater mInflater;
    protected Location mLastKnownLocation;
    private final StreamPhotosCache mPhotosCache;
    private final List mViewHolders = new ArrayList();



/*
    static List access$102(FriendCheckinsAdapter friendcheckinsadapter, List list)
    {
        friendcheckinsadapter.mGroups = list;
        return list;
    }

*/
}

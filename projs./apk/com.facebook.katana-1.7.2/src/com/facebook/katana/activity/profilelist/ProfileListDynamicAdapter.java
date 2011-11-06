// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileListDynamicAdapter.java

package com.facebook.katana.activity.profilelist;

import android.content.Context;
import android.os.AsyncTask;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import java.util.*;

public class ProfileListDynamicAdapter extends ProfileListActivity.ProfileListAdapter
{
    private class SortProfilesTask extends AsyncTask
    {

        protected volatile Object doInBackground(Object aobj[])
        {
            return doInBackground((List[])aobj);
        }

        protected transient List doInBackground(List alist[])
        {
            if(!$assertionsDisabled && alist.length != 1)
            {
                throw new AssertionError();
            } else
            {
                Comparator comparator = new Comparator() {

                    public int compare(FacebookProfile facebookprofile, FacebookProfile facebookprofile1)
                    {
                        return facebookprofile.mDisplayName.compareTo(facebookprofile1.mDisplayName);
                    }

                    public volatile int compare(Object obj, Object obj1)
                    {
                        return compare((FacebookProfile)obj, (FacebookProfile)obj1);
                    }

                    final SortProfilesTask this$1;

                
                {
                    this$1 = SortProfilesTask.this;
                    super();
                }
                }
;
                ArrayList arraylist = new ArrayList(alist[0]);
                Collections.sort(arraylist, comparator);
                return arraylist;
            }
        }

        protected volatile void onPostExecute(Object obj)
        {
            onPostExecute((List)obj);
        }

        protected void onPostExecute(List list)
        {
            mProfiles = list;
            notifyDataSetChanged();
        }

        static final boolean $assertionsDisabled;
        final ProfileListDynamicAdapter this$0;

        static 
        {
            boolean flag;
            if(!com/facebook/katana/activity/profilelist/ProfileListDynamicAdapter.desiredAssertionStatus())
                flag = true;
            else
                flag = false;
            $assertionsDisabled = flag;
        }

        private SortProfilesTask()
        {
            this$0 = ProfileListDynamicAdapter.this;
            super();
        }

    }


    public ProfileListDynamicAdapter(Context context, ProfileImagesCache profileimagescache)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mUserImagesCache = profileimagescache;
        mViewHolders = new ArrayList();
    }

    public Object getChild(int i, int j)
    {
        if(!$assertionsDisabled && i != 0)
            throw new AssertionError();
        else
            return mProfiles.get(j);
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        FacebookProfile facebookprofile = (FacebookProfile)getChild(i, j);
        View view1 = view;
        ViewHolder viewholder;
        String s;
        if(view1 == null)
        {
            view1 = mInflater.inflate(0x7f030063, null);
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

    public int getChildViewType(int i, int j)
    {
        return 1;
    }

    public int getChildrenCount(int i)
    {
        if(!$assertionsDisabled && i != 0)
            throw new AssertionError();
        else
            return mProfiles.size();
    }

    public Object getSection(int i)
    {
        return null;
    }

    public int getSectionCount()
    {
        return 1;
    }

    public View getSectionHeaderView(int i, View view, ViewGroup viewgroup)
    {
        View view1 = view;
        if(view1 == null)
            view1 = new View(mContext);
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
        if(mProfiles == null || mProfiles.size() == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean isEnabled(int i, int j)
    {
        return true;
    }

    public void updateProfileList(List list)
    {
        mSortTask = new SortProfilesTask();
        AsyncTask asynctask = mSortTask;
        List alist[] = new List[1];
        alist[0] = list;
        asynctask.execute(alist);
    }

    static final boolean $assertionsDisabled;
    protected final Context mContext;
    protected final LayoutInflater mInflater;
    protected List mProfiles;
    protected AsyncTask mSortTask;
    protected final ProfileImagesCache mUserImagesCache;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/activity/profilelist/ProfileListDynamicAdapter.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileListActivity.java

package com.facebook.katana.activity.profilelist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.ViewHolder;
import com.facebook.katana.activity.ProfileFacebookListActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.ui.SectionedListAdapter;
import com.facebook.katana.util.ApplicationUtils;
import java.util.Iterator;
import java.util.List;

public abstract class ProfileListActivity extends ProfileFacebookListActivity
{
    public class ProfileListListener extends AppSessionListener
    {

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mAdapter.updateUserImage(profileimage);
        }

        final ProfileListActivity this$0;

        public ProfileListListener()
        {
            this$0 = ProfileListActivity.this;
            super();
        }
    }

    public static abstract class ProfileListAdapter extends SectionedListAdapter
    {

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

        protected List mViewHolders;

        public ProfileListAdapter()
        {
        }
    }


    public ProfileListActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    public void onListItemClick(ListView listview, View view, int i, long l)
    {
        FacebookProfile facebookprofile = (FacebookProfile)mAdapter.getItem(i);
        ApplicationUtils.OpenUserProfile(this, facebookprofile.mId, facebookprofile);
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
        {
            if(!$assertionsDisabled && mAppSessionListener == null)
                throw new AssertionError();
            mAppSession.removeListener(mAppSessionListener);
        }
    }

    public void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(!$assertionsDisabled && mAppSessionListener == null)
                throw new AssertionError();
            mAppSession.addListener(mAppSessionListener);
        }
    }

    static final boolean $assertionsDisabled;
    protected ProfileListAdapter mAdapter;
    protected AppSession mAppSession;
    protected ProfileListListener mAppSessionListener;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/activity/profilelist/ProfileListActivity.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}

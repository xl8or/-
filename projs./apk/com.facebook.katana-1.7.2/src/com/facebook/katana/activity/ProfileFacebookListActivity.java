// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProfileFacebookListActivity.java

package com.facebook.katana.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.platform.PlatformFastTrack;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.util.LocationUtils;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.version.SDK5;
import java.util.List;

// Referenced classes of package com.facebook.katana.activity:
//            BaseFacebookListActivity, FatTitleHeader

public class ProfileFacebookListActivity extends BaseFacebookListActivity
    implements FatTitleHeader
{
    protected class FBListActivityAppSessionListener extends AppSessionListener
    {

        public void onDownloadStreamPhotoComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2, Bitmap bitmap)
        {
            if(i == 200 && mProfileType == 2)
                updateFatTitleHeader();
        }

        public void onGetProfileComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookProfile facebookprofile)
        {
            if(i == 200 && facebookprofile != null && facebookprofile.mId == mProfileId)
            {
                mProfile = facebookprofile;
                updateFatTitleHeader();
            }
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i != 200 || profileimage.id != mProfileId) goto _L2; else goto _L1
_L1:
            if(mProfileId != appsession.getSessionInfo().userId) goto _L4; else goto _L3
_L3:
            mProfile = new FacebookProfile(mAppSession.getSessionInfo().getProfile());
_L7:
            updateFatTitleHeader();
_L2:
            return;
_L4:
            if(mProfile == null) goto _L2; else goto _L5
_L5:
            mProfile = new FacebookProfile(mProfileId, mProfile.mDisplayName, profileimage.url, mProfile.mType);
            if(true) goto _L7; else goto _L6
_L6:
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            if(profileimage.id == mProfileId)
                updateFatTitleHeader();
        }

        final ProfileFacebookListActivity this$0;

        protected FBListActivityAppSessionListener()
        {
            this$0 = ProfileFacebookListActivity.this;
            super();
        }
    }


    public ProfileFacebookListActivity()
    {
    }

    private boolean useQuickContacts()
    {
        boolean flag;
        if(mProfileId != mAppSession.getSessionInfo().userId && PlatformUtils.platformStorageSupported(this) && FacebookAuthenticationService.isSyncEnabled(this, mAppSession.getSessionInfo().username))
            flag = true;
        else
            flag = false;
        return flag;
    }

    protected void onCreate(Bundle bundle)
    {
        mAppSession = AppSession.getActiveSession(this, false);
        super.onCreate(bundle);
        if(mHasFatTitleHeader)
        {
            mProfileId = getIntent().getLongExtra("extra_user_id", mAppSession.getSessionInfo().userId);
            mProfileType = 0;
            if(mProfileId != mAppSession.getSessionInfo().userId)
            {
                int i = getIntent().getIntExtra("extra_user_type", -1);
                if(i != -1)
                {
                    mProfileType = i;
                    mProfile = new FacebookProfile(mProfileId, getIntent().getStringExtra("extra_user_display_name"), getIntent().getStringExtra("extra_image_url"), mProfileType);
                } else
                {
                    FacebookUserPersistent facebookuserpersistent = FacebookUserPersistent.readFromContentProvider(this, mProfileId);
                    if(facebookuserpersistent != null)
                        mProfile = new FacebookProfile(facebookuserpersistent);
                }
            } else
            {
                mProfile = new FacebookProfile(mAppSession.getSessionInfo().getProfile());
            }
            if(useQuickContacts())
                mListHeaders.add(Integer.valueOf(0x7f030020));
            else
                mListHeaders.add(Integer.valueOf(0x7f030021));
            mAppSessionListener = new FBListActivityAppSessionListener();
        }
    }

    protected void onPause()
    {
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
        super.onPause();
    }

    protected void onResume()
    {
        if(mHasFatTitleHeader)
        {
            mAppSession = AppSession.getActiveSession(this, false);
            if(mAppSession != null)
                mAppSession.addListener(mAppSessionListener);
            if(mProfile == null || mProfile.mDisplayName == null || mProfile.mDisplayName.length() == 0)
                FqlGetProfile.RequestSingleProfile(this, mProfileId);
            else
                updateFatTitleHeader();
        }
        super.onResume();
    }

    public void setupFatTitleHeader()
    {
        if(mHasFatTitleHeader)
        {
            findViewById(0x7f0e0072).setVisibility(8);
            findViewById(0x7f0e0070).setVisibility(0);
        }
    }

    public void updateFatTitleHeader()
    {
        if(mHasFatTitleHeader && mAppSession != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        TextView textview = (TextView)findViewById(0x7f0e0071);
        ImageView imageview;
        Bitmap bitmap;
        if(mProfile != null && mProfile.mDisplayName != null)
            textview.setText(mProfile.mDisplayName);
        else
            textview.setText(null);
        imageview = (ImageView)findViewById(0x7f0e0070);
        bitmap = null;
        if(mProfileType == 2)
        {
            FacebookPlace facebookplace = (FacebookPlace)getIntent().getParcelableExtra("extra_place");
            String s1 = LocationUtils.generateGoogleMapsURL(facebookplace.mLatitude, facebookplace.mLongitude, 14, 75, 75);
            bitmap = mAppSession.getPhotosCache().get(this, s1);
            imageview.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    String s2;
                    FacebookPlace facebookplace1 = (FacebookPlace)getIntent().getParcelableExtra("extra_place");
                    s2 = LocationUtils.generateGeoIntentURI(facebookplace1.mName, facebookplace1.mLatitude, facebookplace1.mLongitude);
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(s2)));
_L2:
                    return;
                    ActivityNotFoundException activitynotfoundexception;
                    activitynotfoundexception;
                    if(true) goto _L2; else goto _L1
_L1:
                }

                final ProfileFacebookListActivity this$0;

            
            {
                this$0 = ProfileFacebookListActivity.this;
                super();
            }
            }
);
        }
        if(bitmap == null && mProfile != null)
            bitmap = mAppSession.getUserImagesCache().get(this, mProfileId, mProfile.mImageUrl);
        if(bitmap != null)
            imageview.setImageBitmap(bitmap);
        else
        if(mProfileType == 2)
            imageview.setImageDrawable(getResources().getDrawable(0x7f020103));
        else
        if(mProfileType == 3)
            imageview.setImageDrawable(getResources().getDrawable(0x7f02007f));
        else
            imageview.setImageDrawable(getResources().getDrawable(0x7f0200f3));
        if(Integer.parseInt(android.os.Build.VERSION.SDK) >= 5 && SDK5.isQuickContactBadge(imageview) && useQuickContacts())
        {
            String s = mAppSession.getSessionInfo().username;
            long l = mProfileId;
            String as[] = new String[1];
            as[0] = "vnd.android.cursor.item/vnd.facebook.profile";
            PlatformFastTrack.prepareBadge(imageview, s, l, as);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    public static final String EXTRA_PROFILE = "extra_profile";
    public static final String EXTRA_USER_DISPLAY_NAME = "extra_user_display_name";
    public static final String EXTRA_USER_ID = "extra_user_id";
    public static final String EXTRA_USER_TYPE = "extra_user_type";
    public static final int PROFILE_PIC_MAP_ZOOM_LEVEL = 14;
    public static final int PROFILE_PIC_SIZE = 75;
    protected AppSession mAppSession;
    protected AppSessionListener mAppSessionListener;
    protected boolean mHasFatTitleHeader;
    protected FacebookProfile mProfile;
    protected long mProfileId;
    protected int mProfileType;
}

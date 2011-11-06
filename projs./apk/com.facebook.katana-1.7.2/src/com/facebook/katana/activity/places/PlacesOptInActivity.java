// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlacesOptInActivity.java

package com.facebook.katana.activity.places;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.view.FacebookWebViewActivity;

public class PlacesOptInActivity extends BaseFacebookActivity
{
    private class PlacesOptInAppSessionListener extends AppSessionListener
    {

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                updateCheckinStory();
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            updateCheckinStory();
        }

        final PlacesOptInActivity this$0;

        private PlacesOptInAppSessionListener()
        {
            this$0 = PlacesOptInActivity.this;
            super();
        }

    }


    public PlacesOptInActivity()
    {
    }

    private void setupActivity()
    {
        Button button = (Button)findViewById(0x7f0e00f9);
        button.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                setResult(-1);
                finish();
            }

            final PlacesOptInActivity this$0;

            
            {
                this$0 = PlacesOptInActivity.this;
                super();
            }
        }
);
        Button button1 = (Button)findViewById(0x7f0e00fa);
        button1.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                setResult(0);
                finish();
            }

            final PlacesOptInActivity this$0;

            
            {
                this$0 = PlacesOptInActivity.this;
                super();
            }
        }
);
        ((TextView)findViewById(0x7f0e00f8)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(PlacesOptInActivity.this, com/facebook/katana/view/FacebookWebViewActivity);
                intent.putExtra(FacebookWebViewActivity.URL, "http://touch.facebook.com/places/");
                startActivity(intent);
            }

            final PlacesOptInActivity this$0;

            
            {
                this$0 = PlacesOptInActivity.this;
                super();
            }
        }
);
        TextView textview = (TextView)findViewById(0x7f0e00f7);
        if("checkin".equals(mOptinOrigin))
        {
            textview.setText(0x7f0a0241);
        } else
        {
            button.setText(0x7f0a023b);
            button1.setText(0x7f0a025c);
            textview.setText(0x7f0a0261);
        }
    }

    private void updateCheckinStory()
    {
        ImageView imageview = (ImageView)findViewById(0x7f0e00f5);
        android.graphics.Bitmap bitmap = mAppSession.getUserImagesCache().get(this, mUserProfile.mId, mUserProfile.mImageUrl);
        if(bitmap != null)
            imageview.setImageBitmap(bitmap);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03005d);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mAppSessionListener = new PlacesOptInAppSessionListener();
            mPlaceName = getIntent().getStringExtra("place_name");
            mOptinOrigin = getIntent().getStringExtra("optin_origin");
            mUserProfile = (FacebookProfile)getIntent().getParcelableExtra("user_profile");
            TextView textview = (TextView)findViewById(0x7f0e00f6);
            String s1;
            SpannableString spannablestring;
            int j;
            if("checkin".equals(mOptinOrigin))
            {
                Object aobj1[] = new Object[2];
                aobj1[0] = mUserProfile.mDisplayName;
                aobj1[1] = mPlaceName;
                s1 = getString(0x7f0a0275, aobj1);
                spannablestring = new SpannableString(s1);
                int k = s1.indexOf(mUserProfile.mDisplayName);
                spannablestring.setSpan(new ForegroundColorSpan(getResources().getColor(0x7f07000c)), k, k + mUserProfile.mDisplayName.length(), 33);
            } else
            {
                String s = mUserProfile.mDisplayName;
                Object aobj[] = new Object[2];
                aobj[0] = s;
                aobj[1] = mPlaceName;
                s1 = getString(0x7f0a0276, aobj);
                spannablestring = new SpannableString(s1);
                int i = s1.indexOf(s);
                spannablestring.setSpan(new ForegroundColorSpan(getResources().getColor(0x7f07000c)), i, i + s.length(), 33);
            }
            j = s1.indexOf(mPlaceName);
            spannablestring.setSpan(new ForegroundColorSpan(getResources().getColor(0x7f07000c)), j, j + mPlaceName.length(), 33);
            textview.setText(spannablestring, android.widget.TextView.BufferType.SPANNABLE);
            updateCheckinStory();
            setupActivity();
        }
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
            LoginActivity.toLogin(this);
        else
            mAppSession.addListener(mAppSessionListener);
    }

    public static final String OPTIN_ORIGIN = "optin_origin";
    public static final String ORIGIN_CHECKIN = "checkin";
    public static final String ORIGIN_CHECKIN_TAG = "checkin_tag";
    public static final String PLACE_NAME = "place_name";
    public static final String USER_PROFILE = "user_profile";
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mOptinOrigin;
    private String mPlaceName;
    private FacebookProfile mUserProfile;

}

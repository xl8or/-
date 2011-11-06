// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UploadVideoActivity.java

package com.facebook.katana.activity.media;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.service.method.VideoUpload;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Toaster;
import java.io.File;

public class UploadVideoActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener
{
    public static final class Extras
    {

        public static final String EXTRA_PROFILE_ID = "extra_profile_id";

        public Extras()
        {
        }
    }


    public UploadVideoActivity()
    {
        mDeleteFileOnDestroy = false;
    }

    private void runUI()
    {
        String s;
        Uri uri;
        s = getIntent().getAction();
        uri = (Uri)getIntent().getParcelableExtra("android.intent.extra.STREAM");
        if(!s.startsWith("com.facebook.katana.upload.notification.error")) goto _L2; else goto _L1
_L1:
        mAppSession.cancelUploadNotification(this, (new StringBuilder()).append("").append(getIntent().getExtras().getInt("android.intent.extra.SUBJECT")).toString());
_L5:
        mFilename = uri.toString();
        setContentView(0x7f03008a);
        ((Button)findViewById(0x7f0e014f)).setOnClickListener(this);
        ((Button)findViewById(0x7f0e0133)).setOnClickListener(this);
        setupFatTitleBar();
        String s1 = getIntent().getStringExtra("android.intent.extra.TITLE");
        if(s1 != null)
            ((TextView)findViewById(0x7f0e0150)).setText(s1);
        findViewById(0x7f0e00f1).setVisibility(8);
        hideSearchButton();
        mListener = new AppSessionListener() {

            public void onGetProfileComplete(AppSession appsession, String s2, int i, String s3, Exception exception1, FacebookProfile facebookprofile)
            {
                if(i == 200 && facebookprofile != null && facebookprofile.mId == mProfileId)
                {
                    mProfile = facebookprofile;
                    updateFatTitleBar();
                }
            }

            final UploadVideoActivity this$0;

            
            {
                this$0 = UploadVideoActivity.this;
                super();
            }
        }
;
_L3:
        return;
_L2:
        if(!s.startsWith("com.facebook.katana.upload.notification.ok"))
            continue; /* Loop/switch isn't completed */
        mAppSession.cancelUploadNotification(this, (new StringBuilder()).append("").append(getIntent().getExtras().getInt("android.intent.extra.SUBJECT")).toString());
        try
        {
            (new File(uri.getPath())).delete();
        }
        catch(Exception exception)
        {
            Log.e(getTag(), "Cannot delete local video file", exception);
        }
        finish();
          goto _L3
        if(!s.startsWith("com.facebook.katana.upload.notification.pending")) goto _L5; else goto _L4
_L4:
        finish();
          goto _L3
        if(true) goto _L5; else goto _L6
_L6:
    }

    private void setupFatTitleBar()
    {
        if(mProfileId == -1L)
        {
            ImageButton imagebutton = (ImageButton)findViewById(0x7f0e0073);
            imagebutton.setVisibility(0);
            imagebutton.setImageResource(0x7f0200ce);
            imagebutton.setOnClickListener(this);
        }
    }

    private void updateFatTitleBar()
    {
        ((TextView)findViewById(0x7f0e0071)).setText(0x7f0a021d);
        String s;
        if(mProfile == null)
        {
            FqlGetProfile.RequestSingleProfile(this, mProfileId);
            s = getString(0x7f0a0215);
        } else
        {
            Object aobj[] = new Object[1];
            aobj[0] = mProfile.mDisplayName;
            s = getString(0x7f0a0216, aobj);
        }
        ((TextView)findViewById(0x7f0e0072)).setText(s);
    }

    private void upload()
    {
        String s = ((EditText)findViewById(0x7f0e0150)).getText().toString().trim();
        if(s.length() == 0)
            s = null;
        String s1 = ((EditText)findViewById(0x7f0e0151)).getText().toString().trim();
        if(s1.length() == 0)
            s1 = null;
        VideoUpload.RequestVideoUpload(this, s, s1, mFilename, mProfileId);
        mDeleteFileOnDestroy = false;
        Toaster.toast(this, 0x7f0a0219);
        finish();
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if(j != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        switch(i)
        {
        case 2210: 
            runUI();
            break;
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR lookupswitch 2: default 32
    //                   2131624243: 69
    //                   2131624271: 33;
           goto _L1 _L2 _L3
_L1:
        return;
_L3:
        TextView textview = (TextView)findViewById(0x7f0e0150);
        ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(textview.getWindowToken(), 0);
        upload();
        continue; /* Loop/switch isn't completed */
_L2:
        finish();
        if(true) goto _L1; else goto _L4
_L4:
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mProfileId = getIntent().getLongExtra("extra_profile_id", -1L);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
            LoginActivity.redirectThroughLogin(this);
        else
            runUI();
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(mFilename != null && mDeleteFileOnDestroy)
            (new File(mFilename)).delete();
    }

    protected void onPause()
    {
        if(mAppSession != null && mListener != null)
            mAppSession.removeListener(mListener);
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this, getIntent());
        } else
        {
            updateFatTitleBar();
            if(mFilename == null && (Uri)getIntent().getExtras().getParcelable("android.intent.extra.STREAM") == null)
                finish();
            else
                mAppSession.addListener(mListener);
        }
    }

    private AppSession mAppSession;
    private boolean mDeleteFileOnDestroy;
    private String mFilename;
    private AppSessionListener mListener;
    private FacebookProfile mProfile;
    private long mProfileId;



/*
    static FacebookProfile access$102(UploadVideoActivity uploadvideoactivity, FacebookProfile facebookprofile)
    {
        uploadvideoactivity.mProfile = facebookprofile;
        return facebookprofile;
    }

*/

}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreateEditAlbumActivity.java

package com.facebook.katana.activity.media;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.facebook.katana.AlertDialogs;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookAlbum;
import com.facebook.katana.util.*;
import java.util.List;

public class CreateEditAlbumActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener
{
    private class CreateEditAlbumAppSessionListener extends AppSessionListener
    {

        public void onPhotoCreateAlbumComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookAlbum facebookalbum)
        {
            removeDialog(1);
            mCreateReqId = null;
            if(i == 200)
            {
                Toaster.toast(CreateEditAlbumActivity.this, 0x7f0a003a);
                setResult(-1);
                finish();
            } else
            {
                Toaster.toast(CreateEditAlbumActivity.this, 0x7f0a0038);
            }
        }

        public void onPhotoEditAlbumComplete(AppSession appsession, String s, int i, String s1, Exception exception, String s2)
        {
            removeDialog(2);
            mEditReqId = null;
            if(i == 200)
            {
                Toaster.toast(CreateEditAlbumActivity.this, 0x7f0a0041);
                setResult(-1);
                finish();
            } else
            {
                Toaster.toast(CreateEditAlbumActivity.this, 0x7f0a003f);
            }
        }

        final CreateEditAlbumActivity this$0;

        private CreateEditAlbumAppSessionListener()
        {
            this$0 = CreateEditAlbumActivity.this;
            super();
        }

    }


    public CreateEditAlbumActivity()
    {
    }

    private boolean checkChanges()
    {
        boolean flag;
        if(getIntent().getAction().equals("android.intent.action.EDIT"))
            flag = checkEditChanges();
        else
            flag = checkCreateChanges();
        return flag;
    }

    private boolean checkCreateChanges()
    {
        boolean flag;
        if(((TextView)findViewById(0x7f0e0005)).getText().length() > 0)
            flag = true;
        else
        if(((TextView)findViewById(0x7f0e0009)).getText().length() > 0)
            flag = true;
        else
        if(((TextView)findViewById(0x7f0e000b)).getText().length() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean checkEditChanges()
    {
        String s = ((TextView)findViewById(0x7f0e0005)).getText().toString();
        String s1;
        boolean flag;
        if(mAlbum.getName() != null)
            s1 = mAlbum.getName();
        else
            s1 = "";
        if(!s.equals(s1))
        {
            flag = true;
        } else
        {
            String s2 = ((TextView)findViewById(0x7f0e000b)).getText().toString();
            String s3;
            if(mAlbum.getLocation() != null)
                s3 = mAlbum.getLocation();
            else
                s3 = "";
            if(!s2.equals(s3))
            {
                flag = true;
            } else
            {
                String s4 = ((TextView)findViewById(0x7f0e0009)).getText().toString();
                String s5;
                if(mAlbum.getDescription() != null)
                    s5 = mAlbum.getDescription();
                else
                    s5 = "";
                if(!s4.equals(s5))
                    flag = true;
                else
                if(!visibilityFromSpinnerPosition(((Spinner)findViewById(0x7f0e0053)).getSelectedItemPosition()).equals(mAlbum.getVisibility()))
                    flag = true;
                else
                    flag = false;
            }
        }
        return flag;
    }

    private void createAlbum()
    {
        String s = ((TextView)findViewById(0x7f0e0005)).getText().toString().trim();
        if(s.length() == 0)
        {
            Toaster.toast(this, 0x7f0a0045);
        } else
        {
            String s1 = ((TextView)findViewById(0x7f0e000b)).getText().toString().trim();
            if(s1.length() == 0)
                s1 = null;
            String s2 = ((TextView)findViewById(0x7f0e0009)).getText().toString().trim();
            if(s2.length() == 0)
                s2 = null;
            String s3 = visibilityFromSpinnerPosition(((Spinner)findViewById(0x7f0e0053)).getSelectedItemPosition());
            mCreateReqId = mAppSession.photoCreateAlbum(this, s, s1, s2, s3);
            showDialog(1);
        }
    }

    private boolean onBackKeyPressed()
    {
        boolean flag;
        if(checkChanges())
        {
            showDialog(3);
            flag = true;
        } else
        {
            finish();
            flag = true;
        }
        return flag;
    }

    private static int spinnerPositionFromString(String s)
    {
        int i;
        if(s.equals("everyone"))
            i = 0;
        else
        if(s.equals("networks"))
            i = 1;
        else
        if(s.equals("friends-of-friends"))
            i = 2;
        else
        if(s.equals("friends"))
            i = 3;
        else
        if(s.equals("custom"))
            i = 4;
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid visibility: ").append(s).toString());
        return i;
    }

    private void updateAlbum()
    {
        if(!checkEditChanges())
        {
            Toaster.toast(this, 0x7f0a0047);
        } else
        {
            String s = ((TextView)findViewById(0x7f0e0005)).getText().toString().trim();
            if(s.length() == 0)
            {
                Toaster.toast(this, 0x7f0a0045);
            } else
            {
                String s1 = ((TextView)findViewById(0x7f0e000b)).getText().toString().trim();
                String s2 = ((TextView)findViewById(0x7f0e0009)).getText().toString().trim();
                if(s1.length() == 0)
                    s1 = " ";
                if(s2.length() == 0)
                    s2 = " ";
                String s3 = visibilityFromSpinnerPosition(((Spinner)findViewById(0x7f0e0053)).getSelectedItemPosition());
                mEditReqId = mAppSession.photoEditAlbum(this, mAlbum.getAlbumId(), s, s1, s2, s3);
                showDialog(2);
            }
        }
    }

    private static String visibilityFromSpinnerPosition(int i)
    {
        i;
        JVM INSTR tableswitch 0 4: default 36
    //                   0 63
    //                   1 68
    //                   2 74
    //                   3 80
    //                   4 86;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        throw new IllegalArgumentException((new StringBuilder()).append("Invalid position: ").append(i).toString());
_L2:
        String s = "everyone";
_L8:
        return s;
_L3:
        s = "networks";
        continue; /* Loop/switch isn't completed */
_L4:
        s = "friends-of-friends";
        continue; /* Loop/switch isn't completed */
_L5:
        s = "friends";
        continue; /* Loop/switch isn't completed */
_L6:
        s = "custom";
        if(true) goto _L8; else goto _L7
_L7:
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR tableswitch 2131624020 2131624021: default 28
    //                   2131624020 29
    //                   2131624021 58;
           goto _L1 _L2 _L3
_L1:
        return;
_L2:
        if(getIntent().getAction().equals("android.intent.action.EDIT"))
            updateAlbum();
        else
            createAlbum();
        continue; /* Loop/switch isn't completed */
_L3:
        if(checkChanges())
            showDialog(3);
        else
            finish();
        if(true) goto _L1; else goto _L4
_L4:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030014);
        ArrayAdapter arrayadapter = new ArrayAdapter(this, 0x1090008);
        arrayadapter.setDropDownViewResource(0x1090009);
        arrayadapter.add(getString(0x7f0a013c));
        arrayadapter.add(getString(0x7f0a013e));
        arrayadapter.add(getString(0x7f0a013d));
        arrayadapter.add(getString(0x7f0a013f));
        findViewById(0x7f0e0054).setOnClickListener(this);
        findViewById(0x7f0e0055).setOnClickListener(this);
        boolean flag = false;
        Spinner spinner;
        if(getIntent().getAction().equals("android.intent.action.EDIT"))
        {
            ((TextView)findViewById(0x7f0e0071)).setText(0x7f0a0042);
            ((TextView)findViewById(0x7f0e0072)).setText(0x7f0a0040);
            ((Button)findViewById(0x7f0e0054)).setText(0x7f0a003e);
            mAlbum = FacebookAlbum.readFromContentProvider(this, (String)getIntent().getData().getPathSegments().get(2));
            ((TextView)findViewById(0x7f0e0005)).setText(mAlbum.getName());
            ((TextView)findViewById(0x7f0e000b)).setText(mAlbum.getLocation());
            ((TextView)findViewById(0x7f0e0009)).setText(mAlbum.getDescription());
            if(mAlbum.getVisibility().equals("custom"))
                arrayadapter.add(getString(0x7f0a0141));
            flag = true;
        } else
        {
            ((TextView)findViewById(0x7f0e0071)).setText(0x7f0a003b);
            ((TextView)findViewById(0x7f0e0072)).setText(0x7f0a0039);
            ((Button)findViewById(0x7f0e0054)).setText(0x7f0a0037);
        }
        spinner = (Spinner)findViewById(0x7f0e0053);
        spinner.setAdapter(arrayadapter);
        spinner.setPrompt(getString(0x7f0a0048));
        if(flag)
            spinner.setSelection(spinnerPositionFromString(mAlbum.getVisibility()));
        mAppSessionListener = new CreateEditAlbumAppSessionListener();
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 3: default 28
    //                   1 34
    //                   2 81
    //                   3 128;
           goto _L1 _L2 _L3 _L4
_L1:
        Object obj = null;
_L6:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog1 = new ProgressDialog(this);
        progressdialog1.setProgressStyle(0);
        progressdialog1.setMessage(getText(0x7f0a003c));
        progressdialog1.setIndeterminate(true);
        progressdialog1.setCancelable(false);
        obj = progressdialog1;
        continue; /* Loop/switch isn't completed */
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a0043));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L4:
        android.content.DialogInterface.OnClickListener onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int k)
            {
                finish();
            }

            final CreateEditAlbumActivity this$0;

            
            {
                this$0 = CreateEditAlbumActivity.this;
                super();
            }
        }
;
        int j;
        if(getIntent().getAction().equals("android.intent.action.EDIT"))
            j = 0x7f0a000f;
        else
            j = 0x7f0a0009;
        obj = AlertDialogs.createAlert(this, getString(j), 0x1080027, getString(0x7f0a0049), getString(0x7f0a0233), onclicklistener, getString(0x7f0a00d4), null, null, true);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if(i != 4) goto _L2; else goto _L1
_L1:
        if(!PlatformUtils.isEclairOrLater()) goto _L4; else goto _L3
_L3:
        if(!EclairKeyHandler.onKeyDown(keyevent)) goto _L2; else goto _L5
_L5:
        boolean flag = true;
_L7:
        return flag;
_L4:
        if(onBackKeyPressed())
        {
            flag = true;
            continue; /* Loop/switch isn't completed */
        }
_L2:
        flag = super.onKeyDown(i, keyevent);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public boolean onKeyUp(int i, KeyEvent keyevent)
    {
        boolean flag;
        if(i == 4 && PlatformUtils.isEclairOrLater() && EclairKeyHandler.onKeyUp(keyevent) && onBackKeyPressed())
            flag = true;
        else
            flag = super.onKeyUp(i, keyevent);
        return flag;
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(mCreateReqId != null && !mAppSession.isRequestPending(mCreateReqId))
            {
                removeDialog(1);
                mCreateReqId = null;
            }
            if(mEditReqId != null && !mAppSession.isRequestPending(mEditReqId))
            {
                removeDialog(2);
                mEditReqId = null;
            }
            mAppSession.addListener(mAppSessionListener);
        }
    }

    private static final int PROGRESS_CREATE_DIALOG_ID = 1;
    private static final int PROGRESS_EDIT_DIALOG_ID = 2;
    private static final int QUIT_DIALOG_ID = 3;
    private FacebookAlbum mAlbum;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mCreateReqId;
    private String mEditReqId;


/*
    static String access$002(CreateEditAlbumActivity createeditalbumactivity, String s)
    {
        createeditalbumactivity.mCreateReqId = s;
        return s;
    }

*/


/*
    static String access$102(CreateEditAlbumActivity createeditalbumactivity, String s)
    {
        createeditalbumactivity.mEditReqId = s;
        return s;
    }

*/
}

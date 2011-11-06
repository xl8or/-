// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PasswordDialogActivity.java

package com.facebook.katana;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;

// Referenced classes of package com.facebook.katana:
//            LoginActivity

public class PasswordDialogActivity extends BaseFacebookActivity
{

    public PasswordDialogActivity()
    {
    }

    private void disableButtons()
    {
        findViewById(0x7f0e00e0).setEnabled(false);
        findViewById(0x7f0e0055).setEnabled(false);
    }

    private void doLogout()
    {
        AppSession appsession = AppSession.getActiveSession(this, true);
        if(appsession != null)
        {
            showDialog(1);
            appsession.authLogout(this);
        }
    }

    public boolean facebookOnBackPressed()
    {
        return true;
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03004e);
        String s = getIntent().getStringExtra("un");
        ((TextView)findViewById(0x7f0e00dd)).setText(s);
        findViewById(0x7f0e00e0).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                String s1 = ((EditText)findViewById(0x7f0e00de)).getText().toString();
                AppSession.getActiveSession(PasswordDialogActivity.this, false).handlePasswordEntry(PasswordDialogActivity.this, s1);
                disableButtons();
                finish();
            }

            final PasswordDialogActivity this$0;

            
            {
                this$0 = PasswordDialogActivity.this;
                super();
            }
        }
);
        findViewById(0x7f0e0055).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                doLogout();
            }

            final PasswordDialogActivity this$0;

            
            {
                this$0 = PasswordDialogActivity.this;
                super();
            }
        }
);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
            LoginActivity.toLogin(this);
        else
            mAppSessionListener = new AppSessionListener() {

                public void onLogoutComplete(AppSession appsession, String s1, int i, String s2, Exception exception)
                {
                    removeDialog(1);
                    LoginActivity.toLogin(PasswordDialogActivity.this);
                }

                final PasswordDialogActivity this$0;

            
            {
                this$0 = PasswordDialogActivity.this;
                super();
            }
            }
;
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 1: default 20
    //                   1 24;
           goto _L1 _L2
_L1:
        Object obj = null;
_L4:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a01bd));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected void onPause()
    {
        super.onPause();
        mAppSession.removeListener(mAppSessionListener);
    }

    protected void onResume()
    {
        super.onResume();
        mAppSession.addListener(mAppSessionListener);
    }

    private static final int PROGRESS_LOGGING_OUT_DIALOG_ID = 1;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;


}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LegalDisclaimerActivity.java

package com.facebook.katana.activity.findfriends;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.util.GrowthUtils;

public class LegalDisclaimerActivity extends BaseFacebookActivity
{

    public LegalDisclaimerActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030026);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        hideSearchButton();
        mImportButton = (Button)findViewById(0x7f0e007b);
        android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                setResult(-1);
                finish();
            }

            final LegalDisclaimerActivity this$0;

            
            {
                this$0 = LegalDisclaimerActivity.this;
                super();
            }
        }
;
        mImportButton.setOnClickListener(onclicklistener);
        setPrimaryActionFace(-1, getString(0x7f0a0022));
        if(GrowthUtils.kddiImporterEnabled(this))
        {
            ((LinearLayout)findViewById(0x7f0e007e)).setVisibility(0);
            findViewById(0x7f0e007c).setVisibility(8);
            ((Button)findViewById(0x7f0e007f)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    Intent intent = new Intent("android.intent.action.VIEW", kddiUri);
                    startActivity(intent);
                }

                final LegalDisclaimerActivity this$0;
                final Uri val$kddiUri;

            
            {
                this$0 = LegalDisclaimerActivity.this;
                kddiUri = uri;
                super();
            }
            }
);
        }
        if(true) goto _L4; else goto _L3
_L3:
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
        obj = (new android.app.AlertDialog.Builder(this)).setCancelable(true).setMessage(0x7f0a0298).setPositiveButton(0x7f0a00dd, new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.cancel();
            }

            final LegalDisclaimerActivity this$0;

            
            {
                this$0 = LegalDisclaimerActivity.this;
                super();
            }
        }
).create();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        setResult(0);
        finish();
    }

    private static final int SUMMARY_DIALOG = 1;
    private AppSession mAppSession;
    private Button mImportButton;
}

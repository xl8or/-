// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WidgetActivity.java

package com.facebook.katana;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.facebook.katana.service.FacebookService;
import com.facebook.katana.util.Toaster;

public class WidgetActivity extends Activity
{

    public WidgetActivity()
    {
        mFirstTime = true;
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent)
            {
                removeDialog(2);
                if(intent.getIntExtra("extra_error_code", 0) == 200)
                {
                    Toaster.toast(context, 0x7f0a0232);
                    finish();
                } else
                {
                    showDialog(1);
                    Toaster.toast(context, 0x7f0a01c4);
                }
            }

            final WidgetActivity this$0;

            
            {
                this$0 = WidgetActivity.this;
                super();
            }
        }
;
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("com.facebook.katana.widget.publish.result");
        registerReceiver(mReceiver, intentfilter);
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 28
    //                   2 164;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L5:
        return ((Dialog) (obj));
_L2:
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View view = ((LayoutInflater)getSystemService("layout_inflater")).inflate(0x7f030073, null);
        builder.setView(view);
        builder.setTitle(0x7f0a01d0);
        final TextView textInput = (TextView)view.findViewById(0x7f0e00d1);
        if(mText != null)
            textInput.setText(mText);
        builder.setPositiveButton(getString(0x7f0a01cf), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                String s = textInput.getText().toString().trim();
                if(s.length() == 0)
                {
                    finish();
                } else
                {
                    mText = s;
                    try
                    {
                        Intent intent = new Intent(WidgetActivity.this, com/facebook/katana/service/FacebookService);
                        intent.putExtra("type", 92);
                        intent.putExtra("status", s);
                        PendingIntent.getService(WidgetActivity.this, 0, intent, 0x10000000).send();
                        showDialog(2);
                    }
                    catch(Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }

            final WidgetActivity this$0;
            final TextView val$textInput;

            
            {
                this$0 = WidgetActivity.this;
                textInput = textview;
                super();
            }
        }
);
        builder.setNegativeButton(getString(0x7f0a0022), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                finish();
            }

            final WidgetActivity this$0;

            
            {
                this$0 = WidgetActivity.this;
                super();
            }
        }
);
        builder.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialoginterface)
            {
                finish();
            }

            final WidgetActivity this$0;

            
            {
                this$0 = WidgetActivity.this;
                super();
            }
        }
);
        obj = builder.create();
        continue; /* Loop/switch isn't completed */
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a01c5));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(mReceiver != null)
            unregisterReceiver(mReceiver);
    }

    public void onResume()
    {
        super.onResume();
        if(mFirstTime)
        {
            mFirstTime = false;
            showDialog(1);
        }
    }

    public static final String ACTION_WIDGET_PUBLISH_RESULT = "com.facebook.katana.widget.publish.result";
    public static final String EXTRA_ERROR_CODE = "extra_error_code";
    private static final int PROGRESS_DIALOG = 2;
    private static final int SHARE_DIALOG = 1;
    private boolean mFirstTime;
    private BroadcastReceiver mReceiver;
    private String mText;


/*
    static String access$002(WidgetActivity widgetactivity, String s)
    {
        widgetactivity.mText = s;
        return s;
    }

*/
}

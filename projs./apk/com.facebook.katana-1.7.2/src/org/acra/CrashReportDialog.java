// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CrashReportDialog.java

package org.acra;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import org.acra.annotation.ReportsCrashes;

// Referenced classes of package org.acra:
//            ACRA, ErrorReporter

public class CrashReportDialog extends Activity
{

    public CrashReportDialog()
    {
        prefs = null;
        userComment = null;
        userEmail = null;
        mReportFileName = null;
    }

    protected void cancelNotification()
    {
        ((NotificationManager)getSystemService("notification")).cancel(666);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mReportFileName = getIntent().getStringExtra("REPORT_FILE_NAME");
        Log.d(ACRA.LOG_TAG, (new StringBuilder()).append("Opening CrashReportDialog for ").append(mReportFileName).toString());
        if(mReportFileName == null)
            finish();
        requestWindowFeature(3);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(1);
        linearlayout.setPadding(10, 10, 10, 10);
        linearlayout.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -2));
        ScrollView scrollview = new ScrollView(this);
        linearlayout.addView(scrollview, new android.widget.LinearLayout.LayoutParams(-1, -1, 1F));
        TextView textview = new TextView(this);
        textview.setText(getText(ACRA.getConfig().resDialogText()));
        scrollview.addView(textview, -1, -1);
        int i = ACRA.getConfig().resDialogCommentPrompt();
        if(i != 0)
        {
            TextView textview1 = new TextView(this);
            textview1.setText(getText(i));
            textview1.setPadding(textview1.getPaddingLeft(), 10, textview1.getPaddingRight(), textview1.getPaddingBottom());
            linearlayout.addView(textview1, new android.widget.LinearLayout.LayoutParams(-1, -2));
            userComment = new EditText(this);
            userComment.setLines(2);
            linearlayout.addView(userComment, new android.widget.LinearLayout.LayoutParams(-1, -2));
        }
        int j = ACRA.getConfig().resDialogEmailPrompt();
        if(j != 0)
        {
            TextView textview2 = new TextView(this);
            textview2.setText(getText(j));
            textview2.setPadding(textview2.getPaddingLeft(), 10, textview2.getPaddingRight(), textview2.getPaddingBottom());
            linearlayout.addView(textview2, new android.widget.LinearLayout.LayoutParams(-1, -2));
            userEmail = new EditText(this);
            userEmail.setSingleLine();
            userEmail.setInputType(33);
            prefs = getSharedPreferences(ACRA.getConfig().sharedPreferencesName(), ACRA.getConfig().sharedPreferencesMode());
            userEmail.setText(prefs.getString("acra.user.email", ""));
            linearlayout.addView(userEmail, new android.widget.LinearLayout.LayoutParams(-1, -2));
        }
        LinearLayout linearlayout1 = new LinearLayout(this);
        linearlayout1.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, -2));
        linearlayout1.setPadding(linearlayout1.getPaddingLeft(), 10, linearlayout1.getPaddingRight(), linearlayout1.getPaddingBottom());
        Button button = new Button(this);
        button.setText(0x1040013);
        button.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ErrorReporter errorreporter = ErrorReporter.getInstance();
                errorreporter.getClass();
                ErrorReporter.ReportsSenderWorker reportssenderworker = new ErrorReporter.ReportsSenderWorker(errorreporter);
                reportssenderworker.setApprovePendingReports();
                if(userComment != null)
                    reportssenderworker.setUserComment(mReportFileName, userComment.getText().toString());
                if(prefs != null && userEmail != null)
                {
                    String s = userEmail.getText().toString();
                    android.content.SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("acra.user.email", s);
                    editor.commit();
                    reportssenderworker.setUserEmail(mReportFileName, s);
                }
                Log.v(ACRA.LOG_TAG, "About to start ReportSenderWorker from CrashReportDialog");
                reportssenderworker.start();
                int l = ACRA.getConfig().resDialogOkToast();
                if(l != 0)
                    Toast.makeText(getApplicationContext(), l, 1).show();
                finish();
            }

            final CrashReportDialog this$0;

            
            {
                this$0 = CrashReportDialog.this;
                super();
            }
        }
);
        linearlayout1.addView(button, new android.widget.LinearLayout.LayoutParams(-1, -2, 1F));
        Button button1 = new Button(this);
        button1.setText(0x1040009);
        button1.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ErrorReporter.getInstance().deletePendingReports();
                finish();
            }

            final CrashReportDialog this$0;

            
            {
                this$0 = CrashReportDialog.this;
                super();
            }
        }
);
        linearlayout1.addView(button1, new android.widget.LinearLayout.LayoutParams(-1, -2, 1F));
        linearlayout.addView(linearlayout1, new android.widget.LinearLayout.LayoutParams(-1, -2));
        setContentView(linearlayout);
        int k = ACRA.getConfig().resDialogTitle();
        if(k != 0)
            setTitle(k);
        getWindow().setFeatureDrawableResource(3, ACRA.getConfig().resDialogIcon());
        cancelNotification();
    }

    String mReportFileName;
    private SharedPreferences prefs;
    private EditText userComment;
    private EditText userEmail;



}

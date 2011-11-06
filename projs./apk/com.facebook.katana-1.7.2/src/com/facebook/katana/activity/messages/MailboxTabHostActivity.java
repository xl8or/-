// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxTabHostActivity.java

package com.facebook.katana.activity.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.MyTabHost;
import com.facebook.katana.activity.BaseFacebookTabActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.provider.MailboxProvider;

// Referenced classes of package com.facebook.katana.activity.messages:
//            MailboxThreadsActivity, MessageComposeActivity

public class MailboxTabHostActivity extends BaseFacebookTabActivity
    implements com.facebook.katana.MyTabHost.OnTabChangeListener
{
    public static interface ProgressListener
    {

        public abstract void onShowProgress(boolean flag);
    }


    public MailboxTabHostActivity()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        if(AppSession.getActiveSession(this, true) == null)
        {
            LoginActivity.toLogin(this, getIntent());
            finish();
        } else
        {
            setContentView(0x7f03007f);
            setPrimaryActionIcon(0x7f0200fa);
            MyTabHost mytabhost = (MyTabHost)getTabHost();
            Intent intent = new Intent(this, com/facebook/katana/activity/messages/MailboxThreadsActivity);
            intent.setData(MailboxProvider.INBOX_THREADS_CONTENT_URI);
            intent.putExtra("within_tab", true);
            intent.putExtra("extra_parent_tag", getTag());
            intent.putExtra("extra_folder", 0);
            com.facebook.katana.MyTabHost.TabSpec tabspec = mytabhost.myNewTabSpec("inbox", setupAndGetTabView("inbox", 0x7f0a00ae));
            tabspec.setContent(intent);
            mytabhost.addTab(tabspec);
            Intent intent1 = new Intent(this, com/facebook/katana/activity/messages/MailboxThreadsActivity);
            intent1.setData(MailboxProvider.UPDATES_THREADS_CONTENT_URI);
            intent1.putExtra("within_tab", true);
            intent1.putExtra("extra_parent_tag", getTag());
            intent1.putExtra("extra_folder", 4);
            com.facebook.katana.MyTabHost.TabSpec tabspec1 = mytabhost.myNewTabSpec("updates", setupAndGetTabView("updates", 0x7f0a00bf));
            tabspec1.setContent(intent1);
            mytabhost.addTab(tabspec1);
            Intent intent2 = new Intent(this, com/facebook/katana/activity/messages/MailboxThreadsActivity);
            intent2.putExtra("within_tab", true);
            intent2.putExtra("extra_parent_tag", getTag());
            intent2.setData(MailboxProvider.OUTBOX_THREADS_CONTENT_URI);
            intent2.putExtra("extra_folder", 1);
            com.facebook.katana.MyTabHost.TabSpec tabspec2 = mytabhost.myNewTabSpec("outbox", setupAndGetTabView("outbox", 0x7f0a00b7));
            tabspec2.setContent(intent2);
            mytabhost.addTab(tabspec2);
            mProgressListener = new ProgressListener() {

                public void onShowProgress(boolean flag)
                {
                    View view = findViewById(0x7f0e00f1);
                    int i;
                    if(flag)
                        i = 0;
                    else
                        i = 8;
                    view.setVisibility(i);
                }

                final MailboxTabHostActivity this$0;

            
            {
                this$0 = MailboxTabHostActivity.this;
                super();
            }
            }
;
            mCurrentActivity = (MailboxThreadsActivity)getCurrentActivity();
            mCurrentActivity.setProgressListener(mProgressListener);
            String s = getIntent().getStringExtra("extra_tag");
            if(s != null)
            {
                mytabhost.setCurrentTabByTag(s);
                onTabChanged(s);
            } else
            {
                mytabhost.setCurrentTabByTag("inbox");
                onTabChanged("inbox");
            }
            mytabhost.setOnTabChangedListener(this);
            setupTabs();
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
        if(mCurrentActivity != null)
            mCurrentActivity.setProgressListener(null);
    }

    public void onTabChanged(String s)
    {
        if(mCurrentActivity != null)
            mCurrentActivity.setProgressListener(null);
        mCurrentActivity = (MailboxThreadsActivity)getCurrentActivity();
        mCurrentActivity.setProgressListener(mProgressListener);
    }

    public void titleBarPrimaryActionClickHandler(View view)
    {
        startActivity(new Intent(this, com/facebook/katana/activity/messages/MessageComposeActivity));
    }

    public static final String EXTRA_TAG = "extra_tag";
    public static final String TAG_INBOX = "inbox";
    public static final String TAG_OUTBOX = "outbox";
    public static final String TAG_UPDATES = "updates";
    private MailboxThreadsActivity mCurrentActivity;
    private ProgressListener mProgressListener;
}

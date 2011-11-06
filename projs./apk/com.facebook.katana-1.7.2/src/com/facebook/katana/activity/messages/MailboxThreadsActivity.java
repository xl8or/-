// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxThreadsActivity.java

package com.facebook.katana.activity.messages;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.facebook.katana.CheckboxAdapterListener;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;

// Referenced classes of package com.facebook.katana.activity.messages:
//            MailboxThreadsAdapter, MailboxMessagesActivity, BaseUserSelectionListener, MessageComposeActivity

public class MailboxThreadsActivity extends BaseFacebookListActivity
    implements android.widget.AdapterView.OnItemClickListener, CheckboxAdapterListener, android.widget.AbsListView.OnScrollListener
{
    private class ThreadsAppSessionListener extends AppSessionListener
    {

        public void onMailboxDeleteThreadComplete(AppSession appsession, String s, int i, String s1, Exception exception, long al[])
        {
            if(al.length == 1)
            {
                removeDialog(1);
                mDeleteThreadReqId = null;
            } else
            {
                removeDialog(2);
                mDeleteThreadsReqId = null;
            }
            if(i == 200)
            {
                mAdapter.uncheckAll();
                showButtonBar(false);
            } else
            {
                String s2 = StringUtils.getErrorString(MailboxThreadsActivity.this, getString(0x7f0a00a9), i, s1, exception);
                Toaster.toast(MailboxThreadsActivity.this, s2);
            }
        }

        public void onMailboxMarkThreadComplete(AppSession appsession, String s, int i, String s1, Exception exception, long al[], boolean flag)
        {
            if(i == 200)
            {
                mAdapter.uncheckAll();
                showButtonBar(false);
            } else
            {
                MailboxThreadsActivity mailboxthreadsactivity = MailboxThreadsActivity.this;
                MailboxThreadsActivity mailboxthreadsactivity1 = MailboxThreadsActivity.this;
                int j;
                String s2;
                if(flag)
                    j = 0x7f0a00b3;
                else
                    j = 0x7f0a00b4;
                s2 = StringUtils.getErrorString(mailboxthreadsactivity, mailboxthreadsactivity1.getString(j), i, s1, exception);
                Toaster.toast(MailboxThreadsActivity.this, s2);
            }
        }

        public void onMailboxSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j)
        {
            if(j == mFolder)
            {
                logStepDataReceived();
                showProgress(false);
                if(200 == i)
                {
                    mReceivedGetThreadsResponse = true;
                } else
                {
                    String s2 = StringUtils.getErrorString(MailboxThreadsActivity.this, getString(0x7f0a00ad), i, s1, exception);
                    Toaster.toast(MailboxThreadsActivity.this, s2);
                }
            }
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mAdapter.updateUserImage(profileimage);
        }

        final MailboxThreadsActivity this$0;

        private ThreadsAppSessionListener()
        {
            this$0 = MailboxThreadsActivity.this;
            super();
        }

    }

    private final class QueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            mLoading = false;
            if(!isFinishing())
                handleQueryComplete(cursor);
            else
                cursor.close();
        }

        public static final int QUERY_THREADS_TOKEN = 1;
        final MailboxThreadsActivity this$0;

        public QueryHandler(Context context)
        {
            this$0 = MailboxThreadsActivity.this;
            super(context.getContentResolver());
        }
    }


    public MailboxThreadsActivity()
    {
        mLoading = true;
        mReceivedGetThreadsResponse = false;
    }

    private void checkToLoadMore(int i)
    {
        if(i == mAdapter.getCount() && !mAppSession.isMailboxSyncPending(mFolder))
        {
            int j;
            if(i + 20 > 100)
                j = 100 - i;
            else
                j = 20;
            if(j > 0)
                mailboxSync(i, j, false);
        }
    }

    private void handleQueryComplete(Cursor cursor)
    {
        startManagingCursor(cursor);
        mAdapter.changeCursor(cursor);
        if(!mAppSession.isMailboxSyncPending(mFolder))
        {
            if(mAdapter.getCount() == 0 || !mReceivedGetThreadsResponse)
                mailboxSync(0, 20, false);
            else
                showProgress(false);
        } else
        {
            showProgress(true);
        }
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a00b5);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a00af);
    }

    private void showButtonBar(boolean flag)
    {
        View view;
        ViewStub viewstub = (ViewStub)findViewById(0x7f0e00c8);
        if(viewstub != null)
        {
            viewstub.inflate();
            findViewById(0x7f0e00c3).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    mAppSession.mailboxMarkThread(MailboxThreadsActivity.this, mFolder, mAdapter.getMarkedThreads(), true);
                }

                final MailboxThreadsActivity this$0;

            
            {
                this$0 = MailboxThreadsActivity.this;
                super();
            }
            }
);
            findViewById(0x7f0e00c4).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    mAppSession.mailboxMarkThread(MailboxThreadsActivity.this, mFolder, mAdapter.getMarkedThreads(), false);
                }

                final MailboxThreadsActivity this$0;

            
            {
                this$0 = MailboxThreadsActivity.this;
                super();
            }
            }
);
            findViewById(0x7f0e00c5).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view1)
                {
                    long al[] = mAdapter.getMarkedThreads();
                    String s = mAppSession.mailboxDeleteThread(MailboxThreadsActivity.this, al, mFolder);
                    if(al.length == 1)
                    {
                        mDeleteThreadReqId = s;
                        showDialog(1);
                    } else
                    {
                        mDeleteThreadsReqId = s;
                        showDialog(2);
                    }
                }

                final MailboxThreadsActivity this$0;

            
            {
                this$0 = MailboxThreadsActivity.this;
                super();
            }
            }
);
        }
        view = findViewById(0x7f0e00c9);
        if(!flag || view.getVisibility() != 8) goto _L2; else goto _L1
_L1:
        view.setAnimation(AnimationUtils.loadAnimation(this, 0x7f040005));
        view.setVisibility(0);
_L4:
        return;
_L2:
        if(!flag && view.getVisibility() == 0)
        {
            view.setAnimation(AnimationUtils.loadAnimation(this, 0x7f040004));
            view.setVisibility(8);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void showMessages(Cursor cursor)
    {
        long l = cursor.getLong(1);
        Intent intent = new Intent(this, com/facebook/katana/activity/messages/MailboxMessagesActivity);
        intent.setData(Uri.withAppendedPath(MailboxProvider.getMessagesTidFolderUri(mFolder), (new StringBuilder()).append("").append(l).toString()));
        intent.putExtra("extra_folder", mFolder);
        startActivity(intent);
    }

    private void showProgress(boolean flag)
    {
        if(mProgressListener != null)
            mProgressListener.onShowProgress(flag);
        if(flag)
        {
            findViewById(0x7f0e0056).setVisibility(8);
            findViewById(0x7f0e0057).setVisibility(0);
        } else
        {
            findViewById(0x7f0e0056).setVisibility(0);
            findViewById(0x7f0e0057).setVisibility(8);
        }
    }

    protected void mailboxSync(int i, int j, boolean flag)
    {
        showProgress(true);
        mAppSession.mailboxSync(this, mFolder, i, j, flag);
        logStepDataRequested();
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        boolean flag;
        Cursor cursor;
        android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo;
        try
        {
            adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();
        }
        catch(ClassCastException classcastexception)
        {
            flag = false;
            continue; /* Loop/switch isn't completed */
        }
        cursor = (Cursor)mAdapter.getItem(adaptercontextmenuinfo.position);
        menuitem.getItemId();
        JVM INSTR tableswitch 101 104: default 64
    //                   101 74
    //                   102 85
    //                   103 121
    //                   104 157;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        flag = false;
_L6:
        return flag;
_L2:
        showMessages(cursor);
        flag = true;
        if(true) goto _L6; else goto _L3
_L3:
        long al2[] = new long[1];
        al2[0] = cursor.getLong(1);
        mAppSession.mailboxMarkThread(this, mFolder, al2, false);
        continue; /* Loop/switch isn't completed */
_L4:
        long al1[] = new long[1];
        al1[0] = cursor.getLong(1);
        mAppSession.mailboxMarkThread(this, mFolder, al1, true);
        continue; /* Loop/switch isn't completed */
_L5:
        long al[] = new long[1];
        al[0] = cursor.getLong(1);
        mDeleteThreadReqId = mAppSession.mailboxDeleteThread(this, al, mFolder);
        showDialog(1);
        if(true) goto _L1; else goto _L7
_L7:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030043);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mFolder = getIntent().getIntExtra("extra_folder", 0);
            ListView listview = getListView();
            mAdapter = new MailboxThreadsAdapter(this, null, mFolder, mAppSession.getUserImagesCache(), this, new BaseUserSelectionListener(this));
            listview.setAdapter(mAdapter);
            listview.setOnCreateContextMenuListener(this);
            listview.setOnItemClickListener(this);
            listview.setOnScrollListener(this);
            mAppSessionListener = new ThreadsAppSessionListener();
            mQueryHandler = new QueryHandler(this);
            setupEmptyView();
        }
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
label0:
        {
            android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo;
            Cursor cursor;
            boolean flag;
            byte byte0;
            int i;
            try
            {
                adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)contextmenuinfo;
            }
            catch(ClassCastException classcastexception)
            {
                break label0;
            }
            cursor = (Cursor)mAdapter.getItem(adaptercontextmenuinfo.position);
            if(cursor.getInt(7) > 0)
                flag = true;
            else
                flag = false;
            contextmenu.setHeaderTitle(cursor.getString(3));
            contextmenu.add(0, 101, 0, 0x7f0a00c0);
            if(flag)
                byte0 = 103;
            else
                byte0 = 102;
            if(flag)
                i = 0x7f0a00b1;
            else
                i = 0x7f0a00b2;
            contextmenu.add(0, byte0, 0, i);
            contextmenu.add(0, 104, 0, 0x7f0a00a8);
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 28
    //                   2 74;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L5:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog1 = new ProgressDialog(this);
        progressdialog1.setProgressStyle(0);
        progressdialog1.setMessage(getText(0x7f0a00aa));
        progressdialog1.setIndeterminate(true);
        progressdialog1.setCancelable(false);
        obj = progressdialog1;
        continue; /* Loop/switch isn't completed */
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a00ab));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 2, 0, 0x7f0a00b8).setIcon(0x7f0200bc);
        menu.add(0, 3, 0, 0x7f0a00a7).setIcon(0x7f0200ad);
        menu.add(0, 4, 0, 0x7f0a00bb).setIcon(0x7f0200bd);
        menu.add(0, 5, 0, 0x7f0a00ac).setIcon(0x7f0200ae);
        return true;
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        showMessages((Cursor)mAdapter.getItem(i));
    }

    public void onMarkChanged(long l, boolean flag, int i)
    {
        boolean flag1;
        if(i > 0)
            flag1 = true;
        else
            flag1 = false;
        showButtonBar(flag1);
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 5: default 36
    //                   2 42
    //                   3 124
    //                   4 72
    //                   5 109;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        int i = mAdapter.getCount();
        int j;
        if(i == 0)
            j = 20;
        else
            j = i;
        mailboxSync(0, j, true);
        continue; /* Loop/switch isn't completed */
_L4:
        if(mAdapter.getCount() > 20)
        {
            Toaster.toast(this, 0x7f0a00b0);
        } else
        {
            mAdapter.checkAll();
            showButtonBar(true);
        }
        continue; /* Loop/switch isn't completed */
_L5:
        mAdapter.uncheckAll();
        showButtonBar(false);
        continue; /* Loop/switch isn't completed */
_L3:
        startActivity(new Intent(this, com/facebook/katana/activity/messages/MessageComposeActivity));
        if(true) goto _L1; else goto _L6
_L6:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuitem = menu.findItem(2);
        boolean flag;
        MenuItem menuitem1;
        boolean flag1;
        if(!mAppSession.isMailboxSyncPending(mFolder))
            flag = true;
        else
            flag = false;
        menuitem.setEnabled(flag);
        menuitem1 = menu.findItem(4);
        if(!mAdapter.areAllThreadsChecked())
            flag1 = true;
        else
            flag1 = false;
        menuitem1.setEnabled(flag1);
        menu.findItem(5).setEnabled(mAdapter.areAnyThreadsChecked());
        return true;
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
            if(mDeleteThreadReqId != null && !mAppSession.isRequestPending(mDeleteThreadReqId))
            {
                removeDialog(1);
                mDeleteThreadReqId = null;
            }
            if(mDeleteThreadsReqId != null && !mAppSession.isRequestPending(mDeleteThreadsReqId))
            {
                removeDialog(2);
                mDeleteThreadsReqId = null;
            }
            mAppSession.addListener(mAppSessionListener);
            mQueryHandler.startQuery(1, null, getIntent().getData(), MailboxThreadsAdapter.ThreadsQuery.PROJECTION, "other_party > 0", null, null);
        }
    }

    public void onScroll(AbsListView abslistview, int i, int j, int k)
    {
        if(i != mFirstCell)
            checkToLoadMore(i + j);
        else
            mFirstCell = i;
    }

    public void onScrollStateChanged(AbsListView abslistview, int i)
    {
    }

    public void setProgressListener(MailboxTabHostActivity.ProgressListener progresslistener)
    {
        mProgressListener = progresslistener;
        if(mAppSession != null)
        {
            if(mAppSession.isMailboxSyncPending(mFolder) || mLoading)
                showProgress(true);
            else
                showProgress(false);
        } else
        {
            showProgress(false);
        }
    }

    public boolean shouldChangeState(long l, boolean flag, int i)
    {
        boolean flag1;
        if(i > 20)
        {
            Toaster.toast(this, 0x7f0a00b0);
            flag1 = false;
        } else
        {
            flag1 = true;
        }
        return flag1;
    }

    private static final int COMPOSE_MESSAGE_ID = 3;
    private static final int DELETE_THREAD_ID = 104;
    private static final int DESELECT_ALL_ID = 5;
    public static final String EXTRA_FOLDER = "extra_folder";
    private static final int MARK_MESSAGE_AS_READ_ID = 103;
    private static final int MARK_MESSAGE_AS_UNREAD_ID = 102;
    private static final int MAX_MARK_ITEMS = 20;
    private static final int MAX_THREAD_COUNT = 100;
    private static final int PAGE_SIZE = 20;
    private static final int PROGRESS_DELETE_THREADS_DIALOG = 2;
    private static final int PROGRESS_DELETE_THREAD_DIALOG = 1;
    private static final int REFRESH_ID = 2;
    private static final int SELECT_ALL_ID = 4;
    private static final int VIEW_MESSAGES_ID = 101;
    private MailboxThreadsAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mDeleteThreadReqId;
    private String mDeleteThreadsReqId;
    private int mFirstCell;
    private int mFolder;
    private boolean mLoading;
    private MailboxTabHostActivity.ProgressListener mProgressListener;
    private QueryHandler mQueryHandler;
    private boolean mReceivedGetThreadsResponse;


/*
    static boolean access$002(MailboxThreadsActivity mailboxthreadsactivity, boolean flag)
    {
        mailboxthreadsactivity.mLoading = flag;
        return flag;
    }

*/



/*
    static boolean access$1002(MailboxThreadsActivity mailboxthreadsactivity, boolean flag)
    {
        mailboxthreadsactivity.mReceivedGetThreadsResponse = flag;
        return flag;
    }

*/






/*
    static String access$602(MailboxThreadsActivity mailboxthreadsactivity, String s)
    {
        mailboxthreadsactivity.mDeleteThreadReqId = s;
        return s;
    }

*/


/*
    static String access$702(MailboxThreadsActivity mailboxthreadsactivity, String s)
    {
        mailboxthreadsactivity.mDeleteThreadsReqId = s;
        return s;
    }

*/


}

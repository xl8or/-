// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxMessagesActivity.java

package com.facebook.katana.activity.messages;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.activity.BaseFacebookListActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.util.*;
import java.util.*;

// Referenced classes of package com.facebook.katana.activity.messages:
//            MailboxMessagesAdapter, BaseUserSelectionListener

public class MailboxMessagesActivity extends BaseFacebookListActivity
{
    private class MessagesAppSessionListener extends AppSessionListener
    {

        public void onMailboxDeleteThreadComplete(AppSession appsession, String s, int i, String s1, Exception exception, long al[])
        {
            removeDialog(2);
            mDeleteThreadReqId = null;
            if(MailboxMessagesActivity.responseIsPositive(i, exception))
            {
                if(mThreadsCursor != null)
                    if(mThreadsCursor.getCount() > 1)
                    {
                        if(!mThreadsCursor.moveToNext())
                            mThreadsCursor.moveToFirst();
                        mCurrentThreadId = mThreadsCursor.getLong(0);
                        mThreadsCursor.requery();
                        moveToCurrentThread();
                        switchToThread();
                    } else
                    {
                        finish();
                    }
            } else
            {
                String s2 = StringUtils.getErrorString(MailboxMessagesActivity.this, getString(0x7f0a00a9), i, s1, exception);
                Toaster.toast(MailboxMessagesActivity.this, s2);
            }
        }

        public void onMailboxGetThreadMessagesComplete(AppSession appsession, String s, int i, String s1, Exception exception, int j, long l)
        {
            if(l == mCurrentThreadId && j == mFolder)
            {
                logStepDataReceived();
                showProgress(false);
                if(!MailboxMessagesActivity.responseIsPositive(i, exception))
                {
                    String s2 = StringUtils.getErrorString(MailboxMessagesActivity.this, getString(0x7f0a00ca), i, s1, exception);
                    Toaster.toast(MailboxMessagesActivity.this, s2);
                }
            }
        }

        public void onMailboxMarkThreadComplete(AppSession appsession, String s, int i, String s1, Exception exception, long al[], boolean flag)
        {
            if(mThreadsCursor != null)
            {
                mThreadsCursor.requery();
                moveToCurrentThread();
                if(mCurrentThreadId == al[0])
                    if(MailboxMessagesActivity.responseIsPositive(i, exception))
                    {
                        TextView textview = (TextView)findViewById(0x7f0e00bb);
                        if(flag)
                            textview.setTypeface(Typeface.DEFAULT);
                        else
                            textview.setTypeface(Typeface.DEFAULT_BOLD);
                    } else
                    {
                        int j;
                        String s2;
                        if(flag)
                            j = 0x7f0a00b3;
                        else
                            j = 0x7f0a00b4;
                        s2 = StringUtils.getErrorString(MailboxMessagesActivity.this, getString(j), i, s1, exception);
                        Toaster.toast(MailboxMessagesActivity.this, s2);
                    }
            }
        }

        public void onMailboxReplyComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l)
        {
            if(l == mCurrentThreadId)
            {
                dismissDialog(1);
                if(MailboxMessagesActivity.responseIsPositive(i, exception))
                {
                    ((EditText)findViewById(0x7f0e00c0)).setText(null);
                } else
                {
                    String s2 = StringUtils.getErrorString(MailboxMessagesActivity.this, getString(0x7f0a00c7), i, s1, exception);
                    Toaster.toast(MailboxMessagesActivity.this, s2);
                }
            }
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(MailboxMessagesActivity.responseIsPositive(i, exception))
                mAdapter.updateUserImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            mAdapter.updateUserImage(profileimage);
        }

        final MailboxMessagesActivity this$0;

        private MessagesAppSessionListener()
        {
            this$0 = MailboxMessagesActivity.this;
            super();
        }

    }

    private final class QueryHandler extends AsyncQueryHandler
    {

        protected void onQueryComplete(int i, Object obj, Cursor cursor)
        {
            if(isFinishing()) goto _L2; else goto _L1
_L1:
            i;
            JVM INSTR tableswitch 1 1: default 28
        //                       1 29;
               goto _L3 _L4
_L3:
            return;
_L4:
            handleQueryComplete(cursor);
            continue; /* Loop/switch isn't completed */
_L2:
            cursor.close();
            if(true) goto _L3; else goto _L5
_L5:
        }

        public static final int QUERY_THREADS_TOKEN = 1;
        final MailboxMessagesActivity this$0;

        public QueryHandler(Context context)
        {
            this$0 = MailboxMessagesActivity.this;
            super(context.getContentResolver());
        }
    }

    private static interface ThreadsQuery
    {

        public static final int INDEX_PARTICIPANTS = 3;
        public static final int INDEX_SUBJECT = 1;
        public static final int INDEX_THREAD_ID = 0;
        public static final int INDEX_UNREAD_COUNT = 2;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "tid";
            as[1] = "subject";
            as[2] = "unread_count";
            as[3] = "participants";
        }
    }


    public MailboxMessagesActivity()
    {
        mScrolledToBottom = false;
    }

    private void handleQueryComplete(Cursor cursor)
    {
        mThreadsCursor = cursor;
        startManagingCursor(cursor);
        moveToCurrentThread();
        View view = findViewById(0x7f0e00bd);
        boolean flag;
        View view1;
        boolean flag1;
        if(!mThreadsCursor.isFirst())
            flag = true;
        else
            flag = false;
        view.setEnabled(flag);
        view1 = findViewById(0x7f0e00be);
        if(!mThreadsCursor.isLast())
            flag1 = true;
        else
            flag1 = false;
        view1.setEnabled(flag1);
        if(mFolder != 4)
            findViewById(0x7f0e00bf).setVisibility(0);
        switchToThread();
    }

    private void handleReply()
    {
        String s = ((EditText)findViewById(0x7f0e00c0)).getText().toString().trim();
        if(s.length() > 0)
        {
            mAppSession.mailboxReply(this, mCurrentThreadId, s);
            showDialog(1);
        }
    }

    private boolean moveToCurrentThread()
    {
        boolean flag = false;
        if(!mThreadsCursor.moveToFirst()) goto _L2; else goto _L1
_L1:
        if(mThreadsCursor.getLong(0) != mCurrentThreadId) goto _L4; else goto _L3
_L3:
        flag = true;
_L2:
        if(!flag)
            Log.e("moveToCurrentThread", (new StringBuilder()).append("Thread id not found: ").append(mCurrentThreadId).toString());
        return flag;
_L4:
        if(mThreadsCursor.moveToNext()) goto _L1; else goto _L2
    }

    private static boolean responseIsPositive(int i, Exception exception)
    {
        boolean flag;
        if(200 == i && exception == null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a00b5);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a00af);
    }

    private void showProgress(boolean flag)
    {
        View view = findViewById(0x7f0e00f1);
        int i;
        if(flag)
            i = 0;
        else
            i = 8;
        view.setVisibility(i);
        if(!flag) goto _L2; else goto _L1
_L1:
        findViewById(0x7f0e0056).setVisibility(8);
        findViewById(0x7f0e0057).setVisibility(0);
_L4:
        return;
_L2:
        findViewById(0x7f0e0056).setVisibility(0);
        findViewById(0x7f0e0057).setVisibility(8);
        if(!mScrolledToBottom)
        {
            getListView().setSelection(mAdapter.getCount() - 1);
            mScrolledToBottom = true;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void switchToThread()
    {
        if(mThreadsCursor.isAfterLast())
        {
            finish();
        } else
        {
            TextView textview = (TextView)findViewById(0x7f0e00bb);
            textview.setText(mThreadsCursor.getString(1));
            int i = mThreadsCursor.getInt(2);
            String s;
            String s1;
            Cursor cursor;
            if(i > 0)
                textview.setTypeface(Typeface.DEFAULT_BOLD);
            else
                textview.setTypeface(Typeface.DEFAULT);
            s = mThreadsCursor.getString(3);
            if(s.length() == 0)
            {
                s1 = getString(0x7f0a00a4);
            } else
            {
                Object aobj[] = new Object[1];
                aobj[0] = s;
                s1 = getString(0x7f0a00a5, aobj);
            }
            ((TextView)findViewById(0x7f0e00bc)).setText(s1);
            cursor = managedQuery(Uri.withAppendedPath(MailboxProvider.getMessagesTidFolderUri(mFolder), (new StringBuilder()).append("").append(mCurrentThreadId).toString()), MailboxMessagesAdapter.MessageQuery.PROJECTION, null, null, null);
            mAdapter.changeCursor(cursor);
            if(!mAppSession.isMailboxGetMessagesPending(mFolder, mCurrentThreadId))
            {
                if(mAdapter.getCount() == 0)
                {
                    AppSession appsession = mAppSession;
                    int j = mFolder;
                    long l = mCurrentThreadId;
                    boolean flag;
                    if(i > 0)
                        flag = true;
                    else
                        flag = false;
                    appsession.mailboxGetThreadMessages(this, j, l, flag);
                    logStepDataRequested();
                    showProgress(true);
                } else
                {
                    showProgress(false);
                }
            } else
            {
                showProgress(true);
            }
        }
    }

    public boolean facebookOnBackPressed()
    {
        EditText edittext = (EditText)findViewById(0x7f0e00c0);
        boolean flag;
        if(edittext.isFocused())
        {
            edittext.clearFocus();
            flag = true;
        } else
        {
            flag = super.facebookOnBackPressed();
        }
        return flag;
    }

    public boolean onContextItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 11 13: default 32
    //                   11 34
    //                   12 34
    //                   13 34;
           goto _L1 _L2 _L2 _L2
_L1:
        return true;
_L2:
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(menuitem.getTitle().toString())));
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030040);
        if(bundle != null)
            mScrolledToBottom = bundle.getBoolean("scrolledToBottom", false);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            mFolder = getIntent().getIntExtra("extra_folder", 0);
            mCurrentThreadId = Long.parseLong((String)getIntent().getData().getPathSegments().get(3));
            mAdapter = new MailboxMessagesAdapter(this, null, mAppSession.getUserImagesCache(), new BaseUserSelectionListener(this));
            getListView().setAdapter(mAdapter);
            EditText edittext = (EditText)findViewById(0x7f0e00c0);
            edittext.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {

                public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent)
                {
                    if(i == 101)
                        handleReply();
                    return false;
                }

                final MailboxMessagesActivity this$0;

            
            {
                this$0 = MailboxMessagesActivity.this;
                super();
            }
            }
);
            edittext.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

                public void onFocusChange(View view2, boolean flag)
                {
                    View view3 = findViewById(0x7f0e00c1);
                    int i;
                    if(flag)
                        i = 0;
                    else
                        i = 8;
                    view3.setVisibility(i);
                }

                final MailboxMessagesActivity this$0;

            
            {
                this$0 = MailboxMessagesActivity.this;
                super();
            }
            }
);
            View view = findViewById(0x7f0e00bd);
            view.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view2)
                {
                    if(mThreadsCursor.getCount() > 0 && mThreadsCursor.moveToPrevious())
                    {
                        mCurrentThreadId = mThreadsCursor.getLong(0);
                        View view3 = findViewById(0x7f0e00bd);
                        boolean flag;
                        View view4;
                        boolean flag1;
                        if(!mThreadsCursor.isFirst())
                            flag = true;
                        else
                            flag = false;
                        view3.setEnabled(flag);
                        view4 = findViewById(0x7f0e00be);
                        if(!mThreadsCursor.isLast())
                            flag1 = true;
                        else
                            flag1 = false;
                        view4.setEnabled(flag1);
                        switchToThread();
                    }
                }

                final MailboxMessagesActivity this$0;

            
            {
                this$0 = MailboxMessagesActivity.this;
                super();
            }
            }
);
            view.setEnabled(false);
            View view1 = findViewById(0x7f0e00be);
            view1.setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view2)
                {
                    if(mThreadsCursor.getCount() > 0 && mThreadsCursor.moveToNext())
                    {
                        mCurrentThreadId = mThreadsCursor.getLong(0);
                        View view3 = findViewById(0x7f0e00bd);
                        boolean flag;
                        View view4;
                        boolean flag1;
                        if(!mThreadsCursor.isFirst())
                            flag = true;
                        else
                            flag = false;
                        view3.setEnabled(flag);
                        view4 = findViewById(0x7f0e00be);
                        if(!mThreadsCursor.isLast())
                            flag1 = true;
                        else
                            flag1 = false;
                        view4.setEnabled(flag1);
                        switchToThread();
                    }
                }

                final MailboxMessagesActivity this$0;

            
            {
                this$0 = MailboxMessagesActivity.this;
                super();
            }
            }
);
            view1.setEnabled(false);
            findViewById(0x7f0e00bf).setVisibility(8);
            ((Button)findViewById(0x7f0e0077)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view2)
                {
                    handleReply();
                    EditText edittext1 = (EditText)findViewById(0x7f0e00c0);
                    ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(edittext1.getWindowToken(), 0);
                }

                final MailboxMessagesActivity this$0;

            
            {
                this$0 = MailboxMessagesActivity.this;
                super();
            }
            }
);
            ((Button)findViewById(0x7f0e00c2)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view2)
                {
                    ((EditText)findViewById(0x7f0e00c0)).setText(null);
                }

                final MailboxMessagesActivity this$0;

            
            {
                this$0 = MailboxMessagesActivity.this;
                super();
            }
            }
);
            mQueryHandler = new QueryHandler(this);
            mAppSessionListener = new MessagesAppSessionListener();
            setupEmptyView();
            getListView().setOnCreateContextMenuListener(this);
        }
    }

    public void onCreateContextMenu(ContextMenu contextmenu, View view, android.view.ContextMenu.ContextMenuInfo contextmenuinfo)
    {
        android.widget.AdapterView.AdapterContextMenuInfo adaptercontextmenuinfo = (android.widget.AdapterView.AdapterContextMenuInfo)contextmenuinfo;
        int i;
        Iterator iterator;
        Cursor cursor = (Cursor)mAdapter.getItem(adaptercontextmenuinfo.position);
        ArrayList arraylist = new ArrayList();
        StringUtils.parseExpression(cursor.getString(3), "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*", null, arraylist, 3);
        i = 0;
        iterator = arraylist.iterator();
_L6:
        String s;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        s = (String)iterator.next();
        i;
        JVM INSTR tableswitch 0 2: default 112
    //                   0 121
    //                   1 137
    //                   2 153;
           goto _L1 _L2 _L3 _L4
_L1:
        break; /* Loop/switch isn't completed */
_L4:
        break MISSING_BLOCK_LABEL_153;
_L7:
        i++;
        if(true) goto _L6; else goto _L5
        ClassCastException classcastexception;
        classcastexception;
_L5:
        return;
_L2:
        contextmenu.add(0, 11, 0, s);
          goto _L7
_L3:
        contextmenu.add(0, 12, 0, s);
          goto _L7
        contextmenu.add(0, 13, 0, s);
          goto _L7
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
        progressdialog1.setMessage(getText(0x7f0a00bd));
        progressdialog1.setIndeterminate(true);
        progressdialog1.setCancelable(false);
        obj = progressdialog1;
        continue; /* Loop/switch isn't completed */
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a00aa));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 2, 0, 0x7f0a00b1).setIcon(0x7f0200b2);
        menu.add(0, 3, 0, 0x7f0a00b2).setIcon(0x7f0200b3);
        menu.add(0, 4, 0, 0x7f0a00a8).setIcon(0x108003c);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 4: default 32
    //                   2 38
    //                   3 70
    //                   4 99;
           goto _L1 _L2 _L3 _L4
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        long al2[] = new long[1];
        al2[0] = mCurrentThreadId;
        mAppSession.mailboxMarkThread(this, mFolder, al2, true);
        continue; /* Loop/switch isn't completed */
_L3:
        long al1[] = new long[1];
        al1[0] = mCurrentThreadId;
        mAppSession.mailboxMarkThread(this, mFolder, al1, false);
        continue; /* Loop/switch isn't completed */
_L4:
        long al[] = new long[1];
        al[0] = mCurrentThreadId;
        mDeleteThreadReqId = mAppSession.mailboxDeleteThread(this, al, mFolder);
        showDialog(2);
        if(true) goto _L1; else goto _L5
_L5:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        boolean flag = false;
        super.onPrepareOptionsMenu(menu);
        boolean flag1;
        if(mThreadsCursor != null)
        {
            boolean flag2;
            MenuItem menuitem;
            if(mThreadsCursor.getInt(2) == 0)
                flag2 = true;
            else
                flag2 = flag;
            menuitem = menu.findItem(2);
            if(!flag2)
                flag = true;
            menuitem.setVisible(flag);
            menu.findItem(3).setVisible(flag2);
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        return flag1;
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
                removeDialog(2);
                mDeleteThreadReqId = null;
            }
            mAppSession.addListener(mAppSessionListener);
            mQueryHandler.startQuery(1, null, MailboxProvider.getThreadsFolderUri(mFolder), ThreadsQuery.PROJECTION, null, null, null);
        }
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("scrolledToBottom", mScrolledToBottom);
    }

    private static final int DELETE_ID = 4;
    public static final String EXTRA_FOLDER = "extra_folder";
    private static final int MARK_AS_READ_ID = 2;
    private static final int MARK_AS_UNREAD_ID = 3;
    private static final int PROGRESS_DELETE_THREAD_DIALOG = 2;
    private static final int PROGRESS_REPLY_DIALOG = 1;
    private static final String SCROLLED_STATE_KEY = "scrolledToBottom";
    private static final int VIEW_URL_0_ID = 11;
    private static final int VIEW_URL_1_ID = 12;
    private static final int VIEW_URL_2_ID = 13;
    private MailboxMessagesAdapter mAdapter;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private long mCurrentThreadId;
    private String mDeleteThreadReqId;
    private int mFolder;
    private QueryHandler mQueryHandler;
    private boolean mScrolledToBottom;
    private Cursor mThreadsCursor;





/*
    static String access$1102(MailboxMessagesActivity mailboxmessagesactivity, String s)
    {
        mailboxmessagesactivity.mDeleteThreadReqId = s;
        return s;
    }

*/





/*
    static long access$302(MailboxMessagesActivity mailboxmessagesactivity, long l)
    {
        mailboxmessagesactivity.mCurrentThreadId = l;
        return l;
    }

*/





}

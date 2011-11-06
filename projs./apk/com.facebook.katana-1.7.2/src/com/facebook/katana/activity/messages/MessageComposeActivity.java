// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageComposeActivity.java

package com.facebook.katana.activity.messages;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import com.facebook.katana.*;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.service.method.FqlGetProfile;
import com.facebook.katana.ui.MailAutoCompleteTextView;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.*;

public class MessageComposeActivity extends BaseFacebookActivity
{
    private class ComposeAppSessionListener extends AppSessionListener
    {

        public void onGetProfileComplete(AppSession appsession, String s, int i, String s1, Exception exception, FacebookProfile facebookprofile)
        {
            if(facebookprofile != null)
                addRecipient(facebookprofile);
        }

        public void onMailboxSendComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            removeDialog(1);
            mSendReqId = null;
            if(i == 200)
            {
                setResult(-1);
                finish();
            } else
            {
                String s2 = StringUtils.getErrorString(MessageComposeActivity.this, getString(0x7f0a00c7), i, s1, exception);
                Toaster.toast(MessageComposeActivity.this, s2);
            }
        }

        public void onProfileImageDownloaded(AppSession appsession, String s, int i, String s1, Exception exception, ProfileImage profileimage, ProfileImagesCache profileimagescache)
        {
            if(i == 200)
                updateUserViewImage(profileimage);
        }

        public void onProfileImageLoaded(AppSession appsession, ProfileImage profileimage)
        {
            updateUserViewImage(profileimage);
        }

        final MessageComposeActivity this$0;

        private ComposeAppSessionListener()
        {
            this$0 = MessageComposeActivity.this;
            super();
        }

    }


    public MessageComposeActivity()
    {
    }

    private void addRecipient(FacebookProfile facebookprofile)
    {
        long l;
        Iterator iterator;
        l = facebookprofile.mId;
        iterator = mRecipients.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        if(((FacebookProfile)iterator.next()).mId != l) goto _L4; else goto _L3
_L3:
        return;
_L2:
        mRecipients.add(facebookprofile);
        View view = ((LayoutInflater)getSystemService("layout_inflater")).inflate(0x7f030088, null);
        view.setTag(facebookprofile);
        ViewHolder viewholder = new ViewHolder(view, 0x7f0e001a);
        viewholder.setItemId(Long.valueOf(l));
        mViewHolders.add(viewholder);
        String s = facebookprofile.mImageUrl;
        if(s != null)
        {
            android.graphics.Bitmap bitmap = mAppSession.getUserImagesCache().get(this, l, s);
            String s1;
            View view1;
            if(bitmap != null)
                viewholder.mImageView.setImageBitmap(bitmap);
            else
                viewholder.mImageView.setImageResource(0x7f0200f3);
        } else
        {
            viewholder.mImageView.setImageResource(0x7f0200f3);
        }
        s1 = facebookprofile.mDisplayName;
        if(s1 == null)
            s1 = getString(0x7f0a0067);
        ((TextView)view.findViewById(0x7f0e001c)).setText(s1);
        view1 = view.findViewById(0x7f0e0148);
        view1.setOnClickListener(mRemoveButtonListener);
        view1.setTag(facebookprofile);
        mRecipientsContainer.addView(view, 0);
        if(true) goto _L3; else goto _L5
_L5:
    }

    private void removeRecipient(FacebookProfile facebookprofile)
    {
        View view = mRecipientsContainer.findViewWithTag(facebookprofile);
        mRecipientsContainer.removeView(view);
        mRecipients.remove(facebookprofile);
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            Long long1 = (Long)viewholder.getItemId();
            if(long1 == null || !long1.equals(Long.valueOf(facebookprofile.mId)))
                continue;
            mViewHolders.remove(viewholder);
            break;
        } while(true);
        mRecipientsContainer.requestLayout();
    }

    private void send()
    {
        if(mRecipients.size() != 0) goto _L2; else goto _L1
_L1:
        Toaster.toast(this, 0x7f0a00c3);
_L4:
        return;
_L2:
        CharSequence charsequence = ((TextView)findViewById(0x7f0e00cd)).getText();
        CharSequence charsequence1 = ((TextView)findViewById(0x7f0e00ce)).getText();
        if(charsequence1.length() > 0)
        {
            mSendReqId = mAppSession.mailboxSend(this, mRecipients, charsequence.toString(), charsequence1.toString());
            showDialog(1);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void updateUserViewImage(ProfileImage profileimage)
    {
        Iterator iterator = mViewHolders.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ViewHolder viewholder = (ViewHolder)iterator.next();
            Long long1 = (Long)viewholder.getItemId();
            if(long1 == null || !long1.equals(Long.valueOf(profileimage.id)))
                continue;
            viewholder.mImageView.setImageBitmap(profileimage.getBitmap());
            break;
        } while(true);
    }

    public boolean facebookOnBackPressed()
    {
        if(((TextView)findViewById(0x7f0e00cb)).getText().length() > 0)
            showDialog(2);
        else
        if(((TextView)findViewById(0x7f0e00cd)).getText().length() > 0)
            showDialog(2);
        else
        if(((TextView)findViewById(0x7f0e00ce)).getText().length() > 0)
        {
            showDialog(2);
        } else
        {
            setResult(0);
            finish();
        }
        return true;
    }

    protected void onActivityResult(int i, int j, Intent intent)
    {
        super.onActivityResult(i, j, intent);
        if(j != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Iterator iterator;
        switch(i)
        {
        default:
            break;

        case 1: // '\001'
            iterator = (new ArrayList(mRecipients)).iterator();
            break; /* Loop/switch isn't completed */
        }
        if(true) goto _L1; else goto _L3
_L3:
        for(; iterator.hasNext(); removeRecipient((FacebookProfile)iterator.next()));
        mRecipients.clear();
        Iterator iterator1 = intent.getParcelableArrayListExtra("com.facebook.katana.PickFriendsActivity.result_friends").iterator();
        while(iterator1.hasNext()) 
            addRecipient((FacebookProfile)iterator1.next());
        if(true) goto _L1; else goto _L4
_L4:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030045);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession != null) goto _L2; else goto _L1
_L1:
        LoginActivity.toLogin(this);
_L4:
        return;
_L2:
        final MailAutoCompleteTextView userName = (MailAutoCompleteTextView)findViewById(0x7f0e00cb);
        mDropdownAdapter = new DropdownFriendsAdapter(this, null, mAppSession.getUserImagesCache());
        userName.setAdapter(mDropdownAdapter);
        userName.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                Cursor cursor = (Cursor)mDropdownAdapter.getItem(i);
                long l1 = cursor.getLong(1);
                String s = cursor.getString(2);
                String s1 = cursor.getString(3);
                addRecipient(new FacebookProfile(l1, s, s1, 0));
                userName.setText(null);
            }

            final MessageComposeActivity this$0;
            final MailAutoCompleteTextView val$userName;

            
            {
                this$0 = MessageComposeActivity.this;
                userName = mailautocompletetextview;
                super();
            }
        }
);
        userName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable editable)
            {
            }

            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k)
            {
            }

            public void onTextChanged(CharSequence charsequence, int i, int j, int k)
            {
                if(charsequence.length() == 1)
                {
                    Uri uri = Uri.withAppendedPath(ConnectionsProvider.FRIENDS_SEARCH_CONTENT_URI, Uri.encode(charsequence.toString()));
                    Cursor cursor = managedQuery(uri, com.facebook.katana.DropdownFriendsAdapter.FriendsQuery.PROJECTION, null, null, null);
                    mDropdownAdapter.changeCursor(cursor);
                }
            }

            final MessageComposeActivity this$0;

            
            {
                this$0 = MessageComposeActivity.this;
                super();
            }
        }
);
        findViewById(0x7f0e00cf).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                send();
            }

            final MessageComposeActivity this$0;

            
            {
                this$0 = MessageComposeActivity.this;
                super();
            }
        }
);
        findViewById(0x7f0e00d0).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                setResult(0);
                finish();
            }

            final MessageComposeActivity this$0;

            
            {
                this$0 = MessageComposeActivity.this;
                super();
            }
        }
);
        android.view.View.OnClickListener onclicklistener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(MessageComposeActivity.this, com/facebook/katana/PickFriendsActivity);
                intent.setAction("android.intent.action.PICK");
                intent.putExtra("com.facebook.katana.PickFriendsActivity.initial_friends", mRecipients);
                startActivityForResult(intent, 1);
            }

            final MessageComposeActivity this$0;

            
            {
                this$0 = MessageComposeActivity.this;
                super();
            }
        }
;
        findViewById(0x7f0e00ca).setOnClickListener(onclicklistener);
        mRemoveButtonListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                removeRecipient((FacebookProfile)view.getTag());
            }

            final MessageComposeActivity this$0;

            
            {
                this$0 = MessageComposeActivity.this;
                super();
            }
        }
;
        mRecipientsContainer = (ViewGroup)findViewById(0x7f0e00cc);
        List list = (List)getLastNonConfigurationInstance();
        if(list != null)
        {
            for(Iterator iterator = list.iterator(); iterator.hasNext(); addRecipient((FacebookProfile)iterator.next()));
        }
        mAppSessionListeners = new ArrayList();
        mAppSessionListeners.add(mDropdownAdapter.appSessionListener);
        mAppSessionListeners.add(new ComposeAppSessionListener());
        Long long1 = Long.valueOf(getIntent().getLongExtra("extra_user_id", -1L));
        if(long1.longValue() != -1L)
        {
            FacebookProfile facebookprofile = ConnectionsProvider.getFriendProfileFromId(this, long1.longValue());
            if(facebookprofile != null)
                addRecipient(facebookprofile);
            else
                FqlGetProfile.RequestSingleProfile(this, long1.longValue());
            findViewById(0x7f0e00cd).requestFocus();
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 28
    //                   2 68;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L5:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a00bd));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L3:
        obj = AlertDialogs.createAlert(this, getString(0x7f0a00c5), 0x1080027, getString(0x7f0a00c4), getString(0x7f0a0233), new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                setResult(0);
                finish();
            }

            final MessageComposeActivity this$0;

            
            {
                this$0 = MessageComposeActivity.this;
                super();
            }
        }
, getString(0x7f0a00d4), null, null, true);
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected void onPause()
    {
        super.onPause();
        if(mAppSession != null)
        {
            AppSessionListener appsessionlistener;
            for(Iterator iterator = mAppSessionListeners.iterator(); iterator.hasNext(); mAppSession.removeListener(appsessionlistener))
                appsessionlistener = (AppSessionListener)iterator.next();

        }
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
            if(mSendReqId != null && !mAppSession.isRequestPending(mSendReqId))
            {
                removeDialog(1);
                mSendReqId = null;
            }
            Iterator iterator = mAppSessionListeners.iterator();
            while(iterator.hasNext()) 
            {
                AppSessionListener appsessionlistener = (AppSessionListener)iterator.next();
                mAppSession.addListener(appsessionlistener);
            }
        }
    }

    public Object onRetainNonConfigurationInstance()
    {
        return mRecipients;
    }

    public static final String EXTRA_SUBJECT = "extra_subject";
    private static final int PROGRESS_SEND_DIALOG = 1;
    private static final int QUIT_DIALOG = 2;
    private static final int REQUEST_CODE_PICK_FRIEND = 1;
    private AppSession mAppSession;
    private List mAppSessionListeners;
    private DropdownFriendsAdapter mDropdownAdapter;
    private final ArrayList mRecipients = new ArrayList();
    private ViewGroup mRecipientsContainer;
    private android.view.View.OnClickListener mRemoveButtonListener;
    private String mSendReqId;
    private final List mViewHolders = new ArrayList();







/*
    static String access$602(MessageComposeActivity messagecomposeactivity, String s)
    {
        messagecomposeactivity.mSendReqId = s;
        return s;
    }

*/

}

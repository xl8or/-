// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserInfoActivity.java

package com.facebook.katana;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.dialog.Dialogs;
import com.facebook.katana.model.FacebookUserFull;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.Toaster;
import java.util.List;

// Referenced classes of package com.facebook.katana:
//            ProfileInfoActivity, UserInfoAdapter, LoginActivity, ProfileInfoAdapter

public class UserInfoActivity extends ProfileInfoActivity
    implements android.widget.AdapterView.OnItemClickListener
{
    private class UserInfoAppSessionListener extends ProfileInfoActivity.InfoAppSessionListener
    {

        public void onFriendsAddFriendComplete(AppSession appsession, String s, int i, String s1, Exception exception, List list)
        {
            removeDialog(2);
            mReqId = null;
        }

        public void onUsersGetInfoComplete(AppSession appsession, String s, int i, String s1, Exception exception, long l, 
                FacebookUserFull facebookuserfull, boolean flag)
        {
            if(l == mUserId)
            {
                showProgress(false);
                if(i == 200)
                {
                    logStepDataReceived();
                    if(facebookuserfull != null)
                    {
                        handleInfo(facebookuserfull, flag);
                    } else
                    {
                        Toaster.toast(UserInfoActivity.this, 0x7f0a015b);
                        finish();
                    }
                } else
                {
                    String s2 = StringUtils.getErrorString(UserInfoActivity.this, getString(0x7f0a014f), i, s1, exception);
                    Toaster.toast(UserInfoActivity.this, s2);
                }
            }
        }

        final UserInfoActivity this$0;

        private UserInfoAppSessionListener()
        {
            this$0 = UserInfoActivity.this;
            super(UserInfoActivity.this);
        }

    }


    public UserInfoActivity()
    {
    }

    private void handleInfo(FacebookUserFull facebookuserfull, boolean flag)
    {
        ((UserInfoAdapter)mAdapter).setUserInfo(facebookuserfull, flag);
        mInfo = facebookuserfull;
        if(!flag) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ViewStub viewstub = (ViewStub)findViewById(0x7f0e0103);
        if(viewstub != null)
        {
            viewstub.inflate();
            findViewById(0x7f0e0152).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    showDialog(1);
                }

                final UserInfoActivity this$0;

            
            {
                this$0 = UserInfoActivity.this;
                super();
            }
            }
);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private void setupEmptyView()
    {
        ((TextView)findViewById(0x7f0e0056)).setText(0x7f0a015a);
        ((TextView)findViewById(0x7f0e0058)).setText(0x7f0a0157);
    }

    public void onCreate(Bundle bundle)
    {
        if(getIntent().getBooleanExtra("within_tab", false))
            mHasFatTitleHeader = true;
        super.onCreate(bundle);
        setContentView(0x7f030062);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            if(getParent() != null)
                findViewById(0x7f0e0016).setVisibility(8);
            mUserId = getIntent().getLongExtra("com.facebook.katana.profile.id", 0L);
            setupListHeaders();
            setupFatTitleHeader();
            mAppSessionListener = new UserInfoAppSessionListener();
            mAdapter = new UserInfoAdapter(this, mAppSession.getPhotosCache(), getIntent().getBooleanExtra("com.facebook.katana.profile.show_photo", true), getIntent().getBooleanExtra("com.facebook.katana.profile.is.limited", false));
            ListView listview = getListView();
            listview.setAdapter(mAdapter);
            setupEmptyView();
            listview.setOnItemClickListener(this);
        }
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 2: default 24
    //                   1 28
    //                   2 48;
           goto _L1 _L2 _L3
_L1:
        Object obj = null;
_L5:
        return ((Dialog) (obj));
_L2:
        obj = Dialogs.addFriend(this, mUserId, new com.facebook.katana.dialog.Dialogs.AddFriendListener() {

            public void onAddFriendStart(String s)
            {
                mReqId = s;
                showDialog(2);
            }

            final UserInfoActivity this$0;

            
            {
                this$0 = UserInfoActivity.this;
                super();
            }
        }
);
        continue; /* Loop/switch isn't completed */
_L3:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a0147));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void onItemClick(AdapterView adapterview, View view, int i, long l)
    {
        ProfileInfoAdapter.Item item = mAdapter.getItemByPosition(getCursorPosition(i));
        item.getType();
        JVM INSTR tableswitch 0 4: default 52
    //                   0 53
    //                   1 52
    //                   2 95
    //                   3 135
    //                   4 198;
           goto _L1 _L2 _L1 _L3 _L4 _L5
_L1:
        return;
_L2:
        if(mAppSession != null && mInfo != null)
        {
            String s = mInfo.mUrl;
            if(s != null)
                mAppSession.openURL(this, s);
        }
        continue; /* Loop/switch isn't completed */
_L3:
        Intent intent1 = new Intent("android.intent.action.DIAL", Uri.fromParts("tel", item.getSubTitle(), null));
        intent1.setFlags(0x10000000);
        startActivity(intent1);
        continue; /* Loop/switch isn't completed */
_L4:
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:"));
        String as[] = new String[1];
        as[0] = item.getSubTitle();
        intent.putExtra("android.intent.extra.EMAIL", as);
        startActivity(Intent.createChooser(intent, getString(0x7f0a014e)));
        continue; /* Loop/switch isn't completed */
_L5:
        ApplicationUtils.OpenUserProfile(this, item.getTargetId(), null);
        if(true) goto _L1; else goto _L6
_L6:
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 2 2: default 24
    //                   2 30;
           goto _L1 _L2
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        mAppSession.openURL(this, mInfo.mUrl);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuitem = menu.findItem(2);
        boolean flag;
        if(mInfo != null && mInfo.mUrl != null)
            flag = true;
        else
            flag = false;
        menuitem.setEnabled(flag);
        return true;
    }

    public void onResume()
    {
        super.onResume();
        if(mInfo == null)
        {
            mAppSession.usersGetInfo(this, mUserId);
            logStepDataRequested();
            showProgress(true);
        }
    }

    private static final int VIEW_PROFILE = 2;
    private FacebookUserFull mInfo;
    private long mUserId;



}

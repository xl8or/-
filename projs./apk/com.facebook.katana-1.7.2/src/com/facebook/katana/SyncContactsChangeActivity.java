// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SyncContactsChangeActivity.java

package com.facebook.katana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.platform.FacebookAuthenticationService;

// Referenced classes of package com.facebook.katana:
//            RemoveRawContactsService, LoginActivity, UserTask

public class SyncContactsChangeActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener
{
    private class ReadAccountUserTask extends UserTask
    {

        protected void doInBackground()
        {
            mSyncContacts = FacebookAuthenticationService.isSyncEnabled(SyncContactsChangeActivity.this, mUsername);
            mShowUngroupedContacts = FacebookAuthenticationService.doesShowUngroupedContacts(SyncContactsChangeActivity.this, mUsername);
        }

        protected void onPostExecute()
        {
            if(!isFinishing()) goto _L2; else goto _L1
_L1:
            return;
_L2:
            int i;
            if(mSyncContacts)
            {
                if(mShowUngroupedContacts)
                    i = 0x7f0e0137;
                else
                    i = 0x7f0e013a;
            } else
            {
                i = 0x7f0e013d;
            }
            checkRadioButton(i);
            if(i != 0x7f0e013d)
            {
                ((TextView)findViewById(0x7f0e013e)).setText(getString(0x7f0a01e1));
                ((TextView)findViewById(0x7f0e013f)).setText(getString(0x7f0a01e2));
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        private final String mUsername;
        final SyncContactsChangeActivity this$0;

        public ReadAccountUserTask()
        {
            this$0 = SyncContactsChangeActivity.this;
            super(new Handler());
            mUsername = mAppSession.getSessionInfo().username;
        }
    }


    public SyncContactsChangeActivity()
    {
    }

    private void checkRadioButton(int i)
    {
        RadioButton radiobutton = (RadioButton)findViewById(0x7f0e0137);
        boolean flag;
        RadioButton radiobutton1;
        boolean flag1;
        RadioButton radiobutton2;
        boolean flag2;
        if(i == 0x7f0e0137)
            flag = true;
        else
            flag = false;
        radiobutton.setChecked(flag);
        radiobutton1 = (RadioButton)findViewById(0x7f0e013a);
        if(i == 0x7f0e013a)
            flag1 = true;
        else
            flag1 = false;
        radiobutton1.setChecked(flag1);
        radiobutton2 = (RadioButton)findViewById(0x7f0e013d);
        if(i == 0x7f0e013d)
            flag2 = true;
        else
            flag2 = false;
        radiobutton2.setChecked(flag2);
        mCheckedId = i;
    }

    private void saveSettings()
    {
        boolean flag;
        boolean flag1;
        flag = false;
        flag1 = false;
        mCheckedId;
        JVM INSTR lookupswitch 3: default 44
    //                   2131624247: 87
    //                   2131624250: 94
    //                   2131624253: 44;
           goto _L1 _L2 _L3 _L1
_L1:
        break; /* Loop/switch isn't completed */
_L3:
        break MISSING_BLOCK_LABEL_94;
_L4:
        FacebookAuthenticationService.storeSessionInfo(this, mAppSession.getSessionInfo().username, flag, flag1);
        if(mSyncContacts && !flag)
            startService(new Intent(this, com/facebook/katana/RemoveRawContactsService));
        else
        if(!mSyncContacts && flag)
            mAppSession.syncFriends(this);
        return;
_L2:
        flag = true;
        flag1 = true;
          goto _L4
        flag = true;
          goto _L4
    }

    private void setupFatTitleBar()
    {
        ((TextView)findViewById(0x7f0e0071)).setText(0x7f0a01ea);
        ((TextView)findViewById(0x7f0e0072)).setText(0x7f0a01e9);
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR lookupswitch 5: default 56
    //                   2131624100: 84
    //                   2131624243: 95
    //                   2131624246: 57
    //                   2131624249: 66
    //                   2131624252: 75;
           goto _L1 _L2 _L3 _L4 _L5 _L6
_L1:
        return;
_L4:
        checkRadioButton(0x7f0e0137);
        continue; /* Loop/switch isn't completed */
_L5:
        checkRadioButton(0x7f0e013a);
        continue; /* Loop/switch isn't completed */
_L6:
        checkRadioButton(0x7f0e013d);
        continue; /* Loop/switch isn't completed */
_L2:
        saveSettings();
        finish();
        continue; /* Loop/switch isn't completed */
_L3:
        finish();
        if(true) goto _L1; else goto _L7
_L7:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03007c);
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null)
        {
            LoginActivity.toLogin(this);
        } else
        {
            setupFatTitleBar();
            ((Button)findViewById(0x7f0e00a4)).setOnClickListener(this);
            ((Button)findViewById(0x7f0e0133)).setOnClickListener(this);
            findViewById(0x7f0e0136).setOnClickListener(this);
            findViewById(0x7f0e0139).setOnClickListener(this);
            findViewById(0x7f0e013c).setOnClickListener(this);
            findViewById(0x7f0e0140).setVisibility(8);
            (new ReadAccountUserTask()).execute();
        }
    }

    private AppSession mAppSession;
    private int mCheckedId;
    private boolean mShowUngroupedContacts;
    private boolean mSyncContacts;




/*
    static boolean access$102(SyncContactsChangeActivity synccontactschangeactivity, boolean flag)
    {
        synccontactschangeactivity.mSyncContacts = flag;
        return flag;
    }

*/



/*
    static boolean access$202(SyncContactsChangeActivity synccontactschangeactivity, boolean flag)
    {
        synccontactschangeactivity.mShowUngroupedContacts = flag;
        return flag;
    }

*/

}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SyncContactsSetupActivity.java

package com.facebook.katana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.EclairKeyHandler;
import com.facebook.katana.util.PlatformUtils;

// Referenced classes of package com.facebook.katana:
//            UserTask

public class SyncContactsSetupActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener
{
    private class AddAccountUserTask extends UserTask
    {

        protected void doInBackground()
        {
            FacebookAuthenticationService.storeSessionInfo(SyncContactsSetupActivity.this, mUsername, mSyncFriends, mShowUngroupedContacts);
            if(mAddAccountMode)
                FacebookAuthenticationService.addAccountComplete(getIntent(), mUsername);
        }

        protected void onPostExecute()
        {
            mAppSession.syncFriends(SyncContactsSetupActivity.this);
        }

        private final AppSession mAppSession;
        private final boolean mShowUngroupedContacts;
        private final boolean mSyncFriends;
        private final String mUsername;
        final SyncContactsSetupActivity this$0;

        public AddAccountUserTask(AppSession appsession, boolean flag, boolean flag1)
        {
            this$0 = SyncContactsSetupActivity.this;
            super(new Handler());
            mAppSession = appsession;
            mUsername = appsession.getSessionInfo().username;
            mSyncFriends = flag;
            mShowUngroupedContacts = flag1;
        }
    }


    public SyncContactsSetupActivity()
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

    private boolean onBackKeyPressed()
    {
        return true;
    }

    private void saveSettings(AppSession appsession)
    {
        boolean flag;
        boolean flag1;
        flag = false;
        flag1 = false;
        mCheckedId;
        JVM INSTR lookupswitch 3: default 44
    //                   2131624247: 59
    //                   2131624250: 66
    //                   2131624253: 44;
           goto _L1 _L2 _L3 _L1
_L1:
        (new AddAccountUserTask(appsession, flag, flag1)).execute();
        return;
_L2:
        flag = true;
        flag1 = true;
        continue; /* Loop/switch isn't completed */
_L3:
        flag = true;
        if(true) goto _L1; else goto _L4
_L4:
    }

    private void setupFatTitleBar()
    {
        ((TextView)findViewById(0x7f0e0071)).setText(0x7f0a01ea);
        ((TextView)findViewById(0x7f0e0072)).setText(0x7f0a01e9);
    }

    private void startDefaultActivity()
    {
        ApplicationUtils.startDefaultActivity(this, (Intent)getIntent().getParcelableExtra("com.facebook.katana.continuation_intent"));
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR tableswitch 2131624245 2131624252: default 52
    //                   2131624245 80
    //                   2131624246 53
    //                   2131624247 52
    //                   2131624248 52
    //                   2131624249 62
    //                   2131624250 52
    //                   2131624251 52
    //                   2131624252 71;
           goto _L1 _L2 _L3 _L1 _L1 _L4 _L1 _L1 _L5
_L1:
        return;
_L3:
        checkRadioButton(0x7f0e0137);
        continue; /* Loop/switch isn't completed */
_L4:
        checkRadioButton(0x7f0e013a);
        continue; /* Loop/switch isn't completed */
_L5:
        checkRadioButton(0x7f0e013d);
        continue; /* Loop/switch isn't completed */
_L2:
        UserValuesManager.setContactsSyncSetupDisplayed(this, true);
        if(PlatformUtils.platformStorageSupported(this))
        {
            AppSession appsession = AppSession.getActiveSession(this, false);
            if(appsession != null)
                saveSettings(appsession);
            if(!mAddAccountMode)
                startDefaultActivity();
        } else
        {
            startDefaultActivity();
        }
        finish();
        if(true) goto _L1; else goto _L6
_L6:
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f03007d);
        mAddAccountMode = getIntent().getBooleanExtra("add_account", false);
        setupFatTitleBar();
        checkRadioButton(0x7f0e013a);
        findViewById(0x7f0e0135).setOnClickListener(this);
        findViewById(0x7f0e0136).setOnClickListener(this);
        findViewById(0x7f0e0139).setOnClickListener(this);
        findViewById(0x7f0e013c).setOnClickListener(this);
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if(i != 4) goto _L2; else goto _L1
_L1:
        if(!PlatformUtils.isEclairOrLater()) goto _L4; else goto _L3
_L3:
        if(!EclairKeyHandler.onKeyDown(keyevent)) goto _L2; else goto _L5
_L5:
        boolean flag = true;
_L7:
        return flag;
_L4:
        if(onBackKeyPressed())
        {
            flag = true;
            continue; /* Loop/switch isn't completed */
        }
_L2:
        flag = super.onKeyDown(i, keyevent);
        if(true) goto _L7; else goto _L6
_L6:
    }

    public boolean onKeyUp(int i, KeyEvent keyevent)
    {
        boolean flag;
        if(i == 4 && PlatformUtils.isEclairOrLater() && EclairKeyHandler.onKeyUp(keyevent) && onBackKeyPressed())
            flag = true;
        else
            flag = super.onKeyUp(i, keyevent);
        return flag;
    }

    private boolean mAddAccountMode;
    private int mCheckedId;

}

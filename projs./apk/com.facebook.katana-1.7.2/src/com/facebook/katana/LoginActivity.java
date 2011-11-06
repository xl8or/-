// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoginActivity.java

package com.facebook.katana;

import android.app.*;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageButton;
import com.facebook.katana.activity.BaseFacebookActivity;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.ApplicationUtils;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.RingtoneUtils;
import com.facebook.katana.util.Toaster;
import com.facebook.katana.util.Utils;
import java.io.IOException;

// Referenced classes of package com.facebook.katana:
//            C2DMReceiver, SyncContactsSetupActivity, VersionTasks, AlertDialogs, 
//            HtmlAboutActivity

public class LoginActivity extends BaseFacebookActivity
    implements android.view.View.OnClickListener
{
    private class LoginAppSessionListener extends AppSessionListener
    {

        public void onLoginComplete(AppSession appsession, String s, int i, String s1, Exception exception)
        {
            removeDialog(1);
            if(i != 200) goto _L2; else goto _L1
_L1:
            startNextActivity();
            finish();
_L4:
            return;
_L2:
            appsession.removeListener(mAppSessionListener);
            mAppSession = null;
            if(i == 0)
                if(exception instanceof FacebookApiException)
                {
                    i = ((FacebookApiException)exception).getErrorCode();
                    ((FacebookApiException)exception).getErrorMsg();
                } else
                {
                    exception.getMessage();
                }
            switch(i)
            {
            default:
                mErrorMessage = getString(0x7f0a0093);
                if(mForeground)
                    showDialog(2);
                break;

            case 401: 
                mErrorMessage = getString(0x7f0a0092);
                if(mForeground)
                    showDialog(2);
                break;

            case 406: 
                mErrorMessage = getString(0x7f0a00a3);
                if(mForeground)
                {
                    showDialog(3);
                    ((EditText)findViewById(0x7f0e00b4)).setText("");
                }
                break;
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        final LoginActivity this$0;

        private LoginAppSessionListener()
        {
            this$0 = LoginActivity.this;
            super();
        }

    }


    public LoginActivity()
    {
        mForeground = false;
    }

    private void login()
    {
        String s = ((EditText)findViewById(0x7f0e00b3)).getText().toString();
        if(s.length() != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s1 = ((EditText)findViewById(0x7f0e00b4)).getText().toString();
        if(s1.length() != 0)
        {
            showDialog(1);
            mAppSession = new AppSession();
            mAppSession.addListener(mAppSessionListener);
            mAppSession.authLogin(this, s.toLowerCase(), s1, false);
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public static Intent loginIntent(Context context)
    {
        Intent intent = new Intent(context, com/facebook/katana/LoginActivity);
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(0x4000000);
        return intent;
    }

    public static void redirectThroughLogin(Activity activity)
    {
        Intent intent = loginIntent(activity);
        intent.putExtra("login_redirect", true);
        activity.startActivityForResult(intent, 2210);
    }

    private void startNextActivity()
    {
        Utils.updateErrorReportingUserId(this);
        if(getIntent().getBooleanExtra("login_redirect", false))
        {
            setResult(-1);
            finish();
        } else
        {
            C2DMReceiver.getClientLogin(this);
            if(PlatformUtils.platformStorageSupported(this) && !UserValuesManager.getContactsSyncSetupDisplayed(this))
            {
                Intent intent = new Intent(this, com/facebook/katana/SyncContactsSetupActivity);
                intent.putExtra("add_account", mAddAccountMode);
                if(mAddAccountMode)
                {
                    FacebookAuthenticationService.copyCallback(getIntent(), intent);
                    mAddAccountHandled = true;
                }
                Intent intent1 = (Intent)getIntent().getParcelableExtra("com.facebook.katana.continuation_intent");
                if(intent1 != null)
                    intent.putExtra("com.facebook.katana.continuation_intent", intent1);
                startActivity(intent);
            } else
            {
                ApplicationUtils.startDefaultActivity(this, (Intent)getIntent().getParcelableExtra("com.facebook.katana.continuation_intent"));
            }
        }
    }

    public static void toLogin(Activity activity)
    {
        activity.startActivity(loginIntent(activity));
        activity.finish();
    }

    public static void toLogin(Activity activity, Intent intent)
    {
        Intent intent1 = loginIntent(activity);
        if(intent != null)
            intent1.putExtra("com.facebook.katana.continuation_intent", intent);
        activity.startActivity(intent1);
        activity.finish();
    }

    public boolean facebookOnBackPressed()
    {
        setTransitioningToBackground(true);
        finish();
        return true;
    }

    public void onClick(View view)
    {
        view.getId();
        JVM INSTR tableswitch 2131624117 2131624118: default 28
    //                   2131624117 29
    //                   2131624118 36;
           goto _L1 _L2 _L3
_L1:
        return;
_L2:
        login();
        continue; /* Loop/switch isn't completed */
_L3:
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://m.facebook.com/r.php")));
        if(true) goto _L1; else goto _L4
_L4:
    }

    public void onCreate(Bundle bundle)
    {
        AppSession appsession;
        super.onCreate(bundle);
        mAddAccountMode = getIntent().getBooleanExtra("add_account", false);
        VersionTasks.getInstance(this).executeUpgrades();
        appsession = AppSession.getActiveSession(this, true);
        if(appsession == null) goto _L2; else goto _L1
_L1:
        class _cls1
        {

            static final int $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[];

            static 
            {
                $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus = new int[com.facebook.katana.binding.AppSession.LoginStatus.values().length];
                NoSuchFieldError nosuchfielderror3;
                try
                {
                    $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGED_IN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGING_IN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGED_OUT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                $SwitchMap$com$facebook$katana$binding$AppSession$LoginStatus[com.facebook.katana.binding.AppSession.LoginStatus.STATUS_LOGGING_OUT.ordinal()] = 4;
_L2:
                return;
                nosuchfielderror3;
                if(true) goto _L2; else goto _L1
_L1:
            }
        }

        _cls1..SwitchMap.com.facebook.katana.binding.AppSession.LoginStatus[appsession.getStatus().ordinal()];
        JVM INSTR tableswitch 1 1: default 64
    //                   1 155;
           goto _L2 _L3
_L2:
        if(!UserValuesManager.getRegisterRingtone(this))
            try
            {
                Uri uri = RingtoneUtils.createFacebookRingtone(this);
                if(uri != null)
                {
                    android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putString("ringtone", uri.toString());
                    editor.commit();
                    UserValuesManager.setRegisterRingtone(this, true);
                }
            }
            catch(IOException ioexception) { }
        if(mAddAccountMode && PlatformUtils.platformStorageSupported(this) && FacebookAuthenticationService.getAccountsCount(this) > 0)
        {
            Toaster.toast(this, 0x7f0a008f);
            finish();
        } else
        {
            setContentView(0x7f03003e);
            hideSearchButton();
            ((ImageButton)findViewById(0x7f0e0144)).setEnabled(false);
            findViewById(0x7f0e00b5).setOnClickListener(this);
            findViewById(0x7f0e00b6).setOnClickListener(this);
            String s = AppSession.getUsernameHint(this);
            if(s != null)
            {
                ((EditText)findViewById(0x7f0e00b3)).setText(s);
                ((EditText)findViewById(0x7f0e00b4)).requestFocus();
            }
            if(bundle != null)
                mErrorMessage = bundle.getString("error_message");
            mAppSessionListener = new LoginAppSessionListener(null);
        }
_L5:
        return;
_L3:
        if(mAddAccountMode) goto _L2; else goto _L4
_L4:
        startNextActivity();
        finish();
          goto _L5
    }

    protected Dialog onCreateDialog(int i)
    {
        i;
        JVM INSTR tableswitch 1 3: default 28
    //                   1 32
    //                   2 72
    //                   3 106;
           goto _L1 _L2 _L3 _L4
_L1:
        Object obj = null;
_L6:
        return ((Dialog) (obj));
_L2:
        ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(0);
        progressdialog.setMessage(getText(0x7f0a009c));
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(false);
        obj = progressdialog;
        continue; /* Loop/switch isn't completed */
_L3:
        obj = AlertDialogs.createAlert(this, getString(0x7f0a009b), 0x1080027, mErrorMessage, getString(0x7f0a00dd), null, null, null, null, true);
        continue; /* Loop/switch isn't completed */
_L4:
        obj = AlertDialogs.createAlert(this, getString(0x7f0a00a2), 0x1080027, mErrorMessage, getString(0x7f0a00dd), null, null, null, null, true);
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 101, 0, 0x7f0a008e).setIcon(0x1080041);
        return true;
    }

    public void onDestroy()
    {
        if(isFinishing() && mAddAccountMode && !mAddAccountHandled)
            FacebookAuthenticationService.addAccountError(getIntent(), 400, "User canceled");
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        menuitem.getItemId();
        JVM INSTR tableswitch 101 101: default 24
    //                   101 30;
           goto _L1 _L2
_L1:
        return super.onOptionsItemSelected(menuitem);
_L2:
        startActivity(new Intent(this, com/facebook/katana/HtmlAboutActivity));
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onPause()
    {
        super.onPause();
        mForeground = false;
        if(mAppSession != null)
            mAppSession.removeListener(mAppSessionListener);
    }

    public void onResume()
    {
        super.onResume();
        mForeground = true;
        mAppSession = AppSession.getActiveSession(this, true);
        if(mAppSession == null) goto _L2; else goto _L1
_L1:
        _cls1..SwitchMap.com.facebook.katana.binding.AppSession.LoginStatus[mAppSession.getStatus().ordinal()];
        JVM INSTR tableswitch 1 2: default 60
    //                   1 71
    //                   2 89;
           goto _L3 _L4 _L5
_L3:
        removeDialog(1);
        mAppSession = null;
_L7:
        return;
_L4:
        if(!mAddAccountMode)
            startNextActivity();
        finish();
        continue; /* Loop/switch isn't completed */
_L5:
        mAppSession.addListener(mAppSessionListener);
        continue; /* Loop/switch isn't completed */
_L2:
        removeDialog(1);
        if(true) goto _L7; else goto _L6
_L6:
    }

    protected void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        if(mErrorMessage != null)
            bundle.putString("error_message", mErrorMessage);
    }

    public boolean onSearchRequested()
    {
        return true;
    }

    private static final int ABOUT_ID = 101;
    private static final int ERROR_DIALOG_ID = 2;
    public static final String EXTRA_ADD_ACCOUNT = "add_account";
    public static final String EXTRA_REDIRECT = "login_redirect";
    private static final int LOGIN_APPROVALS_DIALOG_ID = 3;
    private static final int PROGRESS_LOGIN_DIALOG_ID = 1;
    public static final int REQUEST_REDIRECT = 2210;
    private static final String SAVE_ERROR_MESSAGE = "error_message";
    private boolean mAddAccountHandled;
    private boolean mAddAccountMode;
    private AppSession mAppSession;
    private AppSessionListener mAppSessionListener;
    private String mErrorMessage;
    private boolean mForeground;




/*
    static AppSession access$302(LoginActivity loginactivity, AppSession appsession)
    {
        loginactivity.mAppSession = appsession;
        return appsession;
    }

*/


/*
    static String access$402(LoginActivity loginactivity, String s)
    {
        loginactivity.mErrorMessage = s;
        return s;
    }

*/

}

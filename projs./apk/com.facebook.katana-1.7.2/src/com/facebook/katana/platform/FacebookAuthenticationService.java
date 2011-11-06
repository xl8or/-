// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookAuthenticationService.java

package com.facebook.katana.platform;

import android.accounts.*;
import android.app.Service;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import com.facebook.katana.LoginActivity;
import com.facebook.katana.provider.UserValuesManager;
import com.facebook.katana.util.Log;

public class FacebookAuthenticationService extends Service
{
    static final class FacebookAccountAuthenticator extends AbstractAccountAuthenticator
    {

        public Bundle addAccount(AccountAuthenticatorResponse accountauthenticatorresponse, String s, String s1, String as[], Bundle bundle)
        {
            Bundle bundle1 = new Bundle();
            Intent intent = new Intent(mContext, com/facebook/katana/LoginActivity);
            intent.putExtra("add_account", true);
            intent.putExtra("accountAuthenticatorResponse", accountauthenticatorresponse);
            bundle1.putParcelable("intent", intent);
            return bundle1;
        }

        public Bundle confirmCredentials(AccountAuthenticatorResponse accountauthenticatorresponse, Account account, Bundle bundle)
        {
            return null;
        }

        public Bundle editProperties(AccountAuthenticatorResponse accountauthenticatorresponse, String s)
        {
            throw new UnsupportedOperationException();
        }

        public Bundle getAuthToken(AccountAuthenticatorResponse accountauthenticatorresponse, Account account, String s, Bundle bundle)
        {
            return null;
        }

        public String getAuthTokenLabel(String s)
        {
            return mContext.getString(0x7f0a001e);
        }

        public Bundle hasFeatures(AccountAuthenticatorResponse accountauthenticatorresponse, Account account, String as[])
        {
            Bundle bundle = new Bundle();
            bundle.putBoolean("booleanResult", false);
            return bundle;
        }

        public Bundle updateCredentials(AccountAuthenticatorResponse accountauthenticatorresponse, Account account, String s, Bundle bundle)
        {
            return null;
        }

        private final Context mContext;

        public FacebookAccountAuthenticator(Context context)
        {
            super(context);
            mContext = context;
        }
    }


    public FacebookAuthenticationService()
    {
    }

    public static void addAccountComplete(Intent intent, String s)
    {
        AccountAuthenticatorResponse accountauthenticatorresponse = (AccountAuthenticatorResponse)intent.getParcelableExtra("accountAuthenticatorResponse");
        if(accountauthenticatorresponse == null)
        {
            Log.e("FacebookAuthenticationService", "No callback IBinder");
        } else
        {
            Bundle bundle = new Bundle();
            bundle.putString("authAccount", s);
            bundle.putString("accountType", "com.facebook.auth.login");
            accountauthenticatorresponse.onResult(bundle);
        }
    }

    public static void addAccountError(Intent intent, int i, String s)
    {
        AccountAuthenticatorResponse accountauthenticatorresponse = (AccountAuthenticatorResponse)intent.getParcelableExtra("accountAuthenticatorResponse");
        if(accountauthenticatorresponse == null)
            Log.e("FacebookAuthenticationService", "No callback IBinder");
        else
            accountauthenticatorresponse.onError(i, s);
    }

    public static void copyCallback(Intent intent, Intent intent1)
    {
        intent1.putExtra("accountAuthenticatorResponse", intent.getParcelableExtra("accountAuthenticatorResponse"));
    }

    public static boolean doesShowUngroupedContacts(Context context, String s)
    {
        ContentResolver contentresolver = context.getContentResolver();
        android.net.Uri uri = android.provider.ContactsContract.Settings.CONTENT_URI;
        String as[] = new String[1];
        as[0] = "ungrouped_visible";
        Cursor cursor = contentresolver.query(uri, as, (new StringBuilder()).append("account_type='com.facebook.auth.login' AND account_name='").append(s).append("'").toString(), null, null);
        int i = 0;
        if(cursor != null)
        {
            if(cursor.moveToNext())
                i = cursor.getInt(0);
            cursor.close();
        }
        boolean flag;
        if(i == 1)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public static Account getAccount(Context context, String s)
    {
        Account aaccount[] = AccountManager.get(context).getAccountsByType("com.facebook.auth.login");
        Account account = null;
        int i = aaccount.length;
        int j = 0;
        do
        {
label0:
            {
                if(j < i)
                {
                    if(!s.equals(aaccount[j].name))
                        break label0;
                    account = aaccount[j];
                }
                return account;
            }
            j++;
        } while(true);
    }

    public static int getAccountsCount(Context context)
    {
        return AccountManager.get(context).getAccountsByType("com.facebook.auth.login").length;
    }

    public static boolean isSyncEnabled(Context context, String s)
    {
        Account account = getAccount(context, s);
        boolean flag;
        if(account == null)
            flag = false;
        else
            flag = ContentResolver.getSyncAutomatically(account, "com.android.contacts");
        return flag;
    }

    public static void removeSessionInfo(Context context, String s)
    {
        AccountManager accountmanager = AccountManager.get(context);
        Account aaccount[] = accountmanager.getAccountsByType("com.facebook.auth.login");
        String s1 = UserValuesManager.getCurrentAccount(context);
        int i = aaccount.length;
        for(int j = 0; j < i; j++)
        {
            if(aaccount[j].name.equals(s))
                continue;
            if(aaccount[j].name.equals(s1))
                UserValuesManager.setCurrentAccount(context, null);
            accountmanager.removeAccount(aaccount[j], null, null);
        }

    }

    public static void storeSessionInfo(Context context, String s, boolean flag, boolean flag1)
    {
        Account account = getAccount(context, s);
        if(account != null) goto _L2; else goto _L1
_L1:
        account = new Account(s, "com.facebook.auth.login");
        if(AccountManager.get(context).addAccountExplicitly(account, null, null)) goto _L2; else goto _L3
_L3:
        Log.e("FacebookAuthenticationService", (new StringBuilder()).append("Unable to create account for ").append(s).toString());
_L5:
        return;
_L2:
        UserValuesManager.setCurrentAccount(context, s);
        ContentResolver.setSyncAutomatically(account, "com.android.contacts", flag);
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("account_name", s);
        contentvalues.put("account_type", "com.facebook.auth.login");
        int i;
        if(flag1)
            i = 1;
        else
            i = 0;
        contentvalues.put("ungrouped_visible", Integer.valueOf(i));
        context.getContentResolver().insert(android.provider.ContactsContract.Settings.CONTENT_URI, contentvalues);
        if(true) goto _L5; else goto _L4
_L4:
    }

    public IBinder onBind(Intent intent)
    {
        IBinder ibinder;
        if("android.accounts.AccountAuthenticator".equals(intent.getAction()))
        {
            ibinder = mAuthenticator.getIBinder();
        } else
        {
            Log.e("FacebookAuthenticationService", (new StringBuilder()).append("Bound with unknown intent: ").append(intent).toString());
            ibinder = null;
        }
        return ibinder;
    }

    public void onCreate()
    {
        mAuthenticator = new FacebookAccountAuthenticator(this);
    }

    public static final String ACCOUNT_MANAGER_TYPE = "com.facebook.auth.login";
    private static final String TAG = "FacebookAuthenticationService";
    private FacebookAccountAuthenticator mAuthenticator;
}

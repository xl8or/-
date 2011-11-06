// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoveRawContactsService.java

package com.facebook.katana;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.model.FacebookSessionInfo;

public class RemoveRawContactsService extends IntentService
{

    public RemoveRawContactsService()
    {
        super("RemoveRawContactsService");
    }

    protected void onHandleIntent(Intent intent)
    {
        if(mUsername != null)
        {
            Uri uri = android.provider.ContactsContract.RawContacts.CONTENT_URI.buildUpon().appendQueryParameter("account_name", mUsername).appendQueryParameter("account_type", "com.facebook.auth.login").appendQueryParameter("caller_is_syncadapter", "true").build();
            getContentResolver().delete(uri, null, null);
        }
    }

    public void onStart(Intent intent, int i)
    {
        AppSession appsession = AppSession.getActiveSession(this, false);
        if(appsession != null)
            mUsername = appsession.getSessionInfo().username;
        super.onStart(intent, i);
    }

    private String mUsername;
}

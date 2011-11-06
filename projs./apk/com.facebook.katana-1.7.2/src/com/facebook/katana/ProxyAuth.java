// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProxyAuth.java

package com.facebook.katana;

import android.content.Intent;
import android.content.pm.*;
import android.os.Bundle;
import com.facebook.katana.activity.PlatformDialogActivity;
import com.facebook.katana.util.Base64;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.URLQueryBuilder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Set;

public class ProxyAuth extends PlatformDialogActivity
{

    public ProxyAuth()
    {
    }

    private byte[] getCallingPackageSigHash()
    {
        byte abyte0[];
        PackageInfo packageinfo;
        MessageDigest messagedigest;
        try
        {
            packageinfo = getPackageManager().getPackageInfo(getCallingPackage(), 64);
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
        {
            Log.e("Facebook-ProxyAuth", "Failed to read calling package's signature.");
            abyte0 = null;
            continue; /* Loop/switch isn't completed */
        }
        messagedigest = MessageDigest.getInstance("SHA-1");
        messagedigest.update(packageinfo.signatures[0].toByteArray());
        abyte0 = messagedigest.digest();
_L2:
        return abyte0;
        NoSuchAlgorithmException nosuchalgorithmexception;
        nosuchalgorithmexception;
        Log.e("Facebook-ProxyAuth", "Failed to instantiate SHA-1 algorithm. It is evidently missing from this system.");
        abyte0 = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected void setupDialogURL()
    {
        Bundle bundle = new Bundle();
        Bundle bundle1 = getIntent().getExtras();
        Iterator iterator = bundle1.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            Object obj = bundle1.get(s);
            if(obj instanceof String)
                bundle.putString(s, (String)obj);
        } while(true);
        bundle.putString("type", "user_agent");
        bundle.putString("redirect_uri", "fbconnect://success");
        bundle.putString("display", "touch");
        bundle.putString("android_key", Base64.encodeToString(getCallingPackageSigHash(), 3));
        mUrl = (new StringBuilder()).append(Constants.URL.getOAuthUrl(this)).append("?").append(URLQueryBuilder.buildQueryString(bundle)).toString();
    }
}

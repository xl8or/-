// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UploadManagerConnectivity.java

package com.facebook.katana.binding;

import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.facebook.katana.service.UploadManager;

public class UploadManagerConnectivity extends BroadcastReceiver
{

    public UploadManagerConnectivity()
    {
    }

    public static boolean isConnected(Context context)
    {
        NetworkInfo networkinfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        boolean flag;
        if(networkinfo != null && networkinfo.isConnectedOrConnecting())
            flag = true;
        else
            flag = false;
        return flag;
    }

    public void onReceive(Context context, Intent intent)
    {
        if(isConnected(context))
        {
            Intent intent1 = new Intent(context, com/facebook/katana/service/UploadManager);
            intent1.putExtra("type", 1);
            context.startService(intent1);
        }
    }
}

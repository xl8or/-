// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserTask.java

package com.facebook.katana;

import android.os.Handler;

public class UserTask extends Thread
{

    public UserTask(Handler handler)
    {
        mHandler = handler;
    }

    protected void doInBackground()
    {
    }

    public void execute()
    {
        onPreExecute();
        start();
    }

    protected void onPostExecute()
    {
    }

    protected void onPreExecute()
    {
    }

    public void run()
    {
        doInBackground();
        mHandler.post(new Runnable() {

            public void run()
            {
                onPostExecute();
            }

            final UserTask this$0;

            
            {
                this$0 = UserTask.this;
                super();
            }
        }
);
    }

    private final Handler mHandler;
}

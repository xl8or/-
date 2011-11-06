// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookSyncAdapterService.java

package com.facebook.katana.platform;

import android.accounts.Account;
import android.app.Service;
import android.content.*;
import android.os.*;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.util.Log;
import java.io.IOException;

public class FacebookSyncAdapterService extends Service
{
    private final class SyncAdapterImpl extends AbstractThreadedSyncAdapter
    {

        private void messageHandler(final Handler threadHandler, final Context context, Message message, final SyncResult syncResult, final SyncListener listener)
        {
            message.what;
            JVM INSTR tableswitch 1 4: default 36
        //                       1 37
        //                       2 75
        //                       3 112
        //                       4 246;
               goto _L1 _L2 _L3 _L4 _L5
_L1:
            return;
_L2:
            if(FacebookSyncAdapterService.IS_TAG_LOGGABLE_DEBUG)
                Log.d("FBSyncAdapter", "MESSAGE_START");
            mHandler.post(new Runnable() {

                public void run()
                {
                    AppSession appsession = AppSession.getActiveSession(context, false);
                    Message message1;
                    if(appsession == null)
                    {
                        Log.w("FBSyncAdapter", "Cannot sync due to missing active session");
                        syncResult.stats.numAuthExceptions = 1L;
                        AppSession.postLoginRequiredNotification(_fld0, mUsername);
                    } else
                    {
                        appsession.addListener(listener);
                        appsession.syncFriends(context);
                    }
                    message1 = Message.obtain(threadHandler, 2);
                    threadHandler.sendMessage(message1);
                }

                final SyncAdapterImpl this$1;
                final Context val$context;
                final SyncListener val$listener;
                final SyncResult val$syncResult;
                final Handler val$threadHandler;

                
                {
                    this$1 = SyncAdapterImpl.this;
                    context = context1;
                    syncResult = syncresult;
                    listener = synclistener;
                    threadHandler = handler;
                    super();
                }
            }
);
            continue; /* Loop/switch isn't completed */
_L3:
            if(FacebookSyncAdapterService.IS_TAG_LOGGABLE_DEBUG)
                Log.d("FBSyncAdapter", "MESSAGE_SYNC_BEGIN");
            if(syncResult.stats.numAuthExceptions > 0L)
                Looper.myLooper().quit();
            continue; /* Loop/switch isn't completed */
_L4:
            final int errorCode = listener.error;
            final Exception e = listener.exception;
            if(FacebookSyncAdapterService.IS_TAG_LOGGABLE_DEBUG)
                Log.d("FBSyncAdapter", (new StringBuilder()).append("MESSAGE_SYNC_END: error = ").append(errorCode).toString(), e);
            if(errorCode != 200)
                if(e != null)
                {
                    if(e instanceof IOException)
                        syncResult.stats.numIoExceptions = 1L;
                    else
                        syncResult.stats.numParseExceptions = 1L;
                } else
                {
                    syncResult.stats.numIoExceptions = 1L;
                }
            mHandler.post(new Runnable() {

                public void run()
                {
                    if(FacebookSyncAdapterService.IS_TAG_LOGGABLE_DEBUG)
                        Log.d("FBSyncAdapter", "Cleaning up...");
                    AppSession appsession = AppSession.getActiveSession(context, false);
                    if(appsession != null)
                        appsession.removeListener(listener);
                    Message message1 = Message.obtain(threadHandler, 4);
                    threadHandler.sendMessage(message1);
                    if(ApiMethod.isSessionKeyError(errorCode, e))
                        AppSession.postLoginRequiredNotification(context, mUsername);
                }

                final SyncAdapterImpl this$1;
                final Context val$context;
                final Exception val$e;
                final int val$errorCode;
                final SyncListener val$listener;
                final Handler val$threadHandler;

                
                {
                    this$1 = SyncAdapterImpl.this;
                    context = context1;
                    listener = synclistener;
                    threadHandler = handler;
                    errorCode = i;
                    e = exception;
                    super();
                }
            }
);
            continue; /* Loop/switch isn't completed */
_L5:
            if(FacebookSyncAdapterService.IS_TAG_LOGGABLE_DEBUG)
                Log.d("FBSyncAdapter", "MESSAGE_CLEANUP_DONE");
            Looper.myLooper().quit();
            if(true) goto _L1; else goto _L6
_L6:
        }

        public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentproviderclient, final SyncResult syncResult)
        {
            if(FacebookSyncAdapterService.IS_TAG_LOGGABLE_DEBUG)
            {
                StringBuilder stringbuilder = (new StringBuilder()).append("performing sync with extras: ");
                final SyncListener listener;
                Handler handler;
                String s1;
                if(bundle == null)
                    s1 = "null";
                else
                    s1 = bundle.toString();
                Log.d("FBSyncAdapter", stringbuilder.append(s1).toString());
            }
            mUsername = account.name;
            Looper.prepare();
            listener = new SyncListener();
            handler = new Handler() {

                public void handleMessage(Message message)
                {
                    messageHandler(this, getContext(), message, syncResult, listener);
                }

                final SyncAdapterImpl this$1;
                final SyncListener val$listener;
                final SyncResult val$syncResult;

                
                {
                    this$1 = SyncAdapterImpl.this;
                    syncResult = syncresult;
                    listener = synclistener;
                    super();
                }
            }
;
            listener.setThreadHandler(handler);
            handler.sendMessage(Message.obtain(handler, 1));
            Looper.loop();
            if(FacebookSyncAdapterService.IS_TAG_LOGGABLE_DEBUG)
                Log.d("FBSyncAdapter", "=====> Loop end.");
        }

        private final Handler mHandler;
        private String mUsername;
        final FacebookSyncAdapterService this$0;



        public SyncAdapterImpl(Context context, Handler handler)
        {
            this$0 = FacebookSyncAdapterService.this;
            super(context, true);
            mUsername = null;
            mHandler = handler;
        }
    }

    private static final class SyncListener extends AppSessionListener
    {

        public void onFriendsSyncComplete(AppSession appsession, String s, int i, String s1, Exception exception1)
        {
            error = i;
            exception = exception1;
            Message message = Message.obtain(mThreadHandler, 3);
            mThreadHandler.sendMessage(message);
        }

        public void setThreadHandler(Handler handler)
        {
            mThreadHandler = handler;
        }

        public int error;
        public Exception exception;
        private Handler mThreadHandler;

        private SyncListener()
        {
        }

    }


    public FacebookSyncAdapterService()
    {
    }

    public static void requestSync(Context context, String s)
    {
        ContentResolver.requestSync(new Account("com.facebook.auth.login", s), "com.android.contacts", null);
    }

    public IBinder onBind(Intent intent)
    {
        return sSyncAdapter.getSyncAdapterBinder();
    }

    public void onCreate()
    {
        Object obj = sSyncAdapterLock;
        obj;
        JVM INSTR monitorenter ;
        if(sSyncAdapter == null)
            sSyncAdapter = new SyncAdapterImpl(getApplicationContext(), new Handler());
        return;
    }

    private static final boolean IS_TAG_LOGGABLE_DEBUG = Log.isLoggable("FBSyncAdapter", 3);
    private static final int MESSAGE_CLEANUP_DONE = 4;
    private static final int MESSAGE_START = 1;
    private static final int MESSAGE_SYNC_BEGIN = 2;
    private static final int MESSAGE_SYNC_END = 3;
    private static final String TAG = "FBSyncAdapter";
    private static SyncAdapterImpl sSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();


}

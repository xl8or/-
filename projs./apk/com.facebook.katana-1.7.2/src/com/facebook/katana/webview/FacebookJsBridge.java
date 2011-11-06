// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookJsBridge.java

package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.service.method.JsErrorLogging;
import com.facebook.katana.util.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.*;

// Referenced classes of package com.facebook.katana.webview:
//            FacebookRpcCall, FacebookWebView

class FacebookJsBridge
{
    protected static class NativeCallException extends Exception
    {

        public String toString()
        {
            String s;
            if(mRootException != null)
                s = mRootException.toString();
            else
            if(mMessage != null)
                s = mMessage;
            else
                s = "Unknown cause";
            return s;
        }

        private static final long serialVersionUID = 0x20065345L;
        private final String mMessage;
        private final Exception mRootException;

        public NativeCallException(Exception exception)
        {
            mRootException = exception;
            mMessage = null;
        }

        public NativeCallException(String s)
        {
            mRootException = null;
            mMessage = s;
        }
    }

    protected class UriHandler
        implements FacebookWebView.UrlHandler
    {

        public void handle(Context context, FacebookWebView facebookwebview, Uri uri)
        {
            FacebookRpcCall facebookrpccall = new FacebookRpcCall(uri);
            if(!handleCall(context, facebookwebview, facebookrpccall))
                Log.e(TAG, (new StringBuilder()).append("RPC call ").append(facebookrpccall.method).append(" not handled").toString());
        }

        protected boolean handleCall(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            Set set = (Set)mNativeCallHandlers.get(facebookrpccall.method);
            boolean flag;
            if(set == null)
            {
                flag = false;
            } else
            {
                for(Iterator iterator = set.iterator(); iterator.hasNext(); ((FacebookWebView.NativeCallHandler)iterator.next()).handle(context, facebookwebview, facebookrpccall));
                flag = true;
            }
            return flag;
        }

        final FacebookJsBridge this$0;

        protected UriHandler()
        {
            this$0 = FacebookJsBridge.this;
            super();
        }
    }

    protected class NativeCallReturnHandler
        implements FacebookWebView.NativeCallHandler
    {

        public void handle(Context context, FacebookWebView facebookwebview, FacebookRpcCall facebookrpccall)
        {
            String s;
            s = facebookrpccall.getParameterByName("callId");
            String s1 = facebookrpccall.getParameterByName("exc");
            String s2 = facebookrpccall.getParameterByName("retval");
            boolean flag;
            Tuple tuple;
            FacebookWebView.JsReturnHandler jsreturnhandler;
            if(!StringUtils.saneStringEquals("null", s1))
                flag = true;
            else
                flag = false;
            this;
            JVM INSTR monitorenter ;
            tuple = (Tuple)mPendingJsCalls.remove(s);
            this;
            JVM INSTR monitorexit ;
            Exception exception;
            if(tuple != null)
            {
                jsreturnhandler = (FacebookWebView.JsReturnHandler)tuple.d1;
                if(jsreturnhandler != null)
                    jsreturnhandler.handle(facebookwebview, s, flag, s2);
                if(flag)
                    JsErrorLogging.log(context, (String)tuple.d0, s1);
            } else
            {
                Log.w(TAG, (new StringBuilder()).append("js called native_return with callId ").append(s).append(" but no call with that callId was made.").toString());
            }
            return;
            exception;
            this;
            JVM INSTR monitorexit ;
            throw exception;
        }

        final FacebookJsBridge this$0;

        protected NativeCallReturnHandler()
        {
            this$0 = FacebookJsBridge.this;
            super();
        }
    }


    FacebookJsBridge(String s)
    {
        TAG = s;
        mNativeCallHandlers = new HashMap();
        mPendingJsCalls = new HashMap();
        registerNativeCallHandler("call_return", new NativeCallReturnHandler());
    }

    static String jsEncodeCall(String s, List list)
    {
        StringBuilder stringbuilder = new StringBuilder(s);
        stringbuilder.append("(");
        com.facebook.katana.util.StringUtils.StringProcessor stringprocessor = jsonStringQuoter;
        Object aobj[] = new Object[1];
        aobj[0] = list;
        StringUtils.join(stringbuilder, ", ", stringprocessor, aobj);
        stringbuilder.append(");");
        return stringbuilder.toString();
    }

    public void registerNativeCallHandler(String s, FacebookWebView.NativeCallHandler nativecallhandler)
    {
        Object obj = (Set)mNativeCallHandlers.get(s);
        if(obj == null)
        {
            obj = new HashSet();
            mNativeCallHandlers.put(s, obj);
        }
        ((Set) (obj)).add(nativecallhandler);
    }

    public String registerPendingJsCall(String s, FacebookWebView.JsReturnHandler jsreturnhandler)
    {
        String s1 = Integer.toString(mNextCallId.getAndIncrement());
        this;
        JVM INSTR monitorenter ;
        mPendingJsCalls.put(s1, new Tuple(s, jsreturnhandler));
        return s1;
    }

    protected static final com.facebook.katana.util.StringUtils.StringProcessor jsonStringQuoter = new com.facebook.katana.util.StringUtils.StringProcessor() {

        public String formatString(Object obj)
        {
            String s;
            if((obj instanceof JSONArray) || (obj instanceof JSONObject) || (obj instanceof JSONStringer))
                s = obj.toString();
            else
                s = JSONObject.quote(obj.toString());
            return s;
        }

    }
;
    protected static final FBJsonFactory mJsonFactory = new FBJsonFactory();
    protected final String TAG;
    public final UriHandler handler = new UriHandler();
    protected Map mNativeCallHandlers;
    protected final AtomicInteger mNextCallId = new AtomicInteger();
    protected Map mPendingJsCalls;

}

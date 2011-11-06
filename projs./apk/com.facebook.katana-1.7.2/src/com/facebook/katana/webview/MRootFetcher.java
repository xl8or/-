// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MRoot.java

package com.facebook.katana.webview;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.AuthDeepLinkMethod;
import com.facebook.katana.service.method.HttpOperation;
import com.facebook.katana.util.Tuple;
import java.io.*;
import java.net.URI;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;

// Referenced classes of package com.facebook.katana.webview:
//            FacebookAuthentication

class MRootFetcher extends HttpOperation
{
    static class Listener
        implements com.facebook.katana.service.method.HttpOperation.HttpOperationListener
    {

        protected static String getFinalUrl(HttpContext httpcontext)
        {
            HttpRequest httprequest = (HttpRequest)httpcontext.getAttribute("http.request");
            String s1;
            if(!(httprequest instanceof HttpUriRequest))
            {
                s1 = null;
            } else
            {
                String s = ((HttpUriRequest)httprequest).getURI().toString();
                Uri uri = Uri.parse(s);
                if(uri.isAbsolute())
                {
                    s1 = s;
                } else
                {
                    HttpHost httphost = (HttpHost)httpcontext.getAttribute("http.target_host");
                    android.net.Uri.Builder builder = uri.buildUpon();
                    builder.scheme(httphost.getSchemeName());
                    builder.authority(httphost.getHostName());
                    s1 = builder.toString();
                }
            }
            return s1;
        }

        public void onHttpOperationComplete(HttpOperation httpoperation, int i, String s, OutputStream outputstream, Exception exception)
        {
            final Tuple errData = processResponse(httpoperation, i, s, outputstream, exception);
            if(errData != null)
                mHandler.post(new Runnable() {

                    public void run()
                    {
                        mCb.callback(mContext, false, arg, null, null, errData);
                    }

                    final Listener this$0;
                    final Tuple val$errData;

                
                {
                    this$0 = Listener.this;
                    errData = tuple;
                    super();
                }
                }
);
        }

        public void onHttpOperationProgress(HttpOperation httpoperation, long l, long l1)
        {
        }

        public Tuple processResponse(HttpOperation httpoperation, int i, String s, OutputStream outputstream, Exception exception)
        {
            if(i == 200 && exception == null) goto _L2; else goto _L1
_L1:
            Tuple tuple = new Tuple(MRoot.LoadError.NETWORK_ERROR, null);
_L4:
            return tuple;
_L2:
            String s1;
            s1 = getFinalUrl(httpoperation.httpContext);
            if(!FacebookAuthentication.matchUrlLiberally(s1, (String)arg.d0))
            {
                tuple = new Tuple(MRoot.LoadError.UNEXPECTED_REDIRECT, s1);
                continue; /* Loop/switch isn't completed */
            }
            String s2 = new String(((ByteArrayOutputStream)outputstream).toByteArray());
            final JSONArray array = new JSONArray();
            array.put(s1);
            array.put(s2);
            final Tuple deserialized = new Tuple(s1, s2);
            mHandler.post(new Runnable() {

                public void run()
                {
                    mCb.callback(mContext, true, arg, array.toString(), deserialized, null);
                }

                final Listener this$0;
                final JSONArray val$array;
                final Tuple val$deserialized;

                
                {
                    this$0 = Listener.this;
                    array = jsonarray;
                    deserialized = tuple;
                    super();
                }
            }
);
            tuple = null;
            continue; /* Loop/switch isn't completed */
            Exception exception1;
            exception1;
            tuple = new Tuple(MRoot.LoadError.UNKNOWN_ERROR, null);
            if(true) goto _L4; else goto _L3
_L3:
        }

        public final Tuple arg;
        protected NetworkRequestCallback mCb;
        protected Context mContext;
        protected Handler mHandler;

        Listener(Context context, Tuple tuple, Handler handler, NetworkRequestCallback networkrequestcallback)
        {
            arg = tuple;
            mContext = context;
            mHandler = handler;
            mCb = networkrequestcallback;
        }
    }


    MRootFetcher(Context context, Tuple tuple, Handler handler, NetworkRequestCallback networkrequestcallback)
        throws IOException
    {
        super(context, "GET", AuthDeepLinkMethod.getDeepLinkUrl(context, (String)tuple.d0), new ByteArrayOutputStream(8192), new Listener(context, tuple, handler, networkrequestcallback), (String)tuple.d1, "text/xml, text/html, application/xhtml+xml, image/png, text/plain, */*;q=0.8", new BasicHttpContext());
    }
}

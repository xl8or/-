// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UriMapClient.java

package com.facebook.katana.features;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.service.method.FqlGetUserServerSettings;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMSimpleDict;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParser;

public abstract class UriMapClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    public UriMapClient()
    {
    }

    public UriTemplateMap deserialize(String s)
    {
        Map map;
        UriTemplateMap uritemplatemap1;
        Iterator iterator;
        JsonParser jsonparser = (new FBJsonFactory()).createJsonParser(s);
        jsonparser.nextToken();
        Object obj = JMParser.parseJsonResponse(jsonparser, new JMSimpleDict());
        if(obj == null || !(obj instanceof Map))
            break MISSING_BLOCK_LABEL_291;
        map = (Map)obj;
        uritemplatemap1 = new UriTemplateMap();
        iterator = getDevMappings().entrySet().iterator();
_L1:
        java.util.Map.Entry entry1;
        String s2;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_173;
        entry1 = (java.util.Map.Entry)iterator.next();
        s2 = (String)entry1.getKey();
        uritemplatemap1.put(s2, getUriHandler((String)entry1.getValue()));
          goto _L1
        com.facebook.katana.util.UriTemplateMap.InvalidUriTemplateException invaliduritemplateexception1;
        invaliduritemplateexception1;
        Log.e(getTag(), (new StringBuilder()).append("Invalid uri template: ").append(s2).toString(), invaliduritemplateexception1);
        throw invaliduritemplateexception1;
        Iterator iterator1 = map.entrySet().iterator();
_L2:
        java.util.Map.Entry entry;
        String s1;
        if(!iterator1.hasNext())
            break MISSING_BLOCK_LABEL_285;
        entry = (java.util.Map.Entry)iterator1.next();
        s1 = (String)entry.getKey();
        uritemplatemap1.put(s1, getUriHandler((String)entry.getValue()));
          goto _L2
        com.facebook.katana.util.UriTemplateMap.InvalidUriTemplateException invaliduritemplateexception;
        invaliduritemplateexception;
        Log.e(getTag(), (new StringBuilder()).append("Invalid uri template: ").append(s1).toString(), invaliduritemplateexception);
          goto _L2
        JMException jmexception;
        jmexception;
        UriTemplateMap uritemplatemap;
        uritemplatemap = null;
        break MISSING_BLOCK_LABEL_293;
        uritemplatemap = uritemplatemap1;
        break MISSING_BLOCK_LABEL_293;
        uritemplatemap = null;
_L4:
        return uritemplatemap;
        IOException ioexception;
        ioexception;
        uritemplatemap = null;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public volatile Object deserialize(String s)
    {
        return deserialize(s);
    }

    protected abstract Map getDevMappings();

    protected abstract String getProjectMapSetting();

    protected abstract String getProjectName();

    protected abstract String getTag();

    protected abstract com.facebook.katana.IntentUriHandler.UriHandler getUriHandler(String s);

    public String initiateNetworkRequest(Context context, Object obj, final NetworkRequestCallback cb)
    {
        NetworkRequestCallback networkrequestcallback = new NetworkRequestCallback() {

            public volatile void callback(Context context1, boolean flag, Object obj1, String s, Object obj2, Object obj3)
            {
                callback(context1, flag, (String)obj1, s, (String)obj2, obj3);
            }

            public void callback(Context context1, boolean flag, String s, String s1, String s2, Object obj1)
            {
                UriTemplateMap uritemplatemap = null;
                if(flag)
                    uritemplatemap = deserialize(s2);
                cb.callback(context1, flag, s, s2, uritemplatemap, obj1);
            }

            final UriMapClient this$0;
            final NetworkRequestCallback val$cb;

            
            {
                this$0 = UriMapClient.this;
                cb = networkrequestcallback;
                super();
            }
        }
;
        return FqlGetUserServerSettings.RequestSettingsByProjectSetting(AppSession.getActiveSession(context, false), context, getProjectName(), getProjectMapSetting(), networkrequestcallback);
    }

    public boolean staleDataAcceptable(Object obj, UriTemplateMap uritemplatemap)
    {
        return true;
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable(obj, (UriTemplateMap)obj1);
    }
}

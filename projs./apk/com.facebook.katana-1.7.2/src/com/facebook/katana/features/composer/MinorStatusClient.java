// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MinorStatus.java

package com.facebook.katana.features.composer;

import android.content.Context;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.FacebookSessionInfo;
import com.facebook.katana.model.MinorStatusModel;
import com.facebook.katana.service.method.FBJsonFactory;
import com.facebook.katana.service.method.FqlGetMinorStatus;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

class MinorStatusClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    MinorStatusClient()
    {
    }

    public Boolean deserialize(String s)
    {
        List list;
        JsonParser jsonparser = (new FBJsonFactory()).createJsonParser(s);
        jsonparser.nextToken();
        list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/MinorStatusModel);
        JMException jmexception;
        Boolean boolean1;
        IOException ioexception;
        JsonParseException jsonparseexception;
        if(list == null || list.size() != 1)
            boolean1 = null;
        else
            boolean1 = Boolean.valueOf(((MinorStatusModel)list.get(0)).isMinor);
        return boolean1;
        jsonparseexception;
        boolean1 = null;
        continue; /* Loop/switch isn't completed */
        ioexception;
        boolean1 = null;
        continue; /* Loop/switch isn't completed */
        jmexception;
        boolean1 = null;
        if(true) goto _L2; else goto _L1
_L1:
        break MISSING_BLOCK_LABEL_67;
_L2:
        break MISSING_BLOCK_LABEL_46;
    }

    public volatile Object deserialize(String s)
    {
        return deserialize(s);
    }

    public int getCacheTtl(Object obj, Boolean boolean1)
    {
        int i;
        if(Boolean.FALSE.equals(boolean1))
            i = 0x93a80;
        else
            i = 10800;
        return i;
    }

    public volatile int getCacheTtl(Object obj, Object obj1)
    {
        return getCacheTtl(obj, (Boolean)obj1);
    }

    public String getKey(Object obj)
    {
        return "fql:user_minor_status";
    }

    public int getPersistentStoreTtl(Object obj, Boolean boolean1)
    {
        return getCacheTtl(obj, boolean1);
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl(obj, (Boolean)obj1);
    }

    public String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        if(appsession == null) goto _L2; else goto _L1
_L1:
        FacebookSessionInfo facebooksessioninfo = appsession.getSessionInfo();
        if(facebooksessioninfo == null) goto _L2; else goto _L3
_L3:
        String s = FqlGetMinorStatus.get(context, networkrequestcallback, facebooksessioninfo.userId);
_L5:
        return s;
_L2:
        s = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public boolean staleDataAcceptable(Object obj, Boolean boolean1)
    {
        return true;
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable(obj, (Boolean)obj1);
    }
}

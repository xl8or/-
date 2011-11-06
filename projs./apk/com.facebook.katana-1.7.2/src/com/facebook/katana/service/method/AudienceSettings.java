// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AudienceSettings.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.*;
import com.facebook.katana.model.*;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, ApiMethodCallback, FqlGetFriendLists, AudienceSettingsManagedStoreClient, 
//            ApiMethodListener, FqlGeneratedQuery

public class AudienceSettings extends FqlMultiQuery
    implements ApiMethodCallback
{
    static class FqlGetPrivacySetting extends FqlGeneratedQuery
    {

        protected static String buildWhereClause(com.facebook.katana.model.PrivacySetting.Category category)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append("name=");
            StringUtils.appendEscapedFQLString(stringbuilder, category.toString());
            return stringbuilder.toString();
        }

        protected void parseJSON(JsonParser jsonparser)
            throws FacebookApiException, JsonParseException, IOException, JMException
        {
            List list = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/PrivacySetting);
            if(!list.isEmpty())
                mPrivacySetting = (PrivacySetting)list.get(0);
        }

        PrivacySetting mPrivacySetting;

        protected FqlGetPrivacySetting(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, com.facebook.katana.model.PrivacySetting.Category category)
        {
            this(context, intent, s, apimethodlistener, buildWhereClause(category));
        }

        protected FqlGetPrivacySetting(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1)
        {
            super(context, intent, s, apimethodlistener, "privacy_setting", s1, com/facebook/katana/model/PrivacySetting);
        }
    }


    public AudienceSettings(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l, com.facebook.katana.model.PrivacySetting.Category category, 
            NetworkRequestCallback networkrequestcallback)
    {
        super(context, intent, s, buildQueries(context, intent, s, l, category), apimethodlistener);
        mCallback = networkrequestcallback;
        mPrivacyCategory = category;
    }

    protected static String RequestAudienceSettings(Context context, com.facebook.katana.model.PrivacySetting.Category category, NetworkRequestCallback networkrequestcallback)
    {
        AppSession appsession = AppSession.getActiveSession(context, false);
        return appsession.postToService(context, new AudienceSettings(context, null, appsession.getSessionInfo().sessionKey, null, appsession.getSessionInfo().userId, category, networkrequestcallback), 1001, 1020, null);
    }

    protected static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l, com.facebook.katana.model.PrivacySetting.Category category)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("privacy_setting", new FqlGetPrivacySetting(context, intent, s, null, category));
        linkedhashmap.put("friendlists", new FqlGetFriendLists(context, intent, s, null, l));
        return linkedhashmap;
    }

    public static AudienceSettings get(Context context, com.facebook.katana.model.PrivacySetting.Category category)
    {
        return (AudienceSettings)getStore().get(context, category);
    }

    /**
     * @deprecated Method getStore is deprecated
     */

    protected static ManagedDataStore getStore()
    {
        com/facebook/katana/service/method/AudienceSettings;
        JVM INSTR monitorenter ;
        ManagedDataStore manageddatastore;
        if(store == null)
            store = new ManagedDataStore(new AudienceSettingsManagedStoreClient());
        manageddatastore = store;
        com/facebook/katana/service/method/AudienceSettings;
        JVM INSTR monitorexit ;
        return manageddatastore;
        Exception exception;
        exception;
        throw exception;
    }

    public static void reset()
    {
        store = null;
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        boolean flag;
        if(i == 200)
            flag = true;
        else
            flag = false;
        mCallback.callback(context, flag, mPrivacyCategory, "", this, null);
        for(Iterator iterator = appsession.getListeners().iterator(); iterator.hasNext(); ((AppSessionListener)iterator.next()).onGetAudienceSettingsComplete(appsession, s, i, s1, exception, this));
    }

    public NetworkRequestCallback getCallback()
    {
        return mCallback;
    }

    public List getFriendLists()
    {
        return mFriendLists;
    }

    public List getGroups()
    {
        return mGroups;
    }

    public PrivacySetting getPrivacySetting()
    {
        return mPrivacySetting;
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        mPrivacySetting = ((FqlGetPrivacySetting)getQueryByName("privacy_setting")).mPrivacySetting;
        mFriendLists = ((FqlGetFriendLists)getQueryByName("friendlists")).getFriendLists();
    }

    public void setPrivacySetting(PrivacySetting privacysetting)
    {
        mPrivacySetting = privacysetting;
    }

    protected static ManagedDataStore store;
    private NetworkRequestCallback mCallback;
    private List mFriendLists;
    private List mGroups;
    private com.facebook.katana.model.PrivacySetting.Category mPrivacyCategory;
    private PrivacySetting mPrivacySetting;
}

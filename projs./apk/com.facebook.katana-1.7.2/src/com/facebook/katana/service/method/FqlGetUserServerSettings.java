// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlGetUserServerSettings.java

package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.NetworkRequestCallback;
import com.facebook.katana.model.*;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlGeneratedQuery, ApiMethodCallback, ApiMethodListener

public class FqlGetUserServerSettings extends FqlGeneratedQuery
    implements ApiMethodCallback
{

    public FqlGetUserServerSettings(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, String s1, String s2, NetworkRequestCallback networkrequestcallback)
    {
        super(context, intent, s, apimethodlistener, "user_settings", buildWhereClause(s1, s2), com/facebook/katana/model/UserServerSetting);
        mCallback = networkrequestcallback;
        projectName = s1;
        settingName = s2;
    }

    public static String RequestSettingsByProjectSetting(AppSession appsession, Context context, String s, String s1, NetworkRequestCallback networkrequestcallback)
    {
        return appsession.postToService(context, new FqlGetUserServerSettings(context, null, appsession.getSessionInfo().sessionKey, null, s, s1, networkrequestcallback), 1001, 1020, null);
    }

    protected static String buildWhereClause(String s, String s1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("project=").append(StringUtils.FQLEscaper.formatString(s)).append(" AND ").append("setting=").append(StringUtils.FQLEscaper.formatString(s1));
        return stringbuilder.toString();
    }

    public void executeCallbacks(AppSession appsession, Context context, Intent intent, String s, int i, String s1, Exception exception)
    {
        boolean flag;
        if(i == 200 && mValue != null)
            flag = true;
        else
            flag = false;
        mCallback.callback(context, flag, settingName, mValue, mValue, null);
    }

    protected void parseJSON(JsonParser jsonparser)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        Iterator iterator = JMParser.parseObjectListJson(jsonparser, com/facebook/katana/model/UserServerSetting).iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            UserServerSetting userserversetting = (UserServerSetting)iterator.next();
            if(userserversetting.mProjectName.equals(projectName) && userserversetting.mSettingName.equals(settingName))
                mValue = userserversetting.mValue;
        } while(true);
    }

    private static final String TAG = "FqlGetUserServerSettings";
    protected NetworkRequestCallback mCallback;
    protected String mValue;
    public final String projectName;
    public final String settingName;
}

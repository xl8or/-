// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AudienceSettings.java

package com.facebook.katana.service.method;

import android.content.Context;
import com.facebook.katana.binding.NetworkRequestCallback;

// Referenced classes of package com.facebook.katana.service.method:
//            AudienceSettings

class AudienceSettingsManagedStoreClient
    implements com.facebook.katana.binding.ManagedDataStore.Client
{

    AudienceSettingsManagedStoreClient()
    {
    }

    public AudienceSettings deserialize(String s)
    {
        throw new IllegalStateException("Attempting to deserialize AudienceSettings, currently unsupported");
    }

    public volatile Object deserialize(String s)
    {
        return deserialize(s);
    }

    public int getCacheTtl(com.facebook.katana.model.PrivacySetting.Category category, AudienceSettings audiencesettings)
    {
        return 300;
    }

    public volatile int getCacheTtl(Object obj, Object obj1)
    {
        return getCacheTtl((com.facebook.katana.model.PrivacySetting.Category)obj, (AudienceSettings)obj1);
    }

    public String getKey(com.facebook.katana.model.PrivacySetting.Category category)
    {
        return (new StringBuilder()).append("audience_setting:").append(category).toString();
    }

    public volatile String getKey(Object obj)
    {
        return getKey((com.facebook.katana.model.PrivacySetting.Category)obj);
    }

    public int getPersistentStoreTtl(com.facebook.katana.model.PrivacySetting.Category category, AudienceSettings audiencesettings)
    {
        return 0;
    }

    public volatile int getPersistentStoreTtl(Object obj, Object obj1)
    {
        return getPersistentStoreTtl((com.facebook.katana.model.PrivacySetting.Category)obj, (AudienceSettings)obj1);
    }

    public String initiateNetworkRequest(Context context, com.facebook.katana.model.PrivacySetting.Category category, NetworkRequestCallback networkrequestcallback)
    {
        return AudienceSettings.RequestAudienceSettings(context, category, networkrequestcallback);
    }

    public volatile String initiateNetworkRequest(Context context, Object obj, NetworkRequestCallback networkrequestcallback)
    {
        return initiateNetworkRequest(context, (com.facebook.katana.model.PrivacySetting.Category)obj, networkrequestcallback);
    }

    public boolean staleDataAcceptable(com.facebook.katana.model.PrivacySetting.Category category, AudienceSettings audiencesettings)
    {
        return true;
    }

    public volatile boolean staleDataAcceptable(Object obj, Object obj1)
    {
        return staleDataAcceptable((com.facebook.katana.model.PrivacySetting.Category)obj, (AudienceSettings)obj1);
    }
}

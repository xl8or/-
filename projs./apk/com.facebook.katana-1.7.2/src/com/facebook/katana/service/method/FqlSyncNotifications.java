// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FqlSyncNotifications.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import com.facebook.katana.model.*;
import com.facebook.katana.provider.NotificationsProvider;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

// Referenced classes of package com.facebook.katana.service.method:
//            FqlMultiQuery, FqlGetNotifications, ApiMethodListener, FqlGetAppsProfile, 
//            FqlGetProfile

public class FqlSyncNotifications extends FqlMultiQuery
{
    private static interface NotificationsQuery
    {

        public static final int INDEX_NOTIFICATION_ID = 0;
        public static final int INDEX_OBJECT_ID = 1;
        public static final int INDEX_OBJECT_TYPE = 2;
        public static final int INDEX_TITLE = 3;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[4];
            as[0] = "notif_id";
            as[1] = "object_id";
            as[2] = "object_type";
            as[3] = "title";
        }
    }


    public FqlSyncNotifications(Context context, Intent intent, String s, long l, ApiMethodListener apimethodlistener)
    {
        super(context, intent, s, buildQueries(context, intent, s, l), apimethodlistener);
    }

    public static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("notifications", new FqlGetNotifications(context, intent, s, l, (ApiMethodListener)null));
        linkedhashmap.put("apps", new FqlGetAppsProfile(context, intent, s, null, "app_id IN (select app_id FROM #notifications)"));
        linkedhashmap.put("profiles", new FqlGetProfile(context, intent, s, null, "id IN (select sender_id FROM #notifications)"));
        return linkedhashmap;
    }

    private boolean detectSyncChanges()
    {
        HashMap hashmap = new HashMap();
        FacebookNotification facebooknotification2;
        for(Iterator iterator = mNotifications.iterator(); iterator.hasNext(); hashmap.put(Long.valueOf(facebooknotification2.getNotificationId()), facebooknotification2))
            facebooknotification2 = (FacebookNotification)iterator.next();

        Cursor cursor = mContext.getContentResolver().query(NotificationsProvider.CONTENT_URI, NotificationsQuery.PROJECTION, null, null, null);
        if(cursor.moveToFirst())
            do
            {
                Long long1 = Long.valueOf(cursor.getLong(0));
                String s = cursor.getString(2);
                String s1 = cursor.getString(1);
                String s2 = cursor.getString(3);
                FacebookNotification facebooknotification1 = (FacebookNotification)hashmap.get(long1);
                Iterator iterator1;
                FacebookNotification facebooknotification;
                if(facebooknotification1 == null)
                {
                    mDeletedNotificationIds.add(long1);
                } else
                {
                    boolean flag1 = isStringUpdated(s, facebooknotification1.getObjectType());
                    boolean flag2 = isStringUpdated(s1, facebooknotification1.getObjectId());
                    boolean flag3 = isStringUpdated(s2, facebooknotification1.getTitle());
                    if(flag1 || flag2 || flag3)
                        mDeletedNotificationIds.add(long1);
                    else
                        hashmap.remove(long1);
                }
            } while(cursor.moveToNext());
        cursor.close();
        for(iterator1 = hashmap.values().iterator(); iterator1.hasNext(); mNewNotifications.add(facebooknotification))
            facebooknotification = (FacebookNotification)iterator1.next();

        boolean flag;
        if(mNewNotifications.size() > 0 || mDeletedNotificationIds.size() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private static boolean isStringUpdated(String s, String s1)
    {
        boolean flag;
        if(s1 != null && (s == null || !s.equals(s1)))
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void updateContentProviders()
    {
        ContentResolver contentresolver = mContext.getContentResolver();
        if(mDeletedNotificationIds.size() > 0)
        {
            StringBuilder stringbuilder = new StringBuilder(256);
            stringbuilder.append("notif_id").append(" IN(");
            Object aobj[] = new Object[1];
            aobj[0] = mDeletedNotificationIds;
            StringUtils.join(stringbuilder, ",", null, aobj);
            stringbuilder.append(')');
            contentresolver.delete(NotificationsProvider.CONTENT_URI, stringbuilder.toString(), null);
        }
        if(mNewNotifications.size() > 0)
        {
            int i = 0;
            ContentValues acontentvalues[] = new ContentValues[mNewNotifications.size()];
            Iterator iterator = mNewNotifications.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                FacebookNotification facebooknotification = (FacebookNotification)iterator.next();
                ContentValues contentvalues = new ContentValues();
                acontentvalues[i] = contentvalues;
                i++;
                contentvalues.put("title", facebooknotification.getTitle());
                contentvalues.put("body", facebooknotification.getBody());
                contentvalues.put("created", Long.valueOf(facebooknotification.getCreatedTime()));
                contentvalues.put("href", facebooknotification.getHRef());
                contentvalues.put("is_read", Boolean.valueOf(facebooknotification.isRead()));
                contentvalues.put("notif_id", Long.valueOf(facebooknotification.getNotificationId()));
                contentvalues.put("sender_id", Long.valueOf(facebooknotification.getSenderId()));
                contentvalues.put("app_id", Long.valueOf(facebooknotification.getAppId()));
                contentvalues.put("object_id", facebooknotification.getObjectId());
                contentvalues.put("object_type", facebooknotification.getObjectType());
                FacebookApp facebookapp = (FacebookApp)mApps.get(Long.valueOf(facebooknotification.getAppId()));
                if(facebookapp != null && facebookapp.mImageUrl != null)
                    contentvalues.put("app_image", facebookapp.mImageUrl);
                contentvalues.put("app_id", Long.valueOf(facebooknotification.getAppId()));
                FacebookProfile facebookprofile = (FacebookProfile)mAllProfiles.get(Long.valueOf(facebooknotification.getSenderId()));
                if(facebookprofile != null)
                {
                    contentvalues.put("sender_name", facebookprofile.mDisplayName);
                    contentvalues.put("sender_url", facebookprofile.mImageUrl);
                }
            } while(true);
            contentresolver.bulkInsert(NotificationsProvider.CONTENT_URI, acontentvalues);
        }
    }

    protected void parseJSON(JsonParser jsonparser, String s)
        throws FacebookApiException, JsonParseException, IOException, JMException
    {
        super.parseJSON(jsonparser, s);
        FqlGetNotifications fqlgetnotifications = (FqlGetNotifications)getQueryByName("notifications");
        FqlGetProfile fqlgetprofile = (FqlGetProfile)getQueryByName("profiles");
        FqlGetAppsProfile fqlgetappsprofile = (FqlGetAppsProfile)getQueryByName("apps");
        mNotifications = fqlgetnotifications.getNotifications();
        mAllProfiles = fqlgetprofile.getProfiles();
        mApps = fqlgetappsprofile.getApps();
        if(detectSyncChanges())
            updateContentProviders();
    }

    private Map mAllProfiles;
    private Map mApps;
    private final List mDeletedNotificationIds = new ArrayList();
    private final List mNewNotifications = new ArrayList();
    private List mNotifications;
}

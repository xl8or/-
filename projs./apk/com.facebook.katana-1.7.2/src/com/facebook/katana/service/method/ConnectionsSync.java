// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionsSync.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.*;
import com.facebook.katana.platform.FacebookAuthenticationService;
import com.facebook.katana.platform.PlatformStorage;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.Utils;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, ApiMethodListener, FqlMultiQuery, FqlSyncUsersQuery, 
//            FqlGetProfileGeneric

public class ConnectionsSync extends ApiMethod
    implements ApiMethodListener
{
    private static class FacebookFanPageProfile extends FacebookPageProfile
    {

        FacebookFanPageProfile()
        {
            connectionType = com.facebook.katana.provider.ConnectionsProvider.ConnectionType.PAGE_FAN;
        }
    }

    private static class FacebookAdminPageProfile extends FacebookPageProfile
    {

        FacebookAdminPageProfile()
        {
            connectionType = com.facebook.katana.provider.ConnectionsProvider.ConnectionType.PAGE_ADMIN;
        }
    }

    private static class FacebookPageProfile extends FacebookProfile
    {

        long computeHash()
        {
            Object aobj[] = new Object[4];
            aobj[0] = Long.valueOf(mId);
            aobj[1] = mDisplayName;
            aobj[2] = mImageUrl;
            aobj[3] = Integer.valueOf(connectionType.ordinal());
            return Utils.hashItemsLong(aobj);
        }

        com.facebook.katana.provider.ConnectionsProvider.ConnectionType getConnectionType()
        {
            return connectionType;
        }

        protected com.facebook.katana.provider.ConnectionsProvider.ConnectionType connectionType;

        private FacebookPageProfile()
        {
        }

    }

    private static class FqlGetFriendsAndPages extends FqlMultiQuery
    {

        private static LinkedHashMap buildQueries(Context context, Intent intent, String s, long l)
        {
            LinkedHashMap linkedhashmap = new LinkedHashMap();
            linkedhashmap.put("friends", new FqlSyncUsersQuery(context, intent, s, l, null));
            linkedhashmap.put("admin", new FqlGetProfileGeneric(context, intent, s, null, (new StringBuilder()).append("id IN (SELECT page_id FROM page_admin WHERE uid=").append(l).append(")").toString(), com/facebook/katana/service/method/ConnectionsSync$FacebookAdminPageProfile));
            linkedhashmap.put("fan", new FqlGetProfileGeneric(context, intent, s, null, (new StringBuilder()).append("id IN (SELECT page_id FROM page_fan WHERE uid=").append(l).append(")").toString(), com/facebook/katana/service/method/ConnectionsSync$FacebookFanPageProfile));
            return linkedhashmap;
        }

        Map getAdminPages()
        {
            return ((FqlGetProfileGeneric)getQueryByName("admin")).getProfiles();
        }

        Map getFanPages()
        {
            return ((FqlGetProfileGeneric)getQueryByName("fan")).getProfiles();
        }

        List getFriends()
        {
            return ((FqlSyncUsersQuery)getQueryByName("friends")).getFriends();
        }

        FqlGetFriendsAndPages(Context context, Intent intent, String s, ApiMethodListener apimethodlistener, long l)
        {
            super(context, intent, s, buildQueries(context, intent, s, l), apimethodlistener);
        }
    }

    private static interface HashQuery
    {

        public static final int INDEX_HASH = 1;
        public static final int INDEX_USER_ID;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[2];
            as[0] = "user_id";
            as[1] = "hash";
        }
    }


    public ConnectionsSync(Context context, Intent intent, String s, long l, String s1, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", null, com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mSessionKey = s;
        mMyUserId = l;
        mMyUsername = s1;
        mAccount = intent.getStringExtra("un");
    }

    private boolean detectFriendsChanges(List list)
    {
        HashMap hashmap = new HashMap();
        FacebookFriendInfo facebookfriendinfo2;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); hashmap.put(Long.valueOf(facebookfriendinfo2.mUserId), facebookfriendinfo2))
            facebookfriendinfo2 = (FacebookFriendInfo)iterator.next();

        Cursor cursor;
        for(cursor = mContext.getContentResolver().query(ConnectionsProvider.FRIENDS_CONTENT_URI, HashQuery.PROJECTION, null, null, null); cursor.moveToNext();)
        {
            Long long1 = Long.valueOf(cursor.getLong(0));
            FacebookFriendInfo facebookfriendinfo1 = (FacebookFriendInfo)hashmap.get(long1);
            if(facebookfriendinfo1 == null)
            {
                mDeletedFriendIds.add(long1);
                hashmap.remove(long1);
            } else
            if(facebookfriendinfo1.computeHash() != cursor.getLong(1))
            {
                mUpdatedFriends.add(facebookfriendinfo1);
                hashmap.remove(long1);
            } else
            {
                hashmap.remove(long1);
            }
        }

        cursor.close();
        Iterator iterator1 = hashmap.values().iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            FacebookFriendInfo facebookfriendinfo = (FacebookFriendInfo)iterator1.next();
            if(facebookfriendinfo.mDisplayName != null)
                mNewFriends.add(facebookfriendinfo);
        } while(true);
        boolean flag;
        if(mNewFriends.size() > 0 || mUpdatedFriends.size() > 0 || mDeletedFriendIds.size() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private boolean detectPagesChanges(Map map, List list, List list1, List list2)
    {
        Cursor cursor;
        for(cursor = mContext.getContentResolver().query(ConnectionsProvider.PAGES_CONTENT_URI, HashQuery.PROJECTION, null, null, null); cursor.moveToNext();)
        {
            long l = cursor.getLong(0);
            FacebookPageProfile facebookpageprofile1 = (FacebookPageProfile)map.get(Long.valueOf(l));
            if(facebookpageprofile1 == null)
                list2.add(Long.valueOf(l));
            else
            if(facebookpageprofile1.computeHash() != cursor.getLong(1))
            {
                list1.add(facebookpageprofile1);
                map.remove(Long.valueOf(l));
            } else
            {
                map.remove(Long.valueOf(l));
            }
        }

        cursor.close();
        Iterator iterator = map.values().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            FacebookPageProfile facebookpageprofile = (FacebookPageProfile)iterator.next();
            if(facebookpageprofile.mDisplayName != null)
                list.add(facebookpageprofile);
        } while(true);
        boolean flag;
        if(list.size() > 0 || list1.size() > 0 || list2.size() > 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    private void syncFriends(List list)
    {
        ContentResolver contentresolver;
        contentresolver = mContext.getContentResolver();
        if(PlatformUtils.platformStorageSupported(mContext) && FacebookAuthenticationService.isSyncEnabled(mContext, mMyUsername))
            PlatformStorage.syncContacts(mContext, mAccount, list, mNeedUserImageMap);
        break MISSING_BLOCK_LABEL_48;
        while(true) 
        {
            do
                return;
            while(mCanceled || !detectFriendsChanges(list) || mCanceled);
            if(mNewFriends.size() > 0)
            {
                int i = 0;
                ContentValues acontentvalues[] = new ContentValues[mNewFriends.size()];
                Iterator iterator2 = mNewFriends.iterator();
                do
                {
                    if(!iterator2.hasNext())
                        break;
                    FacebookFriendInfo facebookfriendinfo1 = (FacebookFriendInfo)iterator2.next();
                    long l1 = facebookfriendinfo1.mUserId;
                    ContentValues contentvalues1 = new ContentValues();
                    acontentvalues[i] = contentvalues1;
                    i++;
                    contentvalues1.put("user_id", Long.valueOf(l1));
                    contentvalues1.put("cell", facebookfriendinfo1.mCellPhone);
                    contentvalues1.put("other", facebookfriendinfo1.mOtherPhone);
                    contentvalues1.put("email", facebookfriendinfo1.mContactEmail);
                    contentvalues1.put("first_name", facebookfriendinfo1.mFirstName);
                    contentvalues1.put("last_name", facebookfriendinfo1.mLastName);
                    contentvalues1.put("display_name", facebookfriendinfo1.mDisplayName);
                    contentvalues1.put("user_image_url", facebookfriendinfo1.mImageUrl);
                    contentvalues1.put("birthday_month", Integer.valueOf(facebookfriendinfo1.mBirthdayMonth));
                    contentvalues1.put("birthday_day", Integer.valueOf(facebookfriendinfo1.mBirthdayDay));
                    contentvalues1.put("birthday_year", Integer.valueOf(facebookfriendinfo1.mBirthdayYear));
                    contentvalues1.put("hash", Long.valueOf(facebookfriendinfo1.computeHash()));
                    if(facebookfriendinfo1.mImageUrl != null)
                        mNeedUserImageMap.put(Long.valueOf(l1), facebookfriendinfo1.mImageUrl);
                } while(true);
                contentresolver.bulkInsert(ConnectionsProvider.FRIENDS_CONTENT_URI, acontentvalues);
            }
            if(mUpdatedFriends.size() > 0)
            {
                ContentValues contentvalues = new ContentValues();
                Iterator iterator = mUpdatedFriends.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    FacebookFriendInfo facebookfriendinfo = (FacebookFriendInfo)iterator.next();
                    if(facebookfriendinfo.mDisplayName != null)
                    {
                        long l = facebookfriendinfo.mUserId;
                        contentvalues.clear();
                        contentvalues.put("first_name", facebookfriendinfo.mFirstName);
                        contentvalues.put("last_name", facebookfriendinfo.mLastName);
                        contentvalues.put("display_name", facebookfriendinfo.mDisplayName);
                        contentvalues.put("user_image_url", facebookfriendinfo.mImageUrl);
                        contentvalues.put("birthday_month", Integer.valueOf(facebookfriendinfo.mBirthdayMonth));
                        contentvalues.put("birthday_day", Integer.valueOf(facebookfriendinfo.mBirthdayDay));
                        contentvalues.put("birthday_year", Integer.valueOf(facebookfriendinfo.mBirthdayYear));
                        contentvalues.put("hash", Long.valueOf(facebookfriendinfo.computeHash()));
                        contentvalues.put("cell", facebookfriendinfo.mCellPhone);
                        contentvalues.put("other", facebookfriendinfo.mOtherPhone);
                        contentvalues.put("email", facebookfriendinfo.mContactEmail);
                        contentresolver.update(Uri.withAppendedPath(ConnectionsProvider.FRIEND_UID_CONTENT_URI, (new StringBuilder()).append("").append(l).toString()), contentvalues, null, null);
                        if(facebookfriendinfo.mImageUrl != null)
                            mNeedUserImageMap.put(Long.valueOf(facebookfriendinfo.mUserId), facebookfriendinfo.mImageUrl);
                    }
                } while(true);
            }
            if(mDeletedFriendIds.size() > 0)
            {
                StringBuffer stringbuffer = new StringBuffer(128);
                stringbuffer.append("user_id").append(" IN(");
                boolean flag = true;
                Iterator iterator1 = mDeletedFriendIds.iterator();
                while(iterator1.hasNext()) 
                {
                    Long long1 = (Long)iterator1.next();
                    if(flag)
                        flag = false;
                    else
                        stringbuffer.append(",");
                    stringbuffer.append(long1);
                }
                stringbuffer.append(")");
                contentresolver.delete(ConnectionsProvider.CONNECTIONS_CONTENT_URI, stringbuffer.toString(), null);
            }
        }
    }

    private void syncPages(Map map, Map map1)
    {
        ContentResolver contentresolver;
        HashMap hashmap;
        contentresolver = mContext.getContentResolver();
        hashmap = new HashMap(map1);
        java.util.Map.Entry entry;
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); hashmap.put(entry.getKey(), entry.getValue()))
            entry = (java.util.Map.Entry)iterator.next();

        if(!mCanceled) goto _L2; else goto _L1
_L1:
        return;
_L2:
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1 = new ArrayList();
        ArrayList arraylist2 = new ArrayList();
        if(detectPagesChanges(hashmap, arraylist, arraylist1, arraylist2) && !mCanceled)
        {
            if(arraylist.size() > 0)
            {
                int i = 0;
                ContentValues acontentvalues[] = new ContentValues[arraylist.size()];
                Iterator iterator3 = arraylist.iterator();
                do
                {
                    if(!iterator3.hasNext())
                        break;
                    FacebookPageProfile facebookpageprofile1 = (FacebookPageProfile)iterator3.next();
                    long l1 = facebookpageprofile1.mId;
                    ContentValues contentvalues1 = new ContentValues();
                    acontentvalues[i] = contentvalues1;
                    i++;
                    contentvalues1.put("user_id", Long.valueOf(l1));
                    contentvalues1.put("display_name", facebookpageprofile1.mDisplayName);
                    contentvalues1.put("user_image_url", facebookpageprofile1.mImageUrl);
                    contentvalues1.put("connection_type", Integer.valueOf(facebookpageprofile1.getConnectionType().ordinal()));
                    contentvalues1.put("hash", Long.valueOf(facebookpageprofile1.computeHash()));
                    if(facebookpageprofile1.mImageUrl != null)
                        mNeedUserImageMap.put(Long.valueOf(l1), facebookpageprofile1.mImageUrl);
                } while(true);
                contentresolver.bulkInsert(ConnectionsProvider.PAGES_CONTENT_URI, acontentvalues);
            }
            if(arraylist1.size() > 0)
            {
                ContentValues contentvalues = new ContentValues();
                Iterator iterator1 = arraylist1.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    FacebookPageProfile facebookpageprofile = (FacebookPageProfile)iterator1.next();
                    if(facebookpageprofile.mDisplayName != null)
                    {
                        long l = facebookpageprofile.mId;
                        contentvalues.clear();
                        contentvalues.put("display_name", facebookpageprofile.mDisplayName);
                        contentvalues.put("user_image_url", facebookpageprofile.mImageUrl);
                        contentvalues.put("connection_type", Integer.valueOf(facebookpageprofile.getConnectionType().ordinal()));
                        contentvalues.put("hash", Long.valueOf(facebookpageprofile.computeHash()));
                        contentresolver.update(Uri.withAppendedPath(ConnectionsProvider.PAGE_ID_CONTENT_URI, String.valueOf(l)), contentvalues, null, null);
                        if(facebookpageprofile.mImageUrl != null)
                            mNeedUserImageMap.put(Long.valueOf(facebookpageprofile.mId), facebookpageprofile.mImageUrl);
                    }
                } while(true);
            }
            if(arraylist2.size() > 0)
            {
                StringBuffer stringbuffer = new StringBuffer(128);
                stringbuffer.append("user_id").append(" IN(");
                boolean flag = true;
                Iterator iterator2 = arraylist2.iterator();
                while(iterator2.hasNext()) 
                {
                    Long long1 = (Long)iterator2.next();
                    if(flag)
                        flag = false;
                    else
                        stringbuffer.append(",");
                    stringbuffer.append(long1);
                }
                stringbuffer.append(")");
                contentresolver.delete(ConnectionsProvider.CONNECTIONS_CONTENT_URI, stringbuffer.toString(), null);
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void cancel(boolean flag)
    {
        if(mOp != null)
        {
            mOp.cancel(false);
            mOp = null;
        }
        mCanceled = true;
    }

    public Map getUsersWithoutImageMap()
    {
        return mNeedUserImageMap;
    }

    public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
        Object obj = exception;
        if(simulateSessionKeyError())
            obj = new FacebookApiException(102, "Invalid credentials");
        if(mCanceled)
            mListener.onOperationComplete(this, 400, "Canceled", ((Exception) (obj)));
        else
            mListener.onOperationComplete(this, i, s, ((Exception) (obj)));
    }

    public void onOperationProgress(ApiMethod apimethod, long l, long l1)
    {
    }

    public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
    {
        if(!mCanceled && i == 200)
        {
            FqlGetFriendsAndPages fqlgetfriendsandpages = (FqlGetFriendsAndPages)apimethod;
            syncFriends(fqlgetfriendsandpages.getFriends());
            syncPages(fqlgetfriendsandpages.getAdminPages(), fqlgetfriendsandpages.getFanPages());
        }
    }

    protected boolean simulateSessionKeyError()
    {
        return false;
    }

    public void start()
    {
        mOp = new FqlGetFriendsAndPages(mContext, mReqIntent, mSessionKey, this, mMyUserId);
        mOp.start();
    }

    private final String mAccount;
    private boolean mCanceled;
    private final List mDeletedFriendIds = new ArrayList();
    private final long mMyUserId;
    private final String mMyUsername;
    private final Map mNeedUserImageMap = new HashMap();
    private final List mNewFriends = new ArrayList();
    private FqlGetFriendsAndPages mOp;
    private final String mSessionKey;
    private final List mUpdatedFriends = new ArrayList();
}

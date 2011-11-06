// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailboxSync.java

package com.facebook.katana.service.method;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookMailboxThread;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.util.Log;
import java.util.*;

// Referenced classes of package com.facebook.katana.service.method:
//            ApiMethod, MailboxGetThreads, ApiMethodListener, BaseApiMethodListener, 
//            FqlGetProfile

public class MailboxSync extends ApiMethod
{
    private static interface FriendsQuery
    {

        public static final int INDEX_USER_DISPLAY_NAME = 1;
        public static final int INDEX_USER_ID = 0;
        public static final int INDEX_USER_IMAGE_URL = 2;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[3];
            as[0] = "user_id";
            as[1] = "display_name";
            as[2] = "user_image_url";
        }
    }

    private static interface ProfilesQuery
    {

        public static final int INDEX_PROFILE_DISPLAY_NAME = 1;
        public static final int INDEX_PROFILE_ID = 0;
        public static final int INDEX_PROFILE_IMAGE_URL = 2;
        public static final String PROFILES_PROJECTION[] = as;

        
        {
            String as[] = new String[3];
            as[0] = "id";
            as[1] = "display_name";
            as[2] = "profile_image_url";
        }
    }

    private static interface ThreadsQuery
    {

        public static final int INDEX_LAST_UPDATE = 1;
        public static final int INDEX_THREAD_ID;
        public static final String THREADS_PROJECTION[] = as;

        
        {
            String as[] = new String[2];
            as[0] = "tid";
            as[1] = "last_update";
        }
    }


    public MailboxSync(Context context, Intent intent, String s, int i, int j, int k, boolean flag, 
            long l, ApiMethodListener apimethodlistener)
    {
        super(context, intent, "GET", null, com.facebook.katana.Constants.URL.getApiUrl(context), apimethodlistener);
        mSessionKey = s;
        mFolder = i;
        mStart = j;
        mLimit = k;
        mSync = flag;
        mMyUserId = l;
    }

    private static void addParticipantIdsToList(List list, Set set)
    {
        for(Iterator iterator = list.iterator(); iterator.hasNext(); set.addAll(((FacebookMailboxThread)iterator.next()).getParticipants()));
    }

    private void buildThreadChangeSets(List list, List list1, List list2, List list3, Set set)
    {
        boolean flag;
        HashMap hashmap;
        StringBuffer stringbuffer;
        boolean flag1;
        Iterator iterator;
        FacebookMailboxThread facebookmailboxthread1;
        long l1;
        if(list3 != null)
            flag = true;
        else
            flag = false;
        hashmap = new HashMap();
        if(flag)
        {
            stringbuffer = null;
        } else
        {
            stringbuffer = new StringBuffer(256);
            stringbuffer.append("tid").append(" IN(");
        }
        flag1 = true;
        iterator = list.iterator();
_L11:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        facebookmailboxthread1 = (FacebookMailboxThread)iterator.next();
        hashmap.put(Long.valueOf(facebookmailboxthread1.getThreadId()), facebookmailboxthread1);
        if(!flag1) goto _L4; else goto _L3
_L3:
        flag1 = false;
_L5:
        if(!flag)
        {
            l1 = facebookmailboxthread1.getThreadId();
            stringbuffer.append(l1);
        }
        continue; /* Loop/switch isn't completed */
_L4:
        if(!flag)
            stringbuffer.append(",");
        if(true) goto _L5; else goto _L2
_L2:
        if(!flag)
            stringbuffer.append(")");
        String s;
        Cursor cursor;
        FacebookMailboxThread facebookmailboxthread;
        if(flag)
            s = null;
        else
            s = stringbuffer.toString();
        cursor = mContext.getContentResolver().query(MailboxProvider.getThreadsFolderUri(mFolder), ThreadsQuery.THREADS_PROJECTION, s, null, null);
        if(!cursor.moveToFirst()) goto _L7; else goto _L6
_L6:
        long l = cursor.getLong(0);
        facebookmailboxthread = (FacebookMailboxThread)hashmap.get(Long.valueOf(l));
        if(facebookmailboxthread != null)
        {
            if(facebookmailboxthread.getLastUpdate() != cursor.getLong(1))
                list2.add(facebookmailboxthread);
            hashmap.remove(Long.valueOf(l));
        } else
        {
            if(!flag)
                continue; /* Loop/switch isn't completed */
            list3.add(Long.valueOf(l));
        }
_L9:
        if(cursor.moveToNext()) goto _L6; else goto _L7
_L7:
        cursor.close();
        list1.addAll(hashmap.values());
        if(list1.size() > 0)
            log((new StringBuilder()).append("found ").append(list1.size()).append(" messages to add").toString());
        if(list2.size() > 0)
            log((new StringBuilder()).append("found ").append(list2.size()).append(" messages to change").toString());
        if(list3 != null && list3.size() > 0)
            log((new StringBuilder()).append("found ").append(list3.size()).append(" messages to delete").toString());
        addParticipantIdsToList(list, set);
        return;
        if($assertionsDisabled) goto _L9; else goto _L8
_L8:
        throw new AssertionError();
        if(true) goto _L11; else goto _L10
_L10:
    }

    private void commitChanges(List list, List list1, List list2, Map map)
    {
        ContentResolver contentresolver = mContext.getContentResolver();
        if(list.size() > 0)
        {
            int i = 0;
            ContentValues acontentvalues[] = new ContentValues[list.size()];
            String s = mContext.getString(0x7f0a00be);
            for(Iterator iterator4 = list.iterator(); iterator4.hasNext();)
            {
                acontentvalues[i] = ((FacebookMailboxThread)iterator4.next()).getContentValues(mFolder, mMyUserId, map, s);
                i++;
            }

            if(list.size() > 0)
                log((new StringBuilder()).append("adding ").append(list.size()).append(" messages").toString());
            contentresolver.bulkInsert(MailboxProvider.THREADS_CONTENT_URI, acontentvalues);
        }
        if(list1.size() > 0)
        {
            Uri uri = MailboxProvider.getThreadsTidFolderUri(mFolder);
            Uri uri1 = MailboxProvider.getMessagesFolderUri(mFolder);
            ContentValues contentvalues = new ContentValues();
            FacebookMailboxThread facebookmailboxthread1;
            for(Iterator iterator2 = list1.iterator(); iterator2.hasNext(); contentresolver.update(Uri.withAppendedPath(uri, (new StringBuilder()).append("").append(facebookmailboxthread1.getThreadId()).toString()), contentvalues, null, null))
            {
                facebookmailboxthread1 = (FacebookMailboxThread)iterator2.next();
                contentvalues.clear();
                contentvalues.put("subject", facebookmailboxthread1.getSubject());
                contentvalues.put("snippet", facebookmailboxthread1.getSnippet());
                contentvalues.put("other_party", Long.valueOf(facebookmailboxthread1.getOtherPartyUserId()));
                contentvalues.put("msg_count", Integer.valueOf(facebookmailboxthread1.getMsgCount()));
                contentvalues.put("unread_count", Integer.valueOf(facebookmailboxthread1.getUnreadCount()));
                contentvalues.put("last_update", Long.valueOf(facebookmailboxthread1.getLastUpdate()));
                contentvalues.put("participants", facebookmailboxthread1.getParticipantsString(map, mContext.getString(0x7f0a00be), Long.valueOf(mMyUserId)));
                if(list1.size() > 0)
                    log((new StringBuilder()).append("updating ").append(list1.size()).append(" messages").toString());
            }

            StringBuffer stringbuffer2 = new StringBuffer(256);
            stringbuffer2.append("tid");
            stringbuffer2.append(" IN(");
            boolean flag2 = true;
            Iterator iterator3 = list1.iterator();
            while(iterator3.hasNext()) 
            {
                FacebookMailboxThread facebookmailboxthread = (FacebookMailboxThread)iterator3.next();
                if(flag2)
                    flag2 = false;
                else
                    stringbuffer2.append(",");
                stringbuffer2.append(facebookmailboxthread.getThreadId());
            }
            stringbuffer2.append(")");
            contentresolver.delete(uri1, stringbuffer2.toString(), null);
        }
        if(list2 != null && list2.size() > 0)
        {
            StringBuffer stringbuffer = new StringBuffer(128);
            stringbuffer.append("tid");
            stringbuffer.append(" IN(");
            boolean flag = true;
            Iterator iterator = list2.iterator();
            while(iterator.hasNext()) 
            {
                Long long2 = (Long)iterator.next();
                if(flag)
                    flag = false;
                else
                    stringbuffer.append(",");
                stringbuffer.append(long2);
            }
            stringbuffer.append(")");
            contentresolver.delete(MailboxProvider.getThreadsFolderUri(mFolder), stringbuffer.toString(), null);
            StringBuffer stringbuffer1 = new StringBuffer(128);
            stringbuffer1.append("tid");
            stringbuffer1.append(" IN(");
            boolean flag1 = true;
            Iterator iterator1 = list2.iterator();
            while(iterator1.hasNext()) 
            {
                Long long1 = (Long)iterator1.next();
                if(flag1)
                    flag1 = false;
                else
                    stringbuffer1.append(",");
                stringbuffer1.append(long1);
            }
            stringbuffer1.append(")");
            log((new StringBuilder()).append("deleting ").append(list2.size()).append(" messages").toString());
            contentresolver.delete(MailboxProvider.getMessagesFolderUri(mFolder), stringbuffer1.toString(), null);
            contentresolver.delete(MailboxProvider.PROFILES_PRUNE_CONTENT_URI, null, null);
        }
    }

    private void findMissingUserIds(Set set, Set set1, Map map)
    {
        ContentResolver contentresolver = mContext.getContentResolver();
        set1.addAll(set);
        log((new StringBuilder()).append("looking for ").append(set1.size()).append(" users").toString());
        log((new StringBuilder()).append("have ").append(map.size()).append(" cached users").toString());
        set1.removeAll(map.keySet());
        int i = set1.size();
        if(i > 0)
        {
            String s = whereForPropertyWithValues("id", set);
            Cursor cursor = contentresolver.query(MailboxProvider.PROFILES_CONTENT_URI, ProfilesQuery.PROFILES_PROJECTION, s, null, null);
            if(cursor.moveToFirst())
                do
                {
                    Long long2 = Long.valueOf(cursor.getLong(0));
                    set1.remove(long2);
                    map.put(long2, new FacebookProfile(long2.longValue(), cursor.getString(1), cursor.getString(2), 0));
                } while(cursor.moveToNext());
            cursor.close();
            log((new StringBuilder()).append("found ").append(i - set1.size()).append(" users in the users table").toString());
            int j = set1.size();
            if(j > 0)
            {
                ArrayList arraylist = new ArrayList();
                String s1 = whereForPropertyWithValues("user_id", set1);
                Cursor cursor1 = contentresolver.query(ConnectionsProvider.FRIENDS_CONTENT_URI, FriendsQuery.PROJECTION, s1, null, null);
                if(cursor1.moveToFirst())
                    do
                    {
                        Long long1 = Long.valueOf(cursor1.getLong(0));
                        FacebookProfile facebookprofile = new FacebookProfile(long1.longValue(), cursor1.getString(1), cursor1.getString(2), 0);
                        map.put(long1, facebookprofile);
                        arraylist.add(facebookprofile);
                        set1.remove(long1);
                    } while(cursor1.moveToNext());
                cursor1.close();
                if(!$assertionsDisabled && arraylist.size() != set1.size() - j)
                    throw new AssertionError();
                log((new StringBuilder()).append("found ").append(arraylist.size()).append(" users in the friends table").toString());
                insertUsers(arraylist);
            }
        }
    }

    private Collection handleFetchResults(List list, Map map, Map map1, boolean flag)
    {
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1 = new ArrayList();
        ArrayList arraylist2;
        HashSet hashset;
        HashSet hashset1;
        if(mSync)
            arraylist2 = new ArrayList();
        else
            arraylist2 = null;
        hashset = new HashSet();
        hashset1 = new HashSet();
        this;
        JVM INSTR monitorenter ;
        buildThreadChangeSets(list, arraylist, arraylist1, arraylist2, hashset);
        findMissingUserIds(hashset, hashset1, map1);
        if(!flag && hashset1.size() > 0)
            Log.e("MailboxSync", "still missing users after fetching users!");
        if(!flag)
            saveNewUsers(map);
        if(hashset1.size() == 0 || !flag)
            commitChanges(arraylist, arraylist1, arraylist2, map1);
        return hashset1;
    }

    private void insertUsers(Collection collection)
    {
        int i = collection.size();
        if(i > 0)
        {
            ContentResolver contentresolver = mContext.getContentResolver();
            ContentValues acontentvalues[] = new ContentValues[i];
            int j = 0;
            FacebookProfile facebookprofile;
            ContentValues contentvalues;
            for(Iterator iterator = collection.iterator(); iterator.hasNext(); contentvalues.put("type", Integer.valueOf(facebookprofile.mType)))
            {
                facebookprofile = (FacebookProfile)iterator.next();
                contentvalues = new ContentValues();
                acontentvalues[j] = contentvalues;
                j++;
                contentvalues.put("id", Long.valueOf(facebookprofile.mId));
                contentvalues.put("display_name", facebookprofile.mDisplayName);
                contentvalues.put("profile_image_url", facebookprofile.mImageUrl);
            }

            log((new StringBuilder()).append("adding ").append(i).append(" users to the users table").toString());
            contentresolver.bulkInsert(MailboxProvider.PROFILES_CONTENT_URI, acontentvalues);
        }
    }

    private static void log(String s)
    {
        if(DEBUG)
            Log.e("MailboxSync", s);
    }

    private void saveNewUsers(Map map)
    {
        ContentResolver contentresolver = mContext.getContentResolver();
        String s = whereForPropertyWithValues("id", map.keySet());
        Cursor cursor = contentresolver.query(MailboxProvider.PROFILES_CONTENT_URI, ProfilesQuery.PROFILES_PROJECTION, s, null, null);
        HashMap hashmap = new HashMap(map);
        if(cursor.moveToFirst())
            do
                hashmap.remove(Long.valueOf(cursor.getLong(0)));
            while(cursor.moveToNext());
        insertUsers(hashmap.values());
    }

    private String whereForPropertyWithValues(String s, Collection collection)
    {
        StringBuffer stringbuffer = new StringBuffer(256);
        stringbuffer.append(s).append(" IN(");
        boolean flag = true;
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()) 
        {
            Long long1 = (Long)iterator.next();
            if(flag)
                flag = false;
            else
                stringbuffer.append(",");
            stringbuffer.append(long1);
        }
        stringbuffer.append(")");
        return stringbuffer.toString();
    }

    public void start()
    {
        BaseApiMethodListener baseapimethodlistener = new BaseApiMethodListener() {

            public void onOperationComplete(ApiMethod apimethod, int i, String s, Exception exception)
            {
                if(mMissingUserIds != null && mMissingUserIds.size() > 0)
                {
                    BaseApiMethodListener baseapimethodlistener1 = new BaseApiMethodListener() {

                        public void onOperationComplete(ApiMethod apimethod1, int j, String s1, Exception exception1)
                        {
                            mListener.onOperationComplete(_fld0, j, s1, exception1);
                        }

                        public void onProcessComplete(ApiMethod apimethod1, int j, String s1, Exception exception1)
                        {
                            Map map = ((FqlGetProfile)apimethod1).getProfiles();
                            if(map != null && exception1 == null)
                            {
                                mCachedUsers.putAll(map);
                                handleFetchResults(mThreads, map, mCachedUsers, false);
                            } else
                            {
                                Log.e("MailboxSync", (new StringBuilder()).append("failed to download thread participants: ").append(exception1).toString());
                            }
                        }

                        final _cls1 this$1;

                    
                    {
                        this$1 = _cls1.this;
                        super();
                    }
                    }
;
                    Intent intent = new Intent();
                    intent.putExtra("ApiMethod.secret", getIntent().getStringExtra("ApiMethod.secret"));
                    HashMap hashmap = new HashMap();
                    for(Iterator iterator = mMissingUserIds.iterator(); iterator.hasNext(); hashmap.put((Long)iterator.next(), null));
                    (new FqlGetProfile(mContext, intent, mSessionKey, baseapimethodlistener1, hashmap)).start();
                } else
                {
                    mListener.onOperationComplete(MailboxSync.this, i, s, exception);
                }
            }

            public void onProcessComplete(ApiMethod apimethod, int i, String s, Exception exception)
            {
                mThreads = ((MailboxGetThreads)apimethod).getMailboxThreads();
                if(mThreads != null && exception == null)
                    mMissingUserIds = handleFetchResults(mThreads, null, mCachedUsers, true);
                else
                    Log.e("MailboxSync", (new StringBuilder()).append("failed to download mailbox threads: ").append(exception).toString());
            }

            private final Map mCachedUsers = new HashMap();
            private Collection mMissingUserIds;
            private List mThreads;
            final MailboxSync this$0;



            
            {
                this$0 = MailboxSync.this;
                super();
            }
        }
;
        (new MailboxGetThreads(mContext, mReqIntent, mSessionKey, mFolder, mStart, mLimit, baseapimethodlistener)).start();
    }

    static final boolean $assertionsDisabled;
    private static boolean DEBUG = true;
    private final int mFolder;
    private final int mLimit;
    private final long mMyUserId;
    private final String mSessionKey;
    private final int mStart;
    private final boolean mSync;

    static 
    {
        boolean flag;
        if(!com/facebook/katana/service/method/MailboxSync.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }


}

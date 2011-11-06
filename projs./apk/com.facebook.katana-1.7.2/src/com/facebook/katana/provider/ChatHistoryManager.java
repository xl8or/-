// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChatHistoryManager.java

package com.facebook.katana.provider;

import android.content.*;
import android.database.Cursor;
import com.facebook.katana.model.FacebookChatMessage;
import java.util.*;

// Referenced classes of package com.facebook.katana.provider:
//            ChatHistoryProvider

public class ChatHistoryManager
{
    private static interface ChatConversationQuery
    {

        public static final int INDEX_FRIEND_UID = 0;
        public static final int INDEX_UNREAD_COUNT = 1;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[2];
            as[0] = "friend_uid";
            as[1] = "unread_count";
        }
    }

    private static interface ChatMessageQuery
    {

        public static final int INDEX_BODY = 1;
        public static final int INDEX_SENT = 0;
        public static final int INDEX_TIME_STAMP = 2;
        public static final String PROJECTION[] = as;

        
        {
            String as[] = new String[3];
            as[0] = "sent";
            as[1] = "body";
            as[2] = "timestamp";
        }
    }


    public ChatHistoryManager()
    {
    }

    public static void flushMessageHistory(Context context, long l, List list)
    {
        ArrayList arraylist = new ArrayList();
        if(list.size() > 0)
        {
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) 
            {
                FacebookChatMessage facebookchatmessage = (FacebookChatMessage)iterator.next();
                ContentValues contentvalues = new ContentValues();
                boolean flag = false;
                long l1;
                if(facebookchatmessage.mSenderUid == l)
                {
                    flag = true;
                    l1 = facebookchatmessage.mRecipientUid;
                } else
                {
                    l1 = facebookchatmessage.mSenderUid;
                }
                contentvalues.put("friend_uid", Long.valueOf(l1));
                contentvalues.put("sent", Boolean.valueOf(flag));
                contentvalues.put("body", facebookchatmessage.mBody);
                contentvalues.put("timestamp", facebookchatmessage.mTimestamp);
                arraylist.add(contentvalues);
            }
            context.getContentResolver().bulkInsert(ChatHistoryProvider.HISTORY_CONTENT_URI, (ContentValues[])arraylist.toArray(new ContentValues[0]));
        }
    }

    public static Map getActiveConversations(Context context)
    {
        HashMap hashmap = new HashMap();
        Cursor cursor = context.getContentResolver().query(ChatHistoryProvider.CONVERSATIONS_CONTENT_URI, ChatConversationQuery.PROJECTION, null, null, null);
        cursor.moveToFirst();
        for(; !cursor.isAfterLast(); cursor.moveToNext())
            hashmap.put(Long.valueOf(cursor.getLong(0)), new com.facebook.katana.model.FacebookChatUser.UnreadConversation(null, cursor.getInt(1)));

        cursor.close();
        return hashmap;
    }

    public static List getConversationHistory(Context context, long l, long l1)
    {
        String as[] = new String[1];
        as[0] = Long.toString(l);
        Cursor cursor = context.getContentResolver().query(ChatHistoryProvider.HISTORY_CONTENT_URI, ChatMessageQuery.PROJECTION, "friend_uid = ?", as, null);
        ArrayList arraylist = new ArrayList();
        cursor.moveToFirst();
        for(; !cursor.isAfterLast(); cursor.moveToNext())
        {
            long l2 = l;
            long l3 = l1;
            if(cursor.getInt(0) == 1)
            {
                l2 = l1;
                l3 = l;
            }
            arraylist.add(new FacebookChatMessage(l2, l3, cursor.getString(1), Long.valueOf(cursor.getLong(2))));
        }

        cursor.close();
        return arraylist;
    }

    public static void performHistoryCleanup(Context context)
    {
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        Long long1 = Long.valueOf(Long.valueOf(date.getTime()).longValue() - 0x240c8400L);
        context.getContentResolver().delete(ChatHistoryProvider.HISTORY_CONTENT_URI, (new StringBuilder()).append("timestamp < ").append(long1).toString(), null);
    }

    public static void updateActiveConversations(Context context, Map map)
    {
        if(map.size() > 0)
        {
            ContentResolver contentresolver = context.getContentResolver();
            contentresolver.delete(ChatHistoryProvider.CONVERSATIONS_CONTENT_URI, null, null);
            ContentValues acontentvalues[] = new ContentValues[map.size()];
            int i = 0;
            for(Iterator iterator = map.keySet().iterator(); iterator.hasNext();)
            {
                Long long1 = (Long)iterator.next();
                acontentvalues[i] = new ContentValues();
                acontentvalues[i].put("friend_uid", long1);
                acontentvalues[i].put("unread_count", Integer.valueOf(((com.facebook.katana.model.FacebookChatUser.UnreadConversation)map.get(long1)).mUnreadCount));
                i++;
            }

            contentresolver.bulkInsert(ChatHistoryProvider.CONVERSATIONS_CONTENT_URI, acontentvalues);
        }
    }
}

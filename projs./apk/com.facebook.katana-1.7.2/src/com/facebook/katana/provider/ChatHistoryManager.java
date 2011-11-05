package com.facebook.katana.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookChatMessage;
import com.facebook.katana.model.FacebookChatUser;
import com.facebook.katana.provider.ChatHistoryProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatHistoryManager {

   public ChatHistoryManager() {}

   public static void flushMessageHistory(Context var0, long var1, List<FacebookChatMessage> var3) {
      ArrayList var4 = new ArrayList();
      if(var3.size() > 0) {
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            FacebookChatMessage var6 = (FacebookChatMessage)var5.next();
            ContentValues var7 = new ContentValues();
            byte var8 = 0;
            long var9;
            if(var6.mSenderUid == var1) {
               var8 = 1;
               var9 = var6.mRecipientUid;
            } else {
               var9 = var6.mSenderUid;
            }

            Long var11 = Long.valueOf(var9);
            var7.put("friend_uid", var11);
            Boolean var12 = Boolean.valueOf((boolean)var8);
            var7.put("sent", var12);
            String var13 = var6.mBody;
            var7.put("body", var13);
            Long var14 = var6.mTimestamp;
            var7.put("timestamp", var14);
            var4.add(var7);
         }

         ContentResolver var16 = var0.getContentResolver();
         Uri var17 = ChatHistoryProvider.HISTORY_CONTENT_URI;
         ContentValues[] var18 = new ContentValues[0];
         ContentValues[] var19 = (ContentValues[])var4.toArray(var18);
         var16.bulkInsert(var17, var19);
      }
   }

   public static Map<Long, FacebookChatUser.UnreadConversation> getActiveConversations(Context var0) {
      HashMap var1 = new HashMap();
      ContentResolver var2 = var0.getContentResolver();
      Uri var3 = ChatHistoryProvider.CONVERSATIONS_CONTENT_URI;
      String[] var4 = ChatHistoryManager.ChatConversationQuery.PROJECTION;
      Object var5 = null;
      Object var6 = null;
      Cursor var7 = var2.query(var3, var4, (String)null, (String[])var5, (String)var6);

      boolean var13;
      for(boolean var8 = var7.moveToFirst(); !var7.isAfterLast(); var13 = var7.moveToNext()) {
         Long var9 = Long.valueOf(var7.getLong(0));
         int var10 = var7.getInt(1);
         FacebookChatUser.UnreadConversation var11 = new FacebookChatUser.UnreadConversation((String)null, var10);
         var1.put(var9, var11);
      }

      var7.close();
      return var1;
   }

   public static List<FacebookChatMessage> getConversationHistory(Context var0, long var1, long var3) {
      String[] var5 = new String[1];
      String var6 = Long.toString(var1);
      var5[0] = var6;
      ContentResolver var7 = var0.getContentResolver();
      Uri var8 = ChatHistoryProvider.HISTORY_CONTENT_URI;
      String[] var9 = ChatHistoryManager.ChatMessageQuery.PROJECTION;
      Cursor var10 = var7.query(var8, var9, "friend_uid = ?", var5, (String)null);
      ArrayList var11 = new ArrayList();

      boolean var21;
      for(boolean var12 = var10.moveToFirst(); !var10.isAfterLast(); var21 = var10.moveToNext()) {
         long var13 = var1;
         long var15 = var3;
         if(var10.getInt(0) == 1) {
            var13 = var3;
            var15 = var1;
         }

         String var17 = var10.getString(1);
         Long var18 = Long.valueOf(var10.getLong(2));
         FacebookChatMessage var19 = new FacebookChatMessage(var13, var15, var17, var18);
         var11.add(var19);
      }

      var10.close();
      return var11;
   }

   public static void performHistoryCleanup(Context var0) {
      Date var1 = new Date();
      var1.setHours(0);
      var1.setMinutes(0);
      var1.setSeconds(0);
      Long var2 = Long.valueOf(Long.valueOf(var1.getTime()).longValue() - 604800000L);
      ContentResolver var3 = var0.getContentResolver();
      Uri var4 = ChatHistoryProvider.HISTORY_CONTENT_URI;
      String var5 = "timestamp < " + var2;
      var3.delete(var4, var5, (String[])null);
   }

   public static void updateActiveConversations(Context var0, Map<Long, FacebookChatUser.UnreadConversation> var1) {
      if(var1.size() > 0) {
         ContentResolver var2 = var0.getContentResolver();
         Uri var3 = ChatHistoryProvider.CONVERSATIONS_CONTENT_URI;
         var2.delete(var3, (String)null, (String[])null);
         ContentValues[] var5 = new ContentValues[var1.size()];
         int var6 = 0;

         for(Iterator var7 = var1.keySet().iterator(); var7.hasNext(); ++var6) {
            Long var8 = (Long)var7.next();
            ContentValues var9 = new ContentValues();
            var5[var6] = var9;
            var5[var6].put("friend_uid", var8);
            ContentValues var10 = var5[var6];
            Integer var11 = Integer.valueOf(((FacebookChatUser.UnreadConversation)var1.get(var8)).mUnreadCount);
            var10.put("unread_count", var11);
         }

         Uri var12 = ChatHistoryProvider.CONVERSATIONS_CONTENT_URI;
         var2.bulkInsert(var12, var5);
      }
   }

   private interface ChatConversationQuery {

      int INDEX_FRIEND_UID = 0;
      int INDEX_UNREAD_COUNT = 1;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"friend_uid", "unread_count"};
         PROJECTION = var0;
      }
   }

   private interface ChatMessageQuery {

      int INDEX_BODY = 1;
      int INDEX_SENT = 0;
      int INDEX_TIME_STAMP = 2;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"sent", "body", "timestamp"};
         PROJECTION = var0;
      }
   }
}

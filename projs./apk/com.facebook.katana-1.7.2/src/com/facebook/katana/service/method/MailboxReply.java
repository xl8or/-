package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.Utils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class MailboxReply extends ApiMethod {

   private static final String[] USERS_PROJECTION;
   private final FacebookUser mSender;


   static {
      String[] var0 = new String[]{"_id"};
      USERS_PROJECTION = var0;
   }

   public MailboxReply(Context var1, Intent var2, String var3, FacebookUser var4, long var5, String var7, ApiMethodListener var8) {
      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "mailbox.reply", var9, var8);
      Map var14 = this.mParams;
      StringBuilder var15 = (new StringBuilder()).append("");
      long var16 = System.currentTimeMillis();
      String var18 = var15.append(var16).toString();
      var14.put("call_id", var18);
      this.mParams.put("session_key", var3);
      Map var21 = this.mParams;
      String var22 = "" + var5;
      var21.put("tid", var22);
      this.mParams.put("body", var7);
      this.mSender = var4;
   }

   private void updateContentProvider(long var1) {
      ContentResolver var3 = this.mContext.getContentResolver();
      long var4 = System.currentTimeMillis() / 1000L;
      String var6 = "" + var1;
      ContentValues var7 = new ContentValues();
      Uri var8 = Uri.withAppendedPath(MailboxProvider.OUTBOX_THREADS_TID_CONTENT_URI, var6);
      String[] var9 = MailboxReply.ThreadQuery.THREADS_PROJECTION;
      Cursor var10 = var3.query(var8, var9, (String)null, (String[])null, (String)null);
      if(var10 != null) {
         if(var10.moveToFirst()) {
            var7.clear();
            String var11 = Utils.buildSnippet((String)this.mParams.get("body"));
            var7.put("snippet", var11);
            Long var12 = Long.valueOf(this.mSender.mUserId);
            var7.put("other_party", var12);
            Long var13 = Long.valueOf(var4);
            var7.put("last_update", var13);
            Integer var14 = Integer.valueOf(var10.getInt(1) + 1);
            var7.put("msg_count", var14);
            Uri var15 = MailboxProvider.THREADS_CONTENT_URI;
            StringBuilder var16 = (new StringBuilder()).append("");
            int var17 = var10.getInt(0);
            String var18 = var16.append(var17).toString();
            Uri var19 = Uri.withAppendedPath(var15, var18);
            var3.update(var19, var7, (String)null, (String[])null);
            var7.clear();
            Integer var21 = Integer.valueOf(1);
            var7.put("folder", var21);
            Long var22 = Long.valueOf(var1);
            var7.put("tid", var22);
            Integer var23 = Integer.valueOf(var10.getInt(1));
            var7.put("mid", var23);
            Long var24 = Long.valueOf(this.mSender.mUserId);
            var7.put("author_id", var24);
            Long var25 = Long.valueOf(var4);
            var7.put("sent", var25);
            String var26 = (String)this.mParams.get("body");
            var7.put("body", var26);
            Uri var27 = MailboxProvider.MESSAGES_CONTENT_URI;
            var3.insert(var27, var7);
         }

         var10.close();
      }

      Uri var29 = Uri.withAppendedPath(MailboxProvider.INBOX_THREADS_TID_CONTENT_URI, var6);
      String[] var30 = MailboxReply.ThreadQuery.THREADS_PROJECTION;
      Cursor var31 = var3.query(var29, var30, (String)null, (String[])null, (String)null);
      if(var31 != null) {
         if(var31.moveToFirst()) {
            var7.clear();
            Long var32 = Long.valueOf(var4);
            var7.put("last_update", var32);
            Integer var33 = Integer.valueOf(var31.getInt(1) + 1);
            var7.put("msg_count", var33);
            Uri var34 = MailboxProvider.THREADS_CONTENT_URI;
            StringBuilder var35 = (new StringBuilder()).append("");
            int var36 = var31.getInt(0);
            String var37 = var35.append(var36).toString();
            Uri var38 = Uri.withAppendedPath(var34, var37);
            var3.update(var38, var7, (String)null, (String[])null);
            var7.clear();
            Integer var40 = Integer.valueOf(0);
            var7.put("folder", var40);
            Long var41 = Long.valueOf(var1);
            var7.put("tid", var41);
            Integer var42 = Integer.valueOf(var31.getInt(1));
            var7.put("mid", var42);
            Long var43 = Long.valueOf(this.mSender.mUserId);
            var7.put("author_id", var43);
            Long var44 = Long.valueOf(var4);
            var7.put("sent", var44);
            String var45 = (String)this.mParams.get("body");
            var7.put("body", var45);
            Uri var46 = MailboxProvider.MESSAGES_CONTENT_URI;
            var3.insert(var46, var7);
         }

         var31.close();
      }

      Uri var48 = MailboxProvider.PROFILES_ID_CONTENT_URI;
      StringBuilder var49 = (new StringBuilder()).append("");
      long var50 = this.mSender.mUserId;
      String var52 = var49.append(var50).toString();
      Uri var53 = Uri.withAppendedPath(var48, var52);
      String[] var54 = USERS_PROJECTION;
      Cursor var55 = var3.query(var53, var54, (String)null, (String[])null, (String)null);
      if(var55 != null) {
         if(var55.getCount() == 0) {
            var7.clear();
            Long var56 = Long.valueOf(this.mSender.mUserId);
            var7.put("id", var56);
            String var57 = this.mSender.getDisplayName();
            var7.put("display_name", var57);
            String var58 = this.mSender.mImageUrl;
            var7.put("profile_image_url", var58);
            Integer var59 = Integer.valueOf(0);
            var7.put("type", var59);
            Uri var60 = MailboxProvider.PROFILES_CONTENT_URI;
            var3.insert(var60, var7);
         }

         var55.close();
      }
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         long var3 = Long.parseLong(var1);
         this.updateContentProvider(var3);
      }
   }

   private interface ThreadQuery {

      int INDEX_ID = 0;
      int INDEX_MSG_COUNT = 1;
      String[] THREADS_PROJECTION;


      static {
         String[] var0 = new String[]{"_id", "msg_count"};
         THREADS_PROJECTION = var0;
      }
   }
}

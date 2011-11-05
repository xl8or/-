package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.Constants;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookMailboxThread;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.provider.MailboxProvider;
import com.facebook.katana.service.method.ApiMethod;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.Utils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class MailboxSend extends ApiMethod {

   private static final String[] USERS_PROJECTION;
   private final List<FacebookProfile> mRecipients;
   private final FacebookUser mSender;


   static {
      String[] var0 = new String[]{"_id"};
      USERS_PROJECTION = var0;
   }

   public MailboxSend(Context var1, Intent var2, String var3, FacebookUser var4, List<FacebookProfile> var5, String var6, String var7, ApiMethodListener var8) {
      String var9 = Constants.URL.getApiUrl(var1);
      super(var1, var2, "GET", "mailbox.send", var9, var8);
      Map var14 = this.mParams;
      StringBuilder var15 = (new StringBuilder()).append("");
      long var16 = System.currentTimeMillis();
      String var18 = var15.append(var16).toString();
      var14.put("call_id", var18);
      this.mParams.put("session_key", var3);
      Map var21 = this.mParams;
      String var22 = commaSeparatedUserIds(var5);
      var21.put("to", var22);
      this.mParams.put("subject", var6);
      this.mParams.put("body", var7);
      this.mSender = var4;
      this.mRecipients = var5;
   }

   private static String commaSeparatedUserIds(List<FacebookProfile> var0) {
      StringBuffer var1 = new StringBuffer(64);
      boolean var2 = true;
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         FacebookProfile var4 = (FacebookProfile)var3.next();
         if(!var2) {
            StringBuffer var5 = var1.append(",");
         } else {
            var2 = false;
         }

         long var6 = var4.mId;
         var1.append(var6);
      }

      return var1.toString();
   }

   private void updateContentProviders(long var1) {
      long var3 = System.currentTimeMillis() / 1000L;
      ContentResolver var5 = this.mContext.getContentResolver();
      String var6 = Utils.buildSnippet((String)this.mParams.get("body"));
      List var7 = userIdList(this.mRecipients);
      Map var8 = usersMap(this.mRecipients);
      String var9 = (String)this.mParams.get("subject");
      FacebookMailboxThread var12 = new FacebookMailboxThread(var1, var9, var6, 65535L, 1, 0, var3, 0L, var7);
      long var13 = this.mSender.mUserId;
      String var15 = this.mContext.getString(2131361982);
      ContentValues var16 = var12.getContentValues(1, var13, var8, var15);
      Uri var17 = MailboxProvider.THREADS_CONTENT_URI;
      Uri var21 = var5.insert(var17, var16);
      var16.clear();
      Integer var22 = Integer.valueOf(1);
      String var24 = "folder";
      var16.put(var24, var22);
      Long var26 = Long.valueOf(var1);
      String var28 = "tid";
      var16.put(var28, var26);
      Integer var30 = Integer.valueOf(0);
      String var32 = "mid";
      var16.put(var32, var30);
      Long var34 = Long.valueOf(this.mSender.mUserId);
      String var36 = "author_id";
      var16.put(var36, var34);
      Long var38 = Long.valueOf(var3);
      String var40 = "sent";
      var16.put(var40, var38);
      String var42 = (String)this.mParams.get("body");
      String var44 = "body";
      var16.put(var44, var42);
      Uri var46 = MailboxProvider.MESSAGES_CONTENT_URI;
      Uri var50 = var5.insert(var46, var16);
      StringBuilder var51 = (new StringBuilder()).append("");
      long var52 = this.mSender.mUserId;
      String var54 = var51.append(var52).toString();
      Uri var55 = MailboxProvider.PROFILES_ID_CONTENT_URI;
      Uri var57 = Uri.withAppendedPath(var55, var54);
      String[] var58 = USERS_PROJECTION;
      Cursor var59 = var5.query(var57, var58, (String)null, (String[])null, (String)null);
      if(var59 != null) {
         if(var59.getCount() == 0) {
            var16.clear();
            Long var60 = Long.valueOf(this.mSender.mUserId);
            String var62 = "id";
            var16.put(var62, var60);
            String var64 = this.mSender.getDisplayName();
            String var66 = "display_name";
            var16.put(var66, var64);
            String var68 = this.mSender.mImageUrl;
            String var70 = "profile_image_url";
            var16.put(var70, var68);
            Integer var72 = Integer.valueOf(0);
            String var74 = "type";
            var16.put(var74, var72);
            Uri var76 = MailboxProvider.PROFILES_CONTENT_URI;
            Uri var80 = var5.insert(var76, var16);
         }

         var59.close();
      }
   }

   private static List<Long> userIdList(List<FacebookProfile> var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         Long var3 = Long.valueOf(((FacebookProfile)var2.next()).mId);
         var1.add(var3);
      }

      return var1;
   }

   private static Map<Long, FacebookProfile> usersMap(List<FacebookProfile> var0) {
      HashMap var1 = new HashMap();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         FacebookProfile var3 = (FacebookProfile)var2.next();
         Long var4 = Long.valueOf(var3.mId);
         var1.put(var4, var3);
      }

      return var1;
   }

   protected void parseResponse(String var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      printJson(var1);
      if(var1.startsWith("{")) {
         JsonParser var2 = mJsonFactory.createJsonParser(var1);
         throw new FacebookApiException(var2);
      } else {
         long var3 = Long.parseLong(var1);
         this.updateContentProviders(var3);
      }
   }
}

package com.facebook.katana.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.provider.ConnectionsProvider;
import com.facebook.katana.util.Utils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FacebookUserPersistent extends FacebookUser {

   private String mHashCode;


   public FacebookUserPersistent() {}

   public FacebookUserPersistent(long var1, String var3, String var4, String var5, String var6) {
      super(var1, var3, var4, var5, var6);
   }

   public FacebookUserPersistent(JsonParser var1) throws JsonParseException, IllegalAccessException, InstantiationException, IOException, JMException {
      FacebookUser var2 = parseFromJSON(FacebookUser.class, var1);
   }

   public static FacebookUserPersistent readFromContentProvider(Context var0, long var1) {
      Uri var3 = ConnectionsProvider.FRIEND_UID_CONTENT_URI;
      String var4 = String.valueOf(var1);
      Uri var5 = Uri.withAppendedPath(var3, var4);
      ContentResolver var6 = var0.getContentResolver();
      String[] var7 = FacebookUserPersistent.FriendsQuery.PROJECTION;
      Object var8 = null;
      Object var9 = null;
      Cursor var10 = var6.query(var5, var7, (String)null, (String[])var8, (String)var9);
      FacebookUserPersistent var17;
      if(var10.moveToFirst()) {
         long var11 = var10.getLong(0);
         String var13 = var10.getString(1);
         String var14 = var10.getString(2);
         String var15 = var10.getString(3);
         String var16 = var10.getString(4);
         var17 = new FacebookUserPersistent(var11, var13, var14, var15, var16);
      } else {
         var17 = null;
      }

      var10.close();
      return var17;
   }

   public String computeHash() {
      if(this.mHashCode == null) {
         Object[] var1 = new Object[5];
         Long var2 = Long.valueOf(this.mUserId);
         var1[0] = var2;
         String var3 = this.mFirstName;
         var1[1] = var3;
         String var4 = this.mLastName;
         var1[2] = var4;
         String var5 = this.mDisplayName;
         var1[3] = var5;
         String var6 = this.mImageUrl;
         var1[4] = var6;
         String var7 = String.valueOf(Utils.hashItemsLong(var1));
         this.mHashCode = var7;
      }

      return this.mHashCode;
   }

   private interface FriendsQuery {

      int INDEX_USER_DISPLAY_NAME = 3;
      int INDEX_USER_FIRST_NAME = 1;
      int INDEX_USER_ID = 0;
      int INDEX_USER_IMAGE_URL = 4;
      int INDEX_USER_LAST_NAME = 2;
      String[] PROJECTION;


      static {
         String[] var0 = new String[]{"user_id", "first_name", "last_name", "display_name", "user_image_url"};
         PROJECTION = var0;
      }
   }
}

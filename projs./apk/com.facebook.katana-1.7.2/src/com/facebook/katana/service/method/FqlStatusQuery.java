package com.facebook.katana.service.method;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookStatus;
import com.facebook.katana.platform.PlatformStorage;
import com.facebook.katana.provider.UserStatusesProvider;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.PlatformUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FqlStatusQuery extends FqlQuery {

   public static final String FRIENDS_STATUS_QUERY = "SELECT uid,first_name,last_name,name,status,pic_square FROM user WHERE ((uid IN (SELECT target_id FROM connection WHERE source_id=%1$d AND target_type=\'user\' AND is_following=1)) AND (status.message != \'\')) ORDER BY status.time DESC LIMIT 25";
   private final String mMyUsername;
   private final List<FacebookStatus> mStatusList;


   public FqlStatusQuery(Context var1, Intent var2, String var3, String var4, String var5, ApiMethodListener var6) {
      super(var1, var2, var3, var4, var6);
      ArrayList var13 = new ArrayList();
      this.mStatusList = var13;
      this.mMyUsername = var5;
   }

   private static void saveUserStatuses(Context var0, String var1, List<FacebookStatus> var2) {
      ContentResolver var3 = var0.getContentResolver();
      Uri var4 = UserStatusesProvider.CONTENT_URI;
      var3.delete(var4, (String)null, (String[])null);
      ContentValues var6 = new ContentValues();
      Iterator var7 = var2.iterator();

      while(var7.hasNext()) {
         FacebookStatus var8 = (FacebookStatus)var7.next();
         var6.clear();
         Long var9 = Long.valueOf(var8.getUser().mUserId);
         var6.put("user_id", var9);
         String var10 = var8.getUser().mFirstName;
         var6.put("first_name", var10);
         String var11 = var8.getUser().mLastName;
         var6.put("last_name", var11);
         String var12 = var8.getUser().getDisplayName();
         var6.put("display_name", var12);
         String var13 = var8.getUser().mImageUrl;
         var6.put("user_pic", var13);
         String var14 = var8.getMessage();
         var6.put("message", var14);
         Long var15 = Long.valueOf(var8.getTime());
         var6.put("timestamp", var15);
         Uri var16 = UserStatusesProvider.CONTENT_URI;
         var3.insert(var16, var6);
      }

      if(PlatformUtils.platformStorageSupported(var0)) {
         PlatformStorage.insertStatuses(var0, var1, var2);
      }
   }

   public List<FacebookStatus> getStatusList() {
      return this.mStatusList;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      JsonToken var2 = var1.getCurrentToken();
      JsonToken var3 = JsonToken.START_OBJECT;
      if(var2 == var3) {
         FacebookApiException var4 = new FacebookApiException(var1);
         if(var4.getErrorCode() != -1) {
            throw var4;
         }
      } else {
         JsonToken var5 = JsonToken.START_ARRAY;
         if(var2 != var5) {
            throw new IOException("Malformed JSON");
         }

         var2 = var1.nextToken();

         while(true) {
            JsonToken var6 = JsonToken.END_ARRAY;
            if(var2 == var6) {
               break;
            }

            JsonToken var7 = JsonToken.START_OBJECT;
            if(var2 == var7) {
               List var8 = this.mStatusList;
               FacebookStatus var9 = new FacebookStatus(var1);
               var8.add(var9);
            }

            var2 = var1.nextToken();
         }
      }

      if(this.mStatusList.size() > 0) {
         Context var11 = this.mContext;
         String var12 = this.mMyUsername;
         List var13 = this.mStatusList;
         saveUserStatuses(var11, var12, var13);
      }
   }
}

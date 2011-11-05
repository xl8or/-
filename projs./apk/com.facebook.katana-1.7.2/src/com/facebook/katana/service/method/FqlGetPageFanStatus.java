package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetPageFanStatus extends FqlGeneratedQuery implements ApiMethodCallback {

   private boolean mFan;
   private final long mPageId;


   public FqlGetPageFanStatus(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5, long var7) {
      String var9 = buildWhereClause(var5, var7);
      super(var1, var2, var3, var4, "page_fan", var9, FqlGetPageFanStatus.UserPageRelationship.class);
      this.mPageId = var7;
   }

   public static String RequestPageFanStatus(Context var0, long var1) {
      AppSession var3 = AppSession.getActiveSession(var0, (boolean)0);
      String var4 = var3.getSessionInfo().sessionKey;
      long var5 = var3.getSessionInfo().userId;
      Object var8 = null;
      FqlGetPageFanStatus var11 = new FqlGetPageFanStatus(var0, (Intent)null, var4, (ApiMethodListener)var8, var5, var1);
      Object var15 = null;
      return var3.postToService(var0, var11, 1001, 1020, (Bundle)var15);
   }

   private static String buildWhereClause(long var0, long var2) {
      StringBuilder var4 = new StringBuilder();
      StringBuilder var5 = var4.append("uid=").append(var0).append(" and ").append("page_id=").append(var2);
      return var4.toString();
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         long var10 = this.mPageId;
         boolean var12 = this.mFan;
         var9.onGetPageFanStatusComplete(var1, var4, var5, var6, var7, var10, var12);
      }

   }

   public boolean isFan() {
      return this.mFan;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      this.mFan = (boolean)0;
      List var2 = JMParser.parseObjectListJson(var1, FqlGetPageFanStatus.UserPageRelationship.class);
      if(var2 != null) {
         if(var2.size() > 0) {
            this.mFan = (boolean)1;
         }
      }
   }

   private static class UserPageRelationship extends JMCachingDictDestination {

      @JMAutogen.InferredType(
         jsonFieldName = "page_id"
      )
      public final long pageId = 65535L;
      @JMAutogen.InferredType(
         jsonFieldName = "uid"
      )
      public final long uid = 65535L;


      private UserPageRelationship() {}
   }
}

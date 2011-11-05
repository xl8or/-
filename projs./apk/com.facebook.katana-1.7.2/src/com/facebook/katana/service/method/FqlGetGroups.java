package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookGroup;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.service.method.FqlMultiQuery;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetGroups extends FqlMultiQuery implements ApiMethodCallback {

   private List<FacebookGroup> mGroups;


   public FqlGetGroups(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
      LinkedHashMap var7 = buildQueries(var1, var2, var3, var5);
      super(var1, var2, var3, var7, var4);
   }

   public static String RequestGroups(Context var0) {
      AppSession var1 = AppSession.getActiveSession(var0, (boolean)0);
      String var2 = var1.getSessionInfo().sessionKey;
      long var3 = var1.getSessionInfo().userId;
      Object var6 = null;
      FqlGetGroups var7 = new FqlGetGroups(var0, (Intent)null, var2, (ApiMethodListener)var6, var3);
      Object var11 = null;
      return var1.postToService(var0, var7, 1001, 600, (Bundle)var11);
   }

   protected static LinkedHashMap<String, FqlQuery> buildQueries(Context var0, Intent var1, String var2, long var3) {
      LinkedHashMap var5 = new LinkedHashMap();
      FqlGetGroups.UserRelationship var11 = new FqlGetGroups.UserRelationship(var0, var1, var2, (ApiMethodListener)null, var3);
      var5.put("grouprel", var11);
      FqlGetGroups.Info var16 = new FqlGetGroups.Info(var0, var1, var2, (ApiMethodListener)null, "#grouprel");
      var5.put("groupinfo", var16);
      return var5;
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      switch(var3.getIntExtra("extended_type", -1)) {
      case 600:
         Iterator var8 = var1.getListeners().iterator();

         while(var8.hasNext()) {
            AppSessionListener var9 = (AppSessionListener)var8.next();
            List var10 = this.mGroups;
            var9.onGetGroupsComplete(var1, var4, var5, var6, var7, var10);
         }

         return;
      default:
      }
   }

   protected void parseJSON(JsonParser var1, String var2) throws FacebookApiException, JsonParseException, IOException, JMException {
      super.parseJSON(var1, var2);
      FqlGetGroups.UserRelationship var3 = (FqlGetGroups.UserRelationship)this.getQueryByName("grouprel");
      List var4 = ((FqlGetGroups.Info)this.getQueryByName("groupinfo")).mGroups;
      this.mGroups = var4;
      Iterator var5 = this.mGroups.iterator();

      while(var5.hasNext()) {
         FacebookGroup var6 = (FacebookGroup)var5.next();
         Map var7 = var3.mGroups;
         Long var8 = Long.valueOf(var6.mId);
         FqlGetGroups.UserRelationship.GroupUserRelationship var9 = (FqlGetGroups.UserRelationship.GroupUserRelationship)var7.get(var8);
         if(var9 != null) {
            int var10 = var9.mUnread;
            var6.setUnreadCount(var10);
         }
      }

   }

   static class UserRelationship extends FqlGeneratedQuery {

      Map<Long, FqlGetGroups.UserRelationship.GroupUserRelationship> mGroups;


      protected UserRelationship(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
         String var7 = buildWhereClause(var5);
         this(var1, var2, var3, var4, var7);
      }

      protected UserRelationship(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
         super(var1, var2, var3, var4, "group_member", var5, FqlGetGroups.UserRelationship.GroupUserRelationship.class);
         HashMap var12 = new HashMap();
         this.mGroups = var12;
      }

      protected static String buildWhereClause(long var0) {
         StringBuilder var2 = new StringBuilder();
         StringBuilder var3 = var2.append("uid=").append(var0);
         return var2.toString();
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
         List var2 = JMParser.parseObjectListJson(var1, FqlGetGroups.UserRelationship.GroupUserRelationship.class);
         if(var2 != null) {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               FqlGetGroups.UserRelationship.GroupUserRelationship var4 = (FqlGetGroups.UserRelationship.GroupUserRelationship)var3.next();
               Map var5 = this.mGroups;
               Long var6 = Long.valueOf(var4.mGid);
               var5.put(var6, var4);
            }

         }
      }

      static class GroupUserRelationship extends JMCachingDictDestination {

         @JMAutogen.InferredType(
            jsonFieldName = "gid"
         )
         public final long mGid = 65535L;
         @JMAutogen.InferredType(
            jsonFieldName = "unread"
         )
         public final int mUnread = 0;


         private GroupUserRelationship() {}
      }
   }

   static class Info extends FqlGeneratedQuery {

      List<FacebookGroup> mGroups;


      protected Info(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
         String var6 = buildWhereClause(var5);
         super(var1, var2, var3, var4, "group", var6, FacebookGroup.class);
      }

      protected static String buildWhereClause(String var0) {
         StringBuilder var1 = new StringBuilder();
         StringBuilder var2 = var1.append("gid IN (SELECT gid FROM ").append(var0).append(") AND version>0 ORDER BY update_time DESC");
         return var1.toString();
      }

      protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
         List var2 = JMParser.parseObjectListJson(var1, FacebookGroup.class);
         this.mGroups = var2;
      }
   }
}

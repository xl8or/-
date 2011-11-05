package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetUsersProfile;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class UsersSearch extends FqlGetUsersProfile {

   private static int mLastReqId = -1;
   private String mName;
   private final int mReqId;
   private int mStart;
   private int mTotal;


   public UsersSearch(Context var1, Intent var2, int var3, String var4, String var5, int var6, int var7, ApiMethodListener var8) {
      String var9 = buildQuery(var5, var6, var7);
      super(var1, var2, var4, var8, var9, FacebookUser.class);
      this.mStart = 0;
      this.mTotal = 0;
      this.mStart = var6;
      this.mName = var5;
      this.mReqId = var3;
      mLastReqId = var3;
   }

   private static String buildQuery(String var0, int var1, int var2) {
      StringBuilder var3 = new StringBuilder("contains(");
      StringUtils.appendEscapedFQLString(var3, var0);
      StringBuilder var5 = var3.append(") ").append("LIMIT ").append(var1).append(",").append(var2);
      return var3.toString();
   }

   private static boolean isValidNameQuery(String var0) {
      boolean var1;
      if(var0 != null && var0.trim().length() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void saveSearchResults(Map<Long, FacebookUser> param1) {
      // $FF: Couldn't be decompiled
   }

   public int getStartResults() {
      return this.mStart;
   }

   public int getTotalResults() {
      return this.mTotal;
   }

   protected void parseJSON(JsonParser var1) throws JsonParseException, IOException, FacebookApiException, JMException {
      super.parseJSON(var1);
      Map var2 = this.getUsers();
      this.saveSearchResults(var2);
   }

   public void start() {
      if(isValidNameQuery(this.mName)) {
         super.start();
      } else {
         (new UsersSearch.DeleteThread()).start();
      }
   }

   public class DeleteThread extends Thread {

      public DeleteThread() {}

      public void run() {
         UsersSearch var1 = UsersSearch.this;
         HashMap var2 = new HashMap();
         var1.saveSearchResults(var2);
         UsersSearch.this.onComplete(200, (String)null, (Exception)null);
      }
   }
}

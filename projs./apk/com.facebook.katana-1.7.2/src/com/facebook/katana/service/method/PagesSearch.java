package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGetPages;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class PagesSearch extends FqlGetPages {

   private static long mLastReqTimeMillis = 65535L;
   private String mName;
   private final long mReqTimeMillis;
   private int mStart;
   private int mTotal;


   public PagesSearch(Context var1, Intent var2, String var3, String var4, int var5, int var6, ApiMethodListener var7) {
      String var8 = buildQuery(var4, var5, var6);
      super(var1, var2, var3, var7, var8, FacebookPage.class);
      this.mStart = 0;
      this.mTotal = 0;
      this.mStart = var5;
      this.mName = var4;
      mLastReqTimeMillis = System.currentTimeMillis();
      long var14 = mLastReqTimeMillis;
      this.mReqTimeMillis = var14;
   }

   public static String RequestPagesSearch(Context var0, String var1, int var2, int var3) {
      AppSession var4 = AppSession.getActiveSession(var0, (boolean)0);
      String var5 = var4.getSessionInfo().sessionKey;
      Object var10 = null;
      PagesSearch var11 = new PagesSearch(var0, (Intent)null, var5, var1, var2, var3, (ApiMethodListener)var10);
      Object var15 = null;
      return var4.postToService(var0, var11, 1001, 1020, (Bundle)var15);
   }

   private static String buildQuery(String var0, int var1, int var2) {
      StringBuilder var3 = new StringBuilder("contains(");
      StringUtils.appendEscapedFQLString(var3, var0);
      StringBuilder var5 = var3.append(") ").append(" AND is_community_page!=\'true\' ").append("LIMIT ").append(var1).append(",").append(var2);
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

   private void saveSearchResults(Map<Long, FacebookPage> param1) {
      // $FF: Couldn't be decompiled
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      Iterator var8 = var1.getListeners().iterator();

      while(var8.hasNext()) {
         AppSessionListener var9 = (AppSessionListener)var8.next();
         int var10 = this.mStart;
         int var11 = this.mTotal;
         var9.onPagesSearchComplete(var1, var4, var5, var6, var7, var10, var11);
      }

   }

   public int getStartResults() {
      return this.mStart;
   }

   public int getTotalResults() {
      return this.mTotal;
   }

   protected void parseJSON(JsonParser var1) throws JsonParseException, IOException, FacebookApiException, JMException {
      super.parseJSON(var1);
      Map var2 = this.getPages();
      this.saveSearchResults(var2);
   }

   public void start() {
      if(isValidNameQuery(this.mName)) {
         super.start();
      } else {
         (new PagesSearch.DeleteThread()).start();
      }
   }

   public class DeleteThread extends Thread {

      public DeleteThread() {}

      public void run() {
         PagesSearch var1 = PagesSearch.this;
         HashMap var2 = new HashMap();
         var1.saveSearchResults(var2);
         PagesSearch.this.onComplete(200, (String)null, (Exception)null);
      }
   }
}

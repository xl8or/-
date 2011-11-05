package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookPage;
import com.facebook.katana.service.method.ApiMethodCallback;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetPages extends FqlGeneratedQuery implements ApiMethodCallback {

   private static final String TAG = "FqlGetPages";
   private Class<? extends FacebookPage> mCls;
   private Map<Long, FacebookPage> mPages;


   public FqlGetPages(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, Class<? extends FacebookPage> var6) {
      super(var1, var2, var3, var4, "page", var5, var6);
      this.mCls = var6;
   }

   public static <typeClass extends FacebookPage> String RequestPageInfo(Context var0, String var1, Class<typeClass> var2) {
      AppSession var3 = AppSession.getActiveSession(var0, (boolean)0);
      String var4 = var3.getSessionInfo().sessionKey;
      Object var6 = null;
      FqlGetPages var9 = new FqlGetPages(var0, (Intent)null, var4, (ApiMethodListener)var6, var1, var2);
      Object var13 = null;
      return var3.postToService(var0, var9, 1001, 1020, (Bundle)var13);
   }

   public void executeCallbacks(AppSession var1, Context var2, Intent var3, String var4, int var5, String var6, Exception var7) {
      FacebookPage var8 = null;
      short var10 = 200;
      if(var5 == var10) {
         Iterator var11 = this.mPages.values().iterator();
         if(var11.hasNext()) {
            var8 = (FacebookPage)var11.next();
         }
      }

      Iterator var12 = var1.getListeners().iterator();

      while(var12.hasNext()) {
         AppSessionListener var13 = (AppSessionListener)var12.next();
         if(var8 != null) {
            long var14 = ((FacebookPage)var8).mPageId;
            var13.onPagesGetInfoComplete(var1, var4, var5, var6, var7, var14, var8);
         } else {
            var13.onPagesGetInfoComplete(var1, var4, var5, var6, var7, 65535L, (Object)null);
         }
      }

   }

   public Map<Long, FacebookPage> getPages() {
      return Collections.unmodifiableMap(this.mPages);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      Class var2 = this.mCls;
      List var3 = JMParser.parseObjectListJson(var1, var2);
      if(var3 != null) {
         HashMap var4 = new HashMap();
         this.mPages = var4;
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            FacebookPage var6 = (FacebookPage)var5.next();
            Map var7 = this.mPages;
            Long var8 = Long.valueOf(var6.mPageId);
            var7.put(var8, var6);
         }

      }
   }
}

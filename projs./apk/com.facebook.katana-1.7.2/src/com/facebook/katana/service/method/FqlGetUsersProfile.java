package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetUsersProfile extends FqlGeneratedQuery {

   private static final String TAG = "FqlGetUsersProfile";
   private final Class<? extends FacebookUser> mCls;
   private final boolean mFillEmptyProfiles;
   private final Object mOpaque;
   private final Map<Long, FacebookUser> mProfiles;


   public FqlGetUsersProfile(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, Class<? extends FacebookUser> var6) {
      super(var1, var2, var3, var4, "user", var5, var6);
      LinkedHashMap var14 = new LinkedHashMap();
      this.mProfiles = var14;
      this.mOpaque = null;
      this.mCls = var6;
      this.mFillEmptyProfiles = (boolean)0;
   }

   public FqlGetUsersProfile(Context var1, Intent var2, String var3, ApiMethodListener var4, Map<Long, FacebookUser> var5, Class<? extends FacebookUser> var6) {
      String var7 = buildWhereClause(var5);
      super(var1, var2, var3, var4, "user", var7, var6);
      this.mProfiles = var5;
      this.mOpaque = null;
      this.mCls = var6;
      this.mFillEmptyProfiles = (boolean)1;
   }

   public FqlGetUsersProfile(Context var1, Intent var2, String var3, ApiMethodListener var4, Map<Long, FacebookUser> var5, Object var6) {
      String var7 = buildWhereClause(var5);
      super(var1, var2, var3, var4, "user", var7, FacebookUser.class);
      this.mProfiles = var5;
      this.mOpaque = var6;
      this.mCls = FacebookUser.class;
      this.mFillEmptyProfiles = (boolean)1;
   }

   private static String buildWhereClause(Map<Long, FacebookUser> var0) {
      StringBuilder var1 = new StringBuilder("uid IN(");
      Object[] var2 = new Object[1];
      Set var3 = var0.keySet();
      var2[0] = var3;
      StringUtils.join(var1, ",", (StringUtils.StringProcessor)null, var2);
      StringBuilder var4 = var1.append(")");
      return var1.toString();
   }

   public Object getOpaque() {
      return this.mOpaque;
   }

   public Map<Long, FacebookUser> getUsers() {
      return this.mProfiles;
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      Class var2 = this.mCls;
      List var3 = JMParser.parseObjectListJson(var1, var2);
      Iterator var4;
      if(var3 != null) {
         var4 = var3.iterator();

         while(var4.hasNext()) {
            FacebookUser var5 = (FacebookUser)var4.next();
            Map var6 = this.mProfiles;
            Long var7 = Long.valueOf(var5.mUserId);
            var6.put(var7, var5);
         }
      }

      if(this.mFillEmptyProfiles) {
         var4 = this.mProfiles.entrySet().iterator();

         while(var4.hasNext()) {
            Entry var9 = (Entry)var4.next();
            if(var9.getValue() == null) {
               Class var10 = this.mCls;
               String var11 = this.mContext.getString(2131361895);
               FacebookUser var12 = FacebookUser.newInstance(var10, var11);
               Map var13 = this.mProfiles;
               Object var14 = var9.getKey();
               var13.put(var14, var12);
            }
         }

      }
   }
}

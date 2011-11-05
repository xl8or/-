package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.model.FacebookApiException;
import com.facebook.katana.model.FriendList;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlGeneratedQuery;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FqlGetFriendLists extends FqlGeneratedQuery {

   private List<FriendList> mFriendLists;


   protected FqlGetFriendLists(Context var1, Intent var2, String var3, ApiMethodListener var4, long var5) {
      String var7 = buildWhereClause(var5);
      this(var1, var2, var3, var4, var7);
   }

   protected FqlGetFriendLists(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5) {
      super(var1, var2, var3, var4, "friendlist", var5, FriendList.class);
   }

   protected static String buildWhereClause(long var0) {
      StringBuilder var2 = new StringBuilder();
      StringBuilder var3 = var2.append("owner=").append(var0).append(" ORDER BY name");
      return var2.toString();
   }

   public List<FriendList> getFriendLists() {
      return Collections.unmodifiableList(this.mFriendLists);
   }

   protected void parseJSON(JsonParser var1) throws FacebookApiException, JsonParseException, IOException, JMException {
      List var2 = JMParser.parseObjectListJson(var1, FriendList.class);
      this.mFriendLists = var2;
   }
}

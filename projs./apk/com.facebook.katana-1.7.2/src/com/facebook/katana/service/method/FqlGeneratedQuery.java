package com.facebook.katana.service.method;

import android.content.Context;
import android.content.Intent;
import com.facebook.katana.service.method.ApiMethodListener;
import com.facebook.katana.service.method.FqlQuery;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMDictDestination;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class FqlGeneratedQuery extends FqlQuery {

   protected static Map<Class<? extends JMDictDestination>, Set<String>> fieldListCache = new HashMap();


   public FqlGeneratedQuery(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, String var6, Class<? extends JMDictDestination> var7) {
      Set var8 = getJsonFieldsFromClass(var7);
      this(var1, var2, var3, var4, var5, var6, var8);
   }

   public FqlGeneratedQuery(Context var1, Intent var2, String var3, ApiMethodListener var4, String var5, String var6, Set<String> var7) {
      String var8 = buildQuery(var5, var6, var7);
      super(var1, var2, var3, var8, var4);
   }

   protected static String buildQuery(String var0, String var1, Set<String> var2) {
      StringBuffer var3 = new StringBuffer("SELECT ");
      Object[] var4 = new Object[]{var2};
      String var5 = StringUtils.join(",", var4);
      StringBuffer var6 = var3.append(var5).append(" FROM ").append(var0).append(" WHERE ").append(var1);
      return var3.toString();
   }

   protected static Set<String> getJsonFieldsFromClass(Class<? extends JMDictDestination> var0) {
      Set var1 = (Set)fieldListCache.get(var0);
      Set var2;
      if(var1 != null) {
         var2 = var1;
      } else {
         Set var3 = JMAutogen.getJsonFieldsFromClass(var0);
         fieldListCache.put(var0, var3);
         var2 = var3;
      }

      return var2;
   }
}

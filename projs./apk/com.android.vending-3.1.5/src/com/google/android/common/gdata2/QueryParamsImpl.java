package com.google.android.common.gdata2;

import android.text.TextUtils;
import android.util.Log;
import com.google.wireless.gdata2.client.QueryParams;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class QueryParamsImpl extends QueryParams {

   private final Map<String, String> mParams;


   public QueryParamsImpl() {
      HashMap var1 = new HashMap();
      this.mParams = var1;
   }

   public void clear() {
      this.setEntryId((String)null);
      this.mParams.clear();
   }

   public String generateQueryUrl(String var1) {
      if(!TextUtils.isEmpty(this.getEntryId()) || !this.mParams.isEmpty()) {
         if(!TextUtils.isEmpty(this.getEntryId())) {
            if(!this.mParams.isEmpty()) {
               throw new IllegalStateException("Cannot set both an entry ID and other query paramters.");
            }

            StringBuilder var2 = (new StringBuilder()).append(var1).append('/');
            String var3 = this.getEntryId();
            var1 = var2.append(var3).toString();
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append(var1);
            Set var6 = this.mParams.keySet();
            boolean var7 = true;
            if(var1.contains("?")) {
               var7 = false;
            } else {
               StringBuilder var16 = var4.append('?');
            }

            String var14;
            for(Iterator var8 = var6.iterator(); var8.hasNext(); var4.append(var14)) {
               String var9 = (String)var8.next();
               if(var7) {
                  var7 = false;
               } else {
                  StringBuilder var17 = var4.append('&');
               }

               var4.append(var9);
               StringBuilder var11 = var4.append('=');
               String var12 = (String)this.mParams.get(var9);

               String var13;
               try {
                  var13 = URLEncoder.encode(var12, "UTF-8");
               } catch (UnsupportedEncodingException var20) {
                  int var19 = Log.w("QueryParamsImpl", "UTF-8 not supported -- should not happen.  Using default encoding.", var20);
                  var14 = URLEncoder.encode(var12);
                  continue;
               }

               var14 = var13;
            }

            var1 = var4.toString();
         }
      }

      return var1;
   }

   public String getParamValue(String var1) {
      String var2;
      if(!this.mParams.containsKey(var1)) {
         var2 = null;
      } else {
         var2 = (String)this.mParams.get(var1);
      }

      return var2;
   }

   public void setParamValue(String var1, String var2) {
      this.mParams.put(var1, var2);
   }
}

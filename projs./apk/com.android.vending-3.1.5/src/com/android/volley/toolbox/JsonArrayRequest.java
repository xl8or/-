package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionException;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import org.json.JSONArray;
import org.json.JSONException;

public class JsonArrayRequest extends JsonRequest<JSONArray> {

   public JsonArrayRequest(String var1, Response.Listener<JSONArray> var2, Response.ErrorListener var3) {
      super(var1, (String)null, var2, var3);
   }

   protected Response<JSONArray> parseNetworkResponse(NetworkResponse var1) {
      Response var4;
      Response var5;
      try {
         byte[] var2 = var1.data;
         String var3 = new String(var2);
         var4 = Response.success(new JSONArray(var3), (Cache.Entry)null);
      } catch (JSONException var9) {
         Response.ErrorCode var7 = Response.ErrorCode.NETWORK;
         NoConnectionException var8 = new NoConnectionException();
         var5 = Response.error(var7, (String)null, var8);
         return var5;
      }

      var5 = var4;
      return var5;
   }
}

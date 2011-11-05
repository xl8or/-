package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionException;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectRequest extends JsonRequest<JSONObject> {

   public JsonObjectRequest(String var1, JSONObject var2, Response.Listener<JSONObject> var3, Response.ErrorListener var4) {
      String var5 = var2.toString();
      super(var1, var5, var3, var4);
   }

   protected Response<JSONObject> parseNetworkResponse(NetworkResponse var1) {
      Response var4;
      Response var5;
      try {
         byte[] var2 = var1.data;
         String var3 = new String(var2);
         var4 = Response.success(new JSONObject(var3), (Cache.Entry)null);
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

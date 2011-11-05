package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;

public class StringRequest extends Request<String> {

   private final Response.Listener<String> mListener;


   public StringRequest(String var1, Response.Listener<String> var2, Response.ErrorListener var3) {
      super(var1, var3);
      this.mListener = var2;
   }

   protected void deliverResponse(String var1) {
      this.mListener.onResponse(var1);
   }

   protected Response<String> parseNetworkResponse(NetworkResponse var1) {
      String var4;
      try {
         byte[] var2 = var1.data;
         String var3 = HttpHeaderParser.parseCharset(var1.headers);
         var4 = new String(var2, var3);
      } catch (UnsupportedEncodingException var8) {
         byte[] var7 = var1.data;
         var4 = new String(var7);
      }

      Cache.Entry var5 = HttpHeaderParser.parseCacheHeaders(var1);
      return Response.success(var4, var5);
   }
}

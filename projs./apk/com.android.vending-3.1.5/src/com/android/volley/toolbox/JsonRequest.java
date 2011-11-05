package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

public abstract class JsonRequest<T extends Object> extends Request<T> {

   private static final String PROTOCOL_CHARSET = "utf-8";
   private static final String PROTOCOL_CONTENT_TYPE;
   private final Response.Listener<T> mListener;
   private final String mRequestBody;


   static {
      Object[] var0 = new Object[]{"utf-8"};
      PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", var0);
   }

   public JsonRequest(String var1, String var2, Response.Listener<T> var3, Response.ErrorListener var4) {
      super(var1, var4);
      this.mListener = var3;
      this.mRequestBody = var2;
   }

   protected void deliverResponse(T var1) {
      this.mListener.onResponse(var1);
   }

   public byte[] getPostBody() {
      // $FF: Couldn't be decompiled
   }

   public String getPostBodyContentType() {
      return PROTOCOL_CONTENT_TYPE;
   }

   protected abstract Response<T> parseNetworkResponse(NetworkResponse var1);
}

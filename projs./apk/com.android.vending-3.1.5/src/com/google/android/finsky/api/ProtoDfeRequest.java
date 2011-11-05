package com.google.android.finsky.api;

import com.android.volley.Response;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.DfeRequest;
import com.google.protobuf.micro.MessageMicro;

public class ProtoDfeRequest<T extends MessageMicro> extends DfeRequest<T> {

   private static final String CONTENT_TYPE = "application/x-protobuf";
   private final MessageMicro mRequest;


   public ProtoDfeRequest(String var1, MessageMicro var2, DfeApiContext var3, Class<T> var4, Response.Listener<T> var5, Response.ErrorListener var6) {
      super(var1, var3, var4, var5, var6);
      this.mRequest = var2;
      this.setShouldCache((boolean)0);
   }

   public byte[] getPostBody() {
      return this.mRequest.toByteArray();
   }

   public String getPostBodyContentType() {
      return "application/x-protobuf";
   }
}

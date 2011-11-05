package com.google.android.finsky.api;

import com.android.volley.Response;
import com.google.android.finsky.api.DfeApiContext;
import com.google.android.finsky.api.DfeRequest;
import com.google.protobuf.micro.MessageMicro;

public class PaginatedDfeRequest<T extends MessageMicro> extends DfeRequest<T> implements Response.Listener<T> {

   private final PaginatedDfeRequest.PaginatedListener<T> mListener;


   public PaginatedDfeRequest(String var1, DfeApiContext var2, Class<T> var3, PaginatedDfeRequest.PaginatedListener<T> var4, Response.ErrorListener var5) {
      super(var1, var2, var3, (Response.Listener)null, var5);
      this.mListener = var4;
      this.setListener(this);
   }

   public void onResponse(T var1) {
      this.mListener.onResponse(var1);
   }

   public interface PaginatedListener<T extends Object> {

      void onResponse(T var1);
   }
}

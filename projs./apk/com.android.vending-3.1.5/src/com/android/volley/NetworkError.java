package com.android.volley;

import com.android.volley.NetworkResponse;

public class NetworkError extends Exception {

   public String displayError;
   public final NetworkResponse networkResponse;


   public NetworkError() {
      this.networkResponse = null;
   }

   public NetworkError(NetworkResponse var1) {
      this.networkResponse = var1;
   }

   public NetworkError(String var1) {
      super(var1);
      this.networkResponse = null;
   }

   public NetworkError(String var1, Throwable var2) {
      super(var1, var2);
      this.networkResponse = null;
   }
}

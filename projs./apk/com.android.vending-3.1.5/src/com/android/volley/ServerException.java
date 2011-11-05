package com.android.volley;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;

public class ServerException extends NetworkError {

   public ServerException() {}

   public ServerException(NetworkResponse var1) {
      super(var1);
   }

   public ServerException(String var1) {
      super(var1);
   }
}

package com.android.volley;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;

public interface ResponseDelivery {

   void discardBefore(int var1);

   void postError(Request<?> var1, Response.ErrorCode var2, NetworkError var3);

   void postResponse(Request<?> var1, Response<?> var2);
}

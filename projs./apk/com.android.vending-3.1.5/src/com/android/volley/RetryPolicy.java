package com.android.volley;

import com.android.volley.NetworkError;

public interface RetryPolicy {

   int getCurrentRetryCount();

   int getCurrentTimeout();

   void retry(NetworkError var1) throws NetworkError;
}

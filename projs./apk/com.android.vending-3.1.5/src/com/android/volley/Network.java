package com.android.volley;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;

public interface Network {

   NetworkResponse performRequest(Request<?> var1) throws NetworkError;
}

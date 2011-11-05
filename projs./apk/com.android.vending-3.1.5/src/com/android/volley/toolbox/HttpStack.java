package com.android.volley.toolbox;

import com.android.volley.AuthFailureException;
import com.android.volley.Request;
import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpResponse;

public interface HttpStack {

   HttpResponse performRequest(Request<?> var1, Map<String, String> var2) throws IOException, AuthFailureException;
}

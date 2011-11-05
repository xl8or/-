package com.android.volley.toolbox;

import android.content.Context;
import com.android.volley.AuthFailureException;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.GoogleHttpClient;
import com.android.volley.toolbox.HttpClientStack;
import com.google.android.finsky.config.G;
import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpResponse;

public class GoogleHttpClientStack extends HttpClientStack {

   public GoogleHttpClientStack(Context var1) {
      GoogleHttpClient var2 = new GoogleHttpClient(var1, "unused/0", (boolean)1);
      this(var2);
   }

   private GoogleHttpClientStack(GoogleHttpClient var1) {
      super(var1);
      if(VolleyLog.DEBUG) {
         if(((Boolean)G.enableSensitiveLogging.get()).booleanValue()) {
            String var2 = VolleyLog.TAG;
            var1.enableCurlLogging(var2, 2);
         }
      }
   }

   public HttpResponse performRequest(Request<?> var1, Map<String, String> var2) throws IOException, AuthFailureException {
      return super.performRequest(var1, var2);
   }
}

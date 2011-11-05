package com.android.exchange.utility;

import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionManager {

   private static HttpsURLConnection _conn = null;


   public HttpsURLConnectionManager() {}

   public static void CloseConnection() {
      if(_conn != null) {
         _conn.disconnect();
         _conn = null;
      }
   }

   public static HttpsURLConnection getHttpsURLConnection(URL var0) throws IOException {
      if(_conn != null) {
         _conn.disconnect();
         _conn = null;
      }

      _conn = (HttpsURLConnection)var0.openConnection();
      return _conn;
   }
}

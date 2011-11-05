package com.android.volley.toolbox.elegant;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.DefaultRequestDirector;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

public class ElegantRequestDirector extends DefaultRequestDirector {

   public ElegantRequestDirector(HttpRequestExecutor var1, ClientConnectionManager var2, ConnectionReuseStrategy var3, ConnectionKeepAliveStrategy var4, HttpRoutePlanner var5, HttpProcessor var6, HttpRequestRetryHandler var7, RedirectHandler var8, AuthenticationHandler var9, AuthenticationHandler var10, UserTokenHandler var11, HttpParams var12) {
      super(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   protected void establishRoute(HttpRoute var1, HttpContext var2) throws HttpException, IOException {
      ManagedClientConnection var3 = this.managedConn;
      int var4 = HttpConnectionParams.getSoTimeout(this.params);
      var3.setSocketTimeout(var4);
      super.establishRoute(var1, var2);
   }
}

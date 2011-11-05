package org.apache.commons.httpclient.params;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.params.HttpConnectionParams;

public class HttpConnectionManagerParams extends HttpConnectionParams {

   public static final String MAX_HOST_CONNECTIONS = "http.connection-manager.max-per-host";
   public static final String MAX_TOTAL_CONNECTIONS = "http.connection-manager.max-total";


   public HttpConnectionManagerParams() {}

   public int getDefaultMaxConnectionsPerHost() {
      HostConfiguration var1 = HostConfiguration.ANY_HOST_CONFIGURATION;
      return this.getMaxConnectionsPerHost(var1);
   }

   public int getMaxConnectionsPerHost(HostConfiguration var1) {
      Map var2 = (Map)this.getParameter("http.connection-manager.max-per-host");
      int var3;
      if(var2 == null) {
         var3 = 2;
      } else {
         Integer var4 = (Integer)var2.get(var1);
         if(var4 == null) {
            HostConfiguration var5 = HostConfiguration.ANY_HOST_CONFIGURATION;
            if(var1 != var5) {
               HostConfiguration var6 = HostConfiguration.ANY_HOST_CONFIGURATION;
               var3 = this.getMaxConnectionsPerHost(var6);
               return var3;
            }
         }

         if(var4 == null) {
            var3 = 2;
         } else {
            var3 = var4.intValue();
         }
      }

      return var3;
   }

   public int getMaxTotalConnections() {
      return this.getIntParameter("http.connection-manager.max-total", 20);
   }

   public void setDefaultMaxConnectionsPerHost(int var1) {
      HostConfiguration var2 = HostConfiguration.ANY_HOST_CONFIGURATION;
      this.setMaxConnectionsPerHost(var2, var1);
   }

   public void setMaxConnectionsPerHost(HostConfiguration var1, int var2) {
      if(var2 <= 0) {
         throw new IllegalArgumentException("maxHostConnections must be greater than 0");
      } else {
         Map var3 = (Map)this.getParameter("http.connection-manager.max-per-host");
         HashMap var4;
         if(var3 == null) {
            var4 = new HashMap();
         } else {
            var4 = new HashMap(var3);
         }

         Integer var5 = Integer.valueOf(var2);
         var4.put(var1, var5);
         this.setParameter("http.connection-manager.max-per-host", var4);
      }
   }

   public void setMaxTotalConnections(int var1) {
      this.setIntParameter("http.connection-manager.max-total", var1);
   }
}

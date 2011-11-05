package org.apache.commons.httpclient.params;

import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpParams;

public class HostParams extends DefaultHttpParams {

   public static final String DEFAULT_HEADERS = "http.default-headers";


   public HostParams() {}

   public HostParams(HttpParams var1) {
      super(var1);
   }

   public String getVirtualHost() {
      return (String)this.getParameter("http.virtual-host");
   }

   public void setVirtualHost(String var1) {
      this.setParameter("http.virtual-host", var1);
   }
}

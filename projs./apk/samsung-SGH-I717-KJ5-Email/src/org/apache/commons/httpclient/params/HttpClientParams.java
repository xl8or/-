package org.apache.commons.httpclient.params;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;

public class HttpClientParams extends HttpMethodParams {

   public static final String ALLOW_CIRCULAR_REDIRECTS = "http.protocol.allow-circular-redirects";
   public static final String CONNECTION_MANAGER_CLASS = "http.connection-manager.class";
   public static final String CONNECTION_MANAGER_TIMEOUT = "http.connection-manager.timeout";
   public static final String MAX_REDIRECTS = "http.protocol.max-redirects";
   public static final String PREEMPTIVE_AUTHENTICATION = "http.authentication.preemptive";
   private static final String[] PROTOCOL_STRICTNESS_PARAMETERS;
   public static final String REJECT_RELATIVE_REDIRECT = "http.protocol.reject-relative-redirect";


   static {
      String[] var0 = new String[]{"http.protocol.reject-relative-redirect", "http.protocol.allow-circular-redirects"};
      PROTOCOL_STRICTNESS_PARAMETERS = var0;
   }

   public HttpClientParams() {}

   public HttpClientParams(HttpParams var1) {
      super(var1);
   }

   public Class getConnectionManagerClass() {
      return (Class)this.getParameter("http.connection-manager.class");
   }

   public long getConnectionManagerTimeout() {
      return this.getLongParameter("http.connection-manager.timeout", 0L);
   }

   public boolean isAuthenticationPreemptive() {
      return this.getBooleanParameter("http.authentication.preemptive", (boolean)0);
   }

   public void makeLenient() {
      super.makeLenient();
      String[] var1 = PROTOCOL_STRICTNESS_PARAMETERS;
      Boolean var2 = Boolean.FALSE;
      this.setParameters(var1, var2);
   }

   public void makeStrict() {
      super.makeStrict();
      String[] var1 = PROTOCOL_STRICTNESS_PARAMETERS;
      Boolean var2 = Boolean.TRUE;
      this.setParameters(var1, var2);
   }

   public void setAuthenticationPreemptive(boolean var1) {
      this.setBooleanParameter("http.authentication.preemptive", var1);
   }

   public void setConnectionManagerClass(Class var1) {
      this.setParameter("http.connection-manager.class", var1);
   }

   public void setConnectionManagerTimeout(long var1) {
      this.setLongParameter("http.connection-manager.timeout", var1);
   }
}

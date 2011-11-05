package org.apache.commons.httpclient.methods;

import org.apache.commons.httpclient.HttpMethodBase;

public class TraceMethod extends HttpMethodBase {

   public TraceMethod(String var1) {
      super(var1);
      this.setFollowRedirects((boolean)0);
   }

   public String getName() {
      return "TRACE";
   }

   public void recycle() {
      super.recycle();
      this.setFollowRedirects((boolean)0);
   }
}

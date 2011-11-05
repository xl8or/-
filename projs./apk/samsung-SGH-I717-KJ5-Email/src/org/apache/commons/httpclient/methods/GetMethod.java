package org.apache.commons.httpclient.methods;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GetMethod extends HttpMethodBase {

   private static final Log LOG = LogFactory.getLog(GetMethod.class);


   public GetMethod() {
      this.setFollowRedirects((boolean)1);
   }

   public GetMethod(String var1) {
      super(var1);
      LOG.trace("enter GetMethod(String)");
      this.setFollowRedirects((boolean)1);
   }

   public String getName() {
      return "GET";
   }

   public void recycle() {
      LOG.trace("enter GetMethod.recycle()");
      super.recycle();
      this.setFollowRedirects((boolean)1);
   }
}

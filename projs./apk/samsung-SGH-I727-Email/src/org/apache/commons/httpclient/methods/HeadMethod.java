package org.apache.commons.httpclient.methods;

import java.io.IOException;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.ProtocolException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HeadMethod extends HttpMethodBase {

   private static final Log LOG = LogFactory.getLog(HeadMethod.class);


   public HeadMethod() {
      this.setFollowRedirects((boolean)1);
   }

   public HeadMethod(String var1) {
      super(var1);
      this.setFollowRedirects((boolean)1);
   }

   public int getBodyCheckTimeout() {
      return this.getParams().getIntParameter("http.protocol.head-body-timeout", -1);
   }

   public String getName() {
      return "HEAD";
   }

   protected void readResponseBody(HttpState var1, HttpConnection var2) throws HttpException, IOException {
      LOG.trace("enter HeadMethod.readResponseBody(HttpState, HttpConnection)");
      int var3 = this.getParams().getIntParameter("http.protocol.head-body-timeout", -1);
      if(var3 < 0) {
         this.responseBodyConsumed();
      } else {
         if(LOG.isDebugEnabled()) {
            Log var4 = LOG;
            String var5 = "Check for non-compliant response body. Timeout in " + var3 + " ms";
            var4.debug(var5);
         }

         byte var7;
         label25: {
            byte var6;
            try {
               var6 = var2.isResponseAvailable(var3);
            } catch (IOException var9) {
               LOG.debug("An IOException occurred while testing if a response was available, we will assume one is not.", var9);
               var7 = 0;
               break label25;
            }

            var7 = var6;
         }

         if(var7 != 0) {
            if(this.getParams().isParameterTrue("http.protocol.reject-head-body")) {
               throw new ProtocolException("Body content may not be sent in response to HTTP HEAD request");
            } else {
               LOG.warn("Body content returned in response to HTTP HEAD");
               super.readResponseBody(var1, var2);
            }
         }
      }
   }

   public void recycle() {
      super.recycle();
      this.setFollowRedirects((boolean)1);
   }

   public void setBodyCheckTimeout(int var1) {
      this.getParams().setIntParameter("http.protocol.head-body-timeout", var1);
   }
}

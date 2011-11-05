package org.apache.commons.httpclient.methods;

import java.io.IOException;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ExpectContinueMethod extends HttpMethodBase {

   private static final Log LOG = LogFactory.getLog(ExpectContinueMethod.class);


   public ExpectContinueMethod() {}

   public ExpectContinueMethod(String var1) {
      super(var1);
   }

   protected void addRequestHeaders(HttpState var1, HttpConnection var2) throws IOException, HttpException {
      LOG.trace("enter ExpectContinueMethod.addRequestHeaders(HttpState, HttpConnection)");
      super.addRequestHeaders(var1, var2);
      boolean var3;
      if(this.getRequestHeader("Expect") != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if(this.getParams().isParameterTrue("http.protocol.expect-continue")) {
         HttpVersion var4 = this.getEffectiveVersion();
         HttpVersion var5 = HttpVersion.HTTP_1_1;
         if(var4.greaterEquals(var5) && this.hasRequestContent()) {
            if(var3) {
               return;
            }

            this.setRequestHeader("Expect", "100-continue");
            return;
         }
      }

      if(var3) {
         this.removeRequestHeader("Expect");
      }
   }

   public boolean getUseExpectHeader() {
      return this.getParams().getBooleanParameter("http.protocol.expect-continue", (boolean)0);
   }

   protected abstract boolean hasRequestContent();

   public void setUseExpectHeader(boolean var1) {
      this.getParams().setBooleanParameter("http.protocol.expect-continue", var1);
   }
}

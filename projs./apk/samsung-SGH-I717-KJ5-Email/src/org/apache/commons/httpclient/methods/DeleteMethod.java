package org.apache.commons.httpclient.methods;

import org.apache.commons.httpclient.HttpMethodBase;

public class DeleteMethod extends HttpMethodBase {

   public DeleteMethod() {}

   public DeleteMethod(String var1) {
      super(var1);
   }

   public String getName() {
      return "DELETE";
   }
}

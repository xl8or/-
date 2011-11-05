package org.apache.commons.httpclient.methods;

import org.apache.commons.httpclient.methods.EntityEnclosingMethod;

public class PutMethod extends EntityEnclosingMethod {

   public PutMethod() {}

   public PutMethod(String var1) {
      super(var1);
   }

   public String getName() {
      return "PUT";
   }
}

package com.kenai.jbosh;

import com.kenai.jbosh.BodyQName;
import java.util.HashMap;
import java.util.Map;

final class BodyParserResults {

   private final Map<BodyQName, String> attrs;


   BodyParserResults() {
      HashMap var1 = new HashMap();
      this.attrs = var1;
   }

   void addBodyAttributeValue(BodyQName var1, String var2) {
      this.attrs.put(var1, var2);
   }

   Map<BodyQName, String> getAttributes() {
      return this.attrs;
   }
}

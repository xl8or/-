package org.codehaus.jackson.impl;

import org.codehaus.jackson.impl.JsonWriteContext;

final class RootWContext extends JsonWriteContext {

   public RootWContext() {
      super(0, (JsonWriteContext)null);
   }

   protected void appendDesc(StringBuilder var1) {
      StringBuilder var2 = var1.append("/");
   }

   public String getCurrentName() {
      return null;
   }

   public int writeFieldName(String var1) {
      return 4;
   }

   public int writeValue() {
      int var1 = this._index + 1;
      this._index = var1;
      byte var2;
      if(this._index == 0) {
         var2 = 0;
      } else {
         var2 = 3;
      }

      return var2;
   }
}

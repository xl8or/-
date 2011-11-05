package org.codehaus.jackson.impl;

import org.codehaus.jackson.impl.JsonWriteContext;

final class ArrayWContext extends JsonWriteContext {

   public ArrayWContext(JsonWriteContext var1) {
      super(1, var1);
   }

   protected void appendDesc(StringBuilder var1) {
      StringBuilder var2 = var1.append('[');
      int var3 = this.getCurrentIndex();
      var1.append(var3);
      StringBuilder var5 = var1.append(']');
   }

   public String getCurrentName() {
      return null;
   }

   public int writeFieldName(String var1) {
      return 4;
   }

   public int writeValue() {
      int var1 = this._index;
      int var2 = this._index + 1;
      this._index = var2;
      byte var3;
      if(var1 < 0) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      return var3;
   }
}

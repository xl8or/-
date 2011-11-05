package org.codehaus.jackson.impl;

import org.codehaus.jackson.impl.JsonWriteContext;

final class ObjectWContext extends JsonWriteContext {

   protected String _currentName = null;
   protected boolean _expectValue = 0;


   public ObjectWContext(JsonWriteContext var1) {
      super(2, var1);
   }

   protected void appendDesc(StringBuilder var1) {
      StringBuilder var2 = var1.append('{');
      if(this._currentName != null) {
         StringBuilder var3 = var1.append('\"');
         String var4 = this._currentName;
         var1.append(var4);
         StringBuilder var6 = var1.append('\"');
      } else {
         StringBuilder var8 = var1.append('?');
      }

      StringBuilder var7 = var1.append(']');
   }

   public String getCurrentName() {
      return this._currentName;
   }

   public int writeFieldName(String var1) {
      byte var2;
      if(this._currentName != null) {
         var2 = 4;
      } else {
         this._currentName = var1;
         if(this._index < 0) {
            var2 = 0;
         } else {
            var2 = 1;
         }
      }

      return var2;
   }

   public int writeValue() {
      byte var1;
      if(this._currentName == null) {
         var1 = 5;
      } else {
         this._currentName = null;
         int var2 = this._index + 1;
         this._index = var2;
         var1 = 2;
      }

      return var1;
   }
}

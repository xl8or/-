package org.codehaus.jackson.impl;

import org.codehaus.jackson.JsonLocation;
import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.util.CharTypes;

public final class JsonReadContext extends JsonStreamContext {

   JsonReadContext _child = null;
   protected int _columnNr;
   protected String _currentName;
   protected int _lineNr;
   protected final JsonReadContext _parent;


   public JsonReadContext(JsonReadContext var1, int var2, int var3, int var4) {
      super(var2);
      this._parent = var1;
      this._lineNr = var3;
      this._columnNr = var4;
   }

   public static JsonReadContext createRootContext(int var0, int var1) {
      return new JsonReadContext((JsonReadContext)null, 0, var0, var1);
   }

   public final JsonReadContext createChildArrayContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if(var3 == null) {
         var3 = new JsonReadContext(this, 1, var1, var2);
         this._child = var3;
      } else {
         var3.reset(1, var1, var2);
      }

      return var3;
   }

   public final JsonReadContext createChildObjectContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if(var3 == null) {
         var3 = new JsonReadContext(this, 2, var1, var2);
         this._child = var3;
      } else {
         var3.reset(2, var1, var2);
      }

      return var3;
   }

   public final boolean expectComma() {
      int var1 = this._index + 1;
      this._index = var1;
      boolean var2;
      if(this._type != 0 && var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final String getCurrentName() {
      return this._currentName;
   }

   public final JsonReadContext getParent() {
      return this._parent;
   }

   public final JsonLocation getStartLocation(Object var1) {
      int var2 = this._lineNr;
      int var3 = this._columnNr;
      return new JsonLocation(var1, 65535L, var2, var3);
   }

   protected final void reset(int var1, int var2, int var3) {
      this._type = var1;
      this._index = -1;
      this._lineNr = var2;
      this._columnNr = var3;
      this._currentName = null;
   }

   public void setCurrentName(String var1) {
      this._currentName = var1;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder(64);
      switch(this._type) {
      case 0:
         StringBuilder var2 = var1.append("/");
         break;
      case 1:
         StringBuilder var3 = var1.append('[');
         int var4 = this.getCurrentIndex();
         var1.append(var4);
         StringBuilder var6 = var1.append(']');
         break;
      case 2:
         StringBuilder var7 = var1.append('{');
         if(this._currentName != null) {
            StringBuilder var8 = var1.append('\"');
            String var9 = this._currentName;
            CharTypes.appendQuoted(var1, var9);
            StringBuilder var10 = var1.append('\"');
         } else {
            StringBuilder var12 = var1.append('?');
         }

         StringBuilder var11 = var1.append(']');
      }

      return var1.toString();
   }
}

package org.codehaus.jackson;


public abstract class JsonStreamContext {

   protected static final int TYPE_ARRAY = 1;
   protected static final int TYPE_OBJECT = 2;
   protected static final int TYPE_ROOT;
   protected int _index;
   protected int _type;


   public JsonStreamContext(int var1) {
      this._type = var1;
      this._index = -1;
   }

   public final int getCurrentIndex() {
      int var1;
      if(this._index < 0) {
         var1 = 0;
      } else {
         var1 = this._index;
      }

      return var1;
   }

   public abstract String getCurrentName();

   public final int getEntryCount() {
      return this._index + 1;
   }

   public abstract JsonStreamContext getParent();

   public final String getTypeDesc() {
      String var1;
      switch(this._type) {
      case 0:
         var1 = "ROOT";
         break;
      case 1:
         var1 = "ARRAY";
         break;
      case 2:
         var1 = "OBJECT";
         break;
      default:
         var1 = "?";
      }

      return var1;
   }

   public final boolean inArray() {
      boolean var1;
      if(this._type == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean inObject() {
      boolean var1;
      if(this._type == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean inRoot() {
      boolean var1;
      if(this._type == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}

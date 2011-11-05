package org.codehaus.jackson.impl;

import org.codehaus.jackson.JsonStreamContext;
import org.codehaus.jackson.impl.ArrayWContext;
import org.codehaus.jackson.impl.ObjectWContext;
import org.codehaus.jackson.impl.RootWContext;

public abstract class JsonWriteContext extends JsonStreamContext {

   public static final int STATUS_EXPECT_NAME = 5;
   public static final int STATUS_EXPECT_VALUE = 4;
   public static final int STATUS_OK_AFTER_COLON = 2;
   public static final int STATUS_OK_AFTER_COMMA = 1;
   public static final int STATUS_OK_AFTER_SPACE = 3;
   public static final int STATUS_OK_AS_IS;
   JsonWriteContext _childArray = null;
   JsonWriteContext _childObject = null;
   protected final JsonWriteContext _parent;


   protected JsonWriteContext(int var1, JsonWriteContext var2) {
      super(var1);
      this._parent = var2;
   }

   public static JsonWriteContext createRootContext() {
      return new RootWContext();
   }

   protected abstract void appendDesc(StringBuilder var1);

   public final JsonWriteContext createChildArrayContext() {
      Object var1 = this._childArray;
      if(var1 == null) {
         var1 = new ArrayWContext(this);
         this._childArray = (JsonWriteContext)var1;
      } else {
         ((JsonWriteContext)var1)._index = -1;
      }

      return (JsonWriteContext)var1;
   }

   public final JsonWriteContext createChildObjectContext() {
      Object var1 = this._childObject;
      if(var1 == null) {
         var1 = new ObjectWContext(this);
         this._childObject = (JsonWriteContext)var1;
      } else {
         ((JsonWriteContext)var1)._index = -1;
      }

      return (JsonWriteContext)var1;
   }

   public final JsonWriteContext getParent() {
      return this._parent;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder(64);
      this.appendDesc(var1);
      return var1.toString();
   }

   public abstract int writeFieldName(String var1);

   public abstract int writeValue();
}

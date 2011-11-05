package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.ValueNode;

public final class POJONode extends ValueNode {

   final Object _value;


   public POJONode(Object var1) {
      this._value = var1;
   }

   public boolean equals(Object var1) {
      byte var2;
      if(var1 == this) {
         var2 = 1;
      } else if(var1 == null) {
         var2 = 0;
      } else {
         Class var3 = var1.getClass();
         Class var4 = this.getClass();
         if(var3 != var4) {
            var2 = 0;
         } else {
            POJONode var7 = (POJONode)var1;
            if(this._value == null) {
               if(var7._value == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }
            } else {
               Object var5 = this._value;
               Object var6 = var7._value;
               var2 = var5.equals(var6);
            }
         }
      }

      return (boolean)var2;
   }

   public Object getPojo() {
      return this._value;
   }

   public String getValueAsText() {
      return null;
   }

   public int hashCode() {
      return this._value.hashCode();
   }

   public boolean isPojo() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      if(this._value == null) {
         var1.writeNull();
      } else {
         Object var3 = this._value;
         var1.writeObject(var3);
      }
   }

   public String toString() {
      return String.valueOf(this._value);
   }
}

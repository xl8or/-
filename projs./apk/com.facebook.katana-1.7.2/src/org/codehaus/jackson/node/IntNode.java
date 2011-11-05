package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.io.NumberOutput;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.NumericNode;

public final class IntNode extends NumericNode {

   final int _value;


   public IntNode(int var1) {
      this._value = var1;
   }

   public static IntNode valueOf(int var0) {
      return new IntNode(var0);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 == null) {
         var2 = false;
      } else {
         Class var3 = var1.getClass();
         Class var4 = this.getClass();
         if(var3 != var4) {
            var2 = false;
         } else {
            int var5 = ((IntNode)var1)._value;
            int var6 = this._value;
            if(var5 == var6) {
               var2 = true;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }

   public BigInteger getBigIntegerValue() {
      return BigInteger.valueOf((long)this._value);
   }

   public BigDecimal getDecimalValue() {
      return BigDecimal.valueOf((long)this._value);
   }

   public double getDoubleValue() {
      return (double)this._value;
   }

   public int getIntValue() {
      return this._value;
   }

   public long getLongValue() {
      return (long)this._value;
   }

   public Number getNumberValue() {
      return Integer.valueOf(this._value);
   }

   public String getValueAsText() {
      return NumberOutput.toString(this._value);
   }

   public int hashCode() {
      return this._value;
   }

   public boolean isInt() {
      return true;
   }

   public boolean isIntegralNumber() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      int var3 = this._value;
      var1.writeNumber(var3);
   }
}

package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.io.NumberOutput;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.NumericNode;

public final class DoubleNode extends NumericNode {

   final double _value;


   public DoubleNode(double var1) {
      this._value = var1;
   }

   public static DoubleNode valueOf(double var0) {
      return new DoubleNode(var0);
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
            double var5 = ((DoubleNode)var1)._value;
            double var7 = this._value;
            if(var5 == var7) {
               var2 = true;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }

   public BigInteger getBigIntegerValue() {
      return this.getDecimalValue().toBigInteger();
   }

   public BigDecimal getDecimalValue() {
      return BigDecimal.valueOf(this._value);
   }

   public double getDoubleValue() {
      return this._value;
   }

   public int getIntValue() {
      return (int)this._value;
   }

   public long getLongValue() {
      return (long)this._value;
   }

   public Number getNumberValue() {
      return Double.valueOf(this._value);
   }

   public String getValueAsText() {
      return NumberOutput.toString(this._value);
   }

   public int hashCode() {
      long var1 = Double.doubleToLongBits(this._value);
      int var3 = (int)var1;
      return (int)(var1 >> 32) ^ var3;
   }

   public boolean isDouble() {
      return true;
   }

   public boolean isFloatingPointNumber() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      double var3 = this._value;
      var1.writeNumber(var3);
   }
}

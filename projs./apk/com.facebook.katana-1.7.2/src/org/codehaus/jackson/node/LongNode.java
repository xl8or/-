package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.io.NumberOutput;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.NumericNode;

public final class LongNode extends NumericNode {

   final long _value;


   public LongNode(long var1) {
      this._value = var1;
   }

   public static LongNode valueOf(long var0) {
      return new LongNode(var0);
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
            long var5 = ((LongNode)var1)._value;
            long var7 = this._value;
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
      return BigInteger.valueOf(this._value);
   }

   public BigDecimal getDecimalValue() {
      return BigDecimal.valueOf(this._value);
   }

   public double getDoubleValue() {
      return (double)this._value;
   }

   public int getIntValue() {
      return (int)this._value;
   }

   public long getLongValue() {
      return this._value;
   }

   public Number getNumberValue() {
      return Long.valueOf(this._value);
   }

   public String getValueAsText() {
      return NumberOutput.toString(this._value);
   }

   public int hashCode() {
      int var1 = (int)this._value;
      int var2 = (int)(this._value >> 32);
      return var1 ^ var2;
   }

   public boolean isIntegralNumber() {
      return true;
   }

   public boolean isLong() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      long var3 = this._value;
      var1.writeNumber(var3);
   }
}

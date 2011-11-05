package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.NumericNode;

public final class DecimalNode extends NumericNode {

   final BigDecimal _value;


   public DecimalNode(BigDecimal var1) {
      this._value = var1;
   }

   public static DecimalNode valueOf(BigDecimal var0) {
      return new DecimalNode(var0);
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
            BigDecimal var5 = ((DecimalNode)var1)._value;
            BigDecimal var6 = this._value;
            var2 = var5.equals(var6);
         }
      }

      return (boolean)var2;
   }

   public BigInteger getBigIntegerValue() {
      return this._value.toBigInteger();
   }

   public BigDecimal getDecimalValue() {
      return this._value;
   }

   public double getDoubleValue() {
      return this._value.doubleValue();
   }

   public int getIntValue() {
      return this._value.intValue();
   }

   public long getLongValue() {
      return this._value.longValue();
   }

   public Number getNumberValue() {
      return this._value;
   }

   public String getValueAsText() {
      return this._value.toString();
   }

   public int hashCode() {
      return this._value.hashCode();
   }

   public boolean isBigDecimal() {
      return true;
   }

   public boolean isFloatingPointNumber() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      BigDecimal var3 = this._value;
      var1.writeNumber(var3);
   }
}

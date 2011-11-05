package org.codehaus.jackson.node;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.node.ValueNode;

public abstract class NumericNode extends ValueNode {

   protected NumericNode() {}

   public abstract BigInteger getBigIntegerValue();

   public abstract BigDecimal getDecimalValue();

   public abstract double getDoubleValue();

   public abstract int getIntValue();

   public abstract long getLongValue();

   public abstract Number getNumberValue();

   public abstract String getValueAsText();

   public final boolean isNumber() {
      return true;
   }
}

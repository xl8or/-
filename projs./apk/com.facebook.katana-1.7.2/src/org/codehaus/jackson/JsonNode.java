package org.codehaus.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

public abstract class JsonNode implements Iterable<JsonNode> {

   static final List<JsonNode> NO_NODES = Collections.emptyList();
   static final List<String> NO_STRINGS = Collections.emptyList();


   protected JsonNode() {}

   public abstract boolean equals(Object var1);

   public JsonNode get(int var1) {
      return null;
   }

   public JsonNode get(String var1) {
      return null;
   }

   public BigInteger getBigIntegerValue() {
      return BigInteger.ZERO;
   }

   public byte[] getBinaryValue() {
      return null;
   }

   public boolean getBooleanValue() {
      return false;
   }

   public BigDecimal getDecimalValue() {
      return BigDecimal.ZERO;
   }

   public double getDoubleValue() {
      return (double)0L;
   }

   @Deprecated
   public final JsonNode getElementValue(int var1) {
      return this.get(var1);
   }

   public Iterator<JsonNode> getElements() {
      return NO_NODES.iterator();
   }

   public Iterator<String> getFieldNames() {
      return NO_STRINGS.iterator();
   }

   @Deprecated
   public final JsonNode getFieldValue(String var1) {
      return this.get(var1);
   }

   public int getIntValue() {
      return 0;
   }

   public long getLongValue() {
      return 0L;
   }

   public Number getNumberValue() {
      return Integer.valueOf(this.getIntValue());
   }

   @Deprecated
   public final JsonNode getPath(int var1) {
      return this.path(var1);
   }

   @Deprecated
   public final JsonNode getPath(String var1) {
      return this.path(var1);
   }

   public String getTextValue() {
      return null;
   }

   public abstract String getValueAsText();

   public boolean isArray() {
      return false;
   }

   public boolean isBigDecimal() {
      return false;
   }

   public boolean isBigInteger() {
      return false;
   }

   public boolean isBinary() {
      return false;
   }

   public boolean isBoolean() {
      return false;
   }

   public boolean isContainerNode() {
      return false;
   }

   public boolean isDouble() {
      return false;
   }

   public boolean isFloatingPointNumber() {
      return false;
   }

   public boolean isInt() {
      return false;
   }

   public boolean isIntegralNumber() {
      return false;
   }

   public boolean isLong() {
      return false;
   }

   public boolean isMissingNode() {
      return false;
   }

   public boolean isNull() {
      return false;
   }

   public boolean isNumber() {
      return false;
   }

   public boolean isObject() {
      return false;
   }

   public boolean isPojo() {
      return false;
   }

   public boolean isTextual() {
      return false;
   }

   public boolean isValueNode() {
      return false;
   }

   public final Iterator<JsonNode> iterator() {
      return this.getElements();
   }

   public abstract JsonNode path(int var1);

   public abstract JsonNode path(String var1);

   public int size() {
      return 0;
   }

   public abstract String toString();

   public abstract void writeTo(JsonGenerator var1) throws IOException, JsonGenerationException;
}

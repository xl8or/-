package org.codehaus.jackson.node;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.BigIntegerNode;
import org.codehaus.jackson.node.BinaryNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.DecimalNode;
import org.codehaus.jackson.node.DoubleNode;
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.LongNode;
import org.codehaus.jackson.node.NullNode;
import org.codehaus.jackson.node.NumericNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.POJONode;
import org.codehaus.jackson.node.TextNode;

public class JsonNodeFactory {

   public static final JsonNodeFactory instance = new JsonNodeFactory();


   protected JsonNodeFactory() {}

   public POJONode POJONode(Object var1) {
      return new POJONode(var1);
   }

   public ArrayNode arrayNode() {
      return new ArrayNode(this);
   }

   public BinaryNode binaryNode(byte[] var1) {
      return BinaryNode.valueOf(var1);
   }

   public BinaryNode binaryNode(byte[] var1, int var2, int var3) {
      return BinaryNode.valueOf(var1, var2, var3);
   }

   public BooleanNode booleanNode(boolean var1) {
      BooleanNode var2;
      if(var1) {
         var2 = BooleanNode.getTrue();
      } else {
         var2 = BooleanNode.getFalse();
      }

      return var2;
   }

   public NullNode nullNode() {
      return NullNode.getInstance();
   }

   public NumericNode numberNode(byte var1) {
      return IntNode.valueOf(var1);
   }

   public NumericNode numberNode(double var1) {
      return DoubleNode.valueOf(var1);
   }

   public NumericNode numberNode(float var1) {
      return DoubleNode.valueOf((double)var1);
   }

   public NumericNode numberNode(int var1) {
      return IntNode.valueOf(var1);
   }

   public NumericNode numberNode(long var1) {
      return LongNode.valueOf(var1);
   }

   public NumericNode numberNode(BigDecimal var1) {
      return DecimalNode.valueOf(var1);
   }

   public NumericNode numberNode(BigInteger var1) {
      return BigIntegerNode.valueOf(var1);
   }

   public NumericNode numberNode(short var1) {
      return IntNode.valueOf(var1);
   }

   public ObjectNode objectNode() {
      return new ObjectNode(this);
   }

   public TextNode textNode(String var1) {
      return TextNode.valueOf(var1);
   }
}

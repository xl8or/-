package org.codehaus.jackson.node;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.BaseJsonNode;
import org.codehaus.jackson.node.BinaryNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.NullNode;
import org.codehaus.jackson.node.NumericNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.POJONode;
import org.codehaus.jackson.node.TextNode;

public abstract class ContainerNode extends BaseJsonNode {

   JsonNodeFactory _nodeFactory;


   protected ContainerNode(JsonNodeFactory var1) {
      this._nodeFactory = var1;
   }

   public final POJONode POJONode(Object var1) {
      return this._nodeFactory.POJONode(var1);
   }

   public final ArrayNode arrayNode() {
      return this._nodeFactory.arrayNode();
   }

   public final BinaryNode binaryNode(byte[] var1) {
      return this._nodeFactory.binaryNode(var1);
   }

   public final BinaryNode binaryNode(byte[] var1, int var2, int var3) {
      return this._nodeFactory.binaryNode(var1, var2, var3);
   }

   public final BooleanNode booleanNode(boolean var1) {
      return this._nodeFactory.booleanNode(var1);
   }

   public abstract JsonNode get(int var1);

   public abstract JsonNode get(String var1);

   public String getValueAsText() {
      return null;
   }

   public boolean isContainerNode() {
      return true;
   }

   public final NullNode nullNode() {
      return this._nodeFactory.nullNode();
   }

   public final NumericNode numberNode(byte var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(double var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(float var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(int var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(long var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(BigDecimal var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final NumericNode numberNode(short var1) {
      return this._nodeFactory.numberNode(var1);
   }

   public final ObjectNode objectNode() {
      return this._nodeFactory.objectNode();
   }

   public abstract int size();

   public final TextNode textNode(String var1) {
      return this._nodeFactory.textNode(var1);
   }

   protected static class NoNodesIterator implements Iterator<JsonNode> {

      static final ContainerNode.NoNodesIterator instance = new ContainerNode.NoNodesIterator();


      private NoNodesIterator() {}

      public static ContainerNode.NoNodesIterator instance() {
         return instance;
      }

      public boolean hasNext() {
         return false;
      }

      public JsonNode next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         throw new IllegalStateException();
      }
   }

   protected static class NoStringsIterator implements Iterator<String> {

      static final ContainerNode.NoStringsIterator instance = new ContainerNode.NoStringsIterator();


      private NoStringsIterator() {}

      public static ContainerNode.NoStringsIterator instance() {
         return instance;
      }

      public boolean hasNext() {
         return false;
      }

      public String next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         throw new IllegalStateException();
      }
   }
}

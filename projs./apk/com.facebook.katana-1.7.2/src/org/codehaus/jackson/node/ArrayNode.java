package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.BaseJsonNode;
import org.codehaus.jackson.node.BinaryNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.ContainerNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.MissingNode;
import org.codehaus.jackson.node.NullNode;
import org.codehaus.jackson.node.NumericNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.POJONode;
import org.codehaus.jackson.node.TextNode;

public final class ArrayNode extends ContainerNode {

   ArrayList<JsonNode> _children;


   public ArrayNode(JsonNodeFactory var1) {
      super(var1);
   }

   private void _add(JsonNode var1) {
      if(this._children == null) {
         ArrayList var2 = new ArrayList();
         this._children = var2;
      }

      this._children.add(var1);
   }

   private void _insert(int var1, JsonNode var2) {
      if(this._children == null) {
         ArrayList var3 = new ArrayList();
         this._children = var3;
         this._children.add(var2);
      } else if(var1 < 0) {
         this._children.add(0, var2);
      } else {
         int var5 = this._children.size();
         if(var1 >= var5) {
            this._children.add(var2);
         } else {
            this._children.add(var1, var2);
         }
      }
   }

   private boolean _sameChildren(ArrayList<JsonNode> var1) {
      int var2 = var1.size();
      boolean var3;
      if(this._children.size() != var2) {
         var3 = false;
      } else {
         int var4 = 0;

         while(true) {
            if(var4 >= var2) {
               var3 = true;
               break;
            }

            JsonNode var5 = (JsonNode)this._children.get(var4);
            Object var6 = var1.get(var4);
            if(!var5.equals(var6)) {
               var3 = false;
               break;
            }

            ++var4;
         }
      }

      return var3;
   }

   public JsonNode _set(int var1, JsonNode var2) {
      if(this._children != null && var1 >= 0) {
         int var3 = this._children.size();
         if(var1 < var3) {
            return (JsonNode)this._children.set(var1, var2);
         }
      }

      StringBuilder var4 = (new StringBuilder()).append("Illegal index ").append(var1).append(", array size ");
      int var5 = this.size();
      String var6 = var4.append(var5).toString();
      throw new IndexOutOfBoundsException(var6);
   }

   public void add(double var1) {
      NumericNode var3 = this.numberNode(var1);
      this._add(var3);
   }

   public void add(float var1) {
      NumericNode var2 = this.numberNode(var1);
      this._add(var2);
   }

   public void add(int var1) {
      NumericNode var2 = this.numberNode(var1);
      this._add(var2);
   }

   public void add(long var1) {
      NumericNode var3 = this.numberNode(var1);
      this._add(var3);
   }

   public void add(String var1) {
      TextNode var2 = this.textNode(var1);
      this._add(var2);
   }

   public void add(BigDecimal var1) {
      NumericNode var2 = this.numberNode(var1);
      this._add(var2);
   }

   public void add(JsonNode var1) {
      Object var2;
      if(var1 == null) {
         var2 = this.nullNode();
      } else {
         var2 = var1;
      }

      this._add((JsonNode)var2);
   }

   public void add(boolean var1) {
      BooleanNode var2 = this.booleanNode(var1);
      this._add(var2);
   }

   public void add(byte[] var1) {
      BinaryNode var2 = this.binaryNode(var1);
      this._add(var2);
   }

   public ArrayNode addArray() {
      ArrayNode var1 = this.arrayNode();
      this._add(var1);
      return var1;
   }

   public void addNull() {
      NullNode var1 = this.nullNode();
      this._add(var1);
   }

   public ObjectNode addObject() {
      ObjectNode var1 = this.objectNode();
      this._add(var1);
      return var1;
   }

   public void addPOJO(Object var1) {
      POJONode var2 = this.POJONode(var1);
      this._add(var2);
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
            ArrayNode var6 = (ArrayNode)var1;
            if(this._children == null) {
               if(var6._children == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }
            } else {
               ArrayList var5 = this._children;
               var2 = var6._sameChildren(var5);
            }
         }
      }

      return (boolean)var2;
   }

   public JsonNode get(int var1) {
      JsonNode var3;
      if(var1 >= 0 && this._children != null) {
         int var2 = this._children.size();
         if(var1 < var2) {
            var3 = (JsonNode)this._children.get(var1);
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   public JsonNode get(String var1) {
      return null;
   }

   public Iterator<JsonNode> getElements() {
      Object var1;
      if(this._children == null) {
         var1 = ContainerNode.NoNodesIterator.instance();
      } else {
         var1 = this._children.iterator();
      }

      return (Iterator)var1;
   }

   public int hashCode() {
      int var1;
      if(this._children == null) {
         var1 = 1;
      } else {
         var1 = this._children.size();
         Iterator var2 = this._children.iterator();

         while(var2.hasNext()) {
            JsonNode var4 = (JsonNode)var2.next();
            if(var4 != null) {
               int var3 = var4.hashCode();
               var1 ^= var3;
            }
         }
      }

      return var1;
   }

   public void insert(int var1, double var2) {
      NumericNode var4 = this.numberNode(var2);
      this._insert(var1, var4);
   }

   public void insert(int var1, float var2) {
      NumericNode var3 = this.numberNode(var2);
      this._insert(var1, var3);
   }

   public void insert(int var1, int var2) {
      NumericNode var3 = this.numberNode(var2);
      this._insert(var1, var3);
   }

   public void insert(int var1, long var2) {
      NumericNode var4 = this.numberNode(var2);
      this._insert(var1, var4);
   }

   public void insert(int var1, String var2) {
      TextNode var3 = this.textNode(var2);
      this._insert(var1, var3);
   }

   public void insert(int var1, BigDecimal var2) {
      NumericNode var3 = this.numberNode(var2);
      this._insert(var1, var3);
   }

   public void insert(int var1, JsonNode var2) {
      Object var3;
      if(var2 == null) {
         var3 = this.nullNode();
      } else {
         var3 = var2;
      }

      this._insert(var1, (JsonNode)var3);
   }

   public void insert(int var1, boolean var2) {
      BooleanNode var3 = this.booleanNode(var2);
      this._insert(var1, var3);
   }

   public void insert(int var1, byte[] var2) {
      BinaryNode var3 = this.binaryNode(var2);
      this._insert(var1, var3);
   }

   public ArrayNode insertArray(int var1) {
      ArrayNode var2 = this.arrayNode();
      this._insert(var1, var2);
      return var2;
   }

   public void insertNull(int var1) {
      NullNode var2 = this.nullNode();
      this._insert(var1, var2);
   }

   public ObjectNode insertObject(int var1) {
      ObjectNode var2 = this.objectNode();
      this._insert(var1, var2);
      return var2;
   }

   public void insertPOJO(int var1, Object var2) {
      POJONode var3 = this.POJONode(var2);
      this._insert(var1, var3);
   }

   public boolean isArray() {
      return true;
   }

   public JsonNode path(int var1) {
      Object var3;
      if(var1 >= 0 && this._children != null) {
         int var2 = this._children.size();
         if(var1 < var2) {
            var3 = (JsonNode)this._children.get(var1);
            return (JsonNode)var3;
         }
      }

      var3 = MissingNode.getInstance();
      return (JsonNode)var3;
   }

   public JsonNode path(String var1) {
      return MissingNode.getInstance();
   }

   public JsonNode remove(int var1) {
      JsonNode var3;
      if(var1 >= 0 && this._children != null) {
         int var2 = this._children.size();
         if(var1 < var2) {
            var3 = (JsonNode)this._children.remove(var1);
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      var1.writeStartArray();
      if(this._children != null) {
         Iterator var3 = this._children.iterator();

         while(var3.hasNext()) {
            ((BaseJsonNode)((JsonNode)var3.next())).writeTo(var1);
         }
      }

      var1.writeEndArray();
   }

   public JsonNode set(int var1, JsonNode var2) {
      Object var3;
      if(var2 == null) {
         var3 = this.nullNode();
      } else {
         var3 = var2;
      }

      return this._set(var1, (JsonNode)var3);
   }

   public int size() {
      int var1;
      if(this._children == null) {
         var1 = 0;
      } else {
         var1 = this._children.size();
      }

      return var1;
   }

   public String toString() {
      int var1 = (this.size() << 4) + 16;
      StringBuilder var2 = new StringBuilder(var1);
      StringBuilder var3 = var2.append('[');
      if(this._children != null) {
         int var4 = this._children.size();

         for(int var5 = 0; var5 < var4; ++var5) {
            if(var5 > 0) {
               StringBuilder var6 = var2.append(',');
            }

            String var7 = ((JsonNode)this._children.get(var5)).toString();
            var2.append(var7);
         }
      }

      StringBuilder var9 = var2.append(']');
      return var2.toString();
   }
}

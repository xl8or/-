package org.codehaus.jackson.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.BaseJsonNode;
import org.codehaus.jackson.node.BinaryNode;
import org.codehaus.jackson.node.BooleanNode;
import org.codehaus.jackson.node.ContainerNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.MissingNode;
import org.codehaus.jackson.node.NullNode;
import org.codehaus.jackson.node.NumericNode;
import org.codehaus.jackson.node.POJONode;
import org.codehaus.jackson.node.TextNode;

public class ObjectNode extends ContainerNode {

   LinkedHashMap<String, JsonNode> _children = null;


   public ObjectNode(JsonNodeFactory var1) {
      super(var1);
   }

   private final JsonNode _put(String var1, JsonNode var2) {
      if(this._children == null) {
         LinkedHashMap var3 = new LinkedHashMap();
         this._children = var3;
      }

      return (JsonNode)this._children.put(var1, var2);
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
            ObjectNode var12 = (ObjectNode)var1;
            int var5 = var12.size();
            int var6 = this.size();
            if(var5 != var6) {
               var2 = false;
            } else {
               if(this._children != null) {
                  Iterator var7 = this._children.entrySet().iterator();

                  while(var7.hasNext()) {
                     Entry var8 = (Entry)var7.next();
                     String var9 = (String)var8.getKey();
                     JsonNode var11 = (JsonNode)var8.getValue();
                     JsonNode var10 = var12.get(var9);
                     if(var10 == null || !var10.equals(var11)) {
                        var2 = false;
                        return var2;
                     }
                  }
               }

               var2 = true;
            }
         }
      }

      return var2;
   }

   public JsonNode get(int var1) {
      return null;
   }

   public JsonNode get(String var1) {
      JsonNode var2;
      if(this._children != null) {
         var2 = (JsonNode)this._children.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public Iterator<JsonNode> getElements() {
      Object var1;
      if(this._children == null) {
         var1 = ContainerNode.NoNodesIterator.instance();
      } else {
         var1 = this._children.values().iterator();
      }

      return (Iterator)var1;
   }

   public Iterator<String> getFieldNames() {
      Object var1;
      if(this._children == null) {
         var1 = ContainerNode.NoStringsIterator.instance();
      } else {
         var1 = this._children.keySet().iterator();
      }

      return (Iterator)var1;
   }

   public Iterator<Entry<String, JsonNode>> getFields() {
      Object var1;
      if(this._children == null) {
         var1 = ObjectNode.NoFieldsIterator.instance;
      } else {
         var1 = this._children.entrySet().iterator();
      }

      return (Iterator)var1;
   }

   public int hashCode() {
      int var1;
      if(this._children == null) {
         var1 = -1;
      } else {
         var1 = this._children.hashCode();
      }

      return var1;
   }

   public boolean isObject() {
      return true;
   }

   public JsonNode path(int var1) {
      return MissingNode.getInstance();
   }

   public JsonNode path(String var1) {
      Object var2;
      if(this._children != null) {
         JsonNode var3 = (JsonNode)this._children.get(var1);
         if(var3 != null) {
            var2 = var3;
            return (JsonNode)var2;
         }
      }

      var2 = MissingNode.getInstance();
      return (JsonNode)var2;
   }

   public JsonNode put(String var1, JsonNode var2) {
      Object var3;
      if(var2 == null) {
         var3 = this.nullNode();
      } else {
         var3 = var2;
      }

      return this._put(var1, (JsonNode)var3);
   }

   public void put(String var1, double var2) {
      NumericNode var4 = this.numberNode(var2);
      this._put(var1, var4);
   }

   public void put(String var1, float var2) {
      NumericNode var3 = this.numberNode(var2);
      this._put(var1, var3);
   }

   public void put(String var1, int var2) {
      NumericNode var3 = this.numberNode(var2);
      this._put(var1, var3);
   }

   public void put(String var1, long var2) {
      NumericNode var4 = this.numberNode(var2);
      this._put(var1, var4);
   }

   public void put(String var1, String var2) {
      TextNode var3 = this.textNode(var2);
      this._put(var1, var3);
   }

   public void put(String var1, BigDecimal var2) {
      NumericNode var3 = this.numberNode(var2);
      this._put(var1, var3);
   }

   public void put(String var1, boolean var2) {
      BooleanNode var3 = this.booleanNode(var2);
      this._put(var1, var3);
   }

   public void put(String var1, byte[] var2) {
      BinaryNode var3 = this.binaryNode(var2);
      this._put(var1, var3);
   }

   public ArrayNode putArray(String var1) {
      ArrayNode var2 = this.arrayNode();
      this._put(var1, var2);
      return var2;
   }

   public void putNull(String var1) {
      NullNode var2 = this.nullNode();
      this._put(var1, var2);
   }

   public ObjectNode putObject(String var1) {
      ObjectNode var2 = this.objectNode();
      this._put(var1, var2);
      return var2;
   }

   public void putPOJO(String var1, Object var2) {
      POJONode var3 = this.POJONode(var2);
      this._put(var1, var3);
   }

   public JsonNode remove(String var1) {
      JsonNode var2;
      if(this._children != null) {
         var2 = (JsonNode)this._children.remove(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      var1.writeStartObject();
      if(this._children != null) {
         Iterator var3 = this._children.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var4 = (Entry)var3.next();
            String var5 = (String)var4.getKey();
            var1.writeFieldName(var5);
            ((BaseJsonNode)var4.getValue()).serialize(var1, var2);
         }
      }

      var1.writeEndObject();
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
      int var1 = (this.size() << 4) + 32;
      StringBuilder var2 = new StringBuilder(var1);
      StringBuilder var3 = var2.append("{");
      if(this._children != null) {
         int var4 = 0;

         int var8;
         for(Iterator var5 = this._children.entrySet().iterator(); var5.hasNext(); var4 = var8) {
            Entry var6 = (Entry)var5.next();
            if(var4 > 0) {
               StringBuilder var7 = var2.append(",");
            }

            var8 = var4 + 1;
            String var9 = (String)var6.getKey();
            TextNode.appendQuoted(var2, var9);
            StringBuilder var10 = var2.append(':');
            String var11 = ((JsonNode)var6.getValue()).toString();
            var2.append(var11);
         }
      }

      StringBuilder var13 = var2.append("}");
      return var2.toString();
   }

   protected static class NoFieldsIterator implements Iterator<Entry<String, JsonNode>> {

      static final ObjectNode.NoFieldsIterator instance = new ObjectNode.NoFieldsIterator();


      private NoFieldsIterator() {}

      public boolean hasNext() {
         return false;
      }

      public Entry<String, JsonNode> next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         throw new IllegalStateException();
      }
   }
}

package org.codehaus.jackson.schema;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

public class JsonSchema {

   private final ObjectNode schema;


   public JsonSchema(ObjectNode var1) {
      this.schema = var1;
   }

   public static JsonNode getDefaultSchemaNode() {
      ObjectNode var0 = JsonNodeFactory.instance.objectNode();
      var0.put("type", "any");
      var0.put("optional", (boolean)1);
      return var0;
   }

   public boolean equals(Object var1) {
      return this.schema.equals(var1);
   }

   public ObjectNode getSchemaNode() {
      return this.schema;
   }

   public String toString() {
      return this.schema.toString();
   }
}

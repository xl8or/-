package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.BaseJsonNode;

public final class MissingNode extends BaseJsonNode {

   private static final MissingNode instance = new MissingNode();


   private MissingNode() {}

   public static MissingNode getInstance() {
      return instance;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public String getValueAsText() {
      return null;
   }

   public boolean isMissingNode() {
      return true;
   }

   public JsonNode path(int var1) {
      return this;
   }

   public JsonNode path(String var1) {
      return this;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {}

   public String toString() {
      return "";
   }
}

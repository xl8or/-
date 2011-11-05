package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.node.ValueNode;

public final class NullNode extends ValueNode {

   public static final NullNode instance = new NullNode();


   private NullNode() {}

   public static NullNode getInstance() {
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
      return "null";
   }

   public boolean isNull() {
      return true;
   }

   public final void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException {
      var1.writeNull();
   }
}

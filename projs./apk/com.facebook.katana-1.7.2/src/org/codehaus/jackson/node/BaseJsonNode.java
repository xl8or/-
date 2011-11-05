package org.codehaus.jackson.node;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializable;
import org.codehaus.jackson.map.SerializerProvider;

public abstract class BaseJsonNode extends JsonNode implements JsonSerializable {

   protected BaseJsonNode() {}

   public abstract void serialize(JsonGenerator var1, SerializerProvider var2) throws IOException, JsonProcessingException;

   public final void writeTo(JsonGenerator var1) throws IOException, JsonGenerationException {
      this.serialize(var1, (SerializerProvider)null);
   }
}

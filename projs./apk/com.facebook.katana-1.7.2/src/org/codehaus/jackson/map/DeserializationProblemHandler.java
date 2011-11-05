package org.codehaus.jackson.map;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public abstract class DeserializationProblemHandler {

   public DeserializationProblemHandler() {}

   public boolean handleUnknownProperty(DeserializationContext var1, JsonDeserializer<?> var2, Object var3, String var4) throws IOException, JsonProcessingException {
      return false;
   }
}

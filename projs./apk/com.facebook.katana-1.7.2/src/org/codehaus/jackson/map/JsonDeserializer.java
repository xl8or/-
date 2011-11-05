package org.codehaus.jackson.map;

import java.io.IOException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;

public abstract class JsonDeserializer<T extends Object> {

   public JsonDeserializer() {}

   public abstract T deserialize(JsonParser var1, DeserializationContext var2) throws IOException, JsonProcessingException;

   public T deserialize(JsonParser var1, DeserializationContext var2, T var3) throws IOException, JsonProcessingException {
      throw new UnsupportedOperationException();
   }

   public T getNullValue() {
      return null;
   }

   @Deprecated
   public boolean shouldBeCached() {
      return false;
   }

   public abstract static class None extends JsonDeserializer<Object> {

      public None() {}
   }
}

package org.codehaus.jackson.map;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;

public abstract class JsonSerializer<T extends Object> {

   public JsonSerializer() {}

   public abstract void serialize(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonProcessingException;

   public abstract static class None extends JsonSerializer<Object> {

      public None() {}
   }
}

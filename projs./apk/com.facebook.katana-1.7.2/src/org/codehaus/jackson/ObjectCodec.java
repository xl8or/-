package org.codehaus.jackson;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

public abstract class ObjectCodec {

   protected ObjectCodec() {}

   public abstract JsonNode readTree(JsonParser var1) throws IOException, JsonProcessingException;

   public abstract <T extends Object> T readValue(JsonParser var1, Class<T> var2) throws IOException, JsonProcessingException;

   public abstract <T extends Object> T readValue(JsonParser var1, JavaType var2) throws IOException, JsonProcessingException;

   public abstract <T extends Object> T readValue(JsonParser var1, TypeReference<?> var2) throws IOException, JsonProcessingException;

   public abstract void writeTree(JsonGenerator var1, JsonNode var2) throws IOException, JsonProcessingException;

   public abstract void writeValue(JsonGenerator var1, Object var2) throws IOException, JsonProcessingException;
}

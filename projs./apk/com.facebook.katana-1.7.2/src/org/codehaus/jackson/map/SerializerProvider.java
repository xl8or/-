package org.codehaus.jackson.map;

import java.io.IOException;
import java.util.Date;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerFactory;
import org.codehaus.jackson.schema.JsonSchema;

public abstract class SerializerProvider {

   protected final SerializationConfig _config;


   protected SerializerProvider(SerializationConfig var1) {
      this._config = var1;
   }

   public abstract void defaultSerializeDateValue(long var1, JsonGenerator var3) throws IOException, JsonProcessingException;

   public abstract void defaultSerializeDateValue(Date var1, JsonGenerator var2) throws IOException, JsonProcessingException;

   public final void defaultSerializeField(String var1, Object var2, JsonGenerator var3) throws IOException, JsonProcessingException {
      var3.writeFieldName(var1);
      if(var2 == null) {
         this.getNullValueSerializer().serialize((Object)null, var3, this);
      } else {
         Class var4 = var2.getClass();
         this.findValueSerializer(var4).serialize(var2, var3, this);
      }
   }

   public final void defaultSerializeValue(Object var1, JsonGenerator var2) throws IOException, JsonProcessingException {
      if(var1 == null) {
         this.getNullValueSerializer().serialize((Object)null, var2, this);
      } else {
         Class var3 = var1.getClass();
         this.findValueSerializer(var3).serialize(var1, var2, this);
      }
   }

   public abstract JsonSerializer<Object> findValueSerializer(Class<?> var1) throws JsonMappingException;

   public JsonSchema generateJsonSchema(Class<?> var1, SerializationConfig var2, SerializerFactory var3) throws JsonMappingException {
      throw new UnsupportedOperationException();
   }

   public final SerializationConfig getConfig() {
      return this._config;
   }

   public abstract JsonSerializer<Object> getKeySerializer();

   public abstract JsonSerializer<Object> getNullKeySerializer();

   public abstract JsonSerializer<Object> getNullValueSerializer();

   public abstract JsonSerializer<Object> getUnknownTypeSerializer(Class<?> var1);

   public abstract boolean hasSerializerFor(SerializationConfig var1, Class<?> var2, SerializerFactory var3);

   public final boolean isEnabled(SerializationConfig.Feature var1) {
      return this._config.isEnabled(var1);
   }

   public abstract void serializeValue(SerializationConfig var1, JsonGenerator var2, Object var3, SerializerFactory var4) throws IOException, JsonGenerationException;
}

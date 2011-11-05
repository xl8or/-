package org.codehaus.jackson.map;

import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializationConfig;

public abstract class SerializerFactory {

   public SerializerFactory() {}

   public abstract <T extends Object> JsonSerializer<T> createSerializer(Class<T> var1, SerializationConfig var2);
}

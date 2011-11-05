package org.codehaus.jackson.map;

import org.codehaus.jackson.map.BeanDescription;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;

public abstract class ClassIntrospector<T extends BeanDescription> {

   protected ClassIntrospector() {}

   public abstract T forClassAnnotations(DeserializationConfig var1, Class<?> var2);

   public abstract T forClassAnnotations(SerializationConfig var1, Class<?> var2);

   public abstract T forCreation(DeserializationConfig var1, Class<?> var2);

   public abstract T forDeserialization(DeserializationConfig var1, JavaType var2);

   public abstract T forSerialization(SerializationConfig var1, Class<?> var2);
}

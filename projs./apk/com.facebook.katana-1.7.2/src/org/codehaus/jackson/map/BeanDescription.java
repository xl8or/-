package org.codehaus.jackson.map;

import java.util.Collection;
import java.util.LinkedHashMap;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.type.JavaType;

public abstract class BeanDescription {

   protected final JavaType _type;


   protected BeanDescription(JavaType var1) {
      this._type = var1;
   }

   public abstract LinkedHashMap<String, AnnotatedMethod> findGetters(boolean var1, Collection<String> var2);

   public abstract LinkedHashMap<String, AnnotatedMethod> findSetters(boolean var1);

   public Class<?> getBeanClass() {
      return this._type.getRawClass();
   }

   public JavaType getType() {
      return this._type;
   }
}

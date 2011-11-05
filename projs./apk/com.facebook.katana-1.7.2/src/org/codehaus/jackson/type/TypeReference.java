package org.codehaus.jackson.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T extends Object> implements Comparable<TypeReference<T>> {

   final Type _type;


   protected TypeReference() {
      Type var1 = this.getClass().getGenericSuperclass();
      if(var1 instanceof Class) {
         throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
      } else {
         Type var2 = ((ParameterizedType)var1).getActualTypeArguments()[0];
         this._type = var2;
      }
   }

   public int compareTo(TypeReference<T> var1) {
      return 0;
   }

   public Type getType() {
      return this._type;
   }
}

package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Primitives {

   public static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_TYPE;
   public static final Set<Class<?>> PRIMITIVE_TYPES;
   public static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE;
   public static final Set<Class<?>> WRAPPER_TYPES;


   static {
      HashMap var0 = new HashMap(16);
      HashMap var1 = new HashMap(16);
      Class var2 = Boolean.TYPE;
      add(var0, var1, var2, Boolean.class);
      Class var3 = Byte.TYPE;
      add(var0, var1, var3, Byte.class);
      Class var4 = Character.TYPE;
      add(var0, var1, var4, Character.class);
      Class var5 = Double.TYPE;
      add(var0, var1, var5, Double.class);
      Class var6 = Float.TYPE;
      add(var0, var1, var6, Float.class);
      Class var7 = Integer.TYPE;
      add(var0, var1, var7, Integer.class);
      Class var8 = Long.TYPE;
      add(var0, var1, var8, Long.class);
      Class var9 = Short.TYPE;
      add(var0, var1, var9, Short.class);
      Class var10 = Void.TYPE;
      add(var0, var1, var10, Void.class);
      PRIMITIVE_TO_WRAPPER_TYPE = Collections.unmodifiableMap(var0);
      WRAPPER_TO_PRIMITIVE_TYPE = Collections.unmodifiableMap(var1);
      PRIMITIVE_TYPES = PRIMITIVE_TO_WRAPPER_TYPE.keySet();
      WRAPPER_TYPES = WRAPPER_TO_PRIMITIVE_TYPE.keySet();
   }

   private Primitives() {}

   private static void add(Map<Class<?>, Class<?>> var0, Map<Class<?>, Class<?>> var1, Class<?> var2, Class<?> var3) {
      var0.put(var2, var3);
      var1.put(var3, var2);
   }

   public static boolean isWrapperType(Class<?> var0) {
      Map var1 = WRAPPER_TO_PRIMITIVE_TYPE;
      Object var2 = Preconditions.checkNotNull(var0);
      return var1.containsKey(var2);
   }

   public static <T extends Object> Class<T> unwrap(Class<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      Class var2 = (Class)WRAPPER_TO_PRIMITIVE_TYPE.get(var0);
      if(var2 != null) {
         var0 = var2;
      }

      return var0;
   }

   public static <T extends Object> Class<T> wrap(Class<T> var0) {
      Object var1 = Preconditions.checkNotNull(var0);
      Class var2 = (Class)PRIMITIVE_TO_WRAPPER_TYPE.get(var0);
      if(var2 != null) {
         var0 = var2;
      }

      return var0;
   }
}

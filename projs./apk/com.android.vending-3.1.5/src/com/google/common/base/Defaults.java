package com.google.common.base;

import java.util.HashMap;
import java.util.Map;

public final class Defaults {

   private static final Map<Class<?>, Object> DEFAULTS = new HashMap(16);


   static {
      Class var0 = Boolean.TYPE;
      Boolean var1 = Boolean.valueOf((boolean)0);
      put(var0, var1);
      Class var2 = Character.TYPE;
      Character var3 = Character.valueOf('\u0000');
      put(var2, var3);
      Class var4 = Byte.TYPE;
      Byte var5 = Byte.valueOf((byte)0);
      put(var4, var5);
      Class var6 = Short.TYPE;
      Short var7 = Short.valueOf((short)0);
      put(var6, var7);
      Class var8 = Integer.TYPE;
      Integer var9 = Integer.valueOf(0);
      put(var8, var9);
      Class var10 = Long.TYPE;
      Long var11 = Long.valueOf(0L);
      put(var10, var11);
      Class var12 = Float.TYPE;
      Float var13 = Float.valueOf(0.0F);
      put(var12, var13);
      Class var14 = Double.TYPE;
      Double var15 = Double.valueOf(0.0D);
      put(var14, var15);
   }

   private Defaults() {}

   public static <T extends Object> T defaultValue(Class<T> var0) {
      return DEFAULTS.get(var0);
   }

   private static <T extends Object> void put(Class<T> var0, T var1) {
      DEFAULTS.put(var0, var1);
   }
}

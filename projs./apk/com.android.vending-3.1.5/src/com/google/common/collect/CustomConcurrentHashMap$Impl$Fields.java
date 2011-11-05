package com.google.common.collect;

import com.google.common.collect.CustomConcurrentHashMap.Impl;
import java.lang.reflect.Field;

class CustomConcurrentHashMap$Impl$Fields {

   static final Field segmentMask = findField("segmentMask");
   static final Field segmentShift = findField("segmentShift");
   static final Field segments = findField("segments");
   static final Field strategy = findField("strategy");


   CustomConcurrentHashMap$Impl$Fields() {}

   static Field findField(String var0) {
      try {
         Field var1 = Impl.class.getDeclaredField(var0);
         var1.setAccessible((boolean)1);
         return var1;
      } catch (NoSuchFieldException var3) {
         throw new AssertionError(var3);
      }
   }
}

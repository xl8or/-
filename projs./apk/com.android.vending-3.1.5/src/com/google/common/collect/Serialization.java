package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@GwtCompatible(
   emulated = true
)
final class Serialization {

   private Serialization() {}

   @GwtIncompatible("java.lang.reflect.Field")
   static <T extends Object> Serialization.FieldSetter<T> getFieldSetter(Class<T> var0, String var1) {
      try {
         Field var2 = var0.getDeclaredField(var1);
         Serialization.FieldSetter var3 = new Serialization.FieldSetter(var2, (Serialization.1)null);
         return var3;
      } catch (NoSuchFieldException var5) {
         throw new AssertionError(var5);
      }
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   public static <K extends Object, V extends Object> void populateMap(Map<K, V> var0, ObjectInputStream var1) throws IOException, ClassNotFoundException {
      int var2 = var1.readInt();
      populateMap(var0, var1, var2);
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   public static <K extends Object, V extends Object> void populateMap(Map<K, V> var0, ObjectInputStream var1, int var2) throws IOException, ClassNotFoundException {
      for(int var3 = 0; var3 < var2; ++var3) {
         Object var4 = var1.readObject();
         Object var5 = var1.readObject();
         var0.put(var4, var5);
      }

   }

   @GwtIncompatible("java.io.ObjectInputStream")
   public static <K extends Object, V extends Object> void populateMultimap(Multimap<K, V> var0, ObjectInputStream var1) throws IOException, ClassNotFoundException {
      int var2 = var1.readInt();
      populateMultimap(var0, var1, var2);
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   public static <K extends Object, V extends Object> void populateMultimap(Multimap<K, V> var0, ObjectInputStream var1, int var2) throws IOException, ClassNotFoundException {
      for(int var3 = 0; var3 < var2; ++var3) {
         Object var4 = var1.readObject();
         Collection var5 = var0.get(var4);
         int var6 = var1.readInt();

         for(int var7 = 0; var7 < var6; ++var7) {
            Object var8 = var1.readObject();
            var5.add(var8);
         }
      }

   }

   @GwtIncompatible("java.io.ObjectInputStream")
   public static <E extends Object> void populateMultiset(Multiset<E> var0, ObjectInputStream var1) throws IOException, ClassNotFoundException {
      int var2 = var1.readInt();
      populateMultiset(var0, var1, var2);
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   public static <E extends Object> void populateMultiset(Multiset<E> var0, ObjectInputStream var1, int var2) throws IOException, ClassNotFoundException {
      for(int var3 = 0; var3 < var2; ++var3) {
         Object var4 = var1.readObject();
         int var5 = var1.readInt();
         var0.add(var4, var5);
      }

   }

   @GwtIncompatible("java.io.ObjectInputStream")
   public static int readCount(ObjectInputStream var0) throws IOException {
      return var0.readInt();
   }

   @GwtIncompatible("java.io.ObjectOutputStream")
   public static <K extends Object, V extends Object> void writeMap(Map<K, V> var0, ObjectOutputStream var1) throws IOException {
      int var2 = var0.size();
      var1.writeInt(var2);
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getKey();
         var1.writeObject(var5);
         Object var6 = var4.getValue();
         var1.writeObject(var6);
      }

   }

   @GwtIncompatible("java.io.ObjectOutputStream")
   public static <K extends Object, V extends Object> void writeMultimap(Multimap<K, V> var0, ObjectOutputStream var1) throws IOException {
      int var2 = var0.asMap().size();
      var1.writeInt(var2);
      Iterator var3 = var0.asMap().entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Object var5 = var4.getKey();
         var1.writeObject(var5);
         int var6 = ((Collection)var4.getValue()).size();
         var1.writeInt(var6);
         Iterator var7 = ((Collection)var4.getValue()).iterator();

         while(var7.hasNext()) {
            Object var8 = var7.next();
            var1.writeObject(var8);
         }
      }

   }

   @GwtIncompatible("java.io.ObjectOutputStream")
   public static <E extends Object> void writeMultiset(Multiset<E> var0, ObjectOutputStream var1) throws IOException {
      int var2 = var0.entrySet().size();
      var1.writeInt(var2);
      Iterator var3 = var0.entrySet().iterator();

      while(var3.hasNext()) {
         Multiset.Entry var4 = (Multiset.Entry)var3.next();
         Object var5 = var4.getElement();
         var1.writeObject(var5);
         int var6 = var4.getCount();
         var1.writeInt(var6);
      }

   }

   @GwtCompatible(
      emulated = true
   )
   static final class FieldSetter<T extends Object> {

      private final Field field;


      private FieldSetter(Field var1) {
         this.field = var1;
         var1.setAccessible((boolean)1);
      }

      // $FF: synthetic method
      FieldSetter(Field var1, Serialization.1 var2) {
         this(var1);
      }

      @GwtIncompatible("java.lang.reflect.Field")
      void set(T var1, int var2) {
         try {
            Field var3 = this.field;
            Integer var4 = Integer.valueOf(var2);
            var3.set(var1, var4);
         } catch (IllegalAccessException var6) {
            throw new AssertionError(var6);
         }
      }

      @GwtIncompatible("java.lang.reflect.Field")
      void set(T var1, Object var2) {
         try {
            this.field.set(var1, var2);
         } catch (IllegalAccessException var4) {
            throw new AssertionError(var4);
         }
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

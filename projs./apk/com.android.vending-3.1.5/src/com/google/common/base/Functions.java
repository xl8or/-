package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public final class Functions {

   private Functions() {}

   public static <A extends Object, B extends Object, C extends Object> Function<A, C> compose(Function<B, C> var0, Function<A, ? extends B> var1) {
      return new Functions.FunctionComposition(var0, var1);
   }

   public static <E extends Object> Function<Object, E> constant(@Nullable E var0) {
      return new Functions.ConstantFunction(var0);
   }

   public static <K extends Object, V extends Object> Function<K, V> forMap(Map<K, V> var0) {
      return new Functions.FunctionForMapNoDefault(var0);
   }

   public static <K extends Object, V extends Object> Function<K, V> forMap(Map<K, ? extends V> var0, @Nullable V var1) {
      return new Functions.ForMapWithDefault(var0, var1);
   }

   public static <T extends Object> Function<T, Boolean> forPredicate(Predicate<T> var0) {
      return new Functions.PredicateFunction(var0, (Functions.1)null);
   }

   public static <E extends Object> Function<E, E> identity() {
      return Functions.IdentityFunction.INSTANCE;
   }

   public static Function<Object, String> toStringFunction() {
      return Functions.ToStringFunction.INSTANCE;
   }

   private static class ForMapWithDefault<K extends Object, V extends Object> implements Function<K, V>, Serializable {

      private static final long serialVersionUID;
      final V defaultValue;
      final Map<K, ? extends V> map;


      ForMapWithDefault(Map<K, ? extends V> var1, V var2) {
         Map var3 = (Map)Preconditions.checkNotNull(var1);
         this.map = var3;
         this.defaultValue = var2;
      }

      public V apply(K var1) {
         Object var2;
         if(this.map.containsKey(var1)) {
            var2 = this.map.get(var1);
         } else {
            var2 = this.defaultValue;
         }

         return var2;
      }

      public boolean equals(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Functions.ForMapWithDefault) {
            Functions.ForMapWithDefault var3 = (Functions.ForMapWithDefault)var1;
            Map var4 = this.map;
            Map var5 = var3.map;
            if(var4.equals(var5)) {
               Object var6 = this.defaultValue;
               Object var7 = var3.defaultValue;
               if(Objects.equal(var6, var7)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         Object[] var1 = new Object[2];
         Map var2 = this.map;
         var1[0] = var2;
         Object var3 = this.defaultValue;
         var1[1] = var3;
         return Objects.hashCode(var1);
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("forMap(");
         Map var2 = this.map;
         StringBuilder var3 = var1.append(var2).append(", defaultValue=");
         Object var4 = this.defaultValue;
         return var3.append(var4).append(")").toString();
      }
   }

   private static enum IdentityFunction implements Function<Object, Object> {

      // $FF: synthetic field
      private static final Functions.IdentityFunction[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Functions.IdentityFunction[] var0 = new Functions.IdentityFunction[1];
         Functions.IdentityFunction var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private IdentityFunction(String var1, int var2) {}

      public Object apply(Object var1) {
         return var1;
      }

      public String toString() {
         return "identity";
      }
   }

   private static class FunctionForMapNoDefault<K extends Object, V extends Object> implements Function<K, V>, Serializable {

      private static final long serialVersionUID;
      final Map<K, V> map;


      FunctionForMapNoDefault(Map<K, V> var1) {
         Map var2 = (Map)Preconditions.checkNotNull(var1);
         this.map = var2;
      }

      public V apply(K var1) {
         Object var2 = this.map.get(var1);
         byte var3;
         if(var2 == null && !this.map.containsKey(var1)) {
            var3 = 0;
         } else {
            var3 = 1;
         }

         Object[] var4 = new Object[]{var1};
         Preconditions.checkArgument((boolean)var3, "Key \'%s\' not present in map", var4);
         return var2;
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Functions.FunctionForMapNoDefault) {
            Functions.FunctionForMapNoDefault var2 = (Functions.FunctionForMapNoDefault)var1;
            Map var3 = this.map;
            Map var4 = var2.map;
            var5 = var3.equals(var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         return this.map.hashCode();
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("forMap(");
         Map var2 = this.map;
         return var1.append(var2).append(")").toString();
      }
   }

   private static enum ToStringFunction implements Function<Object, String> {

      // $FF: synthetic field
      private static final Functions.ToStringFunction[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Functions.ToStringFunction[] var0 = new Functions.ToStringFunction[1];
         Functions.ToStringFunction var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private ToStringFunction(String var1, int var2) {}

      public String apply(Object var1) {
         return var1.toString();
      }

      public String toString() {
         return "toString";
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class FunctionComposition<A extends Object, B extends Object, C extends Object> implements Function<A, C>, Serializable {

      private static final long serialVersionUID;
      private final Function<A, ? extends B> f;
      private final Function<B, C> g;


      public FunctionComposition(Function<B, C> var1, Function<A, ? extends B> var2) {
         Function var3 = (Function)Preconditions.checkNotNull(var1);
         this.g = var3;
         Function var4 = (Function)Preconditions.checkNotNull(var2);
         this.f = var4;
      }

      public C apply(A var1) {
         Function var2 = this.g;
         Object var3 = this.f.apply(var1);
         return var2.apply(var3);
      }

      public boolean equals(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Functions.FunctionComposition) {
            Functions.FunctionComposition var3 = (Functions.FunctionComposition)var1;
            Function var4 = this.f;
            Function var5 = var3.f;
            if(var4.equals(var5)) {
               Function var6 = this.g;
               Function var7 = var3.g;
               if(var6.equals(var7)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         int var1 = this.f.hashCode();
         int var2 = this.g.hashCode();
         return var1 ^ var2;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.g.toString();
         StringBuilder var3 = var1.append(var2).append("(");
         String var4 = this.f.toString();
         return var3.append(var4).append(")").toString();
      }
   }

   private static class PredicateFunction<T extends Object> implements Function<T, Boolean>, Serializable {

      private static final long serialVersionUID;
      private final Predicate<T> predicate;


      private PredicateFunction(Predicate<T> var1) {
         Predicate var2 = (Predicate)Preconditions.checkNotNull(var1);
         this.predicate = var2;
      }

      // $FF: synthetic method
      PredicateFunction(Predicate var1, Functions.1 var2) {
         this(var1);
      }

      public Boolean apply(T var1) {
         return Boolean.valueOf(this.predicate.apply(var1));
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Functions.PredicateFunction) {
            Functions.PredicateFunction var2 = (Functions.PredicateFunction)var1;
            Predicate var3 = this.predicate;
            Predicate var4 = var2.predicate;
            var5 = var3.equals(var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         return this.predicate.hashCode();
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("forPredicate(");
         Predicate var2 = this.predicate;
         return var1.append(var2).append(")").toString();
      }
   }

   private static class ConstantFunction<E extends Object> implements Function<Object, E>, Serializable {

      private static final long serialVersionUID;
      private final E value;


      public ConstantFunction(@Nullable E var1) {
         this.value = var1;
      }

      public E apply(Object var1) {
         return this.value;
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Functions.ConstantFunction) {
            Functions.ConstantFunction var2 = (Functions.ConstantFunction)var1;
            Object var3 = this.value;
            Object var4 = var2.value;
            var5 = Objects.equal(var3, var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         int var1;
         if(this.value == null) {
            var1 = 0;
         } else {
            var1 = this.value.hashCode();
         }

         return var1;
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("constant(");
         Object var2 = this.value;
         return var1.append(var2).append(")").toString();
      }
   }
}

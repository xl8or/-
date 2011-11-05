package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
public final class Predicates {

   private static final Joiner commaJoiner = Joiner.on(",");


   private Predicates() {}

   @GwtCompatible(
      serializable = true
   )
   public static <T extends Object> Predicate<T> alwaysFalse() {
      return Predicates.AlwaysFalsePredicate.INSTANCE;
   }

   @GwtCompatible(
      serializable = true
   )
   public static <T extends Object> Predicate<T> alwaysTrue() {
      return Predicates.AlwaysTruePredicate.INSTANCE;
   }

   public static <T extends Object> Predicate<T> and(Predicate<? super T> var0, Predicate<? super T> var1) {
      Predicate var2 = (Predicate)Preconditions.checkNotNull(var0);
      Predicate var3 = (Predicate)Preconditions.checkNotNull(var1);
      List var4 = asList(var2, var3);
      return new Predicates.AndPredicate(var4, (Predicates.1)null);
   }

   public static <T extends Object> Predicate<T> and(Iterable<? extends Predicate<? super T>> var0) {
      List var1 = defensiveCopy(var0);
      return new Predicates.AndPredicate(var1, (Predicates.1)null);
   }

   public static <T extends Object> Predicate<T> and(Predicate<? super T> ... var0) {
      List var1 = defensiveCopy((Object[])var0);
      return new Predicates.AndPredicate(var1, (Predicates.1)null);
   }

   private static <T extends Object> List<Predicate<? super T>> asList(Predicate<? super T> var0, Predicate<? super T> var1) {
      Predicate[] var2 = new Predicate[]{var0, var1};
      return Arrays.asList(var2);
   }

   public static <A extends Object, B extends Object> Predicate<A> compose(Predicate<B> var0, Function<A, ? extends B> var1) {
      return new Predicates.CompositionPredicate(var0, var1, (Predicates.1)null);
   }

   static <T extends Object> List<T> defensiveCopy(Iterable<T> var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         Object var3 = Preconditions.checkNotNull(var2.next());
         var1.add(var3);
      }

      return var1;
   }

   private static <T extends Object> List<T> defensiveCopy(T ... var0) {
      return defensiveCopy((Iterable)Arrays.asList(var0));
   }

   public static <T extends Object> Predicate<T> equalTo(@Nullable T var0) {
      Object var1;
      if(var0 == null) {
         var1 = isNull();
      } else {
         var1 = new Predicates.IsEqualToPredicate(var0, (Predicates.1)null);
      }

      return (Predicate)var1;
   }

   public static <T extends Object> Predicate<T> in(Collection<? extends T> var0) {
      return new Predicates.InPredicate(var0, (Predicates.1)null);
   }

   @GwtIncompatible("Class.isInstance")
   public static Predicate<Object> instanceOf(Class<?> var0) {
      return new Predicates.InstanceOfPredicate(var0, (Predicates.1)null);
   }

   public static <T extends Object> Predicate<T> isNull() {
      return Predicates.IsNullPredicate.INSTANCE;
   }

   private static boolean iterableElementsEqual(Iterable<?> var0, Iterable<?> var1) {
      boolean var2 = false;
      Iterator var3 = var0.iterator();
      Iterator var4 = var1.iterator();

      Object var5;
      Object var6;
      do {
         if(!var3.hasNext()) {
            if(!var4.hasNext()) {
               var2 = true;
            }
            break;
         }

         if(!var4.hasNext()) {
            break;
         }

         var5 = var3.next();
         var6 = var4.next();
      } while(var5.equals(var6));

      return var2;
   }

   public static <T extends Object> Predicate<T> not(Predicate<T> var0) {
      return new Predicates.NotPredicate(var0, (Predicates.1)null);
   }

   public static <T extends Object> Predicate<T> notNull() {
      return Predicates.NotNullPredicate.INSTANCE;
   }

   public static <T extends Object> Predicate<T> or(Predicate<? super T> var0, Predicate<? super T> var1) {
      Predicate var2 = (Predicate)Preconditions.checkNotNull(var0);
      Predicate var3 = (Predicate)Preconditions.checkNotNull(var1);
      List var4 = asList(var2, var3);
      return new Predicates.OrPredicate(var4, (Predicates.1)null);
   }

   public static <T extends Object> Predicate<T> or(Iterable<? extends Predicate<? super T>> var0) {
      List var1 = defensiveCopy(var0);
      return new Predicates.OrPredicate(var1, (Predicates.1)null);
   }

   public static <T extends Object> Predicate<T> or(Predicate<? super T> ... var0) {
      List var1 = defensiveCopy((Object[])var0);
      return new Predicates.OrPredicate(var1, (Predicates.1)null);
   }

   private static class CompositionPredicate<A extends Object, B extends Object> implements Predicate<A>, Serializable {

      private static final long serialVersionUID;
      final Function<A, ? extends B> f;
      final Predicate<B> p;


      private CompositionPredicate(Predicate<B> var1, Function<A, ? extends B> var2) {
         Predicate var3 = (Predicate)Preconditions.checkNotNull(var1);
         this.p = var3;
         Function var4 = (Function)Preconditions.checkNotNull(var2);
         this.f = var4;
      }

      // $FF: synthetic method
      CompositionPredicate(Predicate var1, Function var2, Predicates.1 var3) {
         this(var1, var2);
      }

      public boolean apply(A var1) {
         Predicate var2 = this.p;
         Object var3 = this.f.apply(var1);
         return var2.apply(var3);
      }

      public boolean equals(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Predicates.CompositionPredicate) {
            Predicates.CompositionPredicate var3 = (Predicates.CompositionPredicate)var1;
            Function var4 = this.f;
            Function var5 = var3.f;
            if(var4.equals(var5)) {
               Predicate var6 = this.p;
               Predicate var7 = var3.p;
               if(var6.equals(var7)) {
                  var2 = true;
               }
            }
         }

         return var2;
      }

      public int hashCode() {
         int var1 = this.f.hashCode();
         int var2 = this.p.hashCode();
         return var1 ^ var2;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         String var2 = this.p.toString();
         StringBuilder var3 = var1.append(var2).append("(");
         String var4 = this.f.toString();
         return var3.append(var4).append(")").toString();
      }
   }

   private static class OrPredicate<T extends Object> implements Predicate<T>, Serializable {

      private static final long serialVersionUID;
      private final Iterable<? extends Predicate<? super T>> components;


      private OrPredicate(Iterable<? extends Predicate<? super T>> var1) {
         this.components = var1;
      }

      // $FF: synthetic method
      OrPredicate(Iterable var1, Predicates.1 var2) {
         this(var1);
      }

      public boolean apply(T var1) {
         Iterator var2 = this.components.iterator();

         boolean var3;
         while(true) {
            if(var2.hasNext()) {
               if(!((Predicate)var2.next()).apply(var1)) {
                  continue;
               }

               var3 = true;
               break;
            }

            var3 = false;
            break;
         }

         return var3;
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Predicates.OrPredicate) {
            Predicates.OrPredicate var2 = (Predicates.OrPredicate)var1;
            Iterable var3 = this.components;
            Iterable var4 = var2.components;
            var5 = Predicates.iterableElementsEqual(var3, var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         int var1 = 0;

         int var3;
         for(Iterator var2 = this.components.iterator(); var2.hasNext(); var1 |= var3) {
            var3 = ((Predicate)var2.next()).hashCode();
         }

         return var1;
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("Or(");
         Joiner var2 = Predicates.commaJoiner;
         Iterable var3 = this.components;
         String var4 = var2.join(var3);
         return var1.append(var4).append(")").toString();
      }
   }

   private static enum NotNullPredicate implements Predicate<Object> {

      // $FF: synthetic field
      private static final Predicates.NotNullPredicate[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Predicates.NotNullPredicate[] var0 = new Predicates.NotNullPredicate[1];
         Predicates.NotNullPredicate var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private NotNullPredicate(String var1, int var2) {}

      public boolean apply(Object var1) {
         boolean var2;
         if(var1 != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public String toString() {
         return "NotNull";
      }
   }

   private static class NotPredicate<T extends Object> implements Predicate<T>, Serializable {

      private static final long serialVersionUID;
      private final Predicate<T> predicate;


      private NotPredicate(Predicate<T> var1) {
         Predicate var2 = (Predicate)Preconditions.checkNotNull(var1);
         this.predicate = var2;
      }

      // $FF: synthetic method
      NotPredicate(Predicate var1, Predicates.1 var2) {
         this(var1);
      }

      public boolean apply(T var1) {
         boolean var2;
         if(!this.predicate.apply(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Predicates.NotPredicate) {
            Predicates.NotPredicate var2 = (Predicates.NotPredicate)var1;
            Predicate var3 = this.predicate;
            Predicate var4 = var2.predicate;
            var5 = var3.equals(var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         return ~this.predicate.hashCode();
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("Not(");
         String var2 = this.predicate.toString();
         return var1.append(var2).append(")").toString();
      }
   }

   static enum AlwaysTruePredicate implements Predicate<Object> {

      // $FF: synthetic field
      private static final Predicates.AlwaysTruePredicate[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Predicates.AlwaysTruePredicate[] var0 = new Predicates.AlwaysTruePredicate[1];
         Predicates.AlwaysTruePredicate var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private AlwaysTruePredicate(String var1, int var2) {}

      public boolean apply(Object var1) {
         return true;
      }

      public String toString() {
         return "AlwaysTrue";
      }
   }

   static enum AlwaysFalsePredicate implements Predicate<Object> {

      // $FF: synthetic field
      private static final Predicates.AlwaysFalsePredicate[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Predicates.AlwaysFalsePredicate[] var0 = new Predicates.AlwaysFalsePredicate[1];
         Predicates.AlwaysFalsePredicate var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private AlwaysFalsePredicate(String var1, int var2) {}

      public boolean apply(Object var1) {
         return false;
      }

      public String toString() {
         return "AlwaysFalse";
      }
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class InstanceOfPredicate implements Predicate<Object>, Serializable {

      private static final long serialVersionUID;
      private final Class<?> clazz;


      private InstanceOfPredicate(Class<?> var1) {
         Class var2 = (Class)Preconditions.checkNotNull(var1);
         this.clazz = var2;
      }

      // $FF: synthetic method
      InstanceOfPredicate(Class var1, Predicates.1 var2) {
         this(var1);
      }

      public boolean apply(Object var1) {
         return Platform.isInstance(this.clazz, var1);
      }

      public boolean equals(Object var1) {
         boolean var2 = false;
         if(var1 instanceof Predicates.InstanceOfPredicate) {
            Predicates.InstanceOfPredicate var3 = (Predicates.InstanceOfPredicate)var1;
            Class var4 = this.clazz;
            Class var5 = var3.clazz;
            if(var4 == var5) {
               var2 = true;
            }
         }

         return var2;
      }

      public int hashCode() {
         return this.clazz.hashCode();
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("IsInstanceOf(");
         String var2 = this.clazz.getName();
         return var1.append(var2).append(")").toString();
      }
   }

   private static class IsEqualToPredicate<T extends Object> implements Predicate<T>, Serializable {

      private static final long serialVersionUID;
      private final T target;


      private IsEqualToPredicate(T var1) {
         this.target = var1;
      }

      // $FF: synthetic method
      IsEqualToPredicate(Object var1, Predicates.1 var2) {
         this(var1);
      }

      public boolean apply(T var1) {
         return this.target.equals(var1);
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Predicates.IsEqualToPredicate) {
            Predicates.IsEqualToPredicate var2 = (Predicates.IsEqualToPredicate)var1;
            Object var3 = this.target;
            Object var4 = var2.target;
            var5 = var3.equals(var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         return this.target.hashCode();
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("IsEqualTo(");
         Object var2 = this.target;
         return var1.append(var2).append(")").toString();
      }
   }

   private static class AndPredicate<T extends Object> implements Predicate<T>, Serializable {

      private static final long serialVersionUID;
      private final Iterable<? extends Predicate<? super T>> components;


      private AndPredicate(Iterable<? extends Predicate<? super T>> var1) {
         this.components = var1;
      }

      // $FF: synthetic method
      AndPredicate(Iterable var1, Predicates.1 var2) {
         this(var1);
      }

      public boolean apply(T var1) {
         Iterator var2 = this.components.iterator();

         boolean var3;
         while(true) {
            if(var2.hasNext()) {
               if(((Predicate)var2.next()).apply(var1)) {
                  continue;
               }

               var3 = false;
               break;
            }

            var3 = true;
            break;
         }

         return var3;
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Predicates.AndPredicate) {
            Predicates.AndPredicate var2 = (Predicates.AndPredicate)var1;
            Iterable var3 = this.components;
            Iterable var4 = var2.components;
            var5 = Predicates.iterableElementsEqual(var3, var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         int var1 = -1;

         int var3;
         for(Iterator var2 = this.components.iterator(); var2.hasNext(); var1 &= var3) {
            var3 = ((Predicate)var2.next()).hashCode();
         }

         return var1;
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("And(");
         Joiner var2 = Predicates.commaJoiner;
         Iterable var3 = this.components;
         String var4 = var2.join(var3);
         return var1.append(var4).append(")").toString();
      }
   }

   private static class InPredicate<T extends Object> implements Predicate<T>, Serializable {

      private static final long serialVersionUID;
      private final Collection<?> target;


      private InPredicate(Collection<?> var1) {
         Collection var2 = (Collection)Preconditions.checkNotNull(var1);
         this.target = var2;
      }

      // $FF: synthetic method
      InPredicate(Collection var1, Predicates.1 var2) {
         this(var1);
      }

      public boolean apply(T var1) {
         boolean var2 = false;

         boolean var3;
         try {
            var3 = this.target.contains(var1);
         } catch (NullPointerException var6) {
            return var2;
         } catch (ClassCastException var7) {
            return var2;
         }

         var2 = var3;
         return var2;
      }

      public boolean equals(Object var1) {
         boolean var5;
         if(var1 instanceof Predicates.InPredicate) {
            Predicates.InPredicate var2 = (Predicates.InPredicate)var1;
            Collection var3 = this.target;
            Collection var4 = var2.target;
            var5 = var3.equals(var4);
         } else {
            var5 = false;
         }

         return var5;
      }

      public int hashCode() {
         return this.target.hashCode();
      }

      public String toString() {
         StringBuilder var1 = (new StringBuilder()).append("In(");
         Collection var2 = this.target;
         return var1.append(var2).append(")").toString();
      }
   }

   private static enum IsNullPredicate implements Predicate<Object> {

      // $FF: synthetic field
      private static final Predicates.IsNullPredicate[] $VALUES;
      INSTANCE("INSTANCE", 0);


      static {
         Predicates.IsNullPredicate[] var0 = new Predicates.IsNullPredicate[1];
         Predicates.IsNullPredicate var1 = INSTANCE;
         var0[0] = var1;
         $VALUES = var0;
      }

      private IsNullPredicate(String var1, int var2) {}

      public boolean apply(Object var1) {
         boolean var2;
         if(var1 == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public String toString() {
         return "IsNull";
      }
   }
}

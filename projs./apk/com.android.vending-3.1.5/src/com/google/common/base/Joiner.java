package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public class Joiner {

   private final String separator;


   private Joiner(Joiner var1) {
      String var2 = var1.separator;
      this.separator = var2;
   }

   // $FF: synthetic method
   Joiner(Joiner var1, Joiner.1 var2) {
      this(var1);
   }

   private Joiner(String var1) {
      String var2 = (String)Preconditions.checkNotNull(var1);
      this.separator = var2;
   }

   private static Iterable<Object> iterable(Object var0, Object var1, Object[] var2) {
      Object var3 = Preconditions.checkNotNull(var2);
      return new Joiner.3(var2, var0, var1);
   }

   public static Joiner on(char var0) {
      String var1 = String.valueOf(var0);
      return new Joiner(var1);
   }

   public static Joiner on(String var0) {
      return new Joiner(var0);
   }

   public <A extends Object & Appendable> A appendTo(A var1, Iterable<?> var2) throws IOException {
      Object var3 = Preconditions.checkNotNull(var1);
      Iterator var4 = var2.iterator();
      if(var4.hasNext()) {
         Object var5 = var4.next();
         CharSequence var6 = this.toString(var5);
         var1.append(var6);

         while(var4.hasNext()) {
            String var8 = this.separator;
            var1.append(var8);
            Object var10 = var4.next();
            CharSequence var11 = this.toString(var10);
            var1.append(var11);
         }
      }

      return var1;
   }

   public final <A extends Object & Appendable> A appendTo(A var1, @Nullable Object var2, @Nullable Object var3, Object ... var4) throws IOException {
      Iterable var5 = iterable(var2, var3, var4);
      return this.appendTo(var1, var5);
   }

   public final <A extends Object & Appendable> A appendTo(A var1, Object[] var2) throws IOException {
      List var3 = Arrays.asList(var2);
      return this.appendTo(var1, (Iterable)var3);
   }

   public final StringBuilder appendTo(StringBuilder var1, Iterable<?> var2) {
      try {
         this.appendTo((Appendable)var1, var2);
         return var1;
      } catch (IOException var5) {
         throw new AssertionError(var5);
      }
   }

   public final StringBuilder appendTo(StringBuilder var1, @Nullable Object var2, @Nullable Object var3, Object ... var4) {
      Iterable var5 = iterable(var2, var3, var4);
      return this.appendTo(var1, var5);
   }

   public final StringBuilder appendTo(StringBuilder var1, Object[] var2) {
      List var3 = Arrays.asList(var2);
      return this.appendTo(var1, (Iterable)var3);
   }

   public final String join(Iterable<?> var1) {
      StringBuilder var2 = new StringBuilder();
      return this.appendTo(var2, var1).toString();
   }

   public final String join(@Nullable Object var1, @Nullable Object var2, Object ... var3) {
      Iterable var4 = iterable(var1, var2, var3);
      return this.join(var4);
   }

   public final String join(Object[] var1) {
      List var2 = Arrays.asList(var1);
      return this.join((Iterable)var2);
   }

   public Joiner skipNulls() {
      return new Joiner.2(this);
   }

   CharSequence toString(Object var1) {
      if(var1 instanceof CharSequence) {
         var1 = (CharSequence)var1;
      } else {
         var1 = var1.toString();
      }

      return (CharSequence)var1;
   }

   public Joiner useForNull(String var1) {
      Object var2 = Preconditions.checkNotNull(var1);
      return new Joiner.1(this, var1);
   }

   public Joiner.MapJoiner withKeyValueSeparator(String var1) {
      String var2 = (String)Preconditions.checkNotNull(var1);
      return new Joiner.MapJoiner(this, var2, (Joiner.1)null);
   }

   class 1 extends Joiner {

      // $FF: synthetic field
      final String val$nullText;


      1(Joiner var2, String var3) {
         super(var2, (Joiner.1)null);
         this.val$nullText = var3;
      }

      public Joiner skipNulls() {
         throw new UnsupportedOperationException("already specified useForNull");
      }

      CharSequence toString(Object var1) {
         Object var2;
         if(var1 == null) {
            var2 = this.val$nullText;
         } else {
            var2 = Joiner.this.toString(var1);
         }

         return (CharSequence)var2;
      }

      public Joiner useForNull(String var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         throw new UnsupportedOperationException("already specified useForNull");
      }
   }

   public static class MapJoiner {

      private Joiner joiner;
      private String keyValueSeparator;


      private MapJoiner(Joiner var1, String var2) {
         this.joiner = var1;
         this.keyValueSeparator = var2;
      }

      // $FF: synthetic method
      MapJoiner(Joiner var1, String var2, Joiner.1 var3) {
         this(var1, var2);
      }

      public <A extends Object & Appendable> A appendTo(A var1, Map<?, ?> var2) throws IOException {
         Object var3 = Preconditions.checkNotNull(var1);
         Iterator var4 = var2.entrySet().iterator();
         if(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            Joiner var6 = this.joiner;
            Object var7 = var5.getKey();
            CharSequence var8 = var6.toString(var7);
            var1.append(var8);
            String var10 = this.keyValueSeparator;
            var1.append(var10);
            Joiner var12 = this.joiner;
            Object var13 = var5.getValue();
            CharSequence var14 = var12.toString(var13);
            var1.append(var14);

            while(var4.hasNext()) {
               String var16 = this.joiner.separator;
               var1.append(var16);
               Entry var18 = (Entry)var4.next();
               Joiner var19 = this.joiner;
               Object var20 = var18.getKey();
               CharSequence var21 = var19.toString(var20);
               var1.append(var21);
               String var23 = this.keyValueSeparator;
               var1.append(var23);
               Joiner var25 = this.joiner;
               Object var26 = var18.getValue();
               CharSequence var27 = var25.toString(var26);
               var1.append(var27);
            }
         }

         return var1;
      }

      public StringBuilder appendTo(StringBuilder var1, Map<?, ?> var2) {
         try {
            this.appendTo((Appendable)var1, var2);
            return var1;
         } catch (IOException var5) {
            throw new AssertionError(var5);
         }
      }

      public String join(Map<?, ?> var1) {
         StringBuilder var2 = new StringBuilder();
         return this.appendTo(var2, var1).toString();
      }

      public Joiner.MapJoiner useForNull(String var1) {
         Joiner var2 = this.joiner.useForNull(var1);
         String var3 = this.keyValueSeparator;
         return new Joiner.MapJoiner(var2, var3);
      }
   }

   class 2 extends Joiner {

      2(Joiner var2) {
         super(var2, (Joiner.1)null);
      }

      public <A extends Object & Appendable> A appendTo(A var1, Iterable<?> var2) throws IOException {
         Object var3 = Preconditions.checkNotNull(var1, "appendable");
         Object var4 = Preconditions.checkNotNull(var2, "parts");
         Iterator var5 = var2.iterator();

         Object var6;
         while(var5.hasNext()) {
            var6 = var5.next();
            if(var6 != null) {
               CharSequence var7 = Joiner.this.toString(var6);
               var1.append(var7);
               break;
            }
         }

         while(var5.hasNext()) {
            var6 = var5.next();
            if(var6 != null) {
               String var9 = Joiner.this.separator;
               var1.append(var9);
               CharSequence var11 = Joiner.this.toString(var6);
               var1.append(var11);
            }
         }

         return var1;
      }

      public Joiner useForNull(String var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         throw new UnsupportedOperationException("already specified skipNulls");
      }

      public Joiner.MapJoiner withKeyValueSeparator(String var1) {
         Object var2 = Preconditions.checkNotNull(var1);
         throw new UnsupportedOperationException("can\'t use .skipNulls() with maps");
      }
   }

   static class 3 extends AbstractList<Object> {

      // $FF: synthetic field
      final Object val$first;
      // $FF: synthetic field
      final Object[] val$rest;
      // $FF: synthetic field
      final Object val$second;


      3(Object[] var1, Object var2, Object var3) {
         this.val$rest = var1;
         this.val$first = var2;
         this.val$second = var3;
      }

      public Object get(int var1) {
         Object var4;
         switch(var1) {
         case 0:
            var4 = this.val$first;
            break;
         case 1:
            var4 = this.val$second;
            break;
         default:
            Object[] var2 = this.val$rest;
            int var3 = var1 + -2;
            var4 = var2[var3];
         }

         return var4;
      }

      public int size() {
         return this.val$rest.length + 2;
      }
   }
}

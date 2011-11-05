package com.google.common.collect;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.Comparator;
import javax.annotation.Nullable;

public abstract class ComparisonChain {

   private static final ComparisonChain ACTIVE = new ComparisonChain.1();
   private static final ComparisonChain GREATER = new ComparisonChain.InactiveComparisonChain(1);
   private static final ComparisonChain LESS = new ComparisonChain.InactiveComparisonChain(-1);


   private ComparisonChain() {}

   // $FF: synthetic method
   ComparisonChain(ComparisonChain.1 var1) {
      this();
   }

   public static ComparisonChain start() {
      return ACTIVE;
   }

   public abstract ComparisonChain compare(double var1, double var3);

   public abstract ComparisonChain compare(float var1, float var2);

   public abstract ComparisonChain compare(int var1, int var2);

   public abstract ComparisonChain compare(long var1, long var3);

   public abstract ComparisonChain compare(Comparable<?> var1, Comparable<?> var2);

   public abstract <T extends Object> ComparisonChain compare(@Nullable T var1, @Nullable T var2, Comparator<T> var3);

   public abstract ComparisonChain compare(boolean var1, boolean var2);

   public abstract int result();

   static class 1 extends ComparisonChain {

      1() {
         super((ComparisonChain.1)null);
      }

      ComparisonChain classify(int var1) {
         ComparisonChain var2;
         if(var1 < 0) {
            var2 = ComparisonChain.LESS;
         } else if(var1 > 0) {
            var2 = ComparisonChain.GREATER;
         } else {
            var2 = ComparisonChain.ACTIVE;
         }

         return var2;
      }

      public ComparisonChain compare(double var1, double var3) {
         int var5 = Double.compare(var1, var3);
         return this.classify(var5);
      }

      public ComparisonChain compare(float var1, float var2) {
         int var3 = Float.compare(var1, var2);
         return this.classify(var3);
      }

      public ComparisonChain compare(int var1, int var2) {
         int var3 = Ints.compare(var1, var2);
         return this.classify(var3);
      }

      public ComparisonChain compare(long var1, long var3) {
         int var5 = Longs.compare(var1, var3);
         return this.classify(var5);
      }

      public ComparisonChain compare(Comparable var1, Comparable var2) {
         int var3 = var1.compareTo(var2);
         return this.classify(var3);
      }

      public <T extends Object> ComparisonChain compare(@Nullable T var1, @Nullable T var2, Comparator<T> var3) {
         int var4 = var3.compare(var1, var2);
         return this.classify(var4);
      }

      public ComparisonChain compare(boolean var1, boolean var2) {
         int var3 = Booleans.compare(var1, var2);
         return this.classify(var3);
      }

      public int result() {
         return 0;
      }
   }

   private static final class InactiveComparisonChain extends ComparisonChain {

      final int result;


      InactiveComparisonChain(int var1) {
         super((ComparisonChain.1)null);
         this.result = var1;
      }

      public ComparisonChain compare(double var1, double var3) {
         return this;
      }

      public ComparisonChain compare(float var1, float var2) {
         return this;
      }

      public ComparisonChain compare(int var1, int var2) {
         return this;
      }

      public ComparisonChain compare(long var1, long var3) {
         return this;
      }

      public ComparisonChain compare(@Nullable Comparable var1, @Nullable Comparable var2) {
         return this;
      }

      public <T extends Object> ComparisonChain compare(@Nullable T var1, @Nullable T var2, @Nullable Comparator<T> var3) {
         return this;
      }

      public ComparisonChain compare(boolean var1, boolean var2) {
         return this;
      }

      public int result() {
         return this.result;
      }
   }
}

package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
public final class Objects {

   private Objects() {}

   public static boolean equal(@Nullable Object var0, @Nullable Object var1) {
      boolean var2;
      if(var0 != var1 && (var0 == null || !var0.equals(var1))) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static int hashCode(Object ... var0) {
      return Arrays.hashCode(var0);
   }

   public static Objects.ToStringHelper toStringHelper(Object var0) {
      return new Objects.ToStringHelper(var0, (Objects.1)null);
   }

   public static class ToStringHelper {

      private static final Joiner JOINER = Joiner.on(", ");
      private final List<String> fieldString;
      private final Object instance;


      private ToStringHelper(Object var1) {
         ArrayList var2 = new ArrayList();
         this.fieldString = var2;
         Object var3 = Preconditions.checkNotNull(var1);
         this.instance = var3;
      }

      // $FF: synthetic method
      ToStringHelper(Object var1, Objects.1 var2) {
         this(var1);
      }

      @VisibleForTesting
      static String simpleName(Class<?> var0) {
         String var1 = var0.getName();
         int var2 = var1.lastIndexOf(36);
         if(var2 == -1) {
            var2 = var1.lastIndexOf(46);
         }

         int var3 = var2 + 1;
         return var1.substring(var3);
      }

      public Objects.ToStringHelper add(String var1, @Nullable Object var2) {
         StringBuilder var3 = new StringBuilder();
         String var4 = (String)Preconditions.checkNotNull(var1);
         String var5 = var3.append(var4).append("=").append(var2).toString();
         return this.addValue(var5);
      }

      public Objects.ToStringHelper addValue(@Nullable Object var1) {
         List var2 = this.fieldString;
         String var3 = String.valueOf(var1);
         var2.add(var3);
         return this;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(100);
         String var2 = simpleName(this.instance.getClass());
         StringBuilder var3 = var1.append(var2).append('{');
         Joiner var4 = JOINER;
         List var5 = this.fieldString;
         return var4.appendTo(var3, (Iterable)var5).append('}').toString();
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}

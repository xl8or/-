package com.google.common.base;

import com.google.common.base.Preconditions;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Throwables {

   private Throwables() {}

   public static List<Throwable> getCausalChain(Throwable var0) {
      Object var1 = Preconditions.checkNotNull(var0);

      ArrayList var2;
      for(var2 = new ArrayList(4); var0 != null; var0 = var0.getCause()) {
         var2.add(var0);
      }

      return Collections.unmodifiableList(var2);
   }

   public static Throwable getRootCause(Throwable var0) {
      while(true) {
         Throwable var1 = var0.getCause();
         if(var1 == null) {
            return var0;
         }

         var0 = var1;
      }
   }

   public static String getStackTraceAsString(Throwable var0) {
      StringWriter var1 = new StringWriter();
      PrintWriter var2 = new PrintWriter(var1);
      var0.printStackTrace(var2);
      return var1.toString();
   }

   public static RuntimeException propagate(Throwable var0) {
      propagateIfPossible(var0);
      throw new RuntimeException(var0);
   }

   public static <X extends Throwable> void propagateIfInstanceOf(Throwable var0, Class<X> var1) throws X {
      if(var1.isInstance(var0)) {
         throw (Throwable)var1.cast(var0);
      }
   }

   public static void propagateIfPossible(Throwable var0) {
      propagateIfInstanceOf(var0, Error.class);
      propagateIfInstanceOf(var0, RuntimeException.class);
   }

   public static <X extends Throwable> void propagateIfPossible(Throwable var0, Class<X> var1) throws X {
      propagateIfInstanceOf(var0, var1);
      propagateIfPossible(var0);
   }

   public static <X1 extends Throwable, X2 extends Throwable> void propagateIfPossible(Throwable var0, Class<X1> var1, Class<X2> var2) throws X1, X2 {
      propagateIfInstanceOf(var0, var1);
      propagateIfPossible(var0, var2);
   }

   public static Exception throwCause(Exception var0, boolean var1) throws Exception {
      Throwable var2 = var0.getCause();
      if(var2 == null) {
         throw var0;
      } else {
         if(var1) {
            StackTraceElement[] var3 = var2.getStackTrace();
            StackTraceElement[] var4 = var0.getStackTrace();
            int var5 = var3.length;
            int var6 = var4.length;
            StackTraceElement[] var7 = new StackTraceElement[var5 + var6];
            int var8 = var3.length;
            System.arraycopy(var3, 0, var7, 0, var8);
            int var9 = var3.length;
            int var10 = var4.length;
            System.arraycopy(var4, 0, var7, var9, var10);
            var2.setStackTrace(var7);
         }

         if(var2 instanceof Exception) {
            throw (Exception)var2;
         } else if(var2 instanceof Error) {
            throw (Error)var2;
         } else {
            throw var0;
         }
      }
   }
}

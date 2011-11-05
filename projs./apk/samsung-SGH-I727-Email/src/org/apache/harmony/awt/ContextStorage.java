package org.apache.harmony.awt;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import org.apache.harmony.awt.datatransfer.DTK;

public final class ContextStorage {

   private static final ContextStorage globalContext = new ContextStorage();
   private final Object contextLock;
   private DTK dtk;
   private GraphicsEnvironment graphicsEnvironment;
   private volatile boolean shutdownPending = 0;
   private Toolkit toolkit;


   public ContextStorage() {
      ContextStorage.ContextLock var1 = new ContextStorage.ContextLock((ContextStorage.ContextLock)null);
      this.contextLock = var1;
   }

   public static Object getContextLock() {
      return getCurrentContext().contextLock;
   }

   private static ContextStorage getCurrentContext() {
      return globalContext;
   }

   public static DTK getDTK() {
      return getCurrentContext().dtk;
   }

   public static Toolkit getDefaultToolkit() {
      return getCurrentContext().toolkit;
   }

   public static GraphicsEnvironment getGraphicsEnvironment() {
      return getCurrentContext().graphicsEnvironment;
   }

   public static void setDTK(DTK var0) {
      getCurrentContext().dtk = var0;
   }

   public static void setDefaultToolkit(Toolkit var0) {
      getCurrentContext().toolkit = var0;
   }

   public static void setGraphicsEnvironment(GraphicsEnvironment var0) {
      getCurrentContext().graphicsEnvironment = var0;
   }

   public static boolean shutdownPending() {
      return getCurrentContext().shutdownPending;
   }

   void shutdown() {}

   private class ContextLock {

      private ContextLock() {}

      // $FF: synthetic method
      ContextLock(ContextStorage.ContextLock var2) {
         this();
      }
   }
}

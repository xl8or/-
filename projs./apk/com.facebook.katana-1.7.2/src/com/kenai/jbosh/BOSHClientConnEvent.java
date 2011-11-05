package com.kenai.jbosh;

import com.kenai.jbosh.BOSHClient;
import com.kenai.jbosh.ComposableBody;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

public final class BOSHClientConnEvent extends EventObject {

   private static final long serialVersionUID = 1L;
   private final Throwable cause;
   private final boolean connected;
   private final List<ComposableBody> requests;


   private BOSHClientConnEvent(BOSHClient var1, boolean var2, List<ComposableBody> var3, Throwable var4) {
      super(var1);
      this.connected = var2;
      this.cause = var4;
      if(this.connected) {
         if(var4 != null) {
            throw new IllegalStateException("Cannot be connected and have a cause");
         }

         if(var3 != null && var3.size() > 0) {
            throw new IllegalStateException("Cannot be connected and have outstanding requests");
         }
      }

      if(var3 == null) {
         List var5 = Collections.emptyList();
         this.requests = var5;
      } else {
         List var6 = Collections.unmodifiableList(new ArrayList(var3));
         this.requests = var6;
      }
   }

   static BOSHClientConnEvent createConnectionClosedEvent(BOSHClient var0) {
      return new BOSHClientConnEvent(var0, (boolean)0, (List)null, (Throwable)null);
   }

   static BOSHClientConnEvent createConnectionClosedOnErrorEvent(BOSHClient var0, List<ComposableBody> var1, Throwable var2) {
      return new BOSHClientConnEvent(var0, (boolean)0, var1, var2);
   }

   static BOSHClientConnEvent createConnectionEstablishedEvent(BOSHClient var0) {
      return new BOSHClientConnEvent(var0, (boolean)1, (List)null, (Throwable)null);
   }

   public BOSHClient getBOSHClient() {
      return (BOSHClient)this.getSource();
   }

   public Throwable getCause() {
      return this.cause;
   }

   public List<ComposableBody> getOutstandingRequests() {
      return this.requests;
   }

   public boolean isConnected() {
      return this.connected;
   }

   public boolean isError() {
      boolean var1;
      if(this.cause != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}

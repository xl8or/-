package com.kenai.jbosh;

import com.kenai.jbosh.AbstractBody;
import com.kenai.jbosh.BOSHClient;
import java.util.EventObject;

public final class BOSHMessageEvent extends EventObject {

   private static final long serialVersionUID = 1L;
   private final AbstractBody body;


   private BOSHMessageEvent(Object var1, AbstractBody var2) {
      super(var1);
      if(var2 == null) {
         throw new IllegalArgumentException("message body may not be null");
      } else {
         this.body = var2;
      }
   }

   static BOSHMessageEvent createRequestSentEvent(BOSHClient var0, AbstractBody var1) {
      return new BOSHMessageEvent(var0, var1);
   }

   static BOSHMessageEvent createResponseReceivedEvent(BOSHClient var0, AbstractBody var1) {
      return new BOSHMessageEvent(var0, var1);
   }

   public AbstractBody getBody() {
      return this.body;
   }
}

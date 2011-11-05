package com.android.exchange;

import com.android.exchange.EasException;

public class IllegalHeartbeatException extends EasException {

   private static final long serialVersionUID = 1L;
   public final int mLegalHeartbeat;


   public IllegalHeartbeatException(int var1) {
      this.mLegalHeartbeat = var1;
   }
}

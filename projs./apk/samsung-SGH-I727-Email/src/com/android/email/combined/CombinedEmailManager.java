package com.android.email.combined;

import android.content.Context;
import com.android.email.combined.AccountFacade;
import com.android.email.combined.MessageFacade;

public class CombinedEmailManager {

   private static CombinedEmailManager mInstance = null;
   private AccountFacade mAccountFacade = null;
   private Context mContext;
   private MessageFacade mMessageFacade = null;


   public CombinedEmailManager(Context var1) {
      this.mContext = var1;
   }

   public static CombinedEmailManager getInstance(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public AccountFacade getAccountFacade() {
      if(this.mAccountFacade == null) {
         Context var1 = this.mContext;
         AccountFacade var2 = new AccountFacade(var1);
         this.mAccountFacade = var2;
      }

      return this.mAccountFacade;
   }

   public MessageFacade getMessageFacade() {
      if(this.mMessageFacade == null) {
         Context var1 = this.mContext;
         MessageFacade var2 = new MessageFacade(var1);
         this.mMessageFacade = var2;
      }

      return this.mMessageFacade;
   }
}

package com.htc.android.mail;

import android.content.Context;
import android.os.Handler;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.Account;
import com.htc.android.mail.AccountPool;
import com.htc.android.mail.Request;
import com.htc.android.mail.RequestController;
import java.lang.ref.WeakReference;

public class CombinedRequestController extends AbsRequestController {

   private AccountPool mAccountPool;
   private Context mContext;
   private RequestController mRequestController;


   public CombinedRequestController(Context var1, long var2) {
      this.setContext(var1);
      AccountPool var4 = AccountPool.getInstance(var1);
      this.mAccountPool = var4;
      RequestController var5 = RequestController.getInstance(var1);
      this.mRequestController = var5;
   }

   private void setContext(Context var1) {
      if(var1 != null) {
         Context var2 = var1.getApplicationContext();
         this.mContext = var2;
      }
   }

   public void addRequest(Request var1) {
      this.mRequestController.addRequest(var1);
   }

   public void addWeakHandler(WeakReference<Handler> var1) {
      this.mRequestController.addWeakHandler(var1);
   }

   public boolean checkIncomingAccount(Account var1, WeakReference<Handler> var2) throws Exception {
      return this.mRequestController.checkIncomingAccount(var1, var2);
   }

   public boolean checkOutgoingAccount(Account var1, WeakReference<Handler> var2) throws Exception {
      return this.mRequestController.checkIncomingAccount(var1, var2);
   }

   public void deleteMail(Request var1) {
      this.mRequestController.deleteMail(var1);
   }

   public void emptyMailbox(long var1, long var3) {}

   public int getRefreshCheckMoreNum(long var1) {
      return this.mRequestController.getRefreshCheckMoreNum(var1);
   }

   public boolean isSending(Account var1) {
      AccountPool var2 = this.mAccountPool;
      Context var3 = this.mContext;
      Account[] var4 = var2.getAccounts(var3);
      int var5 = var4.length;
      int var6 = 0;

      boolean var9;
      while(true) {
         if(var6 >= var5) {
            var9 = false;
            break;
         }

         Account var7 = var4[var6];
         if(var7 != null && var7.id != Long.MAX_VALUE) {
            RequestController var8 = RequestController.getInstance(this.mContext);
            if(var8 != null && var8.isSending(var7)) {
               var9 = true;
               break;
            }
         }

         ++var6;
      }

      return var9;
   }

   public boolean isServerRefreshing(Account var1) {
      AccountPool var2 = this.mAccountPool;
      Context var3 = this.mContext;
      Account[] var4 = var2.getAccounts(var3);
      int var5 = var4.length;
      int var6 = 0;

      boolean var8;
      while(true) {
         if(var6 >= var5) {
            var8 = false;
            break;
         }

         Account var7 = var4[var6];
         if(var7 != null && var7.id != Long.MAX_VALUE && this.mRequestController.isServerRefreshing(var7)) {
            var8 = true;
            break;
         }

         ++var6;
      }

      return var8;
   }

   public void markStar(Request var1) {
      this.mRequestController.markStar(var1);
   }

   public void moveMail(Request var1) {
      this.mRequestController.moveMail(var1);
   }

   public int refreshOrCheckMoreMail(Request var1, boolean var2) {
      return 0;
   }

   public void registerWeakMailRequestHandler(Account var1, WeakReference<Handler> var2) {
      AccountPool var3 = this.mAccountPool;
      Context var4 = this.mContext;
      Account[] var5 = var3.getAccounts(var4);
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Account var8 = var5[var7];
         if(var8 != null && var8.id != Long.MAX_VALUE) {
            RequestController var9 = RequestController.getInstance(this.mContext);
            if(var9 != null) {
               var9.registerWeakMailRequestHandler(var8, var2);
            }
         }
      }

   }

   public void removeRequest(long var1) {
      AccountPool var3 = this.mAccountPool;
      Context var4 = this.mContext;
      Account[] var5 = var3.getAccounts(var4);
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Account var8 = var5[var7];
         if(var8 != null && var8.id != Long.MAX_VALUE) {
            RequestController var9 = RequestController.getInstance(this.mContext);
            if(var9 != null) {
               long var10 = var8.id;
               var9.removeRequest(var10);
            }
         }
      }

   }

   public void removeRequest(long var1, int var3) {
      AccountPool var4 = this.mAccountPool;
      Context var5 = this.mContext;
      Account[] var6 = var4.getAccounts(var5);
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Account var9 = var6[var8];
         if(var9 != null && var9.id != Long.MAX_VALUE) {
            RequestController var10 = RequestController.getInstance(this.mContext);
            if(var10 != null) {
               var10.removeRequest(var1, var3);
            }
         }
      }

   }

   public void removeRequest(long var1, WeakReference<Handler> var3) {
      AccountPool var4 = this.mAccountPool;
      Context var5 = this.mContext;
      Account[] var6 = var4.getAccounts(var5);
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Account var9 = var6[var8];
         if(var9 != null && var9.id != Long.MAX_VALUE) {
            RequestController var10 = RequestController.getInstance(this.mContext);
            if(var10 != null) {
               long var11 = var9.id;
               var10.removeRequest(var11, var3);
            }
         }
      }

   }

   public void removeRequest(Request var1) {
      AccountPool var2 = this.mAccountPool;
      Context var3 = this.mContext;
      Account[] var4 = var2.getAccounts(var3);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Account var7 = var4[var6];
         if(var7 != null && var7.id != Long.MAX_VALUE) {
            RequestController var8 = RequestController.getInstance(this.mContext);
            if(var8 != null) {
               var8.removeRequest(var1);
            }
         }
      }

   }

   public void removeRequest(Request var1, boolean var2) {
      AccountPool var3 = this.mAccountPool;
      Context var4 = this.mContext;
      Account[] var5 = var3.getAccounts(var4);
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Account var8 = var5[var7];
         if(var8 != null && var8.id != Long.MAX_VALUE) {
            RequestController var9 = RequestController.getInstance(this.mContext);
            if(var9 != null) {
               var9.removeRequest(var1, var2);
            }
         }
      }

   }

   public void removeWeakHandler(WeakReference<Handler> var1) {
      AccountPool var2 = this.mAccountPool;
      Context var3 = this.mContext;
      Account[] var4 = var2.getAccounts(var3);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Account var7 = var4[var6];
         if(var7 != null && var7.id != Long.MAX_VALUE) {
            RequestController var8 = RequestController.getInstance(this.mContext);
            if(var8 != null) {
               var8.removeWeakHandler(var1);
            }
         }
      }

   }

   public void sendMail(Account var1, long var2, int var4) {}

   public void setReadStatus(Request var1) {
      if(var1 != null) {
         if(var1.getAccount() != null) {
            long var2 = var1.getAccount().id;
            RequestController var4 = RequestController.getInstance(this.mContext);
            if(var4 != null) {
               var4.setReadStatus(var1);
            }
         }
      }
   }

   public void stopCheckAccount() {}

   public void stopRequest(WeakReference<Handler> var1) {
      AccountPool var2 = this.mAccountPool;
      Context var3 = this.mContext;
      Account[] var4 = var2.getAccounts(var3);
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Account var7 = var4[var6];
         if(var7 != null && var7.id != Long.MAX_VALUE) {
            RequestController var8 = RequestController.getInstance(this.mContext);
            if(var8 != null) {
               var8.stopRequest(var1);
            }
         }
      }

   }

   public void unregisterWeakMailRequestHandler(Account var1, WeakReference<Handler> var2) {
      AccountPool var3 = this.mAccountPool;
      Context var4 = this.mContext;
      Account[] var5 = var3.getAccounts(var4);
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Account var8 = var5[var7];
         if(var8 != null && var8.id != Long.MAX_VALUE) {
            RequestController var9 = RequestController.getInstance(this.mContext);
            if(var9 != null) {
               var9.unregisterWeakMailRequestHandler(var8, var2);
            }
         }
      }

   }
}

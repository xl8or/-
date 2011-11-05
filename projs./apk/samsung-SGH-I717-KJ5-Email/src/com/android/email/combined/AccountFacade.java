package com.android.email.combined;

import android.content.Context;
import android.util.Log;
import com.android.email.combined.AccountBehavior;
import com.digc.seven.Z7MailHandler;

public class AccountFacade {

   private Context mContext;


   public AccountFacade(Context var1) {
      this.mContext = var1;
   }

   private AccountBehavior getBehavior() {
      return AccountBehavior.getInstance(this.mContext);
   }

   public void addListener(Context var1) {
      AccountBehavior var2 = this.getBehavior();
      AccountFacade.AccountListener var3 = (AccountFacade.AccountListener)var1;
      var2.addListener(var3);
   }

   public void addedAccount(int var1, String var2) {
      int var3 = Log.d("###", "--------------------mook----------------");
      Z7MailHandler.getInstance(this.mContext).setMookSevenNoti();
      this.getBehavior().notifyAddedAccount(var1, var2);
   }

   public boolean isPremiumUser(long var1) {
      return this.getBehavior().isPremiumUser(var1);
   }

   public boolean isPremiumUser(Context var1) {
      return this.getBehavior().isPremiumUser(var1);
   }

   public boolean isPremiumUser(Context var1, long var2) {
      return this.getBehavior().isPremiumUser(var1, var2);
   }

   public void quietRemoveAccount(int var1) {
      int var2 = this.getBehavior().deleteAccountForEmail(var1);
   }

   public void removeAccount(int var1) {
      this.getBehavior().notifyRemovedAccount(var1);
   }

   public void removeListener(Context var1) {
      AccountBehavior var2 = this.getBehavior();
      AccountFacade.AccountListener var3 = (AccountFacade.AccountListener)var1;
      var2.removeListener(var3);
   }

   public void setAccountFontSize(int var1) {
      this.getBehavior().notifyAccountFontSize(var1);
   }

   public interface AccountListener {

      void onAccountAdded(int var1, int var2);

      void onAccountFailed(Exception var1);

      void onAccountFinished();

      void onAccountFontSize(int var1);

      void onAccountRemoved(int var1);

      void onAccountStarted();
   }
}

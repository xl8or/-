package com.seven.Z7.shared;


public class IMRosterCallbackData {

   private int accountId;
   private String userId;


   public IMRosterCallbackData() {
      this.accountId = -1;
      this.userId = null;
   }

   public IMRosterCallbackData(int var1, String var2) {
      this.accountId = var1;
      this.userId = var2;
   }

   public int getAccountId() {
      return this.accountId;
   }

   public String getUserId() {
      return this.userId;
   }

   public void setAccountId(int var1) {
      this.accountId = var1;
   }

   public void setUserId(String var1) {
      this.userId = var1;
   }
}

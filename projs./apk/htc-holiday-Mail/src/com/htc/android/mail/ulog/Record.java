package com.htc.android.mail.ulog;


public class Record {

   private long accountId;
   private long mailReceivedNum;
   private long mailSentNum;
   private long recordTime;


   Record(long var1, long var3, long var5, long var7) {
      this.accountId = var1;
      this.mailReceivedNum = var3;
      this.mailSentNum = var5;
      this.recordTime = var7;
   }

   public long getId() {
      return this.accountId;
   }

   public long getMailReceivedNum() {
      return this.mailReceivedNum;
   }

   public long getMailSentNum() {
      return this.mailSentNum;
   }

   public long getRecordTime() {
      return this.recordTime;
   }

   public void setMailReceivedNum(long var1) {
      this.mailReceivedNum = var1;
   }

   public void setMailSentNum(long var1) {
      this.mailSentNum = var1;
   }

   public void setRecordTime(long var1) {
      this.recordTime = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = String.valueOf(this.mailReceivedNum);
      var1.append(var2);
      StringBuilder var4 = (new StringBuilder()).append(",");
      String var5 = String.valueOf(this.mailSentNum);
      String var6 = var4.append(var5).toString();
      var1.append(var6);
      StringBuilder var8 = (new StringBuilder()).append(",");
      String var9 = String.valueOf(this.recordTime);
      String var10 = var8.append(var9).toString();
      var1.append(var10);
      return var1.toString();
   }
}

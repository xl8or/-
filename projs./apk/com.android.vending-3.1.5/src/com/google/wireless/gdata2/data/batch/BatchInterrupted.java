package com.google.wireless.gdata2.data.batch;


public class BatchInterrupted {

   private int error;
   private String reason;
   private int success;
   private int total;


   public BatchInterrupted() {}

   public int getErrorCount() {
      return this.error;
   }

   public String getReason() {
      return this.reason;
   }

   public int getSuccessCount() {
      return this.success;
   }

   public int getTotalCount() {
      return this.total;
   }

   public void setErrorCount(int var1) {
      this.error = var1;
   }

   public void setReason(String var1) {
      this.reason = var1;
   }

   public void setSuccessCount(int var1) {
      this.success = var1;
   }

   public void setTotalCount(int var1) {
      this.total = var1;
   }
}

package com.google.wireless.gdata2.data.batch;


public class BatchStatus {

   private String content;
   private String contentType;
   private String reason;
   private int statusCode;


   public BatchStatus() {}

   public String getContent() {
      return this.content;
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getReason() {
      return this.reason;
   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public void setContent(String var1) {
      this.content = var1;
   }

   public void setContentType(String var1) {
      this.contentType = var1;
   }

   public void setReason(String var1) {
      this.reason = var1;
   }

   public void setStatusCode(int var1) {
      this.statusCode = var1;
   }
}

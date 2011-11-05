package com.google.android.finsky.download;


public class DownloadProgress {

   public final long bytesCompleted;
   public final long bytesTotal;
   final int mStatusId;


   public DownloadProgress(long var1, long var3, int var5) {
      this.bytesCompleted = var1;
      this.bytesTotal = var3;
      this.mStatusId = var5;
   }

   public boolean equals(Object var1) {
      boolean var2 = false;
      if(var1 != null && var1 instanceof DownloadProgress) {
         DownloadProgress var3 = (DownloadProgress)var1;
         long var4 = this.bytesCompleted;
         long var6 = var3.bytesCompleted;
         if(var4 == var6) {
            long var8 = this.bytesTotal;
            long var10 = var3.bytesTotal;
            if(var8 == var10) {
               int var12 = this.mStatusId;
               int var13 = var3.mStatusId;
               if(var12 == var13) {
                  var2 = true;
               }
            }
         }
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      long var2 = this.bytesCompleted;
      StringBuilder var4 = var1.append(var2).append("/");
      long var5 = this.bytesTotal;
      StringBuilder var7 = var4.append(var5).append(" Status: ");
      int var8 = this.mStatusId;
      return var7.append(var8).toString();
   }
}

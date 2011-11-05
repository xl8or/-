package com.google.wireless.gdata2.data.batch;

import com.google.wireless.gdata2.data.batch.BatchInterrupted;
import com.google.wireless.gdata2.data.batch.BatchStatus;

public class BatchInfo {

   String id;
   BatchInterrupted interrupted;
   String operation;
   BatchStatus status;


   BatchInfo() {}

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("id: ");
      String var3 = this.id;
      var2.append(var3);
      StringBuffer var5 = var1.append(" op: ");
      String var6 = this.operation;
      var5.append(var6);
      if(this.status != null) {
         StringBuffer var8 = var1.append(" sc: ");
         int var9 = this.status.getStatusCode();
         var8.append(var9);
      }

      if(this.interrupted != null) {
         StringBuffer var11 = var1.append(" interrupted: ");
         String var12 = this.interrupted.getReason();
         var11.append(var12);
      }

      return var1.toString();
   }
}

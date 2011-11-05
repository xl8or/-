package com.htc.android.mail.eassvc.core;

import android.os.Bundle;
import android.os.ConditionVariable;
import com.htc.android.mail.eassvc.common.ExchangeSyncSources;
import com.htc.android.mail.eassvc.core.EASRequestController;
import java.util.Date;

public class EASRequest {

   public long accountId = 65535L;
   public ConditionVariable blockCondition;
   public int command;
   public ExchangeSyncSources exSyncSources;
   public Bundle parameter;
   public int priority = 0;
   public long reqTime;
   public EASRequestController requestController;
   public Object returnObject;
   public int syncSourceType = -1;


   public EASRequest() {
      long var1 = (new Date()).getTime();
      this.reqTime = var1;
   }

   public EASRequest(EASRequest var1) {
      ExchangeSyncSources var2 = var1.exSyncSources;
      this.exSyncSources = var2;
      int var3 = var1.syncSourceType;
      this.syncSourceType = var3;
      int var4 = var1.command;
      this.command = var4;
      long var5 = var1.accountId;
      this.accountId = var5;
      int var7 = var1.priority;
      this.priority = var7;
      Bundle var8 = var1.parameter;
      this.parameter = var8;
      Object var9 = var1.returnObject;
      this.returnObject = var9;
      long var10 = var1.reqTime;
      this.reqTime = var10;
      ConditionVariable var12 = var1.blockCondition;
      this.blockCondition = var12;
      EASRequestController var13 = var1.requestController;
      this.requestController = var13;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("account:");
      long var2 = this.accountId;
      StringBuilder var4 = var1.append(var2).append(", cmd:");
      int var5 = this.command;
      StringBuilder var6 = var4.append(var5).append(", srcType:");
      int var7 = this.syncSourceType;
      StringBuilder var8 = var6.append(var7).append(", priority:");
      int var9 = this.priority;
      StringBuilder var10 = var8.append(var9);
      String var11;
      if(this.parameter == null) {
         var11 = "";
      } else {
         StringBuilder var12 = (new StringBuilder()).append(", parameter:");
         Bundle var13 = this.parameter;
         var11 = var12.append(var13).toString();
      }

      return var10.append(var11).toString();
   }
}

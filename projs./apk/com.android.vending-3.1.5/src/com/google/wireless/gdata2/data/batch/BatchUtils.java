package com.google.wireless.gdata2.data.batch;

import com.google.wireless.gdata2.data.Entry;
import com.google.wireless.gdata2.data.batch.BatchInfo;
import com.google.wireless.gdata2.data.batch.BatchInterrupted;
import com.google.wireless.gdata2.data.batch.BatchStatus;

public class BatchUtils {

   public static final String OPERATION_DELETE = "delete";
   public static final String OPERATION_INSERT = "insert";
   public static final String OPERATION_QUERY = "query";
   public static final String OPERATION_UPDATE = "update";


   private BatchUtils() {}

   public static String getBatchId(Entry var0) {
      BatchInfo var1 = var0.getBatchInfo();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.id;
      }

      return var2;
   }

   public static BatchInterrupted getBatchInterrupted(Entry var0) {
      BatchInfo var1 = var0.getBatchInfo();
      BatchInterrupted var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.interrupted;
      }

      return var2;
   }

   public static String getBatchOperation(Entry var0) {
      BatchInfo var1 = var0.getBatchInfo();
      String var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.operation;
      }

      return var2;
   }

   public static BatchStatus getBatchStatus(Entry var0) {
      BatchInfo var1 = var0.getBatchInfo();
      BatchStatus var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.status;
      }

      return var2;
   }

   private static BatchInfo getOrCreateBatchInfo(Entry var0) {
      BatchInfo var1 = var0.getBatchInfo();
      if(var1 == null) {
         var1 = new BatchInfo();
         var0.setBatchInfo(var1);
      }

      return var1;
   }

   public static void setBatchId(Entry var0, String var1) {
      getOrCreateBatchInfo(var0).id = var1;
   }

   public static void setBatchInterrupted(Entry var0, BatchInterrupted var1) {
      getOrCreateBatchInfo(var0).interrupted = var1;
   }

   public static void setBatchOperation(Entry var0, String var1) {
      getOrCreateBatchInfo(var0).operation = var1;
   }

   public static void setBatchStatus(Entry var0, BatchStatus var1) {
      getOrCreateBatchInfo(var0).status = var1;
   }
}

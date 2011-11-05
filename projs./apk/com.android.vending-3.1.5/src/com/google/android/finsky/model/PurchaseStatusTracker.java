package com.google.android.finsky.model;

import android.content.Intent;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class PurchaseStatusTracker {

   public static final int OFFER_FREE_APP = 255;
   private final HashSet<PurchaseStatusTracker.PurchaseStatusListener> mListeners;
   public final Map<String, PurchaseStatusTracker.PurchaseStatus> mPurchaseStatusMap;


   public PurchaseStatusTracker() {
      HashSet var1 = Sets.newHashSet();
      this.mListeners = var1;
      HashMap var2 = Maps.newHashMap();
      this.mPurchaseStatusMap = var2;
   }

   private static String getPackageName(Intent var0) {
      String var1 = var0.getData().getSchemeSpecificPart();
      if(FinskyLog.DEBUG) {
         Object[] var2 = new Object[]{var1};
         FinskyLog.v("Package successfully installed: %s", var2);
      }

      return var1;
   }

   private void notifyPurchaseStateChanged(String var1, PurchaseStatusTracker.PurchaseStatus var2) {
      Iterator var3 = this.mListeners.iterator();

      while(var3.hasNext()) {
         ((PurchaseStatusTracker.PurchaseStatusListener)var3.next()).onPurchaseStatusChanged(var1, var2);
      }

   }

   private void switchState(String var1, int var2, PurchaseStatusTracker.PurchaseState var3, PurchaseStatusTracker.Error var4) {
      if(var1 == null) {
         throw new IllegalStateException("Cannot track the purchase of an item with a null doc ID");
      } else {
         PurchaseStatusTracker.PurchaseStatus var5;
         if(this.mPurchaseStatusMap.containsKey(var1)) {
            var5 = (PurchaseStatusTracker.PurchaseStatus)this.mPurchaseStatusMap.get(var1);
         } else {
            var5 = new PurchaseStatusTracker.PurchaseStatus();
            this.mPurchaseStatusMap.put(var1, var5);
         }

         var5.docId = var1;
         var5.state = var3;
         var5.error = var4;
         PurchaseStatusTracker.PurchaseState var6 = PurchaseStatusTracker.PurchaseState.PURCHASE_INITIATED;
         if(var3 == var6 && var2 == 0) {
            var2 = 1;
         }

         var5.offerType = var2;
         this.notifyPurchaseStateChanged(var1, var5);
      }
   }

   public void attach(PurchaseStatusTracker.PurchaseStatusListener var1) {
      this.mListeners.add(var1);
   }

   public void clearPurchaseStatusMap() {
      this.mPurchaseStatusMap.clear();
   }

   public void detach(PurchaseStatusTracker.PurchaseStatusListener var1) {
      this.mListeners.remove(var1);
   }

   public PurchaseStatusTracker.PurchaseStatus getPurchaseStatus(String var1) {
      return (PurchaseStatusTracker.PurchaseStatus)this.mPurchaseStatusMap.get(var1);
   }

   public boolean isPendingPurchase(String var1) {
      boolean var4;
      if(this.mPurchaseStatusMap.containsKey(var1)) {
         PurchaseStatusTracker.PurchaseState var2 = ((PurchaseStatusTracker.PurchaseStatus)this.mPurchaseStatusMap.get(var1)).state;
         PurchaseStatusTracker.PurchaseState var3 = PurchaseStatusTracker.PurchaseState.PURCHASE_INITIATED;
         if(var2 == var3) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public void remove(String var1) {
      if(this.mPurchaseStatusMap.containsKey(var1)) {
         this.mPurchaseStatusMap.remove(var1);
      }
   }

   public void switchState(String var1, int var2, PurchaseStatusTracker.PurchaseState var3) {
      PurchaseStatusTracker.Error var4 = null;
      PurchaseStatusTracker.PurchaseState var5 = PurchaseStatusTracker.PurchaseState.PURCHASE_COMPLETED_WITH_ERROR;
      if(var3 == var5) {
         var4 = new PurchaseStatusTracker.Error();
      }

      this.switchState(var1, var2, var3, var4);
   }

   public void switchToError(String var1, int var2, PurchaseStatusTracker.Error var3) {
      PurchaseStatusTracker.PurchaseState var4 = PurchaseStatusTracker.PurchaseState.PURCHASE_COMPLETED_WITH_ERROR;
      this.switchState(var1, var2, var4, var3);
   }

   public class PurchaseStatus {

      public String docId;
      public PurchaseStatusTracker.Error error;
      public int offerType;
      public PurchaseStatusTracker.PurchaseState state;


      public PurchaseStatus() {}
   }

   public interface PurchaseStatusListener {

      void onPurchaseStatusChanged(String var1, PurchaseStatusTracker.PurchaseStatus var2);
   }

   public static class Error {

      public String briefMessage;
      public String detailedMessage;
      public String detailsUrl;
      public String docTitle;
      public String sourceUrl;
      public String title;


      public Error() {}

      public String toString() {
         Object[] var1 = new Object[6];
         String var2 = this.title;
         var1[0] = var2;
         String var3 = this.docTitle;
         var1[1] = var3;
         String var4 = this.briefMessage;
         var1[2] = var4;
         String var5 = this.detailedMessage;
         var1[3] = var5;
         String var6 = this.sourceUrl;
         var1[4] = var6;
         String var7 = this.detailsUrl;
         var1[5] = var7;
         return String.format("Error{title=\'%s\', docTitle=\'%s\', briefMessage=\'%s\', detailedMessage=\'%s\', sourceUrl=\'%s\', detailsUrl=\'%s\'}", var1);
      }
   }

   public static enum PurchaseState {

      // $FF: synthetic field
      private static final PurchaseStatusTracker.PurchaseState[] $VALUES;
      PURCHASE_COMPLETED("PURCHASE_COMPLETED", 1),
      PURCHASE_COMPLETED_WITH_ERROR("PURCHASE_COMPLETED_WITH_ERROR", 2),
      PURCHASE_INITIATED("PURCHASE_INITIATED", 0);


      static {
         PurchaseStatusTracker.PurchaseState[] var0 = new PurchaseStatusTracker.PurchaseState[3];
         PurchaseStatusTracker.PurchaseState var1 = PURCHASE_INITIATED;
         var0[0] = var1;
         PurchaseStatusTracker.PurchaseState var2 = PURCHASE_COMPLETED;
         var0[1] = var2;
         PurchaseStatusTracker.PurchaseState var3 = PURCHASE_COMPLETED_WITH_ERROR;
         var0[2] = var3;
         $VALUES = var0;
      }

      private PurchaseState(String var1, int var2) {}
   }
}

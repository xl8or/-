package com.google.android.finsky.billing;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;

public abstract class BillingFlow {

   public static final int REQUEST_CODE_LOWER_BYTE_MASK = 255;
   public static final int REQUEST_CODE_UPPER_BYTES = -559087616;
   protected final BillingFlowContext mBillingFlowContext;
   private boolean mFinished;
   private final BillingFlowListener mListener;
   protected final Bundle mParameters;


   public BillingFlow(BillingFlowContext var1, BillingFlowListener var2, Bundle var3) {
      this.mParameters = var3;
      this.mBillingFlowContext = var1;
      this.mListener = var2;
   }

   private static byte getFlowSpecificCode(int var0) {
      return (byte)(var0 & 255);
   }

   private static boolean isFlowResultCode(int var0) {
      boolean var1;
      if((var0 & -256) == -559087616) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void notifyError(String var1) {
      this.mListener.onError(this, var1);
   }

   private void notifyFinished(boolean var1) {
      this.mListener.onFinished(this, var1);
   }

   public void back() {
      throw new UnsupportedOperationException();
   }

   public boolean canGoBack() {
      return false;
   }

   public void cancel() {
      this.mFinished = (boolean)1;
      this.notifyFinished((boolean)1);
   }

   protected void fail(String var1) {
      this.mFinished = (boolean)1;
      this.notifyError(var1);
   }

   protected void finish() {
      this.mFinished = (boolean)1;
      this.notifyFinished((boolean)0);
   }

   public int getDisplayTitleResourceId() {
      return -1;
   }

   public boolean isFinished() {
      return this.mFinished;
   }

   protected void onActivityResult(byte var1, int var2, Bundle var3) {}

   public boolean onActivityResult(int var1, int var2, Intent var3) {
      if(isFlowResultCode(var1)) {
         byte var4 = getFlowSpecificCode(var1);
         this.onActivityResult((int)var4, var2, var3);
      }

      return false;
   }

   public void resumeFromSavedState(Bundle var1) {
      throw new UnsupportedOperationException();
   }

   public void saveState(Bundle var1) {
      throw new UnsupportedOperationException();
   }

   public abstract void start();

   protected void startActivityForResult(Intent var1, byte var2) {
      BillingFlowContext var3 = this.mBillingFlowContext;
      int var4 = -559087616 & var2;
      var3.startActivityForResult(var1, var4);
   }
}

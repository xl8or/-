package com.google.android.finsky.billing;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.remoting.protos.Buy;
import java.util.Map;

public abstract class Instrument {

   private Buy.BuyResponse.CheckoutInfo.CheckoutOption mCheckoutOption;
   protected final Drawable mDisplayIcon;


   protected Instrument(Buy.BuyResponse.CheckoutInfo.CheckoutOption var1, Drawable var2) {
      this.mCheckoutOption = var1;
      this.mDisplayIcon = var2;
   }

   public abstract BillingFlow completePurchase(BillingFlowContext var1, BillingFlowListener var2, Bundle var3);

   public Buy.BuyResponse.CheckoutInfo.CheckoutOption getCheckoutOption() {
      return this.mCheckoutOption;
   }

   public Map<String, String> getCompleteParams() {
      return null;
   }

   public Drawable getDisplayIcon() {
      return this.mDisplayIcon;
   }

   public String getDisplayName() {
      return this.mCheckoutOption.getFormOfPayment();
   }

   public int getInstrumentFamily() {
      return this.mCheckoutOption.getInstrumentFamily();
   }

   public String getInstrumentId() {
      return this.mCheckoutOption.getInstrumentId();
   }

   public boolean isValid() {
      return this.mCheckoutOption.hasEncodedAdjustedCart();
   }

   public String toString() {
      return this.getDisplayName();
   }
}

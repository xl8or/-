package com.google.android.finsky.billing;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.Instrument;
import com.google.android.finsky.remoting.protos.Buy;
import com.google.android.finsky.utils.Maps;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FopFactory {

   private Map<Integer, FopFactory.FormOfPayment> mFormsOfPayment;


   public FopFactory() {
      HashMap var1 = Maps.newHashMap();
      this.mFormsOfPayment = var1;
   }

   public boolean canAdd(int var1) {
      Map var2 = this.mFormsOfPayment;
      Integer var3 = Integer.valueOf(var1);
      if(var2.containsKey(var3)) {
         Map var4 = this.mFormsOfPayment;
         Integer var5 = Integer.valueOf(var1);
         return ((FopFactory.FormOfPayment)var4.get(var5)).canAdd();
      } else {
         String var6 = "Missing FOP for type " + var1;
         throw new IllegalArgumentException(var6);
      }
   }

   public BillingFlow create(int var1, BillingFlowContext var2, BillingFlowListener var3, Bundle var4) {
      Map var5 = this.mFormsOfPayment;
      Integer var6 = Integer.valueOf(var1);
      if(var5.containsKey(var6)) {
         Map var7 = this.mFormsOfPayment;
         Integer var8 = Integer.valueOf(var1);
         return ((FopFactory.FormOfPayment)var7.get(var8)).create(var2, var3, var4);
      } else {
         String var9 = "Missing FOP for type " + var1;
         throw new IllegalArgumentException(var9);
      }
   }

   public Instrument get(int var1, Buy.BuyResponse.CheckoutInfo.CheckoutOption var2, Drawable var3) {
      Map var4 = this.mFormsOfPayment;
      Integer var5 = Integer.valueOf(var1);
      if(var4.containsKey(var5)) {
         Map var6 = this.mFormsOfPayment;
         Integer var7 = Integer.valueOf(var1);
         return ((FopFactory.FormOfPayment)var6.get(var7)).get(var2, var3);
      } else {
         String var8 = "Missing FOP for type " + var1;
         throw new IllegalArgumentException(var8);
      }
   }

   public Drawable getAddIcon(int var1) {
      Map var2 = this.mFormsOfPayment;
      Integer var3 = Integer.valueOf(var1);
      if(var2.containsKey(var3)) {
         Map var4 = this.mFormsOfPayment;
         Integer var5 = Integer.valueOf(var1);
         return ((FopFactory.FormOfPayment)var4.get(var5)).getAddIcon();
      } else {
         String var6 = "Missing FOP for type " + var1;
         throw new IllegalArgumentException(var6);
      }
   }

   public String getAddText(int var1) {
      Map var2 = this.mFormsOfPayment;
      Integer var3 = Integer.valueOf(var1);
      if(var2.containsKey(var3)) {
         Map var4 = this.mFormsOfPayment;
         Integer var5 = Integer.valueOf(var1);
         return ((FopFactory.FormOfPayment)var4.get(var5)).getAddText();
      } else {
         String var6 = "Missing FOP for type " + var1;
         throw new IllegalArgumentException(var6);
      }
   }

   public Map<String, String> getAllPrepareParameters() {
      HashMap var1 = Maps.newHashMap();
      Iterator var2 = this.mFormsOfPayment.values().iterator();

      while(var2.hasNext()) {
         Map var3 = ((FopFactory.FormOfPayment)var2.next()).getPrepareParams();
         if(var3 != null) {
            var1.putAll(var3);
         }
      }

      return var1;
   }

   public boolean isRegistered(int var1) {
      Map var2 = this.mFormsOfPayment;
      Integer var3 = Integer.valueOf(var1);
      return var2.containsKey(var3);
   }

   public void registerFormOfPayment(int var1, FopFactory.FormOfPayment var2) {
      Map var3 = this.mFormsOfPayment;
      Integer var4 = Integer.valueOf(var1);
      if(var3.containsKey(var4)) {
         String var5 = "Already have a FOP for type " + var1;
         throw new IllegalArgumentException(var5);
      } else {
         Map var6 = this.mFormsOfPayment;
         Integer var7 = Integer.valueOf(var1);
         var6.put(var7, var2);
      }
   }

   public abstract static class FormOfPayment {

      public FormOfPayment() {}

      public abstract boolean canAdd();

      public abstract BillingFlow create(BillingFlowContext var1, BillingFlowListener var2, Bundle var3);

      public abstract Instrument get(Buy.BuyResponse.CheckoutInfo.CheckoutOption var1, Drawable var2);

      public abstract Drawable getAddIcon();

      public abstract String getAddText();

      public Map<String, String> getPrepareParams() {
         return null;
      }
   }
}

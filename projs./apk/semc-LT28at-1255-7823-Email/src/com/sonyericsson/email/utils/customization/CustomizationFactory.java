package com.sonyericsson.email.utils.customization;

import android.content.Context;
import com.sonyericsson.email.utils.customization.Customization;

public class CustomizationFactory {

   private static CustomizationFactory theObject;
   private Customization customization;
   private Customization defaultCustomization;


   private CustomizationFactory() {}

   public static CustomizationFactory getInstance() {
      if(theObject == null) {
         theObject = new CustomizationFactory();
      }

      return theObject;
   }

   public Customization getCustomization(Context var1) {
      if(this.customization == null) {
         Customization var2 = new Customization(var1);
         this.customization = var2;
      }

      return this.customization;
   }

   public Customization getDefaultCustomization(Context var1) {
      if(this.defaultCustomization == null) {
         Customization var2 = new Customization(var1, (boolean)1);
         this.defaultCustomization = var2;
      }

      return this.defaultCustomization;
   }

   public void setMockedCustomization(Customization var1) {
      this.customization = var1;
   }
}

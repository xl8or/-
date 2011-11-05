package com.google.wireless.gdata2.contacts.data;

import com.google.wireless.gdata2.contacts.data.TypedElement;

public abstract class ContactsElement extends TypedElement {

   private boolean isPrimary;


   public ContactsElement() {}

   public ContactsElement(byte var1, String var2, boolean var3) {
      super(var1, var2);
      this.isPrimary = var3;
   }

   public boolean isPrimary() {
      return this.isPrimary;
   }

   public void setIsPrimary(boolean var1) {
      this.isPrimary = var1;
   }

   public void toString(StringBuffer var1) {
      super.toString(var1);
      StringBuffer var2 = var1.append(" isPrimary:");
      boolean var3 = this.isPrimary;
      var2.append(var3);
   }
}

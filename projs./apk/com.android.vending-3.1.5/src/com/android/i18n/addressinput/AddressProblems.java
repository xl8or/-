package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblemType;
import java.util.HashMap;
import java.util.Map;

public class AddressProblems {

   private Map<AddressField, AddressProblemType> mProblems;


   public AddressProblems() {
      HashMap var1 = new HashMap();
      this.mProblems = var1;
   }

   void add(AddressField var1, AddressProblemType var2) {
      this.mProblems.put(var1, var2);
   }

   public void clear() {
      this.mProblems.clear();
   }

   public AddressProblemType getProblem(AddressField var1) {
      return (AddressProblemType)this.mProblems.get(var1);
   }

   public Map<AddressField, AddressProblemType> getProblems() {
      return this.mProblems;
   }

   public boolean isEmpty() {
      return this.mProblems.isEmpty();
   }

   public String toString() {
      return this.mProblems.toString();
   }
}

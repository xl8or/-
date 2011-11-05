package com.android.i18n.addressinput;

import com.android.i18n.addressinput.AddressDataKey;
import com.android.i18n.addressinput.Util;
import java.util.Iterator;
import java.util.Map;

public class AddressVerificationNodeData {

   private final Map<AddressDataKey, String> mMap;


   public AddressVerificationNodeData(Map<AddressDataKey, String> var1) {
      Util.checkNotNull("Cannot construct StandardNodeData with null map");
      this.mMap = var1;
   }

   public boolean containsKey(AddressDataKey var1) {
      return this.mMap.containsKey(var1);
   }

   public String get(AddressDataKey var1) {
      return (String)this.mMap.get(var1);
   }

   public Iterator<AddressDataKey> iterator() {
      return this.mMap.keySet().iterator();
   }
}

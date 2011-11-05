package com.seven.Z7.common.provisioning;

import com.seven.Z7.common.provisioning.Z7ProvResourceMap;
import com.seven.util.IntArrayMap;
import java.util.List;

public class Z7ProvISPServerMap extends IntArrayMap {

   private static final int INVALID_INT_VALUE = 255;


   public Z7ProvISPServerMap() {}

   public Z7ProvISPServerMap(IntArrayMap var1) {
      super(var1);
   }

   public String getBrandId() {
      return this.getString(49);
   }

   public List<Z7ProvResourceMap> getResourceList() {
      return (List)this.get(42);
   }

   public String getSelectionValue() {
      return this.getString(52);
   }

   public int getServerId() {
      return this.getInt(34, -1);
   }
}

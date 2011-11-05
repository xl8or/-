package com.seven.Z7.common.provisioning;

import com.seven.util.IntArrayMap;
import java.util.List;

public class Z7ProvisioningResponse extends IntArrayMap {

   private static final String TAG = "Z7ProvisioningResponse";
   public static final int Z7_PROVISONING_CONNECTORS = 1;
   public static final int Z7_PROVISONING_IS_IM = 3;
   public static final int Z7_PROVISONING_STAGED = 2;


   public Z7ProvisioningResponse(IntArrayMap var1) {
      super(var1);
   }

   public Z7ProvisioningResponse(List<IntArrayMap> var1, List<IntArrayMap> var2, boolean var3) {
      this.put(1, var1);
      this.put(2, var2);
      Boolean var6 = Boolean.valueOf(var3);
      this.put(3, var6);
   }

   public List<IntArrayMap> getConnectors() {
      return this.getList(1);
   }

   public List<IntArrayMap> getStagedConnectors() {
      return this.getList(2);
   }

   public boolean isImScope() {
      return this.getBoolean(3, (boolean)0);
   }
}

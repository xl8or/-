package com.seven.Z7.common.provisioning;

import com.seven.util.IntArrayMap;
import java.util.Date;

public class Z7ProvResourceMap extends IntArrayMap {

   public Z7ProvResourceMap() {}

   public Z7ProvResourceMap(IntArrayMap var1) {
      super(var1);
   }

   public String getIconId() {
      return this.getString(40);
   }

   public Date getIconModified() {
      return (Date)this.get(41);
   }

   public String getResourceId() {
      return this.getString(43);
   }

   public Date getResourceModified() {
      return (Date)this.get(44);
   }
}

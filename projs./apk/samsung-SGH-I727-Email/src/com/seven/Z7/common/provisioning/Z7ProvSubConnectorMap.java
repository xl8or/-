package com.seven.Z7.common.provisioning;

import com.seven.util.IntArrayMap;
import java.util.List;

public class Z7ProvSubConnectorMap extends IntArrayMap {

   public static final int INVALID_INT_VALUE = 255;


   public Z7ProvSubConnectorMap(IntArrayMap var1) {
      super(var1);
   }

   public String getBrandId() {
      return this.getString(49);
   }

   public String getConnectorId() {
      return this.getString(38);
   }

   public int getHostId() {
      return this.getInt(17, -1);
   }

   public List getISPServerList() {
      return (List)this.get(46);
   }

   public int getIspType() {
      return this.getInt(50, 1);
   }

   public String getName() {
      return this.getString(11);
   }

   public int getNocId() {
      return this.getInt(16, -1);
   }

   public short getScope() {
      return this.getShort(10, (short)-1);
   }

   public int getServerId() {
      return this.getInt(34, -1);
   }

   public boolean hasBrandId() {
      return this.containsKey(49);
   }

   public boolean hasConnectorId() {
      return this.containsKey(38);
   }

   public boolean hasHostId() {
      return this.containsKey(17);
   }

   public boolean hasISPServerList() {
      return this.containsKey(46);
   }

   public boolean hasIspType() {
      return this.containsKey(50);
   }

   public boolean hasName() {
      return this.containsKey(11);
   }

   public boolean hasNocId() {
      return this.containsKey(16);
   }

   public boolean hasScope() {
      return this.containsKey(10);
   }

   public boolean hasServerId() {
      return this.containsKey(34);
   }

   public boolean isISP() {
      boolean var1;
      if(this.getScope() == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isOWA() {
      boolean var1;
      if(this.getScope() == 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isVoicemail() {
      boolean var1;
      if(this.getScope() == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isWork() {
      boolean var1;
      if(this.getScope() != 0 && this.getScope() != 1) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}

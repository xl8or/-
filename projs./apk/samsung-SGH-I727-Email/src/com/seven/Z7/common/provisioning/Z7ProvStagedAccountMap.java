package com.seven.Z7.common.provisioning;

import com.seven.util.IntArrayMap;

public class Z7ProvStagedAccountMap extends IntArrayMap {

   private static final short INVALID_INT_VALUE = 255;


   public Z7ProvStagedAccountMap() {}

   public String getBrandId() {
      return this.getString(49);
   }

   public String getConnectorId() {
      return this.getString(38);
   }

   public String getEmail() {
      return this.getString(12);
   }

   public int getHostId() {
      return this.getInt(17, -1);
   }

   public int getIspType() {
      return this.getInt(50, 1);
   }

   public String getMSISDN() {
      return this.getString(26);
   }

   public String getName() {
      return this.getString(11);
   }

   public int getNocId() {
      return this.getInt(16, -1);
   }

   public String getOWAURL() {
      return this.getString(60);
   }

   public short getScope() {
      return this.getShort(10, (short)-1);
   }

   public int getServerId() {
      return this.getInt(34, -1);
   }

   public long getStagedId() {
      return this.getLong(28, 65535L);
   }

   public String getUsername() {
      return this.getString(20);
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

   public boolean isPasswordSet() {
      return this.getString(70, "").equalsIgnoreCase("true");
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

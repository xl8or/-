package com.seven.Z7.common.provisioning;

import com.seven.Z7.common.provisioning.Z7Connector;
import com.seven.Z7.common.provisioning.Z7ProvStagedAccountMap;
import com.seven.Z7.common.provisioning.Z7ProvSubConnectorMap;
import com.seven.util.IntArrayMap;
import java.util.Iterator;
import java.util.List;

public class Z7ProvConnectorMap extends IntArrayMap {

   public static final String BRAND_ID_COX = "cox";
   public static final String BRAND_ID_HELP_ME_DECIDE = "help_me_decide";
   public static final String BRAND_ID_MORE_CHOICES = "more_choices";
   public static final String BRAND_ID_MSN = "msn";
   public static final String BRAND_ID_ROADRUNNER = "rr";
   public static final String BRAND_ID_TERRA = "terra";
   public static final int INVALID_INT_VALUE = 255;


   public Z7ProvConnectorMap(IntArrayMap var1) {
      super(var1);
   }

   public boolean DoesMatchStagedAccount(Z7ProvStagedAccountMap var1, Z7ProvSubConnectorMap var2) {
      boolean var3;
      if(var1 == null) {
         var3 = false;
      } else if((new Z7Connector(this)).DoesMatchStagedAccount(var1)) {
         var3 = true;
      } else {
         if(this.HasSubConnectorsList()) {
            Iterator var4 = this.GetSubConnectorsList().iterator();

            while(var4.hasNext()) {
               Z7ProvSubConnectorMap var5 = (Z7ProvSubConnectorMap)var4.next();
               if((new Z7Connector(this, var5)).DoesMatchStagedAccount(var1)) {
                  var3 = true;
                  return var3;
               }
            }
         }

         var3 = false;
      }

      return var3;
   }

   public String GetBrandId() {
      return this.getString(49);
   }

   public String GetConnectorId() {
      return this.getString(38);
   }

   public String GetEmail() {
      return this.getString(12);
   }

   public int GetHostId() {
      return this.getInt(17, -1);
   }

   public List GetISPServerList() {
      return (List)this.get(46);
   }

   public int GetIspType() {
      return this.getInt(50, 1);
   }

   public int GetNocId() {
      return this.getInt(16, -1);
   }

   public String GetPassword() {
      return this.getString(15);
   }

   public List GetResourceList() {
      return (List)this.get(42);
   }

   public int GetServerId() {
      return this.getInt(34, -1);
   }

   public List<Z7ProvSubConnectorMap> GetSubConnectorsList() {
      return (List)this.get(63);
   }

   public String GetUsername() {
      return this.getString(20);
   }

   public boolean HasHostId() {
      return this.containsKey(17);
   }

   public boolean HasNocId() {
      return this.containsKey(16);
   }

   public boolean HasServerId() {
      return this.containsKey(34);
   }

   public boolean HasSubConnectorsList() {
      return this.containsKey(63);
   }

   public boolean IsHelp() {
      boolean var1;
      if(this.getScope() == -1 && this.getPage() == 1 && this.GetBrandId().equalsIgnoreCase("help_me_decide")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsIM() {
      boolean var1;
      if(this.getScope() == 5) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsISP() {
      boolean var1;
      if(this.getScope() == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsMore() {
      boolean var1;
      if(this.getScope() == -1 && this.getPage() == 1 && this.GetBrandId().equalsIgnoreCase("more_choices")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsNormalISP() {
      boolean var1;
      if(this.IsISP() && this.GetIspType() == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOWA() {
      boolean var1;
      if(this.getScope() == 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOtherIMAP() {
      boolean var1;
      if(this.IsISP() && this.GetIspType() == 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOtherISP() {
      boolean var1;
      if(!this.IsOtherPOP() && !this.IsOtherIMAP()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean IsOtherISPType() {
      boolean var1;
      if(this.IsISP() && this.GetIspType() == 6) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOtherPOP() {
      boolean var1;
      if(this.IsISP() && this.GetIspType() == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsTerra() {
      boolean var1;
      if(this.IsOWA() && this.GetBrandId().equalsIgnoreCase("terra")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsVoicemail() {
      boolean var1;
      if(this.getScope() == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsWork() {
      boolean var1;
      if(this.getScope() != 0 && this.getScope() != 1) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void SetBrandId(String var1) {
      this.put(49, var1);
   }

   public void SetConnectorId(String var1) {
      this.put(38, var1);
   }

   public void SetEmail(String var1) {
      this.put(12, var1);
   }

   public void SetPassword(String var1) {
      this.put(15, var1);
   }

   public void SetServerId(int var1) {
      Integer var2 = Integer.valueOf(var1);
      this.put(34, var2);
   }

   public void SetUsername(String var1) {
      this.put(20, var1);
   }

   public boolean SupportsOWA() {
      boolean var1;
      if(this.IsOWA()) {
         var1 = true;
      } else {
         if(this.HasSubConnectorsList()) {
            Iterator var2 = this.GetSubConnectorsList().iterator();

            while(var2.hasNext()) {
               if(((Z7ProvSubConnectorMap)var2.next()).isOWA()) {
                  var1 = true;
                  return var1;
               }
            }
         }

         var1 = false;
      }

      return var1;
   }

   public String getName() {
      return this.getString(11);
   }

   public int getPage() {
      return this.getInt(39, -1);
   }

   public short getScope() {
      return this.getShort(10, (short)-1);
   }

   public String getURL() {
      return this.getString(24);
   }

   public boolean isEmailNeeded() {
      return this.containsKey(68);
   }

   public void setASS(boolean var1) {
      Boolean var2 = Boolean.valueOf(var1);
      this.put(59, var2);
   }

   public void setName(String var1) {
      this.put(11, var1);
   }

   public void setPage(int var1) {
      Integer var2 = Integer.valueOf(var1);
      this.put(39, var2);
   }

   public void setScope(short var1) {
      Short var2 = Short.valueOf(var1);
      this.put(10, var2);
   }

   public void setURL(String var1) {
      this.put(24, var1);
   }
}

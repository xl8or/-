package com.seven.Z7.common.provisioning;

import com.seven.Z7.common.provisioning.Z7ProvConnectorMap;
import com.seven.Z7.common.provisioning.Z7ProvStagedAccountMap;
import com.seven.Z7.common.provisioning.Z7ProvSubConnectorMap;
import java.util.Iterator;
import java.util.List;

public class Z7Connector {

   Z7ProvConnectorMap m_conn;
   Z7ProvSubConnectorMap m_subConn;


   public Z7Connector(Z7ProvConnectorMap var1) {
      this.m_conn = var1;
   }

   public Z7Connector(Z7ProvConnectorMap var1, Z7ProvSubConnectorMap var2) {
      this.m_conn = var1;
      this.m_subConn = var2;
   }

   public boolean DoesMatchStagedAccount(Z7ProvStagedAccountMap var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         short var3 = this.GetScope();
         if(var3 == 1) {
            var3 = 0;
         }

         short var4 = var1.getScope();
         if(var4 == 1) {
            var4 = 0;
         }

         if(var3 != var4) {
            var2 = false;
         } else {
            switch(var3) {
            case 0:
            case 1:
            case 2:
               if(this.GetName() != null && var1.getName() != null) {
                  String var5 = this.GetName();
                  String var6 = var1.getName();
                  var2 = var5.equalsIgnoreCase(var6);
               } else {
                  var2 = false;
               }
               break;
            case 3:
               if(this.GetBrandId() != null && var1.getBrandId() != null) {
                  String var7 = this.GetBrandId();
                  String var8 = var1.getBrandId();
                  var2 = var7.equalsIgnoreCase(var8);
               } else {
                  var2 = false;
               }
               break;
            default:
               var2 = false;
            }
         }
      }

      return var2;
   }

   public String GetBrandId() {
      String var1;
      if(!this.isValid()) {
         var1 = "";
      } else if(this.m_subConn.hasBrandId()) {
         var1 = this.m_subConn.getBrandId();
      } else {
         var1 = this.m_conn.GetBrandId();
      }

      return var1;
   }

   public String GetConnectorId() {
      String var1;
      if(!this.isValid()) {
         var1 = "";
      } else if(this.m_subConn.hasConnectorId()) {
         var1 = this.m_subConn.getConnectorId();
      } else {
         var1 = this.m_conn.GetConnectorId();
      }

      return var1;
   }

   public int GetHostId() {
      int var1;
      if(this.m_subConn.hasHostId()) {
         var1 = this.m_subConn.getHostId();
      } else {
         var1 = this.m_conn.GetHostId();
      }

      return var1;
   }

   public List GetISPServerList() {
      List var1;
      if(!this.isValid()) {
         var1 = null;
      } else if(this.m_subConn.hasISPServerList()) {
         var1 = this.m_subConn.getISPServerList();
      } else {
         var1 = this.m_conn.GetISPServerList();
      }

      return var1;
   }

   public int GetIspType() {
      int var1;
      if(this.m_subConn.hasIspType()) {
         var1 = this.m_subConn.getIspType();
      } else {
         var1 = this.m_conn.GetIspType();
      }

      return var1;
   }

   public String GetName() {
      String var1;
      if(!this.isValid()) {
         var1 = "";
      } else if(this.m_subConn.hasName()) {
         var1 = this.m_subConn.getName();
      } else {
         var1 = this.m_conn.getName();
      }

      return var1;
   }

   public int GetNocId() {
      int var1;
      if(this.m_subConn.hasNocId()) {
         var1 = this.m_subConn.getNocId();
      } else {
         var1 = this.m_conn.GetNocId();
      }

      return var1;
   }

   public short GetScope() {
      short var1;
      if(this.m_subConn.hasScope()) {
         var1 = this.m_subConn.getScope();
      } else {
         var1 = this.m_conn.getScope();
      }

      return var1;
   }

   public int GetServerId() {
      int var1;
      if(this.m_subConn.hasServerId()) {
         var1 = this.m_subConn.getServerId();
      } else {
         var1 = this.m_conn.GetServerId();
      }

      return var1;
   }

   public boolean HasHostId() {
      boolean var1;
      if(!this.m_subConn.hasHostId() && !this.m_conn.HasHostId()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean HasNocId() {
      boolean var1;
      if(!this.m_subConn.hasNocId() && !this.m_conn.HasNocId()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean IsEmailNeeded() {
      boolean var1;
      if(this.isValid() && this.m_conn.isEmailNeeded()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsHelp() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsHelp()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsISP() {
      boolean var1;
      if(this.m_subConn.hasScope()) {
         var1 = this.m_subConn.isISP();
      } else {
         var1 = this.m_conn.IsISP();
      }

      return var1;
   }

   public boolean IsMore() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsMore()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsNormalISP() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsNormalISP()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOWA() {
      boolean var1;
      if(this.m_subConn.hasScope()) {
         var1 = this.m_subConn.isOWA();
      } else {
         var1 = this.m_conn.IsOWA();
      }

      return var1;
   }

   public boolean IsOtherIMAP() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsOtherIMAP()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOtherISP() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsOtherISP()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOtherISPType() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsOtherISPType()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsOtherPOP() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsOtherPOP()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsTerra() {
      boolean var1;
      if(this.isValid() && this.m_conn.IsTerra()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsVoicemail() {
      boolean var1;
      if(this.m_subConn.hasScope()) {
         var1 = this.m_subConn.isVoicemail();
      } else {
         var1 = this.m_conn.IsVoicemail();
      }

      return var1;
   }

   public boolean IsWork() {
      boolean var1;
      if(this.m_subConn.hasScope()) {
         var1 = this.m_subConn.isWork();
      } else {
         var1 = this.m_conn.IsWork();
      }

      return var1;
   }

   public boolean SupportsOWA() {
      boolean var1;
      if(this.isValid() && this.m_conn.SupportsOWA()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   Z7ProvConnectorMap getProvConnector() {
      return this.m_conn;
   }

   public boolean isValid() {
      boolean var1;
      if(this.m_conn != null && !this.m_conn.isEmpty()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setOWASubConnector() {
      Iterator var1 = this.m_conn.GetSubConnectorsList().iterator();

      while(var1.hasNext()) {
         Z7ProvSubConnectorMap var2 = (Z7ProvSubConnectorMap)var1.next();
         if(var2.isOWA()) {
            this.m_subConn = var2;
            return;
         }
      }

   }

   public void setScope(int var1) {
      Z7ProvConnectorMap var2 = this.m_conn;
      short var3 = (short)var1;
      var2.setScope(var3);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("Connector: ");
      String var3 = this.m_conn.toString();
      var1.append(var3);
      StringBuffer var5 = var1.append("Sub-Connector: ");
      String var6 = this.m_subConn.toString();
      var1.append(var6);
      return var1.toString();
   }
}

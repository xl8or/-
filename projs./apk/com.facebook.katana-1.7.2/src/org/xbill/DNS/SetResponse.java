package org.xbill.DNS;

import java.util.ArrayList;
import java.util.List;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.DNAMERecord;
import org.xbill.DNS.RRset;

public class SetResponse {

   static final int CNAME = 4;
   static final int DELEGATION = 3;
   static final int DNAME = 5;
   static final int NXDOMAIN = 1;
   static final int NXRRSET = 2;
   static final int SUCCESSFUL = 6;
   static final int UNKNOWN;
   private static final SetResponse nxdomain = new SetResponse(1);
   private static final SetResponse nxrrset = new SetResponse(2);
   private static final SetResponse unknown = new SetResponse(0);
   private Object data;
   private int type;


   private SetResponse() {}

   SetResponse(int var1) {
      if(var1 >= 0 && var1 <= 6) {
         this.type = var1;
         this.data = null;
      } else {
         throw new IllegalArgumentException("invalid type");
      }
   }

   SetResponse(int var1, RRset var2) {
      if(var1 >= 0 && var1 <= 6) {
         this.type = var1;
         this.data = var2;
      } else {
         throw new IllegalArgumentException("invalid type");
      }
   }

   static SetResponse ofType(int var0) {
      SetResponse var1;
      switch(var0) {
      case 0:
         var1 = unknown;
         break;
      case 1:
         var1 = nxdomain;
         break;
      case 2:
         var1 = nxrrset;
         break;
      case 3:
      case 4:
      case 5:
      case 6:
         var1 = new SetResponse();
         var1.type = var0;
         var1.data = null;
         break;
      default:
         throw new IllegalArgumentException("invalid type");
      }

      return var1;
   }

   void addRRset(RRset var1) {
      if(this.data == null) {
         ArrayList var2 = new ArrayList();
         this.data = var2;
      }

      boolean var3 = ((List)this.data).add(var1);
   }

   public RRset[] answers() {
      RRset[] var1;
      if(this.type != 6) {
         var1 = null;
      } else {
         List var2 = (List)this.data;
         RRset[] var3 = new RRset[var2.size()];
         var1 = (RRset[])((RRset[])var2.toArray(var3));
      }

      return var1;
   }

   public CNAMERecord getCNAME() {
      return (CNAMERecord)((RRset)this.data).first();
   }

   public DNAMERecord getDNAME() {
      return (DNAMERecord)((RRset)this.data).first();
   }

   public RRset getNS() {
      return (RRset)this.data;
   }

   public boolean isCNAME() {
      boolean var1;
      if(this.type == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isDNAME() {
      boolean var1;
      if(this.type == 5) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isDelegation() {
      boolean var1;
      if(this.type == 3) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isNXDOMAIN() {
      boolean var1;
      if(this.type == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isNXRRSET() {
      boolean var1;
      if(this.type == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isSuccessful() {
      boolean var1;
      if(this.type == 6) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isUnknown() {
      boolean var1;
      if(this.type == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toString() {
      String var1;
      switch(this.type) {
      case 0:
         var1 = "unknown";
         break;
      case 1:
         var1 = "NXDOMAIN";
         break;
      case 2:
         var1 = "NXRRSET";
         break;
      case 3:
         StringBuilder var2 = (new StringBuilder()).append("delegation: ");
         Object var3 = this.data;
         var1 = var2.append(var3).toString();
         break;
      case 4:
         StringBuilder var4 = (new StringBuilder()).append("CNAME: ");
         Object var5 = this.data;
         var1 = var4.append(var5).toString();
         break;
      case 5:
         StringBuilder var6 = (new StringBuilder()).append("DNAME: ");
         Object var7 = this.data;
         var1 = var6.append(var7).toString();
         break;
      case 6:
         var1 = "successful";
         break;
      default:
         throw new IllegalStateException();
      }

      return var1;
   }
}

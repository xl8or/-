package myorg.bouncycastle.voms;

import java.util.List;
import java.util.Vector;
import myorg.bouncycastle.x509.X509AttributeCertificate;

public class VOMSAttribute {

   public static final String VOMS_ATTR_OID = "1.3.6.1.4.1.8005.100.100.4";
   private X509AttributeCertificate myAC;
   private Vector myFQANs;
   private String myHostPort;
   private Vector myStringList;
   private String myVo;


   public VOMSAttribute(X509AttributeCertificate param1) {
      // $FF: Couldn't be decompiled
   }

   public X509AttributeCertificate getAC() {
      return this.myAC;
   }

   public List getFullyQualifiedAttributes() {
      return this.myStringList;
   }

   public String getHostPort() {
      return this.myHostPort;
   }

   public List getListOfFQAN() {
      return this.myFQANs;
   }

   public String getVO() {
      return this.myVo;
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("VO      :");
      String var2 = this.myVo;
      StringBuilder var3 = var1.append(var2).append("\n").append("HostPort:");
      String var4 = this.myHostPort;
      StringBuilder var5 = var3.append(var4).append("\n").append("FQANs   :");
      Vector var6 = this.myFQANs;
      return var5.append(var6).toString();
   }

   public class FQAN {

      String capability;
      String fqan;
      String group;
      String role;


      public FQAN(String var2) {
         this.fqan = var2;
      }

      public FQAN(String var2, String var3, String var4) {
         this.group = var2;
         this.role = var3;
         this.capability = var4;
      }

      public String getCapability() {
         if(this.group == null && this.fqan != null) {
            this.split();
         }

         return this.capability;
      }

      public String getFQAN() {
         String var1;
         if(this.fqan != null) {
            var1 = this.fqan;
         } else {
            StringBuilder var2 = new StringBuilder();
            String var3 = this.group;
            StringBuilder var4 = var2.append(var3).append("/Role=");
            String var5;
            if(this.role != null) {
               var5 = this.role;
            } else {
               var5 = "";
            }

            StringBuilder var6 = var4.append(var5);
            String var9;
            if(this.capability != null) {
               StringBuilder var7 = (new StringBuilder()).append("/Capability=");
               String var8 = this.capability;
               var9 = var7.append(var8).toString();
            } else {
               var9 = "";
            }

            String var10 = var6.append(var9).toString();
            this.fqan = var10;
            var1 = this.fqan;
         }

         return var1;
      }

      public String getGroup() {
         if(this.group == null && this.fqan != null) {
            this.split();
         }

         return this.group;
      }

      public String getRole() {
         if(this.group == null && this.fqan != null) {
            this.split();
         }

         return this.role;
      }

      protected void split() {
         int var1 = this.fqan.length();
         int var2 = this.fqan.indexOf("/Role=");
         if(var2 >= 0) {
            String var3 = this.fqan.substring(0, var2);
            this.group = var3;
            String var4 = this.fqan;
            int var5 = var2 + 6;
            int var6 = var4.indexOf("/Capability=", var5);
            String var9;
            if(var6 < 0) {
               String var7 = this.fqan;
               int var8 = var2 + 6;
               var9 = var7.substring(var8);
            } else {
               String var13 = this.fqan;
               int var14 = var2 + 6;
               var9 = var13.substring(var14, var6);
            }

            String var10;
            if(var9.length() == 0) {
               var10 = null;
            } else {
               var10 = var9;
            }

            this.role = var10;
            String var11;
            if(var6 < 0) {
               var11 = null;
            } else {
               String var15 = this.fqan;
               int var16 = var6 + 12;
               var11 = var15.substring(var16);
            }

            String var12;
            if(var11 != null && var11.length() != 0) {
               var12 = var11;
            } else {
               var12 = null;
            }

            this.capability = var12;
         }
      }

      public String toString() {
         return this.getFQAN();
      }
   }
}

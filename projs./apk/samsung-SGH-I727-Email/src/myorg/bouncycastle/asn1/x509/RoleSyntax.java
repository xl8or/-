package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralNames;

public class RoleSyntax extends ASN1Encodable {

   private GeneralNames roleAuthority;
   private GeneralName roleName;


   public RoleSyntax(String var1) {
      GeneralName var2 = new GeneralName;
      String var3;
      if(var1 == null) {
         var3 = "";
      } else {
         var3 = var1;
      }

      var2.<init>(6, var3);
      this(var2);
   }

   public RoleSyntax(ASN1Sequence var1) {
      if(var1.size() >= 1 && var1.size() <= 2) {
         int var5 = 0;

         while(true) {
            int var6 = var1.size();
            if(var5 == var6) {
               return;
            }

            ASN1TaggedObject var7 = ASN1TaggedObject.getInstance(var1.getObjectAt(var5));
            switch(var7.getTagNo()) {
            case 0:
               GeneralNames var8 = GeneralNames.getInstance(var7, (boolean)0);
               this.roleAuthority = var8;
               break;
            case 1:
               GeneralName var9 = GeneralName.getInstance(var7, (boolean)1);
               this.roleName = var9;
               break;
            default:
               throw new IllegalArgumentException("Unknown tag in RoleSyntax");
            }

            ++var5;
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public RoleSyntax(GeneralName var1) {
      this((GeneralNames)null, var1);
   }

   public RoleSyntax(GeneralNames var1, GeneralName var2) {
      if(var2 != null && var2.getTagNo() == 6 && !((DERString)var2.getName()).getString().equals("")) {
         this.roleAuthority = var1;
         this.roleName = var2;
      } else {
         throw new IllegalArgumentException("the role name MUST be non empty and MUST use the URI option of GeneralName");
      }
   }

   public static RoleSyntax getInstance(Object var0) {
      RoleSyntax var1;
      if(var0 != null && !(var0 instanceof RoleSyntax)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("Unknown object in RoleSyntax factory.");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RoleSyntax(var2);
      } else {
         var1 = (RoleSyntax)var0;
      }

      return var1;
   }

   public GeneralNames getRoleAuthority() {
      return this.roleAuthority;
   }

   public String[] getRoleAuthorityAsString() {
      String[] var1;
      if(this.roleAuthority == null) {
         var1 = new String[0];
      } else {
         GeneralName[] var2 = this.roleAuthority.getNames();
         String[] var3 = new String[var2.length];
         int var4 = 0;

         while(true) {
            int var5 = var2.length;
            if(var4 >= var5) {
               var1 = var3;
               break;
            }

            DEREncodable var6 = var2[var4].getName();
            if(var6 instanceof DERString) {
               String var7 = ((DERString)var6).getString();
               var3[var4] = var7;
            } else {
               String var8 = var6.toString();
               var3[var4] = var8;
            }

            ++var4;
         }
      }

      return var1;
   }

   public GeneralName getRoleName() {
      return this.roleName;
   }

   public String getRoleNameAsString() {
      return ((DERString)this.roleName.getName()).getString();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.roleAuthority != null) {
         GeneralNames var2 = this.roleAuthority;
         DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
         var1.add(var3);
      }

      GeneralName var4 = this.roleName;
      DERTaggedObject var5 = new DERTaggedObject((boolean)1, 1, var4);
      var1.add(var5);
      return new DERSequence(var1);
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("Name: ");
      String var2 = this.getRoleNameAsString();
      String var3 = var1.append(var2).append(" - Auth: ").toString();
      StringBuffer var4 = new StringBuffer(var3);
      if(this.roleAuthority != null && this.roleAuthority.getNames().length != 0) {
         String[] var6 = this.getRoleAuthorityAsString();
         StringBuffer var7 = var4.append('[');
         String var8 = var6[0];
         var7.append(var8);
         int var10 = 1;

         while(true) {
            int var11 = var6.length;
            if(var10 >= var11) {
               StringBuffer var15 = var4.append(']');
               break;
            }

            StringBuffer var12 = var4.append(", ");
            String var13 = var6[var10];
            var12.append(var13);
            ++var10;
         }
      } else {
         StringBuffer var5 = var4.append("N/A");
      }

      return var4.toString();
   }
}

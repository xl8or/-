package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AccessDescription;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.X509Extension;

public class AuthorityInformationAccess extends ASN1Encodable {

   private AccessDescription[] descriptions;


   public AuthorityInformationAccess(ASN1Sequence var1) {
      if(var1.size() < 1) {
         throw new IllegalArgumentException("sequence may not be empty");
      } else {
         AccessDescription[] var2 = new AccessDescription[var1.size()];
         this.descriptions = var2;
         int var3 = 0;

         while(true) {
            int var4 = var1.size();
            if(var3 == var4) {
               return;
            }

            AccessDescription[] var5 = this.descriptions;
            AccessDescription var6 = AccessDescription.getInstance(var1.getObjectAt(var3));
            var5[var3] = var6;
            ++var3;
         }
      }
   }

   public AuthorityInformationAccess(DERObjectIdentifier var1, GeneralName var2) {
      AccessDescription[] var3 = new AccessDescription[1];
      this.descriptions = var3;
      AccessDescription[] var4 = this.descriptions;
      AccessDescription var5 = new AccessDescription(var1, var2);
      var4[0] = var5;
   }

   public static AuthorityInformationAccess getInstance(Object var0) {
      AuthorityInformationAccess var1;
      if(var0 instanceof AuthorityInformationAccess) {
         var1 = (AuthorityInformationAccess)var0;
      } else if(var0 instanceof ASN1Sequence) {
         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AuthorityInformationAccess(var2);
      } else {
         if(!(var0 instanceof X509Extension)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         var1 = getInstance(X509Extension.convertValueToObject((X509Extension)var0));
      }

      return var1;
   }

   public AccessDescription[] getAccessDescriptions() {
      return this.descriptions;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      int var2 = 0;

      while(true) {
         int var3 = this.descriptions.length;
         if(var2 == var3) {
            return new DERSequence(var1);
         }

         AccessDescription var4 = this.descriptions[var2];
         var1.add(var4);
         ++var2;
      }
   }

   public String toString() {
      StringBuilder var1 = (new StringBuilder()).append("AuthorityInformationAccess: Oid(");
      String var2 = this.descriptions[0].getAccessMethod().getId();
      return var1.append(var2).append(")").toString();
   }
}

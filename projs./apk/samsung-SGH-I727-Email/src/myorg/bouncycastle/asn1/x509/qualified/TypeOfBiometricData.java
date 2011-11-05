package myorg.bouncycastle.asn1.x509.qualified;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;

public class TypeOfBiometricData extends ASN1Encodable implements ASN1Choice {

   public static final int HANDWRITTEN_SIGNATURE = 1;
   public static final int PICTURE;
   DEREncodable obj;


   public TypeOfBiometricData(int var1) {
      if(var1 != 0 && var1 != 1) {
         String var3 = "unknow PredefinedBiometricType : " + var1;
         throw new IllegalArgumentException(var3);
      } else {
         DERInteger var2 = new DERInteger(var1);
         this.obj = var2;
      }
   }

   public TypeOfBiometricData(DERObjectIdentifier var1) {
      this.obj = var1;
   }

   public static TypeOfBiometricData getInstance(Object var0) {
      TypeOfBiometricData var1;
      if(var0 != null && !(var0 instanceof TypeOfBiometricData)) {
         if(var0 instanceof DERInteger) {
            int var2 = DERInteger.getInstance(var0).getValue().intValue();
            var1 = new TypeOfBiometricData(var2);
         } else {
            if(!(var0 instanceof DERObjectIdentifier)) {
               throw new IllegalArgumentException("unknown object in getInstance");
            }

            DERObjectIdentifier var3 = DERObjectIdentifier.getInstance(var0);
            var1 = new TypeOfBiometricData(var3);
         }
      } else {
         var1 = (TypeOfBiometricData)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getBiometricDataOid() {
      return (DERObjectIdentifier)this.obj;
   }

   public int getPredefinedBiometricType() {
      return ((DERInteger)this.obj).getValue().intValue();
   }

   public boolean isPredefined() {
      return this.obj instanceof DERInteger;
   }

   public DERObject toASN1Object() {
      return this.obj.getDERObject();
   }
}

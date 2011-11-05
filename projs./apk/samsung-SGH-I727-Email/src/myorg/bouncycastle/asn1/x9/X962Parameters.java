package myorg.bouncycastle.asn1.x9;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Null;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x9.X9ECParameters;

public class X962Parameters extends ASN1Encodable implements ASN1Choice {

   private DERObject params = null;


   public X962Parameters(DERObject var1) {
      this.params = var1;
   }

   public X962Parameters(DERObjectIdentifier var1) {
      this.params = var1;
   }

   public X962Parameters(X9ECParameters var1) {
      DERObject var2 = var1.getDERObject();
      this.params = var2;
   }

   public static X962Parameters getInstance(Object var0) {
      X962Parameters var1;
      if(var0 != null && !(var0 instanceof X962Parameters)) {
         if(!(var0 instanceof DERObject)) {
            throw new IllegalArgumentException("unknown object in getInstance()");
         }

         DERObject var2 = (DERObject)var0;
         var1 = new X962Parameters(var2);
      } else {
         var1 = (X962Parameters)var0;
      }

      return var1;
   }

   public static X962Parameters getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public DERObject getParameters() {
      return this.params;
   }

   public boolean isImplicitlyCA() {
      return this.params instanceof ASN1Null;
   }

   public boolean isNamedCurve() {
      return this.params instanceof DERObjectIdentifier;
   }

   public DERObject toASN1Object() {
      return this.params;
   }
}

package myorg.bouncycastle.asn1.cryptopro;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;

public class GOST28147Parameters extends ASN1Encodable {

   ASN1OctetString iv;
   DERObjectIdentifier paramSet;


   public GOST28147Parameters(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      ASN1OctetString var3 = (ASN1OctetString)var2.nextElement();
      this.iv = var3;
      DERObjectIdentifier var4 = (DERObjectIdentifier)var2.nextElement();
      this.paramSet = var4;
   }

   public static GOST28147Parameters getInstance(Object var0) {
      GOST28147Parameters var1;
      if(var0 != null && !(var0 instanceof GOST28147Parameters)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid GOST3410Parameter: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new GOST28147Parameters(var2);
      } else {
         var1 = (GOST28147Parameters)var0;
      }

      return var1;
   }

   public static GOST28147Parameters getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.iv;
      var1.add(var2);
      DERObjectIdentifier var3 = this.paramSet;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

package myorg.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;

public class RC2CBCParameter extends ASN1Encodable {

   ASN1OctetString iv;
   DERInteger version;


   public RC2CBCParameter(int var1, byte[] var2) {
      DERInteger var3 = new DERInteger(var1);
      this.version = var3;
      DEROctetString var4 = new DEROctetString(var2);
      this.iv = var4;
   }

   public RC2CBCParameter(ASN1Sequence var1) {
      if(var1.size() == 1) {
         this.version = null;
         ASN1OctetString var2 = (ASN1OctetString)var1.getObjectAt(0);
         this.iv = var2;
      } else {
         DERInteger var3 = (DERInteger)var1.getObjectAt(0);
         this.version = var3;
         ASN1OctetString var4 = (ASN1OctetString)var1.getObjectAt(1);
         this.iv = var4;
      }
   }

   public RC2CBCParameter(byte[] var1) {
      this.version = null;
      DEROctetString var2 = new DEROctetString(var1);
      this.iv = var2;
   }

   public static RC2CBCParameter getInstance(Object var0) {
      if(var0 instanceof ASN1Sequence) {
         ASN1Sequence var1 = (ASN1Sequence)var0;
         return new RC2CBCParameter(var1);
      } else {
         throw new IllegalArgumentException("unknown object in RC2CBCParameter factory");
      }
   }

   public byte[] getIV() {
      return this.iv.getOctets();
   }

   public BigInteger getRC2ParameterVersion() {
      BigInteger var1;
      if(this.version == null) {
         var1 = null;
      } else {
         var1 = this.version.getValue();
      }

      return var1;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.version != null) {
         DERInteger var2 = this.version;
         var1.add(var2);
      }

      ASN1OctetString var3 = this.iv;
      var1.add(var3);
      return new DERSequence(var1);
   }
}

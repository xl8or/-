package myorg.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;

public class PBKDF2Params extends ASN1Encodable {

   DERInteger iterationCount;
   DERInteger keyLength;
   ASN1OctetString octStr;


   public PBKDF2Params(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      ASN1OctetString var3 = (ASN1OctetString)var2.nextElement();
      this.octStr = var3;
      DERInteger var4 = (DERInteger)var2.nextElement();
      this.iterationCount = var4;
      if(var2.hasMoreElements()) {
         DERInteger var5 = (DERInteger)var2.nextElement();
         this.keyLength = var5;
      } else {
         this.keyLength = null;
      }
   }

   public PBKDF2Params(byte[] var1, int var2) {
      DEROctetString var3 = new DEROctetString(var1);
      this.octStr = var3;
      DERInteger var4 = new DERInteger(var2);
      this.iterationCount = var4;
   }

   public static PBKDF2Params getInstance(Object var0) {
      PBKDF2Params var1;
      if(var0 instanceof PBKDF2Params) {
         var1 = (PBKDF2Params)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PBKDF2Params(var2);
      }

      return var1;
   }

   public BigInteger getIterationCount() {
      return this.iterationCount.getValue();
   }

   public BigInteger getKeyLength() {
      BigInteger var1;
      if(this.keyLength != null) {
         var1 = this.keyLength.getValue();
      } else {
         var1 = null;
      }

      return var1;
   }

   public byte[] getSalt() {
      return this.octStr.getOctets();
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.octStr;
      var1.add(var2);
      DERInteger var3 = this.iterationCount;
      var1.add(var3);
      if(this.keyLength != null) {
         DERInteger var4 = this.keyLength;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}

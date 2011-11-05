package myorg.bouncycastle.asn1.sec;

import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.util.BigIntegers;

public class ECPrivateKeyStructure extends ASN1Encodable {

   private ASN1Sequence seq;


   public ECPrivateKeyStructure(BigInteger var1) {
      byte[] var2 = BigIntegers.asUnsignedByteArray(var1);
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      DERInteger var4 = new DERInteger(1);
      var3.add(var4);
      DEROctetString var5 = new DEROctetString(var2);
      var3.add(var5);
      DERSequence var6 = new DERSequence(var3);
      this.seq = var6;
   }

   public ECPrivateKeyStructure(BigInteger var1, ASN1Encodable var2) {
      this(var1, (DERBitString)null, var2);
   }

   public ECPrivateKeyStructure(BigInteger var1, DERBitString var2, ASN1Encodable var3) {
      byte[] var4 = BigIntegers.asUnsignedByteArray(var1);
      ASN1EncodableVector var5 = new ASN1EncodableVector();
      DERInteger var6 = new DERInteger(1);
      var5.add(var6);
      DEROctetString var7 = new DEROctetString(var4);
      var5.add(var7);
      if(var3 != null) {
         DERTaggedObject var8 = new DERTaggedObject((boolean)1, 0, var3);
         var5.add(var8);
      }

      if(var2 != null) {
         DERTaggedObject var9 = new DERTaggedObject((boolean)1, 1, var2);
         var5.add(var9);
      }

      DERSequence var10 = new DERSequence(var5);
      this.seq = var10;
   }

   public ECPrivateKeyStructure(ASN1Sequence var1) {
      this.seq = var1;
   }

   private ASN1Object getObjectInTag(int var1) {
      Enumeration var2 = this.seq.getObjects();

      ASN1Object var5;
      while(true) {
         if(var2.hasMoreElements()) {
            DEREncodable var3 = (DEREncodable)var2.nextElement();
            if(!(var3 instanceof ASN1TaggedObject)) {
               continue;
            }

            ASN1TaggedObject var4 = (ASN1TaggedObject)var3;
            if(var4.getTagNo() != var1) {
               continue;
            }

            var5 = (ASN1Object)var4.getObject().getDERObject();
            break;
         }

         var5 = null;
         break;
      }

      return var5;
   }

   public BigInteger getKey() {
      byte[] var1 = ((ASN1OctetString)this.seq.getObjectAt(1)).getOctets();
      return new BigInteger(1, var1);
   }

   public ASN1Object getParameters() {
      return this.getObjectInTag(0);
   }

   public DERBitString getPublicKey() {
      return (DERBitString)this.getObjectInTag(1);
   }

   public DERObject toASN1Object() {
      return this.seq;
   }
}

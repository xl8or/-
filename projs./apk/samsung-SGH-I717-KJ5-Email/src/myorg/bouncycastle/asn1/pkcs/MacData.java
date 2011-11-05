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
import myorg.bouncycastle.asn1.x509.DigestInfo;

public class MacData extends ASN1Encodable {

   private static final BigInteger ONE = BigInteger.valueOf(1L);
   DigestInfo digInfo;
   BigInteger iterationCount;
   byte[] salt;


   public MacData(ASN1Sequence var1) {
      DigestInfo var2 = DigestInfo.getInstance(var1.getObjectAt(0));
      this.digInfo = var2;
      byte[] var3 = ((ASN1OctetString)var1.getObjectAt(1)).getOctets();
      this.salt = var3;
      if(var1.size() == 3) {
         BigInteger var4 = ((DERInteger)var1.getObjectAt(2)).getValue();
         this.iterationCount = var4;
      } else {
         BigInteger var5 = ONE;
         this.iterationCount = var5;
      }
   }

   public MacData(DigestInfo var1, byte[] var2, int var3) {
      this.digInfo = var1;
      this.salt = var2;
      BigInteger var4 = BigInteger.valueOf((long)var3);
      this.iterationCount = var4;
   }

   public static MacData getInstance(Object var0) {
      MacData var1;
      if(var0 instanceof MacData) {
         var1 = (MacData)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new MacData(var2);
      }

      return var1;
   }

   public BigInteger getIterationCount() {
      return this.iterationCount;
   }

   public DigestInfo getMac() {
      return this.digInfo;
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DigestInfo var2 = this.digInfo;
      var1.add(var2);
      byte[] var3 = this.salt;
      DEROctetString var4 = new DEROctetString(var3);
      var1.add(var4);
      BigInteger var5 = this.iterationCount;
      BigInteger var6 = ONE;
      if(!var5.equals(var6)) {
         BigInteger var7 = this.iterationCount;
         DERInteger var8 = new DERInteger(var7);
         var1.add(var8);
      }

      return new DERSequence(var1);
   }
}

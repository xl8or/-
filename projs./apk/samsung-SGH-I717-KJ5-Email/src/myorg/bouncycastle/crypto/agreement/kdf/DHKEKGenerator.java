package myorg.bouncycastle.crypto.agreement.kdf;

import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.DerivationFunction;
import myorg.bouncycastle.crypto.DerivationParameters;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.agreement.kdf.DHKDFParameters;

public class DHKEKGenerator implements DerivationFunction {

   private DERObjectIdentifier algorithm;
   private final Digest digest;
   private int keySize;
   private byte[] partyAInfo;
   private byte[] z;


   public DHKEKGenerator(Digest var1) {
      this.digest = var1;
   }

   private byte[] integerToBytes(int var1) {
      byte[] var2 = new byte[4];
      byte var3 = (byte)(var1 >> 24);
      var2[0] = var3;
      byte var4 = (byte)(var1 >> 16);
      var2[1] = var4;
      byte var5 = (byte)(var1 >> 8);
      var2[2] = var5;
      byte var6 = (byte)var1;
      var2[3] = var6;
      return var2;
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      int var4 = var1.length - var3;
      if(var4 < var2) {
         throw new DataLengthException("output buffer too small");
      } else {
         long var6 = (long)var3;
         int var8 = this.digest.getDigestSize();
         if(var6 > 8589934591L) {
            throw new IllegalArgumentException("Output length too large");
         } else {
            long var9 = (long)var8 + var6 - 1L;
            long var11 = (long)var8;
            int var13 = (int)(var9 / var11);
            byte[] var14 = new byte[this.digest.getDigestSize()];
            int var15 = 1;

            for(int var16 = 0; var16 < var13; ++var16) {
               Digest var17 = this.digest;
               byte[] var18 = this.z;
               int var19 = this.z.length;
               var17.update(var18, 0, var19);
               ASN1EncodableVector var20 = new ASN1EncodableVector();
               ASN1EncodableVector var21 = new ASN1EncodableVector();
               DERObjectIdentifier var22 = this.algorithm;
               var21.add(var22);
               byte[] var25 = this.integerToBytes(var15);
               DEROctetString var26 = new DEROctetString(var25);
               var21.add(var26);
               DERSequence var27 = new DERSequence(var21);
               var20.add(var27);
               if(this.partyAInfo != null) {
                  byte[] var28 = this.partyAInfo;
                  DEROctetString var29 = new DEROctetString(var28);
                  DERTaggedObject var30 = new DERTaggedObject((boolean)1, 0, var29);
                  var20.add(var30);
               }

               int var31 = this.keySize;
               byte[] var34 = this.integerToBytes(var31);
               DEROctetString var35 = new DEROctetString(var34);
               DERTaggedObject var36 = new DERTaggedObject((boolean)1, 2, var35);
               var20.add(var36);
               byte[] var37 = (new DERSequence(var20)).getDEREncoded();
               Digest var38 = this.digest;
               int var39 = var37.length;
               byte var42 = 0;
               var38.update(var37, var42, var39);
               Digest var44 = this.digest;
               byte var46 = 0;
               var44.doFinal(var14, var46);
               if(var3 > var8) {
                  byte var51 = 0;
                  System.arraycopy(var14, var51, var1, var2, var8);
                  var2 += var8;
                  var3 -= var8;
               } else {
                  byte var56 = 0;
                  System.arraycopy(var14, var56, var1, var2, var3);
               }

               ++var15;
            }

            this.digest.reset();
            return var3;
         }
      }
   }

   public Digest getDigest() {
      return this.digest;
   }

   public void init(DerivationParameters var1) {
      DHKDFParameters var2 = (DHKDFParameters)var1;
      DERObjectIdentifier var3 = var2.getAlgorithm();
      this.algorithm = var3;
      int var4 = var2.getKeySize();
      this.keySize = var4;
      byte[] var5 = var2.getZ();
      this.z = var5;
      byte[] var6 = var2.getExtraInfo();
      this.partyAInfo = var6;
   }
}

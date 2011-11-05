package myorg.bouncycastle.asn1.icao;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.icao.DataGroupHash;
import myorg.bouncycastle.asn1.icao.ICAOObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class LDSSecurityObject extends ASN1Encodable implements ICAOObjectIdentifiers {

   public static final int ub_DataGroups = 16;
   DataGroupHash[] datagroupHash;
   AlgorithmIdentifier digestAlgorithmIdentifier;
   DERInteger version;


   public LDSSecurityObject(ASN1Sequence var1) {
      DERInteger var2 = new DERInteger(0);
      this.version = var2;
      if(var1 != null && var1.size() != 0) {
         Enumeration var3 = var1.getObjects();
         DERInteger var4 = DERInteger.getInstance(var3.nextElement());
         this.version = var4;
         AlgorithmIdentifier var5 = AlgorithmIdentifier.getInstance(var3.nextElement());
         this.digestAlgorithmIdentifier = var5;
         ASN1Sequence var6 = ASN1Sequence.getInstance(var3.nextElement());
         int var7 = var6.size();
         this.checkDatagroupHashSeqSize(var7);
         DataGroupHash[] var8 = new DataGroupHash[var6.size()];
         this.datagroupHash = var8;
         int var9 = 0;

         while(true) {
            int var10 = var6.size();
            if(var9 >= var10) {
               return;
            }

            DataGroupHash[] var11 = this.datagroupHash;
            DataGroupHash var12 = DataGroupHash.getInstance(var6.getObjectAt(var9));
            var11[var9] = var12;
            ++var9;
         }
      } else {
         throw new IllegalArgumentException("null or empty sequence passed.");
      }
   }

   public LDSSecurityObject(AlgorithmIdentifier var1, DataGroupHash[] var2) {
      DERInteger var3 = new DERInteger(0);
      this.version = var3;
      this.digestAlgorithmIdentifier = var1;
      this.datagroupHash = var2;
      int var4 = var2.length;
      this.checkDatagroupHashSeqSize(var4);
   }

   private void checkDatagroupHashSeqSize(int var1) {
      if(var1 < 2 || var1 > 16) {
         throw new IllegalArgumentException("wrong size in DataGroupHashValues : not in (2..16)");
      }
   }

   public static LDSSecurityObject getInstance(Object var0) {
      LDSSecurityObject var1;
      if(var0 != null && !(var0 instanceof LDSSecurityObject)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new LDSSecurityObject(var2);
      } else {
         var1 = (LDSSecurityObject)var0;
      }

      return var1;
   }

   public DataGroupHash[] getDatagroupHash() {
      return this.datagroupHash;
   }

   public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
      return this.digestAlgorithmIdentifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      AlgorithmIdentifier var3 = this.digestAlgorithmIdentifier;
      var1.add(var3);
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      int var5 = 0;

      while(true) {
         int var6 = this.datagroupHash.length;
         if(var5 >= var6) {
            DERSequence var8 = new DERSequence(var4);
            var1.add(var8);
            return new DERSequence(var1);
         }

         DataGroupHash var7 = this.datagroupHash[var5];
         var4.add(var7);
         ++var5;
      }
   }
}

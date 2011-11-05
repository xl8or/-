package myorg.bouncycastle.asn1.ess;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.IssuerSerial;

public class ESSCertIDv2 extends ASN1Encodable {

   private static final AlgorithmIdentifier DEFAULT_ALG_ID;
   private byte[] certHash;
   private AlgorithmIdentifier hashAlgorithm;
   private IssuerSerial issuerSerial;


   static {
      DERObjectIdentifier var0 = NISTObjectIdentifiers.id_sha256;
      DEFAULT_ALG_ID = new AlgorithmIdentifier(var0);
   }

   public ESSCertIDv2(ASN1Sequence var1) {
      if(var1.size() != 2 && var1.size() != 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         int var5 = 0;
         if(var1.getObjectAt(0) instanceof ASN1OctetString) {
            AlgorithmIdentifier var6 = DEFAULT_ALG_ID;
            this.hashAlgorithm = var6;
         } else {
            int var11 = var5 + 1;
            AlgorithmIdentifier var12 = AlgorithmIdentifier.getInstance(var1.getObjectAt(var5).getDERObject());
            this.hashAlgorithm = var12;
            var5 = var11;
         }

         int var7 = var5 + 1;
         byte[] var8 = ASN1OctetString.getInstance(var1.getObjectAt(var5).getDERObject()).getOctets();
         this.certHash = var8;
         if(var1.size() > var7) {
            ASN1Sequence var9 = ASN1Sequence.getInstance(var1.getObjectAt(var7).getDERObject());
            IssuerSerial var10 = new IssuerSerial(var9);
            this.issuerSerial = var10;
         }
      }
   }

   public ESSCertIDv2(AlgorithmIdentifier var1, byte[] var2) {
      this(var1, var2, (IssuerSerial)null);
   }

   public ESSCertIDv2(AlgorithmIdentifier var1, byte[] var2, IssuerSerial var3) {
      if(var1 == null) {
         AlgorithmIdentifier var4 = DEFAULT_ALG_ID;
         this.hashAlgorithm = var4;
      } else {
         this.hashAlgorithm = var1;
      }

      this.certHash = var2;
      this.issuerSerial = var3;
   }

   public static ESSCertIDv2 getInstance(Object var0) {
      ESSCertIDv2 var1;
      if(var0 != null && !(var0 instanceof ESSCertIDv2)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in \'ESSCertIDv2\' factory : ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).append(".").toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new ESSCertIDv2(var2);
      } else {
         var1 = (ESSCertIDv2)var0;
      }

      return var1;
   }

   public byte[] getCertHash() {
      return this.certHash;
   }

   public AlgorithmIdentifier getHashAlgorithm() {
      return this.hashAlgorithm;
   }

   public IssuerSerial getIssuerSerial() {
      return this.issuerSerial;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      AlgorithmIdentifier var2 = this.hashAlgorithm;
      AlgorithmIdentifier var3 = DEFAULT_ALG_ID;
      if(!var2.equals(var3)) {
         AlgorithmIdentifier var4 = this.hashAlgorithm;
         var1.add(var4);
      }

      byte[] var5 = this.certHash;
      DERObject var6 = (new DEROctetString(var5)).toASN1Object();
      var1.add(var6);
      if(this.issuerSerial != null) {
         IssuerSerial var7 = this.issuerSerial;
         var1.add(var7);
      }

      return new DERSequence(var1);
   }
}

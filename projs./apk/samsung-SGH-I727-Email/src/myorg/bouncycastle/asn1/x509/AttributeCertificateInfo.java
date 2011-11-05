package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.AlgorithmIdentifier;
import myorg.bouncycastle.asn1.x509.AttCertIssuer;
import myorg.bouncycastle.asn1.x509.AttCertValidityPeriod;
import myorg.bouncycastle.asn1.x509.Holder;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class AttributeCertificateInfo extends ASN1Encodable {

   private AttCertValidityPeriod attrCertValidityPeriod;
   private ASN1Sequence attributes;
   private X509Extensions extensions;
   private Holder holder;
   private AttCertIssuer issuer;
   private DERBitString issuerUniqueID;
   private DERInteger serialNumber;
   private AlgorithmIdentifier signature;
   private DERInteger version;


   public AttributeCertificateInfo(ASN1Sequence var1) {
      if(var1.size() >= 7 && var1.size() <= 9) {
         DERInteger var5 = DERInteger.getInstance(var1.getObjectAt(0));
         this.version = var5;
         Holder var6 = Holder.getInstance(var1.getObjectAt(1));
         this.holder = var6;
         AttCertIssuer var7 = AttCertIssuer.getInstance(var1.getObjectAt(2));
         this.issuer = var7;
         AlgorithmIdentifier var8 = AlgorithmIdentifier.getInstance(var1.getObjectAt(3));
         this.signature = var8;
         DERInteger var9 = DERInteger.getInstance(var1.getObjectAt(4));
         this.serialNumber = var9;
         AttCertValidityPeriod var10 = AttCertValidityPeriod.getInstance(var1.getObjectAt(5));
         this.attrCertValidityPeriod = var10;
         ASN1Sequence var11 = ASN1Sequence.getInstance(var1.getObjectAt(6));
         this.attributes = var11;
         int var12 = 7;

         while(true) {
            int var13 = var1.size();
            if(var12 >= var13) {
               return;
            }

            ASN1Encodable var14 = (ASN1Encodable)var1.getObjectAt(var12);
            if(var14 instanceof DERBitString) {
               DERBitString var15 = DERBitString.getInstance(var1.getObjectAt(var12));
               this.issuerUniqueID = var15;
            } else if(var14 instanceof ASN1Sequence || var14 instanceof X509Extensions) {
               X509Extensions var16 = X509Extensions.getInstance(var1.getObjectAt(var12));
               this.extensions = var16;
            }

            ++var12;
         }
      } else {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      }
   }

   public static AttributeCertificateInfo getInstance(Object var0) {
      AttributeCertificateInfo var1;
      if(var0 instanceof AttributeCertificateInfo) {
         var1 = (AttributeCertificateInfo)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new AttributeCertificateInfo(var2);
      }

      return var1;
   }

   public static AttributeCertificateInfo getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public AttCertValidityPeriod getAttrCertValidityPeriod() {
      return this.attrCertValidityPeriod;
   }

   public ASN1Sequence getAttributes() {
      return this.attributes;
   }

   public X509Extensions getExtensions() {
      return this.extensions;
   }

   public Holder getHolder() {
      return this.holder;
   }

   public AttCertIssuer getIssuer() {
      return this.issuer;
   }

   public DERBitString getIssuerUniqueID() {
      return this.issuerUniqueID;
   }

   public DERInteger getSerialNumber() {
      return this.serialNumber;
   }

   public AlgorithmIdentifier getSignature() {
      return this.signature;
   }

   public DERInteger getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERInteger var2 = this.version;
      var1.add(var2);
      Holder var3 = this.holder;
      var1.add(var3);
      AttCertIssuer var4 = this.issuer;
      var1.add(var4);
      AlgorithmIdentifier var5 = this.signature;
      var1.add(var5);
      DERInteger var6 = this.serialNumber;
      var1.add(var6);
      AttCertValidityPeriod var7 = this.attrCertValidityPeriod;
      var1.add(var7);
      ASN1Sequence var8 = this.attributes;
      var1.add(var8);
      if(this.issuerUniqueID != null) {
         DERBitString var9 = this.issuerUniqueID;
         var1.add(var9);
      }

      if(this.extensions != null) {
         X509Extensions var10 = this.extensions;
         var1.add(var10);
      }

      return new DERSequence(var1);
   }
}

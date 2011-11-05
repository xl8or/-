package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.GeneralNames;
import myorg.bouncycastle.asn1.x509.IssuerSerial;
import myorg.bouncycastle.asn1.x509.ObjectDigestInfo;

public class V2Form extends ASN1Encodable {

   IssuerSerial baseCertificateID;
   GeneralNames issuerName;
   ObjectDigestInfo objectDigestInfo;


   public V2Form(ASN1Sequence var1) {
      if(var1.size() > 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         int var5 = 0;
         if(!(var1.getObjectAt(0) instanceof ASN1TaggedObject)) {
            ++var5;
            GeneralNames var6 = GeneralNames.getInstance(var1.getObjectAt(0));
            this.issuerName = var6;
         }

         int var7 = var5;

         while(true) {
            int var8 = var1.size();
            if(var7 == var8) {
               return;
            }

            ASN1TaggedObject var9 = ASN1TaggedObject.getInstance(var1.getObjectAt(var7));
            if(var9.getTagNo() == 0) {
               IssuerSerial var10 = IssuerSerial.getInstance(var9, (boolean)0);
               this.baseCertificateID = var10;
            } else {
               if(var9.getTagNo() != 1) {
                  StringBuilder var12 = (new StringBuilder()).append("Bad tag number: ");
                  int var13 = var9.getTagNo();
                  String var14 = var12.append(var13).toString();
                  throw new IllegalArgumentException(var14);
               }

               ObjectDigestInfo var11 = ObjectDigestInfo.getInstance(var9, (boolean)0);
               this.objectDigestInfo = var11;
            }

            ++var7;
         }
      }
   }

   public V2Form(GeneralNames var1) {
      this.issuerName = var1;
   }

   public static V2Form getInstance(Object var0) {
      V2Form var1;
      if(var0 != null && !(var0 instanceof V2Form)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new V2Form(var2);
      } else {
         var1 = (V2Form)var0;
      }

      return var1;
   }

   public static V2Form getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public IssuerSerial getBaseCertificateID() {
      return this.baseCertificateID;
   }

   public GeneralNames getIssuerName() {
      return this.issuerName;
   }

   public ObjectDigestInfo getObjectDigestInfo() {
      return this.objectDigestInfo;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.issuerName != null) {
         GeneralNames var2 = this.issuerName;
         var1.add(var2);
      }

      if(this.baseCertificateID != null) {
         IssuerSerial var3 = this.baseCertificateID;
         DERTaggedObject var4 = new DERTaggedObject((boolean)0, 0, var3);
         var1.add(var4);
      }

      if(this.objectDigestInfo != null) {
         ObjectDigestInfo var5 = this.objectDigestInfo;
         DERTaggedObject var6 = new DERTaggedObject((boolean)0, 1, var5);
         var1.add(var6);
      }

      return new DERSequence(var1);
   }
}

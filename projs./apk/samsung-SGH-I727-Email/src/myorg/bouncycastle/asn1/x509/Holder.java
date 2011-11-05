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

public class Holder extends ASN1Encodable {

   IssuerSerial baseCertificateID;
   GeneralNames entityName;
   ObjectDigestInfo objectDigestInfo;
   private int version = 1;


   public Holder(ASN1Sequence var1) {
      if(var1.size() > 3) {
         StringBuilder var2 = (new StringBuilder()).append("Bad sequence size: ");
         int var3 = var1.size();
         String var4 = var2.append(var3).toString();
         throw new IllegalArgumentException(var4);
      } else {
         int var5 = 0;

         while(true) {
            int var6 = var1.size();
            if(var5 == var6) {
               this.version = 1;
               return;
            }

            ASN1TaggedObject var7 = ASN1TaggedObject.getInstance(var1.getObjectAt(var5));
            switch(var7.getTagNo()) {
            case 0:
               IssuerSerial var8 = IssuerSerial.getInstance(var7, (boolean)0);
               this.baseCertificateID = var8;
               break;
            case 1:
               GeneralNames var9 = GeneralNames.getInstance(var7, (boolean)0);
               this.entityName = var9;
               break;
            case 2:
               ObjectDigestInfo var10 = ObjectDigestInfo.getInstance(var7, (boolean)0);
               this.objectDigestInfo = var10;
               break;
            default:
               throw new IllegalArgumentException("unknown tag in Holder");
            }

            ++var5;
         }
      }
   }

   public Holder(ASN1TaggedObject var1) {
      switch(var1.getTagNo()) {
      case 0:
         IssuerSerial var2 = IssuerSerial.getInstance(var1, (boolean)0);
         this.baseCertificateID = var2;
         break;
      case 1:
         GeneralNames var3 = GeneralNames.getInstance(var1, (boolean)0);
         this.entityName = var3;
         break;
      default:
         throw new IllegalArgumentException("unknown tag in Holder");
      }

      this.version = 0;
   }

   public Holder(GeneralNames var1) {
      this.entityName = var1;
   }

   public Holder(GeneralNames var1, int var2) {
      this.entityName = var1;
      this.version = var2;
   }

   public Holder(IssuerSerial var1) {
      this.baseCertificateID = var1;
   }

   public Holder(IssuerSerial var1, int var2) {
      this.baseCertificateID = var1;
      this.version = var2;
   }

   public Holder(ObjectDigestInfo var1) {
      this.objectDigestInfo = var1;
   }

   public static Holder getInstance(Object var0) {
      Holder var1;
      if(var0 instanceof Holder) {
         var1 = (Holder)var0;
      } else if(var0 instanceof ASN1Sequence) {
         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new Holder(var2);
      } else {
         if(!(var0 instanceof ASN1TaggedObject)) {
            StringBuilder var4 = (new StringBuilder()).append("unknown object in factory: ");
            String var5 = var0.getClass().getName();
            String var6 = var4.append(var5).toString();
            throw new IllegalArgumentException(var6);
         }

         ASN1TaggedObject var3 = (ASN1TaggedObject)var0;
         var1 = new Holder(var3);
      }

      return var1;
   }

   public IssuerSerial getBaseCertificateID() {
      return this.baseCertificateID;
   }

   public GeneralNames getEntityName() {
      return this.entityName;
   }

   public ObjectDigestInfo getObjectDigestInfo() {
      return this.objectDigestInfo;
   }

   public int getVersion() {
      return this.version;
   }

   public DERObject toASN1Object() {
      Object var8;
      if(this.version == 1) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         if(this.baseCertificateID != null) {
            IssuerSerial var2 = this.baseCertificateID;
            DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
            var1.add(var3);
         }

         if(this.entityName != null) {
            GeneralNames var4 = this.entityName;
            DERTaggedObject var5 = new DERTaggedObject((boolean)0, 1, var4);
            var1.add(var5);
         }

         if(this.objectDigestInfo != null) {
            ObjectDigestInfo var6 = this.objectDigestInfo;
            DERTaggedObject var7 = new DERTaggedObject((boolean)0, 2, var6);
            var1.add(var7);
         }

         var8 = new DERSequence(var1);
      } else if(this.entityName != null) {
         GeneralNames var9 = this.entityName;
         var8 = new DERTaggedObject((boolean)0, 1, var9);
      } else {
         IssuerSerial var10 = this.baseCertificateID;
         var8 = new DERTaggedObject((boolean)0, 0, var10);
      }

      return (DERObject)var8;
   }
}

package myorg.bouncycastle.asn1.ocsp;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.ocsp.CertID;
import myorg.bouncycastle.asn1.ocsp.CertStatus;
import myorg.bouncycastle.asn1.x509.X509Extensions;

public class SingleResponse extends ASN1Encodable {

   private CertID certID;
   private CertStatus certStatus;
   private DERGeneralizedTime nextUpdate;
   private X509Extensions singleExtensions;
   private DERGeneralizedTime thisUpdate;


   public SingleResponse(ASN1Sequence var1) {
      CertID var2 = CertID.getInstance(var1.getObjectAt(0));
      this.certID = var2;
      CertStatus var3 = CertStatus.getInstance(var1.getObjectAt(1));
      this.certStatus = var3;
      DERGeneralizedTime var4 = (DERGeneralizedTime)var1.getObjectAt(2);
      this.thisUpdate = var4;
      if(var1.size() > 4) {
         DERGeneralizedTime var5 = DERGeneralizedTime.getInstance((ASN1TaggedObject)var1.getObjectAt(3), (boolean)1);
         this.nextUpdate = var5;
         X509Extensions var6 = X509Extensions.getInstance((ASN1TaggedObject)var1.getObjectAt(4), (boolean)1);
         this.singleExtensions = var6;
      } else if(var1.size() > 3) {
         ASN1TaggedObject var7 = (ASN1TaggedObject)var1.getObjectAt(3);
         if(var7.getTagNo() == 0) {
            DERGeneralizedTime var8 = DERGeneralizedTime.getInstance(var7, (boolean)1);
            this.nextUpdate = var8;
         } else {
            X509Extensions var9 = X509Extensions.getInstance(var7, (boolean)1);
            this.singleExtensions = var9;
         }
      }
   }

   public SingleResponse(CertID var1, CertStatus var2, DERGeneralizedTime var3, DERGeneralizedTime var4, X509Extensions var5) {
      this.certID = var1;
      this.certStatus = var2;
      this.thisUpdate = var3;
      this.nextUpdate = var4;
      this.singleExtensions = var5;
   }

   public static SingleResponse getInstance(Object var0) {
      SingleResponse var1;
      if(var0 != null && !(var0 instanceof SingleResponse)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SingleResponse(var2);
      } else {
         var1 = (SingleResponse)var0;
      }

      return var1;
   }

   public static SingleResponse getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public CertID getCertID() {
      return this.certID;
   }

   public CertStatus getCertStatus() {
      return this.certStatus;
   }

   public DERGeneralizedTime getNextUpdate() {
      return this.nextUpdate;
   }

   public X509Extensions getSingleExtensions() {
      return this.singleExtensions;
   }

   public DERGeneralizedTime getThisUpdate() {
      return this.thisUpdate;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      CertID var2 = this.certID;
      var1.add(var2);
      CertStatus var3 = this.certStatus;
      var1.add(var3);
      DERGeneralizedTime var4 = this.thisUpdate;
      var1.add(var4);
      if(this.nextUpdate != null) {
         DERGeneralizedTime var5 = this.nextUpdate;
         DERTaggedObject var6 = new DERTaggedObject((boolean)1, 0, var5);
         var1.add(var6);
      }

      if(this.singleExtensions != null) {
         X509Extensions var7 = this.singleExtensions;
         DERTaggedObject var8 = new DERTaggedObject((boolean)1, 1, var7);
         var1.add(var8);
      }

      return new DERSequence(var1);
   }
}

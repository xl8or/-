package myorg.bouncycastle.asn1.x509;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.x509.X509Extension;

public class PrivateKeyUsagePeriod extends ASN1Encodable {

   private DERGeneralizedTime _notAfter;
   private DERGeneralizedTime _notBefore;


   private PrivateKeyUsagePeriod(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         ASN1TaggedObject var3 = (ASN1TaggedObject)var2.nextElement();
         if(var3.getTagNo() == 0) {
            DERGeneralizedTime var4 = DERGeneralizedTime.getInstance(var3, (boolean)0);
            this._notBefore = var4;
         } else if(var3.getTagNo() == 1) {
            DERGeneralizedTime var5 = DERGeneralizedTime.getInstance(var3, (boolean)0);
            this._notAfter = var5;
         }
      }

   }

   public static PrivateKeyUsagePeriod getInstance(Object var0) {
      PrivateKeyUsagePeriod var1;
      if(var0 instanceof PrivateKeyUsagePeriod) {
         var1 = (PrivateKeyUsagePeriod)var0;
      } else if(var0 instanceof ASN1Sequence) {
         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PrivateKeyUsagePeriod(var2);
      } else {
         if(!(var0 instanceof X509Extension)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         var1 = getInstance(X509Extension.convertValueToObject((X509Extension)var0));
      }

      return var1;
   }

   public DERGeneralizedTime getNotAfter() {
      return this._notAfter;
   }

   public DERGeneralizedTime getNotBefore() {
      return this._notBefore;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this._notBefore != null) {
         DERGeneralizedTime var2 = this._notBefore;
         DERTaggedObject var3 = new DERTaggedObject((boolean)0, 0, var2);
         var1.add(var3);
      }

      if(this._notAfter != null) {
         DERGeneralizedTime var4 = this._notAfter;
         DERTaggedObject var5 = new DERTaggedObject((boolean)0, 1, var4);
         var1.add(var5);
      }

      return new DERSequence(var1);
   }
}

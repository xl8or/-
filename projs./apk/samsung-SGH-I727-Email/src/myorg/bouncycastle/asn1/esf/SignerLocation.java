package myorg.bouncycastle.asn1.esf;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERUTF8String;

public class SignerLocation extends ASN1Encodable {

   private DERUTF8String countryName;
   private DERUTF8String localityName;
   private ASN1Sequence postalAddress;


   public SignerLocation(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();

      while(var2.hasMoreElements()) {
         DERTaggedObject var3 = (DERTaggedObject)var2.nextElement();
         switch(var3.getTagNo()) {
         case 0:
            DERUTF8String var4 = DERUTF8String.getInstance(var3, (boolean)1);
            this.countryName = var4;
            break;
         case 1:
            DERUTF8String var5 = DERUTF8String.getInstance(var3, (boolean)1);
            this.localityName = var5;
            break;
         case 2:
            if(var3.isExplicit()) {
               ASN1Sequence var6 = ASN1Sequence.getInstance(var3, (boolean)1);
               this.postalAddress = var6;
            } else {
               ASN1Sequence var7 = ASN1Sequence.getInstance(var3, (boolean)0);
               this.postalAddress = var7;
            }

            if(this.postalAddress != null && this.postalAddress.size() > 6) {
               throw new IllegalArgumentException("postal address must contain less than 6 strings");
            }
            break;
         default:
            throw new IllegalArgumentException("illegal tag");
         }
      }

   }

   public SignerLocation(DERUTF8String var1, DERUTF8String var2, ASN1Sequence var3) {
      if(var3 != null && var3.size() > 6) {
         throw new IllegalArgumentException("postal address must contain less than 6 strings");
      } else {
         if(var1 != null) {
            DERUTF8String var4 = DERUTF8String.getInstance(var1.toASN1Object());
            this.countryName = var4;
         }

         if(var2 != null) {
            DERUTF8String var5 = DERUTF8String.getInstance(var2.toASN1Object());
            this.localityName = var5;
         }

         if(var3 != null) {
            ASN1Sequence var6 = ASN1Sequence.getInstance(var3.toASN1Object());
            this.postalAddress = var6;
         }
      }
   }

   public static SignerLocation getInstance(Object var0) {
      SignerLocation var1;
      if(var0 != null && !(var0 instanceof SignerLocation)) {
         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new SignerLocation(var2);
      } else {
         var1 = (SignerLocation)var0;
      }

      return var1;
   }

   public DERUTF8String getCountryName() {
      return this.countryName;
   }

   public DERUTF8String getLocalityName() {
      return this.localityName;
   }

   public ASN1Sequence getPostalAddress() {
      return this.postalAddress;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.countryName != null) {
         DERUTF8String var2 = this.countryName;
         DERTaggedObject var3 = new DERTaggedObject((boolean)1, 0, var2);
         var1.add(var3);
      }

      if(this.localityName != null) {
         DERUTF8String var4 = this.localityName;
         DERTaggedObject var5 = new DERTaggedObject((boolean)1, 1, var4);
         var1.add(var5);
      }

      if(this.postalAddress != null) {
         ASN1Sequence var6 = this.postalAddress;
         DERTaggedObject var7 = new DERTaggedObject((boolean)1, 2, var6);
         var1.add(var7);
      }

      return new DERSequence(var1);
   }
}

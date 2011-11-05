package myorg.bouncycastle.asn1.mozilla;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class PublicKeyAndChallenge extends ASN1Encodable {

   private DERIA5String challenge;
   private ASN1Sequence pkacSeq;
   private SubjectPublicKeyInfo spki;


   public PublicKeyAndChallenge(ASN1Sequence var1) {
      this.pkacSeq = var1;
      SubjectPublicKeyInfo var2 = SubjectPublicKeyInfo.getInstance(var1.getObjectAt(0));
      this.spki = var2;
      DERIA5String var3 = DERIA5String.getInstance(var1.getObjectAt(1));
      this.challenge = var3;
   }

   public static PublicKeyAndChallenge getInstance(Object var0) {
      PublicKeyAndChallenge var1;
      if(var0 instanceof PublicKeyAndChallenge) {
         var1 = (PublicKeyAndChallenge)var0;
      } else {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unkown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new PublicKeyAndChallenge(var2);
      }

      return var1;
   }

   public DERIA5String getChallenge() {
      return this.challenge;
   }

   public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
      return this.spki;
   }

   public DERObject toASN1Object() {
      return this.pkacSeq;
   }
}

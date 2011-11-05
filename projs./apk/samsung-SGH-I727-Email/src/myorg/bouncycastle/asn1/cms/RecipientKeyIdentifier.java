package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.OtherKeyAttribute;

public class RecipientKeyIdentifier extends ASN1Encodable {

   private DERGeneralizedTime date;
   private OtherKeyAttribute other;
   private ASN1OctetString subjectKeyIdentifier;


   public RecipientKeyIdentifier(ASN1OctetString var1, DERGeneralizedTime var2, OtherKeyAttribute var3) {
      this.subjectKeyIdentifier = var1;
      this.date = var2;
      this.other = var3;
   }

   public RecipientKeyIdentifier(ASN1Sequence var1) {
      ASN1OctetString var2 = ASN1OctetString.getInstance(var1.getObjectAt(0));
      this.subjectKeyIdentifier = var2;
      switch(var1.size()) {
      case 2:
         if(!(var1.getObjectAt(1) instanceof DERGeneralizedTime)) {
            OtherKeyAttribute var4 = OtherKeyAttribute.getInstance(var1.getObjectAt(2));
            this.other = var4;
            return;
         } else {
            DERGeneralizedTime var3 = (DERGeneralizedTime)var1.getObjectAt(1);
            this.date = var3;
         }
      case 1:
         return;
      case 3:
         DERGeneralizedTime var5 = (DERGeneralizedTime)var1.getObjectAt(1);
         this.date = var5;
         OtherKeyAttribute var6 = OtherKeyAttribute.getInstance(var1.getObjectAt(2));
         this.other = var6;
         return;
      default:
         throw new IllegalArgumentException("Invalid RecipientKeyIdentifier");
      }
   }

   public static RecipientKeyIdentifier getInstance(Object var0) {
      RecipientKeyIdentifier var1;
      if(var0 != null && !(var0 instanceof RecipientKeyIdentifier)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("Invalid RecipientKeyIdentifier: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new RecipientKeyIdentifier(var2);
      } else {
         var1 = (RecipientKeyIdentifier)var0;
      }

      return var1;
   }

   public static RecipientKeyIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public DERGeneralizedTime getDate() {
      return this.date;
   }

   public OtherKeyAttribute getOtherKeyAttribute() {
      return this.other;
   }

   public ASN1OctetString getSubjectKeyIdentifier() {
      return this.subjectKeyIdentifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      ASN1OctetString var2 = this.subjectKeyIdentifier;
      var1.add(var2);
      if(this.date != null) {
         DERGeneralizedTime var3 = this.date;
         var1.add(var3);
      }

      if(this.other != null) {
         OtherKeyAttribute var4 = this.other;
         var1.add(var4);
      }

      return new DERSequence(var1);
   }
}

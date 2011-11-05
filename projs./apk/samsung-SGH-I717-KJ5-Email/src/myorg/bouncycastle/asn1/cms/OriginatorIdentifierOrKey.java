package myorg.bouncycastle.asn1.cms;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import myorg.bouncycastle.asn1.cms.OriginatorPublicKey;
import myorg.bouncycastle.asn1.x509.SubjectKeyIdentifier;

public class OriginatorIdentifierOrKey extends ASN1Encodable implements ASN1Choice {

   private DEREncodable id;


   public OriginatorIdentifierOrKey(ASN1OctetString var1) {
      SubjectKeyIdentifier var2 = new SubjectKeyIdentifier(var1);
      this(var2);
   }

   public OriginatorIdentifierOrKey(DERObject var1) {
      this.id = var1;
   }

   public OriginatorIdentifierOrKey(IssuerAndSerialNumber var1) {
      this.id = var1;
   }

   public OriginatorIdentifierOrKey(OriginatorPublicKey var1) {
      DERTaggedObject var2 = new DERTaggedObject((boolean)0, 1, var1);
      this.id = var2;
   }

   public OriginatorIdentifierOrKey(SubjectKeyIdentifier var1) {
      DERTaggedObject var2 = new DERTaggedObject((boolean)0, 0, var1);
      this.id = var2;
   }

   public static OriginatorIdentifierOrKey getInstance(Object var0) {
      OriginatorIdentifierOrKey var1;
      if(var0 != null && !(var0 instanceof OriginatorIdentifierOrKey)) {
         if(var0 instanceof IssuerAndSerialNumber) {
            IssuerAndSerialNumber var2 = (IssuerAndSerialNumber)var0;
            var1 = new OriginatorIdentifierOrKey(var2);
         } else if(var0 instanceof SubjectKeyIdentifier) {
            SubjectKeyIdentifier var3 = (SubjectKeyIdentifier)var0;
            var1 = new OriginatorIdentifierOrKey(var3);
         } else if(var0 instanceof OriginatorPublicKey) {
            OriginatorPublicKey var4 = (OriginatorPublicKey)var0;
            var1 = new OriginatorIdentifierOrKey(var4);
         } else {
            if(!(var0 instanceof ASN1TaggedObject)) {
               StringBuilder var6 = (new StringBuilder()).append("Invalid OriginatorIdentifierOrKey: ");
               String var7 = var0.getClass().getName();
               String var8 = var6.append(var7).toString();
               throw new IllegalArgumentException(var8);
            }

            ASN1TaggedObject var5 = (ASN1TaggedObject)var0;
            var1 = new OriginatorIdentifierOrKey(var5);
         }
      } else {
         var1 = (OriginatorIdentifierOrKey)var0;
      }

      return var1;
   }

   public static OriginatorIdentifierOrKey getInstance(ASN1TaggedObject var0, boolean var1) {
      if(!var1) {
         throw new IllegalArgumentException("Can\'t implicitly tag OriginatorIdentifierOrKey");
      } else {
         return getInstance(var0.getObject());
      }
   }

   public DEREncodable getId() {
      return this.id;
   }

   public IssuerAndSerialNumber getIssuerAndSerialNumber() {
      IssuerAndSerialNumber var1;
      if(this.id instanceof IssuerAndSerialNumber) {
         var1 = (IssuerAndSerialNumber)this.id;
      } else {
         var1 = null;
      }

      return var1;
   }

   public OriginatorPublicKey getOriginatorKey() {
      OriginatorPublicKey var1;
      if(this.id instanceof ASN1TaggedObject && ((ASN1TaggedObject)this.id).getTagNo() == 1) {
         var1 = OriginatorPublicKey.getInstance((ASN1TaggedObject)this.id, (boolean)0);
      } else {
         var1 = null;
      }

      return var1;
   }

   public SubjectKeyIdentifier getSubjectKeyIdentifier() {
      SubjectKeyIdentifier var1;
      if(this.id instanceof ASN1TaggedObject && ((ASN1TaggedObject)this.id).getTagNo() == 0) {
         var1 = SubjectKeyIdentifier.getInstance((ASN1TaggedObject)this.id, (boolean)0);
      } else {
         var1 = null;
      }

      return var1;
   }

   public DERObject toASN1Object() {
      return this.id.getDERObject();
   }
}

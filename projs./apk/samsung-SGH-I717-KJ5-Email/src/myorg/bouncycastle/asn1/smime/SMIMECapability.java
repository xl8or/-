package myorg.bouncycastle.asn1.smime;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SMIMECapability extends ASN1Encodable {

   public static final DERObjectIdentifier aES128_CBC = NISTObjectIdentifiers.id_aes128_CBC;
   public static final DERObjectIdentifier aES192_CBC = NISTObjectIdentifiers.id_aes192_CBC;
   public static final DERObjectIdentifier aES256_CBC = NISTObjectIdentifiers.id_aes256_CBC;
   public static final DERObjectIdentifier canNotDecryptAny = PKCSObjectIdentifiers.canNotDecryptAny;
   public static final DERObjectIdentifier dES_CBC = new DERObjectIdentifier("1.3.14.3.2.7");
   public static final DERObjectIdentifier dES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC;
   public static final DERObjectIdentifier preferSignedData = PKCSObjectIdentifiers.preferSignedData;
   public static final DERObjectIdentifier rC2_CBC = PKCSObjectIdentifiers.RC2_CBC;
   public static final DERObjectIdentifier sMIMECapabilitiesVersions = PKCSObjectIdentifiers.sMIMECapabilitiesVersions;
   private DERObjectIdentifier capabilityID;
   private DEREncodable parameters;


   public SMIMECapability(ASN1Sequence var1) {
      DERObjectIdentifier var2 = (DERObjectIdentifier)var1.getObjectAt(0);
      this.capabilityID = var2;
      if(var1.size() > 1) {
         DERObject var3 = (DERObject)var1.getObjectAt(1);
         this.parameters = var3;
      }
   }

   public SMIMECapability(DERObjectIdentifier var1, DEREncodable var2) {
      this.capabilityID = var1;
      this.parameters = var2;
   }

   public static SMIMECapability getInstance(Object var0) {
      SMIMECapability var1;
      if(var0 != null && !(var0 instanceof SMIMECapability)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("Invalid SMIMECapability");
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new SMIMECapability(var2);
      } else {
         var1 = (SMIMECapability)var0;
      }

      return var1;
   }

   public DERObjectIdentifier getCapabilityID() {
      return this.capabilityID;
   }

   public DEREncodable getParameters() {
      return this.parameters;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      DERObjectIdentifier var2 = this.capabilityID;
      var1.add(var2);
      if(this.parameters != null) {
         DEREncodable var3 = this.parameters;
         var1.add(var3);
      }

      return new DERSequence(var1);
   }
}

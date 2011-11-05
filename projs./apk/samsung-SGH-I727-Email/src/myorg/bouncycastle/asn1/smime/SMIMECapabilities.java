package myorg.bouncycastle.asn1.smime;

import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cms.Attribute;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.smime.SMIMECapability;

public class SMIMECapabilities extends ASN1Encodable {

   public static final DERObjectIdentifier canNotDecryptAny = PKCSObjectIdentifiers.canNotDecryptAny;
   public static final DERObjectIdentifier dES_CBC = new DERObjectIdentifier("1.3.14.3.2.7");
   public static final DERObjectIdentifier dES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC;
   public static final DERObjectIdentifier preferSignedData = PKCSObjectIdentifiers.preferSignedData;
   public static final DERObjectIdentifier rC2_CBC = PKCSObjectIdentifiers.RC2_CBC;
   public static final DERObjectIdentifier sMIMECapabilitesVersions = PKCSObjectIdentifiers.sMIMECapabilitiesVersions;
   private ASN1Sequence capabilities;


   public SMIMECapabilities(ASN1Sequence var1) {
      this.capabilities = var1;
   }

   public static SMIMECapabilities getInstance(Object var0) {
      SMIMECapabilities var1;
      if(var0 != null && !(var0 instanceof SMIMECapabilities)) {
         if(var0 instanceof ASN1Sequence) {
            ASN1Sequence var2 = (ASN1Sequence)var0;
            var1 = new SMIMECapabilities(var2);
         } else {
            if(!(var0 instanceof Attribute)) {
               StringBuilder var4 = (new StringBuilder()).append("unknown object in factory: ");
               String var5 = var0.getClass().getName();
               String var6 = var4.append(var5).toString();
               throw new IllegalArgumentException(var6);
            }

            ASN1Sequence var3 = (ASN1Sequence)((ASN1Sequence)((Attribute)var0).getAttrValues().getObjectAt(0));
            var1 = new SMIMECapabilities(var3);
         }
      } else {
         var1 = (SMIMECapabilities)var0;
      }

      return var1;
   }

   public Vector getCapabilities(DERObjectIdentifier var1) {
      Enumeration var2 = this.capabilities.getObjects();
      Vector var3 = new Vector();
      if(var1 == null) {
         while(var2.hasMoreElements()) {
            SMIMECapability var4 = SMIMECapability.getInstance(var2.nextElement());
            var3.addElement(var4);
         }
      } else {
         while(var2.hasMoreElements()) {
            SMIMECapability var5 = SMIMECapability.getInstance(var2.nextElement());
            DERObjectIdentifier var6 = var5.getCapabilityID();
            if(var1.equals(var6)) {
               var3.addElement(var5);
            }
         }
      }

      return var3;
   }

   public DERObject toASN1Object() {
      return this.capabilities;
   }
}

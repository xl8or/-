package myorg.bouncycastle.asn1.x509.qualified;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.x509.GeneralName;

public class SemanticsInformation extends ASN1Encodable {

   GeneralName[] nameRegistrationAuthorities;
   DERObjectIdentifier semanticsIdentifier;


   public SemanticsInformation(ASN1Sequence var1) {
      Enumeration var2 = var1.getObjects();
      if(var1.size() < 1) {
         throw new IllegalArgumentException("no objects in SemanticsInformation");
      } else {
         Object var3 = var2.nextElement();
         if(var3 instanceof DERObjectIdentifier) {
            DERObjectIdentifier var4 = DERObjectIdentifier.getInstance(var3);
            this.semanticsIdentifier = var4;
            if(var2.hasMoreElements()) {
               var3 = var2.nextElement();
            } else {
               var3 = null;
            }
         }

         if(var3 != null) {
            ASN1Sequence var5 = ASN1Sequence.getInstance(var3);
            GeneralName[] var6 = new GeneralName[var5.size()];
            this.nameRegistrationAuthorities = var6;
            int var7 = 0;

            while(true) {
               int var8 = var5.size();
               if(var7 >= var8) {
                  return;
               }

               GeneralName[] var9 = this.nameRegistrationAuthorities;
               GeneralName var10 = GeneralName.getInstance(var5.getObjectAt(var7));
               var9[var7] = var10;
               ++var7;
            }
         }
      }
   }

   public SemanticsInformation(DERObjectIdentifier var1) {
      this.semanticsIdentifier = var1;
      this.nameRegistrationAuthorities = null;
   }

   public SemanticsInformation(DERObjectIdentifier var1, GeneralName[] var2) {
      this.semanticsIdentifier = var1;
      this.nameRegistrationAuthorities = var2;
   }

   public SemanticsInformation(GeneralName[] var1) {
      this.semanticsIdentifier = null;
      this.nameRegistrationAuthorities = var1;
   }

   public static SemanticsInformation getInstance(Object var0) {
      SemanticsInformation var1;
      if(var0 != null && !(var0 instanceof SemanticsInformation)) {
         if(!(var0 instanceof ASN1Sequence)) {
            throw new IllegalArgumentException("unknown object in getInstance");
         }

         ASN1Sequence var2 = ASN1Sequence.getInstance(var0);
         var1 = new SemanticsInformation(var2);
      } else {
         var1 = (SemanticsInformation)var0;
      }

      return var1;
   }

   public GeneralName[] getNameRegistrationAuthorities() {
      return this.nameRegistrationAuthorities;
   }

   public DERObjectIdentifier getSemanticsIdentifier() {
      return this.semanticsIdentifier;
   }

   public DERObject toASN1Object() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      if(this.semanticsIdentifier != null) {
         DERObjectIdentifier var2 = this.semanticsIdentifier;
         var1.add(var2);
      }

      if(this.nameRegistrationAuthorities != null) {
         ASN1EncodableVector var3 = new ASN1EncodableVector();
         int var4 = 0;

         while(true) {
            int var5 = this.nameRegistrationAuthorities.length;
            if(var4 >= var5) {
               DERSequence var7 = new DERSequence(var3);
               var1.add(var7);
               break;
            }

            GeneralName var6 = this.nameRegistrationAuthorities[var4];
            var3.add(var6);
            ++var4;
         }
      }

      return new DERSequence(var1);
   }
}

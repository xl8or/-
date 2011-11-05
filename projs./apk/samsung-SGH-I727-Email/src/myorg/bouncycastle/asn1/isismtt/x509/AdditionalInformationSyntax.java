package myorg.bouncycastle.asn1.isismtt.x509;

import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.x500.DirectoryString;

public class AdditionalInformationSyntax extends ASN1Encodable {

   private DirectoryString information;


   public AdditionalInformationSyntax(String var1) {
      DirectoryString var2 = new DirectoryString(var1);
      this(var2);
   }

   private AdditionalInformationSyntax(DirectoryString var1) {
      this.information = var1;
   }

   public static AdditionalInformationSyntax getInstance(Object var0) {
      AdditionalInformationSyntax var1;
      if(var0 != null && !(var0 instanceof AdditionalInformationSyntax)) {
         if(!(var0 instanceof DERString)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         DirectoryString var2 = DirectoryString.getInstance(var0);
         var1 = new AdditionalInformationSyntax(var2);
      } else {
         var1 = (AdditionalInformationSyntax)var0;
      }

      return var1;
   }

   public DirectoryString getInformation() {
      return this.information;
   }

   public DERObject toASN1Object() {
      return this.information.toASN1Object();
   }
}

package myorg.bouncycastle.asn1.x509;

import java.io.IOException;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERUTF8String;
import myorg.bouncycastle.asn1.x509.X509Name;
import myorg.bouncycastle.asn1.x509.X509NameEntryConverter;

public class X509DefaultEntryConverter extends X509NameEntryConverter {

   public X509DefaultEntryConverter() {}

   public DERObject getConvertedValue(DERObjectIdentifier var1, String var2) {
      Object var3;
      if(var2.length() != 0 && var2.charAt(0) == 35) {
         byte var17 = 1;

         DERObject var4;
         try {
            var4 = this.convertHexEncoded(var2, var17);
         } catch (IOException var16) {
            StringBuilder var6 = (new StringBuilder()).append("can\'t recode value for oid ");
            String var7 = var1.getId();
            String var8 = var6.append(var7).toString();
            throw new RuntimeException(var8);
         }

         var3 = var4;
      } else {
         if(var2.length() != 0 && var2.charAt(0) == 92) {
            var2 = var2.substring(1);
         }

         DERObjectIdentifier var9 = X509Name.EmailAddress;
         if(!var1.equals(var9)) {
            DERObjectIdentifier var10 = X509Name.DC;
            if(!var1.equals(var10)) {
               DERObjectIdentifier var11 = X509Name.DATE_OF_BIRTH;
               if(var1.equals(var11)) {
                  var3 = new DERGeneralizedTime(var2);
               } else {
                  DERObjectIdentifier var12 = X509Name.C;
                  if(!var1.equals(var12)) {
                     DERObjectIdentifier var13 = X509Name.SN;
                     if(!var1.equals(var13)) {
                        DERObjectIdentifier var14 = X509Name.DN_QUALIFIER;
                        if(!var1.equals(var14)) {
                           DERObjectIdentifier var15 = X509Name.TELEPHONE_NUMBER;
                           if(!var1.equals(var15)) {
                              var3 = new DERUTF8String(var2);
                              return (DERObject)var3;
                           }
                        }
                     }
                  }

                  var3 = new DERPrintableString(var2);
               }

               return (DERObject)var3;
            }
         }

         var3 = new DERIA5String(var2);
      }

      return (DERObject)var3;
   }
}

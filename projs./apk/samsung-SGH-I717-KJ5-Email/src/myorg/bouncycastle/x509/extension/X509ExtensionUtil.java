package myorg.bouncycastle.x509.extension;

import java.io.IOException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.X509Extensions;
import myorg.bouncycastle.asn1.x509.X509Name;

public class X509ExtensionUtil {

   public X509ExtensionUtil() {}

   public static ASN1Object fromExtensionValue(byte[] var0) throws IOException {
      return ASN1Object.fromByteArray(((ASN1OctetString)ASN1Object.fromByteArray(var0)).getOctets());
   }

   private static Collection getAlternativeNames(byte[] var0) throws CertificateParsingException {
      Object var1;
      if(var0 == null) {
         var1 = Collections.EMPTY_LIST;
      } else {
         Collection var24;
         try {
            ArrayList var2 = new ArrayList();
            Enumeration var3 = DERSequence.getInstance(fromExtensionValue(var0)).getObjects();

            while(true) {
               if(!var3.hasMoreElements()) {
                  var24 = Collections.unmodifiableCollection(var2);
                  break;
               }

               GeneralName var4 = GeneralName.getInstance(var3.nextElement());
               ArrayList var5 = new ArrayList();
               int var6 = var4.getTagNo();
               Integer var7 = new Integer(var6);
               var5.add(var7);
               switch(var4.getTagNo()) {
               case 0:
               case 3:
               case 5:
                  DERObject var13 = var4.getName().getDERObject();
                  var5.add(var13);
                  break;
               case 1:
               case 2:
               case 6:
                  String var18 = ((DERString)var4.getName()).getString();
                  var5.add(var18);
                  break;
               case 4:
                  String var16 = X509Name.getInstance(var4.getName()).toString();
                  var5.add(var16);
                  break;
               case 7:
                  byte[] var22 = DEROctetString.getInstance(var4.getName()).getOctets();
                  var5.add(var22);
                  break;
               case 8:
                  String var20 = DERObjectIdentifier.getInstance(var4.getName()).getId();
                  var5.add(var20);
                  break;
               default:
                  StringBuilder var9 = (new StringBuilder()).append("Bad tag number: ");
                  int var10 = var4.getTagNo();
                  String var11 = var9.append(var10).toString();
                  throw new IOException(var11);
               }

               var2.add(var5);
            }
         } catch (Exception var25) {
            String var12 = var25.getMessage();
            throw new CertificateParsingException(var12);
         }

         var1 = var24;
      }

      return (Collection)var1;
   }

   public static Collection getIssuerAlternativeNames(X509Certificate var0) throws CertificateParsingException {
      String var1 = X509Extensions.IssuerAlternativeName.getId();
      return getAlternativeNames(var0.getExtensionValue(var1));
   }

   public static Collection getSubjectAlternativeNames(X509Certificate var0) throws CertificateParsingException {
      String var1 = X509Extensions.SubjectAlternativeName.getId();
      return getAlternativeNames(var0.getExtensionValue(var1));
   }
}

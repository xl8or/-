package myorg.bouncycastle.asn1.nist;

import java.util.Enumeration;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.sec.SECNamedCurves;
import myorg.bouncycastle.asn1.sec.SECObjectIdentifiers;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.util.Strings;

public class NISTNamedCurves {

   static final Hashtable names = new Hashtable();
   static final Hashtable objIds = new Hashtable();


   static {
      DERObjectIdentifier var0 = SECObjectIdentifiers.sect571r1;
      defineCurve("B-571", var0);
      DERObjectIdentifier var1 = SECObjectIdentifiers.sect409r1;
      defineCurve("B-409", var1);
      DERObjectIdentifier var2 = SECObjectIdentifiers.sect283r1;
      defineCurve("B-283", var2);
      DERObjectIdentifier var3 = SECObjectIdentifiers.sect233r1;
      defineCurve("B-233", var3);
      DERObjectIdentifier var4 = SECObjectIdentifiers.sect163r2;
      defineCurve("B-163", var4);
      DERObjectIdentifier var5 = SECObjectIdentifiers.secp521r1;
      defineCurve("P-521", var5);
      DERObjectIdentifier var6 = SECObjectIdentifiers.secp384r1;
      defineCurve("P-384", var6);
      DERObjectIdentifier var7 = SECObjectIdentifiers.secp256r1;
      defineCurve("P-256", var7);
      DERObjectIdentifier var8 = SECObjectIdentifiers.secp224r1;
      defineCurve("P-224", var8);
      DERObjectIdentifier var9 = SECObjectIdentifiers.secp192r1;
      defineCurve("P-192", var9);
   }

   public NISTNamedCurves() {}

   static void defineCurve(String var0, DERObjectIdentifier var1) {
      objIds.put(var0, var1);
      names.put(var1, var0);
   }

   public static X9ECParameters getByName(String var0) {
      Hashtable var1 = objIds;
      String var2 = Strings.toUpperCase(var0);
      DERObjectIdentifier var3 = (DERObjectIdentifier)var1.get(var2);
      X9ECParameters var4;
      if(var3 != null) {
         var4 = getByOID(var3);
      } else {
         var4 = null;
      }

      return var4;
   }

   public static X9ECParameters getByOID(DERObjectIdentifier var0) {
      return SECNamedCurves.getByOID(var0);
   }

   public static String getName(DERObjectIdentifier var0) {
      return (String)names.get(var0);
   }

   public static Enumeration getNames() {
      return objIds.keys();
   }

   public static DERObjectIdentifier getOID(String var0) {
      Hashtable var1 = objIds;
      String var2 = Strings.toUpperCase(var0);
      return (DERObjectIdentifier)var1.get(var2);
   }
}

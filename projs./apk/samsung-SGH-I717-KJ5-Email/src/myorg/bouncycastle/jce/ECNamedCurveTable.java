package myorg.bouncycastle.jce;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Vector;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.nist.NISTNamedCurves;
import myorg.bouncycastle.asn1.sec.SECNamedCurves;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import myorg.bouncycastle.asn1.x9.X962NamedCurves;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECNamedCurveTable {

   public ECNamedCurveTable() {}

   private static void addEnumeration(Vector var0, Enumeration var1) {
      while(var1.hasMoreElements()) {
         Object var2 = var1.nextElement();
         var0.addElement(var2);
      }

   }

   public static Enumeration getNames() {
      Vector var0 = new Vector();
      Enumeration var1 = X962NamedCurves.getNames();
      addEnumeration(var0, var1);
      Enumeration var2 = SECNamedCurves.getNames();
      addEnumeration(var0, var2);
      Enumeration var3 = NISTNamedCurves.getNames();
      addEnumeration(var0, var3);
      Enumeration var4 = TeleTrusTNamedCurves.getNames();
      addEnumeration(var0, var4);
      return var0.elements();
   }

   public static ECNamedCurveParameterSpec getParameterSpec(String var0) {
      X9ECParameters var1 = X962NamedCurves.getByName(var0);
      X9ECParameters var2;
      if(var1 == null) {
         label46: {
            try {
               var2 = X962NamedCurves.getByOID(new DERObjectIdentifier(var0));
            } catch (IllegalArgumentException var15) {
               break label46;
            }

            var1 = var2;
         }
      }

      if(var1 == null) {
         var1 = SECNamedCurves.getByName(var0);
         if(var1 == null) {
            label40: {
               try {
                  var2 = SECNamedCurves.getByOID(new DERObjectIdentifier(var0));
               } catch (IllegalArgumentException var14) {
                  break label40;
               }

               var1 = var2;
            }
         }
      }

      if(var1 == null) {
         var1 = TeleTrusTNamedCurves.getByName(var0);
         if(var1 == null) {
            label34: {
               try {
                  var2 = TeleTrusTNamedCurves.getByOID(new DERObjectIdentifier(var0));
               } catch (IllegalArgumentException var13) {
                  break label34;
               }

               var1 = var2;
            }
         }
      }

      if(var1 == null) {
         var1 = NISTNamedCurves.getByName(var0);
      }

      ECNamedCurveParameterSpec var3;
      if(var1 == null) {
         var3 = null;
      } else {
         ECCurve var4 = var1.getCurve();
         ECPoint var5 = var1.getG();
         BigInteger var6 = var1.getN();
         BigInteger var7 = var1.getH();
         byte[] var8 = var1.getSeed();
         var3 = new ECNamedCurveParameterSpec(var0, var4, var5, var6, var7, var8);
      }

      return var3;
   }
}

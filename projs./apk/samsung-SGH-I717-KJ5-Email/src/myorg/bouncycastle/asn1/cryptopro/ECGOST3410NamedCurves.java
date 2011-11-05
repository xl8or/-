package myorg.bouncycastle.asn1.cryptopro;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.crypto.params.ECDomainParameters;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECFieldElement;
import myorg.bouncycastle.math.ec.ECPoint;

public class ECGOST3410NamedCurves {

   static final Hashtable names = new Hashtable();
   static final Hashtable objIds = new Hashtable();
   static final Hashtable params = new Hashtable();


   static {
      BigInteger var0 = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639319");
      BigInteger var1 = new BigInteger("115792089237316195423570985008687907853073762908499243225378155805079068850323");
      BigInteger var2 = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639316");
      BigInteger var3 = new BigInteger("166");
      ECCurve.Fp var4 = new ECCurve.Fp(var0, var2, var3);
      BigInteger var5 = var4.getQ();
      BigInteger var6 = new BigInteger("1");
      ECFieldElement.Fp var7 = new ECFieldElement.Fp(var5, var6);
      BigInteger var8 = var4.getQ();
      BigInteger var9 = new BigInteger("64033881142927202683649881450433473985931760268884941288852745803908878638612");
      ECFieldElement.Fp var10 = new ECFieldElement.Fp(var8, var9);
      ECPoint.Fp var11 = new ECPoint.Fp(var4, var7, var10);
      ECDomainParameters var12 = new ECDomainParameters(var4, var11, var1);
      Hashtable var13 = params;
      DERObjectIdentifier var14 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_A;
      var13.put(var14, var12);
      BigInteger var16 = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639319");
      BigInteger var17 = new BigInteger("115792089237316195423570985008687907853073762908499243225378155805079068850323");
      BigInteger var18 = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639316");
      BigInteger var19 = new BigInteger("166");
      ECCurve.Fp var20 = new ECCurve.Fp(var16, var18, var19);
      BigInteger var21 = var20.getQ();
      BigInteger var22 = new BigInteger("1");
      ECFieldElement.Fp var23 = new ECFieldElement.Fp(var21, var22);
      BigInteger var24 = var20.getQ();
      BigInteger var25 = new BigInteger("64033881142927202683649881450433473985931760268884941288852745803908878638612");
      ECFieldElement.Fp var26 = new ECFieldElement.Fp(var24, var25);
      ECPoint.Fp var27 = new ECPoint.Fp(var20, var23, var26);
      ECDomainParameters var28 = new ECDomainParameters(var20, var27, var17);
      Hashtable var29 = params;
      DERObjectIdentifier var30 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_XchA;
      var29.put(var30, var28);
      BigInteger var32 = new BigInteger("57896044618658097711785492504343953926634992332820282019728792003956564823193");
      BigInteger var33 = new BigInteger("57896044618658097711785492504343953927102133160255826820068844496087732066703");
      BigInteger var34 = new BigInteger("57896044618658097711785492504343953926634992332820282019728792003956564823190");
      BigInteger var35 = new BigInteger("28091019353058090096996979000309560759124368558014865957655842872397301267595");
      ECCurve.Fp var36 = new ECCurve.Fp(var32, var34, var35);
      BigInteger var37 = new BigInteger("1");
      ECFieldElement.Fp var38 = new ECFieldElement.Fp(var32, var37);
      BigInteger var39 = new BigInteger("28792665814854611296992347458380284135028636778229113005756334730996303888124");
      ECFieldElement.Fp var40 = new ECFieldElement.Fp(var32, var39);
      ECPoint.Fp var41 = new ECPoint.Fp(var36, var38, var40);
      ECDomainParameters var42 = new ECDomainParameters(var36, var41, var33);
      Hashtable var43 = params;
      DERObjectIdentifier var44 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_B;
      var43.put(var44, var42);
      BigInteger var46 = new BigInteger("70390085352083305199547718019018437841079516630045180471284346843705633502619");
      BigInteger var47 = new BigInteger("70390085352083305199547718019018437840920882647164081035322601458352298396601");
      BigInteger var48 = new BigInteger("70390085352083305199547718019018437841079516630045180471284346843705633502616");
      BigInteger var49 = new BigInteger("32858");
      ECCurve.Fp var50 = new ECCurve.Fp(var46, var48, var49);
      BigInteger var51 = new BigInteger("0");
      ECFieldElement.Fp var52 = new ECFieldElement.Fp(var46, var51);
      BigInteger var53 = new BigInteger("29818893917731240733471273240314769927240550812383695689146495261604565990247");
      ECFieldElement.Fp var54 = new ECFieldElement.Fp(var46, var53);
      ECPoint.Fp var55 = new ECPoint.Fp(var50, var52, var54);
      ECDomainParameters var56 = new ECDomainParameters(var50, var55, var47);
      Hashtable var57 = params;
      DERObjectIdentifier var58 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_XchB;
      var57.put(var58, var56);
      BigInteger var60 = new BigInteger("70390085352083305199547718019018437841079516630045180471284346843705633502619");
      BigInteger var61 = new BigInteger("70390085352083305199547718019018437840920882647164081035322601458352298396601");
      BigInteger var62 = new BigInteger("70390085352083305199547718019018437841079516630045180471284346843705633502616");
      BigInteger var63 = new BigInteger("32858");
      ECCurve.Fp var64 = new ECCurve.Fp(var60, var62, var63);
      BigInteger var65 = new BigInteger("0");
      ECFieldElement.Fp var66 = new ECFieldElement.Fp(var60, var65);
      BigInteger var67 = new BigInteger("29818893917731240733471273240314769927240550812383695689146495261604565990247");
      ECFieldElement.Fp var68 = new ECFieldElement.Fp(var60, var67);
      ECPoint.Fp var69 = new ECPoint.Fp(var64, var66, var68);
      ECDomainParameters var70 = new ECDomainParameters(var64, var69, var61);
      Hashtable var71 = params;
      DERObjectIdentifier var72 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_C;
      var71.put(var72, var70);
      Hashtable var74 = objIds;
      DERObjectIdentifier var75 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_A;
      var74.put("GostR3410-2001-CryptoPro-A", var75);
      Hashtable var77 = objIds;
      DERObjectIdentifier var78 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_B;
      var77.put("GostR3410-2001-CryptoPro-B", var78);
      Hashtable var80 = objIds;
      DERObjectIdentifier var81 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_C;
      var80.put("GostR3410-2001-CryptoPro-C", var81);
      Hashtable var83 = objIds;
      DERObjectIdentifier var84 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_XchA;
      var83.put("GostR3410-2001-CryptoPro-XchA", var84);
      Hashtable var86 = objIds;
      DERObjectIdentifier var87 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_XchB;
      var86.put("GostR3410-2001-CryptoPro-XchB", var87);
      Hashtable var89 = names;
      DERObjectIdentifier var90 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_A;
      var89.put(var90, "GostR3410-2001-CryptoPro-A");
      Hashtable var92 = names;
      DERObjectIdentifier var93 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_B;
      var92.put(var93, "GostR3410-2001-CryptoPro-B");
      Hashtable var95 = names;
      DERObjectIdentifier var96 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_C;
      var95.put(var96, "GostR3410-2001-CryptoPro-C");
      Hashtable var98 = names;
      DERObjectIdentifier var99 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_XchA;
      var98.put(var99, "GostR3410-2001-CryptoPro-XchA");
      Hashtable var101 = names;
      DERObjectIdentifier var102 = CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_XchB;
      var101.put(var102, "GostR3410-2001-CryptoPro-XchB");
   }

   public ECGOST3410NamedCurves() {}

   public static ECDomainParameters getByName(String var0) {
      DERObjectIdentifier var1 = (DERObjectIdentifier)objIds.get(var0);
      ECDomainParameters var2;
      if(var1 != null) {
         var2 = (ECDomainParameters)params.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public static ECDomainParameters getByOID(DERObjectIdentifier var0) {
      return (ECDomainParameters)params.get(var0);
   }

   public static String getName(DERObjectIdentifier var0) {
      return (String)names.get(var0);
   }

   public static Enumeration getNames() {
      return objIds.keys();
   }

   public static DERObjectIdentifier getOID(String var0) {
      return (DERObjectIdentifier)objIds.get(var0);
   }
}

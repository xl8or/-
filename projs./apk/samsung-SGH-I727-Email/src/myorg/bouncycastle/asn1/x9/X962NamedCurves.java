package myorg.bouncycastle.asn1.x9;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.asn1.x9.X9ECParametersHolder;
import myorg.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.util.Strings;
import myorg.bouncycastle.util.encoders.Hex;

public class X962NamedCurves {

   static X9ECParametersHolder c2pnb163v1 = new X962NamedCurves.8();
   static X9ECParametersHolder c2pnb163v2 = new X962NamedCurves.9();
   static X9ECParametersHolder c2pnb163v3 = new X962NamedCurves.10();
   static X9ECParametersHolder c2pnb176w1 = new X962NamedCurves.11();
   static X9ECParametersHolder c2pnb208w1 = new X962NamedCurves.15();
   static X9ECParametersHolder c2pnb272w1 = new X962NamedCurves.19();
   static X9ECParametersHolder c2pnb304w1 = new X962NamedCurves.20();
   static X9ECParametersHolder c2pnb368w1 = new X962NamedCurves.22();
   static X9ECParametersHolder c2tnb191v1 = new X962NamedCurves.12();
   static X9ECParametersHolder c2tnb191v2 = new X962NamedCurves.13();
   static X9ECParametersHolder c2tnb191v3 = new X962NamedCurves.14();
   static X9ECParametersHolder c2tnb239v1 = new X962NamedCurves.16();
   static X9ECParametersHolder c2tnb239v2 = new X962NamedCurves.17();
   static X9ECParametersHolder c2tnb239v3 = new X962NamedCurves.18();
   static X9ECParametersHolder c2tnb359v1 = new X962NamedCurves.21();
   static X9ECParametersHolder c2tnb431r1 = new X962NamedCurves.23();
   static final Hashtable curves = new Hashtable();
   static final Hashtable names = new Hashtable();
   static final Hashtable objIds = new Hashtable();
   static X9ECParametersHolder prime192v1 = new X962NamedCurves.1();
   static X9ECParametersHolder prime192v2 = new X962NamedCurves.2();
   static X9ECParametersHolder prime192v3 = new X962NamedCurves.3();
   static X9ECParametersHolder prime239v1 = new X962NamedCurves.4();
   static X9ECParametersHolder prime239v2 = new X962NamedCurves.5();
   static X9ECParametersHolder prime239v3 = new X962NamedCurves.6();
   static X9ECParametersHolder prime256v1 = new X962NamedCurves.7();


   static {
      DERObjectIdentifier var0 = X9ObjectIdentifiers.prime192v1;
      X9ECParametersHolder var1 = prime192v1;
      defineCurve("prime192v1", var0, var1);
      DERObjectIdentifier var2 = X9ObjectIdentifiers.prime192v2;
      X9ECParametersHolder var3 = prime192v2;
      defineCurve("prime192v2", var2, var3);
      DERObjectIdentifier var4 = X9ObjectIdentifiers.prime192v3;
      X9ECParametersHolder var5 = prime192v3;
      defineCurve("prime192v3", var4, var5);
      DERObjectIdentifier var6 = X9ObjectIdentifiers.prime239v1;
      X9ECParametersHolder var7 = prime239v1;
      defineCurve("prime239v1", var6, var7);
      DERObjectIdentifier var8 = X9ObjectIdentifiers.prime239v2;
      X9ECParametersHolder var9 = prime239v2;
      defineCurve("prime239v2", var8, var9);
      DERObjectIdentifier var10 = X9ObjectIdentifiers.prime239v3;
      X9ECParametersHolder var11 = prime239v3;
      defineCurve("prime239v3", var10, var11);
      DERObjectIdentifier var12 = X9ObjectIdentifiers.prime256v1;
      X9ECParametersHolder var13 = prime256v1;
      defineCurve("prime256v1", var12, var13);
      DERObjectIdentifier var14 = X9ObjectIdentifiers.c2pnb163v1;
      X9ECParametersHolder var15 = c2pnb163v1;
      defineCurve("c2pnb163v1", var14, var15);
      DERObjectIdentifier var16 = X9ObjectIdentifiers.c2pnb163v2;
      X9ECParametersHolder var17 = c2pnb163v2;
      defineCurve("c2pnb163v2", var16, var17);
      DERObjectIdentifier var18 = X9ObjectIdentifiers.c2pnb163v3;
      X9ECParametersHolder var19 = c2pnb163v3;
      defineCurve("c2pnb163v3", var18, var19);
      DERObjectIdentifier var20 = X9ObjectIdentifiers.c2pnb176w1;
      X9ECParametersHolder var21 = c2pnb176w1;
      defineCurve("c2pnb176w1", var20, var21);
      DERObjectIdentifier var22 = X9ObjectIdentifiers.c2tnb191v1;
      X9ECParametersHolder var23 = c2tnb191v1;
      defineCurve("c2tnb191v1", var22, var23);
      DERObjectIdentifier var24 = X9ObjectIdentifiers.c2tnb191v2;
      X9ECParametersHolder var25 = c2tnb191v2;
      defineCurve("c2tnb191v2", var24, var25);
      DERObjectIdentifier var26 = X9ObjectIdentifiers.c2tnb191v3;
      X9ECParametersHolder var27 = c2tnb191v3;
      defineCurve("c2tnb191v3", var26, var27);
      DERObjectIdentifier var28 = X9ObjectIdentifiers.c2pnb208w1;
      X9ECParametersHolder var29 = c2pnb208w1;
      defineCurve("c2pnb208w1", var28, var29);
      DERObjectIdentifier var30 = X9ObjectIdentifiers.c2tnb239v1;
      X9ECParametersHolder var31 = c2tnb239v1;
      defineCurve("c2tnb239v1", var30, var31);
      DERObjectIdentifier var32 = X9ObjectIdentifiers.c2tnb239v2;
      X9ECParametersHolder var33 = c2tnb239v2;
      defineCurve("c2tnb239v2", var32, var33);
      DERObjectIdentifier var34 = X9ObjectIdentifiers.c2tnb239v3;
      X9ECParametersHolder var35 = c2tnb239v3;
      defineCurve("c2tnb239v3", var34, var35);
      DERObjectIdentifier var36 = X9ObjectIdentifiers.c2pnb272w1;
      X9ECParametersHolder var37 = c2pnb272w1;
      defineCurve("c2pnb272w1", var36, var37);
      DERObjectIdentifier var38 = X9ObjectIdentifiers.c2pnb304w1;
      X9ECParametersHolder var39 = c2pnb304w1;
      defineCurve("c2pnb304w1", var38, var39);
      DERObjectIdentifier var40 = X9ObjectIdentifiers.c2tnb359v1;
      X9ECParametersHolder var41 = c2tnb359v1;
      defineCurve("c2tnb359v1", var40, var41);
      DERObjectIdentifier var42 = X9ObjectIdentifiers.c2pnb368w1;
      X9ECParametersHolder var43 = c2pnb368w1;
      defineCurve("c2pnb368w1", var42, var43);
      DERObjectIdentifier var44 = X9ObjectIdentifiers.c2tnb431r1;
      X9ECParametersHolder var45 = c2tnb431r1;
      defineCurve("c2tnb431r1", var44, var45);
   }

   public X962NamedCurves() {}

   static void defineCurve(String var0, DERObjectIdentifier var1, X9ECParametersHolder var2) {
      objIds.put(var0, var1);
      names.put(var1, var0);
      curves.put(var1, var2);
   }

   public static X9ECParameters getByName(String var0) {
      Hashtable var1 = objIds;
      String var2 = Strings.toLowerCase(var0);
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
      X9ECParametersHolder var1 = (X9ECParametersHolder)curves.get(var0);
      X9ECParameters var2;
      if(var1 != null) {
         var2 = var1.getParameters();
      } else {
         var2 = null;
      }

      return var2;
   }

   public static String getName(DERObjectIdentifier var0) {
      return (String)names.get(var0);
   }

   public static Enumeration getNames() {
      return objIds.keys();
   }

   public static DERObjectIdentifier getOID(String var0) {
      Hashtable var1 = objIds;
      String var2 = Strings.toLowerCase(var0);
      return (DERObjectIdentifier)var1.get(var2);
   }

   static class 8 extends X9ECParametersHolder {

      8() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("0400000000000000000001E60FC8821CC74DAEAFC1", 16);
         BigInteger var2 = BigInteger.valueOf(2L);
         BigInteger var3 = new BigInteger("072546B5435234A422E0789675F432C89435DE5242", 16);
         BigInteger var4 = new BigInteger("00C9517D06D5240D3CFF38C74B20B6CD4D6F9DD4D9", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(163, 1, 2, 8, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("0307AF69989546103D79329FCC3D74880F33BBE803CB");
         ECPoint var7 = var5.decodePoint(var6);
         byte[] var8 = Hex.decode("D2COFB15760860DEF1EEF4D696E6768756151754");
         return new X9ECParameters(var5, var7, var1, var2, var8);
      }
   }

   static class 20 extends X9ECParametersHolder {

      20() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("0101D556572AABAC800101D556572AABAC8001022D5C91DD173F8FB561DA6899164443051D", 16);
         BigInteger var2 = BigInteger.valueOf(65070L);
         BigInteger var3 = new BigInteger("00FD0D693149A118F651E6DCE6802085377E5F882D1B510B44160074C1288078365A0396C8E681", 16);
         BigInteger var4 = new BigInteger("00BDDB97E555A50A908E43B01C798EA5DAA6788F1EA2794EFCF57166B8C14039601E55827340BE", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(304, 1, 2, 11, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("02197B07845E9BE2D96ADB0F5F3C7F2CFFBD7A3EB8B6FEC35C7FD67F26DDF6285A644F740A2614");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 9 extends X9ECParametersHolder {

      9() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("03FFFFFFFFFFFFFFFFFFFDF64DE1151ADBB78F10A7", 16);
         BigInteger var2 = BigInteger.valueOf(2L);
         BigInteger var3 = new BigInteger("0108B39E77C4B108BED981ED0E890E117C511CF072", 16);
         BigInteger var4 = new BigInteger("0667ACEB38AF4E488C407433FFAE4F1C811638DF20", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(163, 1, 2, 8, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("030024266E4EB5106D0A964D92C4860E2671DB9B6CC5");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 21 extends X9ECParametersHolder {

      21() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("01AF286BCA1AF286BCA1AF286BCA1AF286BCA1AF286BC9FB8F6B85C556892C20A7EB964FE7719E74F490758D3B", 16);
         BigInteger var2 = BigInteger.valueOf(76L);
         BigInteger var3 = new BigInteger("5667676A654B20754F356EA92017D946567C46675556F19556A04616B567D223A5E05656FB549016A96656A557", 16);
         BigInteger var4 = new BigInteger("2472E2D0197C49363F1FE7F5B6DB075D52B6947D135D8CA445805D39BC345626089687742B6329E70680231988", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(359, 68, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("033C258EF3047767E7EDE0F1FDAA79DAEE3841366A132E163ACED4ED2401DF9C6BDCDE98E8E707C07A2239B1B097");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 10 extends X9ECParametersHolder {

      10() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("03FFFFFFFFFFFFFFFFFFFE1AEE140F110AFF961309", 16);
         BigInteger var2 = BigInteger.valueOf(2L);
         BigInteger var3 = new BigInteger("07A526C63D3E25A256A007699F5447E32AE456B50E", 16);
         BigInteger var4 = new BigInteger("03F7061798EB99E238FD6F1BF95B48FEEB4854252B", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(163, 1, 2, 8, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("0202F9F87B7C574D0BDECF8A22E6524775F98CDEBDCB");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 4 extends X9ECParametersHolder {

      4() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839");
         BigInteger var2 = new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16);
         BigInteger var3 = new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("7fffffffffffffffffffffff7fffff9e5e9a9f5d9071fbd1522688909d0b", 16);
         BigInteger var8 = BigInteger.valueOf(1L);
         byte[] var9 = Hex.decode("e43bb460f0b80cc0c0b075798e948060f8321b7d");
         return new X9ECParameters(var4, var6, var7, var8, var9);
      }
   }

   static class 11 extends X9ECParametersHolder {

      11() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("010092537397ECA4F6145799D62B0A19CE06FE26AD", 16);
         BigInteger var2 = BigInteger.valueOf(65390L);
         BigInteger var3 = new BigInteger("00E4E6DB2995065C407D9D39B8D0967B96704BA8E9C90B", 16);
         BigInteger var4 = new BigInteger("005DDA470ABE6414DE8EC133AE28E9BBD7FCEC0AE0FFF2", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(176, 1, 2, 43, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("038D16C2866798B600F9F08BB4A8E860F3298CE04A5798");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 5 extends X9ECParametersHolder {

      5() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839");
         BigInteger var2 = new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16);
         BigInteger var3 = new BigInteger("617fab6832576cbbfed50d99f0249c3fee58b94ba0038c7ae84c8c832f2c", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("0238af09d98727705120c921bb5e9e26296a3cdcf2f35757a0eafd87b830e7");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("7fffffffffffffffffffffff800000cfa7e8594377d414c03821bc582063", 16);
         BigInteger var8 = BigInteger.valueOf(1L);
         byte[] var9 = Hex.decode("e8b4011604095303ca3b8099982be09fcb9ae616");
         return new X9ECParameters(var4, var6, var7, var8, var9);
      }
   }

   static class 12 extends X9ECParametersHolder {

      12() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("40000000000000000000000004A20E90C39067C893BBB9A5", 16);
         BigInteger var2 = BigInteger.valueOf(2L);
         BigInteger var3 = new BigInteger("2866537B676752636A68F56554E12640276B649EF7526267", 16);
         BigInteger var4 = new BigInteger("2E45EF571F00786F67B0081B9495A3D95462F5DE0AA185EC", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(191, 9, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("0236B3DAF8A23206F9C4F299D7B21A9C369137F2C84AE1AA0D");
         ECPoint var7 = var5.decodePoint(var6);
         byte[] var8 = Hex.decode("4E13CA542744D696E67687561517552F279A8C84");
         return new X9ECParameters(var5, var7, var1, var2, var8);
      }
   }

   static class 6 extends X9ECParametersHolder {

      6() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839");
         BigInteger var2 = new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16);
         BigInteger var3 = new BigInteger("255705fa2a306654b1f4cb03d6a750a30c250102d4988717d9ba15ab6d3e", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("036768ae8e18bb92cfcf005c949aa2c6d94853d0e660bbf854b1c9505fe95a");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("7fffffffffffffffffffffff7fffff975deb41b3a6057c3c432146526551", 16);
         BigInteger var8 = BigInteger.valueOf(1L);
         byte[] var9 = Hex.decode("7d7374168ffe3471b60a857686a19475d3bfa2ff");
         return new X9ECParameters(var4, var6, var7, var8, var9);
      }
   }

   static class 22 extends X9ECParametersHolder {

      22() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("010090512DA9AF72B08349D98A5DD4C7B0532ECA51CE03E2D10F3B7AC579BD87E909AE40A6F131E9CFCE5BD967", 16);
         BigInteger var2 = BigInteger.valueOf(65392L);
         BigInteger var3 = new BigInteger("00E0D2EE25095206F5E2A4F9ED229F1F256E79A0E2B455970D8D0D865BD94778C576D62F0AB7519CCD2A1A906AE30D", 16);
         BigInteger var4 = new BigInteger("00FC1217D4320A90452C760A58EDCD30C8DD069B3C34453837A34ED50CB54917E1C2112D84D164F444F8F74786046A", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(368, 1, 2, 85, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("021085E2755381DCCCE3C1557AFA10C2F0C0C2825646C5B34A394CBCFA8BC16B22E7E789E927BE216F02E1FB136A5F");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 13 extends X9ECParametersHolder {

      13() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("20000000000000000000000050508CB89F652824E06B8173", 16);
         BigInteger var2 = BigInteger.valueOf(4L);
         BigInteger var3 = new BigInteger("401028774D7777C7B7666D1366EA432071274F89FF01E718", 16);
         BigInteger var4 = new BigInteger("0620048D28BCBD03B6249C99182B7C8CD19700C362C46A01", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(191, 9, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("023809B2B7CC1B28CC5A87926AAD83FD28789E81E2C9E3BF10");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 7 extends X9ECParametersHolder {

      7() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951");
         BigInteger var2 = new BigInteger("ffffffff00000001000000000000000000000000fffffffffffffffffffffffc", 16);
         BigInteger var3 = new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("036b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551", 16);
         BigInteger var8 = BigInteger.valueOf(1L);
         byte[] var9 = Hex.decode("c49d360886e704936a6678e1139d26b7819f7e90");
         return new X9ECParameters(var4, var6, var7, var8, var9);
      }
   }

   static class 14 extends X9ECParametersHolder {

      14() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("155555555555555555555555610C0B196812BFB6288A3EA3", 16);
         BigInteger var2 = BigInteger.valueOf(6L);
         BigInteger var3 = new BigInteger("6C01074756099122221056911C77D77E77A777E7E7E77FCB", 16);
         BigInteger var4 = new BigInteger("71FE1AF926CF847989EFEF8DB459F66394D90F32AD3F15E8", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(191, 9, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("03375D4CE24FDE434489DE8746E71786015009E66E38A926DD");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 23 extends X9ECParametersHolder {

      23() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("0340340340340340340340340340340340340340340340340340340323C313FAB50589703B5EC68D3587FEC60D161CC149C1AD4A91", 16);
         BigInteger var2 = BigInteger.valueOf(10080L);
         BigInteger var3 = new BigInteger("1A827EF00DD6FC0E234CAF046C6A5D8A85395B236CC4AD2CF32A0CADBDC9DDF620B0EB9906D0957F6C6FEACD615468DF104DE296CD8F", 16);
         BigInteger var4 = new BigInteger("10D9B4A3D9047D8B154359ABFB1B7F5485B04CEB868237DDC9DEDA982A679A5A919B626D4E50A8DD731B107A9962381FB5D807BF2618", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(431, 120, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("02120FC05D3C67A99DE161D2F4092622FECA701BE4F50F4758714E8A87BBF2A658EF8C21E7C5EFE965361F6C2999C0C247B0DBD70CE6B7");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 1 extends X9ECParametersHolder {

      1() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
         BigInteger var2 = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);
         BigInteger var3 = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("03188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("ffffffffffffffffffffffff99def836146bc9b1b4d22831", 16);
         BigInteger var8 = BigInteger.valueOf(1L);
         byte[] var9 = Hex.decode("3045AE6FC8422f64ED579528D38120EAE12196D5");
         return new X9ECParameters(var4, var6, var7, var8, var9);
      }
   }

   static class 2 extends X9ECParametersHolder {

      2() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
         BigInteger var2 = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);
         BigInteger var3 = new BigInteger("cc22d6dfb95c6b25e49c0d6364a4e5980c393aa21668d953", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("03eea2bae7e1497842f2de7769cfe9c989c072ad696f48034a");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("fffffffffffffffffffffffe5fb1a724dc80418648d8dd31", 16);
         BigInteger var8 = BigInteger.valueOf(1L);
         byte[] var9 = Hex.decode("31a92ee2029fd10d901b113e990710f0d21ac6b6");
         return new X9ECParameters(var4, var6, var7, var8, var9);
      }
   }

   static class 3 extends X9ECParametersHolder {

      3() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
         BigInteger var2 = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);
         BigInteger var3 = new BigInteger("22123dc2395a05caa7423daeccc94760a7d462256bd56916", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("027d29778100c65a1da1783716588dce2b8b4aee8e228f1896");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("ffffffffffffffffffffffff7a62d031c83f4294f640ec13", 16);
         BigInteger var8 = BigInteger.valueOf(1L);
         byte[] var9 = Hex.decode("c469684435deb378c4b65ca9591e2a5763059a2e");
         return new X9ECParameters(var4, var6, var7, var8, var9);
      }
   }

   static class 18 extends X9ECParametersHolder {

      18() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("0CCCCCCCCCCCCCCCCCCCCCCCCCCCCCAC4912D2D9DF903EF9888B8A0E4CFF", 16);
         BigInteger var2 = BigInteger.valueOf(10L);
         BigInteger var3 = new BigInteger("01238774666A67766D6676F778E676B66999176666E687666D8766C66A9F", 16);
         BigInteger var4 = new BigInteger("6A941977BA9F6A435199ACFC51067ED587F519C5ECB541B8E44111DE1D40", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(239, 36, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("0370F6E9D04D289C4E89913CE3530BFDE903977D42B146D539BF1BDE4E9C92");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 17 extends X9ECParametersHolder {

      17() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("1555555555555555555555555555553C6F2885259C31E3FCDF154624522D", 16);
         BigInteger var2 = BigInteger.valueOf(6L);
         BigInteger var3 = new BigInteger("4230017757A767FAE42398569B746325D45313AF0766266479B75654E65F", 16);
         BigInteger var4 = new BigInteger("5037EA654196CFF0CD82B2C14A2FCF2E3FF8775285B545722F03EACDB74B", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(239, 36, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("0228F9D04E900069C8DC47A08534FE76D2B900B7D7EF31F5709F200C4CA205");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 16 extends X9ECParametersHolder {

      16() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("2000000000000000000000000000000F4D42FFE1492A4993F1CAD666E447", 16);
         BigInteger var2 = BigInteger.valueOf(4L);
         BigInteger var3 = new BigInteger("32010857077C5431123A46B808906756F543423E8D27877578125778AC76", 16);
         BigInteger var4 = new BigInteger("790408F2EEDAF392B012EDEFB3392F30F4327C0CA3F31FC383C422AA8C16", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(239, 36, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("0257927098FA932E7C0A96D3FD5B706EF7E5F5C156E16B7E7C86038552E91D");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 15 extends X9ECParametersHolder {

      15() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("0101BAF95C9723C57B6C21DA2EFF2D5ED588BDD5717E212F9D", 16);
         BigInteger var2 = BigInteger.valueOf(65096L);
         BigInteger var3 = new BigInteger("0", 16);
         BigInteger var4 = new BigInteger("00C8619ED45A62E6212E1160349E2BFA844439FAFC2A3FD1638F9E", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(208, 1, 2, 83, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("0289FDFBE4ABE193DF9559ECF07AC0CE78554E2784EB8C1ED1A57A");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }

   static class 19 extends X9ECParametersHolder {

      19() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("0100FAF51354E0E39E4892DF6E319C72C8161603FA45AA7B998A167B8F1E629521", 16);
         BigInteger var2 = BigInteger.valueOf(65286L);
         BigInteger var3 = new BigInteger("0091A091F03B5FBA4AB2CCF49C4EDD220FB028712D42BE752B2C40094DBACDB586FB20", 16);
         BigInteger var4 = new BigInteger("7167EFC92BB2E3CE7C8AAAFF34E12A9C557003D7C73A6FAF003F99F6CC8482E540F7", 16);
         ECCurve.F2m var5 = new ECCurve.F2m(272, 1, 3, 56, var3, var4, var1, var2);
         byte[] var6 = Hex.decode("026108BABB2CEEBCF787058A056CBE0CFE622D7723A289E08A07AE13EF0D10D171DD8D");
         ECPoint var7 = var5.decodePoint(var6);
         return new X9ECParameters(var5, var7, var1, var2, (byte[])null);
      }
   }
}

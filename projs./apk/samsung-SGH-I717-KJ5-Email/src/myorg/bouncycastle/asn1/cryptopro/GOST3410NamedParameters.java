package myorg.bouncycastle.asn1.cryptopro;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import myorg.bouncycastle.asn1.cryptopro.GOST3410ParamSetParameters;

public class GOST3410NamedParameters {

   private static GOST3410ParamSetParameters cryptoProA;
   private static GOST3410ParamSetParameters cryptoProB;
   private static GOST3410ParamSetParameters cryptoProXchA;
   static final Hashtable names = new Hashtable();
   static final Hashtable objIds = new Hashtable();
   static final Hashtable params = new Hashtable();


   static {
      BigInteger var0 = new BigInteger("127021248288932417465907042777176443525787653508916535812817507265705031260985098497423188333483401180925999995120988934130659205614996724254121049274349357074920312769561451689224110579311248812610229678534638401693520013288995000362260684222750813532307004517341633685004541062586971416883686778842537820383");
      BigInteger var1 = new BigInteger("68363196144955700784444165611827252895102170888761442055095051287550314083023");
      BigInteger var2 = new BigInteger("100997906755055304772081815535925224869841082572053457874823515875577147990529272777244152852699298796483356699682842027972896052747173175480590485607134746852141928680912561502802222185647539190902656116367847270145019066794290930185446216399730872221732889830323194097355403213400972588322876850946740663962");
      cryptoProA = new GOST3410ParamSetParameters(1024, var0, var1, var2);
      BigInteger var3 = new BigInteger("139454871199115825601409655107690713107041707059928031797758001454375765357722984094124368522288239833039114681648076688236921220737322672160740747771700911134550432053804647694904686120113087816240740184800477047157336662926249423571248823968542221753660143391485680840520336859458494803187341288580489525163");
      BigInteger var4 = new BigInteger("79885141663410976897627118935756323747307951916507639758300472692338873533959");
      BigInteger var5 = new BigInteger("42941826148615804143873447737955502392672345968607143066798112994089471231420027060385216699563848719957657284814898909770759462613437669456364882730370838934791080835932647976778601915343474400961034231316672578686920482194932878633360203384797092684342247621055760235016132614780652761028509445403338652341");
      cryptoProB = new GOST3410ParamSetParameters(1024, var3, var4, var5);
      BigInteger var6 = new BigInteger("142011741597563481196368286022318089743276138395243738762872573441927459393512718973631166078467600360848946623567625795282774719212241929071046134208380636394084512691828894000571524625445295769349356752728956831541775441763139384457191755096847107846595662547942312293338483924514339614727760681880609734239");
      BigInteger var7 = new BigInteger("91771529896554605945588149018382750217296858393520724172743325725474374979801");
      BigInteger var8 = new BigInteger("133531813272720673433859519948319001217942375967847486899482359599369642528734712461590403327731821410328012529253871914788598993103310567744136196364803064721377826656898686468463277710150809401182608770201615324990468332931294920912776241137878030224355746606283971659376426832674269780880061631528163475887");
      cryptoProXchA = new GOST3410ParamSetParameters(1024, var6, var7, var8);
      Hashtable var9 = params;
      DERObjectIdentifier var10 = CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_A;
      GOST3410ParamSetParameters var11 = cryptoProA;
      var9.put(var10, var11);
      Hashtable var13 = params;
      DERObjectIdentifier var14 = CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_B;
      GOST3410ParamSetParameters var15 = cryptoProB;
      var13.put(var14, var15);
      Hashtable var17 = params;
      DERObjectIdentifier var18 = CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_XchA;
      GOST3410ParamSetParameters var19 = cryptoProXchA;
      var17.put(var18, var19);
      Hashtable var21 = objIds;
      DERObjectIdentifier var22 = CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_A;
      var21.put("GostR3410-94-CryptoPro-A", var22);
      Hashtable var24 = objIds;
      DERObjectIdentifier var25 = CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_B;
      var24.put("GostR3410-94-CryptoPro-B", var25);
      Hashtable var27 = objIds;
      DERObjectIdentifier var28 = CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_XchA;
      var27.put("GostR3410-94-CryptoPro-XchA", var28);
   }

   public GOST3410NamedParameters() {}

   public static GOST3410ParamSetParameters getByName(String var0) {
      DERObjectIdentifier var1 = (DERObjectIdentifier)objIds.get(var0);
      GOST3410ParamSetParameters var2;
      if(var1 != null) {
         var2 = (GOST3410ParamSetParameters)params.get(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public static GOST3410ParamSetParameters getByOID(DERObjectIdentifier var0) {
      return (GOST3410ParamSetParameters)params.get(var0);
   }

   public static Enumeration getNames() {
      return objIds.keys();
   }

   public static DERObjectIdentifier getOID(String var0) {
      return (DERObjectIdentifier)objIds.get(var0);
   }
}

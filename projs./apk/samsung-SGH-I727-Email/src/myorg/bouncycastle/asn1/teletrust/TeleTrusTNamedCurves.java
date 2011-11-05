package myorg.bouncycastle.asn1.teletrust;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import myorg.bouncycastle.asn1.x9.X9ECParameters;
import myorg.bouncycastle.asn1.x9.X9ECParametersHolder;
import myorg.bouncycastle.math.ec.ECCurve;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.util.Strings;
import myorg.bouncycastle.util.encoders.Hex;

public class TeleTrusTNamedCurves {

   static X9ECParametersHolder brainpoolP160r1 = new TeleTrusTNamedCurves.1();
   static X9ECParametersHolder brainpoolP160t1 = new TeleTrusTNamedCurves.2();
   static X9ECParametersHolder brainpoolP192r1 = new TeleTrusTNamedCurves.3();
   static X9ECParametersHolder brainpoolP192t1 = new TeleTrusTNamedCurves.4();
   static X9ECParametersHolder brainpoolP224r1 = new TeleTrusTNamedCurves.5();
   static X9ECParametersHolder brainpoolP224t1 = new TeleTrusTNamedCurves.6();
   static X9ECParametersHolder brainpoolP256r1 = new TeleTrusTNamedCurves.7();
   static X9ECParametersHolder brainpoolP256t1 = new TeleTrusTNamedCurves.8();
   static X9ECParametersHolder brainpoolP320r1 = new TeleTrusTNamedCurves.9();
   static X9ECParametersHolder brainpoolP320t1 = new TeleTrusTNamedCurves.10();
   static X9ECParametersHolder brainpoolP384r1 = new TeleTrusTNamedCurves.11();
   static X9ECParametersHolder brainpoolP384t1 = new TeleTrusTNamedCurves.12();
   static X9ECParametersHolder brainpoolP512r1 = new TeleTrusTNamedCurves.13();
   static X9ECParametersHolder brainpoolP512t1 = new TeleTrusTNamedCurves.14();
   static final Hashtable curves = new Hashtable();
   static final Hashtable names = new Hashtable();
   static final Hashtable objIds = new Hashtable();


   static {
      DERObjectIdentifier var0 = TeleTrusTObjectIdentifiers.brainpoolP160r1;
      X9ECParametersHolder var1 = brainpoolP160r1;
      defineCurve("brainpoolp160r1", var0, var1);
      DERObjectIdentifier var2 = TeleTrusTObjectIdentifiers.brainpoolP160t1;
      X9ECParametersHolder var3 = brainpoolP160t1;
      defineCurve("brainpoolp160t1", var2, var3);
      DERObjectIdentifier var4 = TeleTrusTObjectIdentifiers.brainpoolP192r1;
      X9ECParametersHolder var5 = brainpoolP192r1;
      defineCurve("brainpoolp192r1", var4, var5);
      DERObjectIdentifier var6 = TeleTrusTObjectIdentifiers.brainpoolP192t1;
      X9ECParametersHolder var7 = brainpoolP192t1;
      defineCurve("brainpoolp192t1", var6, var7);
      DERObjectIdentifier var8 = TeleTrusTObjectIdentifiers.brainpoolP224r1;
      X9ECParametersHolder var9 = brainpoolP224r1;
      defineCurve("brainpoolp224r1", var8, var9);
      DERObjectIdentifier var10 = TeleTrusTObjectIdentifiers.brainpoolP224t1;
      X9ECParametersHolder var11 = brainpoolP224t1;
      defineCurve("brainpoolp224t1", var10, var11);
      DERObjectIdentifier var12 = TeleTrusTObjectIdentifiers.brainpoolP256r1;
      X9ECParametersHolder var13 = brainpoolP256r1;
      defineCurve("brainpoolp256r1", var12, var13);
      DERObjectIdentifier var14 = TeleTrusTObjectIdentifiers.brainpoolP256t1;
      X9ECParametersHolder var15 = brainpoolP256t1;
      defineCurve("brainpoolp256t1", var14, var15);
      DERObjectIdentifier var16 = TeleTrusTObjectIdentifiers.brainpoolP320r1;
      X9ECParametersHolder var17 = brainpoolP320r1;
      defineCurve("brainpoolp320r1", var16, var17);
      DERObjectIdentifier var18 = TeleTrusTObjectIdentifiers.brainpoolP320t1;
      X9ECParametersHolder var19 = brainpoolP320t1;
      defineCurve("brainpoolp320t1", var18, var19);
      DERObjectIdentifier var20 = TeleTrusTObjectIdentifiers.brainpoolP384r1;
      X9ECParametersHolder var21 = brainpoolP384r1;
      defineCurve("brainpoolp384r1", var20, var21);
      DERObjectIdentifier var22 = TeleTrusTObjectIdentifiers.brainpoolP384t1;
      X9ECParametersHolder var23 = brainpoolP384t1;
      defineCurve("brainpoolp384t1", var22, var23);
      DERObjectIdentifier var24 = TeleTrusTObjectIdentifiers.brainpoolP512r1;
      X9ECParametersHolder var25 = brainpoolP512r1;
      defineCurve("brainpoolp512r1", var24, var25);
      DERObjectIdentifier var26 = TeleTrusTObjectIdentifiers.brainpoolP512t1;
      X9ECParametersHolder var27 = brainpoolP512t1;
      defineCurve("brainpoolp512t1", var26, var27);
   }

   public TeleTrusTNamedCurves() {}

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

   public static DERObjectIdentifier getOID(short var0, boolean var1) {
      StringBuilder var2 = (new StringBuilder()).append("brainpoolP").append(var0);
      String var3;
      if(var1) {
         var3 = "t";
      } else {
         var3 = "r";
      }

      return getOID(var2.append(var3).append("1").toString());
   }

   static class 10 extends X9ECParametersHolder {

      10() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("D35E472036BC4FB7E13C785ED201E065F98FCFA6F6F40DEF4F92B9EC7893EC28FCD412B1F1B32E27", 16);
         BigInteger var2 = new BigInteger("D35E472036BC4FB7E13C785ED201E065F98FCFA6F6F40DEF4F92B9EC7893EC28FCD412B1F1B32E24", 16);
         BigInteger var3 = new BigInteger("A7F561E038EB1ED560B3D147DB782013064C19F27ED27C6780AAF77FB8A547CEB5B4FEF422340353", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("04925BE9FB01AFC6FB4D3E7D4990010F813408AB106C4F09CB7EE07868CC136FFF3357F624A21BED5263BA3A7A27483EBF6671DBEF7ABB30EBEE084E58A0B077AD42A5A0989D1EE71B1B9BC0455FB0D2C3");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("D35E472036BC4FB7E13C785ED201E065F98FCFA5B68F12A32D482EC7EE8658E98691555B44C59311", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 12 extends X9ECParametersHolder {

      12() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B412B1DA197FB71123ACD3A729901D1A71874700133107EC53", 16);
         BigInteger var2 = new BigInteger("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B412B1DA197FB71123ACD3A729901D1A71874700133107EC50", 16);
         BigInteger var3 = new BigInteger("7F519EADA7BDA81BD826DBA647910F8C4B9346ED8CCDC64E4B1ABD11756DCE1D2074AA263B88805CED70355A33B471EE", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("0418DE98B02DB9A306F2AFCD7235F72A819B80AB12EBD653172476FECD462AABFFC4FF191B946A5F54D8D0AA2F418808CC25AB056962D30651A114AFD2755AD336747F93475B7A1FCA3B88F2B6A208CCFE469408584DC2B2912675BF5B9E582928");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B31F166E6CAC0425A7CF3AB6AF6B7FC3103B883202E9046565", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 11 extends X9ECParametersHolder {

      11() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B412B1DA197FB71123ACD3A729901D1A71874700133107EC53", 16);
         BigInteger var2 = new BigInteger("7BC382C63D8C150C3C72080ACE05AFA0C2BEA28E4FB22787139165EFBA91F90F8AA5814A503AD4EB04A8C7DD22CE2826", 16);
         BigInteger var3 = new BigInteger("4A8C7DD22CE28268B39B55416F0447C2FB77DE107DCD2A62E880EA53EEB62D57CB4390295DBC9943AB78696FA504C11", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("041D1C64F068CF45FFA2A63A81B7C13F6B8847A3E77EF14FE3DB7FCAFE0CBD10E8E826E03436D646AAEF87B2E247D4AF1E8ABE1D7520F9C2A45CB1EB8E95CFD55262B70B29FEEC5864E19C054FF99129280E4646217791811142820341263C5315");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("8CB91E82A3386D280F5D6F7E50E641DF152F7109ED5456B31F166E6CAC0425A7CF3AB6AF6B7FC3103B883202E9046565", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 3 extends X9ECParametersHolder {

      3() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("C302F41D932A36CDA7A3463093D18DB78FCE476DE1A86297", 16);
         BigInteger var2 = new BigInteger("6A91174076B1E0E19C39C031FE8685C1CAE040E5C69A28EF", 16);
         BigInteger var3 = new BigInteger("469A28EF7C28CCA3DC721D044F4496BCCA7EF4146FBF25C9", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("04C0A0647EAAB6A48753B033C56CB0F0900A2F5C4853375FD614B690866ABD5BB88B5F4828C1490002E6773FA2FA299B8F");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("C302F41D932A36CDA7A3462F9E9E916B5BE8F1029AC4ACC1", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 2 extends X9ECParametersHolder {

      2() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("E95E4A5F737059DC60DFC7AD95B3D8139515620F", 16);
         BigInteger var2 = new BigInteger("E95E4A5F737059DC60DFC7AD95B3D8139515620C", 16);
         BigInteger var3 = new BigInteger("7A556B6DAE535B7B51ED2C4D7DAA7A0B5C55F380", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("04B199B13B9B34EFC1397E64BAEB05ACC265FF2378ADD6718B7C7C1961F0991B842443772152C9E0AD");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("E95E4A5F737059DC60DF5991D45029409E60FC09", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 5 extends X9ECParametersHolder {

      5() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FF", 16);
         BigInteger var2 = new BigInteger("68A5E62CA9CE6C1C299803A6C1530B514E182AD8B0042A59CAD29F43", 16);
         BigInteger var3 = new BigInteger("2580F63CCFE44138870713B1A92369E33E2135D266DBB372386C400B", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("040D9029AD2C7E5CF4340823B2A87DC68C9E4CE3174C1E6EFDEE12C07D58AA56F772C0726F24C6B89E4ECDAC24354B9E99CAA3F6D3761402CD");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("D7C134AA264366862A18302575D0FB98D116BC4B6DDEBCA3A5A7939F", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 4 extends X9ECParametersHolder {

      4() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("C302F41D932A36CDA7A3463093D18DB78FCE476DE1A86297", 16);
         BigInteger var2 = new BigInteger("C302F41D932A36CDA7A3463093D18DB78FCE476DE1A86294", 16);
         BigInteger var3 = new BigInteger("13D56FFAEC78681E68F9DEB43B35BEC2FB68542E27897B79", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("043AE9E58C82F63C30282E1FE7BBF43FA72C446AF6F4618129097E2C5667C2223A902AB5CA449D0084B7E5B3DE7CCC01C9");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("C302F41D932A36CDA7A3462F9E9E916B5BE8F1029AC4ACC1", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 1 extends X9ECParametersHolder {

      1() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("E95E4A5F737059DC60DFC7AD95B3D8139515620F", 16);
         BigInteger var2 = new BigInteger("340E7BE2A280EB74E2BE61BADA745D97E8F7C300", 16);
         BigInteger var3 = new BigInteger("1E589A8595423412134FAA2DBDEC95C8D8675E58", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("04BED5AF16EA3F6A4F62938C4631EB5AF7BDBCDBC31667CB477A1A8EC338F94741669C976316DA6321");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("E95E4A5F737059DC60DF5991D45029409E60FC09", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 7 extends X9ECParametersHolder {

      7() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("A9FB57DBA1EEA9BC3E660A909D838D726E3BF623D52620282013481D1F6E5377", 16);
         BigInteger var2 = new BigInteger("7D5A0975FC2C3057EEF67530417AFFE7FB8055C126DC5C6CE94A4B44F330B5D9", 16);
         BigInteger var3 = new BigInteger("26DC5C6CE94A4B44F330B5D9BBD77CBF958416295CF7E1CE6BCCDC18FF8C07B6", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("048BD2AEB9CB7E57CB2C4B482FFC81B7AFB9DE27E1E3BD23C23A4453BD9ACE3262547EF835C3DAC4FD97F8461A14611DC9C27745132DED8E545C1D54C72F046997");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("A9FB57DBA1EEA9BC3E660A909D838D718C397AA3B561A6F7901E0E82974856A7", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 6 extends X9ECParametersHolder {

      6() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FF", 16);
         BigInteger var2 = new BigInteger("D7C134AA264366862A18302575D1D787B09F075797DA89F57EC8C0FC", 16);
         BigInteger var3 = new BigInteger("4B337D934104CD7BEF271BF60CED1ED20DA14C08B3BB64F18A60888D", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("046AB1E344CE25FF3896424E7FFE14762ECB49F8928AC0C76029B4D5800374E9F5143E568CD23F3F4D7C0D4B1E41C8CC0D1C6ABD5F1A46DB4C");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("D7C134AA264366862A18302575D0FB98D116BC4B6DDEBCA3A5A7939F", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 13 extends X9ECParametersHolder {

      13() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA703308717D4D9B009BC66842AECDA12AE6A380E62881FF2F2D82C68528AA6056583A48F3", 16);
         BigInteger var2 = new BigInteger("7830A3318B603B89E2327145AC234CC594CBDD8D3DF91610A83441CAEA9863BC2DED5D5AA8253AA10A2EF1C98B9AC8B57F1117A72BF2C7B9E7C1AC4D77FC94CA", 16);
         BigInteger var3 = new BigInteger("3DF91610A83441CAEA9863BC2DED5D5AA8253AA10A2EF1C98B9AC8B57F1117A72BF2C7B9E7C1AC4D77FC94CADC083E67984050B75EBAE5DD2809BD638016F723", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("0481AEE4BDD82ED9645A21322E9C4C6A9385ED9F70B5D916C1B43B62EEF4D0098EFF3B1F78E2D0D48D50D1687B93B97D5F7C6D5047406A5E688B352209BCB9F8227DDE385D566332ECC0EABFA9CF7822FDF209F70024A57B1AA000C55B881F8111B2DCDE494A5F485E5BCA4BD88A2763AED1CA2B2FA8F0540678CD1E0F3AD80892");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA70330870553E5C414CA92619418661197FAC10471DB1D381085DDADDB58796829CA90069", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 9 extends X9ECParametersHolder {

      9() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("D35E472036BC4FB7E13C785ED201E065F98FCFA6F6F40DEF4F92B9EC7893EC28FCD412B1F1B32E27", 16);
         BigInteger var2 = new BigInteger("3EE30B568FBAB0F883CCEBD46D3F3BB8A2A73513F5EB79DA66190EB085FFA9F492F375A97D860EB4", 16);
         BigInteger var3 = new BigInteger("520883949DFDBC42D3AD198640688A6FE13F41349554B49ACC31DCCD884539816F5EB4AC8FB1F1A6", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("0443BD7E9AFB53D8B85289BCC48EE5BFE6F20137D10A087EB6E7871E2A10A599C710AF8D0D39E2061114FDD05545EC1CC8AB4093247F77275E0743FFED117182EAA9C77877AAAC6AC7D35245D1692E8EE1");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("D35E472036BC4FB7E13C785ED201E065F98FCFA5B68F12A32D482EC7EE8658E98691555B44C59311", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 8 extends X9ECParametersHolder {

      8() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("A9FB57DBA1EEA9BC3E660A909D838D726E3BF623D52620282013481D1F6E5377", 16);
         BigInteger var2 = new BigInteger("A9FB57DBA1EEA9BC3E660A909D838D726E3BF623D52620282013481D1F6E5374", 16);
         BigInteger var3 = new BigInteger("662C61C430D84EA4FE66A7733D0B76B7BF93EBC4AF2F49256AE58101FEE92B04", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("04A3E8EB3CC1CFE7B7732213B23A656149AFA142C47AAFBC2B79A191562E1305F42D996C823439C56D7F7B22E14644417E69BCB6DE39D027001DABE8F35B25C9BE");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("A9FB57DBA1EEA9BC3E660A909D838D718C397AA3B561A6F7901E0E82974856A7", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }

   static class 14 extends X9ECParametersHolder {

      14() {}

      protected X9ECParameters createParameters() {
         BigInteger var1 = new BigInteger("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA703308717D4D9B009BC66842AECDA12AE6A380E62881FF2F2D82C68528AA6056583A48F3", 16);
         BigInteger var2 = new BigInteger("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA703308717D4D9B009BC66842AECDA12AE6A380E62881FF2F2D82C68528AA6056583A48F0", 16);
         BigInteger var3 = new BigInteger("7CBBBCF9441CFAB76E1890E46884EAE321F70C0BCB4981527897504BEC3E36A62BCDFA2304976540F6450085F2DAE145C22553B465763689180EA2571867423E", 16);
         ECCurve.Fp var4 = new ECCurve.Fp(var1, var2, var3);
         byte[] var5 = Hex.decode("04640ECE5C12788717B9C1BA06CBC2A6FEBA85842458C56DDE9DB1758D39C0313D82BA51735CDB3EA499AA77A7D6943A64F7A3F25FE26F06B51BAA2696FA9035DA5B534BD595F5AF0FA2C892376C84ACE1BB4E3019B71634C01131159CAE03CEE9D9932184BEEF216BD71DF2DADF86A627306ECFF96DBB8BACE198B61E00F8B332");
         ECPoint var6 = var4.decodePoint(var5);
         BigInteger var7 = new BigInteger("AADD9DB8DBE9C48B3FD4E6AE33C9FC07CB308DB3B3C9D20ED6639CCA70330870553E5C414CA92619418661197FAC10471DB1D381085DDADDB58796829CA90069", 16);
         BigInteger var8 = new BigInteger("01", 16);
         return new X9ECParameters(var4, var6, var7, var8);
      }
   }
}

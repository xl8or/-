package myorg.bouncycastle.crypto.engines;

import java.lang.reflect.Array;
import myorg.bouncycastle.crypto.BlockCipher;
import myorg.bouncycastle.crypto.CipherParameters;
import myorg.bouncycastle.crypto.DataLengthException;
import myorg.bouncycastle.crypto.params.KeyParameter;

public class AESEngine implements BlockCipher {

   private static final int BLOCK_SIZE = 16;
   private static final byte[] S = new byte[]{(byte)99, (byte)124, (byte)119, (byte)123, (byte)242, (byte)107, (byte)111, (byte)197, (byte)48, (byte)1, (byte)103, (byte)43, (byte)254, (byte)215, (byte)171, (byte)118, (byte)202, (byte)130, (byte)201, (byte)125, (byte)250, (byte)89, (byte)71, (byte)240, (byte)173, (byte)212, (byte)162, (byte)175, (byte)156, (byte)164, (byte)114, (byte)192, (byte)183, (byte)253, (byte)147, (byte)38, (byte)54, (byte)63, (byte)247, (byte)204, (byte)52, (byte)165, (byte)229, (byte)241, (byte)113, (byte)216, (byte)49, (byte)21, (byte)4, (byte)199, (byte)35, (byte)195, (byte)24, (byte)150, (byte)5, (byte)154, (byte)7, (byte)18, (byte)128, (byte)226, (byte)235, (byte)39, (byte)178, (byte)117, (byte)9, (byte)131, (byte)44, (byte)26, (byte)27, (byte)110, (byte)90, (byte)160, (byte)82, (byte)59, (byte)214, (byte)179, (byte)41, (byte)227, (byte)47, (byte)132, (byte)83, (byte)209, (byte)0, (byte)237, (byte)32, (byte)252, (byte)177, (byte)91, (byte)106, (byte)203, (byte)190, (byte)57, (byte)74, (byte)76, (byte)88, (byte)207, (byte)208, (byte)239, (byte)170, (byte)251, (byte)67, (byte)77, (byte)51, (byte)133, (byte)69, (byte)249, (byte)2, (byte)127, (byte)80, (byte)60, (byte)159, (byte)168, (byte)81, (byte)163, (byte)64, (byte)143, (byte)146, (byte)157, (byte)56, (byte)245, (byte)188, (byte)182, (byte)218, (byte)33, (byte)16, (byte)255, (byte)243, (byte)210, (byte)205, (byte)12, (byte)19, (byte)236, (byte)95, (byte)151, (byte)68, (byte)23, (byte)196, (byte)167, (byte)126, (byte)61, (byte)100, (byte)93, (byte)25, (byte)115, (byte)96, (byte)129, (byte)79, (byte)220, (byte)34, (byte)42, (byte)144, (byte)136, (byte)70, (byte)238, (byte)184, (byte)20, (byte)222, (byte)94, (byte)11, (byte)219, (byte)224, (byte)50, (byte)58, (byte)10, (byte)73, (byte)6, (byte)36, (byte)92, (byte)194, (byte)211, (byte)172, (byte)98, (byte)145, (byte)149, (byte)228, (byte)121, (byte)231, (byte)200, (byte)55, (byte)109, (byte)141, (byte)213, (byte)78, (byte)169, (byte)108, (byte)86, (byte)244, (byte)234, (byte)101, (byte)122, (byte)174, (byte)8, (byte)186, (byte)120, (byte)37, (byte)46, (byte)28, (byte)166, (byte)180, (byte)198, (byte)232, (byte)221, (byte)116, (byte)31, (byte)75, (byte)189, (byte)139, (byte)138, (byte)112, (byte)62, (byte)181, (byte)102, (byte)72, (byte)3, (byte)246, (byte)14, (byte)97, (byte)53, (byte)87, (byte)185, (byte)134, (byte)193, (byte)29, (byte)158, (byte)225, (byte)248, (byte)152, (byte)17, (byte)105, (byte)217, (byte)142, (byte)148, (byte)155, (byte)30, (byte)135, (byte)233, (byte)206, (byte)85, (byte)40, (byte)223, (byte)140, (byte)161, (byte)137, (byte)13, (byte)191, (byte)230, (byte)66, (byte)104, (byte)65, (byte)153, (byte)45, (byte)15, (byte)176, (byte)84, (byte)187, (byte)22};
   private static final byte[] Si = new byte[]{(byte)82, (byte)9, (byte)106, (byte)213, (byte)48, (byte)54, (byte)165, (byte)56, (byte)191, (byte)64, (byte)163, (byte)158, (byte)129, (byte)243, (byte)215, (byte)251, (byte)124, (byte)227, (byte)57, (byte)130, (byte)155, (byte)47, (byte)255, (byte)135, (byte)52, (byte)142, (byte)67, (byte)68, (byte)196, (byte)222, (byte)233, (byte)203, (byte)84, (byte)123, (byte)148, (byte)50, (byte)166, (byte)194, (byte)35, (byte)61, (byte)238, (byte)76, (byte)149, (byte)11, (byte)66, (byte)250, (byte)195, (byte)78, (byte)8, (byte)46, (byte)161, (byte)102, (byte)40, (byte)217, (byte)36, (byte)178, (byte)118, (byte)91, (byte)162, (byte)73, (byte)109, (byte)139, (byte)209, (byte)37, (byte)114, (byte)248, (byte)246, (byte)100, (byte)134, (byte)104, (byte)152, (byte)22, (byte)212, (byte)164, (byte)92, (byte)204, (byte)93, (byte)101, (byte)182, (byte)146, (byte)108, (byte)112, (byte)72, (byte)80, (byte)253, (byte)237, (byte)185, (byte)218, (byte)94, (byte)21, (byte)70, (byte)87, (byte)167, (byte)141, (byte)157, (byte)132, (byte)144, (byte)216, (byte)171, (byte)0, (byte)140, (byte)188, (byte)211, (byte)10, (byte)247, (byte)228, (byte)88, (byte)5, (byte)184, (byte)179, (byte)69, (byte)6, (byte)208, (byte)44, (byte)30, (byte)143, (byte)202, (byte)63, (byte)15, (byte)2, (byte)193, (byte)175, (byte)189, (byte)3, (byte)1, (byte)19, (byte)138, (byte)107, (byte)58, (byte)145, (byte)17, (byte)65, (byte)79, (byte)103, (byte)220, (byte)234, (byte)151, (byte)242, (byte)207, (byte)206, (byte)240, (byte)180, (byte)230, (byte)115, (byte)150, (byte)172, (byte)116, (byte)34, (byte)231, (byte)173, (byte)53, (byte)133, (byte)226, (byte)249, (byte)55, (byte)232, (byte)28, (byte)117, (byte)223, (byte)110, (byte)71, (byte)241, (byte)26, (byte)113, (byte)29, (byte)41, (byte)197, (byte)137, (byte)111, (byte)183, (byte)98, (byte)14, (byte)170, (byte)24, (byte)190, (byte)27, (byte)252, (byte)86, (byte)62, (byte)75, (byte)198, (byte)210, (byte)121, (byte)32, (byte)154, (byte)219, (byte)192, (byte)254, (byte)120, (byte)205, (byte)90, (byte)244, (byte)31, (byte)221, (byte)168, (byte)51, (byte)136, (byte)7, (byte)199, (byte)49, (byte)177, (byte)18, (byte)16, (byte)89, (byte)39, (byte)128, (byte)236, (byte)95, (byte)96, (byte)81, (byte)127, (byte)169, (byte)25, (byte)181, (byte)74, (byte)13, (byte)45, (byte)229, (byte)122, (byte)159, (byte)147, (byte)201, (byte)156, (byte)239, (byte)160, (byte)224, (byte)59, (byte)77, (byte)174, (byte)42, (byte)245, (byte)176, (byte)200, (byte)235, (byte)187, (byte)60, (byte)131, (byte)83, (byte)153, (byte)97, (byte)23, (byte)43, (byte)4, (byte)126, (byte)186, (byte)119, (byte)214, (byte)38, (byte)225, (byte)105, (byte)20, (byte)99, (byte)85, (byte)33, (byte)12, (byte)125};
   private static final int[] T0 = new int[]{-1520213050, -2072216328, -1720223762, -1921287178, 234025727, -1117033514, -1318096930, 1422247313, 1345335392, 50397442, -1452841010, 2099981142, 436141799, 1658312629, -424957107, -1703512340, 1170918031, -1652391393, 1086966153, -2021818886, 368769775, -346465870, -918075506, 200339707, -324162239, 1742001331, -39673249, -357585083, -1080255453, -140204973, -1770884380, 1539358875, -1028147339, 486407649, -1366060227, 1780885068, 1513502316, 1094664062, 49805301, 1338821763, 1546925160, -190470831, 887481809, 150073849, -1821281822, 1943591083, 1395732834, 1058346282, 201589768, 1388824469, 1696801606, 1589887901, 672667696, -1583966665, 251987210, -1248159185, 151455502, 907153956, -1686077413, 1038279391, 652995533, 1764173646, -843926913, -1619692054, 453576978, -1635548387, 1949051992, 773462580, 756751158, -1301385508, -296068428, -73359269, -162377052, 1295727478, 1641469623, -827083907, 2066295122, 1055122397, 1898917726, -1752923117, -179088474, 1758581177, 0, 753790401, 1612718144, 536673507, -927878791, -312779850, -1100322092, 1187761037, -641810841, 1262041458, -565556588, -733197160, -396863312, 1255133061, 1808847035, 720367557, -441800113, 385612781, -985447546, -682799718, 1429418854, -1803188975, -817543798, 284817897, 100794884, -2122350594, -263171936, 1144798328, -1163944155, -475486133, -212774494, -22830243, -1069531008, -1970303227, -1382903233, -1130521311, 1211644016, 83228145, -541279133, -1044990345, 1977277103, 1663115586, 806359072, 452984805, 250868733, 1842533055, 1288555905, 336333848, 890442534, 804056259, -513843266, -1567123659, -867941240, 957814574, 1472513171, -223893675, -2105639172, 1195195770, -1402706744, -413311558, 723065138, -1787595802, -1604296512, -1736343271, -783331426, 2145180835, 1713513028, 2116692564, -1416589253, -2088204277, -901364084, 703524551, -742868885, 1007948840, 2044649127, -497131844, 487262998, 1994120109, 1004593371, 1446130276, 1312438900, 503974420, -615954030, 168166924, 1814307912, -463709000, 1573044895, 1859376061, -273896381, -1503501628, -1466855111, -1533700815, 937747667, -1954973198, 854058965, 1137232011, 1496790894, -1217565222, -1936880383, 1691735473, -766620004, -525751991, -1267962664, -95005012, 133494003, 636152527, -1352309302, -1904575756, -374428089, 403179536, -709182865, -2005370640, 1864705354, 1915629148, 605822008, -240736681, -944458637, 1371981463, 602466507, 2094914977, -1670089496, 555687742, -582268010, -591544991, -2037675251, -2054518257, -1871679264, 1111375484, -994724495, -1436129588, -666351472, 84083462, 32962295, 302911004, -1553899070, 1597322602, -111716434, -793134743, -1853454825, 1489093017, 656219450, -1180787161, 954327513, 335083755, -1281845205, 856756514, -1150719534, 1893325225, -1987146233, -1483434957, -1231316179, 572399164, -1836611819, 552200649, 1238290055, -11184726, 2015897680, 2061492133, -1886614525, -123625127, -2138470135, 386731290, -624967835, 837215959, -968736124, -1201116976, -1019133566, -1332111063, 1999449434, 286199582, -877612933, -61582168, -692339859, 974525996};
   private static final int[] Tinv0 = new int[]{1353184337, 1399144830, -1012656358, -1772214470, -882136261, -247096033, -1420232020, -1828461749, 1442459680, -160598355, -1854485368, 625738485, -52959921, -674551099, -2143013594, -1885117771, 1230680542, 1729870373, -1743852987, -507445667, 41234371, 317738113, -1550367091, -956705941, -413167869, -1784901099, -344298049, -631680363, 763608788, -752782248, 694804553, 1154009486, 1787413109, 2021232372, 1799248025, -579749593, -1236278850, 397248752, 1722556617, -1271214467, 407560035, -2110711067, 1613975959, 1165972322, -529046351, -2068943941, 480281086, -1809118983, 1483229296, 436028815, -2022908268, -1208452270, 601060267, -503166094, 1468997603, 715871590, 120122290, 63092015, -1703164538, -1526188077, -226023376, -1297760477, -1167457534, 1552029421, 723308426, -1833666137, -252573709, -1578997426, -839591323, -708967162, 526529745, -1963022652, -1655493068, -1604979806, 853641733, 1978398372, 971801355, -1427152832, 111112542, 1360031421, -108388034, 1023860118, -1375387939, 1186850381, -1249028975, 90031217, 1876166148, -15380384, 620468249, -1746289194, -868007799, 2006899047, -1119688528, -2004121337, 945494503, -605108103, 1191869601, -384875908, -920746760, 0, -2088337399, 1223502642, -1401941730, 1316117100, -67170563, 1446544655, 517320253, 658058550, 1691946762, 564550760, -783000677, 976107044, -1318647284, 266819475, -761860428, -1634624741, 1338359936, -1574904735, 1766553434, 370807324, 179999714, -450191168, 1138762300, 488053522, 185403662, -1379431438, -1180125651, -928440812, -2061897385, 1275557295, -1143105042, -44007517, -1624899081, -1124765092, -985962940, 880737115, 1982415755, -590994485, 1761406390, 1676797112, -891538985, 277177154, 1076008723, 538035844, 2099530373, -130171950, 288553390, 1839278535, 1261411869, -214912292, -330136051, -790380169, 1813426987, -1715900247, -95906799, 577038663, -997393240, 440397984, -668172970, -275762398, -951170681, -1043253031, -22885748, 906744984, -813566554, 685669029, 646887386, -1530942145, -459458004, 227702864, -1681105046, 1648787028, -1038905866, -390539120, 1593260334, -173030526, -1098883681, 2090061929, -1456614033, -1290656305, 999926984, -1484974064, 1852021992, 2075868123, 158869197, -199730834, 28809964, -1466282109, 1701746150, 2129067946, 147831841, -420997649, -644094022, -835293366, -737566742, -696471511, -1347247055, 824393514, 815048134, -1067015627, 935087732, -1496677636, -1328508704, 366520115, 1251476721, -136647615, 240176511, 804688151, -1915335306, 1303441219, 1414376140, -553347356, -474623586, 461924940, -1205916479, 2136040774, 82468509, 1563790337, 1937016826, 776014843, 1511876531, 1389550482, 861278441, 323475053, -1939744870, 2047648055, -1911228327, -1992551445, -299390514, 902390199, -303751967, 1018251130, 1507840668, 1064563285, 2043548696, -1086863501, -355600557, 1537932639, 342834655, -2032450440, -2114736182, 1053059257, 741614648, 1598071746, 1925389590, 203809468, -1958134744, 1100287487, 1895934009, -558691320, -1662733096, -1866377628, 1636092795, 1890988757, 1952214088, 1113045200};
   private static final int m1 = -2139062144;
   private static final int m2 = 2139062143;
   private static final int m3 = 27;
   private static final int[] rcon = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};
   private int C0;
   private int C1;
   private int C2;
   private int C3;
   private int ROUNDS;
   private int[][] WorkingKey;
   private boolean forEncryption;


   public AESEngine() {
      int[][] var1 = (int[][])false;
      this.WorkingKey = var1;
   }

   private int FFmulX(int var1) {
      int var2 = (2139062143 & var1) << 1;
      int var3 = ((-2139062144 & var1) >>> 7) * 27;
      return var2 ^ var3;
   }

   private void decryptBlock(int[][] var1) {
      int var2 = this.C0;
      int var3 = this.ROUNDS;
      int var4 = var1[var3][0];
      int var5 = var2 ^ var4;
      this.C0 = var5;
      int var6 = this.C1;
      int var7 = this.ROUNDS;
      int var8 = var1[var7][1];
      int var9 = var6 ^ var8;
      this.C1 = var9;
      int var10 = this.C2;
      int var11 = this.ROUNDS;
      int var12 = var1[var11][2];
      int var13 = var10 ^ var12;
      this.C2 = var13;
      int var14 = this.C3;
      int var15 = this.ROUNDS;
      int var16 = var1[var15][3];
      int var17 = var14 ^ var16;
      this.C3 = var17;

      int var18;
      int var179;
      for(var18 = this.ROUNDS - 1; var18 > 1; this.C3 = var179) {
         int[] var19 = Tinv0;
         int var20 = this.C0 & 255;
         int var21 = var19[var20];
         int[] var22 = Tinv0;
         int var23 = this.C3 >> 8 & 255;
         int var24 = var22[var23];
         int var25 = this.shift(var24, 24);
         int var26 = var21 ^ var25;
         int[] var27 = Tinv0;
         int var28 = this.C2 >> 16 & 255;
         int var29 = var27[var28];
         int var30 = this.shift(var29, 16);
         int var31 = var26 ^ var30;
         int[] var32 = Tinv0;
         int var33 = this.C1 >> 24 & 255;
         int var34 = var32[var33];
         int var35 = this.shift(var34, 8);
         int var36 = var31 ^ var35;
         int var37 = var1[var18][0];
         int var38 = var36 ^ var37;
         int[] var39 = Tinv0;
         int var40 = this.C1 & 255;
         int var41 = var39[var40];
         int[] var42 = Tinv0;
         int var43 = this.C0 >> 8 & 255;
         int var44 = var42[var43];
         int var45 = this.shift(var44, 24);
         int var46 = var41 ^ var45;
         int[] var47 = Tinv0;
         int var48 = this.C3 >> 16 & 255;
         int var49 = var47[var48];
         int var50 = this.shift(var49, 16);
         int var51 = var46 ^ var50;
         int[] var52 = Tinv0;
         int var53 = this.C2 >> 24 & 255;
         int var54 = var52[var53];
         int var55 = this.shift(var54, 8);
         int var56 = var51 ^ var55;
         int var57 = var1[var18][1];
         int var58 = var56 ^ var57;
         int[] var59 = Tinv0;
         int var60 = this.C2 & 255;
         int var61 = var59[var60];
         int[] var62 = Tinv0;
         int var63 = this.C1 >> 8 & 255;
         int var64 = var62[var63];
         int var65 = this.shift(var64, 24);
         int var66 = var61 ^ var65;
         int[] var67 = Tinv0;
         int var68 = this.C0 >> 16 & 255;
         int var69 = var67[var68];
         int var70 = this.shift(var69, 16);
         int var71 = var66 ^ var70;
         int[] var72 = Tinv0;
         int var73 = this.C3 >> 24 & 255;
         int var74 = var72[var73];
         int var75 = this.shift(var74, 8);
         int var76 = var71 ^ var75;
         int var77 = var1[var18][2];
         int var78 = var76 ^ var77;
         int[] var79 = Tinv0;
         int var80 = this.C3 & 255;
         int var81 = var79[var80];
         int[] var82 = Tinv0;
         int var83 = this.C2 >> 8 & 255;
         int var84 = var82[var83];
         int var85 = this.shift(var84, 24);
         int var86 = var81 ^ var85;
         int[] var87 = Tinv0;
         int var88 = this.C1 >> 16 & 255;
         int var89 = var87[var88];
         int var90 = this.shift(var89, 16);
         int var91 = var86 ^ var90;
         int[] var92 = Tinv0;
         int var93 = this.C0 >> 24 & 255;
         int var94 = var92[var93];
         int var95 = this.shift(var94, 8);
         int var96 = var91 ^ var95;
         int var97 = var18 + -1;
         int var98 = var1[var18][3];
         int var99 = var96 ^ var98;
         int[] var100 = Tinv0;
         int var101 = var38 & 255;
         int var102 = var100[var101];
         int[] var103 = Tinv0;
         int var104 = var99 >> 8 & 255;
         int var105 = var103[var104];
         int var106 = this.shift(var105, 24);
         int var107 = var102 ^ var106;
         int[] var108 = Tinv0;
         int var109 = var78 >> 16 & 255;
         int var110 = var108[var109];
         int var111 = this.shift(var110, 16);
         int var112 = var107 ^ var111;
         int[] var113 = Tinv0;
         int var114 = var58 >> 24 & 255;
         int var115 = var113[var114];
         int var116 = this.shift(var115, 8);
         int var117 = var112 ^ var116;
         int var118 = var1[var97][0];
         int var119 = var117 ^ var118;
         this.C0 = var119;
         int[] var120 = Tinv0;
         int var121 = var58 & 255;
         int var122 = var120[var121];
         int[] var123 = Tinv0;
         int var124 = var38 >> 8 & 255;
         int var125 = var123[var124];
         int var126 = this.shift(var125, 24);
         int var127 = var122 ^ var126;
         int[] var128 = Tinv0;
         int var129 = var99 >> 16 & 255;
         int var130 = var128[var129];
         int var131 = this.shift(var130, 16);
         int var132 = var127 ^ var131;
         int[] var133 = Tinv0;
         int var134 = var78 >> 24 & 255;
         int var135 = var133[var134];
         int var136 = this.shift(var135, 8);
         int var137 = var132 ^ var136;
         int var138 = var1[var97][1];
         int var139 = var137 ^ var138;
         this.C1 = var139;
         int[] var140 = Tinv0;
         int var141 = var78 & 255;
         int var142 = var140[var141];
         int[] var143 = Tinv0;
         int var144 = var58 >> 8 & 255;
         int var145 = var143[var144];
         int var146 = this.shift(var145, 24);
         int var147 = var142 ^ var146;
         int[] var148 = Tinv0;
         int var149 = var38 >> 16 & 255;
         int var150 = var148[var149];
         int var151 = this.shift(var150, 16);
         int var152 = var147 ^ var151;
         int[] var153 = Tinv0;
         int var154 = var99 >> 24 & 255;
         int var155 = var153[var154];
         int var156 = this.shift(var155, 8);
         int var157 = var152 ^ var156;
         int var158 = var1[var97][2];
         int var159 = var157 ^ var158;
         this.C2 = var159;
         int[] var160 = Tinv0;
         int var161 = var99 & 255;
         int var162 = var160[var161];
         int[] var163 = Tinv0;
         int var164 = var78 >> 8 & 255;
         int var165 = var163[var164];
         int var166 = this.shift(var165, 24);
         int var167 = var162 ^ var166;
         int[] var168 = Tinv0;
         int var169 = var58 >> 16 & 255;
         int var170 = var168[var169];
         int var171 = this.shift(var170, 16);
         int var172 = var167 ^ var171;
         int[] var173 = Tinv0;
         int var174 = var38 >> 24 & 255;
         int var175 = var173[var174];
         int var176 = this.shift(var175, 8);
         int var177 = var172 ^ var176;
         var18 = var97 + -1;
         int var178 = var1[var97][3];
         var179 = var177 ^ var178;
      }

      int[] var180 = Tinv0;
      int var181 = this.C0 & 255;
      int var182 = var180[var181];
      int[] var183 = Tinv0;
      int var184 = this.C3 >> 8 & 255;
      int var185 = var183[var184];
      int var186 = this.shift(var185, 24);
      int var187 = var182 ^ var186;
      int[] var188 = Tinv0;
      int var189 = this.C2 >> 16 & 255;
      int var190 = var188[var189];
      int var191 = this.shift(var190, 16);
      int var192 = var187 ^ var191;
      int[] var193 = Tinv0;
      int var194 = this.C1 >> 24 & 255;
      int var195 = var193[var194];
      int var196 = this.shift(var195, 8);
      int var197 = var192 ^ var196;
      int var198 = var1[var18][0];
      int var199 = var197 ^ var198;
      int[] var200 = Tinv0;
      int var201 = this.C1 & 255;
      int var202 = var200[var201];
      int[] var203 = Tinv0;
      int var204 = this.C0 >> 8 & 255;
      int var205 = var203[var204];
      int var206 = this.shift(var205, 24);
      int var207 = var202 ^ var206;
      int[] var208 = Tinv0;
      int var209 = this.C3 >> 16 & 255;
      int var210 = var208[var209];
      int var211 = this.shift(var210, 16);
      int var212 = var207 ^ var211;
      int[] var213 = Tinv0;
      int var214 = this.C2 >> 24 & 255;
      int var215 = var213[var214];
      int var216 = this.shift(var215, 8);
      int var217 = var212 ^ var216;
      int var218 = var1[var18][1];
      int var219 = var217 ^ var218;
      int[] var220 = Tinv0;
      int var221 = this.C2 & 255;
      int var222 = var220[var221];
      int[] var223 = Tinv0;
      int var224 = this.C1 >> 8 & 255;
      int var225 = var223[var224];
      int var226 = this.shift(var225, 24);
      int var227 = var222 ^ var226;
      int[] var228 = Tinv0;
      int var229 = this.C0 >> 16 & 255;
      int var230 = var228[var229];
      int var231 = this.shift(var230, 16);
      int var232 = var227 ^ var231;
      int[] var233 = Tinv0;
      int var234 = this.C3 >> 24 & 255;
      int var235 = var233[var234];
      int var236 = this.shift(var235, 8);
      int var237 = var232 ^ var236;
      int var238 = var1[var18][2];
      int var239 = var237 ^ var238;
      int[] var240 = Tinv0;
      int var241 = this.C3 & 255;
      int var242 = var240[var241];
      int[] var243 = Tinv0;
      int var244 = this.C2 >> 8 & 255;
      int var245 = var243[var244];
      int var246 = this.shift(var245, 24);
      int var247 = var242 ^ var246;
      int[] var248 = Tinv0;
      int var249 = this.C1 >> 16 & 255;
      int var250 = var248[var249];
      int var251 = this.shift(var250, 16);
      int var252 = var247 ^ var251;
      int[] var253 = Tinv0;
      int var254 = this.C0 >> 24 & 255;
      int var255 = var253[var254];
      int var256 = this.shift(var255, 8);
      int var257 = var252 ^ var256;
      int var258 = var1[var18][3];
      int var259 = var257 ^ var258;
      byte[] var260 = Si;
      int var261 = var199 & 255;
      int var262 = var260[var261] & 255;
      byte[] var263 = Si;
      int var264 = var259 >> 8 & 255;
      int var265 = (var263[var264] & 255) << 8;
      int var266 = var262 ^ var265;
      byte[] var267 = Si;
      int var268 = var239 >> 16 & 255;
      int var269 = (var267[var268] & 255) << 16;
      int var270 = var266 ^ var269;
      byte[] var271 = Si;
      int var272 = var219 >> 24 & 255;
      int var273 = var271[var272] << 24;
      int var274 = var270 ^ var273;
      int var275 = var1[0][0];
      int var276 = var274 ^ var275;
      this.C0 = var276;
      byte[] var277 = Si;
      int var278 = var219 & 255;
      int var279 = var277[var278] & 255;
      byte[] var280 = Si;
      int var281 = var199 >> 8 & 255;
      int var282 = (var280[var281] & 255) << 8;
      int var283 = var279 ^ var282;
      byte[] var284 = Si;
      int var285 = var259 >> 16 & 255;
      int var286 = (var284[var285] & 255) << 16;
      int var287 = var283 ^ var286;
      byte[] var288 = Si;
      int var289 = var239 >> 24 & 255;
      int var290 = var288[var289] << 24;
      int var291 = var287 ^ var290;
      int var292 = var1[0][1];
      int var293 = var291 ^ var292;
      this.C1 = var293;
      byte[] var294 = Si;
      int var295 = var239 & 255;
      int var296 = var294[var295] & 255;
      byte[] var297 = Si;
      int var298 = var219 >> 8 & 255;
      int var299 = (var297[var298] & 255) << 8;
      int var300 = var296 ^ var299;
      byte[] var301 = Si;
      int var302 = var199 >> 16 & 255;
      int var303 = (var301[var302] & 255) << 16;
      int var304 = var300 ^ var303;
      byte[] var305 = Si;
      int var306 = var259 >> 24 & 255;
      int var307 = var305[var306] << 24;
      int var308 = var304 ^ var307;
      int var309 = var1[0][2];
      int var310 = var308 ^ var309;
      this.C2 = var310;
      byte[] var311 = Si;
      int var312 = var259 & 255;
      int var313 = var311[var312] & 255;
      byte[] var314 = Si;
      int var315 = var239 >> 8 & 255;
      int var316 = (var314[var315] & 255) << 8;
      int var317 = var313 ^ var316;
      byte[] var318 = Si;
      int var319 = var219 >> 16 & 255;
      int var320 = (var318[var319] & 255) << 16;
      int var321 = var317 ^ var320;
      byte[] var322 = Si;
      int var323 = var199 >> 24 & 255;
      int var324 = var322[var323] << 24;
      int var325 = var321 ^ var324;
      int var326 = var1[0][3];
      int var327 = var325 ^ var326;
      this.C3 = var327;
   }

   private void encryptBlock(int[][] var1) {
      int var2 = this.C0;
      int var3 = var1[0][0];
      int var4 = var2 ^ var3;
      this.C0 = var4;
      int var5 = this.C1;
      int var6 = var1[0][1];
      int var7 = var5 ^ var6;
      this.C1 = var7;
      int var8 = this.C2;
      int var9 = var1[0][2];
      int var10 = var8 ^ var9;
      this.C2 = var10;
      int var11 = this.C3;
      int var12 = var1[0][3];
      int var13 = var11 ^ var12;
      this.C3 = var13;
      int var14 = 1;

      while(true) {
         int var15 = this.ROUNDS - 1;
         if(var14 >= var15) {
            int[] var177 = T0;
            int var178 = this.C0 & 255;
            int var179 = var177[var178];
            int[] var180 = T0;
            int var181 = this.C1 >> 8 & 255;
            int var182 = var180[var181];
            int var183 = this.shift(var182, 24);
            int var184 = var179 ^ var183;
            int[] var185 = T0;
            int var186 = this.C2 >> 16 & 255;
            int var187 = var185[var186];
            int var188 = this.shift(var187, 16);
            int var189 = var184 ^ var188;
            int[] var190 = T0;
            int var191 = this.C3 >> 24 & 255;
            int var192 = var190[var191];
            int var193 = this.shift(var192, 8);
            int var194 = var189 ^ var193;
            int var195 = var1[var14][0];
            int var196 = var194 ^ var195;
            int[] var197 = T0;
            int var198 = this.C1 & 255;
            int var199 = var197[var198];
            int[] var200 = T0;
            int var201 = this.C2 >> 8 & 255;
            int var202 = var200[var201];
            int var203 = this.shift(var202, 24);
            int var204 = var199 ^ var203;
            int[] var205 = T0;
            int var206 = this.C3 >> 16 & 255;
            int var207 = var205[var206];
            int var208 = this.shift(var207, 16);
            int var209 = var204 ^ var208;
            int[] var210 = T0;
            int var211 = this.C0 >> 24 & 255;
            int var212 = var210[var211];
            int var213 = this.shift(var212, 8);
            int var214 = var209 ^ var213;
            int var215 = var1[var14][1];
            int var216 = var214 ^ var215;
            int[] var217 = T0;
            int var218 = this.C2 & 255;
            int var219 = var217[var218];
            int[] var220 = T0;
            int var221 = this.C3 >> 8 & 255;
            int var222 = var220[var221];
            int var223 = this.shift(var222, 24);
            int var224 = var219 ^ var223;
            int[] var225 = T0;
            int var226 = this.C0 >> 16 & 255;
            int var227 = var225[var226];
            int var228 = this.shift(var227, 16);
            int var229 = var224 ^ var228;
            int[] var230 = T0;
            int var231 = this.C1 >> 24 & 255;
            int var232 = var230[var231];
            int var233 = this.shift(var232, 8);
            int var234 = var229 ^ var233;
            int var235 = var1[var14][2];
            int var236 = var234 ^ var235;
            int[] var237 = T0;
            int var238 = this.C3 & 255;
            int var239 = var237[var238];
            int[] var240 = T0;
            int var241 = this.C0 >> 8 & 255;
            int var242 = var240[var241];
            int var243 = this.shift(var242, 24);
            int var244 = var239 ^ var243;
            int[] var245 = T0;
            int var246 = this.C1 >> 16 & 255;
            int var247 = var245[var246];
            int var248 = this.shift(var247, 16);
            int var249 = var244 ^ var248;
            int[] var250 = T0;
            int var251 = this.C2 >> 24 & 255;
            int var252 = var250[var251];
            int var253 = this.shift(var252, 8);
            int var254 = var249 ^ var253;
            int var255 = var14 + 1;
            int var256 = var1[var14][3];
            int var257 = var254 ^ var256;
            byte[] var258 = S;
            int var259 = var196 & 255;
            int var260 = var258[var259] & 255;
            byte[] var261 = S;
            int var262 = var216 >> 8 & 255;
            int var263 = (var261[var262] & 255) << 8;
            int var264 = var260 ^ var263;
            byte[] var265 = S;
            int var266 = var236 >> 16 & 255;
            int var267 = (var265[var266] & 255) << 16;
            int var268 = var264 ^ var267;
            byte[] var269 = S;
            int var270 = var257 >> 24 & 255;
            int var271 = var269[var270] << 24;
            int var272 = var268 ^ var271;
            int var273 = var1[var255][0];
            int var274 = var272 ^ var273;
            this.C0 = var274;
            byte[] var275 = S;
            int var276 = var216 & 255;
            int var277 = var275[var276] & 255;
            byte[] var278 = S;
            int var279 = var236 >> 8 & 255;
            int var280 = (var278[var279] & 255) << 8;
            int var281 = var277 ^ var280;
            byte[] var282 = S;
            int var283 = var257 >> 16 & 255;
            int var284 = (var282[var283] & 255) << 16;
            int var285 = var281 ^ var284;
            byte[] var286 = S;
            int var287 = var196 >> 24 & 255;
            int var288 = var286[var287] << 24;
            int var289 = var285 ^ var288;
            int var290 = var1[var255][1];
            int var291 = var289 ^ var290;
            this.C1 = var291;
            byte[] var292 = S;
            int var293 = var236 & 255;
            int var294 = var292[var293] & 255;
            byte[] var295 = S;
            int var296 = var257 >> 8 & 255;
            int var297 = (var295[var296] & 255) << 8;
            int var298 = var294 ^ var297;
            byte[] var299 = S;
            int var300 = var196 >> 16 & 255;
            int var301 = (var299[var300] & 255) << 16;
            int var302 = var298 ^ var301;
            byte[] var303 = S;
            int var304 = var216 >> 24 & 255;
            int var305 = var303[var304] << 24;
            int var306 = var302 ^ var305;
            int var307 = var1[var255][2];
            int var308 = var306 ^ var307;
            this.C2 = var308;
            byte[] var309 = S;
            int var310 = var257 & 255;
            int var311 = var309[var310] & 255;
            byte[] var312 = S;
            int var313 = var196 >> 8 & 255;
            int var314 = (var312[var313] & 255) << 8;
            int var315 = var311 ^ var314;
            byte[] var316 = S;
            int var317 = var216 >> 16 & 255;
            int var318 = (var316[var317] & 255) << 16;
            int var319 = var315 ^ var318;
            byte[] var320 = S;
            int var321 = var236 >> 24 & 255;
            int var322 = var320[var321] << 24;
            int var323 = var319 ^ var322;
            int var324 = var1[var255][3];
            int var325 = var323 ^ var324;
            this.C3 = var325;
            return;
         }

         int[] var16 = T0;
         int var17 = this.C0 & 255;
         int var18 = var16[var17];
         int[] var19 = T0;
         int var20 = this.C1 >> 8 & 255;
         int var21 = var19[var20];
         int var22 = this.shift(var21, 24);
         int var23 = var18 ^ var22;
         int[] var24 = T0;
         int var25 = this.C2 >> 16 & 255;
         int var26 = var24[var25];
         int var27 = this.shift(var26, 16);
         int var28 = var23 ^ var27;
         int[] var29 = T0;
         int var30 = this.C3 >> 24 & 255;
         int var31 = var29[var30];
         int var32 = this.shift(var31, 8);
         int var33 = var28 ^ var32;
         int var34 = var1[var14][0];
         int var35 = var33 ^ var34;
         int[] var36 = T0;
         int var37 = this.C1 & 255;
         int var38 = var36[var37];
         int[] var39 = T0;
         int var40 = this.C2 >> 8 & 255;
         int var41 = var39[var40];
         int var42 = this.shift(var41, 24);
         int var43 = var38 ^ var42;
         int[] var44 = T0;
         int var45 = this.C3 >> 16 & 255;
         int var46 = var44[var45];
         int var47 = this.shift(var46, 16);
         int var48 = var43 ^ var47;
         int[] var49 = T0;
         int var50 = this.C0 >> 24 & 255;
         int var51 = var49[var50];
         int var52 = this.shift(var51, 8);
         int var53 = var48 ^ var52;
         int var54 = var1[var14][1];
         int var55 = var53 ^ var54;
         int[] var56 = T0;
         int var57 = this.C2 & 255;
         int var58 = var56[var57];
         int[] var59 = T0;
         int var60 = this.C3 >> 8 & 255;
         int var61 = var59[var60];
         int var62 = this.shift(var61, 24);
         int var63 = var58 ^ var62;
         int[] var64 = T0;
         int var65 = this.C0 >> 16 & 255;
         int var66 = var64[var65];
         int var67 = this.shift(var66, 16);
         int var68 = var63 ^ var67;
         int[] var69 = T0;
         int var70 = this.C1 >> 24 & 255;
         int var71 = var69[var70];
         int var72 = this.shift(var71, 8);
         int var73 = var68 ^ var72;
         int var74 = var1[var14][2];
         int var75 = var73 ^ var74;
         int[] var76 = T0;
         int var77 = this.C3 & 255;
         int var78 = var76[var77];
         int[] var79 = T0;
         int var80 = this.C0 >> 8 & 255;
         int var81 = var79[var80];
         int var82 = this.shift(var81, 24);
         int var83 = var78 ^ var82;
         int[] var84 = T0;
         int var85 = this.C1 >> 16 & 255;
         int var86 = var84[var85];
         int var87 = this.shift(var86, 16);
         int var88 = var83 ^ var87;
         int[] var89 = T0;
         int var90 = this.C2 >> 24 & 255;
         int var91 = var89[var90];
         int var92 = this.shift(var91, 8);
         int var93 = var88 ^ var92;
         int var94 = var14 + 1;
         int var95 = var1[var14][3];
         int var96 = var93 ^ var95;
         int[] var97 = T0;
         int var98 = var35 & 255;
         int var99 = var97[var98];
         int[] var100 = T0;
         int var101 = var55 >> 8 & 255;
         int var102 = var100[var101];
         int var103 = this.shift(var102, 24);
         int var104 = var99 ^ var103;
         int[] var105 = T0;
         int var106 = var75 >> 16 & 255;
         int var107 = var105[var106];
         int var108 = this.shift(var107, 16);
         int var109 = var104 ^ var108;
         int[] var110 = T0;
         int var111 = var96 >> 24 & 255;
         int var112 = var110[var111];
         int var113 = this.shift(var112, 8);
         int var114 = var109 ^ var113;
         int var115 = var1[var94][0];
         int var116 = var114 ^ var115;
         this.C0 = var116;
         int[] var117 = T0;
         int var118 = var55 & 255;
         int var119 = var117[var118];
         int[] var120 = T0;
         int var121 = var75 >> 8 & 255;
         int var122 = var120[var121];
         int var123 = this.shift(var122, 24);
         int var124 = var119 ^ var123;
         int[] var125 = T0;
         int var126 = var96 >> 16 & 255;
         int var127 = var125[var126];
         int var128 = this.shift(var127, 16);
         int var129 = var124 ^ var128;
         int[] var130 = T0;
         int var131 = var35 >> 24 & 255;
         int var132 = var130[var131];
         int var133 = this.shift(var132, 8);
         int var134 = var129 ^ var133;
         int var135 = var1[var94][1];
         int var136 = var134 ^ var135;
         this.C1 = var136;
         int[] var137 = T0;
         int var138 = var75 & 255;
         int var139 = var137[var138];
         int[] var140 = T0;
         int var141 = var96 >> 8 & 255;
         int var142 = var140[var141];
         int var143 = this.shift(var142, 24);
         int var144 = var139 ^ var143;
         int[] var145 = T0;
         int var146 = var35 >> 16 & 255;
         int var147 = var145[var146];
         int var148 = this.shift(var147, 16);
         int var149 = var144 ^ var148;
         int[] var150 = T0;
         int var151 = var55 >> 24 & 255;
         int var152 = var150[var151];
         int var153 = this.shift(var152, 8);
         int var154 = var149 ^ var153;
         int var155 = var1[var94][2];
         int var156 = var154 ^ var155;
         this.C2 = var156;
         int[] var157 = T0;
         int var158 = var96 & 255;
         int var159 = var157[var158];
         int[] var160 = T0;
         int var161 = var35 >> 8 & 255;
         int var162 = var160[var161];
         int var163 = this.shift(var162, 24);
         int var164 = var159 ^ var163;
         int[] var165 = T0;
         int var166 = var55 >> 16 & 255;
         int var167 = var165[var166];
         int var168 = this.shift(var167, 16);
         int var169 = var164 ^ var168;
         int[] var170 = T0;
         int var171 = var75 >> 24 & 255;
         int var172 = var170[var171];
         int var173 = this.shift(var172, 8);
         int var174 = var169 ^ var173;
         var14 = var94 + 1;
         int var175 = var1[var94][3];
         int var176 = var174 ^ var175;
         this.C3 = var176;
      }
   }

   private int[][] generateWorkingKey(byte[] var1, boolean var2) {
      int var3 = var1.length / 4;
      if(var3 == 4 || var3 == 6 || var3 == 8) {
         int var4 = var3 * 4;
         int var5 = var1.length;
         if(var4 == var5) {
            int var6 = var3 + 6;
            this.ROUNDS = var6;
            int var7 = this.ROUNDS + 1;
            int[] var8 = new int[]{var7, 4};
            int[][] var9 = (int[][])Array.newInstance(Integer.TYPE, var8);
            int var10 = 0;
            int var11 = 0;

            while(true) {
               int var12 = var1.length;
               if(var11 >= var12) {
                  int var26 = this.ROUNDS + 1 << 2;

                  for(int var27 = var3; var27 < var26; ++var27) {
                     int var28 = var27 - 1 >> 2;
                     int[] var29 = var9[var28];
                     int var30 = var27 - 1 & 3;
                     int var31 = var29[var30];
                     if(var27 % var3 == 0) {
                        int var32 = this.shift(var31, 8);
                        int var33 = this.subWord(var32);
                        int[] var34 = rcon;
                        int var35 = var27 / var3 - 1;
                        int var36 = var34[var35];
                        var31 = var33 ^ var36;
                     } else if(var3 > 6 && var27 % var3 == 4) {
                        var31 = this.subWord(var31);
                     }

                     int var37 = var27 >> 2;
                     int[] var38 = var9[var37];
                     int var39 = var27 & 3;
                     int var40 = var27 - var3 >> 2;
                     int[] var41 = var9[var40];
                     int var42 = var27 - var3 & 3;
                     int var43 = var41[var42] ^ var31;
                     var38[var39] = var43;
                  }

                  if(!var2) {
                     int var44 = 1;

                     while(true) {
                        int var45 = this.ROUNDS;
                        if(var44 >= var45) {
                           break;
                        }

                        int var49;
                        for(byte var50 = 0; var50 < 4; var49 = var50 + 1) {
                           int[] var46 = var9[var44];
                           int var47 = var9[var44][var50];
                           int var48 = this.inv_mcol(var47);
                           var46[var50] = var48;
                        }

                        ++var44;
                     }
                  }

                  return var9;
               }

               int var13 = var10 >> 2;
               int[] var14 = var9[var13];
               int var15 = var10 & 3;
               int var16 = var1[var11] & 255;
               int var17 = var11 + 1;
               int var18 = (var1[var17] & 255) << 8;
               int var19 = var16 | var18;
               int var20 = var11 + 2;
               int var21 = (var1[var20] & 255) << 16;
               int var22 = var19 | var21;
               int var23 = var11 + 3;
               int var24 = var1[var23] << 24;
               int var25 = var22 | var24;
               var14[var15] = var25;
               var11 += 4;
               ++var10;
            }
         }
      }

      throw new IllegalArgumentException("Key length not 128/192/256 bits.");
   }

   private int inv_mcol(int var1) {
      int var2 = this.FFmulX(var1);
      int var3 = this.FFmulX(var2);
      int var4 = this.FFmulX(var3);
      int var5 = var1 ^ var4;
      int var6 = var2 ^ var3 ^ var4;
      int var7 = var2 ^ var5;
      int var8 = this.shift(var7, 8);
      int var9 = var6 ^ var8;
      int var10 = var3 ^ var5;
      int var11 = this.shift(var10, 16);
      int var12 = var9 ^ var11;
      int var13 = this.shift(var5, 24);
      return var12 ^ var13;
   }

   private void packBlock(byte[] var1, int var2) {
      int var4 = var2 + 1;
      byte var5 = (byte)this.C0;
      var1[var2] = var5;
      int var6 = var4 + 1;
      byte var7 = (byte)(this.C0 >> 8);
      var1[var4] = var7;
      int var8 = var6 + 1;
      byte var9 = (byte)(this.C0 >> 16);
      var1[var6] = var9;
      int var10 = var8 + 1;
      byte var11 = (byte)(this.C0 >> 24);
      var1[var8] = var11;
      int var12 = var10 + 1;
      byte var13 = (byte)this.C1;
      var1[var10] = var13;
      int var14 = var12 + 1;
      byte var15 = (byte)(this.C1 >> 8);
      var1[var12] = var15;
      int var16 = var14 + 1;
      byte var17 = (byte)(this.C1 >> 16);
      var1[var14] = var17;
      int var18 = var16 + 1;
      byte var19 = (byte)(this.C1 >> 24);
      var1[var16] = var19;
      int var20 = var18 + 1;
      byte var21 = (byte)this.C2;
      var1[var18] = var21;
      int var22 = var20 + 1;
      byte var23 = (byte)(this.C2 >> 8);
      var1[var20] = var23;
      int var24 = var22 + 1;
      byte var25 = (byte)(this.C2 >> 16);
      var1[var22] = var25;
      int var26 = var24 + 1;
      byte var27 = (byte)(this.C2 >> 24);
      var1[var24] = var27;
      int var28 = var26 + 1;
      byte var29 = (byte)this.C3;
      var1[var26] = var29;
      int var30 = var28 + 1;
      byte var31 = (byte)(this.C3 >> 8);
      var1[var28] = var31;
      int var32 = var30 + 1;
      byte var33 = (byte)(this.C3 >> 16);
      var1[var30] = var33;
      int var34 = var32 + 1;
      byte var35 = (byte)(this.C3 >> 24);
      var1[var32] = var35;
   }

   private int shift(int var1, int var2) {
      int var3 = var1 >>> var2;
      int var4 = -var2;
      int var5 = var1 << var4;
      return var3 | var5;
   }

   private int subWord(int var1) {
      byte[] var2 = S;
      int var3 = var1 & 255;
      int var4 = var2[var3] & 255;
      byte[] var5 = S;
      int var6 = var1 >> 8 & 255;
      int var7 = (var5[var6] & 255) << 8;
      int var8 = var4 | var7;
      byte[] var9 = S;
      int var10 = var1 >> 16 & 255;
      int var11 = (var9[var10] & 255) << 16;
      int var12 = var8 | var11;
      byte[] var13 = S;
      int var14 = var1 >> 24 & 255;
      int var15 = var13[var14] << 24;
      return var12 | var15;
   }

   private void unpackBlock(byte[] var1, int var2) {
      int var4 = var2 + 1;
      int var5 = var1[var2] & 255;
      this.C0 = var5;
      int var6 = this.C0;
      int var7 = var4 + 1;
      int var8 = (var1[var4] & 255) << 8;
      int var9 = var6 | var8;
      this.C0 = var9;
      int var10 = this.C0;
      int var11 = var7 + 1;
      int var12 = (var1[var7] & 255) << 16;
      int var13 = var10 | var12;
      this.C0 = var13;
      int var14 = this.C0;
      int var15 = var11 + 1;
      int var16 = var1[var11] << 24;
      int var17 = var14 | var16;
      this.C0 = var17;
      int var18 = var15 + 1;
      int var19 = var1[var15] & 255;
      this.C1 = var19;
      int var20 = this.C1;
      int var21 = var18 + 1;
      int var22 = (var1[var18] & 255) << 8;
      int var23 = var20 | var22;
      this.C1 = var23;
      int var24 = this.C1;
      int var25 = var21 + 1;
      int var26 = (var1[var21] & 255) << 16;
      int var27 = var24 | var26;
      this.C1 = var27;
      int var28 = this.C1;
      int var29 = var25 + 1;
      int var30 = var1[var25] << 24;
      int var31 = var28 | var30;
      this.C1 = var31;
      int var32 = var29 + 1;
      int var33 = var1[var29] & 255;
      this.C2 = var33;
      int var34 = this.C2;
      int var35 = var32 + 1;
      int var36 = (var1[var32] & 255) << 8;
      int var37 = var34 | var36;
      this.C2 = var37;
      int var38 = this.C2;
      int var39 = var35 + 1;
      int var40 = (var1[var35] & 255) << 16;
      int var41 = var38 | var40;
      this.C2 = var41;
      int var42 = this.C2;
      int var43 = var39 + 1;
      int var44 = var1[var39] << 24;
      int var45 = var42 | var44;
      this.C2 = var45;
      int var46 = var43 + 1;
      int var47 = var1[var43] & 255;
      this.C3 = var47;
      int var48 = this.C3;
      int var49 = var46 + 1;
      int var50 = (var1[var46] & 255) << 8;
      int var51 = var48 | var50;
      this.C3 = var51;
      int var52 = this.C3;
      int var53 = var49 + 1;
      int var54 = (var1[var49] & 255) << 16;
      int var55 = var52 | var54;
      this.C3 = var55;
      int var56 = this.C3;
      int var57 = var53 + 1;
      int var58 = var1[var53] << 24;
      int var59 = var56 | var58;
      this.C3 = var59;
   }

   public String getAlgorithmName() {
      return "AES";
   }

   public int getBlockSize() {
      return 16;
   }

   public void init(boolean var1, CipherParameters var2) {
      if(var2 instanceof KeyParameter) {
         byte[] var3 = ((KeyParameter)var2).getKey();
         int[][] var4 = this.generateWorkingKey(var3, var1);
         this.WorkingKey = var4;
         this.forEncryption = var1;
      } else {
         StringBuilder var5 = (new StringBuilder()).append("invalid parameter passed to AES init - ");
         String var6 = var2.getClass().getName();
         String var7 = var5.append(var6).toString();
         throw new IllegalArgumentException(var7);
      }
   }

   public int processBlock(byte[] var1, int var2, byte[] var3, int var4) {
      if(this.WorkingKey == null) {
         throw new IllegalStateException("AES engine not initialised");
      } else {
         int var5 = var2 + 16;
         int var6 = var1.length;
         if(var5 > var6) {
            throw new DataLengthException("input buffer too short");
         } else {
            int var7 = var4 + 16;
            int var8 = var3.length;
            if(var7 > var8) {
               throw new DataLengthException("output buffer too short");
            } else {
               if(this.forEncryption) {
                  this.unpackBlock(var1, var2);
                  int[][] var9 = this.WorkingKey;
                  this.encryptBlock(var9);
                  this.packBlock(var3, var4);
               } else {
                  this.unpackBlock(var1, var2);
                  int[][] var10 = this.WorkingKey;
                  this.decryptBlock(var10);
                  this.packBlock(var3, var4);
               }

               return 16;
            }
         }
      }
   }

   public void reset() {}
}
